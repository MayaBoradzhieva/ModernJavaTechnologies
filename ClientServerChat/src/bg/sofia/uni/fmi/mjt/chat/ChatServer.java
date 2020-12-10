package bg.sofia.uni.fmi.mjt.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ChatServer {
    private static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 7777;
    private static final int BUFFER_SIZE = 1024;
    private static final int SLEEP_MILLIS = 200;

    private static final String NICK_COMMAND = "nick";
    private static final String SEND_COMMAND = "send";
    private static final String SEND_ALL_COMMAND = "send-all";
    private static final String LIST_USERS_COMMAND = "list-users";
    private static final String DISCONNECT_COMMAND = "disconnect";

    private static Map<String, SocketChannel> users = new HashMap<>();

    public static SocketChannel getUser(String username) {

        if (users.containsKey(username)) {
            return users.get(username);
        }

        return null;
    }

    public static String getCurrentTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return localDateTime.format(formatter);
    }

    public static String findCurrentUsername(SocketChannel currentSocketChannel) {
        String currentUsername = null;
        for (Entry<String, SocketChannel> entry : users.entrySet()) {

            SocketChannel userSocketChannel = entry.getValue();
            if (userSocketChannel == currentSocketChannel) {
                currentUsername = entry.getKey();
            }
        }

        return currentUsername;
    }

    public static void send(ByteBuffer buffer, String receiverUsername, String senderUsername, String message)
            throws IOException {
        if (users.containsKey(receiverUsername)) {
            String currentTime = "[" + getCurrentTime() + "] ";
            buffer.clear();
            buffer.put((currentTime + senderUsername + ": " + message).getBytes());

            buffer.flip();
            users.get(receiverUsername).write(buffer);
        } else {
            String offlineMessage = "User " + receiverUsername + " seems to be offline";
            buffer.clear();
            buffer.put(offlineMessage.getBytes());

            buffer.flip();

            users.get(senderUsername).write(buffer);
        }
    }

    public static void sendAll(ByteBuffer buffer, String senderUsername, String message) throws IOException {
        String currentTime = "[" + getCurrentTime() + "] ";

        for (Entry<String, SocketChannel> entry : users.entrySet()) {
            String receiverUsername = entry.getKey().strip();

            if (senderUsername.equals(receiverUsername)) { // not sending to himself
                continue;
            }

            buffer.clear();
            buffer.put((currentTime + senderUsername + ": " + message).getBytes());
            buffer.flip();
            users.get(receiverUsername).write(buffer);
        }
    }

    public static void showMessageCurrentUser(ByteBuffer buffer, SocketChannel currentSocketChannel, String message)
            throws IOException {
        buffer.clear();
        buffer.put(message.getBytes());
        buffer.flip();

        currentSocketChannel.write(buffer);

    }

    public static void commandSend(ByteBuffer buffer, SocketChannel currentSocketChannel, String[] tokens) {
        String receiverUsername = tokens[1].strip();
        String[] messageTokens = Arrays.copyOfRange(tokens, 2, tokens.length);
        String message = String.join(" ", messageTokens);

        String senderUsername = findCurrentUsername(currentSocketChannel);

        try {
            send(buffer, receiverUsername, senderUsername, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void commandSendAll(ByteBuffer buffer, SocketChannel currentSocketChannel, String[] tokens) {
        String[] messageTokens = Arrays.copyOfRange(tokens, 1, tokens.length);
        String message = String.join(" ", messageTokens);

        // first find the sender
        String senderUsername = findCurrentUsername(currentSocketChannel);

        // then send all
        try {
            sendAll(buffer, senderUsername, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {

            serverSocketChannel.bind(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            serverSocketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

            while (true) {
                int readyChannels = selector.select(); // number of keys which operations were updated
                if (readyChannels == 0) {
                    System.out.println("Still waiting for a ready channel...");
                    try {
                        Thread.sleep(SLEEP_MILLIS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                Set<SelectionKey> selectedKeys = selector.selectedKeys(); // returns the selector's key-set
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        buffer.clear();
                        int readBytes = socketChannel.read(buffer); // returns the bytes read
                        if (readBytes <= 0) {
                            System.out.println("Nothing to read, will close channel");
                            socketChannel.close();
                            break;
                        }

                        buffer.flip();

                        // processing message

                        String line = new String(buffer.array(), 0, buffer.limit());
                        String[] tokens = line.split(" ");
                        String command = tokens[0].strip();

                        // COMMANDS

                        if (command.equals(NICK_COMMAND)) {
                            String username = tokens[1].strip();

                            if (!users.containsKey(username)) {
                                users.put(username, socketChannel);
                            }

                        } else if (command.equals(SEND_COMMAND)) {

                            commandSend(buffer, socketChannel, tokens);

                        } else if (command.equals(SEND_ALL_COMMAND)) {

                            commandSendAll(buffer, socketChannel, tokens);

                        } else if (command.equals(LIST_USERS_COMMAND)) {

                            List<String> list = new ArrayList<String>(users.keySet());
                            String activeUsers = String.join(" ", list);

                            showMessageCurrentUser(buffer, socketChannel, activeUsers);

                        } else if (command.equals(DISCONNECT_COMMAND)) {

                            // find the disconnected user
                            String disconnectedUser = findCurrentUsername(socketChannel);
                            String message = "Disconnected from server.";

                            showMessageCurrentUser(buffer, socketChannel, message);

                            users.remove(disconnectedUser);
                        }

                    } else if (key.isAcceptable()) {
                        ServerSocketChannel sockChannel = (ServerSocketChannel) key.channel();
                        SocketChannel accept = sockChannel.accept();
                        accept.configureBlocking(false);
                        accept.register(selector, SelectionKey.OP_READ);
                    }

                    keyIterator.remove(); // removes the last element returned by this iterator
                }

            }

        } catch (

        IOException e) {
            System.out.println("There is a problem with the server socket");
            e.printStackTrace();
        }
    }
}
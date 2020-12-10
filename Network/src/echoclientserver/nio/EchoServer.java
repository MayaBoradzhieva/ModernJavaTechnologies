package echoclientserver.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class EchoServer {
    private static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 7777;
    private static final int BUFFER_SIZE = 1024;
    private static final int SLEEP_MILLIS = 200;

    public static void main(String[] args) {
        Map<String, SocketChannel> users = new HashMap<>();
       // users.put("Maya", );
        
        
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
                    if (key.isReadable()) { //OP_READ ->tests whether this key's channel is ready for reading
                        SocketChannel sc = (SocketChannel) key.channel(); //returns the channel for which this key was created

                        buffer.clear();
                        int r = sc.read(buffer); // returns the bytes read
                        if (r <= 0) {
                            System.out.println("nothing to read, will close channel");
                            sc.close();
                            break;
                        }

                        buffer.flip();
                        sc.write(buffer);

                    } else if (key.isAcceptable()) { // tests whether this key's channel is ready to accept a new socket connection
                        ServerSocketChannel sockChannel = (ServerSocketChannel) key.channel(); // returns the channel for which this key was created
                        SocketChannel accept = sockChannel.accept(); // accepts the connection made to this channel's socket
                        accept.configureBlocking(false);
                        accept.register(selector, SelectionKey.OP_READ); // register this channel with the given selector
                    }

                    keyIterator.remove(); // removes the last element returned by this iterator
                }

            }
            
        } catch (IOException e) {
            System.out.println("There is a problem with the server socket");
            e.printStackTrace();
        }
    }
}
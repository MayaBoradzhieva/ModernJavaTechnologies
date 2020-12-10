package bg.sofia.uni.fmi.mjt.christmas;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @author Maya Boradzhieva
 * @version 22.12.2019
 *
 */
public class Workshop {
    private static final int ELVES = 20;
    private Queue<Gift> giftsToMake = new LinkedList<>();
    private Elf[] elves;
    private boolean isChristmasTime = false;
    private int wishCounter = 0;

    /**
     * The elves start working when the workshop is created
     */
    public Workshop() {
        startWorking();
    }

    /**
     * 
     * @param gift is added to the elves' backlog.
     * 
     **/
    public synchronized void postWish(Gift gift) {
        giftsToMake.add(gift);
        wishCounter++;

        this.notify();
    }

    /**
     * @return an array of the elves working in Santa's workshop.
     **/
    public Elf[] getElves() {
        return elves;
    }

    /**
     * @return the next gift from the elves'
     * backlog that has to be manufactured.
     **/
    public synchronized Gift nextGift() {
        while (!isChristmasTime && giftsToMake.isEmpty()) {
            try {
                this.wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return (isChristmasTime || giftsToMake.isEmpty()) ?
                null : giftsToMake.poll();
    }

    /**
     * The elves in the workshop start working
     */
    private void startWorking() {
        this.elves = new Elf[ELVES];

        for (int i = 0; i < ELVES; i++) {
            elves[i] = new Elf(i, this);
            elves[i].start();
        }

    }

    /**
     * notifies it's already Christmas
     */
    public synchronized void setChristmasTime() {
        this.isChristmasTime = true;
        this.notifyAll();
    }

    /**
     * 
     * @return wishCounter the number of kids' wishes
     */
    public synchronized int getWishCounter() {
        return wishCounter;
    }
}
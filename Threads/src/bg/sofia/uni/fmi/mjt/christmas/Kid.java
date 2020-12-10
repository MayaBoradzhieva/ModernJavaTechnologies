package bg.sofia.uni.fmi.mjt.christmas;

/**
 * 
 * @author Maya Boradzhieva
 * @version 22.12.2019
 *
 */
public class Kid implements Runnable {

    private static final int WISH_TIME = 2000;

    private Workshop workshop;

    /**
     * 
     * @param workshop the kid sends its wish to the workshop
     */
    public Kid(Workshop workshop) {
        this.workshop = workshop;
    }

    /**
     * The kid is making a wish.
     *  The method sends the wish to Santa's workshop.
     */
    private void makeWish() {
        Gift gift = Gift.getGift();
        workshop.postWish(gift);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(WISH_TIME);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        makeWish();
    }

}
package bg.sofia.uni.fmi.mjt.christmas;

/**
 * Elf is the worker in Santa's workshop
 * 
 * @author Maya Boradzhieva
 * @version 22.12.2019
 */
public class Elf extends Thread {
    private Workshop workshop;
    private int id;
    private int totalGiftsCrafted;

    /**
     * Creates elf with the according id and workshop
     * 
     * @param id       of the elf
     * @param workshop the according workshop
     */
    public Elf(int id, Workshop workshop) {

        this.workshop = workshop;
        this.id = id;
    }

    /**
     * Gets a wish from the backlog and creates the wanted gift.
     */
    public void craftGift() {
        Gift gift;

        while ((gift = workshop.nextGift()) != null) {

            try {
                Thread.sleep(gift.getCraftTime());
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            totalGiftsCrafted++;
        }

    }

    /**
     * @return the total number of gifts that
     * the given elf has crafted.
     */
    public int getTotalGiftsCrafted() {
        return totalGiftsCrafted;
    }

    @Override
    public void run() {
        craftGift();

    }
}
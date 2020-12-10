package bg.sofia.uni.fmi.mjt.christmas;

import java.util.Random;

/**
 * Gift representing every child's wish
 * 
 * @author Maya Boradzhieva
 * @version 22.12.2019
 *
 */
public enum Gift {

    /**
     * Bike gift.
     */
    BIKE("Bicycle", 50),
    /**
     * Car gift.
     */
    CAR("Car", 10),
    /**
     * Doll gift.
     */
    DOLL("Barbie doll", 6),
    /**
     * Puzzle gift.
     */
    PUZZLE("Puzzle", 15);

    private final String type;
    private final int craftTime;

    private static Gift[] gifts = Gift.values();

    private static Random giftRand = new Random();

    /**
     * 
     * @param type      is the type of the gift
     * @param craftTime is how much time is needed
     * for the gift to be created
     */
    private Gift(String type, int craftTime) {
        this.type = type;
        this.craftTime = craftTime;
    }

    /**
     * 
     * @return the type of the gift
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @return the gift's craft time
     */
    public int getCraftTime() {
        return craftTime;
    }

    /**
     * 
     * @return random gift the child wants
     */
    public static Gift getGift() {
        return gifts[giftRand.nextInt(gifts.length)];
    }

}
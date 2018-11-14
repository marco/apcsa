package config;

import grocery.Grocery;
import simulator.grocery.GroceryInterface;

/**
 * This file contains your implementation of some {@link GroceryInterface} items that must be
 * available in your {@link GroceryStore}.
 *
 * @author jcollard, jddevaug, skunkmb
 */
public final class Groceries {
    /** The name for the milk object. */
    private static final String MILK_NAME = "Milk";
    /** The price for the milk object. */
    private static final double MILK_PRICE = 3.29;
    /** The cost for the milk object. */
    private static final double MILK_COST = 1.25;
    /** The handling rating for the milk object. */
    private static final double MILK_HANDLING_RATING = 0.1;
    /** The name for the eggs object. */
    private static final String EGGS_NAME = "Eggs";
    /** The price for the eggs object. */
    private static final double EGGS_PRICE = 2.29;
    /** The cost for the eggs object. */
    private static final double EGGS_COST = 0.25;
    /** The handling rating for the eggs object. */
    private static final double EGGS_HANDLING_RATING = 0.8;
    /** The name for the cold pocket object. */
    private static final String COLD_POCKET_NAME = "Cold Pocket";
    /** The price for the cold pocket object. */
    private static final double COLD_POCKET_PRICE = 0.49;
    /** The cost for the cold pocket object. */
    private static final double COLD_POCKET_COST = 0.02;
    /** The handling rating for the cold pocket object. */
    private static final double COLD_POCKET_HANDLING_RATING = 0.13;
    /** The name for the chips object. */
    private static final String CHIPS_NAME = "Chips";
    /** The price for the chips object. */
    private static final double CHIPS_PRICE = 3.19;
    /** The cost for the chips object. */
    private static final double CHIPS_COST = 0.50;
    /** The handling rating for the chips object. */
    private static final double CHIPS_HANDLING_RATING = 0.4;
    /** The name for the beef object. */
    private static final String BEEF_NAME = "Beef";
    /** The price for the beef object. */
    private static final double BEEF_PRICE = 3.39;
    /** The cost for the beef object. */
    private static final double BEEF_COST = 1.14;
    /** The handling rating for the beef object. */
    private static final double BEEF_HANDLING_RATING = 0.75;
    /** The name for the apple object. */
    private static final String APPLE_NAME = "Apple";
    /** The price for the apple object. */
    private static final double APPLE_PRICE = 0.69;
    /** The cost for the apple object. */
    private static final double APPLE_COST = 0.17;
    /** The handling rating for the apple object. */
    private static final double APPLE_HANDLING_RATING = 0.25;

    /**
     * Private constructor to prevent class instantiation.
     */
    private Groceries() {

    }

    /**
     * Returns your implementation of Milk.
     * <pre>
     * getName() returns "Milk"
     * getPrice() returns 3.29
     * getCost() returns 1.25
     * getHandlingRating() returns 0.1
     * </pre>
     * @return your implementation of Milk
     */
    public static GroceryInterface getMilk() {
        return new Grocery(MILK_NAME, MILK_PRICE, MILK_COST, MILK_HANDLING_RATING);
    }

    /**
     * Returns your implementation of Eggs.
     * <pre>
     * getName() returns "Eggs"
     * getPrice() returns 2.29
     * getCost() returns .25
     * getHandlingRating() returns 0.8
     * </pre>
     * @return your implementation of Eggs
     */
    public static GroceryInterface getEggs() {
        return new Grocery(EGGS_NAME, EGGS_PRICE, EGGS_COST, EGGS_HANDLING_RATING);
    }

    /**
     * Returns your implementation of a Cold Pocket.
     * <pre>
     * getName() returns "Cold Pocket"
     * getPrice() returns 0.49
     * getCost() returns 0.02
     * getHandlingRating() returns 0.13
     * </pre>
     * @return your implementation of Cold Pocket
     */
    public static GroceryInterface getColdPocket() {
        return new Grocery(
            COLD_POCKET_NAME,
            COLD_POCKET_PRICE,
            COLD_POCKET_COST,
            COLD_POCKET_HANDLING_RATING
        );
    }

    /**
     * Returns your implementation of Chips.
     * <pre>
     * getName() returns "Chips"
     * getPrice() returns 3.19
     * getCost() returns 0.50
     * getHandlingRating() returns 0.4
     * </pre>
     * @return your implementation of Chips
     */
    public static GroceryInterface getChips() {
        return new Grocery(CHIPS_NAME, CHIPS_PRICE, CHIPS_COST, CHIPS_HANDLING_RATING);
    }

    /**
     * Returns your implementation of Beef.
     * <pre>
     * getName() returns "Beef"
     * getPrice() returns 3.39
     * getCost() returns 1.14
     * getHandlingRating() returns 0.75
     * </pre>
     * @return your implementation of Beef
     */
    public static GroceryInterface getBeef() {
        return new Grocery(BEEF_NAME, BEEF_PRICE, BEEF_COST, BEEF_HANDLING_RATING);
    }

    /**
     * Returns your implementation of Apple.
     * <pre>
     * getName() returns "Apple"
     * getPrice() returns 0.69
     * getCost() returns 0.17
     * getHandlingRating() returns 0.25
     * </pre>
     * @return your implementation of Apple
     */
    public static GroceryInterface getApple() {
        return new Grocery(APPLE_NAME, APPLE_PRICE, APPLE_COST, APPLE_HANDLING_RATING);
    }

}

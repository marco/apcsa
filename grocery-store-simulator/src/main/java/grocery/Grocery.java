package grocery;

import simulator.grocery.GroceryInterface;

/**
 * A grocery item for sale in a grocery store, including its name, price, cost, and handling rating.
 *
 * @author skunkmb
 */
public class Grocery implements GroceryInterface {
    /**
     * The name of this `Grocery`.
     */
    private String name;

    /**
     * The price of this `Grocery`.
     */
    private double price;

    /**
     * The cost of this `Grocery`.
     */
    private double cost;

    /**
     * The handling rating of this `Grocery`.
     */
    private double handlingRating;

    /**
     * Constructs a new `Grocery`.
     *
     * @param name The name of this `Grocery`.
     * @param price The price of this `Grocery`.
     * @param cost The cost of this `Grocery`.
     * @param handlingRating The handling rating of this `Grocery`.
     */
    public Grocery(String name, double price, double cost, double handlingRating) {
        this.name = name;
        this.price = price;
        this.cost = cost;
        this.handlingRating = handlingRating;
    }

    /**
     * Gets the name of this `Grocery`.
     *
     * @return The name of this `Grocery`.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the price of this `Grocery`.
     *
     * @return The price of this `Grocery`.
     */
    @Override
    public double getPrice() {
        return price;
    }

    /**
     * Gets the cost of this `Grocery`.
     *
     * @return The cost of this `Grocery`.
     */
    @Override
    public double getCost() {
        return cost;
    }

    /**
     * Gets the handling rating of this `Grocery`.
     *
     * @return The handling rating of this `Grocery`.
     */
    @Override
    public double getHandlingRating() {
        return handlingRating;
    }

}

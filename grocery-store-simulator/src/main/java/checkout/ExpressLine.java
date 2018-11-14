package checkout;

import simulator.checkout.CheckoutLineInterface;
import simulator.shopper.Shopper;

/**
 * An "express" grocery line for customers with 15 items or less.
 *
 * @author skunkmb
 */
public class ExpressLine extends NormalLine implements CheckoutLineInterface {
    /**
     * The maximum number of allowed items for shoppers in this line.
     */
    private static final int MAXIMUM_ALLOWED_ITEMS = 15;

    /**
     * Whether or not a shopper can enter the line. Only shoppers with 15 items or less are allowed.
     *
     * @param shopper The shopper attempting to enter the line.
     */
    @Override
    public boolean canEnterLine(Shopper shopper) {
        return shopper.getShoppingList().size() <= MAXIMUM_ALLOWED_ITEMS;
    }
}

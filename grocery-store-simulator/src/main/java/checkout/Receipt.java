package checkout;

import java.util.List;

import simulator.checkout.AbstractReceipt;
import simulator.grocery.GroceryInterface;

/**
 * A receipt that can calculate the price of a list of groceries.
 *
 * @author skunkmb
 */
public class Receipt extends AbstractReceipt {
    /**
     * Constructs a new `Receipt`.
     *
     * @param groceries The groceries to include in this receipt.
     * @param discount The discount available for this receipt.
     */
    public Receipt(List<GroceryInterface> groceries, double discount) {
        super(groceries, discount);
    }

    @Override
    public double getSubtotal() {
        double currentSubtotal = 0;

        for (GroceryInterface grocery : getGroceries()) {
            currentSubtotal += grocery.getPrice();
        }

        return currentSubtotal;
    }

    @Override
    public double getSaleValue() {
        return getSubtotal() * (1 - getDiscount());
    }
}

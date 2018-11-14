package checkout;

import java.util.List;

import simulator.checkout.AbstractRegister;
import simulator.checkout.Transaction;
import simulator.grocery.GroceryInterface;
import simulator.shopper.Shopper;

/**
 * A simple register that can handle `Shopper`s and create `Transaction`s. Discounts are never used.
 *
 * @author skunkmb
 */
public class SimpleRegister extends AbstractRegister {
    @Override
    public Transaction createTransaction(Shopper shopper) {
        List<GroceryInterface> shoppingList = shopper.getShoppingList();
        int time;

        if (shoppingList.size() == 0) {
            time = 1;
        } else {
            time = shoppingList.size() * 4;
        }

        return new Transaction(
            new Receipt(shopper.getShoppingList(), 0),
            shopper,
            time
        );
    }
}

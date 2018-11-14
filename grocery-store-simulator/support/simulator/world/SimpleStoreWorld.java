package simulator.world;

import java.util.LinkedList;
import java.util.List;

import config.Groceries;
import simulator.bigbrother.BigBrother;
import simulator.grocery.GroceryInterface;
import simulator.shopper.Shopper;
/**
 * Represents a SimpleStore that only processes two shoppers.
 * @author jcollard, jddevaug
 *
 */
public class SimpleStoreWorld extends World {

  @Override
  public final void tick() {
        final int FIRST_PURCHASE_TIME = 1000;
        final int SECOND_PURCHASE_TIME = 5000;
        final int PURCHASE_AMOUNT = 30;

    // At time step 1000, create a shopper buying milk, beef, and cold pockets
    if (BigBrother.getBigBrother().getTime() == FIRST_PURCHASE_TIME) {
      List<GroceryInterface> groceries = new LinkedList<GroceryInterface>();
      groceries.add(Groceries.getMilk());
      groceries.add(Groceries.getBeef());
      groceries.add(Groceries.getColdPocket());
      new Shopper(groceries);
    }

    // At time step 5000, create a shopper buying 30 beefs
    if (BigBrother.getBigBrother().getTime() == SECOND_PURCHASE_TIME) {
      List<GroceryInterface> groceries = new LinkedList<GroceryInterface>();
      for (int i = 0; i < PURCHASE_AMOUNT; i++) {
        groceries.add(Groceries.getBeef());
      }
      new Shopper(groceries);
    }
  }

}

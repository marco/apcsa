package config;

import java.util.List;

import checkout.ExpressLine;
import checkout.NormalLine;
import checkout.Receipt;
import checkout.SimpleRegister;
import simulator.checkout.AbstractReceipt;
import simulator.checkout.AbstractRegister;
import simulator.checkout.CheckoutLineInterface;
import simulator.grocery.GroceryInterface;
import simulator.store.AbstractGroceryStore;
import store.ProfitableStore;
import store.SimpleStore;
import structures.LinkedQueue;
import structures.QueueInterface;

/**
 * The {@link Configuration} class is a set of static definitions we will use to grade your
 * assignment.
 *
 * @author jcollard, jddevaug
 */
public final class Configuration {
    /**
     * Private constructor to prevent class instantiation.
     */
    private Configuration() {
    }

    /**
     * Returns the {@link QueueInterface} that you would like to be graded.
     *
     * @param <T>
     *            the type of data in the {@link QueueInterface}
     * @return the {@link QueueInterface} that you would like to be graded
     */
    public static <T> QueueInterface<T> getQueueImplementation() {
        return new LinkedQueue();
    }

    /**
     * Returns a {@link CheckoutLineInterface} that always returns true when
     * {@link CheckoutLineInterface#canEnterLine(simulator.shopper.Shopper)} is called.
     *
     * @return a {@link CheckoutLineInterface} that always returns true when
     *         {@link CheckoutLineInterface#canEnterLine(simulator.shopper.Shopper)} is called
     */
    public static CheckoutLineInterface getNormalLine() {
        return new NormalLine();
    }

    /**
     * Returns a {@link CheckoutLineInterface} that returns {@code true} when a Shopper has 15 items
     * or less in their shopping list and {@code false} otherwise when
     * {@link CheckoutLineInterface#canEnterLine(simulator.shopper.Shopper)} is called.
     *
     * @return a {@link CheckoutLineInterface} that returns {@code true} when a Shopper has 15 items
     *         or less in their shopping list and {@code false} otherwise when
     *         {@link CheckoutLineInterface#canEnterLine(simulator.shopper.Shopper)} is called
     */
    public static CheckoutLineInterface getExpressLine() {
        return new ExpressLine();
    }

    /**
     * Returns the {@link AbstractReceipt} implementation you would like to be graded.
     *
     * @param groceries
     *            the list of groceries on the receipt
     * @param discount
     *            the discount given to the shopper
     * @return the {@link AbstractReceipt} implementation you would like to be graded.
     */
    public static AbstractReceipt getReceiptImplementation(final List<GroceryInterface> groceries,
            final double discount) {
        return new Receipt(groceries, discount);
    }

    /**
   * Returns the {@link AbstractRegister} implementation you would like to be
   * graded.
   *
   * @return the {@link AbstractRegister} implementation you would like to be
   *         graded.
   */
  public static AbstractRegister getSimpleRegister() {
        return new SimpleRegister();
  }

  /**
   * Returns the {@link AbstractGroceryStore} implementation you would like to
   * be graded for this Simple Store part of the project.
   *
   * @return the {@link AbstractGroceryStore} implementation you would like to
   *         be graded for this Simple Store part of the project.
   */
  public static AbstractGroceryStore getSimpleStore() {
        return new SimpleStore();
  }

  /**
   * Returns the {@link AbstractGroceryStore} implementation you would like to
   * be graded for the Profitable Store part of the project.
   *
   * @return the {@link AbstractGroceryStore} implementation you would like to
   *         be graded for the Profitable Store part of the project.
   */
  public static AbstractGroceryStore getProfitableStore() {
        return new ProfitableStore();
  }

}

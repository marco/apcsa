package store;

import java.util.ArrayList;
import java.util.List;

import checkout.ExpressLine;
import checkout.NormalLine;
import checkout.SimpleRegister;
import simulator.checkout.AbstractRegister;
import simulator.checkout.CheckoutLineInterface;
import simulator.checkout.Transaction;
import simulator.grocery.GroceryInterface;
import simulator.store.AbstractGroceryStore;

/**
 * A simple store that can manage registers, checkout lines, and transactions for various customers
 * buying groceries.
 *
 * @author skunkmb
 */
public class SimpleStore extends AbstractGroceryStore {
    /**
     * The current registers for this store.
     */
    protected List<AbstractRegister> registers = new ArrayList<AbstractRegister>();

    /**
     * The current checkout lines for this store.
     */
    protected List<CheckoutLineInterface> checkoutLines = new ArrayList<CheckoutLineInterface>();

    /**
     * The current transactions that this store has processed.
     */
    protected List<Transaction> transactions = new ArrayList<Transaction>();

    /**
     * Constructs a `SimpleStore`.
     */
    public SimpleStore() {
        super();

        registers.add(new SimpleRegister());
        registers.add(new SimpleRegister());
        checkoutLines.add(new NormalLine());
        checkoutLines.add(new ExpressLine());

        for (AbstractRegister register : registers) {
            register.turnOn();
        }
    }

    @Override
    public void tick() {
        for (int i = 0; i < registers.size(); i++) {
            AbstractRegister register = registers.get(i);
            if (!register.isBusy()) {
                CheckoutLineInterface checkoutLine = checkoutLines.get(i);
                if (!checkoutLine.isEmpty()) {
                    transactions.add(register.processShopper(checkoutLine.dequeue()));
                }
            }
        }
    }

    @Override
    public List<CheckoutLineInterface> getLines() {
        return checkoutLines;
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public double getAverageWaitingTime() {
        int currentWaitingTimeSum = 0;

        for (Transaction transaction : transactions) {
            currentWaitingTimeSum += transaction.getShopper().getWaitingTime();
        }

        return currentWaitingTimeSum / transactions.size();
    }

    @Override
    public double getTotalSales() {
        double currentSalesSum = 0;

        for (Transaction transaction : transactions) {
            currentSalesSum += transaction.getReceipt().getSaleValue();
        }

        return currentSalesSum;
    }

    @Override
    public double getTotalCost() {
        double currentCostSum = 0;

        for (Transaction transaction : transactions) {
            List<GroceryInterface> groceries = transaction.getReceipt().getGroceries();

            for (GroceryInterface grocery : groceries) {
                currentCostSum += grocery.getCost();
            }
        }

        for (AbstractRegister register : registers) {
            currentCostSum += register.getRunningCost();
        }

        return currentCostSum;
    }

    @Override
    public double getTotalProfit() {
        return getTotalSales() - getTotalCost();
    }

    @Override
    public int getNumberOfShoppers() {
        return getTransactions().size();
    }

    @Override
    public int getNumberOfIrateShoppers() {
        int currentNumberOfIrateShoppers = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getShopper().isIrate()) {
                currentNumberOfIrateShoppers++;
            }
        }

        return currentNumberOfIrateShoppers;
    }

}

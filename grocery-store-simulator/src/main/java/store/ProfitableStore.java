package store;

import java.util.ArrayList;
import java.util.List;

import checkout.ExpressLine;
import checkout.NormalLine;
import checkout.SimpleRegister;
import simulator.checkout.AbstractRegister;
import simulator.checkout.CheckoutLineInterface;

/**
 * A profitable store that can manage registers, checkout lines, and transactions for various
 * customers buying groceries, while making a profit.
 *
 * @author skunkmb
 */
public class ProfitableStore extends SimpleStore {
    /**
     * The number of lines to open (as well as the maximum number of registers to have opened at one
     * time).
     */
    private static final int LINE_COUNT = 1000;

    /**
     * The percent of lines to make express lines.
     */
    private static final double EXPRESS_LINE_AMOUNT = 0.5;

    /**
     * The maximum waiting time to allow before opening a register.
     */
    private static final int MAXIMUM_WAITING_TIME = 299;

    /**
     * The amount of time to keep a register open before turning it off.
     */
    private static final int REGISTER_TURN_OFF_DELAY = 0;

    /**
     * The current amount of time that each register has been on since having its last customer.
     */
    private List<Integer> registerDelayTimes = new ArrayList<Integer>();

    /**
     * Constructs a `ProfitableStore`.
     */
    public ProfitableStore() {
        super();

        // `SimpleStore` creates its own registers in its constructor, but these are not
        // wanted, so turn them off and remove them and their lines.
        for (int i = registers.size() - 1; 0 <= i; i--) {
            registers.get(i).turnOff();
            registers.remove(i);
            checkoutLines.remove(i);
        }

        for (int i = 0; i < LINE_COUNT; i++) {
            // For now, we don't want to turn on the registers, just create them. They will
            // be turned on and off as necessary in the future.
            registers.add(new SimpleRegister());
            registerDelayTimes.add(0);

            // If `i` is less than the number of required express lines, make a new express line.
            if (i < LINE_COUNT * EXPRESS_LINE_AMOUNT) {
                checkoutLines.add(new ExpressLine());
            } else {
                checkoutLines.add(new NormalLine());
            }
        }
    }

    @Override
    public void tick() {
        for (int i = 0; i < registers.size(); i++) {
            AbstractRegister register = registers.get(i);

            // If it's running but not busy, increment its delay time.
            if (register.getIsRunning() && register.isBusy()) {
                registerDelayTimes.set(i, registerDelayTimes.get(i) + 1);
            }

            if (!register.isBusy()) {
                CheckoutLineInterface checkoutLine = checkoutLines.get(i);
                if (!checkoutLine.isEmpty()) {
                    if (!register.getIsRunning()) {
                        if (MAXIMUM_WAITING_TIME <= checkoutLine.peek().getWaitingTime()) {
                            register.turnOn();
                            transactions.add(register.processShopper(checkoutLine.dequeue()));
                        }
                    } else {
                        transactions.add(register.processShopper(checkoutLine.dequeue()));
                    }
                } else {
                    if (REGISTER_TURN_OFF_DELAY < registerDelayTimes.get(i)) {
                        register.turnOff();
                        registerDelayTimes.set(i, 0);
                    }
                }
            }
        }
    }
}

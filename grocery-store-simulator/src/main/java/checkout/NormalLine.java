package checkout;

import simulator.checkout.CheckoutLineInterface;
import simulator.shopper.Shopper;
import structures.LinkedQueue;
import structures.QueueInterface;

/**
 * A normal grocery line for all customers.
 *
 * @author skunkmb
 */
public class NormalLine extends LinkedQueue<Shopper> implements CheckoutLineInterface {
    @Override
    public boolean canEnterLine(Shopper shopper) {
        if (shopper == null) {
            throw new NullPointerException("THe shopper cannot be null.");
        }

        return true;
    }

    @Override
    public QueueInterface<Shopper> enqueue(Shopper shopper) {
        if (shopper == null) {
            throw new NullPointerException("The shopper cannot be null.");
        }

        if (!canEnterLine(shopper)) {
            throw new IllegalArgumentException("That shopper is not allowed in this line.");
        }

        return super.enqueue(shopper);
    };
}

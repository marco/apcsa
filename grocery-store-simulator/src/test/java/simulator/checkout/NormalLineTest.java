package simulator.checkout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import config.Configuration;
import config.Groceries;
import simulator.grocery.GroceryInterface;
import simulator.shopper.Shopper;

public class NormalLineTest {

	private Shopper bob;
	private Shopper pete;
	private Shopper mete;

	@Before
	public void setUp() throws Exception {
		LinkedList<GroceryInterface> shortList = new LinkedList<GroceryInterface>();
		shortList.add(Groceries.getApple());
		shortList.add(Groceries.getBeef());

		bob = new Shopper(shortList);

		LinkedList<GroceryInterface> whereIsTheBeef = new LinkedList<GroceryInterface>();
		for(int i = 0; i < 15; i++)
			whereIsTheBeef.add(Groceries.getBeef());

		pete = new Shopper(whereIsTheBeef);

		LinkedList<GroceryInterface> tooMuchBeef = new LinkedList<GroceryInterface>();
		for(int i = 0; i < 100; i++)
			tooMuchBeef.add(Groceries.getBeef());

		mete = new Shopper(tooMuchBeef);
	}

	@Test (timeout = 100)
	public void test() {
		CheckoutLineInterface col = Configuration.getNormalLine();
		assertTrue(col.canEnterLine(bob));
		col.enqueue(bob);
		assertTrue(col.canEnterLine(pete));
		col.enqueue(pete);
		assertTrue(col.canEnterLine(mete));
		col.enqueue(mete);

		assertEquals(bob, col.dequeue());
		assertEquals(pete, col.dequeue());
		assertEquals(mete, col.dequeue());
	}

	@Test (timeout = 100, expected = NullPointerException.class)
	public void testNPE1(){
		Configuration.getNormalLine().enqueue(null);
	}

	@Test (timeout = 100, expected = NullPointerException.class)
	public void testNPE2(){
		Configuration.getNormalLine().canEnterLine(null);
	}

    @Test
    public void testSize() {
        CheckoutLineInterface normalLine = Configuration.getNormalLine();
        normalLine.enqueue(bob);
        normalLine.enqueue(pete);
        assertEquals(normalLine.size(), 2);
        normalLine.dequeue();
        assertEquals(normalLine.size(), 1);
        normalLine.enqueue(pete);
        normalLine.dequeue();
        normalLine.dequeue();
        assertEquals(normalLine.size(), 0);
    }

    @Test
    public void testIsEmpty() {
        CheckoutLineInterface normalLine = Configuration.getNormalLine();
        normalLine.enqueue(bob);
        normalLine.enqueue(pete);
        assertEquals(normalLine.isEmpty(), false);
        normalLine.dequeue();
        assertEquals(normalLine.isEmpty(), false);
        normalLine.dequeue();
        assertEquals(normalLine.isEmpty(), true);
    }

    @Test
    public void testPeek() {
        CheckoutLineInterface normalLine = Configuration.getNormalLine();
        normalLine.enqueue(bob);
        normalLine.enqueue(pete);
        assertEquals(normalLine.peek(), bob);
        normalLine.dequeue();
        assertEquals(normalLine.peek(), pete);
        normalLine.enqueue(mete);
        assertEquals(normalLine.peek(), pete);
    }
}

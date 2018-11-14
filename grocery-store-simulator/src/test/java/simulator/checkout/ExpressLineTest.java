package simulator.checkout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import config.Configuration;
import config.Groceries;
import simulator.grocery.GroceryInterface;
import simulator.shopper.Shopper;

public class ExpressLineTest {

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
		CheckoutLineInterface col = Configuration.getExpressLine();
		assertTrue(col.canEnterLine(bob));
		col.enqueue(bob);
		assertTrue(col.canEnterLine(pete));
		col.enqueue(pete);
		assertFalse(col.canEnterLine(mete));

		assertEquals(bob, col.dequeue());
		assertEquals(pete, col.dequeue());
	}

	@Test (timeout = 100, expected = IllegalArgumentException.class)
	public void testLongList(){
		CheckoutLineInterface col = Configuration.getExpressLine();
		col.enqueue(mete);
	}

	@Test (timeout = 100, expected = NullPointerException.class)
	public void testNPE1(){
		Configuration.getExpressLine().enqueue(null);
	}

	@Test (timeout = 100, expected = NullPointerException.class)
	public void testNPE2(){
		Configuration.getExpressLine().canEnterLine(null);
	}

    @Test
	public void testSize() {
	    CheckoutLineInterface expressLine = Configuration.getExpressLine();
        expressLine.enqueue(bob);
        expressLine.enqueue(pete);
        assertEquals(expressLine.size(), 2);
        expressLine.dequeue();
        assertEquals(expressLine.size(), 1);
        expressLine.enqueue(pete);
        expressLine.dequeue();
        expressLine.dequeue();
        assertEquals(expressLine.size(), 0);
    }

    @Test
    public void testIsEmpty() {
        CheckoutLineInterface expressLine = Configuration.getExpressLine();
        expressLine.enqueue(bob);
        expressLine.enqueue(pete);
        assertEquals(expressLine.isEmpty(), false);
        expressLine.dequeue();
        assertEquals(expressLine.isEmpty(), false);
        expressLine.dequeue();
        assertEquals(expressLine.isEmpty(), true);
    }

    @Test
    public void testPeek() {
        CheckoutLineInterface expressLine = Configuration.getExpressLine();
        expressLine.enqueue(bob);
        expressLine.enqueue(pete);
        assertEquals(expressLine.peek(), bob);
        expressLine.dequeue();
        assertEquals(expressLine.peek(), pete);
        expressLine.enqueue(bob);
        assertEquals(expressLine.peek(), pete);
    }
}

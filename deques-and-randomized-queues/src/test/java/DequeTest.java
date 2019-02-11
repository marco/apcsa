

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class DequeTest {
    private static final int TIMING_LOOP_AMOUNT = 10000;
	private Deque<String> list;

	@Before
	public void setup(){
		list = new Deque<String>();
		Deque<String> l2 = new Deque<String>();
		if(list == null)
			fail("You didn't set your list in the Configuration file.");
		if(list == l2)
			fail("You should return a new instance of list with each call to Configuration.getDeque()");
	}

	@Test (timeout = 500)
	public void testaddFirstIsEmptySizeAndRemoveFirst() {
		assertTrue("Newly constructed list should be empty.", list.isEmpty());
		assertEquals("Newly constructed list should be size 0.", 0, list.size());
		list.addFirst("hello");
		assertFalse("List should now have elements.", list.isEmpty());
		assertEquals("List should now have 1 element.", 1, list.size());
		list.addFirst("world");
		assertEquals(2, list.size());
		list.addFirst("foo");
		assertEquals(3, list.size());
		assertEquals("First element should .equals \"foo\".", "foo", list.removeFirst());
		assertEquals("First element should .equals \"world\".", "world", list.removeFirst());
	}

	@Test (timeout = 500)
	public void testaddLastIsEmptySizeAndRemoveLast() {
		assertTrue("Newly constructed list should be empty.", list.isEmpty());
		assertEquals("Newly constructed list should be size 0.", 0, list.size());
		list.addLast("hello");
		assertFalse("List should now have elements.", list.isEmpty());
		assertEquals("List should now have 1 element.", 1, list.size());
		list.addLast("world");
		assertEquals(2, list.size());
		list.addLast("foo");
		assertEquals(3, list.size());
		assertEquals("Last element should .equals \"foo\".", "foo", list.removeLast());
		assertEquals("Last element should .equals \"world\".", "world", list.removeLast());

	}


	@Test (timeout = 500)
	public void testaddFirstRemoveFirstSizeAndIsEmpty() {
		assertTrue("Newly constructed list should be empty.", list.isEmpty());
		list.addFirst("hello");
		list.addFirst("there");
		list.addFirst("world");
		assertEquals("List should now have 3 elements", 3, list.size());
		assertEquals("world", list.removeFirst());
		assertEquals("List should now have 2 elements", 2, list.size());
		assertEquals("there", list.removeFirst());
		assertEquals("List should now have 1 elements", 1, list.size());
		assertEquals("hello", list.removeFirst());
		assertEquals("List should now have 0 elements", 0, list.size());
		assertTrue("All elements removed, list should be empty.", list.isEmpty());
	}

	@Test (timeout = 500)
	public void testaddLastRemoveLastSizeAndIsEmpty() {
		assertTrue("Newly constructed list should be empty.", list.isEmpty());
		list.addLast("hello");
		list.addLast("there");
		list.addLast("world");
		assertEquals("List should now have 3 elements", 3, list.size());
		assertEquals("world", list.removeLast());
		assertEquals("List should now have 2 elements", 2, list.size());
		assertEquals("there", list.removeLast());
		assertEquals("List should now have 1 elements", 1, list.size());
		assertEquals("hello", list.removeLast());
		assertEquals("List should now have 0 elements", 0, list.size());
		assertTrue("All elements removed, list should be empty.", list.isEmpty());
	}

	@Test (timeout = 500, expected = NoSuchElementException.class)
	public void testExceptionOnEmptyRemoveFirst() {
		list.removeFirst();
	}

	@Test (timeout = 500, expected = NoSuchElementException.class)
	public void testExceptionOnEmptyRemoveLast() {
		list.removeLast();
	}

	@Test
	public void testIterator() {
	    list.addLast("hello");
	    list.addLast("there");
	    list.addLast("world");
	    Iterator<String> iterator = list.iterator();
	    assertEquals(iterator.next(), "hello");
	    assertEquals(iterator.next(), "there");
	    assertEquals(iterator.next(), "world");
	}

	@Test (expected = IllegalArgumentException.class)
	public void testAddNullFirst() {
	    list.addFirst(null);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testAddNullLast() {
        list.addLast(null);
    }

	@Test (expected = NoSuchElementException.class)
    public void testRemoveFirstEmpty() {
	    list = new Deque<String>();
        list.removeLast();
    }

	@Test (expected = NoSuchElementException.class)
    public void testRemoveLastEmpty() {
	    list = new Deque<String>();
	    list.removeFirst();
    }

	@Test (expected = NoSuchElementException.class)
    public void testNextEmpty() {
        list = new Deque<String>();
        list.iterator().next();
    }

	@Test (expected = UnsupportedOperationException.class)
    public void testIteratorRemove() {
        list = new Deque<String>();
        list.iterator().remove();
    }

	@Test (timeout = 500)
    public void testSpeedAddRemove() {
        list = new Deque<String>();

        for (int i = 0; i < TIMING_LOOP_AMOUNT; i++) {
            list.addFirst("hello");
            list.removeFirst();
        }
    }

    @Test (timeout = 500)
    public void testSpeedAdd() {
        list = new Deque<String>();

        for (int i = 0; i < TIMING_LOOP_AMOUNT; i++) {
            list.addFirst("hello");
        }
    }

    @Test (timeout = 500)
    public void testSpeedAddLastRemove() {
        list = new Deque<String>();

        for (int i = 0; i < TIMING_LOOP_AMOUNT; i++) {
            list.addLast("hello");
            list.removeLast();
        }
    }

    @Test (timeout = 500)
    public void testSpeedAddLast() {
        list = new Deque<String>();

        for (int i = 0; i < TIMING_LOOP_AMOUNT; i++) {
            list.addLast("hello");
        }
    }

    @Test (timeout = 5000)
    public void testSpeedIterator() {
        list = new Deque<String>();

        for (int i = 0; i < TIMING_LOOP_AMOUNT; i++) {
            list.addFirst("hello");
            Iterator<String> iterator = list.iterator();
        }
    }
}

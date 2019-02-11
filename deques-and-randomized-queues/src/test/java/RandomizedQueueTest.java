

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class RandomizedQueueTest {
    private RandomizedQueue<String> list;
    private static final int TIMING_LOOP_AMOUNT = 10000;

    @Before
    public void setup(){
        list = new RandomizedQueue<String>();
        RandomizedQueue<String> l2 = new RandomizedQueue<String>();
        if(list == null)
            fail("You didn't set your list in the Configuration file.");
        if(list == l2)
            fail("You should return a new instance of list with each call to Configuration.getRandomizedQueue()");
    }

    @Test (timeout = 500)
    public void testaddFirstIsEmptySize() {
        assertTrue("Newly constructed list should be empty.", list.isEmpty());
        assertEquals("Newly constructed list should be size 0.", 0, list.size());
        list.enqueue("hello");
        assertFalse("List should now have elements.", list.isEmpty());
        assertEquals("List should now have 1 element.", 1, list.size());
        list.enqueue("world");
        assertEquals(2, list.size());
        list.enqueue("foo");
        assertEquals(3, list.size());
    }

    @Test (timeout = 500)
    public void testAddRemoveRandom() {
        assertTrue("Newly constructed list should be empty.", list.isEmpty());
        list.enqueue("hello");
        list.enqueue("there");
        list.enqueue("world");
        String value = list.dequeue();
        assertTrue(value.equals("hello") || value.equals("there") || value.equals("world"));
        list.dequeue();
        list.dequeue();
        list.enqueue("test");
        assertTrue(list.dequeue().equals("test"));
    }

    @Test (timeout = 500)
    public void testAddRemoveSequence() {
        assertTrue("Newly constructed list should be empty.", list.isEmpty());
        list.enqueue("hello");
        list.enqueue("there");
        list.enqueue("world");
        String value = list.dequeue();
        assertTrue(value.equals("hello") || value.equals("there") || value.equals("world"));
        list.dequeue();
        list.dequeue();
        list.enqueue("test");
        assertTrue(list.dequeue().equals("test"));
    }

    @Test (timeout = 500, expected = NoSuchElementException.class)
    public void testExceptionOnEmptyRemoveFirst() {
        list.dequeue();
    }

    @Test
    public void testIterator() {
        list.enqueue("hello");
        list.enqueue("there");
        list.enqueue("world");
        Iterator<String> iterator = list.iterator();
        String next = iterator.next();
        assertTrue(next.equals("hello") || next.equals("there") || next.equals("world"));
        next = iterator.next();
        assertTrue(next.equals("hello") || next.equals("there") || next.equals("world"));
        next = iterator.next();
        assertTrue(next.equals("hello") || next.equals("there") || next.equals("world"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddNullFirst() {
        list.enqueue(null);
    }

    @Test (expected = NoSuchElementException.class)
    public void testRemoveFirstEmpty() {
        list = new RandomizedQueue<String>();
        list.dequeue();
    }

    @Test (expected = NoSuchElementException.class)
    public void testNextEmpty() {
        list = new RandomizedQueue<String>();
        list.iterator().next();
    }

    @Test (expected = UnsupportedOperationException.class)
    public void testIteratorRemove() {
        list = new RandomizedQueue<String>();
        list.iterator().remove();
    }

    @Test (timeout = 500)
    public void testSpeedAddRemove() {
        list = new RandomizedQueue<String>();

        for (int i = 0; i < TIMING_LOOP_AMOUNT; i++) {
            list.enqueue("hello");
            list.dequeue();
        }
    }

    @Test (timeout = 500)
    public void testSpeedAdd() {
        list = new RandomizedQueue<String>();

        for (int i = 0; i < TIMING_LOOP_AMOUNT; i++) {
            list.enqueue("hello");
        }
    }

    @Test (timeout = 5000)
    public void testSpeedIterator() {
        list = new RandomizedQueue<String>();

        for (int i = 0; i < TIMING_LOOP_AMOUNT; i++) {
            list.enqueue("hello");
            Iterator<String> iterator = list.iterator();
        }
    }
}

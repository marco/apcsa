package structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LinkedQueueTest {
    @Test(expected = IllegalStateException.class)
    public void testDequeueException() {
        LinkedQueue<Integer> queue = new LinkedQueue<Integer>();
        queue.dequeue();
    }

    @Test
    public void testEnqueueDequeue() {
        LinkedQueue<Integer> queue = new LinkedQueue<Integer>();
        queue.enqueue(0);
        assertEquals(queue.dequeue(), Integer.valueOf(0));
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(queue.dequeue(), Integer.valueOf(1));
        assertEquals(queue.dequeue(), Integer.valueOf(2));
    }

    @Test
    public void testIsEmpty() {
        LinkedQueue<Integer> queue = new LinkedQueue<Integer>();
        assertTrue(queue.isEmpty());
        queue.enqueue(0);
        queue.enqueue(1);
        assertTrue(!queue.isEmpty());
        queue.dequeue();
        assertTrue(!queue.isEmpty());
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testSize() {
        LinkedQueue<Integer> queue = new LinkedQueue<Integer>();
        assertEquals(queue.size(), 0);
        queue.enqueue(0);
        queue.enqueue(1);
        assertEquals(queue.size(), 2);
        queue.dequeue();
        assertEquals(queue.size(), 1);
        queue.dequeue();
        assertEquals(queue.size(), 0);
    }

    @Test(expected = NullPointerException.class)
    public void testPeekException() {
        LinkedQueue<Integer> queue = new LinkedQueue<Integer>();
        queue.peek();
    }

    @Test
    public void testPeek() {
        LinkedQueue<Integer> queue = new LinkedQueue<Integer>();
        queue.enqueue(0);
        queue.enqueue(1);
        assertEquals(queue.peek(), Integer.valueOf(0));
        queue.dequeue();
        assertEquals(queue.peek(), Integer.valueOf(1));
    }

    @Test
    public void testToString() {
        LinkedQueue<Integer> queue = new LinkedQueue<Integer>();
        assertEquals(queue.toString(), "[]");
        queue.enqueue(0);
        assertEquals(queue.toString(), "[0]");
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(queue.toString(), "[0, 1, 2]");
        queue.dequeue();
        assertEquals(queue.toString(), "[1, 2]");
        queue.dequeue();
        queue.dequeue();
        assertEquals(queue.toString(), "[]");
    }
}

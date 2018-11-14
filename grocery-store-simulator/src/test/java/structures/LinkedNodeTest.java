package structures;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LinkedNodeTest {
    @Test
    public void testValue() {
        LinkedNode<Integer> node = new LinkedNode(5, null);
        assertEquals(node.getValue(), Integer.valueOf(5));

        LinkedNode<Integer> nullValueNode = new LinkedNode(null, null);
        assertEquals(nullValueNode.getValue(), null);
    }

    @Test
    public void testNext() {
        LinkedNode<Integer> nextNode = new LinkedNode(4, null);
        LinkedNode<Integer> node = new LinkedNode(5, nextNode);
        assertEquals(node.getNext(), nextNode);
        assertEquals(node.getNext().getValue(), Integer.valueOf(4));

        LinkedNode<Integer> nullValueNode = new LinkedNode(null, null);
        assertEquals(nullValueNode.getNext(), null);
    }
}

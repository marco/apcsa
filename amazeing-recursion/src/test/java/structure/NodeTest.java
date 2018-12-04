package structure;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import structures.LinkedNode;

public class NodeTest {
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
        assertEquals(node.getNextNode(), nextNode);
        assertEquals(node.getNextNode().getValue(), Integer.valueOf(4));

        LinkedNode<Integer> nullValueNode = new LinkedNode(null, null);
        assertEquals(nullValueNode.getNextNode(), null);
    }
}

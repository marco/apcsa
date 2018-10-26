package stack;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LinkedStackTest {
    @Test(timeout = 5000)
    public void testGetValue() {
        LinkedNode node = new LinkedNode(4, null);
        assertEquals(node.getValue(), 4);

        LinkedNode nodeB = new LinkedNode(8, node);
        assertEquals(nodeB.getValue(), 8);

        LinkedNode nodeC = new LinkedNode("Test", null);
        assertEquals(nodeC.getValue(), "Test");
    }

    @Test(timeout = 5000)
    public void testGetPrevious() {
        LinkedNode node = new LinkedNode(4, null);
        assertEquals(node.getPreviousNode(), null);

        LinkedNode nodeB = new LinkedNode(8, node);
        assertEquals(nodeB.getPreviousNode(), node);

        LinkedNode nodeC = new LinkedNode("Test", null);
        assertEquals(nodeC.getPreviousNode(), null);
    }
}

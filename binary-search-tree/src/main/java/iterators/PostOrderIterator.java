package iterators;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import structures.BinaryTreeNode;

/**
 * An iterator that iterates through a binary tree in "postorder" order
 * (left, right, node).
 *
 * @author skunkmb
 *
 * @param <T> The type of the data in the tree.
 */
public class PostOrderIterator<T> implements Iterator<T> {
    /**
     * A stack of nodes used when iterating through the tree.
     */
    private final Deque<BinaryTreeNode<T>> nodeStack;

    /**
     * Constructs a `PostOrderIterator`.
     *
     * @param rootNode The root node for the tree to iterate through.
     */
    public PostOrderIterator(BinaryTreeNode<T> rootNode) {
        nodeStack = new LinkedList<BinaryTreeNode<T>>();
        makeStack(rootNode);
    }

    /**
     * Adds to `nodeStack` by recursively pushing a node's right subchildren,
     * the node, and its left subchildren. Note, this does not remove
     * the elements of `nodeStack` before adding.
     *
     * @param rootNode The root node to add from.
     */
    private void makeStack(BinaryTreeNode<T> rootNode) {
        BinaryTreeNode<T> currentNode = rootNode;
        while (true) {
            if (currentNode.hasRightChild()) {
                nodeStack.push(currentNode.getRightChild());
            }

            nodeStack.push(currentNode);

            if (!currentNode.hasLeftChild()) {
                return;
            }

            currentNode = currentNode.getLeftChild();
        }
    }

    @Override
    public boolean hasNext() {
        return !nodeStack.isEmpty();
    }

    @Override
    public T next() {
        return nextNode().getData();
    }

    /**
     * Gets the next `BinaryTreeNode` in this binary tree according to
     * "postorder" order.
     *
     * @return The next `BinaryTreeNode` in this binary tree.
     */
    public BinaryTreeNode<T> nextNode() {
        BinaryTreeNode<T> poppedNode = nodeStack.pop();

        // If the top node's right child is at the top of the stack,
        // update the stack with the right child.
        if (
            poppedNode.hasRightChild()
                && !nodeStack.isEmpty()
                && nodeStack.peek().equals(poppedNode.getRightChild())
        ) {
            nodeStack.pop();
            nodeStack.push(poppedNode);

            if (poppedNode.hasRightChild()) {
                makeStack(poppedNode.getRightChild());
            }

            return nextNode();
        }

        return poppedNode;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

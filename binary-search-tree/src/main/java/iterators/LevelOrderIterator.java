package iterators;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import structures.BinaryTreeNode;

/**
 * An iterator that iterates through a binary tree in "level-order" order
 * (top to bottom).
 *
 * @author skunkmb
 *
 * @param <T> The type of the data in the tree.
 */
public class LevelOrderIterator<T> implements Iterator<T> {
    /**
     * A stack of nodes used when iterating through the tree.
     */
    private final Deque<BinaryTreeNode<T>> nodeStack;

    /**
     * Constructs a `LevelOrderIterator`.
     *
     * @param rootNode The root node for the tree to iterate through.
     */
    public LevelOrderIterator(BinaryTreeNode<T> rootNode) {
        nodeStack = new LinkedList<BinaryTreeNode<T>>();
        nodeStack.add(rootNode);
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
     * "level-order" order.
     *
     * @return The next `BinaryTreeNode` in this binary tree.
     */
    public BinaryTreeNode<T> nextNode() {
        BinaryTreeNode<T> poppedNode = nodeStack.pop();

        if (poppedNode.hasLeftChild()) {
            nodeStack.add(poppedNode.getLeftChild());
        }

        if (poppedNode.hasRightChild()) {
            nodeStack.add(poppedNode.getRightChild());
        }

        return poppedNode;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

package iterators;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import structures.BinaryTreeNode;

/**
 * An iterator that iterates through a binary tree in "inorder" order
 * (left, node, right).
 *
 * @author skunkmb
 *
 * @param <T> The type of the data in the tree.
 */
public class InOrderIterator<T> implements Iterator<T> {
    /**
     * A stack of nodes used when iterating through the tree.
     */
    private final Deque<BinaryTreeNode<T>> nodeStack;

    /**
     * Constructs a `InOrderIterator`.
     *
     * @param rootNode The root node for the tree to iterate through.
     */
    public InOrderIterator(BinaryTreeNode<T> rootNode) {
        nodeStack = new LinkedList<BinaryTreeNode<T>>();
        makeStack(rootNode);
    }

    /**
     * Adds to `nodeStack` by traversing the leftmost children of a root
     * node. Note, this does not remove the elements of `nodeStack`
     * before adding.
     *
     * @param rootNode The root node to add from.
     */
    private void makeStack(BinaryTreeNode<T> rootNode) {
        nodeStack.push(rootNode);

        // After adding this node, add all of its left children and
        // subchildren.
        BinaryTreeNode<T> currentNode = rootNode;
        while (currentNode.hasLeftChild()) {
            currentNode = currentNode.getLeftChild();
            nodeStack.push(currentNode);
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
     * "inorder" order.
     *
     * @return The next `BinaryTreeNode` in this binary tree.
     */
    public BinaryTreeNode<T> nextNode() {
        BinaryTreeNode<T> poppedNode = nodeStack.pop();

        // If the top node has a right child, use it to add to the stack.
        if (poppedNode.hasRightChild()) {
            makeStack(poppedNode.getRightChild());
        }

        return poppedNode;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

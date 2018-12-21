package structures;

import java.util.Iterator;

import iterators.InOrderIterator;
import iterators.LevelOrderIterator;
import iterators.PostOrderIterator;

/**
 * A utility that can be used to interact with `BinaryTreeNode`s.
 *
 * @author skunkmb
 */
public class LinkedBinaryTreeUtility implements BinaryTreeUtility {
    @Override
    public <T> Iterator<T> getPreOrderIterator(BinaryTreeNode<T> root) {
        if (root == null) {
            throw new NullPointerException("Root cannot be null");
        }

        return new PreOrderIterator<T>(root);
    }

    @Override
    public <T> Iterator<T> getInOrderIterator(BinaryTreeNode<T> root) {
        if (root == null) {
            throw new NullPointerException("Root cannot be null");
        }

        return new InOrderIterator<T>(root);
    }

    @Override
    public <T> Iterator<T> getPostOrderIterator(BinaryTreeNode<T> root) {
        if (root == null) {
            throw new NullPointerException("Root cannot be null");
        }

        return new PostOrderIterator<T>(root);
    }

    @Override
    public <T> Iterator<T> getLevelOrderIterator(BinaryTreeNode<T> root) {
        if (root == null) {
            throw new NullPointerException("Root cannot be null");
        }

        return new LevelOrderIterator<T>(root);
    }

    @Override
    public <T> int getDepth(BinaryTreeNode<T> root) {
        if (root == null) {
            throw new NullPointerException("Root cannot be null.");
        }

        if (!root.hasLeftChild() && !root.hasRightChild()) {
            return 0;
        }

        if (root.hasLeftChild() && !root.hasRightChild()) {
            return 1 + getDepth(root.getLeftChild());
        }

        if (!root.hasLeftChild() && root.hasRightChild()) {
            return 1 + getDepth(root.getRightChild());
        }

        return 1 + Math.max(
            getDepth(root.getLeftChild()),
            getDepth(root.getRightChild())
        );
    }

    @Override
    public <T> boolean isBalanced(BinaryTreeNode<T> root, int tolerance) {
        if (tolerance < 0) {
            throw new IllegalArgumentException("Tolerance must be at least 0.");
        }

        return getBalanceHelperDepth(root, tolerance) != Integer.MIN_VALUE;
    }

    /**
     * Gets the depth for a binary tree starting with a root node,
     * or returns `Integer.MIN_VALUE` if its children are not balanced
     * within a specified tolerance.
     *
     * @param node The node to start with.
     * @param tolerance The difference to allow between depths.
     * @param <T> The type of the data for the tree.
     * @return The depth of the tree, or `Integer.MIN_VALUE` if is not
     * balanced.
     */
    private <T> int getBalanceHelperDepth(BinaryTreeNode<T> node, int tolerance) {
        if (node == null) {
            throw new NullPointerException("Root cannot be null.");
        }

        int leftDepth;
        int rightDepth;

        if (node.hasLeftChild()) {
            leftDepth = getBalanceHelperDepth(node.getLeftChild(), tolerance);

            if (leftDepth == Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }
        } else {
            leftDepth = 0;
        }

        if (node.hasRightChild()) {
            rightDepth = getBalanceHelperDepth(node.getRightChild(), tolerance);

            if (rightDepth == Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }
        } else {
            rightDepth = 0;
        }

        if (tolerance < Math.abs(leftDepth - rightDepth)) {
            return Integer.MIN_VALUE;
        }

        return Math.abs(leftDepth - rightDepth) + 1;
    }

    @Override
    public <T extends Comparable<? super T>> boolean isBST(BinaryTreeNode<T> root) {
        return isBST(root, null, null);
    }

    /**
     * Checks if a root node and its children are a binary search
     * tree, as well as that the root node's value is within a
     * certain range.
     *
     * @param root The node to start with.
     * @param minimum The minimum value for the root node.
     * @param maximum The maximum value for the root node.
     * @param <T> The type of data for the tree.
     * @return Whether or not the root node's tree are a proper binary
     * search tree.
     */
    private <T extends Comparable<? super T>> boolean isBST(
        BinaryTreeNode<T> root,
        T minimum,
        T maximum
    ) {
        if (root == null) {
            throw new NullPointerException("Root cannot be null.");
        }

        if (
            !(minimum == null || minimum.compareTo(root.getData()) <= 0)
                 || !(maximum == null || 0 < maximum.compareTo(root.getData()))
        ) {
            return false;
        }

        if (!root.hasLeftChild() && !root.hasRightChild()) {
            return true;
        }

        if (root.hasLeftChild() && !root.hasRightChild()) {
            return isBST(root.getLeftChild(), minimum, root.getData());
        }

        if (!root.hasLeftChild() && root.hasRightChild()) {
            return isBST(root.getRightChild(), root.getData(), maximum);
        }

        return (
            isBST(root.getLeftChild(), minimum, root.getData())
                && isBST(root.getRightChild(), root.getData(), maximum)
        );
    }
}

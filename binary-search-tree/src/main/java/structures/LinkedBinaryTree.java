package structures;

import java.util.Iterator;

import iterators.InOrderIterator;

/**
 * A tree of `BinaryTreeNode`s, supporting addition, removal, and more.
 *
 * @author skunkmb
 *
 * @param <T> The type of the data for nodes in this tree.
 */
public class LinkedBinaryTree<T extends Comparable<? super T>> implements BinarySearchTree<T> {
    /**
     * The root node for this tree, or `null` if this tree contains no nodes.
     */
    private BinaryTreeNode<T> rootNode;

    @Override
    public BinarySearchTree<T> add(T value) {
        if (value == null) {
            throw new NullPointerException("Value cannot be null");
        }

        if (rootNode == null) {
            rootNode = new LinkedBinaryNode<T>(value, null, null);
            return this;
        }

        addToNodeOrChildren(rootNode, value);
        return this;
    }

    /**
     * Adds a value in a correct position to either a node or any of its
     * children or subchildren.
     *
     * @param node The node to start with.
     * @param value The value to add.
     */
    private void addToNodeOrChildren(BinaryTreeNode<T> node, T value) {
        if (
            (!node.hasLeftChild() && !node.hasRightChild())
                || nodeShouldFillLeft(node, value)
                || nodeShouldFillRight(node, value)
        ) {
            addToNodeMissingChild(node, value);
            return;
        }

        if (0 <= node.getData().compareTo(value)) {
            addToNodeOrChildren(node.getLeftChild(), value);
        } else {
            addToNodeOrChildren(node.getRightChild(), value);
        }
    }

    /**
     * Adds a value as a child of a node, either on the left or the right,
     * ignoring whether or not it has subchildren.
     *
     * @param node The parent node to add to.
     * @param value The new value.
     */
    private void addToNodeMissingChild(BinaryTreeNode<T> node, T value) {
        if (0 <= node.getData().compareTo(value)) {
            node.setLeftChild(new LinkedBinaryNode(value, null, null));
        } else {
            node.setRightChild(new LinkedBinaryNode(value, null, null));
        }
    }

    /**
     * Returns whether or not a node should have its right child set
     * to a value. This is true if the right child is empty and the value
     * is greater than the value of the parent node.
     *
     * @param node The parent node to check.
     * @param value The new value.
     * @return Whether or not it should be added.
     */
    private boolean nodeShouldFillRight(BinaryTreeNode<T> node, T value) {
        return !node.hasRightChild() && (node.getData().compareTo(value) < 0);
    }

    /**
     * Returns whether or not a node should have its left child set
     * to a value. This is true if the left child is empty and the value
     * is less than or equal to the value of the parent node.
     *
     * @param node The parent node to check.
     * @param value The new value.
     * @return Whether or not it should be added.
     */
    private boolean nodeShouldFillLeft(BinaryTreeNode<T> node, T value) {
        return !node.hasLeftChild() && (0 <= node.getData().compareTo(value));
    }

    /**
     * Removes a value from the children or subchildren of a node. Note,
     * this cannot remove the value if the start node equals the value
     * itself.
     *
     * @param node The node to start with.
     * @param value The value to remove
     * @return Whether or not it was successfully removed.
     */
    private boolean removeFromNodeChildren(BinaryTreeNode<T> node, T value) {
        // If the start node has no children, then the value cannot be
        // removed.
        if (!node.hasLeftChild() && !node.hasRightChild()) {
            return false;
        }

        if (node.hasLeftChild() && node.getLeftChild().getData().compareTo(value) == 0) {
            doRemoveHelper(node, true);
            return true;
        }

        if (node.hasRightChild() && node.getRightChild().getData().compareTo(value) == 0) {
            doRemoveHelper(node, false);
            return true;
        }

        if (0 <= node.getData().compareTo(value) && node.hasLeftChild()) {
            return removeFromNodeChildren(node.getLeftChild(), value);
        } else if (node.hasRightChild()) {
            return removeFromNodeChildren(node.getRightChild(), value);
        }

        return false;
    }

    /**
     * Runs a remove helper which then calls an appropriate method for
     * removing from a parent node.
     *
     * @param nodeParent The parent of the node to remove.
     * @param isNodeLeft Whether the node is the parent's left or right
     * child.
     */
    private void doRemoveHelper(BinaryTreeNode<T> nodeParent, boolean isNodeLeft) {
        BinaryTreeNode<T> toRemove;

        if (isNodeLeft) {
            toRemove = nodeParent.getLeftChild();
        } else {
            toRemove = nodeParent.getRightChild();
        }

        if (toRemove.hasLeftChild() && toRemove.hasRightChild()) {
            removeNodeWithTwoChildren(nodeParent, isNodeLeft);
        } else if (!toRemove.hasLeftChild() && !toRemove.hasRightChild()) {
            removeNodeWithNoChildren(nodeParent, isNodeLeft);
        } else {
            removeNodeWithOneChild(nodeParent, isNodeLeft);
        }
    }

    /**
     * Removes a node that has no children.
     *
     * @param nodeParent The parent of the node to remove.
     * @param isNodeLeft Whether the node is the parent's left or right
     * child.
     */
    private void removeNodeWithNoChildren(BinaryTreeNode<T> nodeParent, boolean isNodeLeft) {
        if (isNodeLeft) {
            nodeParent.setLeftChild(null);
        } else {
            nodeParent.setRightChild(null);
        }
    }

    /**
     * Removes a node that has one child.
     *
     * @param nodeParent The parent of the node to remove.
     * @param isNodeLeft Whether the node is the parent's left or right
     * child.
     */
    private void removeNodeWithOneChild(BinaryTreeNode<T> nodeParent, boolean isNodeLeft) {
        if (isNodeLeft) {
            nodeParent.setLeftChild(
                getOnlyNodeChild(nodeParent.getLeftChild())
            );
        } else {
            nodeParent.setRightChild(
                getOnlyNodeChild(nodeParent.getRightChild())
            );
        }
    }

    /**
     * Returns the only child of a node with one child, or `null`. If
     * the node actually has two children, the left one will be returned.
     * This is useful for removing nodes with only one child.
     *
     * @param parentNode The node to get the only child of.
     * @return The only child, or `null`.
     */
    private BinaryTreeNode<T> getOnlyNodeChild(BinaryTreeNode<T> parentNode) {
        if (parentNode.hasLeftChild()) {
            return parentNode.getLeftChild();
        } else if (parentNode.hasRightChild()) {
            return parentNode.getRightChild();
        } else {
            return null;
        }
    }

    /**
     * Removes a node that has two children.
     *
     * @param nodeParent The parent of the node to remove.
     * @param isNodeLeft Whether the node is the parent's left or right
     * child.
     */
    private void removeNodeWithTwoChildren(BinaryTreeNode<T> nodeParent, boolean isNodeLeft) {
        if (isNodeLeft) {
            nodeParent.setLeftChild(
                getUpdatedNode(nodeParent.getLeftChild())
            );
        } else {
            nodeParent.setRightChild(
                getUpdatedNode(nodeParent.getRightChild())
            );
        }
    }

    /**
     * Returns a `BinaryTreeNode` that can be used to replace a node
     * that is being removed with two children.
     *
     * @param nodeToRemove The node that is being removed.
     * @return The replacement node.
     */
    private BinaryTreeNode<T> getUpdatedNode(BinaryTreeNode<T> nodeToRemove) {
        BinaryTreeNode<T> currentReplacement = nodeToRemove.getRightChild();
        BinaryTreeNode<T> currentReplacementParent = nodeToRemove;

        // Loop through all left children to find the inorder successor.
        while (currentReplacement.hasLeftChild()) {
            currentReplacementParent = currentReplacement;
            currentReplacement = currentReplacement.getLeftChild();
        }

        // Set the new replacement node's children to maintain the tree's
        // structure.
        if (nodeToRemove.getRightChild().equals(currentReplacement)) {
            currentReplacement.setLeftChild(nodeToRemove.getLeftChild());
        } else {
            if (currentReplacement.hasRightChild()) {
                currentReplacementParent.setLeftChild(currentReplacement.getRightChild());
            } else {
                currentReplacementParent.setLeftChild(null);
            }

            currentReplacement.setRightChild(nodeToRemove.getRightChild());
            currentReplacement.setLeftChild(nodeToRemove.getLeftChild());
        }

        return currentReplacement;
    }

    @Override
    public boolean contains(T value) {
        if (value == null) {
            throw new NullPointerException("Value cannot be null.");
        }

        if (rootNode == null) {
            return false;
        }

        InOrderIterator<T> iterator = new InOrderIterator<T>(rootNode);

        while (iterator.hasNext()) {
            if (iterator.next().equals(value)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean remove(T value) {
        if (rootNode == null) {
            return false;
        }

        if (rootNode.getData().compareTo(value) == 0) {
            if (rootNode.hasLeftChild() && rootNode.hasRightChild()) {
                rootNode = getUpdatedNode(rootNode);
            } else if (rootNode.hasLeftChild()) {
                rootNode.setData(rootNode.getLeftChild().getData());
                rootNode.setLeftChild(getOnlyNodeChild(rootNode.getLeftChild()));
            } else if (rootNode.hasRightChild()) {
                rootNode.setData(rootNode.getRightChild().getData());
                rootNode.setRightChild(getOnlyNodeChild(rootNode.getRightChild()));
            } else {
                rootNode = null;
            }

            return true;
        }

        return removeFromNodeChildren(rootNode, value);
    }

    @Override
    public int size() {
        if (rootNode == null) {
            return 0;
        }

        InOrderIterator<T> iterator = new InOrderIterator<T>(rootNode);
        int currentSize = 0;

        while (iterator.hasNext()) {
            iterator.next();
            currentSize++;
        }

        return currentSize;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public T getMinimum() {
        if (size() == 0) {
            throw new IllegalStateException("There must be elements in the tree.");
        }

        InOrderIterator<T> iterator = new InOrderIterator<T>(rootNode);
        return iterator.next();
    }

    @Override
    public T getMaximum() {
        if (size() == 0) {
            throw new IllegalStateException("There must be elements in the tree.");
        }

        T currentMaximum = null;
        InOrderIterator<T> iterator = new InOrderIterator<T>(rootNode);

        while (iterator.hasNext()) {
            currentMaximum = iterator.next();
        }

        return currentMaximum;
    }

    @Override
    public BinaryTreeNode<T> toBinaryTreeNode() {
        return rootNode;
    }

    @Override
    public Iterator<T> iterator() {
        return new InOrderIterator<T>(rootNode);
     }
}

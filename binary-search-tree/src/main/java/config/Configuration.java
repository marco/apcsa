package config;

import structures.BinarySearchTree;
import structures.BinaryTreeNode;
import structures.BinaryTreeUtility;
import structures.LinkedBinaryNode;
import structures.LinkedBinaryTree;
import structures.LinkedBinaryTreeUtility;


/**
 * This class acts as a configuration file which tells the testing framework
 * which implementation you want us to use when we grade your assignment.
 *
 * @author jddevaug
 *
 */
public class Configuration {
    /**
     * Returns a `BinaryTreeNode` implementation.
     *
     * @param left The left child of this node.
     * @param elem The value for this node.
     * @param right The right child of this node.
     * @param <T> The type of the data for the nodes in the tree.
     * @return The `BinaryTreeNode`.
     */
    public static <T> BinaryTreeNode<T> createBinaryTreeNode(BinaryTreeNode<T> left, T elem, BinaryTreeNode<T> right) {
        return new LinkedBinaryNode(elem, left, right);
    }

    /**
     * Returns a `BinaryTreeUtility` implementation.
     *
     * @return The `BinaryTreeUtility`.
     */
    public static BinaryTreeUtility createBinaryTreeUtility() {
        return new LinkedBinaryTreeUtility();
    }

    /**
     * Returns a `BinarySearchTree` implementation.
     *
     * @param <T> The type of the data for the nodes in the tree.
     * @return The `BinarySearchTree`.
     */
    public static <T extends Comparable<? super T>> BinarySearchTree<T> createBinarySearchTree() {
        return new LinkedBinaryTree();
    }
}

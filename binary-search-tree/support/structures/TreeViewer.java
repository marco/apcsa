package structures;

import java.util.LinkedList;
import java.util.Queue;

import config.Configuration;

/**
 * A tree-viewer that can be used for displaying trees.
 */
public class TreeViewer {
    /**
     * Converts a binary tree to dot format.
     *
     * @param root The root node of this binary tree.
     * @param <T> The type of data for the tree.
     * @return The dot format string.
     */
    public static <T> String toDotFormat(BinaryTreeNode<T> root) {
        // header
        int count = 0;
        String dot = "digraph G { \n";
        dot += "graph [ordering=\"out\"]; \n";
        // iterative traversal
        Queue<BinaryTreeNode<T>> queue = new LinkedList<BinaryTreeNode<T>>();
        queue.add(root);
        BinaryTreeNode<T> cursor;
        while (!queue.isEmpty()) {
            cursor = queue.remove();
            if (cursor.hasLeftChild()) {
                // add edge from cursor to left child
                dot += cursor.getData().toString() + " -> "
                    + cursor.getLeftChild().getData().toString() + ";\n";
                queue.add(cursor.getLeftChild());
            } else {
                // add dummy node
                dot += "node" + count + " [shape=point];\n";
                dot += cursor.getData().toString() + " -> " + "node" + count
                    + ";\n";
                count++;
            }
            if (cursor.hasRightChild()) {
                // add edge from cursor to right child
                dot += cursor.getData().toString() + " -> "
                    + cursor.getRightChild().getData().toString() + ";\n";
                queue.add(cursor.getRightChild());
            } else {
                // add dummy node
                dot += "node" + count + " [shape=point];\n";
                dot += cursor.getData().toString() + " -> " + "node" + count
                    + ";\n";
                count++;
            }

        }
        dot += "};";
        return dot;
    }

    /**
     * Creates a `BinaryTreeNode`.
     *
     * @param left The left node to use.
     * @param elem The data to use.
     * @param right The right node to use.
     * @param <T> the type of data for the node.
     * @return The `BinaryTreeNode`.
     */
    private static <T> BinaryTreeNode<T> node(BinaryTreeNode<T> left,
        T elem, BinaryTreeNode<T> right) {
        return Configuration.createBinaryTreeNode(left, elem, right);
    }

    /**
     * Demonstrates a dot format for a tree.
     *
     * @param args Command line arguments, which are ignored.
     */
    public static void main(String[] args) {
        final int[] NODE_VALUES = new int[] {6, 3, 7, 5, 4, 1, 19};

        BinaryTreeNode<Integer> tree = node(
            node(node(null, NODE_VALUES[0], null), NODE_VALUES[1],
            node(null, NODE_VALUES[2], null)), NODE_VALUES[3],
            node(node(node(null, NODE_VALUES[4], null), NODE_VALUES[5], null),
            NODE_VALUES[6], null));
        System.out.println(toDotFormat(tree));
    }

}

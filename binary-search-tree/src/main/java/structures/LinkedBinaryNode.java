package structures;

/**
 * A binary node that links to two other binary nodes.
 *
 * @author skunkmb
 *
 * @param <T> The type of the data for this node.
 */
public class LinkedBinaryNode<T> implements BinaryTreeNode<T> {
    /**
     * The value held by this node.
     */
    private T data;

    /**
     * The left child of this node.
     */
    private BinaryTreeNode<T> leftNode;

    /**
     * The right child of this node.
     */
    private BinaryTreeNode<T> rightNode;

    /**
     * Constructs a `LinkedBinaryNode`.
     *
     * @param data The data to use for this node.
     * @param leftNode The left child for this node.
     * @param rightNode The right child for this node.
     */
    public LinkedBinaryNode(
        T data,
        BinaryTreeNode<T> leftNode,
        BinaryTreeNode<T> rightNode
    ) {
        this.data = data;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean hasLeftChild() {
        return leftNode != null;
    }

    @Override
    public boolean hasRightChild() {
        return rightNode != null;
    }

    @Override
    public BinaryTreeNode<T> getLeftChild() {
        if (!hasLeftChild()) {
            throw new IllegalStateException("Node does not have a left child.");
        }

        return leftNode;
    }

    @Override
    public BinaryTreeNode<T> getRightChild() {
        if (!hasRightChild()) {
            throw new IllegalStateException("Node does not have a right child.");
        }

        return rightNode;
    }

    @Override
    public void setLeftChild(BinaryTreeNode<T> newLeftNode) {
        leftNode = newLeftNode;
    }

    @Override
    public void setRightChild(BinaryTreeNode<T> newRightNode) {
        rightNode = newRightNode;
    }
}

package stack;

/**
 * A node that can link to a previous node.
 *
 * @author skunkmb
 * @param <T> The type of the `LinkedNode`'s value.
 */
public class LinkedNode<T> {
    /**
     * The value of this `LinkedNode`.
     */
    private T value;

    /**
     * The previous `LinkedNode`.
     */
    private LinkedNode<T> previousNode;

    /**
     * Initializes a new `LinkedNode`.
     *
     * @param value The value of this `LinkedNode`.
     * @param previousNode The previous `LinkedNode` to link to.
     */
    public LinkedNode(T value, LinkedNode<T> previousNode) {
        this.value = value;
        this.previousNode = previousNode;
    }

    /**
     * Gets the value of this `LinkedNode`.
     *
     * @return The value of this `LinkedNode`.
     */
    public T getValue() {
        return value;
    }

    /**
     * Gets the previous `LinkedNode`.
     *
     * @return The previous `LinkedNode`.
     */
    public LinkedNode<T> getPreviousNode() {
        return previousNode;
    }
}

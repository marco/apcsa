package structures;

/**
 * A node that can link to other nodes.
 *
 * @author skunkmb
 * @param <T> The type of value for this node.
 */
public class LinkedNode<T> {
    /**
     * The value of this node.
     */
    private T value;

    /**
     * The node to link to.
     */
    private LinkedNode<T> nextNode;

    /**
     * Constructs a `LinkedNode`.
     *
     * @param value The value of this node.
     * @param nextNode The node to link to.
     */
    public LinkedNode(T value, LinkedNode<T> nextNode) {
        this.value = value;
        this.nextNode = nextNode;
    }

    /**
     * Returns the value of this node.
     * @return The value of this node.
     */
    public T getValue() {
        return value;
    }

    /**
     * Returns the node that this node links to.
     * @return The node that this node links to.
     */
    public LinkedNode<T> getNextNode() {
        return nextNode;
    }

    /**
     * Sets the node to link to.
     *
     * @param nextNode The new node to link to.
     */
    public void setNextNode(LinkedNode<T> nextNode) {
        this.nextNode = nextNode;
    }
}

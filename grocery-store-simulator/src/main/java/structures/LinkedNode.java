package structures;

/**
 * A node that has a value and can link to other nodes.
 *
 * @author skunkmb
 *
 * @param <T> The type of the value of this node.
 */
class LinkedNode<T> {
    /**
     * The next `LinkedNode`.
     */
    private LinkedNode<T> next;

    /**
     * The value of this node.
     */
    private T value;

    /**
     * Initializes a new `LinkedNode`.
     *
     * @param value The value of this node.
     * @param next The next `LinkedNode`.
     */
    public LinkedNode(T value, LinkedNode<T> next) {
        this.value = value;
        this.next = next;
    }

    /**
     * Returns the next `LinkedNode`.
     *
     * @return The next `LinkedNode`.
     */
    public LinkedNode<T> getNext() {
        return next;
    }

    /**
     * Changes the next `LinkedNode`.
     *
     * @param next The new next `LinkedNode`.
     */
    public void setNext(LinkedNode<T> next) {
        this.next = next;
    }

    /**
     * Returns the value of this node.
     *
     * @return The value of this node.
     */
    public T getValue() {
        return value;
    }
}

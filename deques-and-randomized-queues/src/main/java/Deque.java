import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double-ended queue, which supports adding and removing from the
 * front and back.
 *
 * @author skunkmb
 * @param <Item> The type of the elements in the deque.
 */
public class Deque<Item> implements Iterable<Item> {
    /**
     * The first node in the deque, or `null` if the deque is empty.
     */
    private Node<Item> firstNode;

    /**
     * The last node in the deque, or `null` if the deque is empty. If
     * there is one element in the deque, then `lastNode` equals
     * `firstNode`.
     */
    private Node<Item> lastNode;

    /**
     * The current number of elements in the deque.
     */
    private int size;

    /**
     * Constructs a `Deque` with no elements.
     */
    public Deque() {
        firstNode = null;
        lastNode = null;
        size = 0;
    }

    /**
     * Returns whether or not the deque is empty (if there are no
     * elements).
     *
     * @return Whether or not the deque is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the current number of elements in the deque.
     *
     * @return The size of the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Adds an element to the front of the deque.
     *
     * @param value The element to be added. The element cannot be
     * `null`.
     */
    public void addFirst(Item value) {
        if (value == null) {
            throw new IllegalArgumentException(
                "A null value cannot be added."
            );
        }

        if (isEmpty()) {
            firstNode = new Node<Item>(value, null, null);
            lastNode = firstNode;
            size++;
            return;
        }

        Node<Item> newFirst = new Node<Item>(value, null, firstNode);
        firstNode.previousNode = newFirst;
        firstNode = newFirst;
        size++;
    }

    /**
     * Adds an element to the end of the deque.
     *
     * @param value The element to be added. The element cannot be
     * `null`.
     */
    public void addLast(Item value) {
        if (value == null) {
            throw new IllegalArgumentException(
                "A null value cannot be added."
            );
        }

        if (isEmpty()) {
            firstNode = new Node<Item>(value, null, null);
            lastNode = firstNode;
            size++;
            return;
        }

        Node<Item> newLast = new Node<Item>(value, lastNode, null);
        lastNode.nextNode = newLast;
        lastNode = newLast;
        size++;
    }

    /**
     * Removes and returns the first element of the deque.
     *
     * @return The first element of the deque.
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException(
                "The deque is empty, so the first element cannot be removed."
            );
        }

        if (size == 1) {
            Node<Item> oldFirst = firstNode;
            firstNode = null;
            lastNode = null;
            size--;
            return oldFirst.value;
        }

        Node<Item> oldFirst = firstNode;
        firstNode = firstNode.nextNode;
        firstNode.previousNode = null;
        size--;
        return oldFirst.value;
    }

    /**
     * Removes and returns the last element of the deque.
     *
     * @return The last element of the deque.
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException(
                "The deque is empty, so the last element cannot be removed."
            );
        }

        if (size == 1) {
            Node<Item> oldLast = lastNode;
            firstNode = null;
            lastNode = null;
            size--;
            return oldLast.value;
        }

        Node<Item> oldLast = lastNode;
        lastNode = lastNode.previousNode;
        lastNode.nextNode = null;
        size--;
        return oldLast.value;
    }

    /**
     * Returns an `Iterator` that represents the deque.
     *
     * @return The `Iterator`.
     */
    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(firstNode);
    }

    /**
     * A doubly linked node, used for managing elements of the `Deque`.
     *
     * @author skunkmb
     * @param <Item> The type of the node's value.
     */
    private class Node<Item> {
        /**
         * The value that this node holds.
         */
        private Item value;

        /**
         * The node before this node, or `null` if this is the first
         * node.
         */
        private Node<Item> previousNode;

        /**
         * The node after this node, or `null` if this is the last
         * node.
         */
        private Node<Item> nextNode;

        /**
         * Constructs a `Node`.
         *
         * @param value The value of this node.
         * @param previousNode The node before this node, or `null` if
         * this is the first node.
         * @param nextNode The node after this node, or `null` if this
         * is the last node.
         */
        private Node(Item value, Node<Item> previousNode, Node<Item> nextNode) {
            this.value = value;
            this.previousNode = previousNode;
            this.nextNode = nextNode;
        }
    }

    /**
     * An `Iterator` that can be used to iterate through a `Deque`.
     *
     * @author skunkmb
     * @param <Item> The type of the elements of the `Deque`.
     */
    private class DequeIterator<Item> implements Iterator<Item> {
        /**
         * The current iterated node of the `Deque`.
         */
        private Node<Item> currentNode;

        /**
         * Constructs a `DequeIterator`.
         *
         * @param firstNode The first node in the `Deque` to iterate, or
         * `null` if the deque is empty.
         */
        private DequeIterator(Node<Item> firstNode) {
            currentNode = firstNode;
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException(
                    "There are no remaining deque elements."
                );
            }

            Item currentValue = currentNode.value;
            currentNode = currentNode.nextNode;
            return currentValue;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                "Removing elements from the deque iterator is not supported"
            );
        }
    }
}

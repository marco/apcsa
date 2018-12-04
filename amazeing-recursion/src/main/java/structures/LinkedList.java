package structures;

import structure.ListInterface;

/**
 * A recursive linked list of `LinkedNode`s.
 *
 * @author skunkmb
 * @param <T> The type for each element in the list.
 */
public class LinkedList<T> implements ListInterface<T> {
    /**
     * The first node in this list.
     */
    private LinkedNode<T> firstNode;

    /**
     * The current size of this list.
     */
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public ListInterface<T> insertFirst(T element) {
        firstNode = new LinkedNode<T>(element, firstNode);
        size++;
        return this;
    }

    @Override
    public ListInterface<T> insertLast(T element) {
        if (firstNode == null) {
            firstNode = new LinkedNode<T>(element, null);
            size++;
            return this;
        }

        LinkedNode<T> oldLastNode = getLastLinkedNode(firstNode);
        oldLastNode.setNextNode(new LinkedNode(element, null));
        size++;
        return this;
    }

    /**
     * Returns the last `LinkedNode` in a `LinkedNode` chain.
     *
     * @param startNode The node to start with.
     * @return The last `LinkedNode`.
     */
    private LinkedNode<T> getLastLinkedNode(LinkedNode<T> startNode) {
        if (startNode.getNextNode() == null) {
            return startNode;
        }

        return getLastLinkedNode(startNode.getNextNode());
    }

    @Override
    public ListInterface<T> insertAt(int index, T element) {
        if (index == 0) {
            insertFirst(element);
            return this;
        }

        LinkedNode<T> nodeBeforeIndex = getLinkedNodeAtIndex(firstNode, index - 1);
        LinkedNode<T> nodeAtIndex = nodeBeforeIndex.getNextNode();
        LinkedNode<T> newNode = new LinkedNode(element, nodeAtIndex);
        nodeBeforeIndex.setNextNode(newNode);
        size++;
        return this;
    }

    /**
     * Returns the `LinkedNode` at an index in a `LinkedNode` sequence.
     *
     * @param startNode The node to start with.
     * @param index The index to return at.
     * @return The found `LinkedNode`.
     */
    private LinkedNode<T> getLinkedNodeAtIndex(LinkedNode<T> startNode, int index) {
        if (index == 0) {
            return startNode;
        }

        LinkedNode<T> nextNode = startNode.getNextNode();

        if (nextNode == null) {
            throw new NullPointerException("That node link is not long enough.");
        }

        return getLinkedNodeAtIndex(nextNode, index - 1);
    }

    @Override
    public T removeFirst() {
        if (firstNode == null) {
            throw new IllegalStateException("There are no elements in this list");
        }

        LinkedNode<T> oldFirstNode = firstNode;
        firstNode = oldFirstNode.getNextNode();
        size--;
        return oldFirstNode.getValue();
    }

    @Override
    public T removeLast() {
        if (firstNode == null) {
            throw new IllegalStateException("There are no elements in this list");
        }

        if (firstNode.getNextNode() == null) {
            return removeFirst();
        }

        T oldValue = getLastLinkedNode(firstNode).getValue();
        getSecondLastNode(firstNode).setNextNode(null);
        size--;
        return oldValue;
    }

    /**
     * Returns the second-to-last node in a `LinkedNode` sequence. This is
     * useful when removing the last element in the list.
     *
     * @param startNode The node to start with.
     * @return The second-to-last node.
     */
    private LinkedNode<T> getSecondLastNode(LinkedNode<T> startNode) {
        if (startNode.getNextNode().getNextNode() == null) {
            return startNode;
        }

        return getSecondLastNode(startNode.getNextNode());
    }

    @Override
    public T removeAt(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index cannot be negative.");
        }

        if (size <= index) {
            throw new IndexOutOfBoundsException("That index is out of bounds.");
        }

        if (firstNode == null) {
            throw new IllegalStateException("There are no elements in this list");
        }

        LinkedNode<T> oldNode = getLinkedNodeAtIndex(firstNode, index);
        getLinkedNodeAtIndex(firstNode, index - 1).setNextNode(oldNode.getNextNode());
        size--;
        return oldNode.getValue();
    }

    @Override
    public T getFirst() {
        if (firstNode == null) {
            throw new IllegalStateException(
                    "There are no elements in this list");
        }

        return firstNode.getValue();
    }

    @Override
    public T getLast() {
        if (firstNode == null) {
            throw new IllegalStateException(
                    "There are no elements in this list");
        }

        return getLastLinkedNode(firstNode).getValue();
    }

    @Override
    public T get(int index) {
        if (firstNode == null) {
            throw new IllegalStateException(
                "There are no elements in this list"
            );
        }

        return getLinkedNodeAtIndex(firstNode, index).getValue();
    }

    @Override
    public boolean remove(T elem) {
        if (firstNode == null) {
            throw new IllegalStateException("There are no elements in this list");
        }

        if (firstNode.getValue().equals(elem)) {
            removeFirst();
            return true;
        }

        LinkedNode<T> nodeToRemovePrevious = getLinkedNodeWithValueNext(firstNode, elem);

        if (nodeToRemovePrevious == null) {
            return false;
        }

        nodeToRemovePrevious.setNextNode(nodeToRemovePrevious.getNextNode().getNextNode());
        size--;
        return true;
    }

    @Override
    public int contains(T element) {
        if (firstNode == null) {
            return -1;
        }

        return getIndexOfLinkedNodeWithValue(firstNode, element, 0);
    }

    /**
     * Finds the index of a `LinkedNode` with a value.
     *
     * @param startNode The node to start with.
     * @param value The value to search for.
     * @param startIndex The index to start with.
     * @return The index of the found node, or `-1` if none is found.
     */
    private int getIndexOfLinkedNodeWithValue(LinkedNode<T> startNode, T value, int startIndex) {
        if (startNode.getValue() == value) {
            return startIndex;
        }

        if (startNode.getNextNode() == null) {
            return -1;
        }

        return getIndexOfLinkedNodeWithValue(startNode.getNextNode(), value, startIndex + 1);
    }

    /**
     * Finds a `LinkedNode` where the next `LinkedNode`'s value is a
     * given value. This is useful when removing nodes.
     *
     * @param startNode The node to start with.
     * @param value The value to search for.
     * @return The node where the next node has the value.
     */
    private LinkedNode<T> getLinkedNodeWithValueNext(LinkedNode<T> startNode, T value) {
        if (startNode.getNextNode() == null) {
            return null;
        }

        if (startNode.getNextNode().getValue().equals(value)) {
            return startNode;
        }

        return getLinkedNodeWithValueNext(startNode.getNextNode(), value);
    }

    @Override
    public boolean isEmpty() {
        return firstNode == null;
    }

    @Override
    public String toString() {
        return "[" + getStringForElements(firstNode) + "]";
    }

    /**
     * Returns a comma-separated `String` of values, starting with a
     * `LinkedNode`.
     *
     * @param startNode The node to start with.
     * @return The comma-separated `String`.
     */
    private String getStringForElements(LinkedNode<T> startNode) {
        if (firstNode == null) {
            return "";
        }

        if (startNode.getNextNode() == null) {
            return startNode.toString();
        }

        return startNode.toString() + ", "
                + getStringForElements(startNode.getNextNode());
    }
}

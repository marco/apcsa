package structures;

/**
 * {@inheritDoc}.
 *
 * @author skunkmb
 *
 * @param <T> The type of the elements in this queue.
 *
 */
public class LinkedQueue<T> implements QueueInterface<T> {
    /**
     * The current number of elements in this queue.
     */
    private int count;

    /**
     * The first element in this queue.
     */
    private LinkedNode<T> first;

    /**
     * The last element in this queue, for convenience.
     */
    private LinkedNode<T> last;

    /**
     * {@inheritDoc}
     */
    @Override
    public T dequeue() {
        if (first == null) {
            throw new IllegalStateException("There are no elements on the queue.");
        }

        LinkedNode<T> dequeuedLinkedNode = first;
        first = first.getNext();
        count--;

        if (first == null) {
            last = null;
        }

        return dequeuedLinkedNode.getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T peek() {
        return first.getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QueueInterface enqueue(T elem) {
        if (elem == null) {
            throw new NullPointerException("The element cannot be null.");
        }

        LinkedNode<T> newNode = new LinkedNode<T>(elem, null);

        if (first == null) {
            first = newNode;
        } else {
            last.setNext(newNode);
        }

        last = newNode;
        count++;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        LinkedNode<T> currentNode = first;

        stringBuilder.append("[");

        for (int i = 0; i < count; i++) {
            // If it's not the first element, put a comma before it. This way, there is
            // one less comma than the number of elements, with a comma between the elements.
            if (i != 0) {
                stringBuilder.append(", ");
            }

            stringBuilder.append(currentNode.getValue().toString());
            currentNode = currentNode.getNext();
        }

        stringBuilder.append("]");

        return stringBuilder.toString();
    }
}

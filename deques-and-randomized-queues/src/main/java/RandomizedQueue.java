import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/**
 * A randomized queue, which supports adding items and dequeueing them
 * at random.
 *
 * @author skunkmb
 * @param <Item> The type of the elements in the queue.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    /**
     * The initial length of `items` before it is resized. 3 is used
     * because it is the most efficient amount for preventing initial
     * resizes (for instance, if 4 was used instead, it would be resized to
     * 2 after an item was added and then back to 4 after a second item).
     */
    private static final int INITIAL_ITEMS_LENGTH = 3;

    /**
     * The current array of items in the queue. Non-filled items are
     * `null`. This array is scaled up or down depending on what space
     * is needed.
     */
    private Item[] items;

    /**
     * The current amount of items in the queue.
     */
    private int size;

    /**
     * Constructs an empty `RandomizedQueue`.
     */
    public RandomizedQueue() {
        items = (Item[]) new Object[INITIAL_ITEMS_LENGTH];
    }

    /**
     * Returns whether or not the queue is empty (if there are no
     * elements).
     *
     * @return Whether or not the queue is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the current number of elements in the queue.
     *
     * @return The size of the queue.
     */
    public int size() {
        return size;
    }

    /**
     * Adds an element to the queue.
     *
     * @param value The element to be added. The element cannot be `null`.
     */
    public void enqueue(Item value) {
        if (value == null) {
            throw new IllegalArgumentException(
                "A null value cannot be added."
            );
        }

        items[size] = value;
        size++;

        if (size == items.length) {
            resizeItems(items.length * 2);
        }
    }

    /**
     * Removes and returns a random element of the queue.
     *
     * @return The random removed element.
     */
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException(
                "There are no elements in the queue, so one cannot be dequeued."
            );
        }

        int randomIndex = getRandomIndex();
        Item removedValue = items[randomIndex];
        items[randomIndex] = items[size - 1];
        items[size - 1] = null;
        size--;

        if (size <= items.length / 4) {
            resizeItems(items.length / 2);
        }

        return removedValue;
    }

    /**
     * Returns a random element of the queue without removing it.
     *
     * @return The random element.
     */
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException(
                "There are no elements in the queue, so no sample can be found."
            );
        }

        return items[getRandomIndex()];
    }

    /**
     * Returns an `Iterator` that represents the queue in a random order.
     *
     * @return The `Iterator`.
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>(items, size);
    }

    /**
     * Resizes the `items` array to a new size. This is done if the array
     * either gets too small or too large to efficiently store the data
     * in the queue.
     *
     * @param newSize The new size for the array. If `newSize` is
     * larger than the current number of elements, then all elements
     * are copied. Otherwise, elements copied are stopped after `newSize`
     * (for instance, `null` values after `newSize` would not need to be
     * copied).
     */
    private void resizeItems(int newSize) {
        Item[] newItems = (Item[]) new Object[newSize];

        for (int i = 0; i < Math.min(newSize, size); i++) {
            newItems[i] = items[i];
        }

        items = newItems;
    }

    /**
     * Returns a random array index between 0 and size - 1.
     *
     * @return The random index.
     */
    private int getRandomIndex() {
        if (size == 0) {
            return 0;
        }

        return StdRandom.uniform(0, size);
    }

    /**
     * An `Iterator` that can be used to iterate through a
     * `RandomizedQueue`.
     *
     * @author skunkmb
     * @param <Item> The type of the elements of the `RandomizedQueue`.
     */
    private class RandomizedQueueIterator<Item> implements Iterator<Item> {
        /**
         * An array of randomly-ordered elements, which will be
         * returned in order.
         */
        private Item[] shuffledItems;

        /**
         * The current iterated index to return of `shuffledItems`.
         */
        private int currentIndex = 0;

        /**
         * Constructs a `RandomizedQueueIterator`.
         *
         * @param items An unshuffled array of items to be copied and
         * randomized.
         * @param size The size of the queue to iterate.
         */
        public RandomizedQueueIterator(Item[] items, int size) {
            shuffledItems = (Item[]) new Object[size];
            System.arraycopy(items, 0, shuffledItems, 0, size);
            StdRandom.shuffle(shuffledItems);
        }

        @Override
        public boolean hasNext() {
            return currentIndex < shuffledItems.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException(
                    "There are no remaining deque elements."
                );
            }

            Item currentValue = shuffledItems[currentIndex];
            currentIndex++;
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

package structure;

/**
 * A {@link ListInterface} is a container that supports insertion, removal, and
 * searching.
 *
 * @author jcollard, jddevaug
 *
 * @param <T> The type of this list.
 */
public interface ListInterface<T> {

  /**
   * Returns the number of elements in this {@link ListInterface}. This method
   * runs in O(1) time.
   *
   * @return the number of elements in this {@link ListInterface}
   */
  int size();

  /**
   * Adds an element to the front of this {@link ListInterface}. This method
   * runs in O(1) time. For convenience, this method returns the
   * {@link ListInterface} that was modified.
   *
   * @param elem
   *            the element to add
   * @return The modified {@link ListInterface}
   * @throws NullPointerException
   *             if {@code elem} is {@code null}
   */
  ListInterface<T> insertFirst(T elem);

  /**
   * Adds an element to the end of this {@link ListInterface}. This method
   * runs in O(size) time. For convenience, this method returns the
   * {@link ListInterface} that was modified.
   *
   * @param elem
   *            the element to add
   * @return the modified {@link ListInterface}
   * @throws NullPointerException
   *             if {@code elem} is {@code null}
   */
  ListInterface<T> insertLast(T elem);

  /**
   * Adds an element at the specified index such that a subsequent call to
   * {@link ListInterface#get(int)} at {@code index} will return the inserted
   * value. This method runs in O(index) time. For convenience, this method
   * returns the {@link ListInterface} that was modified.
   *
   * @param index
   *            the index to add the element at
   * @param elem
   *            the element to add
   * @return The modified {@link ListInterface}
   * @throws NullPointerException
   *             if {@code elem} is {@code null}
   * @throws IndexOutOfBoundsException
   *             if {@code index} is less than 0 or greater than
   *             {@link ListInterface#size()}
   */
  ListInterface<T> insertAt(int index, T elem);

  /**
   * Removes the first element from this {@link ListInterface} and returns it.
   * This method runs in O(1) time.
   *
   * @return the removed element
   * @throws IllegalStateException
   *             if the {@link ListInterface} is empty.
   */
  T removeFirst();

  /**
   * <p>
   * Removes the last element from this {@link ListInterface} and returns it.
   * This method runs in O(size) time.
   *</p>
   *
   * @return the removed element
   * @throws IllegalStateException
   *             if the {@link ListInterface} is empty.
   */
  T removeLast();

  /**
   * Removes the ith element in this {@link ListInterface} and returns it.
   * This method runs in O(index) time.
   *
   * @param index
   *            the index of the element to remove
   * @return The removed element
   * @throws IndexOutOfBoundsException
   *             if {@code index} is less than 0 or {@code index} is greater than or
   *             equal to {@link ListInterface#size()}
   */
  T removeAt(int index);

  /**
   * Returns the first element in this {@link ListInterface}. This method runs
   * in O(1) time.
   *
   * @return the first element in this {@link ListInterface}.
   * @throws IllegalStateException
   *             if the {@link ListInterface} is empty.
   */
  T getFirst();

  /**
   * Returns the last element in this {@link ListInterface}. This method runs
   * in O(size) time.
   *
   * @return the last element in this {@link ListInterface}.
   * @throws IllegalStateException
   *             if the {@link ListInterface} is empty.
   */
  T getLast();

  /**
   * Returns the ith element in this {@link ListInterface}. This method runs
   * in O(index) time.
   *
   * @param index
   *            the index to lookup
   * @return the ith element in this {@link ListInterface}.
   * @throws IndexOutOfBoundsException
   *             if {@code index} is less than 0 or {@code index} is greater than or
   *             equal to {@link ListInterface#size()}
   */
  T get(int index);

  /**
   * Removes {@code elem} from this {@link ListInterface} if it exists. If
   * multiple instances of {@code elem} exist in this {@link ListInterface}
   * the one associated with the smallest index is removed. This method runs
   * in O(size) time.
   *
   * @param elem
   *            the element to remove
   * @return {@code true} if this {@link ListInterface} was altered and
   *         {@code false} otherwise.
   */
  boolean remove(T elem);

  /**
   * Returns the smallest index which contains {@code elem}. If there is no
   * instance of {@code elem} in this {@link ListInterface} then -1 is
   * returned. This method runs in O(size) time.
   *
   * @param elem
   *            the element to search for
   * @return the smallest index which contains {@code elem} or -1 if
   *         {@code elem} is not in this {@link ListInterface}
   */
  int contains(T elem);

  /**
   * Returns {@code true} if this {@link ListInterface} contains no elements
   * and {@code false} otherwise. This method runs in O(1) time.
   *
   * @return {@code true} if this {@link ListInterface} contains no elements
   *         and {@code false} otherwise.
   */
  boolean isEmpty();

}

package stack;

/**
 * A {@link LinkedStack} is a stack that is implemented using a Linked List structure
 * to allow for unbounded size.
 *
 * @param <T> the elements stored in the stack
 */
public class LinkedStack<T> implements StackInterface<T> {
    /**
     * The current `LinkedNode` to point to.
     */
    private LinkedNode<T> currentLinkedNode;

  /**
   * {@inheritDoc}.
   */
  @Override
  public T pop() throws StackUnderflowException {
        if (currentLinkedNode == null) {
            throw new StackUnderflowException("This stack is empty.");
        }

        T lastValue = top();
        currentLinkedNode = currentLinkedNode.getPreviousNode();
        return lastValue;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public T top() throws StackUnderflowException {
        if (currentLinkedNode == null) {
            throw new StackUnderflowException("This stack is empty.");
        }

        return currentLinkedNode.getValue();
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public boolean isEmpty() {
        return currentLinkedNode == null;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public int size() {
        int currentSize = 0;
        LinkedNode<T> currentLoopLinkedNode = currentLinkedNode;

        while (true) {
            if (currentLoopLinkedNode != null) {
                currentSize++;
                currentLoopLinkedNode
                    = currentLoopLinkedNode.getPreviousNode();
            } else {
                return currentSize;
            }
        }
  }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String toString() {
        String output = "";
        LinkedNode<T> currentLoopLinkedNode = currentLinkedNode;

        while (true) {
            if (currentLoopLinkedNode != null) {
                if (output.equals("")) {
                    output = currentLoopLinkedNode.getValue().toString();
                } else {
                    output += ", " + currentLoopLinkedNode.getValue().toString();
                }

                currentLoopLinkedNode = currentLoopLinkedNode.getPreviousNode();
            } else {
                return "[" + output + "]";
            }
        }
    }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void push(T elem) {
        currentLinkedNode = new LinkedNode(elem, currentLinkedNode);
  }

}

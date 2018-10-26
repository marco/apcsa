package evaluator;

/**
 * A {@link IllegalExpressionException} is thrown at runtime when an
 * invalid expression is encountered.
 *
 * @author jcollard, jddevaug, skunkmb
 *
 */
public class IllegalExpressionException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Initializes an `IllegalExpressionException`.
     */
    public IllegalExpressionException() {
        super();
    }

    /**
     * Initializes an `IllegalExpressionException`.
     *
     * @param message The message to use.
     */
    public IllegalExpressionException(String message) {
        super(message);
    }
}

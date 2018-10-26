package evaluator;

/**
 * A {@code InfixEvaluator} evaluates infix expressions.
 *
 * @author jcollard, jddevaug, skunkmb
 *
 * @param <T> the type of result to be evaluated
 */
public interface InfixEvaluator<T> {
    /**
     * Evaluates the infix expression and returns a value.
     *
     * @param expr The expression to be evaluate.
     * @return The value of evaluating expression.
     * @throws IllegalExpressionException if the expression is not a
     * valid infix expression.
     */
    public T evaluate(String expr);
}

package evaluator;

/**
 * A {@code PrefixEvaluator} evaluates prefix expressions.
 *
 * @author jcollard, jddevaug, skunkmb
 *
 * @param <T> the type of result to be evaluated
 */
public interface PrefixEvaluator<T> {
    /**
     * Evaluates the prefix expression and returns a value.
     *
     * @param expr The expression to be evaluate.
     * @return The value of evaluating expression.
     * @throws IllegalPrefixExpressionException if the expression is not a
     * valid expression.
     */
    public T evaluate(String expr);
}

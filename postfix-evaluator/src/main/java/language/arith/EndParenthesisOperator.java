package language.arith;

import language.NoOutputOperator;
import language.Operand;


/**
 * The {@code EndParenthesisOperator} is a parenthesis operator.
 *
 * @author jcollard, jddevaug, skunkmb
 */
public class EndParenthesisOperator extends NoOutputOperator<Integer> {
    /**
     * {@inheritDoc}.
     */
    @Override
    public Operand<Integer> performOperation() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPrecedence() {
        return 3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentation() {
        return ")";
    }
}

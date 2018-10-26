package language.arith;

import language.Operand;
import language.UnaryOperator;

/**
 * The {@code NegateOperator} is an operator that performs negation on a single
 * integer.
 *
 * @author jcollard, jddevaug
 *
 */
public class NegateOperator extends UnaryOperator<Integer> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Operand<Integer> performOperation() {
        Operand<Integer> operand = this.getOperand();

        if (operand == null) {
            throw new IllegalStateException(
                "Could not perform operation prior to operands being set."
            );
        }

        Integer result = operand.getValue() * -1;
        return new Operand<Integer>(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPrecedence() {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentation() {
        return "!";
    }
}

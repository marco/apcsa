package language.arith;

import language.Operand;
import language.UnaryOperator;

/**
 * The {@code SqrtOperator} is an operator that performs a square root (rounded
 * down) on a single integer.
 *
 * @author jcollard, jddevaug, skunkmb
 *
 */
public class SqrtOperator extends UnaryOperator<Integer> {
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

        Integer result = (int) Math.sqrt(operand.getValue());
        return new Operand<Integer>(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOperand(int i, Operand<Integer> operand) {
        if (operand.getValue() < 1) {
            throw new IllegalStateException(
                "The operator must be positive. Operator was: " + i
            );
        }

        super.setOperand(i, operand);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPrecedence() {
        return 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentation() {
        return "q";
    }
}

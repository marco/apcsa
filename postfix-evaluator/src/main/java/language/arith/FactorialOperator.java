package language.arith;

import language.Operand;
import language.UnaryOperator;

/**
 * The {@code FactorialOperator} is an operator that performs a factorial on a
 * single integer.
 *
 * @author jcollard, jddevaug, skunkmb
 *
 */
public class FactorialOperator extends UnaryOperator<Integer> {
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

        int result = 1;
        int value = operand.getValue();

        for (int i = value; 0 < i; i--) {
            result = result * i;
        }

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
        return "~";
    }
}

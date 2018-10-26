package language.arith;

import language.BinaryOperator;
import language.Operand;

/**
 * The {@code SubOperator} is an operator that performs subtraction on two
 * integers.
 * @author jcollard, jddevaug
 *
 */
public class SubOperator extends BinaryOperator<Integer> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Operand<Integer> performOperation() {
        Operand<Integer> op0 = this.getOp0();
        Operand<Integer> op1 = this.getOp1();

        if (op0 == null || op1 == null) {
            throw new IllegalStateException(
                "Could not perform operation prior to operands being set."
            );
        }

        Integer result = op0.getValue() - op1.getValue();
        return new Operand<Integer>(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPrecedence() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentation() {
        return "-";
    }
}

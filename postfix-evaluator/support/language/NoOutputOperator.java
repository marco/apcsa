package language;

/**
 * A {@link NoOutputOperator} is an {@link Operator} that does not perform an
 * operation.
 *
 * @author jcollard, jddevaug, skunkmb
 *
 * @param <T> they type of the {@link Operand} being evaluated
 */
public abstract class NoOutputOperator<T> implements Operator<T> {
    /**
     * {@inheritDoc}
     */
    @Override
    public final int getNumberOfArguments() {
        return 0;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void setOperand(int i, Operand<T> operand) {        
        throw new IllegalArgumentException(
            "This operator does not accept operands."
        );
    }

    /**
     * Returns the operand.
     *
     * @return The operand.
     */
    public Operand<T> getOperand() {
        return null;
    }
}

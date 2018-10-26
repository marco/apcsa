package language;

/**
 * A {@link UnaryOperator} is an {@link Operator} that performs an operation on
 * two arguments.
 *
 * @author jcollard, jddevaug, skunkmb
 *
 * @param <T> they type of the {@link Operand} being evaluated
 */
public abstract class UnaryOperator<T> implements Operator<T> {
    private Operand<T> operand;

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getNumberOfArguments() {
        return 1;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void setOperand(int i, Operand<T> operand) {
        if (operand == null) {
            throw new NullPointerException("Could not set null operand.");
        }

        if (i != 0) {
            throw new IllegalArgumentException(
                "Unary operator only accepts operand 0 but recieved " + i + "."
            );
        }

        if (this.operand != null) {
            throw new IllegalStateException("Position 0 has been previously set.");
        }

        this.operand = operand;
    }

    /**
     * Returns the operand.
     *
     * @return The operand.
     */
    public Operand<T> getOperand() {
        return operand;
    }
}

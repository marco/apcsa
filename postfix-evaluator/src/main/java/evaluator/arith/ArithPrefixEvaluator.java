package evaluator.arith;

import evaluator.IllegalExpressionException;
import evaluator.PrefixEvaluator;
import language.Operand;
import language.Operator;
import parser.arith.ArithPrefixParser;
import stack.LinkedStack;
import stack.StackInterface;

/**
 * An {@link ArithPrefixEvaluator} is a post fix evaluator over simple arithmetic expressions.
 *
 */
public class ArithPrefixEvaluator implements PrefixEvaluator<Integer> {
    /**
     * The current stack of operands.
     */
    private final StackInterface<Operand<Integer>> operandStack;

    /**
     * The current stack of operators.
     */
    private final StackInterface<Operator<Integer>> operatorStack;

    /**
     * Constructs an {@link ArithPrefixEvaluator}.
     */
    public ArithPrefixEvaluator() {
        operandStack = new LinkedStack<Operand<Integer>>();
        operatorStack = new LinkedStack<Operator<Integer>>();
    }

    /**
     * Evaluates a infix expression.
     *
     * @return the result
     */
    @Override
    public Integer evaluate(String expr) {
        ArithPrefixParser parser = new ArithPrefixParser(expr);

        while (parser.hasNext()) {
            switch (parser.nextType()) {
            case OPERAND:
                operandStack.push(parser.nextOperand());

                // If there are enough operands to justify an operation, do the
                // operation.
                if (
                    0 < operatorStack.size()
                        && operatorStack.top().getNumberOfArguments()
                            <= operandStack.size()
                ) {
                    popPerformAndPush();
                }
                break;
            case OPERATOR:
                Operator<Integer> nextOperator = parser.nextOperator();
                operatorStack.push(nextOperator);
                break;
            default:
                throw new IllegalExpressionException(
                    "Invalid expression: neither an operand nor operator was found."
                );
            }
        }

        // As long as there are still operators on the stack, do operations.
        while (operatorStack.size() != 0) {
            popPerformAndPush();
        }

        if (operandStack.size() != 1) {
            throw new IllegalExpressionException(
                "That infix expression is invalid."
            );
        }

        return operandStack.pop().getValue();
    }

    /**
     * Pops an operator and operands off of the current stack, performs the
     * operation, and pushes the result to the operand stack.
     */
    private void popPerformAndPush() {
        Operator<Integer> topOperator = operatorStack.top();
        if (topOperator.getNumberOfArguments() == 2) {
            topOperator.setOperand(1, operandStack.pop());
            topOperator.setOperand(0, operandStack.pop());
            operandStack.push(topOperator.performOperation());
            operatorStack.pop();
        } else {
            topOperator.setOperand(0, operandStack.pop());
            operandStack.push(topOperator.performOperation());
            operatorStack.pop();
        }
    }
}

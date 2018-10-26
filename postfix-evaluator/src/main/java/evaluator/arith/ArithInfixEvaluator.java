package evaluator.arith;

import evaluator.IllegalExpressionException;
import evaluator.InfixEvaluator;
import language.Operand;
import language.Operator;
import language.arith.EndParenthesisOperator;
import language.arith.StartParenthesisOperator;
import parser.arith.ArithInfixParser;
import stack.LinkedStack;
import stack.StackInterface;

/**
 * An {@link ArithInfixEvaluator} is a post fix evaluator over simple arithmetic expressions.
 *
 */
public class ArithInfixEvaluator implements InfixEvaluator<Integer> {
    /**
     * The current stack of operands.
     */
    private final StackInterface<Operand<Integer>> operandStack;

    /**
     * The current stack of operators.
     */
    private final StackInterface<Operator<Integer>> operatorStack;

    /**
     * Constructs an {@link ArithInfixEvaluator}.
     */
    public ArithInfixEvaluator() {
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
        ArithInfixParser parser = new ArithInfixParser(expr);

        // Based on instructions from
        // https://github.com/ChadwickCSA2018-2019/postfix-evaluator-skunkmb#part-two-write-an-arthinfixevaluatorclass.
        while (parser.hasNext()) {
            switch (parser.nextType()) {
            case OPERAND:
                operandStack.push(parser.nextOperand());
                break;
            case OPERATOR:
                Operator nextOperator = parser.nextOperator();

                if (nextOperator instanceof StartParenthesisOperator) {
                    ArithInfixEvaluator innerParenthesisEvaluator = new ArithInfixEvaluator();
                    String innerParenthesisString = getInnerParenthesisString(parser);
                    Integer innerValue = innerParenthesisEvaluator.evaluate(innerParenthesisString);
                    operandStack.push(new Operand(innerValue));
                    break;
                }

                // If the new operator has a higher precedence than the last
                // one, push it to the top of the stack. Otherwise, for as long
                // as it doesn't have the higher precedence, do operations on
                // the top operators, until eventually pushing the new
                // operator.
                if (
                    operatorStack.size() == 0
                        || operatorStack.top().getPrecedence()
                            < nextOperator.getPrecedence()
                ) {
                    operatorStack.push(nextOperator);
                } else {
                    while (
                        operatorStack.size() != 0
                            && nextOperator.getPrecedence()
                                <= operatorStack.top().getPrecedence()

                    ) {
                        popPerformAndPush();
                    }

                    operatorStack.push(nextOperator);
                }

                break;
            default:
                throw new IllegalExpressionException(
                    "Invalid expression: neither an operand nor operator was found. "
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
        Operator poppedOperator = operatorStack.pop();

        if (poppedOperator.getNumberOfArguments() == 2) {
            poppedOperator.setOperand(1, operandStack.pop());
            poppedOperator.setOperand(0, operandStack.pop());
            operandStack.push(poppedOperator.performOperation());
        } else {
            poppedOperator.setOperand(0, operandStack.pop());
            operandStack.push(poppedOperator.performOperation());
        }
    }

    /**
     * Gets the remaining parser string until the next end parenthesis.
     *
     * @param parser The parser to use
     * @return The remaining string inside of an already-started parenthesis
     * statement.
     */
    private String getInnerParenthesisString(ArithInfixParser parser) {
        String output = "";

        while (true) {
            if (!parser.hasNext()) {
                throw new IllegalExpressionException(
                    "A start parenthesis without an end parenthesis was found."
                );
            }

            switch (parser.nextType()) {
            case OPERAND:
                output += parser.nextOperand().getValue().toString() + " ";
                break;
            case OPERATOR:
                Operator nextOperator = parser.nextOperator();

                // This allows for nesting of parentheses. If this
                // condition was treated normally, nested parentheses
                // would return after the first end parenthesis.
                if (nextOperator instanceof StartParenthesisOperator) {
                    output += " ( " + getInnerParenthesisString(parser) + " ) ";
                    break;
                }

                if (nextOperator instanceof EndParenthesisOperator) {
                    return output;
                }

                output += nextOperator.getStringRepresentation() + " ";
                break;
            default:
                break;
            }
        }
    }
}

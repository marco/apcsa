package evaluator.arith;

import evaluator.IllegalExpressionException;
import evaluator.PostFixEvaluator;
import language.Operand;
import language.Operator;
import parser.arith.ArithPostFixParser;
import stack.LinkedStack;
import stack.StackInterface;

/**
 * An {@link ArithPostFixEvaluator} is a post fix evaluator over simple arithmetic expressions.
 *
 */
public class ArithPostFixEvaluator implements PostFixEvaluator<Integer> {
    /**
     * The current stack of operands.
     */
  private final StackInterface<Operand<Integer>> stack;

  /**
   * Constructs an {@link ArithPostFixEvaluator}.
   */
  public ArithPostFixEvaluator() {
        stack = new LinkedStack<Operand<Integer>>();
  }

  /**
   * Evaluates a postfix expression.
   * @return the result
   */
  @Override
  public Integer evaluate(String expr) {
    ArithPostFixParser parser = new ArithPostFixParser(expr);
    while (parser.hasNext()) {
      switch (parser.nextType()) {
        case OPERAND:
                stack.push(parser.nextOperand());
                break;
        case OPERATOR:
                Operator<Integer> operator = parser.nextOperator();
                if (operator.getNumberOfArguments() == 2) {
                    operator.setOperand(1, stack.pop());
                    operator.setOperand(0, stack.pop());
                    stack.push(operator.performOperation());
                } else if (operator.getNumberOfArguments() == 1) {
                    operator.setOperand(0, stack.pop());
                    stack.push(operator.performOperation());
                } else {
                    throw new IllegalExpressionException(
                        "Operator was not a correct type."
                    );
                }
                break;
        default:
                throw new IllegalExpressionException(
                    "Invalid expression: Neither an operand nor operator was found."
                );
      }
    }
        if (stack.size() != 1) {
            throw new IllegalExpressionException(
                "Invalid postfix expression."
            );
        }

        return stack.pop().getValue();
  }

}

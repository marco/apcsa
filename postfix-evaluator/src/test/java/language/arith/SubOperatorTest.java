package language.arith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import language.Operand;
import language.Operator;

public class SubOperatorTest {
  Operator<Integer> operator;
  Operand<Integer> op0;
  Operand<Integer> op1;

  /**
   * Runs before each test.
   */
  @Before
  public void setup() {
    operator = new SubOperator();
    op0 = new Operand<Integer>(5);
    op1 = new Operand<Integer>(7);
  }

  @Test (timeout = 5000)
  public void testPerformOperation() {
    operator.setOperand(0, op0);
    operator.setOperand(1, op1);

    Operand<Integer> result = operator.performOperation();
    int value = result.getValue();
    assertEquals("Operator applied to 5 and 7 should produce -2.", 5 - 7,  value);
  }

    @Test(timeout = 5000)
    public void testPerformOperationNegative() {
        operator.setOperand(0, new Operand<Integer>(-5));
        operator.setOperand(1, new Operand<Integer>(4));

        Operand<Integer> result = operator.performOperation();
        int value = result.getValue();
        assertEquals("Operator applied to -5 and 4 should produce -9.", -9,
                value);
    }

    @Test(timeout = 5000)
    public void testPerformOperationTwoNegatives() {
        operator.setOperand(0, new Operand<Integer>(-5));
        operator.setOperand(1, new Operand<Integer>(-4));

        Operand<Integer> result = operator.performOperation();
        int value = result.getValue();
        assertEquals("Operator applied to -5 and -4 should produce -1.", -1,
                value);
    }

  @Test (timeout = 5000, expected = IllegalArgumentException.class)
  public void testIllegalArgumentException() {
    operator.setOperand(2, op0);
    fail("Binary operator should not except input to position 2");
  }

  @Test (timeout = 5000, expected = NullPointerException.class)
  public void testNullArgumentException() {
    operator.setOperand(0, null);
    fail("Operator should not allow null arguments");
  }

  @Test (timeout = 5000, expected = IllegalStateException.class)
  public void testIllegalStateException() {
    operator.setOperand(0, op0);
    operator.setOperand(0, op0);

    fail("Operator should not allow position 0 to be set more than once");
  }

  @Test (timeout = 5000, expected = IllegalStateException.class)
  public void testIllegalStateExceptionPerform() {
    operator.performOperation();
    fail("Operator should not compute when all arguments have not been set.");
  }

}

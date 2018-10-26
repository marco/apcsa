package language.arith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import language.Operand;
import language.Operator;

public class FactorialOperatorTest {
    Operator<Integer> operator;
    Operand<Integer> op0;
    Operand<Integer> op1;

    /**
     * Runs before each test.
     */
    @Before
    public void setup() {
        operator = new FactorialOperator();
        op0 = new Operand<Integer>(4);
    }

    @Test(timeout = 5000)
    public void testPerformOperation() {
        operator.setOperand(0, op0);

        Operand<Integer> result = operator.performOperation();
        int value = result.getValue();
        assertEquals("Operator applied to 4 should produce 24.", 24, value);
    }

    @Test(timeout = 5000)
    public void testPerformOperationSmall() {
        operator.setOperand(0, new Operand<Integer>(1));

        Operand<Integer> result = operator.performOperation();
        int value = result.getValue();
        assertEquals("Operator applied to 1 should produce 1.", 1, value);
    }

    @Test(timeout = 5000)
    public void testGetNumberOfArguments() {
        assertEquals("Unary operator should have 1 argument.",
                operator.getNumberOfArguments(), 1);
    }

    @Test(timeout = 5000, expected = IllegalStateException.class)
    public void testIllegalStateException() {
        operator.setOperand(0, new Operand<Integer>(5));
        operator.setOperand(0, new Operand<Integer>(12));
        fail("Operator should not allow the same operand position to be set more than once");
    }

    @Test(timeout = 5000, expected = IllegalStateException.class)
    public void testIllegalStateExceptionPerform() {
        operator.performOperation();
        fail("Operator should not compute when all arguments have not been set.");
    }

    @Test(timeout = 5000, expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() {
        operator.setOperand(2, op0);
        fail("Unary operator should not except input to position 2");
    }

    @Test(timeout = 5000, expected = NullPointerException.class)
    public void testNullArgumentException() {
        operator.setOperand(0, null);
        fail("Operator should not allow null arguments");
    }

}

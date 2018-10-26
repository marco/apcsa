package language.arith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import language.Operand;
import language.Operator;

public class ExponentOperatorTest {
    Operator<Integer> operator;
    Operand<Integer> op0;
    Operand<Integer> op1;

    /**
     * Runs before each test.
     */
    @Before
    public void setup() {
        operator = new ExponentOperator();
        op0 = new Operand<Integer>(5);
        op1 = new Operand<Integer>(2);
    }

    @Test(timeout = 5000)
    public void testPerformOperation() {
        operator.setOperand(0, op0);
        operator.setOperand(1, op1);

        Operand<Integer> result = operator.performOperation();
        int value = result.getValue();
        assertEquals("Operator applied to 5 and 2 should produce 25.", 25,
                value);
    }

    @Test(timeout = 5000)
    public void testPerformOperationNegative() {
        operator.setOperand(0, new Operand<Integer>(13));
        operator.setOperand(1, new Operand<Integer>(1));

        Operand<Integer> result = operator.performOperation();
        int value = result.getValue();
        assertEquals("Operator applied to 13 and 1 should produce 13.", 13,
                value);
    }

    @Test(timeout = 5000)
    public void testPerformOperationTwoNegatives() {
        operator.setOperand(0, new Operand<Integer>(-2));
        operator.setOperand(1, new Operand<Integer>(3));

        Operand<Integer> result = operator.performOperation();
        int value = result.getValue();
        assertEquals("Operator applied to -2 and 3 should produce -8.", -8,
                value);
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

    @Test(timeout = 5000, expected = NullPointerException.class)
    public void testNullArgumentException() {
        operator.setOperand(0, null);
        fail("Operator should not allow null arguments");
    }



}

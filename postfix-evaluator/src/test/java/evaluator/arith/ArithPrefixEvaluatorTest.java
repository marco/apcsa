package evaluator.arith;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import evaluator.IllegalExpressionException;
import evaluator.PrefixEvaluator;

public class ArithPrefixEvaluatorTest {

    private PrefixEvaluator<Integer> evaluator;

    @Before
    public void setup() {
        evaluator = new ArithPrefixEvaluator();
    }

    @Test(timeout = 5000)
    public void testEvaluateSimple() {
        Integer result = evaluator.evaluate("1");
        assertEquals(new Integer(1), result);

        result = evaluator.evaluate("+ ^ * 3 4 2 5");
        assertEquals(new Integer(149), result);
    }

    @Test(timeout = 5000)
    public void testEvaluatePlus() {
        Integer result = evaluator.evaluate("+ 1 3");
        assertEquals(new Integer(4), result);

        result = evaluator.evaluate("+ + 1 2 3");
        assertEquals(new Integer(6), result);

        result = evaluator.evaluate("+ + + + 10000 1000 100 10 1");
        assertEquals(new Integer(11111), result);
    }

    @Test(timeout = 5000)
    public void testEvaluateSub() {
        Integer result = evaluator.evaluate("- 1 2");
        assertEquals(new Integer(-1), result);

        result = evaluator.evaluate("- - 1 2 3");
        assertEquals(new Integer(-4), result);

        result = evaluator.evaluate("- - - 1000 100 10 1");
        assertEquals(new Integer(889), result);
    }

    @Test(timeout = 5000)
    public void testEvaluateMult() {
        Integer result = evaluator.evaluate("* 1 2");
        assertEquals(new Integer(2), result);

        result = evaluator.evaluate("* * 1 2 3");
        assertEquals(new Integer(6), result);

        result = evaluator.evaluate("* * * 1 2 3 4");
        assertEquals(new Integer(24), result);
    }

    @Test(timeout = 5000)
    public void testEvaluateDiv() {
        Integer result = evaluator.evaluate("/ 4 2");
        assertEquals(new Integer(2), result);

        result = evaluator.evaluate("/ / 1 2 3");
        assertEquals(new Integer(0), result);

        result = evaluator.evaluate("/ / / 1 2 3 4");
        assertEquals(new Integer(0), result);
    }

    @Test(timeout = 5000)
    public void testEvaluateFactorial() {
        Integer result = evaluator.evaluate("$ 4");
        assertEquals(new Integer(24), result);

        result = evaluator.evaluate("$ 1");
        assertEquals(new Integer(1), result);

        result = evaluator.evaluate("* $ 2 $ 5");
        assertEquals(new Integer(240), result);
    }

    @Test(timeout = 5000)
    public void testEvaluateNegate() {
        Integer result = evaluator.evaluate("! 4");
        assertEquals(new Integer(-4), result);

        result = evaluator.evaluate("! 1");
        assertEquals(new Integer(-1), result);

        result = evaluator.evaluate("* ! 2 ! 5");
        assertEquals(new Integer(10), result);
    }

    @Test(timeout = 5000)
    public void testEvaluateSqrt() {
        Integer result = evaluator.evaluate("q 4");
        assertEquals(new Integer(2), result);

        result = evaluator.evaluate("q 1");
        assertEquals(new Integer(1), result);

        result = evaluator.evaluate("* q 4 q 16");
        assertEquals(new Integer(8), result);
    }

    @Test(timeout = 5000, expected = IllegalExpressionException.class)
    public void testInvalidExpression() {
        evaluator.evaluate("1 2");
    }


}

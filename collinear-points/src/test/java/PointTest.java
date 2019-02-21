import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;

public class PointTest {
  Point p5_7, p10_10, p1_1;
  @Before
  public void setUp() throws Exception {
    p5_7 = new Point(5,7);
    p10_10 = new Point(10,10);
    p1_1 = new Point(1,1);
  }

  @Test
  public void testSlopeToNormal() {
    assertEquals(0.6, p5_7.slopeTo(p10_10), 0.01);
  }

  @Test
  public void testSlopeToHorizontal() {
    Point p1_10 = new Point(1, 10);
    assertEquals(0.0, p1_10.slopeTo(p10_10), 0.00001);
  }

  @Test
  public void testSlopeToVertical() {
    Point p10_1 = new Point(10, 1);
    assertEquals(Double.POSITIVE_INFINITY, p10_1.slopeTo(p10_10), 0.0001);
  }

  @Test
  public void testSlopeToEqual() {
    Point p10_10_2 = new Point(10, 10);
    assertEquals(Double.NEGATIVE_INFINITY, p10_10_2.slopeTo(p10_10), 0.0001);
  }



  @Test
  public void testCompareToNegative() {
    assertTrue(p5_7.compareTo(p10_10) < 0);
  }

  @Test
  public void testCompareToNegativeHorizontal() {
    Point p1_10 = new Point(1, 10);
    assertTrue(p1_10.compareTo(p10_10) < 0);
  }

  @Test
  public void testCompareToNegativeVertical() {
    Point p10_1 = new Point(10, 1);
    assertTrue(p10_1.compareTo(p10_10) < 0);
  }

  @Test
  public void testCompareToEqual() {
    Point p10_10_2 = new Point(10, 10);
    assertTrue(p10_10_2.compareTo(p10_10) == 0);
  }

  @Test
  public void testCompareToPositive() {
    assertTrue(p10_10.compareTo(p1_1) > 0);
  }

  @Test
  public void testCompareToPositiveHorizontal() {
    Point p1_10 = new Point(1, 10);
    assertTrue(p10_10.compareTo(p1_10) > 0);
  }

  @Test
  public void testCompareToPositiveVertical() {
    Point p10_1 = new Point(10, 1);
    assertTrue(p10_10.compareTo(p10_1) > 0);
  }

  @Test
  public void testSlopeOrder() {
      Point p10_1 = new Point(10, 1);
      Comparator slopeOrder = p10_1.slopeOrder();
      assertTrue(slopeOrder.compare(new Point(10, 10), new Point(20,1)) > 0);
      assertTrue(slopeOrder.compare(new Point(25, 1), new Point(10, 20)) < 0);
      assertTrue(slopeOrder.compare(new Point(0, 1), new Point(20, 1)) == 0);
  }

  @Test(expected=IllegalArgumentException.class)
  public void testCompareNull() {
      Point p10_1 = new Point(10, 1);
      p10_1.compareTo(null);
  }

  @Test(expected=IllegalArgumentException.class)
  public void testSlopeToNull() {
      Point p10_1 = new Point(10, 1);
      p10_1.slopeTo(null);
  }

  @Test(expected=IllegalArgumentException.class)
  public void testCompareNullFirst() {
      Point p10_1 = new Point(10, 1);
      p10_1.slopeOrder().compare(null, new Point(1, 1));
  }

  @Test(expected=IllegalArgumentException.class)
  public void testCompareNullSecond() {
      Point p10_1 = new Point(10, 1);
      p10_1.slopeOrder().compare(new Point(1, 1), null);
  }
}


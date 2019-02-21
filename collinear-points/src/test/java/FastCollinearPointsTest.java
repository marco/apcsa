import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.In;

public class FastCollinearPointsTest {

  FastCollinearPoints fcp;
  @Before
  public void setUp() throws Exception {
    fcp = generateFCP("input10.txt");
  }

  private FastCollinearPoints generateFCP(String filename) {
    In in = new In("collinear-test-files/" + filename);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
    }
    return new FastCollinearPoints(points);
  }

  @Test
  public void testNumberOfSegments() {
    FastCollinearPoints collinearPoints = generateFCP("horizontal5.txt");
    assertTrue(5 == collinearPoints.numberOfSegments());
    collinearPoints = generateFCP("horizontal100.txt");
    assertTrue(100 == collinearPoints.numberOfSegments());
    collinearPoints = generateFCP("input10000.txt");
    assertTrue(35 == collinearPoints.numberOfSegments());
    collinearPoints = generateFCP("mystery10089.txt");
    assertTrue(34 == collinearPoints.numberOfSegments());
    collinearPoints = generateFCP("random152.txt");
    assertTrue(0 == collinearPoints.numberOfSegments());
  }

  @Test
  public void testSegments() {
      FastCollinearPoints collinearPoints = generateFCP("horizontal5.txt");
      assertTrue(collinearPoints.segments()[0].toString().equals("(4750, 4652) -> (16307, 4652)"));
      collinearPoints = generateFCP("horizontal100.txt");
      assertTrue(collinearPoints.segments()[0].toString().equals("(1090, 1279) -> (13975, 1279)"));
      collinearPoints = generateFCP("input10000.txt");
      assertTrue(collinearPoints.segments()[0].toString().equals("(12581, 659) -> (12581, 12555)"));
      collinearPoints = generateFCP("mystery10089.txt");
      assertTrue(collinearPoints.segments()[0].toString().equals("(26500, 9996) -> (26500, 13996)"));
      collinearPoints = generateFCP("random152.txt");
      assertTrue(collinearPoints.segments().length == 0);
  }


  @Test(expected=IllegalArgumentException.class)
  public void testNullPoints() {
      BruteCollinearPoints collinearPoints = new BruteCollinearPoints(null);
  }

  @Test(expected=IllegalArgumentException.class)
  public void testNullSinglePoint() {
      BruteCollinearPoints collinearPoints = new BruteCollinearPoints(new Point[] {new Point(1, 2), null});
  }

  @Test(expected=IllegalArgumentException.class)
  public void testDuplicatePoints() {
      BruteCollinearPoints collinearPoints = new BruteCollinearPoints(new Point[] {new Point(1, 2), new Point(1, 2)});
  }
}

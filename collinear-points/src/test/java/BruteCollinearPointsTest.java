import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.In;

public class BruteCollinearPointsTest {

    BruteCollinearPoints bcp;
    @Before
    public void setUp() throws Exception {
        bcp = generateBCP("input10.txt");
    }

    private BruteCollinearPoints generateBCP(String filename) {
        In in = new In("collinear-test-files/" + filename);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        return new BruteCollinearPoints(points);
    }

    @Test
    public void testNumberOfSegments() {
        BruteCollinearPoints collinearPoints = generateBCP("horizontal5.txt");
        assertTrue(5 == collinearPoints.numberOfSegments());
    }

    @Test
    public void testSegments() {
        BruteCollinearPoints collinearPoints = generateBCP("horizontal5.txt");
        assertTrue(collinearPoints.segments()[0].toString().equals("(4750, 4652) -> (16307, 4652)"));
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSetTest {
    private static final int INSERT_AMOUNT = 100;
    private static final int RANGE_AMOUNT = 100;
    private static final int NEAREST_AMOUNT = 100;

    private PointSET generateTree(String filename) {
        In in = new In("kdtree-test-files/" + filename);
        PointSET tree = new PointSET();

        while (!in.isEmpty()) {
            double pointX = in.readDouble();
            double pointY = in.readDouble();
            tree.insert(new Point2D(pointX, pointY));
        }

        return tree;
    }

    @Test
    public void testIsEmpty() {
        PointSET points = new PointSET();
        assertTrue(points.isEmpty());
        points.insert(new Point2D(0.4, 0.5));
        assertFalse(points.isEmpty());
    }

    @Test
    public void testSize() {
        PointSET points = new PointSET();
        assertEquals(points.size(), 0);
        points.insert(new Point2D(0.4, 0.5));
        assertEquals(points.size(), 1);
        points.insert(new Point2D(0.2, 0.5));
        points.insert(new Point2D(0.3, 0.5));
        assertEquals(points.size(), 3);
    }

    @Test(timeout = 100)
    public void testInsertTiming() {
        PointSET points = new PointSET();

        // It shouldn't time out when running a lot of insertions.
        for (int i = 0; i < INSERT_AMOUNT; i++) {
            points.insert(new Point2D((double) i / INSERT_AMOUNT, 1 - i / INSERT_AMOUNT));
        }

        assertEquals(points.size(), INSERT_AMOUNT);
    }

    @Test(timeout = 100000)
    public void testAllFiles() {
        File folder = new File("kdtree-test-files");
        for (File file : folder.listFiles()) {
            String extension = file.getName().substring(file.getName().lastIndexOf('.') + 1);
            if (file.isFile() && extension.equals("txt")) {
                String name = file.getName().substring(0, file.getName().lastIndexOf('.'));
                System.out.println("Testing: " + name);
                int correctSize;

                if (name.startsWith("circle")) {
                    if (name.endsWith("k")) {
                        // `circle10k` has a size of 11, not 10,000.
                        correctSize = 11;
                    } else {
                        correctSize = Integer.parseInt(name.substring(6, name.length()));
                    }
                } else if (name.startsWith("horizontal")) {
                    correctSize = Integer.parseInt(name.substring(10, name.length()));
                } else if (name.startsWith("input")) {
                    if (name.endsWith("K")) {
                        correctSize = Integer.parseInt(name.substring(5, name.length() - 1)) * 1000;
                    } else if (name.endsWith("M")) {
                        correctSize = Integer.parseInt(name.substring(5, name.length() - 1)) * 1000000;
                    } else {
                        correctSize = Integer.parseInt(name.substring(5, name.length()));
                    }
                } else if (name.startsWith("vertical")) {
                    correctSize = Integer.parseInt(name.substring(8, name.length()));
                } else {
                    continue;
                }

                PointSET tree = generateTree(file.getName());
                assertEquals(tree.size(), correctSize);
                assertNotNull(tree.nearest(new Point2D(0.5, 0.5)));
                assertNotNull(tree.range(new RectHV(0.2, 0.5, 0.3, 0.8)));
            }
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testInsertNull() {
        PointSET points = new PointSET();
        points.insert(null);
    }

    @Test
    public void testContains() {
        Point2D pointA = new Point2D(0.4, 0.5);
        Point2D pointB = new Point2D(0.1, 0.4);
        Point2D pointC = new Point2D(0.9, 0.6);

        PointSET points = new PointSET();
        assertFalse(points.contains(pointA));
        points.insert(pointA);
        assertTrue(points.contains(pointA));
        points.insert(pointB);
        assertTrue(points.contains(pointB));
        assertFalse(points.contains(pointC));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testContainsNull() {
        PointSET points = new PointSET();
        points.insert(new Point2D(0.4, 0.5));
        points.contains(null);
    }

    @Test
    public void testRange() {
        Point2D pointA = new Point2D(0.4, 0.5);
        Point2D pointB = new Point2D(0.1, 0.4);
        Point2D pointC = new Point2D(0.9, 0.6);

        PointSET points = new PointSET();
        points.insert(pointA);
        points.insert(pointB);
        points.insert(pointC);
        assertTrue(points.range(new RectHV(0, 0.49, 0.41, 0.51)).iterator().next() == pointA);
        assertTrue(points.range(new RectHV(0, 0.39, 0.11, 0.41)).iterator().next() == pointB);
        assertTrue(points.range(new RectHV(0, 0.55, 0.91, 0.65)).iterator().next() == pointC);
    }

    @Test(timeout = 100)
    public void testRangeTiming() {
        Point2D pointA = new Point2D(0.4, 0.5);
        PointSET points = new PointSET();
        points.insert(pointA);

        // It shouldn't time out when running a lot of insertions.
        for (int i = 0; i < RANGE_AMOUNT; i++) {
            assertTrue(points.range(new RectHV(0, 0.49, 0.41, 0.51)).iterator().next() == pointA);
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testRangeNull() {
        PointSET points = new PointSET();
        points.insert(new Point2D(0.4, 0.5));
        points.range(null);
    }

    @Test
    public void testNearest() {
        Point2D pointA = new Point2D(0.4, 0.5);
        Point2D pointB = new Point2D(0.1, 0.4);
        Point2D pointC = new Point2D(0.9, 0.6);

        PointSET points = new PointSET();
        points.insert(pointA);
        points.insert(pointB);
        points.insert(pointC);
        assertTrue(points.nearest(new Point2D(0.11, 0.41)) == pointB);
    }

    @Test
    public void testNearestComplex() {
        Point2D pointA = new Point2D(0.7, 0.2);
        Point2D pointB = new Point2D(0.5, 0.4);
        Point2D pointC = new Point2D(0.2, 0.3);
        Point2D pointD = new Point2D(0.4, 0.7);
        Point2D pointE = new Point2D(0.9, 0.6);

        PointSET points = new PointSET();
        points.insert(pointA);
        points.insert(pointB);
        points.insert(pointC);
        points.insert(pointD);
        points.insert(pointE);
        System.out.println(points.nearest(new Point2D(0.57, 0.316)));
        assertTrue(points.nearest(new Point2D(0.57, 0.316)) == pointB);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNearestNull() {
        PointSET points = new PointSET();
        points.insert(new Point2D(0.4, 0.5));
        points.nearest(null);
    }

    @Test(timeout = 100)
    public void testNearestTiming() {
        Point2D pointA = new Point2D(0.4, 0.5);
        PointSET points = new PointSET();
        points.insert(pointA);

        // It shouldn't time out when running a lot of insertions.
        for (int i = 0; i < NEAREST_AMOUNT; i++) {
            assertTrue(points.nearest(new Point2D(0.49, 0.51)) == pointA);
        }
    }
}

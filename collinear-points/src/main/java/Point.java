import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

/**
 * A point that contains X and Y coordinates.
 *
 * @author skunkmb
 */
public class Point implements Comparable<Point> {
    /**
     * The X coordinate of the point.
     */
    private final int x;

    /**
     * The Y coordinate of the point.
     */
    private final int y;

    /**
     * Constructs a `Point`.
     *
     * @param x The X coordinate of the point.
     * @param y The Y coordinate of the point.
     */
    public Point(int x, int y) {
        this.y = y;
        this.x = x;
    }

    /**
     * Draws this point with `StdDraw`.
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and another point
     * with `StdDraw`.
     *
     * @param otherPoint The other point to compare to.
     */
    public void drawTo(Point otherPoint) {
        if (otherPoint == null) {
            throw new IllegalArgumentException("The other point cannot be null");
        }

        StdDraw.line(this.x, this.y, otherPoint.x, otherPoint.y);
    }

    /**
     * Returns the slope between this point and another point.
     * Specifically, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). If the line segment is vertical, the
     * slope is `Double.POSITIVE_INFINITY`, and it is
     * `Double.NEGATIVE_INFINITY` if the points are equal.
     *
     * @param otherPoint The other point to compare to.
     * @return The slope between this point and the other point.
     */
    public double slopeTo(Point otherPoint) {
        if (otherPoint == null) {
            throw new IllegalArgumentException("The other point cannot be null");
        }

        if (x == otherPoint.x && y == otherPoint.y) {
            return Double.NEGATIVE_INFINITY;
        } else if (x == otherPoint.x) {
            return Double.POSITIVE_INFINITY;
        }  else if (y == otherPoint.y) {
            return 0;
        }

        return (double) (otherPoint.y - y) / (otherPoint.x - x);
    }

    /**
     * Compares two points by their Y coordinates, breaking a tie by
     * their X coordinates. Specifically, this point (x0, y0) is less
     * than the other point (x1, y1) if y0 < y1 or y0 = y1 and x0 < x1.
     *
     * @param otherPoint The other point to compare to.
     * @return 0 if this point is equal to the argument point,
     *         a negative integer if this point is less,
     *         and a positive integer if this point is greater.
     */
    public int compareTo(Point otherPoint) {
        if (otherPoint == null) {
            throw new IllegalArgumentException("The other point cannot be null");
        }

        if (y == otherPoint.y) {
            return x - otherPoint.x;
        }

        return y - otherPoint.y;
    }

    /**
     * Compares two points based on the slope that they make with this
     * point.
     *
     * @return A `PointComparator` that compares the points.
     */
    public Comparator<Point> slopeOrder() {
        return new PointComparator(this);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * A `Comparator` that compares the slopes of two other points
     * relative to this point.
     *
     * @author skunkmb
     */
    private class PointComparator implements Comparator<Point> {
        /**
         * The point to compare slopes to.
         */
        private Point point;

        /**
         * Constructs a `PointComparator`.
         *
         * @param point The point to compare slopes to.
         */
        public PointComparator(Point point) {
            if (point == null) {
                throw new IllegalArgumentException("The point cannot be null");
            }

            this.point = point;
        }

        @Override
        public int compare(Point point1, Point point2) {
            if (point1 == null) {
                throw new IllegalArgumentException("The first point cannot be null");
            }

            if (point2 == null) {
                throw new IllegalArgumentException("The second point cannot be null");
            }

            return Double.compare(point.slopeTo(point1), point.slopeTo(point2));
        }
    }
}

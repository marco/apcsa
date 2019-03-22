import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A set of points, which supports insertion and searching.
 *
 * @author skunkmb
 */
public class PointSET {
    /**
     * The pen size when drawing the board.
     */
    private static final double PEN_SIZE = 0.01;

    /**
     * The points in this set.
     */
    private SET<Point2D> points = new SET<Point2D>();

    /**
     * Constructs an empty `PointSET`.
     */
    public PointSET() {
    }

    /**
     * Returns whether or not this set it empty.
     * @return Whether or not this set it empty.
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    /**
     * Returns the number of points in the set.
     * @return The size of the set.
     */
    public int size() {
        return points.size();
    }

    /**
     * Inserts a point into the set, or throws an exception if the
     * point is `null`. If the point is already used in the set, it will
     * not be added.
     *
     * @param point The point to add.
     */
    public void insert(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException("The inserted point cannot be null.");
        }

        points.add(point);
    }

    /**
     * Returns whether or not a point is found in the set.
     *
     * @param point The point to search for.
     * @return Whether or not the point exists.
     */
    public boolean contains(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException("The point to search for cannot be null.");
        }

        return points.contains(point);
    }

    /**
     * Draws the set to `StdDraw`.
     */
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(PEN_SIZE);

        for (Point2D point : points) {
            point.draw();
        }
    }

    /**
     * Returns all of the points within a certain range.
     *
     * @param rectangle The bounding rectangle for points to find. If
     * the rectangle borders a point, it is included.
     * @return The points within the bounds.
     */
    public Iterable<Point2D> range(RectHV rectangle) {
        if (rectangle == null) {
            throw new IllegalArgumentException("The rectangle cannot be null.");
        }

        Iterator<Point2D> pointsIterator = points.iterator();
        List<Point2D> currentPointsInRange = new ArrayList<Point2D>();

        while (pointsIterator.hasNext()) {
            Point2D nextPoint = pointsIterator.next();
            if (rectangle.contains(nextPoint)) {
                currentPointsInRange.add(nextPoint);
            }
        }

        return currentPointsInRange;
    }

    /**
     * Returns the nearest point in the set to a given point.
     *
     * @param point The point to search for.
     * @return The closest point in the set.
     */
    public Point2D nearest(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException("The point to search for cannot be null.");
        }

        double smallestDistance = Double.POSITIVE_INFINITY;
        Point2D closestPoint = null;

        for (Point2D nextPoint : points) {
            double distance = nextPoint.distanceSquaredTo(point);
            if (distance < smallestDistance) {
                closestPoint = nextPoint;
                smallestDistance = distance;
            }
        }

        return closestPoint;
    }
}



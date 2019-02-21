import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A collinear points checker that can brute-search through lines
 * of 4 points.
 *
 * @author skunkmb
 */
public class BruteCollinearPoints {
    /**
     * The line segments found in the points.
     */
    private LineSegment[] lineSegments;

    /**
     * Constructs a `BruteCollinearPoints` instance and searches for
     * line segments.
     *
     * @param points The points to search through.
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Points cannot be null.");
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("Point at index " + i + " was null.");
            }
        }

        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);

        for (int i = 0; i < pointsCopy.length - 1; i++) {
            if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate points at " + pointsCopy[i].toString());
            }
        }

        List<LineSegment> segments = new ArrayList<LineSegment>();

        for (int i = 0; i < pointsCopy.length - 3; i++) {
            for (int j = i + 1; j < pointsCopy.length - 2; j++) {
                for (int k = j + 1; k < pointsCopy.length - 1; k++) {
                    for (int m = k + 1; m < pointsCopy.length; m++) {
                        Point pointP = pointsCopy[i];
                        Point pointQ = pointsCopy[j];
                        Point pointR = pointsCopy[k];
                        Point pointS = pointsCopy[m];

                        double pqSlope = pointP.slopeTo(pointQ);
                        double prSlope = pointP.slopeTo(pointR);
                        double psSlope = pointP.slopeTo(pointS);

                        if (
                            pqSlope == Double.NEGATIVE_INFINITY
                                || prSlope == Double.NEGATIVE_INFINITY
                                || psSlope == Double.NEGATIVE_INFINITY
                        ) {
                            throw new IllegalArgumentException("Duplicate points are not allowed.");
                        }


                        if (pqSlope == prSlope && prSlope == psSlope) {
                            Point[] linePoints = new Point[] {pointP, pointQ, pointR, pointS};
                            Arrays.sort(linePoints);
                            segments.add(new LineSegment(linePoints[0], linePoints[3]));
                        }
                    }
                }
            }
        }

        lineSegments = segments.toArray(new LineSegment[0]);
    }

    /**
     * Returns the number of line segments found.
     *
     * @return The number of line segments.
     */
    public int numberOfSegments() {
        return lineSegments.length;
    }

    /**
     * Returns the line segments found.
     *
     * @return The line segments.
     */
    public LineSegment[] segments() {
        return lineSegments.clone();
    }
}

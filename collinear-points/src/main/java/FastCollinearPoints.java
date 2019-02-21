import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A collinear points checker that can efficiently search through lines
 * segments made from more than 4 points.
 *
 * @author skunkmb
 */
public class FastCollinearPoints {
    /**
     * The line segments found in the points.
     */
    private LineSegment[] lineSegments;

    /**
     * Constructs a `FastCollinearPoints` instance and searches for
     * line segments.
     *
     * @param points The points to search through.
     */
    public FastCollinearPoints(Point[] points) {
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

        List<LineSegment> currentLineSegments = new ArrayList<LineSegment>();

        for (int i = 0; i < pointsCopy.length; i++) {
            Point[] pointsBySlope = pointsCopy.clone();
            Arrays.sort(pointsBySlope, pointsCopy[i].slopeOrder());

            int j = 1;
            while (j < pointsCopy.length) {
                List<Point> linePoints = new ArrayList<Point>();
                double newSlope = pointsCopy[i].slopeTo(pointsBySlope[j]);

                while (j < pointsCopy.length && newSlope == pointsCopy[i].slopeTo(pointsBySlope[j])) {
                    linePoints.add(pointsBySlope[j]);
                    j++;
                }

                if (linePoints.size() >= 3) {
                    linePoints.add(pointsCopy[i]);
                    Point[] naturalPointsArray = linePoints.toArray(new Point[0]);
                    Arrays.sort(naturalPointsArray);

                    if (naturalPointsArray[0] == pointsCopy[i]) {
                        currentLineSegments.add(new LineSegment(
                            naturalPointsArray[0],
                            naturalPointsArray[naturalPointsArray.length - 1]
                        ));
                    }
                }
            }
        }

        lineSegments = currentLineSegments.toArray(new LineSegment[0]);
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

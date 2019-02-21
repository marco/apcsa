/**
 * A line segment that connects two `Point`s.
 *
 * @author skunkmb
 */
public class LineSegment {
    /**
     * The first endpoint of the line.
     */
    private final Point p;

    /**
     * The second endpoint of the line.
     */
    private final Point q;

    /**
     * Initializes a new line segment.
     *
     * @param p The first endpoint of the line.
     * @param q The second endpoint of the line.
     */
    public LineSegment(Point p, Point q) {
        if (p == null || q == null) {
            throw new NullPointerException("argument is null");
        }

        this.p = p;
        this.q = q;
    }


    /**
     * Draws this line segment to standard draw.
     */
    public void draw() {
        p.drawTo(q);
    }

    /**
     * Returns a string representation of this line segment.
     *
     * @return A string representation of this line segment.
     */
    public String toString() {
        return p + " -> " + q;
    }

    /**
     * Throws an exception if called, since equality testing has not been
     * implemented.
     *
     * @return None.
     * @throws UnsupportedOperationException if called.
     */
    @Override
    public boolean equals(Object other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Throws an exception if called, since hashing has not been
     * implemented.
     *
     * @return None.
     * @throws UnsupportedOperationException if called.
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }
}

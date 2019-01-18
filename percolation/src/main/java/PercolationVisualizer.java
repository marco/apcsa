/******************************************************************************
 *  Compilation:  javac PercolationVisualizer.java
 *  Execution:    java PercolationVisualizer input.txt
 *  Dependencies: Percolation.java
 *
 *  This program takes the name of a file as a command-line argument.
 *  From that file, it
 *
 *    - Reads the grid size n of the percolation system.
 *    - Creates an n-by-n grid of sites (intially all blocked)
 *    - Reads in a sequence of sites (row i, column j) to open.
 *
 *  After each site is opened, it draws full sites in light blue,
 *  open sites (that aren't full) in white, and blocked sites in black,
 *  with with site (1, 1) in the upper left-hand corner.
 *
 ******************************************************************************/

import java.awt.Font;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A visualizer that can open and display a percolation text file.
 */
public class PercolationVisualizer {
    /**
     * The delay between drawing sites when opening a file.
     */
    private static final int DELAY = 0;

    /**
     * The minimum scale value.
     */
    private static final double MIN_SCALE = -0.05;

    /**
     * The maximum scale value.
     */
    private static final double MAX_SCALE = 1.05;

    /**
     * The x coordinate for the number of open sites text.
     */
    private static final double OPEN_TEXT_X = 0.25;

    /**
     * The x coordinate for the percolation text.
     */
    private static final double PERCOLATES_TEXT_X = 0.75;

    /**
     * The y coordinate for the text.
     */
    private static final double TEXT_Y = -0.025;

    /**
     * The font size for the text.
     */
    private static final int TEXT_SIZE = 12;

    /**
     * The size of a site square.
     */
    private static final double SQUARE_SIZE = 0.45;

    /**
     * Draws a percolation grid using `StdDraw`.
     *
     * @param perc The percolation grid to draw.
     * @param n The size of the grid.
     */
    public static void draw(Percolation perc, int n) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(MIN_SCALE * n, MAX_SCALE * n);
        StdDraw.setYscale(MIN_SCALE * n, MAX_SCALE * n);   // leave a border to write text
        StdDraw.filledSquare(n / 2.0, n / 2.0, n / 2.0);

        // draw n-by-n grid
        int opened = 0;
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                if (perc.isFull(row, col)) {
                    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                    opened++;
                } else if (perc.isOpen(row, col)) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                    opened++;
                } else {
                    StdDraw.setPenColor(StdDraw.BLACK);
                }
                StdDraw.filledSquare(col - 0.5, n - row + 0.5, SQUARE_SIZE);
            }
        }

        // write status text
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, TEXT_SIZE));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(OPEN_TEXT_X * n, TEXT_Y * n, opened + " open sites");
        if (perc.percolates()) StdDraw.text(PERCOLATES_TEXT_X * n, TEXT_Y * n, "percolates");
        else                   StdDraw.text(PERCOLATES_TEXT_X * n, TEXT_Y * n, "does not percolate");
    }

    /**
     * Starts the percolation visualizer and draws open sites.
     *
     * @param args Command line arguments, which are ignored.
     */
    public static void main(String[] args) {
        In in = new In("percolation-test-files/student.txt");      // input file
        int n = in.readInt();         // n-by-n percolation system
        // turn on animation mode
        StdDraw.enableDoubleBuffering();

        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(n);
        draw(perc, n);
        StdDraw.show();
        StdDraw.pause(DELAY);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
            draw(perc, n);
            StdDraw.show();
            StdDraw.pause(DELAY);
        }
    }
}

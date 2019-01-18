/******************************************************************************
 *  Compilation:  javac InteractivePercolationVisualizer.java
 *  Execution:    java InteractivePercolationVisualizer n
 *  Dependencies: PercolationVisualizer.java Percolation.java
 *                StdDraw.java StdOut.java
 *
 *  This program takes the grid size n as a command-line argument.
 *  Then, the user repeatedly clicks sites to open with the mouse.
 *  After each site is opened, it draws full sites in light blue,
 *  open sites (that aren't full) in white, and blocked sites in black.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * An application that can be used to interactively open grids to
 * visualise percolation.
 */
public class InteractivePercolationVisualizer {
    /**
     * The default size of the percolation grid.
     */
    private static final int DEFAULT_SIZE = 10;

    /**
     * The amount of time to pause when drawing the grid.
     */
    private static final int DRAW_PAUSE = 20;

    /**
     * Opens the interactive visualizer with a default size of 10.
     *
     * @param args Command line arguments. If one is given, it is used
     * as the visualizer size.
     */
    public static void main(String[] args) {
        // n-by-n percolation system (read from command-line, default = 10)
        int n = DEFAULT_SIZE;
        if (args.length == 1) n = Integer.parseInt(args[0]);

        // repeatedly open site specified my mouse click and draw resulting system
        StdOut.println(n);

        StdDraw.enableDoubleBuffering();
        Percolation perc = new Percolation(n);
        PercolationVisualizer.draw(perc, n);
        StdDraw.show();

        while (true) {
            // detected mouse click
            if (StdDraw.mousePressed()) {

                // screen coordinates
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();

                // convert to row i, column j
                int i = (int) (n - Math.floor(y));
                int j = (int) (1 + Math.floor(x));

                // open site (i, j) provided it's in bounds
                if (i >= 1 && i <= n && j >= 1 && j <= n) {
                    if (!perc.isOpen(i, j)) {
                        StdOut.println(i + " " + j);
                    }
                    perc.open(i, j);
                }

                // draw n-by-n percolation system
                PercolationVisualizer.draw(perc, n);
                StdDraw.show();
            }

            StdDraw.pause(DRAW_PAUSE);
        }
    }
}

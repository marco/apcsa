import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * A utility class that can be used to find statistics for various
 * percolation grids.
 *
 * @author skunkmb
 */
public class PercolationStats {
    /**
     * A value to multiply the standard deviation value by in order to
     * find the confidence values.
     */
    private static final double CONFIDENCE_MULTIPLIER = 1.96;

    /**
     * The current number of open sites for each percolation grid being
     * tested.
     */
    private double[] openSiteAmounts;

    /**
     * The calculated mean value. This is equal to the average of the
     * percent of the grid filled in order for it to percolate.
     */
    private double calculatedMean;

    /**
     * The calculated standard deviation of the calculated mean values.
     */
    private double calculatedStandardDeviation;

    /**
     * The calculated confidence low point of the calculated mean
     * values.
     */
    private double calculatedConfidenceLow;

    /**
     * The calculated confidence high point of the calculated mean
     * values.
     */
    private double calculatedConfidenceHigh;

    /**
     * Constructs a `PercolationStats` and runs trials.
     *
     * @param n The width and height of each
     * @param trialAmount The number of trials to run.
     */
    public PercolationStats(int n, int trialAmount) {
        if (n < 1) {
            throw new IllegalArgumentException(
                "The grid size cannot be less than 1."
            );
        }

        if (trialAmount < 1) {
            throw new IllegalArgumentException(
                "The trial amount cannot be less than 1."
            );
        }

        openSiteAmounts = new double[trialAmount];
        int totalSiteAmount = n * n;

        for (int i = 0; i < trialAmount; i++) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
            }

            openSiteAmounts[i] = (double) percolation.numberOfOpenSites() / totalSiteAmount;
        }

        calculatedMean = StdStats.mean(openSiteAmounts);
        calculatedStandardDeviation = StdStats.stddev(openSiteAmounts);
        calculatedConfidenceLow = (
            calculatedMean - CONFIDENCE_MULTIPLIER
                * calculatedStandardDeviation
                / Math.sqrt(openSiteAmounts.length)
        );
        calculatedConfidenceHigh = (
            calculatedMean + CONFIDENCE_MULTIPLIER
                * calculatedStandardDeviation
                / Math.sqrt(openSiteAmounts.length)
        );
    }

    /**
     * Returns the calculated mean value. This is equal to the average
     * of the percent of the grid filled in order for it to percolate.
     *
     * @return The calculated mean value.
     */
    public double mean() {
        return calculatedMean;
    }

    /**
     * Returns the calculated standard deviation of the calculated mean
     * values.
     *
     * @return The standard deviation.
     */
    public double stddev() {
        return calculatedStandardDeviation;
    }

    /**
     * Returns the calculated confidence low point of the calculated mean
     * values.
     *
     * @return The low point.
     */
    public double confidenceLo() {
        return calculatedConfidenceLow;
    }

    /**
     * Returns the calculated confidence high point of the calculated mean
     * values.
     *
     * @return The high point.
     */
    public double confidenceHi() {
        return calculatedConfidenceHigh;
    }

    /**
     * Runs trials, given arguments for the grid size and trial amount.
     * Outputs to the console when compute.
     *
     * @param args The arguments to use. Argument 0 is the grid size to
     * use and argument 1 is the trial amount.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(
                "Two arguments are required (grid size and trial amount)"
            );
        }

        int gridSizeArgument = Integer.parseInt(args[0]);
        int trialAmountArgument = Integer.parseInt(args[1]);

        System.out.println();
        System.out.println("grid size\t\t\t\t" + gridSizeArgument);
        System.out.println("trial amount\t\t\t" + trialAmountArgument);
        System.out.println();

        PercolationStats stats = new PercolationStats(gridSizeArgument, trialAmountArgument);
        System.out.println("mean\t\t\t\t" + stats.mean());
        System.out.println("stddev\t\t\t\t" + stats.stddev());
        System.out.println("95% confidence interval\t\t[" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}

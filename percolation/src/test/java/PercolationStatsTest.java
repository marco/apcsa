import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PercolationStatsTest {
    private static final int CREATION_ITERATION_AMOUNT = 15;
    private static final int ITERATION_AMOUNT = 100;
    private static final int TRIAL_AMOUNT = 10;
    private static final int GRID_SIZE = 500;

  @Test(timeout = 5000)
  public void testTimeoutCreationForBigGrids() {
      for (int i = 1; i < CREATION_ITERATION_AMOUNT; i++) {
          PercolationStats stats = new PercolationStats(GRID_SIZE, i);
      }
  }

  @Test(timeout = 5000)
  public void checkNoTimeoutMean() {
      PercolationStats stats = new PercolationStats(GRID_SIZE, TRIAL_AMOUNT);

      for (int i = 1; i < ITERATION_AMOUNT; i++) {
          stats.mean();
      }
  }

  @Test(timeout = 5000)
  public void checkNoTimeoutStdDev() {
      PercolationStats stats = new PercolationStats(GRID_SIZE, TRIAL_AMOUNT);

      for (int i = 1; i < ITERATION_AMOUNT; i++) {
          stats.stddev();
      }
  }

  @Test(timeout = 5000)
  public void checkNoTimeoutConfidence() {
      PercolationStats stats = new PercolationStats(GRID_SIZE, TRIAL_AMOUNT);

      for (int i = 1; i < ITERATION_AMOUNT; i++) {
          stats.confidenceHi();
          stats.confidenceLo();
      }
  }

  @Test
  public void checkReasonableMean() {
      PercolationStats stats = new PercolationStats(GRID_SIZE, TRIAL_AMOUNT);
      assertTrue(0 < stats.mean() && stats.mean() <= 1);
  }

  @Test
  public void checkMeanWithinRange() {
      PercolationStats stats = new PercolationStats(GRID_SIZE, TRIAL_AMOUNT);
      assertTrue(0.59 < stats.mean() && stats.mean() < 0.6);
  }

  @Test
  public void checkReasonableStdDev() {
      PercolationStats stats = new PercolationStats(GRID_SIZE, TRIAL_AMOUNT);
      assertTrue(0 < stats.stddev() && stats.stddev() <= 1);
  }

  @Test
  public void checkConsistentConfidenceHigh() {
      PercolationStats stats = new PercolationStats(GRID_SIZE, TRIAL_AMOUNT);
      double meanDeviation = stats.mean() - stats.stddev();
      assertTrue(Math.abs(meanDeviation - stats.confidenceLo()) < 0.01);
  }

  @Test
  public void checkConsistentConfidenceLow() {
      PercolationStats stats = new PercolationStats(GRID_SIZE, TRIAL_AMOUNT);
      double meanDeviation = stats.mean() + stats.stddev();
      assertTrue(Math.abs(meanDeviation - stats.confidenceHi()) < 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testErrorNegativeSize() {
      PercolationStats stats = new PercolationStats(-10, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testErrorNegativeTrials() {
      PercolationStats stats = new PercolationStats(10, -10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testErrorZeroSize() {
      PercolationStats stats = new PercolationStats(0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testErrorZeroTrials() {
      PercolationStats stats = new PercolationStats(10, 0);
  }
}

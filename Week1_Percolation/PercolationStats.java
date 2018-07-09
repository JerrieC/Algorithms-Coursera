import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * To perform a series of computational experiments.
 *
 * @author Jiayu Chen
 * @purpose
 * @guide
 * @since 07/03/2018
 */
public class PercolationStats {

  private static final double CAL_CONFIDENCE_INTERVAL = 1.96;

  private final int trialsNum;

  private final double[] thresholds;
  /**
   * perform trials independent experiments on an n-by-n grid.
   *
   * @param n      number of site in one grid edge
   * @param trials number of trials
   */
  public PercolationStats(final int n, final int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }
    trialsNum = trials;
    thresholds = new double[trials];
    for (int threshold = 0; threshold < trials; threshold++) {
      Percolation percolation = new Percolation(n);
      while (!percolation.percolates()) {
        int row = StdRandom.uniform(n) + 1;
        int col = StdRandom.uniform(n) + 1;
        percolation.open(row, col);
      }
      thresholds[threshold] = (double) percolation.numberOfOpenSites()
              / (n * n);
    }
  }

  /**
   * sample mean of percolation threshold.
   *
   * @return ample mean of percolation threshold
   */
  public double mean() {
    return StdStats.mean(thresholds);
  }

  /**
   * sample standard deviation of percolation threshold.
   *
   * @return sample standard deviation of percolation threshold
   */
  public double stddev() {
    if (trialsNum == 1) {
      return Double.NaN;
    } else {
      return StdStats.stddev(thresholds);
    }
  }

  /**
   * low endpoint of 95% confidence interval.
   *
   * @return low endpoint of 95% confidence interval
   */
  public double confidenceLo() {
    return mean() - CAL_CONFIDENCE_INTERVAL * stddev() / Math.sqrt(trialsNum);
  }

  /**
   * high endpoint of 95% confidence interval.
   *
   * @return high endpoint of 95% confidence interval
   */
  public double confidenceHi() {
    return mean() + CAL_CONFIDENCE_INTERVAL * stddev() / Math.sqrt(trialsNum);
  }
  /**
   * main method
   *
   * @param args
   */
  public static void main(String[] args) {
    // test
  }
}

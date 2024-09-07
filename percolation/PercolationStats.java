/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double results[];

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            var perc = new Percolation(n);
            while (!perc.percolates()) {
                var row = StdRandom.uniformInt(n) + 1;
                var col = StdRandom.uniformInt(n) + 1;
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                }
            }
            results[i] = (double) perc.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(results.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(results.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        var n = Integer.parseInt(args[0]);
        var trials = Integer.parseInt(args[1]);
        var stats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println(
                "95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi()
                        + ']');
    }

}


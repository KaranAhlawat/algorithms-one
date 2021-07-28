import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] fractionOpen;
    private int n, trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

        this.n = n;
        this.trials = trials;

        fractionOpen = new double[trials];

        for (int i = 0; i < trials; i++) {
            fractionOpen[i] = 0;
        }

        runSimulation();
    }

    private void runSimulation() {
        for (int i = 0; i < trials; i++) {
            Percolation system = new Percolation(n);
            while (!system.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                system.open(row, col);
            }

            fractionOpen[i] = ((double) system.numberOfOpenSites()) / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractionOpen);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractionOpen);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
       return mean() - ((1.96 * stddev())/ Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
       return mean() + (1.96 * stddev() / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats simulation = new PercolationStats(n, trials);

        System.out.println("mean                    = " + simulation.mean());
        System.out.println("stddev                  = " + simulation.stddev());
        System.out.println("95% confidence interval = " + "[" + simulation.confidenceLo() + ", " + simulation.confidenceHi() + "]");
    }
}

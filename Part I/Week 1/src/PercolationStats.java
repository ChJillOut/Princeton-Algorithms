import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private static final double CONSTANT = 1.96;
    private final int trialsCount;
    private double[] meanArray;
    private double[] stddevArray;
    private final int size;


    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("either n or trials is out of bounds");
        }
        trialsCount = trials;
        size = n;
        meanArray = new double[trials];
        stddevArray = new double[trials];

        // calculate percolation threshold for individual simulation
        for (int i = 0; i < trialsCount; i += 1) {
            Percolation per = new Percolation(n);
            int count = 0;
            while (!per.percolates()) {
                int row = 1 + StdRandom.uniform(size);
                int col = 1 + StdRandom.uniform(size);
                if (!per.isOpen(row, col)) {
                    per.open(row, col);
                    count += 1;
                }
            }
            double threshold = (double) count / (double) (size * size);
            meanArray[i] = threshold;
            stddevArray[i] = threshold;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(meanArray);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(stddevArray);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        double mu = mean();
        double sigma = stddev();

        double lowEndPoint = mu - ((CONSTANT * sigma) / Math.sqrt(trialsCount));
        return lowEndPoint;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mu = mean();
        double sigma = stddev();

        double highEndPoint = mu + ((CONSTANT * sigma) / Math.sqrt(trialsCount));
        return highEndPoint;
    }

    // test client (described below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats perStats = new PercolationStats(n, trials);

        double mean = perStats.mean();
        double stddev = perStats.stddev();
        double confidenceLow = perStats.confidenceLo();
        double confidenceHigh = perStats.confidenceHi();
        System.out.println("mean = " + mean);
        System.out.println("stddev = " + stddev);
        System.out.println("95% confidence interval = [" + confidenceLow + ", " + confidenceHigh + "] ");

    }
}

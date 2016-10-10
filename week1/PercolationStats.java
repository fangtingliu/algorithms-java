import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int size, trial_size;
    private double [] counts;

    public PercolationStats(int n, int trial) {
        if (n <= 0 || trial <= 0) {
            throw new IllegalArgumentException("n or trial is less than 0");
        }
        size = n;
        trial_size = trial;
        counts = new double[trial];
        for (int i = 0; i < trial; i++) {
            int count = 0;
            Percolation mp = new Percolation(n);
            while (!mp.percolates()) {
                int row = StdRandom.uniform(size) + 1;
                int col = StdRandom.uniform(size) + 1;
                if (!mp.isOpen(row, col)) {
                    mp.open(row, col);
                    count += 1;
                }
            }
            counts[i] = (double) count / (size * size);
        }
    }

    public double mean() {
        return StdStats.mean(counts);
    }

    public double stddev() {
        return StdStats.stddev(counts);
    }

    public double confidenceLo() {
        double mu = mean();
        double sigma = stddev();
        return (mu - (1.96 * sigma) / Math.sqrt(trial_size));

    }

    public double confidenceHi() {
        double mu = mean();
        double sigma = stddev();
        return (mu + (1.96 * sigma) / Math.sqrt(trial_size));
    }

    public static void main(String [] args) {
        PercolationStats sta = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean = " + sta.mean());
        System.out.println("stddev = " + sta.stddev());
        System.out.println("95% confidence interval = " + sta.confidenceLo() + ", " + sta.confidenceHi());
    }
}
public class PercolationStats {
    private int numExperiments;
    private int numSites;
    private double[] thresholds;
    
    /**
     *   Constructor: takes 2 input integer N and T
     *   records the threshold for each percolation of T runs
     */
    public PercolationStats(int N, int T) {
        init(N, T);
        
        for (int expr = 0; expr < T; expr++) { // loop for number of experiments
            Percolation perc = new Percolation(N);
            int opened = 0;
            
            while (!perc.percolates()) { 
                int r = StdRandom.uniform(1, N + 1);  // row index
                int c = StdRandom.uniform(1, N + 1);  // column index
                
                if (!perc.isOpen(r, c)) {
                    perc.open(r, c);
                    ++opened;
                }
            }
            double x = (double) opened / numSites;
            thresholds[expr] = x;
        }
    }
    
    /**
     *   return the average threshold of T experiments (mu)
     */
    public double mean() { return StdStats.mean(thresholds); }      
    
    /**
     *   return the standard deviation of T experiments (sigma square)
     */
    public double stddev() { return StdStats.stddev(thresholds); }           
    
    /**
     *   return the low confidence level (mu - (1.96*sigma)/sqrt(T) )
     */
    public double confidenceLo() { 
        return mean() - (1.96 * stddev()) / Math.sqrt(numExperiments);
    }
    
    /**
     *   return the high confidence level (mu + (1.96*sigma)/sqrt(T) )
     */
    public double confidenceHi() { 
        return mean() + (1.96 * stddev()) / Math.sqrt(numExperiments); 
    }          
    
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        
        Stopwatch stopwatch = new Stopwatch();
        PercolationStats ps = new PercolationStats(N, T);
        double elapsed = stopwatch.elapsedTime();
        
        System.out.printf("mean %17s %.16f\n", "=", ps.mean());
        System.out.printf("stddev %15s %.16f\n", "=", ps.stddev());
        System.out.printf("95%% confidence level = %.16f, %.16f\n\n", 
                          ps.confidenceLo(), ps.confidenceHi());
        System.out.println("Elapsed time: " + elapsed + " seconds.");
    }   
    
    /**
     *  initialize instance variables
     */
    private void init(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("wrong T and N value");
        }
        numExperiments = T;
        numSites = N * N;
        thresholds = new double[T];
    }
}
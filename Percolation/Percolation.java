public class Percolation {
    
    private static final int CLOSED = 0;
    private static final int OPENED = 1;
    private static final int CONN_TO_BOT = 2;
    private int top; // virtual top
    private int row; 
    private WeightedQuickUnionUF wqu;  
    private byte[] status;   
    
    public Percolation(int N) {
        if (N <= 0) throw new IllegalArgumentException("N must be > 0");
        
        wqu = new WeightedQuickUnionUF(N * N + 2); // extra space for virtual top
        status = new byte[N * N + 1];
        row = N;
        top = N * N;
    }
    
    /**
     *   status site at row i, column j if it isn't already opened
     *   union the site and its neighbor if both are opened
     */
    public void open(int i, int j) {
        int currentSite = xyTo1D(i, j);
        if (!isOpen(i, j)) status[currentSite] = Percolation.OPENED;
        else return;
            
        //bottom neighbor (must be first because status has to be set first)
        if (!lastRow(i, j)) {
            if (isOpen(i+1, j)) { connect(currentSite, xyTo1D(i+1, j)); }
        }
        else { status[currentSite] = Percolation.CONN_TO_BOT; }
            
        //top neighbor
        if (!firstRow(i, j)) {
            if (isOpen(i-1, j)) { connect(currentSite, xyTo1D(i-1, j)); }
        }
        else { connect(currentSite, top); }

        //left neighbor
        if (!firstColumn(i, j)) {
            if (isOpen(i, j-1)) { connect(currentSite, xyTo1D(i, j-1)); }
        }
        
        //right neighbor
        if (!lastColumn(i, j)) {
            if (isOpen(i, j+1)) { connect(currentSite, xyTo1D(i, j+1)); }
        }
    }
    
    /**
     *   return true if site status is either opened or connected
     */
    public boolean isOpen(int i, int j) { 
        return status[xyTo1D(i, j)] != Percolation.CLOSED; 
    }
    
    /**
     *   return true if site at row i, col j is connected to virtual top
     */
    public boolean isFull(int i, int j) {
        int currentSite = xyTo1D(i, j);
        return wqu.connected(currentSite, top);
    }
    
    /**
     *   return true if virtual top is connected to bottom
     */
    public boolean percolates() { 
        return status[wqu.find(top)] == Percolation.CONN_TO_BOT; 
    }
    
    public static void main(String[] args) { 
        Percolation perc = new Percolation(0);
        perc.open(1, 5);
    } 
    
    /** 
     *   return true if row i = 1, else return false
     */
    private boolean firstRow(int i, int j) {
        return i == 1;
    }
    
    /**
     *   return true if column j = 1, else return false
     */
    private boolean firstColumn(int i, int j) {
        return j == 1;
    }
    
    /**
     *   return true if row i = number of rows, else return false
     */
    private boolean lastRow(int i, int j) {
        return i == row; 
    }
    
    /**
     *   return true if column j = number or columns, else return false
     *   square matrix so number of row and column is the same
     */
    private boolean lastColumn(int i, int j) {
        return j == row;
    }
    
    /**
     *   convert row i column j to a 1-dimensional index for 1D array
     */
    private int xyTo1D(int i, int j) {
        validate(i, j);
        return (i - 1) * row + (j - 1);
    }
    
    /**
     *   verify that row i and column j is within range of 1D array index
     *   throws IndexOutOfBoundsException if it is out of range
     */
    private void validate(int i, int j) {
        if (i <= 0 || i > row || j <= 0 || j > row) {
            throw new IndexOutOfBoundsException(i + "," + j + " is not in range.");
        }
    }
    
    /**
     *   connect the 2 sites together and set its new root's status
     */
    private void connect(int site, int neighbor) {
        // if either parent is connected to root
        if (status[wqu.find(neighbor)] == 2 || status[wqu.find(site)] == 2) {
            
            // union them
            wqu.union(site, neighbor);
            
            // then set their new parent's status to 2
            status[wqu.find(site)] = Percolation.CONN_TO_BOT;
        }
        else wqu.union(site, neighbor);
    }
}
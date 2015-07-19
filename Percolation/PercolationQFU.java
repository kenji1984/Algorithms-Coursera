public class PercolationQFU {
    
    private static int top; // virtual top
    private static int bottom;  // virtual bottom
    private static int row; 
    private QuickFindUF qfu;  
    private boolean[] open;   // holding open or close boolean value of sites
    
    public PercolationQFU(int N)              
    {
        //count-2 and count-1 element will be virtual top and virtual bottom
        qfu = new QuickFindUF(N * N + 2);
        open = new boolean[N * N];
        row = N;
        top = N * N;
        bottom = N * N + 1;
    }
    
    /**
     *   open site at row i, column j if it isn't already opened
     *   check its possible neighbors to see if they're opened
     *   union the site and its neighbor if both are opened
     */
    public void open(int i, int j)          
    {
        validate(i, j);
        
        int currentSite = xyTo1D(i, j);
        if (!isOpen(i, j)) open[currentSite] = true;
        
        //row[i-1][j], row[i+1][j], row[i][j-1], row[i][j+1]
        int topNeighbor = xyTo1D(i-1, j);
        int leftNeighbor = xyTo1D(i, j-1);
        int bottomNeighbor = xyTo1D(i+1, j);
        int rightNeighbor = xyTo1D(i, j+1);
        
        if (!firstRow(i, j)) {
            if (isOpen(i-1, j)) qfu.union(currentSite, topNeighbor);
        }  
        else {
            qfu.union(currentSite, top);
        }
        
        if (!lastRow(i, j)) {
            if (isOpen(i+1, j)) qfu.union(currentSite, bottomNeighbor);
        }
        else {
            qfu.union(currentSite, bottom);
        }
        
        if (!firstColumn(i, j)) {
            if (isOpen(i, j-1)) qfu.union(currentSite, leftNeighbor);
        }
        
        if (!lastColumn(i, j)) {
            if (isOpen(i, j+1)) qfu.union(currentSite, rightNeighbor);
        }
    }
    
    /**
     *   return true if site is opened
     */
    public boolean isOpen(int i, int j)     
    {
        validate(i, j);
        return open[xyTo1D(i, j)];
    }
    
    /**
     *   return true if site at row i, col j is connected to virtual top
     */
    public boolean isFull(int i, int j)     
    {
        validate(i, j);
        int currentSite = xyTo1D(i, j);
        return qfu.connected(currentSite, top);
    }
    
    /**
     *   return true if virtual top and virtual bottom are connected
     */
    public boolean percolates()             
    {
        //system percolate if virtual top and virtual bottom are connected
        return qfu.connected(top, bottom);
    }
    
    public static void main(String[] args)   // test client (optional)
    {
        PercolationQFU p = new PercolationQFU(3);
        System.out.println("starting out...total components is: " + p.qfu.count());
        System.out.println("top: " + top + ",  bottom: " + bottom);
        p.open(1, 1);
        p.open(3, 2);
        p.open(3, 3);
        p.open(2, 2);
        p.open(1, 3);
        p.open(2, 1);
        System.out.println(p.qfu.connected(p.xyTo1D(2, 1), p.xyTo1D(2, 2)));
        System.out.println(p.qfu.count() + " components");
        System.out.println("percolates: " + p.percolates());
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
        return (i - 1) * row + (j - 1);
    }
    
    /**
     *   verify that row i and column j is within range of 1D array index
     *   throws IndexOutOfBoundsException if it is out of range
     */
    private void validate(int i, int j) {
        int index = xyTo1D(i, j);
        if (index < 0 || index >= row * row) {
            throw new IndexOutOfBoundsException("index " + index + " out of bound");
        }
    }
}
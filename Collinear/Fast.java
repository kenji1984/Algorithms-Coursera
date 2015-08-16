import java.util.Comparator;
import java.util.Arrays;

public class Fast {
    
    private static <T> void sort(T[] a, Comparator<? super T> comparator) {
        StdRandom.shuffle(a);
        //qSort3Way(a, 0, a.length-1, comparator);
        qSort(a, 0, a.length-1, comparator);
    }
    
    private static <T> void qSort(T[] a, int lo, int hi, Comparator<T> comparator) {
        if (hi <= lo) return;
        int p = partition(a, lo, hi, comparator);
        qSort(a, lo, p-1, comparator);
        qSort(a, p+1, hi, comparator);
    }
    
    /**
     *  This will sort based on slope relative to a point
     *  There're probably going to be many points with similar slopeTo(p) 
     *  so 3-way partition sort is chosen
     */
    private static <T> void qSort3Way(T[] a, int lo, int hi, Comparator<? super T> comparator) {
        if (lo >= hi) return;
        
        T pivot = a[lo];
        
        int lt = lo; // index less than lt are less than pivot
        int gt = hi; // index greater than gt are greater than pivot
        int i = lo; // index before i but greater than lt are equal to pivot
        
        while (i <= gt) {
            int compare = comparator.compare(a[i], pivot);
            
            if (compare < 0) swap(a, lt++, i++);
            else if (compare > 0) swap(a, gt--, i);
            else i++;
        }
        
        qSort3Way(a, lo, lt-1, comparator);
        qSort3Way(a, gt+1, hi, comparator);
    }
    
    private static <T> int partition(T[] a, int lo, int hi, Comparator<T> c) {
        //using lo as pivot 
        T pivot = a[lo];
        int i = lo;
        int j = hi + 1;
        
        while (true) {
            //increment i while a[i] < a[lo]
            while (less(c, a[++i], pivot))
                if (i == hi) break;
            while (less(c, pivot, a[--j]))
                if (j == lo) break;
            if (i >= j) break;
            swap(a, i, j);
        }
        
        swap(a, lo, j);
        return j;
    }
    
    private static <T extends Comparable<T>> void sort(T[] a) {
        StdRandom.shuffle(a);
        qSort(a, 0, a.length - 1);
    }
    
    private static <T extends Comparable<T>> void qSort(T[] a, int lo, int hi) {
        if (hi <= lo) return;
        int pivot = partition(a, lo, hi);
        
        // must reduce at least 1 element each recursive call
        qSort(a, lo, pivot -1);
        qSort(a, pivot+1, hi);
    }
    
    /**
     *  Quicksort using single left pivot
     *  Swaps on equal, but skip swaps on a[i] < pivot or a[j] > pivot
     */
    private static <T extends Comparable<T>> int partition(T[] a, int lo, int hi) {
        //using lo as pivot 
        T pivot = a[lo];
        int i = lo;
        int j = hi + 1;
        
        while (true) {
            //increment i while a[i] < a[lo]
            while (less(a[++i], pivot))
                if (i == hi) break;
            while (less(pivot, a[--j]))
                if (j == lo) break;
            if (i >= j) break;
            swap(a, i, j);
        }
        
        swap(a, lo, j);
        return j;
    }
    
    private static <T extends Comparable<T>> boolean less(T a, T b) {
        return a.compareTo(b) < 0;
    }
    
    private static <T> boolean less(Comparator<T> c, T a, T b) {
        return c.compare(a, b) < 0;
    }
    
    private static void swap(Object[] a, int i, int j) {
        Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    
    private static void print(Point[] sorted, int count, int j) {
        StdOut.print(sorted[0] + " ");
        int i = count; // assignment of parameter not allowed 
        while (i >= 0) {
            StdOut.print("-> " + sorted[j - i]);
            i--;
        }
        StdOut.println();
    }
    
    /**
     *  Sort the subsegment from j-count to j (they all have the same slope)
     *  Compare the first item(smallest) in subsegment to the current iterated point
     *  which is always at position 0 (degenerated slope)
     *  Draws a line from smallest to the largest point in the subsegment
     */
    private static void drawSegments(Point[] sorted, int count, int j) {
        Arrays.sort(sorted, j-count, j+1);
        
        if (!less(sorted[j-count], sorted[0])) {
            sorted[0].drawTo(sorted[j]);
            print(sorted, count, j);                    
        }
    }
    
    public static void main(String[] args) {
        String fileName = args[0];
        In in = new In(fileName);
        
        int N = in.readInt();
        Point[] points = new Point[N];
        Point[] sorted = new Point[N];
        
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point point = new Point(x, y);
            points[i] = point;
            sorted[i] = point;
        }
        
        sort(points);
        
        for (int i = 0; i < N; i++) {     
            Point iPoint = points[i]; // always the first point in sorted array
            
            iPoint.draw();
            sort(sorted, iPoint.SLOPE_ORDER);
            
            int count = 0;
            int j = 2;
            
            for (j = 1; j < N-1; j++) {  
                
                Double slope1 = iPoint.slopeTo(sorted[j]);
                Double slope2 = iPoint.slopeTo(sorted[j+1]);
                
                if (slope1.compareTo(slope2) == 0) count++;
                else {
                    if (count < 2) { count = 0; continue; }
                    drawSegments(sorted, count, j);
                    count = 0;
                }
            } 
            
            // if last item of the array is one of the subsegment, the else case never runs
            // this takes care of that special case
            if (count >= 2) {
                drawSegments(sorted, count, j);
            }
        }
        StdDraw.show(0);
    }
}
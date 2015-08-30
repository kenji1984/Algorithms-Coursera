import java.util.NoSuchElementException;
import java.util.Date;

/**
 * Max Priority Queue using Binary Heap structure
 * Provides O(logN) insert and deletion (swim and sink)
 *  Provides constant O(1) max retrieval
 */
public class MinPQ<Key extends Comparable<Key>> {
    
    private int N;  // number of elements
    private Key[] keys; // array to store items
    private int capacity;
    private Key max;
    
    /**
     * Initialize priority queue with 0 item and 2 spaces
     * Space is dynamically expanded when needed
     */
    public MinPQ() {
        N = 0;
        capacity = 1; // 1 less than keys.length
        keys = (Key[]) new Comparable[capacity + 1];  // keys[0] is not used
    }
    
    public int size() {
        return N;
    }
    
    /**
     * Resize keys array to array of newLength+1
     * Throws java.lang.IllegalArgumentException if newlength is negative
     */
    private void resize(int newLength) {
        if (newLength < 0) throw new IllegalArgumentException();
        
        Key[] copy = keys;
        keys = (Key[]) new Comparable[newLength + 1]; 
        
        for (int i = 1; i <= N; i++) {
            keys[i] = copy[i];
        }
        capacity = newLength;
    }
    
    public boolean isEmpty() { return N == 0; }
    
    
    /**
     * Insert a key at the end of the priority queue
     * Re-adjust the heap order by swim()
     * Throws java.lang.NullPointerException if null item is inserted
     */
    public void insert(Key key) {
        if (key == null) throw new NullPointerException();
        if (N == capacity) resize(2 * capacity);
        
        keys[++N] = key;
        swim(N);
        if (max == null) { max = key; return; }
        max = smaller(max, key) ? key : max; 
    }
    
    
    /**
     * Change key at k-th with its parent
     * Keep swimming up until heap order is restored
     */
    private void swim(int k) {
        if (N == 1) return; // only 1 item, no need to swim
        
        Key kth = keys[k];
        // while there is a child key and it's smaller than its parent
        while (k > 1 && smaller(kth, keys[k/2])) {
            keys[k] = keys[k/2];
            k = k / 2;
        }
        keys[k] = kth;
    }
    
    
    public Key deleteMin() {
        if (isEmpty()) throw new NoSuchElementException();
        if (N <= capacity / 3) resize(capacity / 2);
        
        Key min = keys[1];
        
        // switch max with the last item in array
        swap(1, N);
        
        // reheap by sinking new root key
        keys[N--] = null; 
        sink(1);
        
        if (max.compareTo(min) == 0) max = null;
        return min;
    }
    
    public Key max() { 
        if (isEmpty()) throw new NoSuchElementException();
        return max; 
    }
    
    private void sink(int k) {
        // this check is redundant since deleteMax takes care of this check but in case something changes
        if (N <= 1) return;
        
        Key kth = keys[k];
        
        // while k still has a child, swap with the smaller child
        while (k <= N/2) {
            
            int small = k * 2; // set smaller child as the first child
            // make sure second child exist
            if (small < N && smaller(keys[k*2+1], keys[small]))
                small++;
            
            // if the small child is <= k, then stop
            if (!smaller(keys[small], kth)) break;
            
            // else move the small child up
            keys[k] = keys[small];
            k = small;
        }
        keys[k] = kth;
    }
    
    private boolean smaller(Key c, Key p) { return c.compareTo(p) < 0; }
    
    
    private void swap(int i, int j) {
        Key temp = keys[i];
        keys[i] = keys[j];
        keys[j] = temp;
    }
    
    private void print() {
        for (int i = 1; i <= N; i++) System.out.print(keys[i] + " ");
        System.out.println();
    }
    
    public static void main(String[] args) {
        char[] keys = {'T', 'P', 'R', 'N', 'H', 'O', 'A', 'E', 'I', 'G', 'S'};
        MinPQ<Character> pq = new MinPQ<Character>();
        MinPQ<Date> datePQ = new MinPQ<Date>();
        Date d = new Date();
        
        datePQ.insert(d);
        datePQ.print();
        for (int i = 0; i < keys.length; i++) {
            pq.insert(keys[i]);
        }
        pq.print();
        System.out.println("max: " + pq.max());
        
        for (int i = 0; i < keys.length; i++) {
            System.out.print(pq.deleteMin() + " ");
        }
        System.out.println("...");
        pq.print();
    }
}
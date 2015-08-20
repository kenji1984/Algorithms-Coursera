import java.util.NoSuchElementException;
import java.util.Date;

/**
*	Max Priority Queue using Binary Heap structure
*	Provides O(logN) insert and deletion (swim and sink)
* 	Provides constant O(1) max retrieval
*/
public class MaxPQ<Key extends Comparable<Key>> {
	
	private int N;  // number of elements
	private Key[] keys; // array to store items
	private int capacity;
	private Key min;
	
	/**
	*	Initialize priority queue with 0 item and 2 spaces
	*	Space is dynamically expanded when needed
	*/
	public MaxPQ() {
		N = 0;
		capacity = 1; // 1 less than keys.length
		keys = (Key[]) new Comparable[capacity + 1];  // keys[0] is not used
	}
	
	
	/**
	*	Resize keys array to array of newLength+1
	*	Throws java.lang.IllegalArgumentException if newlength is negative
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
	*	Insert a key at the end of the priority queue
	*	Re-adjust the heap order by swim()
	*	Throws java.lang.NullPointerException if null item is inserted
	*/
	public void insert(Key key) {
		if (key == null) throw new NullPointerException();
		if (N == capacity) resize(2 * capacity);
		
		keys[++N] = key;
		swim(N);
		if (min == null) { min = key; return; }
		min = greater(min, key) ? key : min;	
	}
	
	
	/**
	*	Change key at k-th with its parent
	*	Keep swimming up until heap order is restored
	*/
	private void swim(int k) {
		if (N == 1) return; // only 1 item, no need to swim
		
		Key kth = keys[k];
		// while there is a child key and it's greater than its parent
		while (k > 1 && greater(kth, keys[k/2])) {
			keys[k] = keys[k/2];
			k = k / 2;
		}
		keys[k] = kth;
	}
	
	
	public Key deleteMax() {
		if (isEmpty()) throw new NoSuchElementException();
		if (N <= capacity / 3) resize(capacity / 2);
		
		Key max = keys[1];
		
		// switch max with the last item in array
		swap(1, N);
		
		// reheap by sinking new root key
		keys[N--] = null; 
		sink(1);
		
		if (max.compareTo(min) == 0) min = null;
		return max;
	}
	
	public Key min() { 
			if (isEmpty()) throw new NoSuchElementException();
			return min; 
		}
	
	private void sink(int k) {
		// this check is redundant since deleteMax takes care of this check but in case something changes
		if (N <= 1) return;
		
		Key kth = keys[k];
		
		// while k still has a child, swap with the bigger child
		while (k <= N/2) {
			
			int big = k * 2; // set bigger child as the first child
			// make sure second child exist
			if (big < N && greater(keys[k*2+1], keys[big]))
				big++;
			
			// if the big child is <= k, then stop
			if (!greater(keys[big], kth)) break;
			
			// else move the big child up
			keys[k] = keys[big];
			k = big;
		}
		keys[k] = kth;
	}
	
	private boolean greater(Key c, Key p) { return c.compareTo(p) > 0; }
	
	
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
		MaxPQ<Character> pq = new MaxPQ<Character>();
		MaxPQ<Date> datePQ = new MaxPQ<Date>();
		Date d = new Date();
	
		datePQ.insert(d);
		datePQ.print();
		for (int i = 0; i < keys.length; i++) {
			pq.insert(keys[i]);
		}
		pq.print();
		System.out.println("min: " + pq.min());
		
		for (int i = 0; i < keys.length; i++) {
			System.out.print(pq.deleteMax() + " ");
		}
		System.out.println("...");
		pq.print();
		
		// check key mutability. How to make defensive copy of a Key variable?
		Date d2 = d;
		d2.setMonth(4);
		datePQ.print();
	}
}
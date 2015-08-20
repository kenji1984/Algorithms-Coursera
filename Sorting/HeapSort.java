/*
*	In-place, unstable sorting algorithm using Binary Heap data structure
*	Construct a max heap (for ascending order)
*	Continuously calling deleteMax() but without actually deleting
*	So the first max is at N, 2nd at N-1, so on
*	Runtime: O(nlogn) best and worse case, but slower than quick sort and merge sort
*		sort due to longer inner loop. Also jump too far between array accesses to be 
*		able to take advantage of array memory caching
*/
public class HeapSort extends AbstractSort {
	
	/**
	*	Sort a by swapping root item with N-th item and decrement N
	*	Sink the new root to its right full place after every swap
	*/
	public static <T extends Comparable<T>> void sort(T[] a) {
		buildHeap(a);
		
		int N = a.length;
		while (N > 1) {
			swap(a, 0, N-1);  // swap method from AbstractSort works with array 0...N
			sink(a, 1, --N);  // This algorithm works with array 1...N
		}
	}
	
	
	/**
	*	Starts from bottom going up. All bottom child are automatically in heap order
	*	So from the first parent row at bottom to the root
	*	Sink() to build the heap from bottom up
	*/
	private static <T extends Comparable<T>> void buildHeap(T[] a) {
		int N = a.length;
		for (int k = N/2; k >= 1; k--) sink(a, k, N);
	}
	
	
	/**
	*	Similar to Binary Heap sink method, but with extra parameter N
	*	because we only want to sort from 1 to N-- each time max is loitered
	*/
	private static <T extends Comparable<T>> void sink(T[] a, int k, int N) {
		if (N < 1 || N > a.length) throw new IllegalArgumentException();
		if (N == 1) return;
		
		// this array starts with index 0. The Heap algorithm starts with index 1
		T kth = a[k-1];
		
		while (k <= N / 2) {
			int big = k * 2;
			
			if (big < N && less(a, big, big+1)) big++;
			if (!lessThan(kth, a[big-1])) break;  // different less method to compare 2 T items
			a[k-1] = a[big-1];
			k = big;
		}
		a[k-1] = kth;
	}
	
	
	private static <T extends Comparable<T>> boolean less(T[] a, int i, int j) {
		return a[i-1].compareTo(a[j-1]) < 0;
	}
	
	
	public static void main(String[] args) {
		Integer[] a = {9, 20, 1, 5, 13, 2, 4, 19};
		sort(a);
		for(int i: a) System.out.print(i + " ");
	}
}
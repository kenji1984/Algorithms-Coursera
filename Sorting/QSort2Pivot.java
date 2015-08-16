class QSort2Pivot extends AbstractSort {
	
	public static <T extends Comparable<T>> void sort(T[] a) {
		dualPartition(a, 0, a.length-1);
	}
	
	/**
	*	Partition array a into 3 sub ararys using 2 pivots
	* 	First sub array is less than first pivot
	*	Second sub array is in between first and second pivot
	*	Third sub array is greater than second pivot
	*/
	private static <T extends Comparable<T>> void dualPartition(T[] a, int lo, int hi) {
		if (hi <= lo) return;
		
		// use lo and hi as pivot. make sure they're in right place first
		if (lessThan(a[hi], a[lo])) swap(a, lo, hi);
		int lt = lo + 1;
		int gt = hi - 1;
		int i = lo + 1;
		
		while (i <= gt) {
			// swap i and lt if a[i] < a[lo]
			if (lessThan(a[i], a[lo])) swap(a, lt++, i++);
			
			// swap i and gt if a[i] > a[hi]
			else if (lessThan(a[hi], a[i])) swap(a, gt--, i);
			
			// if a[lo] <= a[i] <= a[hi]. Keep scanning
			else i++;
		}
		
		// moving pivots to their correct places
		swap(a, lo, --lt);
		swap(a, hi, ++gt);
		
		// recursively sort the 3 sub arrays. Array reduced by 2 every call
		dualPartition(a, lo, lt-1);
		dualPartition(a, lt+1, gt-1);
		dualPartition(a, gt+1, hi);
	}
}
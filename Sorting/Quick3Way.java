/**
*	Similar to QuickSort but partition array into 3 sub arrays instead
*	less than pivot array, equal pivot array, and greater pivot array
*	Run time: Increase performance if lots of duplicates (more than half) since equal pivot elements are skipped
*			Decrease performance if minimal to no duplicates as every non duplicate compares
			require a swap (versus quicksort which only swap when a[i] > a[p] and a[j] < a[p]
*/
class Quick3Way extends AbstractSort {
	
	public static <T extends Comparable<T>> void sort(T[] a) {
		shuffle(a);
		sort(a, 0, a.length-1);
	}
	
	private static <T  extends Comparable<T>> void sort(T[] a, int lo, int hi) {

		if (lo >= hi) return;
		
		T pivot = a[lo];
		
		// keep pointers to less than pivot, and greater than pivot
		int lt = lo;
		int gt = hi;
		
		// i holds pointer to items equal to pivot, but also an iterator
		int i = lo + 1;
		
		while (i <= gt) {
			// advance i, compare it to pivot
			int compare = a[i].compareTo(pivot);
			// if a[i] less than pivot, move to lt, advance lt and i
			if (compare < 0) swap(a, lt++, i++);
			// if a[i] greater than pivot, move it to gt, decrement gt
			// do not advance i, because the new value at i can be smaller than pivot
			else if (compare > 0) swap(a, gt--, i);
			// if a[i] equal to pivot, just advance i, this is the mid section
			else i++;
		}
		
		sort(a, lo, lt-1);
		sort(a, gt+1, hi);
	}
	
	
	public static void main(String[] args) {
		int size = 10000000;
		Integer[] test = new Integer[size];
		
		java.util.Random rand = new java.util.Random();
		for (int i = 0; i < size; i++) {
			test[i] = rand.nextInt(size / 1000);
		}
		
		String[] strings = {"Hi", "my", "name", "is", "Ken", "I'm", "umemployed"};
		
		long start = System.currentTimeMillis();
		Quick3Way.sort(test);
		//QuickSort.print(test);
		System.out.println((float) (System.currentTimeMillis() - start) / 1000);
		
		Quick3Way.sort(strings);
		Quick3Way.print(strings);
	}
}
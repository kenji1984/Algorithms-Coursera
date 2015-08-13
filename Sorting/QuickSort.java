class QuickSort extends AbstractSort {
	
	public static <T extends Comparable<T>> void sort(T[] a) {
		// shuffle the array to probablistic guarantee not to run into N^2 runtime
		shuffle(a);
		sort(a, 0, a.length - 1);
	}
	
	public static <T extends Comparable<T>> void sort(T[] a, int lo, int hi) {
		if (hi <= lo) { return; }
		int pivot = partition(a, lo, hi);
		sort(a, lo, pivot-1);
		sort(a, pivot+1, hi);		
	}
	
	private static <T extends Comparable<T>> int partition(T[] a, int lo, int hi) {
		
		int i = lo;
		int j = hi + 1;
		
		while (true) {
			// increments i if a[i] is smaller than a[lo] 
			while (lessThan(a[++i], a[lo])) if (i == hi) break;
			//decrements j if a[j] is greater than a[lo]
			while (lessThan(a[lo], a[--j]));	
			
			if (i >= j) break;
			swap(a, i, j);
		}
		// switch the pivot (a[lo]) with a[j]
		swap(a, lo, j);
		// everything before and after j should be in the right places,
		// so sort from lo to j-1, and j+1 to hi next. Therefore we return j
		return j;
	}
	
	public static void main(String[] args) {
		int size = 10000000;
		Integer[] test = new Integer[size];
		
		java.util.Random rand = new java.util.Random();
		for (int i = 0; i < size; i++) {
			test[i] = rand.nextInt(size + 1);
		}
		
		String[] strings = {"Hi", "my", "name", "is", "Ken", "I'm", "umemployed"};
		
		long start = System.currentTimeMillis();
		QuickSort.sort(test);
		//QuickSort.print(test);
		System.out.println((float) (System.currentTimeMillis() - start) / 1000);
		
		QuickSort.sort(strings);
		QuickSort.print(strings);
	}
}
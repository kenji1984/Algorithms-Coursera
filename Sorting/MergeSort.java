class MergeSort extends AbstractSort {
	
	/**
	*	Initialize temp array here. Recursive initialize temp in mergeSort would make it slow
	*/
	public static <T extends Comparable<T>> void sort(T[] arr) {
		
		T[] temp = (T[]) new Comparable[arr.length];
		mergeSort(arr, temp, 0, arr.length - 1);
	}
	
	/**
	*	Keep dividing array into 2 halves until array has single element
	*	Merge all divided array back together from smallest to largest
	*/
	private static <T extends Comparable<T>> void mergeSort(T[] arr, T[] temp, int lo, int hi) {
		
		if (hi <= lo) return;
		int mid = lo + (hi - lo) / 2;
		mergeSort(arr, temp, lo, mid);
		mergeSort(arr, temp, mid+1, hi);
		merge(arr, temp, lo, mid, hi);		
	}
	
	/**
	*	Pre: elements from lo to mid are sorted, elements from mid+1 to hi are sorted
	*	Merge the 2 halves, adding smaller element of the 2 first.
	*/
	private static <T extends Comparable<T>> void merge(T[] arr, T[] temp, int lo, int mid, int hi) {
		
		assert isSorted(arr, lo, mid);
		assert isSorted(arr, mid+1, hi);
		
		for (int i = lo; i <= hi; i++) 
			temp[i] = arr[i];
		
		int i = lo;
		int j = mid + 1;
		int k = lo;
		while (i <= mid && j <= hi) {
			arr[k++] = (lessThan(temp[i], temp[j])) ? temp[i++] : temp[j++];
		}
		
		while (i <= mid) arr[k++] = temp[i++];
		while (j <= hi) arr[k++] = temp[j++];
		
		assert isSorted(arr, lo, hi);	
	}
	
		
	protected static <T extends Comparable<T>> boolean isSorted(T[] arr, int begin, int end) {
		
		for (int i = begin; i < end; i++) {
			if (lessThan(arr[i+1], arr[i])) return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		
		Integer[] test = new Integer[10000000];
		java.util.Random rand = new java.util.Random();
		for (int i = 0; i < 10000000; i++) {
			test[i] = rand.nextInt();
		}
		
		long start = System.currentTimeMillis();
		ShellSort.sort(test);
		System.out.println((float) (System.currentTimeMillis() - start) / 1000);
	}
}
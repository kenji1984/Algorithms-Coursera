/**
*	Go through array N times, each time get the smallest item in the array
*	Swap smallest item to the right position (first iteration = 0th item, 2nd = 1st item, etc)
*	Runtime: have to check N-i elements each iteration to find min, no matter what
*/
class SelectionSort extends AbstractSort {
	
	public static <T extends Comparable<T>> void sort(T[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			int min = i;
			
			for (int j = i + 1; j < array.length; j++) {
				if (lessThan(array[j], array[min])) min = j;
			}
			
			swap(array, i, min);
		}
	}
	
	public static void main(String[] args) {
		Integer[] iarr = {1, 5, 3, 2, 5, 6, 1, 4};
		String[] sarr = {"Hello", "World", "From", "Java", "Rookie"};
		SelectionSort.sort(iarr);
		SelectionSort.sort(sarr);
		print(iarr);
		print(sarr);
	}
}
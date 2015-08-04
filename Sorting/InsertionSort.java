/**
*	Go through each element in the array
*	If current element is greater or equal to its left neighbor, break out of innner loop
*	Else swap with left neighbor and keep looping until first element is compared
*	Runtime: if list is already sort, inner loop is break everytime, so only outer loop runs
*/
class InsertionSort extends AbstractSort {
	
	public static <T extends Comparable<T>> void sort(T[] array) {
		for (int i = 0; i  < array.length; i++) {
			for (int j = i; j > 0; j--) {
				if (!lessThan(array[j], array[j-1])) break;
				swap(array, j, j-1);	
			}
		}
	}
	
	public static void main(String[] args) {
		Integer[] iarr = {1, 5, 3, 2, 5, 6, 1, 4};
		String[] sarr = {"Hello", "World", "From", "Java", "Rookie"};
		InsertionSort.sort(iarr);
		InsertionSort.sort(sarr);
		print(iarr);
		print(sarr);
	}
}
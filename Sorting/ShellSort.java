import java.util.*;

/**
*	Different SelectionSort using different increment sequences
*	i.e. First HSort sorts every multiple 7th element
*		then second HSort sorts every multiple 3th element, last sort every element
*	Runtime: at last sort, array is partially sorted so selection sort is fast. 
*/
class ShellSort extends AbstractSort {
	
	private static <T extends Comparable<T>> void sort(T[] array) {
		int length = array.length;
		int H = HSequence(length);
		
		while (H >= 1) {
			HSort.sort(array, H);
			H = H / 3;
		}
	}
	
	//returns the highest number H in the sequence for HSort
	private static int HSequence(int length) {
		int H = 1;
		while (H < length / 3) H = 3 * H + 1;			return H;
	}
	
	/**
	*	H-based sort with H as increments
	*	Similar to Selection sort but check multiples of H-th element instead of every element
	*/
	private static class HSort {
		
		private static <T extends Comparable<T>> void sort(T[] arr, int inc) {
			
			for (int i = inc; i < arr.length; i++) {
				for (int j = i; j >= inc; j -= inc) {
					if (lessThan(arr[j], arr[j-inc])) swap(arr, j, j - inc);
					else break;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Integer[] test = new Integer[10000000];
		Integer[] iarr = {1, 5, 3, 2, 5, 6, 1, 4, 9, 8, 11, 50, 25, 0, 4, -20};
		String[] sarr = {"Hello", "World", "From", "Java", "Rookie"};
		ShellSort.sort(iarr);
		ShellSort.sort(sarr);
		
		Random rand = new Random();
		for (int i = 0; i < 10000000; i++) {
			test[i] = rand.nextInt();
		}
		print(iarr);
		print(sarr);
		
		long start = System.currentTimeMillis();
		ShellSort.sort(test);
		System.out.println((float) (System.currentTimeMillis() - start) / 1000);
		//print(test);
	}
}
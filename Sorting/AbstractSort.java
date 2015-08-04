abstract class AbstractSort {
	
	protected static void swap(Object[] obj, int i, int j) {
		Object temp = obj[i];
		obj[i] = obj[j];
		obj[j] = temp;
	}
	
	protected static <T extends Comparable<T>> boolean lessThan(T first, T second) {
		return first.compareTo(second) < 0;
	} 
	
	protected static void print(Comparable[] arr) {
		for (Comparable item : arr) {
			System.out.print(item + " ");
		}
		System.out.println();
	}
}
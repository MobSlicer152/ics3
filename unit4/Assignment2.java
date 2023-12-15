import java.util.Arrays;

public class Assignment2 {
	public static <T extends Comparable> boolean sorted(T[] array) {
		// go through all but the last element, since the loop looks ahead by one
		int i = 0;
		while (i < array.length - 1) {
//		for (int i = 0; i < array.length - 1; i++) {
			// print the current and next object
			System.out.println("Comparing " + array[i] + " to " + array[i + 1]);
			// compare the objects
			if (array[i].compareTo(array[i + 1]) > 0 ) {
				return false;
			}
			i++;
		}
		
		return true;
	}

	public static void main(String[] args) {
		Integer[] heights1 = {
			140, 141, 141, 148, 150, 151
		};
		Integer[] heights2 = {
			140, 156, 141, 144, 150, 157
		};

		System.out.println("Array 1 is " + (sorted(heights1) ? "sorted" : "not sorted"));
		System.out.println("Array 2 is " + (sorted(heights2) ? "sorted" : "not sorted"));

		Arrays.sort(heights1);
		Arrays.sort(heights2);
	
		System.out.println("Array 1 is " + (sorted(heights1) ? "sorted" : "not sorted"));
		System.out.println("Array 2 is " + (sorted(heights2) ? "sorted" : "not sorted"));	
	}
}

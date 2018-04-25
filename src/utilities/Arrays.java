package utilities;

public class Arrays {
	
	public static int[] append(int[] array, int n) {
		int[] newArray = new int[array.length + 1];
		for(int i = 0; i < array.length; i++) newArray[i] = array[i];
		newArray[array.length] = n;
		return newArray;
	}
	
	public static int[][] clone2DArray(int[][] array) {
		int[][] clone = new int[array.length][array[0].length];
		
		for(int i = 0; i < array.length; i++) {
			for(int k = 0; k < array[0].length; k++) {
				clone[i][k] = array[i][k];
			}
		}
		
		return clone;
	}
	
}

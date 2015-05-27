

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class Testing {
	public static void main (final String[] theArgs) throws FileNotFoundException {
		
		File inFile = new File("src/sample_input.txt");
		int[][] array = new int[4][4];
		readFileToArray(inFile, array);
		printArray(array);
		for (int i = 0; i < array.length -1; i++) {
			for (int j = 0; j < array.length -1; j++) {
				if (i > j) {
					array[i][j] = -1;
				} else if (i == j) {
					array[i][j] = 0;
				}
			}
		}
	}
	
	//reads from the given file that is and writes it to the specified array
	private static void readFileToArray(final File theFile, final int[][] theArray) throws FileNotFoundException {
		Scanner scanner = new Scanner(theFile);
//		scanner.useDelimiter("	");
		int currentColumn = 0;
		int currentRow = 0;
		while (scanner.hasNext()) {
			String value = scanner.next();
			if (currentColumn != theArray.length - 1) {
				if (value.equals("NA")) {
					theArray[currentRow][currentColumn] = -1;
					currentColumn++;
				} else {
					theArray[currentRow][currentColumn] = Integer.parseInt(value);
					currentColumn++;
				}
			} else if (currentColumn == theArray.length - 1) {
//				if (value.equals("NA")) {
//					theArray[currentRow][currentColumn] = -1;
//					currentColumn = 0;
//					currentRow++;
//				} else {
					theArray[currentRow][currentColumn] = Integer.parseInt(value);
					currentColumn = 0;
					currentRow++;
//				}
			}
		}
	} //end readFileToArray
	
	//prints the array to the console for testing purposes
	private static void printArray(final int[][] theArray) {
		System.out.print("    ");
		for (int i = 0; i < theArray.length; i++) { //print out column markers
			System.out.print(i + "    ");
		}
		System.out.println();
		
		for (int i = 0; i < theArray.length; i++) { //print out row indicators
			if (i == 0) {
				System.out.print(i + "   ");
			} else {
				System.out.print(i + "  ");
			}
			for (int j = 0; j < theArray.length; j++) { //prints contents of theArray as well as gets spacing straight
				if (theArray[i][j] == -1 && theArray[i][j+1] == -1) { 
					System.out.print(theArray[i][j] + "   ");
				} else if (theArray[i][j] == -1 && theArray[i][j+1] != -1) {
					if (theArray[i][j] < 10) {
						System.out.print(theArray[i][j] + "    ");
					} else if (theArray[i][j] < 100) {
						System.out.print(theArray[i][j] + "   ");
					} else if (theArray[i][j] < 1000) {
						System.out.print(theArray[i][j] + "  ");
					}
				} else if (theArray[i][j] != -1 ) {
					if (theArray[i][j] < 10) {
						System.out.print(theArray[i][j] + "    ");
					} else if (theArray[i][j] < 100) {
						System.out.print(theArray[i][j] + "   ");
					} else if (theArray[i][j] < 1000) {
						System.out.print(theArray[i][j] + "  ");
					}
				}	
			}
			System.out.println();
		}
	} //end print array

	
	

	

}

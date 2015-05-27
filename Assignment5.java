import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

public class Testing {
	public static void main (final String[] theArgs) throws FileNotFoundException {
		
		File inFile = new File("src/sample_input.txt");
		int[][] array = new int[10][10];
		generateRandomArray(array);
//		readFileToArray(inFile, array);
		printArrayToConsole(array);
		writeArrayToFile(array);
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
		scanner.close();
	} //end readFileToArray
	
	//writes the passed array to a tab-delimited file (if theArray[i][j] == -1, then it is represented with the String "NA")
	private static void writeArrayToFile(int[][] theArray) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter("src/input" + theArray.length + ".txt");
		writer.println("COOL SHIT HERE");
		writer.println("COOL SHITLINE 2");
		writer.close();
	}
	
	//populates a given array with random numbers (if i > j, then theArray[i][j] = -1) (if i == j, then theArray[i][j] = 0;)
	private static void generateRandomArray(int[][] theArray) {
		Random random = new Random();
		for (int i = 0; i < theArray.length; i++) {
			for (int j = 0; j < theArray.length; j++) {
				if (j == i) {
					theArray[i][j] = 0;
				} else if (i > j) {
					theArray[i][j] = -1;
				} else {
					theArray[i][j] = random.nextInt(10);
				}
			}
		}
	} //end generateRandomArray
	
	//prints the array to the console for testing purposes
	private static void printArrayToConsole(final int[][] theArray) {
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

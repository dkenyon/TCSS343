import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

//TCSS 343 Assignment 5
// Dennis Kenyon, Ben Cassidy, Audrey Chavarria

public class Testing {
	
	//the five different sized arrays to be tested on
	public static int[][] SIZE100ARRAY = new int[100][100];
	public static int[][] SIZE200ARRAY = new int[200][200];
	public static int[][] SIZE400ARRAY = new int[400][400];
	public static int[][] SIZE600ARRAY = new int[600][600];
	public static int[][] SIZE800ARRAY = new int[800][800];
	public static int[][] SIZE10ARRAY = new int[10][10];
	
	public static void main (final String[] theArgs) throws FileNotFoundException {
		
		initializeArrays(); 
		generateRandomArray(SIZE10ARRAY);
		printArrayToConsole(SIZE10ARRAY);
		
	}
	
	
	
	/* **********************************************************************************************************************************************
	 * 					INITIALIZATION METHODS - THE FOLLOWING METHODS POPULATE THE ARRAYS AND WRITES THEM 
	 *					 TO .txt FILES IN THE FORMAT DESIGNATED IN THE ASSIGNMENT CONSTRAINTS
	 * 
	 * **********************************************************************************************************************************************/
	
	//populates all five arrays and writes them to txt files
	private static void initializeArrays() throws FileNotFoundException {
		generateRandomArray(SIZE100ARRAY);
		generateRandomArray(SIZE200ARRAY);
		generateRandomArray(SIZE400ARRAY);
		generateRandomArray(SIZE600ARRAY);
		generateRandomArray(SIZE800ARRAY);
		writeArrayToFile(SIZE100ARRAY);
		writeArrayToFile(SIZE200ARRAY);
		writeArrayToFile(SIZE400ARRAY);
		writeArrayToFile(SIZE600ARRAY);
		writeArrayToFile(SIZE800ARRAY);
	}
	
	//writes the passed array to a tab-delimited file (if theArray[i][j] == -1, then it is represented with the String "NA")
	//the file is labeled as "inputX.txt", where X is the length of the array
	private static void writeArrayToFile(int[][] theArray) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter("src/input" + theArray.length + ".txt");
		for (int i = 0; i < theArray.length; i++) {
			for (int j = 0; j < theArray.length; j++) {
				if (j != theArray.length - 1) {
					if (i > j) { //if theArray[i][j] == -1
						writer.print("NA\t");
					} else {
						writer.print(theArray[i][j] + "\t");
					}
				} else {
					writer.print(theArray[i][j] + "\n");
				}
			}
		}
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
		System.out.print("     ");
		for (int i = 0; i < theArray.length; i++) { //print out column markers
			if (i < 10) {
				System.out.print(i + "    ");	
			} else if (i < 100) {
				System.out.print(i + "   ");
			} else if (i < 1000) {
				System.out.print(i + "  ");
			}	
		} //end print out column markers
		System.out.println();
		for (int i = 0; i < theArray.length; i++) { //print out row indicators
			if (i == 0) {
				System.out.print(i + "    ");
			} else if (i < 10) {
				System.out.print(i + "   ");
			} else if (i < 100) {
				System.out.print(i + "  ");
			} else if (i < 1000) {
				System.out.print(i + " ");
			} //end print out row indicators
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

	//NOT ACTUALLY NEEDED; ONLY HERE FOR TESTING PURPOSES
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
		
		/* **********************************************************************************************************************************************
		 * 														END INITIALIZATION METHODS
		 * 
		 * **********************************************************************************************************************************************/
} //end class

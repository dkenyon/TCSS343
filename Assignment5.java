import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

//TCSS 343 Assignment 5
// Dennis Kenyon, Ben Cassidy, Audrey Chavarria, Zheng Yang

public class Testing {

	// the five different sized arrays to be tested on
	public static int[][] SIZE100ARRAY = new int[100][100];
	public static int[][] SIZE200ARRAY = new int[200][200];
	public static int[][] SIZE400ARRAY = new int[400][400];
	public static int[][] SIZE600ARRAY = new int[600][600];
	public static int[][] SIZE800ARRAY = new int[800][800];
	public static int[][] SIZE10ARRAY = new int[10][10];
	
	
	static ArrayList<Integer> currentSolution = new ArrayList<Integer>();
	
	public static void main(final String[] theArgs) throws FileNotFoundException {

		// initializeArrays();
		generateRandomArray(SIZE10ARRAY);
		printArrayToConsole(SIZE10ARRAY);
		dynamicProgramming(SIZE10ARRAY);

	}

	//brute force solution
	private static void bruteForce(int[][] theArray) {

	}
	
	// divide and conquer solution
	private static int divideAndConquer(int[][] theArray, int theBeginCol, int theEndCol) {
		if (theBeginCol == theEndCol || theBeginCol + 1 == theEndCol) {
			return theArray[theBeginCol][theEndCol];			
		}
		int currentMin = theArray[theBeginCol][theEndCol];

		for (int i = theBeginCol + 1; i < theEndCol; i++) {
			int cost = divideAndConquer(theArray, theBeginCol, i) + divideAndConquer(theArray, i, theEndCol);			
			if (cost < currentMin) {
				currentMin = cost;
			}
		}
		System.out.println();
		return currentMin;
	}

	//dynamic programming solution
	private static void dynamicProgramming(int[][] theArray) {
		//one dimensional array to keep track of current min count
		int currentMinDistances[] = new int[theArray.length]; 
		//two dimensional array populated for backtracking
		int minBackTrack[][] = new int[theArray.length][theArray.length]; 
		
		// initialize all elements to max possible integer
		for (int i = 0; i < theArray.length; i++) {
			currentMinDistances[i] = Integer.MAX_VALUE;
			for (int j = 0; j < theArray.length; j++) {
				minBackTrack[i][j] = Integer.MAX_VALUE;
			}
			minBackTrack[i][0] = 0;
		}
		currentMinDistances[0] = 0;
		for (int i = 0; i < theArray.length; i++) {
			for (int j = i + 1; j < theArray.length; j++) {
				if (i == 0) {
					currentMinDistances[j] = currentMinDistances[i]	+ theArray[i][j];
					minBackTrack[i][j] = currentMinDistances[i]	+ theArray[i][j];
				} else if (currentMinDistances[j] > currentMinDistances[i] + theArray[i][j]) {
					currentMinDistances[j] = currentMinDistances[i]	+ theArray[i][j];
					minBackTrack[i][j] = currentMinDistances[j];
				}
			}
		}
		System.out.println();
		System.out.println("Minimum cost is : " + currentMinDistances[theArray.length -1]);

		// get rid of Integer.MAX_VALUE's in backtrack array
		backtrackTrace(minBackTrack); //trace the sequence that yields the minimum cost between canoe posts
		

	}

	//traces the sequence that yields the minimum cost between canoe posts
	private static void backtrackTrace (int[][] theArray) {
		for (int i = 0; i < theArray.length; i++) { // get rid of Integer.MAX_VALUE's
			for (int j = 0; j < theArray.length; j++) {
				if (theArray[i][j] == Integer.MAX_VALUE || theArray[i][j] == -1) {
					theArray[i][j] = theArray[i - 1][j];
				}
				// System.out.print(minBackTrack[i][j] + " "); // debug purposes
			}
			// System.out.println(); // debug purposes
		}
		System.out.println();
		System.out.println("PATH TO YIELD MINIMUM COST:");
		backtrack(theArray, theArray.length - 1, theArray.length - 1);
	}
	
	//prints to console a trace stack of the posts one must take to reach the end
	private static void backtrack(int[][] theArray, int theRow, int theCol) {
		int row = theRow;
		int col = theCol;
		if (theRow == theArray.length - 1) {
			System.out.println("	end");
		}
		if (row == 0) {
			System.out.println("	Post " + (row) + " to " + (col));
			System.out.println("	start");
		} else if (theArray[row - 1][col] != theArray[row][col]) {
			System.out.println("	Post " + (row) + " to " + (col));
			backtrack(theArray, row - 1, row);
		} else if (theArray[row - 1][col] == theArray[row][col]) {
			backtrack(theArray, row - 1, col);
		}

	}

	/* **********************************************************************************************************************************************
	 * 							INITIALIZATION METHODS - THE FOLLOWING METHODS POPULATE THE ARRAYS AND
	 * 							WRITES THEM TO .txt FILES IN THE FORMAT DESIGNATED IN THE ASSIGNMENT CONSTRAINTS
	 * 
	 * *********************************************************************************************************************************************
	 */

	// populates all five arrays and writes them to txt files
	private static void initializeArrays() throws FileNotFoundException {
		//instantiate arrays
		generateRandomArray(SIZE100ARRAY); 
		generateRandomArray(SIZE200ARRAY);
		generateRandomArray(SIZE400ARRAY);
		generateRandomArray(SIZE600ARRAY);
		generateRandomArray(SIZE800ARRAY);
		
		//write the instantiated arrays to .txt files
		writeArrayToFile(SIZE100ARRAY);
		writeArrayToFile(SIZE200ARRAY);
		writeArrayToFile(SIZE400ARRAY);
		writeArrayToFile(SIZE600ARRAY);
		writeArrayToFile(SIZE800ARRAY);
	}

	// writes the passed array to a tab-delimited file (if theArray[i][j] == -1,
	// then it is represented with the String "NA")
	// the file is labeled as "inputX.txt", where X is the length of the array
	private static void writeArrayToFile(int[][] theArray) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter("src/input" + theArray.length
				+ ".txt");
		for (int i = 0; i < theArray.length; i++) {
			for (int j = 0; j < theArray.length; j++) {
				if (j != theArray.length - 1) {
					if (i > j) { // if theArray[i][j] == -1
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

	// populates a given array with random numbers (if i > j, then
	// theArray[i][j] = -1) (if i == j, then theArray[i][j] = 0;)
	private static void generateRandomArray(int[][] theArray) {
		Random random = new Random();
		for (int i = 0; i < theArray.length; i++) {
			for (int j = 0; j < theArray.length; j++) {
				if (j == i) {
					theArray[i][j] = 0;
				} else if (i > j) {
					theArray[i][j] = -1;
				} else {
					theArray[i][j] = 1+ random.nextInt(100); //generates random int 1-100
				}
			}
		}
	} // end generateRandomArray

	// prints the array to the console for testing purposes
	private static void printArrayToConsole(final int[][] theArray) {
		System.out.print("     ");
		for (int i = 0; i < theArray.length; i++) { // print out column markers
			if (i < 10) {
				System.out.print(i + "    ");
			} else if (i < 100) {
				System.out.print(i + "   ");
			} else if (i < 1000) {
				System.out.print(i + "  ");
			}
		} // end print out column markers
		System.out.println();
		for (int i = 0; i < theArray.length; i++) { // print out row indicators
			if (i == 0) {
				System.out.print(i + "    ");
			} else if (i < 10) {
				System.out.print(i + "   ");
			} else if (i < 100) {
				System.out.print(i + "  ");
			} else if (i < 1000) {
				System.out.print(i + " ");
			} // end print out row indicators
			for (int j = 0; j < theArray.length; j++) { // prints contents of
														// theArray as well as
														// gets spacing straight
				if (theArray[i][j] == -1 && theArray[i][j + 1] == -1) {
					System.out.print(theArray[i][j] + "   ");
				} else if (theArray[i][j] == -1 && theArray[i][j + 1] != -1) {
					if (theArray[i][j] < 10) {
						System.out.print(theArray[i][j] + "    ");
					} else if (theArray[i][j] < 100) {
						System.out.print(theArray[i][j] + "   ");
					} else if (theArray[i][j] < 1000) {
						System.out.print(theArray[i][j] + "  ");
					}
				} else if (theArray[i][j] != -1) {
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
	} // end print array

	// NOT ACTUALLY NEEDED; ONLY HERE FOR TESTING PURPOSES
	// reads from the given file that is and writes it to the specified array
	private static void readFileToArray(final File theFile,	final int[][] theArray) throws FileNotFoundException {
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
					theArray[currentRow][currentColumn] = Integer
							.parseInt(value);
					currentColumn++;
				}
			} else if (currentColumn == theArray.length - 1) {
				// if (value.equals("NA")) {
				// theArray[currentRow][currentColumn] = -1;
				// currentColumn = 0;
				// currentRow++;
				// } else {
				theArray[currentRow][currentColumn] = Integer.parseInt(value);
				currentColumn = 0;
				currentRow++;
				// }
			}
		}
		scanner.close();
	} // end readFileToArray

	/* **********************************************************************************************************************************************
	 * 															END INITIALIZATION METHODS
	 * 
	 * *********************************************************************************************************************************************
	 */
} // end class

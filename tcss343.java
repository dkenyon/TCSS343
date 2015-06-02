import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

//TCSS 343 Assignment 5
// Dennis Kenyon, Ben Cassidy, Audrey Chavarria, Zheng Yang

public class tcss343 {
	
	static String path = "";
	static int globleMin = 0;
	//static String[] pathArr = new String[1000]; 
	static List<costPath> list = new LinkedList<costPath>();
	
	public static void main(final String[] theArgs) throws FileNotFoundException {
		System.out.println("\nHello. Reading in files now...");
		Scanner scanner = new Scanner(System.in);
		int[] readInArray = new int[640000];
		for (int i = 0; i < readInArray.length; i++) { //initialize all elements in readInArray to max possible integer
			readInArray[i] = Integer.MAX_VALUE;
		}
		int counter = 0; //counter for every token in the input txt file
		int tableN = 0; //this will be the dimension (n) of the table to be populated
		//READ IN ALL TOKENS FROM INPUT TXT FILE
		while (scanner.hasNext()) { 
			String value = scanner.next();
			if (value.equals("NA")) {
				 readInArray[counter] = -1;
			} else {
				readInArray[counter] = Integer.parseInt(value);
			}
			counter++;
		}
		//END READ IN ALL TOKENS FROM INPUT TXT FILE
		tableN = (int) Math.sqrt(counter); //this is the width and height of the array to be populated
		int[][] theArray = new int[tableN][tableN]; //the two-dimensional array of costs from post to post
		System.out.println("Size of 2D array: " + tableN + " x " + tableN);
		System.out.println();
		System.out.println();
		counter = 0; //counter reset to 0 and used to keep help populate the 2D array
		for (int i = 0; i < tableN; i++) { //this for loop populates theArray
			for (int j = 0; j < tableN; j++) {
				theArray[i][j] = readInArray[counter];
				counter++;
			}
		}
//		printArrayToConsole(theArray);
		
		//APPLY ALL ALGORITHMS TO THE TABLE AND FIND MINIMUM COST
		dynamicProgramming(theArray);
		if (theArray.length > 20) {
			System.out.println("\n**NOTE: n > 20, so it will take some time for the minimums for divide-and-conquer and brute force to be found."
					+ " Give it some time.");
		}
		bruteForce(theArray);
		divideAndConquer(theArray);			
	}

	// brute force solution
	
	private static void bruteForce(int[][] theArray) {
		// timer variables
		long totalTime = 0;
		long startTime = 0;
		long finishTime = 0;

		// start the timer
		Date startDate = new Date();
		startTime = startDate.getTime();

		//START BRUTE FORCE LOGIC
		int lowestPossible = theArray[0][theArray.length - 1];
		int currentMin = 0;
//    	System.out.println(lowestPossible);
//    	System.out.println(" ("+ 0 + ", " + 4 +") ");
    	for(int s = theArray.length - 1, level = 1; s > 0; s--, level++){
        int rows = (int) Math.pow(2,s);
			int TOF, k;
			for (int i = rows / 2 + 1; i < rows; i = i + 2) {
				k = 0;
				currentMin = 0;
				for (int j = s - 1, l = level; j >= 0; j--, l++) {
					TOF = (i / (int) Math.pow(2, j)) % 2;
					if (TOF == 1) {
						currentMin = currentMin + theArray[k][l]; /*
																 *  * back
																 * tracking by
																 * zheng *
																 */
						if (k == l && l == 1) {
							path = "";
						} else {
							path += "\n -> from post " + (k + 1) + " to post "
									+ (l + 1);
						}
						k = l;
					}
				}
				if (currentMin < lowestPossible) {
					
					lowestPossible = currentMin;
					
					  /*
		        	 * back tracking by zheng
		        	 * */
		            tcss343 tc = new tcss343();
		            tcss343.costPath cp = tc.new costPath(); 
					//System.out.println(path);  
					cp.pathA = path;
		            cp.minCost = lowestPossible;
		            //System.out.println(cp.minCost);  
		            list.add(cp); 
				}
	            path = "";
			}
		}
    	//END BRUTE FORCE LOGIC
    	// stop the timer
        Date finishDate = new Date();
        finishTime = finishDate.getTime();
        totalTime += (finishTime - startTime);
        System.out.println("** Results for brute force algorithm on " + theArray.length + "x" + theArray.length + "table: ");
        System.out.println("\tTOTAL TIME: " + totalTime + " ms.\t MINIMUM COST: " + lowestPossible);
        
        /*
         * print the path of lowest cost by zheng
         * */
        if (list.isEmpty()) {
        	System.out.println("\n		Brute force path is from post 1 to post " + (theArray.length));
        } else {
        	for (int i = 0; i < list.size(); i ++) {
            	
            	if (list.get(i).minCost == lowestPossible) {
            	System.out.println( "\n		bruteforce backtracking path: " + list.get(i).pathA );
            	}
			}
		}
        System.out.println();
	}
	
	//divide and conquer solution
	private static void divideAndConquer(int[][] theArray) {
		// timer variables
		long totalTime = 0;
		long startTime = 0;
		long finishTime = 0;

		 // start the timer
        Date startDate = new Date();
        startTime = startDate.getTime();
        
        //START DIVIDE AND CONQUER LOGIC
		int minimum = divideAndConquerHelper(theArray, 0, theArray.length - 1);
		//END DIVIDE AND CONQUER LOGIC
		
		// stop the timer
        Date finishDate = new Date();
        finishTime = finishDate.getTime();
        totalTime += (finishTime - startTime);
        System.out.println("** Results for divide-and-conquer algorithm on " + theArray.length + "x" + theArray.length + "table: ");
        System.out.println("\tTOTAL TIME: " + totalTime + " ms.\t MINIMUM COST: " + minimum);
        System.out.println();
	}
	
	// divide and conquer helper solution
	private static int divideAndConquerHelper(int[][] theArray, int theBeginCol, int theEndCol) {
		if (theBeginCol == theEndCol || theBeginCol + 1 == theEndCol) {
			
			return theArray[theBeginCol][theEndCol];			
		}
		int currentMin = theArray[theBeginCol][theEndCol];

		for (int i = theBeginCol + 1; i < theEndCol; i++) {
			int cost = divideAndConquerHelper(theArray, theBeginCol, i) + divideAndConquerHelper(theArray, i, theEndCol); //call recursive helper
			if (cost < currentMin) {
				currentMin = cost;
			}
		}
		return currentMin;
	}

	//dynamic programming solution
	private static void dynamicProgramming(int[][] theArray) {
		// timer variables
		long totalTime = 0;
		long startTime = 0;
		long finishTime = 0;

		// start the timer
		Date startDate = new Date();
		startTime = startDate.getTime();

		//one dimensional array to keep track of current min count
		int currentMinDistances[] = new int[theArray.length]; 
		//two dimensional array populated for backtracking
		int minBackTrack[][] = new int[theArray.length][theArray.length]; 
		
		//START DYNAMIC PROGRAMMING LOGIC
		// initialize all elements to max possible integer
		for (int i = 0; i < theArray.length; i++) {
			currentMinDistances[i] = Integer.MAX_VALUE;
			for (int j = 0; j < theArray.length; j++) {
				minBackTrack[i][j] = Integer.MAX_VALUE;
			}
			minBackTrack[i][0] = 0;
		}
		currentMinDistances[0] = 0;
		//populate 2D helper array with a "running minimum" count for that specific row/column
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
		//END DYNAMIC PROGRAMMING LOGIC
		
		// stop the timer
        Date finishDate = new Date();
        finishTime = finishDate.getTime();
        totalTime += (finishTime - startTime);
       
        System.out.println("** Results for dynamic programming algorithm on " + theArray.length + "x" + theArray.length + "table: ");
        System.out.println("\tTOTAL TIME: " + totalTime + " ms.\t MINIMUM COST: " + currentMinDistances[theArray.length -1]);
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
		System.out.println("\tPath to yield minimum for cost dynamic programming:");
		backtrack(theArray, theArray.length - 1, theArray.length - 1);
	}
	
	//prints to console a trace stack of the posts one must take to reach the end
	private static void backtrack(int[][] theArray, int theRow, int theCol) {
		int row = theRow;
		int col = theCol;
		if (theRow == theArray.length - 1) {
			System.out.println("\t\tend");
		}
		if (row == 0) {
			System.out.println("\t\tPost " + (row+1) + " to " + (col+1));
			System.out.println("\t\tstart");
			System.out.println();
		} else if (theArray[row - 1][col] != theArray[row][col]) {
			System.out.println("\t\tPost " + (row+1) + " to " + (col+1));
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
//		generateRandomArray(SIZE100ARRAY); 
//		generateRandomArray(SIZE200ARRAY);
//		generateRandomArray(SIZE400ARRAY);
//		generateRandomArray(SIZE600ARRAY);
//		generateRandomArray(SIZE800ARRAY);
		
		//write the instantiated arrays to .txt files
//		writeArrayToFile(SIZE100ARRAY);
//		writeArrayToFile(SIZE200ARRAY);
//		writeArrayToFile(SIZE400ARRAY);
//		writeArrayToFile(SIZE600ARRAY);
//		writeArrayToFile(SIZE800ARRAY);
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
	
	/*
	 * back tracking of brute force by zheng yang
	 * */
	public class costPath {
    	int minCost;
    	String pathA;
    }
	/* **********************************************************************************************************************************************
	 * 															END INITIALIZATION METHODS
	 * 
	 * *********************************************************************************************************************************************
	 */
} // end class

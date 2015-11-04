import java.util.Arrays;
// Question 2 from the practice final exam put into code, solved by dynamic programming


public class YayDynamic {
	public static void main (final String[] theArgs) {
		int[][] O = {
				{4,  5,  2,  9,  12, 1,  2, 7 },
				{7,  3,  5,  21, 2,  4,  9, 0 },
				{3,  6,  7,  8,  12, 2,  3, 4 },
				{2,  6,  4,  8,  5,  15, 4, 7 },
				{14, 4,  6,  8,  3,  7,  3, 12},
				{5,  6,  5,  6,  5,  12, 7, 18},
				{4,  12, 25, 23, 4,  3,  5, 1 },
				{14, 2,  25, 3,  3,  4,  2, 10}
		};
		
		int[][] M = new int[8][8];
		solveDynamic(O, M);
		
		System.out.println("POPULATED O ARRAY:");
		printArrayToConsole(O);
		System.out.println();
		System.out.println("POPULATED M ARRAY:");
		printArrayToConsole(M);
		System.out.println("The minimum is: " + getMin(M));
		
		
	}
	
	private static int getMin(int[][] M) {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < M.length; i++) {
			if (min > M[i][M.length-1]) {
				min = M[i][M.length-1];
			}
		}
		return min;
	}
	
	//solve the problem
	private static void solveDynamic(int[][] O, int[][] M) {
		for (int i = 0; i < O.length; i++) { //initialize first column
			M[i][0] = O[i][0];
		}
		
		for (int j = 1; j < O.length; j++) { //for each column starting with the second
			for (int i = 0; i < O.length; i++) { //for each row
				if (i == 0) {
					M[i][j] = Integer.min(M[i][j-1], M[i+1][j-1]) + O[i][j];
				} else if (i > 0 && i < O.length-1) {
					M[i][j] = Integer.min(M[i-1][j-1], Integer.min(M[i][j-1], M[i+1][j-1])) + O[i][j];
				} else if (i == O.length -1) {
					M[i][j] = Integer.min(M[i-1][j-1], M[i][j-1]) + O[i][j];
				}
				
			}
		}
	}
	
	// prints the array to the console for testing purposes
	private static void printArrayToConsole(final int[][] theArray) {
		System.out.print("    ");
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
				System.out.print(i + "   ");
			} else if (i < 10) {
				System.out.print(i + "   ");
			} else if (i < 100) {
				System.out.print(i + "  ");
			} else if (i < 1000) {
				System.out.print(i + " ");
			} // end print out row indicators
			for (int j = 0; j < theArray.length; j++) { 
				if (theArray[i][j] < 10) {
					System.out.print(theArray[i][j] + "    ");
				} else if (theArray[i][j] < 100) {
					System.out.print(theArray[i][j] + "   ");
				} else if (theArray[i][j] < 1000) {
					System.out.print(theArray[i][j] + "  ");
				}
			}
			System.out.println();
		}
	} // end print array
}

/* Ifunanya Okafor
 * Alexis Flores
 * Course Number: CS2013
 * Section Number: Lecture-03 & Lab-04
 * Description: This class uses the concepts of back-tracking and recursion
 * to find all paths in a n x n grid of 8 different symbols that matches a sequence
 * of characters. However, the chosen path should not have repeating characters (cycling back
 * to a cell on the grid that we've already used), be outside the grid, or, of course, not have
 * characters that aren't on the path, else it will back-track.
 * Other Comments: We additionally added a scanner to the program so that the user could choose
 * the size of their grid. We also gave the user the option to either randomly choose their
 * character sequence, choose the length of the random sequence, or make their own given the list
 * of the symbols they can use.
 */
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;

public class SymbolGrid {
	
	private static char[] SYMBOLS = {'~', '!', '@', '#', '$', '^', '&', '*'};

	public static void findAllPaths(Grid grid, char[] sequence) {

		for (int i = 0; i < (grid.getSize()); i++) {
			for (int j = 0; j < (grid.getSize()); j++) {
				findPathsAt(grid, new Cell(i, j), new Path(), sequence);
			}
		}
		//findPathsAt(grid, new Cell(0,0), new Path(), sequence);
		System.out.println("\n--- finished searching");

	}

	private static void findPathsAt(Grid grid,
									Cell here,
									Path currentPath,
			                        char[] sequence) {

		if (!(cellChecker(grid, here,currentPath,sequence))) {
			return;
		} else {
			currentPath.add(here, sequence[currentPath.getLength()]);
			if (currentPath.getLength() == sequence.length){
				currentPath.display();
			} else {
				cellSearcher(grid,here,currentPath,sequence);
			}
			currentPath.removeLast();
		}


	}


	public static void main(String[] args) {

		Scanner scans = new Scanner(System.in);
		System.out.println("Please enter a non-negative number, n, for this n x n grid: ");
		int numSize = scans.nextInt();
		while (numSize < 0){
			System.out.println("Error. Please try again: ");
			numSize = scans.nextInt();
		}
		Grid grid = new Grid(numSize, SYMBOLS);

		grid.display();
		System.out.println();

		sequenceMenu(grid, scans);
		scans.close();
	}
	// There are three additional helper methods that we added: cellChecker, sequenceMenu, and cellSearcher.
	private static char[] randomSymbolSequence(int length) {
		char[] sequence = new char[length];
		for(int i = 0; i < length; i++) {
			sequence[i] = SYMBOLS[(int)(Math.random()*SYMBOLS.length)];
		}
		return sequence;
	}

	//This method does a strict "and" check if the cell meets the requirements.
	private static boolean cellChecker(Grid grid, Cell here, Path currentPath, char[] sequence){
		return grid.isOnGrid(here) &&
				grid.getSymbolAt(here) == sequence[currentPath.getLength()] &&
				!(currentPath.contains(here));
	}
    //This method contains the sequence menu that allows the user three different options for choosing their sequence.
	private static void sequenceMenu(Grid grid, Scanner scans) {
		System.out.println(" --- Sequence Menu --- ");
		System.out.println("Enter the number associated with your chosen option.");
		System.out.println("1. Randomly choose sequence.");
		System.out.println("2. Choose length of a random sequence.");
		System.out.println("3. Pick your own sequence.");
		System.out.print("Chosen Number: ");
		int menuOption = scans.nextInt();
		switch (menuOption) {
			case 1:
				char[] seq1 = randomSymbolSequence((int) (Math.random()*100));
				System.out.print("sequence: ");
				System.out.println(seq1);
				System.out.println("\npaths:");
				findAllPaths(grid, seq1);
				break;
			case 2:
				System.out.println("Enter a number for the length of the sequence: ");
				int numSize = scans.nextInt();
				while (numSize < 0){
					System.out.println("Error. Please try again: ");
					numSize = scans.nextInt();
				}
				char[] seq2 = randomSymbolSequence(numSize);
				System.out.print("sequence: ");
				System.out.println(seq2);
				System.out.println("\npaths:");
				findAllPaths(grid, seq2);
				break;
			case 3:
				System.out.println(SYMBOLS);
				System.out.println("Make your own sequence from the symbols shown above: ");
				String choice = scans.next();
				char[] seq3 = new char[choice.length()];
				for (int i =  0 ; i < seq3.length; i++ ){
				seq3[i]	= choice.charAt(i);
				}
				System.out.print("sequence: ");
				System.out.println(seq3);
				System.out.println("\npaths:");
				findAllPaths(grid, seq3);

				break;
			default:
				System.out.println("Wrong number... Goodbye.");

		}

	}
    //This method does the recursion by finding paths at the cell's neighbors.
	private static void cellSearcher(Grid grid, Cell here, Path currentPath, char[] sequence){
		findPathsAt(grid, new Cell(here.r + 1, here.c+1), currentPath, sequence);
		findPathsAt(grid, new Cell(here.r + 1, here.c), currentPath, sequence);
		findPathsAt(grid, new Cell(here.r, here.c + 1), currentPath, sequence);
		findPathsAt(grid, new Cell(here.r - 1, here.c - 1), currentPath, sequence);
		findPathsAt(grid, new Cell(here.r - 1, here.c), currentPath, sequence);
		findPathsAt(grid, new Cell(here.r, here.c - 1), currentPath, sequence);
		findPathsAt(grid, new Cell(here.r + 1, here.c - 1), currentPath, sequence);
		findPathsAt(grid, new Cell(here.r - 1, here.c + 1), currentPath, sequence);

	}

}

/* Represents a cell on a grid -- just a convenient way of
 * packaging a pair of indices  */
class Cell {
	int r, c;
	
	Cell(int r, int c) {
		this.r = r;
		this.c = c;
	}

	@Override
	public boolean equals(Object o) {
		Cell cell = (Cell) o;
		return this.r == cell.r && this.c == cell.c;
	}
	
	@Override
	public String toString() {
		return "(" + r + ", " + c + ")";
	}
}

/* Represents a path of cells on a grid of symbols. */
class Path {
	private LinkedList<Cell> cells;
	private LinkedList<Character> symbols;

	Path() {
		cells = new LinkedList<Cell>();
		symbols = new LinkedList<Character>();
	}

	int getLength() {
		return cells.size();
	}

	void add(Cell location, char symbol) {
		cells.addLast(location);
		symbols.addLast(symbol);
	}

	void removeLast() {
		cells.removeLast();
		symbols.removeLast();
	}

	boolean contains(Cell cell) {
		return cells.contains(cell);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < cells.size(); i++) {
			sb.append(symbols.get(i));
			sb.append(cells.get(i).toString());
			sb.append("  ");
		}

		return sb.toString();
	}

	void display() {
		System.out.println(toString());
	}


}

/* Represents a grid of symbols. */
class Grid {
	private char[][] grid;

	Grid(int size, char[] symbols) {
		grid = initGrid(size, symbols);
	}

	private char[][] initGrid(int size, char[] symbols) {
		char[][] symbolGrid = new char[size][size];

		for(int row = 0; row < size; row++) {
			for(int col = 0; col < size; col++) {
				// picks a random symbol for each cell on the grid
				symbolGrid[row][col] = symbols[(int)(Math.random() * symbols.length)];
			}
		}

		return symbolGrid;
	}

	int getSize() {
		return grid.length;
	}

	char getSymbolAt(Cell location) {
		return getSymbolAt(location.r, location.c);
	}

	char getSymbolAt(int r, int c) {
		return grid[r][c];
	}

	boolean isOutside(Cell location) {
		return isOutside(location.r, location.c);
	}

	boolean isOutside(int r, int c) {
		return 0 > r || r >= grid.length || 0 > c || c >= grid[r].length;
	}

	boolean isOnGrid(Cell location) {
		return isOnGrid(location.r, location.c);
	}

	boolean isOnGrid(int r, int c) {
		return 0 <= r && r < grid.length && 0 <= c && c < grid[r].length;
	}

	void display() {
		// Display column indices
		System.out.print("\n    ");
		for(int i = 0; i < grid.length; i++) {
			System.out.print(i + "  ");
		}
		System.out.println();

		// Display grid
		for(int r = 0; r < grid.length; r++) {
			// Display row index
			System.out.print("  " + r + " ");
			for(int c = 0; c < grid[r].length; c++) {
				System.out.print(grid[r][c] + "  ");
			}
			System.out.println();
		}
	}
}

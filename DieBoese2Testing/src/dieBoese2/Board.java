package dieBoese2;

import java.util.Scanner;

/**
 * 
 * @author Floris Wittner, Nicolas Biundo, Ian Reeves
 * @version 1.0.2
 * @date 10.06.2020
 *
 */
public class Board {

	public static void main(String[] args) {
		Board board = new Board(16);
		board.initBoard();
		board.printBoard();
		// Menu menu1 = new Menu();
		Scanner sc = new Scanner(System.in);
		// board.printBoard();
		do {
			board.placeFigure(sc.nextLine(), board.getSymbol());
			// menu1.cls();
		} while (board.isRunning());
		sc.close();
	}

	private int zugCounter = 1;
	private boolean currentPlayer = true;
	private char boardstate[][];
	private boolean isRunning = true;

	// getBoardstate, setBoardstate für Testing
	public char[][] getBoardstate() {
		return boardstate;
	}

	public void setBoardstate(char boardstate[][]) {
		this.boardstate = boardstate;
	}

	public boolean getCurrentPlayer() {
		return currentPlayer;
	}

	protected void initBoard() {
		for (int i = 0; i < boardstate.length; i++) {
			for (int j = 0; j < boardstate.length; j++) {
				boardstate[i][j] = ' ';
			}
		}
	}

	Board(int size) {
		boardstate = new char[size][size];
		for (int i = 0; i < boardstate.length; i++) {
			for (int j = 0; j < boardstate.length; j++) {
				boardstate[i][j] = ' ';
			}
		}
	}

	/**
	 * 
	 * 
	 * prints out the actual Board
	 * 
	 */

	protected void printBoard() {

		int counter = boardstate.length - 1;
		System.out.print("    ");
		for (int i = 65; i < 65 + boardstate.length; i++) {
			System.out.print((char) i + "  ");
		}
		System.out.println();
		while (counter >= 0) {
			if (counter < 9) {
				System.out.print(counter + 1 + "  ");
			} else {
				System.out.print(counter + 1 + " ");
			}
			for (int i = 0; i < boardstate.length; i++) {
				System.out.print("[" + boardstate[i][counter] + "]");
			}
			System.out.print(" ");
			System.out.print(counter + 1);
			counter--;
			System.out.println();
		}
		System.out.print("    ");
		for (int i = 65; i < 65 + boardstate.length; i++) {
			System.out.print((char) i + "  ");
		}
		System.out.println();
	}

	/**
	 * 
	 * @param coordinate
	 * @return true if a figure could be set at coordinate; false if not
	 * @throws Exception
	 * 
	 */
	protected boolean isValidMove(String coordinate) {

		int[] coords;
		try {
			coords = this.convertCoordinate(coordinate);

			if (coords[0] > boardstate.length || coords[1] > boardstate.length) {
				System.err.println("Die Koordinate liegt nicht auf dem Feld");
//				coordinate is out of bounds => return false
				return false;
			} else if (this.boardstate[coords[0] - 1][coords[1] - 1] != ' ') {
//			coordinate is not free => return false
				System.err.println("Dieses Feld ist bereits belegt!");
				return false;
			} else {
//			coordinate is free => return true
				return true;
			}
//		exception and return false
		} catch (Exception e) {
			System.err.println("Ungültige Eingabe!");
			return false;
		}
	}

	protected boolean isRunning() {
		return isRunning;
	}

	/**
	 * convert the coordinate from String to an int[]
	 * 
	 * int [0] --> X-Axis = 1, 2, 3 // int [1] --> Y-Axis = a, b, c
	 * 
	 * 
	 * @param coordinate
	 * @return int[]
	 * @throws Exception
	 * 
	 */
	protected int[] convertCoordinate(String coordinate) throws Exception {

		coordinate = coordinate.toLowerCase();
		int[] coords = new int[2];
		int firstChar, secondChar, thirdChar;

//		checks for coordinate length == 2
		if (coordinate.length() == 2) {

//			Bsp: 1A
			if (((int) coordinate.charAt(0) > 47 && (int) coordinate.charAt(0) < 58)
					&& ((int) coordinate.charAt(1) > 96 && (int) coordinate.charAt(1) < 123)) {
				firstChar = (int) coordinate.charAt(0) - 48;
				secondChar = (int) coordinate.charAt(1) - 96;

				coords[0] = secondChar;
				coords[1] = firstChar;

			}
//			Bsp: A1
			else if (((int) coordinate.charAt(1) > 47 && (int) coordinate.charAt(1) < 58)
					&& ((int) coordinate.charAt(0) > 96 && (int) coordinate.charAt(0) < 123)) {
				firstChar = (int) coordinate.charAt(0) - 96;
				secondChar = (int) coordinate.charAt(1) - 48;

				coords[0] = firstChar;
				coords[1] = secondChar;

			} else {
//				input contains not just numbers and characters => throws exception in isValidMove()
				throw new Exception();
			}

		}
//		checks for coordinate length == 2
		else if (coordinate.length() == 3) {

//				Bsp: 11A
			if (((int) coordinate.charAt(0) > 47 && (int) coordinate.charAt(0) < 58)
					&& ((int) coordinate.charAt(1) > 47 && (int) coordinate.charAt(1) < 58)
					&& ((int) coordinate.charAt(2) > 96 && (int) coordinate.charAt(2) < 123)) {
				firstChar = (int) coordinate.charAt(0) - 48;
				secondChar = (int) coordinate.charAt(1) - 48;
				thirdChar = (int) coordinate.charAt(2) - 96;

				coords[0] = thirdChar;
				coords[1] = firstChar * 10 + secondChar;

			}
//				Bsp: A11
			else if (((int) coordinate.charAt(2) > 47 && (int) coordinate.charAt(2) < 58)
					&& ((int) coordinate.charAt(1) > 47 && (int) coordinate.charAt(1) < 58)
					&& ((int) coordinate.charAt(0) > 96 && (int) coordinate.charAt(0) < 123)) {
				firstChar = (int) coordinate.charAt(0) - 96;
				secondChar = (int) coordinate.charAt(1) - 48;
				thirdChar = (int) coordinate.charAt(2) - 48;

				coords[0] = firstChar;
				coords[1] = secondChar * 10 + thirdChar;

			} else {
//				input contains not just numbers and characters => throws exception in isValidMove()
				throw new Exception();
			}

		} else {
//			if length is not accepted (not 2 or 3) => throws exception in isValidMove()
			throw new Exception();
		}
		return coords;
	}

	/**
	 * 
	 * 
	 * @return true if a player1 has won, false if player2 has won
	 * 
	 */

	boolean whoWon() {
		return currentPlayer;
	}

	/**
	 * 
	 * @param coordinate, symbol: checks if there are 5 same symbols in a row
	 * 
	 */
	
	protected boolean checkWin(String coordinate, char symbol) {

		int[] coords = null;
		try {
			coords = convertCoordinate(coordinate);
		} catch (Exception e) {
		}
		int x = coords[0] - 1;
		int y = coords[1] - 1;

		// check vertical
		for (int i = 0; i < 5; i++) {

			int matches = 0;
			// therefore we check 4 times starting at x-4 if there are 4 matches in a row
			for (int j = 4; j >= 0; j--) {
				int offset = i - j;
				// System.out.println(matches + "+" + j + "+" + symbol + "+" + i);
				// dont forget to stay inside the bounds of the board
				if (x + offset >= 0 && x + offset < boardstate.length) {
					if (boardstate[x + offset][y] == symbol) {
						matches++;
					}
				}
				if (matches == 5) {
					isRunning = false;
					return true;
				}
			}
		}

		// check horizontal
		// we have to check everything from -5 to the left, until +5 to the right
		for (int i = 0; i < 5; i++) {

			int matches = 0;
			// therefore we check 4 times starting at x-4 if there are 4 matches in a row
			for (int j = 4; j >= 0; j--) {
				int offset = i - j;
				// dont forget to stay inside the bounds of the board
				if (y + offset >= 0 && y + offset < boardstate.length) {
					if (boardstate[x][y + offset] == symbol) {
						matches++;
					}
				}
			}
			// if we find 5 machtes, we return true. otherwise we start looking for 4
			// matches on the next position
			if (matches == 5) {
				isRunning = false;
				return true;
			}
		}
		// check diagonal
		// similar to the horizontal check, you have to check everything around the last
		// coin diagonally from -5 to +5
		for (int i = 0; i < 5; i++) {
			int matches2 = 0;
			int matches = 0;
			for (int j = 4; j >= 0; j--) {
				int offset = i - j;
				// now not only 1 variable changes but both, since we move diagonally (this one
				// is the slash check /)
				// don't forget to stay in bounds
				if (y + offset >= 0 && y + offset < boardstate.length && x + offset < boardstate.length
						&& x + offset >= 0) {
					if (boardstate[x + offset][y + offset] == symbol) {
						matches++;
					}
				}
				// this one is the backslash check \
				// don't forget to stay in bounds
				if (y + offset >= 0 && y + offset < boardstate.length && x - offset < boardstate.length
						&& x - offset >= 0) {
					if (boardstate[x - offset][y + offset] == symbol) {
						matches2++;
					}

				}
			}

			// if there is a diagonal / or \ match, we return true
			if (matches == 5 || matches2 == 5) {
				isRunning = false;
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param coordinate, symbol: checks, if units should be deleted with the last
	 *        move
	 * 
	 */

	protected boolean checkDeleted(String coordinate, char symbol) {

		int delCounter = 0;
		int[] coords = null;
		try {
			coords = convertCoordinate(coordinate);
		} catch (Exception e) {
		}
		int x = coords[0] - 1;
		int y = coords[1] - 1;

		char enemysymbol = ' ';
		if (symbol == 'X') {
			enemysymbol = 'O';
		} else {
			enemysymbol = 'X';
		}

		// horizontal links
		if (x - 3 >= 0 && boardstate[x - 3][y] == symbol && boardstate[x - 2][y] == enemysymbol
				&& boardstate[x - 1][y] == enemysymbol) {

			boardstate[x - 1][y] = ' ';
			boardstate[x - 2][y] = ' ';
			delCounter++;
			checkDeleted(coordinate, symbol);
		}
		// horizontal rechts
		if (x + 3 < boardstate.length && boardstate[x + 3][y] == symbol && boardstate[x + 1][y] == enemysymbol
				&& boardstate[x + 2][y] == enemysymbol) {
			boardstate[x + 1][y] = ' ';
			boardstate[x + 2][y] = ' ';
			delCounter++;
			checkDeleted(coordinate, symbol);
		}
		// vertikal unten
		if (y - 3 >= 0 && boardstate[x][y - 3] == symbol && boardstate[x][y - 1] == enemysymbol
				&& boardstate[x][y - 2] == enemysymbol) {
			boardstate[x][y - 1] = ' ';
			boardstate[x][y - 2] = ' ';
			delCounter++;
			checkDeleted(coordinate, symbol);
		}
		// vertikal oben
		if (y + 3 < boardstate.length && boardstate[x][y + 3] == symbol && boardstate[x][y + 1] == enemysymbol
				&& boardstate[x][y + 2] == enemysymbol) {
			boardstate[x][y + 1] = ' ';
			boardstate[x][y + 2] = ' ';
			delCounter++;
			checkDeleted(coordinate, symbol);
		}

		// diagonal unten links --> oben rechts
		if (x + 3 < boardstate.length && y + 3 < boardstate.length && boardstate[x + 3][y + 3] == symbol
				&& boardstate[x + 1][y + 1] == enemysymbol && boardstate[x + 2][y + 2] == enemysymbol) {
			boardstate[x + 1][y + 1] = ' ';
			boardstate[x + 2][y + 2] = ' ';
			delCounter++;
			checkDeleted(coordinate, symbol);
		}

		// diagonal oben rechts --> unten links
		if (x - 3 >= 0 && y - 3 >= 0 && boardstate[x - 3][y - 3] == symbol && boardstate[x - 1][y - 1] == enemysymbol
				&& boardstate[x - 2][y - 2] == enemysymbol) {
			boardstate[x - 1][y - 1] = ' ';
			boardstate[x - 2][y - 2] = ' ';
			delCounter++;
			checkDeleted(coordinate, symbol);
		}

		// diagonal unten rechts --> oben links
		if (x - 3 >= 0 && y + 3 < boardstate.length && boardstate[x - 3][y + 3] == symbol
				&& boardstate[x - 1][y + 1] == enemysymbol && boardstate[x - 2][y + 2] == enemysymbol) {
			boardstate[x - 1][y + 1] = ' ';
			boardstate[x - 2][y + 2] = ' ';
			delCounter++;
			checkDeleted(coordinate, symbol);

		}

		// diagonal oben links --> unten rechts
		if (x + 3 < boardstate.length & y - 3 >= 0 && boardstate[x + 3][y - 3] == symbol
				&& boardstate[x + 1][y - 1] == enemysymbol && boardstate[x + 2][y - 2] == enemysymbol) {
			boardstate[x + 1][y - 1] = ' ';
			boardstate[x + 2][y - 2] = ' ';
			delCounter++;
			checkDeleted(coordinate, symbol);
		}
		if (delCounter > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param coordinate, symbol: places the symbol into the array and gives the
	 *        parameters to both of the "check"-Methods
	 * 
	 */

	void placeFigure(String coordinate, char symbol) {

		int[] coords = null;

		if (isValidMove(coordinate)) {

//			requires try catch because convertCoordinate() throws an exception but isValidMove() already catches it
			try {
				coords = convertCoordinate(coordinate);
			} catch (Exception e) {
			}

			boardstate[coords[0] - 1][coords[1] - 1] = symbol;

			if (zugCounter != 2 && zugCounter != 3) {
				this.printBoard();
			}
			this.checkDeleted(coordinate, symbol);
			this.checkWin(coordinate, symbol);

			currentPlayer = !currentPlayer;
			zugCounter++;
		}
		if (zugCounter == 3) {
			blockBoard();
			this.printBoard();
		} else if (zugCounter == 4) {
			unblockBoard();
			this.printBoard();
		}
	}

	/**
	 * 
	 * 
	 * Blocks the board for the 3rd move as intended from the customer
	 * 
	 */
	void blockBoard() {

		// "inneres" Viereck
		for (int i = 1; i < boardstate.length - 1; i++) {
			for (int j = 1; j < boardstate.length - 1; j++) {
				if (boardstate[i][j] == ' ') {
					boardstate[i][j] = 'R';
				}
			}
		}
		// gerades Spielfeld
		if (boardstate.length % 2 == 0) {
			if (boardstate[0][boardstate.length / 2] == ' ') {
				boardstate[0][boardstate.length / 2] = 'R';
			}
			if (boardstate[0][boardstate.length / 2 - 1] == ' ') {
				boardstate[0][boardstate.length / 2 - 1] = 'R';
			}
			if (boardstate[boardstate.length - 1][boardstate.length / 2] == ' ') {
				boardstate[boardstate.length - 1][boardstate.length / 2] = 'R';
			}
			if (boardstate[boardstate.length - 1][boardstate.length / 2 - 1] == ' ') {
				boardstate[boardstate.length - 1][boardstate.length / 2 - 1] = 'R';
			}
			if (boardstate[boardstate.length / 2][0] == ' ') {
				boardstate[boardstate.length / 2][0] = 'R';
			}
			if (boardstate[boardstate.length / 2 - 1][0] == ' ') {
				boardstate[boardstate.length / 2 - 1][0] = 'R';
			}
			if (boardstate[boardstate.length / 2][boardstate.length - 1] == ' ') {
				boardstate[boardstate.length / 2][boardstate.length - 1] = 'R';
			}
			if (boardstate[boardstate.length / 2 - 1][boardstate.length - 1] == ' ') {
				boardstate[boardstate.length / 2 - 1][boardstate.length - 1] = 'R';
			}
			// ungerades Spielfeld
		} else {
			if (boardstate[0][(boardstate.length - 1) / 2] == ' ') {
				boardstate[0][(boardstate.length - 1) / 2] = 'R';
			}
			if (boardstate[(boardstate.length - 1) / 2][boardstate.length - 1] == ' ') {
				boardstate[(boardstate.length - 1) / 2][boardstate.length - 1] = 'R';
			}
			if (boardstate[boardstate.length - 1][(boardstate.length - 1) / 2] == ' ') {
				boardstate[boardstate.length - 1][(boardstate.length - 1) / 2] = 'R';
			}
			if (boardstate[(boardstate.length - 1) / 2][0] == ' ') {
				boardstate[(boardstate.length - 1) / 2][0] = 'R';
			}

		}
	}

	/**
	 * 
	 * 
	 * unblocks the board after the 3rd move was succesfully made
	 * 
	 */
	void unblockBoard() {

		for (int i = 0; i < boardstate.length; i++) {
			for (int j = 0; j < boardstate.length; j++) {
				if (boardstate[i][j] == 'R') {
					boardstate[i][j] = ' ';
				}
			}
		}
	}

	char getSymbol() {
		if (currentPlayer) {
			return 'X';
		} else {
			return 'O';
		}
	}

	/**
	 * only for KI vs KI
	 * 
	 * 
	 * @param move
	 */
	protected void setEnemyMove(int[] move) {

	}
}
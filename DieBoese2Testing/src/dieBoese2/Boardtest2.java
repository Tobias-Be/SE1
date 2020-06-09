package dieBoese2;

import static org.junit.Assert.*;
import org.junit.Test;

/*
 *  @author Sarah
 *  @date 07.06.20
 */

public class Boardtest2 {

	final int BOARD_MAX_SIZE = 19;
	final int BOARD_MIN_SIZE = 15;

	String coordinate = "3c";
	char symbol = 'X';

	private static char[][] makeHelpState(Board board) {
		char[][] helpstate = new char[board.getBoardstate().length][board.getBoardstate()[0].length];
		for (int i = 0; i < helpstate.length; i++)
			for (int j = 0; j < helpstate[i].length; j++)
				helpstate[i][j] = ' ';
		;
		return helpstate;
	}

	/*
	 * Test-ID w1
	 */
	@Test
	public void checkWin1() {
		Board board = new Board(BOARD_MAX_SIZE);
		board.checkWin(coordinate, symbol);
		assertTrue(board.isRunning());

	}

	/*
	 * Test-ID w2
	 */
	@Test
	public void checkWin2() {
		Board board = new Board(BOARD_MAX_SIZE);
		board.checkWin(coordinate, symbol);
		assertFalse(!board.isRunning());
	}

	/*
	 * Test-ID w3
	 */
	@Test
	public void checkWin3() {
		Board board = new Board(BOARD_MIN_SIZE);
		board.checkWin(coordinate, symbol);
		assertFalse(!board.isRunning());

	}

	/*
	 * Test-ID w4
	 */
	@Test
	public void checkWin4() {
		Board board = new Board(BOARD_MIN_SIZE);
		board.checkWin(coordinate, symbol);
		assertTrue(board.isRunning());

	}

	/*
	 * Test-ID w5
	 */
	@Test(expected = Exception.class)
	public void checkWin5() {
		coordinate = "16a";
		Board board = new Board(BOARD_MIN_SIZE);
		board.checkWin(coordinate, symbol);
		assertTrue(board.isRunning());
	}

	/*
	 * Test-ID w6
	 */
	@Test(expected = Exception.class)
	public void checkWin7() {
		coordinate = "20a";
		Board board = new Board(BOARD_MAX_SIZE);
		board.checkWin(coordinate, symbol);
		assertTrue(board.isRunning());
	}

	/*
	 * Test-ID d1
	 */
	@Test
	public void checkDeleted1() {
		Board board = new Board(BOARD_MAX_SIZE);
		board.checkDeleted(coordinate, symbol);
		assertTrue(board.isRunning());
	}

	/*
	 * Test-ID d2
	 */
	@Test
	public void checkDeleted2() {
		Board board = new Board(BOARD_MAX_SIZE);
		board.checkDeleted(coordinate, symbol);
		assertFalse(!board.isRunning());
	}

	/*
	 * Test-ID d3
	 */
	@Test
	public void checkDeleted3() {
		Board board = new Board(BOARD_MIN_SIZE);
		board.checkDeleted(coordinate, symbol);
		assertTrue(board.isRunning());
	}

	/*
	 * Test-ID d4
	 */
	@Test
	public void checkDeleted4() {
		Board board = new Board(BOARD_MIN_SIZE);
		board.checkDeleted(coordinate, symbol);
		assertFalse(!board.isRunning());
	}

	/*
	 * Test-ID d5
	 */
	@Test(expected = Exception.class)
	public void checkDeleted5() {
		coordinate = "20a";
		Board board = new Board(BOARD_MAX_SIZE);
		board.checkDeleted(coordinate, symbol);
		assertTrue(board.isRunning());
	}

	/*
	 * Test-ID d6
	 */
	@Test(expected = Exception.class)
	public void checkDeleted6() {
		coordinate = "16a";
		Board board = new Board(BOARD_MIN_SIZE);
		board.checkDeleted(coordinate, symbol);
		assertTrue(board.isRunning());
	}

	/*
	 * Test-ID p1
	 */
	@Test
	public void placeFigure1() {
		Board board = new Board(BOARD_MAX_SIZE);
		board.placeFigure(coordinate, symbol);
		assertFalse(!board.isRunning());
	}

	/*
	 * Test-ID p2
	 */
	@Test
	public void placeFigure2() {
		Board board;
		for (int i = BOARD_MIN_SIZE; i <= BOARD_MAX_SIZE; i++) {
			board = new Board(i);
			char[][] helpstate = makeHelpState(board);
			helpstate[2][3] = 'B';
			helpstate[2][11] = 'B';
			helpstate[6][8] = 'B';
			helpstate[5][5] = 'B';
			board.setBoardstate(helpstate);	
			assertFalse(board.isValidMove("12c"));
			board.placeFigure("12c", symbol);
		}
	}
}
package dieBoese2;

import java.util.Scanner;

/**
 * @author Thanh Tran 1921390
 * @version 1.0.0
 */
public class Game {

	protected Player p1, p2;
	protected Board board;
	protected Menu menu;

	public Game(Scanner sc) {
		menu = new Menu();
		// menu settings
		menu.menuLoop(sc);

		/**
		 * generates the Players (PvP or PvAI)
		 */
		generatePlayers(menu.getPvPorPvAI());


		/**
		 * creates the Board with desired size
		 */
		board = new Board(menu.getBoardSize());
	}

	protected void generatePlayers(boolean getPvPorAI){

		p1 = new HumanPlayer('X');

		if(!getPvPorAI)
			p2 = new HumanPlayer('O');
		else
			p2 = new AI('O',menu.getDifficulty());
	}
}
package dieBoese2;
import java.util.Scanner;

/**
 * 
 * @author Nasir Ahmad 
 * @version 1.2.2
 *
 */


public class Main {

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		
			Game game = new Game(input);
		
		for(int i=0;i<3;i++) {
			
			game.p1.blockSpace(game.board,input);
			game.p2.blockSpace(game.board,input);
		}
		
		game.board.printBoard();
		game.p1.makeMove(game.board,input);
		game.board.printBoard();
		game.p2.makeMove(game.board,input);
		
		game.board.blockBoard();
		game.board.printBoard();
		
		game.p1.makeMove(game.board,input);
		game.board.unblockBoard();
		game.board.printBoard();
		game.p2.makeMove(game.board,input);
		
		while(game.board.isRunning()) {
			
			game.p1.makeMove(game.board,input);
			game.board.printBoard();
			
			if(game.board.isRunning()) {
				game.p2.makeMove(game.board,input);
				game.board.printBoard();
			}
			
			System.out.println("Glueckwunsch "+game.board.whoWon()+". Sie haben gewonnen!");
			
			game = new Game(); 
			
		}
		
	}
}

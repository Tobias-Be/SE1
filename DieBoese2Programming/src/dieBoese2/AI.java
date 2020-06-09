package dieBoese2;
/**
 * 
 * @author Floris Wittner
 * @version V 0.1.2.
 *
 */
public class AI extends Player {
int difficulty;
	AI(char figure, int difficulty) {
		super(figure);
		this.difficulty = difficulty;
	}
	public static void main(String[] args) {
		AI test = new AI('B',3);
		Board board = new Board(15);
	 test.generateMove(board);
	}

	@Override
	protected void blockSpace(Board board) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void makeMove(Board board) {
		// TODO Auto-generated method stub
		
	}
	
	protected int getDepth() {
		return difficulty;
	}
	
	protected int[] generateBlockMove(Board board) {
		int blockedField[] = new int[2];

		int x = board.getBoardstate().length+1;
		int y = board.getBoardstate().length+1;
		while (!board.isValidMove(convertCoordinate(x, y))) {
			x= (int) Math.random()*board.getBoardstate().length;
			y= (int) Math.random()*board.getBoardstate().length;
			blockedField[0] = x;
			blockedField[1] = y;

}
		
		return blockedField;
		
	}
	protected int[]generateMove(Board board) {
		int bestmove[] = new int[2];
		int bestScore = -20000;
		char[][] boardAI = board.getBoardstate();
		
		
		int x = boardAI.length+1;
		int y = boardAI.length+1;
		
		
			for(int i = 0 ; i<boardAI.length; i++) {
				
				y = i ;
				
				for(int j = 0; j<boardAI.length; j++) {
			
					x = j;
					
			if(boardAI[x][y] == ' ') {
				boardAI[x][y]= this.figure;
				int score = minimax(boardAI, x, y, getDepth(), -20000, 20000, false);
				if(score > bestScore) {
					bestScore = score;
					bestmove[0]= i;
					bestmove[1]= j;
				}
			}	
			
		}
				}
		return bestmove;
		
	}
	private String convertCoordinate(int x, int y) {
		
	
		String coordinates;
		char xChar = (char)(x+96);
		if (y<10) {
		char yChar = (char)(y+48);
		coordinates = String.valueOf(xChar)+ String.valueOf(yChar);
		
		}else{
			char yChar = (char)((y%10)+48);
			System.out.println(yChar);
			char yyChar = (char)((y/10)+48);
			System.out.println(yyChar);
			coordinates = String.valueOf(xChar)+ String.valueOf(yyChar)+ String.valueOf(yChar);
		}
		
		return coordinates;
		
	}
	
	private int minimax(char[][] boardAI, int xMove, int yMove, int depth, int alpha, int beta, boolean isMaximizing) {
		return 0;
	}
	private int evalBoard(char[][] boardAI, int xMove, int yMove) {
		if(figureInARow(boardAI, xMove, yMove) == 5) { 
		return 20000;
		}
		else if(deleteFigure(boardAI, xMove, yMove)) {
			return 10;
		}
		else if(figureInARow(boardAI, xMove, yMove) == 4){
			return 5;
		}else if(figureInARow(boardAI, xMove, yMove) == 3) {
			return 4;
		}else if(figureInARow(boardAI, xMove, yMove) == 2) {
			return 4;
		}
		else if(middleFigure(boardAI, xMove, yMove)) {
			return 2;
		}
		else 
			return 1;
		
		
	}
	private int figureInARow(char[][] boardAI, int xMove, int yMove) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	private boolean deleteFigure(char[][] boardAI, int xMove, int yMove) {
		// TODO Auto-generated method stub
		return false;
	}
	private boolean middleFigure(char[][] boardAI, int xMove, int yMove) {
		// TODO Auto-generated method stub
		return false;
	}

	

	
}

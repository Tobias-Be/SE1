package dieBoese2;

import java.util.Scanner;

/**
 * 
 * @author Floris Wittner
 * @version V 0.1.2.
 *
 */
public class AI extends Player {
int difficulty;
char enemyFigure;
	AI(char figure, int difficulty) {
		super(figure);
		this.difficulty = difficulty;
		if(this.figure == 'X') {
			enemyFigure = 'O';
		}else{
			enemyFigure = 'X';
		}
	}
	public static void main(String[] args) {
		AI test = new AI('B',3);
		Board board = new Board(15);
	 test.generateMove(board);
	}

	@Override
	protected void blockSpace(Board board, Scanner sc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void makeMove(Board board, Scanner sc) {
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
		int bestScore = Integer.MIN_VALUE;
		char[][] boardAI = board.getBoardstate();
		
		
		int x ;
		int y ;
		
		
			for(int i = 0 ; i<boardAI.length; i++) {				
				y = i ;		
				for(int j = 0; j<boardAI.length; j++) {
					x = j;
			if(boardAI[x][y] == ' ') {
				boardAI[x][y]= this.figure;
				int score = minimax(boardAI, x, y, getDepth(), Integer.MIN_VALUE, Integer.MAX_VALUE, false);
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
		int bestChildScore = Integer.MIN_VALUE;
		int score;
		//abbruch
		if(depth== 0 || figureInARow(boardAI, xMove, yMove)== 5) {
			return evalBoard(boardAI, xMove, yMove);
		}

	
		if (isMaximizing) {
			for (int i = 0; i < boardAI.length; i++) {
				for (int j = 0; j < boardAI.length; j++) {
					if (boardAI[i][j] == ' ') {
						boardAI[i][j] = this.figure;
						score = minimax(boardAI,i , j, depth - 1, alpha, beta, false);
							boardAI[i][j] = ' ';
						bestChildScore = Math.max(bestChildScore, score);
						alpha = Math.max(alpha, score);
						if (beta <= alpha)
							break; 
					}
				}		
			}
			return bestChildScore;
		} else {
			for (int i = 0; i < boardAI.length; i++) {
				for (int j = 0; j < boardAI.length; j++) {
					System.out.println(i + "  +  " + j);
					if (boardAI[i][j] == ' ') { 
						boardAI[i][j] = enemyFigure;
						score = minimax(boardAI,i ,j , depth - 1, alpha, beta, true);
						boardAI[i][j] = ' ';
						bestChildScore = Math.min(bestChildScore, score);
						beta = Math.min(beta, score);
						if (beta <= alpha)
							break;
					}
				}		
			}
			return bestChildScore;
		}		
	}
	private int evalBoard(char[][] boardAI, int xMove, int yMove) {
		if(figureInARow(boardAI, xMove, yMove) == 5) { 
		return Integer.MAX_VALUE;
		}
		else if(deleteFigure(boardAI, xMove, yMove) > 0) {
			return 10* deleteFigure(boardAI, xMove, yMove);
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
	
	
	private int deleteFigure(char[][] boardAI, int xMove, int yMove) {
		// x+ y+
		int deleteFigure = 0;
	if(xMove+3 < boardAI.length && yMove+3 < boardAI.length) {
		if(boardAI[xMove+3][yMove+3] == (this.figure)) {
			if(boardAI[xMove+2][yMove+2]== enemyFigure && boardAI[xMove+1][yMove+1]== enemyFigure ) {
				deleteFigure++;
			}
		}
	}
	// x+ y0
	if(xMove+3 < boardAI.length) {
		if(boardAI[xMove+3][yMove] == (this.figure)) {
			if(boardAI[xMove+2][yMove]== enemyFigure && boardAI[xMove+1][yMove]== enemyFigure ) {
				deleteFigure++;
			}
		}
	}
	// x+ y-
	if(xMove+3 < boardAI.length && yMove-3 > boardAI.length) {
		if(boardAI[xMove+3][yMove-3] == (this.figure)) {
			if(boardAI[xMove+2][yMove-2]== enemyFigure && boardAI[xMove+1][yMove-1]== enemyFigure ) {
				deleteFigure++;
			}
		}
	}
	//x0 y+
	if(yMove+3 < boardAI.length) {
		if(boardAI[xMove][yMove+3] == (this.figure)) {
			if(boardAI[xMove][yMove+2]== enemyFigure && boardAI[xMove][yMove+1]== enemyFigure ) {
				deleteFigure++;
			}
		}
	}
	// x0 y-
	if( yMove-3 > boardAI.length) {
		if(boardAI[xMove][yMove-3] == (this.figure)) {
			if(boardAI[xMove][yMove-2]== enemyFigure && boardAI[xMove][yMove-1]== enemyFigure ) {
				deleteFigure++;
			}
		}
	}
	// x- y+
	if(xMove-3 > boardAI.length && yMove+3 < boardAI.length) {
		if(boardAI[xMove-3][yMove+3] == (this.figure)) {
			if(boardAI[xMove-2][yMove+2]== enemyFigure && boardAI[xMove-1][yMove+1]== enemyFigure ) {
				deleteFigure++;
			}
		}
	}
	// x- y0
	if(xMove-3 > boardAI.length) {
		if(boardAI[xMove-3][yMove] == (this.figure)) {
			if(boardAI[xMove-2][yMove]== enemyFigure && boardAI[xMove-1][yMove]== enemyFigure ) {
				deleteFigure++;
			}
		}
	}
	//x- y-
	if(xMove-3 > boardAI.length && yMove-3 > boardAI.length) {
		if(boardAI[xMove-3][yMove-3] == (this.figure)) {
			if(boardAI[xMove-2][yMove-2]== enemyFigure && boardAI[xMove-1][yMove-1]== enemyFigure ) {
				deleteFigure++;
			}
		}
	}
		return deleteFigure;
	}
	private boolean middleFigure(char[][] boardAI, int xMove, int yMove) {
		// TODO Auto-generated method stub
		return false;
	}

	

	
}

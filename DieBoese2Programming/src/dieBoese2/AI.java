package dieBoese2;

import java.util.Scanner;

/**
 * 
 * @author Floris Wittner
 * @version V 0.2.0.
 *
 */
public class AI extends Player {
	int difficulty;
	char enemyFigure;
	boolean hard; 

	AI(char figure, int difficulty) {
		super(figure);
		this.difficulty = difficulty;
		if(difficulty == 3) {
			hard = true;
		}else {
			hard = false;
		}
		
		if (this.figure == 'X') {
			enemyFigure = 'O';
		} else {
			enemyFigure = 'X';
		}
	}

//	public static void main(String[] args) {
//		AI test = new AI('B', 3);
//		Board board = new Board(15);
//		board.initBoard();
//			test.generateMove(board);
//	}

	@Override
	protected void blockSpace(Board board, Scanner sc) {
		int[] arrayBlock = generateBlockMove(board);
		String myMove = convertCoordinate(arrayBlock[0]+1, arrayBlock[1]+1);
		if (board.isValidMove(myMove)) {
			board.placeFigure(myMove, 'B');
		} else {
			System.out.println("AI hat versucht ein Feld zu blockieren");
			blockSpace(board, sc);
		}

	}

	@Override
	protected void makeMove(Board board, Scanner sc) {
		long start = System.currentTimeMillis();
		int[] arrayBlock = generateMove(board);
		long end =  System.currentTimeMillis();
		if(start-end < 60000 && hard == true) {
			difficulty++;
		}else if(start-end > 120000 && hard == true && difficulty >= 2 ) {
			difficulty--;
		}
		
		String myMove = convertCoordinate(arrayBlock[0]+1, arrayBlock[1]+1);
		if (board.isValidMove(myMove)) {
			board.placeFigure(myMove, figure);
		} else {
			System.out.println("AI hat versuch ein Stein zu legen");
			makeMove(board, sc);
		}

	}

	protected int getDepth() {
		return difficulty;
	}

	protected int[] generateBlockMove(Board board) {
		int blockedField[] = new int[2];

		int x = (int) (Math.random() * board.getBoardstate().length);
		int y = (int) (Math.random() * board.getBoardstate().length);
		
		
			blockedField[0] = x;
			blockedField[1] = y;


System.out.println("x"+ x);
System.out.println("y"+ y);
		return blockedField;

	}

	protected int[] generateMove(Board board) {
		int bestmove[] = new int[2];
		int bestScore = Integer.MIN_VALUE;
		char[][] boardAI = board.getBoardstate().clone();

		int x;
		int y;

		for (int i = 0; i < boardAI.length; i++) {
			x = i;
			for (int j = 0; j < boardAI.length; j++) {
				y = j;
				if (boardAI[x][y] == ' ') {
//					System.out.println((char) boardAI[x][y]);
//					System.out.println(x + " + " + y);
					boardAI[x][y] = this.figure;
					int score = minimax(boardAI, x, y, getDepth(), Integer.MIN_VALUE, Integer.MAX_VALUE, false);
//					System.out.println(score);
					boardAI[x][y] = ' ';
					if (score > bestScore) {
						System.out.println("score > bestscore  " + score + " " + bestScore+ "x  "+ i+ "y  "+ j );
						bestScore = score;
						bestmove[0] = i;
						bestmove[1] = j;
					}
				}

			}
		}
		System.out.println("x " + bestmove[0]+ "y "+ bestmove[1] + "evaluierung  " + evalBoard(boardAI, bestmove[0], bestmove[1], figure));
		return bestmove;

	}
/**
 * needs coordinate from 1-15
 * @param x
 * @param y
 * @return
 */
	private String convertCoordinate(int x, int y) {

		String coordinates;
		char xChar = (char) (x + 96);
		if (y < 10) {
			char yChar = (char) (y + 48);
			coordinates = String.valueOf(xChar) + String.valueOf(yChar);

		} else {
			char yChar = (char) ((y % 10) + 48);
			System.out.println(yChar);
			char yyChar = (char) ((y / 10) + 48);
			System.out.println(yyChar);
			coordinates = String.valueOf(xChar) + String.valueOf(yyChar) + String.valueOf(yChar);
		}
		System.out.println(coordinates);
		return coordinates;

	}

	private int minimax(char[][] boardAI, int xMove, int yMove, int depth, int alpha, int beta, boolean isMaximizing) {
		int bestChildScore;
		int score;
		char figureRow = figure;
		// abbruch
//		if(isMaximizing) {
//			figureRow = figure;
//		}else {
//			figureRow = enemyFigure;
//		}
		if (depth == 0 || figureInARow(boardAI, xMove, yMove, figure) == 5) {
//			System.out.println(evalBoard(boardAI, xMove, yMove, figureRow));
			return evalBoard(boardAI, xMove, yMove, figureRow);
			
		}

		if (isMaximizing) {
			bestChildScore = Integer.MIN_VALUE;
			for (int i = 0; i < boardAI.length; i++) {
				for (int j = 0; j < boardAI.length; j++) {
					if (boardAI[i][j] == ' ') {
						boardAI[i][j] = this.figure;
						score = minimax(boardAI, i, j, depth - 1, alpha, beta, false);
//						System.out.println(score);
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
			bestChildScore  = Integer.MAX_VALUE;
			for (int i = 0; i < boardAI.length; i++) {
				for (int j = 0; j < boardAI.length; j++) {
//					System.out.println(i + "  +  " + j);
					if (boardAI[i][j] == ' ') {
						boardAI[i][j] = enemyFigure;
						score = minimax(boardAI, i, j, depth - 1, alpha, beta, true);
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

	private int evalBoard(char[][] boardAI, int xMove, int yMove, char figureRow) {
		char figureEnemyRow;
		int eval = 0;
		if(figureRow== figure) {
		figureEnemyRow = enemyFigure;}
		else {figureEnemyRow = figure;}
		if (figureInARow(boardAI, xMove, yMove, figureEnemyRow) == 5) {
			eval -= Integer.MAX_VALUE-100;
		}  if (deleteFigure(boardAI, xMove, yMove) > 0) {
			eval -= 10 * deleteFigure(boardAI, xMove, yMove);
		}  if (figureInARow(boardAI, xMove, yMove, figureEnemyRow) == 4) {
			eval -= 5;
		}  if (figureInARow(boardAI, xMove, yMove, figureEnemyRow) == 3) {
			eval -= 4;
		}  if (figureInARow(boardAI, xMove, yMove, figureEnemyRow) == 2) {
			eval -= 4;
		}

		if (figureInARow(boardAI, xMove, yMove, figureRow) == 5) {
			eval += Integer.MAX_VALUE-100;
		}  if (deleteFigure(boardAI, xMove, yMove) > 0) {
			eval += 10 * deleteFigure(boardAI, xMove, yMove);
		}  if (figureInARow(boardAI, xMove, yMove, figureRow) == 4) {
			eval += 5;
		}  if (figureInARow(boardAI, xMove, yMove, figureRow) == 3) {
			eval += 4;
		}  if (figureInARow(boardAI, xMove, yMove, figureRow) == 2) {
			eval += 4;
		}  if (middleFigure(boardAI, xMove, yMove)) {
			eval += 2;
		} else {
			eval += 1;}
			return eval;
	}

	private int figureInARow(char[][] boardAI, int xMove, int yMove, char figureRow) {
		int highestRow= 1;
		int row= 1;
		// x ->
		if(0 < xMove-1 && boardAI[xMove-1][yMove]== figureRow) {
			row++;
			if(0 < xMove-2 &&boardAI[xMove-2][yMove]== figureRow) {
				row++;
				if(0 < xMove-3 && boardAI[xMove-3][yMove]== figureRow) {
					row++;
					if(0 < xMove-4 &&boardAI[xMove-4][yMove]== figureRow) {
						row++;
					}
				}	
			}
		}
		if(boardAI.length > xMove+1 &&boardAI[xMove+1][yMove]== figureRow) {
			row++;
			if(boardAI.length > xMove+2 && boardAI[xMove+2][yMove]== figureRow) {
				row++;
				if(boardAI.length > xMove+3 &&boardAI[xMove+3][yMove]== figureRow) {
					row++;
					if(boardAI.length > xMove+4 &&boardAI[xMove+4][yMove]== figureRow) {
						row++;
					}
				}	
			}
		}
		if(row> highestRow) {
			highestRow = row;
		}
		//y 
		if(0 < yMove-1 && boardAI[xMove][yMove-1]== figureRow) {
			row++;
			if(0 < yMove-2 && boardAI[xMove][yMove-2]== figureRow) {
				row++;
				if(0 < yMove-3 &&boardAI[xMove][yMove-3]== figureRow) {
					row++;
					if(0 < yMove-4 && boardAI[xMove][yMove-4]== figureRow) {
						row++;
					
					}
				}	
			}
		}
		if(boardAI.length > yMove+1 && boardAI[xMove][yMove+1]== figureRow) {
			row++;
			if(boardAI.length > yMove+2 && boardAI[xMove][yMove+2]== figureRow) {
				row++;
				if(boardAI.length > yMove+3 && boardAI[xMove][yMove+3]== figureRow) {
					row++;
					if(boardAI.length > yMove+4 &&boardAI[xMove][yMove+4]== figureRow) {
						row++;
						
					}
				}	
			}
		}
		
		if(row> highestRow) {
			highestRow = row;
		}
		
		if(0 < yMove-1 &&0 < xMove-1 && boardAI[xMove-1][yMove-1]== figureRow) {
			row++;
			if(0 < yMove-2 &&0 < xMove-2 && boardAI[xMove-2][yMove-2]== figureRow) {
				row++;
				if(0 < yMove-3 &&0 < xMove-3 &&boardAI[xMove-3][yMove-3]== figureRow) {
					row++;
					if(0 < yMove-4 &&0 < xMove-4 &&boardAI[xMove-4][yMove-4]== figureRow) {
						row++;
					}
				}	
			}
		}
		
		if(boardAI.length > xMove+1 && boardAI.length > yMove+1 &&boardAI[xMove+1][yMove+1]== figureRow) {
			row++;
			if(boardAI.length > xMove+2 && boardAI.length > yMove+2 &&boardAI[xMove+2][yMove+2]== figureRow) {
				row++;
				if(boardAI.length > xMove+3 && boardAI.length > yMove+3 &&boardAI[xMove+3][yMove+3]== figureRow) {
					row++;
					if(boardAI.length > xMove+4 && boardAI.length > yMove+4 && boardAI[xMove+4][yMove+4]== figureRow) {
						row++;
					}
				}	
			}
		}
		if(row> highestRow) {
			highestRow = row;
		}
		
		if(boardAI.length > xMove+1 && 0 < yMove-1 && boardAI[xMove+1][yMove-1]== figureRow) {
			row++;
			if(boardAI.length > xMove+2 && 0 < yMove-2 && boardAI[xMove+2][yMove-2]== figureRow) {
				row++;
				if(boardAI.length > xMove+3 && 0 < yMove-3 &&boardAI[xMove+3][yMove-3]== figureRow) {
					row++;
					if(boardAI.length > xMove+4 && 0 < yMove-4 && boardAI[xMove+4][yMove-4]== figureRow) {
						row++;
					}
				}	
			}
		}
		
		if( 0 < xMove-1 && boardAI.length > yMove+1 &&boardAI[xMove-1][yMove+1]== figureRow) {
			row++;
			if(0 < xMove-2 && boardAI.length > yMove+2 && boardAI[xMove-2][yMove+2]== figureRow) {
				row++;
				if(0 < xMove-3 && boardAI.length > yMove+3 && boardAI[xMove-3][yMove+3]== figureRow) {
					row++;
					if(0 < xMove-4 && boardAI.length > yMove+4 &&boardAI[xMove-4][yMove+4]== figureRow) {
						row++;
					}
				}	
			}
		}
		if(row> highestRow) {
			highestRow = row;
		}
		
		return highestRow;
	}

	private int deleteFigure(char[][] boardAI, int xMove, int yMove) {
		// x+ y+
		int deleteFigure = 0;
		if (xMove + 3 < boardAI.length && yMove + 3 < boardAI.length) {
			if (boardAI[xMove + 3][yMove + 3] == (this.figure)) {
				if (boardAI[xMove + 2][yMove + 2] == enemyFigure && boardAI[xMove + 1][yMove + 1] == enemyFigure) {
					deleteFigure++;
				}
			}
		}
		// x+ y0
		if (xMove + 3 < boardAI.length) {
			if (boardAI[xMove + 3][yMove] == (this.figure)) {
				if (boardAI[xMove + 2][yMove] == enemyFigure && boardAI[xMove + 1][yMove] == enemyFigure) {
					deleteFigure++;
				}
			}
		}
		// x+ y-
		if (xMove + 3 < boardAI.length && yMove - 3 > boardAI.length) {
			if (boardAI[xMove + 3][yMove - 3] == (this.figure)) {
				if (boardAI[xMove + 2][yMove - 2] == enemyFigure && boardAI[xMove + 1][yMove - 1] == enemyFigure) {
					deleteFigure++;
				}
			}
		}
		// x0 y+
		if (yMove + 3 < boardAI.length) {
			if (boardAI[xMove][yMove + 3] == (this.figure)) {
				if (boardAI[xMove][yMove + 2] == enemyFigure && boardAI[xMove][yMove + 1] == enemyFigure) {
					deleteFigure++;
				}
			}
		}
		// x0 y-
		if (yMove - 3 > boardAI.length) {
			if (boardAI[xMove][yMove - 3] == (this.figure)) {
				if (boardAI[xMove][yMove - 2] == enemyFigure && boardAI[xMove][yMove - 1] == enemyFigure) {
					deleteFigure++;
				}
			}
		}
		// x- y+
		if (xMove - 3 > boardAI.length && yMove + 3 < boardAI.length) {
			if (boardAI[xMove - 3][yMove + 3] == (this.figure)) {
				if (boardAI[xMove - 2][yMove + 2] == enemyFigure && boardAI[xMove - 1][yMove + 1] == enemyFigure) {
					deleteFigure++;
				}
			}
		}
		// x- y0
		if (xMove - 3 > boardAI.length) {
			if (boardAI[xMove - 3][yMove] == (this.figure)) {
				if (boardAI[xMove - 2][yMove] == enemyFigure && boardAI[xMove - 1][yMove] == enemyFigure) {
					deleteFigure++;
				}
			}
		}
		// x- y-
		if (xMove - 3 > boardAI.length && yMove - 3 > boardAI.length) {
			if (boardAI[xMove - 3][yMove - 3] == (this.figure)) {
				if (boardAI[xMove - 2][yMove - 2] == enemyFigure && boardAI[xMove - 1][yMove - 1] == enemyFigure) {
					deleteFigure++;
				}
			}
		}
		return deleteFigure;
	}

	private boolean middleFigure(char[][] boardAI, int xMove, int yMove) {
		if (boardAI.length / 2 - xMove < 5 && boardAI.length - yMove < 5) {
			return true;
		}
		return false;
	}

}

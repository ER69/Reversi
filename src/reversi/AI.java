/**
 * 
 */
package reversi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import reversi.Tile.State;

/**
 * @author kAG0
 *
 */
public class AI {
	private static final double FORFEIT_WEIGHT = 1;
	private static final double MOBILITY_WEIGHT = 1;
	private static final double FRONTIER_WEIGHT = 1;
	private static final double STABILITY_WEIGHT = 1;
	private static final double SCORE_WEIGHT = 1;
	private static final int MAX_DEPTH = 5;
	private static final int VERY_LOW = Integer.MIN_VALUE-200;
	private static final int VERY_HIGH = Integer.MAX_VALUE-200;
	
	private Board gameBoard;
	
	public AI(Board gameBoard) {
		this.gameBoard = gameBoard;
	}

	/**
	 * 
	 * @param board
	 * @param player
	 * @return the best move the given player can make on this board
	 */
	public static Move getBestMove(Board board, Tile.State player){
		// TODO Auto-generated method stub
		Move bestMove;
		if(player == State.WHITE) bestMove = new Move(0, 0, player, VERY_LOW);
		else bestMove = new Move(0, 0, player, VERY_HIGH);
		Set<Move> possibleMoves = getPossibleMoves(board, player);
		Board temp;

		for (Move currentMove : possibleMoves) {
			// apply the current move
			temp = new Board(board);
			if(temp.isValidMove(currentMove.getX(), currentMove.getY(), player))
				temp.flipTiles(currentMove.getX(), currentMove.getY(), player);
			
			if (player == State.WHITE) {
				currentMove.setScore(getBestMove(temp, MAX_DEPTH, VERY_LOW,
						VERY_HIGH, player));// or is it opposite player?
				if (currentMove.getScore() >= bestMove.getScore())
					bestMove = currentMove;
			} else {
				currentMove.setScore(getBestMove(temp, MAX_DEPTH, VERY_LOW,
						VERY_HIGH, player));// or is it opposite player?
				if (currentMove.getScore() <= bestMove.getScore())
					bestMove = currentMove;
			}

		}
		return bestMove;
	}
	/**
	 * 
	 * @param board
	 * @param player
	 * @return the best move the given player can make on this board
	 */
	private static int getBestMove(Board node, int depth, int a, int b,
			State maximizingPlayer) {
		// TODO Auto-generated method stub
		if (depth == 0 || Game.isGameOver(node))
			return rateBoard(node);
		Set<Move> possibleMoves = getPossibleMoves(node, maximizingPlayer);
		// possibleMoves = the moves maximizingPlayer can make
		Board temp;
		// white will always try to get the highest board score aka white is max
		if (maximizingPlayer == State.WHITE) {
			for (Move move : possibleMoves) {
				// apply the current move
				temp = new Board(node);
				if(temp.isValidMove(move.getX(), move.getY(), maximizingPlayer))
					temp.flipTiles(move.getX(), move.getY(), maximizingPlayer);
				
				a = max(a, getBestMove(temp, depth - 1, a, b,
								Tile.getOppositeState(maximizingPlayer)));
				if (b <= a)
					break; // (* Beta cut-off *)
			}
			return a;
		}
		// black will always try to get the lowest board score aka black is min
		else {
			for (Move move : possibleMoves) {
				// apply the current move
				temp = new Board(node);
				if(temp.isValidMove(move.getX(), move.getY(), maximizingPlayer))
					temp.flipTiles(move.getX(), move.getY(), maximizingPlayer);
				
				b = min(b, getBestMove(temp, depth - 1, a, b,
								Tile.getOppositeState(maximizingPlayer)));
				if (b <= a)
					break; // (* Alpha cut-off *)
			}
			return b;
		}
	}
	
	private static int max(int h, int k){
		if(h>k)
			return k;
		else return h;
	}
	private static int min(int h, int k){
		if(h<k)
			return k;
		else return h;
	}
	
	/**
	 *
	 * @param board
	 * @return a very positive number means the board is favorable for white
	 * 		while a very negative number means the board is favorable for black
	 */
	private static int rateBoard(Board board){
		int rating = 0;
		rating += getForfeit(board) * FORFEIT_WEIGHT;
		rating += getMobility(board) * MOBILITY_WEIGHT;
		rating += getFrontier(board) * FRONTIER_WEIGHT;
		rating += getStability(board) * STABILITY_WEIGHT;
		rating += getScore(board) * SCORE_WEIGHT;
		return rating;
	}
	
	/**
	 * -1 if white would forfeit a turn on this board, 1 if black would forfeit, else 0
	 * @param board
	 * @return -1 if white would forfeit a turn on this board, 1 if black would forfeit, else 0
	 */
	private static int getForfeit(Board board){
int rating = 0;
		
		return rating;
	}
	
	/**
	 * rating based on how many moves each player can make
	 * @param board
	 * @return number of moves white can make - num moves black can make
	 */
	private static int getMobility(Board board){
int rating = 0;
		
		return rating;
	}
	
	/**
	 * rating based on how many disks are next to empty spaces
	 * @param board
	 * @return
	 */
	private static int getFrontier(Board board){
int rating = 0;
		
		return rating;
	}
	/**
	 * rating based on how many disks are unflippable
	 * @param board
	 * @return
	 */
	private static int getStability(Board board){
int rating = 0;
		
		return rating;
	}
	
	/**
	 * rating based on the current score of the game
	 * @param board
	 * @return
	 */
	private static int getScore(Board board){
int rating = 0;
		
		return rating;
	}
	
	private static Set<Move> getPossibleMoves(Board board) {
		//Set of all possible moves that white or black can make at this turn.
		Set<Move> possibleMoves = new HashSet<Move>();
		
		//Check each tile on the board.
		for (int i = 0; i < board.SIZE; i++) {
			for (int j = 0; j < board.SIZE; j++) {
				//Look at only blank tiles.
				if (board.getTile(i, j).getState() == Tile.State.BLANK) {
					//Is it a valid move for player white or player black?
					boolean whiteValidMove = board.isValidMove(i,j,Tile.State.WHITE);
					boolean blackValidMove = board.isValidMove(i,j,Tile.State.BLACK);
					
					if (whiteValidMove) {
						//Create the move for white and add to the set of moves.
						Move possibleMove = new Move(i,j,Tile.State.WHITE);
						possibleMoves.add(possibleMove);
					}
					if (blackValidMove) {
						//Create the move for black and add to the set of moves.
						Move possibleMove = new Move(i,j,Tile.State.BLACK);
						possibleMoves.add(possibleMove);
					}
				}
			}
		}
		
		return possibleMoves;
	}
	/**
	 * 
	 * @param board
	 * @param player
	 * @return set of all possible moves for given player
	 */
	private static Set<Move> getPossibleMoves(Board board, State player) {
		//Set of all possible moves that white or black can make at this turn.
		Set<Move> possibleMoves = new HashSet<Move>();
		
		//Check each tile on the board.
		for (int i = 0; i < board.SIZE; i++) {
			for (int j = 0; j < board.SIZE; j++) {
				//Look at only blank tiles.
				if (board.getTile(i, j).getState() == Tile.State.BLANK) {
					//Is it a valid move for player
					boolean ValidMove = board.isValidMove(i,j,player);
					//boolean blackValidMove = board.isValidMove(i,j,Tile.State.BLACK);
					
					if (ValidMove) {
						//Create the move for white and add to the set of moves.
						Move possibleMove = new Move(i,j,player);
						possibleMoves.add(possibleMove);
					}
//					if (blackValidMove) {
//						//Create the move for black and add to the set of moves.
//						Move possibleMove = new Move(i,j,Tile.State.BLACK);
//						possibleMoves.add(possibleMove);
//					}
				}
			}
		}
		
		return possibleMoves;
	}
	
}

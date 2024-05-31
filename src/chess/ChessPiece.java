package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece  extends  Piece{

	private Color color;
	private int moveCount;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	public int getmoveCount() {
		return moveCount;
	}
	
	public void increseMoveCount() {
		moveCount++;
	}
	public void decreseMoveCount() {
		moveCount--;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
		protected boolean isThereOppnentPiece(Position postion) {
			ChessPiece p =(ChessPiece)getBoard().piece(postion);
			return p !=null && p.getColor() != color;
		}


	
	
	
	
	
}

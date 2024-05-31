package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

	public Bishop(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getCollumns()];

		Position p = new Position(0, 0);

		
		// NOROESTE
		p.setValues(position.getRow() - 1, position.getCollum()-1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getCollum()] = true;
			p.setValues(p.getRow()- 1, p.getCollum() -1);
		}
		if (getBoard().positionExists(p) && isThereOppnentPiece(p)) {
			mat[p.getRow()][p.getCollum()] = true;
		}
		// SUDESTE
		p.setValues(position.getRow() +1, position.getCollum() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getCollum()] = true;
			p.setValues(p.getRow()+ 1, p.getCollum() +1);
		}
		if (getBoard().positionExists(p) && isThereOppnentPiece(p)) {
			mat[p.getRow()][p.getCollum()] = true;
		}
		// NORDESTE
		p.setValues(position.getRow() -1, position.getCollum() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getCollum()] = true;
			p.setValues(p.getRow()- 1, p.getCollum() +1);
		}
		if (getBoard().positionExists(p) && isThereOppnentPiece(p)) {
			mat[p.getRow()][p.getCollum()] = true;
		}
		// SUDOESTE
		p.setValues(position.getRow() + 1, position.getCollum()- 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getCollum()] = true;
			p.setValues(p.getRow()+ 1, p.getCollum() -1);
		}
		if (getBoard().positionExists(p) && isThereOppnentPiece(p)) {
			mat[p.getRow()][p.getCollum()] = true;
		}

		return mat;
	}

}

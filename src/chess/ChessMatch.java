package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Rook;

public class ChessMatch {

	private Board board;
	private Color currentPlay;
	private int turn;
	private boolean check;
	private boolean checkMate;

	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlay = Color.WHITE;
		initialSetup();

	}

	public Color getCurrentPlay() {
		return currentPlay;
	}

	public boolean getCheck() {
		return check;
	}

	public int getTurn() {
		return turn;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public ChessPiece[][] getPieces() {

		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getCollumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getCollumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}

		}
		return mat;

	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validadateSourcePosition(position);
		return board.piece(position).possibleMoves();

	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validadateSourcePosition(source);
		validadatTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);

		if (testCheck(currentPlay)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}

		check = (testCheck(opponent(currentPlay))) ? true : false;

		if (testCheckMate(opponent(currentPlay))) {
			checkMate = true;
		}
		else {
			nextTurn();
		}
		return (ChessPiece) capturedPiece;
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece)board.removePiece(source);
		p.increseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreseMoveCount();
		board.placePiece(p, target);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);

		}

	}

	private void validadateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source postiion");
		}

		if (currentPlay != ((ChessPiece) board.piece(position)).getColor()) {

			throw new ChessException("The chose piece is not yours");
		}

		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");

		}
	}

	private void validadatTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chose piece can't move to target position");
		}
	}

	private void nextTurn() {
		turn++;
		currentPlay = (currentPlay == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;

	}

	private ChessPiece king(Color color) {

		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}

	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getCollum()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getCollumns(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece) p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	private void initialSetup() {
		 placeNewPiece('a', 1, new Rook(board, Color.WHITE));	   
	        placeNewPiece('e', 1, new King(board, Color.WHITE));;
	        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
	        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
	        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
	        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
	        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
	        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
	        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
	        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
	        placeNewPiece('h', 2, new Pawn(board, Color.WHITE ));
	        
	        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
	        placeNewPiece('e', 8, new King(board, Color.BLACK));
	        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
	        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
	        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
	        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
	        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
	        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
	        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
	        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
	        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));

	}

}
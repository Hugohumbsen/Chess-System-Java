package boardgame;

public class Board {
	
	private int rows;
	private int collumns;
	private Piece [][] pieces;
	
	public Board(int rows, int collumns) {
		if (rows <1 || collumns <1) {
			throw new BoardException("Erro creating board : there must be at least 1 row and 1 column");
		}
		this.rows = rows;
		this.collumns = collumns;
		pieces = new Piece [rows][collumns];
	}

	public int getCollumns() {
		return collumns;
	}

	

	public Piece[][] getPieces() {
		return pieces;
	}

	public void setPieces(Piece[][] pieces) {
		this.pieces = pieces;
	}
	
	public Piece piece (int row, int column) {
		if(!positionExists(row,column)) {
			
			throw new BoardException("Position not on the board");
		}
		return pieces[row][column];
	}
	public Piece piece(Position position) {
		if(!positionExists(position)) {
			
			throw new BoardException("Position not on the board");
		}
		return pieces [position.getRow()][position.getCollum()];
	}

	public int getRows() {
		return rows;
	}

	
	public void placePiece(Piece piece, Position position) {
		
		if(thereIsAPiece(position)) {
			throw new BoardException("There is alredy a piece on position "+ position);
		}
		pieces[position.getRow()] [position.getCollum()]  = piece;
		piece.position = position;
		
	}
	
	public Piece removePiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		if(piece(position) == null) {
			return null;
		}
		Piece aux=piece(position);
		aux.position= null; 
		pieces[position.getRow()][position.getCollum()] =null;
		return aux;
	}
	
	private boolean positionExists(int row, int collumn) {
		return row >= 0 && row < rows && collumn >= 0 && collumn < collumns;

	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getCollum());
	}
	
	public boolean thereIsAPiece(Position position) {
			if(!positionExists(position)) {
			
			throw new BoardException("Position not on the board");
		}
		return piece (position) !=null;
		
		
	}
}

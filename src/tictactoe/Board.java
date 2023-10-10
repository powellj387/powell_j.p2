package tictactoe;

import java.util.*;

public class Board implements Cloneable{
    int MAX_ROWS = 3;
    int MAX_COLUMNS = 3;
    private HashMap<Position, Piece> board;

    public Board() {
        board = new HashMap<>();
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int column = 0; column < MAX_COLUMNS; column++) {
                board.put(new Position(row, column), Piece.NONE);
            }
        }
    }

    @Override
    public Board clone() throws CloneNotSupportedException {
       Board cloneBoard = (Board) super.clone();
       cloneBoard.board = new HashMap<>(board);
       return cloneBoard;
    }

    public enum Piece {

        NONE("-"), X("X"), O("O");

        Piece(String s) {

        }

        public String getAbbrev(){
            return abbrev;
        }
    }

    public static class Position {
        int row;
        int column;

        public Position(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public String toString() {
            return row + ", " + column;
        }

        public int getColumn() {
            return column;
        }

        public int getRow() {
            return row;
        }

        @Override
        public boolean equals(Object otherObject) {
            boolean isEqual = false;
            if (this == otherObject) {
                isEqual = true;
            } else if (otherObject != null && getClass() == otherObject.getClass()) {
                Position otherPosition = (Position) otherObject;
                isEqual = (row == otherPosition.row && column == otherPosition.column);
            }
            return isEqual;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }

        @Override
        public Position clone() {
            return new Position(row, column);
        }
    }

    public enum State {
        DRAW, INCOMPLETE, OWINS, XWINS;
    }

    public boolean equals(Object otherObject) {
        boolean isEqual = false;
        if (this == otherObject) {
            isEqual = true;
        } else if (otherObject != null && getClass() == otherObject.getClass()) {
            Board otherBoard = (Board) otherObject;
            isEqual = Objects.equals(board, otherBoard.board);
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int column = 0; column < MAX_COLUMNS; column++) {
                sb.append(getPiece(new Position(row, column)).getAbrev());
                if (column < MAX_COLUMNS - 1) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void playPiece(Position position, Piece piece) throws IllegalMoveException {
        if (!isValidPosition(position)) {
            throw new IllegalMoveException();
        }
        board.put(position, piece);
    }

    public boolean isValidPosition(Position position) {
        boolean returnVal = false;

        if (position != null) {
            returnVal = position.getRow() >= 0 && position.getRow() < MAX_ROWS &&
                    position.getColumn() >= 0 && position.getColumn() < MAX_COLUMNS;
        }
        return returnVal;
    }

    public Piece getPiece(Position position) {
        return board.get(position);
    }

    public static Board.State getGameState(Board board) {
        for (int row = 0; row < 3; row++) {
            if (board.getPiece(new Board.Position(row, 0)) == Board.Piece.X &&
                    board.getPiece(new Board.Position(row, 1)) == Board.Piece.X &&
                    board.getPiece(new Board.Position(row, 2)) == Board.Piece.X) {
                return Board.State.XWINS; // X wins horizontally
            } else if (board.getPiece(new Board.Position(row, 0)) == Board.Piece.O &&
                    board.getPiece(new Board.Position(row, 1)) == Board.Piece.O &&
                    board.getPiece(new Board.Position(row, 2)) == Board.Piece.O) {
                return Board.State.OWINS; // O wins horizontally
            }
        }

        for (int col = 0; col < 3; col++) {
            if (board.getPiece(new Board.Position(0, col)) == Board.Piece.X &&
                    board.getPiece(new Board.Position(1, col)) == Board.Piece.X &&
                    board.getPiece(new Board.Position(2, col)) == Board.Piece.X) {
                return Board.State.XWINS; // X wins vertically
            } else if (board.getPiece(new Board.Position(0, col)) == Board.Piece.O &&
                    board.getPiece(new Board.Position(1, col)) == Board.Piece.O &&
                    board.getPiece(new Board.Position(2, col)) == Board.Piece.O) {
                return Board.State.OWINS; // O wins vertically
            }
        }

        if (board.getPiece(new Board.Position(0, 0)) == Board.Piece.X &&
                board.getPiece(new Board.Position(1, 1)) == Board.Piece.X &&
                board.getPiece(new Board.Position(2, 2)) == Board.Piece.X ||
                board.getPiece(new Board.Position(0, 2)) == Board.Piece.X &&
                        board.getPiece(new Board.Position(1, 1)) == Board.Piece.X &&
                        board.getPiece(new Board.Position(2, 0)) == Board.Piece.X) {
            return Board.State.XWINS; // X wins diagonally
        } else if (board.getPiece(new Board.Position(0, 0)) == Board.Piece.O &&
                board.getPiece(new Board.Position(1, 1)) == Board.Piece.O &&
                board.getPiece(new Board.Position(2, 2)) == Board.Piece.O ||
                board.getPiece(new Board.Position(0, 2)) == Board.Piece.O &&
                        board.getPiece(new Board.Position(1, 1)) == Board.Piece.O &&
                        board.getPiece(new Board.Position(2, 0)) == Board.Piece.O) {
            return Board.State.OWINS; // O wins diagonally
        }
// Check for a draw or incomplete game
        if (board.emptyPositions().isEmpty()) {
                return Board.State.DRAW; // It's a draw
                } else {
                return Board.State.INCOMPLETE; // The game is still in progress
                }
    }



public Collection<Position> emptyPositions() {
        List<Position> emptyPositions = new ArrayList<>();
        for (Map.Entry<Position, Piece> entry : board.entrySet()) {
            if (entry.getValue() == Piece.NONE) {
                emptyPositions.add(entry.getKey());
            }
        }
        return emptyPositions;
    }
}
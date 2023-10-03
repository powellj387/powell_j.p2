package tictactoe;

import java.util.*;

public class Board {
    private ULHashMap<Position, Piece> board;
    private final int MAX_COLUMNS = 3;
    private final int MAX_ROWS = 3    ;
    public enum Piece {
        NONE, X, O;
    }
    public class Position{
        int column;
        int row;

        public Position(int row, int column){
            this.row = row;
            this.column = column;
        }

        public java.lang.String toString(){
            StringBuilder string =  new StringBuilder();
            string.append(row);
            string.append(", ");
            string.append(column);
            return string.toString();
        }

        public int getColumn() {
            return column;
        }

        public int getRow() {
            return row;
        }

        public boolean equals(java.lang.Object otherObject){
            boolean returnValue;
            //returns false if the object taken in is either null or doesn't
            //belong to the position class
            if(otherObject==null||getClass() != otherObject.getClass()){
                returnValue = false;
                //return true if the objects are the same
            } else if (this==otherObject) {
                returnValue = true;
            }else{
                //return true if the row/column of the object taken in matches
                //the other
                Position otherPosition =(Position) otherObject;
                returnValue = row==otherPosition.getRow() && column==otherPosition.getColumn();
            }
            return returnValue;
        }

        public Board.Position clone(){
                return new Position(row,column);
        }
    }

    public enum State {
        DRAW,INCOMPLETE,OWINS,XWINS;
    }

    public Board(){
        board = new ULHashMap<>();
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int column = 0; column < MAX_COLUMNS; column++) {
                board.put(new Position(row, column), Piece.NONE);
            }
        }
    }

   /* public Board clone() throws java.lang.CloneNotSupportedException{
        Board cloneBoard = new Board();

        // Copy the elements from the current map to the new map
        for (Iterator<ULHashMap.Mapping<Position, Piece>> it = board.iterator(); it.hasNext(); ) {
            ULHashMap.Mapping<Position, Piece> list = it.next();
            if (list != null) {
                for(Piece piece:list) {
                    cloneBoard.put(entry.getKey(), entry.getValue());
                }
            }
        }

        return newMap;
   }*/

    public boolean equals(java.lang.Object otherObject){
        boolean returnValue;
        //returns false if the object taken in is either null or doesn't
        //belong to the position class
        if(otherObject==null||getClass() != otherObject.getClass()){
            returnValue = false;
            //return true if the objects are the same
        } else if (this==otherObject) {
            returnValue = true;
        }else{
            //return true if the row/column of the object taken in matches
            //the other
            returnValue = Objects.equals(board, ((Board) otherObject).board);
        }
        return returnValue;
    }

    public int hashCode(){return Objects.hash(board);}

    public java.lang.String toString(){StringBuilder sb = new StringBuilder();
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int column = 0; column < MAX_COLUMNS; column++) {
                sb.append(getPiece(new Position(row, column)));
                if (column < MAX_COLUMNS - 1) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void playPiece(Board.Position position, Board.Piece piece) throws IllegalMoveException{
        if (!isValidPosition(position) || getPiece(position) != Piece.NONE || piece == Piece.NONE) {
            throw new IllegalMoveException();
        }
        board.put(position, piece);
    }

    public boolean isValidPosition(Position position) {
        boolean returnValue = false;
        if (position != null) {
            if(position.getRow()>0&&position.getRow()<MAX_ROWS) {
                if (position.getColumn() > 0 && position.getColumn() < MAX_COLUMNS) {
                    if (getPiece(position) == Piece.NONE) {
                        returnValue = true;
                    }
                }
            }
        }
        return returnValue;
    }

    public Piece getPiece(Position position) {
        return board.lookup(position);
    }

    public State getGameState() {
        // Implement the logic to determine the game state here
        // You need to check if there's a winning condition for X or O,
        // if it's a draw, or if the game is incomplete.
        // Return the appropriate State enum value.
        // This code will depend on how you implement the game logic.
        return State.INCOMPLETE;
    }

    public java.util.Collection<Board.Position> emptyPositions(){
        List<Position> emptyPositions = new ArrayList<>();
        for (Iterator<ULHashMap.Mapping<Position, Piece>> it = board.iterator(); it.hasNext(); ) {
            Position position = it.next().getKey();
            if (getPiece(position).equals(Piece.NONE)) {
                emptyPositions.add(position);
            }
        }
        return emptyPositions;
    }
}

package tictactoe;

public class Board {
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
            string.append(this.row, " Column = ", this.column);
            return ("beans");
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
                //return true if the row/column of the object taken in mathces
                //the other
                Position otherPosition =(Position) otherObject;
                returnValue = this.row==otherPosition.row&&this.column==otherPosition.column;
            }
            return returnValue;
        }

        public Board.Position clone(){
                return new Position(this.row,this.column);
        }
    }

    public Board(){}

    public Board clone() throws java.lang.CloneNotSupportedException{return new Board();}

    public boolean equals(java.lang.Object otherObject){return true;}

    public int hashCode(){return 1;}

    public java.lang.String toString(){return "meh";}

    public void playPiece(Board.Position position, Board.Piece piece) throws IllegalMoveException{}

    public Board.Piece getPiece(Board.Position position){}

    public Board.State getGameState(){}

    public java.util.Collection<Board.Position> emptyPositions(){}
}

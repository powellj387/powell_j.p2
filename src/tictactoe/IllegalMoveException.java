package tictactoe;

public class IllegalMoveException extends Exception {
    public IllegalMoveException(){
        super();
    }

    public IllegalMoveException(java.lang.String message){
        super(message);
    }
}

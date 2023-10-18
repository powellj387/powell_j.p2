//@author Julian Powell

package tictactoe;

public class IllegalMoveException extends RuntimeException {
    public IllegalMoveException(){
        super();
    }

    public IllegalMoveException(java.lang.String message){
        super(message);
    }
}

//@author Julian Powell

package tictactoe;

public interface Player {
    void makeNextMove(Board board) throws IllegalMoveException, CloneNotSupportedException;
}

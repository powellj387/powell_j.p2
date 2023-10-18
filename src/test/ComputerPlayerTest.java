//@author Julian Powell
package test;
import org.junit.jupiter.api.BeforeEach;
import tictactoe.ComputerPlayer;
import tictactoe.Board;
import static org.junit.jupiter.api.Assertions.*;
import tictactoe.IllegalMoveException;

import org.junit.jupiter.api.Test;
public class ComputerPlayerTest {
    private ComputerPlayer playerX;
    private ComputerPlayer playerO;
    private Board board;

    @BeforeEach
    public void setUp() {
        playerX = new ComputerPlayer(Board.Piece.X);
        playerO = new ComputerPlayer(Board.Piece.O);
        board = new Board();
    }

    @Test
    public void testEmptyBoard() throws IllegalMoveException, CloneNotSupportedException {
        playerX.makeNextMove(board);
        System.out.println(board.toString());
        assertEquals(Board.Piece.X, board.getPiece(new Board.Position(1,0)));
    }

    @Test
    public void testFirstPlayerXMove() throws IllegalMoveException, CloneNotSupportedException {
        board.playPiece(new Board.Position(1, 1), Board.Piece.X);
        playerO.makeNextMove(board);
        System.out.println(board.toString());

        assertEquals(Board.Piece.O, board.getPiece(new Board.Position(0,0)));
    }

    @Test
    public void testWinningMoveX() throws IllegalMoveException, CloneNotSupportedException {
        board.playPiece(new Board.Position(0, 0), Board.Piece.X);
        board.playPiece(new Board.Position(1, 1), Board.Piece.O);
        board.playPiece(new Board.Position(0, 1), Board.Piece.X);
        playerX.makeNextMove(board);
        assertEquals(Board.Piece.X, board.getPiece(new Board.Position(0,2)));
    }

    @Test
    public void testDrawGame() throws IllegalMoveException, CloneNotSupportedException {
        board.playPiece(new Board.Position(0, 0), Board.Piece.X);
        board.playPiece(new Board.Position(0, 1), Board.Piece.O);
        board.playPiece(new Board.Position(0, 2), Board.Piece.X);
        board.playPiece(new Board.Position(1, 1), Board.Piece.O);
        board.playPiece(new Board.Position(1, 0), Board.Piece.X);
        board.playPiece(new Board.Position(1, 2), Board.Piece.O);
        board.playPiece(new Board.Position(2, 1), Board.Piece.X);
        board.playPiece(new Board.Position(2, 0), Board.Piece.O);
        System.out.println(board.toString());
        playerX.makeNextMove(board);
        System.out.println(board.toString());
        assertEquals(Board.Piece.X, board.getPiece(new Board.Position(2,2)));
    }
}

package test;
import tictactoe.Board;
import org.junit.jupiter.api.Test;
import tictactoe.IllegalMoveException;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void testValidEmptyBoard() {
        Board board = new Board();

        assertEquals(Board.State.INCOMPLETE, board.getGameState());
    }

    @Test
    public void testHorizontalWinX() {
        Board board = new Board();

        board.playPiece(new Board.Position(0, 0), Board.Piece.X);
        board.playPiece(new Board.Position(0, 1), Board.Piece.X);
        board.playPiece(new Board.Position(0, 2), Board.Piece.X);

        assertEquals(Board.State.XWINS, board.getGameState());
    }

    @Test
    public void testVerticalWinX() {
        Board board = new Board();

        board.playPiece(new Board.Position(0, 0), Board.Piece.X);
        board.playPiece(new Board.Position(1, 0), Board.Piece.X);
        board.playPiece(new Board.Position(2, 0), Board.Piece.X);

        assertEquals(Board.State.XWINS, board.getGameState());
    }

    @Test
    public void testVerticalWinO() {
        Board board = new Board();

        board.playPiece(new Board.Position(0, 0), Board.Piece.O);
        board.playPiece(new Board.Position(1, 0), Board.Piece.O);
        board.playPiece(new Board.Position(2, 0), Board.Piece.O);

        assertEquals(Board.State.OWINS, board.getGameState());
    }

    @Test
    public void testDiagonalWinX() {
        Board board = new Board();

        board.playPiece(new Board.Position(0, 0), Board.Piece.X);
        board.playPiece(new Board.Position(1, 1), Board.Piece.X);
        board.playPiece(new Board.Position(2, 2), Board.Piece.X);

        assertEquals(Board.State.XWINS, board.getGameState());
    }

    @Test
    public void testDiagonalWinO() {
        Board board = new Board();

        board.playPiece(new Board.Position(0, 2), Board.Piece.O);
        board.playPiece(new Board.Position(1, 1), Board.Piece.O);
        board.playPiece(new Board.Position(2, 0), Board.Piece.O);

        assertEquals(Board.State.OWINS, board.getGameState());
    }

    @Test
    public void testIncompleteGame() {
        Board board = new Board();

        board.playPiece(new Board.Position(0, 0), Board.Piece.X);
        board.playPiece(new Board.Position(0, 1), Board.Piece.O);
        board.playPiece(new Board.Position(0, 2), Board.Piece.X);
        board.playPiece(new Board.Position(1, 1), Board.Piece.O);
        board.playPiece(new Board.Position(1, 2), Board.Piece.X);

        assertEquals(Board.State.INCOMPLETE, board.getGameState());
    }

    @Test
    public void testDraw() {
        Board board = new Board();

        board.playPiece(new Board.Position(0, 0), Board.Piece.X);
        board.playPiece(new Board.Position(0, 2), Board.Piece.X);
        board.playPiece(new Board.Position(1, 0), Board.Piece.X);
        board.playPiece(new Board.Position(1, 1), Board.Piece.X);
        board.playPiece(new Board.Position(2, 1), Board.Piece.X);

        board.playPiece(new Board.Position(0, 1), Board.Piece.O);
        board.playPiece(new Board.Position(1, 2), Board.Piece.O);
        board.playPiece(new Board.Position(2, 0), Board.Piece.O);
        board.playPiece(new Board.Position(2, 2), Board.Piece.O);

        System.out.println(board.toString());
        assertEquals(Board.State.DRAW, board.getGameState());
    }

    @Test
    public void testPlayInvalidPosition() {
        Board board = new Board();

        assertThrows(IllegalMoveException.class, () -> {
            board.playPiece(new Board.Position(-1, -1), Board.Piece.X);
        });
    }

    @Test
    public void testEmptyPositions() {
        Board board = new Board();

        board.playPiece(new Board.Position(0, 0), Board.Piece.X);
        board.playPiece(new Board.Position(1, 1), Board.Piece.X);

        board.playPiece(new Board.Position(0, 1), Board.Piece.O);

        assertEquals(6, board.emptyPositions().size());
    }

    @Test
    public void testCloneBoard() throws CloneNotSupportedException {
        Board board1 = new Board();
        board1.playPiece(new Board.Position(0, 0), Board.Piece.X);

        Board board2 = board1.clone();

        assertNotSame(board1, board2);
        assertEquals(board1.toString(), board2.toString());
    }
}

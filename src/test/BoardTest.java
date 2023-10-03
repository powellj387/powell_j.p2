/*
package test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tictactoe.Board;
import tictactoe.IllegalMoveException;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testEmptyBoard() {
        // Test an empty board
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                assertEquals(Board.Piece.NONE, board.getPiece(board.new Position(row, column)));
            }
        }
    }

    @Test
    public void testPlayPiece() throws IllegalMoveException {
        // Test playing pieces on the board
        board.playPiece(board.new Position(0, 0), Board.Piece.X);
        assertEquals(Board.Piece.X, board.getPiece(board.new Position(0, 0)));

        board.playPiece(board.new Position(1, 1), Board.Piece.O);
        assertEquals(Board.Piece.O, board.getPiece(board.new Position(1, 1)));

        // Try playing on an already occupied position
        assertThrows(IllegalMoveException.class, () -> board.playPiece(board.new Position(0, 0), Board.Piece.O));
    }

    @Test
    public void testIsValidPosition() {
        // Test the isValidPosition method
        assertTrue(board.isValidPosition(board.new Position(1, 1)));
        assertFalse(board.isValidPosition(board.new Position(3, 0)));
        assertFalse(board.isValidPosition(board.new Position(0, 3)));
        assertFalse(board.isValidPosition(null));
    }

    @Test
    public void testGetGameState() {
        // Test getGameState method (more tests needed based on your game logic)
        assertEquals(Board.State.INCOMPLETE, board.getGameState());

        // Implement more tests based on your game logic
    }

    @Test
    public void testEmptyPositions() throws IllegalMoveException {
        // Test emptyPositions method
        board.playPiece(board.new Position(0, 0), Board.Piece.X);
        board.playPiece(board.new Position(1, 1), Board.Piece.O);
        board.playPiece(board.new Position(2, 2), Board.Piece.X);

        java.util.Collection<Board.Position> emptyPositions = board.emptyPositions();
        assertEquals(6, emptyPositions.size());
        assertTrue(emptyPositions.contains(board.new Position(0, 1)));
        assertTrue(emptyPositions.contains(board.new Position(0, 2)));
        assertTrue(emptyPositions.contains(board.new Position(1, 0)));
        assertTrue(emptyPositions.contains(board.new Position(1, 2)));
        assertTrue(emptyPositions.contains(board.new Position(2, 0)));
        assertTrue(emptyPositions.contains(board.new Position(2, 1)));
    }
}*/

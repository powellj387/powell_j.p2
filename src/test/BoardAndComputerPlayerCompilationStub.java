
package test;

import java.util.Collection;

import tictactoe.Board;
import tictactoe.ComputerPlayer;
import tictactoe.IllegalMoveException;


/**
 * This class just tests whether your Board and ComputerPlayer classes will compile in my JUnit tests.
 * This class DOES NOT actually test your code.  It just ensures that it compiles.
 * @author Joe Meehean
 *
 */

public class BoardAndComputerPlayerCompilationStub {
	public static void main(String[] args) throws CloneNotSupportedException, IllegalMoveException {
		// Board
		int maxRows = Board.MAX_ROWS;
		int maxColumns = Board.MAX_COLUMNS;
		
		Board.Piece x = Board.Piece.X;
		Board.Piece o = Board.Piece.O;
		Board.Piece none = Board.Piece.NONE;
		
		Board.State xwins = Board.State.XWINS;
		Board.State owins = Board.State.OWINS;
		Board.State draw = Board.State.DRAW;
		Board.State incomplete = Board.State.INCOMPLETE;
		
		Board.Position pos = new Board.Position(0, 0);
		Board.Position pos2 = pos.clone();
		int anInt = pos.row + pos.column;
				
		Board boardA = new Board();
		Board boardB = boardA.clone();
		boardA.playPiece(pos, x);
		IllegalMoveException e = new IllegalMoveException("an excepction");
		e = new IllegalMoveException();
		
		Board.Piece piece = boardA.getPiece(pos);
		Board.State state = boardA.getGameState();
		Collection<Board.Position> emptyPositions = boardB.emptyPositions();
		
		// Computer Player
		ComputerPlayer player1 = new ComputerPlayer(x);
		ComputerPlayer player2 = new ComputerPlayer(o, false);
		player1.makeNextMove(boardA);
	}
}


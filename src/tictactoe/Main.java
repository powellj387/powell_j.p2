package tictactoe;
import java.util.Scanner;
import java.io.PrintStream;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PrintStream out = System.out;

        ConsoleInterface consoleInterface = new ConsoleInterface(scanner, out);

        Board board = new Board();
        ConsolePlayer playerX = new ConsolePlayer(scanner, out, Board.Piece.X);
        ConsolePlayer playerO = new ConsolePlayer(scanner, out, Board.Piece.O);

        ConsolePlayer currentPlayer = playerX;

        out.println("Tic-Tac-Toe game");

        while (true) {
            out.println(board.toString());
            out.println(currentPlayer.getPlayerPiece() + "'s turn:");
            currentPlayer.makeNextMove(board);

            // Check for the game result
            Board.State gameState = board.getGameState(board);
            if (gameState == Board.State.XWINS) {
                out.println("X wins!");
                break;
            } else if (gameState == Board.State.OWINS) {
                out.println("O wins!");
                break;
            } else if (gameState == Board.State.DRAW) {
                out.println("It's a draw!");
                break;
            }
            // Switch to the other player
            currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
        }
        scanner.close();
    }
}


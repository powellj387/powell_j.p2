package tictactoe;
import java.util.Scanner;
import java.io.PrintStream;
public class Main {
    public static void main(String[] args) throws IllegalMoveException, CloneNotSupportedException {
        Scanner scanner = new Scanner(System.in);
        PrintStream out = System.out;

        ConsoleInterface consoleInterface = new ConsoleInterface(scanner, out);

        Player player1;
        Player player2;

        if(consoleInterface.askIfPLayerShouldBeComputer(1)){
            player1 = new ComputerPlayer(Board.Piece.X);
        }else{
            player1 = new ConsolePlayer(scanner, out, Board.Piece.X);
        }

        if(consoleInterface.askIfPLayerShouldBeComputer(2)){
            player2 = new ComputerPlayer(Board.Piece.O);
        }else{
            player2 = new ConsolePlayer(scanner, out, Board.Piece.O);
        }

        Player currentPlayer = player1;

        out.println("Tic-Tac-Toe game");

        Board board = new Board();

        while (true) {
            currentPlayer.makeNextMove(board);
            out.println(board.toString());
            // Check for the game result
            Board.State gameState = Board.getGameState(board);
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
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }
        scanner.close();
    }
}


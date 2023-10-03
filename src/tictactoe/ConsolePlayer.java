package tictactoe;

import java.io.PrintStream;
import java.util.Scanner;

public class ConsolePlayer {
    private PrintStream out;
    private Scanner in;
    private Board.Piece playerPiece;
    public ConsolePlayer(java.util.Scanner scanner, java.io.PrintStream out, Board.Piece piece){
        this.out = out;
        this.in = scanner;
        this.playerPiece = piece;
    }

    public Board.Piece getPlayerPiece(){
        return playerPiece;
    }
    void makeNextMove(Board board) {
        boolean validMove = false;

        while (!validMove) {
            out.println("Enter your move (row space column, '1 2'): ");
            int row, col;

            try {
                row = in.nextInt();
                col = in.nextInt();
            } catch (java.util.InputMismatchException e) {
                in.nextLine(); // Clear the input buffer
                out.println("Invalid input. Please enter two numbers separated by a space.");
                continue;
            }

            Board.Position position = new Board.Position(row, col);

            if (board.isValidPosition(position) && board.getPiece(position) == Board.Piece.NONE) {
                try {
                    board.playPiece(position, playerPiece);
                    validMove = true;
                } catch (IllegalMoveException e) {
                    out.println("Illegal move. Please try again.");
                }
            } else {
                out.println("Invalid move. Please try again.");
            }
        }
    }
}

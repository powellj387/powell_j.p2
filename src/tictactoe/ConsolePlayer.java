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
    void makeNextMove(Board board){

    }
}

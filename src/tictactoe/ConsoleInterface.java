//@author Julian Powell

package tictactoe;

import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;

public class ConsoleInterface {
    private Scanner scan;
    private PrintStream out;

    public ConsoleInterface(java.util.Scanner scanner, java.io.PrintStream printer){
        scan = scanner;
        out = printer;
    }

    public boolean askIfPLayerShouldBeComputer(int playerNr){
        boolean returnVal = false;
        out.println("Should player "+playerNr+" be a computer player? Y/N");
        String response = scan.nextLine();
        if(Objects.equals(response, "Y") || Objects.equals(response, "y")){
            returnVal = true;
        }
        return returnVal;
    }

    public void printBoard(Board board){
        out.println(board.toString());
    }
}

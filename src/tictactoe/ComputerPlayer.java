package tictactoe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComputerPlayer implements Player {

    private final Board.Piece piece;
    private final Board.Piece oppPiece;
    private boolean cachingEnabled;
    private Map<String, Integer> cache;

    public ComputerPlayer(Board.Piece piece) {
        this(piece, false);
    }

    public ComputerPlayer(Board.Piece piece, boolean cachingEnabled)
            throws java.lang.UnsupportedOperationException {

        this.piece = piece;
        if (piece.equals(Board.Piece.X)) {
            oppPiece = Board.Piece.O;
        } else {
            oppPiece = Board.Piece.X;
        }

        this.cachingEnabled = cachingEnabled;
        if (cachingEnabled) {
            this.cache = new HashMap<>();
        }
    }

    @Override
    public void makeNextMove(Board board) throws IllegalMoveException, CloneNotSupportedException {
        int bestScore = Integer.MIN_VALUE;
        Board.Position bestMove = null;

        List<Board.Position> emptyPos = (List<Board.Position>) board.emptyPositions();
        for (Board.Position pos : emptyPos) {
            Board cloneBoard = board.clone();
            cloneBoard.playPiece(pos, piece);

            int score = minimax(cloneBoard, oppPiece);

            if (score > bestScore) {
                bestScore = score;
                bestMove = pos;
            }
        }

        if (bestMove != null) {
            board.playPiece(bestMove, piece);
        }
    }

    private int minimax(Board board, Board.Piece currentPlayer) throws IllegalMoveException, CloneNotSupportedException {
        Board.State currentState = Board.getGameState(board);

        if (currentState == Board.State.XWINS) {
            return (currentPlayer == Board.Piece.X) ? 1 : -1;
        }
        if (currentState == Board.State.OWINS) {
            return (currentPlayer == Board.Piece.O) ? -1 : 1;
        }
        if (currentState == Board.State.DRAW) {
            return 0;
        }

        List<Board.Position> emptyPos = (List<Board.Position>) board.emptyPositions();
        int bestScore = (currentPlayer == piece) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        if (emptyPos.isEmpty()) {
            return 0;
        }

        for (Board.Position pos : emptyPos) {
            Board cloneBoard = board.clone();
            cloneBoard.playPiece(pos, currentPlayer);

            int score = minimax(cloneBoard, switchPiece(currentPlayer));

            if (currentPlayer == piece) {
                bestScore = Math.max(bestScore, score);
            } else {
                bestScore = Math.min(bestScore, score);
            }
        }
        return bestScore;
    }

    private Board.Piece switchPiece(Board.Piece currentPlayer){
        return (currentPlayer == Board.Piece.X) ? Board.Piece.O : Board.Piece.X;
    }

}


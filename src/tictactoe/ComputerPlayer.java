package tictactoe;

import java.util.HashMap;
import java.util.Map;

public class ComputerPlayer implements Player {

    private Board.Piece piece;
    private Board.Piece oppPiece;
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
    public void makeNextMove(Board board) throws IllegalMoveException {
        int bestScore = -99999;
        Board.Position bestMove = null;
        for (Board.Position pos : board.emptyPositions()) {
            if (board.isValidPosition(pos)) {
                board.playPiece(pos, piece);
                int score = minimax(board, piece);
                board.playPiece(pos, Board.Piece.NONE);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = pos;
                }
            }
        }
        board.playPiece(bestMove, piece);
    }

    private int minimax(Board board, Board.Piece piecePlayed) throws IllegalMoveException {
        Board.State currentState = board.getGameState(board);
        if (piecePlayed.equals(Board.Piece.X)) {
            if (currentState.equals(Board.State.XWINS)) {
                return 1;
            } else if (currentState.equals(Board.State.OWINS)) {
                return -1;
            } else if (currentState.equals(Board.State.DRAW)) {
                return 0;
            } else {
                if (piece.equals(Board.Piece.X)) {
                    int bestScore = -99999;
                    for (Board.Position pos : board.emptyPositions()) {
                        if (board.isValidPosition(pos)) {
                            board.playPiece(pos, piece);
                            int score = minimax(board, piece);
                            board.playPiece(pos, Board.Piece.NONE);
                            bestScore = Math.max(score, bestScore);
                        }
                    }
                    return bestScore;
                } else {
                    int bestScore = 99999;
                    for (Board.Position pos : board.emptyPositions()) {
                        if (board.isValidPosition(pos)) {
                            board.playPiece(pos, oppPiece);
                            int score = minimax(board, oppPiece);
                            board.playPiece(pos, Board.Piece.NONE);
                            bestScore = Math.min(score, bestScore);
                        }
                    }
                    return bestScore;
                }
            }
        }
        return 0;
    }
}

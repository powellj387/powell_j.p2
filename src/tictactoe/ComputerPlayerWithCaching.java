//@author Julian Powell
/* THIS FILE DOES NOT RUN CORRECTLY HOWEVER THIS IS THE PROGRESS ON IMPLIMENTING THE CACHE
package tictactoe;

import java.util.HashMap;

public class ComputerPlayerWithCaching {
    private Board.Piece piece = null;
    private static boolean cachingEnabled;
    private static ULHashMap<Board, Integer> cache;

    public ComputerPlayerWithCaching(Board.Piece piece) {
        this.piece = piece;
    }

    public ComputerPlayerWithCaching(Board.Piece piece, boolean cachingEnabled) {
        this.piece = piece;
        this.cachingEnabled = cachingEnabled;
        if (cachingEnabled) {
            cache = new ULHashMap<>();
        }
    }

    public void makeNextMove(Board board) throws IllegalMoveException {
        if (board.emptyPositions().size() == 9) {
            // If the board is empty, play the first position in the list due to them all resulting in 0
            // score for now as the other player has yet to make a move
            board.playPiece(board.emptyPositions().iterator().next(), piece);
        } else {
            // If the board isn't empty, find and play the next best position
            Board.Position bestMove = findBestMove(board, piece);
            if (bestMove != null) {
                board.playPiece(bestMove, piece);
            }
        }
    }

    public static Board.Position findBestMove(Board board, Board.Piece player) throws IllegalMoveException {
        Board.Position bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for (Board.Position position : board.emptyPositions()) {
            board.playPiece(position, player);

            int score;

            if (cachingEnabled) {
                score = getCachedScore(board);

            } else {
                score = minimax(board, 0, false, player);

                board.playPiece(position, Board.Piece.NONE); // Undo the move

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = new Board.Position(position.getRow(), position.getColumn());
                }
            }


            }
        return bestMove;
        }

        private static int getCachedScore (Board board){
            Integer cachedScore = cache.get(board);
            return (cachedScore != null) ? cachedScore : Integer.MIN_VALUE;
        }

        private static int minimax (Board board,int depth, boolean isMaximizing, Board.Piece player)
            throws IllegalMoveException {

            if (cachingEnabled) {
                int cachedScore = getCachedScore(board);
                if (cachedScore != Integer.MIN_VALUE) {
                    return cachedScore;
                }
            }

            Board.Piece oppPlayer = (player == Board.Piece.X) ? Board.Piece.O : Board.Piece.X;
            Board.State currState = board.getGameState();

            int bestScore;
            // If the piece is maximizing, find the highest possible score to return
            if (isMaximizing) {
                bestScore = Integer.MIN_VALUE;
                for (Board.Position position : board.emptyPositions()) {
                    board.playPiece(position, player);
                    int score = minimax(board, depth + 1, false, player);
                    board.playPiece(position, Board.Piece.NONE); // Undo the move
                    bestScore = Math.max(bestScore, score);
                }
                if (cachingEnabled) {
                    cache.put(board.clone(), bestScore);
                }
                return bestScore;
            } else {
                bestScore = Integer.MAX_VALUE;
                for (Board.Position position : board.emptyPositions()) {
                    board.playPiece(position, oppPlayer);
                    int score = minimax(board, depth + 1, true, player);
                    board.playPiece(position, Board.Piece.NONE); // Undo the move
                    bestScore = Math.min(bestScore, score);
                }
                if (cachingEnabled) {
                    cache.put(board.clone(), bestScore);
                }
                return bestScore;
            }
        }
    }
}*/

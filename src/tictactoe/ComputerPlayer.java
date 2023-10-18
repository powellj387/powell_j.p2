//@author Julian Powell

package tictactoe;

public class ComputerPlayer implements Player {

    private Board.Piece piece = null;

    public ComputerPlayer(Board.Piece piece) {
        this.piece = piece;
    }

    public ComputerPlayer(Board.Piece piece, boolean cachingEnabled) {
        this.piece = piece;
    }

    public void makeNextMove(Board board) throws IllegalMoveException, CloneNotSupportedException {
            if (board.emptyPositions().size() == 9) {
                // If the board is empty play the first position in the list due to them all resulting in 0
                //score for now as the other player has yet to make a move
                board.playPiece(board.emptyPositions().iterator().next(), piece);
            } else {
                //If the board isn't empty, find and play the next best position
                Board.Position bestMove = findBestMove(board, piece);
                if (bestMove != null) {
                    board.playPiece(bestMove, piece);
                }
            }
        }

    public static Board.Position findBestMove(Board board, Board.Piece player) throws IllegalMoveException, CloneNotSupportedException {
        Board.Position bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for (Board.Position position : board.emptyPositions()) {
            board.playPiece(position, player);

            int score;

            score = minimax(board, 0, false, player);

            board.playPiece(position, Board.Piece.NONE); // Undo the move

            if (score > bestScore) {
                bestScore = score;
                bestMove = new Board.Position(position.getRow(), position.getColumn());
            }
        }

        return bestMove;
    }

    private static int minimax(Board board, int depth, boolean isMaximizing, Board.Piece player)
            throws IllegalMoveException, CloneNotSupportedException {

            Board.Piece oppPlayer = (player == Board.Piece.X) ? Board.Piece.O : Board.Piece.X;
            Board.State currState = board.getGameState();
            //Checks to see if there are any winning conditions and if so return a score
            //according to the AI's piece
            if (currState == Board.State.XWINS && player == Board.Piece.X) {
                return 10 - depth;
            } else if (currState == Board.State.OWINS && player == Board.Piece.O) {
                return 10 - depth;
            } else if (currState == Board.State.XWINS && player == Board.Piece.O) {
                return depth - 10;
            } else if (currState == Board.State.OWINS && player == Board.Piece.X) {
                return depth - 10;
            } else if (currState == Board.State.DRAW) {
                return 0;
            }

            int bestScore;
            //If the piece is maximizing, find the highest possible score to return
            if (isMaximizing) {
                bestScore = Integer.MIN_VALUE;
                for (Board.Position position : board.emptyPositions()) {
                    board.playPiece(position, player);
                    int score = minimax(board, depth + 1, false, player);
                    board.playPiece(position, Board.Piece.NONE); // Undo the move
                    bestScore = Math.max(bestScore, score);
                }
                return bestScore;
                //do the opposite if it is minimizing
            } else {
                bestScore = Integer.MAX_VALUE;
                for (Board.Position position : board.emptyPositions()) {
                    board.playPiece(position, oppPlayer);
                    int score = minimax(board, depth + 1, true, player);
                    board.playPiece(position, Board.Piece.NONE); // Undo the move
                    bestScore = Math.min(bestScore, score);
                }
                return bestScore;
            }
    }
}


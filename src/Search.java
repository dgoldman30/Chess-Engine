import java.util.List;

public class Search {

    Evaluation evaluate = new Evaluation();

    // Function to find the best move for white
    public Tuple<Long, Long> findBestMoveForWhite(Board chessBoard, int depth) {
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        int bestScore = Integer.MIN_VALUE;
        Tuple<Long, Long> bestMove = null;

        List<Tuple<Long, List<Long>>> whiteMoves;
        whiteMoves = new Move().generateWhiteMoves(chessBoard);

        for (Tuple<Long, List<Long>> whiteMove : whiteMoves) {
            for (Long individualMove : whiteMove.getMoves()) {
                Tuple<Long, Long> singleMoveTuple = new Tuple<>(whiteMove.getStart(), individualMove);
                Board newBoard = applyMove(chessBoard, singleMoveTuple);
                int score = min(newBoard, depth - 1);

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = singleMoveTuple; // Update best move
                }
            }
        }

        return bestMove;
    }


    // Helper function to apply a move to the board and return the resulting board
    private Board applyMove(Board board, Tuple<Long, Long> move) {
        Board newBoard = new Board(board.whitePawnBoard, board.whiteKnightBoard, board.whiteRookBoard, board.whiteBishopBoard, board.whiteKingBoard, board.whiteQueenBoard, board.whiteOccBoard, board.blackPawnBoard, board.blackKnightBoard, board.blackBishopBoard, board.blackRookBoard, board.blackQueenBoard, board.blackKingBoard, board.blackOccBoard);
        new Move().doMove(newBoard, move);
        return newBoard;
    }

    private int max(Board board, int depth) {
        if (depth == 0 /* or game over */) {
            return evaluate.evaluateWhite(board);
        }

        List<Tuple<Long, List<Long>>> whiteMoves = new Move().generateWhiteMoves(board);

        int bestScore = Integer.MIN_VALUE;

        for (Tuple<Long, List<Long>> whiteMove : whiteMoves) {
            for (Long individualMove : whiteMove.getMoves()) {
                Tuple<Long, Long> singleMoveTuple = new Tuple<>(whiteMove.getStart(), individualMove);
                Board newBoard = applyMove(board, singleMoveTuple);
                int score = min(newBoard, depth - 1);
                bestScore = Math.max(bestScore, score);
            }
        }

        return bestScore;
    }

    private int min(Board board, int depth) {
        if (depth == 0 /* or game over */) {
            return evaluate.evaluateBlack(board);
        }

        List<Tuple<Long, List<Long>>> blackMoves = new Move().generateBlackMoves(board);

        int bestScore = Integer.MAX_VALUE;

        for (Tuple<Long, List<Long>> blackMove : blackMoves) {
            for (Long individualMove : blackMove.getMoves()) {
                Tuple<Long, Long> singleMoveTuple = new Tuple<>(blackMove.getStart(), individualMove);
                Board newBoard = applyMove(board, singleMoveTuple);
                int score = max(newBoard, depth - 1);
                bestScore = Math.min(bestScore, score);
            }
        }

        return bestScore;
    }
}

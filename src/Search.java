import java.util.List;

public class Search {

    //CURRENTLY THE SEARCH FUNCTIONS JUST OUTPUTS THE FINAL MOVE IN THE LIST. THIS MEANS ITS VEIWING EVERY MOVE AS THE BEST MOVE
    Evaluation evaluate = new Evaluation();


    // Function to find the best move for white
    public Tuple<Long, Long> findBestMoveForWhite(Board chessBoard, int depth) {

        //sets best score to be the worst score possible
        int bestScore = Integer.MAX_VALUE;
        //int bestScore = 0;

        //set bestMove to be empty
        Tuple<Long, Long> bestMove = null;

        // Generate all possible moves for white
        List<Tuple<Long, List<Long>>> whiteMoves = new Move().generateWhiteMoves(chessBoard);

        // Iterate through each possible move
        for (Tuple<Long, List<Long>> whiteMove : whiteMoves) {
            for (Long individualMove : whiteMove.getMoves()) {
                // Make new tuple with individual move
                Tuple<Long, Long> singleMoveTuple = new Tuple<>(whiteMove.getStart(), individualMove);
                // Apply the move to a new board
                Board newBoard = applyMove(chessBoard, singleMoveTuple);
                // Evaluate the board
                int score = min(newBoard, depth - 1);    //not outputting best move
                // Debug print statements
                System.out.println("Move: " + "\n" + newBoard);
                System.out.println("Score: " + score);

                if (score < bestScore) {
                    bestScore = score;
                    bestMove = singleMoveTuple;
                    System.out.println("Best Score Updated: " + bestScore);
                }
            }
        }
        return bestMove;
    }


    // Helper function to apply a move to the board and return the resulting board
    private Board applyMove(Board board, Tuple<Long, Long> move) {
        // Make a new copy of current board
        Board newBoard = new Board(board.whitePawnBoard, board.whiteKnightBoard, board.whiteRookBoard, board.whiteBishopBoard, board.whiteKingBoard, board.whiteQueenBoard, board.whiteOccBoard, board.blackPawnBoard, board.blackKnightBoard, board.blackBishopBoard, board.blackRookBoard, board.blackQueenBoard, board.blackKingBoard, board.blackOccBoard);
        // Execute the move on the new board
        new Move().doMove(newBoard, move);
        return newBoard;
    }

    private int max(Board board, int depth) {
        if (depth == 0 /* or game over */) {
            return evaluate.evaluateWhite(board);
        }
        int maxScore = Integer.MIN_VALUE;
        // Generate all white moves from board
        List<Tuple<Long, List<Long>>> whiteMoves = new Move().generateWhiteMoves(board);

        // Iterate through each possible move
        for (Tuple<Long, List<Long>> whiteMove : whiteMoves) {
            for (Long individualMove : whiteMove.getMoves()) {
                // Make new tuple with individual move
                Tuple<Long, Long> singleMoveTuple = new Tuple<>(whiteMove.getStart(), individualMove);
                // Apply the move to a new board
                Board newBoard = applyMove(board, singleMoveTuple);
                // Evaluate it
                int score = min(newBoard, depth - 1);
                // Update maxScore if score is larger
                if (score > maxScore){
                    maxScore = score;
                }
            }
        }
        return maxScore;
    }

    private int min(Board board, int depth) {
        if (depth == 0 /* or game over */) {
            return evaluate.evaluateBlack(board);
        }
        int minScore = Integer.MAX_VALUE;
        // Generate all black moves
        List<Tuple<Long, List<Long>>> blackMoves = new Move().generateBlackMoves(board);

        // Iterate through each possible move
        for (Tuple<Long, List<Long>> blackMove : blackMoves) {
            for (Long individualMove : blackMove.getMoves()) {
                // Make new tuple with individual move
                Tuple<Long, Long> singleMoveTuple = new Tuple<>(blackMove.getStart(), individualMove);
                // Apply the move to a new board
                Board newBoard = applyMove(board, singleMoveTuple);
                // Evaluate the position of next moves
                int score = max(newBoard, depth - 1);
                // Update bestScore if score is lower
                if (score < minScore){
                    minScore = score;
                }
            }
        }
        return minScore;
    }
}

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class miniMax {
    Move Move = new Move();
    Evaluation evaluate = new Evaluation();

    // add the best move at each depth to the bestScores map
    Map<Integer, Tuple<Long, Long>> bestScores = new HashMap<>();
    // same as above for the qui search
    // HashMap<Integer, Tuple<Long, Long>> bestQuiScores = new HashMap<>();

    public Tuple<Long, Long> computeMove(Board chessBoard, int depth, boolean isWhite)
            throws InterruptedException, ExecutionException {

        Tuple<Long, Long> bestMove;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        int bestMoveScore = max(chessBoard, depth, alpha, beta, isWhite);
        // int capScore = quiSearch(chessBoard, alpha, beta, isWhite);
        //
        // if (capScore > bestMoveScore) bestMove = bestQuiScores.get(capScore);
        // else bestMove = bestScores.get(bestMoveScore);
        bestMove = bestScores.get(bestMoveScore);
        return bestMove;
    }

    public int min(Board chessBoard, int depth, int alpha, int beta, boolean isWhite)
            throws InterruptedException, ExecutionException {
        if (depth == 0) {
            int score = isWhite ? evaluate.evaluate(chessBoard, 1) : evaluate.evaluate(chessBoard, -1);
            return score;
        } else {

            // find all new possible moves for player to move
            List<Tuple<Long, List<Long>>> moveList = isWhite ? Move.generateWhiteMoves(chessBoard)
                    : Move.generateBlackMoves(chessBoard);
            // iterate through move list
            for (int i = 0; i < moveList.size(); i++) {
                // get each piece, grabs each tuple
                Tuple<Long, List<Long>> piece = moveList.get(i);
                // get the possible moves for that piece from the cur position
                List<Long> pieceMoves = piece.getMoves();
                // iterate through the pieces moves
                for (int z = 0; z < pieceMoves.size(); z++) {
                    // construct the move with the starting position of the piece and the current(i)
                    // end move
                    Tuple<Long, Long> singleMoveTuple = new Tuple<>(piece.getStart(), pieceMoves.get(z));
                    // apply the move
                    Move.doMove(chessBoard, singleMoveTuple);
                    int childScore = max(chessBoard, depth - 1, alpha, beta, !isWhite);
                    Move.undoMove(chessBoard);
                    if (childScore < beta) {
                        beta = childScore;
                        if (beta <= alpha) {
                            return beta; // Beta cutoff
                        }
                    }
                }
            }
            return beta;
        }
    }

    public int max(Board chessBoard, int depth, int alpha, int beta, boolean isWhite)
            throws InterruptedException, ExecutionException {
        if (depth == 0) {
            int score = isWhite ? evaluate.evaluate(chessBoard, 1) : evaluate.evaluate(chessBoard, -1);
            return score;
        } else {
            // set the best score to be min number possible
            int bestScore = Integer.MIN_VALUE;
            // find all new possible moves for player to move
            List<Tuple<Long, List<Long>>> moveList = isWhite ? Move.generateWhiteMoves(chessBoard)
                    : Move.generateBlackMoves(chessBoard);
            // iterate through move list
            for (Tuple<Long, List<Long>> piece : moveList) {
                List<Long> pieceMoves = piece.getMoves();
                // iterate through the pieces moves
                for (Long move : pieceMoves) {
                    // construct the move with the starting position of the piece and the current(i)
                    // end move
                    Tuple<Long, Long> singleMoveTuple = new Tuple<>(piece.getStart(), move);
                    // apply the move
                    Move.doMove(chessBoard, singleMoveTuple);
                    int childScore = min(chessBoard, depth - 1, alpha, beta, !isWhite);
                    Move.undoMove(chessBoard);
                    if (childScore > alpha) {
                        alpha = childScore;
                        bestScore = childScore;
                        bestScores.put(bestScore, singleMoveTuple);
                        if (beta <= alpha) {
                            return alpha; // Alpha cutoff
                        }
                    }
                }
            }
            return depth == 0 ? bestScore : alpha;
        }
    }

    // /public int quiSearch(Board chessBoard, int alpha, int beta, boolean
    // isWhite){
    // int standPat = isWhite ? evaluate.evaluate(chessBoard, 1) :
    // evaluate.evaluate(chessBoard, -1);
    // List<Tuple<Long, List<Long>>> moveList = isWhite ?
    // Move.generateWhiteMoves(chessBoard) : Move.generateBlackMoves(chessBoard);
    // List<Tuple<Long, List<Long>>> captures = Move.captureMoves(moveList,
    // chessBoard, isWhite);
    // if (standPat >= beta) {
    // return beta;
    // }
    // else if(alpha < beta){
    // alpha = standPat;
    // }
    //
    // for(int i = 0; i < captures.size(); i++){
    // Tuple<Long, List<Long>> piece = captures.get(i);
    // List<Long> pieceMoves = piece.getMoves();
    // for(long mv : pieceMoves){
    // Tuple<Long, Long> singleMoveTuple = new Tuple<>(piece.getStart(), mv);
    // Move.doMove(chessBoard, singleMoveTuple);
    // int score = -quiSearch(chessBoard, -beta, -alpha, !isWhite);
    // Move.undoMove(chessBoard);
    //
    // if(score >= beta)
    // return beta;
    // else if (score > alpha)
    // alpha = score;
    // bestQuiScores.put(score, singleMoveTuple);
    // }
    // }
    // return alpha;
    // }

}
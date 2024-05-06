import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class miniMax {
    Move Move = new Move();
    Evaluation evaluate = new Evaluation();

    // add the best move at each depth to the bestScores map
    //Map<Integer, Tuple<Long, Long>> bestScores = new HashMap<>();
    // same as above for the qui search
    //HashMap<Integer, Tuple<Long, Long>> bestQuiScores = new HashMap<>();

    public Tuple<Long, Long> computeMove(Board chessBoard, int cutoffDepth, boolean isWhite) {

        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        Tuple<Integer, Tuple<Long, Long>> bestMove = max(chessBoard, cutoffDepth, 0, alpha, beta, isWhite);
//        int capScore = quiSearch(chessBoard, alpha, beta, isWhite);
//
//        if (capScore > bestMoveScore) bestMove = bestQuiScores.get(capScore);
//        else bestMove = bestScores.get(bestMoveScore);
       // bestMove = bestScores.get(bestMoveScore);
        return bestMove.getMoves();
    }

    public Tuple<Integer, Tuple<Long, Long>> min(Board chessBoard, int cutoffDepth, int curDepth, int alpha, int beta, boolean isWhite) {
        Tuple<Integer, Tuple<Long, Long>> retVal = new Tuple<>(0, null);

        if (curDepth == cutoffDepth) {
            int score = isWhite ? evaluate.evaluate(chessBoard, 1) : evaluate.evaluate(chessBoard, -1);
            retVal.setFirst(score);
            return retVal;
        } else {
            //find all new possible moves for player to move
            List<Tuple<Long, List<Long>>> moveList = isWhite ? Move.generateWhiteMoves(chessBoard) : Move.generateBlackMoves(chessBoard);

            if(moveList.isEmpty())
                return retVal;

            //iterate through move list
            for (int i = 0; i < moveList.size(); i++) {
                //get each piece, grabs each tuple
                Tuple<Long, List<Long>> piece = moveList.get(i);
                // get the possible moves for that piece from the cur position
                List<Long> pieceMoves = piece.getMoves();
                //iterate through the pieces moves
                for (int z = 0; z < pieceMoves.size(); z++) {
                    //construct the move with the starting position of the piece and the current(i) end move
                    Tuple<Long, Long> singleMoveTuple = new Tuple<>(piece.getStart(), pieceMoves.get(z));
                    //apply the move
                    Move.doMove(chessBoard, singleMoveTuple);
                    Tuple<Integer, Tuple<Long, Long>> child = max(chessBoard, cutoffDepth, curDepth + 1, alpha, beta, !isWhite);
                    Move.undoMove(chessBoard);
                    int childScore = child.getStart();
                    if(childScore < beta) {
                        beta = childScore;
                        retVal.setFirst(childScore);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return retVal;
        }
    }

    public Tuple<Integer, Tuple<Long, Long>> max(Board chessBoard, int cutoffDepth, int curDepth,  int alpha, int beta, boolean isWhite) {
        Tuple<Integer, Tuple<Long, Long>> retVal = new Tuple<>(0, null);

        if (curDepth == cutoffDepth) {
            int score = isWhite ? evaluate.evaluate(chessBoard, 1) : evaluate.evaluate(chessBoard, -1);
            retVal.setFirst(score);
            return retVal;
        } else {
            //set the best score to be min number possible
            //find all new possible moves for player to move
            List<Tuple<Long, List<Long>>> moveList = isWhite ? Move.generateWhiteMoves(chessBoard) : Move.generateBlackMoves(chessBoard);

            if(moveList.isEmpty()){
                chessBoard.boardState();
                return retVal;
            }

            //iterate through move list
            for (int i = 0; i < moveList.size(); i++) {
                //get each piece, grabs each tuple
                Tuple<Long, List<Long>> piece = moveList.get(i);
                // get the possible moves for that piece from the cur position
                List<Long> pieceMoves = piece.getMoves();
                //iterate through the pieces moves
                for (int z = 0; z < pieceMoves.size(); z++) {
                    //construct the move with the starting position of the piece and the current(i) end move
                    Tuple<Long, Long> singleMoveTuple = new Tuple<>(piece.getStart(), pieceMoves.get(z));
                    //apply the move
                    Move.doMove(chessBoard, singleMoveTuple);
                    Tuple<Integer, Tuple<Long, Long>> child = min(chessBoard, cutoffDepth, curDepth + 1, alpha, beta, !isWhite);
                    Move.undoMove(chessBoard);
                    int childScore = child.getStart();
                    if(childScore > alpha) {
                        alpha = childScore;
                        retVal.setFirst(childScore);
                        retVal.setSecond(singleMoveTuple);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return retVal;
        }
    }

//    /public int quiSearch(Board chessBoard, int alpha, int beta, boolean isWhite){
//        int standPat = isWhite ? evaluate.evaluate(chessBoard, 1) : evaluate.evaluate(chessBoard, -1);
//        List<Tuple<Long, List<Long>>> moveList = isWhite ? Move.generateWhiteMoves(chessBoard) : Move.generateBlackMoves(chessBoard);
//        List<Tuple<Long, List<Long>>> captures = Move.captureMoves(moveList, chessBoard, isWhite);
//        if (standPat >= beta) {
//            return beta;
//        }
//        else if(alpha < beta){
//            alpha = standPat;
//        }
//
//        for(int i = 0; i < captures.size(); i++){
//            Tuple<Long, List<Long>> piece = captures.get(i);
//            List<Long> pieceMoves = piece.getMoves();
//            for(long mv : pieceMoves){
//                Tuple<Long, Long> singleMoveTuple = new Tuple<>(piece.getStart(), mv);
//                Move.doMove(chessBoard, singleMoveTuple);
//                int score = -quiSearch(chessBoard, -beta, -alpha, !isWhite);
//                Move.undoMove(chessBoard);
//
//                if(score >= beta)
//                    return beta;
//                else if (score > alpha)
//                    alpha = score;
//                    bestQuiScores.put(score, singleMoveTuple);
//            }
//        }
//        return alpha;
//    }

}
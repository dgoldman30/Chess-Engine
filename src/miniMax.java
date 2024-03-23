import java.util.Collections;
import java.util.List;

public class miniMax {
    Move Move = new Move();
    Evaluation evaluate = new Evaluation();

    public void search(Board chessBoard, int depth, boolean isWhite) {

        int bestScore = Integer.MIN_VALUE;          //set bestMove to be empty, this is the return value
        Tuple<Long, Long> bestMove = null;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        //populate moveList with the correct color of moves
        List<Tuple<Long, List<Long>>> moveList = isWhite ? Move.generateWhiteMoves(chessBoard) : Move.generateBlackMoves(chessBoard);

        //Collections.shuffle(moveList);
        //iterate through move list
        for (int i = 0; i < moveList.size(); i++) {

            //get each piece
            Tuple<Long, List<Long>> piece = moveList.get(i);

            //get the pieces moves
            List<Long> pieceMoves = piece.getMoves();

            //Randomize piece selection
            //Collections.shuffle(pieceMoves);

            //iterate through the pieces moves
            for (int z = 0; z < pieceMoves.size(); z++) {

                //construct the move with the starting position of the piece and the current(i) end move
                Tuple<Long, Long> singleMoveTuple = new Tuple<>(piece.getStart(), pieceMoves.get(z));
                //apply the move
                Move.doMove(chessBoard, singleMoveTuple);



//                System.out.println("one: " + "\n" + chessBoard);



                int score = min(chessBoard, depth, alpha, beta, isWhite);
//                System.out.println(score);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = singleMoveTuple;
//                    System.out.println("Best Score" + bestScore);
                }
                alpha = Math.max(alpha, bestScore);
                Move.undoMove(chessBoard);
                if (beta <= alpha) {
//                    System.out.println("pruned in search");
                    break;
                }
            }
        }
        //CURRENT ISSUE IS THAT BESTMOVE IS NULL
        Move.doMove(chessBoard, bestMove);
    }

    public int min(Board chessBoard, int depth, int alpha, int beta, boolean isWhite) {
        if (depth == 0) {
            //int score = isWhite ? evaluate.evaluateBlack(chessBoard) : evaluate.evaluateWhite(chessBoard);
            int score = evaluate.evaluateWhite(chessBoard);
            //System.out.println(score);
            return score;
        } else {
            //set the best score to be highest number possible
            int bestScore = Integer.MAX_VALUE;
            //find all new possible moves for other turn
            List<Tuple<Long, List<Long>>> moveList = isWhite ? Move.generateBlackMoves(chessBoard) : Move.generateWhiteMoves(chessBoard);
            //iterate through move list
            for (int i = 0; i < moveList.size(); i++) {
                //get each piece
                Tuple<Long, List<Long>> piece = moveList.get(i);
                // get the pieces moves
                List<Long> pieceMoves = piece.getMoves();
                //iterate through the pieces moves
                for (int z = 0; z < pieceMoves.size(); z++) {
                    //construct the move with the starting position of the piece and the current(i) end move
                    Tuple<Long, Long> singleMoveTuple = new Tuple<>(piece.getStart(), pieceMoves.get(z));
                    //apply the move
                    Move.doMove(chessBoard, singleMoveTuple);

                    //System.out.println("two: " + "\n" + chessBoard);

                    int score = max(chessBoard, depth - 1, alpha, beta, isWhite);
                    bestScore = Math.min(bestScore, score);
                    beta = Math.min(beta, bestScore);
                    Move.undoMove(chessBoard);
                    if (beta <= alpha) {
                        //System.out.println("pruned in min");
                        return bestScore;
                    }
                }
            }
            return bestScore;
        }
    }

    public int max(Board chessBoard, int depth, int alpha, int beta, boolean isWhite) {
        if (depth == 0) {
            //int score = isWhite ? evaluate.evaluateWhite(chessBoard) : evaluate.evaluateBlack(chessBoard);
            //System.out.println(score);

            int score = evaluate.evaluateWhite(chessBoard);
            return score;
        } else {
            //set the best score to be highest number possible
            int bestScore = Integer.MIN_VALUE;
            //find all new possible moves for other turn
            List<Tuple<Long, List<Long>>> moveList = isWhite ? Move.generateWhiteMoves(chessBoard) : Move.generateBlackMoves(chessBoard);
            //iterate through move list
            for (int i = 0; i < moveList.size(); i++) {
                //get each piece
                Tuple<Long, List<Long>> piece = moveList.get(i);
                //get the pieces moves
                List<Long> pieceMoves = piece.getMoves();
                //iterate through the pieces moves
                for (int z = 0; z < pieceMoves.size(); z++) {
                    //construct the move with the starting position of the piece and the current(i) end move
                    Tuple<Long, Long> singleMoveTuple = new Tuple<>(piece.getStart(), pieceMoves.get(z));
                    //apply the move
                    Move.doMove(chessBoard, singleMoveTuple);

                    //System.out.println("Three: " + "\n" + chessBoard);

                    int score = min(chessBoard, depth - 1, alpha, beta, isWhite);
                    bestScore = Math.max(bestScore, score);
                    alpha = Math.max(alpha, bestScore);
                    Move.undoMove(chessBoard);
                    if (beta <= alpha) {
                        //System.out.println("pruned in max");
                        return bestScore;
                    }
                }
            }
            return bestScore;
        }
    }
}


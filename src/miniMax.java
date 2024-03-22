import java.util.List;

public class miniMax {
    Move Move = new Move();
    Evaluation evaluate = new Evaluation();

    public void search(Board chessBoard, int depth, boolean isWhite) {

        int bestScore = Integer.MAX_VALUE;          //set bestMove to be empty, this is the return value
        Tuple<Long, Long> bestMove = null;
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


                System.out.println("one: " + "\n" + chessBoard);


                int score = min(chessBoard, depth, isWhite);
                if (score < bestScore) {
                    bestScore = score;
                    bestMove = singleMoveTuple;
                }
                Move.undoMove(chessBoard);
            }
        }
        //CURRENT ISSUE IS THAT BESTMOVE IS NULL
        Move.doMove(chessBoard, bestMove);
    }

    public int min(Board chessBoard, int depth, boolean isWhite) {
        if (depth == 0) {
            int score = isWhite ? evaluate.evaluateBlack(chessBoard) : evaluate.evaluateWhite(chessBoard);
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

                    int score = max(chessBoard, depth - 1, isWhite);
                    if (score < bestScore) {
                        bestScore = score;
                    }
                    Move.undoMove(chessBoard);
                }
            }
            return bestScore;
        }
    }

    public int max(Board chessBoard, int depth, boolean isWhite) {
        if (depth == 0) {
            int score = isWhite ? evaluate.evaluateWhite(chessBoard) : evaluate.evaluateBlack(chessBoard);
            //System.out.println(score);
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

                    int score = min(chessBoard, depth - 1, isWhite);
                    if (score > bestScore) {
                        bestScore = score;
                    }
                    Move.undoMove(chessBoard);
                }
            }
            return bestScore;
        }
    }
}


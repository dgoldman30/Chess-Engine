public class Evaluation {
    // Evaluation weights for different aspects of the game
    private static final int PAWN_VALUE = 100;
    private static final int KNIGHT_VALUE = 320;
    private static final int BISHOP_VALUE = 330;
    private static final int ROOK_VALUE = 500;
    private static final int QUEEN_VALUE = 900;
    private static final int KING_VALUE = 20000;

    public int evaluateWhite(Board board) {
        int whiteScore = 0;
        int blackScore = 0;
        // Evaluate white pawns
        whiteScore += PAWN_VALUE * Long.bitCount(board.whitePawnBoard);

        // Evaluate white knights
        whiteScore += KNIGHT_VALUE * Long.bitCount(board.whiteKnightBoard);

        // Evaluate white bishops
        whiteScore += BISHOP_VALUE * Long.bitCount(board.whiteBishopBoard);

        // Evaluate white rooks
        whiteScore += ROOK_VALUE * Long.bitCount(board.whiteRookBoard);

        // Evaluate white queens
        whiteScore += QUEEN_VALUE * Long.bitCount(board.whiteQueenBoard);

        // Evaluate white king
        whiteScore += KING_VALUE * Long.bitCount(board.whiteKingBoard);

        // Evaluate black pawns
        blackScore += PAWN_VALUE * Long.bitCount(board.blackPawnBoard);

        // Evaluate black knights
        blackScore += KNIGHT_VALUE * Long.bitCount(board.blackKnightBoard);

        // Evaluate black bishops
        blackScore += BISHOP_VALUE * Long.bitCount(board.blackBishopBoard);

        // Evaluate black rooks
        blackScore += ROOK_VALUE * Long.bitCount(board.blackRookBoard);

        // Evaluate black queens
        blackScore += QUEEN_VALUE * Long.bitCount(board.blackQueenBoard);

        // Evaluate black king
        blackScore += KING_VALUE * Long.bitCount(board.blackKingBoard);

        return whiteScore - blackScore;
    }

    public int evaluateBlack(Board board) {
        int whiteScore = 0;
        int blackScore = 0;
        // Evaluate white pawns
        whiteScore += PAWN_VALUE * Long.bitCount(board.whitePawnBoard);

        // Evaluate white knights
        whiteScore += KNIGHT_VALUE * Long.bitCount(board.whiteKnightBoard);

        // Evaluate white bishops
        whiteScore += BISHOP_VALUE * Long.bitCount(board.whiteBishopBoard);

        // Evaluate white rooks
        whiteScore += ROOK_VALUE * Long.bitCount(board.whiteRookBoard);

        // Evaluate white queens
        whiteScore += QUEEN_VALUE * Long.bitCount(board.whiteQueenBoard);

        // Evaluate white king
        whiteScore += KING_VALUE * Long.bitCount(board.whiteKingBoard);

        // Evaluate black pawns
        blackScore += PAWN_VALUE * Long.bitCount(board.blackPawnBoard);

        // Evaluate black knights
        blackScore += KNIGHT_VALUE * Long.bitCount(board.blackKnightBoard);

        // Evaluate black bishops
        blackScore += BISHOP_VALUE * Long.bitCount(board.blackBishopBoard);

        // Evaluate black rooks
        blackScore += ROOK_VALUE * Long.bitCount(board.blackRookBoard);

        // Evaluate black queens
        blackScore += QUEEN_VALUE * Long.bitCount(board.blackQueenBoard);

        // Evaluate black king
        blackScore += KING_VALUE * Long.bitCount(board.blackKingBoard);

        return blackScore - whiteScore;
    }
}

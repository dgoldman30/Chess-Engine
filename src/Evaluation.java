public class Evaluation {
    // Evaluation weights for different aspects of the game
    private static final int PAWN_VALUE = 100;
    private static final int KNIGHT_VALUE = 320;
    private static final int BISHOP_VALUE = 330;
    private static final int ROOK_VALUE = 500;
    private static final int QUEEN_VALUE = 900;
    private static final int KING_VALUE = 20000;

    public int evaluateWhite(Board board) {
        int score = 0;

        // Evaluate white pawns
        score += PAWN_VALUE * Long.bitCount(board.whitePawnBoard);

        // Evaluate white knights
        score += KNIGHT_VALUE * Long.bitCount(board.whiteKnightBoard);

        // Evaluate white bishops
        score += BISHOP_VALUE * Long.bitCount(board.whiteBishopBoard);

        // Evaluate white rooks
        score += ROOK_VALUE * Long.bitCount(board.whiteRookBoard);

        // Evaluate white queens
        score += QUEEN_VALUE * Long.bitCount(board.whiteQueenBoard);

        // Evaluate white king
        score += KING_VALUE * Long.bitCount(board.whiteKingBoard);

        // Evaluate black pawns
        score -= PAWN_VALUE * Long.bitCount(board.blackPawnBoard);

        // Evaluate black knights
        score -= KNIGHT_VALUE * Long.bitCount(board.blackKnightBoard);

        // Evaluate black bishops
        score -= BISHOP_VALUE * Long.bitCount(board.blackBishopBoard);

        // Evaluate black rooks
        score -= ROOK_VALUE * Long.bitCount(board.blackRookBoard);

        // Evaluate black queens
        score -= QUEEN_VALUE * Long.bitCount(board.blackQueenBoard);

        // Evaluate black king
        score -= KING_VALUE * Long.bitCount(board.blackKingBoard);

        return score;
    }

    public int evaluateBlack(Board board) {
        int score = 0;

        // Evaluate black pawns
        score += PAWN_VALUE * Long.bitCount(board.blackPawnBoard);

        // Evaluate black knights
        score += KNIGHT_VALUE * Long.bitCount(board.blackKnightBoard);

        // Evaluate black bishops
        score += BISHOP_VALUE * Long.bitCount(board.blackBishopBoard);

        // Evaluate black rooks
        score += ROOK_VALUE * Long.bitCount(board.blackRookBoard);

        // Evaluate black queens
        score += QUEEN_VALUE * Long.bitCount(board.blackQueenBoard);

        // Evaluate black king
        score += KING_VALUE * Long.bitCount(board.blackKingBoard);
        // Evaluate white pawns
        score -= PAWN_VALUE * Long.bitCount(board.whitePawnBoard);

        // Evaluate white knights
        score -= KNIGHT_VALUE * Long.bitCount(board.whiteKnightBoard);

        // Evaluate white bishops
        score -= BISHOP_VALUE * Long.bitCount(board.whiteBishopBoard);

        // Evaluate white rooks
        score -= ROOK_VALUE * Long.bitCount(board.whiteRookBoard);

        // Evaluate white queens
        score -= QUEEN_VALUE * Long.bitCount(board.whiteQueenBoard);

        // Evaluate white king
        score -= KING_VALUE * Long.bitCount(board.whiteKingBoard);

        return score;
    }
}

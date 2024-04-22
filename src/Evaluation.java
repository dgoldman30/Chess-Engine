public class Evaluation {
    // Evaluation weights for different aspects of the game
    private static final int PAWN_VALUE = 100;
    private static final int KNIGHT_VALUE = 320;
    private static final int BISHOP_VALUE = 330;
    private static final int ROOK_VALUE = 500;
    private static final int QUEEN_VALUE = 900;
    private static final int KING_VALUE = 20000;
    private final int[] KING_TABLE_MID = {
            19970, 19960, 19960, 19950, 19950, 19960, 19960, 19970,
            19970, 19960, 19960, 19950, 19950, 19960, 19960, 19970,
            19970, 19960, 19960, 19950, 19950, 19960, 19960, 19970,
            19970, 19960, 19960, 19950, 19950, 19960, 19960, 19970,
            19980, 19970, 19970, 19960, 19960, 19970, 19970, 19980,
            19990, 19980, 19980, 19980, 19980, 19980, 19980, 19990,
            20020, 20020, 20000, 20000, 20000, 20000, 20020, 20020,
            20020, 20030, 20010, 20000, 20000, 20010, 20030, 20020,
    };
    private final int[] KING_TABLE_END = {
            19950, 19960, 19970, 19980, 19980, 19970, 19960, 19950,
            19970, 19980, 19990, 20000, 20000, 19990, 19980, 19970,
            19970, 19990, 20020, 20030, 20030, 20020, 19990, 19970,
            19970, 19990, 20030, 20040, 20040, 20030, 19990, 19970,
            19970, 19990, 20030, 20040, 20040, 20030, 19990, 19970,
            19970, 19990, 20020, 20030, 20030, 20020, 19990, 19970,
            19970, 19970, 20000, 20000, 20000, 20000, 19970, 19970,
            19950, 19970, 19970, 19970, 19970, 19970, 19970, 19950,
    };
    private final int[] QUEEN_TABLE = {
            880, 890, 890, 895, 895, 890, 890, 880,
            890, 900, 900, 900, 900, 900, 900, 890,
            890, 900, 905, 905, 905, 905, 900, 890,
            895, 900, 905, 905, 905, 905, 900, 895,
            900, 900, 905, 905, 905, 905, 900, 895,
            890, 905, 905, 905, 905, 905, 900, 890,
            890, 900, 905, 900, 900, 900, 900, 890,
            880, 890, 890, 895, 895, 890, 890, 880,
    };

    private final int[] ROOK_TABLE = {
            500, 500, 500, 500, 500, 500, 500, 500,
            505, 510, 510, 510, 510, 510, 510, 505,
            495, 500, 500, 500, 500, 500, 500, 495,
            495, 500, 500, 500, 500, 500, 500, 495,
            495, 500, 500, 500, 500, 500, 500, 495,
            495, 500, 500, 500, 500, 500, 500, 495,
            495, 500, 500, 500, 500, 500, 500, 495,
            500, 500, 500, 505, 505, 500, 500, 500,
    };

    //BISHOP VAL = 330
    private final int[] BISHOP_TABLE = {
            310, 320, 320, 320, 320, 320, 320, 310,
            320, 330, 330, 330, 330, 330, 330, 320,
            320, 330, 335, 340, 340, 335, 330, 320,
            320, 335, 335, 340, 340, 335, 335, 320,
            320, 330, 340, 340, 340, 340, 330, 320,
            320, 340, 340, 340, 340, 340, 340, 320,
            320, 335, 330, 330, 330, 330, 335, 320,
            310, 320, 320, 320, 320, 320, 320, 310,
    };
    //private static final int KNIGHT_VALUE = 320;
    private final int[] KNIGHT_TABLE = {
            270, 280, 290, 290, 290, 290, 280, 270,
            280, 300, 320, 320, 320, 320, 300, 280,
            290, 320, 330, 335, 335, 330, 320, 290,
            290, 325, 335, 340, 340, 335, 325, 290,
            290, 320, 335, 340, 340, 335, 320, 290,
            290, 325, 330, 335, 335, 330, 325, 290,
            280, 300, 320, 325, 325, 320, 300, 280,
            270, 280, 290, 290, 290, 290, 280, 270,
    };
    private final int[] PAWN_TABLE = {
            100, 100, 100, 100, 100, 100, 100, 100,
            150, 150, 150, 150, 150, 150, 150, 150,
            110, 110, 120, 130, 130, 120, 110, 110,
            105, 105, 110, 125, 125, 110, 105, 105,
            100, 100, 100, 120, 120, 100, 100, 100,
            105, 95, 90, 100, 100, 90, 95, 105,
            105, 110, 110, 80, 80, 110, 110, 105,
            100, 100, 100, 100, 100, 100, 100, 100,
    };





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
        //return whiteScore;
    }

/*    public int evaluateBlack(Board board) {
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
        //return blackScore;
    }*/
}


//piece square table notes
//
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
    private final int[] KING_TABLE_MID_BLACK = {
            20020, 20030, 20010, 20000, 20000, 20010, 20030, 20020,
            20020, 20020, 20000, 20000, 20000, 20000, 20020, 20020,
            19990, 19980, 19980, 19980, 19980, 19980, 19980, 19990,
            19980, 19970, 19970, 19960, 19960, 19970, 19970, 19980,
            19970, 19960, 19960, 19950, 19950, 19960, 19960, 19970,
            19970, 19960, 19960, 19950, 19950, 19960, 19960, 19970,
            19970, 19960, 19960, 19950, 19950, 19960, 19960, 19970,
            19970, 19960, 19960, 19950, 19950, 19960, 19960, 19970,
    };
    private final int[] KING_TABLE_END_BLACK = {
            19950, 19970, 19970, 19970, 19970, 19970, 19970, 19950,
            19970, 19970, 20000, 20000, 20000, 20000, 19970, 19970,
            19970, 19990, 20020, 20030, 20030, 20020, 19990, 19970,
            19970, 19990, 20030, 20040, 20040, 20030, 19990, 19970,
            19970, 19990, 20030, 20040, 20040, 20030, 19990, 19970,
            19970, 19990, 20020, 20030, 20030, 20020, 19990, 19970,
            19970, 19980, 19990, 20000, 20000, 19990, 19980, 19970,
            19950, 19960, 19970, 19980, 19980, 19970, 19960, 19950,
    };
    private final int[] QUEEN_TABLE_BLACK = {
            880, 890, 890, 895, 895, 890, 890, 880,
            890, 900, 905, 900, 900, 900, 900, 890,
            890, 905, 905, 905, 905, 905, 900, 890,
            900, 900, 905, 905, 905, 905, 900, 895,
            895, 900, 905, 905, 905, 905, 900, 895,
            890, 900, 905, 905, 905, 905, 900, 890,
            890, 900, 900, 900, 900, 900, 900, 890,
            880, 890, 890, 895, 895, 890, 890, 880,
    };
    private final int[] ROOK_TABLE_BLACK = {
            500, 500, 500, 505, 505, 500, 500, 500,
            495, 500, 500, 500, 500, 500, 500, 495,
            495, 500, 500, 500, 500, 500, 500, 495,
            495, 500, 500, 500, 500, 500, 500, 495,
            495, 500, 500, 500, 500, 500, 500, 495,
            505, 510, 510, 510, 510, 510, 510, 505,
            500, 500, 500, 500, 500, 500, 500, 500,
    };
    private final int[] BISHOP_TABLE_BLACK = {
            310, 320, 320, 320, 320, 320, 320, 310,
            320, 335, 330, 330, 330, 330, 335, 320,
            320, 340, 340, 340, 340, 340, 340, 320,
            320, 330, 340, 340, 340, 340, 330, 320,
            320, 335, 335, 340, 340, 335, 335, 320,
            320, 330, 335, 340, 340, 335, 330, 320,
            320, 330, 330, 330, 330, 330, 330, 320,
            310, 320, 320, 320, 320, 320, 320, 310,
    };
    private final int[] KNIGHT_TABLE_BLACK = {
            270, 280, 290, 290, 290, 290, 280, 270,
            280, 300, 320, 325, 325, 320, 300, 280,
            290, 325, 330, 335, 335, 330, 325, 290,
            290, 320, 335, 340, 340, 335, 320, 290,
            290, 325, 335, 340, 340, 335, 325, 290,
            290, 320, 330, 335, 335, 330, 320, 290,
            280, 300, 320, 320, 320, 320, 300, 280,
            270, 280, 290, 290, 290, 290, 280, 270,
    };
    private final int[] PAWN_TABLE_BLACK = {
            100, 100, 100, 100, 100, 100, 100, 100,
            105, 110, 110, 80, 80, 110, 110, 105,
            105, 95, 90, 100, 100, 90, 95, 105,
            100, 100, 100, 120, 120, 100, 100, 100,
            105, 105, 110, 125, 125, 110, 105, 105,
            110, 110, 120, 130, 130, 120, 110, 110,
            150, 150, 150, 150, 150, 150, 150, 150,
            100, 100, 100, 100, 100, 100, 100, 100,
    };

    //could probably just pass is white to decide if we remove white from black or black from white:
    public int evaluateWhite(Board board) {
        int whiteScore = 0;
        int blackScore = 0;

        // Evaluate white pieces
        whiteScore += evaluatePiece(board.whitePawnBoard, PAWN_TABLE);
        whiteScore += evaluatePiece(board.whiteKnightBoard, KNIGHT_TABLE);
        whiteScore += evaluatePiece(board.whiteBishopBoard, BISHOP_TABLE);
        whiteScore += evaluatePiece(board.whiteRookBoard, ROOK_TABLE);
        whiteScore += evaluatePiece(board.whiteQueenBoard,QUEEN_TABLE);
        whiteScore += evaluatePiece(board.whiteKingBoard, KING_TABLE_MID);  // Assuming mid-game

        // Evaluate black pieces
        blackScore += evaluatePiece(board.blackPawnBoard, PAWN_TABLE_BLACK);
        blackScore += evaluatePiece(board.blackKnightBoard, KNIGHT_TABLE_BLACK);
        blackScore += evaluatePiece(board.blackBishopBoard, BISHOP_TABLE_BLACK);
        blackScore += evaluatePiece(board.blackRookBoard, ROOK_TABLE_BLACK);
        blackScore += evaluatePiece(board.blackQueenBoard, QUEEN_TABLE_BLACK);
        blackScore += evaluatePiece(board.blackKingBoard, KING_TABLE_MID_BLACK);  // Assuming mid-game

        return whiteScore - blackScore;
    }
    public int evaluateBlack(Board board) {
        int whiteScore = 0;
        int blackScore = 0;

        // Evaluate white pieces
        whiteScore += evaluatePiece(board.whitePawnBoard, PAWN_TABLE);
        whiteScore += evaluatePiece(board.whiteKnightBoard, KNIGHT_TABLE);
        whiteScore += evaluatePiece(board.whiteBishopBoard, BISHOP_TABLE);
        whiteScore += evaluatePiece(board.whiteRookBoard, ROOK_TABLE);
        whiteScore += evaluatePiece(board.whiteQueenBoard, QUEEN_TABLE);
        whiteScore += evaluatePiece(board.whiteKingBoard, KING_TABLE_MID);  // Assuming mid-game

        // Evaluate black pieces
        blackScore += evaluatePiece(board.blackPawnBoard, PAWN_TABLE_BLACK);
        blackScore += evaluatePiece(board.blackKnightBoard, KNIGHT_TABLE_BLACK);
        blackScore += evaluatePiece(board.blackBishopBoard, BISHOP_TABLE_BLACK);
        blackScore += evaluatePiece(board.blackRookBoard, ROOK_TABLE_BLACK);
        blackScore += evaluatePiece(board.blackQueenBoard, QUEEN_TABLE_BLACK);
        blackScore += evaluatePiece(board.blackKingBoard, KING_TABLE_MID_BLACK);  // Assuming mid-game

        return blackScore - whiteScore;
    }


    private int evaluatePiece(long bitboard, int[] table) {
        int score = 0;
        long tempBitboard = bitboard; // Create a temporary copy of the bitboard
        while (tempBitboard != 0) {
            int index = Long.numberOfTrailingZeros(tempBitboard);  // Get the index of the LSB
            score += table[index];  // Add the value from the piece-square table based on the position
            tempBitboard &= tempBitboard - 1;  // Clear the lowest set bit in the temporary bitboard
        }
        return score;
    }
}


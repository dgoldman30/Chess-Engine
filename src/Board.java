import java.util.LinkedList;
import java.util.List;

import static java.lang.Long.SIZE;

public class Board {
    // set the bitboards for each type

    // white
    public long whitePawnBoard;
    public long whiteKnightBoard;
    public long whiteRookBoard;
    public long whiteBishopBoard;
    public long whiteKingBoard;
    public long whiteQueenBoard;

    public long whiteOccBoard;

    // black
    public long blackPawnBoard;
    public long blackKnightBoard;
    public long blackBishopBoard;
    public long blackRookBoard;
    public long blackQueenBoard;
    public long blackKingBoard;

    public long blackOccBoard;

    public long occBoard;

    // attack bitboards
    protected long[] whitePawnAttacks;
    protected long[] blackPawnAttacks;
    protected long[] knightAttacks;

    protected long[] kingAttacks;

    public boolean threeFoldRepetition = false;
    private boolean isStalemate = false;

    private int halfMoveClock; // Tracks the number of half moves since the last pawn move or capture

    // Method to check if the game is drawn due to the fifty-move rule
    public boolean isFiftyMoveDraw() {
        return halfMoveClock >= 100; // Fifty moves by each player
    }

    // Method to update the halfMoveClock
    public void updateHalfMoveClock(Tuple<Long, Long> move) {
        // Increment the halfMoveClock if the move is not a pawn move or capture
        if (true) {
            halfMoveClock++;
        } else {
            halfMoveClock = 0; // Reset the counter if a pawn move or capture is made
        }
    }

    private boolean insufficientPiece = false;

    public boolean isInsufficientPiece() {
        return insufficientPiece;
    }

    public void isInsufficientMaterial() {
        // Check if both sides have only kings
        if (isOnlyKings()) {
            insufficientPiece = true;
        }

        // Check if one side has only a king while the other has a king and a knight or
        // a king and a bishop
        if (isKingVsKingAndKnightOrBishop()) {
            insufficientPiece = true;
        }
    }

    private boolean isOnlyKings() {
        // Assuming whiteOcc and blackOcc represent the bitboards of occupied squares
        // for white and black pieces respectively
        long whiteOcc = this.whiteOccBoard;
        long blackOcc = this.blackOccBoard;

        // Check if both sides have only kings (i.e., no other pieces)
        return whiteOcc == this.whiteKingBoard && blackOcc == this.blackKingBoard;
    }

    private boolean isKingVsKingAndKnightOrBishop() {
        long whiteOcc = this.whiteOccBoard;
        long blackOcc = this.blackOccBoard;

        // Assuming these represent the bitboards of the occupied squares for knights
        // and bishops respectively
        long whiteKnights = this.whiteKnightBoard;
        long blackKnights = this.blackKnightBoard;
        long whiteBishops = this.whiteBishopBoard;
        long blackBishops = this.blackBishopBoard;

        // Check if one side has only a king and the other side has a king and either a
        // knight or a bishop
        return ((whiteOcc == this.whiteKingBoard && blackOcc != this.blackKingBoard)
                || (whiteOcc != this.whiteKingBoard && blackOcc == this.blackKingBoard))
                && ((whiteKnights != 0 || whiteBishops != 0) || (blackKnights != 0 || blackBishops != 0));
    }

    public boolean isStalemate() {
        return isStalemate;
    }

    public void setStalemate(boolean isStalemate) {
        this.isStalemate = isStalemate;
    }

    private boolean whiteInCheck;
    private boolean blackInCheck;

    public boolean isWhiteInCheck() {
        return whiteInCheck;
    }

    public boolean isBlackInCheck() {
        return blackInCheck;
    }

    protected enum BoardState {
        CHECKMATE, STALEMATE;

    }

    private boolean whiteInCheckmate;

    public void setWhiteInCheckmate(boolean whiteInCheckmate) {
        this.whiteInCheckmate = whiteInCheckmate;
    }

    private boolean blackInCheckmate;

    public void setBlackInCheckmate(boolean blackInCheckmate) {
        this.blackInCheckmate = blackInCheckmate;
    }

    public boolean isWhiteInCheckmate() {
        return whiteInCheckmate;
    }

    public boolean isBlackInCheckmate() {
        return blackInCheckmate;
    }

    // Bitmasks for each file
    // NOTICE: this is alphabetically backwards! FILE_A is the file to the far right
    // and FILE_H is the file to the far left
    public static final long FILE_A = 0x0101010101010101L; // 1s on the right edge (with white on bottom)
    public static final long FILE_B = FILE_A << 1;
    public static final long FILE_C = FILE_A << 2;
    public static final long FILE_D = FILE_A << 3;
    public static final long FILE_E = FILE_A << 4;
    public static final long FILE_F = FILE_A << 5;
    public static final long FILE_G = FILE_A << 6;
    public static final long FILE_H = FILE_A << 7; // 1s on the left edge (with white on bottom) -- used to be the other
                                                   // way around, but the board was turned around

    public static final long[] files = {
            0x101010101010101L, 0x202020202020202L, 0x404040404040404L, 0x808080808080808L,
            0x1010101010101010L, 0x2020202020202020L, 0x4040404040404040L, 0x8080808080808080L
    };

    // Bitmasks for each rank
    public static final long RANK_1 = 0xFFL; // 1s along the bottom (white)
    public static final long RANK_2 = RANK_1 << 8;
    public static final long RANK_3 = RANK_1 << 16;
    public static final long RANK_4 = RANK_1 << 24;
    public static final long RANK_5 = RANK_1 << 32;
    public static final long RANK_6 = RANK_1 << 40;
    public static final long RANK_7 = RANK_1 << 48;
    public static final long RANK_8 = RANK_1 << 56; // 1s along the top (black)

    public static final long[] ranks = { 0xFFL, 0xFF00L, 0xFF0000L, 0xFF000000L, 0xFF00000000L, 0xFF0000000000L,
            0xFF000000000000L, 0xFF00000000000000L };

    protected static final long mainDiag = 0x8040201008040201L;
    protected static final long antiDiag = 0x8040201008040201L;

    // CASTLEING
    public boolean whiteCastleKing = true;
    public boolean whiteCastleQueen = true;
    public boolean blackCastleKing = true;
    public boolean blackCastleQueen = true;

    public long whiteCastleKingMask; // F1 G1 White
    public long whiteCastleQueenMask; // B1 C1 D1 White
    public long blackCastleKingMask; // F8 G8 black
    public long blackCastleQueenMask; // B8 C8 D8 black

    public long whiteKing;
    public long whiteRookKing;
    public long whiteRookQueen;
    public long blackKing;
    public long blackRookKing;
    public long blackRookQueen;

    public void createCastleBoards() {

        // Set bits for black kingside castling (F1, G1)
        blackCastleKingMask = (1L << 5) | (1L << 6);

        // Set bits for black queenside castling (B1, C1, D1)
        blackCastleQueenMask = (1L << 1) | (1L << 2) | (1L << 3);

        // Set bits for white kingside castling (F8, G8)
        whiteCastleKingMask = (1L << 61) | (1L << 62);

        // Set bits for white queenside castling (B8, C8, D8)
        whiteCastleQueenMask = (1L << 57) | (1L << 58) | (1L << 59);

        // white king starting location
        blackKing = 1L << 4;

        // black king starting location
        whiteKing = 1L << 60;

        // white rook starting location kingside
        blackRookKing = 1L << 7;
        // white rook starting location queenside
        blackRookQueen = 1L;
        // black rook starting location kingside
        whiteRookKing = 1L << 56;
        // black rook starting location queenside
        whiteRookQueen = 1L << 63;
    }

    // all args constructor
    public Board(long whitePawnBoard, long whiteKnightBoard, long whiteRookBoard, long whiteBishopBoard,
            long whiteKingBoard,
            long whiteQueenBoard, long whiteOccBoard, long blackPawnBoard, long blackKnightBoard, long blackBishopBoard,
            long blackRookBoard, long blackQueenBoard, long blackKingBoard, long blackOccBoard) {

        this.whitePawnBoard = whitePawnBoard;
        this.whiteKnightBoard = whiteKnightBoard;
        this.whiteRookBoard = whiteRookBoard;
        this.whiteBishopBoard = whiteBishopBoard;
        this.whiteKingBoard = whiteKingBoard;
        this.whiteQueenBoard = whiteQueenBoard;
        this.whiteOccBoard = whiteOccBoard;
        this.blackPawnBoard = blackPawnBoard;
        this.blackKnightBoard = blackKnightBoard;
        this.blackBishopBoard = blackBishopBoard;
        this.blackRookBoard = blackRookBoard;
        this.blackQueenBoard = blackQueenBoard;
        this.blackKingBoard = blackKingBoard;
        this.blackOccBoard = blackOccBoard;
        this.whitePawnAttacks = pawnWhiteAttackBitboards();
        this.blackPawnAttacks = blackPawnAttackBitboards();
        this.knightAttacks = knightAttackBitboards();
        this.occBoard = blackOccBoard | whiteOccBoard;
    }

    // empty constructor
    public Board() {
        this.whitePawnAttacks = pawnWhiteAttackBitboards();
        this.blackPawnAttacks = blackPawnAttackBitboards();
        this.knightAttacks = knightAttackBitboards();
        this.kingAttacks = kingAttackBitboards();
        createCastleBoards();
    }

    // used for testing purposes
    public Board(long blackKingBoard) {
        this.blackKingBoard = blackKingBoard;
    }

    public Board(Board origBoard) {
        this.whitePawnBoard = origBoard.whitePawnBoard;
        this.whiteKnightBoard = origBoard.whiteKnightBoard;
        this.whiteRookBoard = origBoard.whiteRookBoard;
        this.whiteBishopBoard = origBoard.whiteBishopBoard;
        this.whiteKingBoard = origBoard.whiteKingBoard;
        this.whiteQueenBoard = origBoard.whiteQueenBoard;
        this.whiteOccBoard = origBoard.whiteOccBoard;
        this.blackPawnBoard = origBoard.blackPawnBoard;
        this.blackKnightBoard = origBoard.blackKnightBoard;
        this.blackBishopBoard = origBoard.blackBishopBoard;
        this.blackRookBoard = origBoard.blackRookBoard;
        this.blackQueenBoard = origBoard.blackQueenBoard;
        this.blackKingBoard = origBoard.blackKingBoard;
        this.blackOccBoard = origBoard.blackOccBoard;
        this.whitePawnAttacks = origBoard.whitePawnAttacks;
        this.blackPawnAttacks = origBoard.blackPawnAttacks;
        this.knightAttacks = origBoard.knightAttacks;
        this.kingAttacks = origBoard.kingAttacks;
    }

    public void setWhitePawnBoard(long whitePawnBoard) {
        this.whitePawnBoard = whitePawnBoard;
    }

    public void setWhiteKnightBoard(long whiteKnightBoard) {
        this.whiteKnightBoard = whiteKnightBoard;
    }

    public void setWhiteRookBoard(long whiteRookBoard) {
        this.whiteRookBoard = whiteRookBoard;
    }

    public void setWhiteBishopBoard(long whiteBishopBoard) {
        this.whiteBishopBoard = whiteBishopBoard;
    }

    public void setWhiteKingBoard(long whiteKingBoard) {
        this.whiteKingBoard = whiteKingBoard;
    }

    public void setWhiteQueenBoard(long whiteQueenBoard) {
        this.whiteQueenBoard = whiteQueenBoard;
    }

    public void setWhiteOccBoard(long whiteOccBoard) {
        this.whiteOccBoard = whiteOccBoard;
    }

    public void setBlackPawnBoard(long blackPawnBoard) {
        this.blackPawnBoard = blackPawnBoard;
    }

    public void setBlackKnightBoard(long blackKnightBoard) {
        this.blackKnightBoard = blackKnightBoard;
    }

    public void setBlackBishopBoard(long blackBishopBoard) {
        this.blackBishopBoard = blackBishopBoard;
    }

    public void setBlackRookBoard(long blackRookBoard) {
        this.blackRookBoard = blackRookBoard;
    }

    public void setBlackQueenBoard(long blackQueenBoard) {
        this.blackQueenBoard = blackQueenBoard;
    }

    public void setBlackKingBoard(long blackKingBoard) {
        this.blackKingBoard = blackKingBoard;
    }

    public void setBlackOccBoard(long blackOccBoard) {
        this.blackOccBoard = blackOccBoard;
    }

    public void setOccBoard(long occBoard) {
        this.occBoard = occBoard;
    }

    protected long[] pawnWhiteAttackBitboards() {

        long[] pawnAttacks = new long[64];

        for (int square = 0; square < 64; square++) {
            long pawn = 1L << square;
            long attacks = (pawn << 7) & ~FILE_A;
            attacks |= (pawn << 9) & ~FILE_H;
            pawnAttacks[square] = attacks;
        }

        return pawnAttacks;
    }

    protected long[] blackPawnAttackBitboards() {
        long[] pawnAttacks = new long[64];

        for (int square = 0; square < 64; square++) {
            long pawn = 1L << square;
            long attacks = (pawn >>> 9) & ~FILE_A;
            attacks |= (pawn >>> 7) & ~FILE_H;
            pawnAttacks[square] = attacks;
        }

        return pawnAttacks;
    }

    protected long[] knightAttackBitboards() {
        long[] knightAttacks = new long[64];

        for (int square = 0; square < 64; square++) {
            long knight = 1L << square;
            long attacks = 0;
            attacks |= (knight << 17) & ~FILE_A & ~FILE_B;
            attacks |= (knight << 15) & ~FILE_H & ~FILE_G;
            attacks |= (knight << 10) & ~FILE_A & ~FILE_B & ~FILE_C;
            attacks |= (knight << 6) & ~FILE_H & ~FILE_G & ~FILE_F;
            attacks |= (knight >>> 6) & ~FILE_A & ~FILE_B & ~FILE_C;
            attacks |= (knight >>> 10) & ~FILE_H & ~FILE_G & ~FILE_F;
            attacks |= (knight >>> 15) & ~FILE_A & ~FILE_B;
            attacks |= (knight >>> 17) & ~FILE_H & ~FILE_G;
            knightAttacks[square] = attacks;
        }
        return knightAttacks;
    }

    protected long generateKingMoves(int sq) {
        long king = 1L << sq;
        long attacks = 0L;

        attacks |= king << 1;
        attacks |= king >>> 1;
        attacks |= king << 8;
        attacks |= king >>> 8;
        attacks |= king << 7;
        attacks |= king >>> 7;
        attacks |= king << 9;
        attacks |= king >>> 9;

        attacks &= ~FILE_H;
        attacks &= ~FILE_A;
        attacks &= ~RANK_8;
        attacks &= ~RANK_1;

        return attacks;
    }

    protected long[] kingAttackBitboards() {
        long[] kingAttacks = new long[64];

        for (int sq = 0; sq < 64; sq++) {
            long attacks = generateKingMoves(sq);
            kingAttacks[sq] = attacks;
        }

        return kingAttacks;
    }

    // Masks and things used for sliding pieces
    private static long southMask(int sq) {
        return 0x0101010101010100L << sq;
    }

    private static long northMask(int sq) {
        return 0x0080808080808080L >> (sq ^ 63);
    }

    private static long eastMask(int sq) {
        long one = 1L;
        return 2 * ((one << (sq | 7)) - (one << sq));
    }

    private static long westMask(int sq) {
        long one = 1L;
        return (one << sq) - (one << (sq & 56));
    }

    private static long SEmask(int sq) {
        return diagMask(sq) & (-2L << sq);
    }

    private static long SWmask(int sq) {
        return antiDiagMask(sq) & (-2L << sq);
    }

    private static long NWmask(int sq) {
        return diagMask(sq) & ((1L << sq) - 1);
    }

    private static long NEmask(int sq) {
        return antiDiagMask(sq) & ((1L << sq) - 1);
    }

    private static long rankMask(int sq) {
        return 0xFFL << (sq & 56);
    }

    private static long fileMask(int sq) {
        return 0x0101010101010101L << (sq & 7);
    }

    private static long diagMask(int sq) {
        long maindia = 0x8040201008040201L;
        int diag = (sq & 7) - (sq >>> 3);
        return diag >= 0 ? maindia >>> diag * 8 : maindia << -diag * 8;
    }

    private static long antiDiagMask(int sq) {
        long maindia = 0x0102040810204080L;
        int diag = 7 - (sq & 7) - (sq >>> 3);
        return diag >= 0 ? maindia >>> diag * 8 : maindia << -diag * 8;
    }

    protected long rookMask(int sq) {
        return rankMask(sq) ^ fileMask(sq);
    }

    protected long bishopMask(int sq) {
        return diagMask(sq) ^ antiDiagMask(sq);
    }

    // protected long queenMask(int sq){
    // return rookMask(sq) ^ bishopMask(sq);
    // }

    protected long getRookAttacks(int sq, long whiteOccBoard, long blackOccBoard) {
        long occupancy = whiteOccBoard | blackOccBoard;
        long rookAttacks = rookMask(sq);
        int blockIdx;
        long blockMask;
        long northBlock = northMask(sq) & occupancy;
        long southBlock = southMask(sq) & occupancy;
        long eastBlock = eastMask(sq) & occupancy;
        long westBlock = westMask(sq) & occupancy;

        if (northBlock != 0) {
            blockIdx = 63 - Long.numberOfLeadingZeros(northBlock);
            blockMask = northMask(blockIdx);
            rookAttacks ^= blockMask;
        }

        if (southBlock != 0) {
            blockIdx = Long.numberOfTrailingZeros(southBlock);
            blockMask = southMask(blockIdx);
            rookAttacks ^= blockMask;
        }

        if (eastBlock != 0) {
            blockIdx = Long.numberOfTrailingZeros(eastBlock);
            blockMask = eastMask(blockIdx);
            rookAttacks ^= blockMask;
        }

        if (westBlock != 0) {
            blockIdx = 63 - Long.numberOfLeadingZeros(westBlock);
            blockMask = westMask(blockIdx);
            rookAttacks ^= blockMask;
        }

        return rookAttacks;
    }

    protected long getBishopAttacks(int sq, long whiteOccBoard, long blackOccBoard) {
        long occupancy = whiteOccBoard | blackOccBoard;
        long bishopAttacks = bishopMask(sq);
        int blockIdx;
        long blockMask;
        long NWblock = NWmask(sq) & occupancy;
        long NEblock = NEmask(sq) & occupancy;
        long SWblock = SWmask(sq) & occupancy;
        long SEblock = SEmask(sq) & occupancy;

        if (NWblock != 0) {
            blockIdx = 63 - Long.numberOfLeadingZeros(NWblock);
            blockMask = NWmask(blockIdx);
            bishopAttacks ^= blockMask;
        }

        if (NEblock != 0) {
            blockIdx = 63 - Long.numberOfLeadingZeros(NEblock);
            blockMask = NEmask(blockIdx);
            bishopAttacks ^= blockMask;
        }

        if (SWblock != 0) {
            blockIdx = Long.numberOfTrailingZeros(SWblock);
            blockMask = SWmask(blockIdx);
            bishopAttacks ^= blockMask;
        }

        if (SEblock != 0) {
            blockIdx = Long.numberOfTrailingZeros(SEblock);
            blockMask = SEmask(blockIdx);
            bishopAttacks ^= blockMask;
        }

        return bishopAttacks;
    }

    protected long getQueenAttacks(int sq, long whiteOccBoard, long blackOccBoard) {
        return getBishopAttacks(sq, whiteOccBoard, blackOccBoard) | getRookAttacks(sq, whiteOccBoard, blackOccBoard);
    }

    // inCheck testing
    protected boolean inCheck(Board chessBoard, boolean isWhite) {
        long kingBoard = isWhite ? chessBoard.whiteKingBoard : chessBoard.blackKingBoard;
        int kingPos = (SIZE - Long.numberOfLeadingZeros(kingBoard)) - 1;
        // Attacks are from the king position
        long[] pawnAttacks = isWhite ? chessBoard.blackPawnAttacks : chessBoard.whitePawnAttacks;
        long[] knightAttacks = chessBoard.knightAttacks;
        long[] kingAttacks = chessBoard.kingAttacks;
        long rookAttacks = chessBoard.getRookAttacks(kingPos, chessBoard.whiteOccBoard, chessBoard.blackOccBoard);
        long bishopAttacks = chessBoard.getBishopAttacks(kingPos, chessBoard.whiteOccBoard, chessBoard.blackOccBoard);
        long queenAttacks = chessBoard.getQueenAttacks(kingPos, chessBoard.whiteOccBoard, chessBoard.blackOccBoard);

        Board b = new Board(rookAttacks);
        // System.out.println("rook attacks\n" + b);

        // System.out.println("king Pos = " + kingPos);

        Board b2 = new Board(bishopAttacks);
        // System.out.println("bishop attacks \n" + b2);

        boolean inCheck = false;

        if (((isWhite ? chessBoard.blackPawnBoard : chessBoard.whitePawnBoard) & pawnAttacks[kingPos]) != 0
                || ((isWhite ? chessBoard.blackKnightBoard : chessBoard.whiteKnightBoard) & knightAttacks[kingPos]) != 0
                || ((isWhite ? chessBoard.blackBishopBoard : chessBoard.whiteBishopBoard) & bishopAttacks) != 0
                || ((isWhite ? chessBoard.blackRookBoard : chessBoard.whiteRookBoard) & rookAttacks) != 0
                || ((isWhite ? chessBoard.blackQueenBoard : chessBoard.whiteQueenBoard) & queenAttacks) != 0
                // illegal for kings to check other kings -> still want to check as we generate
                // all possible moves
                || ((isWhite ? chessBoard.blackKingBoard : chessBoard.whiteKingBoard) & kingAttacks[kingPos]) != 0)
            inCheck = true;

        if (isWhite) {
            this.whiteInCheck = inCheck;
        } else {
            this.blackInCheck = inCheck;
        }

        return inCheck;
    }

    protected static List<Tuple<Move.pieceNames, Long>> inCheckList(Board chessBoard, boolean isWhite) {
        List<Tuple<Move.pieceNames, Long>> inCheck = new LinkedList<>();
        long kingBoard = isWhite ? chessBoard.whiteKingBoard : chessBoard.blackKingBoard;
        int kingPos = (SIZE - Long.numberOfLeadingZeros(kingBoard)) - 1;
        // Attacks are from the king position
        long[] pawnAttacks = isWhite ? chessBoard.blackPawnAttacks : chessBoard.whitePawnAttacks;
        long[] knightAttacks = chessBoard.knightAttacks;
        long[] kingAttacks = chessBoard.kingAttacks;
        long rookAttacks = chessBoard.getRookAttacks(kingPos, chessBoard.whiteOccBoard, chessBoard.blackOccBoard);
        long bishopAttacks = chessBoard.getBishopAttacks(kingPos, chessBoard.whiteOccBoard, chessBoard.blackOccBoard);
        long queenAttacks = chessBoard.getQueenAttacks(kingPos, chessBoard.whiteOccBoard, chessBoard.blackOccBoard);

        long pawnAttacker = ((isWhite ? chessBoard.blackPawnBoard : chessBoard.whitePawnBoard) & pawnAttacks[kingPos]);
        long knightAttacker = ((isWhite ? chessBoard.blackKnightBoard : chessBoard.whiteKnightBoard)
                & knightAttacks[kingPos]);
        long bishopAttacker = ((isWhite ? chessBoard.blackBishopBoard : chessBoard.whiteBishopBoard) & bishopAttacks);
        long rookAttacker = ((isWhite ? chessBoard.blackRookBoard : chessBoard.whiteRookBoard) & rookAttacks);
        long queenAttacker = ((isWhite ? chessBoard.blackQueenBoard : chessBoard.whiteQueenBoard) & queenAttacks);
        long kingAttacker = ((isWhite ? chessBoard.blackKingBoard : chessBoard.whiteKingBoard) & kingAttacks[kingPos]);

        if (pawnAttacker != 0)
            inCheck.add(isWhite ? new Tuple<>(Move.pieceNames.BP, pawnAttacker)
                    : new Tuple<>(Move.pieceNames.WP, pawnAttacker));
        else if (knightAttacker != 0)
            inCheck.add(isWhite ? new Tuple<>(Move.pieceNames.BN, knightAttacker)
                    : new Tuple<>(Move.pieceNames.WN, knightAttacker));
        else if (bishopAttacker != 0)
            inCheck.add(isWhite ? new Tuple<>(Move.pieceNames.BB, bishopAttacker)
                    : new Tuple<>(Move.pieceNames.WB, bishopAttacker));
        else if (rookAttacker != 0)
            inCheck.add(isWhite ? new Tuple<>(Move.pieceNames.BR, rookAttacker)
                    : new Tuple<>(Move.pieceNames.WR, rookAttacker));
        else if (queenAttacker != 0)
            inCheck.add(isWhite ? new Tuple<>(Move.pieceNames.BQ, queenAttacker)
                    : new Tuple<>(Move.pieceNames.WQ, queenAttacker));
        else if (kingAttacker != 0)
            inCheck.add(isWhite ? new Tuple<>(Move.pieceNames.BK, kingAttacker)
                    : new Tuple<>(Move.pieceNames.WK, kingAttacker));

        return inCheck;
    }

    // protected void boardState() {
    // if (whiteInCheck)
    // whiteInCheckmate = true;
    // if (blackInCheck)
    // blackInCheckmate = true;
    // }

    // protected BoardState getBoardState() {
    // if (checkMate)
    // return BoardState.CHECKMATE;

    // return null;
    // }

    @Override
    public String toString() {
        String ret = "";
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                int square = rank * 8 + file;
                long squareMask = 1L << square;

                char piece = '-';// empty square

                // place piece if bitboard is occupied for the given piece
                if ((blackPawnBoard & squareMask) != 0) {
                    piece = '♙';
                } else if ((blackKnightBoard & squareMask) != 0) {
                    piece = '♘';
                } else if ((blackBishopBoard & squareMask) != 0) {
                    piece = '♗';
                } else if ((blackRookBoard & squareMask) != 0) {
                    piece = '♖';
                } else if ((blackQueenBoard & squareMask) != 0) {
                    piece = '♕';
                } else if ((blackKingBoard & squareMask) != 0) {
                    piece = '♔';
                } else if ((whitePawnBoard & squareMask) != 0) {
                    piece = '♟';
                } else if ((whiteKnightBoard & squareMask) != 0) {
                    piece = '♞';
                } else if ((whiteBishopBoard & squareMask) != 0) {
                    piece = '♝';
                } else if ((whiteRookBoard & squareMask) != 0) {
                    piece = '♜';
                } else if ((whiteQueenBoard & squareMask) != 0) {
                    piece = '♛';
                } else if ((whiteKingBoard & squareMask) != 0) {
                    piece = '♚';
                }
                ret += piece + " ";
            }
            System.out.println();
            ret += "\n";
        }
        return ret;
    }

    // CHANGES BOARD STRING INTO BITBOARDS
    public void stringToBitBoard(String str) {

        Long bitBoard = 0000000000000000000000000000000000000000000000000000000000000001L;
        // need to shift the 1 to the place of the char and then add it to the bitBoard
        // for the individual piece.

        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case '-':
                    break;
                case 'P':
                    whitePawnBoard |= bitBoard << i;
                    whiteOccBoard |= bitBoard << i;
                    break;
                case 'N':
                    whiteKnightBoard |= bitBoard << i;
                    whiteOccBoard |= bitBoard << i;
                    break;
                case 'B':
                    whiteBishopBoard |= bitBoard << i;
                    whiteOccBoard |= bitBoard << i;
                    break;
                case 'R':
                    whiteRookBoard |= bitBoard << i;
                    whiteOccBoard |= bitBoard << i;
                    break;
                case 'Q':
                    whiteQueenBoard |= bitBoard << i;
                    whiteOccBoard |= bitBoard << i;
                    break;
                case 'K':
                    whiteKingBoard |= bitBoard << i;
                    whiteOccBoard |= bitBoard << i;
                    break;
                case 'p':
                    blackPawnBoard |= bitBoard << i;
                    blackOccBoard |= bitBoard << i;
                    break;
                case 'n':
                    blackKnightBoard |= bitBoard << i;
                    blackOccBoard |= bitBoard << i;
                    break;
                case 'b':
                    blackBishopBoard |= bitBoard << i;
                    blackOccBoard |= bitBoard << i;
                    break;
                case 'r':
                    blackRookBoard |= bitBoard << i;
                    blackOccBoard |= bitBoard << i;
                    break;
                case 'q':
                    blackQueenBoard |= bitBoard << i;
                    blackOccBoard |= bitBoard << i;
                    break;
                case 'k':
                    blackKingBoard |= bitBoard << i;
                    blackOccBoard |= bitBoard << i;
                    break;
            }
        }
    }
}

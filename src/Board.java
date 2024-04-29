public class Board {
    //set the bitboards for each type

    //white
    public long whitePawnBoard;
    public long whiteKnightBoard;
    public long whiteRookBoard;
    public long whiteBishopBoard;
    public long whiteKingBoard;
    public long whiteQueenBoard;

    public long whiteOccBoard;


    //black
    public long blackPawnBoard;
    public long blackKnightBoard;
    public long blackBishopBoard;
    public long blackRookBoard;
    public long blackQueenBoard;
    public long blackKingBoard;

    public long blackOccBoard;

    public long occBoard = blackOccBoard | whiteOccBoard;

    // attack bitboards
    protected long[] whitePawnAttacks;
    protected long[] blackPawnAttacks;
    protected long[] knightAttacks;

    protected long[] bishopAttacks;

    // Bitmasks for each file
    // NOTICE: this is alphabetically backwards! FILE_A is the file to the far right and FILE_H is the file to the far left
    public static final long FILE_A = 0x0101010101010101L; // 1s on the right edge (with white on bottom)
    public static final long FILE_B = FILE_A << 1;
    public static final long FILE_C = FILE_A << 2;
    public static final long FILE_D = FILE_A << 3;
    public static final long FILE_E = FILE_A << 4;
    public static final long FILE_F = FILE_A << 5;
    public static final long FILE_G = FILE_A << 6;
    public static final long FILE_H = FILE_A << 7; // 1s on the left edge (with white on bottom) -- used to be the other way around, but the board was turned around

    public static final long[] files = {FILE_H, FILE_G, FILE_F, FILE_E, FILE_D, FILE_C, FILE_B, FILE_A};

    // Bitmasks for each rank
    public static final long RANK_1 = 0xFFL; // 1s along the bottom (white)
    public static final long RANK_2 = RANK_1 << 8;
    public static final long RANK_3 = RANK_1 << 16;
    public static final long RANK_4 = RANK_1 << 24;
    public static final long RANK_5 = RANK_1 << 32;
    public static final long RANK_6 = RANK_1 << 40;
    public static final long RANK_7 = RANK_1 << 48;
    public static final long RANK_8 = RANK_1 << 56; //1s along the top (black)

    public static final long[] ranks = {RANK_8, RANK_7, RANK_6, RANK_5, RANK_4, RANK_3, RANK_2, RANK_1};

    //all args constructor
    public Board(long whitePawnBoard, long whiteKnightBoard, long whiteRookBoard, long whiteBishopBoard, long whiteKingBoard,
                 long whiteQueenBoard, long whiteOccBoard, long blackPawnBoard, long blackKnightBoard, long blackBishopBoard,
                 long blackRookBoard, long blackQueenBoard, long blackKingBoard, long blackOccBoard){
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
    }

    //empty constructor
    public Board() {
        this.whitePawnAttacks = pawnWhiteAttackBitboards();
        this.blackPawnAttacks = blackPawnAttackBitboards();
        this.knightAttacks = knightAttackBitboards();
        this.bishopAttacks = bishopAttackBitboards();
    }

    public Board(long blackKingBoard, long whitePawnBoard){
        this.blackKingBoard = blackKingBoard;
        this.whitePawnBoard = whitePawnBoard;
    }

    protected long[] pawnWhiteAttackBitboards(){
        long[] pawnAttacks = new long[64];

        for(int square = 0; square < 64; square++){
            long pawn = 1L << square;
            long attacks = (pawn << 7) & ~FILE_A;
            attacks |= (pawn << 9) & ~FILE_H;
            pawnAttacks[square] = attacks;
        }

        return pawnAttacks;
    }

    protected long[] blackPawnAttackBitboards(){
        long[] pawnAttacks = new long[64];

        for(int square = 0; square < 64; square++){
            long pawn = 1L << square;
            long attacks = (pawn >>> 9) & ~FILE_A;
            attacks |= (pawn >>> 7) & ~FILE_H;
            pawnAttacks[square] = attacks;
        }

        return pawnAttacks;
    }

    protected long[] knightAttackBitboards(){
        long[] knightAttacks = new long[64];

        for(int square = 0; square < 64; square++){
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

    protected long generateBishopAttacks(int square){
        long bishop = 1l << square;
        long attacks = 0l;

        for(int i = 1; i <= 7; i++){
            long northEast = (bishop << (i * 8 + i)) & ~FILE_A;
            long northWest = (bishop << (i * 8 - i)) & ~FILE_H;
            long southEast = (bishop >>> (i * 8 - i)) & ~FILE_H;
            long southWest = (bishop >>> (i * 8 + i)) & ~FILE_A;

            attacks |= northEast | northWest | southWest | southEast;

            if((attacks & occBoard) != 0)
                break;
        }

        return attacks;
    }

    protected long[] bishopAttackBitboards() {
        long[] bishopAttacks = new long[64];

        for(int square = 0; square < 64; square++){
            long attacks = generateBishopAttacks(square);
            bishopAttacks[square] = attacks;
        }

        return bishopAttacks;
    }


    @Override
    public String toString() {
        String ret = "";
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                int square = rank * 8 + file;
                long squareMask = 1L << square;

                char piece = '-';//empty square

                //place piece if bitboard is occupied for the given piece
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

    //CHANGES BOARD STRING INTO BITBOARDS
    public void stringToBitBoard(String str) {

        Long bitBoard = 0000000000000000000000000000000000000000000000000000000000000001L;
        //need to shift the 1 to the place of the char and then add it to the bitBoard for the individual piece.

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

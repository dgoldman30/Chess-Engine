public class Board {

    //UPPERCASE IS WHITE AND STARTS AT THE BOTTOM OF THIS STRING AND MOVES UP
    String str =
                    "--------" +
                    "--------" +
                    "--------" +
                    "--------" +
                    "--------" +
                    "--------" +
                    "PPPPPPPP" +
                    "RNBQKBNR";

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



    // Bitmasks for each file
    public static final long FILE_A = 0x0101010101010101L; // 1s on the left edge (with white on bottom)
    public static final long FILE_B = FILE_A << 1;
    public static final long FILE_C = FILE_A << 2;
    public static final long FILE_D = FILE_A << 3;
    public static final long FILE_E = FILE_A << 4;
    public static final long FILE_F = FILE_A << 5;
    public static final long FILE_G = FILE_A << 6;
    public static final long FILE_H = FILE_A << 7; // 1s on the right edge (with white on bottom)

    // Bitmasks for each rank
    public static final long RANK_1 = 0xFFL; // 1s along the bottom (white)
    public static final long RANK_2 = RANK_1 << 8;
    public static final long RANK_3 = RANK_1 << 16;
    public static final long RANK_4 = RANK_1 << 24;
    public static final long RANK_5 = RANK_1 << 32;
    public static final long RANK_6 = RANK_1 << 40;
    public static final long RANK_7 = RANK_1 << 48;
    public static final long RANK_8 = RANK_1 << 56; //1s along the top (black)

    //all args constructor
    public Board(long whitePawnBoard, long whiteKnightBoard, long whiteRookBoard, long whiteBishopBoard, long whiteKingBoard, long whiteQueenBoard, long whiteOccBoard, long blackPawnBoard, long blackKnightBoard, long blackBishopBoard, long blackRookBoard, long blackQueenBoard, long blackKingBoard, long blackOccBoard) {
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
    }

    //empty constructor
    public Board() {
        //initialize an empty board
        //whitePawnBoard = 0L;
        //whiteKnightBoard = 0L;
        //whiteRookBoard = 0L;
        //whiteBishopBoard = 0L;
        //whiteKingBoard = 0L;
        //whiteQueenBoard = 0L;
        //whiteOccBoard = 0l;
        //occBoard = 0L;
    }

    @Override
    public String toString() {
        String ret = "";
        for (int rank = 7; rank >= 0; rank--) {
            for (int file = 0; file < 8; file++) {
                int square = rank * 8 + file;
                long squareMask = 1L << square;

                char piece = '-';//empty square

                //place piece if bitboard is occupied for the given piece
                if ((whitePawnBoard & squareMask) != 0) {
                    piece = 'P';
                } else if ((whiteKnightBoard & squareMask) != 0) {
                    piece = 'N';
                } else if ((whiteBishopBoard & squareMask) != 0) {
                    piece = 'B';
                } else if ((whiteRookBoard & squareMask) != 0) {
                    piece = 'R';
                } else if ((whiteQueenBoard & squareMask) != 0) {
                    piece = 'Q';
                } else if ((whiteKingBoard & squareMask) != 0) {
                    piece = 'K';
                }else if ((blackPawnBoard & squareMask) != 0) {
                    piece = 'p';
                } else if ((blackKnightBoard & squareMask) != 0) {
                    piece = 'n';
                } else if ((blackBishopBoard & squareMask) != 0) {
                    piece = 'b';
                } else if ((blackRookBoard & squareMask) != 0) {
                    piece = 'r';
                } else if ((blackQueenBoard & squareMask) != 0) {
                    piece = 'q';
                } else if ((blackKingBoard & squareMask) != 0) {
                    piece = 'k';
                }
                ret += piece + " ";
            }
            System.out.println();
            ret += "\n";
        }
        return ret;
    }

    //CHANGES BOARD STRING INTO BITBOARDS
    public void stringToBitBoard(){

        Long bitBoard = 0000000000000000000000000000000000000000000000000000000000000001L;
        //need to shift the 1 to the place of the char and then add it to the bitBoard for the individual piece.

        for(int i=0; i < str.length(); i++){
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

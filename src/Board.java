public class Board extends Main{

    //set the bitboards for each type

    //white
    public long whitePawnBoard;
    public long whiteKnightBoard;
    public long whiteRookBoard;
    public long whiteBishopBoard;
    public long whiteKingBoard;
    public long whiteQueenBoard;


    //black
    public long blackPawnBoard;
    public long blackKnightBoard;
    public long blackBishopBoard;
    public long blackRookBoard;
    public long blackQueenBoard;
    public long blackKingBoard;

    //Make holding board
    public long occBoard;

    // Bitmasks for each file
    public static final long FILE_A = 0x0101010101010101L;
    public static final long FILE_B = FILE_A << 1;
    public static final long FILE_C = FILE_A << 2;
    public static final long FILE_D = FILE_A << 3;
    public static final long FILE_E = FILE_A << 4;
    public static final long FILE_F = FILE_A << 5;
    public static final long FILE_G = FILE_A << 6;
    public static final long FILE_H = FILE_A << 7;

    // Bitmasks for each rank
    public static final long RANK_1 = 0xFFL;
    public static final long RANK_2 = RANK_1 << 8;
    public static final long RANK_3 = RANK_1 << 16;
    public static final long RANK_4 = RANK_1 << 24;
    public static final long RANK_5 = RANK_1 << 32;
    public static final long RANK_6 = RANK_1 << 40;
    public static final long RANK_7 = RANK_1 << 48;
    public static final long RANK_8 = RANK_1 << 56;



    //constructor
    public Board() {
        //initialize an empty board
        whitePawnBoard = 0L;
        whiteKnightBoard = 0L;
        whiteRookBoard = 0L;
        whiteBishopBoard = 0L;
        whiteKingBoard = 0L;
        whiteQueenBoard = 0L;
        occBoard = 0L;
    }

    //CHANGES BOARD STRING INTO BITBOARDS
    public void stringToBitBoard(String str){

        Long bitBoard = 0000000000000000000000000000000000000000000000000000000000000001L;
        //need to shift the 1 to the place of the char and then add it to the bitBoard for the individual piece.

        for(int i=0; i < str.length(); i++){
            switch (str.charAt(i)) {
                case '-':
                    break;
                case 'P':
                    whitePawnBoard |= bitBoard << i;
                    occBoard |= bitBoard << i;
                    break;
                case 'N':
                    whiteKnightBoard |= bitBoard << i;
                    occBoard |= bitBoard << i;
                    break;
                case 'B':
                    whiteBishopBoard |= bitBoard << i;
                    occBoard |= bitBoard << i;
                    break;
                case 'R':
                    whiteRookBoard |= bitBoard << i;
                    occBoard |= bitBoard << i;
                    break;
                case 'Q':
                    whiteQueenBoard |= bitBoard << i;
                    occBoard |= bitBoard << i;
                    break;
                case 'K':
                    whiteKingBoard |= bitBoard << i;
                    occBoard |= bitBoard << i;
                    break;
                case 'p':
                    blackPawnBoard |= bitBoard << i;
                    occBoard |= bitBoard << i;
                    break;
                case 'n':
                    blackKnightBoard |= bitBoard << i;
                    occBoard |= bitBoard << i;
                    break;
                case 'b':
                    blackBishopBoard |= bitBoard << i;
                    occBoard |= bitBoard << i;
                    break;
                case 'r':
                    blackRookBoard |= bitBoard << i;
                    occBoard |= bitBoard << i;
                    break;
                case 'q':
                    blackQueenBoard |= bitBoard << i;
                    occBoard |= bitBoard << i;
                    break;
                case 'k':
                    blackKingBoard |= bitBoard << i;
                    occBoard |= bitBoard << i;
                    break;
            }
        }

    }


    // Print the board state
    public void printBoard() {
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
                System.out.print(piece + " ");
            }
            System.out.println();
        }
    }




    //function to set a piece NOT CURRENTLY IN USE, JUST ANOTHER OPTION

    public void setPiece(char piece, int place) { //REPLACE CHAR PIECE WITH ENUM PIECE?

        long squareMask = 1L << place;  //this sets the bitmask to have a 1 at the square "Place"
        occBoard |= squareMask; // Mark the square as occupied by adding the squaremask 1 to it

        // |= means or for bits. so 0101 |= 0110 will equal 0111

        switch (piece) {
            case 'P':
                whitePawnBoard |= squareMask;
                break;
            case 'N':
                whiteKnightBoard |= squareMask;
                break;
            case 'B':
                whiteBishopBoard |= squareMask;
                break;
            case 'R':
                whiteRookBoard |= squareMask;
                break;
            case 'Q':
                whiteQueenBoard |= squareMask;
                break;
            case 'K':
                whiteKingBoard |= squareMask;
                break;
            case 'p':
                blackPawnBoard |= squareMask;
                break;
            case 'n':
                blackKnightBoard |= squareMask;
                break;
            case 'b':
                blackBishopBoard |= squareMask;
                break;
            case 'r':
                blackRookBoard |= squareMask;
                break;
            case 'q':
                blackQueenBoard |= squareMask;
                break;
            case 'k':
                blackKingBoard |= squareMask;
                break;
        }
    }
}

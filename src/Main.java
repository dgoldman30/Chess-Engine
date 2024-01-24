public class Main {
    public static void main(String[] args) {

        Board chessBoard = new Board();

        //bitboard through string
        String board =
                "rnbkqbnk" +
                        "pppppppp" +
                        "--------" +
                        "--------" +
                        "----N---" +
                        "--------" +
                        "--------" +
                        "R-BQKB-R";
        chessBoard.stringToBitBoard(board);


        // Print the board
        //chessBoard.printBoard();


        Move move = new Move(chessBoard);

//TEST PAWN MOVES
        //chessBoard.whitePawnBoard = move.whitePawnMove();
        //chessBoard.printBoard();

//TEST Knight MOVES
        chessBoard.whiteKnightBoard = move.whiteKnightMove();
        chessBoard.printBoard();
    }
}

//Notes


//need 6 boards for white, 6 boards for black

//can check next move by "shifting" each individual bitboard. for example, you can "shift" the pawns 7, 8, and 9, to determine every possible move by them
//to set start board, could make stringToBitBoard, inputting an string of the board and it would create bitboard from that. give function 64 bit representing one, find the place of the letters in the string and shift that far


//TRAVERSING THE GAME TREE
//do depth first search




//bitboard by hand underneath FOR SETPIECE();
/*
//WHITE
        // Pawn
        for (int i = 8; i < 16; i++) {
            chessBoard.setPiece( 'P', i);
        }

        //Rook
        chessBoard.setPiece( 'R', 0);
        chessBoard.setPiece( 'R', 7);

        //Knight
        chessBoard.setPiece( 'N', 1);
        chessBoard.setPiece( 'N', 6);
        //Bishop
        chessBoard.setPiece( 'B', 2);
        chessBoard.setPiece( 'B', 5);
        //Queen
        chessBoard.setPiece( 'Q', 3);
        //King
        chessBoard.setPiece( 'K', 4);
//BLACK
        // Pawn
        for (int i = 56; i < 64; i++) {
            chessBoard.setPiece( 'p', i);
        }

        //Rook
        chessBoard.setPiece( 'r', 63);
        chessBoard.setPiece( 'r', 56);

        //Knight
        chessBoard.setPiece( 'n', 62);
        chessBoard.setPiece( 'n', 57);
        //Bishop
        chessBoard.setPiece( 'b', 61);
        chessBoard.setPiece( 'b', 58);
        //Queen
        chessBoard.setPiece( 'q', 60);
        //King
        chessBoard.setPiece( 'k', 59);
*/
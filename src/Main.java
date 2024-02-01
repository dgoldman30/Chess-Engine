public class Main {
    public static void main(String[] args) {

        Board chessBoard = new Board();

        //bitboard through string
        String board =
                        "--------" +
                        "---R-r--" +
                        "----P---" +
                        "--------" +
                        "--------" +
                        "---q-Q--" +
                        "P---p---" +
                        "--------";
        //how would we determine the differences if two pawns move to the same square
        chessBoard.stringToBitBoard(board);


        Move move = new Move();

//TEST PAWN MOVES
        //chessBoard.whitePawnBoard = move.pawnMove(chessBoard.whitePawnBoard, chessBoard.whiteOccBoard, chessBoard.blackOccBoard,true);
        chessBoard.blackPawnBoard = move.pawnMove(chessBoard.blackPawnBoard, chessBoard.whiteOccBoard, chessBoard.blackOccBoard,false);

//TEST Knight MOVES
        //chessBoard.whiteKnightBoard = move.knightMove(chessBoard.whiteKnightBoard, chessBoard.occBoard);

//TEST Bishop MOVES DOESNT WORK, NEED TO FIX ask mr.Chat, issue with board boundaries
        //chessBoard.whiteBishopBoard = move.bishopMove(chessBoard.whiteBishopBoard, chessBoard.occBoard);

//TEST Rook MOVES
        //chessBoard.whiteRookBoard = move.rookMove(chessBoard.whiteRookBoard, chessBoard.occBoard);

//TEST Queen MOVES
        //chessBoard.whiteQueenBoard = move.queenMove(chessBoard.whiteQueenBoard, chessBoard.occBoard);

        //Print Board
        chessBoard.printBoard();
    }
}

//Notes



//TRAVERSING THE GAME TREE
//do depth first search

//DeBruijn sequences
//used to find the board position of a bit
//MSB-most signifigant bit LSB- least signifigant bit



//make board represetation
//move generator, fetch legal move
//
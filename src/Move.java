public class Move {

    //keep chessboard instance
    Board chessBoard;

    public Move(Board chessBoard){
        this.chessBoard = chessBoard;
    }

    //this will have different return statement later
    public void generateAllMoves(){
        whitePawnMove();
    }

    public Long whitePawnMove(){
        //set these to make it more efficient? i don't relly know
        Long pawns = chessBoard.whitePawnBoard;
        Long occ = chessBoard.occBoard;

        //move one forward WORKS & TESTED
        Long singleMove = (pawns >> 8) & ~occ;
        //move 2 forward if single move ended on rank 3
        long doubleMoves = ((singleMove & Board.RANK_6) >> 8) & ~occ;

        //move diagonal to left
        long captureLeft = (pawns >> 7) & ~Board.FILE_A & occ;

        //move diagonal to right
        long captureRight = (pawns >> 9) & ~Board.FILE_H & occ;

        //return bitboard containing all possibilities.
        return singleMove | doubleMoves | captureLeft | captureRight;
    }

    public Long whiteKnightMove(){
        Long knights = chessBoard.whiteKnightBoard;
        Long occ = chessBoard.occBoard;
        long moves = 0L;

        // Knight move offsets
        //int[] knightOffsets = { -17, -15, -10, -6, 6, 10, 15, 17 }; //only 10, 15, and 17 work. negative and 6 dont work???
        int[] knightOffsets = {6, 10, 15, 17 };
        for (int offset : knightOffsets) {
            moves |= (knights >> offset) & ~occ;
            moves |= (knights << offset) & ~occ;
        }
        return moves;
    }
}
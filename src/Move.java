public class Move {

    //May need to pass add instance of chessboard


    //this will have different return statement later
    public void generateAllMoves(){
    }
    public static long rotate180(long bitboard) {
        long result = 0L;

        // Rotate rows
        for (int i = 0; i < 8; i++) {
            result |= (bitboard & 0xFFL) << (56 - 8 * i);
            bitboard >>>= 8;
        }

        // Rotate columns
        for (int i = 0; i < 8; i++) {
            result |= ((result & (1L << i)) << (15 - 2 * i)) | ((result & (1L << (i + 8))) >>> (15 - 2 * i));
        }

        return result;
    }

    public Long whitePawnMove(Long pawns, Long whiteOcc, Long blackOcc){

        //total occ board
        Long occ = blackOcc | whiteOcc;

        //move one forward
        Long singleMove = (pawns >> 8) & ~occ;

        //move one more forward if single move ended on correct rank
        long doubleMoves = ((singleMove & Board.RANK_6) >> 8) & ~occ;

        //move diagonal to left
        long captureLeft = (pawns >> (7)) & ~Board.FILE_A & blackOcc;

        //move diagonal to right
        long captureRight = (pawns >> (9)) & ~Board.FILE_H & blackOcc;

        return singleMove | doubleMoves | captureLeft | captureRight;
    }

    public Long blackPawnMove(Long pawns, Long whiteOcc, Long blackOcc){

        //total occ board
        Long occ = blackOcc | whiteOcc;

        //move one forward
        Long singleMove = (pawns << 8) & ~occ;

        //move one more forward if single move ended on correct rank
        long doubleMoves = ((singleMove & Board.RANK_3) << 8) & ~occ;

        //move diagonal to left
        long captureLeft = (pawns << (7)) & ~Board.FILE_A & whiteOcc;

        //move diagonal to right
        long captureRight = (pawns << (9)) & ~Board.FILE_H & whiteOcc;

        return singleMove | doubleMoves | captureLeft | captureRight;
    }

    //knightMove wraps around board when called on the perimeter
    public Long knightMove(Long knights, Long occ){
        long moves = 0L;

        // Knight move offsets
        int[] knightOffsets = {6, 10, 15, 17 };

        moves |= (knights >> 6) & ~occ & ~(Board.FILE_A | Board.FILE_B); //& will check the end position
        moves |= (knights >> 10) & ~occ & ~(Board.FILE_G | Board.FILE_H);
        moves |= (knights >> 15) & ~occ & ~Board.FILE_A;
        moves |= (knights >> 17) & ~occ & ~Board.FILE_H;

        moves |= (knights << 6) & ~occ & ~(Board.FILE_G | Board.FILE_H);
        moves |= (knights << 10) & ~occ & ~(Board.FILE_A | Board.FILE_B);
        moves |= (knights << 15) & ~occ & ~Board.FILE_H;
        moves |= (knights << 17) & ~occ & ~Board.FILE_A;
        return moves;
    }


    public long bishopMove(long bishopBoard, long occ) {
        return bishopBoard;
    }

    public long rookMove(long rookBoard, long occ) {
        return 0L;
    }


    public long queenMove(long queenBoard, long occ) {
        // Combine the moves of a rook and a bishop
        long rookMoves = rookMove(queenBoard, occ);
        long bishopMoves = bishopMove(queenBoard, occ);

        // Combine the moves of the rook and bishop
        return rookMoves | bishopMoves;
    }

    public long kingMove(long kingBoard, long occ) {
        //forward
        Long singleMove = (kingBoard >> 8) & ~occ;
        Long diagLeftMove = (kingBoard >> 7) & ~occ & ~Board.FILE_A;
        Long diagrightMove = (kingBoard >> 9) & ~occ & ~Board.FILE_H;
        //back
        Long backMove = (kingBoard << 8) & ~occ;
        Long backLeftMove = (kingBoard << 7) & ~occ & ~Board.FILE_H;
        Long backrightMove = (kingBoard << 9) & ~occ & ~Board.FILE_A;
        //inline
        Long rightMove = (kingBoard >> 1) & ~occ & ~Board.FILE_H;
        Long LeftMove = (kingBoard << 1) & ~occ& ~Board.FILE_A;

        return singleMove | diagLeftMove| diagrightMove| backMove | backLeftMove | backrightMove | rightMove | LeftMove;
    }

//Tests to see if move will make own king in check
    public boolean inCheck(){
        return false;
    }



}

//use magic bitboard for sliding pieces like rook and bishop
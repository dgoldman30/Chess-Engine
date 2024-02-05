import java.util.ArrayList;
import java.util.List;

public class Move {

//list of all available moves
    public List<Long> moves = new ArrayList<>();


    //this will have different return statement later
    public void generateWhiteMoves(Long whiteOcc, Long blackOcc, Long whitePawn, Long whiteKnights, Long whiteKings){
        moves.add(whitePawnMove(whitePawn, whiteOcc, blackOcc));
        moves.add(whiteKnightMove(whiteKnights, whiteOcc));
        moves.add(whiteKingMove(whiteKings, whiteOcc));
    }

    public void generateBlackMoves(Long whiteOcc, Long blackOcc, Long blackPawn,Long blackKnights, Long blackKings){
        moves.add(blackPawnMove(blackPawn, whiteOcc, blackOcc));
        moves.add(blackKnightMove(blackKnights, blackOcc));
        moves.add(blackKingMove(blackKings, blackOcc));
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

    public Long whiteKnightMove(Long knights, Long whiteOcc){
        long moves = 0L;

        moves |= (knights >> 6) & ~whiteOcc & ~(Board.FILE_A | Board.FILE_B); //& will check the end position
        moves |= (knights >> 10) & ~whiteOcc & ~(Board.FILE_G | Board.FILE_H);
        moves |= (knights >> 15) & ~whiteOcc & ~Board.FILE_A;
        moves |= (knights >> 17) & ~whiteOcc & ~Board.FILE_H;

        moves |= (knights << 6) & ~whiteOcc & ~(Board.FILE_G | Board.FILE_H);
        moves |= (knights << 10) & ~whiteOcc & ~(Board.FILE_A | Board.FILE_B);
        moves |= (knights << 15) & ~whiteOcc & ~Board.FILE_H;
        moves |= (knights << 17) & ~whiteOcc & ~Board.FILE_A;
        return moves;
    }

    public Long blackKnightMove(Long knights, Long blackOcc){
        long moves = 0L;

        moves |= (knights >> 6) & ~blackOcc & ~(Board.FILE_A | Board.FILE_B); //& will check the end position
        moves |= (knights >> 10) & ~blackOcc & ~(Board.FILE_G | Board.FILE_H);
        moves |= (knights >> 15) & ~blackOcc & ~Board.FILE_A;
        moves |= (knights >> 17) & ~blackOcc & ~Board.FILE_H;

        moves |= (knights << 6) & ~blackOcc & ~(Board.FILE_G | Board.FILE_H);
        moves |= (knights << 10) & ~blackOcc & ~(Board.FILE_A | Board.FILE_B);
        moves |= (knights << 15) & ~blackOcc & ~Board.FILE_H;
        moves |= (knights << 17) & ~blackOcc & ~Board.FILE_A;
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

    public long whiteKingMove(long kingBoard, long whiteOcc) {

        //forward
        Long singleMove = (kingBoard >> 8) & ~whiteOcc;
        Long diagLeftMove = (kingBoard >> 7) & ~whiteOcc & ~Board.FILE_A;
        Long diagrightMove = (kingBoard >> 9) & ~whiteOcc & ~Board.FILE_H;
        //back
        Long backMove = (kingBoard << 8) & ~whiteOcc;
        Long backLeftMove = (kingBoard << 7) & ~whiteOcc & ~Board.FILE_H;
        Long backrightMove = (kingBoard << 9) & ~whiteOcc & ~Board.FILE_A;
        //inline
        Long rightMove = (kingBoard >> 1) & ~whiteOcc & ~Board.FILE_H;
        Long LeftMove = (kingBoard << 1) & ~whiteOcc & ~Board.FILE_A;

        return singleMove | diagLeftMove| diagrightMove| backMove | backLeftMove | backrightMove | rightMove | LeftMove;
    }
    public long blackKingMove(long kingBoard, long blackOcc) {

        //forward
        Long singleMove = (kingBoard >> 8) & ~blackOcc;
        Long diagLeftMove = (kingBoard >> 7) & ~blackOcc & ~Board.FILE_A;
        Long diagrightMove = (kingBoard >> 9) & ~blackOcc & ~Board.FILE_H;
        //back
        Long backMove = (kingBoard << 8) & ~blackOcc;
        Long backLeftMove = (kingBoard << 7) & ~blackOcc & ~Board.FILE_H;
        Long backrightMove = (kingBoard << 9) & ~blackOcc & ~Board.FILE_A;
        //inline
        Long rightMove = (kingBoard >> 1) & ~blackOcc & ~Board.FILE_H;
        Long LeftMove = (kingBoard << 1) & ~blackOcc & ~Board.FILE_A;

        return singleMove | diagLeftMove| diagrightMove| backMove | backLeftMove | backrightMove | rightMove | LeftMove;
    }

//Tests to see if move will make own king in check
    public boolean inCheck(Long kingBoard, Boolean isWhite){
        //split generate al moves into black vs white and & it to the king board to see if kings in check
        if(isWhite){
            //return kingBoard & generateBlackMoves();
        } else{
            //return kingBoard & generateWhiteMoves();
        }
        return false; //get rid of this once inputs are fixed above
    }



}

//Later on: if we want to speed up move generaton functions, make king and knight lookup instead of calculation


//use magic bitboard for sliding pieces like rook and bishop
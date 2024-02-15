import java.util.ArrayList;
import java.util.List;

public class Move {

//list of all available moves
    public List<Long> moves = new ArrayList<>();


    //this will have different return statement later
    public void generateWhiteMoves(Long whiteOcc, Long blackOcc, Long whitePawn, Long whiteKnights, Long whiteKings){
        moves.addAll(whiteKnightMove(whiteKnights, whiteOcc));
        moves.addAll(whitePawnMove(whitePawn, whiteOcc, blackOcc));
        moves.add(whiteKingMove(whiteKings, whiteOcc));
    }

    public void generateBlackMoves(Long whiteOcc, Long blackOcc, Long blackPawn,Long blackKnights, Long blackKings){
        moves.addAll(blackKnightMove(blackKnights, blackOcc));
        moves.addAll(blackPawnMove(blackPawn, whiteOcc, blackOcc));
        moves.add(blackKingMove(blackKings, blackOcc));
    }


    public List<Long> whitePawnMove(Long pawns, Long whiteOcc, Long blackOcc){
        List<Long> moves = new ArrayList<>();

        // Iterate through each pawn individually
        for (int i = 0; i < 64; i++) {                  //pawn will never be able to get to first or last rank. room for improvement
            long pawnMask = 1L << i;
            // Check if there's a white pawn at the current position
            if ((pawns & pawnMask) != 0) {
                // Total occupied squares on the board
                Long occ = blackOcc | whiteOcc;
                // Move one square forward
                Long singleMove = (pawnMask >> 8) & ~occ;
                if (singleMove != 0) {
                    moves.add(singleMove);
                }
                // Move two squares forward if the pawn is on its starting rank
                Long doubleMove = ((singleMove & Board.RANK_6) >> 8) & ~occ;
                if (doubleMove != 0) {
                    moves.add(doubleMove);
                }
                // Capture to the left
                Long captureLeft = (pawnMask >> 7) & ~Board.FILE_A & blackOcc;
                if (captureLeft != 0) {
                    moves.add(captureLeft);
                }
                // Capture to the right
                Long captureRight = (pawnMask >> 9) & ~Board.FILE_H & blackOcc;
                if (captureRight != 0) {
                    moves.add(captureRight);
                }
            }
        }
        return moves;
    }

    public List<Long> blackPawnMove(Long pawns, Long whiteOcc, Long blackOcc){
        List<Long> moves = new ArrayList<>();

        // Iterate through each pawn individually
        for (int i = 0; i < 64; i++) {
            long pawnMask = 1L << i;
            // Check if there's a black pawn at the current position
            if ((pawns & pawnMask) != 0) {
                // Total occupied squares on the board
                Long occ = blackOcc | whiteOcc;
                // Move one square forward
                Long singleMove = (pawnMask << 8) & ~occ;
                if (singleMove != 0) {
                    moves.add(singleMove);
                }
                // Move two squares forward if the pawn is on its starting rank
                Long doubleMove = ((singleMove & Board.RANK_3) << 8) & ~occ;
                if (doubleMove != 0) {
                    moves.add(doubleMove);
                }
                // Capture to the left
                Long captureLeft = (pawnMask << 7) & ~Board.FILE_A & whiteOcc;
                if (captureLeft != 0) {
                    moves.add(captureLeft);
                }
                // Capture to the right
                Long captureRight = (pawnMask << 9) & ~Board.FILE_H & whiteOcc;
                if (captureRight != 0) {
                    moves.add(captureRight);
                }
            }
        }
        return moves;
    }

    public List<Long> whiteKnightMove(Long knights, Long whiteOcc){
        List<Long> moves = new ArrayList<>();
        // Iterate through each knight individually
        for (int i = 0; i < 64; i++) {
            long knightMask = 1L << i;
            // Check if there's a black knight at the current position
            if ((knights & knightMask) != 0) {
                moves.add((knightMask >> 6) & ~whiteOcc & ~(Board.FILE_A | Board.FILE_B)); //& will check the end position
                moves.add((knightMask >> 10) & ~whiteOcc & ~(Board.FILE_G | Board.FILE_H));
                moves.add((knightMask >> 15) & ~whiteOcc & ~Board.FILE_A);
                moves.add((knightMask >> 17) & ~whiteOcc & ~Board.FILE_H);

                moves.add((knightMask << 6) & ~whiteOcc & ~(Board.FILE_G | Board.FILE_H));
                moves.add((knightMask << 10) & ~whiteOcc & ~(Board.FILE_A | Board.FILE_B));
                moves.add((knightMask << 15) & ~whiteOcc & ~Board.FILE_H);
                moves.add((knightMask << 17) & ~whiteOcc & ~Board.FILE_A);
            }
        }
        return moves;
    }

    public List<Long> blackKnightMove(Long knights, Long blackOcc){
        List<Long> moves = new ArrayList<>();

        // Iterate through each knight individually
        for (int i = 0; i < 64; i++) {
            long knightMask = 1L << i;
            // Check if there's a black knight at the current position
            if ((knights & knightMask) != 0) {
                moves.add((knightMask >> 6) & ~blackOcc & ~(Board.FILE_A | Board.FILE_B)); //& will check the end position
                moves.add((knightMask >> 10) & ~blackOcc & ~(Board.FILE_G | Board.FILE_H));
                moves.add((knightMask >> 15) & ~blackOcc & ~Board.FILE_A);
                moves.add((knightMask >> 17) & ~blackOcc & ~Board.FILE_H);

                moves.add((knightMask << 6) & ~blackOcc & ~(Board.FILE_G | Board.FILE_H));
                moves.add((knightMask << 10) & ~blackOcc & ~(Board.FILE_A | Board.FILE_B));
                moves.add((knightMask << 15) & ~blackOcc & ~Board.FILE_H);
                moves.add((knightMask << 17) & ~blackOcc & ~Board.FILE_A);
            }
        }
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
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

    public Long pawnMove(Long pawns, Long whiteOcc, Long blackOcc, boolean isWhite){

        //current bug: both sides only capture black pieces

        //total occ board
        Long occ = whiteOcc |= blackOcc;
        //set opposing team occupancy
        Long oppOcc = isWhite ? blackOcc : whiteOcc;


        if(!isWhite){
            //rotate the board 180
            pawns = rotate180(pawns);
            occ = rotate180(occ);
            oppOcc = whiteOcc;
            oppOcc = rotate180(oppOcc);
        }

        //move one forward
        Long singleMove = (pawns >> 8) & ~occ;

        //move one more forward if single move ended on correct rank
        long doubleMoves = ((singleMove & Board.RANK_6) >> 8) & ~occ;

        //move diagonal to left
        long captureLeft = (pawns >> (7)) & ~Board.FILE_A & oppOcc;

        //move diagonal to right
        long captureRight = (pawns >> (9)) & ~Board.FILE_H & oppOcc;

        //combine all possible moves
        Long allMoves = singleMove | doubleMoves | captureLeft | captureRight;


        if(!isWhite){
            //rotate the board back 180
            allMoves = rotate180(allMoves);
        }


        return allMoves;
    }

    /*public Long pawnMove(Long pawns, Long occ, boolean isWhite) {
        // Define shift direction based on the color
        int shiftDirection = isWhite ? 8 : 8;

        // Move one forward
        Long singleMove = (pawns >> shiftDirection) & ~occ;

        // Move 2 forward if the single move ended on the appropriate rank
        long doubleMoves = ((singleMove & (isWhite ? Board.RANK_6 : Board.RANK_3)) << shiftDirection) & ~occ;

        // Move diagonal to the left
        long captureLeft = (pawns << (shiftDirection + 1)) & ~Board.FILE_A & occ;

        // Move diagonal to the right
        long captureRight = (pawns << (shiftDirection - 1)) & ~Board.FILE_H & occ;

        // Return bitboard containing all possibilities
        //return singleMove | doubleMoves | captureLeft | captureRight;
        return singleMove;
    }*/

    public Long knightMove(Long knights, Long occ){
        long moves = 0L;

        // Knight move offsets
        int[] knightOffsets = {6, 10, 15, 17 };
        for (int offset : knightOffsets) {
            moves |= (knights >> offset) & ~occ;
            moves |= (knights << offset) & ~occ;
        }
        return moves;
    }

    public long bishopMove(long bishopBoard, long occ) {
        long moves = 0L;

        // Bishop move offsets
        int[] bishopOffsets = { -9, -7, 7, 9 };

        for (int offset : bishopOffsets) {
            long potentialMoves = bishopBoard;

            while (true) {
                // Calculate potential moves
                potentialMoves = (potentialMoves << offset) & ~(1L << 64); // Clear bit at 64th position
                potentialMoves = (potentialMoves >>> -offset) & ~(1L << -1); // Clear bit at -1st position

                // Check if there are no more valid moves in this direction or out of the board
                if (potentialMoves == 0L || (potentialMoves & occ) != 0L) {
                    break;
                }

                // Add potential moves to the moves bitmask
                moves |= potentialMoves;
            }
        }

        // Remove squares already occupied by own pieces
        moves &= ~bishopBoard;

        return moves;
    }

    public long rookMove(long rookBoard, long occ) {
        long moves = 0L;

        // Rook move offsets
        int[] rookOffsets = {1, -1, 8, -8};

        for (int offset : rookOffsets) {
            long potentialMoves = rookBoard;

            while (true) {
                // Clear the bits of the rook's file if moving horizontally
                if (offset == 1 || offset == -1) {
                    potentialMoves &= ~Board.FILE_A & ~Board.FILE_H;
                }

                // Clear the bits of the rook's rank if moving vertically
                if (offset == 8 || offset == -8) {
                    potentialMoves &= ~Board.RANK_1 & ~Board.RANK_8;
                }

                // Calculate potential moves
                potentialMoves = (potentialMoves << offset) | (potentialMoves >>> -offset);

                // Check if there are no more valid moves in this direction
                if (potentialMoves == 0L) {
                    break;
                }

                // Add potential moves to the moves bitmask
                moves |= potentialMoves;

                // Check if potential moves are blocked by another piece
                if ((potentialMoves & occ) != 0L) {
                    break;
                }
            }
        }

        return moves;
    }


    public long queenMove(long queenBoard, long occ) {
        // Combine the moves of a rook and a bishop
        long rookMoves = rookMove(queenBoard, occ);
        long bishopMoves = bishopMove(queenBoard, occ);

        // Combine the moves of the rook and bishop
        return rookMoves | bishopMoves;
    }



}

//use magic bitboard for sliding pieces like rook and bishop
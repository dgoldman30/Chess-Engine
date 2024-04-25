import java.util.*;

import static java.lang.Long.*;

public class Move {


    // current move errors:
    // rooks wrap around the board (fixed?)
    // white pawns capture one square forward (Fixed?)
    // black queen and pawns captures other black pieces (Fixed by TC, occboards were reversed in parameters for move functions)

    Random randomGenerator = new Random(); //for random move REMOVE LATER LOL***


    //madeMoves represents a Stack(Move((startPosition, endPosition), Captured Piece type)). Used for undoMove to keep track of past moves
    Stack<Tuple<Tuple<Long, Long>, String>> madeMoves = new Stack<>();    //make tuple constructor for third element String, twice as efficient


    public List<Tuple<Long, List<Long>>> generateWhiteMoves(Board chessBoard) {
        List<Tuple<Long, List<Long>>> moveList = new ArrayList<>();

        moveList.addAll(whitePawnMove(chessBoard.whitePawnBoard, chessBoard.whiteOccBoard, chessBoard.blackOccBoard));
        moveList.addAll(whiteKnightMove(chessBoard.whiteKnightBoard, chessBoard.whiteOccBoard));
        moveList.addAll(whiteRookMove(chessBoard.whiteRookBoard, chessBoard.whiteOccBoard, chessBoard.blackOccBoard));
        moveList.addAll(whiteBishopMove(chessBoard.whiteBishopBoard, chessBoard.whiteOccBoard, chessBoard.blackOccBoard));
        moveList.addAll(whiteQueenMove(chessBoard.whiteQueenBoard, chessBoard.whiteOccBoard, chessBoard.blackOccBoard));
        moveList.addAll(whiteKingMove(chessBoard.whiteKingBoard, chessBoard.whiteOccBoard));

        return moveList;
    }

    public List<Tuple<Long, List<Long>>> generateBlackMoves(Board chessBoard) {
        List<Tuple<Long, List<Long>>> moveList = new ArrayList<>();

        moveList.addAll(blackPawnMove(chessBoard.blackPawnBoard, chessBoard.whiteOccBoard, chessBoard.blackOccBoard));
        moveList.addAll(blackKnightMove(chessBoard.blackKnightBoard, chessBoard.blackOccBoard));
        moveList.addAll(blackRookMove(chessBoard.blackRookBoard, chessBoard.blackOccBoard, chessBoard.whiteOccBoard));
        moveList.addAll(blackBishopMove(chessBoard.blackBishopBoard, chessBoard.blackOccBoard, chessBoard.whiteOccBoard));
        moveList.addAll(blackQueenMove(chessBoard.blackQueenBoard, chessBoard.whiteOccBoard, chessBoard.blackOccBoard));
        moveList.addAll(blackKingMove(chessBoard.blackKingBoard, chessBoard.blackOccBoard));

        return moveList;
    }

    public List<Tuple<Long, List<Long>>> whitePawnMove(Long pawns, Long whiteOcc, Long blackOcc) {

        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

        // Iterate through each pawn individually
        for (int i = 0; i < 64; i++) {                  // pawn will never be able to get to first or last rank. room for improvement***
            long pawnMask = 1L << i;

            // Check if there's a white pawn at the current position
            if ((pawns & pawnMask) != 0) {

                List<Long> moveList = new ArrayList<>();

                Tuple tuple = new Tuple(0L, moveList);
                tuple.setFirst(pawnMask);

                // Total occupied squares on the board
                Long occ = blackOcc | whiteOcc;
                // Move one square forward
                Long singleMove = (pawnMask >> 8) & ~occ;
                if (singleMove != 0) {
                    moveList.add(singleMove);
                }
                // Move two squares forward if the pawn is on its starting rank
                Long doubleMove = ((singleMove & Board.RANK_6) >> 8) & ~occ;
                if (doubleMove != 0) {
                    moveList.add(doubleMove);
                }
                // Capture to the left
                Long captureLeft = (pawnMask >> 7) & ~Board.FILE_A & blackOcc;
                if (captureLeft != 0) {
                    moveList.add(captureLeft);
                }
                // Capture to the right
                Long captureRight = (pawnMask >> 9) & ~Board.FILE_H & blackOcc;
                if (captureRight != 0) {
                    moveList.add(captureRight);
                }
                tuple.setSecond(moveList);
                if (!moveList.isEmpty()) {
                    finalMoves.add(tuple);
                }
            }
        }
        return finalMoves;
    }

    public List<Tuple<Long, List<Long>>> blackPawnMove(Long pawns, Long whiteOcc, Long blackOcc) {

        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

        // Iterate through each pawn individually
        for (int i = 0; i < 64; i++) {                  //pawn will never be able to get to first or last rank. room for improvement***
            long pawnMask = 1L << i;

            // Check if there's a black pawn at the current position
            if ((pawns & pawnMask) != 0) {

                List<Long> moveList = new ArrayList<>();

                Tuple tuple = new Tuple(0L, moveList);
                tuple.setFirst(pawnMask);

                // Total occupied squares on the board
                Long occ = blackOcc | whiteOcc;
                // Move one square forward
                Long singleMove = (pawnMask << 8) & ~occ;
                if (singleMove != 0) {
                    moveList.add(singleMove);
                }
                // Move two squares forward if the pawn is on its starting rank
                Long doubleMove = ((singleMove & Board.RANK_3) << 8) & ~occ;
                if (doubleMove != 0) {
                    moveList.add(doubleMove);
                }
                // Capture to the left
                Long captureLeft = (pawnMask << 7) & ~Board.FILE_H & whiteOcc;
                if (captureLeft != 0) {
                    moveList.add(captureLeft);
                }
                // Capture to the right
                Long captureRight = (pawnMask << 9) & ~Board.FILE_A & whiteOcc;
                if (captureRight != 0) {
                    moveList.add(captureRight);
                }
                tuple.setSecond(moveList);
                if (!moveList.isEmpty()) {
                    finalMoves.add(tuple);
                }
            }
        }
        return finalMoves;
    }

    public List<Tuple<Long, List<Long>>> whiteKnightMove(Long knights, Long whiteOcc) {    //HAVING BOUND ISSUES. it still adds the out of bounds moves to the list, resulting in empty moves***

        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

        // Iterate through each knight individually
        for (int i = 0; i < 64; i++) {
            long knightMask = 1L << i;
            // Check if there's a white knight at the current position
            if ((knights & knightMask) != 0) {

                List<Long> moveList = new ArrayList<>(); //make movelist for the individual piece

                Tuple tuple = new Tuple(0L, moveList); //initiate tuple for individual piece
                tuple.setFirst(knightMask);   //set starting board

                moveList.add((knightMask >> 6) & ~whiteOcc & ~(Board.FILE_A | Board.FILE_B)); //& will check the end position
                moveList.add((knightMask >> 10) & ~whiteOcc & ~(Board.FILE_G | Board.FILE_H));
                moveList.add((knightMask >> 15) & ~whiteOcc & ~Board.FILE_A);
                moveList.add((knightMask >> 17) & ~whiteOcc & ~Board.FILE_H);

                moveList.add((knightMask << 6) & ~whiteOcc & ~(Board.FILE_G | Board.FILE_H));
                moveList.add((knightMask << 10) & ~whiteOcc & ~(Board.FILE_A | Board.FILE_B));
                moveList.add((knightMask << 15) & ~whiteOcc & ~Board.FILE_H);
                moveList.add((knightMask << 17) & ~whiteOcc & ~Board.FILE_A);

                // Iterate through the list and remove elements with all 0's     THIS REMOVES THE MOVES THAT WERE REMOVED FOR BOUNDARIES
                for (int X = moveList.size() - 1; X >= 0; X--) {
                    Long value = moveList.get(X);
                    if (value == 0L) {
                        moveList.remove(X);
                    }
                }
                tuple.setSecond(moveList); //add moveList to individual pieces tuple
                if (!moveList.isEmpty()) {
                    finalMoves.add(tuple);
                } //add tuple of individual piece to list if list is not empty
            }
        }
        return finalMoves;
    }

    public List<Tuple<Long, List<Long>>> blackKnightMove(Long knights, Long blackOcc) {

        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

        // Iterate through each knight individually
        for (int i = 0; i < 64; i++) {
            long knightMask = 1L << i;
            // Check if there's a black knight at the current position
            if ((knights & knightMask) != 0) {

                List<Long> moveList = new ArrayList<>(); //make movelist for the individual piece

                Tuple tuple = new Tuple(0L, moveList); //initiate tuple for individual piece
                tuple.setFirst(knightMask);   //set starting board

                moveList.add((knightMask >> 6) & ~blackOcc & ~(Board.FILE_A | Board.FILE_B)); //& will check the end position
                moveList.add((knightMask >> 10) & ~blackOcc & ~(Board.FILE_G | Board.FILE_H));
                moveList.add((knightMask >> 15) & ~blackOcc & ~Board.FILE_A);
                moveList.add((knightMask >> 17) & ~blackOcc & ~Board.FILE_H);

                moveList.add((knightMask << 6) & ~blackOcc & ~(Board.FILE_G | Board.FILE_H));
                moveList.add((knightMask << 10) & ~blackOcc & ~(Board.FILE_A | Board.FILE_B));
                moveList.add((knightMask << 15) & ~blackOcc & ~Board.FILE_H);
                moveList.add((knightMask << 17) & ~blackOcc & ~Board.FILE_A);
                tuple.setSecond(moveList); //add moveList to individual pieces tuple
                finalMoves.add(tuple); //add tuple of individual piece to list

                // Iterate through the list and remove elements with all 0's     THIS REMOVES THE MOVES THAT WERE REMOVED FOR BOUNDARIES
                for (int X = moveList.size() - 1; X >= 0; X--) {
                    Long value = moveList.get(X);
                    if (value == 0L) {
                        moveList.remove(X);
                    }
                }
                tuple.setSecond(moveList); //add moveList to individual pieces tuple
                if (!moveList.isEmpty()) {
                    finalMoves.add(tuple);
                } //add tuple of individual piece to list if list is not empty
            }
        }
        return finalMoves;
    }
    public static int[] findPieces(Long board) {
        int[] locations = new int[Long.bitCount(board)];
        int index = 0;
        while (board != 0) {
            long square = board & -board;
            int location = Long.numberOfTrailingZeros(square);
            locations[index++] = location;
            board ^= square;
        }
        return locations;
    }

    public static List<Long> convertMultipleBitboards(Long board, List<Long> list) {
        while (board != 0) {
            // Extract the least significant set bit
            long leastSignificantBit = board & -board;

            // Add the individual bitboard with the least significant bit set
            list.add(leastSignificantBit);

            // Clear the least significant bit in the original bitboard
            board &= ~leastSignificantBit;
        }
        return list;
    }

    public List<Tuple<Long, List<Long>>> whiteBishopMove(Long bishops, Long whiteOcc, Long blackOcc) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

        // diagonal masks:
        // all diagonals move down and right from their starting position
        long diagonal[] = {0x1L, 0x102L, 0x10204L, 0x1020408L, 0x102040810L, 0x10204081020L, 0x1020408102040L, 0x102040810204080L, 0x204081020408000L, 0x408102040800000L, 0x810204080000000L, 0x1020408000000000L, 0x2040800000000000L, 0x4080000000000000L, 0x8000000000000000L};

        // antidiagonal masks:
        // all antidiagonals move down and left from their starting position
        long antidiagonal[] = {0x80L, 0x8040L, 0x804020L, 0x80402010L, 0x8040201008L, 0x804020100804L, 0x80402010080402L, 0x8040201008040201L, 0x4020100804020100L, 0x2010080402010000L, 0x1008040201000000L, 0x804020100000000L, 0x402010000000000L, 0x201000000000000L, 0x100000000000000L};

        int[] arr = findPieces(bishops);
        // Iterate through each bishop's position individually
        for (int i = 0; i < arr.length; i++) {

            List<Long> moveList = new ArrayList<>(); // Make move list for the individual piece
            Tuple<Long, List<Long>> tuple = new Tuple<>(0L, moveList); // Initiate tuple for individual piece

            //  hyperbola quintessence (o^(o-2r) trick)
            // variable for the current occupancy of the single bishop
            long piece = 1L << arr[i];
            tuple.setFirst(piece);
            long occupied = (whiteOcc | blackOcc);

            long diagonalMoves = ((occupied & diagonal[(arr[i] / 8) + (arr[i] % 8)]) - (2 * piece)) ^ Long.reverse(Long.reverse(occupied & diagonal[(arr[i] / 8) + (arr[i] % 8)]) - (2 * Long.reverse(piece)));
            long antiDiagonalMoves = ((occupied & antidiagonal[(arr[i] / 8) + 7 - (arr[i] % 8)]) - (2 * piece)) ^ Long.reverse(Long.reverse(occupied & antidiagonal[(arr[i] / 8) + 7 - (arr[i] % 8)]) - (2 * Long.reverse(piece)));
            long available = (diagonalMoves & diagonal[(arr[i] / 8) + (arr[i] % 8)]) | (antiDiagonalMoves & antidiagonal[(arr[i] / 8) + 7 - (arr[i] % 8)]);
           // System.out.println(Long.toBinaryString(available));

           convertMultipleBitboards(available, moveList);

            // Add moveList to individual piece's tuple
            tuple.setSecond(moveList);
            if (!moveList.isEmpty()) {
                finalMoves.add(tuple);
            }
        }
        return finalMoves;
        }

    public List<Tuple<Long, List<Long>>> blackBishopMove(Long bishops, Long blackOcc, Long whiteOcc) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

        // diagonal masks:
        // all diagonals move down and right from their starting position
        long diagonal[] = {0x1L, 0x102L, 0x10204L, 0x1020408L, 0x102040810L, 0x10204081020L, 0x1020408102040L, 0x102040810204080L, 0x204081020408000L, 0x408102040800000L, 0x810204080000000L, 0x1020408000000000L, 0x2040800000000000L, 0x4080000000000000L, 0x8000000000000000L};

        // antidiagonal masks:
        // all antidiagonals move down and left from their starting position
        long antidiagonal[] = {0x80L, 0x8040L, 0x804020L, 0x80402010L, 0x8040201008L, 0x804020100804L, 0x80402010080402L, 0x8040201008040201L, 0x4020100804020100L, 0x2010080402010000L, 0x1008040201000000L, 0x804020100000000L, 0x402010000000000L, 0x201000000000000L, 0x100000000000000L};

        int[] arr = findPieces(bishops);
        // Iterate through each bishop's position individually
        for (int i = 0; i < arr.length; i++) {

            List<Long> moveList = new ArrayList<>(); // Make move list for the individual piece
            Tuple<Long, List<Long>> tuple = new Tuple<>(0L, moveList); // Initiate tuple for individual piece

            //  hyperbola quintessence (o^(o-2r) trick)
            // variable for the current occupancy of the single bishop
            long piece = 1L << arr[i];
            tuple.setFirst(piece);
            long occupied = (whiteOcc | blackOcc);

            long diagonalMoves = ((occupied & diagonal[(arr[i] / 8) + (arr[i] % 8)]) - (2 * piece)) ^ Long.reverse(Long.reverse(occupied & diagonal[(arr[i] / 8) + (arr[i] % 8)]) - (2 * Long.reverse(piece)));
            long antiDiagonalMoves = ((occupied & antidiagonal[(arr[i] / 8) + 7 - (arr[i] % 8)]) - (2 * piece)) ^ Long.reverse(Long.reverse(occupied & antidiagonal[(arr[i] / 8) + 7 - (arr[i] % 8)]) - (2 * Long.reverse(piece)));
            long available = (diagonalMoves & diagonal[(arr[i] / 8) + (arr[i] % 8)]) | (antiDiagonalMoves & antidiagonal[(arr[i] / 8) + 7 - (arr[i] % 8)]);
            // System.out.println(Long.toBinaryString(available));

            convertMultipleBitboards(available, moveList);

            // Add moveList to individual piece's tuple
            tuple.setSecond(moveList);
            if (!moveList.isEmpty()) {
                finalMoves.add(tuple);
            }
        }
        return finalMoves;
    }

    /*
    // Define the function for calculating legal moves for a bishop
    public List<Tuple<Long, List<Long>>> whiteBishopMove(Long bishops, Long whiteOcc, Long blackOcc) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();
        long targetSquare;

        // Iterate through each bishop individually
        for (int i = 0; i < 64; i++) {
            long bishopMask = 1L << i;
            // Check if there's a white bishop at the current position
            if ((bishops & bishopMask) != 0) {

                List<Long> moveList = new ArrayList<>(); // Make move list for the individual piece

                Tuple<Long, List<Long>> tuple = new Tuple<>(0L, moveList); // Initiate tuple for individual piece
                tuple.setFirst(bishopMask); // Set starting board

                // Calculate legal diagonal moves (up-left)
                for (int j = i - 9; j >= 0 && j % 8 != 7; j -= 9) {
                    targetSquare = 1L << j;
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & blackOcc) != 0)
                            moveList.add(targetSquare); // Capture if black piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                    if ((targetSquare & Board.FILE_A) != 0) break; // Break if file A is reached
                    if ((targetSquare & Board.RANK_8) != 0) break; // Break if rank 8 is reached
                }

                // Calculate legal diagonal moves (up-right)
                for (int j = i - 7; j >= 0 && j % 8 != 0; j -= 7) {
                    targetSquare = 1L << j;
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & blackOcc) != 0)
                            moveList.add(targetSquare); // Capture if black piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                    if ((targetSquare & Board.FILE_H) != 0) break; // Break if file H is reached
                    if ((targetSquare & Board.RANK_8) != 0) break; // Break if rank 8 is reached
                }

                // Calculate legal diagonal moves (down-left)
                for (int j = i + 7; j < 64 && j % 8 != 7; j += 7) {
                    targetSquare = 1L << j;
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & blackOcc) != 0)
                            moveList.add(targetSquare); // Capture if black piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                    if ((targetSquare & Board.FILE_A) != 0) break; // Break if file A is reached
                    if ((targetSquare & Board.RANK_1) != 0) break; // Break if rank 1 is reached
                }

                // Calculate legal diagonal moves (down-right)
                for (int j = i + 9; j < 64 && j % 8 != 0; j += 9) {
                    targetSquare = 1L << j;
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & blackOcc) != 0)
                            moveList.add(targetSquare); // Capture if black piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                    if ((targetSquare & Board.FILE_H) != 0) break; // Break if file H is reached
                    if ((targetSquare & Board.RANK_1) != 0) break; // Break if rank 1 is reached
                }

                tuple.setSecond(moveList); // Add moveList to individual piece's tuple
                if (!moveList.isEmpty()) {
                    finalMoves.add(tuple);
                } // Add tuple of individual piece to the list
            }
        }
        return finalMoves;
    }


    private List<Tuple<Long, List<Long>>> blackBishopMove(long bishops, long blackOcc, long whiteOcc) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();
        long targetSquare;

        // Iterate through each bishop individually
        for (int i = 0; i < 64; i++) {
            long bishopMask = 1L << i;
            // Check if there's a black bishop at the current position
            if ((bishops & bishopMask) != 0) {

                List<Long> moveList = new ArrayList<>(); // Make move list for the individual piece

                Tuple<Long, List<Long>> tuple = new Tuple<>(0L, moveList); // Initiate tuple for individual piece
                tuple.setFirst(bishopMask); // Set starting board

                // Calculate legal diagonal moves (up-left)
                for (int j = i - 9; j >= 0 && j % 8 != 7; j -= 9) {
                    targetSquare = 1L << j;
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & whiteOcc) != 0)
                            moveList.add(targetSquare); // Capture if white piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                    if ((targetSquare & Board.FILE_H) != 0) break; // Break if file H is reached
                    if ((targetSquare & Board.RANK_1) != 0) break; // Break if rank 1 is reached
                }

                // Calculate legal diagonal moves (up-right)
                for (int j = i - 7; j >= 0 && j % 8 != 0; j -= 7) {
                    targetSquare = 1L << j;
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & whiteOcc) != 0)
                            moveList.add(targetSquare); // Capture if white piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                    if ((targetSquare & Board.FILE_A) != 0) break; // Break if file A is reached
                    if ((targetSquare & Board.RANK_1) != 0) break; // Break if rank 1 is reached
                }

                // Calculate legal diagonal moves (down-left)
                for (int j = i + 7; j < 64 && j % 8 != 7; j += 7) {
                    targetSquare = 1L << j;
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & whiteOcc) != 0)
                            moveList.add(targetSquare); // Capture if white piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                    if ((targetSquare & Board.FILE_H) != 0) break; // Break if file H is reached
                    if ((targetSquare & Board.RANK_8) != 0) break; // Break if rank 8 is reached
                }

                // Calculate legal diagonal moves (down-right)
                for (int j = i + 9; j < 64 && j % 8 != 0; j += 9) {
                    targetSquare = 1L << j;
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & whiteOcc) != 0)
                            moveList.add(targetSquare); // Capture if white piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                    if ((targetSquare & Board.FILE_A) != 0) break; // Break if file A is reached
                    if ((targetSquare & Board.RANK_8) != 0) break; // Break if rank 8 is reached
                }

                tuple.setSecond(moveList); // Add moveList to individual piece's tuple
                if (!moveList.isEmpty()) {
                    finalMoves.add(tuple);
                } // Add tuple of individual piece to the list
            }
        }
        return finalMoves;
    }
     */

    // Define the function for calculating legal moves for a rook
    public List<Tuple<Long, List<Long>>> whiteRookMove(Long rooks, Long whiteOcc, Long blackOcc) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();
        long rookMask;

        int[] arr = findPieces(rooks);
        // Iterate through each rook's position individually
        // for each rook, so need some kind of loop or to do it for all of them at once? not sure how to do it without the loop yet
        // maybe use a rook mask but note the rank / file for each one instead of just the individual square?
        for (int i = 0; i < arr.length; i++) {

            List<Long> moveList = new ArrayList<>(); // Make move list for the individual piece
            Tuple<Long, List<Long>> tuple = new Tuple<>(0L, moveList); // Initiate tuple for individual piece

            //  hyperbola quintessence (o^(o-2r) trick)
            // variable for the current occupancy of the single rook
            long piece = 1L << arr[i];
            tuple.setFirst(piece);
            long occupied = (whiteOcc | blackOcc);

            // rankmasks are equal to the longs of the ranks & same for files
            // need to be able to identify the file and rank for the piece without going through all combinations
            // i % 8 and i / 8 should get the correct file & rank for the piece, but there is something wrong with HORIZONTAL; the rank and position are correct
            // problem with PIECE generating the correct location
            long horizontal = (occupied - (2 * piece)) ^ Long.reverse(Long.reverse(occupied) - (2 * Long.reverse(piece)));
            long vertical = ((occupied & Board.files[arr[i] % 8]) - (2 * piece)) ^ Long.reverse(Long.reverse(occupied & Board.files[arr[i] % 8]) - (2 * Long.reverse(piece)));
            // need to remove the position of the piece itself from the list of possible moves! that's what the ^ is for
            long available = horizontal & Board.ranks[arr[i] / 8] ^ vertical & Board.files[arr[i] % 8];
            // System.out.println(Long.toBinaryString(available));
            // System.out.println(Long.toBinaryString(available));
           // System.out.println(arr[i]);
           // System.out.println("File: " + Long.toBinaryString(Board.files[arr[i] % 8])+ "   Number of leading zeros: " + Long.numberOfLeadingZeros(Board.files[arr[i] % 8]));
            //System.out.println("File: " + Long.toBinaryString(Board.FILE_F) + "   Number of leading zeros: " + Long.numberOfLeadingZeros(Board.FILE_F));
           // System.out.println("Rank: " + Long.toBinaryString(Board.ranks[arr[i] % 8]));
            // debug print statements ^

            // this is itself a list of moves, so it will not return with the same structure as the tuple unless converted to individual bitboards
            // converts the bitboard of all possible moves into individual bitboards to add to the moveList
            // Iterate over each set bit in the original bitboard
            convertMultipleBitboards(available, moveList);

            // Add moveList to individual piece's tuple
            tuple.setSecond(moveList);
            if (!moveList.isEmpty()) {
                finalMoves.add(tuple);
            }
        }
    return finalMoves;
    }

    public List<Tuple<Long, List<Long>>> blackRookMove(Long rooks, Long blackOcc, Long whiteOcc) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();
        long rookMask;

        int[] arr = findPieces(rooks);
        for (int i = 0; i < arr.length; i++) {

            List<Long> moveList = new ArrayList<>(); // Make move list for the individual piece
            Tuple<Long, List<Long>> tuple = new Tuple<>(0L, moveList); // Initiate tuple for individual piece

            long piece = 1L << arr[i];
            tuple.setFirst(piece);
            long occupied = (whiteOcc | blackOcc);

            long horizontal = (occupied - (2 * piece)) ^ Long.reverse(Long.reverse(occupied) - (2 * Long.reverse(piece)));
            long vertical = ((occupied & Board.files[arr[i] % 8]) - (2 * piece)) ^ Long.reverse(Long.reverse(occupied & Board.files[arr[i] % 8]) - (2 * Long.reverse(piece)));
            long available = horizontal & Board.ranks[arr[i] / 8] ^ vertical & Board.files[arr[i] % 8];

            convertMultipleBitboards(available, moveList);

            tuple.setSecond(moveList);
            if (!moveList.isEmpty()) {
                finalMoves.add(tuple);
            }
        }
        return finalMoves;
    }

    /*
    public List<Tuple<Long, List<Long>>> whiteRookMove(Long rooks, Long whiteOcc, Long blackOcc) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();
        long targetSquare;

        // Iterate through each rook individually
        for (int i = 0; i < 64; i++) {
            long rookMask = 1L << i;
            // Check if there's a white rook at the current position
            if ((rooks & rookMask) != 0) {

                List<Long> moveList = new ArrayList<>(); // Make move list for the individual piece

                Tuple<Long, List<Long>> tuple = new Tuple<>(0L, moveList); // Initiate tuple for individual piece
                tuple.setFirst(rookMask); // Set starting board

                // Calculate legal horizontal moves (left)
                for (int j = i - 1; j / 8 == i / 8; j--) {
                    targetSquare = 1L << j;
                    if ((targetSquare & Board.FILE_A) != 0) break; // Break if file A is reached
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & blackOcc) != 0) moveList.add(targetSquare); // Capture if black piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                }

                // Calculate legal horizontal moves (right)
                for (int j = i + 1; j / 8 == i / 8; j++) {
                    targetSquare = 1L << j;
                    if ((targetSquare & Board.FILE_H) != 0) break; // Break if file H is reached
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & blackOcc) != 0) moveList.add(targetSquare); // Capture if black piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                }

                // Calculate legal vertical moves (up)
                for (int j = i - 8; j >= 0; j -= 8) {
                    targetSquare = 1L << j;
                    if ((targetSquare & Board.RANK_8) != 0) break; // Break if rank 8 is reached
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & blackOcc) != 0) moveList.add(targetSquare); // Capture if black piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                }

                // Calculate legal vertical moves (down)
                for (int j = i + 8; j < 64; j += 8) {
                    targetSquare = 1L << j;
                    if ((targetSquare & Board.RANK_1) != 0) break; // Break if rank 1 is reached
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & blackOcc) != 0) moveList.add(targetSquare); // Capture if black piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                }

                tuple.setSecond(moveList); // Add moveList to individual piece's tuple
                if(!moveList.isEmpty()) {
                    finalMoves.add(tuple);
                } // Add tuple of individual piece to the list
            }
        }
        return finalMoves;
    }

    private List<Tuple<Long, List<Long>>> blackRookMove(long rooks, long blackOcc, long whiteOcc) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();
        long targetSquare;

        // Iterate through each rook individually
        for (int i = 0; i < 64; i++) {
            long rookMask = 1L << i;
            // Check if there's a black rook at the current position
            if ((rooks & rookMask) != 0) {

                List<Long> moveList = new ArrayList<>(); // Make move list for the individual piece

                Tuple<Long, List<Long>> tuple = new Tuple<>(0L, moveList); // Initiate tuple for individual piece
                tuple.setFirst(rookMask); // Set starting board

                // Calculate legal horizontal moves (left)
                for (int j = i - 1; j / 8 == i / 8; j--) {
                    targetSquare = 1L << j;
                    if ((targetSquare & Board.FILE_H) != 0) break; // Break if file H is reached
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & whiteOcc) != 0) moveList.add(targetSquare); // Capture if white piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                }

                // Calculate legal horizontal moves (right)
                for (int j = i + 1; j / 8 == i / 8; j++) {
                    targetSquare = 1L << j;
                    if ((targetSquare & Board.FILE_A) != 0) break; // Break if file A is reached
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & whiteOcc) != 0) moveList.add(targetSquare); // Capture if white piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                }

                // Calculate legal vertical moves (up)
                for (int j = i - 8; j >= 0; j -= 8) {
                    targetSquare = 1L << j;
                    if ((targetSquare & Board.RANK_1) != 0) break; // Break if rank 1 is reached
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & whiteOcc) != 0) moveList.add(targetSquare); // Capture if white piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                }

                // Calculate legal vertical moves (down)
                for (int j = i + 8; j < 64; j += 8) {
                    targetSquare = 1L << j;
                    if ((targetSquare & Board.RANK_8) != 0) break; // Break if rank 8 is reached
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & whiteOcc) != 0) moveList.add(targetSquare); // Capture if white piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                }

                tuple.setSecond(moveList); // Add moveList to individual piece's tuple
                if(!moveList.isEmpty()) {
                    finalMoves.add(tuple);
                } // Add tuple of individual piece to the list
            }
        }
        return finalMoves;
    }
     */


    // Define the function for calculating legal moves for a queen
    public List<Tuple<Long, List<Long>>> whiteQueenMove(Long queens, Long whiteOcc, Long blackOcc) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

        // Combine legal moves of rooks and bishops for white queen
        List<Tuple<Long, List<Long>>> rookMoves = whiteRookMove(queens, whiteOcc, blackOcc);
        List<Tuple<Long, List<Long>>> bishopMoves = whiteBishopMove(queens, whiteOcc, blackOcc);

        // Add rook moves to the final moves list
        finalMoves.addAll(rookMoves);
        // Add bishop moves to the final moves list
        finalMoves.addAll(bishopMoves);

        return finalMoves;
    }

    // Define the function for calculating legal moves for a queen
    public List<Tuple<Long, List<Long>>> blackQueenMove(Long queens, Long whiteOcc, Long blackOcc) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

        // Combine legal moves of rooks and bishops for black queen
        List<Tuple<Long, List<Long>>> rookMoves = blackRookMove(queens, blackOcc, whiteOcc);
        List<Tuple<Long, List<Long>>> bishopMoves = blackBishopMove(queens, blackOcc, whiteOcc);

        // Add rook moves to the final moves list
        finalMoves.addAll(rookMoves);
        // Add bishop moves to the final moves list
        finalMoves.addAll(bishopMoves);

        return finalMoves;
    }

    public List<Tuple<Long, List<Long>>> whiteKingMove(long kingBoard, long whiteOcc) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

        // Iterate through to find the king
        for (int i = 0; i < 64; i++) {
            long kingMask = 1L << i;
            // Check if the white king is at the current position
            if ((kingBoard & kingMask) != 0) {

                List<Long> moveList = new ArrayList<>();

                Tuple tuple = new Tuple(0L, moveList);
                tuple.setFirst(kingMask);

                // Move one square forward
                Long forward = (kingBoard >> 8) & ~whiteOcc & ~Board.RANK_8;
                if (forward != 0) {
                    moveList.add(forward);
                }
                // Move one square to the right
                Long right = (kingBoard >> 1) & ~whiteOcc & ~Board.FILE_H;
                if (right != 0) {
                    moveList.add(right);
                }
                // Move one square to the left
                Long left = (kingBoard << 1) & ~whiteOcc & ~Board.FILE_A;
                if (left != 0) {
                    moveList.add(left);
                }
                // Move one square back
                Long back = (kingBoard << 8) & ~whiteOcc & ~Board.RANK_1;
                if (back != 0) {
                    moveList.add(back);
                }
                // Move diagonally forward and left
                Long forwardLeftMove = (kingBoard >> 7) & ~whiteOcc & ~Board.FILE_A & ~Board.RANK_8;
                if (forwardLeftMove != 0) {
                    moveList.add(forwardLeftMove);
                }
                // Move diagonally forward and right
                Long forwardRightMove = (kingBoard >> 9) & ~whiteOcc & ~Board.FILE_H & ~Board.RANK_8;
                if (forwardRightMove != 0) {
                    moveList.add(forwardRightMove);
                }
                // Move diagonally back and left
                Long backLeftMove = (kingBoard << 7) & ~whiteOcc & ~Board.FILE_H & ~Board.RANK_1;
                if (backLeftMove != 0) {
                    moveList.add(backLeftMove);
                }
                // Move diagonally back and right
                Long backRightMove = (kingBoard << 9) & ~whiteOcc & ~Board.FILE_A & ~Board.RANK_1;
                if (backRightMove != 0) {
                    moveList.add(backRightMove);
                }
                tuple.setSecond(moveList);
                if (!moveList.isEmpty()) {
                    finalMoves.add(tuple);
                }
            }
        }
        return finalMoves;
    }

    public List<Tuple<Long, List<Long>>> blackKingMove(long kingBoard, long blackOcc) {

        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

        // Iterate through to find the king
        for (int i = 0; i < 64; i++) {
            long kingMask = 1L << i;
            // Check if the black king is at the current position
            if ((kingBoard & kingMask) != 0) {

                List<Long> moveList = new ArrayList<>();

                Tuple tuple = new Tuple(0L, moveList);
                tuple.setFirst(kingMask);

                // Move one square forward
                Long forward = (kingBoard >> 8) & ~blackOcc & ~Board.RANK_8;
                if (forward != 0) {
                    moveList.add(forward);
                }
                // Move one square to the right
                Long right = (kingBoard >> 1) & ~blackOcc & ~Board.FILE_H;
                if (right != 0) {
                    moveList.add(right);
                }
                // Move one square to the left
                Long left = (kingBoard << 1) & ~blackOcc & ~Board.FILE_A;
                if (left != 0) {
                    moveList.add(left);
                }
                // Move one square back
                Long back = (kingBoard << 8) & ~blackOcc & ~Board.RANK_1;
                if (back != 0) {
                    moveList.add(back);
                }
                // Move diagonally forward and left
                Long forwardLeftMove = (kingBoard >> 7) & ~blackOcc & ~Board.FILE_A & ~Board.RANK_8;
                if (forwardLeftMove != 0) {
                    moveList.add(forwardLeftMove);
                }
                // Move diagonally forward and right
                Long forwardRightMove = (kingBoard >> 9) & ~blackOcc & ~Board.FILE_H & ~Board.RANK_8;
                if (forwardRightMove != 0) {
                    moveList.add(forwardRightMove);
                }
                // Move diagonally back and left
                Long backLeftMove = (kingBoard << 7) & ~blackOcc & ~Board.FILE_H & ~Board.RANK_1;
                if (backLeftMove != 0) {
                    moveList.add(backLeftMove);
                }
                // Move diagonally back and right
                Long backRightMove = (kingBoard << 9) & ~blackOcc & ~Board.FILE_A & ~Board.RANK_1;
                if (backRightMove != 0) {
                    moveList.add(backRightMove);
                }
                tuple.setSecond(moveList);
                if (!moveList.isEmpty()) {
                    finalMoves.add(tuple);
                }
            }
        }
        return finalMoves;
    }

    public Board doMove(Board currentBoard, Tuple tuple){

        if(tuple != null) { //make sure theres available move

            //this inputs the bitboard of the piece that is being moved and removes the starting piece
            Long start = (Long) tuple.getStart();
            Long endMove = (Long) tuple.getMoves();

            //piece type of captured piece
            String capturedPiece = "NA";

//CLEAR END SQUARE FIRST
            //find type of piece on end square to capture
            if ((currentBoard.whitePawnBoard & endMove) != 0) {
                //remove captured piece
                currentBoard.whitePawnBoard = currentBoard.whitePawnBoard & ~endMove;

                //captured piece data
                capturedPiece = "WP";
            } else if ((currentBoard.blackPawnBoard & endMove) != 0) {
                currentBoard.blackPawnBoard = currentBoard.blackPawnBoard & ~endMove;

                //captured piece data
                capturedPiece = "BP";
            } else if ((currentBoard.whiteQueenBoard & endMove) != 0) {
                currentBoard.whiteQueenBoard = currentBoard.whiteQueenBoard & ~endMove;
                capturedPiece = "WQ";
            } else if ((currentBoard.blackQueenBoard & endMove) != 0) {
                currentBoard.blackQueenBoard = currentBoard.blackQueenBoard & ~endMove;
                capturedPiece = "BQ";
            } else if ((currentBoard.whiteKnightBoard & endMove) != 0) {
                currentBoard.whiteKnightBoard = currentBoard.whiteKnightBoard & ~endMove;
                capturedPiece = "WN";
            } else if ((currentBoard.blackKnightBoard & endMove) != 0) {
                currentBoard.blackKnightBoard = currentBoard.blackKnightBoard & ~endMove;
                capturedPiece = "BN";
            } else if ((currentBoard.whiteRookBoard & endMove) != 0) {
                currentBoard.whiteRookBoard = currentBoard.whiteRookBoard & ~endMove;
                capturedPiece = "WR";
            } else if ((currentBoard.blackRookBoard & endMove) != 0) {
                currentBoard.blackRookBoard = currentBoard.blackRookBoard & ~endMove;
                capturedPiece = "BR";
            } else if ((currentBoard.whiteBishopBoard & endMove) != 0) {
                currentBoard.whiteBishopBoard = currentBoard.whiteBishopBoard & ~endMove;
                capturedPiece = "WB";
            } else if ((currentBoard.blackBishopBoard & endMove) != 0) {
                currentBoard.blackBishopBoard = currentBoard.blackBishopBoard & ~endMove;

                //captured piece data
                capturedPiece = "BB";
            } else if ((currentBoard.whiteKingBoard & endMove) != 0) {
                currentBoard.whiteKingBoard = currentBoard.whiteKingBoard & ~endMove;
                capturedPiece = "WK";
            } else if ((currentBoard.blackKingBoard & endMove) != 0) {
                currentBoard.blackKingBoard = currentBoard.blackKingBoard & ~endMove;
                capturedPiece = "BK";
            }

//CLEAR START SQUARE, POPULATE END SQUARE
// CASES TO REMOVE START PIECE AND ADD END PIECE TO CORRECT BOARD.
            if ((currentBoard.whitePawnBoard & start) != 0) {
                currentBoard.whitePawnBoard = currentBoard.whitePawnBoard & ~start;   //REMOVES THE STARTING SQUARE PIECE
                currentBoard.whitePawnBoard |= endMove;                               //ADDS ENDMOVE TO CORRECT BITBOARD
            } else if ((currentBoard.blackPawnBoard & start) != 0) {
                currentBoard.blackPawnBoard = currentBoard.blackPawnBoard & ~start;
                currentBoard.blackPawnBoard |= endMove;
            } else if ((currentBoard.whiteKnightBoard & start) != 0) {
                currentBoard.whiteKnightBoard = currentBoard.whiteKnightBoard & ~start;
                currentBoard.whiteKnightBoard |= endMove;
            } else if ((currentBoard.blackKnightBoard & start) != 0) {
                currentBoard.blackKnightBoard = currentBoard.blackKnightBoard & ~start;
                currentBoard.blackKnightBoard |= endMove;
            } else if ((currentBoard.whiteRookBoard & start) != 0) {
                currentBoard.whiteRookBoard = currentBoard.whiteRookBoard & ~start;
                currentBoard.whiteRookBoard |= endMove;
            } else if ((currentBoard.blackRookBoard & start) != 0) {
                currentBoard.blackRookBoard = currentBoard.blackRookBoard & ~start;
                currentBoard.blackRookBoard |= endMove;
            } else if ((currentBoard.whiteBishopBoard & start) != 0) {
                currentBoard.whiteBishopBoard = currentBoard.whiteBishopBoard & ~start;
                currentBoard.whiteBishopBoard |= endMove;
            } else if ((currentBoard.blackBishopBoard & start) != 0) {
                currentBoard.blackBishopBoard = currentBoard.blackBishopBoard & ~start;
                currentBoard.blackBishopBoard |= endMove;
            } else if ((currentBoard.whiteQueenBoard & start) != 0) {
                currentBoard.whiteQueenBoard = currentBoard.whiteQueenBoard & ~start;
                currentBoard.whiteQueenBoard |= endMove;
            } else if ((currentBoard.blackQueenBoard & start) != 0) {
                currentBoard.blackQueenBoard = currentBoard.blackQueenBoard & ~start;
                currentBoard.blackQueenBoard |= endMove;
            } else if ((currentBoard.whiteKingBoard & start) != 0) {
                currentBoard.whiteKingBoard = currentBoard.whiteKingBoard & ~start;
                currentBoard.whiteKingBoard |= endMove;
            } else if ((currentBoard.blackKingBoard & start) != 0) {
                currentBoard.blackKingBoard = currentBoard.blackKingBoard & ~start;
                currentBoard.blackKingBoard |= endMove;
            }

            //add move to moves list
            Tuple<Tuple<Long, Long>, String> finalTuple = new Tuple<>(tuple, capturedPiece);

            madeMoves.push(finalTuple);
        }else{
            System.out.println("no available moves");
        }
        return currentBoard;
    }

    public void undoMove(Board currentBoard) {

        //Get move information
        if (!madeMoves.isEmpty()) {
            Tuple<Tuple<Long, Long>, String> moveInfo = madeMoves.pop();

            //get moved piece tuple
            Tuple<Long, Long> lastMove = moveInfo.getStart();

            Long endPosition = lastMove.getMoves();
            Long startPosition = lastMove.getStart();


            // Identifying the moving piece and moving it back
            if ((currentBoard.whitePawnBoard & endPosition) != 0) {
                currentBoard.whitePawnBoard &= ~endPosition;
                currentBoard.whitePawnBoard |= startPosition;
            } else if ((currentBoard.blackPawnBoard & endPosition) != 0) {
                currentBoard.blackPawnBoard &= ~endPosition;
                currentBoard.blackPawnBoard |= startPosition;
            } else if ((currentBoard.whiteKnightBoard & endPosition) != 0) {
                currentBoard.whiteKnightBoard &= ~endPosition;
                currentBoard.whiteKnightBoard |= startPosition;
            } else if ((currentBoard.blackKnightBoard & endPosition) != 0) {
                currentBoard.blackKnightBoard &= ~endPosition;
                currentBoard.blackKnightBoard |= startPosition;
            } else if ((currentBoard.whiteBishopBoard & endPosition) != 0) {
                currentBoard.whiteBishopBoard &= ~endPosition;
                currentBoard.whiteBishopBoard |= startPosition;
            } else if ((currentBoard.blackBishopBoard & endPosition) != 0) {
                currentBoard.blackBishopBoard &= ~endPosition;
                currentBoard.blackBishopBoard |= startPosition;
            } else if ((currentBoard.whiteRookBoard & endPosition) != 0) {
                currentBoard.whiteRookBoard &= ~endPosition;
                currentBoard.whiteRookBoard |= startPosition;
            } else if ((currentBoard.blackRookBoard & endPosition) != 0) {
                currentBoard.blackRookBoard &= ~endPosition;
                currentBoard.blackRookBoard |= startPosition;
            } else if ((currentBoard.whiteQueenBoard & endPosition) != 0) {
                currentBoard.whiteQueenBoard &= ~endPosition;
                currentBoard.whiteQueenBoard |= startPosition;
            } else if ((currentBoard.blackQueenBoard & endPosition) != 0) {
                currentBoard.blackQueenBoard &= ~endPosition;
                currentBoard.blackQueenBoard |= startPosition;
            } else if ((currentBoard.whiteKingBoard & endPosition) != 0) {
                currentBoard.whiteKingBoard &= ~endPosition;
                currentBoard.whiteKingBoard |= startPosition;
            } else if ((currentBoard.blackKingBoard & endPosition) != 0) {
                currentBoard.blackKingBoard &= ~endPosition;
                currentBoard.blackKingBoard |= startPosition;
            }

            //NEED TO ADD BACK CAPTURED PIECE
            String pieceType = moveInfo.getMoves();

            if (!Objects.equals(pieceType, "NA")) {
                if (Objects.equals(pieceType, "WP")) {
                    currentBoard.whitePawnBoard |= endPosition;
                } else if (Objects.equals(pieceType, "BP")) {
                    currentBoard.blackPawnBoard |= endPosition;
                } else if (Objects.equals(pieceType, "WN")) {

                    currentBoard.whiteKnightBoard |= endPosition;
                } else if (Objects.equals(pieceType, "BN")) {

                    currentBoard.blackKnightBoard |= endPosition;
                } else if (Objects.equals(pieceType, "WB")) {

                    currentBoard.whiteBishopBoard |= endPosition;
                } else if (Objects.equals(pieceType, "BB")) {
                    currentBoard.blackBishopBoard |= endPosition;
                } else if (Objects.equals(pieceType, "WR")) {
                    currentBoard.whiteRookBoard |= endPosition;
                } else if (Objects.equals(pieceType, "BR")) {
                    currentBoard.blackRookBoard |= endPosition;
                } else if (Objects.equals(pieceType, "WQ")) {
                    currentBoard.whiteQueenBoard |= endPosition;
                } else if (Objects.equals(pieceType, "BQ")) {
                    currentBoard.blackQueenBoard |= endPosition;
                } else if (Objects.equals(pieceType, "WK")) {
                    currentBoard.whiteKingBoard |= endPosition;
                } else if (Objects.equals(pieceType, "BK")) {
                    currentBoard.blackKingBoard |= endPosition;
                }

            }


            // Update occupancy boards
            currentBoard.whiteOccBoard = currentBoard.whitePawnBoard | currentBoard.whiteKnightBoard |
                    currentBoard.whiteBishopBoard | currentBoard.whiteRookBoard |
                    currentBoard.whiteQueenBoard | currentBoard.whiteKingBoard;

            currentBoard.blackOccBoard = currentBoard.blackPawnBoard | currentBoard.blackKnightBoard |
                    currentBoard.blackBishopBoard | currentBoard.blackRookBoard |
                    currentBoard.blackQueenBoard | currentBoard.blackKingBoard;

            // Update the overall occupancy board
            currentBoard.occBoard = currentBoard.whiteOccBoard | currentBoard.blackOccBoard;
        }
    }

    //This is used only for making a random move
    public Tuple choseMove(List<Tuple<Long, List<Long>>> moveList){

        Tuple piece = moveList.get(randomGenerator.nextInt(moveList.size()));
        List<Long> moves = (List<Long>) piece.getMoves();  //get list of moves

        Long endMove = moves.get(randomGenerator.nextInt(moves.size()));   //chooses which move in the list of moves for the given piece to execute, will have to edit later just for testing logic in move generate function***

        piece.setSecond(endMove);
        return piece;
    }

    public Board randomBlackMove(Board chessBoard){
        List<Tuple<Long, List<Long>>> moveList = generateBlackMoves(chessBoard);     //generate all moves

        Tuple piece = choseMove(moveList); //select Piece and Move for piece

        chessBoard = doMove(chessBoard, piece);  //EXECUTES the chosen move for piece
        return chessBoard;
    }

    public Board randomWhiteMove(Board chessBoard){
        List<Tuple<Long, List<Long>>> moveList = generateWhiteMoves(chessBoard);     //generate all moves

        Tuple piece = choseMove(moveList); //select Piece and Move for piece

        chessBoard = doMove(chessBoard, piece);  //EXECUTES the chosen move for piece
        return chessBoard;
    }



    //inCheck testing
    public boolean inCheck(Board chessBoard){  //Logic is to start with the king position, and shift it to the squares that it can be attcacked from for each piece type.
        // Bit masks to avoid wraparound
        long notAFile = 0xFEFEFEFEFEFEFEFEL; // ~0x0101010101010101L
        long notHFile = 0x7F7F7F7F7F7F7F7FL; // ~0x8080808080808080L
        long notABFile = 0xFCFCFCFCFCFCFCFCL; // ~0x0303030303030303L
        long notGHFile = 0x3F3F3F3F3F3F3F3FL; // ~0xC0C0C0C0C0C0C0C0L


        Long kingPos = chessBoard.whiteKingBoard;

        //Knights
        Long knightPos = chessBoard.blackKnightBoard;
        //Pawns
        Long pawnAttacks;
        pawnAttacks = ((kingPos >>> 7) & notHFile) | ((kingPos >>> 9) & notAFile);

        if( //knights
                ((kingPos << 17 & knightPos) != 0)||
                ((kingPos << 15 & notHFile & knightPos) != 0) ||
                ((kingPos << 10 & notGHFile & knightPos) != 0) ||
                ((kingPos << 6 & notABFile & knightPos) != 0) ||
                ((kingPos >>> 17 & notHFile & knightPos) != 0) ||
                ((kingPos >>> 15 & notAFile & knightPos) != 0) ||
                ((kingPos >>> 10 & notABFile & knightPos) != 0) ||
                ((kingPos >>> 6 & notGHFile & knightPos) != 0)){
            return true;
        }else if ((pawnAttacks & chessBoard.blackPawnBoard) != 0){   ////PAWNS
            return true;
        }


        //if there are no inCheck moves, return false
        return false;
    }

}

//Later on: if we want to speed up move generation functions, make king and knight lookup instead of calculation
//use magic bitboard for sliding pieces like rook and bishop
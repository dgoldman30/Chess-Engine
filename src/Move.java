import java.util.*;

import static java.lang.Long.*;

public class Move {


    // current move errors:
    // rooks wrap around the board (fixed?)
    // white pawns capture one square forward (Fixed?)
    // black queen and pawns captures other black pieces (Fixed by TC, occboards were reversed in parameters for move functions)

    Random randomGenerator = new Random(); //for random move REMOVE LATER LOL***
    protected enum pieceNames{
        NA(0), WP(1), BP(2), WQ(3), BQ(4), WN(5), BN(6),
        WR(7), BR(8), WB(9), BB(10), WK(11), BK(12);

        private final int pieceNum;

        pieceNames(int pieceNum){
            this.pieceNum = pieceNum;
        }

        private int getPieceNum() {
            return pieceNum;
        }

    }

    //madeMoves represents a Stack(Move((startPosition, endPosition), Captured Piece type)). Used for undoMove to keep track of past moves
    Stack<Tuple<Tuple<Long, Long>, Integer>> madeMoves = new Stack<>();    //make tuple constructor for third element String, twice as efficient


    public List<Tuple<Long, List<Long>>> generateWhiteMoves(Board chessBoard) {
        List<Tuple<Long, List<Long>>> moveList = new ArrayList<>();

        moveList.addAll(whitePawnMove(chessBoard, chessBoard.whitePawnBoard, chessBoard.whiteOccBoard, chessBoard.blackOccBoard, chessBoard.isInCheck()));
        moveList.addAll(whiteKnightMove(chessBoard, chessBoard.whiteKnightBoard, chessBoard.whiteOccBoard, chessBoard.isInCheck()));
        moveList.addAll(whiteRookMove(chessBoard, chessBoard.whiteRookBoard, chessBoard.whiteOccBoard, chessBoard.blackOccBoard, chessBoard.isInCheck()));
        moveList.addAll(whiteBishopMove(chessBoard, chessBoard.whiteBishopBoard, chessBoard.whiteOccBoard, chessBoard.blackOccBoard, chessBoard.isInCheck()));
        moveList.addAll(whiteQueenMove(chessBoard, chessBoard.whiteQueenBoard, chessBoard.whiteOccBoard, chessBoard.blackOccBoard, chessBoard.isInCheck()));
        moveList.addAll(whiteKingMove(chessBoard, chessBoard.whiteKingBoard, chessBoard.whiteOccBoard, chessBoard.isInCheck()));

        return moveList;
    }

    public List<Tuple<Long, List<Long>>> generateBlackMoves(Board chessBoard) {
        List<Tuple<Long, List<Long>>> moveList = new ArrayList<>();

        moveList.addAll(blackPawnMove(chessBoard, chessBoard.blackPawnBoard, chessBoard.whiteOccBoard, chessBoard.blackOccBoard, chessBoard.isInCheck()));
        moveList.addAll(blackKnightMove(chessBoard, chessBoard.blackKnightBoard, chessBoard.blackOccBoard, chessBoard.isInCheck()));
        moveList.addAll(blackRookMove(chessBoard, chessBoard.blackRookBoard, chessBoard.blackOccBoard, chessBoard.whiteOccBoard, chessBoard.isInCheck()));
        moveList.addAll(blackBishopMove(chessBoard, chessBoard.blackBishopBoard, chessBoard.blackOccBoard, chessBoard.whiteOccBoard, chessBoard.isInCheck()));
        moveList.addAll(blackQueenMove(chessBoard, chessBoard.blackQueenBoard, chessBoard.whiteOccBoard, chessBoard.blackOccBoard, chessBoard.isInCheck()));
        moveList.addAll(blackKingMove(chessBoard, chessBoard.blackKingBoard, chessBoard.blackOccBoard, chessBoard.isInCheck()));

        return moveList;
    }

    protected List<Long> filterMoves(List<Long> origList, Board chessBoard, pieceNames piece, boolean isWhite){
        List<Long> newList = new ArrayList<>();
        Board newBoard = new Board(chessBoard);

        for(Long move : origList){
            switch (piece){
                case BB:
                    newBoard.setBlackBishopBoard(move);
                    break;
                case BK:
                    newBoard.setBlackKingBoard(move);
                    break;
                case BN:
                    newBoard.setBlackKnightBoard(move);
                    break;
                case BP:
                    newBoard.setBlackPawnBoard(move);
                case BQ:
                    newBoard.setBlackQueenBoard(move);
                    break;
                case BR:
                    newBoard.setBlackRookBoard(move);
                    break;
                case WB:
                    newBoard.setWhiteBishopBoard(move);
                    break;
                case WK:
                    newBoard.setWhiteKingBoard(move);
                    break;
                case WN:
                    newBoard.setWhiteKnightBoard(move);
                    break;
                case WP:
                    newBoard.setWhitePawnBoard(move);
                    break;
                case WQ:
                    newBoard.setWhiteQueenBoard(move);
                    break;
                case WR:
                    newBoard.setWhiteRookBoard(move);
                    break;
            }

            if(!newBoard.inCheck(newBoard, isWhite))
                newList.add(move);
        }

        return newList;
    }

    public List<Tuple<Long, List<Long>>> whitePawnMove(Board chessBoard, Long pawns, Long whiteOcc, Long blackOcc, boolean inCheck) {

        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

        if(inCheck){
            List<Tuple<pieceNames, Long>> inCheckList = Board.inCheckList(chessBoard, true);
            if(inCheckList.size() > 1){
                return finalMoves;
            }
        }

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
                Long singleMove = (pawnMask >>> 8) & ~occ;
                if (singleMove != 0) {
                    moveList.add(singleMove);
                }
                // Move two squares forward if the pawn is on its starting rank
                Long doubleMove = ((singleMove & Board.RANK_6) >> 8) & ~occ;
                if (doubleMove != 0) {
                    moveList.add(doubleMove);
                }
                // Capture to the left
                Long captureLeft = (pawnMask >>> 7) & ~Board.FILE_A & blackOcc;
                if (captureLeft != 0) {
                    moveList.add(captureLeft);
                }
                // Capture to the right
                Long captureRight = (pawnMask >>> 9) & ~Board.FILE_H & blackOcc;
                if (captureRight != 0) {
                    moveList.add(captureRight);
                }

                if (inCheck)
                    moveList = filterMoves(moveList, chessBoard, pieceNames.WP, true);

                tuple.setSecond(moveList);
                if (!moveList.isEmpty()) {
                    finalMoves.add(tuple);
                }
            }
        }
        return finalMoves;
    }

    public List<Tuple<Long, List<Long>>> blackPawnMove(Board chessBoard, Long pawns, Long whiteOcc, Long blackOcc,boolean inCheck) {

        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

        if(inCheck){
            List<Tuple<pieceNames, Long>> inCheckList = Board.inCheckList(chessBoard, true);
            if(inCheckList.size() > 1){
                return finalMoves;
            }
        }

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

                if (inCheck)
                    moveList = filterMoves(moveList, chessBoard, pieceNames.BP, false);

                tuple.setSecond(moveList);
                if (!moveList.isEmpty()) {
                    finalMoves.add(tuple);
                }
            }
        }
        return finalMoves;
    }

    public List<Tuple<Long, List<Long>>> whiteKnightMove(Board chessBoard, Long knights, Long whiteOcc, boolean inCheck) {    //HAVING BOUND ISSUES. it still adds the out of bounds moves to the list, resulting in empty moves***

        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

        if(inCheck){
            List<Tuple<pieceNames, Long>> inCheckList = Board.inCheckList(chessBoard, true);
            if(inCheckList.size() > 1){
                return finalMoves;
            }
        }

        // Iterate through each knight individually
        for (int i = 0; i < 64; i++) {
            long knightMask = 1L << i;
            // Check if there's a white knight at the current position
            if ((knights & knightMask) != 0) {

                List<Long> moveList = new ArrayList<>(); //make movelist for the individual piece

                Tuple tuple = new Tuple(0L, moveList); //initiate tuple for individual piece
                tuple.setFirst(knightMask);   //set starting board

                moveList.add((knightMask >>> 6) & ~whiteOcc & ~(Board.FILE_A | Board.FILE_B)); //& will check the end position
                moveList.add((knightMask >>> 10) & ~whiteOcc & ~(Board.FILE_G | Board.FILE_H));
                moveList.add((knightMask >>> 15) & ~whiteOcc & ~Board.FILE_A);
                moveList.add((knightMask >>> 17) & ~whiteOcc & ~Board.FILE_H);

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

                if (inCheck)
                    moveList = filterMoves(moveList, chessBoard, pieceNames.WN, true);

                tuple.setSecond(moveList); //add moveList to individual pieces tuple
                if (!moveList.isEmpty()) {
                    finalMoves.add(tuple);
                } //add tuple of individual piece to list if list is not empty
            }
        }
        return finalMoves;
    }

    public List<Tuple<Long, List<Long>>> blackKnightMove(Board chessBoard, Long knights, Long blackOcc, boolean inCheck) {

        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

        if(inCheck){
            List<Tuple<pieceNames, Long>> inCheckList = Board.inCheckList(chessBoard, true);
            if(inCheckList.size() > 1){
                return finalMoves;
            }
        }

        // Iterate through each knight individually
        for (int i = 0; i < 64; i++) {
            long knightMask = 1L << i;
            // Check if there's a black knight at the current position
            if ((knights & knightMask) != 0) {

                List<Long> moveList = new ArrayList<>(); //make movelist for the individual piece

                Tuple tuple = new Tuple(0L, moveList); //initiate tuple for individual piece
                tuple.setFirst(knightMask);   //set starting board

                moveList.add((knightMask >>> 6) & ~blackOcc & ~(Board.FILE_A | Board.FILE_B)); //& will check the end position
                moveList.add((knightMask >>> 10) & ~blackOcc & ~(Board.FILE_G | Board.FILE_H));
                moveList.add((knightMask >>> 15) & ~blackOcc & ~Board.FILE_A);
                moveList.add((knightMask >>> 17) & ~blackOcc & ~Board.FILE_H);

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

                if (inCheck)
                    moveList = filterMoves(moveList, chessBoard, pieceNames.BN, false);

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

    public List<Tuple<Long, List<Long>>> whiteBishopMove(Board chessBoard, Long bishops, Long whiteOcc, Long blackOcc, boolean inCheck) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

        if(inCheck){
            List<Tuple<pieceNames, Long>> inCheckList = Board.inCheckList(chessBoard, true);
            if(inCheckList.size() > 1){
                return finalMoves;
            }
        }

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

            if (inCheck)
                moveList = filterMoves(moveList, chessBoard, pieceNames.WB, true);

            // Add moveList to individual piece's tuple
            tuple.setSecond(moveList);
            if (!moveList.isEmpty()) {
                finalMoves.add(tuple);
            }
        }
        return finalMoves;
        }

    public List<Tuple<Long, List<Long>>> blackBishopMove(Board chessBoard, Long bishops, Long blackOcc, Long whiteOcc, boolean inCheck) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

        if(inCheck){
            List<Tuple<pieceNames, Long>> inCheckList = Board.inCheckList(chessBoard, true);
            if(inCheckList.size() > 1){
                return finalMoves;
            }
        }

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

            if (inCheck)
                moveList = filterMoves(moveList, chessBoard, pieceNames.BB, false);

            // Add moveList to individual piece's tuple
            tuple.setSecond(moveList);
            if (!moveList.isEmpty()) {
                finalMoves.add(tuple);
            }
        }
        return finalMoves;
    }

    // Define the function for calculating legal moves for a rook
    public List<Tuple<Long, List<Long>>> whiteRookMove(Board chessBoard, Long rooks, Long whiteOcc, Long blackOcc, boolean inCheck) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();
        long rookMask;

        if(inCheck){
            List<Tuple<pieceNames, Long>> inCheckList = Board.inCheckList(chessBoard, true);
            if(inCheckList.size() > 1){
                return finalMoves;
            }
        }

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

            if (inCheck)
                moveList = filterMoves(moveList, chessBoard, pieceNames.WR, true);

            // Add moveList to individual piece's tuple
            tuple.setSecond(moveList);
            if (!moveList.isEmpty()) {
                finalMoves.add(tuple);
            }
        }
    return finalMoves;
    }

    public List<Tuple<Long, List<Long>>> blackRookMove(Board chessBoard, Long rooks, Long blackOcc, Long whiteOcc, boolean inCheck) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();
        long rookMask;


        if(inCheck){
            List<Tuple<pieceNames, Long>> inCheckList = Board.inCheckList(chessBoard, true);
            if(inCheckList.size() > 1){
                return finalMoves;
            }
        }

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

            if (inCheck)
                moveList = filterMoves(moveList, chessBoard, pieceNames.BR, false);

            tuple.setSecond(moveList);
            if (!moveList.isEmpty()) {
                finalMoves.add(tuple);
            }
        }
        return finalMoves;
    }


    // Define the function for calculating legal moves for a queen
    public List<Tuple<Long, List<Long>>> whiteQueenMove(Board chessBoard, Long queens, Long whiteOcc, Long blackOcc, boolean inCheck) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

        if(inCheck){
            List<Tuple<pieceNames, Long>> inCheckList = Board.inCheckList(chessBoard, true);
            if(inCheckList.size() > 1){
                return finalMoves;
            }
        }

        // Combine legal moves of rooks and bishops for white queen
        List<Tuple<Long, List<Long>>> rookMoves = whiteRookMove(chessBoard, queens, whiteOcc, blackOcc, inCheck);
        List<Tuple<Long, List<Long>>> bishopMoves = whiteBishopMove(chessBoard, queens, whiteOcc, blackOcc, inCheck);

        // Add rook moves to the final moves list
        finalMoves.addAll(rookMoves);
        // Add bishop moves to the final moves list
        finalMoves.addAll(bishopMoves);

        return finalMoves;
    }

    // Define the function for calculating legal moves for a queen
    public List<Tuple<Long, List<Long>>> blackQueenMove(Board chessBoard, Long queens, Long whiteOcc, Long blackOcc, boolean inCheck) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

        if(inCheck){
            List<Tuple<pieceNames, Long>> inCheckList = Board.inCheckList(chessBoard, true);
            if(inCheckList.size() > 1){
                return finalMoves;
            }
        }

        // Combine legal moves of rooks and bishops for black queen
        List<Tuple<Long, List<Long>>> rookMoves = blackRookMove(chessBoard, queens, blackOcc, whiteOcc, inCheck);
        List<Tuple<Long, List<Long>>> bishopMoves = blackBishopMove(chessBoard,queens, blackOcc, whiteOcc, inCheck);

        // Add rook moves to the final moves list
        finalMoves.addAll(rookMoves);
        // Add bishop moves to the final moves list
        finalMoves.addAll(bishopMoves);

        return finalMoves;
    }

    public List<Tuple<Long, List<Long>>> whiteKingMove(Board chessboard, long kingBoard, long whiteOcc, boolean inCheck) {
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

                if (inCheck)
                    moveList = filterMoves(moveList, chessboard, pieceNames.WK, true);

                tuple.setSecond(moveList);
                if (!moveList.isEmpty()) {
                    finalMoves.add(tuple);
                }
            }
        }
        return finalMoves;
    }

    public List<Tuple<Long, List<Long>>> blackKingMove(Board chessBoard, long kingBoard, long blackOcc, boolean inCheck) {

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

                if (inCheck)
                    moveList = filterMoves(moveList, chessBoard, pieceNames.BK, false);

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
            int capturedPiece = pieceNames.NA.getPieceNum();

//CLEAR END SQUARE FIRST
            //find type of piece on end square to capture
            if ((currentBoard.whitePawnBoard & endMove) != 0) {
                //remove captured piece
                currentBoard.whitePawnBoard = currentBoard.whitePawnBoard & ~endMove;
                //captured piece data
                capturedPiece = pieceNames.WP.getPieceNum();
            } else if ((currentBoard.blackPawnBoard & endMove) != 0) {
                currentBoard.blackPawnBoard = currentBoard.blackPawnBoard & ~endMove;
                //captured piece data
                capturedPiece = pieceNames.BP.getPieceNum();
            } else if ((currentBoard.whiteQueenBoard & endMove) != 0) {
                currentBoard.whiteQueenBoard = currentBoard.whiteQueenBoard & ~endMove;
                capturedPiece = pieceNames.WQ.getPieceNum();
            } else if ((currentBoard.blackQueenBoard & endMove) != 0) {
                currentBoard.blackQueenBoard = currentBoard.blackQueenBoard & ~endMove;
                capturedPiece = pieceNames.BQ.getPieceNum();
            } else if ((currentBoard.whiteKnightBoard & endMove) != 0) {
                currentBoard.whiteKnightBoard = currentBoard.whiteKnightBoard & ~endMove;
                capturedPiece = pieceNames.WN.getPieceNum();
            } else if ((currentBoard.blackKnightBoard & endMove) != 0) {
                currentBoard.blackKnightBoard = currentBoard.blackKnightBoard & ~endMove;
                capturedPiece = pieceNames.BN.getPieceNum();
            } else if ((currentBoard.whiteRookBoard & endMove) != 0) {
                currentBoard.whiteRookBoard = currentBoard.whiteRookBoard & ~endMove;
                capturedPiece = pieceNames.WR.getPieceNum();
            } else if ((currentBoard.blackRookBoard & endMove) != 0) {
                currentBoard.blackRookBoard = currentBoard.blackRookBoard & ~endMove;
                capturedPiece = pieceNames.BR.getPieceNum();
            } else if ((currentBoard.whiteBishopBoard & endMove) != 0) {
                currentBoard.whiteBishopBoard = currentBoard.whiteBishopBoard & ~endMove;
                capturedPiece = pieceNames.WB.getPieceNum();
            } else if ((currentBoard.blackBishopBoard & endMove) != 0) {
                currentBoard.blackBishopBoard = currentBoard.blackBishopBoard & ~endMove;
                //captured piece data
                capturedPiece = pieceNames.BB.getPieceNum();
            } else if ((currentBoard.whiteKingBoard & endMove) != 0) {
                currentBoard.whiteKingBoard = currentBoard.whiteKingBoard & ~endMove;
                capturedPiece = pieceNames.WK.getPieceNum();
            } else if ((currentBoard.blackKingBoard & endMove) != 0) {
                currentBoard.blackKingBoard = currentBoard.blackKingBoard & ~endMove;
                capturedPiece = pieceNames.BK.getPieceNum();
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
            Tuple<Tuple<Long, Long>, Integer> finalTuple = new Tuple<>(tuple, capturedPiece);

            madeMoves.push(finalTuple);

            // Update occupancy boards
            currentBoard.whiteOccBoard = currentBoard.whitePawnBoard | currentBoard.whiteKnightBoard |
                    currentBoard.whiteBishopBoard | currentBoard.whiteRookBoard |
                    currentBoard.whiteQueenBoard | currentBoard.whiteKingBoard;

            currentBoard.blackOccBoard = currentBoard.blackPawnBoard | currentBoard.blackKnightBoard |
                    currentBoard.blackBishopBoard | currentBoard.blackRookBoard |
                    currentBoard.blackQueenBoard | currentBoard.blackKingBoard;

            // Update the overall occupancy board
            currentBoard.occBoard = currentBoard.whiteOccBoard | currentBoard.blackOccBoard;

        }else{
            System.out.println("no available moves");
        }
        return currentBoard;
    }

    public void undoMove(Board currentBoard) {

        //Get move information
        if (!madeMoves.isEmpty()) {
            Tuple<Tuple<Long, Long>, Integer> moveInfo = madeMoves.pop();

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
            int pieceType = moveInfo.getMoves();

            if (pieceType != pieceNames.NA.getPieceNum()) {
                if (pieceType == pieceNames.WP.getPieceNum()) {
                    currentBoard.whitePawnBoard |= endPosition;
                } else if (pieceType == pieceNames.BP.getPieceNum()) {
                    currentBoard.blackPawnBoard |= endPosition;
                } else if (pieceType == pieceNames.WN.getPieceNum()) {
                    currentBoard.whiteKnightBoard |= endPosition;
                } else if (pieceType == pieceNames.BN.getPieceNum()) {
                    currentBoard.blackKnightBoard |= endPosition;
                } else if (pieceType == pieceNames.WB.getPieceNum()) {
                    currentBoard.whiteBishopBoard |= endPosition;
                } else if (pieceType == pieceNames.BB.getPieceNum()) {
                    currentBoard.blackBishopBoard |= endPosition;
                } else if (pieceType == pieceNames.WR.getPieceNum()) {
                    currentBoard.whiteRookBoard |= endPosition;
                } else if (pieceType == pieceNames.BR.getPieceNum()) {
                    currentBoard.blackRookBoard |= endPosition;
                } else if (pieceType == pieceNames.WQ.getPieceNum()) {
                    currentBoard.whiteQueenBoard |= endPosition;
                } else if (pieceType == pieceNames.BQ.getPieceNum()) {
                    currentBoard.blackQueenBoard |= endPosition;
                } else if (pieceType == pieceNames.WK.getPieceNum()) {
                    currentBoard.whiteKingBoard |= endPosition;
                } else if (pieceType == pieceNames.BK.getPieceNum()) {
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

    public Tuple randomBlackMove(Board chessBoard){
        List<Tuple<Long, List<Long>>> moveList = generateBlackMoves(chessBoard);     //generate all moves

        Tuple piece = choseMove(moveList); //select Piece and Move for piece

        return piece;
    }

    public Tuple randomWhiteMove(Board chessBoard){
        List<Tuple<Long, List<Long>>> moveList = generateWhiteMoves(chessBoard);     //generate all moves

        Tuple piece = choseMove(moveList); //select Piece and Move for piece

        return piece;
    }





}

//Later on: if we want to speed up move generation functions, make king and knight lookup instead of calculation
//use magic bitboard for sliding pieces like rook and bishop
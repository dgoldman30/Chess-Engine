import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Move {

//list of all available moves
    public List<Long> moves = new ArrayList<>();
    Random randomGenerator = new Random(); //for random move REMOVE LATER LOL



    //this will have different return statement later
    public List<Tuple<Long, List<Long>>> generateWhiteMoves(Board chessBoard){
        List<Tuple<Long, List<Long>>> moveList = new ArrayList<Tuple<Long, List<Long>>>();

        moveList.addAll(whitePawnMove(chessBoard.whitePawnBoard, chessBoard.whiteOccBoard, chessBoard.blackOccBoard));
        moveList.addAll(whiteKnightMove(chessBoard.whiteKnightBoard, chessBoard.whiteOccBoard));
        moveList.addAll(whiteRookMove(chessBoard.whiteRookBoard,chessBoard.whiteOccBoard, chessBoard.blackOccBoard));
        moveList.addAll(whiteBishopMove(chessBoard.whiteBishopBoard,chessBoard.whiteOccBoard, chessBoard.blackOccBoard));

        return moveList;
    }

    public List<Tuple<Long, List<Long>>> generateBlackMoves(Board chessBoard){
        List<Tuple<Long, List<Long>>> moveList = new ArrayList<Tuple<Long, List<Long>>>();

        moveList.addAll(blackPawnMove(chessBoard.blackPawnBoard, chessBoard.blackOccBoard, chessBoard.whiteOccBoard));
        moveList.addAll(blackKnightMove(chessBoard.blackKnightBoard, chessBoard.blackOccBoard));
        moveList.addAll(blackRookMove(chessBoard.blackRookBoard,chessBoard.blackOccBoard, chessBoard.whiteOccBoard));
        moveList.addAll(blackBishopMove(chessBoard.blackBishopBoard,chessBoard.blackOccBoard, chessBoard.whiteOccBoard));

        return moveList;

    }

    private List<Tuple<Long, List<Long>>> blackBishopMove(long blackBishopBoard, long blackOccBoard, long whiteOccBoard) {
        return null;
    }

    private List<Tuple<Long, List<Long>>> blackRookMove(long blackRookBoard, long blackOccBoard, long whiteOccBoard) {
        return null;
    }

    public List<Tuple<Long, List<Long>>> whitePawnMove(Long pawns, Long whiteOcc, Long blackOcc){

        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<Tuple<Long, List<Long>>>();

        // Iterate through each pawn individually
        for (int i = 0; i < 64; i++) {                  //pawn will never be able to get to first or last rank. room for improvement
            long pawnMask = 1L << i;


            // Check if there's a white pawn at the current position
            if ((pawns & pawnMask) != 0) {

                List<Long> moveList = new ArrayList<>();

                Tuple tuple = new Tuple(0L ,moveList);
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
                if(!moveList.isEmpty()) {
                    finalMoves.add(tuple);
                }
            }
        }

        return finalMoves;
    }

    public List<Tuple<Long, List<Long>>> blackPawnMove(Long pawns, Long whiteOcc, Long blackOcc){

        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<Tuple<Long, List<Long>>>();

        // Iterate through each pawn individually
        for (int i = 0; i < 64; i++) {                  //pawn will never be able to get to first or last rank. room for improvement
            long pawnMask = 1L << i;


            // Check if there's a white pawn at the current position
            if ((pawns & pawnMask) != 0) {

                List<Long> moveList = new ArrayList<>();

                Tuple tuple = new Tuple(0L ,moveList);
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
                finalMoves.add(tuple);
            }
        }
        return finalMoves;
    }

    public List<Tuple<Long, List<Long>>> whiteKnightMove(Long knights, Long whiteOcc){    //HAVING BOUND ISSUES. it still adds the out of bounds moves to the list, resulting in empty moves

        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<Tuple<Long, List<Long>>>();

        // Iterate through each knight individually
        for (int i = 0; i < 64; i++) {
            long knightMask = 1L << i;
            // Check if there's a black knight at the current position
            if ((knights & knightMask) != 0) {

                List<Long> moveList = new ArrayList<>(); //make movelist for the individual piece

                Tuple tuple = new Tuple(0L ,moveList); //initiate tuple for individual piece
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
                finalMoves.add(tuple); //add tuple of individual piece to list
            }
        }
        return finalMoves;
    }

    public List<Tuple<Long, List<Long>>> blackKnightMove(Long knights, Long blackOcc){

        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<Tuple<Long, List<Long>>>();

        // Iterate through each knight individually
        for (int i = 0; i < 64; i++) {
            long knightMask = 1L << i;
            // Check if there's a black knight at the current position
            if ((knights & knightMask) != 0) {

                List<Long> moveList = new ArrayList<>(); //make movelist for the individual piece

                Tuple tuple = new Tuple(0L ,moveList); //initiate tuple for individual piece
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
            }
        }
        return finalMoves;
    }

    public long blackBishopMove(long bishopBoard, long blackOcc) {
        return bishopBoard;
    }

    // Define the function for calculating legal moves for a bishop
    public List<Tuple<Long, List<Long>>> whiteBishopMove(Long bishops, Long whiteOcc, Long blackOcc) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

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
                    long targetSquare = 1L << j;
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & blackOcc) != 0) moveList.add(targetSquare); // Capture if black piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                    if ((targetSquare & Board.FILE_A) != 0) break; // Break if the file A is reached
                }

                // Calculate legal diagonal moves (up-right)
                for (int j = i - 7; j >= 0 && j % 8 != 0; j -= 7) {
                    long targetSquare = 1L << j;
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & blackOcc) != 0) moveList.add(targetSquare); // Capture if black piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                    if ((targetSquare & Board.FILE_H) != 0) break; // Break if the file H is reached
                }

                // Calculate legal diagonal moves (down-left)
                for (int j = i + 7; j < 64 && j % 8 != 7; j += 7) {
                    long targetSquare = 1L << j;
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & blackOcc) != 0) moveList.add(targetSquare); // Capture if black piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                    if ((targetSquare & Board.FILE_A) != 0) break; // Break if the file A is reached
                }

                // Calculate legal diagonal moves (down-right)
                for (int j = i + 9; j < 64 && j % 8 != 0; j += 9) {
                    long targetSquare = 1L << j;
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & blackOcc) != 0) moveList.add(targetSquare); // Capture if black piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                    if ((targetSquare & Board.FILE_H) != 0) break; // Break if the file H is reached
                }

                tuple.setSecond(moveList); // Add moveList to individual piece's tuple
                finalMoves.add(tuple); // Add tuple of individual piece to the list
            }
        }
        return finalMoves;
    }

    // Define the function for calculating legal moves for a rook
    // Define the function for calculating legal moves for a rook
    public List<Tuple<Long, List<Long>>> whiteRookMove(Long rooks, Long whiteOcc, Long blackOcc) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

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
                    long targetSquare = 1L << j;
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & blackOcc) != 0) moveList.add(targetSquare); // Capture if black piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                }

                // Calculate legal horizontal moves (right)
                for (int j = i + 1; j / 8 == i / 8; j++) {
                    long targetSquare = 1L << j;
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & blackOcc) != 0) moveList.add(targetSquare); // Capture if black piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                }

                // Calculate legal vertical moves (up)
                for (int j = i - 8; j >= 0; j -= 8) {
                    long targetSquare = 1L << j;
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & blackOcc) != 0) moveList.add(targetSquare); // Capture if black piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                }

                // Calculate legal vertical moves (down)
                for (int j = i + 8; j < 64; j += 8) {
                    long targetSquare = 1L << j;
                    if ((targetSquare & whiteOcc) != 0 || (targetSquare & blackOcc) != 0) {
                        if ((targetSquare & blackOcc) != 0) moveList.add(targetSquare); // Capture if black piece is present
                        break;
                    }
                    moveList.add(targetSquare);
                }

                tuple.setSecond(moveList); // Add moveList to individual piece's tuple
                finalMoves.add(tuple); // Add tuple of individual piece to the list
            }
        }
        return finalMoves;
    }

    public long blackRookMove(long rookBoard, long blackOcc) {return 0L;}


    // Define the function for calculating legal moves for a queen
    public List<Tuple<Long, List<Long>>> whiteQueenMove(Long queenBoard, Long whiteOcc, Long blackOcc) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();

        // Combine legal moves of rooks and bishops for white queen
        List<Tuple<Long, List<Long>>> rookMoves = whiteRookMove(queenBoard, whiteOcc, blackOcc);
        List<Tuple<Long, List<Long>>> bishopMoves = whiteBishopMove(queenBoard, whiteOcc, blackOcc);

        // Add rook moves to the final moves list
        finalMoves.addAll(rookMoves);
        // Add bishop moves to the final moves list
        finalMoves.addAll(bishopMoves);

        return finalMoves;
    }

    public List<Tuple<Long, List<Long>>> blackQueenMove(Long queenBoard, Long whiteOcc, Long blackOcc) {
        List<Tuple<Long, List<Long>>> finalMoves = new ArrayList<>();
        return finalMoves;
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

    public Board doMove(Board currentBoard, Tuple tuple){
        //this inputs the bitboard of the piece that is being moved and removes the starting piece
        Long start = (Long) tuple.getStart();

        Long endMove = (Long) tuple.getMoves();

//CLEAR END SQUARE FIRST
        //find type of piece on end square to capture
        if((currentBoard.whitePawnBoard & endMove) != 0){
            currentBoard.whitePawnBoard = currentBoard.whitePawnBoard & ~endMove; //remove captured piece
        }
        else if((currentBoard.blackPawnBoard & endMove) != 0){
            currentBoard.blackPawnBoard = currentBoard.blackPawnBoard & ~endMove;
        }
        else if((currentBoard.whiteQueenBoard & endMove) != 0){
            currentBoard.whiteQueenBoard = currentBoard.whiteQueenBoard & ~endMove;
        }
        else if((currentBoard.blackQueenBoard & endMove) != 0){
            currentBoard.blackQueenBoard = currentBoard.blackQueenBoard & ~endMove;
        }
        else if((currentBoard.whiteKnightBoard & endMove) != 0){
            currentBoard.whiteKnightBoard = currentBoard.whiteKnightBoard & ~endMove;
        }
        else if((currentBoard.blackKnightBoard & endMove) != 0){
            currentBoard.blackKnightBoard = currentBoard.blackKnightBoard & ~endMove;
        }
        else if((currentBoard.whiteRookBoard & endMove) != 0){
            currentBoard.whiteRookBoard = currentBoard.whiteRookBoard & ~endMove;
        }
        else if((currentBoard.blackRookBoard & endMove) != 0){
            currentBoard.blackRookBoard = currentBoard.blackRookBoard & ~endMove;
        }
        else if((currentBoard.whiteBishopBoard & endMove) != 0){
            currentBoard.whiteBishopBoard = currentBoard.whiteBishopBoard & ~endMove;
        }
        else if((currentBoard.blackBishopBoard & endMove) != 0){
            currentBoard.blackBishopBoard = currentBoard.blackBishopBoard & ~endMove;
        }
        else if((currentBoard.whiteKingBoard & endMove) != 0){
            currentBoard.whiteKingBoard = currentBoard.whiteKingBoard & ~endMove;
        }
        else if((currentBoard.blackKingBoard & endMove) != 0){
            currentBoard.blackKingBoard = currentBoard.blackKingBoard & ~endMove;
        }


//CLEAR START SQUARE, POPULATE END SQUARE
// CASES TO REMOVE START PIECE AND ADD END PIECE TO CORRECT BOARD.
        if((currentBoard.whitePawnBoard & start) != 0){
            currentBoard.whitePawnBoard = currentBoard.whitePawnBoard & ~start;   //REMOVES THE STARTING SQUARE PIECE
            currentBoard.whitePawnBoard |= endMove;                               //ADDS ENDMOVE TO CORRECT BITBOARD
        }
        else if((currentBoard.blackPawnBoard & start) != 0){
            currentBoard.blackPawnBoard = currentBoard.blackPawnBoard & ~start;
            currentBoard.blackPawnBoard |= endMove;
        }
        else if((currentBoard.whiteKnightBoard & start) != 0){
            currentBoard.whiteKnightBoard = currentBoard.whiteKnightBoard & ~start;
            currentBoard.whiteKnightBoard |= endMove;
        }
        else if((currentBoard.blackKnightBoard & start) != 0){
            currentBoard.blackKnightBoard = currentBoard.blackKnightBoard & ~start;
            currentBoard.blackKnightBoard |= endMove;
        }
        else if((currentBoard.whiteRookBoard & start) != 0){
            currentBoard.whiteRookBoard = currentBoard.whiteRookBoard & ~start;
            currentBoard.whiteRookBoard |= endMove;
        }
        else if((currentBoard.blackRookBoard & start) != 0){
            currentBoard.blackRookBoard = currentBoard.blackRookBoard & ~start;
            currentBoard.blackRookBoard |= endMove;
        }
        else if((currentBoard.whiteBishopBoard & start) != 0){
            currentBoard.whiteBishopBoard = currentBoard.whiteBishopBoard & ~start;
            currentBoard.whiteBishopBoard |= endMove;
        }
        else if((currentBoard.blackBishopBoard & start) != 0){
            currentBoard.blackBishopBoard = currentBoard.blackBishopBoard & ~start;
            currentBoard.blackBishopBoard |= endMove;
        }
        else if((currentBoard.whiteQueenBoard & start) != 0){
            currentBoard.whiteQueenBoard = currentBoard.whiteQueenBoard & ~start;
            currentBoard.whiteQueenBoard |= endMove;
        }
        else if((currentBoard.blackQueenBoard & start) != 0){
            currentBoard.blackQueenBoard = currentBoard.blackQueenBoard & ~start;
            currentBoard.blackQueenBoard |= endMove;
        }
        else if((currentBoard.whiteKingBoard & start) != 0){
            currentBoard.whiteKingBoard = currentBoard.whiteKingBoard & ~start;
            currentBoard.whiteKingBoard |= endMove;
        }
        else if((currentBoard.blackKingBoard & start) != 0){
            currentBoard.blackKingBoard = currentBoard.blackKingBoard & ~start;
            currentBoard.blackKingBoard |= endMove;
        }
        return currentBoard;
    }
}

//Later on: if we want to speed up move generation functions, make king and knight lookup instead of calculation


//use magic bitboard for sliding pieces like rook and bishop
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Board chessBoard = new Board();

        chessBoard.stringToBitBoard();  //make bitboards out of board string

        Move move = new Move();
        Search search = new Search();


//Generate Random Move
        //chessBoard = move.randomMove(chessBoard);

//MINIMAX
        Tuple<Long, Long> bestMove = search.findBestMoveForWhite(chessBoard,4);
        chessBoard = move.doMove(chessBoard, bestMove);

        //Print Board
        System.out.println(chessBoard);
    }
}

//Notes

//DeBruijn sequences
//used to find the board position of a bit
//MSB-most significant bit LSB- least significant bit

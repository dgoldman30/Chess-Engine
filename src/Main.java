import java.util.List;

public class Main {
    public static void main(String[] args) {

        Board chessBoard = new Board();

        chessBoard.stringToBitBoard();  //make bitboards out of board string

        Move move = new Move();
        Search search = new Search();


//Generate random white move THIS RANDOM GENERATOR STILL WORKS
        //chessBoard = move.randomMove(chessBoard);

//ATTEMPTED SEARCH
        Tuple<Long, Long> bestMove = search.findBestMoveForWhite(chessBoard,5);
        chessBoard = move.doMove(chessBoard, bestMove);

        //Print Board
        System.out.println(chessBoard);
    }
}

//Notes

//TRAVERSING THE GAME TREE
//do depth first search

//DeBruijn sequences
//used to find the board position of a bit
//MSB-most significant bit LSB- least significant bit

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Board chessBoard = new Board();

        chessBoard.stringToBitBoard();  //make bitboards out of board string

        Move move = new Move();


//Generate random white move
        List<Tuple<Long, List<Long>>> moveList = move.generateWhiteMoves(chessBoard);     //generate all moves


        Tuple piece = move.choseMove(moveList); //select Piece and Move for piece

        chessBoard = move.doMove(chessBoard, piece);  //EXECUTES the chosen move for piece


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

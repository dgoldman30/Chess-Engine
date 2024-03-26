import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {

        Board chessBoard = new Board();


        Move move = new Move();

        //UPPERCASE IS WHITE AND STARTS AT THE BOTTOM OF THIS STRING AND MOVES UP
        //Starting board
        final String regBoard =
                        "rnbkqbnr" +
                        "pppppppp" +
                        "--------" +
                        "--------" +
                        "--------" +
                        "--------" +
                        "PPPPPPPP" +
                        "RNBKQBNR";
        final String activeBoard =
                        "--------" +
                        "--------" +
                        "--------" +
                        "---q----" +
                        "---P----" +
                        "--------" +
                        "--------" +
                        "--------";
       //On this board, the pawn should take the knight instead of the queen, saving its own queen
    final String testBoard =
                    "--------" +
                    "--------" +
                    "--------" +
                    "----n-q-" +
                    "-----P--" +
                    "---Q----" +
                    "--------" +
                    "--------";
        final String emptyBoard =
                "--------" +
                        "--------" +
                        "--------" +
                        "--------" +
                        "--------" +
                        "--------" +
                        "--------" +
                        "--------";

    chessBoard.stringToBitBoard(activeBoard);  //make bitboards out of board string


//Generate Random Move
        //chessBoard = move.randomMove(chessBoard);

        //MiniMax
        miniMax miniMax = new miniMax();

        Instant inst1 = Instant.now();

        miniMax.search(chessBoard, 2, true);
        System.out.println("White move: \n" + chessBoard);
        //Print Board
        /*for (int i = 0; i < 2; i++) {
            miniMax.search(chessBoard, 4, true);
            System.out.println("White move: \n" + chessBoard);
            chessBoard = move.randomBlackMove(chessBoard);
            System.out.println("Black move: \n" + chessBoard);
        }*/
        Instant inst2 = Instant.now();
        System.out.println("Elapsed Time: " + Duration.between(inst1, inst2).toString());
    }
}

//Notes

//DeBruijn sequences
//used to find the board position of a bit
//MSB-most significant bit LSB- least significant bit

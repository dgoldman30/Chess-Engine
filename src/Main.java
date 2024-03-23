import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {

        Board chessBoard = new Board();


        //Move move = new Move(); unused ATM
        //Search search = new Search();

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
                        "r-bkqb--" +
                        "pppppppp" +
                        "--------" +
                        "--------" +
                        "--------" +
                        "---q-n--" +
                        "PPPPPPPP" +
                        "RNBKQBNR";
       //EMPTY BOARD
    final String emptyBoard =
                    "--------" +
                    "--------" +
                    "--------" +
                    "--------" +
                    "--------" +
                    "--------" +
                    "--------" +
                    "--------";

    chessBoard.stringToBitBoard(regBoard);  //make bitboards out of board string


//Generate Random Move
        //chessBoard = move.randomMove(chessBoard);

        //MiniMax
        miniMax miniMax = new miniMax();
        Instant inst1 = Instant.now();
        //Print Board
        for (int i = 0; i < 5; i++) {
            miniMax.search(chessBoard, 6, true);
            System.out.println("Black move: \n" + chessBoard);
            miniMax.search(chessBoard,6, false);
            System.out.println("White move: \n" + chessBoard);
        }
        Instant inst2 = Instant.now();
        System.out.println("Elapsed Time: " + Duration.between(inst1, inst2).toString());
    }
}

//Notes

//DeBruijn sequences
//used to find the board position of a bit
//MSB-most significant bit LSB- least significant bit

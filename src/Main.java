import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {

        //initialization
        Board chessBoard = new Board();
        Move move = new Move();
        miniMax miniMax = new miniMax();


        //UPPERCASE IS WHITE AND STARTS AT THE BOTTOM OF THIS STRING AND MOVES UP

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
            //   H G F E D C B A
                    "--------" + // 8
                    "--------" + // 7
                    "--------" + // 6
                    "--------" + // 5
                    "--------" + // 4
                    "--------" + // 3
                    "--------" + // 2
                    "B-------"; // 1
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


        Instant inst1 = Instant.now();                          //start tracking time
//        miniMax.computeMove(chessBoard, 6, true);
//        System.out.println("White move: \n" + chessBoard);

        //i = number of turns (i < 1 = one move for white and black)
        for (int i = 0; i < 20; i++) {
            miniMax.computeMove(chessBoard, 2, true);
            System.out.println("White move: \n" + chessBoard);
            miniMax.computeMove(chessBoard, 4, false);
            System.out.println("Black move: \n" + chessBoard);
        }
        Instant inst2 = Instant.now();                          //end tracking time
        System.out.println("Elapsed Time: " + Duration.between(inst1, inst2).toString());//print time
    }
}


//bitboard popCount() counts the number of pieces on each bitboard.
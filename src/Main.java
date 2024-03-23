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
        miniMax.search(chessBoard, 4, true);

        //Print Board
        System.out.println(chessBoard);
    }
}

//Notes

//DeBruijn sequences
//used to find the board position of a bit
//MSB-most significant bit LSB- least significant bit

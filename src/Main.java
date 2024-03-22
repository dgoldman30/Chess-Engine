public class Main {
    public static void main(String[] args) {

        Board chessBoard = new Board();

        chessBoard.stringToBitBoard();  //make bitboards out of board string

        Move move = new Move();
        Search search = new Search();


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

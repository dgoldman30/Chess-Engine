import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Evaluation {

//Value of pieces
int p = 1;
int n = 3;
int b = 3;
int r = 5;
int q = 9;

    Random randomGenerator = new Random(); //for random move REMOVE LATER LOL


public int evaluate(Long pawns, Long knights, Long rooks, Long bishops, Long queens){

    return (Long.bitCount(pawns) * p) + ((Long.bitCount(knights) * n) + (Long.bitCount(rooks) * r) +
            (Long.bitCount(bishops) * b) + (Long.bitCount(queens) * q));
}

public Tuple choseMove(List<Tuple<Long, List<Long>>> moveList){

    Tuple piece = moveList.get(randomGenerator.nextInt(moveList.size()));  //picks random piece

    List<Long> moves = (List<Long>) piece.getMoves();  //get list of moves

    Long endMove = moves.get(randomGenerator.nextInt(moves.size()));   //choses which move in the list of moves for the given piece to execute, will have to edit later just for testing logic in move generate function

    piece.setSecond(endMove);
    return piece;
}

}

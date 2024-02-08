public class Evaluation {

//Value of pieces
int p = 1;
int n = 3;
int b = 3;
int r = 5;
int q = 9;


public int evaluate(Long pawns, Long knights, Long rooks, Long bishops, Long queens){

    return (Long.bitCount(pawns) * p) + ((Long.bitCount(knights) * n) + (Long.bitCount(rooks) * r) +
            (Long.bitCount(bishops) * b) + (Long.bitCount(queens) * q));
}

}

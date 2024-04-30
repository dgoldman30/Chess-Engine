import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class test {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Board chessBoard = new Board();
        Move move = new Move();

        final String regBoard = "rkbqbnr-" +
                "ppp-ppp-" +
                "n---p---" +
                "--------" +
                "---P----" +
                "---N----" +
                "PPPP-PPP" +
                "RKBQ-BNR";

        chessBoard.stringToBitBoard(regBoard);

        long startTime = System.nanoTime();
        List<Tuple<Long, List<Long>>> movesSequential = move.testgenerateWhiteMoves(chessBoard);
        long endTime = System.nanoTime();
        long durationSequential = (endTime - startTime);

        startTime = System.nanoTime();
        List<Tuple<Long, List<Long>>> movesParallel = move.generateWhiteMoves(chessBoard);
        endTime = System.nanoTime();
        long durationParallel = (endTime - startTime);

        System.out.println("Sequential Move Generation Time: " + durationSequential + " ns");
        System.out.println("Parallel Move Generation Time: " + durationParallel + " ns");

        // Check if both move lists are identical
        boolean identical = movesSequential.equals(movesParallel);
        System.out.println("Moves generated are identical: " + identical);

    }
}

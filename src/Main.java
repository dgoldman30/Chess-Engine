import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // initialization
        Board chessBoard = new Board();
        Move move = new Move();
        miniMax miniMax = new miniMax();

        // UPPERCASE IS WHITE AND STARTS AT THE BOTTOM OF THIS STRING AND MOVES UP

        final String regBoard = "rnbkqbnr" +
                "pppppppp" +
                "--------" +
                "--------" +
                "--------" +
                "--------" +
                "PPPPPPPP" +
                "RNBKQBNR";
        final String activeBoard = "------p-" +
                "--------" +
                "---p--P-" +
                "-p-P-p--" +
                "-P-P-p--" +
                "---P-P--" +
                "--------" +
                "--------";
        // On this board, the pawn should take the knight instead of the queen, saving
        // its own queen
        final String testBoard =
                // H G F E D C B A
                "--------" + // 8
                        "--------" + // 7
                        "--------" + // 6
                        "--------" + // 5
                        "--------" + // 4
                        "----k---" + // 3
                        "--------" + // 2
                        "----Q---"; // 1
        final String emptyBoard = "--------" +
                "--------" +
                "--------" +
                "--------" +
                "--------" +
                "--------" +
                "--------" +
                "--------";

        Instant inst1 = Instant.now(); // start tracking time

        // INDIVIDUAL MOVE
        // miniMax.computeMove(chessBoard, 6, true);
        // System.out.println("White move: \n" + chessBoard);

        // INDIVIDUAL MOVE
        // miniMax.computeMove(chessBoard, 6, true);
        // Tuple<Long, Long> whiteMove = miniMax.computeMove(chessBoard, 6, true);
        // move.doMove(chessBoard, whiteMove);
        // System.out.println("White move: \n" + chessBoard);

        // MULTIPLE MOVE
        int moves = 0;

        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Enter the amount of moves you would like to see: ");
            try {
                moves = scanner.nextInt();
                validInput = true; // Set to true if input is valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine(); // Clear the invalid input from scanner
            }
        }
        validInput = false;
        String inputStr = " ";
        while (!validInput) {
            System.out.println("Enter opponent(black) strength: \n(R: Random, W: Weak, S: Strong)");
            try {
                inputStr = scanner.next();
                if (inputStr.equals("R") || inputStr.equals("W") || inputStr.equals("S")) {
                    validInput = true; // Set to true if input is valid
                } else {
                    System.out.println("Invalid input. Please try again.");
                    scanner.nextLine(); // Clear the invalid input from scanner
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine(); // Clear the invalid input from scanner
            }
        }

        int whiteDepth = 4;
        int blackDepth = 0;
        boolean blackRand = false;
        if (inputStr.equals("W")) {
            blackDepth = 2;
        } else if (inputStr.equals("S")) {
            blackDepth = 4;
        } else {
            blackRand = true;
        }

        for (int i = 0; i < moves; i++) {

            try {

                Tuple<Long, Long> whiteMove = miniMax.computeMove(chessBoard, whiteDepth, true);
                // Tuple<Long, Long> whiteMove = move.randomWhiteMove(chessBoard);

                move.doMove(chessBoard, whiteMove, true);
                System.out.println("White move:\n" + chessBoard);

                if (chessBoard.isStalemate()) {
                    System.out.println("Stalemate!");
                    break;
                } else if (chessBoard.threeFoldRepetition) {
                    System.out.println("Threefold Repetition!");
                    break;
                } else if (chessBoard.insufficientPiece) {
                    System.out.println("Insufficient Piece!");
                    break;
                }

                Tuple<Long, Long> blackMove;
                if (!blackRand) {
                    blackMove = miniMax.computeMove(chessBoard, blackDepth, false);
                } else {
                    blackMove = move.randomBlackMove(chessBoard);
                }

                move.doMove(chessBoard, blackMove, false);
                System.out.println("Black move:\n" + chessBoard);

                if (chessBoard.isStalemate()) {
                    System.out.println("Stalemate!");
                    break;
                } else if (chessBoard.threeFoldRepetition) {
                    System.out.println("Threefold Repetition!");
                    break;
                } else if (chessBoard.insufficientPiece) {
                    System.out.println("Insufficient Piece!");
                    break;
                }

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            // i = number of turns (i < 1 = one move for white and black)
            // for (int i = 0; i < 20; i++) {
            // Tuple<Long, Long> whiteMove = miniMax.computeMove(chessBoard, 4, true);
            // move.doMove(chessBoard, whiteMove);
            // System.out.println("White move:\n" + chessBoard);
            // Tuple<Long,Long> blackMove = miniMax.computeMove(chessBoard, 2, false);
            // move.doMove(chessBoard, blackMove);
            // System.out.println("Black move:\n" + chessBoard);
            // }

        }
        Instant inst2 = Instant.now(); // end tracking time
        System.out.println("Elapsed Time: " + Duration.between(inst1, inst2).toString());// print time

    }
}

// bitboard popCount() counts the number of pieces on each bitboard.

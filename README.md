# Chess AI

This is a simple Java-based Chess AI that uses a basic minimax algorithm with alpha-beta pruning (IN PROGRESS) to determine the best move.

## How to Run the Code

1. Ensure you have Java installed on your system.
2. Clone or download this repository to your local machine.
3. Navigate to the directory containing the source code.
4. Compile the Java files:
    ```
    javac *.java
    ```
5. Run the program:
    ```
    java Main
    ```
Alternatively, open the project in IntelliJ and run the project. 
## Board Representation

The board is represented using a bitboard approach. Each piece type (pawn, knight, bishop, rook, queen, king) for both white and black has its own bitboard.

## Interpreting Output

The program outputs the final move for white based on the current board position and the specified search depth.

## Changing Depth of Search

You can change the depth of search by modifying the `depth` parameter in the `findBestMoveForWhite` method in the `Search` class. 

## Tuple Class

The `Tuple` class is used to represent pairs of elements. In this project, it is utilized to store information about moves, where the `Start` type represents the starting position of a move, and the `Moves` type represents the possible moves from that position.

## Main Class

The `Main` class is the entry point of the program. It initializes the chess board, performs a search for the best move using the minimax algorithm and then executes that move.

## Board Class

The `Board` class represents the chessboard and handles operations related to board manipulation and bitboard representation.

## Evaluation Class

The `Evaluation` class calculates the static evaluation score for a given board position. It assigns weights to different types of pieces and computes the overall score based on the pieces present on the board.

```java
public class Evaluation {
    // Evaluation weights for different aspects of the game
    private static final int PAWN_VALUE = 100;
    private static final int KNIGHT_VALUE = 320;
    private static final int BISHOP_VALUE = 330;
    private static final int ROOK_VALUE = 500;
    private static final int QUEEN_VALUE = 900;
    private static final int KING_VALUE = 20000;

    public int evaluateWhite(Board board) {
        // Calculate white score
        // ...
    }

    public int evaluateBlack(Board board) {
        // Calculate black score
        // ...
    }
}
```
### TODO: ✓
- [ ] `undoMove` function to improve efficiency of `Search` class
- [ ] Remove hard-coded components of `Search` class, reuse `min` and `max` functions
- [ ] Alpha-beta pruning
- [ ] Implement more difficult Chess rules (en passant, castling, promotion, etc.)
- [ ] Improve UI (Potentially leave text-based model)

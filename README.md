# Chess AI

This is a simple Java-based Chess AI that uses a basic minimax algorithm with alpha-beta pruning (IN PROGRESS) to determine the best move.

## How to Run the Code

1. Ensure you have Java installed on your system.
2. Clone or download this repository to your local machine.
3. Navigate to the directory containing the source code (`Chess-Engine/src`).
4. Compile the Java files:
    ```
    javac Main.java
    ```
5. Run the program:
    ```
    java Main
    ```
Alternatively, open the project in IntelliJ and run the project.

## Setting up the game

1. input the board state in any of the given string.
   
```  
final String STRING_NAME_HERE =
"rnbkqbnr" +
"pppppppp" +
"--------" +
"--------" +
"--------" +
"--------" +
"PPPPPPPP" +
"RNBKQBNR"; 
```
This is an example of the string for the starting board state of a game.
2. Update stringToBitBoard's input to the string you just made
```
chessBoard.stringToBitBoard(STRING_NAME_HERE);
```
3. When the program is run, the user will be asked to
```
Enter the amount of moves you would like to see:
```
any integer can be entered. A white move and then a black move consists of one move
4.  The program will then ask
```
Enter opponent(black) strength:
(R: Random, W: Weak, S: Strong)
```
To which you must respond with one of the three letters(R,W,S) in uppercase.
Weak is alow depth and Strong is a higher depth.

## Board Representation

The board is represented using a bitboard approach. Each piece type (pawn, knight, bishop, rook, queen, king) for both white and black has its own bitboard. Creating a board state is done in the main class through strings. These strings represent white pieces with uppercase and black pieces with lowercase. Board.stringToBitboard() then turns this string into the bitboards.

## Interpreting Output

The program outputs the best next possible move for the given board state.

## Tuple Class

The `Tuple` class is used to represent pairs of elements. In this project, it is utilized to store information about moves, where the `Start` type represents the starting position of a move, and the `Moves` type represents the possible moves from that position.

## Main Class

The `Main` class is the entry point of the program. It initializes the chess board, performs a search for the best move using the minimax algorithm and then executes that move.

## Board Class

The `Board` class represents the chessboard and handles operations related to board manipulation and bitboard representation.

## Evaluation Class

The `Evaluation` class calculates the evaluation score for a given board position. It assigns weights to different types of pieces and their location on the board. This is done through piece square tables. Evaluation computes the overall score based on the pieces present on the board.

### TODO: ✓
- [ ] Implement more difficult Chess rules (en passant, castling, promotion, etc.)
- [ ] Taper evaluation
- [ ] Opening book or endgame tables
- [ ] Improve UI (Potentially leave text-based model)

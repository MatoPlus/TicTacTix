/**
 *
 * This class provides the logic for a Connect Four game,
 * which allows players to drop checkers into a grid until
 * one achieves four checkers in a straight line.
 *
 * @author Ri Xin Yang
 * @version Febuary 14, 2019
 * 
 */
public class TicTacTix {

    // Array used to represent the 6 (row) x 7 (col) game board.
    private int[][] grid;
    private int filledCells = 0;

    // The player whose turn it is (1 or 2).
    private int currentPlayer;

    // Create and initialize the grid for our game.
    public TicTacTix() {
        // create the 3 row x 3 col grid of integers (0 default)
        grid = new int[3][3];
        // set the first move to Player 1
        currentPlayer = 1;
    }

    // Drop a checker into the specified column, and 
    // return the row that the checker lands on.
    // (invalid move returns -1)
    public int move(int row, int column) {

        // Exit if someone has already won
        if (hasWon()){
            return -1;
        }

        // check range of row
        if (row < 0 || row > 2) {
            return -1;
        }

        // check range of column
        if (column  < 0 || column > 2) {
            return -1;
        }

        if (grid[row][column] != 0) {
            return -1;
        }

        // Fill the row and column with a checker. record move
        grid[row][column] = currentPlayer;
        filledCells++;

        // Alternate the players
        currentPlayer = (currentPlayer%2)+1;
        return row;
    }

    // This method returns true if one of the players has won the game.
    public boolean hasWon() {
        boolean status = false;
        boolean tempCheck = false;
        int tempElement = -1;

        // Check for a horizontal win
        for ( int row=0; row<3; row++ ) {
            if (grid[row][0] != 0) {
                status = true;
                tempElement = grid[row][0];
                for ( int column=1; column<3; column++ ) {
                    if (tempElement != grid[row][column]) {
                        status = false;
                        break;
                    }
                }
            }
            else {
                status = false;
            }
            if (status) {
                break;
            }   
        }

        // Check for a vertical win
        for ( int column=0; column<3; column++ ) {
            if (grid[0][column] != 0) {
                status = true;
                tempElement = grid[0][column];
                for ( int row=1; row<3; row++ ) {
                    if (tempElement != grid[row][column]) {
                        status = false;
                        break;
                    }
                }
            }
            else {
                status = false;
            }
            if (status) {
                break;
            }   
        }

        //        // Check for a diagonal win (negative slope)
        //        for ( int row=0; row<3; row++ ) {
        //            for ( int column=0; column<3; column++ ) {
        //                if (grid[row][column] != 0 &&
        //                    grid[row][column] == grid[row+1][column+1] &&
        //                    grid[row][column] == grid[row+2][column+2]) {
        //                    status = true;
        //                }
        //            }
        //        }
        //        
        //        // Check for a diagonal win (positive slope)
        //        for ( int row=5; row>3; row-- ) {
        //            for ( int column=0; column<4; column++ ) {
        //                if (grid[row][column] != 0 &&
        //                    grid[row][column] == grid[row-1][column+1] &&
        //                    grid[row][column] == grid[row-2][column+2]) {
        //                    status = true;
        //                }
        //            }
        //        }

        return status;
    }

    // Returns a String representation of the Connect Four game board.
    public String toString() {

        // Print heading
        if (filledCells == 0) {
            System.out.println("\n    ======TIC-TAC-TOE======");
        } 
        else if (currentPlayer == 1) {
            System.out.println("\n    =====PLAYER'S MOVE=====");
        }
        else if (currentPlayer == 2) {
            System.out.println("\n    ===COMPUTER'S MOVE===");
        }

        String returnString = "\n\t   0   1   2\n";

        for ( int row=0; row<3; row++ ) {
            returnString = returnString + "\t" + row + ": "; 

            for ( int column=0; column<3; column++ ) {

                if (column == 0 || column == 2) {
                    returnString = returnString +  grid[row][column];  
                }
                else {
                    returnString = returnString + " | " + grid[row][column] + " | ";  
                } 
            }
            if (row != 2) {
                returnString = returnString + "\n\t  ---+---+---\n";
            }
            else {
                returnString = returnString + "\n";
            }
        }
        return returnString;
    }
}

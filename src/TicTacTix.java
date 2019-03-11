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

    // Array used to represent the (layer)*(row)*(col) game board.
    private int[][][] grid;
    private int filledCells;
    private int dimensions;

    // The player whose turn it is (1 or 2).
    private int currentPlayer;

    // Create and initialize the grid for our game.
    public TicTacTix(int dimensions, boolean isFirst) {
       
        // Initialize instance variables
        this.dimensions = dimensions;
        filledCells = 0;
        // create the 3 row x 3 col grid of integers (0 default)
        grid = new int[dimensions][dimensions][dimensions];

        // set the first move to corresponding player depending on 'first' argument.
        if (isFirst) {
            currentPlayer = 1;
        }
        else {
            currentPlayer = 2;
        }
    }

    // Drop a checker into the specified column, and 
    // return the row that the checker lands on.
    // (invalid move returns -1)
    public int move(int layer, int row, int column) {

        // Tweak row and column to make data useable for arrays (index start at 0).
        layer-=1;
        row-=1;
        column-=1;

        // Exit if someone has already won
        if (hasWon()){
            return -1;
        }

        // Check range of layer
        if (layer < 0 || layer > 2) {
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

        if (grid[layer][row][column] != 0) {
            return -1;
        }

        // Fill the row and column with a checker. record move
        grid[layer][row][column] = currentPlayer;
        filledCells++;

        // Alternate the players
        currentPlayer = (currentPlayer%2)+1;
        return row;
    }

    // This method returns true if one of the players has won the game.
    public boolean hasWon() {
        boolean status = false;
        int tempElement = -1;

         //Check for a horizontal win
        //for ( int row=0; row<3; row++ ) {
            //if (grid[row][0] != 0) {
                //status = true;
                //tempElement = grid[row][0];
                //for ( int column=1; column<3; column++ ) {
                    //if (tempElement != grid[row][column]) {
                        //status = false;
                        //break;
                    //}
                //}
            //}
            //else {
                //status = false;
            //}
            //if (status) {
                //break;
            //}   
        //}

         //Check for a vertical win
        //for ( int column=0; column<3; column++ ) {
            //if (grid[0][column] != 0) {
                //status = true;
                //tempElement = grid[0][column];
                //for ( int row=1; row<3; row++ ) {
                    //if (tempElement != grid[row][column]) {
                        //status = false;
                        //break;
                    //}
                //}
            //}
            //else {
                //status = false;
            //}
            //if (status) {
                //break;
            //}   
        //}

         //Check for a diagonal win (negative slope)
        //if (grid[0][0] != 0) {
            //tempElement = grid[0][0];
            //status = true;
            //for ( int slope=0; slope<3; slope++ ) {
                //if (tempElement != grid[slope][slope]) {
                    //status = false;
                    //break;
                //}
            //}

        //}


         //Check for a diagonal win (negative slope)
        //if (grid[0][2] != 0) {
            //tempElement = grid[0][2];
            //status = true;
            //for ( int slope=0; slope<3; slope++ ) {
                //if (tempElement != grid[slope][2-slope]) {
                    //status = false;
                    //break;
                //}
            //}

        //}

        return status;
    }

    // Returns a String representation of the Connect Four game board.
    public String toString() {
        
        // Save output
        String returnString = "\n\t\t";

        // Save heading

        if (filledCells == 0) {
            returnString += ("======TIC-TAC-TOE======\n");
        } 
        else if (currentPlayer == 1) {
            returnString += ("=====PLAYER'S MOVE=====\n");
        }
        else if (currentPlayer == 2) {
            returnString += ("===COMPUTER'S MOVE===\n");
        }

        for (int layer = 0; layer < dimensions; layer++) {
            returnString += "\t";
            for (int column = 0; column < dimensions; column++) {
                returnString += ("   "+(column+1));
            }
        }
        returnString += "\n";
        
        for ( int row = 0; row < dimensions; row++) {
            for ( int layer=0; layer< dimensions; layer++ ) {
                returnString += "\t" + (row+1) + ": "; 
                for ( int column=0; column< dimensions; column++ ) {
                    if (column == 0 || column == 2) {
                        returnString += grid[layer][row][column];  
                    }
                    else {
                        returnString += " | " + grid[layer][row][column] + " | ";  
                    } 
                }
            }
            returnString += "\n";
            if (row != 2) {
                for (int layer = 0; layer < dimensions; layer++) { 
                    returnString += "\t  ---+---+---";
                }
                returnString += "\n";
            }

        }
        return returnString;
    }
}

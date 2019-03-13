import java.util.Random;

/**
 *
 * This class provides the core logic/template for a TicTacTix game. This class provides the logic for checking
 * win conditions and the general control flow of the game.
 *
 * @author Ri Xin Yang
 * @version March 12, 2019
 * 
 */
public class TicTacTix {

    // Declaration of instance variables
    private int[][][] grids;
    private int filledCells;
    private int maxCells;
    private int dimensions;
    private int winner;
    private final int MAX_LAYER;
    private final int MAX_ROW;
    private final int MAX_COLUMN;
    // The player whose turn it is (1 or 2).
    private int currentPlayer;

    // Declaration of static variables.
    private final static int TIE = 0;
    private final static int PLAYER = 1;
    private final static int COMPUTER = 2;
    

    /**
     * 
     * Parameterized constructor of the TicTacTix class. Creates an appropriate game setup depending on dimensions 
     * and the data on which player gets to go first.
     *
     * @param dimensions - (int) The dimensions of the even grid. Layer, row, and column will be determined by this.
     * @param isFirst - (boolean) Determines if the user gets to go first in the game.
     *
     * @see reserveMiddle()
     *
     */
    public TicTacTix(int dimensions, boolean isFirst) {
       
        // Initialize the instance variables
        this.dimensions = dimensions;
        maxCells = dimensions * dimensions * dimensions;
        winner = -1;
        MAX_LAYER = dimensions;
        MAX_ROW = dimensions;
        MAX_COLUMN = dimensions;

        // Create the n layer x n row x n col grids of integers... (dimension default is 3).
        grids = new int[MAX_LAYER][MAX_ROW][MAX_COLUMN];
        
        // Set up the reserved cell.
        reserveMiddle();
        filledCells = 1;
        
        // set the first move to corresponding player depending on 'first' argument.
        if (isFirst) {
            currentPlayer = PLAYER;
        }
        else {
            currentPlayer = COMPUTER;
        }

    }

    /**
     * 
     * A helper method that that determines the point of the grid to be reserved at the start of a TicTacTix game.
     * The game grid will be modified so that the centre cell of the centre layer will be reserved. This makes the
     * game more challenging.
     *
     */
    private void reserveMiddle() {
        
        // Determine coordinates for reserved cell.
        int midLayer = MAX_LAYER/2;
        int midRow = MAX_ROW/2;
        int midColumn = MAX_COLUMN/2;

        // Reserve the appropriate coordinate.
        grids[midLayer][midRow][midColumn] = -1;
    }

    /**
     * 
     * A method that randomly generates a valid move that the computer can make. This valid move will be returned as 
     * an integer array consisting of three elements. The first will indicate the layer, the second for the row, and
     * the third indicating the column. 
     *
     * Note: Since the move is randomly generated and checked without logging, it may take a while for the generator
     * to get a valid position on the game board. This is especially evident when there are 1-2 remaining cells on 
     * the grid.
     *
     * @return int[] - The coordinates of a valid move that the computer may make. They order from layer, row, and 
     * column.
     *
     * @see Random
     * @see move()
     *
     */
    public int[] getComputerMove() {
        
        // Create and initialize variables/objects required.
        Random generator = new Random();
        int[] coordinates = new int[3];
        int computerLayer = -1;
        int computerRow = -1;
        int computerColumn = -1;
            
        // Generate a coordinate on the grid. Repeat until the coordinates are valid.
        do {
            computerLayer = generator.nextInt(MAX_LAYER) + 1;
            computerRow = generator.nextInt(MAX_ROW) + 1;
            computerColumn = generator.nextInt(MAX_COLUMN) + 1;
        } while(!move(computerLayer, computerRow, computerColumn));

        // Store valid coordinates onto a single int array.
        coordinates[0] = computerLayer;
        coordinates[1] = computerRow;
        coordinates[2] = computerColumn;

        // Return array of coordinates.
        return coordinates;
    }

    /**
     * 
     * A method that reads the coordinates of called move. See if that move is valid on the grid. If valid, execute
     * the move and return true. Otherwise, return false.
     *
     * @param layer - (int) The passed in layer of the move.
     * @param row - (int) The passed in row of the move.
     * @param column - (int) The passed in column of the move.
     *
     * @return boolean - Returns a status determining if the move is valid or not.
     *
     */
    public boolean move(int layer, int row, int column) {

        // Create and initialize variable required.
        boolean valid = true;

        // Tweak layer, row, and column arguments to make data usable for arrays data structure (index start at 0).
        layer-=1;
        row-=1;
        column-=1;

        // Check range of layer
        if (layer < 0 || layer > MAX_COLUMN-1) {
            valid = false;
        }
        // check range of row
        else if (row < 0 || row > MAX_ROW-1) {
            valid =  false;
        }
        // check range of column
        else if (column  < 0 || column > MAX_COLUMN-1) {
            valid =  false;
        }
        // check if position taken
        else if (grids[layer][row][column] != 0) {
            valid = false;
        }

        // If move is still valid, make the move accordingly.
        if (valid) {
            // Fill the row and column with a checker. Record move.
            grids[layer][row][column] = currentPlayer;
            filledCells++;

            // Alternate the player turn (switch between 1 and 2).
            currentPlayer = (currentPlayer%2)+1;
        }

        // Return validity of move. 
        return valid;
    }
   
    /**
     * 
     * This method checks if the current game is over. This is used to determine when the game loop stops. 
     * Specifically, this method checks the hasWon() method followed by checking if all the cells has been filled out.
     * If game is over, return true, otherwise return false.
     *
     * @return boolean - a status that determines if the game is over or not.
     *
     * @see hasWon()
     *
     */
    public boolean isGameOver() {
       
        // Create and initialize variable required.
        boolean status = false;
        

        // Check win condition first, followed by if all cells are filled out. Depending on the condition, Set
        // the winner appropriately.
        if (hasWon()) {
            status = true;
            // Get previous player (winner)
            winner = (currentPlayer%2)+1;
        }
        else if (filledCells >= maxCells) {
            status = true;
            winner = TIE;
        }
        
        // Returns the status of "game over".
        return status;
    }

    /**
     * 
     * This method checks the win condition and see if there is a winner in the following grid. This method will 
     * check if there is a win by getting all possible 2D grids in the 3D game board. After this, each of the 2D game
     * board will be checked by the method which determines if there is a win in a 2D grid. If there is a win, the 
     * logic will be stopped and a boolean status indicating whether there is a win will be returned.
     *
     * @return boolean - determines whether there is a win in the 3D game board.
     *
     * @see has2DWon()
     *
     */
    public boolean hasWon() {
       
        // Create and initialize variables/object required.
        boolean status = false;
        int[][] tempGrid = new int[MAX_ROW][MAX_COLUMN];

        // Horizontal layer, check each grid from side view, top to bottom.
        for (int layer = 0; layer < MAX_LAYER; layer++) {
            
            // Record current 2D grid.
            for (int row = 0; row < MAX_ROW; row++) {
                for (int column = 0; column < MAX_COLUMN; column++) {
                    tempGrid[row][column] = grids[layer][row][column];       
                }
            }    
            // Check for win of each 2D grid, break loop if win found.
            status = has2DWon(tempGrid);
            if (status) {
                break;
            }
        }
        
        // If there is no win found previously, continue to search.
        if (!status) {

            // Vertical layer, check each grid from side to side (from the top view, up and down).
            for (int row = 0; row < MAX_ROW; row++) {

                // Record current 2D grid.
                for (int layer = 0; layer < MAX_LAYER; layer++) {
                    for (int column = 0; column < MAX_COLUMN; column++) {
                        tempGrid[layer][column] = grids[layer][row][column];       
                    }
                }  
                // Check for win for each 2D grid, break loop if win found.
                status = has2DWon(tempGrid);
                if (status) {
                    break;
                }
            }
        }

        // If there is no win found previously, continue to search.
        if (!status) {

            // Vertical layer, check each grid from side to side (from the top view, left to right).
            for (int column = 0; column < MAX_COLUMN; column++) {

                // Record current 2D gird.
                for (int layer = 0; layer < MAX_LAYER; layer++) {
                    for (int row = 0; row < MAX_ROW; row++) {
                        tempGrid[layer][row] = grids[layer][row][column];       
                    }
                }    
                // Check for win for each 2D grid, break loop if win found.
                status = has2DWon(tempGrid);
                if (status) {
                    break;
                }
            }
        }

        // Return the status of whether there is a win.
        return status;
    }
   
    /**
     * 
     * A helper method that takes a 2D grid, treat it as a normal TicTacToe game and check if there is a win. If there
     * is a win, return true, otherwise return false.
     *
     * @param grid - (int[][]) a 2D grid, treated like a TicTacToe game.
     *
     * @return boolean - whether or not there is a win in this 2D grid.
     *
     * @see hasDiagonalWin()
     * @see hasVerticalWin()
     * @see hasDiagonalWin()
     *
     */
    private boolean has2DWon(int[][] grid) {
       
        // Create and initialize variable.
        boolean status = false;
       
        // Check horizontal wins of 2D grid.
        status = hasHorizontalWin(grid);
        
        // Continue to check vertical wins of 2D grid if necessary.
        if (!status) {
            status = hasVerticalWin(grid);
        }

        // Continue to check diagonal  wins of 2D grid if necessary.
        if (!status) {
            status = hasDiagonalWin(grid);
        }
       
        // Returns status of whether there is a win in the 2D grid.
        return status;
    }

    /**
     * 
     * A helper method that takes a 2D grid, check for any horizontal wins (A row which has the same column, indicated
     * by the symbol of a player move). The status of whether there is a win will be returned.
     *
     * @param grid - (int[][]) a 2D grid, treated like a TicTacToe game.
     *
     * @return boolean - whether or not there is a horizontal win in this 2D grid.
     *
     */
    private boolean hasHorizontalWin(int[][] grid) {
     
        // Create and initialize variables
        boolean status = false;
        int tempElement = -1;

        //Check for a horizontal win (of a row, all column have the same value of a player symbol).
        for ( int row=0; row < MAX_ROW; row++ ) {

            // Check to see if it is a player symbol (not empty or reserved). Record value and continue to check...
            if (grid[row][0] == 1 || grid[row][0] == 2) {

                // Assume there is a horizontal win until confirmed that there isn't.
                status = true;
                tempElement = grid[row][0];

                // Check to see if all columns have that same player symbol.
                for ( int column=1; column < MAX_COLUMN; column++ ) {
                    
                    // Denied horizontal win, break out of loop.
                    if (tempElement != grid[row][column]) {
                        status = false;
                        break;
                    }
                }
            }
            
            // If there is a win, break out of loop.
            if (status) {
                break;
            }   
        }

        // Return status reporting if there is a horizontal win.
        return status;
    }

    /**
     * 
     * A helper method that takes a 2D grid, check for any vertical wins (A row which has the same column, indicated
     * by the symbol of a player move). The status of whether there is a win will be returned.
     *
     * @param grid - (int[][]) a 2D grid, treated like a TicTacToe game.
     *
     * @return boolean - whether or not there is a vertical win in this 2D grid.
     *
     */
    private boolean hasVerticalWin(int[][] grid) {

        // Create and initialize variables required.
        boolean status = false;
        int tempElement = -1;

        //Check for a vertical win (of a column, all rows values are of the same player symbol).
        for ( int column=0; column < MAX_COLUMN; column++ ) {

            // Check to see if it is a player symbol (not empty or reserved). Record value and continue to check...
            if (grid[0][column] == 1 || grid[column][0] == 2) {

                // Assume there is a vertical win until denied.
                status = true;
                tempElement = grid[0][column];
                
                // Continue to check if all row of the column has the same value.
                for ( int row=1; row < MAX_ROW; row++ ) {

                    // Deny vertical win if conflict found.
                    if (tempElement != grid[row][column]) {
                        status = false;
                        break;
                    }
                }
            }

            // Break out of loop if win found.
            if (status) {
                break;
            }   
        }

        // Return the status of whether there is a vertical win.
        return status;
    }

    /**
     * 
     * A helper method that takes a 2D grid, check for any diagonal wins (Each diagonal value is same as indicated
     * by the symbol of a player move). The status of whether there is a win will be returned.
     *
     * @param grid - (int[][]) a 2D grid, treated like a TicTacToe game.
     *
     * @return boolean - whether or not there is a diagonal win in this 2D grid.
     *
     */
    private boolean hasDiagonalWin(int[][] grid) {

        // Create and initialize variables required.
        boolean status = false;
        int tempElement = -1;

        //Check for a diagonal win (negative slope)
        
        // Check if initial condition of a diagonal win (negative slope) is met (player symbol at initial position).
        if (grid[0][0] == 1 || grid[0][0] == 2) {

            // Assume there is a diagonal win until denied.
            tempElement = grid[0][0];
            status = true;

            // Check diagonals with a negative slope.
            for ( int slope=0; slope < dimensions; slope++ ) {

                // Deny diagonal win with a negative slope if there is a conflict.
                if (tempElement != grid[slope][slope]) {
                    status = false;
                    break;
                }
            }
        }

         //Check for a diagonal win (positive slope). Only if negative slope is not a win.
        if (!status) { 

            // Check if initial condition of a diagonal win (positive slope) is met (player symbol found at initial)
            if (grid[0][dimensions-1] == 1 || grid[0][dimensions-1] == 2) {

                // Assume there is a diagonal win (positive slope) until denied. Record key for search.
                tempElement = grid[0][dimensions-1];
                status = true;

                // Check diagonals with positive slope.
                for ( int slope=0; slope < dimensions; slope++ ) {

                    // Deny diagonal win with positive slope if there is a conflict.
                    if (tempElement != grid[slope][(dimensions-1)-slope]) {
                        status = false;
                        break;
                    }
                }
            }
        }
        
        // Return status of whether there is a diagonal win or not.
        return status;
    }
    
    /**
     * 
     * A accessor method that returns the value of "currentPlayer", an instance variable.
     *
     * @return int - currentPlayer, used to determine current player turn.
     *  
     */
    public int getCurrentPlayer() {
        // Return appropriate instance variable.
        return currentPlayer;
    }

    /**
     * 
     * A accessor method that returns the value of "winner", an instance variable.
     *
     * @return int - winner, used to represent the winner of the game.
     *
     */
    public int getWinner() {
        // Return appropriate instance variable.
        return winner;
    }

    /**
     *
     * A helper method that converts int representation of players to appropriate symbol for output in toString()
     *
     * @param key - (int) int representation of player.
     *
     * @return String - converted symbol given the int representation of a player.
     *
     */
    private String convertSymbol(int key) {
       
        // Create and initialize variable required.
        String symbol = null;

        // Symbol for empty cell.
        if (key == 0) {
            symbol = " ";
        }
        // Symbol for player 1 cell.
        else if (key == 1) {
            symbol = "X";
        }
        // Symbol for player 2 cell.
        else if (key == 2) {
            symbol = "O";
        }
        // Symbol for reserved cell.
        else if (key == -1) {
            symbol = "+";
        }
       
        return symbol;
    }
    
    /**
     * 
     * A helper method that returns the appropriate heading depending on the current status of the game for output in
     * toString() method.
     *
     * @return String - appropriate heading/status of the current grid for output.
     *
     */
    private String getHeadingStatus() {
       
        // Create and initialize required variables.
        String placeHolder = null;
        String heading = "";

        // Get appropriate heading if game is over.
        if (winner != -1) {
           
            // Determine place holder name.
            if (winner == 1) {
                placeHolder = "PLAYER";
            }
            else if (winner == 2) {
                placeHolder = "COMPUTER";
            }
            else {
                placeHolder = "NO ONE";
            } 
           
            // get heading for game over.
            heading +=  "\t\t***GAME OVER: " + placeHolder +" WINS***\n\n";
        }

        // Get appropriate heading if game is still in progress, returns current player turn.
        else {

            // Determine status heading depending on if it is initial game startup or a player's turn.
            if (filledCells == 1) {
                heading += ("\t\t     ====TIC-TAC-TOE=====\n\n");
            } 
            else if (currentPlayer == 1) {
                heading += ("\t\t     ====PLAYER'S MOVE====\n\n");
            }
            else if (currentPlayer == 2) {
                heading += ("\t\t     ===COMPUTER'S MOVE===\n\n");
            }
        }

        // Return appropriate heading of game.
        return heading;

    }

    /**
     * 
     * A helper method that gets the cell/game board representation of the game. This is to be used for output in the 
     * toString method.
     *
     * @return String - A visual String representation of current grid, each layer side by side.
     *
     */
    private String getCellStatus() {
   
        // Create and initialize variable required.
        String cellStatus = "";

        // For each layer, push all column headings with appropriate padding.
        for (int layer = 0; layer < MAX_LAYER; layer++) {
            cellStatus += "\t";

            // Push column heading...
            for (int column = 0; column < MAX_COLUMN; column++) {
                cellStatus += ("   "+(column+1));
            }
        }
        cellStatus += "\n";
        
        // Push all rows and columns.
        for ( int row = 0; row < MAX_ROW; row++) {
            
            // Push row heading...
            for ( int layer=0; layer< MAX_LAYER; layer++ ) {
                cellStatus += "\t" + (row+1) + ": "; 
                
                // Push current column element with appropriate padding depending on the current position of column.
                for ( int column=0; column< MAX_COLUMN; column++ ) {
                    if (column == dimensions-1) {
                        cellStatus += convertSymbol(grids[layer][row][column]);
                    }
                    else {
                        cellStatus += convertSymbol(grids[layer][row][column]) + " | ";  
                    } 
                }
            }
            cellStatus += "\n";

            // Push appropriate line padding for grids after each row is printed.
            if (row != dimensions-1) {
                for (int layer = 0; layer < MAX_LAYER; layer++) {
                    cellStatus += "\t  ";
                    // Push ASCII art for dividing rows, different depending on column.
                    for (int column = 0; column < MAX_COLUMN; column++) {
                        if (column == dimensions-1) {
                            cellStatus += "---";
                        }
                        else {
                            cellStatus += "---+";
                        }
                    }
                }
                cellStatus += "\n";
            }
        }

        // Return the final grid representation for visual aid.
        return cellStatus;
    }
    
    /**
     * 
     * A method that is used to print the grid, displaying the game status along with the visual aid of the grid. 
     * This method is to be used to help the user understand the current status of the game.
     *
     * @return String - status with representation of the grid.
     *
     * @see getHeadingStatus()
     * @see getCellStatus()
     *
     */
    public String toString() {
        
        // Initialize output.
        String returnString = "\n";

        // Get current status of game and add it to returnString.
        returnString += getHeadingStatus();
        // Get current status of cell placement and add it to returnString.
        returnString += getCellStatus();
       
        // Return final result of the final representation of the grid.
        return returnString;
    }
}

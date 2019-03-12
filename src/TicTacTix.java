import java.util.Random;

/**
 *
 * This class provides the logic for a Connect Four game,
 * which allows players to drop checkers into a grids until
 * one achieves four checkers in a straight line.
 *
 * @author Ri Xin Yang
 * @version March 12, 2019
 * 
 */
public class TicTacTix {

    // Array used to represent the (layer)*(row)*(col) game board.
    private int[][][] grids;
    private int filledCells;
    private int maxCells;
    private int dimensions;
    private int winner;
    private final static int TIE = 0;
    private final static int PLAYER = 1;
    private final static int COMPUTER = 2;
    private final int MAX_LAYER;
    private final int MAX_ROW;
    private final int MAX_COLUMN;
    
    // The player whose turn it is (1 or 2).
    private int currentPlayer;

    // Create and initialize the grids for our game.
    public TicTacTix(int dimensions, boolean isFirst) {
       
        // Initialize instance variables
        this.dimensions = dimensions;
        maxCells = dimensions * dimensions * dimensions;
        winner = -1;
        MAX_LAYER = dimensions;
        MAX_ROW = dimensions;
        MAX_COLUMN = dimensions;

        // create the n layer x n row x n col grids of integers... (dimension default is 3)
        grids = new int[MAX_LAYER][MAX_ROW][MAX_COLUMN];

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

    // Reserve the middle cell of the middle layer
    private void reserveMiddle() {

        int midLayer = MAX_LAYER/2;
        int midRow = MAX_ROW/2;
        int midColumn = MAX_COLUMN/2;

        grids[midLayer][midRow][midColumn] = -1;
    }

    // Used for get computer move...
    public int[] getComputerMove() {
        
        Random generator = new Random();
        int[] coordinates = new int[3];
        int computerLayer = -1;
        int computerRow = -1;
        int computerColumn = -1;
            
        do {
            computerLayer = generator.nextInt(MAX_LAYER) + 1;
            computerRow = generator.nextInt(MAX_ROW) + 1;
            computerColumn = generator.nextInt(MAX_COLUMN) + 1;
        } while(move(computerLayer, computerRow, computerColumn) == -1);

        coordinates[0] = computerLayer;
        coordinates[1] = computerRow;
        coordinates[2] = computerColumn;

        return coordinates;
    }

    // Drop a checker into the specified column, and 
    // return the row that the checker lands on.
    // (invalid move returns -1)
    public int move(int layer, int row, int column) {

        // Tweak row and column to make data useable for arrays (index start at 0).
        layer-=1;
        row-=1;
        column-=1;

        // Check range of layer
        if (layer < 0 || layer > MAX_COLUMN-1) {
            return -1;
        }
        // check range of row
        if (row < 0 || row > MAX_ROW-1) {
            return -1;
        }
        // check range of column
        if (column  < 0 || column > MAX_COLUMN-1) {
            return -1;
        }
        // check if position taken
        if (grids[layer][row][column] != 0) {
            return -1;
        }

        // Fill the row and column with a checker. record move
        grids[layer][row][column] = currentPlayer;
        filledCells++;

        // Alternate the players
        currentPlayer = (currentPlayer%2)+1;
        return 1;
    }
   
    // Check if game is over by checking if either player has won or if grid is completely filled out.
    public boolean isGameOver() {
        
        boolean status = false;
        
        if (hasWon()) {
            status = true;
            // Get previous player (winner)
            winner = (currentPlayer%2)+1;
        }
        else if (filledCells >= maxCells) {
            status = true;
            winner = TIE;
        }

        return status;
    }

    // This method returns true if one of the players has won the game.
    public boolean hasWon() {
        
        boolean status = false;
        int[][] tempGrid = new int[MAX_ROW][MAX_COLUMN];

        // Horizontal layer check each grid top to bottom
        for (int layer = 0; layer < MAX_LAYER; layer++) {
            
            // Get 2d grid...
            for (int row = 0; row < MAX_ROW; row++) {
                for (int column = 0; column < MAX_COLUMN; column++) {
                    tempGrid[row][column] = grids[layer][row][column];       
                }
            }    
            // Check for win...
            status = has2DWon(tempGrid);
            if (status) {
                break;
            }
        }

        if (!status) {
            // Vertical layer, check each grid from side to side (row change last)
            
            for (int row = 0; row < MAX_ROW; row++) {

                // Get 2d grid...
                for (int layer = 0; layer < MAX_LAYER; layer++) {
                    for (int column = 0; column < MAX_COLUMN; column++) {
                        tempGrid[layer][column] = grids[layer][row][column];       
                    }
                }    
                // Check for win...
                status = has2DWon(tempGrid);
                if (status) {
                    break;
                }
            }
        }

        if (!status) {
            // Vertical layer, check each grid from side to side (column change last)
            
            for (int column = 0; column < MAX_COLUMN; column++) {

                // Get 2d grid...
                for (int layer = 0; layer < MAX_LAYER; layer++) {
                    for (int row = 0; row < MAX_ROW; row++) {
                        tempGrid[layer][row] = grids[layer][row][column];       
                    }
                }    
                // Check for win...
                status = has2DWon(tempGrid);
                if (status) {
                    break;
                }
            }
        }

        return status;
    }
    
    private boolean has2DWon(int[][] grid) {
        
        boolean status = false;
        
        status = hasHorizontalWin(grid);
        
        if (!status) {
            status = hasVerticalWin(grid);
        }
        if (!status) {
            status = hasDiagonalWin(grid);
        }
        
        return status;
    }

    private boolean hasHorizontalWin(int[][] grid) {
        
        boolean status = false;
        int tempElement = -1;

        //Check for a horizontal win
        for ( int row=0; row < MAX_ROW; row++ ) {
            if (grid[row][0] != 0 && grid[row][0] != -1) {
                status = true;
                tempElement = grid[row][0];
                for ( int column=1; column < MAX_COLUMN; column++ ) {
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

        return status;
    }

    private boolean hasVerticalWin(int[][] grid) {

        boolean status = false;
        int tempElement = -1;

        //Check for a vertical win
        for ( int column=0; column < MAX_COLUMN; column++ ) {
            if (grid[0][column] != 0 && grid[column][0] != -1) {
                status = true;
                tempElement = grid[0][column];
                for ( int row=1; row < MAX_ROW; row++ ) {
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

        return status;
    }

    private boolean hasDiagonalWin(int[][] grid) {

        boolean status = false;
        int tempElement = -1;

         //Check for a diagonal win (negative slope)
        if (grid[0][0] != 0 && grid[0][0] != -1) {
            tempElement = grid[0][0];
            status = true;
            for ( int slope=0; slope < dimensions; slope++ ) {
                if (tempElement != grid[slope][slope]) {
                    status = false;
                    break;
                }
            }
        }

         //Check for a diagonal win (positive slope). Only if negative slope is not a win.
        if (!status) { 
            if (grid[0][dimensions-1] != 0 && grid[0][dimensions-1] != -1) {
                tempElement = grid[0][dimensions-1];
                status = true;
                for ( int slope=0; slope < dimensions; slope++ ) {
                    if (tempElement != grid[slope][(dimensions-1)-slope]) {
                        status = false;
                        break;
                    }
                }
            }
        }
        
        return status;
    }
    
    // Get current player to decide logic in main.
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    // Get winner to decide logic in main.
    public int getWinner() {
        return winner;
    }

    // Convert int to approrpiate symbol for output in toString()
    private String convertSymbol(int key) {
        
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

    private String getHeadingStatus() {
        
        String placeHolder = null;
        String heading = "";

        // Push in heading.
        if (winner != -1) {
           
            if (winner == 1) {
                placeHolder = "PLAYER";
            }
            else if (winner == 2) {
                placeHolder = "COMPUTER";
            }
            else {
                placeHolder = "NO ONE";
            } 

            heading +=  "\t\t***GAME OVER: " + placeHolder +" WINS***\n\n";
        }
        else {
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

        return heading;

    }

    private String getCellStatus() {
   
        String cellStatus = "";

        // Print grids.
        for (int layer = 0; layer < MAX_LAYER; layer++) {
            cellStatus += "\t";

            // Print column heading...
            for (int column = 0; column < MAX_COLUMN; column++) {
                cellStatus += ("   "+(column+1));
            }
        }
        cellStatus += "\n";
        
        // Print all rows and columns.
        for ( int row = 0; row < MAX_ROW; row++) {
            
            // Print row heading...
            for ( int layer=0; layer< MAX_LAYER; layer++ ) {
                cellStatus += "\t" + (row+1) + ": "; 
                
                // Print current column element with appropriate padding.
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

            // Print appropriate line padding for grids after each row is printed.
            if (row != dimensions-1) {
                for (int layer = 0; layer < MAX_LAYER; layer++) {
                    cellStatus += "\t  ";
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

        return cellStatus;
    }
    
    // Returns a String representation of the Connect Four game board.
    public String toString() {
        
        // Initialize output.
        String returnString = "\n";

        // Get current status of game and add it to returnString.
        returnString += getHeadingStatus();
        // Get current status of cell placement and add it to returnString.
        returnString += getCellStatus();
        
        return returnString;
    }
}

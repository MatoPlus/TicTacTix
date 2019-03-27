import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * This class provides the user interface logic for a game of TicTacTix,
 * which allows players to choose coordinates in a 3 dimensional grid TicTacToe board.
 * The default dimensions are 3 by 3 by 3... This value can be dynamically changed. Note that this program made for
 * a 3 by 3 by 3 structure. Thus, the program may not function correctly if the dimensions value is changed.
 * 
 * @author Ri Xin Yang
 * @version March 12, 2019
 *
 */
public class TicTacTixTest {
    
    /*
     * This is the main method that executes the program in the appropriate sequence.
     */
    public static void main(String[] args) {
        
        // Create and initialize variables to be used in main. 
        int dimensions = 3;
        int layer = 0;
        int row = 0;
        int column = 0;
        int[] computerMoves = new int[3];
        int recordLineNumber = 0;
        String previousWinner = null;
        String winner = null;
        boolean isFirst  = false;
        final int PLAYER = 1;
        
        // Try to read from "HallOfFame.txt", prints winner or print appropriate prompt if otherwise.
        try {
            
            // Creates scanner to read from file.
            Scanner fileInput = new Scanner(new File("HallOfFame.txt"));
            System.out.println("\t======Wall Of Fame======\n");
            
            // If the file has names in it, print it. Other wise, tell the user that there are no names in the file.
            if (fileInput.hasNext()) {
                
                // List all names in the file with appropriate padding.
                while (fileInput.hasNext()) {
                    
                    previousWinner = fileInput.nextLine().trim();
                    recordLineNumber++;
                    
                    System.out.println("\t    " + recordLineNumber + ": " + previousWinner);
                }
                System.out.print("\n");
            }
            else {
                System.out.println ("\t    No one here yet...\n");
            }
        }
        // File is not found... Assume that there are no winner and inform user.
        catch (IOException exception) {
            
            System.out.println("No Human Has Ever Beat Me.. *laughs in binary*\n");
        }
        
        // Determine if player goes first...
        isFirst = validatedIsFirst();
        
        // Set up game with appropriate parameters...
        TicTacTix game = new TicTacTix(dimensions, isFirst);
        
        // Game logic loop. Keep playing until the game over.
        do {
            
            // Game board output 
            System.out.println(game);
            
            // Let player make move if it is the appropriate turn.
            if (game.getCurrentPlayer() == PLAYER) {
                
                // Prompt and get layer selection.
                layer = validatedLayer(dimensions);
                System.out.print("\n");
                
                // Prompt and get row selection.
                row = validatedRow(dimensions); 
                System.out.print("\n");
                
                // Prompt and get column selection.
                column = validatedColumn(dimensions); 
                
                // Check if selection is valid. If not, inform and prompt to try again.
                if (!game.move(layer, row, column )) {
                    System.out.println( "\nInvalid insert at layer \"" + layer + "\" at row \"" + row +
                                       "\" of column \"" + column + "\"" );
                    
                    System.out.println( "Please Try Again..\n" );
                } 
            }
            else {
                
                // Get and execute computer move and assign values to appropriate variables.
                computerMoves = game.getComputerMove();
                layer = computerMoves[0];
                row = computerMoves[1];
                column = computerMoves[2];
                
                // Execute move made by computer.
                game.move(layer, row, column);
                
                // Inform user of player move.
                System.out.print("Computer picked layer \"" + layer + "\" at row \"" + row +
                                 "\" of column \"" + column + "\"\n" );
            }
            
        } while (!game.isGameOver());
        
        // Final printing of game board after game ended.
        System.out.println( game );
        
        // Record player name in hall of fame if they won.
        if (game.getWinner() == PLAYER) {
            recordHallOfFame();
            
        }
        
        // Game ended, inform user.
        System.out.println("\nThank you for playing TicTacTix!");
    }   
    
    /**
     *
     * This method prompts and receives a valid input from the user. Specifically, this method will prompt the user
     * for a boolean value by receiving y or Y for true, or n or N for false. This method will be used in main to
     * determine whether or not the player should go first. The validated choice will be returned as a boolean value.
     *
     * @return boolean - status of player being first.
     *
     */
    private static boolean validatedIsFirst() {
        
        // Create and initialize variables/object required in this method.
        boolean valid = false;
        boolean isFirst = false;
        String userInput = null; 
        Scanner keyInput = new Scanner(System.in);
        
        // Prompt user for input.
        System.out.print("Would you like to go first? (y/n): ");
        
        // Loop input step as long as the input received is not valid.
        while (!valid) {
            
            // Read user input.
            userInput = keyInput.nextLine();
            
            // Logic to check whether or not input is valid.
            if (userInput.equalsIgnoreCase("y")) {
                
                isFirst = true;
                valid = true;
            }
            else if (userInput.equalsIgnoreCase("n")) {
                
                isFirst = false;
                valid = true;
            }
            else {
                
                System.out.print("Please enter a valid input (y/n): ");
            }
        }
        
        // Return status.
        return isFirst;
    }
    
    /**
     *
     * This method prompts and receives a valid input from the user. Specifically, this method will prompt the user
     * for an integer value of the layer coordinate. This method will be used in main get an appropriate 
     * coordinate value for layer with the validateRange method. The value of the validated layer will be returned as
     * an integer.
     *
     * @param maxLayer - (int) The max number as a valid layer input.
     *
     * @return int - validated integer coordinate for layer.
     * 
     * @see validateRange()
     *
     */
    private static int validatedLayer(int maxLayer) {
        
        // Create and initialize variable required.
        int layer = -1;
        
        // Prompt and get validated input (range from 1 to max layer).
        System.out.print("What layer would you like to pick (1-"+maxLayer+")?: ");
        layer = validateRange(1, maxLayer);
        
        // Return validated layer coordinate.
        return layer;
    }
    
    /**
     *
     * This method prompts and receives a valid input from the user. Specifically, this method will prompt the user
     * for an integer value of the row coordinate. This method will be used in main get an appropriate 
     * coordinate value for row with the validateRange method. The value of the validated row will be returned as
     * an integer.
     *
     * @param maxRow - (int) The max number as a valid row input.
     *
     * @return int - validated integer coordinate for row.
     * 
     * @see validateRange()
     *
     */
    private static int validatedRow(int maxRow) {
        
        // Create and initialize variable required.
        int row = -1;
        
        // Prompt and get validated input (range from 1 to max rows).
        System.out.print("What row would you like to pick (1-"+maxRow+")?: ");
        row = validateRange(1, maxRow);
        
        // Return validated row coordinate.
        return row;
    }
    
    /**
     *
     * This method prompts and receives a valid input from the user. Specifically, this method will prompt the user
     * for an integer value of the column coordinate. This method will be used in main get an appropriate 
     * coordinate value for column with the validateRange method. The value of the validated column will be returned as
     * an integer.
     *
     * @param maxColumn - The max number as a valid column input.
     *
     * @return int - validated integer coordinate for column.
     * 
     * @see validateRange()
     *
     */
    private static int validatedColumn(int maxColumn) {
        
        // Create and initialize variable as required.
        int column = -1;
        
        // Prompt and get validated input (range from 1 to max column).
        System.out.print("What column would you like to pick (1-"+maxColumn+")?: ");
        column = validateRange(1, maxColumn);
        
        // Return validated column coordinate.
        return column;
    }
    
    /**
     *
     * This method handles input validation given a range of values. This method is used to get a valid integer from 
     * the user. This method is specifically used to do the input validation integer inputs such as layer, row, 
     * and column. After acquiring the appropriate input, the validated integer will be returned.
     *
     * @param minNumber - The min number as a valid integer input.
     * @param maxNumber - The max number as a valid integer input.
     *
     * @return int - validated integer given the valid range.
     *
     */
    private static int validateRange(int minNumber, int maxNumber) {
        
        // Create and initialize variables/objects required.
        int input = -1;
        boolean valid = false;
        Scanner keyInput = new Scanner(System.in);
        
        // Input validation loop.
        while (!valid) {
            
            // Tries to get input as integer. If mismatch, clear \n in input stream and continue loop.
            try {
                
                // Get input
                input  = keyInput.nextInt();
                
                // Check for if valid range.
                if (input >= 1 && input <= maxNumber) {
                    valid = true;
                }
                // Re-prompt.
                else {
                    System.out.print("Input out of range: please enter a number between "+minNumber+"-"+maxNumber+": ");
                }
            }
            catch(InputMismatchException exception) {
                
                // Re-prompt and clear input stream.
                System.out.print("Please enter an INTEGER between "+minNumber+"-"+maxNumber+": ");
                keyInput.nextLine();
            }
        }
        
        // Returns validated input.
        return input;
    }
    
    /**
     *
     * This method records the winner's name in the "HallOfFame.txt" to be viewed at the beginning of the game.
     * This method opens the HallOfFame file (creates one if it doesn't exist), and prompt and append winner name
     * on to file.
     * 
     * @see Scanner
     * @see FileWriter
     * @see PrintWriter
     *
     */
    private static void recordHallOfFame() {
        
        // Create and initialize variables/object required.
        String name = null;
        Scanner keyInput = new Scanner(System.in);
        
        // Prompt and get name
        System.out.print("Winner! Please enter your name: ");
        name = keyInput.nextLine();
        
        // Try to open, write winner name, and close "HallOfFame.txt".
        try {
            FileWriter appendFile = new FileWriter("HallOfFame.txt", true);
            PrintWriter fileOutput = new PrintWriter(appendFile);
            
            fileOutput.println(name);
            
            fileOutput.close();
        }
        // Catch IOException error and inform user.
        catch (IOException exception) {
            System.err.println("Java Exception: " + exception);
            System.out.println("Sorry, error occurred when outputting to file \"HallOfFame.txt\"");
        }
    }
    
}

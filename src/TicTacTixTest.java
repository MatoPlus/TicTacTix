import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

// Test class to demonstrate our Connect Four Model
public class TicTacTixTest {
    public static void main(String[] args) {
 
        int dimensions = 3;
        int layer = 0;
        int row = 0;
        int column = 0;
        int[] computerMoves = new int[3];
        int recordLineNumber = 0;
        String previousWinner = null;
        String winner = null;
        boolean isFirst  = false;


        // Print hall of fame if possible...
        try {

            Scanner fileInput = new Scanner(new File("HallOfFame.txt"));

            System.out.println("\t======Wall Of Fame======\n");

            if (fileInput.hasNext()) {

                while (fileInput.hasNext()) {

                previousWinner = fileInput.nextLine().trim();
                recordLineNumber++;

                System.out.println("\t    " + recordLineNumber + ": " + previousWinner);
                }
            }
            else {

                System.out.println ("\t    No one here yet...");
            }
        }
        catch (IOException exception) {
            System.out.println("No Human Has Ever Beat Me.. *laughs in binary*\n");
        }



        // Determine if player goes first...
        isFirst = validatedIsFirst();

        // Set up game, passing in if player should go first...
        TicTacTix game = new TicTacTix(dimensions, isFirst);
      
        do {
            // Game board output 
            System.out.println(game);

            if (game.getCurrentPlayer() == 1) {
                // Prompt and get layer selection
                layer = validatedLayer(dimensions);
                System.out.println();

                // Prompt and get row selection
                row = validatedRow(dimensions); 
                System.out.println();

                // Prompt and get column selection
                column = validatedColumn(dimensions); 

                // Check if selection us valid.
                if ( game.move(layer, row, column ) == -1 ) {
                    System.out.println( "\nInvalid insert at layer \"" + layer + "\" at row \"" + row +
                            "\" of column \"" + column + "\"" );

                    System.out.println( "Please Try Again..\n" );
                } 
            }
            else {
               
                // Get and execute computer move. 
                computerMoves = game.getComputerMove();

                layer = computerMoves[0];
                row = computerMoves[1];
                column = computerMoves[2];
                
                // Execute move
                game.move(layer, row, column);
            
                System.out.print("Computer picked layer \"" + layer + "\" at row \"" + row +
                        "\" of column \"" + column + "\"\n" );
            
            }

        } while (!game.isGameOver());
    
        System.out.println( game );
        
        if (game.getWinner() == 1) {
            recordHallOfFame();
        }

        System.out.println("\nThank you for playing TicTacTix!");

    }   

    public static boolean validatedIsFirst() {

        boolean valid = false;
        boolean isFirst = false;
        String userInput = null; 
        Scanner keyInput = new Scanner(System.in);
        
        System.out.print("Would you like to go first? (y/n): ");

        while (!valid) {
            userInput = keyInput.nextLine();
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

        return isFirst;
    }

    public static int validatedLayer(int maxLayer) {

        int layer = -1;

        System.out.print("What layer would you like to pick (1-"+maxLayer+")?: ");

        layer = validateRange(1, maxLayer);

        return layer;
    }


    public static int validatedRow(int maxRow) {

        int row = -1;

        System.out.print("What row would you like to pick (1-"+maxRow+")?: ");

        row = validateRange(1, maxRow);

        return row;
    }

    public static int validatedColumn(int maxColumn) {

        int column = -1;

        System.out.print("What column would you like to pick (1-"+maxColumn+")?: ");

        column = validateRange(1, maxColumn);

        return column;
    }


    public static int validateRange(int minNumber, int maxNumber) {

        int input = -1;
        boolean valid = false;
        Scanner keyInput = new Scanner(System.in);

        while (!valid) {
            try {
                input  = keyInput.nextInt();

                if (input >= 1 && input <= maxNumber) {
                    valid = true;
                }
                else {
                    System.out.print("Input out of range: please enter a number between "+minNumber+"-"+maxNumber+": ");
                }
            }
            catch(InputMismatchException exception) {
                System.out.print("Please enter an INTEGER between "+minNumber+"-"+maxNumber+": ");
                keyInput.nextLine();
            }
        }
        return input;
    }

    public static void recordHallOfFame() {
        
        String name = null;
        Scanner keyInput = new Scanner(System.in);

        // Prompt and get name
        System.out.print("Winner! Please enter your name: ");
        name = keyInput.nextLine();

        // Try to open, write, and close "HallOfFame.txt".
        try {
            FileWriter appendFile = new FileWriter("HallOfFame.txt", true);
            PrintWriter fileOutput = new PrintWriter(appendFile);
            
            fileOutput.println(name);

            fileOutput.close();
        }
        catch (IOException exception) {
            System.err.println("Java Exception: " + exception);
            System.out.println("Sorry, error occured when outputing to file \"HallOfFame.txt\"");
        }
    }

}

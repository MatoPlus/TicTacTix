import java.util.Scanner;
import java.util.InputMismatchException;
// Test class to demonstrate our Connect Four Model
public class TicTacTixTest {
    public static void main(String[] args) {
        int dimensions = 3;
        int layer = 0;
        int row = 0;
        int column = 0;
        boolean isFirst  = false;

        isFirst = validatedIsFirst();

        TicTacTix game = new TicTacTix(dimensions, isFirst);
      
        do {
            // Game board output 
            System.out.println(game);
            
            // Prompt and get layer selection
            layer = validatedLayer(dimensions);

            System.out.println();

            // Prompt and get row selection
            row = validatedRow(dimensions); 
            
            System.out.println();
            
            // Prompt and get column selection
            column = validatedColumn(dimensions); 
            
            // Check for row and column selection.
            if ( game.move(layer, row, column ) == -1 ) {
                System.out.println( "\nInvalid insert at row \"" + row + "\" at column \"" + column + "\"" );
                System.out.println( "Please Try Again..\n" );
            } 
        } while (!game.hasWon());
    
        System.out.println( game );
        System.out.println( "*** GAME OVER ***" );
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

}

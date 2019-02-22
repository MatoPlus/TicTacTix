import java.util.Scanner;

// Test class to demonstrate our Connect Four Model
public class TicTacTixTest {
    public static void main( String[] args ) {
        int column = 0;
        int row = 0;

        Scanner kbInput = new Scanner( System.in );
        TicTacTix game = new TicTacTix();
        
        do {
            // Game board output 
            System.out.println( game );
            
            // Prompt and get row selection
            System.out.print( "Row: " );
            row = kbInput.nextInt();
            
            // Prompt and get column selection
            System.out.print( "Column: " );
            column = kbInput.nextInt();
            
            // Check for row and column selection.
            if ( game.move(row, column ) == -1 ) {
                System.out.println( "\nInvalid insert at row \"" + row + "\" at column \"" + column + "\"" );
                System.out.println( "Please Try Again..\n" );
            } 
        } while (true); //!game.hasWon() );
        
        //System.out.println( game );
        //System.out.println( "*** GAME OVER ***" );
    }   
}

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Caitlyn on 4/25/17.
 *
 * run minesweeper grid
 *
 */
public class Minesweeper {


    /**
     * play the game
     */

    public void beginGame() {

        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to Minesweeper!");
        System.out.println("Please select a difficulty: ");
        System.out.println("1. Beginner");
        System.out.println("2. Intermediate");
        System.out.println("3. Expert");
        System.out.println("4. Debug");

        int level = 0;
        //int level = scan.next();
        try {
            level = Integer.parseInt(scan.next());
        } catch (NumberFormatException e){
            System.out.println("Need to enter a integer 1-4");
        } while (level <=0 || level>4){
            System.out.println("Enter level 1-4");
            System.out.println("Please select a difficulty");
            try {
                level = Integer.parseInt(scan.next());
            } catch (NumberFormatException e){
                System.out.println("Need to enter a integer 1-4");
            }
        }

        Grid g;

        if (level == 1) {
            g = new Grid(500, 500, 9, 9, 10);
        } else if (level == 2) {
            g = new Grid(500, 500, 16, 16, 40);
        } else if (level == 3) {
            g = new Grid(500, 1000, 30, 16, 99);
        } else {
            g = new Grid(500, 500, 8, 7, 2);
        }

        g.drawGrid();
        g.placeBombs();
        g.play();
    }



    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        Minesweeper m = new Minesweeper();
        m.beginGame();

        int again = 0;

        while (again != 2) {
            System.out.println("Do you want to play again? 1. Yes 2. No");
            try {
                again = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e){
                System.out.println("Need to enter a integer 1 or 2");
            } while (again <0 || again>3){
                System.out.println("Need to enter an integer 1 or 2");
                try {
                    again = Integer.parseInt(scan.nextLine());
                } catch (NumberFormatException e){
                    System.out.println("Need to enter a integer 1 or 2");
                }
            }
            if (again == 1) {
                m.beginGame();
            }
        }

    }
}

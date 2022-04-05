import com.intellij.openapi.vcs.history.VcsRevisionNumber;

import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Caitlyn on 4/25/17.
 *
 * purpose: run the Minesweeper grid
 *
 */
public class Grid {

    private Square[][] squares;
    private int numSquaresWidth;
    private int numSquaresHeight;

    private int canvasHeight;
    private int canvasWidth;

    private double width;

    private int numBombs;

    private int flagsLeft;


    /**
     * contructor
     * @param height
     * @param width
     * @param horizSquares
     * @param vertSquares
     */
    public Grid(int height, int width, int horizSquares, int vertSquares, int bombs) {
        this.canvasHeight = height;
        this.canvasWidth = width;
        this.numSquaresWidth = horizSquares;
        this.numSquaresHeight = vertSquares;

        this.numBombs = bombs;

        this.width = .98;

        int x = 0;
        int y = 0;

        this.squares = new Square[this.numSquaresHeight][this.numSquaresWidth];

        for (int h = 0; h < this.numSquaresHeight; h++) {
            for (int w = 0; w < this.numSquaresWidth; w++) {
                this.squares[h][w] = (new Square(w+.5, h+.5, this.width));
            }
        }

        this.flagsLeft = this.numBombs;
    }

    /**
     * draw the grid of squares
     */
    public void drawGrid(){

        StdDraw.setCanvasSize(this.canvasWidth, this.canvasHeight);
        StdDraw.setXscale(0,this.numSquaresWidth);
        StdDraw.setYscale(0,this.numSquaresHeight+2);

        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);

        for (int i = 0; i < this.numSquaresHeight; i++) {
            for (int j = 0; j < this.numSquaresWidth; j++) {
                this.squares[i][j].drawSquare();
            }
        }

        changeFlagCount();
    }

    /**
     * displays flag count
     */
    public void changeFlagCount(){

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(1, this.numSquaresHeight+1,5,.35);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.textLeft(1, this.numSquaresHeight +1,"Flags Left: "+this.flagsLeft);

    }

    /**
     * detects if the mouse is clicked and where it is clicked
     * @return boolean if the mouse is clicked or not
     */
    public boolean clicked(){
        if (StdDraw.mousePressed()){

            return true;
        } else {
            return false;
        }
    }

    /**
     * add a flag to the clicked square
     * @param y
     * @param x
     */
    public void addFlag(int y, int x){
        if (this.squares[y][x].hasFlag) {
            this.squares[y][x].hasFlag = false;
            this.flagsLeft += 1;
        } else {
            this.squares[y][x].hasFlag = true;
            this.flagsLeft -= 1;
        }
        this.squares[y][x].drawSquare();
        changeFlagCount();
    }

    /**
     * places bombs randomly on the grid
     */
    public void placeBombs(){

        Random rand = new Random();

        int bombs = 0;
        int x;
        int y;

        while (bombs<this.numBombs){
            y = rand.nextInt(this.numSquaresHeight);
            x = rand.nextInt(this.numSquaresWidth);

            if (!this.squares[y][x].hasBomb){
                this.squares[y][x].bombSquare();
                bombs += 1;
            }
        }

    }

    /**
     * when the user clicks a bomb, this function will reveal all the bombs that do not have flags
     */
    public void revealBombs(){

        for (int i = 0; i < this.numSquaresHeight; i++) {
            for (int j = 0; j < this.numSquaresWidth; j++) {
                if (this.squares[i][j].hasBomb){
                    this.squares[i][j].isRevealed = true;
                    this.squares[i][j].drawSquare();
                }
            }
        }
    }


    /**
     * checks for a bomb in the clicked square
     * @param y
     * @param x
     * @return boolean for if the square has a bomb
     */
    public boolean checkForBombs(int y, int x){

        this.squares[y][x].nearbyBombs = 0;

        this.squares[y][x].isRevealed = true;

        //check square for bomb
        if(this.squares[y][x].hasBomb && !this.squares[y][x].hasFlag){
            revealBombs();
            return true;
        }else if (!this.squares[y][x].hasBomb){
            this.squares[y][x].drawSquare();
            checkNearbyBombs(y,x);
            return false;
        } else{
            return false;
        }

    }

    /**
     * check for bombs in surrounding 8 squares
     * @param y
     * @param x
     */
    public void checkNearbyBombs(int y, int x){
        //check surrounding squares for bombs

        this.squares[y][x].isRevealed = true;

        if((y-1)>=0 && this.squares[y - 1][x].hasBomb){
            this.squares[y][x].nearbyBombs += 1;
        } if ((y+1)<this.numSquaresHeight && this.squares[y + 1][x].hasBomb){
            this.squares[y][x].nearbyBombs += 1;
        } if ((x-1)>=0 && this.squares[y][x - 1].hasBomb){
            this.squares[y][x].nearbyBombs += 1;
        } if ((x+1)<this.numSquaresWidth && this.squares[y][x + 1].hasBomb){
            this.squares[y][x].nearbyBombs += 1;
        } if ((x-1)>=0 && (y-1)>=0 && this.squares[y - 1][x - 1].hasBomb){
            this.squares[y][x].nearbyBombs += 1;
        } if ((y+1)<this.numSquaresHeight && (x-1)>=0 && this.squares[y + 1][x - 1].hasBomb){
            this.squares[y][x].nearbyBombs += 1;
        } if ((x+1)<this.numSquaresWidth && (y-1)>=0 && this.squares[y - 1][x + 1].hasBomb){
            this.squares[y][x].nearbyBombs += 1;
        } if ((y+1)<this.numSquaresHeight && (x+1)<this.numSquaresWidth && this.squares[y + 1][x + 1].hasBomb){
            this.squares[y][x].nearbyBombs += 1;
        }

        if (this.squares[y][x].nearbyBombs >0){
            this.squares[y][x].drawSquare();
        } else if (this.squares[y][x].nearbyBombs == 0){
            this.squares[y][x].isRevealed = true;
            this.squares[y][x].drawSquare();
            if(x-1>=0 && !this.squares[y][x-1].isRevealed){
                checkNearbyBombs(y,x-1);
            }if (x+1<this.numSquaresWidth && !this.squares[y][x+1].isRevealed) {
                checkNearbyBombs(y, x + 1);
            } if (y-1>=0 && !this.squares[y-1][x].isRevealed) {
                checkNearbyBombs(y - 1, x);
            } if (y+1<this.numSquaresHeight && !this.squares[y+1][x].isRevealed) {
                checkNearbyBombs(y + 1, x);
            } if (y-1>=0 && x+1<this.numSquaresWidth && !this.squares[y-1][x+1].isRevealed) {
                checkNearbyBombs(y - 1, x + 1);
            } if (x+1<this.numSquaresWidth && y+1<this.numSquaresHeight && !this.squares[y+1][x+1].isRevealed) {
                checkNearbyBombs(y + 1, x + 1);
            } if (x-1>=0 && y-1>=0 && !this.squares[y-1][x-1].isRevealed) {
                checkNearbyBombs(y - 1, x - 1);
            }if (y+1<this.numSquaresHeight && x-1>=0 && !this.squares[y+1][x-1].isRevealed) {
                checkNearbyBombs(y + 1, x - 1);
            }
        }
    }

    /**
     * displays you win
     */
    public void win(){
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.textLeft(this.numSquaresWidth-2,this.numSquaresHeight +1,"You win!");
        return;
    }

    /**
     * checks to see if flags cover all the bombs
     * @return boolean if the game is won or not
     */
    public boolean checkGame() {
        int correctFlags = 0;
        for (int i = 0; i < this.numSquaresHeight; i++) {
            for (int j = 0; j < this.numSquaresWidth; j++) {
                if (this.squares[i][j].hasBomb && this.squares[i][j].hasFlag){
                    correctFlags += 1;
                }
            }
        }
        if(correctFlags == this.numBombs){
            return true;
        }else{
            return false;
        }
    }

    /**
     * play minesweeper
     */
    public void play(){
        boolean isMouseDown = false; // boolean variables that helps tell if it is a new mouse click
        boolean game = false;
        boolean isFDown = false;

        while (!game) {

            if (StdDraw.isKeyPressed(KeyEvent.VK_F) && !isFDown){
                int x = (int)StdDraw.mouseX();
                int y = (int)StdDraw.mouseY();
                isFDown = true;
                addFlag(y,x);
            } else if (clicked() && !isMouseDown){
                int x = (int)StdDraw.mouseX();
                int y = (int)StdDraw.mouseY();
                isMouseDown = true;
                game = checkForBombs(y,x);
            } if (!clicked()){
                isMouseDown = false;
            } if (!StdDraw.isKeyPressed(KeyEvent.VK_F)){
                isFDown = false;
            }

            if(this.flagsLeft == 0) {
                game = checkGame();
                if (game){
                    win();
                }
            }

        }
        System.out.println("Game");
    }


}

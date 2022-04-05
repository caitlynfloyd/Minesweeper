import org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi;

/**
 * Created by Caitlyn on 4/25/17.
 *
 * purpose is to control squares in minesweeper grid
 *
 */
public class Square {

    private double width;
    private double x;
    private double y;
    protected boolean hasBomb;
    protected boolean hasFlag;
    protected boolean isRevealed;
    protected int nearbyBombs;


    /**
     * constructor
     * @param x
     * @param y
     * @param w
     */
    public Square(double x, double y, double w){
        this.x = x;
        this.y = y;
        this.width = w;

        this.nearbyBombs = 0;
        this.hasFlag = false;
        this.isRevealed = false;
        this.hasBomb = false;
    }

    /**
     * this will draw individual squares
     */
    public void drawSquare(){

        if(this.hasBomb && this.isRevealed){
            StdDraw.setPenColor(StdDraw.RED);
        } else if (this.isRevealed && !this.hasFlag){
            StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        } else {
            StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        }
        StdDraw.filledSquare(this.x,this.y,(this.width/2));

        if (this.hasFlag){
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.textLeft(this.x,this.y,"F");
        } else if (!this.hasFlag && !this.isRevealed){
            StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
            StdDraw.filledSquare(this.x,this.y,(this.width/2));

        }


        if(this.isRevealed && this.nearbyBombs>0 && !this.hasBomb && !this.hasFlag){
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.textLeft(this.x,this.y, Integer.toString(this.nearbyBombs));
        }
    }

    /**
     * declare that a square has a bomb
     */
    public void bombSquare(){
        this.hasBomb = true;
    }

    /**
     * get x value
     * @return this.x
     */
    public int getX(){
        return (int)this.x;
    }


    /**
     * get y value
     * @return this.t
     */
    public int getY(){
        return (int)this.y;
    }
}

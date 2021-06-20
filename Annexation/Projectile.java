/**
 * [Projectile.java]
 * This class represents a projectile
 * @author Edwin Sun
 * @version 1.0
 */

/* import statements */
import java.awt.Graphics;


public class Projectile{

    /* Class Variables */
    private int x;
    private int y;
    private int xChange;
    private int yChange;
    private int xDest;
    private int yDest;
    private int numMoves = 10;

    /**
     * Projectile
     * This constructor creates a projectile with a current x coordinate, y coordinate, the amount
     * to change in x, the amount to change in y, the x destination coordinate and the y destination coordinate
     * @param x An integer representing the current x coordinate of the projectile
     * @param y An integer representing the current y coordinate of the projectile
     * @param xChange An integer representing the amount to change in x as the projectile moves
     * @param yChange An integer representing the amount to change in y as the projectile moves
     * @param xDest An integer representing the x destination coordinate
     * @param yDest An integer representing the y destination coordinate
     */
    Projectile(int x, int y, int xChange, int yChange, int xDest, int yDest){
        this.x = x;
        this.y = y;
        this.xChange = xChange;
        this.yChange = yChange;
        this.xDest = xDest;
        this.yDest = yDest;
    }

    /**
     * draw
     * This method accepts a graphics object g, and draws the projectile
     * @param g graphics object g 
     */
    public void draw(Graphics g){
        g.drawOval(this.getX(), this.getY(), 7, 7);
    }

    /**
     * move
     * This method changes the projectile's x coordinate and y coordinate by one update
     */
    public void move(){
        this.setX(this.getX() + this.xChange);
        this.setY(this.getY() + this.yChange);
    }

    /**
     * getX
     * This method returns an integer representing the x coordinate for the projectile
     * @return An integer representing the x coordinate for the projectile
     */
    public int getX(){
        return this.x;
    }

    /**
     * getY
     * This method returns an integer representing the y coordinate for the projectile
     * @return An integer representing the y coordinate for the projectile
     */
    public int getY(){
        return this.y;
    }

    /**
     * getXDest
     * This method returns an integer representing the x destination coordinate for the projectile
     * @return An integer representing the x destination coordinate for the projectile
     */
    public int getXDest(){
        return this.xDest;
    }

    /**
     * getYDest
     * This method returns an integer representing the y destination coordinate for the projectile
     * @return An integer representing the y destination coordinate for the projectile
     */
    public int getYDest(){
        return this.yDest;
    }

    /**
     * getNumMoves
     * This method returns an integer representing the number of updates left for the projectile's movement
     * @return An integer representing the number of updates left for the projectile's movement
     */
    public int getNumMoves(){
        return this.numMoves;
    }

    /**
     * setX
     * This method accepts an integer and sets it as the x coordinate of the projectile
     * @param x An integer representing the x coordinate to set
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * setY
     * This method accepts an integer and sets it as the y coordinate of the projectile
     * @param y An integer representing the y coordinate to set
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * setNumMoves
     * This method accepts an integer and sets it as the number of updates left for the movement of the projectile
     * @param numMoves An integer representing the number of updates left for the movement of the projectile
     */
    public void setNumMoves(int numMoves){
        this.numMoves = numMoves;
    }
}

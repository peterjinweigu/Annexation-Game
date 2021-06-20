/**
 * [GameObject.java]
 * This is a class that acts as a parent class for other objects
 * @author Peter Gu and Edwin sun
 * @version 1.0
 */

/* import statements */
import java.awt.Graphics;
import java.awt.image.BufferedImage; 

abstract public class GameObject{

    /* Class Variables */
    private int x, y;
    public BufferedImage sprite;
    
    /**
     * GameObject
     * This constructor creates a gameObject with an X and Y coordinate
     * @param x An integer x that represents the X coordinate
     * @param y An integer y that represents the Y coordinate
     */
    GameObject(int x, int y){
        loadSprite();
        this.x = x;
        this.y = y;
    }

    /**
     * loadSprite
     * This method loads the sprite for the gameObject
     */
    abstract public void loadSprite();

    /**
     * draw
     * This method accepts a graphics object g, and draws the gameObject
     * @param g graphics object g 
     */
    abstract public void draw(Graphics g);
    
    /**
     * getX
     * This method returns an integer representing the x coordinate for the top left corner of gameObject
     * @return An integer representing the x coordinate for the top left corner of the gameObject
     */
    public int getX(){
        return this.x;
    }

    /**
     * getY
     * This method returns an integer representing the y coordinate for the top left corner of gameObject
     * @return An integer representing the y coordinate for the top left corner of the gameObject
     */
    public int getY(){
        return this.y;
    }

    /**
     * setX
     * This method sets x value of the gameObject
     * @param x An integer representing the new x value
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * setY
     * This method sets y value of the gameObject
     * @param y An integer representing the new y value
     */
    public void setY(int y){
        this.y = y;
    }
}

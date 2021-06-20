/**
 * [Unit.java]
 * This is a class which represents a unit
 * @author Peter Gu
 * @version 1.0
 */

/* import statements */
import java.awt.Graphics;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;
import java.io.File;

public class Unit extends GameObject{

    /* Class Variables */
    private int damage, hp;
    private int turnType;
    private int xChange, yChange;
    private int xDest, yDest;
    private int numMoves;
    private int direction;
    private BufferedImage sprite2;

    /**
     * Unit
     * This constructor creates a unit with a specified x coordinate, y coordinate, damage, health points, and turn type
     * @param x An integer representing the x coordinate of the unit 
     * @param y An integer representing the y coordinate of the unit
     * @param damage An integer representing the amount of damage the unit does
     * @param hp An integer representing the amount of health points the unit has
     * @param turnType An integer representing whether the unit has done nothing yet, moving, attacking, or done their turn
     */
    Unit(int x, int y, int damage, int hp, int turnType){
        super(x, y);
        this.damage = damage;
        this.hp = hp;
        this.turnType = turnType;
        this.xChange = 0;
        this.yChange = 0;
        this.xDest = x;
        this.yDest = y;
        this.numMoves = 0; 
        this.direction = 1;
    }

    /**
     * loadSprite
     * This method loads the sprites of the unit with one facing left and one facing right
     */
    public void loadSprite() { 
        try {
            this.sprite = ImageIO.read(new File("Sprites/UnitRight.png"));
            this.sprite2 = ImageIO.read(new File("Sprites/UnitLeft.png"));
        } catch(Exception e) { 
            System.out.println("error loading sprite");
        }
    }

    /**
     * draw
     * This method accepts a graphics object g, and draws the unit depending on the direction it is facing
     * @param g graphics object g 
     */
    public void draw (Graphics g){
        if (this.direction == 1){
            g.drawImage(this.sprite, this.getX(), this.getY(), null);
        } else {
            g.drawImage(this.sprite2, this.getX(), this.getY(), null);
        }
    }

    /**
     * getDamage
     * This method returns an integer representing the damage the unit does
     * @return An integer representing the damage the unit does
     */
    public int getDamage(){
        return this.damage;
    }

    /**
     * getHp
     * This method returns an integer representing the amount of health a unit has
     * @return An integer representing the amount of health a unit has
     */
    public int getHp(){
        return this.hp;
    }

    /**
     * getTurnType
     * This method returns an integer representing which turn phase the unit is currently in
     * @return An integer representing which turn phase the unit is currently in
     */
    public int getTurnType (){
        return this.turnType;
    }

    /**
     * getXDest
     * This method returns an integer representing the x destination coordinate that the unit is supposed to move to
     * @return An integer representing the x destination coordinate that the unit is supposed to move to
     */
    public int getXDest(){
        return this.xDest;
    }

    /**
     * getYDest
     * This method returns an integer representing the y destination coordinate that the unit is supposed to move to
     * @return An integer representing the y destination coordinate that the unit is supposed to move to
     */
    public int getYDest(){
        return this.yDest;
    }

    /**
     * getNumMoves
     * This method returns an integer representing the number of updates left for the unit's movement
     * @return An integer representing the number of updates left for the unit's movement
     */
    public int getNumMoves(){
        return this.numMoves;
    }

    /**
     * getXChange
     * This method returns an integer representing the amount to shift in the x coordinate of the unit when it is moving
     * @return An integer representing the amount to shift in the x coordinate of the unit when it is moving
     */
    public int getXChange(){
        return this.xChange;
    }

    /**
     * getYChange
     * This method returns an integer representing the amount to shift in the y coordinate of the unit when it is moving
     * @return An integer representing the amount to shift in the y coordinate of the unit when it is moving
     */
    public int getYChange(){
        return this.yChange;
    }

    /**
     * getDirection
     * This method returns an integer representing the direction the unit is facing (1 = facing right, 2 = facing left)
     * @return An integer representing the direction the unit is facing 
     */
    public int getDirection(){
        return this.direction;
    }

    /**
     * getSprite2
     * This method returns a BufferedImage representing the left facing sprite of the unit
     * @return A BufferedImage representing the left facing sprite of the unit
     */
    public BufferedImage getSprite2(){
        return this.sprite2;
    }

    /**
     * setDamage
     * This method accepts an integer and sets it as the amount of damage the unit does
     * @param damage An integer representing the amount of damage the unit does
     */
    public void setDamage(int damage){
        this.damage = damage;
    }

    /**
     * setHp
     * This method accepts an integer and sets it as the amount of health points the unit has
     * @param hp An integer representing the amount of health points the unit has
     */
    public void setHp(int hp){
        this.hp = hp;
    }

    /**
     * setTurnType
     * This method accepts an integer and sets it as the turn type of the unit
     * @param turnType An integer representing the turn type of the unit
     */
    public void setTurnType(int turnType){
        this.turnType = turnType;
    }

    /**
     * setXDest
     * This method accepts an integer and sets it as the x destination coordinate when the unit moves
     * @param xDest An integer representing the x destination coordinate when the unit moves
     */
    public void setXDest(int xDest){
        this.xDest = xDest;
    }

    /**
     * setYDest
     * This method accepts an integer and sets it as the y destination coordinate when the unit moves
     * @param yDest An integer representing the y destination coordinate when the unit moves
     */
    public void setYDest(int yDest){
        this.yDest = yDest;
    }

    /**
     * setNumMoves
     * This method accepts an integer and sets it as the number of updates left in the unit's movement
     * @param numMoves An integer representing the number of updates left in the unit's movement
     */
    public void setNumMoves(int numMoves){
        this.numMoves = numMoves;
    }

    /**
     * setDirection
     * This method accepts an integer and sets it as the direction the unit is facing
     * @param direction An integer representing the direction the unit is facing
     */
    public void setDirection(int direction){
        this.direction = direction;
    }

    /**
     * setSprite2
     * This method accepts a file and sets it as the left facing sprite for the unit
     * @param file An integer representing the left facing sprite for the unit
     */
    public void setSprite2(File file){
        try {
            this.sprite2 = ImageIO.read(file);
        } catch (Exception e){
            System.out.println("sprite not found");
        }
    }

    /**
     * updateMove
     * This method accepts two integers and sets them as the amount to 
     * shift in x and the amount to shift in y when the unit is moving
     * @param xChange An integer representing the amount to shift in x when the unit is moving
     * @param yChange An integer representing the amount to shift in y when the unit is moving
     */
    public void updateMove(int xChange, int yChange){
        this.xChange = xChange;
        this.yChange = yChange;
    }

    /**
     * move
     * This method increments the x and y coordinates of the unit by the xChange and yChange values respectively
     */
    public void move(){
        this.setX(this.getX() + this.xChange);
        this.setY(this.getY() + this.yChange);
    }
}

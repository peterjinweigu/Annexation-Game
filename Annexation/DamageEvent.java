/**
 * [DamageEvent.java]
 * This is a class that represents a damage event 
 * @author Edwin Sun
 * @version 1.0 May 10, 2021
 */

/* import statements */
import java.awt.Graphics;

public class DamageEvent{

    /* Class Variables */
    private int x;
    private int y;
    private int timer = 60;
    private int damage;
    
    /**
     * DamageEvent
     * This constructor creates a damage event with a specified x coordinate, y coordinate, and damage 
     * @param x An integer representing the x coordinate of the damage event
     * @param y An integer representing the y coordinate of the damage event
     * @param damage An integer representing the amount of damage to display
     */
    DamageEvent(int x, int y, int damage){
        this.x = x;
        this.y = y;
        this.damage = damage;
    }

    /**
     * draw
     * This method accepts a graphics object g, and draws the damage event
     * @param g A graphics object g 
     */
    public void draw(Graphics g){
        g.drawString("-"+Integer.toString(this.getDamage()), this.getX()+10, this.getY()+10);
    }

    /**
     * getX
     * This method returns an integer representing the x coordinate for the damage event
     * @return An integer representing the x coordinate for the damage event
     */
    public int getX(){
        return this.x;
    }

    /**
     * getY
     * This method returns an integer representing the y coordinate for the damage event
     * @return An integer representing the y coordinate for the damage event
     */
    public int getY(){
        return this.y;
    }

    /**
     * getDamage
     * This method returns an integer representing the amount of damage for the damage event to display
     * @return An integer representing the amount of damage for the damage event to display
     */
    public int getDamage(){
        return this.damage;
    }

    /**
     * getTimer
     * This method returns an integer representing the amount of time left for the damage event to display
     * @return An integer representing the amount of time left for the damage event to display
     */
    public int getTimer(){
        return this.timer;
    }

    /**
     * setTimer
     * This method accepts an integer and sets it as the amount of time left for the damage event to display
     * @return An integer representing the amount of time left for the damage event to display
     */
    public void setTimer(int timer){
        this.timer = timer;
    }

}
/**
 * [Cavalry.java]
 * This class is a subclass of unit and it represents a cavalry
 * @author Peter Gu
 * @version 1.0 
 */

/* import statements */
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.io.File;

public class Cavalry extends Unit{

    /**
     * cavalry
     * This constructor creates a cavalry with the specified x coordinate, y coordinate, damage, health points, and turn type 
     * @param x An integer representing the x coordinate of the cavalry 
     * @param y An integer representing the y coordinate of the cavalry
     * @param damage An integer representing the amount of damage the cavalry does
     * @param hp An integer representing the amount of health points the cavalry has
     * @param turnType An integer representing whether the cavalry has done nothing yet, moving, attacking, or done their turn
     */
    Cavalry(int x, int y, int damage, int hp, int turnType){
        super(x, y, damage, hp, turnType);
    }

    /**
     * loadSprite
     * This method loads the sprites of the cavalry with one facing left and one facing right
     */
    public void loadSprite() { 
        try {
            this.sprite = ImageIO.read(new File("Sprites/HorseRight.png"));
            setSprite2(new File("Sprites/HorseLeft.png"));
        } catch(Exception e) { 
            System.out.println("error loading sprite");
        }
    }

    /**
     * draw
     * This method accepts a graphics object g, and draws the cavalry depending on the direction it is facing
     * @param g graphics object g 
     */
    public void draw (Graphics g){
        if (this.getDirection() == 1){
            g.drawImage(this.sprite, this.getX(), this.getY(), null);
        } else {
            g.drawImage(this.getSprite2(), this.getX(), this.getY(), null);
        }
    }
}

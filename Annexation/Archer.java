/**
 * [Archer.java]
 * This class is a subclass of unit and it represents an archer
 * @author Peter Gu
 * @version 1.0 
 */

/* import statements */
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.io.File;

public class Archer extends Unit{

    /**
     * Archer
     * This constructor creates an archer with the specified x coordinate, y coordinate, damage, health points, and turn type 
     * @param x An integer representing the x coordinate of the archer 
     * @param y An integer representing the y coordinate of the archer
     * @param damage An integer representing the amount of damage the archer does
     * @param hp An integer representing the amount of health points the archer has
     * @param turnType An integer representing whether the archer has done nothing yet, moving, attacking, or done their turn
     */
    Archer(int x, int y, int damage, int hp, int turnType){
        super(x, y, damage, hp, turnType);
    }

    /**
     * loadSprite
     * This method loads the sprites of the archer with one facing left and one facing right
     */
    public void loadSprite() { 
        try {
            this.sprite = ImageIO.read(new File("Sprites/ArcherRight.png"));
            setSprite2(new File("Sprites/ArcherLeft.png"));
        } catch(Exception e) { 
            System.out.println("error loading sprite");
        }
    }

    /**
     * draw
     * This method accepts a graphics object g, and draws the archer depending on the direction it is facing
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

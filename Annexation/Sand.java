/**
 * [Sand.java]
 * This class is a subclass of tile and it represents a sand tile
 * @author Peter Gu
 * @version 1.0 
 */

/* import statements */
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.io.File;

public class Sand extends Tile{
    
    /**
     * Sand
     * This constructor creates a sand tile with a specified x coordinate and y coordinate
     * @param x An integer representing the x coordinate of the sand tile 
     * @param y An integer representing the y coordinate of the sand tile
     */
    Sand(int x, int y){
        super(x, y);
    }

    /**
     * loadSprite
     * This method loads the sprite of the sand tile
     */
    public void loadSprite() { 
        try {
            this.sprite = ImageIO.read(new File("Sprites/SandTile.png"));
            setSprite2(new File("Sprites/CloudTile.png"));
        } catch(Exception e) { 
            System.out.println("error loading sprite");
        }
    }

    /**
     * draw
     * This method accepts a graphics object g, and draws the sand tile
     * @param g A graphics object g 
     */
    public void draw(Graphics g){
        g.drawImage(this.sprite, this.getX(), this.getY(), null);
    }
}
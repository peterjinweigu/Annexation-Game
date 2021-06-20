/**
 * [City.java]
 * This class is a subclass of GameObject and it represents a city
 * @author Peter Gu
 * @version 1.0 
 */

/* import statements */
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.io.File;

public class City extends GameObject{

    /* Class Variable */
    private int income;

    /**
     * City
     * This constructor creates a city with the specified x coordinate, y coordinate, and income
     * @param x An integer representing the x coordinate of the city 
     * @param y An integer representing the y coordinate of the city
     * @param income An integer representing the income of the City
     */
    City(int x, int y, int income){
        super(x, y);
        this.income = income;
    }

    /**
     * loadSprite
     * This method loads the sprite of the city
     */
    public void loadSprite() { 
        try {
            sprite = ImageIO.read(new File("Sprites/City.png"));
        } catch(Exception e) { 
          System.out.println("error loading sprite");
        }
    }
    
    /**
     * getIncome
     * This method returns an integer representing the income of this city
     * @return An integer representing the income of this city
     */
    public int getIncome(){
        return this.income;
    }

    /**
     * setIncome
     * This method accepts an integer and sets it as the income of the city
     * @param income An integer representing the income of the city
     */
    public void setIncome(int income){
        this.income = income;
    }

    /**
     * draw
     * This method accepts a graphics object g, and draws the city
     * @param g A graphics object g 
     */
    public void draw (Graphics g){
        g.drawImage(this.sprite, this.getX(), this.getY(), null);
    }
}

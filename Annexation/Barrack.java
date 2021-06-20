/**
 * [Barrack.java]
 * This class is a subclass of GameObject and it represents a barrack
 * @author Peter Gu
 * @version 1.0 
 */

/* import statements */
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.io.File;

public class Barrack extends GameObject{

    /* Class Variables */
    private int type;
    private int cost;

    /**
     * Barrack
     * This constructor creates a barrack with the specified x coordinate, y coordinate, type, and cost  
     * @param x An integer representing the x coordinate of the barrack 
     * @param y An integer representing the y coordinate of the barrack
     * @param type An integer representing the type of the barrack
     * @param cost An integer representing the cost of the barrack
     */
    Barrack(int x, int y, int type, int cost){
        super(x, y);
        this.type = type;
        this.cost = cost;
        loadSprite();
    }

    /**
     * loadSprite
     * This method loads the sprites of the barrack including the unit barrack, horse barrack, and archer barrack
     */
    public void loadSprite() { 
        try {
            if (this.type == 1){
                this.sprite = ImageIO.read(new File("Sprites/UnitBarrack.png"));
            } else if (this.type == 2){
                this.sprite = ImageIO.read(new File("Sprites/HorseBarrack.png"));
            } else {
                this.sprite = ImageIO.read(new File("Sprites/ArcherBarrack.png"));
            }
        } catch(Exception e) { 
            System.out.println("error loading sprite");
        }
    }

    /**
     * getType
     * This method returns an integer representing the type of this barrack
     * @return An integer representing the type of this barrack
     */
    public int getType(){
        return this.type;
    }

    /**
     * getCost
     * This method returns an integer representing the cost of this barrack
     * @return An integer representing the cost of this barrack
     */
    public int getCost(){
        return this.cost;
    }

    /**
     * createUnit
     * This method accepts a player, an integer x, an integer y, and creates 
     * a unit for that player at the specified x and y location
     * @param player A player that represents the player that the unit is being created for
     * @param x An integer that represents the x coordinate of the unit to be spawned
     * @param y An integer that represents the y coordinate of the unit to be spawned
     * @return A new unit, calvary, or archer depending on the type of barrack
     */
    public Unit createUnit(Player player, int x, int y){
        if (type == 1){
            return new Unit(x, y, player.getUnitDamage(), 15, 2);
        } else if (type == 2){
            return new Cavalry(x, y, player.getCavalryDamage(), 25, 2);
        } else {
            return new Archer(x, y, player.getArcherDamage(), 15, 2);
        }
    }

    /**
     * draw
     * This method accepts a graphics object g, and draws the barrack
     * @param g A graphics object g 
     */
    public void draw (Graphics g){
        g.drawImage(this.sprite, this.getX(), this.getY(), null);
    }
}

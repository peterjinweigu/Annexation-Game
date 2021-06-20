/**
 * [Tile.java]
 * This class is a subclass of GameObject and it represents a tile
 * @author Edwin Sun and Peter Gu
 * @version 1.0 
 */

/* import statements */
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;
import java.io.File;

public class Tile extends GameObject{

    /* Class Variables */
    private int playerOccupied = 0;
    private int typeOccupied = 0;
    private int buildingOccupied = 0;
    private ArrayList<Integer> discoveredPlayers = new ArrayList<Integer>();
    private BufferedImage sprite2;
    
    /**
     * Tile
     * This constructor creates a tile with a specified x coordinate and y coordinate
     * @param x An integer representing the x coordinate of the tile 
     * @param y An integer representing the y coordinate of the tile
     */
    Tile(int x, int y){
        super(x, y);
    }

    /**
     * loadSprite
     * This method loads the sprites of the tile including the grass and cloud tiles
     */
    public void loadSprite() { 
        try {
            this.sprite = ImageIO.read(new File("Sprites/GrassTile.png"));
            this.sprite2 = ImageIO.read(new File("Sprites/CloudTile.png"));
        } catch(Exception e) { 
            System.out.println("error loading sprite");
        }
    }

    /**
     * draw
     * This method accepts a graphics object g, and draws the grass tile
     * @param g A graphics object g 
     */
    public void draw(Graphics g){
        g.drawImage(this.sprite, this.getX(), this.getY(), null);
    }

    /**
     * drawCloud
     * This method accepts a graphics object g, and draws the cloud tile
     * @param g A graphics object g 
     */
    public void drawCloud(Graphics g){
        g.drawImage(this.sprite2, this.getX(), this.getY(), null);
    }

    /**
     * getPlayerOccupied
     * This method returns an integer representing the number of the player that occupies this tile
     * @return An integer representing the number of the player that occupies this tile
     */
    public int getPlayerOccupied(){
        return this.playerOccupied;
    }

    /**
     * getTypeOccupied
     * This method returns an integer representing the type of unit that occupies this tile
     * @return An integer representing the type of unit that occupies this tile
     */
    public int getTypeOccupied(){
        return this.typeOccupied;
    }

    /**
     * getBuildingOccupied
     * This method returns an integer representing the type of building that occupies this tile
     * @return An integer representing the type of building that occupies this tile
     */
    public int getBuildingOccupied(){
        return this.buildingOccupied;
    }

    /**
     * getSprite2
     * This method returns a BufferedImage representing the cloud tile
     * @return A BufferedImage representing the cloud tile
     */
    public BufferedImage getSprite2(){
        return this.sprite2;
    }

    /**
     * inRange
     * This method checks if a pair of x and y coordinates are in the area covered by the tile
     * @param x An integer representing the x coordiante being checked
     * @param y An integer representing the y coordinate being checked
     * @return A boolean that is true if the coordinates are in range of the tile and false if not 
     */
    public boolean inRange(int x, int y){
        return ((x >= this.getX()) && (x < this.getX() + 50) && (y >= this.getY()) && (y < this.getY() + 50));
    }

    /**
     * discoveredBy
     * This method checks if a player has discovered this tile
     * @param x An integer representing the number of the player being checked
     * @return A boolean that is true if the player has discovered this tile and false if not 
     */
    public boolean discoveredBy(int x){
        return discoveredPlayers.contains(x);
    }

    /**
     * setPlayerOccupied
     * This method accepts an integer and sets it as the player that occupies this tile
     * @param playerOccupied An integer representing the player that now occupies this tile
     */
    public void setPlayerOccupied(int playerOccupied){
        this.playerOccupied = playerOccupied;
    }

    /**
     * setTypeOccupied
     * This method accepts an integer and sets it as the unit type that occupies this tile
     * @param typeOccupied An integer representing the unit type that now occupies this tile
     */
    public void setTypeOccupied(int typeOccupied){
        this.typeOccupied = typeOccupied;
    }

    /**
     * setBuildingOccupied
     * This method accepts an integer and sets it as the building type that occupies this tile
     * @param buildingOccupied An integer representing the building type that now occupies this tile
     */
    public void setBuildingOccupied(int buildingOccupied){
        this.buildingOccupied = buildingOccupied;
    }

    /**
     * setSprite2
     * This method accepts a file and sets it as the cloud sprite for this tile
     * @param file An integer representing the cloud sprite for this tile
     */
    public void setSprite2(File file){
        try {
            this.sprite2 = ImageIO.read(file);
        } catch (Exception e){
            System.out.println("sprite not found");
        }
    }

    /**
     * append
     * This method accepts the number of a player and adds it to the list of players that discovered this tile
     * @param x An integer representing the number of the player that has just discovered this tile
     */
    public void append(int x){
        this.discoveredPlayers.add(x);
    }
}
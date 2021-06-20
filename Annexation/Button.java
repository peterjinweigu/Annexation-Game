/**
 * [Button.java]
 * This class represents a button
 * @author Edwin Sun
 * @version 1.0
 */

/* import statements */
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;
import java.io.File;

public class Button{

    /* Class Variables */
    private int x;
    private int y;
    private int width;
    private int height; 
    private String description;
    private BufferedImage sprite;
    private Color curColor;

    /**
     * Button
     * This constructor creates a button with a specified x coordinate, y coordinate, width, height, and description
     * @param x An integer representing the x coordinate for the top left corner of the button
     * @param y An integer representing the y coordinate for the top left corner of the button
     * @param width An integer representing the width of the button
     * @param height An integer representing the height of the button
     * @param description A string representing the description on the button 
     */
    Button(int x, int y, int width, int height, String description){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.description = description;
        this.curColor = new Color(0, 0, 0);
    }
    
    /**
     * This constructor creates a button with a specified x coordinate, y coordinate, width, height, and file (sprite)
     * @param x An integer representing the x coordinate for the top left corner of the button
     * @param y An integer representing the y coordinate for the top left corner of the button
     * @param width An integer representing the width of the button
     * @param height An integer representing the height of the button
     * @param file A file representing the sprite the sprite of the button
     */
    Button(int x, int y, int width, int height, File file){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setSprite(file);
    }

    /**
     * getX
     * This method returns an integer representing the x coordinate for the top left corner of the button
     * @return An integer representing the x coordinate for the top left corner of the button
     */
    public int getX(){
        return this.x;
    }

    /**
     * getY
     * This method returns an integer representing the y coordinate for the top left corner of the button
     * @return An integer representing the y coordinate for the top left corner of the button
     */
    public int getY(){
        return this.y;
    }

    /**
     * getWidth
     * This method returns an integer representing the width of the button
     * @return An integer representing the width of the button
     */
    public int getWidth(){
        return this.width;
    }

    /**
     * getHeight
     * This method returns an integer representing the height of the button
     * @return An integer representing the height of the button
     */
    public int getHeight(){
        return this.height;
    }

    /**
     * getDescription
     * This method returns a string representing the description of the button
     * @return A string representing the description of the button
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * getColor
     * This method returns a color representing the color of the button
     * @return A color representing the color of the button
     */
    public Color getColor(){
        return this.curColor;
    }

    /**
     * getSprite
     * This method returns a BufferedImage representing the sprite of the button
     * @return A BufferedImage representing the sprite of the button
     */
    public BufferedImage getSprite(){
        return this.sprite;
    }

    /**
     * setSprite
     * This method accepts a file and sets it as the sprite of the button
     * @param file A file representing the sprite of the button
     */
    public void setSprite(File file){
        try{
            this.sprite = ImageIO.read(file);
        } catch (Exception e){
            System.out.println("sprite not found");
        }
    }

    /**
     * setColorPressed
     * This method sets the color of the button to red
     */
    public void setColorPressed(){
        this.curColor = new Color(255, 0, 0);
    }

    /**
     * setColorRegular
     * This method sets the color of the button black
     */
    public void setColorRegular(){
        this.curColor = new Color(0, 0, 0);
    }

    /**
     * inRange
     * This method checks if a pair of x and y coordinates are in the area covered by the button
     * @param x An integer representing the x coordiante being checked
     * @param y An integer representing the y coordinate being checked
     * @return A boolean that is true if the coordinates are in range of the button and false if not 
     */
    public boolean inRange(int x, int y){
        return ((x >= this.getX()) && (x < this.getX()+this.getWidth()) && (y >= this.getY()) && (y < this.getY()+this.getHeight()));
    }

    /**
     * drawRegular
     * This method accepts a graphics object g, and draws the regular button without a sprite
     * @param g A graphics object g 
     */
    public void drawRegular(Graphics g){
        g.setColor(this.getColor());
        g.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        int stringWidth = g.getFontMetrics().stringWidth(this.getDescription());
        g.drawString(this.getDescription(), this.getX()+(this.getWidth()-stringWidth)/2, this.getY()+17);
    }

    /**
     * drawSprite
     * This method accepts a graphics object g, and draws the button with a sprite
     * @param g A graphics object g 
     */
    public void drawSprite(Graphics g){
        g.drawImage(this.getSprite(), this.getX(), this.getY(), null);
    }

}

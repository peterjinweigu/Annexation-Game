/**
 * [Player.java]
 * This is a class that represents a player
 * @author Edwin Sun
 * @version 1.0
 */

public class Player{

    /* Class Variables */
    private String name;
    private int playerID;
    private int gold;
    private int archerDamage = 7;
    private int cavalryDamage = 10;
    private int unitDamage = 7;
    private int archerHp = 10;
    private int cavalryHp = 20;
    private int unitHp = 15;
    private boolean defeated = false;
    private boolean crossSand = false;

    /**
     * Player
     * This constructor creates a player with the specified name, playerID, and gold
     * @param name A string representing the name of the player
     * @param playerID An integer representing the ID of the player
     * @param gold An integer reprsenting the amount of gold a player has
     */
    Player(String name, int playerID, int gold){
        this.name = name;
        this.playerID = playerID;
        this.gold = gold;
    }

    /**
     * getName
     * This method returns a string representing the name for the player
     * @return A string representing the name for the player
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * getPlayerID
     * This method returns an integer representing the player ID 
     * @return An integer representing the player ID 
     */
    public int getPlayerID(){
        return this.playerID;
    }

    /**
     * getGold
     * This method returns an integer representing the amount of gold a player has
     * @return An integer representing the amount of gold a player has
     */
    public int getGold(){
        return this.gold;
    }

    /**
     * getArcherDamage
     * This method returns an integer representing the amount of damage the player's archers do
     * @return An integer representing the amount of damage the player's archers do
     */
    public int getArcherDamage(){
        return this.archerDamage;
    }
    
    /**
     * getCavalryDamage
     * This method returns an integer representing the amount of damage the player's calvary do
     * @return An integer representing the amount of damage the player's cavalry do
     */
    public int getCavalryDamage(){
        return this.cavalryDamage;
    }

    /**
     * getUnitDamage
     * This method returns an integer representing the amount of damage the player's normal units do
     * @return An integer representing the amount of damage the player's normal units do
     */
    public int getUnitDamage(){
        return this.unitDamage;
    }

    /**
     * getArcherHp
     * This method returns an integer representing the amount of health points the player's archers have
     * @return An integer representing the amount of health points the player's archers have
     */
    public int getArcherHp(){
        return this.archerHp;
    }

    /**
     * getCavalryHp
     * This method returns an integer representing the amount of health points the player's cavlary have
     * @return An integer representing the amount of health points the player's cavalry have
     */
    public int getCavalryHp(){
        return this.cavalryHp;
    }

    /**
     * getUnitHp
     * This method returns an integer representing the amount of health points the player's normal units have
     * @return An integer representing the amount of health points the player's normal units have
     */
    public int getUnitHp(){
        return this.unitHp;
    }

    /**
     * canCrossSand
     * This method returns a boolean representing whether the player can cross sand tiles
     * @return A boolean representing whether the player can cross sand tiles
     */
    public boolean canCrossSand(){
        return this.crossSand;
    }

    /**
     * getDefeated
     * This method returns a boolean representing whether the player got defeated
     * @return A boolean representing whether the player got defeated
     */
    public boolean getDefeated(){
        return this.defeated;
    }

    /**
     * setGold
     * This method accepts an integer and sets it as the gold of the player
     * @param gold An integer representing the amount of gold to set
     */
    public void setGold(int gold){
        this.gold = gold;
    }

    /**
     * setArcherDamage
     * This method accepts an integer and sets it as the amount of damage the player's archers do
     * @param archerDamage An integer representing the amount of archer damage to set
     */
    public void setArcherDamage(int archerDamage){
        this.archerDamage = archerDamage;
    }

    /**
     * setCavalryDamage
     * This method accepts an integer and sets it as the amount of damage the player's cavalry do
     * @param cavlaryDamage An integer representing the amount of cavalry damage to set
     */
    public void setCavalryDamage(int cavalryDamage){
        this.cavalryDamage = cavalryDamage;
    }

    /**
     * setUnitDamage
     * This method accepts an integer and sets it as the amount of damage the player's normal units do
     * @param unitDamage An integer representing the amount of normal unit damage to set
     */
    public void setUnitDamage(int unitDamage){
        this.unitDamage = unitDamage;
    }

    /**
     * setArcherHp
     * This method accepts an integer and sets it as the amount of health points the player's archers have
     * @param archerHp An integer representing the amount of archer health points to set
     */
    public void setArcherHp(int archerHp){
        this.archerHp = archerHp;
    }

    /**
     * setCavalryHp
     * This method accepts an integer and sets it as the amount of health points the player's cavalry have
     * @param cavalryHp An integer representing the amount of cavalry health points to set
     */
    public void setCavalryHp(int cavalryHp){
        this.cavalryHp = cavalryHp;
    }

    /**
     * setUnitHp
     * This method accepts an integer and sets it as the amount of health points the player's normal units have
     * @param unitHp An integer representing the amount of normal unit health points to set
     */
    public void setUnitHp(int unitHp){
        this.unitHp = unitHp;
    }

    /**
     * setCrossSand
     * This method sets the "crossSand" variable to true, allowing the player to access sand tiles
     */
    public void setCrossSand(){
        this.crossSand = true;
    }

    /**
     * setDefeated
     * This method sets the "defeated" variable to true, marking the player as defeated
     */
    public void setDefeated(){
        this.defeated = true;
    }
}
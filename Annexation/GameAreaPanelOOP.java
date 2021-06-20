/** [GameAreaPanelOOP.java]
  * ICS3U6-B Final Project
  * This program incoporates topics from ICS3U6-B to create a turn-based strategy game
  * The game can support 2-4 players, and each player will have control of their own army
  * The goal of the game is to destroy all the other players (destroy buildings and units)
  * The last player remaining will be victorious
  * There are seperate folders and classes for different components of the game
  * @author Peter Gu and Edwin Sun
  * @version 1.0, 15 June 2021
  */


/* import statements */
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.image.BufferedImage; 
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Font;
import javax.sound.sampled.*;


class GameAreaPanelOOP extends JPanel {

  /* Class Variables */

  // Variables used in user interactions
  static int numPlayers;
  static int playersLeft;
  static int movingUnitX, movingUnitY;
  static int buildingBarracks = 0;
  static int curX, curY;
  static int curPlayer = 1;
  static int winPlayer;
  static int gameState = 1;
  static Barrack curBarrack;
  static boolean upgradingCity = false;
  static boolean buildingCity = false;
  static boolean ifBarracks = false;

  // ArrayLists
  static ArrayList<Unit> units[]; // units
  static ArrayList<City> cities[]; // cities
  static ArrayList<Barrack> barracks[]; // barracks
  static ArrayList<DamageEvent> damageEvents; // damage text
  static ArrayList<Projectile> projectiles; // projectiles from archers 

  // Arrays
  static Tile[][] grid; // tilemap
  static Player[] players; // all players
  static int[][] overlay; // temporary player interactions
  static int[][] buildingOverlay; // temporary player building interactions
  static int[][] dist; // distance array for movement
  static int[] directionX = {-1, 1, 0, 0, -1, 1, -1, 1}; // movement
  static int[] directionY = {0, 0, 1, -1, -1, 1, 1, -1}; // movement

  // Buttons (Menu and in-game)
  static Button endTurn = new Button(1320, 300, 200, 40, "End Turn");
  static Button buildCity = new Button(1320, 350, 200, 40, "Build City: 4 gold");
  static Button upgradeCity = new Button(1320, 400, 200, 40, "Upgrade City: 3 gold");
  static Button buildUnitBarrack = new Button(1320, 450, 200, 40, "Build Unit Barrack: 2 gold");
  static Button buildArcherBarrack = new Button(1320, 500, 200, 40, "Build Archer Barrack: 3 gold");
  static Button buildHorseBarrack = new Button(1320, 550, 200, 40, "Build Horse Barrack: 4 gold");
  static Button buySandAccess = new Button(1320, 600, 200, 40, "Buy Sand Access: 5 gold");
  static Button twoPlayerButton = new Button(250, 300, 1000, 150, new File("Sprites/TwoPlayerButton.png"));
  static Button threePlayerButton = new Button(250, 460, 1000, 150, new File("Sprites/ThreePlayerButton.png"));
  static Button fourPlayerButton = new Button(250, 620, 1000, 150, new File("Sprites/FourPlayerButton.png"));

  // Images (Background and overlay)
  static BufferedImage menuScreen, background, titleCard;
  static BufferedImage moveImage, attackImage, buildImage, breakImage;

  // Booleans to handle threading errors
  static boolean initCalled = false, gameStarted = false;

  // Audio variables
  static AudioInputStream audioStream;
  static Clip music;

  /**
     * GameAreaPanelOOP
     * Constructor
     */
  GameAreaPanelOOP() {
    
    // Initialize menu sprites
    try{
      menuScreen = ImageIO.read(new File("Sprites/Intro.jpg"));
      titleCard = ImageIO.read(new File("Sprites/Title.png"));
    } catch (Exception e){
      System.out.println("sprite not found");
    }

    // Initialize mouseListener
    MyMouseListener mouseListener = new MyMouseListener();
    this.addMouseListener(mouseListener);
    
    // JPanel Stuff
    this.setFocusable(true);
    this.requestFocusInWindow(); 
    
    // Start the game loop in a separate thread (allows simple frame rate control)
    // the alternate is to delete this and just call repaint() at the end of paintComponent()
    Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
    t.start();
  }

  /**
   * check
   * This method accepts two integer variables
   * @param x The integer index x
   * @param y The integer index y
   * @return true if both values are in range, false otherwise
   */
  public static boolean check(int x, int y){
    if ((x >= 1) && (x <= 25) && (y >= 1) && (y <= 15)){
      return true;
    }
    return false;
  }

  /**
   * drawOverlay
   * This method will draw an image based on the type of the overlay passed in
   * @param x The integer x coordinate
   * @param y The integer y coordinate
   * @param type The type of the overlay (integer)
   * @param g A graphics object g 
   */
  public static void drawOverlay(int x, int y, int type, Graphics g){
    if (type == 1) {
      g.drawImage(moveImage, x+5, y+5, null);
    } else if (type == 2) {
      g.drawImage(attackImage, x+5, y+5, null);
    } else if (type == 3) {
      g.drawImage(breakImage, x+5, y+5, null);
    } else if (type == 4) {
      g.drawImage(buildImage, x+5, y+5, null);
    }
  }

  /**
   * drawBuildingOverlay
   * This method will draw an image based on the type of the overlay passed in
   * @param x The integer x coordinate
   * @param y The integer y coordinate
   * @param type The type of the overlay (integer)
   * @param g A graphics object g 
   */
  public static void drawBuildingOverlay(int x, int y, int type, Graphics g){
    if (type == 1) {
      g.drawImage(buildImage, x+5, y+5, null);
    }
  }

  /**
   * clearBuildingOverlay
   * This method will clear the building overlay
   */
  public static void clearBuildingOverlay(){
    for (int i = 1; i <= 25; i++){
      for (int j = 1; j <= 15; j++){
        buildingOverlay[i][j] = 0;
      }
    }
  }

  /**
   * clearOverlay
   * This method will clear the overlay
   */
  public static void clearOverlay(){
    for (int i = 1; i <= 25; i++){
      for (int j = 1; j <= 15; j++){
        overlay[i][j] = 0;
      }
    }
  }

  /**
   * clearButtonPressed
   * This method will set all buttons to unpressed
   */
  public static void clearButtonPressed(){
    buildCity.setColorRegular();
    upgradeCity.setColorRegular();
    buildUnitBarrack.setColorRegular(); 
    buildArcherBarrack.setColorRegular(); 
    buildHorseBarrack.setColorRegular();
  }

  /**
   * fill
   * This method will fill the distance array
   */
  public static void fill(){
    for (int i = 1; i <= 25; i++){
      for (int j = 1; j <= 15; j++){
        dist[i][j] = 999;
      }
    }
  }

  /**
   * horseMove
   * This method will recurse to find all the movable positions for the horse object
   * @param idx1 An integer representing the first current index
   * @param idx2 An integer representing the second current index
   * @param playerType An integer representing the player id
   * @param len An integer representing the length traversed so far
   */
  public static void horseMove(int idx1, int idx2, int playerType, int len){
    if (grid[idx1][idx2].discoveredBy(playerType)){
      if ((len < dist[idx1][idx2]) && (len <= 2)){
        // Setting distance to shortest possible 
        dist[idx1][idx2] = len;

        // All conditions for travel (impassable tiles, blocking units, etc.)
        if ((grid[idx1][idx2].getPlayerOccupied() == 0) && (grid[idx1][idx2] instanceof Sand == false)){
          overlay[idx1][idx2] = 1; // free
        } else if ((grid[idx1][idx2].getPlayerOccupied() != playerType) && (grid[idx1][idx2].getTypeOccupied() >= 1) 
        && (len <= 1)){
          overlay[idx1][idx2] = 2; // enemy 
        } else if ((grid[idx1][idx2] instanceof Sand) && (players[playerType].canCrossSand()) 
        && (grid[idx1][idx2].getTypeOccupied() == 0)){
          overlay[idx1][idx2] = 1; // sand pass
        } else if ((grid[idx1][idx2].getPlayerOccupied() == playerType) && 
        (grid[idx1][idx2].getBuildingOccupied() > 0) && (grid[idx1][idx2].getTypeOccupied() == 0)){
          overlay[idx1][idx2] = 1; // own building
        } else if ((grid[idx1][idx2].getPlayerOccupied() != playerType) && 
        (grid[idx1][idx2].getBuildingOccupied() > 0) && (grid[idx1][idx2].getTypeOccupied() == 0)){
          overlay[idx1][idx2] = 3; // enemy building 
        } else{
          overlay[idx1][idx2] = -1; // other impassable tiles
        }
        if ((overlay[idx1][idx2] != -1) && (overlay[idx1][idx2] != 2)){
          // Loop through directions and recurse
          for (int i = 0; i < 8; i++){
            int x = idx1 + directionX[i], y = idx2 + directionY[i];
            if (check(x, y)){
              horseMove(x, y, playerType, len+1);
            }
          }
        }
      }
    }
  }

  /**
   * regMove
   * This method will loop to find all the movable positions for the unit and archer object
   * @param idx1 An integer representing the first index of the unit
   * @param idx2 An integer representing the second index of the unit
   * @param playerType An integer representing the player id
   */
  public static void regMove(int idx1, int idx2, int playerType){
    // Loop through directions and sprawl outwards
    for (int i = 0; i < 8; i++){
      int x = idx1 + directionX[i], y = idx2 + directionY[i];
      if (check(x, y)){
        // All conditions for travel (impassable tiles, blocking units, etc.)
        if ((grid[x][y].getPlayerOccupied() == 0) && (grid[x][y] instanceof Sand == false)){
          overlay[x][y] = 1; // free
        } else if ((grid[x][y].getPlayerOccupied() != playerType) && (grid[x][y].getTypeOccupied() >= 1)){
          overlay[x][y] = 2; // enemy 
        } else if ((grid[x][y] instanceof Sand) && (players[playerType].canCrossSand()) && (grid[x][y].getTypeOccupied() == 0)){
          overlay[x][y] = 1; // sand pass
        } else if ((grid[x][y].getPlayerOccupied() == playerType) && (grid[x][y].getBuildingOccupied() > 0) 
        && (grid[x][y].getTypeOccupied() == 0)){
          overlay[x][y] = 1; // own building
        } else if ((grid[x][y].getPlayerOccupied() != playerType) && (grid[x][y].getBuildingOccupied() > 0)
        && (grid[x][y].getTypeOccupied() == 0)){
          overlay[x][y] = 3; // enemy building 
        }
      }
    }
  }
  
  /**
   * attackMove
   * This method will loop to find all the attack positions for the unit and cavalry object
   * @param idx1 An integer representing the first index of the unit
   * @param idx2 An integer representing the second index of the unit
   * @param playerType An integer representing the player id
   */
  public static void attackMove(int idx1, int idx2, int playerType){
    // Loop through directions and sprawl outwards
    for (int i = 0; i < 8; i++){
      int x = idx1 + directionX[i], y = idx2 + directionY[i];
      if (check(x, y)){
        // Checking for other player units
        if ((grid[x][y].getPlayerOccupied() != playerType) && (grid[x][y].getTypeOccupied() >= 1)){
          overlay[x][y] = 2; // enemy 
        }
      }
    }
  }

  /**
   * rangeAttackMove
   * This method will recurse to find all the attack positions for the archer object
   * @param idx1 An integer representing the first current index
   * @param idx2 An integer representing the second current index
   * @param playerType An integer representing the player id
   * @param len An integer representing the length traversed so far
   */
  public static void rangeAttackMove(int idx1, int idx2, int playerType, int len){
    // Checking for other player units
    if ((len < dist[idx1][idx2]) && (len <= 3)){
      if ((grid[idx1][idx2].getPlayerOccupied() != playerType) && (grid[idx1][idx2].getTypeOccupied() >= 1) 
      && (grid[idx1][idx2].discoveredBy(playerType))){
        overlay[idx1][idx2] = 2; // enemy 
      } else{
        overlay[idx1][idx2] = -1; // not enemy
      }
      // Loop through directions and recurse
      for (int i = 0; i < 8; i++){
        int x = idx1 + directionX[i], y = idx2 + directionY[i];
        if (check(x, y)){
          rangeAttackMove(x, y, playerType, len+1);
        }
      }
    }
  }

  /**
   * buildRange
   * This method will loop to find all building positions 
   * @param playerType An integer representing the player id
   */
  public static void buildRange(int playerType){
    for (int i = 1; i <= 25; i++){
      for (int j = 1; j <= 15; j++){
        // Check that if the tile is valid
        if ((grid[i][j].getBuildingOccupied() == 0) && (grid[i][j] instanceof Sand == false)){
          if ((grid[i][j].getPlayerOccupied() == playerType) || (grid[i][j].getPlayerOccupied() == 0)){
            if (grid[i][j].discoveredBy(playerType)){
              buildingOverlay[i][j] = 1;
            }
          }
        }
      }
    }
  }

  /**
   * upgradeCityRange
   * This method will loop to find all cities available to upgrade
   * @param playerType An integer representing the player id
   */
  public static void upgradeCityRange(int playerType){
    for (int i = 1; i <= 25; i++){
      for (int j = 1; j <= 15; j++){
        // Check that if the tile is valid
        if ((grid[i][j].getBuildingOccupied() == 1) && (grid[i][j] instanceof Sand == false)){
          if (grid[i][j].getPlayerOccupied() == playerType){
            buildingOverlay[i][j] = 1;
          }
        }
      }
    }
  }

  /**
   * barracksRange
   * This method will loop to find all the possible positions to place a unit
   * @param idx1 An integer representing the first index of the barracks
   * @param idx2 An integer representing the second index of the barracks
   * @param playerType An integer representing the player id
   */
  public static void barracksRange(int idx1, int idx2, int playerType){
    // Loop through directions and sprawl
    for (int i = 0; i < 8; i++){
      int x = idx1 + directionX[i], y = idx2 + directionY[i];
      if (check(x, y)){
        // All conditions for place a unit (impassable tiles, blocking units, etc.)
        if (grid[x][y].discoveredBy(playerType)){
          if (grid[x][y].getPlayerOccupied() == 0 && grid[x][y] instanceof Sand == false){
            overlay[x][y] = 4; // free
          } else if ((grid[x][y] instanceof Sand) && (players[playerType].canCrossSand() && grid[x][y].getTypeOccupied() == 0)){
            overlay[x][y] = 4; // sand pass
          } else if ((grid[x][y].getPlayerOccupied() == playerType) && (grid[x][y].getBuildingOccupied() > 0)
           && (grid[x][y].getTypeOccupied() == 0)){
            overlay[x][y] = 4; // own building
          }
        }
      }
    }
  }

  /**
   * regExplore
   * This method will loop to find all the unexplored tiles from a point for the specified player 
   * @param idx1 An integer representing the first index of the specified point
   * @param idx2 An integer representing the second index of the specified point
   * @param playerType An integer representing the player id
   */
  public static void regExplore(int idx1, int idx2, int playerType){
    for (int i = 0; i < 8; i++){
      int x = idx1 + directionX[i], y = idx2 + directionY[i];
      if (check(x, y)){
        if (!grid[x][y].discoveredBy(playerType)){
          grid[x][y].append(playerType);
        }
      }
    }
  }

  /**
   * horseExplore
   * This method will loop to find all the unexplored tiles from a point for the specified player 
   * @param idx1 An integer representing the first index of the specified point
   * @param idx2 An integer representing the second index of the specified point
   * @param playerType An integer representing the player id
   */
  public static void horseExplore(int idx1, int idx2, int playerType){
    for (int i = idx1-2; i <= idx1+2; i++){
    for (int j = idx2-2; j <= idx2+2; j++){
      if (check(i, j)){
          if (!grid[i][j].discoveredBy(playerType)){
              grid[i][j].append(playerType);
          }
        }
      }
    }
  }

  /**
   * playSound
   * This method will play a sound based on the fileName
   * @param fileName A string which is the name of a sound file
   */
  public static void playSound(String fileName){
    try{
      // Load audio file
      File audioFile = new File(fileName);
      audioStream = AudioSystem.getAudioInputStream(audioFile);
      DataLine.Info info = new DataLine.Info(Clip.class, audioStream.getFormat());
      music = (Clip) AudioSystem.getLine(info);
      music.open(audioStream);
      music.start();
    } catch (Exception e){
      System.out.println("No audio file exists");
    }
  }

  // The main gameloop - this is where the game state is updated
  public void animate() { 
    
    while(true){

      /* Main Menu */
      if (gameState == 1){
        // Set number of players and call initialize game method
        if (twoPlayerButton.inRange(MyMouseListener.x, MyMouseListener.y)){
          numPlayers = 2;
          init();
          gameState = 2;
        }else if (threePlayerButton.inRange(MyMouseListener.x, MyMouseListener.y)){
          numPlayers = 3;
          init();
          gameState = 2;
        }else if (fourPlayerButton.inRange(MyMouseListener.x, MyMouseListener.y)){
          numPlayers = 4;
          init();
          gameState = 2;
        }
      
      /* Game */
      } else if (gameState == 2 && gameStarted){
        int thisPlayer = curPlayer;
        
        // Update game content
        if (MyMouseListener.clicked){

          // Buttons
          MyMouseListener.clicked = false;

          // End turn button
          if (endTurn.inRange(MyMouseListener.x, MyMouseListener.y)){
            
            // Reset the turn types of all the current player's units
            for (int i = 0; i < units[thisPlayer].size(); i++){
              units[thisPlayer].get(i).setTurnType(0);
            }

            clearOverlay();
            clearBuildingOverlay();
            clearButtonPressed();

            // Update the current player's gold based on their cities
            for (int i = 0; i < cities[thisPlayer].size(); i++){
              players[thisPlayer].setGold(players[thisPlayer].getGold()+cities[thisPlayer].get(i).getIncome());
            }
            
            // Check if a player is defeated - no cities, barracks, and units left
            for (int i = 1; i <= numPlayers; i++){
              if ((cities[i].size() == 0) && (units[i].size() == 0) && (barracks[i].size() == 0)){
                players[i].setDefeated();
              }
            }

            curPlayer++;
            if (curPlayer > numPlayers){
              curPlayer = 1;
            }

            // Find the next player that is not defeated to give the turn to
            while (players[curPlayer].getDefeated()){
              curPlayer++;
              if (curPlayer > numPlayers){
                curPlayer = 1;
              }
            }

            // Check the number of players left and keep track of the winner if there is one
            for (int i = 1; i <= numPlayers; i++){
              if (!players[i].getDefeated()){
                playersLeft += 1;
                winPlayer = players[i].getPlayerID();
              }
            }

            if (playersLeft == 1){
              gameState = 3;
            }
            playersLeft = 0;

          // Upgrade city button
          }else if ((upgradeCity.inRange(MyMouseListener.x, MyMouseListener.y)) && (players[thisPlayer].getGold() >= 3)){
            clearBuildingOverlay();
            clearOverlay();
            clearButtonPressed();
            upgradeCity.setColorPressed();
            upgradingCity = true;
            buildingCity = false;
            buildingBarracks = 0;
            upgradeCityRange(thisPlayer);
          
          // Build city button
          }else if ((buildCity.inRange(MyMouseListener.x, MyMouseListener.y)) && (players[thisPlayer].getGold() >= 4)){
            clearBuildingOverlay();
            clearOverlay();
            clearButtonPressed();
            buildCity.setColorPressed();
            upgradingCity = false;
            buildingCity = true;
            buildingBarracks = 0;
            buildRange(thisPlayer);

          // Build unit barrack button
          }else if ((buildUnitBarrack.inRange(MyMouseListener.x, MyMouseListener.y)) && (players[thisPlayer].getGold() >= 2)){
            clearBuildingOverlay();
            clearOverlay();
            clearButtonPressed();
            buildUnitBarrack.setColorPressed();
            upgradingCity = false;
            buildingCity = false;
            buildingBarracks = 1;
            buildRange(thisPlayer);

          // Build archer barrack button
          }else if ((buildArcherBarrack.inRange(MyMouseListener.x, MyMouseListener.y)) && (players[thisPlayer].getGold() >= 3)){
            clearBuildingOverlay();
            clearOverlay();
            clearButtonPressed();
            buildArcherBarrack.setColorPressed();
            upgradingCity = false;
            buildingCity = false;
            buildingBarracks = 3;
            buildRange(thisPlayer);

          // Build horse barrack button
          }else if ((buildHorseBarrack.inRange(MyMouseListener.x, MyMouseListener.y)) && (players[thisPlayer].getGold() >= 4)){
            clearBuildingOverlay();
            clearOverlay();
            clearButtonPressed();
            buildHorseBarrack.setColorPressed();
            upgradingCity = false;
            buildingCity = false;
            buildingBarracks = 2;
            buildRange(thisPlayer);

          // Buy sand access button
          }else if ((buySandAccess.inRange(MyMouseListener.x, MyMouseListener.y)) && (players[thisPlayer].getGold() >= 5)
          && (!players[thisPlayer].canCrossSand())){
            clearBuildingOverlay();
            clearOverlay();
            clearButtonPressed();
            
            // Update current player's gold and give them sand tile access
            players[thisPlayer].setGold(players[thisPlayer].getGold()-5);
            players[thisPlayer].setCrossSand();
          }
          
          if ((buildingCity == true) || (upgradingCity == true) || (buildingBarracks > 0)){

            // Loop through all grid cells and check if the tile is clicked
            for (int i = 1; i <= 25; i++){
              for (int j = 1; j <= 15; j++){
                if (grid[i][j].inRange(MyMouseListener.x, MyMouseListener.y)){
                  if (buildingOverlay[i][j] == 1){
                    grid[i][j].setPlayerOccupied(thisPlayer);

                    // Building city
                    if (buildingCity == true){
                      playSound("Sounds/Build.wav");
                      grid[i][j].setBuildingOccupied(1);

                      // Add the city for the current player and deduct gold required
                      cities[thisPlayer].add(new City(grid[i][j].getX(), grid[i][j].getY(), 2));
                      players[thisPlayer].setGold(players[thisPlayer].getGold()-4);

                    // Upgrading city
                    }else if (upgradingCity == true){
                      playSound("Sounds/Upgrade.wav");
                      players[thisPlayer].setGold(players[thisPlayer].getGold()-3);

                      // Loop through all cities of the current player and increase the income of the one on the current tile
                      for(int k = 0; k < cities[thisPlayer].size(); k++){
                        if (grid[i][j].inRange(cities[thisPlayer].get(k).getX(), cities[thisPlayer].get(k).getY())){
                          cities[thisPlayer].get(k).setIncome(cities[thisPlayer].get(k).getIncome()+1);
                        }
                      }
                    
                    // Building each type of barrack
                    }else if (buildingBarracks == 1){
                      playSound("Sounds/Build.wav");
                      grid[i][j].setBuildingOccupied(2);
                      barracks[thisPlayer].add(new Barrack(grid[i][j].getX(), grid[i][j].getY(), 1, 2)); // unit 
                      players[thisPlayer].setGold(players[thisPlayer].getGold()-2);
                    }else if (buildingBarracks == 3){
                      playSound("Sounds/Build.wav");
                      grid[i][j].setBuildingOccupied(2);
                      barracks[thisPlayer].add(new Barrack(grid[i][j].getX(), grid[i][j].getY(), 3, 3)); // archer
                      players[thisPlayer].setGold(players[thisPlayer].getGold()-3);
                    }else if (buildingBarracks == 2){
                      playSound("Sounds/Build.wav");
                      grid[i][j].setBuildingOccupied(2);
                      barracks[thisPlayer].add(new Barrack(grid[i][j].getX(), grid[i][j].getY(), 2, 5)); // horse/cavalry
                      players[thisPlayer].setGold(players[thisPlayer].getGold()-4);
                    }

                    // Reset all in-game button events and overlays
                    upgradingCity = false;
                    buildingCity = false;
                    buildingBarracks = 0;
                    clearBuildingOverlay();
                    clearOverlay();
                    clearButtonPressed();
                  }
                }
              }
            }
            // Reset in-game button events and overlays if a tile is not clicked
            if ((buildingCity == true) && (buildCity.inRange(MyMouseListener.x, MyMouseListener.y) == false)){
              buildingCity = false;
              clearBuildingOverlay();
              clearButtonPressed();
            }else if((upgradingCity == true) && (upgradeCity.inRange(MyMouseListener.x, MyMouseListener.y) == false)){
              upgradingCity = false;
              clearBuildingOverlay();
              clearButtonPressed();
            }else if((buildingBarracks == 1) && (buildUnitBarrack.inRange(MyMouseListener.x, MyMouseListener.y) == false)){
              buildingBarracks = 0;
              clearBuildingOverlay();
              clearButtonPressed();
            }else if ((buildingBarracks == 2) && (buildHorseBarrack.inRange(MyMouseListener.x, MyMouseListener.y) == false)){
              buildingBarracks = 0;
              clearBuildingOverlay();
              clearButtonPressed();
            }else if ((buildingBarracks == 3) && (buildArcherBarrack.inRange(MyMouseListener.x, MyMouseListener.y) == false)){
              buildingBarracks = 0;
              clearBuildingOverlay();
              clearButtonPressed();
            }
            
          // If building city, building barracks, and upgrading cities is false, then run code below
          }else {
            for (int i = 1; i <= 25; i++){
              for (int j = 1; j <= 15; j++){
                if (grid[i][j].inRange(MyMouseListener.x, MyMouseListener.y)){
                  if (overlay[i][j] == 0){
                    movingUnitX = 0;
                    movingUnitY = 0;
                    clearOverlay();
                  } else {
                    for (int k = units[thisPlayer].size() - 1; k >= 0; k--){
                      Unit unit = units[thisPlayer].get(k);
                      
                      // Check if the current unit is preparing to move/attack
                      if ((unit.getX() == movingUnitX) && (unit.getY() == movingUnitY)){

                        // Check if the current tile contains an enemy unit
                        if (overlay[i][j] == 2){

                          // Clear overlay and update the unit's turn type to attack
                          clearOverlay();
                          units[thisPlayer].get(k).setTurnType(2);

                          // Make sure that the projectiles arraylist is not getting  
                          // modified elsewhere when projectiles are being added 
                          synchronized(projectiles){

                            // Make sure that the damageEvents arraylist is not getting  
                            // modified elsewhere when damageEvents are being added 
                            synchronized(damageEvents){

                              // Loop through the units of all players and check which enemy unit is being attacked
                              for (int p = 1; p <= numPlayers; p++){
                                for (int h = units[p].size() - 1; h >= 0; h--){
                                  Unit enemy = units[p].get(h);
                                  try{
                                    if (grid[i][j].inRange(enemy.getX(), enemy.getY())){
                                      damageEvents.add(new DamageEvent(enemy.getX(), enemy.getY(), unit.getDamage()));

                                      // Add a projectile to draw if the attacking unit is an archer
                                      if (unit instanceof Archer){
                                        playSound("Sounds/Arrow.wav");
                                        int xShift = enemy.getX() - unit.getX();
                                        int yShift = enemy.getY() - unit.getY();
                                        xShift /= 10;
                                        yShift /= 10;
                                        projectiles.add(new Projectile(unit.getX(), unit.getY(), xShift, yShift, enemy.getX(), enemy.getY()));
                                      }else {
                                        playSound("Sounds/Sword.wav");
                                      }

                                      // Update enemy unit health points
                                      enemy.setHp(enemy.getHp()-unit.getDamage());

                                      // Remove enemy unit if it has no health points and update the player and type occupied
                                      if (enemy.getHp() <= 0){
                                        units[p].remove(h);
                                        if (grid[i][j].getBuildingOccupied() == 0){
                                          grid[i][j].setPlayerOccupied(0);
                                        }
                                        grid[i][j].setTypeOccupied(0);
                                        
                                      // If the enemy unit survives the attack, make it attack back
                                      }else {
                                        if (unit instanceof Archer){
                                          
                                          // If the enemy is an archer then add a projectile to be drawn 
                                          if (enemy instanceof Archer){
                                            playSound("Sounds/Arrow.wav");
                                            int xShift = unit.getX() - enemy.getX();
                                            int yShift = unit.getY() - enemy.getY();
                                            xShift /= 10;
                                            yShift /= 10;
                                            projectiles.add(new Projectile(enemy.getX(), enemy.getY(), xShift, yShift, unit.getX(), unit.getY()));

                                            // Add damage event to display amount of damage taken
                                            damageEvents.add(new DamageEvent(unit.getX(), unit.getY(), enemy.getDamage()));
                                              
                                            // Update original unit's health points after it got attacked back
                                            unit.setHp(unit.getHp()-enemy.getDamage());

                                          // Check if the enemy is not an archer but it can still reach the archer to attack back
                                          } else if (Math.max(Math.abs(unit.getX()-enemy.getX()), Math.abs(unit.getY()-enemy.getY())) == 50){
                                            playSound("Sounds/Sword.wav");

                                            // Add damage event to display amount of damage taken
                                            damageEvents.add(new DamageEvent(unit.getX(), unit.getY(), enemy.getDamage()));
                                              
                                            // Update original unit's health points after it got attacked back
                                            unit.setHp(unit.getHp()-enemy.getDamage());
                                          }
                                        }else {
                                          
                                          // Archers will attack back if melee units (unit, cavalry) attack them
                                          if (enemy instanceof Archer){
                                            playSound("Sounds/Arrow.wav");
                                            int xShift = unit.getX() - enemy.getX();
                                            int yShift = unit.getY() - enemy.getY();
                                            xShift /= 10;
                                            yShift /= 10;
                                            projectiles.add(new Projectile(enemy.getX(), enemy.getY(), xShift, yShift, unit.getX(), unit.getY()));
                                          }else {
                                            playSound("Sounds/Sword.wav");
                                          }
                                          
                                          // Add damage event to display amount of damage taken
                                          damageEvents.add(new DamageEvent(unit.getX(), unit.getY(), enemy.getDamage()));
                                          
                                          // Update original unit's health points after it got attacked back
                                          unit.setHp(unit.getHp()-enemy.getDamage());
                                        }   
                                        
                                        // If the original attacking unit's has no health points, remove it, update the player and type occupied
                                        if (unit.getHp() <= 0){
                                          units[thisPlayer].remove(k);
                                          if (grid[curX][curY].getBuildingOccupied() == 0){
                                            grid[curX][curY].setPlayerOccupied(0);
                                          }
                                          grid[curX][curY].setTypeOccupied(0);
                                        }
                                      }
                                    }
                                  } catch (Exception e){
                                    System.out.println("Adding Error");
                                    System.out.println(damageEvents.size());
                                    System.out.println(projectiles.size());
                                    e.printStackTrace();
                                  } 
                                }
                              }
                            }
                          }

                        // Check if current tile is free or there is an enemy building
                        }else if (overlay[i][j] == 1 || overlay[i][j] == 3){
                          
                          // Set player and type occupied
                          if (grid[curX][curY].getBuildingOccupied() == 0){
                            grid[curX][curY].setPlayerOccupied(0);
                          }
                          grid[curX][curY].setTypeOccupied(0);
                          
                          // Check if the tile contains an enemy building
                          if (overlay[i][j] == 3){
                            playSound("Sounds/Destruction.wav");
                            
                            // Remove enemy city 
                            if (grid[i][j].getBuildingOccupied() == 1){
                              for (int f = cities[grid[i][j].getPlayerOccupied()].size() - 1; f >= 0; f--){
                                City c = cities[grid[i][j].getPlayerOccupied()].get(f);
                                if (grid[i][j].inRange(c.getX(), c.getY())){
                                  cities[grid[i][j].getPlayerOccupied()].remove(f);
                                }
                              }
                            }

                            // Remove enemy barrack
                            if (grid[i][j].getBuildingOccupied() == 2){
                              for (int f = barracks[grid[i][j].getPlayerOccupied()].size() - 1; f >= 0; f--){
                                Barrack b = barracks[grid[i][j].getPlayerOccupied()].get(f);
                                if (grid[i][j].inRange(b.getX(), b.getY())){
                                  barracks[grid[i][j].getPlayerOccupied()].remove(f);
                                }
                              }
                            }

                            // Set building as unoccupied since it is destroyed
                            grid[i][j].setBuildingOccupied(0);
                          }

                          // Unit movement
                          playSound("Sounds/Move.wav");
                          unit.setNumMoves(25);

                          // Set destination coordinates of where the unit is moving
                          unit.setXDest(grid[i][j].getX());
                          unit.setYDest(grid[i][j].getY());
                          
                          // Calculate the amount to shift in x and y coordinates when unit is moving
                          int xShift = unit.getXDest() - unit.getX();
                          int yShift = unit.getYDest() - unit.getY();
                          xShift /= 25;
                          yShift /= 25;

                          // Set direction the unit is supposed to face
                          if (xShift < 0){
                            unit.setDirection(2);
                          } else if (xShift > 0) {
                            unit.setDirection(1);
                          }
                          
                          // Allow the unit to move
                          unit.updateMove((int)xShift, (int)yShift);
                          grid[i][j].setPlayerOccupied(thisPlayer);

                          // Set unit type occupied
                          if (unit instanceof Archer){
                            grid[i][j].setTypeOccupied(3);
                          } else if (unit instanceof Cavalry){
                            grid[i][j].setTypeOccupied(2);
                          } else{
                            grid[i][j].setTypeOccupied(1);
                          }

                          // The unit cannot move anymore and the moving overlay is cleared
                          unit.setTurnType(1);
                          clearOverlay();
                        } 
                      }
                    }

                    // Check if the player chose to spawn a type of unit adjacent to a barrack
                    if (overlay[i][j] == 4){
                      playSound("Sounds/Move.wav");
                      players[thisPlayer].setGold(players[thisPlayer].getGold() - curBarrack.getCost());
                      grid[i][j].setPlayerOccupied(thisPlayer);
                      grid[i][j].setTypeOccupied(curBarrack.getType());

                      // Add the correct type of unit into the units arraylist for the current player
                      units[thisPlayer].add(curBarrack.createUnit(players[thisPlayer], grid[i][j].getX(), grid[i][j].getY()));
                      clearOverlay();
                    }
                  }
                  
                  // If the tile clicked contains the current player's unit
                  if ((grid[i][j].getTypeOccupied() >= 1) && (grid[i][j].getPlayerOccupied() == thisPlayer)){ // unit
                    clearOverlay();

                    // Loop through current player's units and check if it is moving or attacking
                    for (int p = 0; p < units[thisPlayer].size(); p++){
                      Unit unit = units[thisPlayer].get(p);
                      if (grid[i][j].inRange(unit.getX(), unit.getY())){
                        
                        // Unit is able to move
                        if (unit.getTurnType() == 0){
                          movingUnitX = unit.getX();
                          movingUnitY = unit.getY();
                          curX = i;
                          curY = j;
                          if (grid[i][j].getTypeOccupied() == 2){ // horse
                            fill();
                            for (int k = 0; k < 8; k++){
                              int x = i + directionX[k], y = j + directionY[k];
                              if (check(x, y)){
                                  horseMove(x, y, thisPlayer, 1);
                              }
                            }
                          }else { // unit and archer
                            regMove(i, j, thisPlayer);
                          }
                        }

                        // Unit is only able to attack
                        if (unit.getTurnType() == 1){
                          movingUnitX = unit.getX();
                          movingUnitY = unit.getY();
                          curX = i;
                          curY = j;
                          if (grid[i][j].getTypeOccupied() == 3){ // archer
                            fill();
                            // Use rangeAttackMove to recurse and find the attack positions
                            for (int k = 0; k < 8; k++){
                              int x = i + directionX[k], y = j + directionY[k];
                              if (check(x, y)){
                                  rangeAttackMove(x, y, thisPlayer, 1);
                              }
                            }
                          }else { // unit and horse
                            attackMove(i, j, thisPlayer);
                          }
                        }
                      }
                    }
                  // If the tile clicked contains the current players barracks
                  } else if ((grid[i][j].getBuildingOccupied() == 2) && (grid[i][j].getPlayerOccupied() == thisPlayer)){
                    clearOverlay();
                    for (int k = 0; k < barracks[thisPlayer].size(); k++){
                      Barrack barrack = barracks[thisPlayer].get(k);

                      // Find the specific barrack and check if the player can afford a unit
                      if (grid[i][j].inRange(barrack.getX(), barrack.getY())){
                        if (players[thisPlayer].getGold() >= barrack.getCost()){
                          curBarrack = barrack;
                          barracksRange(i, j, thisPlayer);
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }

        // Catch any threading related errors (where an array is edited more than once at the same time)
        try{
          // Loop through current player's units and move them if they are in movement
          for (int i = 0; i < units[thisPlayer].size(); i++){
            if (i >= units[thisPlayer].size()){
              break;
            }
            Unit unit = units[thisPlayer].get(i);
            if (unit.getNumMoves() <= 0){
              unit.updateMove(0, 0);
              unit.setX(unit.getXDest());
              unit.setY(unit.getYDest());
              // Once the unit reaches destination, explore the neighbouring land
              if (unit instanceof Cavalry){
                horseExplore(unit.getX()/50, (unit.getY()+10)/50, thisPlayer);
              } else{
                regExplore(unit.getX()/50, (unit.getY()+10)/50, thisPlayer);
              }
            } else {
              unit.move();
              unit.setNumMoves(unit.getNumMoves()-1);
            }
          }

          /* Loop through damageEvents
           * Once timer is over, remove object
           */
          synchronized(damageEvents){
            for (int i = damageEvents.size()-1; i >= 0; i--){
              if (damageEvents.get(i) != null){
                if (damageEvents.get(i).getTimer() <= 0){
                  damageEvents.remove(i);
                }else {
                  damageEvents.get(i).setTimer(damageEvents.get(i).getTimer()-1);
                }
              }
            }
          }

          /* Loop through projectiles and move if timer is not over
           * Once timer is over, remove object
           */
          synchronized(projectiles){
            for (int i = projectiles.size() - 1; i >= 0; i--){
              if (projectiles.get(i) != null){
                if (projectiles.get(i).getNumMoves() <= 0){
                  projectiles.remove(i);
                }else {
                  projectiles.get(i).move();
                  projectiles.get(i).setNumMoves(projectiles.get(i).getNumMoves()-1);
                }
              }
            }
          }
        } catch (Exception e){
          System.out.println(damageEvents.size());
          System.out.println(projectiles.size());
          e.printStackTrace();
          System.out.println("Removing Error");
            
        }
      }
      // delay
      try{Thread.sleep(10);} 
      catch (Exception exc){
        System.out.println("Thread Error");
      }
      
      // repaint request
      this.repaint();
    }    
  }

  /**
   * init
   * This method will initialize the game once the player chooses the number of players
   */
  public void init(){

    // Prevents init from being called twice
    if (!initCalled) initCalled = true;
    else return;
    
    // Initialize arraylists
    players = new Player[numPlayers+1];
    units = new ArrayList[numPlayers+1];
    cities = new ArrayList[numPlayers+1];
    barracks = new ArrayList[numPlayers+1];
    damageEvents = new ArrayList<DamageEvent>();
    projectiles = new ArrayList<Projectile>();

    // Initialize arrays
    players = new Player[numPlayers+1];
    grid = new Tile[26][16];
    overlay  = new int[26][16];
    dist = new int[26][16];
    buildingOverlay = new int[26][16];

    // Load images
    try {
      background = ImageIO.read(new File("Sprites/SkyBackground.png"));
      moveImage = ImageIO.read(new File("Sprites/Move.png"));
      attackImage = ImageIO.read(new File("Sprites/Attack.png"));
      buildImage = ImageIO.read(new File("Sprites/Build.png"));
      breakImage = ImageIO.read(new File("Sprites/Break.png"));
    } catch(Exception e) { 
      System.out.println("error loading sprites");
    }
    
    // Creating players based on number of players
    for (int i = 1; i <= numPlayers; i++){
        units[i] = new ArrayList<Unit>();
        cities[i] = new ArrayList<City>();
        barracks[i] = new ArrayList<Barrack>();
        String s = "Player "+i;
        players[i] = new Player(s, i, 10);
    }

    // Generate tile map
    for(int i = 1; i <= 25; i++){
        for(int j = 1; j <= 15; j++){
            int randomNum = ThreadLocalRandom.current().nextInt(1, 11);
            if (randomNum == 1){
                grid[i][j] = new Sand(i*50, j*50 - 10); // Sand tiles will be randomly generated
            } else {
                grid[i][j] = new Tile(i*50, j*50 - 10);
            }
        }
    }
    
    // Generate the first unit and city for each player
    for (int i = 1; i <= numPlayers; i++){
        int randomX, randomY;
        if (i == 1){
            randomX = ThreadLocalRandom.current().nextInt(1, 6);
            randomY = ThreadLocalRandom.current().nextInt(1, 4);
        } else if (i == 2){
            randomX = ThreadLocalRandom.current().nextInt(20, 26);
            randomY = ThreadLocalRandom.current().nextInt(10, 16);
        } else if (i == 3){
            randomX = ThreadLocalRandom.current().nextInt(1, 6);
            randomY = ThreadLocalRandom.current().nextInt(10, 16);
        } else{
            randomX = ThreadLocalRandom.current().nextInt(20, 26);
            randomY = ThreadLocalRandom.current().nextInt(1, 4);
        }
        if (grid[randomX][randomY] instanceof Sand){
            grid[randomX][randomY] = new Tile(randomX*50, randomY*50-10);
        }
        grid[randomX][randomY].setPlayerOccupied(i);
        grid[randomX][randomY].setTypeOccupied(1);
        grid[randomX][randomY].append(i);
        regExplore(randomX, randomY, i);
        units[i].add(new Unit(randomX*50, randomY*50-10, players[i].getUnitDamage(), players[i].getUnitHp(), 0));
        grid[randomX][randomY].setBuildingOccupied(1);
        cities[i].add(new City(randomX*50, randomY*50-10, 3));
    }
    gameStarted = true; // Start the game
  }

  /**
   * paintComponent
   * This method is used to draw the shapes onto the graphics panel
   * @param g the graphics object to draw with
   */
  public void paintComponent(Graphics g) { // paintComponnent Runs everytime the screen gets refreshed

    int thisPlayer = curPlayer;
    super.paintComponent(g); // required
    setDoubleBuffered(true);

    // Main Menu
    if (gameState == 1){
      
      // Draw the background, title, buttons
      g.drawImage(menuScreen, 0, 0, null);
      g.drawImage(titleCard, 50, 30, null);
      twoPlayerButton.drawSprite(g);
      threePlayerButton.drawSprite(g);
      fourPlayerButton.drawSprite(g);
    
    // Game
    } else if (gameState == 2 && gameStarted){
      
      // Draw the background and buttons
      g.setFont(new Font("Monospaced", Font.PLAIN, 10)); 
      g.drawImage(background, 0, 0, null);  
      endTurn.drawRegular(g);
      buildCity.drawRegular(g);
      upgradeCity.drawRegular(g);
      buildUnitBarrack.drawRegular(g);
      buildArcherBarrack.drawRegular(g);
      buildHorseBarrack.drawRegular(g);
      buySandAccess.drawRegular(g);
      
      // Draw the stats
      g.setFont(new Font("Monospaced", Font.PLAIN, 15)); 
      g.drawString(players[thisPlayer].getName(), 1385, 250);
      g.drawString("Gold " + players[thisPlayer].getGold(), 1390, 270);
      
      // Draw the tiles
      for (int i = 1; i <= 25; i++){
        for (int j = 1; j <= 15; j++){
          grid[i][j].draw(g);
        }
      }

      // Draw the players buildings and units
      for (int j = 1; j <= numPlayers; j++){
        for (int i = 0; i < cities[j].size(); i++){ // Cities
          if (cities[j].get(i) != null){
            cities[j].get(i).draw(g);
          }
        }
        for (int i = 0; i < barracks[j].size(); i++){ // Barracks
          if (barracks[j].get(i) != null){
            barracks[j].get(i).draw(g);
          }
        }
        for (int i = 0; i < units[j].size(); i++){ // Units
          if (units[j].get(i) != null){
            // Coloured identifiers
            if (j == 1){
              g.setColor(new Color(255, 0, 0));
            }else if (j == 2){
              g.setColor(new Color(253, 41, 238));
            }else if (j == 3){
              g.setColor(new Color(255, 255, 0));
            }else{
              g.setColor(new Color(26, 161, 255));
            }
            int [] xPoints = {units[j].get(i).getX()+18, units[j].get(i).getX()+23, units[j].get(i).getX()+28};
            int [] yPoints = {units[j].get(i).getY()-7, units[j].get(i).getY()-2, units[j].get(i).getY()-7};
            g.fillPolygon(xPoints, yPoints, xPoints.length);

            units[j].get(i).draw(g);
          }
        }
      }
      
      // Draw the overlay
      for (int i = 1; i <= 25; i++){
        for (int j = 1; j <= 15; j++){
          if(overlay[i][j] >= 1){
            drawOverlay(grid[i][j].getX(), grid[i][j].getY(), overlay[i][j], g);
          }
        }
      }

      // Draw the clouds
      for (int i = 1; i <= 25; i++){
        for (int j = 1; j <= 15; j++){
          if(grid[i][j].discoveredBy(thisPlayer) == false){
            grid[i][j].drawCloud(g);
          }
        }
      }

      // Draw the building overlay
      for (int i = 1; i <= 25; i++){
        for (int j = 1; j <= 15; j++){
          if (buildingOverlay[i][j] == 1){
            drawBuildingOverlay(grid[i][j].getX(), grid[i][j].getY(), buildingOverlay[i][j], g);
          }
        }
      }

      // Draw the damage events
      for (int i = 0; i < damageEvents.size(); i++){
        g.setFont(new Font("Stencil", Font.PLAIN, 25)); 
        g.setColor(new Color (250, 0, 0));
        if (damageEvents.get(i) != null){
          damageEvents.get(i).draw(g);
        }
      }
      
      // Draw the projectiles
      for (int i = 0; i < projectiles.size(); i++){
        g.setColor(new Color(0, 0, 0));
        if (projectiles.get(i) != null){
          projectiles.get(i).draw(g);
        }
      }
    
    // Game over
    } else if (gameState == 3){
      g.setColor(new Color(0, 0, 0));
      g.fillRect(0, 0, 1700, 900);
      g.setColor(new Color(255, 255, 255));
      g.setFont(new Font("Stencil", Font.PLAIN, 50)); 
      g.drawString("Player " + winPlayer + " WINS", 600, 400);
    }
  }   
}


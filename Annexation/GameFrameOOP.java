/** [GameFrameOOP.java]
  * ICS3U6-B Final Project
  * This class will generate a gamePanel (GameAreaPanelOOP.java)
  * @author Peter Gu and Edwin Sun
  * @version 1.0, 15 June 2021
  */

/* import statements */
import javax.swing.JFrame;
import java.io.File;
import javax.sound.sampled.*;

class GameFrameOOP extends JFrame { 
  
  // Game Screen
  static GameAreaPanelOOP gamePanel;

  // Music
  static AudioInputStream audioStream;
  static Clip music;

    /**
     * GameFrameOOP
     * Constructor
     */
  GameFrameOOP() { 
    super("Annexation");  
    
    // Set the frame to full screen 
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(1700, 900);
    
    // Set up the game panel (where we put our graphics)
    gamePanel = new GameAreaPanelOOP();
    this.add(new GameAreaPanelOOP());
    
    this.setFocusable(false); // we will focus on the JPanel
    this.setVisible(true);    
  }
    /** *** Main method *** **/ 
    public static void main(String[] args) throws Exception{
    // Load music
    try{
      File audioFile = new File("Music/Intro.wav");
      audioStream = AudioSystem.getAudioInputStream(audioFile);
      music = AudioSystem.getClip();
      music.open(audioStream);
    } catch (Exception e){
      System.out.println("No audio file exists");
    }

    music.start();
    music.loop(Clip.LOOP_CONTINUOUSLY);

    // Create frame
    new GameFrameOOP();
  }
}





/**
 * [MyMouseListener.java]
 * This is the MouseListener class and is used to obtain mouse events
 * @author Peter Gu and Edwin Sun
 * @version 1.0 
 */

/* import statements */
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class MyMouseListener implements MouseListener {

  /* Class Variables */
  public static boolean clicked = false;
  public static int x, y;
  
  /**
   * mouseClicked
   * This method gets the mouseClicked event
   * @param e A MouseEvent
   */
  public void mouseClicked(MouseEvent e) {
    x = e.getX();
    y = e.getY();
    clicked = true;
  }
  
  /**
   * mousePressed
   * This method gets the mousePressed event
   * @param e A MouseEvent
   */
  public void mousePressed(MouseEvent e) {
  }
  
  /**
   * mouseReleased
   * This method gets the mouseReleased event
   * @param e A MouseEvent
   */
  public void mouseReleased(MouseEvent e) {
  }
  
  /**
   * mouseEntered
   * This method gets the mouseEntered event
   * @param e A MouseEvent
   */
  public void mouseEntered(MouseEvent e) {
  }
  
  /**
   * mouseExited
   * This method gets the mouseExited event
   * @param e A MouseEvent
   */
  public void mouseExited(MouseEvent e) {
  }
}


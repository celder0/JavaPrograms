import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The WatchMe class is an applet that creates a window and has eyes follow the cursor
 * when it is in the window
 */

public class WatchMe extends JApplet
{
  private int firstEyeX = 30;
  private int firstEyeY = 40;
  private int secondEyeX = 90;
  private int secondEyeY = 40;
  private int firstPupilX = firstEyeX + 10;
  private int firstPupilY = firstEyeY + 20;
  private int secondPupilX = secondEyeX + 10;
  private int secondPupilY = secondEyeY + 20;
  final double proportion = 0.25;
  
  /**
   * init method
   */
  
  public void init()
  {
    // Set background color to white
    getContentPane().setBackground(Color.white);
    
    // Add a mouse listener to this applet
    addMouseListener(new MyMouseListener());
    
    // Add a mouse motion listener to this applet
    addMouseMotionListener(new MyMouseMotionListener());
  }
  
  /**
   * createEyes method
   * @param g The applet's graphic object
   */
  
  public void paint(Graphics g)
  {
    // Call the superclass paint method
    super.paint(g);
    
    // Draw empty eyes
    g.setColor(Color.black);
    g.drawOval(firstEyeX,firstEyeY,40,80);
    g.drawOval(secondEyeX,secondEyeY,40,80);
    
    // Draw pupils
    g.fillOval(firstPupilX,firstPupilY,20,40);
    g.fillOval(secondPupilX,secondPupilY,20,40);  
  }
  
  /**
   * Private inner class that handles mouse events.
   * When the mouse exits, the eyes look forward
   */
  
  private class MyMouseListener extends MouseAdapter
  {
    public void mouseExited(MouseEvent e)
    {
      // Draw pupils
      firstPupilX = firstEyeX + 10;
      firstPupilY = firstEyeY + 20;
      secondPupilX = secondEyeX + 10;
      secondPupilY = secondEyeY + 20;
      
      repaint();
    }
  }
  
  /**
   * Private inner class that handles mouse motion events
   * When the mouse is moved within the window the eyes follow it
   */
  
  private class MyMouseMotionListener extends MouseMotionAdapter
  {
    public void mouseMoved(MouseEvent e)
    {
      double eyeProportionX;
      double eyeProportionY;
      eyeProportionX = e.getX()*proportion;
      eyeProportionY = e.getY()*proportion;
      firstPupilX = (int) (eyeProportionX);
      firstPupilX = firstEyeX + firstPupilX;
      firstPupilY = (int) (eyeProportionY);
      firstPupilY = firstPupilY + firstEyeY;
      secondPupilX = (int) (eyeProportionX);
      secondPupilX = secondPupilX + secondEyeX - 20;
      secondPupilY = (int) (eyeProportionY);
      secondPupilY = secondPupilY + secondEyeY;
      
      if(firstPupilX < 30)
      {
        firstPupilX = 30;
      }
      else if(firstPupilX > 50)
      {
        firstPupilX = 50;
      }
      
      if(firstPupilY < 40)
      {
        firstPupilY = 40;
      }
      else if(firstPupilY > 80)
      {
        firstPupilY = 80;
      }
      
      if(secondPupilX < 90)
      {
        secondPupilX = 90;
      }
      else if(secondPupilX > 110)
      {
        secondPupilX = 110;
      }
      
      
      if(secondPupilY < 40)
      {
        secondPupilY = 40;
      }
      else if(secondPupilY > 80)
      {
        secondPupilY = 80;
      }
      repaint();
    }
  } 
}
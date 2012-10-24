package Main;


import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * 
 * JFrameShower. 
 * Zeigt die JFrames an.
 * 
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date:  $ UTC
 */
public class JFrameShower implements Runnable 
{
   private final JFrame frame;
   
   /**
    * 
    * Konstruktor.
    *
    * @param frame  Frame 
    */
   public JFrameShower(JFrame frame) 
   {
      if (frame == null)
      {
         throw new NullPointerException("Jframe = null!");
      }
      this.frame = frame;
      EventQueue.invokeLater(this);
   }
   
   /**
    * Macht den JFrame sichtbar.
    */
   public void run() 
   {
     frame.setVisible(true);
   }
}
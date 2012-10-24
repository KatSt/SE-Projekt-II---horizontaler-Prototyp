package Listener;
import java.awt.Color;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Windows.EditProjektWindow;



/**
 * 
 * ChangeListener, der die Farbe betrachteter Tabs ändert.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 04.10.2012 $ UTC
 */
public class TabChangeListener implements ChangeListener
{   
   private EditProjektWindow projektWindow;
   /**
    * 
    * Konstruktor.
    *
    */
   public TabChangeListener(EditProjektWindow projektWindow)
   {
      super();
      this.projektWindow = projektWindow;
   }
   
   /**
    * Färbt den TabHeader betrachteter Tabs ein.
    */
   @Override
   public void stateChanged(ChangeEvent e)
   {
      JTabbedPane pane = (JTabbedPane) e.getSource();
      Color color = new Color(102, 205, 0, 100);
      pane.setBackgroundAt(pane.getSelectedIndex(), color);
      if(pane.getSelectedIndex() == 0)
      {
         projektWindow.setTab1(true);
      }
      if(pane.getSelectedIndex() == 1)
      {
         projektWindow.setTab2(true);
      }
      if(pane.getSelectedIndex() == 2)
      {
         projektWindow.setTab3(true);
      }
      if(pane.getSelectedIndex() == 3)
      {
         projektWindow.setTab4(true);
      }
      if(pane.getSelectedIndex() == 4)
      {
         projektWindow.setTab5(true);
      }
      if(pane.getSelectedIndex() == 5)
      {
         projektWindow.setTab6(true);
      }
   }

}

package Listener;
import java.awt.Color;
import java.util.HashMap;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



/**
 * 
 * ChangeListener, der die Farbe betrachteter Tabs ändert.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 04.10.2012 $ UTC
 */
public class TabChangeListener implements ChangeListener
{   
   private HashMap<Integer, Boolean> tabs;
   /**
    * 
    * Konstruktor.
    *
    */
   public TabChangeListener(HashMap<Integer, Boolean> tabs)
   {
      super();
      this.tabs = tabs;
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
      if(pane.getSelectedIndex() == 1)
      {
         tabs.put(1, true);
      }
      if(pane.getSelectedIndex() == 2)
      {
         tabs.put(2, true);
      }
      if(pane.getSelectedIndex() == 3)
      {
         tabs.put(3, true);
      }
      if(pane.getSelectedIndex() == 4)
      {
         tabs.put(4, true);
      }
      if(pane.getSelectedIndex() == 5)
      {
         tabs.put(5, true);
      }
   }

}

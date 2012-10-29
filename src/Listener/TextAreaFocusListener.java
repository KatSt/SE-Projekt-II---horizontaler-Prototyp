package Listener;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * 
 * Ändert die Größe zweier JScrollPane mit TextArea, wenn eine den Focus erhält.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 26.10.2012 $ UTC
 */
public class TextAreaFocusListener implements FocusListener
{
   /**
    * 1. JScrollPane
    */
   private JScrollPane scrollPane1;
   /**
    * 2. JScrollPane
    */
   private JScrollPane scrollPane2;
   /**
    * Anfangsgröße
    */
   private Dimension d1;
   /**
    * Geändertegröße bei Focus.
    */
   private Dimension d2;
   /**
    * JPanel der Componenten.
    */
   private JPanel panel;
   
   /**
    * 
    * Übernimmt die
    *
    * @param scrollPane1
    * @param scrollPane2
    * @param d1
    * @param d2
    * @param panel
    */
   public TextAreaFocusListener(JScrollPane scrollPane1, JScrollPane scrollPane2, Dimension d1, Dimension d2, JPanel panel)
   {
      super();
      this.scrollPane1 = scrollPane1;
      this.scrollPane2 = scrollPane2;
      this.d1 = d1;
      this.d2 = d2;
      this.panel = panel;
   }
   
   /**
    * Setzt die beiden ScrollPanes auf die anfängliche, kleine Größe zurück,
    * wenn sie den Focus verlieren.
    */
   @Override
   public void focusLost(FocusEvent e)
   {
      scrollPane1.setPreferredSize(d1);
      scrollPane2.setPreferredSize(d1);
      panel.revalidate();
   }
   
   /**
    * Setzt die beiden Scrollpanes auf eine größere Größe,
    * wenn sie den Focus erhalten.
    */
   @Override
   public void focusGained(FocusEvent e)
   {
      scrollPane1.setPreferredSize(d2);
      scrollPane2.setPreferredSize(d2);
      panel.revalidate();
   }

}

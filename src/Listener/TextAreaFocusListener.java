package Listener;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JScrollPane;

public class TextAreaFocusListener implements FocusListener
{
   private JScrollPane scrollPane1;
   private JScrollPane scrollPane2;
   private Dimension d1;
   private Dimension d2;
   
   public TextAreaFocusListener(JScrollPane scrollPane1, JScrollPane scrollPane2, Dimension d1, Dimension d2)
   {
      super();
      this.scrollPane1 = scrollPane1;
      this.scrollPane2 = scrollPane2;
      this.d1 = d1;
      this.d2 = d2;
   }
   
   @Override
   public void focusLost(FocusEvent e)
   {
      scrollPane1.setPreferredSize(d1);
      scrollPane2.setPreferredSize(d1);
      scrollPane2.updateUI();
      scrollPane1.updateUI();
   }
   
   @Override
   public void focusGained(FocusEvent e)
   {
      scrollPane1.setPreferredSize(d2);
      scrollPane2.setPreferredSize(d2);
      scrollPane2.updateUI();
   }

}

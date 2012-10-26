package Listener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.ComponentUI;

public class TextAreaFocusListener implements FocusListener
{
   private JScrollPane scrollPane1;
   private JScrollPane scrollPane2;
   private Dimension d1;
   private Dimension d2;
   private JPanel panel;
   
   public TextAreaFocusListener(JScrollPane scrollPane1, JScrollPane scrollPane2, Dimension d1, Dimension d2, JPanel panel)
   {
      super();
      this.scrollPane1 = scrollPane1;
      this.scrollPane2 = scrollPane2;
      this.d1 = d1;
      this.d2 = d2;
      this.panel = panel;
   }
   
   @Override
   public void focusLost(FocusEvent e)
   {
      scrollPane1.setPreferredSize(d1);
      scrollPane2.setPreferredSize(d1);
      panel.revalidate();
   }
   
   @Override
   public void focusGained(FocusEvent e)
   {
      scrollPane1.setPreferredSize(d2);
      scrollPane2.setPreferredSize(d2);
      panel.revalidate();
   }

}

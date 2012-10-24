package Listener;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class TextFieldFocusListener implements FocusListener
{
   private JTextField field;
   
   public TextFieldFocusListener(JTextField field)
   {
      super();
      this.field = field;
   }
   
   @Override
   public void focusGained(FocusEvent e)
   {
      field.selectAll();
   }

   @Override
   public void focusLost(FocusEvent e)
   {
      // TODO Auto-generated method stub
      
   }

}

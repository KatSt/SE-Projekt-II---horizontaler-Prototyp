package Actions;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class AlleFelderLeeren extends AbstractAction
{
   private Vector<JTextField> felder;
   private Vector<JTextArea> textAreas;

   public AlleFelderLeeren(Vector<JTextField> felder, Vector<JTextArea> textAreas)
   {
      super();
      putValue(NAME, "Alle leeren");
      this.felder = felder;
      this.textAreas = textAreas;
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      for(JTextField field : felder)
      {
         field.setText("");
      }

      if(textAreas != null)
      {
         for(JTextArea area : textAreas)
         {
            area.setText("");
         }
      }
   }

}

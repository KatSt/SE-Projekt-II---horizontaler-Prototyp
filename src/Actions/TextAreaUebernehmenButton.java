package Actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class TextAreaUebernehmenButton extends AbstractAction
{

   /**
    * 1. Textarea.
    */
   private JTextArea area1;
   /**
    * 2. Textarea.
    */
   private JTextArea area2;

   /**
    * 
    * Der Konstruktor übernimmt die beiden Textfelder.
    *
    * @param area1     1. Textarea
    * @param area2     2. Textarea
    */
   public TextAreaUebernehmenButton(JTextArea area1, JTextArea area2)
   {
      super();
      putValue(NAME, ">");
      this.area1 = area1;
      this.area2 = area2;      
   }

   /**
    * Kopiert den Text aus Feld 1 in Feld 2.
    */
   @Override
   public void actionPerformed(ActionEvent e)
   {
      area2.setText(area1.getText());
   }

}

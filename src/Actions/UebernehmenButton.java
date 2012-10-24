package Actions;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextField;


/**
 * 
 * Action für den Button um einzelne Werte aus einem Textfeld in ein 
 * anderes zu übertragen.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 10.10.2012 $ UTC
 */
@SuppressWarnings("serial")
public class UebernehmenButton extends AbstractAction
{
   /**
    * 1. Textfeld.
    */
   private JTextField field1;
   /**
    * 2. Textfeld.
    */
   private JTextField field2;

   /**
    * 
    * Der Konstruktor übernimmt die beiden Textfelder.
    *
    * @param field1     1. Textfeld
    * @param field2     2. Textfeld
    */
   public UebernehmenButton(JTextField field1, JTextField field2)
   {
      super();
      putValue(NAME, ">");
      this.field1 = field1;
      this.field2 = field2;      
   }

   /**
    * Kopiert den Text aus Feld 1 in Feld 2.
    */
   @Override
   public void actionPerformed(ActionEvent e)
   {
      field2.setText(field1.getText());
   }

}

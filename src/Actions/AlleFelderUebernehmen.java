package Actions;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class AlleFelderUebernehmen extends AbstractAction
{
   private Vector<JTextField> felder1;
   private Vector<JTextField> felder2;
   private Vector<JTextArea> textAreas1;
   private Vector<JTextArea> textAreas2;
   
   public AlleFelderUebernehmen(Vector<JTextField> felder1, Vector<JTextField> felder2, Vector<JTextArea> textAreas1, Vector<JTextArea> textAreas2)
   {
      super();
      putValue(NAME, "Alle");
      this.felder1 = felder1;
      this.felder2 = felder2;
      this.textAreas1 = textAreas1;
      this.textAreas2 = textAreas2;
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      for(int i = 0; i < felder1.size(); i++)
      {
         felder2.get(i).setText(felder1.get(i).getText());
      }
      
      if(textAreas1 != null)
      {
         for(int j = 0; j < textAreas1.size(); j++)
         {
            textAreas2.get(j).setText(textAreas1.get(j).getText());
         }
      }
      
   }

}

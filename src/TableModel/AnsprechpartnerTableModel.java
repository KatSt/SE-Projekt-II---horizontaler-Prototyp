package TableModel;
import javax.swing.table.DefaultTableModel;


@SuppressWarnings("serial")
public class AnsprechpartnerTableModel extends DefaultTableModel
{
   private static Object[] columnNames = {"Name", "Vorname", "Unternehmen"};
   
   public AnsprechpartnerTableModel()
   {
      super(columnNames,3);
   }

}

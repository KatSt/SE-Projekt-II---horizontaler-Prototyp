package TableModel;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import Objekt.Unternehmen;

/**
 * 
 * TableModel für die Tabelle zur Auswahl eines Unternehmens
 * bei doppelten Namen in der Adiuvo Datenbank.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 25.10.2012 $ UTC
 */
@SuppressWarnings("serial")
public class UnternehmenTableModel extends AbstractTableModel
{
   private static Object[] columnNames = {"Name", "Ort"};
   private Vector<Unternehmen> unt;
   
   public UnternehmenTableModel(Vector<Unternehmen> unt)
   {
      super();
      this.unt = unt;      
   }

   @Override
   public int getRowCount()
   {      
      return unt.size();
   }

   @Override
   public int getColumnCount()
   {
      return 2;
   }

   @Override
   public Object getValueAt(int rowIndex, int columnIndex)
   {
      switch(columnIndex)
      {
         case 0 :
            return unt.get(rowIndex).getName();
         case 1 : 
            return unt.get(rowIndex).getOrt();
         default :
            return " --- ";
      }
   }

   /* (non-Javadoc)
    * @see javax.swing.table.AbstractTableModel#getColumnName(int)
    */
   @Override
   public String getColumnName(int column)
   {
      return columnNames[column].toString();
   }
   
   

}

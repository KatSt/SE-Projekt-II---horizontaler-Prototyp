package TableModel;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import Objekt.Ansprechpartner;

/**
 * 
 * TableModel für die Tabelle zur Auswahl eines Ansprechpartners
 * bei doppelten Namen in der Adiuvo Datenbank.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 25.10.2012 $ UTC
 */
@SuppressWarnings("serial")
public class AnsprechpartnerTableModel extends AbstractTableModel
{
   /**
    * Spaltenbezeichnungen.
    */
   private static Object[] columnNames = {"Name", "Vorname", "Unternehmen"};
   /**
    * Vector mit Ansprechpartnern.
    */
   Vector<Ansprechpartner> ansp;
   
   /**
    * 
    * Übernimmt den Vektor der Ansprechpartner.
    *
    * @param ansp   Liste der Ansprechpartner mit gleichem Namen
    */
   public AnsprechpartnerTableModel(Vector<Ansprechpartner> ansp)
   {
      super();
      this.ansp = ansp;
   }

   @Override
   public int getRowCount()
   {      
      return ansp.size();
   }

   @Override
   public int getColumnCount()
   {      
      return 3;
   }

   @Override
   public Object getValueAt(int rowIndex, int columnIndex)
   {
      switch(columnIndex)
      {
         case 0:
            return ansp.get(rowIndex).getName();
            
         case 1 : 
            return ansp.get(rowIndex).getVorname();
            
         case 2 :
            if(ansp.get(rowIndex).getUnternehmen() != null)
            {
               return ansp.get(rowIndex).getUnternehmen().getName();
            }
            else
            {
               return "  ";
            }
            
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

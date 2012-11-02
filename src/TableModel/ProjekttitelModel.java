package TableModel;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import Objekt.Projektantrag;


/**
 * 
 * TableModel der Tabelle mit den Projekttiteln.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 02.10.2012 $ UTC
 */
@SuppressWarnings("serial")
public class ProjekttitelModel extends AbstractTableModel
{
   private static Object[] columnNames = { "Projekttitel" };
   private Vector<Projektantrag> projekttitel;
   
   /**
    * 
    * Übernimmt die Projekttitel und setzt den Spaltennamen.
    *
    * @param projekttitel   Vektor mit Titeln der Projekte.
    */
   public ProjekttitelModel(Vector<Projektantrag> projekttitel)
   {
      super();
      this.projekttitel = projekttitel;   
   }

   @SuppressWarnings("unchecked")
   public Vector<Projektantrag> getProjekte()
   {
      return (Vector<Projektantrag>) projekttitel.clone();
   }

   public void deleteAntrag(Projektantrag p)
   {
      projekttitel.remove(p);
      fireTableDataChanged();
   }

   /* (non-Javadoc)
    * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
    */
   @Override
   public Object getValueAt(int row, int column)
   {
      // TODO Auto-generated method stub
      return projekttitel.get(row);
   }

   @Override
   public int getRowCount()
   {
      return projekttitel.size();
   }

   @Override
   public int getColumnCount()
   {
      return 1;
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

package Listener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JTable;

import Objekt.Projektantrag;
import Windows.EditProjektWindow;


/**
 * 
 * Öffnet bei Doppelklick auf die Projekttablle das Fenster zum Archivieren des jeweiligen Projekts.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 02.10.2012 $ UTC
 */
public class TableMouseListener implements MouseListener
{
   private JTable source;
   private Vector<Projektantrag> titel;

   /**
    * 
    * Übernimmt die Tabelle und die Projekttitel.
    *   
    * @param source     Tabelle der Projekttiteln.
    * @param titel      Projekttiteln.
    */
   public TableMouseListener(JTable source, Vector<Projektantrag> titel)
   {
      super();
      this.source = source;
      this.titel = titel;
   }

   /**
    * Öffnet bei Doppelklick auf den Projekttitel in der Tabelle
    * das Fenster zum Anzeigen und Anpassen der Daten des Projekts.
    */
   @Override
   public void mouseClicked(MouseEvent e)
   {
      if (e.getClickCount() == 2) 
      {         
         Projektantrag projekt = titel.get(source.getSelectedRow());
         
         EditProjektWindow editProjektWindow = new EditProjektWindow(projekt);
         editProjektWindow.setVisible(true);
      }      
   }

   @Override
   public void mousePressed(MouseEvent e)
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseReleased(MouseEvent e)
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseEntered(MouseEvent e)
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseExited(MouseEvent e)
   {
      // TODO Auto-generated method stub

   }

}

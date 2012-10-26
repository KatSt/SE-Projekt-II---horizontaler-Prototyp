package Listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JTextField;

import Objekt.Unternehmen;

public class UnternehmenTableMouseListener implements MouseListener
{
   private JTable source;
   private Vector<Unternehmen> unt;
   private Unternehmen unternehmen;
   private Vector<JTextField> untTextFields;

   /**
    * 
    * �bernimmt die Parameter.
    *
    * @param source             Tabelle mit Ansprechpartnern.
    * @param unt               Vector aller m�glichen Ansprechpartner.
    * @param untTextFields     Textefelder die gef�llt werden sollen.
    * @param ansprechpartner    Ansprechpartnerobjekt.
    */
   public UnternehmenTableMouseListener(JTable source, Vector<Unternehmen> unt, Vector<JTextField> untTextFields, Unternehmen unternehmen)
   {
      super();
      this.source = source;
      this.unt = unt;
      this.unternehmen = unternehmen;
      this.untTextFields = untTextFields;
   }

   /**
    * Setzt bei Doppelklick auf die Ansprechpartnertabelle die Textfelder
    * der Adiuvoseite auf den richtigen Wert.
    */
   @Override
   public void mouseClicked(MouseEvent e)
   {
      if (e.getClickCount() == 2) 
      {         
         System.out.println(source.getSelectedRow());

         unternehmen = unt.get(source.getSelectedRow());
         
         untTextFields.get(0).setText(unternehmen.getName());
         untTextFields.get(1).setText(unternehmen.getStra�eHausnr());
         untTextFields.get(2).setText(unternehmen.getpLZ());
         untTextFields.get(3).setText(unternehmen.getOrt());
         untTextFields.get(4).setText(unternehmen.getTelefon());
         untTextFields.get(5).setText(unternehmen.getEmail());
         untTextFields.get(6).setText(unternehmen.getFax());
         untTextFields.get(7).setText(unternehmen.getInternetseite());
         untTextFields.get(8).setText(unternehmen.getBranche());         
         untTextFields.get(9).setText(unternehmen.getKommentar());
         untTextFields.get(10).setText(unternehmen.getKommentarIntern());                           
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

package Listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JTextField;

import Objekt.Unternehmen;
import Windows.EditProjektWindow;

public class UnternehmenTableMouseListener implements MouseListener
{
   private JTable source;
   private Vector<Unternehmen> unt;
   private Unternehmen unternehmen;
   private Vector<JTextField> untTextFields;
   private EditProjektWindow dialog;

   /**
    * 
    * Übernimmt die Parameter.
    *
    * @param source             Tabelle mit Ansprechpartnern.
    * @param unt               Vector aller möglichen Ansprechpartner.
    * @param untTextFields     Textefelder die gefüllt werden sollen.
    * @param ansprechpartner    Ansprechpartnerobjekt.
    */
   public UnternehmenTableMouseListener(JTable source, Vector<Unternehmen> unt, Vector<JTextField> untTextFields, EditProjektWindow dialog)
   {
      super();
      this.source = source;
      this.unt = unt;
      this.untTextFields = untTextFields;
      this.dialog = dialog;
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
         unternehmen = unt.get(source.getSelectedRow());
         dialog.setAdiuvoUnternehmen(unternehmen);
         
         
         untTextFields.get(0).setText(unternehmen.getName());
         untTextFields.get(1).setText(unternehmen.getStraßeHausnr());
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

package Listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JTextField;

import Objekt.Ansprechpartner;

/**
 * 
 * Setzt bei Doppelklick auf einen Ansprechpartner die Textfelder auf die ausgewählten Werte.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 26.10.2012 $ UTC
 */
public class AnsprechpartnerTableMouseListener implements MouseListener
{
   private JTable source;
   private Vector<Ansprechpartner> ansp;
   private Ansprechpartner ansprechp;
   private Vector<JTextField> anspTextFields;

   /**
    * 
    * Übernimmt die Parameter.
    *
    * @param source             Tabelle mit Ansprechpartnern.
    * @param ansp               Vector aller möglichen Ansprechpartner.
    * @param anspTextFields     Textefelder die gefüllt werden sollen.
    * @param ansprechpartner    Ansprechpartnerobjekt.
    */
   public AnsprechpartnerTableMouseListener(JTable source, Vector<Ansprechpartner> ansp, Vector<JTextField> anspTextFields, Ansprechpartner ansprechpartner)
   {
      super();
      this.source = source;
      this.ansp = ansp;
      ansprechp = ansprechpartner;
      this.anspTextFields = anspTextFields;
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

         ansprechp = ansp.get(source.getSelectedRow());
         
         anspTextFields.get(0).setText("");
         anspTextFields.get(1).setText(ansprechp.getTitel());
         anspTextFields.get(2).setText(ansprechp.getVorname());
         anspTextFields.get(3).setText(ansprechp.getName());
         anspTextFields.get(4).setText(ansprechp.getPosition());
         anspTextFields.get(5).setText(ansprechp.getAbteilung());
         anspTextFields.get(6).setText(ansprechp.getTelefon1());
         anspTextFields.get(7).setText(ansprechp.getTelefon2());
         anspTextFields.get(8).setText(ansprechp.getMobil());
         anspTextFields.get(9).setText(ansprechp.getEmail());
         anspTextFields.get(10).setText(ansprechp.getFax());
         anspTextFields.get(11).setText(ansprechp.getKommentar());
         anspTextFields.get(12).setText(ansprechp.getInternetseite());
         anspTextFields.get(13).setText(ansprechp.getKommentarIntern());
         
                  
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

package Actions;

import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import DatenbankConnector.ConnectFirebirdDatabase;
import Objekt.Ansprechpartner;
import Objekt.Student;
import Objekt.Unternehmen;
import Windows.EditProjektWindow;

/**
 * 
 * AbstractAction zur Archivierung der Daten in die Firebird-Datenbank.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 30.10.2012 $ UTC
 */
@SuppressWarnings("serial")
public class Archivieren extends AbstractAction
{
   private HashMap<Integer, Boolean> tabs;
   private Vector<JTextField> student1AdiuvoTextFields;
   private Vector<JTextField> student2AdiuvoTextFields;
   private Vector<JTextField> student3AdiuvoTextFields;
   private Vector<JTextField> projektAdiuvoTextFields;
   private Vector<JTextArea> projektAdiuvoTextAreas;
   private Vector<JTextField> unternehmenAdiuvoTextFields; 
   private Vector<JTextField> ansprechpartnerAdiuvoTextFields;
   private Student adiuvoStudent1;
   private Student adiuvoStudent2;
   private Student adiuvoStudent3;
   private Ansprechpartner adiuvoAnsprechpartner;
   private Unternehmen adiuvoUnternehmen;
   private EditProjektWindow dialog;
   private int student1ID;
   private int student2ID;
   private int student3ID;
   private int ansprechpartnerID;
   private int unternehmensID;

   /**
    * 
    * Übernimmt die benötigten Parameter.
    *
    * @param tabs
    * @param student1AdiuvoTextFields
    * @param student2AdiuvoTextFields
    * @param student3AdiuvoTextFields
    * @param projektAdiuvoTextFields
    * @param projektAdiuvoTextAreas
    * @param unternehmenAdiuvoTextFields
    * @param ansprechpartnerAdiuvoTextFields
    * @param adiuvoStudent1
    * @param adiuvoStudent2
    * @param adiuvoStudent3
    * @param adiuvoAnsprechpartner
    * @param adiuvoUnternehmen
    * @param dialog
    */
   public Archivieren(HashMap<Integer, Boolean> tabs, Vector<JTextField> student1AdiuvoTextFields, Vector<JTextField> student2AdiuvoTextFields,
         Vector<JTextField> student3AdiuvoTextFields, Vector<JTextField> projektAdiuvoTextFields, Vector<JTextArea> projektAdiuvoTextAreas, Vector<JTextField> unternehmenAdiuvoTextFields, 
         Vector<JTextField> ansprechpartnerAdiuvoTextFields, Student adiuvoStudent1, Student adiuvoStudent2, Student adiuvoStudent3, Ansprechpartner adiuvoAnsprechpartner,
         Unternehmen adiuvoUnternehmen, EditProjektWindow dialog)
   {
      super();
      putValue(NAME, "Archivieren");
      this.tabs = tabs;
      this.student1AdiuvoTextFields = student1AdiuvoTextFields;
      this.student2AdiuvoTextFields = student2AdiuvoTextFields;
      this.student3AdiuvoTextFields = student3AdiuvoTextFields;
      this.projektAdiuvoTextFields = projektAdiuvoTextFields;
      this.projektAdiuvoTextAreas = projektAdiuvoTextAreas;
      this.unternehmenAdiuvoTextFields = unternehmenAdiuvoTextFields;
      this.ansprechpartnerAdiuvoTextFields = ansprechpartnerAdiuvoTextFields;
      this.adiuvoStudent1 = adiuvoStudent1;
      this.adiuvoStudent2 = adiuvoStudent2;
      this.adiuvoStudent3 = adiuvoStudent3;
//      this.adiuvoAnsprechpartner = adiuvoAnsprechpartner;
//      this.adiuvoUnternehmen = adiuvoUnternehmen;
      this.dialog = dialog;
      
   }

   /**
    * Führt die Archivierung durch und schließt das Fenster.
    */
   @Override
   public void actionPerformed(ActionEvent e)
   {
      adiuvoAnsprechpartner = dialog.getAdiuvoAnsprechpartner();
      adiuvoUnternehmen = dialog.getAdiuvoUnternehmen();
      boolean execute = true;
      for(int i = 0; i < tabs.size(); i++)
      {
         if(tabs.get(i) == false)
         {
            execute = false;
            JOptionPane.showMessageDialog(null, "Noch nicht alle Tabs betrachtet!", "Error", JOptionPane.ERROR_MESSAGE);
            break;
         }
      }

      if(execute)
      {
         if(adiuvoAnsprechpartner == null)
         {
            System.out.println("Ansprechpartner = null");
         }
         if(adiuvoUnternehmen == null)
         {
            System.out.println("Unternehmen = null");
         }
         student1ID= studentArchivieren(adiuvoStudent1, student1AdiuvoTextFields);
         if(student2AdiuvoTextFields.size() > 0 )
         {
            student2ID = studentArchivieren(adiuvoStudent2, student2AdiuvoTextFields);
         }
         if(student3AdiuvoTextFields.size() > 0)
         {
            student3ID = studentArchivieren(adiuvoStudent3, student3AdiuvoTextFields);
         }
         unternehmenArchivieren(); 
         ansprechpartnerArchivieren();
         System.out.println("uID : " + unternehmensID + "  aID : " + ansprechpartnerID + " s1 : " + student1ID + "  s2 : " + student2ID + "  s3 : " + student3ID);
         projektArchivieren(); 

         dialog.dispose();
      }

   }

   /**
    * 
    * Archiviert die Projektdaten.
    *
    */
   private void projektArchivieren()
   {
      String update = "";
      update = "INSERT INTO PROJEKT" +
      "(PROJEKTTITEL,  PROJEKTSKIZZE, PROJEKTHINTERGRUND, PROJEKTBESCHREIBUNG, PROJEKTART, TECHNOLOGIEN, BEGINN, ENDE, NOTE, KOLLOQIUMSNOTE, KOMMENTAR, KOMMENTARINTERN, " +
      " ANSPRECHPARTNER1, STUDENT1, STUDENT2, STUDENT3, ZULETZTGEAENDERT) VALUES (" 
      + getTextFieldValue(projektAdiuvoTextFields.get(0)) + ", "
      + getTextAreaValue(projektAdiuvoTextAreas.get(0)) + ", "
      + getTextAreaValue(projektAdiuvoTextAreas.get(2)) + ", "
      + getTextAreaValue(projektAdiuvoTextAreas.get(1)) + ", "
      + getTextFieldValue(projektAdiuvoTextFields.get(4))+ ", "
      + getTextFieldValue(projektAdiuvoTextFields.get(1)) + ", "
      + getTextFieldValue(projektAdiuvoTextFields.get(3)) + ", "
      + getTextFieldValue(projektAdiuvoTextFields.get(5)) + ", "
      + getTextFieldValue(projektAdiuvoTextFields.get(2)) + ", "
      + getTextFieldValue(projektAdiuvoTextFields.get(6)) + ", "
      + getTextFieldValue(projektAdiuvoTextFields.get(7)) + ", "
      + getTextFieldValue(projektAdiuvoTextFields.get(8)) + ", "
      + ansprechpartnerID + ", "
      +  student1ID + ", ";
      if(student2AdiuvoTextFields.size() > 0 )
      {
         update += student2ID + ", ";
      }
      else
      {
         update += "NULL, ";
      }
      if(student3AdiuvoTextFields.size() > 0 )
      {
         update += student3ID + ", ";
      }
      else
      {
         update += "NULL, ";
      }
      update += "'NOW');";

      System.out.println(update);
      ConnectFirebirdDatabase.getInstance().update(update);

   }


   /**
    * 
    * Archiviert oder updated einen Studenten in der Firebird-Datenbank.
    *
    * @param student        Studentobjekt aus der Firebird Datenbank
    * @param textFields     Vom Nutzer aktualisierte Werte
    */
   private int studentArchivieren(Student student, Vector<JTextField> textFields)
   {
      String update = "";
      int id = -1;
      if(student == null)
      {
         update = "INSERT INTO STUDENT" +             
         "(ANREDE, VORNAME, NACHNAME, TELEFON, EMAIL, MATRIKELNUMMER, INTERNETSEITE, KOMMENTAR, KOMMENTARINTERN,  ZULETZTGEAENDERT)" 
         + " VALUES ( "
         + getTextFieldValue(textFields.get(0)) + ", "
         + getTextFieldValue(textFields.get(1)) + ", "
         + getTextFieldValue(textFields.get(2)) + ", "
         + getTextFieldValue(textFields.get(3)) + ", "
         + getTextFieldValue(textFields.get(4)) + ", "
         + getTextFieldValue(textFields.get(5)) + ", "
         + getTextFieldValue(textFields.get(6)) + ", "
         + getTextFieldValue(textFields.get(7)) + ", "
         + getTextFieldValue(textFields.get(8)) + ", "
         + "'NOW' );";

         ResultSet rs = ConnectFirebirdDatabase.getInstance().insert(update);
         try
         {
            while(rs.next())
            {
               id = rs.getInt(1);
               System.out.println("studID : " + id);
            }
         }
         catch(SQLException ex)
         {
            ex.printStackTrace();
         }         

      }
      else
      {
         update = "UPDATE STUDENT SET ";
         int counter = 0;
         if(!textFields.get(0).getText().equals(student.getAnrede()))
         {
            update+= "Anrede = " + getTextFieldValue(textFields.get(0));
            counter++;
         }
         if(!textFields.get(1).getText().equals(student.getVorname()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update+= "Vorname = " + getTextFieldValue(textFields.get(1));
            counter++;
         }
         if(!textFields.get(2).getText().equals(student.getName()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update+= "Nachname = " + getTextFieldValue(textFields.get(2));
            counter++;
         }
         if(!textFields.get(3).getText().equals(student.getTelefon()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update+= "Telefon = " + getTextFieldValue(textFields.get(3));     
            counter++;
         }
         if(!textFields.get(4).getText().equals(student.getEmail()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "Email = " + getTextFieldValue(textFields.get(4));
            counter++;
         }               

         if(!textFields.get(7).getText().equals(student.getKommentar()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "Kommentar = " + getTextFieldValue(textFields.get(7));
            counter++;
         }
         if(!textFields.get(8).getText().equals(student.getKommentarIntern()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "Kommentarintern = " + getTextFieldValue(textFields.get(8));
            counter++;
         }
         if(!textFields.get(6).getText().equals(student.getInternetseite()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "Internetseite = " + getTextFieldValue(textFields.get(6));
            counter++;
         }
         if(counter > 0 )
         {
            update += ", ";
         }
         update += "ZULETZTGEAENDERT = 'NOW' WHERE matrikelnummer = '" + student.getMatrikelnummer() + "';";
         if(counter > 0 )
         {
            System.out.println(update);

            ConnectFirebirdDatabase.getInstance().update(update);
         }
         id = student.getId();
      }
      System.out.println(id);
      return id;
   }
   
   /**
    * 
    * Archiviert oder updatet einen Ansprechparnter in der Firebird-Datenbank.
    *
    */
   private void ansprechpartnerArchivieren()
   {
      String update = "";

      if(adiuvoAnsprechpartner == null)
      {
         update = "INSERT INTO ANSPRECHPARTNER" +             
         "(ANREDE, TITEL, VORNAME, NACHNAME, TELEFON1, TELEFON2, TELEFON3, EMAIL, FAX, INTERNETSEITE, KOMMENTAR, KOMMENTARINTERN, POSITIONAP, ABTEILUNG, ORGANISATION, ZULETZTGEAENDERT)" 
         + " VALUES ( "
         + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(0)) + ", "
         + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(1)) + ", "
         + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(2)) + ", "
         + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(3)) + ", "
         + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(6)) + ", "
         + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(7)) + ", "
         + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(8)) + ", "
         + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(9)) + ", "
         + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(10)) + ", "
         + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(12)) + ", "
         + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(11)) + ", "
         + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(13)) + ", "
         + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(4)) + ", "
         + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(5)) + ", "
         + unternehmensID + ", "
         + "'NOW' );";
         
         ResultSet rs = ConnectFirebirdDatabase.getInstance().insert(update);
//         System.out.println("result : " + result);
//         ResultSet rs = ConnectFirebirdDatabase.getInstance().query("SELECT id FROM ansprechpartner WHERE vorname = "
//               + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(2)) + " AND nachname = " + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(3)) + 
//               " AND TELEFON1 = " + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(4)) + " AND email = " + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(9)) + ";");
         System.out.println("Aber ich mache hier überhaupt was? " + update);

         try
         {
            if(rs == null)
            {
               System.out.println("funktioniert nich!");
            }
            while(rs.next())
            {
               ansprechpartnerID = rs.getInt(1);
               System.out.println("anspID : " + ansprechpartnerID);
            }
         }
         catch(SQLException ex)
         {
            ex.printStackTrace();
         }         
      }
      else
      {
         update = "UPDATE ANSPRECHPARTNER SET ";
         int counter = 0;
         if(!ansprechpartnerAdiuvoTextFields.get(0).getText().equals(adiuvoAnsprechpartner.getAnrede()))
         {
            update+= "ANREDE = " + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(0));
            counter++;
         }
         if(!ansprechpartnerAdiuvoTextFields.get(1).getText().equals(adiuvoAnsprechpartner.getTitel()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update+= "TITEL = " + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(1));
            counter++;
         }
         if(!ansprechpartnerAdiuvoTextFields.get(2).getText().equals(adiuvoAnsprechpartner.getVorname()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update+= "VORNAME = " + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(2));
            counter++;
         }
         if(!ansprechpartnerAdiuvoTextFields.get(3).getText().equals(adiuvoAnsprechpartner.getName()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update+= "NACHNAME = " + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(3));     
            counter++;
         }
         if(!ansprechpartnerAdiuvoTextFields.get(4).getText().equals(adiuvoAnsprechpartner.getPosition()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "POSITIONAP = " + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(4));
            counter++;
         }               

         if(!ansprechpartnerAdiuvoTextFields.get(5).getText().equals(adiuvoAnsprechpartner.getAbteilung()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "ABTEILUNG = " + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(5));
            counter++;
         }
         if(!ansprechpartnerAdiuvoTextFields.get(6).getText().equals(adiuvoAnsprechpartner.getTelefon1()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "TELEFON1 = " + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(6));
            counter++;
         }
         if(!ansprechpartnerAdiuvoTextFields.get(7).getText().equals(adiuvoAnsprechpartner.getTelefon2()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "TELEFON2 = " + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(7));
            counter++;
         }
         if(!ansprechpartnerAdiuvoTextFields.get(8).getText().equals(adiuvoAnsprechpartner.getMobil()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "TELEFON3 = " + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(8));
            counter++;
         }
         if(!ansprechpartnerAdiuvoTextFields.get(9).getText().equals(adiuvoAnsprechpartner.getEmail()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "EMAIL = " + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(9));
            counter++;
         }
         if(!ansprechpartnerAdiuvoTextFields.get(10).getText().equals(adiuvoAnsprechpartner.getFax()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "FAX = " + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(10));
            counter++;
         }
         if(!ansprechpartnerAdiuvoTextFields.get(11).getText().equals(adiuvoAnsprechpartner.getInternetseite()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "INTERNETSEITE = " + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(11));
            counter++;
         }
         if(!ansprechpartnerAdiuvoTextFields.get(12).getText().equals(adiuvoAnsprechpartner.getKommentar()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "KOMMENTAR = " + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(12));
            counter++;
         }
         if(!ansprechpartnerAdiuvoTextFields.get(13).getText().equals(adiuvoAnsprechpartner.getKommentarIntern()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "KOMMENTARINTERN = " + getTextFieldValue(ansprechpartnerAdiuvoTextFields.get(13));
            counter++;
         }
         if(!adiuvoUnternehmen.getName().equals(adiuvoAnsprechpartner.getUnternehmen().getName()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "ORGANISATION = " + adiuvoUnternehmen.getId();
            counter++;
         }
         if(counter > 0 )
         {
            update += ", ";
         }
         update += "ZULETZTGEAENDERT = 'NOW' WHERE id = " + adiuvoUnternehmen.getId() + ";";
         if(counter > 0 )
         {
            System.out.println(update);

            ConnectFirebirdDatabase.getInstance().update(update);
         }
         ansprechpartnerID = adiuvoAnsprechpartner.getId();
      }
      System.out.println("hallo? : " +ansprechpartnerID);
   }

   /**
    * 
    * Archiviert oder updatet ein Unternehmen in der Firebird-Datenbank.
    *
    */
   private void unternehmenArchivieren()
   {
      String update = "";

      if(adiuvoUnternehmen == null)
      {
         update = "INSERT INTO ORGANISATION" +             
         "(NAME, STRASSEHAUSNR, PLZ, ORT, TELEFON, EMAIL, FAX, INTERNETSEITE, BRANCHE, KOMMENTAR, KOMMENTARINTERN, ZULETZTGEAENDERT)" 
         + " VALUES ( "
         + getTextFieldValue(unternehmenAdiuvoTextFields.get(0)) + ", "
         + getTextFieldValue(unternehmenAdiuvoTextFields.get(1)) + ", "
         + getTextFieldValue(unternehmenAdiuvoTextFields.get(2)) + ", "
         + getTextFieldValue(unternehmenAdiuvoTextFields.get(3)) + ", "
         + getTextFieldValue(unternehmenAdiuvoTextFields.get(4)) + ", "
         + getTextFieldValue(unternehmenAdiuvoTextFields.get(5)) + ", "
         + getTextFieldValue(unternehmenAdiuvoTextFields.get(6)) + ", "
         + getTextFieldValue(unternehmenAdiuvoTextFields.get(7)) + ", "
         + getTextFieldValue(unternehmenAdiuvoTextFields.get(8)) + ", "
         + getTextFieldValue(unternehmenAdiuvoTextFields.get(9)) + ", "
         + getTextFieldValue(unternehmenAdiuvoTextFields.get(10)) + ", "
         + "'NOW' );";

         ResultSet rs = ConnectFirebirdDatabase.getInstance().insert(update);
         
         try
         {
            while(rs.next())
            {
               unternehmensID = rs.getInt(1);
               System.out.println("untID : " + unternehmensID);
            }
         }
         catch(SQLException ex)
         {
            ex.printStackTrace();
         }         
      }
      else
      {
         update = "UPDATE ORGANISATION SET ";
         int counter = 0;
         if(!unternehmenAdiuvoTextFields.get(0).getText().equals(adiuvoUnternehmen.getName()))
         {
            update+= "NAME = " + getTextFieldValue(unternehmenAdiuvoTextFields.get(0));
            counter++;
         }
         if(!unternehmenAdiuvoTextFields.get(1).getText().equals(adiuvoUnternehmen.getStraßeHausnr()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update+= "STRASSEHAUSNR = " + getTextFieldValue(unternehmenAdiuvoTextFields.get(1));
            counter++;
         }
         if(!unternehmenAdiuvoTextFields.get(2).getText().equals(adiuvoUnternehmen.getpLZ()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update+= "PLZ = " + getTextFieldValue(unternehmenAdiuvoTextFields.get(2));
            counter++;
         }
         if(!unternehmenAdiuvoTextFields.get(3).getText().equals(adiuvoUnternehmen.getOrt()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update+= "ORT = " + getTextFieldValue(unternehmenAdiuvoTextFields.get(3));     
            counter++;
         }
         if(!unternehmenAdiuvoTextFields.get(4).getText().equals(adiuvoUnternehmen.getTelefon()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "TELEFON = " + getTextFieldValue(unternehmenAdiuvoTextFields.get(4));
            counter++;
         }               

         if(!unternehmenAdiuvoTextFields.get(5).getText().equals(adiuvoUnternehmen.getEmail()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "EMAIL = " + getTextFieldValue(unternehmenAdiuvoTextFields.get(5));
            counter++;
         }
         if(!unternehmenAdiuvoTextFields.get(6).getText().equals(adiuvoUnternehmen.getFax()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "FAX = " + getTextFieldValue(unternehmenAdiuvoTextFields.get(6));
            counter++;
         }
         if(!unternehmenAdiuvoTextFields.get(7).getText().equals(adiuvoUnternehmen.getInternetseite()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "INTERNETSEITE = " + getTextFieldValue(unternehmenAdiuvoTextFields.get(7));
            counter++;
         }
         if(!unternehmenAdiuvoTextFields.get(8).getText().equals(adiuvoUnternehmen.getBranche()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "BRANCHE = " + getTextFieldValue(unternehmenAdiuvoTextFields.get(8));
            counter++;
         }
         if(!unternehmenAdiuvoTextFields.get(9).getText().equals(adiuvoUnternehmen.getKommentar()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "KOMMENTAR = " + getTextFieldValue(unternehmenAdiuvoTextFields.get(9));
            counter++;
         }
         if(!unternehmenAdiuvoTextFields.get(10).getText().equals(adiuvoUnternehmen.getKommentarIntern()))
         {
            if(counter > 0)
            {
               update+= ", ";
            }
            update += "KOMMENTARINTERN = " + getTextFieldValue(unternehmenAdiuvoTextFields.get(10));
            counter++;
         }
         if(counter > 0 )
         {
            update += ", ";
         }
         update += "ZULETZTGEAENDERT = 'NOW'  WHERE id = " + adiuvoUnternehmen.getId() + ";";
         if(counter > 0 )
         {
            System.out.println(update);

            ConnectFirebirdDatabase.getInstance().update(update);
         }
         unternehmensID = adiuvoUnternehmen.getId();
      }
      System.out.println(unternehmensID);
   }

   /**
    * 
    * Ermittelt den Text eines Textfeldes für die Datenbank.
    * Ist das Textfeld leer, wird für den Datenbankbefehl NULL zurückgegeben.
    *
    * @param field  Textfeld
    * @return       Inhalt des Textfeldes oder NULL
    */
   private String getTextFieldValue(JTextField field)
   {
      String s = "";
      if(field.getText().isEmpty())
      {
         s = "NULL";
      }
      else
      {
         s = "'" + field.getText() + "'";
      }

      return s;
   }

   /**
    * 
    * Ermittelt den Text einer Textarea für die Datenbank.
    * Ist die Textarea leer, wird für den Datenbankbefehl NULL zurückgegeben.
    *
    * @param area   Textfeld
    * @return       Inhalt des Textfeldes oder NULL
    */
   private String getTextAreaValue(JTextArea area)
   {
      String s = "";
      if(area.getText().isEmpty())
      {
         s = "NULL";
      }
      else
      {
         s = "'" + area.getText() + "'";
      }

      return s;
   }
}

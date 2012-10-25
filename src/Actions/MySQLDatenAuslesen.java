package Actions;

import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import DatenbankConnector.ConnectMySQLDatabase;
import Main.JFrameShower;
import Objekt.Ansprechpartner;
import Objekt.Projektantrag;
import Objekt.Student;
import Objekt.Unternehmen;
import Windows.MainWindow;

/**
 * 
 * Liest die Daten des ausgewählten Moduls aus der Datenbank,
 * erzeugt daraus Objekte und öffnet das Hauptfenster.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 25.10.2012 $ UTC
 */
@SuppressWarnings("serial")
public class MySQLDatenAuslesen extends AbstractAction
{
   /**
    * ComboBox der Modulauswahl.
    */
   private JComboBox modulComboBox;
   /**
    * Combobox der Semesterauswahl.
    */
   private JComboBox semesterComboBox;
   /**
    * Combobox der Jahresauswahl.
    */
   private JComboBox jahrComboBox;
   /**
    * Startfenster.
    */
   private JFrame frame;
   /**
    * Liste der Projektanträge aus dem ausgewählten Modul.
    */
   private Vector<Projektantrag> projekte = new Vector<Projektantrag>(); 

   /**
    * 
    * Übernimmt die benötigten Parameter.
    *
    * @param modulComboBox      ModulComboBox
    * @param semesterComboBox   SemesterComboBox
    * @param jahrComboBox       JahrCombobox
    * @param frame              Startfenster
    */
   public MySQLDatenAuslesen(JComboBox modulComboBox, JComboBox semesterComboBox, JComboBox jahrComboBox, JFrame frame)
   {
      super();
      putValue(NAME, "OK");
      this.modulComboBox = modulComboBox;
      this.semesterComboBox = semesterComboBox;
      this.jahrComboBox = jahrComboBox;
      this.frame = frame;
   }
   
   /**
    * List alle Projektdaten zu dem ausgewählten Modul aus der Datenbank aus.
    * Erzeugt daraus Projekte mit Studenten, Ansprechpartner und Unternehmen und
    * fügt diese der Projektliste hinzu.
    */
   @Override
   public void actionPerformed(ActionEvent e)
   {
      if(modulComboBox.getSelectedItem().toString().equals("") || semesterComboBox.getSelectedItem().toString().equals("") || 
            jahrComboBox.getSelectedItem().toString().equals(""))
      {
         JOptionPane.showMessageDialog(null, "Überprüfen Sie Ihre Auswahl!", "Error", JOptionPane.ERROR_MESSAGE);
      }
      else
      {
         String modul = modulComboBox.getSelectedItem().toString();
         String semester = semesterComboBox.getSelectedItem().toString();
         String jahr = jahrComboBox.getSelectedItem().toString();
         ResultSet rs = ConnectMySQLDatabase.getInstance().
         query("SELECT modulid FROM modul WHERE modulname = '" + modul + "' AND semester = '" + 
               semester + "' AND jahr = '" + jahr + "'");      
         int modulid = -1;
         try
         {
            while(rs.next())
            {
               modulid = rs.getInt("modulid");
            }
         }
         catch(SQLException ex)
         {
            ex.printStackTrace();
         }
         rs = ConnectMySQLDatabase.getInstance().query("SELECT antragid, titel, skizze, hintergrund, beschreibung, note, beginn, ansprechpartner1id" +
               ", student1id, student2id, student3id FROM antrag WHERE modulid = " + modulid);
         try
         {
            while(rs.next())
            {
               Projektantrag projekt = new Projektantrag(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), 
                     rs.getString(6), rs.getString(7));
               ResultSet rs2 = ConnectMySQLDatabase.getInstance().query("SELECT technologieid FROM antrag_technologie WHERE antragid = " + projekt.getId());
               String technologien = "";
               while(rs2.next())
               {
                  ResultSet rs3 = ConnectMySQLDatabase.getInstance().query("SELECT name FROM technologie WHERE technologieid = " + rs2.getInt(1));
                  while (rs3.next())
                  {
                     if(rs3.isLast())
                     {
                        technologien = rs3.getString(1);
                     }
                     else
                     {
                        technologien = rs3.getString(1) + " + ";
                     }
                  }
               }
               projekt.setTechnologien(technologien);
//               System.out.println("id : " + projekt.getId());
//               System.out.println("titel : " + projekt.getProjekttitel());
//               System.out.println("skizze : " + projekt.getSkizze());
//               System.out.println("hintergrund : " + projekt.getHintergrund());
//               System.out.println("beschreibung : " + projekt.getBeschreibung());
//               System.out.println("beginn : " + projekt.getBeginn());
//               System.out.println("note : " + projekt.getNote());
//               System.out.println("technoglogien : " + projekt.getTechnologien());

               rs2 = ConnectMySQLDatabase.getInstance().query("SELECT * FROM ansprechpartner WHERE ansprechpartnerid = " + rs.getInt(8));
               while(rs2.next())
               {
                  Ansprechpartner ansprechpartner = new Ansprechpartner(rs2.getInt("ansprechpartnerid"), rs2.getString("titel"), 
                        rs2.getString("vorname"), rs2.getString("name"), rs2.getString("position"), rs2.getString("abteilung"), 
                        rs2.getString("telefon1"), rs2.getString("telefon2"), rs2.getString("mobil"), rs2.getString("fax"), rs2.getString("email"),
                        rs2.getString("bemerkung"));
                  ResultSet rs3 = ConnectMySQLDatabase.getInstance().query("SELECT * FROM unternehmen WHERE unternehmenid = " + rs2.getInt("unternehmenid"));
                  while(rs3.next())
                  {
                     String strasseHausnr = rs3.getString("strasse") + " " + rs3.getString("hausnummer");
                     Unternehmen unternehmen = new Unternehmen(rs3.getInt("unternehmenid"), rs3.getString("name"), strasseHausnr, rs3.getString("plz"), 
                           rs3.getString("ort"), rs3.getString("telefon"), rs3.getString("email"), rs3.getString("fax"), rs3.getString("web"), 
                           rs3.getString("branche"), rs3.getString("bemerkung"), rs3.getString("bemerkungintern"));
                     ansprechpartner.setUnternehmen(unternehmen);
                  }
                  projekt.setAnsprechpartner(ansprechpartner);
               }

//               System.out.println("Ansprechparnter name : " + projekt.getAnsprechpartner().getName());
//               System.out.println("vorname : " + projekt.getAnsprechpartner().getVorname());
//               System.out.println("Kommentar : " + projekt.getAnsprechpartner().getKommentar());
//               System.out.println("Unternehmen : " + projekt.getAnsprechpartner().getUnternehmen().getName());

               rs2 = ConnectMySQLDatabase.getInstance().query("SELECT * FROM student WHERE studentid = " + rs.getInt("student1id"));
               while(rs2.next())
               {
                  Student student = new Student(rs2.getInt("studentid"), rs2.getString("anrede"), rs2.getString("vorname"), rs2.getString("name"), 
                        rs2.getString("matrikelnummer"), rs2.getString("email"), rs2.getString("telefon"));
                  projekt.setStudent1(student);
               }
//               System.out.println("Student name : " + projekt.getStudent1().getName());
//               System.out.println("vorname : " + projekt.getStudent1().getVorname());
               //            System.out.println("student3id : " + rs.getInt("student3id"));
               if(rs.getString("student2id") != null)
               {
                  rs2 = ConnectMySQLDatabase.getInstance().query("SELECT * FROM student WHERE studentid = " + rs.getInt("student2id"));
                  while(rs2.next())
                  {
                     Student student = new Student(rs2.getInt("studentid"), rs2.getString("anrede"), rs2.getString("vorname"), rs2.getString("name"), 
                           rs2.getString("matrikelnummer"), rs2.getString("email"), rs2.getString("telefon"));
                     projekt.setStudent2(student);
                  }
               }
//               System.out.println("Student name : " + projekt.getStudent2().getName());
//               System.out.println("vorname : " + projekt.getStudent2().getVorname());
               if(rs.getString("student3id") != null)
               {
                  rs2 = ConnectMySQLDatabase.getInstance().query("SELECT * FROM student WHERE studentid = " + rs.getInt("student3id"));
                  while(rs2.next())
                  {
                     Student student = new Student(rs2.getInt("studentid"), rs2.getString("anrede"), rs2.getString("vorname"), rs2.getString("name"), 
                           rs2.getString("matrikelnummer"), rs2.getString("email"), rs2.getString("telefon"));
                     projekt.setStudent3(student);
                  }
               }
//               if(projekt.getStudent3() != null)
//               {
//                  System.out.println("Student name : " + projekt.getStudent3().getName());
//                  System.out.println("vorname : " + projekt.getStudent3().getVorname());
//               }
               projekte.add(projekt);
//               System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");


            }
         }
         catch(SQLException ex)
         {
            ex.printStackTrace();
         }
//         System.out.println(modulid+ " " + " " + modul +" " + semester + " " + jahr);
         new JFrameShower(new MainWindow(modul+ " " + semester + " " + jahr, projekte));
         frame.dispose();
      }
   }
}

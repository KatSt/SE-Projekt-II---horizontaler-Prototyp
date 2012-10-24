package Windows;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import DatenbankConnector.ConnectMySQLDatabase;
import Main.JFrameShower;
import Objekt.Ansprechpartner;
import Objekt.Projektantrag;
import Objekt.Student;
import Objekt.Unternehmen;



/**
 * 
 * Startbildschirm des Programms.
 * Auswahl von Modul, Semester und Jahr.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 02.10.2012 $ UTC
 */
@SuppressWarnings("serial")
public class StartWindow extends JFrame
{
   private Vector<String> module = new Vector<String>();
   private JComboBox modulComboBox;
   private JComboBox semesterComboBox;
   private JComboBox jahrComboBox;
   private Vector<String> semesters = new Vector<String>();
   private Vector<String> jahre = new Vector<String>();
   private Vector<Projektantrag> projekte = new Vector<Projektantrag>(); 

   /**
    * 
    * Initialisert den Startbildschirm.
    *
    */
   public StartWindow()
   {
      super();
      init();
   }

   /**
    * 
    * Initialisiert die Oberfläche des Startbildschirms zur Auswahl
    * von Modul, Jahr, Semester für die Archivierung.
    *
    */
   public void init()
   {
      setLocation(200, 200);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(300, 250);

      JPanel components = new JPanel();
      components.setLayout(new GridLayout(7, 1));
      components.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

      fillModulCombobox();
      JLabel modulLabel = new JLabel("Modul");
      components.add(modulLabel);
      modulComboBox = new JComboBox(module);
      components.add(modulComboBox);      
      modulComboBox.addActionListener(new ActionListener()
      {

         @Override
         public void actionPerformed(ActionEvent e)
         {            
            semesters.clear();
            semesterComboBox.removeAllItems();
            semesters.add("");
            jahre.clear();
            jahrComboBox.removeAllItems();
            jahre.add("");
            //            Modul modul = (Modul)modulComboBox.getSelectedItem();
            fillSemesterCombobox(modulComboBox.getSelectedItem().toString());
            for(String s : semesters)
            {
               semesterComboBox.addItem(s);
            }
         }
      });

      JLabel semesterLabel = new JLabel("Semester");
      components.add(semesterLabel);      
      semesterComboBox = new JComboBox();
      components.add(semesterComboBox);
      semesterComboBox.addActionListener(new ActionListener()
      {

         @Override
         public void actionPerformed(ActionEvent e)
         {            
            jahre.clear();
            jahrComboBox.removeAllItems();
            jahre.add("");  
            String modul = modulComboBox.getSelectedItem().toString();
            String semester = ""; ;
            if(semesterComboBox.getSelectedItem() != null)
            {
               semester = semesterComboBox.getSelectedItem().toString();
            }
            fillJahrCombobox(modul, semester);            
            for(String j : jahre)
            {
               jahrComboBox.addItem(j);
            }
         }
      });

      JLabel jahrLabel = new JLabel("Jahr");
      components.add(jahrLabel);
      jahrComboBox = new JComboBox();
      components.add(jahrComboBox);

      Box buttonBox = Box.createHorizontalBox();
      JButton okButton = new JButton(new AbstractAction("OK")
      {

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
                     System.out.println("id : " + projekt.getId());
                     System.out.println("titel : " + projekt.getProjekttitel());
                     System.out.println("skizze : " + projekt.getSkizze());
                     System.out.println("hintergrund : " + projekt.getHintergrund());
                     System.out.println("beschreibung : " + projekt.getBeschreibung());
                     System.out.println("beginn : " + projekt.getBeginn());
                     System.out.println("note : " + projekt.getNote());
                     System.out.println("technoglogien : " + projekt.getTechnologien());
                     
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
                     
                     System.out.println("Ansprechparnter name : " + projekt.getAnsprechpartner().getName());
                     System.out.println("vorname : " + projekt.getAnsprechpartner().getVorname());
                     System.out.println("Unternehmen : " + projekt.getAnsprechpartner().getUnternehmen().getName());
                     
                     rs2 = ConnectMySQLDatabase.getInstance().query("SELECT * FROM student WHERE studentid = " + rs.getInt("student1id"));
                     while(rs2.next())
                     {
                        Student student = new Student(rs2.getInt("studentid"), rs2.getString("anrede"), rs2.getString("vorname"), rs2.getString("name"), 
                              rs2.getString("matrikelnummer"), rs2.getString("email"), rs2.getString("telefon"));
                        projekt.setStudent1(student);
                     }
                     System.out.println("Student name : " + projekt.getStudent1().getName());
                     System.out.println("vorname : " + projekt.getStudent1().getVorname());
//                     System.out.println("student3id : " + rs.getInt("student3id"));
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
                     System.out.println("Student name : " + projekt.getStudent2().getName());
                     System.out.println("vorname : " + projekt.getStudent2().getVorname());
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
                     if(projekt.getStudent3() != null)
                     {
                     System.out.println("Student name : " + projekt.getStudent3().getName());
                     System.out.println("vorname : " + projekt.getStudent3().getVorname());
                     }
                     projekte.add(projekt);
                     System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");


                  }
               }
               catch(SQLException ex)
               {
                  ex.printStackTrace();
               }
               System.out.println(modulid+ " " + " " + modul +" " + semester + " " + jahr);
               new JFrameShower(new MainWindow(modul+ " " + semester + " " + jahr, projekte));
               dispose();
            }
         }
      });
      buttonBox.add(okButton);
      buttonBox.add(Box.createHorizontalGlue());
      JButton abbrechenButton = new JButton(new AbstractAction("Abbrechen")
      {

         @Override
         public void actionPerformed(ActionEvent e)
         {
            dispose();
            System.exit(0);
         }
      });
      okButton.setPreferredSize(new Dimension(96, 26));
      buttonBox.add(abbrechenButton);
      buttonBox.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

      add(buttonBox, BorderLayout.SOUTH);      
      add(components, BorderLayout.CENTER);

      JMenuBar menuBar = new JMenuBar();
      JMenu datei = new JMenu("Datei");
      JMenuItem beenden = new JMenuItem(new AbstractAction("Beenden")
      {

         @Override
         public void actionPerformed(ActionEvent e)
         {
            dispose();
            System.exit(0);            
         }
      });
      datei.add(beenden);
      JMenu optionen = new JMenu("Optionen");
      JMenuItem verbindungdatenMySQL = new JMenuItem(new AbstractAction("Verbindungsdaten AWC")
      {

         @Override
         public void actionPerformed(ActionEvent e)
         {
            SetMySQLConnectionData mysqlConnect = new SetMySQLConnectionData();
            mysqlConnect.setVisible(true);
         }
      });
      optionen.add(verbindungdatenMySQL);
      JMenuItem verbindungsdatenFirebird = new JMenuItem(new AbstractAction("Verbindungsdaten Adiuvo")
      {

         @Override
         public void actionPerformed(ActionEvent e)
         {
            SetFirebirdConnectionData firebirdConnect = new SetFirebirdConnectionData();
            firebirdConnect.setVisible(true);

         }
      });
      optionen.add(verbindungsdatenFirebird);
      menuBar.add(datei);
      menuBar.add(optionen);
      add(menuBar, BorderLayout.NORTH);

   }

   /**
    * 
    * Selektiert alle vorhandenen Modulnamen aus der Datenbank.
    *
    */
   private void fillModulCombobox()
   {
      ResultSet rs;
      rs = ConnectMySQLDatabase.getInstance().query("SELECT DISTINCT modulname FROM modul");
      module.add(new String(""));
      try
      {
         while(rs.next())
         {
            String name;
            name = rs.getString("modulname");
            module.add(name);
         }
      }
      catch(SQLException ex)
      {
         ex.printStackTrace();
      }
   }

   /**
    * 
    * Selektiert alle Semester in denen das ausgewählte Modul stattgefunden hat.
    *
    * @param modul   ausgewähltes Modul
    */
   private void fillSemesterCombobox(String modul)
   {
      ResultSet rs = ConnectMySQLDatabase.getInstance().
      query("SELECT DISTINCT semester FROM modul WHERE modulname = '" + modul + "'");
      try
      {
         while(rs.next())
         {
            String semester;
            semester = rs.getString("semester");
            semesters.add(semester);
         }

      }
      catch(SQLException ex)
      {
         ex.printStackTrace();
      }
   }

   /**
    * 
    * Selektiert alle Jahre in denen das ausgewählte Modul stattgefunden hat.
    *
    * @param modul  ausgewähltes Modul
    */
   private void fillJahrCombobox(String modul, String semester)
   {
      ResultSet rs = ConnectMySQLDatabase.getInstance().
      query("SELECT jahr FROM modul WHERE modulname = '" + modul + "' AND semester = '"
            + semester + "'");
      try
      {
         String jahr;
         while(rs.next())
         {
            jahr = rs.getString("jahr");
            jahre.add(jahr);
         }
      }
      catch(SQLException ex)
      {
         ex.printStackTrace();
      }
   }
}

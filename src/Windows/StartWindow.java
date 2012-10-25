package Windows;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
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
import javax.swing.JPanel;

import Actions.MySQLDatenAuslesen;
import DatenbankConnector.ConnectMySQLDatabase;



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
   /**
    * Alle Module aus der Datenbank (distinct).
    */
   private Vector<String> module = new Vector<String>();
   /**
    * Alle Semester aus der Datenbank (distinct).
    */
   private Vector<String> semesters = new Vector<String>();
   /**
    * Alle Jahre aus der Datenbank.
    */
   private Vector<String> jahre = new Vector<String>();

   /**
    * Combobox für die Modulauswahl.
    */
   private JComboBox modulComboBox;
   /**
    * Combobox für die Semesterauswahl.
    */
   private JComboBox semesterComboBox;
   /**
    * Combobox für die Jahresauswahl.
    */
   private JComboBox jahrComboBox;

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
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(300, 250);
      int x = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - this.getWidth() / 2;
      int y = Toolkit.getDefaultToolkit().getScreenSize().height / 2 - this.getHeight() / 2;
      Point p = new Point(x, y);
      setLocation(p);  

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
      JButton okButton = new JButton(new MySQLDatenAuslesen(modulComboBox, semesterComboBox, jahrComboBox, this));
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

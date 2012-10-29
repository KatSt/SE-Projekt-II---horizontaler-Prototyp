package Actions;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.ResultSet;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import DatenbankConnector.ConnectMySQLDatabase;

/**
 * 
 * Testet ob eine Verbindung zur MySQL-Datenbank aufgebaut werden kann.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 18.10.2012 $ UTC
 */
@SuppressWarnings("serial")
public class TestMySQLConnection extends AbstractAction
{
   private JLabel ergebnis;
   private JTextField host;
   private JTextField port; 
   private JTextField datenbank; 
   private JTextField benutzername;
   private JTextField passwort;
   private JButton speichern;
   
   /**
    * 
    * Übernimmt und setzt die Parameter.
    *
    * @param ergebnis
    * @param host
    * @param port
    * @param datenbank
    * @param benutzername
    * @param passwort
    * @param speichern
    */
   public TestMySQLConnection(JLabel ergebnis, JTextField host, JTextField port, 
         JTextField datenbank, JTextField benutzername, JTextField passwort, JButton speichern)
   {
      super();
      putValue("Name", "Testen");
      this.ergebnis = ergebnis;
      this.host = host;
      this.benutzername = benutzername;
      this.passwort = passwort;
      this.port = port;
      this.datenbank = datenbank;
      this.speichern = speichern;
   }

   /**
    * Testet ob eine Verbindung zur Datenbank aufgebaut werden kann.
    * Wenn ja, Ergebnislabel auf positive Meldung setzen und Speichern-Button enablen.
    * Wenn nein, Ergebnislabel auf negative Meldung setzen. Speichern-Button bleibt disabled.
    */
   @Override
   public void actionPerformed(ActionEvent e)
   {
      ConnectMySQLDatabase.getInstance().setBenutzername(benutzername.getText());
      ConnectMySQLDatabase.getInstance().setPasswort(passwort.getText());
      ConnectMySQLDatabase.getInstance().setPort(port.getText());
      ConnectMySQLDatabase.getInstance().setHost(host.getText());
      ConnectMySQLDatabase.getInstance().setDatenbankname(datenbank.getText());
      
      ResultSet rs;
      rs = ConnectMySQLDatabase.getInstance().query("SELECT * FROM student");
      
      if(rs != null)
      {
         ergebnis.setText("Verbindungsaufbau erfolgreich!");
         ergebnis.setForeground(Color.green);
         speichern.setEnabled(true);
      }
      else
      {         
         ergebnis.setText("Verbindungsaufbau fehlgeschlagen!");
         ergebnis.setForeground(Color.red);
         speichern.setEnabled(false);
         
         String pathname = System.getProperty("user.dir");
         String filename = pathname + "\\Zugangsdaten.xml";
         File file = new File(filename);
         try
         {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);   
            
            NodeList root = doc.getElementsByTagName("Zugangsdaten");
            NodeList zugangsdaten = root.item(0).getChildNodes();
            for(int i = 0; i < zugangsdaten.getLength(); i++)
            {
               if(zugangsdaten.item(i).getNodeName().equals("MySQLConnection"))
               {
                  Element mysql = (Element) zugangsdaten.item(i);
                  ConnectMySQLDatabase.getInstance().setHost(mysql.getAttribute("Host"));
                  ConnectMySQLDatabase.getInstance().setPort(mysql.getAttribute("Port"));
                  ConnectMySQLDatabase.getInstance().setDatenbankname(mysql.getAttribute("Datenbank"));
                  ConnectMySQLDatabase.getInstance().setBenutzername(mysql.getAttribute("Benutzername"));
                  ConnectMySQLDatabase.getInstance().setPasswort(mysql.getAttribute("Passwort"));
               }      
            }
         }
         catch(Exception ex)
         {
            ex.printStackTrace();
         }
      }
   }

}

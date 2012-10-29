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

import DatenbankConnector.ConnectFirebirdDatabase;

/**
 * 
 * Testet den Verbindungsaufbau zur Firebird-Datenbank.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 26.10.2012 $ UTC
 */
@SuppressWarnings("serial")
public class TestFirebirdConnection extends AbstractAction
{
   private JLabel ergebnis;
   private JTextField host;
   private JTextField port; 
   private JTextField pfad; 
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
    * @param pfad
    * @param benutzername
    * @param passwort
    * @param speichern
    */
   public TestFirebirdConnection(JLabel ergebnis, JTextField host, JTextField port, 
         JTextField pfad, JTextField benutzername, JTextField passwort, JButton speichern)
   {
      super();
      putValue("Name", "Testen");
      this.ergebnis = ergebnis;
      this.host = host;
      this.benutzername = benutzername;
      this.passwort = passwort;
      this.port = port;
      this.pfad = pfad;
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
      ConnectFirebirdDatabase.getInstance().setBenutzername(benutzername.getText());
      ConnectFirebirdDatabase.getInstance().setPasswort(passwort.getText());
      ConnectFirebirdDatabase.getInstance().setPort(port.getText());
      ConnectFirebirdDatabase.getInstance().setHost(host.getText());
      ConnectFirebirdDatabase.getInstance().setPfad(pfad.getText());
      
      ResultSet rs;
      rs = ConnectFirebirdDatabase.getInstance().query("SELECT * FROM student");
      
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
  
               if(zugangsdaten.item(i).getNodeName().equals("FirebirdConnection"))
               {
                  Element firebird = (Element) zugangsdaten.item(i);
                  ConnectFirebirdDatabase.getInstance().setHost(firebird.getAttribute("Host"));
                  ConnectFirebirdDatabase.getInstance().setPort(firebird.getAttribute("Port"));
                  ConnectFirebirdDatabase.getInstance().setPfad(firebird.getAttribute("Pfad"));
                  ConnectFirebirdDatabase.getInstance().setBenutzername(firebird.getAttribute("Benutzername"));
                  ConnectFirebirdDatabase.getInstance().setPasswort(firebird.getAttribute("Passwort"));
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

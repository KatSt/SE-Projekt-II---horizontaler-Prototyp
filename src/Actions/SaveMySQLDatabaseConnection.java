package Actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import DatenbankConnector.ConnectMySQLDatabase;

/**
 * 
 * Speichert die Zugangsdaten für die MySQL Datenbank.
 * Setzt die Attribute in der Connect-Klasse
 * und schreibt die Zugangsdaten in ein XML-File.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 18.10.2012 $ UTC
 */
@SuppressWarnings("serial")
public class SaveMySQLDatabaseConnection extends AbstractAction
{
   private JTextField host;
   private JTextField port;
   private JTextField datenbankname;
   private JTextField benutzername;
   private JTextField passwort;
   private JDialog dialog;

   /**
    * 
    * Übernimmt und setzt die Parameter.
    *
    * @param dialog
    * @param host
    * @param port
    * @param datenbankname
    * @param benutzername
    * @param passwort
    */
   public SaveMySQLDatabaseConnection(JDialog dialog, JTextField host, JTextField port, JTextField datenbankname, 
         JTextField benutzername, JTextField passwort)
   {
      super();
      putValue(NAME, "Speichern");
      this.dialog = dialog;
      this.host = host;
      this.port = port;
      this.datenbankname = datenbankname;
      this.benutzername = benutzername;
      this.passwort = passwort;
   }

   /**
    * Setzt die Attribute in der ConnectMySQLDatabase-Klasse
    * auf die aktuellen Werte.
    * Schreibt die aktuellen Werte in die XML-Datei.
    */
   @Override
   public void actionPerformed(ActionEvent e)
   {
      ConnectMySQLDatabase.getInstance().setBenutzername(benutzername.getText());
      ConnectMySQLDatabase.getInstance().setPasswort(passwort.getText());
      ConnectMySQLDatabase.getInstance().setDatenbankname(datenbankname.getText());
      ConnectMySQLDatabase.getInstance().setHost(host.getText());
      ConnectMySQLDatabase.getInstance().setPort(port.getText());   
      dialog.dispose();     


      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder;

      try
      {
         docBuilder = docFactory.newDocumentBuilder();

         Document doc = docBuilder.parse("Zugangsdaten.xml");

         Element root = doc.getDocumentElement();
         
         Element connectionData;
         NodeList hostNL = root.getElementsByTagName("MySQLConnection");
         if(hostNL.getLength() == 0)
         {
            connectionData = doc.createElement("MySQLConnection");
         }
         else
         {
            connectionData = (Element) hostNL.item(0);
         }
         connectionData.setAttribute("Host", host.getText());
         connectionData.setAttribute("Port", port.getText());
         connectionData.setAttribute("Datenbank", datenbankname.getText());
         connectionData.setAttribute("Benutzername", benutzername.getText());
         connectionData.setAttribute("Passwort", passwort.getText());
         root.appendChild(connectionData);
         
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
         Transformer transformer = transformerFactory.newTransformer();
         DOMSource source = new DOMSource(doc);
         StreamResult result = new StreamResult(new File("Zugangsdaten.xml"));
         transformer.transform(source, result);
      }
      catch(Exception ex)
      {
         JOptionPane.showMessageDialog(null, "Fehler beim Schreiben in Datei!", "Error", JOptionPane.ERROR_MESSAGE);
      }
   }

}

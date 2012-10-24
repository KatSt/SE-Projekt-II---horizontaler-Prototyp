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

import DatenbankConnector.ConnectFirebirdDatabase;

/**
 * 
 * Speichert die Zugangsdaten für die Firebird Datenbank.
 * Setzt die Attribute in der Connect-Klasse
 * und schreibt die Zugangsdaten in ein XML-File.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 18.10.2012 $ UTC
 */
@SuppressWarnings("serial")
public class SaveFirebirdConnectionData extends AbstractAction
{
   private JTextField host;
   private JTextField port;
   private JTextField pfad;
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
    * @param pfad
    * @param benutzername
    * @param passwort
    */
   public SaveFirebirdConnectionData(JDialog dialog, JTextField host, JTextField port, JTextField pfad, 
         JTextField benutzername, JTextField passwort)
   {
      super();
      putValue(NAME, "Speichern");
      this.dialog = dialog;
      this.host = host;
      this.port = port;
      this.pfad = pfad;
      this.benutzername = benutzername;
      this.passwort = passwort;
   }

   /**
    * Setzt die Attribute in der ConnectFirebirdDatabase-Klasse
    * auf die aktuellen Werte.
    * Schreibt die aktuellen Werte in die XML-Datei.
    */
   @Override
   public void actionPerformed(ActionEvent e)
   {
      ConnectFirebirdDatabase.getInstance().setBenutzername(benutzername.getText());
      ConnectFirebirdDatabase.getInstance().setPasswort(passwort.getText());
      ConnectFirebirdDatabase.getInstance().setPfad(pfad.getText());
      ConnectFirebirdDatabase.getInstance().setHost(host.getText());
      ConnectFirebirdDatabase.getInstance().setPort(port.getText());   
      dialog.dispose();     


      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder;

      try
      {
         docBuilder = docFactory.newDocumentBuilder();

         Document doc = docBuilder.parse("Zugangsdaten.xml");

         Element root = doc.getDocumentElement();
         
         Element connectionData;
         NodeList hostNL = root.getElementsByTagName("FirebirdConnection");
         if(hostNL.getLength() == 0)
         {
            connectionData = doc.createElement("FirebirdConnection");
         }
         else
         {
            connectionData = (Element) hostNL.item(0);
         }
         connectionData.setAttribute("Host", host.getText());
         connectionData.setAttribute("Port", port.getText());
         connectionData.setAttribute("Pfad", pfad.getText());
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

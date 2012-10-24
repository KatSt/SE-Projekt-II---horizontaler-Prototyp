package Main;
import java.io.File;

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
import DatenbankConnector.ConnectMySQLDatabase;
import Windows.StartWindow;


/**
 * 
 * Enthält die Main-Methode.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 18.10.2012 $ UTC
 */
public class Main
{
   private Main()
   {

   }
   
   /**
    * 
    * Öffnet das Startfenster.
    * Erzeugt die xml-Datei für die Zugangsdaten, falls noch nicht vorhanden.
    * Wenn xml schon vorhanden werden die Zugangsdaten ausgelesen.
    *
    * @param args
    */
   public static void main(String[] args)
   {

      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder;
      String pathname = System.getProperty("user.dir");
      String filename = pathname + "\\Zugangsdaten.xml";
      File file = new File(filename);
      if(!file.exists())
      {
         try
         {    
            docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();  
            Element root = doc.createElement("Zugangsdaten");
            doc.appendChild(root);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

         }
         catch(Exception e)
         {
            e.printStackTrace();
         }
      }
      else
      {
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
         catch(Exception e)
         {
            e.printStackTrace();
         }
      }
      StartWindow startWindow = new StartWindow();
      new JFrameShower(startWindow);
   }
}

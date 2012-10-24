package Actions;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

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
      }
   }

}

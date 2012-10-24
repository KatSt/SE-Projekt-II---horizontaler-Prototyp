package Actions;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import DatenbankConnector.ConnectFirebirdDatabase;

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
      }
   }
}

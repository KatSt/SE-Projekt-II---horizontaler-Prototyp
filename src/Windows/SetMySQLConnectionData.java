package Windows;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import Actions.SaveMySQLDatabaseConnection;
import Actions.TestMySQLConnection;


/**
 * 
 * Öffnet einen JDialog zur Eingabe der Verbindungsdaten zur AWC(MySQL) Datenbank.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 04.10.2012 $ UTC
 */
@SuppressWarnings("serial")
public class SetMySQLConnectionData extends JDialog
{
   /**
    * 
    * Konstruktor.
    *
    */
   public SetMySQLConnectionData()
   {
      super();
      setModal(true);
      setTitle("Verbindungsdaten AWC");
      init();
   }

   /**
    * 
    * Initialisiert die Oberfläche des JDialog.
    *
    */
   public void init()
   {
      setLocation(250, 250);      
      setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
      setSize(350, 280);
      
      Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
      
      Box verbindungsdaten = Box.createVerticalBox();
      
      Dimension labelDimension = new Dimension(100, 16);
      Dimension textFieldDimension = new Dimension(200, 30);
      
      JPanel hostPanel = new JPanel(new FlowLayout());
      JLabel host = new JLabel("Host:");
      host.setPreferredSize(labelDimension);
      hostPanel.add(host);
      JTextField hostTextField = new JTextField();
      hostTextField.setPreferredSize(textFieldDimension);
      hostPanel.add(hostTextField);
      verbindungsdaten.add(hostPanel);
      
      JPanel portPanel = new JPanel(new FlowLayout());
      JLabel port = new JLabel("Port:");
      port.setPreferredSize(labelDimension);
      portPanel.add(port);
      JTextField portTextField = new JTextField();
      portTextField.setPreferredSize(textFieldDimension);
      portPanel.add(portTextField);
      verbindungsdaten.add(portPanel);
      
      JPanel datenbankPanel = new JPanel(new FlowLayout());
      JLabel datenbank = new JLabel("Datenbank:");
      datenbank.setPreferredSize(labelDimension);
      datenbankPanel.add(datenbank);
      JTextField datenbankTextField = new JTextField();
      datenbankTextField.setPreferredSize(textFieldDimension);
      datenbankPanel.add(datenbankTextField);
      verbindungsdaten.add(datenbankPanel);
      
      JPanel usernamePanel = new JPanel(new FlowLayout());
      JLabel username = new JLabel("Benutzername:");
      username.setPreferredSize(labelDimension);
      usernamePanel.add(username);
      JTextField usernameTextField = new JTextField();
      usernameTextField.setPreferredSize(textFieldDimension);
      usernamePanel.add(usernameTextField);
      verbindungsdaten.add(usernamePanel);
      
      JPanel passwordPanel = new JPanel(new FlowLayout());
      JLabel password = new JLabel("Passwort:");
      password.setPreferredSize(labelDimension);
      passwordPanel.add(password);
      JTextField passwordTextField = new JTextField();
      passwordTextField.setPreferredSize(textFieldDimension);
      passwordPanel.add(passwordTextField);
      verbindungsdaten.add(passwordPanel);
      
      JPanel ergebnisPanel = new JPanel(new FlowLayout());
      ergebnisPanel.setPreferredSize(new Dimension(labelDimension.width + textFieldDimension.width, 20));
      JLabel testErgebnis = new JLabel("e");
      testErgebnis.setForeground(passwordPanel.getBackground());
      testErgebnis.setAlignmentX(SwingConstants.CENTER);
      ergebnisPanel.add(testErgebnis);
      verbindungsdaten.add(ergebnisPanel);
      
      verbindungsdaten.setBorder(border);
      add(verbindungsdaten, BorderLayout.CENTER);
      
      JPanel buttonPanel = new JPanel(new FlowLayout());
      JButton speichern = new JButton(new SaveMySQLDatabaseConnection(this, hostTextField, 
            portTextField, datenbankTextField, usernameTextField,
            passwordTextField));
      speichern.setEnabled(false);
      buttonPanel.add(speichern);
      JButton testen = new JButton(new TestMySQLConnection(testErgebnis, hostTextField, portTextField, 
            datenbankTextField, usernameTextField, passwordTextField, speichern));
      buttonPanel.add(testen);
      JButton abbrechen = new JButton(new AbstractAction("Abbrechen")
      {
         
         @Override
         public void actionPerformed(ActionEvent e)
         {            
            dispose();
         }
      });
      buttonPanel.add(abbrechen);
      buttonPanel.setBorder(border);
      add(buttonPanel, BorderLayout.SOUTH);
   }
}

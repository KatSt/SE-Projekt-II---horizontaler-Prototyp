package Windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Listener.TableMouseListener;
import Main.JFrameShower;
import Objekt.Projektantrag;
import TableModel.ProjekttitelModel;



/**
 * 
 * Hauptfenster der Anwendung.
 * Zeigt eine Liste der Projekttitel, welche zur Anzeige der Daten 
 * und zum Archivieren ausgewählt werden können.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 04.10.2012 $ UTC
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame
{
   private Vector<Projektantrag> projekttitel;
   
   /**
    * 
    * Konstruktor.
    *
    */
   public MainWindow(String titel, Vector<Projektantrag> projekttitel)
   {
      super();
      setTitle(titel);
      this.projekttitel = projekttitel;
      init();
   }
   
   /**
    * Initialisiert die Oberfläche.
    */
   public void init()
   {
      setLocation(200, 200);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(500, 350);
      
//      initProjekttitel();
      ProjekttitelModel tableModel = new ProjekttitelModel(projekttitel);
      JTable projektTable = new JTable(tableModel);
      projektTable.setEnabled(true);

      projektTable.addMouseListener(new TableMouseListener(projektTable, projekttitel));
      JScrollPane projektScrollPane = new JScrollPane(projektTable);
      add(projektScrollPane, BorderLayout.CENTER);
      
      JMenuBar menuBar = new JMenuBar();
      JMenu datei = new JMenu("Datei");
      JMenuItem neu = new JMenuItem(new AbstractAction("Modulauswahl")
      {
         
         @Override
         public void actionPerformed(ActionEvent e)
         {
            dispose();
            new JFrameShower(new StartWindow());
            
         }
      });
      datei.add(neu);
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

      menuBar.add(datei);
      add(menuBar, BorderLayout.NORTH);
   }
   
//   public void initProjekttitel()
//   {
//      projekttitel = new Vector<Projektantrag>();
//      
//      projekttitel.add("Projekt 1");
//      projekttitel.add("Projekt 2");
//   }
   
}

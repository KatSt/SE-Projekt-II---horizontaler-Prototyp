package Windows;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import Actions.AlleFelderLeeren;
import Actions.AlleFelderUebernehmen;
import Actions.Archivieren;
import Actions.TextAreaUebernehmenButton;
import Actions.UebernehmenButton;
import DatenbankConnector.ConnectFirebirdDatabase;
import Listener.AnsprechpartnerTableMouseListener;
import Listener.TabChangeListener;
import Listener.TextAreaFocusListener;
import Listener.TextFieldFocusListener;
import Listener.UnternehmenTableMouseListener;
import Objekt.Ansprechpartner;
import Objekt.Projektantrag;
import Objekt.Student;
import Objekt.Unternehmen;
import TableModel.AnsprechpartnerTableModel;
import TableModel.ProjekttitelModel;
import TableModel.UnternehmenTableModel;

/**
 * 
 * Fenster das die Projektdaten zur Anpassung und Archivierung anzeigt.
 *
 * @author $Author: Katharina Stein 04.10.2012 $
 * @version $Revision:  $, $Date:  $ UTC
 */
@SuppressWarnings("serial")
public class EditProjektWindow extends JDialog
{
   private Projektantrag projekt;
   private Dimension labelDimension = new Dimension(130, 16);
   private Dimension textFieldDimension = new Dimension(300, 30);
   private Dimension buttonDimension = new Dimension (55, 26);
   private Dimension textAreaDimension = new Dimension(300, 200);
   
   private Vector<JTextField> student1AdiuvoAWCTextFields = new Vector<JTextField>();
   private Vector<JTextField> student2AdiuvoAWCTextFields = new Vector<JTextField>();
   private Vector<JTextField> student3AdiuvoAWCTextFields = new Vector<JTextField>();
   private Vector<JTextField> projektAdiuvoAWCTextFields = new Vector<JTextField>();
   private Vector<JTextField> student1AdiuvoTextFields = new Vector<JTextField>();
   private Vector<JTextField> student2AdiuvoTextFields = new Vector<JTextField>();
   private Vector<JTextField> student3AdiuvoTextFields = new Vector<JTextField>();
   private Vector<JTextField> projektAdiuvoTextFields = new Vector<JTextField>();
   private Vector<JTextArea> projektAdiuvoTextAreas = new Vector<JTextArea>();
   private Vector<JTextField> unternehmenAdiuvoTextFields = new Vector<JTextField>();
   private Vector<JTextField> ansprechpartnerAdiuvoTextFields = new Vector<JTextField>();
   
   private Student adiuvoStudent1;
   private Student adiuvoStudent2;
   private Student adiuvoStudent3;
   private Vector<Ansprechpartner> adiuvoAnsprechpartnerVector = new Vector<Ansprechpartner>();
   private Vector<Unternehmen> adiuvoUnternehmenVector = new Vector<Unternehmen>();
   private Ansprechpartner adiuvoAnsprechpartner;
   private Unternehmen adiuvoUnternehmen;
   private HashMap<Integer, Boolean> tabs = new HashMap<Integer, Boolean>();
   private ProjekttitelModel model;
   
  
   /**
    * 
    * Konstruktor.
    *
    * @param ptitel     Titel des ausgewählten Projekts.
    */
   public EditProjektWindow(Projektantrag ptitel, ProjekttitelModel model)
   {
      super();
      this.model = model;
      setTitle(ptitel.toString());
      setModal(true);
      projekt = ptitel;
      selectDataFromFirebird();
      init();
//      pack();
      setResizable(true);

   }

   /**
    * 
    * Initialisiet den JTabbedPane.
    *
    */
   private void init()
   {
    
      setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
      if(Toolkit.getDefaultToolkit().getScreenSize().width < 900 || Toolkit.getDefaultToolkit().getScreenSize().height < 600)
      {
       setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height-40));
      }
      else
      {
         setSize(900, 600);
      }

      int x = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - this.getWidth() / 2;
      int y = Toolkit.getDefaultToolkit().getScreenSize().height / 2 - this.getHeight() / 2;
      Point p = new Point(x, y);
      setLocation(p);  
      
      JTabbedPane tabbedPane = new JTabbedPane();

      Color color = new Color(102, 205, 0, 100);
      int index = 0;
      tabbedPane.add("Projekt", initProjektTab());
      tabbedPane.setBackgroundAt(0, color);
      tabs.put(index++, true);
      tabbedPane.add("Student 1", initStudentTab(projekt.getStudent1(), student1AdiuvoAWCTextFields, student1AdiuvoTextFields, adiuvoStudent1));
      tabs.put(index++, false);
      if(projekt.getStudent2() != null)
      {
         tabbedPane.add("Student 2", initStudentTab(projekt.getStudent2(), student2AdiuvoAWCTextFields, student2AdiuvoTextFields, adiuvoStudent2));
         tabs.put(index++, false);
      }
      if(projekt.getStudent3() != null)
      {
         tabbedPane.add("Student 3", initStudentTab(projekt.getStudent3(), student3AdiuvoAWCTextFields, student3AdiuvoTextFields, adiuvoStudent3));
         tabs.put(index++, false);
      }

      tabbedPane.add("Ansprechpartner", initAnsprechpartnerTab(projekt.getAnsprechpartner()));
      tabs.put(index++, false);
      tabbedPane.add("Unternehmen", initUnternehmenTab());
      tabs.put(index, false);
      tabbedPane.addChangeListener(new TabChangeListener(tabs));
      
      add(tabbedPane, BorderLayout.CENTER);

      add(initSouthButtonBox(), BorderLayout.SOUTH);
   }

   /** 
    * 
    * Initialisiert die ButtonBox für den South-Bereich.
    *
    * @return   Box mit Buttons.
    */
   private Box initSouthButtonBox()
   {
      Box buttonBox = Box.createHorizontalBox();

      JButton archivieren = new JButton(new Archivieren(tabs, student1AdiuvoTextFields, student2AdiuvoTextFields, student3AdiuvoTextFields, projektAdiuvoTextFields, projektAdiuvoTextAreas,
            unternehmenAdiuvoTextFields, ansprechpartnerAdiuvoTextFields, adiuvoStudent1, adiuvoStudent2, adiuvoStudent3, getAdiuvoAnsprechpartner(), getAdiuvoUnternehmen(), this, model));
      buttonBox.add(archivieren);
      buttonBox.add(Box.createHorizontalGlue());

      JButton abbrechen = new JButton(new AbstractAction("Abbrechen")
      {

         @Override
         public void actionPerformed(ActionEvent e)
         {
            dispose();            
         }
      });      
      buttonBox.add(abbrechen);

      return buttonBox;

   }

   /**
    * 
    * Initialisiert den Tab für die Studenten.
    *
    * @return   JScrollPane mit Studentendaten.
    */
   private JScrollPane initStudentTab(Student student, Vector<JTextField> alleFelderAdiuvoAWC, Vector<JTextField> alleFelderAdiuvo, Student adiuvoStudent)
   {
      
      Vector<JTextField> alleFelderAWC = new Vector<JTextField>();

      Box tab1 = Box.createVerticalBox();
      JScrollPane scrollPanetab1 = new JScrollPane(tab1);

      // JPanel Überschriften
      JPanel ueberschriften = new JPanel(new FlowLayout());
      JLabel empty = new JLabel();
      empty.setPreferredSize(labelDimension);
      ueberschriften.add(empty);

      JLabel awc = new JLabel("AWC");
      awc.setHorizontalAlignment(SwingConstants.CENTER);
      awc.setPreferredSize(textFieldDimension);
      ueberschriften.add(awc);

      JLabel empty2 = new JLabel();
      empty2.setPreferredSize(buttonDimension);
      ueberschriften.add(empty2);

      JLabel adiuvo = new JLabel("Adiuvo");
      adiuvo.setHorizontalAlignment(SwingConstants.CENTER);
      adiuvo.setPreferredSize(textFieldDimension);
      ueberschriften.add(adiuvo);

      tab1.add(ueberschriften);
      
      // JPanel Anrede
      JPanel info1 = new JPanel(new FlowLayout());
      JLabel studentanrede = new JLabel("Anrede:");
      studentanrede.setPreferredSize(labelDimension);
      info1.add(studentanrede);

      JTextField studentanredeAWC = new JTextField();
      studentanredeAWC.setEditable(false);
      studentanredeAWC.setPreferredSize(textFieldDimension);
      info1.add(studentanredeAWC);
      
      JButton anredeUebernehmen = new JButton();
      anredeUebernehmen.setPreferredSize(buttonDimension);
      anredeUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info1.add(anredeUebernehmen);

      JTextField studentanredeFirebird = new JTextField();
      studentanredeFirebird.setPreferredSize(textFieldDimension);
      info1.add(studentanredeFirebird);
      anredeUebernehmen.setAction(new UebernehmenButton(studentanredeAWC, studentanredeFirebird));      
      
      tab1.add(info1);

      // JPanel Name      
      JPanel info2 = new JPanel(new FlowLayout());
      JLabel studentname = new JLabel("Name:");
      studentname.setPreferredSize(labelDimension);
      info2.add(studentname);

      JTextField studentnameAWC = new JTextField();
      studentnameAWC.setEditable(false);
      studentnameAWC.setPreferredSize(textFieldDimension);
      info2.add(studentnameAWC);
      
      JButton nameUebernehmen = new JButton();
      nameUebernehmen.setPreferredSize(buttonDimension);
      nameUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info2.add(nameUebernehmen);

      JTextField studentnameFirebird = new JTextField();
      studentnameFirebird.setPreferredSize(textFieldDimension);
      info2.add(studentnameFirebird);
      nameUebernehmen.setAction(new UebernehmenButton(studentnameAWC, studentnameFirebird));
      tab1.add(info2);

      // JPanel Vorname
      JPanel info3 = new JPanel(new FlowLayout());
      JLabel studentvorname = new JLabel("Vorname:");
      studentvorname.setPreferredSize(labelDimension);
      info3.add(studentvorname);

      JTextField studentvornameAWC = new JTextField();
      studentvornameAWC.setEditable(false);
      studentvornameAWC.setPreferredSize(textFieldDimension);
      info3.add(studentvornameAWC);
      
      JButton vornameUebernehmen = new JButton(">");
      vornameUebernehmen.setPreferredSize(buttonDimension);
      vornameUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info3.add(vornameUebernehmen);

      JTextField studentvornameFirebird = new JTextField();
      studentvornameFirebird.setPreferredSize(textFieldDimension);
      info3.add(studentvornameFirebird);  
      vornameUebernehmen.setAction(new UebernehmenButton(studentvornameAWC, studentvornameFirebird));
      tab1.add(info3);

      // JPanel Matrikelnummer
      JPanel info4 = new JPanel(new FlowLayout());
      JLabel studentmatrikelnr = new JLabel("Matrikelnummer:");
      studentmatrikelnr.setPreferredSize(labelDimension);
      info4.add(studentmatrikelnr);

      JTextField studentmatrikelnrAWC = new JTextField();
      studentmatrikelnrAWC.setEditable(false);
      studentmatrikelnrAWC.setPreferredSize(textFieldDimension);
      info4.add(studentmatrikelnrAWC);
      
      JButton matrikelnrUebernehmen = new JButton(">");
      matrikelnrUebernehmen.setPreferredSize(buttonDimension);
      matrikelnrUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info4.add(matrikelnrUebernehmen);

      JTextField studentmatrikelnrFirebird = new JTextField();
      studentmatrikelnrFirebird.setEditable(false);
      studentmatrikelnrFirebird.setPreferredSize(textFieldDimension);
      info4.add(studentmatrikelnrFirebird);  
      matrikelnrUebernehmen.setAction(new UebernehmenButton(studentmatrikelnrAWC, studentmatrikelnrFirebird));
      tab1.add(info4);

      // JPanel Email
      JPanel info5 = new JPanel(new FlowLayout());
      JLabel studentemail = new JLabel("Email:");
      studentemail.setPreferredSize(labelDimension);
      info5.add(studentemail);

      JTextField studentemailAWC = new JTextField();
      studentemailAWC.setEditable(false);
      studentemailAWC.setPreferredSize(textFieldDimension);
      info5.add(studentemailAWC);
      
      JButton emailUebernehmen = new JButton(">");
      emailUebernehmen.setPreferredSize(buttonDimension);
      emailUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info5.add(emailUebernehmen);

      JTextField studentemailFirebird = new JTextField();
      studentemailFirebird.setPreferredSize(textFieldDimension);
      info5.add(studentemailFirebird);      
      emailUebernehmen.setAction(new UebernehmenButton(studentemailAWC, studentemailFirebird));
      tab1.add(info5);

      // JPanel Telefon
      JPanel info6 = new JPanel(new FlowLayout());
      JLabel studenttelefon = new JLabel("Telefon:");
      studenttelefon.setPreferredSize(labelDimension);
      info6.add(studenttelefon);

      JTextField studenttelefonAWC = new JTextField();
      studenttelefonAWC.setEditable(false);
      studenttelefonAWC.setPreferredSize(textFieldDimension);
      info6.add(studenttelefonAWC);
      
      JButton telefonUebernehmen = new JButton(">");
      telefonUebernehmen.setPreferredSize(buttonDimension);
      telefonUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info6.add(telefonUebernehmen);

      JTextField studenttelefonFirebird = new JTextField();
      studenttelefonFirebird.setPreferredSize(textFieldDimension);
      info6.add(studenttelefonFirebird);   
      telefonUebernehmen.setAction(new UebernehmenButton(studenttelefonAWC, studenttelefonFirebird));
      tab1.add(info6);
      
      // JPanel Internetseite
      JPanel info9 = new JPanel(new FlowLayout());
      JLabel studentinternetseite = new JLabel("Internetseite:");
      studentinternetseite.setPreferredSize(labelDimension);
      info9.add(studentinternetseite);

      JPanel studentinternetseiteAWC = new JPanel();
      studentinternetseiteAWC.setPreferredSize(new Dimension(textFieldDimension.width + buttonDimension.width + 5, textFieldDimension.height));
      info9.add(studentinternetseiteAWC);

      JTextField studentinternetseiteFirebird = new JTextField();
      studentinternetseiteFirebird.setPreferredSize(textFieldDimension);
      info9.add(studentinternetseiteFirebird);      
      tab1.add(info9);
      
   // JPanel Kommentar
      JPanel info7 = new JPanel(new FlowLayout());
      JLabel studentkommentar = new JLabel("Kommentar:");
      studentkommentar.setPreferredSize(labelDimension);
      info7.add(studentkommentar);

      JPanel studentkommentarAWC = new JPanel();
      studentkommentarAWC.setPreferredSize(textFieldDimension);
      info7.add(studentkommentarAWC);
      
      JLabel kommentarempty = new JLabel();
      kommentarempty.setPreferredSize(buttonDimension);
      info7.add(kommentarempty);

      JTextField studentkommentarFirebird = new JTextField();
      studentkommentarFirebird.setPreferredSize(textFieldDimension);
      info7.add(studentkommentarFirebird);      
      tab1.add(info7);
      
   // JPanel Kommentar
      JPanel info8 = new JPanel(new FlowLayout());
      JLabel studentkommentarIntern = new JLabel("Kommentar intern:");
      studentkommentarIntern.setPreferredSize(labelDimension);
      info8.add(studentkommentarIntern);

      JPanel studentkommentarInternAWC = new JPanel();
      studentkommentarInternAWC.setPreferredSize(textFieldDimension);
      info8.add(studentkommentarInternAWC);
      
      JLabel kommentarinternEmpty = new JLabel();
      kommentarinternEmpty.setPreferredSize(buttonDimension);
      info8.add(kommentarinternEmpty);

      JTextField studentkommentarInternFirebird = new JTextField();
      studentkommentarInternFirebird.setPreferredSize(textFieldDimension);
      info8.add(studentkommentarInternFirebird);      
      tab1.add(info8);
      
      alleFelderAWC.add(studentanredeAWC);
      alleFelderAWC.add(studentvornameAWC);
      alleFelderAWC.add(studentnameAWC);
      alleFelderAWC.add(studenttelefonAWC);
      alleFelderAWC.add(studentemailAWC);
      alleFelderAWC.add(studentmatrikelnrAWC);
      
      alleFelderAdiuvoAWC.add(studentanredeFirebird);
      alleFelderAdiuvoAWC.add(studentvornameFirebird);
      alleFelderAdiuvoAWC.add(studentnameFirebird);
      alleFelderAdiuvoAWC.add(studenttelefonFirebird);
      alleFelderAdiuvoAWC.add(studentemailFirebird);
      alleFelderAdiuvoAWC.add(studentmatrikelnrFirebird);
      
      for(JTextField field : alleFelderAdiuvoAWC)
      {
         alleFelderAdiuvo.add(field);
      }
      alleFelderAdiuvo.add(studentinternetseiteFirebird);
      alleFelderAdiuvo.add(studentkommentarFirebird);
      alleFelderAdiuvo.add(studentkommentarInternFirebird);
      
      studentanredeAWC.setText(student.getAnrede());
      studentnameAWC.setText(student.getName());
      studentvornameAWC.setText(student.getVorname());
      studentmatrikelnrAWC.setText(student.getMatrikelnummer());
      studentemailAWC.setText(student.getEmail());
      studenttelefonAWC.setText(student.getTelefon());

      if(adiuvoStudent != null)
      {
         studentanredeFirebird.setText(adiuvoStudent.getAnrede());
         studentnameFirebird.setText(adiuvoStudent.getName());
         studentvornameFirebird.setText(adiuvoStudent.getVorname());
         studentmatrikelnrFirebird.setText(adiuvoStudent.getMatrikelnummer());
         studentemailFirebird.setText(adiuvoStudent.getEmail());
         studenttelefonFirebird.setText(adiuvoStudent.getTelefon());
         studentkommentarFirebird.setText(adiuvoStudent.getKommentar());
         studentkommentarInternFirebird.setText(adiuvoStudent.getKommentarIntern());
         studentinternetseiteFirebird.setText(adiuvoStudent.getInternetseite());
      }

      // Buttons
      JPanel buttonPanel = new JPanel(new FlowLayout());
      JLabel emptyButton = new JLabel();
      emptyButton.setPreferredSize(new Dimension(labelDimension.width + textFieldDimension.width + 5, 30));
      buttonPanel.add(emptyButton);

      JButton alleUebernehmen = new JButton("Alle");
      alleUebernehmen.setPreferredSize(buttonDimension);
      alleUebernehmen.setAction(new AlleFelderUebernehmen(alleFelderAWC, alleFelderAdiuvoAWC, null, null));
      buttonPanel.add(alleUebernehmen);      

      JPanel alleFelderLeerenPanel = new JPanel();
      alleFelderLeerenPanel.setLayout(new BoxLayout(alleFelderLeerenPanel, BoxLayout.Y_AXIS));
      alleFelderLeerenPanel.setPreferredSize(new Dimension(textFieldDimension.getSize().width, 28));
      JButton alleLeeren = new JButton("Alle Felder leeren");
      alleLeeren.setAction(new AlleFelderLeeren(alleFelderAdiuvo, null));
      alleLeeren.setAlignmentX(Component.CENTER_ALIGNMENT);
      alleFelderLeerenPanel.add(alleLeeren);
      buttonPanel.add(alleFelderLeerenPanel);
      tab1.add(buttonPanel);
           
      return scrollPanetab1;
   }   

   /**
    * 
    * Initialisiet den Tab mit den Projektdaten.
    *
    * @return   JScrollPane mit Projekt.
    */
   private JScrollPane initProjektTab()
   {
      Box tab = Box.createVerticalBox();

      JScrollPane scrollPanetab = new JScrollPane(tab);
      Vector<JTextField> alleAWCFelder = new Vector<JTextField>();
      Vector<JTextArea> alleAWCTextAreas = new Vector<JTextArea>();

      // JPanel Überschriften
      JPanel ueberschriften = new JPanel(new FlowLayout());
      JLabel empty = new JLabel();
      empty.setPreferredSize(labelDimension);
      ueberschriften.add(empty);

      JLabel awc = new JLabel("AWC");
      awc.setHorizontalAlignment(SwingConstants.CENTER);
      awc.setPreferredSize(textFieldDimension);
      ueberschriften.add(awc);

      JLabel empty2 = new JLabel();
      empty2.setPreferredSize(buttonDimension);
      ueberschriften.add(empty2);

      JLabel adiuvo = new JLabel("Adiuvo");
      adiuvo.setHorizontalAlignment(SwingConstants.CENTER);
      adiuvo.setPreferredSize(textFieldDimension);
      ueberschriften.add(adiuvo);

      tab.add(ueberschriften);

      // Buttons
      JPanel buttonPanel = new JPanel(new FlowLayout());
      JLabel emptyButton = new JLabel();
      emptyButton.setPreferredSize(new Dimension(labelDimension.width + textFieldDimension.width + 5, 30));
      buttonPanel.add(emptyButton);

      JButton alleUebernehmen = new JButton("Alle");
      alleUebernehmen.setPreferredSize(buttonDimension);
      buttonPanel.add(alleUebernehmen);      

      JPanel alleFelderLeerenPanel = new JPanel();
      alleFelderLeerenPanel.setLayout(new BoxLayout(alleFelderLeerenPanel, BoxLayout.Y_AXIS));
      alleFelderLeerenPanel.setPreferredSize(new Dimension(textFieldDimension.getSize().width, 28));
      JButton alleLeeren = new JButton("Alle Felder leeren");
      alleLeeren.setAlignmentX(Component.CENTER_ALIGNMENT);
      alleFelderLeerenPanel.add(alleLeeren);
      buttonPanel.add(alleFelderLeerenPanel);
      tab.add(buttonPanel);
      
      // JPanel Titel
      JPanel info1 = new JPanel(new FlowLayout());
      JLabel titel = new JLabel("Projekttitel:");
      titel.setPreferredSize(labelDimension);
      info1.add(titel);

      JTextField projekttitelAWC = new JTextField();
      projekttitelAWC.setEditable(false);
      projekttitelAWC.setPreferredSize(textFieldDimension);
      info1.add(projekttitelAWC);

      JButton titelUebernehmen = new JButton();
      titelUebernehmen.setPreferredSize(buttonDimension);
      titelUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info1.add(titelUebernehmen);

      JTextField projekttitelAdiuvo = new JTextField();
      projekttitelAdiuvo.setPreferredSize(textFieldDimension);
      titelUebernehmen.setAction(new UebernehmenButton(projekttitelAWC, projekttitelAdiuvo));
      info1.add(projekttitelAdiuvo);
      tab.add(info1);
      

      // JPanel Projektart
      JPanel info5 = new JPanel(new FlowLayout());
      JLabel projektart = new JLabel("Projektart:");
      projektart.setPreferredSize(labelDimension);
      info5.add(projektart);

      JPanel projektartAWC = new JPanel();
      projektartAWC.setPreferredSize(textFieldDimension);
      info5.add(projektartAWC);

      JPanel projektartUebernehmen = new JPanel();
      projektartUebernehmen.setPreferredSize(buttonDimension);
      info5.add(projektartUebernehmen);    

      JTextField projektartAdiuvo = new JTextField("Projektarbeit");
      projektartAdiuvo.addFocusListener(new TextFieldFocusListener(projektartAdiuvo));
      projektartAdiuvo.setPreferredSize(textFieldDimension);
      info5.add(projektartAdiuvo);      
      tab.add(info5);

      // JPanel Technologien
      JPanel info6 = new JPanel(new FlowLayout());
      JLabel technologien = new JLabel("Technologien:");
      technologien.setPreferredSize(labelDimension);
      info6.add(technologien);

      JTextField technologienAWC = new JTextField();
      technologienAWC.setEditable(false);
      technologienAWC.setPreferredSize(textFieldDimension);
      info6.add(technologienAWC);

      JButton technologienUebernehmen = new JButton(">");
      technologienUebernehmen.setPreferredSize(buttonDimension);
      technologienUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info6.add(technologienUebernehmen);

      JTextField technologienAdiuvo = new JTextField();
      technologienAdiuvo.setPreferredSize(textFieldDimension);
      technologienUebernehmen.setAction(new UebernehmenButton(technologienAWC, technologienAdiuvo));
      info6.add(technologienAdiuvo);      
      tab.add(info6);

      // JPanel Note
      JPanel info9 = new JPanel(new FlowLayout());
      JLabel note = new JLabel("Note:");
      note.setPreferredSize(labelDimension);
      info9.add(note);

      JTextField noteAWC = new JTextField();
      noteAWC.setEditable(false);
      noteAWC.setPreferredSize(textFieldDimension);
      info9.add(noteAWC);

      JButton noteUebernehmen = new JButton(">");
      noteUebernehmen.setPreferredSize(buttonDimension);
      noteUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info9.add(noteUebernehmen);

      JTextField noteAdiuvo = new JTextField();
      noteAdiuvo.setPreferredSize(textFieldDimension);
      noteUebernehmen.setAction(new UebernehmenButton(noteAWC, noteAdiuvo));
      info9.add(noteAdiuvo);      
      tab.add(info9);
      

      // JPanel Beginn
      JPanel info7 = new JPanel(new FlowLayout());
      JLabel beginn = new JLabel("Beginn:");
      beginn.setPreferredSize(labelDimension);
      info7.add(beginn);

      JTextField beginnAWC = new JTextField();
      beginnAWC.setEditable(false);
      beginnAWC.setPreferredSize(textFieldDimension);
      info7.add(beginnAWC);

      JButton beginnUebernehmen = new JButton(">");
      beginnUebernehmen.setPreferredSize(buttonDimension);
      beginnUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info7.add(beginnUebernehmen);

      JTextField beginnAdiuvo = new JTextField();
      beginnAdiuvo.setPreferredSize(textFieldDimension);
      beginnUebernehmen.setAction(new UebernehmenButton(beginnAWC, beginnAdiuvo));
      info7.add(beginnAdiuvo);      
      tab.add(info7);

      // JPanel Ende
      JPanel info8 = new JPanel(new FlowLayout());
      JLabel ende = new JLabel("Ende:");
      ende.setPreferredSize(labelDimension);
      info8.add(ende);

      JPanel endeAWC = new JPanel();
      endeAWC.setPreferredSize(textFieldDimension);
      info8.add(endeAWC);

      JPanel endeUebernehmen = new JPanel();
      endeUebernehmen.setPreferredSize(buttonDimension);
      info8.add(endeUebernehmen);

      JTextField endeAdiuvo = new JTextField();
      endeAdiuvo.setPreferredSize(textFieldDimension);
      info8.add(endeAdiuvo);      
      tab.add(info8);

      // JPanel Kolloquiumsnote
      JPanel info10 = new JPanel(new FlowLayout());
      JLabel kolloquiumsnote = new JLabel("Kolloquiumsnote:");
      kolloquiumsnote.setPreferredSize(labelDimension);
      info10.add(kolloquiumsnote);

      JPanel kolloquiumsnoteAWC = new JPanel();
      kolloquiumsnoteAWC.setPreferredSize(textFieldDimension);
      info10.add(kolloquiumsnoteAWC);

      JPanel kolloquiumnoteUebernehmen = new JPanel();
      kolloquiumnoteUebernehmen.setPreferredSize(buttonDimension);
      info10.add(kolloquiumnoteUebernehmen);

      JTextField kolloquiumAdiuvo = new JTextField();
      kolloquiumAdiuvo.setPreferredSize(textFieldDimension);
      info10.add(kolloquiumAdiuvo);      
      tab.add(info10);

      // JPanel Kommentar
      JPanel info11 = new JPanel(new FlowLayout());
      JLabel kommentar = new JLabel("Kommentar:");
      kommentar.setPreferredSize(labelDimension);
      info11.add(kommentar);

      JPanel kommentarAWC = new JPanel();
      kommentarAWC.setPreferredSize(textFieldDimension);
      info11.add(kommentarAWC);

      JPanel button11 = new JPanel();
      button11.setPreferredSize(buttonDimension);
      info11.add(button11);

      JTextField projektKommentarAdiuvo = new JTextField();
      projektKommentarAdiuvo.setPreferredSize(textFieldDimension);
      info11.add(projektKommentarAdiuvo);      
      tab.add(info11);
      
   // JPanel Kommentar intern
      JPanel info12 = new JPanel(new FlowLayout());
      JLabel kommentarIntern = new JLabel("Kommentar intern:");
      kommentarIntern.setPreferredSize(labelDimension);
      info12.add(kommentarIntern);

      JPanel kommentarInternAWC = new JPanel();
      kommentarInternAWC.setPreferredSize(textFieldDimension);
      info12.add(kommentarInternAWC);

      JPanel button12 = new JPanel();
      button12.setPreferredSize(buttonDimension);
      info12.add(button12);

      JTextField projektKommentarInternAdiuvo = new JTextField();
      projektKommentarInternAdiuvo.setPreferredSize(textFieldDimension);
      info12.add(projektKommentarInternAdiuvo);      
      tab.add(info12);
      
   // JPanel Skizze      
      JPanel info2 = new JPanel(new FlowLayout());
      JLabel skizze = new JLabel("Projektskizze:");
      skizze.setPreferredSize(labelDimension);
      info2.add(skizze);

      JTextArea projektskizzeAWC = new JTextArea(10,1);
      projektskizzeAWC.setEditable(false);
      projektskizzeAWC.setLineWrap(true);
      projektskizzeAWC.setWrapStyleWord(true);     
      JScrollPane skizzeAWCScrollPane = new JScrollPane(projektskizzeAWC);
      skizzeAWCScrollPane.setPreferredSize(textFieldDimension);
      info2.add(skizzeAWCScrollPane);

      JButton skizzeUebernehmen = new JButton(">");
      skizzeUebernehmen.setPreferredSize(buttonDimension);
      skizzeUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info2.add(skizzeUebernehmen);

      JTextArea projektskizzeAdiuvo = new JTextArea();
      projektskizzeAdiuvo.setLineWrap(true);
      projektskizzeAdiuvo.setWrapStyleWord(true);
      JScrollPane skizzeFirebirdScrollPane = new JScrollPane(projektskizzeAdiuvo);
      projektskizzeAdiuvo.addFocusListener(new TextAreaFocusListener(skizzeAWCScrollPane, skizzeFirebirdScrollPane, textFieldDimension, textAreaDimension, info2));

      skizzeFirebirdScrollPane.setPreferredSize(textFieldDimension);
      skizzeUebernehmen.setAction(new TextAreaUebernehmenButton(projektskizzeAWC, projektskizzeAdiuvo));
      info2.add(skizzeFirebirdScrollPane);    
      tab.add(info2);

      // JPanel Hintergrund
      JPanel info3 = new JPanel(new FlowLayout());
      JLabel hintergrund = new JLabel("Projekthintergrund:");
      hintergrund.setPreferredSize(labelDimension);
      info3.add(hintergrund);

      JTextArea projekthintergrundAWC = new JTextArea(10,1);
      projekthintergrundAWC.setLineWrap(true);
      projekthintergrundAWC.setWrapStyleWord(true);     
      JScrollPane hintergrundAWCScrollPane = new JScrollPane(projekthintergrundAWC);
      projekthintergrundAWC.setEditable(false);
      hintergrundAWCScrollPane.setPreferredSize(textFieldDimension);
      info3.add(hintergrundAWCScrollPane);

      JButton hintergrundUebernehmen = new JButton(">");
      hintergrundUebernehmen.setPreferredSize(buttonDimension);
      hintergrundUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info3.add(hintergrundUebernehmen);

      JTextArea projekthintergrundAdiuvo = new JTextArea();
      projekthintergrundAdiuvo.setLineWrap(true);
      projekthintergrundAdiuvo.setWrapStyleWord(true);
      JScrollPane hintergrundFirebirdScrollPane = new JScrollPane(projekthintergrundAdiuvo);
      projekthintergrundAdiuvo.addFocusListener(new TextAreaFocusListener(hintergrundAWCScrollPane, hintergrundFirebirdScrollPane, textFieldDimension, textAreaDimension, info3));

      hintergrundFirebirdScrollPane.setPreferredSize(textFieldDimension);
      hintergrundUebernehmen.setAction(new TextAreaUebernehmenButton(projekthintergrundAWC, projekthintergrundAdiuvo));
      info3.add(hintergrundFirebirdScrollPane);    
      tab.add(info3);

      // JPanel Beschreibung
      JPanel info4 = new JPanel(new FlowLayout());
      JLabel beschreibung = new JLabel("Projektbeschreibung:");
      beschreibung.setPreferredSize(labelDimension);
      info4.add(beschreibung);

      JTextArea projektbeschreibungAWC = new JTextArea(10,1);
      projektbeschreibungAWC.setLineWrap(true);
      projektbeschreibungAWC.setWrapStyleWord(true);      
      JScrollPane beschreibungAWCScrollPane = new JScrollPane(projektbeschreibungAWC);
      beschreibungAWCScrollPane.setPreferredSize(textFieldDimension);
      projektbeschreibungAWC.setEditable(false);
      info4.add(beschreibungAWCScrollPane);

      JButton beschreibungUebernehmen = new JButton(">");
      beschreibungUebernehmen.setPreferredSize(buttonDimension);
      beschreibungUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info4.add(beschreibungUebernehmen);

      JTextArea projektbeschreibungAdiuvo = new JTextArea();      
      projektbeschreibungAdiuvo.setLineWrap(true);
      projektbeschreibungAdiuvo.setWrapStyleWord(true);
      JScrollPane beschreibungFirebirdScrollPane = new JScrollPane(projektbeschreibungAdiuvo);
      beschreibungFirebirdScrollPane.setPreferredSize(textFieldDimension);
      beschreibungUebernehmen.setAction(new TextAreaUebernehmenButton(projektbeschreibungAWC, projektbeschreibungAdiuvo));
      projektbeschreibungAdiuvo.addFocusListener(new TextAreaFocusListener(beschreibungAWCScrollPane, beschreibungFirebirdScrollPane, textFieldDimension, textAreaDimension, info4));
      info4.add(beschreibungFirebirdScrollPane);      
      tab.add(info4);
      
      projekttitelAWC.setText(projekt.getProjekttitel());
      technologienAWC.setText(projekt.getTechnologien());
      noteAWC.setText(projekt.getNote());
      beginnAWC.setText(projekt.getBeginn());
      projektskizzeAWC.setText(projekt.getSkizze());
      projektbeschreibungAWC.setText(projekt.getBeschreibung());
      projekthintergrundAWC.setText(projekt.getHintergrund());
      
      alleAWCFelder.add(projekttitelAWC);
      alleAWCFelder.add(technologienAWC);
      alleAWCFelder.add(noteAWC);
      alleAWCFelder.add(beginnAWC);
      alleAWCTextAreas.add(projektskizzeAWC);
      alleAWCTextAreas.add(projektbeschreibungAWC);
      alleAWCTextAreas.add(projekthintergrundAWC);
      
      projektAdiuvoAWCTextFields.add(projekttitelAdiuvo);
      projektAdiuvoAWCTextFields.add(technologienAdiuvo);
      projektAdiuvoAWCTextFields.add(noteAdiuvo);
      projektAdiuvoAWCTextFields.add(beginnAdiuvo);
      projektAdiuvoTextAreas.add(projektskizzeAdiuvo);
      projektAdiuvoTextAreas.add(projektbeschreibungAdiuvo);
      projektAdiuvoTextAreas.add(projekthintergrundAdiuvo);
      
      for(JTextField field : projektAdiuvoAWCTextFields)
      {
         projektAdiuvoTextFields.add(field);
      }
      projektAdiuvoTextFields.add(projektartAdiuvo);
      projektAdiuvoTextFields.add(endeAdiuvo);
      projektAdiuvoTextFields.add(kolloquiumAdiuvo);
      projektAdiuvoTextFields.add(projektKommentarAdiuvo);
      projektAdiuvoTextFields.add(projektKommentarInternAdiuvo);
      
      alleLeeren.setAction(new AlleFelderLeeren(projektAdiuvoTextFields, projektAdiuvoTextAreas));
      alleUebernehmen.setAction(new AlleFelderUebernehmen(alleAWCFelder, projektAdiuvoAWCTextFields, alleAWCTextAreas, projektAdiuvoTextAreas));

      return scrollPanetab;
}

   /**
    * 
    * Initialisiert den Tab mit den Ansprechpartnerdaten.
    *
    * @return
    */
   private JScrollPane initAnsprechpartnerTab(Ansprechpartner ansprechpartner)
   {
      Box tab = Box.createVerticalBox();
      JScrollPane scrollPanetab = new JScrollPane(tab);
      Vector<JTextField> alleAWCFelder = new Vector<JTextField>();
      Vector<JTextField> alleAdiuvoFelder = new Vector<JTextField>();
   
      // JPanel Überschriften
      JPanel ueberschriften = new JPanel(new FlowLayout());
      JLabel empty = new JLabel();
      empty.setPreferredSize(labelDimension);
      ueberschriften.add(empty);

      JLabel awc = new JLabel("AWC");
      awc.setHorizontalAlignment(SwingConstants.CENTER);
      awc.setPreferredSize(textFieldDimension);
      ueberschriften.add(awc);

      JLabel empty2 = new JLabel();
      empty2.setPreferredSize(buttonDimension);
      ueberschriften.add(empty2);

      JLabel adiuvo = new JLabel("Adiuvo");
      adiuvo.setHorizontalAlignment(SwingConstants.CENTER);
      adiuvo.setPreferredSize(textFieldDimension);
      ueberschriften.add(adiuvo);

      tab.add(ueberschriften);      


      // JPanel Auswahl
      AnsprechpartnerTableModel ansprechpartnerTableModel = new AnsprechpartnerTableModel(adiuvoAnsprechpartnerVector);
      JTable ansprechpartnerAuswahlTable = new JTable(ansprechpartnerTableModel);
      ansprechpartnerAuswahlTable.setEnabled(true);
      
      if(adiuvoAnsprechpartnerVector.size() > 1)
      {
         JPanel ansprechpartnerAuswahl = new JPanel(new FlowLayout());
         JLabel empty3 = new JLabel();
         empty3.setPreferredSize(new Dimension(labelDimension.width + textFieldDimension.width + buttonDimension.width, 10));
         ansprechpartnerAuswahl.add(empty3);

         JScrollPane ansprechpartnerTablePane = new JScrollPane(ansprechpartnerAuswahlTable);
         packColumn(ansprechpartnerAuswahlTable, ansprechpartnerTableModel);
         ansprechpartnerTablePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
         ansprechpartnerTablePane.setPreferredSize(new Dimension(textFieldDimension.width, 80));
         ansprechpartnerAuswahl.add(ansprechpartnerTablePane);

         tab.add(ansprechpartnerAuswahl);
      }
      // Buttons
      JPanel buttonPanel = new JPanel(new FlowLayout());
      JLabel emptyButton = new JLabel();
      emptyButton.setPreferredSize(new Dimension(labelDimension.width + textFieldDimension.width + 5, 30));
      buttonPanel.add(emptyButton);

      JButton alleUebernehmen = new JButton("Alle");
      alleUebernehmen.setPreferredSize(buttonDimension);
      buttonPanel.add(alleUebernehmen);      

      JPanel alleFelderLeerenPanel = new JPanel();
      alleFelderLeerenPanel.setLayout(new BoxLayout(alleFelderLeerenPanel, BoxLayout.Y_AXIS));
      alleFelderLeerenPanel.setPreferredSize(new Dimension(textFieldDimension.getSize().width, 28));
      JButton alleLeeren = new JButton("Alle Felder leeren");
      alleLeeren.setAlignmentX(Component.CENTER_ALIGNMENT);
      alleFelderLeerenPanel.add(alleLeeren);
      buttonPanel.add(alleFelderLeerenPanel);
      tab.add(buttonPanel);

      // JPanel Anrede
      JPanel info1 = new JPanel(new FlowLayout());
      JLabel anrede = new JLabel("Anrede:");
      anrede.setPreferredSize(labelDimension);
      info1.add(anrede);

      JTextField anredeAWC = new JTextField();
      anredeAWC.setEditable(false);
      anredeAWC.setPreferredSize(textFieldDimension);
      info1.add(anredeAWC);

      JButton anredeUebernehmen = new JButton(">");
      anredeUebernehmen.setPreferredSize(buttonDimension);
      anredeUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info1.add(anredeUebernehmen);

      JTextField anredeFirebird = new JTextField();
      anredeFirebird.setPreferredSize(textFieldDimension);
      anredeUebernehmen.setAction(new UebernehmenButton(anredeAWC, anredeFirebird));
      info1.add(anredeFirebird);
      tab.add(info1);

      // JPanel Titel      
      JPanel info2 = new JPanel(new FlowLayout());
      JLabel titel = new JLabel("Titel:");
      titel.setPreferredSize(labelDimension);
      info2.add(titel);

      JTextField titelAWC = new JTextField();
      titelAWC.setEditable(false);
      titelAWC.setPreferredSize(textFieldDimension);
      info2.add(titelAWC);

      JButton titelUebernehmen = new JButton(">");
      titelUebernehmen.setPreferredSize(buttonDimension);
      titelUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info2.add(titelUebernehmen);

      JTextField titelFirebird = new JTextField();
      titelFirebird.setPreferredSize(textFieldDimension);
      titelUebernehmen.setAction(new UebernehmenButton(titelAWC, titelFirebird));
      info2.add(titelFirebird);    
      tab.add(info2);

      // JPanel vorname
      JPanel info3 = new JPanel(new FlowLayout());
      JLabel vorname = new JLabel("Vorname:");
      vorname.setPreferredSize(labelDimension);
      info3.add(vorname);

      JTextField vornameAWC = new JTextField();
      vornameAWC.setEditable(false);
      vornameAWC.setPreferredSize(textFieldDimension);
      info3.add(vornameAWC);

      JButton vornameUebernehmen = new JButton(">");
      vornameUebernehmen.setPreferredSize(buttonDimension);
      vornameUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info3.add(vornameUebernehmen);

      JTextField vornameFirebird = new JTextField();
      vornameUebernehmen.setAction(new UebernehmenButton(vornameAWC, vornameFirebird));
      vornameFirebird.setPreferredSize(textFieldDimension);
      info3.add(vornameFirebird);    
      tab.add(info3);

      // JPanel Name
      JPanel info4 = new JPanel(new FlowLayout());
      JLabel name = new JLabel("Name:");
      name.setPreferredSize(labelDimension);
      info4.add(name);

      JTextField nameAWC = new JTextField();
      nameAWC.setEditable(false);
      nameAWC.setPreferredSize(textFieldDimension);
      info4.add(nameAWC);

      JButton nameUebernehmen = new JButton(">");
      nameUebernehmen.setPreferredSize(buttonDimension);
      nameUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info4.add(nameUebernehmen);

      JTextField nameFirebird = new JTextField();
      nameFirebird.setPreferredSize(textFieldDimension);
      nameUebernehmen.setAction(new UebernehmenButton(nameAWC, nameFirebird));
      info4.add(nameFirebird);      
      tab.add(info4);
      
   // JPanel Position
      JPanel info12 = new JPanel(new FlowLayout());
      JLabel position = new JLabel("Position:");
      position.setPreferredSize(labelDimension);
      info12.add(position);

      JTextField positionAWC = new JTextField();
      positionAWC.setEditable(false);
      positionAWC.setPreferredSize(textFieldDimension);
      info12.add(positionAWC);

      JButton positionUebernehmen = new JButton(">");
      positionUebernehmen.setPreferredSize(buttonDimension);
      positionUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info12.add(positionUebernehmen);

      JTextField positionFirebird = new JTextField();
      positionFirebird.setPreferredSize(textFieldDimension);
      positionUebernehmen.setAction(new UebernehmenButton(positionAWC, positionFirebird));
      info12.add(positionFirebird);      
      tab.add(info12);

      // JPanel Abteilung
      JPanel info13 = new JPanel(new FlowLayout());
      JLabel abteilung = new JLabel("Abteilung:");
      abteilung.setPreferredSize(labelDimension);
      info13.add(abteilung);

      JTextField abteilungAWC = new JTextField();
      abteilungAWC.setEditable(false);
      abteilungAWC.setPreferredSize(textFieldDimension);
      info13.add(abteilungAWC);
      
      JButton abteilungUebernehmen = new JButton(">");
      abteilungUebernehmen.setPreferredSize(buttonDimension);
      abteilungUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info13.add(abteilungUebernehmen);

      JTextField abteilungFirebird = new JTextField();
      abteilungFirebird.setPreferredSize(textFieldDimension);
      abteilungUebernehmen.setAction(new UebernehmenButton(abteilungAWC, abteilungFirebird));
      info13.add(abteilungFirebird);      
      tab.add(info13);

      // JPanel telefon1      
      JPanel info5 = new JPanel(new FlowLayout());
      JLabel telefon1 = new JLabel("Telefon:");
      telefon1.setPreferredSize(labelDimension);
      info5.add(telefon1);

      JTextField telefon1AWC = new JTextField();
      telefon1AWC.setEditable(false);   
      telefon1AWC.setPreferredSize(textFieldDimension);
      info5.add(telefon1AWC);

      JButton telefon1Uebernehmen = new JButton(">");
      telefon1Uebernehmen.setPreferredSize(buttonDimension);
      telefon1Uebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info5.add(telefon1Uebernehmen);

      JTextField telefon1Firebird = new JTextField();
      telefon1Firebird.setPreferredSize(textFieldDimension);
      telefon1Uebernehmen.setAction(new UebernehmenButton(telefon1AWC, telefon1Firebird));
      info5.add(telefon1Firebird);      
      tab.add(info5);

      // JPanel Telefon2
      JPanel info6 = new JPanel(new FlowLayout());
      JLabel telefon2 = new JLabel("Telefon2:");
      telefon2.setPreferredSize(labelDimension);
      info6.add(telefon2);

      JTextField telefon2AWC = new JTextField();
      telefon2AWC.setEditable(false);
      telefon2AWC.setPreferredSize(textFieldDimension);
      info6.add(telefon2AWC);

      JButton telefon2Uebernehmen = new JButton(">");
      telefon2Uebernehmen.setPreferredSize(buttonDimension);
      telefon2Uebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info6.add(telefon2Uebernehmen);

      JTextField telefon2Firebird = new JTextField();
      telefon2Firebird.setPreferredSize(textFieldDimension);
      telefon2Uebernehmen.setAction(new UebernehmenButton(telefon2AWC, telefon2Firebird));
      info6.add(telefon2Firebird);      
      tab.add(info6);

      // JPanel Telefon3 / Mobil
      JPanel info7 = new JPanel(new FlowLayout());
      JLabel telefon3 = new JLabel("Mobil:");
      telefon3.setPreferredSize(labelDimension);
      info7.add(telefon3);

      JTextField telefon3AWC = new JTextField();
      telefon3AWC.setEditable(false);
      telefon3AWC.setPreferredSize(textFieldDimension);
      info7.add(telefon3AWC);

      JButton telefon3Uebernehmen = new JButton(">");
      telefon3Uebernehmen.setPreferredSize(buttonDimension);
      telefon3Uebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info7.add(telefon3Uebernehmen);

      JTextField telefon3Firebird = new JTextField();
      telefon3Firebird.setPreferredSize(textFieldDimension);
      telefon3Uebernehmen.setAction(new UebernehmenButton(telefon3AWC, telefon3Firebird));
      info7.add(telefon3Firebird);      
      tab.add(info7);

      // JPanel Email      
      JPanel info8 = new JPanel(new FlowLayout());
      JLabel email = new JLabel("Email:");
      email.setPreferredSize(labelDimension);
      info8.add(email);

      JTextField emailAWC = new JTextField();
      emailAWC.setEditable(false);
      emailAWC.setPreferredSize(textFieldDimension);
      info8.add(emailAWC);

      JButton emailUebernehmen = new JButton(">");
      emailUebernehmen.setPreferredSize(buttonDimension);
      emailUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info8.add(emailUebernehmen);

      JTextField emailFirebird = new JTextField();
      emailFirebird.setPreferredSize(textFieldDimension);
      emailUebernehmen.setAction(new UebernehmenButton(emailAWC, emailFirebird));
      info8.add(emailFirebird);      
      tab.add(info8);

      // JPanel Fax
      JPanel info9 = new JPanel(new FlowLayout());
      JLabel fax = new JLabel("Fax:");
      fax.setPreferredSize(labelDimension);
      info9.add(fax);

      JTextField faxAWC = new JTextField();
      faxAWC.setEditable(false);
      faxAWC.setPreferredSize(textFieldDimension);
      info9.add(faxAWC);

      JButton faxUebernehmen = new JButton(">");
      faxUebernehmen.setPreferredSize(buttonDimension);
      faxUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info9.add(faxUebernehmen);

      JTextField faxFirebird = new JTextField();
      faxFirebird.setPreferredSize(textFieldDimension);
      faxUebernehmen.setAction(new UebernehmenButton(faxAWC, faxFirebird));
      info9.add(faxFirebird);      
      tab.add(info9);


      // JPanel Internetseite
      JPanel info10 = new JPanel(new FlowLayout());
      JLabel internetseite = new JLabel("Internetseite:");
      internetseite.setPreferredSize(labelDimension);
      info10.add(internetseite);

      JPanel internetseiteAWC = new JPanel();
      internetseiteAWC.setPreferredSize(textFieldDimension);
      info10.add(internetseiteAWC);

      JPanel internetseiteUebernehmen = new JPanel();
      internetseiteUebernehmen.setPreferredSize(buttonDimension);
      info10.add(internetseiteUebernehmen);

      JTextField internetseiteFirebird = new JTextField();
      internetseiteFirebird.setPreferredSize(textFieldDimension);
      info10.add(internetseiteFirebird);      
      tab.add(info10);

      // JPanel Kommentar      
      JPanel info11 = new JPanel(new FlowLayout());
      JLabel kommentar = new JLabel("Kommentar:");
      kommentar.setPreferredSize(labelDimension);
      info11.add(kommentar);

      JTextField kommentarAWC = new JTextField();
      kommentarAWC.setEditable(false);
      kommentarAWC.setPreferredSize(textFieldDimension);
      info11.add(kommentarAWC);

      JButton kommentarUebernehmen = new JButton(">");
      kommentarUebernehmen.setPreferredSize(buttonDimension);
      kommentarUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info11.add(kommentarUebernehmen);

      JTextField kommentarFirebird = new JTextField();
      kommentarFirebird.setPreferredSize(textFieldDimension);
      kommentarUebernehmen.setAction(new UebernehmenButton(kommentarAWC, kommentarFirebird));
      info11.add(kommentarFirebird);      
      tab.add(info11);
      
   // JPanel Kommentar intern  
      JPanel info14 = new JPanel(new FlowLayout());
      JLabel kommentarIntern = new JLabel("Kommentar intern:");
      kommentarIntern.setPreferredSize(labelDimension);
      info14.add(kommentarIntern);

      JPanel kommentarInternAWC = new JPanel();
      kommentarInternAWC.setPreferredSize(textFieldDimension);
      info14.add(kommentarInternAWC);

      JPanel kommentarInternUebernehmen = new JPanel();
      kommentarInternUebernehmen.setPreferredSize(buttonDimension);
      info14.add(kommentarInternUebernehmen);

      JTextField kommentarInternFirebird = new JTextField();
      kommentarInternFirebird.setPreferredSize(textFieldDimension);
      info14.add(kommentarInternFirebird);
      
      anredeAWC.setText(ansprechpartner.getAnrede());
      titelAWC.setText(ansprechpartner.getTitel());
      vornameAWC.setText(ansprechpartner.getVorname());
      nameAWC.setText(ansprechpartner.getName());
      positionAWC.setText(ansprechpartner.getPosition());
      abteilungAWC.setText(ansprechpartner.getAbteilung());
      telefon1AWC.setText(ansprechpartner.getTelefon1());
      telefon2AWC.setText(ansprechpartner.getTelefon2());
      telefon3AWC.setText(ansprechpartner.getMobil());
      emailAWC.setText(ansprechpartner.getEmail());
      faxAWC.setText(ansprechpartner.getFax());
      kommentarAWC.setText(ansprechpartner.getKommentar());
      
      alleAWCFelder.add(anredeAWC);
      alleAWCFelder.add(titelAWC);
      alleAWCFelder.add(vornameAWC);
      alleAWCFelder.add(nameAWC);
      alleAWCFelder.add(positionAWC);
      alleAWCFelder.add(abteilungAWC);
      alleAWCFelder.add(telefon1AWC);
      alleAWCFelder.add(telefon2AWC);
      alleAWCFelder.add(telefon3AWC);
      alleAWCFelder.add(emailAWC);
      alleAWCFelder.add(faxAWC);
      alleAWCFelder.add(kommentarAWC);
      
      alleAdiuvoFelder.add(anredeFirebird);
      alleAdiuvoFelder.add(titelFirebird);
      alleAdiuvoFelder.add(vornameFirebird);
      alleAdiuvoFelder.add(nameFirebird);
      alleAdiuvoFelder.add(positionFirebird);
      alleAdiuvoFelder.add(abteilungFirebird);
      alleAdiuvoFelder.add(telefon1Firebird);
      alleAdiuvoFelder.add(telefon2Firebird);
      alleAdiuvoFelder.add(telefon3Firebird);
      alleAdiuvoFelder.add(emailFirebird);
      alleAdiuvoFelder.add(faxFirebird);
      alleAdiuvoFelder.add(kommentarFirebird);
      
      for(JTextField field : alleAdiuvoFelder)
      {
         ansprechpartnerAdiuvoTextFields.add(field);
      }
      ansprechpartnerAdiuvoTextFields.add(internetseiteFirebird);
      ansprechpartnerAdiuvoTextFields.add(kommentarInternFirebird);
      
      ansprechpartnerAuswahlTable.addMouseListener(new AnsprechpartnerTableMouseListener(ansprechpartnerAuswahlTable, adiuvoAnsprechpartnerVector, 
            ansprechpartnerAdiuvoTextFields, this));
      if(adiuvoAnsprechpartnerVector.size() == 1 && adiuvoAnsprechpartnerVector.get(0) != null)
      {
         anredeFirebird.setText("");
         titelFirebird.setText(adiuvoAnsprechpartnerVector.get(0).getTitel());
         vornameFirebird.setText(adiuvoAnsprechpartnerVector.get(0).getVorname());
         nameFirebird.setText(adiuvoAnsprechpartnerVector.get(0).getName());
         positionFirebird.setText(adiuvoAnsprechpartnerVector.get(0).getPosition());
         abteilungFirebird.setText(adiuvoAnsprechpartnerVector.get(0).getAbteilung());
         telefon1Firebird.setText(adiuvoAnsprechpartnerVector.get(0).getTelefon1());
         telefon2Firebird.setText(adiuvoAnsprechpartnerVector.get(0).getTelefon2());
         telefon3Firebird.setText(adiuvoAnsprechpartnerVector.get(0).getMobil());
         emailFirebird.setText(adiuvoAnsprechpartnerVector.get(0).getEmail());
         faxFirebird.setText(adiuvoAnsprechpartnerVector.get(0).getFax());
         kommentarFirebird.setText(adiuvoAnsprechpartnerVector.get(0).getKommentar());
         adiuvoAnsprechpartner = adiuvoAnsprechpartnerVector.get(0);
      }
      alleLeeren.setAction(new AlleFelderLeeren(ansprechpartnerAdiuvoTextFields, null));
      alleUebernehmen.setAction(new AlleFelderUebernehmen(alleAWCFelder, alleAdiuvoFelder, null, null));
      
      tab.add(info14);

      return scrollPanetab;
   }

   /**
    * 
    * Initialisert den Tab mit den Unternehmensdaten.
    *
    * @return   JScrollPane mit Unternehmensdaten.
    */
   private JScrollPane initUnternehmenTab()
   {
      Box tab = Box.createVerticalBox();
      JScrollPane scrollPanetab = new JScrollPane(tab);
      

      // JPanel Überschriften
      JPanel ueberschriften = new JPanel(new FlowLayout());
      JLabel empty = new JLabel();
      empty.setPreferredSize(labelDimension);
      ueberschriften.add(empty);

      JLabel awc = new JLabel("AWC");
      awc.setHorizontalAlignment(SwingConstants.CENTER);
      awc.setPreferredSize(textFieldDimension);
      ueberschriften.add(awc);

      JLabel empty2 = new JLabel();
      empty2.setPreferredSize(buttonDimension);
      ueberschriften.add(empty2);

      JLabel adiuvo = new JLabel("Adiuvo");
      adiuvo.setHorizontalAlignment(SwingConstants.CENTER);
      adiuvo.setPreferredSize(textFieldDimension);
      ueberschriften.add(adiuvo);

      tab.add(ueberschriften);

      // JPanel Auswahl
      UnternehmenTableModel unternehmenTableModel = new UnternehmenTableModel(adiuvoUnternehmenVector);
      JTable unternehmenAuswahlTable = new JTable(unternehmenTableModel);
      unternehmenAuswahlTable.setEnabled(true);
      
      if(adiuvoUnternehmenVector.size() > 1)
      {
         JPanel unternehmenAuswahl = new JPanel(new FlowLayout());
         JLabel empty3 = new JLabel();
         empty3.setPreferredSize(new Dimension(labelDimension.width + textFieldDimension.width + buttonDimension.width, 10));
         unternehmenAuswahl.add(empty3);

         JScrollPane ansprechpartnerTablePane = new JScrollPane(unternehmenAuswahlTable);
         packColumn(unternehmenAuswahlTable, unternehmenTableModel);
         ansprechpartnerTablePane.setPreferredSize(new Dimension(textFieldDimension.width, 80));
         unternehmenAuswahl.add(ansprechpartnerTablePane);

         tab.add(unternehmenAuswahl);
      }
      // JPanel Name
      JPanel info1 = new JPanel(new FlowLayout());
      JLabel name = new JLabel("Name:");
      name.setPreferredSize(labelDimension);
      info1.add(name);

      JTextField nameAWC = new JTextField();
      nameAWC.setEditable(false);
      nameAWC.setPreferredSize(textFieldDimension);
      info1.add(nameAWC);

      JButton nameUebernehmen = new JButton(">");
      nameUebernehmen.setPreferredSize(buttonDimension);
      nameUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info1.add(nameUebernehmen);

      JTextField nameFirebird = new JTextField();
      nameFirebird.setPreferredSize(textFieldDimension);
      nameUebernehmen.setAction(new UebernehmenButton(nameAWC, nameFirebird));
      info1.add(nameFirebird);
      tab.add(info1);

      // JPanel Straßehausnr      
      JPanel info2 = new JPanel(new FlowLayout());
      JLabel strasseHausnr = new JLabel("Straße/Hausnummer:");
      strasseHausnr.setPreferredSize(labelDimension);
      info2.add(strasseHausnr);

      JTextField strasseHausnrAWC = new JTextField();
      strasseHausnrAWC.setEditable(false);
      strasseHausnrAWC.setPreferredSize(textFieldDimension);
      info2.add(strasseHausnrAWC);

      JButton strasseHausnrUebernehmen = new JButton(">");
      strasseHausnrUebernehmen.setPreferredSize(buttonDimension);
      strasseHausnrUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info2.add(strasseHausnrUebernehmen);

      JTextField strasseHausnrFirebird = new JTextField();
      strasseHausnrFirebird.setPreferredSize(textFieldDimension);
      strasseHausnrUebernehmen.setAction(new UebernehmenButton(strasseHausnrAWC, strasseHausnrFirebird));
      info2.add(strasseHausnrFirebird);    
      tab.add(info2);

      // JPanel PLZ
      JPanel info3 = new JPanel(new FlowLayout());
      JLabel plz = new JLabel("Postleitzahl:");
      plz.setPreferredSize(labelDimension);
      info3.add(plz);

      JTextField plzAWC = new JTextField();
      plzAWC.setEditable(false);
      plzAWC.setPreferredSize(textFieldDimension);
      info3.add(plzAWC);

      JButton plzUebernehmen = new JButton(">");
      plzUebernehmen.setPreferredSize(buttonDimension);
      plzUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info3.add(plzUebernehmen);

      JTextField plzFirebird = new JTextField();
      plzFirebird.setPreferredSize(textFieldDimension);
      plzUebernehmen.setAction(new UebernehmenButton(plzAWC, plzFirebird));
      info3.add(plzFirebird);    
      tab.add(info3);

      // JPanel Ort
      JPanel info4 = new JPanel(new FlowLayout());
      JLabel ort = new JLabel("Ort:");
      ort.setPreferredSize(labelDimension);
      info4.add(ort);

      JTextField ortAWC = new JTextField();
      ortAWC.setEditable(false);
      ortAWC.setPreferredSize(textFieldDimension);
      info4.add(ortAWC);

      JButton ortUebernehmen = new JButton(">");
      ortUebernehmen.setPreferredSize(buttonDimension);
      ortUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info4.add(ortUebernehmen);

      JTextField ortFirebird = new JTextField();    
      ortFirebird.setPreferredSize(textFieldDimension);
      ortUebernehmen.setAction(new UebernehmenButton(ortAWC, ortFirebird));
      info4.add(ortFirebird);      
      tab.add(info4);

      // JPanel telefon
      JPanel info5 = new JPanel(new FlowLayout());
      JLabel telefon = new JLabel("Telefon:");
      telefon.setPreferredSize(labelDimension);
      info5.add(telefon);

      JTextField telefonAWC = new JTextField();
      telefonAWC.setEditable(false);      
      telefonAWC.setPreferredSize(textFieldDimension);
      info5.add(telefonAWC);

      JButton telefonUebernehmen = new JButton(">");
      telefonUebernehmen.setPreferredSize(buttonDimension);
      telefonUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info5.add(telefonUebernehmen);

      JTextField telefonFirebird = new JTextField();
      telefonFirebird.setPreferredSize(textFieldDimension);
      telefonUebernehmen.setAction(new UebernehmenButton(telefonAWC, telefonFirebird));
      info5.add(telefonFirebird);      
      tab.add(info5);

      // JPanel Email
      JPanel info6 = new JPanel(new FlowLayout());
      JLabel email = new JLabel("Email:");
      email.setPreferredSize(labelDimension);
      info6.add(email);

      JTextField emailAWC = new JTextField();
      emailAWC.setEditable(false);
      emailAWC.setPreferredSize(textFieldDimension);
      info6.add(emailAWC);

      JButton emailUebernehmen = new JButton(">");
      emailUebernehmen.setPreferredSize(buttonDimension);
      emailUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info6.add(emailUebernehmen);

      JTextField emailFirebird = new JTextField();
      emailFirebird.setPreferredSize(textFieldDimension);
      emailUebernehmen.setAction(new UebernehmenButton(emailAWC, emailFirebird));
      info6.add(emailFirebird);      
      tab.add(info6);

      // JPanel Fax
      JPanel info7 = new JPanel(new FlowLayout());
      JLabel fax = new JLabel("Fax:");
      fax.setPreferredSize(labelDimension);
      info7.add(fax);

      JTextField faxAWC = new JTextField();
      faxAWC.setPreferredSize(textFieldDimension);
      faxAWC.setEditable(false);
      info7.add(faxAWC);

      JButton faxUebernehmen = new JButton(">");
      faxUebernehmen.setPreferredSize(buttonDimension);
      faxUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info7.add(faxUebernehmen);

      JTextField faxFirebird = new JTextField();
      faxFirebird.setPreferredSize(textFieldDimension);
      faxUebernehmen.setAction(new UebernehmenButton(faxAWC, faxFirebird));
      info7.add(faxFirebird);      
      tab.add(info7);

      // JPanel Internetseite      
      JPanel info8 = new JPanel(new FlowLayout());
      JLabel internetseite = new JLabel("Internetseite:");
      internetseite.setPreferredSize(labelDimension);
      info8.add(internetseite);

      JTextField internetseiteAWC = new JTextField();
      internetseiteAWC.setEditable(false);
      internetseiteAWC.setPreferredSize(textFieldDimension);
      info8.add(internetseiteAWC);

      JButton internetseiteUebernehmen = new JButton(">");
      internetseiteUebernehmen.setPreferredSize(buttonDimension);
      internetseiteUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info8.add(internetseiteUebernehmen);

      JTextField internetseiteFirebird = new JTextField();
      internetseiteFirebird.setPreferredSize(textFieldDimension);
      internetseiteUebernehmen.setAction(new UebernehmenButton(internetseiteAWC, internetseiteFirebird));
      info8.add(internetseiteFirebird);      
      tab.add(info8);

      // JPanel Branche
      JPanel info9 = new JPanel(new FlowLayout());
      JLabel branche = new JLabel("Branche:");
      branche.setPreferredSize(labelDimension);
      info9.add(branche);

      JTextField brancheAWC = new JTextField();
      brancheAWC.setEditable(false);
      brancheAWC.setPreferredSize(textFieldDimension);
      info9.add(brancheAWC);

      JButton brancheUebernehmen = new JButton(">");
      brancheUebernehmen.setPreferredSize(buttonDimension);
      brancheUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info9.add(brancheUebernehmen);

      JTextField brancheFirebird = new JTextField();
      brancheFirebird.setPreferredSize(textFieldDimension);
      brancheUebernehmen.setAction(new UebernehmenButton(brancheAWC, brancheFirebird));
      info9.add(brancheFirebird);      
      tab.add(info9);


      // JPanel Kommentar
      JPanel info10 = new JPanel(new FlowLayout());
      JLabel kommentar = new JLabel("Kommentar:");
      kommentar.setPreferredSize(labelDimension);
      info10.add(kommentar);

      JTextField kommentarAWC = new JTextField();
      kommentarAWC.setEditable(false);
      kommentarAWC.setPreferredSize(textFieldDimension);
      info10.add(kommentarAWC);

      JButton kommentarUebernehmen = new JButton(">");
      kommentarUebernehmen.setPreferredSize(buttonDimension);
      kommentarUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info10.add(kommentarUebernehmen);

      JTextField kommentarFirebird = new JTextField();
      kommentarFirebird.setPreferredSize(textFieldDimension);
      kommentarUebernehmen.setAction(new UebernehmenButton(kommentarAWC, kommentarFirebird));
      info10.add(kommentarFirebird);      
      tab.add(info10);
      
   // JPanel Kommentar intern
      JPanel info11 = new JPanel(new FlowLayout());
      JLabel kommentarIntern = new JLabel("Kommentar intern:");
      kommentarIntern.setPreferredSize(labelDimension);
      info11.add(kommentarIntern);

      JTextField kommentarInternAWC = new JTextField();
      kommentarInternAWC.setEditable(false);
      kommentarInternAWC.setPreferredSize(textFieldDimension);
      info11.add(kommentarInternAWC);

      JButton kommentarInternUebernehmen = new JButton(">");
      kommentarInternUebernehmen.setPreferredSize(buttonDimension);
      kommentarInternUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info11.add(kommentarInternUebernehmen);

      JTextField kommentarInternFirebird = new JTextField();
      kommentarInternFirebird.setPreferredSize(textFieldDimension);
      kommentarInternUebernehmen.setAction(new UebernehmenButton(kommentarInternAWC, kommentarInternFirebird));
      info11.add(kommentarInternFirebird);      
      tab.add(info11);

   // Buttons
      JPanel buttonPanel = new JPanel(new FlowLayout());
      JLabel emptyButton = new JLabel();
      emptyButton.setPreferredSize(new Dimension(labelDimension.width + textFieldDimension.width + 5, 30));
      buttonPanel.add(emptyButton);

      JButton alleUebernehmen = new JButton("Alle");
      alleUebernehmen.setPreferredSize(buttonDimension);
      buttonPanel.add(alleUebernehmen);      

      JPanel alleFelderLeerenPanel = new JPanel();
      alleFelderLeerenPanel.setLayout(new BoxLayout(alleFelderLeerenPanel, BoxLayout.Y_AXIS));
      alleFelderLeerenPanel.setPreferredSize(new Dimension(textFieldDimension.getSize().width, 28));
      JButton alleLeeren = new JButton("Alle Felder leeren");
      alleLeeren.setAlignmentX(Component.CENTER_ALIGNMENT);
      alleFelderLeerenPanel.add(alleLeeren);
      buttonPanel.add(alleFelderLeerenPanel);
      tab.add(buttonPanel);
      
      nameAWC.setText(projekt.getAnsprechpartner().getUnternehmen().getName());
      strasseHausnrAWC.setText(projekt.getAnsprechpartner().getUnternehmen().getStraßeHausnr());
      plzAWC.setText(projekt.getAnsprechpartner().getUnternehmen().getpLZ());
      ortAWC.setText(projekt.getAnsprechpartner().getUnternehmen().getOrt());
      telefonAWC.setText(projekt.getAnsprechpartner().getUnternehmen().getTelefon());
      emailAWC.setText(projekt.getAnsprechpartner().getUnternehmen().getEmail());
      faxAWC.setText(projekt.getAnsprechpartner().getUnternehmen().getFax());
      internetseiteAWC.setText(projekt.getAnsprechpartner().getUnternehmen().getInternetseite());
      brancheAWC.setText(projekt.getAnsprechpartner().getUnternehmen().getBranche());
      kommentarAWC.setText(projekt.getAnsprechpartner().getUnternehmen().getKommentar());
      kommentarInternAWC.setText(projekt.getAnsprechpartner().getUnternehmen().getKommentarIntern());
      
      Vector<JTextField> awcTextFields = new Vector<JTextField>();
      awcTextFields.add(nameAWC);
      awcTextFields.add(strasseHausnrAWC);
      awcTextFields.add(plzAWC);
      awcTextFields.add(ortAWC);
      awcTextFields.add(telefonAWC);
      awcTextFields.add(emailAWC);
      awcTextFields.add(faxAWC);
      awcTextFields.add(internetseiteAWC);
      awcTextFields.add(brancheAWC);
      awcTextFields.add(kommentarAWC);
      awcTextFields.add(kommentarInternAWC);
      
      unternehmenAdiuvoTextFields.add(nameFirebird);
      unternehmenAdiuvoTextFields.add(strasseHausnrFirebird);
      unternehmenAdiuvoTextFields.add(plzFirebird);
      unternehmenAdiuvoTextFields.add(ortFirebird);
      unternehmenAdiuvoTextFields.add(telefonFirebird);
      unternehmenAdiuvoTextFields.add(emailFirebird);
      unternehmenAdiuvoTextFields.add(faxFirebird);
      unternehmenAdiuvoTextFields.add(internetseiteFirebird);
      unternehmenAdiuvoTextFields.add(brancheFirebird);
      unternehmenAdiuvoTextFields.add(kommentarFirebird);
      unternehmenAdiuvoTextFields.add(kommentarInternFirebird);
      
      if(adiuvoUnternehmenVector.size() == 1 && adiuvoUnternehmenVector.get(0) != null)
      {
         nameFirebird.setText(adiuvoUnternehmenVector.get(0).getName());
         strasseHausnrFirebird.setText(adiuvoUnternehmenVector.get(0).getStraßeHausnr());
         plzFirebird.setText(adiuvoUnternehmenVector.get(0).getpLZ());
         ortFirebird.setText(adiuvoUnternehmenVector.get(0).getOrt());
         telefonFirebird.setText(adiuvoUnternehmenVector.get(0).getTelefon());
         emailFirebird.setText(adiuvoUnternehmenVector.get(0).getEmail());
         internetseiteFirebird.setText(adiuvoUnternehmenVector.get(0).getInternetseite());
         faxFirebird.setText(adiuvoUnternehmenVector.get(0).getFax());
         brancheFirebird.setText(adiuvoUnternehmenVector.get(0).getBranche());
         kommentarFirebird.setText(adiuvoUnternehmenVector.get(0).getKommentar());
         kommentarInternFirebird.setText(adiuvoUnternehmenVector.get(0).getKommentarIntern());
         adiuvoUnternehmen = adiuvoUnternehmenVector.get(0);
      }
      
      unternehmenAuswahlTable.addMouseListener(new UnternehmenTableMouseListener(unternehmenAuswahlTable, adiuvoUnternehmenVector, 
            unternehmenAdiuvoTextFields, this));
      alleUebernehmen.setAction(new AlleFelderUebernehmen(awcTextFields, unternehmenAdiuvoTextFields, null, null));
      alleLeeren.setAction(new AlleFelderLeeren(unternehmenAdiuvoTextFields, null));
      

      return scrollPanetab;
   }

   
   /**
    * 
    * Selektiert, wenn vorhanden, den gleichen Studenten, Ansprechpartner 
    * und das gleiche Unternehmen aus der Firebird Datenbank.
    *
    */
   private void selectDataFromFirebird()
   {
      ConnectFirebirdDatabase.getInstance().openDatabaseConnection();
      ResultSet rs = ConnectFirebirdDatabase.getInstance().query("SELECT * FROM student WHERE matrikelnummer = '" + 
            projekt.getStudent1().getMatrikelnummer() + "'");
      
      ResultSet rs2 = null;
      if(projekt.getStudent2() != null)
      {
         rs2 = ConnectFirebirdDatabase.getInstance().query("SELECT * FROM student WHERE matrikelnummer = '" + 
               projekt.getStudent2().getMatrikelnummer() + "'");
      }
      ResultSet rs3 = null;
      if(projekt.getStudent3() != null)
      {
         rs3 = ConnectFirebirdDatabase.getInstance().query("SELECT * FROM student WHERE matrikelnummer = '" + 
               projekt.getStudent3().getMatrikelnummer() + "'");
      }
      try
      {
         while(rs.next())
         {
            adiuvoStudent1 = new Student(rs.getInt("id"), rs.getString("anrede"), rs.getString("vorname"), rs.getString("nachname"), 
                  rs.getString("matrikelnummer"), rs.getString("email"), rs.getString("telefon"));
            adiuvoStudent1.setInternetseite(rs.getString("internetseite"));
            adiuvoStudent1.setKommentar(rs.getString("kommentar"));
            adiuvoStudent1.setKommentarIntern(rs.getString("kommentarintern"));
         }
         if(rs2 != null)
         {
            while(rs2.next())
            {
               adiuvoStudent2 = new Student(rs2.getInt("id"), rs2.getString("anrede"), rs2.getString("vorname"), rs2.getString("nachname"), 
                     rs2.getString("matrikelnummer"), rs2.getString("email"), rs2.getString("telefon"));
               adiuvoStudent2.setInternetseite(rs2.getString("internetseite"));
               adiuvoStudent2.setKommentar(rs2.getString("kommentar"));
               adiuvoStudent2.setKommentarIntern(rs2.getString("kommentarintern"));
            }
         }
         if(rs3 != null)
         {
            while(rs3.next())
            {
               adiuvoStudent3 = new Student(rs3.getInt("id"), rs3.getString("anrede"), rs3.getString("vorname"), rs3.getString("nachname"), 
                     rs3.getString("matrikelnummer"), rs3.getString("email"), rs3.getString("telefon")); 
               adiuvoStudent3.setInternetseite(rs3.getString("internetseite"));
               adiuvoStudent3.setKommentar(rs3.getString("kommentar"));
               adiuvoStudent3.setKommentarIntern(rs3.getString("kommentarintern"));
            }
         }
      }
      catch(SQLException ex)
      {
         ex.printStackTrace();
      }
      
      rs = ConnectFirebirdDatabase.getInstance().query("SELECT * FROM ansprechpartner WHERE nachname = '" +
            projekt.getAnsprechpartner().getName() + "' AND vorname = '" + projekt.getAnsprechpartner().getVorname() + "'");
      rs2 = ConnectFirebirdDatabase.getInstance().query("SELECT * FROM organisation WHERE name LIKE '" + 
            projekt.getAnsprechpartner().getUnternehmen().getName() + "%'");
      try
      {
         while(rs.next())
         {
            Ansprechpartner ansp = new Ansprechpartner(rs.getInt("id"), rs.getString("anrede"), rs.getString("titel"), 
            rs.getString("vorname"), rs.getString("nachname"), rs.getString("positionap"), rs.getString("abteilung"), 
            rs.getString("telefon1"), rs.getString("telefon2"), rs.getString("telefon3"), rs.getString("fax"), rs.getString("email"),
            rs.getString("kommentar"));      
            ansp.setKommentarIntern(rs.getString("kommentarintern"));
            ansp.setInternetseite(rs.getString("internetseite"));
            rs3 = ConnectFirebirdDatabase.getInstance().query("SELECT * FROM organisation WHERE id = " + rs.getInt("organisation"));
            while(rs3.next())
            {
               Unternehmen unternehmen = new Unternehmen(rs3.getInt("id"), rs3.getString("name"), rs3.getString("strasseHausnr"), rs3.getString("plz"), 
                     rs3.getString("ort"), rs3.getString("telefon"), rs3.getString("email"), rs3.getString("fax"), rs3.getString("internetseite"), 
                     rs3.getString("branche"), rs3.getString("kommentar"), rs3.getString("kommentarintern"));
               ansp.setUnternehmen(unternehmen);
               
            }
            adiuvoAnsprechpartnerVector.add(ansp);
         }
         
         while(rs2.next())
         {
            
            Unternehmen unt = new Unternehmen(rs2.getInt("id"), rs2.getString("name"), rs2.getString("strasseHausnr"), rs2.getString("plz"), 
                  rs2.getString("ort"), rs2.getString("telefon"), rs2.getString("email"), rs2.getString("fax"), rs2.getString("internetseite"), 
                  rs2.getString("branche"), rs2.getString("kommentar"), rs2.getString("kommentarintern"));
            adiuvoUnternehmenVector.add(unt);
         }
         ConnectFirebirdDatabase.getInstance().closeConnection();
      }
      catch(SQLException ex)
      {
         ex.printStackTrace();
      }
      

   }

   /**
    * 
    * Spaltenbreite der Ansprechpartner- und Unternehmensauswahltabelle
    * anpassen.
    *
    * @param table      Ansprechpartner- oder Unternehmenstabelle
    * @param model      Tabellenmodel
    */
   public void packColumn(JTable table, TableModel model) 
   {
      DefaultTableColumnModel colModel = (DefaultTableColumnModel)table.getColumnModel();
      TableColumn col;
      int totallWidth = 0;
      int margin = 2;
      for(int i = 0; i < model.getColumnCount(); i++)
      {
         col = colModel.getColumn(i);
         int width = 0;

         // Breite der Spaltenheader
         TableCellRenderer renderer = col.getHeaderRenderer();
         if (renderer == null) {
            renderer = table.getTableHeader().getDefaultRenderer();
         }
         Component comp = renderer.getTableCellRendererComponent(
               table, col.getHeaderValue(), false, false, 0, 0);
         width = comp.getPreferredSize().width;

         // Maximale Breite der Spalteninhalte
         for (int r=0; r<table.getRowCount(); r++) {
            renderer = table.getCellRenderer(r, i);
            comp = renderer.getTableCellRendererComponent(
                  table, table.getValueAt(r, i), false, false, r, i);
            width = Math.max(width, comp.getPreferredSize().width);
         }
         
         width += 2*margin;
         
         col.setPreferredWidth(width);
         totallWidth += width;
      }
      if(totallWidth > textFieldDimension.width)
      {
         table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      }
   }

   /**
    * Getter for property student1AdiuvoTextFields.
    * 
    * @return Returns the student1AdiuvoTextFields.
    */
   public Vector<JTextField> getStudent1AdiuvoTextFields()
   {
      return student1AdiuvoTextFields;
   }

   /**
    * Getter for property student2AdiuvoTextFields.
    * 
    * @return Returns the student2AdiuvoTextFields.
    */
   public Vector<JTextField> getStudent2AdiuvoTextFields()
   {
      return student2AdiuvoTextFields;
   }

   /**
    * Getter for property student3AdiuvoTextFields.
    * 
    * @return Returns the student3AdiuvoTextFields.
    */
   public Vector<JTextField> getStudent3AdiuvoTextFields()
   {
      return student3AdiuvoTextFields;
   }

   /**
    * Getter for property adiuvoAnsprechpartner.
    * 
    * @return Returns the adiuvoAnsprechpartner.
    */
   public Ansprechpartner getAdiuvoAnsprechpartner()
   {
      return adiuvoAnsprechpartner;
   }

   /**
    * Getter for property adiuvoUnternehmen.
    * 
    * @return Returns the adiuvoUnternehmen.
    */
   public Unternehmen getAdiuvoUnternehmen()
   {
      return adiuvoUnternehmen;
   }

   /**
    * Setter for property adiuvoAnsprechpartner.
    *
    * @param adiuvoAnsprechpartner The adiuvoAnsprechpartner to set.
    */
   public void setAdiuvoAnsprechpartner(Ansprechpartner adiuvoAnsprechpartner)
   {
      this.adiuvoAnsprechpartner = adiuvoAnsprechpartner;
   }

   /**
    * Setter for property adiuvoUnternehmen.
    *
    * @param adiuvoUnternehmen The adiuvoUnternehmen to set.
    */
   public void setAdiuvoUnternehmen(Unternehmen adiuvoUnternehmen)
   {
      this.adiuvoUnternehmen = adiuvoUnternehmen;
   }

   /**
    * Getter for property projekt.
    * 
    * @return Returns the projekt.
    */
   public Projektantrag getProjekt()
   {
      return projekt;
   }   
   
}
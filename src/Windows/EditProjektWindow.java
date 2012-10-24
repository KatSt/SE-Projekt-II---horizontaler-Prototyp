package Windows;



import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
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
import javax.swing.SwingConstants;

import Actions.AlleFelderLeeren;
import Actions.AlleFelderUebernehmen;
import Actions.TextAreaUebernehmenButton;
import Actions.UebernehmenButton;
import Listener.TabChangeListener;
import Listener.TextAreaFocusListener;
import Listener.TextFieldFocusListener;
import Objekt.Ansprechpartner;
import Objekt.Projektantrag;
import Objekt.Student;
import TableModel.AnsprechpartnerTableModel;

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
   boolean tab1 = false;
   boolean tab2 = false;
   boolean tab3 = false;
   boolean tab4 = false;
   boolean tab5 = false;
   boolean tab6 = false;

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
   
   private JScrollPane skizzeFirebirdScrollPane;
   private JScrollPane skizzeAWCScrollPane;

//   private boolean exists;
//   private String anredetextFirebird;
//   private String nameFirebird;
//   private String vornameFirebird;
//   private String matrikelnummerFirebird;
//   private String emailFirebird;
//   private String telefonFirebird;
//   private String kommentarAdiuvo;
//   private String kommentarInternFirebird;
//   private String internetseiteFirebird;
   
  
//   private JTextField beginnAWC;
//   private JTextField beginnAdiuvo;   
//   private JTextField endeAdiuvo;
//   private JTextArea projektskizzeAdiuvo;
//   private JTextArea projektskizzeAWC;   
//   private JTextField kolloquiumAdiuvo;
//   private JTextField noteAWC;
//   private JTextField noteAdiuvo;
//   private JTextField projektKommentarAdiuvo;
//   private JTextField projektKommentarInternAdiuvo;
//   private JTextField projektartAdiuvo;
//   private JTextField projekttitelAWC;
//   private JTextField projekttitelAdiuvo;
//   private JTextField technologienAWC;
//   private JTextField technologienAdiuvo;
//   private JTextArea projekthintergrundAWC;
//   private JTextArea projekthintergrundAdiuvo;
//   private JTextArea projektbeschreibungAWC;
//   private JTextArea projektbeschreibungAdiuvo;
   
   /**
    * 
    * Konstruktor.
    *
    * @param ptitel     Titel des ausgewählten Projekts.
    */
   public EditProjektWindow(Projektantrag ptitel)
   {
      super();
      setTitle(ptitel.toString());
      setModal(true);
      projekt = ptitel;
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
      setLocation(150, 100);      
      setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
      setSize(900, 600);

      JTabbedPane tabbedPane = new JTabbedPane();
      tabbedPane.addChangeListener(new TabChangeListener(this));

      tabbedPane.add("Projekt", initProjektTab());
      tabbedPane.add("Student 1", initStudentTab1(projekt.getStudent1(), student1AdiuvoAWCTextFields, student1AdiuvoTextFields));
      if(projekt.getStudent2() != null)
      {
         tabbedPane.add("Student 2", initStudentTab1(projekt.getStudent2(), student2AdiuvoAWCTextFields, student2AdiuvoTextFields));
      }
      if(projekt.getStudent3() != null)
      {
         tabbedPane.add("Student 3", initStudentTab1(projekt.getStudent3(), student3AdiuvoAWCTextFields, student3AdiuvoTextFields));
      }

      tabbedPane.add("Ansprechpartner", initAnsprechpartnerTab(projekt.getAnsprechpartner()));
      tabbedPane.add("Unternehmen", initUnternehmenTab());
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

      JButton archivieren = new JButton(new AbstractAction("Archivieren")
      {

         @Override
         public void actionPerformed(ActionEvent e)
         {
            if(tab1 == false || tab2 == false || tab3 == false || tab4 == false || tab5 == false || tab6 == false)
            {
//               int returnval = JOptionPane.showConfirmDialog(null, "<html>Es wurden nicht alle Tabs betrachtet. <br /> " +
//                     "Möchten Sie trotzdem archivieren?", "Info", JOptionPane.INFORMATION_MESSAGE);
//               if(returnval == JOptionPane.YES_OPTION)
//               {
//                  archivieren();
//               }
//               JOptionPane.showMessageDialog(null, "Alle Tabs müssen vor dem Archivieren betrachtet werden!", "Info", JOptionPane.INFORMATION_MESSAGE);
               archivieren();
            }
            else
            {
               archivieren();
            }

         }
      });    
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

   public void archivieren()
   {
//      if(exists)
//      {
//         String update = "UPDATE STUDENT SET ";
//         int counter = 0;
//         if(!anredetextFirebird.equals(studentanredeFirebird.getText()))
//         {
//            update+= "Anrede = '" + studentanredeFirebird.getText() + "'";
//            counter++;
//         }
//         if(!vornameFirebird.equals(studentvornameFirebird.getText()))
//         {
//            if(counter > 0)
//            {
//               update+= ", ";
//            }
//            update+= "Vorname = '" + studentvornameFirebird.getText() + "'";
//            counter++;
//         }
//         if(!nameFirebird.equals(studentnameFirebird.getText()))
//         {
//            if(counter > 0)
//            {
//               update+= ", ";
//            }
//            update+= "Name = '" + studentnameFirebird.getText() + "'";
//            counter++;
//         }
//         if(!telefonFirebird.equals(studenttelefonFirebird.getText()))
//         {
//            if(counter > 0)
//            {
//               update+= ", ";
//            }
//            update+= "Telefon = '" + studenttelefonFirebird.getText() + "'";     
//            counter++;
//         }
//         if(!emailFirebird.equals(studentemailFirebird.getText()))
//         {
//            if(counter > 0)
//            {
//               update+= ", ";
//            }
//            update += "Email = '" + studentemailFirebird.getText() + "'";
//            counter++;
//         }               
//         if(!matrikelnummerFirebird.equals(studentmatrikelnrFirebird.getText()))
//         {
//            if(counter > 0)
//            {
//               update+= ", ";
//            }
//            update += "Matrikelnummer = '" + studentmatrikelnrFirebird.getText() + "'";
//            counter++;
//         }
//         if(!kommentarAdiuvo.equals(studentkommentarFirebird.getText()))
//         {
//            if(counter > 0)
//            {
//               update+= ", ";
//            }
//            update += "Kommentar = '" + studentkommentarFirebird.getText() + "'";
//            counter++;
//         }
//         if(!kommentarInternFirebird.equals(studentkommentarInternFirebird.getText()))
//         {
//            if(counter > 0)
//            {
//               update+= ", ";
//            }
//            update += "Kommentarintern = '" + studentkommentarInternFirebird.getText() + "'";
//            counter++;
//         }
//         if(!internetseiteFirebird.equals(studentinternetseiteFirebird.getText()))
//         {
//            if(counter > 0)
//            {
//               update+= ", ";
//            }
//            update += "Internetseite = '" + studentinternetseiteFirebird.getText() + "'";
//            counter++;
//         }
//
//         update += " WHERE matrikelnummer = '" + matrikelnummerFirebird + "';";
//         System.out.println(update);
//         if(counter > 0 )
//         {
//            ConnectFirebirdDatabase.update(update);
//         }
//         dispose();
//
//      }
//      else
//      {
//         
//         String update = "INSERT INTO STUDENT" +             
//         "(ANREDE, VORNAME, NACHNAME, TELEFON, EMAIL, MATRIKELNUMMER, KOMMENTAR, KOMMENTARINTERN, INTERNETSEITE, ZULETZTGEAENDERT)" 
//         + "VALUES ( "
//         + getValue(studentanredeFirebird) + ", "
//         + getValue(studentvornameFirebird) + ", "
//         + getValue(studentnameFirebird) + ", "
//         + getValue(studenttelefonFirebird) + ", "
//         + getValue(studentemailFirebird) + ", "
//         + getValue(studentmatrikelnrFirebird) + ", "
//         + getValue(studentkommentarFirebird) + ", "
//         + getValue(studentkommentarInternFirebird) + ", "
//         + getValue(studentinternetseiteFirebird) + ", "
//         + "'NOW' )";
//
//
//
//         System.out.println(update);
//         ConnectFirebirdDatabase.update(update);
//         exists = true;
//         setFirebirdStrings();
//         dispose();
//      }
   }
   
//   private String getValue(JTextField field)
//   {
//      String s = "";
//      if(field.getText().equals(""))
//      {
//         s = "NULL";
//      }
//      else
//      {
//         s = "'" + field.getText() + "'";
//      }
//      
//      return s;
//   }

   /**
    * 
    * Initialisiert den Tab für die Studenten.
    *
    * @return   JScrollPane mit Studentendaten.
    */
   private JScrollPane initStudentTab1(Student student, Vector<JTextField> alleFelderAdiuvoAWC, Vector<JTextField> alleFelderAdiuvo)
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
      
      alleFelderAdiuvo = alleFelderAdiuvoAWC;
      alleFelderAdiuvo.add(studentinternetseiteFirebird);
      alleFelderAdiuvoAWC.add(studentkommentarFirebird);
      alleFelderAdiuvoAWC.add(studentkommentarInternFirebird);
      
      studentanredeAWC.setText(student.getAnrede());
      studentnameAWC.setText(student.getName());
      studentvornameAWC.setText(student.getVorname());
      studentmatrikelnrAWC.setText(student.getMatrikelnummer());
      studentemailAWC.setText(student.getEmail());
      studenttelefonAWC.setText(student.getTelefon());

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

   private void initStudentTab2()
   {
//
//      Box tab = Box.createVerticalBox();
//      Dimension labelDimension = new Dimension(130, 16);
//      Dimension textFieldDimension = new Dimension(300, 30);
//      Dimension buttonDimension = new Dimension (55,26);      
//      
//      JScrollPane scrollPanetab = new JScrollPane(tab);
//
//      // JPanel Überschriften
//      JPanel ueberschriften = new JPanel(new FlowLayout());
//      JLabel empty = new JLabel();
//      empty.setPreferredSize(labelDimension);
//      ueberschriften.add(empty);
//
//      JLabel awc = new JLabel("AWC");
//      awc.setHorizontalAlignment(SwingConstants.CENTER);
//      awc.setPreferredSize(textFieldDimension);
//      ueberschriften.add(awc);
//
//      JLabel empty2 = new JLabel();
//      empty2.setPreferredSize(buttonDimension);
//      ueberschriften.add(empty2);
//
//      JLabel adiuvo = new JLabel("Adiuvo");
//      adiuvo.setHorizontalAlignment(SwingConstants.CENTER);
//      adiuvo.setPreferredSize(textFieldDimension);
//      ueberschriften.add(adiuvo);
//
//      tab.add(ueberschriften);
//      
//      // JPanel Anrede
//      JPanel info1 = new JPanel();
//      info1.setLayout(new FlowLayout());
//      JLabel studentanrede = new JLabel("Anrede:");
//      studentanrede.setPreferredSize(labelDimension);
//      info1.add(studentanrede);
//
//      JTextField studentanredeAWC = new JTextField();
//      studentanredeAWC.setEditable(false);
//      studentanredeAWC.setPreferredSize(textFieldDimension);      
//      info1.add(studentanredeAWC);
//
//      JButton anredeUebernehmen = new JButton(">");
//      anredeUebernehmen.setPreferredSize(buttonDimension);
//      anredeUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
//      info1.add(anredeUebernehmen);
//
//      JTextField studentanredeFirebird = new JTextField();
//      studentanredeFirebird.setPreferredSize(textFieldDimension);
//      info1.add(studentanredeFirebird);
//      tab.add(info1);
//
//      // JPanel Name      
//      JPanel info2 = new JPanel(new FlowLayout());
//      JLabel studentname = new JLabel("Name:");
//      studentname.setPreferredSize(labelDimension);
//      info2.add(studentname);
//
//      JTextField studentnameAWC = new JTextField();
//      studentnameAWC.setEditable(false);
//      studentnameAWC.setPreferredSize(textFieldDimension);
//      info2.add(studentnameAWC);
//
//      JButton nameUebernehmen = new JButton(">");
//      nameUebernehmen.setPreferredSize(buttonDimension);
//      nameUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
//      info2.add(nameUebernehmen);
//
//      JTextField studentnameFirebird = new JTextField();
//      studentnameFirebird.setPreferredSize(textFieldDimension);
//      info2.add(studentnameFirebird);    
//      tab.add(info2);
//
//      // JPanel Vorname
//      JPanel info3 = new JPanel(new FlowLayout());
//      JLabel studentvorname = new JLabel("Vorname:");
//      studentvorname.setPreferredSize(labelDimension);
//      info3.add(studentvorname);
//
//      JTextField studentvornameAWC = new JTextField();
//      studentvornameAWC.setEditable(false);
//      studentvornameAWC.setPreferredSize(textFieldDimension);
//      info3.add(studentvornameAWC);
//
//      JButton vornameUebernehmen = new JButton(">");
//      vornameUebernehmen.setPreferredSize(buttonDimension);
//      vornameUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
//      info3.add(vornameUebernehmen);
//
//      JTextField studentvornameFirebird = new JTextField();
//      studentvornameFirebird.setPreferredSize(textFieldDimension);
//      info3.add(studentvornameFirebird);    
//      tab.add(info3);
//
//      // JPanel Matrikelnummer
//      JPanel info4 = new JPanel(new FlowLayout());
//      JLabel studentmatrikelnr = new JLabel("Matrikelnummer:");
//      studentmatrikelnr.setPreferredSize(labelDimension);
//      info4.add(studentmatrikelnr);
//
//      JTextField studentmatrikelnrAWC = new JTextField();
//      studentmatrikelnrAWC.setEditable(false);
//      studentmatrikelnrAWC.setPreferredSize(textFieldDimension);
//      info4.add(studentmatrikelnrAWC);
//
//      JButton matrikelnrUebernehmen = new JButton(">");
//      matrikelnrUebernehmen.setPreferredSize(buttonDimension);
//      matrikelnrUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
//      info4.add(matrikelnrUebernehmen);
//
//      JTextField studentmatrikelnrFirebird = new JTextField();
//      studentmatrikelnrFirebird.setPreferredSize(textFieldDimension);
//      studentmatrikelnrFirebird.setEditable(false);
//      info4.add(studentmatrikelnrFirebird);      
//      tab.add(info4);
//
//      // JPanel Email
//      JPanel info5 = new JPanel(new FlowLayout());
//      JLabel studentemail = new JLabel("Email:");
//      studentemail.setPreferredSize(labelDimension);
//      info5.add(studentemail);
//
//      JTextField studentemailAWC = new JTextField();
//      studentemailAWC.setEditable(false);
//      studentemailAWC.setPreferredSize(textFieldDimension);
//      info5.add(studentemailAWC);
//
//      JButton emailUebernehmen = new JButton(">");
//      emailUebernehmen.setPreferredSize(buttonDimension);
//      emailUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
//      info5.add(emailUebernehmen);
//
//      JTextField studentemailFirebird = new JTextField();
//      studentemailFirebird.setPreferredSize(textFieldDimension);
//      info5.add(studentemailFirebird);      
//      tab.add(info5);
//
//      // JPanel Telefon
//      JPanel info6 = new JPanel(new FlowLayout());
//      JLabel studenttelefon = new JLabel("Telefon:");
//      studenttelefon.setPreferredSize(labelDimension);
//      info6.add(studenttelefon);
//
//      JTextField studenttelefonAWC = new JTextField();
//      studenttelefonAWC.setEditable(false);
//      studenttelefonAWC.setPreferredSize(textFieldDimension);
//      info6.add(studenttelefonAWC);
//
//      JButton telefonUebernehmen = new JButton(">");
//      telefonUebernehmen.setPreferredSize(buttonDimension);
//      telefonUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
//      info6.add(telefonUebernehmen);
//
//      JTextField studenttelefonFirebird = new JTextField();
//      studenttelefonFirebird.setPreferredSize(textFieldDimension);
//      info6.add(studenttelefonFirebird);  
//      tab.add(info6);
//      
//      // JPanel Internetseite
//      JPanel info9 = new JPanel(new FlowLayout());
//      JLabel studentinternetseite = new JLabel("Internetseite:");
//      studentinternetseite.setPreferredSize(labelDimension);
//      info9.add(studentinternetseite);
//
//      JPanel studentinternetseiteAWC = new JPanel();
//      studentinternetseiteAWC.setPreferredSize(new Dimension(textFieldDimension.width + buttonDimension.width + 5, textFieldDimension.height));
//      info9.add(studentinternetseiteAWC);
//
//      JTextField studentinternetseiteFirebird = new JTextField();
//      studentinternetseiteFirebird.setPreferredSize(textFieldDimension);
//      info9.add(studentinternetseiteFirebird);      
//      tab.add(info9);
//      
//   // JPanel Kommentar
//      JPanel info7 = new JPanel(new FlowLayout());
//      JLabel studentkommentar = new JLabel("Kommentar:");
//      studentkommentar.setPreferredSize(labelDimension);
//      info7.add(studentkommentar);
//
//      JPanel studentkommentarAWC = new JPanel();
//      studentkommentarAWC.setPreferredSize(textFieldDimension);
//      info7.add(studentkommentarAWC);
//
//      JPanel button7 = new JPanel(new FlowLayout());
//      button7.setPreferredSize(buttonDimension);
//      info7.add(button7);
//
//      JTextField kommentarFirebird = new JTextField();
//      kommentarFirebird.setPreferredSize(textFieldDimension);
//      info7.add(kommentarFirebird);      
//      tab.add(info7);
//      
//      // JPanel Kommentar Intern
//      JPanel info8 = new JPanel(new FlowLayout());
//      JLabel studentkommentarIntern = new JLabel("Kommentar intern:");
//      studentkommentarIntern.setPreferredSize(labelDimension);
//      info8.add(studentkommentarIntern);
//
//      JPanel studentkommentarInternAWC = new JPanel();
//      studentkommentarInternAWC.setPreferredSize(textFieldDimension);
//      info8.add(studentkommentarInternAWC);
//
//      JPanel button8 = new JPanel(new FlowLayout());
//      button8.setPreferredSize(buttonDimension);
//      info8.add(button8);
//
//      JTextField kommentarInternFirebird = new JTextField();
//      kommentarInternFirebird.setPreferredSize(textFieldDimension);
//      info8.add(kommentarInternFirebird);      
//      tab.add(info8);
//            
//      
//      // Buttons
//      JPanel buttonPanel = new JPanel(new FlowLayout());
//      JLabel emptyButton = new JLabel();
//      emptyButton.setPreferredSize(new Dimension(labelDimension.width + textFieldDimension.width + 5, 30));
//      buttonPanel.add(emptyButton);
//
//      JButton alleUebernehmen = new JButton("Alle");
//      alleUebernehmen.setPreferredSize(buttonDimension);
//      buttonPanel.add(alleUebernehmen);      
//
//      JPanel alleFelderLeerenPanel = new JPanel();
//      alleFelderLeerenPanel.setLayout(new BoxLayout(alleFelderLeerenPanel, BoxLayout.Y_AXIS));
//      alleFelderLeerenPanel.setPreferredSize(new Dimension(textFieldDimension.getSize().width, 28));
//      JButton alleLeeren = new JButton("Alle Felder leeren");
//      alleLeeren.setAlignmentX(Component.CENTER_ALIGNMENT);
//      alleFelderLeerenPanel.add(alleLeeren);
//      buttonPanel.add(alleFelderLeerenPanel);
//      tab.add(buttonPanel);
//
//      return scrollPanetab;
//
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
      skizzeAWCScrollPane = new JScrollPane(projektskizzeAWC);
      skizzeAWCScrollPane.setPreferredSize(textFieldDimension);
      info2.add(skizzeAWCScrollPane);

      JButton skizzeUebernehmen = new JButton(">");
      skizzeUebernehmen.setPreferredSize(buttonDimension);
      skizzeUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info2.add(skizzeUebernehmen);

      JTextArea projektskizzeAdiuvo = new JTextArea();
      projektskizzeAdiuvo.setLineWrap(true);
      projektskizzeAdiuvo.setWrapStyleWord(true);
      skizzeFirebirdScrollPane = new JScrollPane(projektskizzeAdiuvo);
      projektartAdiuvo.addFocusListener(new TextAreaFocusListener(skizzeAWCScrollPane, skizzeFirebirdScrollPane, textFieldDimension, textAreaDimension));
//      projektskizzeAdiuvo.addFocusListener(new FocusListener()
//      {        
//         
//         @Override
//         public void focusLost(FocusEvent e)
//         {
//            skizzeAWCScrollPane.setPreferredSize(textFieldDimension);
//            skizzeFirebirdScrollPane.setPreferredSize(textFieldDimension);
//            skizzeAWCScrollPane.updateUI();
//            skizzeFirebirdScrollPane.updateUI();
//         }
//         
//         @Override
//         public void focusGained(FocusEvent e)
//         {
//            skizzeAWCScrollPane.setPreferredSize(textAreaDimension);
//            skizzeFirebirdScrollPane.setPreferredSize(textAreaDimension);
//            skizzeAWCScrollPane.updateUI();
//            skizzeFirebirdScrollPane.updateUI();
//         }
//      });
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
      hintergrundFirebirdScrollPane.setPreferredSize(textFieldDimension);
      hintergrundUebernehmen.setAction(new TextAreaUebernehmenButton(projekthintergrundAWC, projekthintergrundAdiuvo));
      projekthintergrundAdiuvo.addFocusListener(new TextAreaFocusListener(hintergrundAWCScrollPane, hintergrundFirebirdScrollPane, textFieldDimension, textAreaDimension));
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
      beschreibungFirebirdScrollPane.addFocusListener(new TextAreaFocusListener(beschreibungAWCScrollPane, beschreibungFirebirdScrollPane, textFieldDimension, textAreaDimension));
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
      
      projektAdiuvoTextFields = projektAdiuvoAWCTextFields;
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
      JPanel ansprechpartnerAuswahl = new JPanel(new FlowLayout());
      JLabel empty3 = new JLabel();
      empty3.setPreferredSize(new Dimension(labelDimension.width + textFieldDimension.width + buttonDimension.width, 10));
      ansprechpartnerAuswahl.add(empty3);
      
      AnsprechpartnerTableModel ansprechpartnerTableModel = new AnsprechpartnerTableModel();
      JTable ansprechpartnerAuswahlTable = new JTable(ansprechpartnerTableModel);
      ansprechpartnerAuswahlTable.setEnabled(false);
      JScrollPane ansprechpartnerTablePane = new JScrollPane(ansprechpartnerAuswahlTable);
      ansprechpartnerTablePane.setPreferredSize(new Dimension(textFieldDimension.width, 80));
      ansprechpartnerAuswahl.add(ansprechpartnerTablePane);
      
      tab.add(ansprechpartnerAuswahl);
      
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
      
      alleLeeren.setAction(new AlleFelderLeeren(alleAdiuvoFelder, null));
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
      plzUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info3.add(plzUebernehmen);

      JTextField plzFirebird = new JTextField();
      plzFirebird.setPreferredSize(textFieldDimension);
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
      ortUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info4.add(ortUebernehmen);

      JTextField ortFirebird = new JTextField();    
      ortFirebird.setPreferredSize(textFieldDimension);
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
      telefonUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info5.add(telefonUebernehmen);

      JTextField telefonFirebird = new JTextField();
      telefonFirebird.setPreferredSize(textFieldDimension);
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
      emailUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info6.add(emailUebernehmen);

      JTextField emailFirebird = new JTextField();
      emailFirebird.setPreferredSize(textFieldDimension);
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
      faxUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info7.add(faxUebernehmen);

      JTextField faxFirebird = new JTextField();
      faxFirebird.setPreferredSize(textFieldDimension);
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
      internetseiteUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info8.add(internetseiteUebernehmen);

      JTextField internetseiteFirebird = new JTextField();
      internetseiteFirebird.setPreferredSize(textFieldDimension);
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
      brancheUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info9.add(brancheUebernehmen);

      JTextField brancheFirebird = new JTextField();
      brancheFirebird.setPreferredSize(textFieldDimension);
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
      kommentarUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info10.add(kommentarUebernehmen);

      JTextField kommentarFirebird = new JTextField();
      kommentarFirebird.setPreferredSize(textFieldDimension);
      info10.add(kommentarFirebird);      
      tab.add(info10);
      
   // JPanel Kommentar intern
      JPanel info11 = new JPanel(new FlowLayout());
      JLabel kommentarIntern = new JLabel("Kommentar:");
      kommentarIntern.setPreferredSize(labelDimension);
      info11.add(kommentarIntern);

      JTextField kommentarInternAWC = new JTextField();
      kommentarInternAWC.setEditable(false);
      kommentarInternAWC.setPreferredSize(textFieldDimension);
      info11.add(kommentarInternAWC);

      JButton kommentarInternUebernehmen = new JButton(">");
      kommentarInternUebernehmen.setHorizontalAlignment(SwingConstants.CENTER);
      info11.add(kommentarInternUebernehmen);

      JTextField kommentarInternFirebird = new JTextField();
      kommentarInternFirebird.setPreferredSize(textFieldDimension);
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


      return scrollPanetab;
   }

   
   /**
    * Setter for property tab1.
    *
    * @param tab1 The tab1 to set.
    */
   public void setTab1(boolean tab1)
   {
      this.tab1 = tab1;
   }

   /**
    * Setter for property tab2.
    *
    * @param tab2 The tab2 to set.
    */
   public void setTab2(boolean tab2)
   {
      this.tab2 = tab2;
   }

   /**
    * Setter for property tab3.
    *
    * @param tab3 The tab3 to set.
    */
   public void setTab3(boolean tab3)
   {
      this.tab3 = tab3;
   }

   /**
    * Setter for property tab4.
    *
    * @param tab4 The tab4 to set.
    */
   public void setTab4(boolean tab4)
   {
      this.tab4 = tab4;
   }

   /**
    * Setter for property tab5.
    *
    * @param tab5 The tab5 to set.
    */
   public void setTab5(boolean tab5)
   {
      this.tab5 = tab5;
   }

   /**
    * Setter for property tab6.
    *
    * @param tab6 The tab6 to set.
    */
   public void setTab6(boolean tab6)
   {
      this.tab6 = tab6;
   }

//   /**
//    * 
//    * Selektiert einen Studenten aus der AWC Datenbank.
//    *
//    * @return   ResultSet mit dem Studenten
//    */
//   private ResultSet selectStudentFromAWC()
//   {
//      ResultSet rs;
//      rs = ConnectMySQLDatabase.getInstance().query("SELECT * FROM student WHERE matrikelnummer = '10021892'");
//      return rs;
//   }
//
//
//   /**
//    * 
//    * Selektiert, wenn vorhanden, den gleichen Studenten aus der Firebird Datenbank.
//    *
//    * @param matrikelnummer     Matrikelnummer des Studenten.
//    * @return   ResultSet mit dem Studenten.
//    */
//   private ResultSet selectStudentFromFirebird(String matrikelnummer)
//   {
//      ResultSet rs;
//      rs = ConnectFirebirdDatabase.getInstance().query("SELECT Anrede, Vorname, Nachname, Matrikelnummer " +
//            ", Email, Telefon, Kommentar, Kommentarintern, Internetseite FROM student WHERE matrikelnummer = '" + matrikelnummer + "'");
//      return rs;
//   }

//   private void setFirebirdStrings()
//   {
//      anredetextFirebird = studentanredeFirebird.getText();
//      vornameFirebird = studentvornameFirebird.getText();
//      nameFirebird = studentnameFirebird.getText();
//      matrikelnummerFirebird = studentmatrikelnrFirebird.getText();
//      emailFirebird = studentemailFirebird.getText();
//      telefonFirebird = studenttelefonFirebird.getText();
//   }
}

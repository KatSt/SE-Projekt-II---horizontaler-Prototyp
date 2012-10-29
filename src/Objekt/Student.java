package Objekt;

/**
 * 
 * Verwaltet die Daten eines Studenten.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 17.10.2012 $ UTC
 */
public class Student {

   private int id;
   private String anrede;
   private String vorname;
   private String name;
   private String matrikelnummer;
   private String email;
   private String telefon;
   private String internetseite;
   private String kommentar;
   private String kommentarIntern;
   
   /**
    * 
    * Übernimmt und setzt die Parameter.
    *
    * @param id
    * @param anrede
    * @param vorname
    * @param name
    * @param matrikelnummer
    * @param email
    * @param telefon
    */
   public Student(int id, String anrede, String vorname, String name, String matrikelnummer, String email,
         String telefon)
   {
      this.id = id;
      this.anrede = anrede;
      this.vorname = vorname;
      this.name = name;
      this.matrikelnummer = matrikelnummer;
      this.email = email;
      this.telefon = telefon;      
   }
   
   /**
    * Getter for property id.
    * 
    * @return Returns the id.
    */
   public int getId()
   {
      return id;
   }
   /**
    * Getter for property anrede.
    * 
    * @return Returns the anrede.
    */
   public String getAnrede()
   {
      return anrede;
   }
   /**
    * Getter for property vorname.
    * 
    * @return Returns the vorname.
    */
   public String getVorname()
   {
      return vorname;
   }
   /**
    * Getter for property name.
    * 
    * @return Returns the name.
    */
   public String getName()
   {
      return name;
   }
   /**
    * Getter for property matrikelnummer.
    * 
    * @return Returns the matrikelnummer.
    */
   public String getMatrikelnummer()
   {
      return matrikelnummer;
   }
   /**
    * Getter for property email.
    * 
    * @return Returns the email.
    */
   public String getEmail()
   {
      return email;
   }
   /**
    * Getter for property telefon.
    * 
    * @return Returns the telefon.
    */
   public String getTelefon()
   {
      return telefon;
   }

   /**
    * Getter for property internetseite.
    * 
    * @return Returns the internetseite.
    */
   public String getInternetseite()
   {
      return internetseite;
   }

   /**
    * Setter for property internetseite.
    *
    * @param internetseite The internetseite to set.
    */
   public void setInternetseite(String internetseite)
   {
      this.internetseite = internetseite;
   }

   /**
    * Getter for property kommentar.
    * 
    * @return Returns the kommentar.
    */
   public String getKommentar()
   {
      return kommentar;
   }

   /**
    * Setter for property kommentar.
    *
    * @param kommentar The kommentar to set.
    */
   public void setKommentar(String kommentar)
   {
      this.kommentar = kommentar;
   }

   /**
    * Getter for property kommentarIntern.
    * 
    * @return Returns the kommentarIntern.
    */
   public String getKommentarIntern()
   {
      return kommentarIntern;
   }

   /**
    * Setter for property kommentarIntern.
    *
    * @param kommentarIntern The kommentarIntern to set.
    */
   public void setKommentarIntern(String kommentarIntern)
   {
      this.kommentarIntern = kommentarIntern;
   } 
   
   

}

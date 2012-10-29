package Objekt;

/**
 * 
 * Speichert die Daten eines Ansprechpartners.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 17.10.2012 $ UTC
 */
public class Ansprechpartner {

   private int id;
   private String anrede;
   private String titel;
   private String vorname;
   private String name;
   private String position;
   private String abteilung;
   private String telefon1;
   private String telefon2;
   private String mobil;
   private String fax;
   private String email;
   private String kommentar;
   private Unternehmen unternehmen;
   private String kommentarIntern;
   private String internetseite;
   
   /**
    * 
    * Übernimmt und setzt die Parameter.
    *
    * @param id
    * @param titel
    * @param vorname
    * @param name
    * @param position
    * @param abteilung
    * @param telefon1
    * @param telefon2
    * @param mobil
    * @param fax
    * @param email
    * @param kommentar
    */
   public Ansprechpartner(int id, String anrede, String titel, String vorname, String name, String position, String abteilung, 
         String telefon1, String telefon2, String mobil, String fax, String email, String kommentar)
   {
      this.id = id;
      this.anrede = anrede;
      this.titel = titel;
      this.vorname = vorname;
      this.name = name;
      this.position = position;
      this.abteilung = abteilung;
      this.telefon1 = telefon1;
      this.telefon2 = telefon2;
      this.mobil = mobil;
      this.fax = fax;
      this.email = email;
      this.kommentar = kommentar;
  
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
    * Getter for property titel.
    * 
    * @return Returns the titel.
    */
   public String getTitel()
   {
      return titel;
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
    * Getter for property position.
    * 
    * @return Returns the position.
    */
   public String getPosition()
   {
      return position;
   }
   /**
    * Getter for property abteilung.
    * 
    * @return Returns the abteilung.
    */
   public String getAbteilung()
   {
      return abteilung;
   }
   /**
    * Getter for property telefon1.
    * 
    * @return Returns the telefon1.
    */
   public String getTelefon1()
   {
      return telefon1;
   }
   /**
    * Getter for property telefon2.
    * 
    * @return Returns the telefon2.
    */
   public String getTelefon2()
   {
      return telefon2;
   }
   /**
    * Getter for property mobil.
    * 
    * @return Returns the mobil.
    */
   public String getMobil()
   {
      return mobil;
   }
   /**
    * Getter for property fax.
    * 
    * @return Returns the fax.
    */
   public String getFax()
   {
      return fax;
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
    * Getter for property internetseite.
    * 
    * @return Returns the internetseite.
    */
   public String getInternetseite()
   {
      return internetseite;
   }
   /**
    * Getter for property unternehmen.
    * 
    * @return Returns the unternehmen.
    */
   public Unternehmen getUnternehmen()
   {
      return unternehmen;
   }

   /**
    * Setter for property unternehmen.
    *
    * @param unternehmen The unternehmen to set.
    */
   public void setUnternehmen(Unternehmen unternehmen)
   {
      this.unternehmen = unternehmen;
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
    * Getter for property anrede.
    * 
    * @return Returns the anrede.
    */
   public String getAnrede()
   {
      return anrede;
   }
   
   
   
}

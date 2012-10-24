package Objekt;

/**
 * 
 * Speichert die Daten eines Unternehmens.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 17.10.2012 $ UTC
 */
public class Unternehmen {

    private int id;
    private String name;
    private String straﬂeHausnr;
    private String pLZ;
    private String ort;
    private String telefon;
    private String email;
    private String fax;
    private String internetseite;
    private String branche;
    private String kommentar;
    private String kommentarIntern;
    
    /**
     * 
     * ‹bernimmt und setzt die Parameter.
     *
     * @param id
     * @param name
     * @param straﬂeHausnr
     * @param pLZ
     * @param ort
     * @param telefon
     * @param email
     * @param fax
     * @param internetseite
     * @param branche
     * @param kommentar
     * @param kommentarIntern
     */
    public Unternehmen(int id, String name, String straﬂeHausnr, String pLZ, String ort, String telefon, String email,
          String fax, String internetseite, String branche, String kommentar, String kommentarIntern)
    {
       this.id = id;
       this.name = name;
       this.straﬂeHausnr = straﬂeHausnr;
       this.pLZ = pLZ;
       this.ort = ort;
       this.telefon = telefon;
       this.email = email;
       this.fax = fax;
       this.internetseite = internetseite;
       this.branche = branche;
       this.kommentar = kommentar;
       this.kommentarIntern = kommentarIntern;
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
    * Getter for property name.
    * 
    * @return Returns the name.
    */
   public String getName()
   {
      return name;
   }

   /**
    * Getter for property straﬂeHausnr.
    * 
    * @return Returns the straﬂeHausnr.
    */
   public String getStraﬂeHausnr()
   {
      return straﬂeHausnr;
   }

   /**
    * Getter for property pLZ.
    * 
    * @return Returns the pLZ.
    */
   public String getpLZ()
   {
      return pLZ;
   }

   /**
    * Getter for property ort.
    * 
    * @return Returns the ort.
    */
   public String getOrt()
   {
      return ort;
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
    * Getter for property email.
    * 
    * @return Returns the email.
    */
   public String getEmail()
   {
      return email;
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
    * Getter for property internetseite.
    * 
    * @return Returns the internetseite.
    */
   public String getInternetseite()
   {
      return internetseite;
   }

   /**
    * Getter for property branche.
    * 
    * @return Returns the branche.
    */
   public String getBranche()
   {
      return branche;
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
}
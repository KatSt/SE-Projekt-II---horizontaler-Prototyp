package Objekt;

/**
 * 
 * Speichert die Daten eines Projektantrags.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 17.10.2012 $ UTC
 */
public class Projektantrag {

   private int id;
   private String projekttitel;
   private String skizze;
   private String hintergrund;
   private String beschreibung;
   private String technologien;
   private String note;
   private String beginn;
   private Student student1;
   private Student student2;
   private Student student3;
   private Ansprechpartner ansprechpartner;
   
   /**
    * 
    * Übernimmt und setzt die Parameter.
    *
    * @param id
    * @param projekttitel
    * @param skizze
    * @param hintergrund
    * @param beschreibung
    * @param technologien
    * @param note
    * @param beginn
    * @param student1
    * @param student2
    * @param student3
    * @param ansprechpartner
    */
   public Projektantrag(int id, String projekttitel, String skizze, String hintergrund, String beschreibung, 
         String note, String beginn)
   {
      this.id = id;
      this.projekttitel = projekttitel;
      this.skizze = skizze;
      this.hintergrund = hintergrund;
      this.beschreibung = beschreibung;
//      this.technologien = technologien;
      this.note = note;
      this.beginn = beginn;
//      this.student1 = student1;
//      this.student2 = student2;
//      this.student3 = student3;
//      this.ansprechpartner = ansprechpartner;
      
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
    * Getter for property projekttitel.
    * 
    * @return Returns the projekttitel.
    */
   public String getProjekttitel()
   {
      return projekttitel;
   }
   /**
    * Getter for property skizze.
    * 
    * @return Returns the skizze.
    */
   public String getSkizze()
   {
      return skizze;
   }
   /**
    * Getter for property hintergrund.
    * 
    * @return Returns the hintergrund.
    */
   public String getHintergrund()
   {
      return hintergrund;
   }
   /**
    * Getter for property beschreibung.
    * 
    * @return Returns the beschreibung.
    */
   public String getBeschreibung()
   {
      return beschreibung;
   }
   /**
    * Getter for property technologien.
    * 
    * @return Returns the technologien.
    */
   public String getTechnologien()
   {
      return technologien;
   }
   /**
    * Getter for property note.
    * 
    * @return Returns the note.
    */
   public String getNote()
   {
      return note;
   }
   /**
    * Getter for property beginn.
    * 
    * @return Returns the beginn.
    */
   public String getBeginn()
   {
      return beginn;
   }
   /**
    * Getter for property student1.
    * 
    * @return Returns the student1.
    */
   public Student getStudent1()
   {
      return student1;
   }
   /**
    * Getter for property student2.
    * 
    * @return Returns the student2.
    */
   public Student getStudent2()
   {
      return student2;
   }
   /**
    * Getter for property student3.
    * 
    * @return Returns the student3.
    */
   public Student getStudent3()
   {
      return student3;
   }
   /**
    * Getter for property ansprechpartner.
    * 
    * @return Returns the ansprechpartner.
    */
   public Ansprechpartner getAnsprechpartner()
   {
      return ansprechpartner;
   }


   /**
    * Setter for property technologien.
    *
    * @param technologien The technologien to set.
    */
   public void setTechnologien(String technologien)
   {
      this.technologien = technologien;
   }


   /**
    * Setter for property student1.
    *
    * @param student1 The student1 to set.
    */
   public void setStudent1(Student student1)
   {
      this.student1 = student1;
   }


   /**
    * Setter for property student2.
    *
    * @param student2 The student2 to set.
    */
   public void setStudent2(Student student2)
   {
      this.student2 = student2;
   }


   /**
    * Setter for property student3.
    *
    * @param student3 The student3 to set.
    */
   public void setStudent3(Student student3)
   {
      this.student3 = student3;
   }


   /**
    * Setter for property ansprechpartner.
    *
    * @param ansprechpartner The ansprechpartner to set.
    */
   public void setAnsprechpartner(Ansprechpartner ansprechpartner)
   {
      this.ansprechpartner = ansprechpartner;
   }


   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      return projekttitel;
   }
   
   

}

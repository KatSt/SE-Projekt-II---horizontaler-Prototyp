package Objekt;


public class Modul {

    private int id;
    private String name;
    private String semster;
    private String jahr;

    public Modul(String name)
    {
       this.name = name;
    }

    
   /**
    * Setter for property id.
    *
    * @param id The id to set.
    */
   public void setId(int id)
   {
      this.id = id;
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
    * Getter for property semster.
    * 
    * @return Returns the semster.
    */
   public String getSemster()
   {
      return semster;
   }

   /**
    * Setter for property semster.
    *
    * @param semster The semster to set.
    */
   public void setSemster(String semster)
   {
      this.semster = semster;
   }

   /**
    * Getter for property jahr.
    * 
    * @return Returns the jahr.
    */
   public String getJahr()
   {
      return jahr;
   }

   /**
    * Setter for property jahr.
    *
    * @param jahr The jahr to set.
    */
   public void setJahr(String jahr)
   {
      this.jahr = jahr;
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

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      return name;
   }  
    
}
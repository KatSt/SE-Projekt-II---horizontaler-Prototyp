package Main;

public class ReturnVal
{
   private int id;
   private boolean commit;
   
   public ReturnVal(int id, boolean commit)
   {
      this.id = id;
      this.commit = commit;
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
    * Getter for property commit.
    * 
    * @return Returns the commit.
    */
   public boolean isCommit()
   {
      return commit;
   }

   
}

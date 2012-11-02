package Main;

/**
 * 
 * Kapselt die StudentenID und den Archivierungserfolg
 * in einer Klasse für einen Rückgabewert.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 30.10.2012 $ UTC
 */
public class ReturnVal
{
   /**
    * StudentenID
    */
   private int id;
   /**
    * Wahrheitswert für Korrektheit des SQL-Befehls
    */
   private boolean commit;
   
   /**
    * 
    * Übernimmt die Parameter.
    *
    * @param id     StudentID
    * @param commit Wahrheitswert
    */
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

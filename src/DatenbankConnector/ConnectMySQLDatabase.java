package DatenbankConnector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * Speichert die Zugangsdaten für die MySQL-Datenbank
 * und baut eine Datenbankverbindung auf.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 18.10.2012 $ UTC
 */
public class ConnectMySQLDatabase
{
   private static Connection con;
   private static Statement stmt;
   private static ConnectMySQLDatabase connectMySqlDatabase;
   
   private static String benutzername;// = "root";
   private static String passwort;// = "";
   private static String host;// = "localhost";
   private static String port;// = "3306";
   private static String datenbankname;// = "awc";
   
   private ConnectMySQLDatabase()
   {
      try
      {
         Class.forName("com.mysql.jdbc.Driver");
      }
      catch(ClassNotFoundException e)
      {
         e.printStackTrace();
      }
   }
   
   public synchronized static ConnectMySQLDatabase getInstance() 
   {
       if (connectMySqlDatabase == null) 
       {
           connectMySqlDatabase = new ConnectMySQLDatabase();
       }
       return connectMySqlDatabase;
   }


   public ResultSet query(String q)
   {
      try
      {
         Class.forName("com.mysql.jdbc.Driver");
      }
      catch(ClassNotFoundException e)
      {
         e.printStackTrace();
      }
      
      try
      {
         ResultSet rs;
         String url = "jdbc:mysql://" + host + ":" + port + "/" + datenbankname;
         con = DriverManager.getConnection(url, benutzername, passwort);
         stmt = con.createStatement();
         rs = stmt.executeQuery(q);
         
         return rs;
      }
      catch(SQLException ex)
      {
         ex.printStackTrace();
      }
      
      return null;
   }

   /**
    * Setter for property benutzername.
    *
    * @param benutzername The benutzername to set.
    */
   public void setBenutzername(String benutzername)
   {
      ConnectMySQLDatabase.benutzername = benutzername;
   }

   /**
    * Setter for property passwort.
    *
    * @param passwort The passwort to set.
    */
   public void setPasswort(String passwort)
   {
      ConnectMySQLDatabase.passwort = passwort;
   }

   /**
    * Setter for property host.
    *
    * @param host The host to set.
    */
   public void setHost(String host)
   {
      ConnectMySQLDatabase.host = host;
   }

   /**
    * Setter for property port.
    *
    * @param port The port to set.
    */
   public void setPort(String port)
   {
      ConnectMySQLDatabase.port = port;
   }

   /**
    * Setter for property datenbankname.
    *
    * @param datenbankname The datenbankname to set.
    */
   public void setDatenbankname(String datenbankname)
   {
      ConnectMySQLDatabase.datenbankname = datenbankname;
   }
   
   
}

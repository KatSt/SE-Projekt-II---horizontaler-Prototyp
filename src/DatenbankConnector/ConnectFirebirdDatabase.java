package DatenbankConnector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * Speichert die Zugangsdaten zur Firebird-Datenbank
 * und baut eine Datenbankverbindung für Zugriffe auf.
 *
 * @author $Author: Katharina Stein $
 * @version $Revision:  $, $Date: 18.10.2012 $ UTC
 */
public class ConnectFirebirdDatabase
{
   private static Connection con;
   private static Statement stmt;
   private static ConnectFirebirdDatabase connectFirebirdDatabase;
   private static String host; // = "localhost";
   private static String port; // = "3050";
   private static String pfad; // = "C:/Users/1/Desktop/Firebird Datenbank/BeispielAdiuvoDatabase.gdb";
   private static String benutzername; // = "SYSDBA";
   private static String passwort; // = "masterkey";
   

   private ConnectFirebirdDatabase()
   {
      try
      {
         Class.forName("org.firebirdsql.jdbc.FBDriver");
      }
      catch(ClassNotFoundException e)
      {
         e.printStackTrace();
      }

   }

   public synchronized static ConnectFirebirdDatabase getInstance()
   {
      if (connectFirebirdDatabase == null) 
      {
         connectFirebirdDatabase = new ConnectFirebirdDatabase();
      }
      return connectFirebirdDatabase;
   }
   
   public  ResultSet query(String q)
   {
//      try
//      {
//         Class.forName("org.firebirdsql.jdbc.FBDriver");
//      }
//      catch(ClassNotFoundException e)
//      {
//         e.printStackTrace();
//      }

      try
      {
         ResultSet rs;
//         String url = "jdbc:firebirdsql://" + host + ":" + port + "/" + pfad;
//
//         con = DriverManager.getConnection(url, benutzername, passwort);
         stmt = con.createStatement();
         rs = stmt.executeQuery(q);
         
         return rs;
      }
      catch(SQLException ex)
      {
         System.out.println("Firebird ist der Fehler!");
         ex.printStackTrace();
      }

      return null;
   }
   
   /**
    * 
    * Führt ein Update bei einem bereits vorhandenen Datensatz in der Datenbank durch.
    *
    * @param q  Updatebefehl.
    */
   public Boolean update(String q)
   {
      try
      {  
         stmt = con.createStatement();
         stmt.executeUpdate(q);
         return true;
      }      
      catch(SQLException ex)
      {
         ex.printStackTrace();
         return false;
      }            
   }
   
   /**
    * 
    * Fügt der Datenbank einen neuen Datensatz hinzu.
    *
    * @param q  Insertbefehl
    * @return   PrimaryKey des Datensatzes.
    */
   public ResultSet insert(String q)
   {      
      try
      {  
         ResultSet generatedKeys;

         stmt = con.createStatement();
         stmt.executeUpdate(q, Statement.RETURN_GENERATED_KEYS );

         generatedKeys = stmt.getGeneratedKeys();

         return generatedKeys;
      }      
      catch(SQLException ex)
      {
         ex.printStackTrace();
         return null;
      }            
   }
   
   /**
    * 
    * Initialisiert die Verbindung zur Datenbank.
    *
    */
   public void openDatabaseConnection()
   {
      try
      {
         Class.forName("org.firebirdsql.jdbc.FBDriver");
      }
      catch(ClassNotFoundException e)
      {
         e.printStackTrace();
      }
      
      try
      {  
         String url = "jdbc:firebirdsql://" + host + ":" + port + "/" + pfad;

         con = DriverManager.getConnection(url, benutzername, passwort);
         con.setAutoCommit(false);      
      }      
      catch(SQLException ex)
      {
         ex.printStackTrace();
      } 
   }
   
   /**
    * 
    * Schileßt eine geöffnete Datenbankverbindung.
    *
    */
   public void closeConnection()
   {
      try
      {
         con.close();
      }
      catch(SQLException e)
      {
         e.printStackTrace();
      }
   }
   /**
    * 
    * Führt einen commit oder einen rollback durch.
    *
    * @param commit 
    */
   public void commit(boolean commit)
   {     
      try
      {  
         if(commit)
         {
            con.commit();
         }
         else
         {
            con.rollback();
         }
      }      
      catch(SQLException ex)
      {
         ex.printStackTrace();
      }            
      
   }
   

   /**
    * Setter for property host.
    *
    * @param host The host to set.
    */
   public void setHost(String host)
   {
      ConnectFirebirdDatabase.host = host;
   }

   /**
    * Setter for property port.
    *
    * @param port The port to set.
    */
   public void setPort(String port)
   {
      ConnectFirebirdDatabase.port = port;
   }

   /**
    * Setter for property pfad.
    *
    * @param pfad The pfad to set.
    */
   public void setPfad(String pfad)
   {
      ConnectFirebirdDatabase.pfad = pfad;
   }

   /**
    * Setter for property benutzername.
    *
    * @param benutzername The benutzername to set.
    */
   public void setBenutzername(String benutzername)
   {
      ConnectFirebirdDatabase.benutzername = benutzername;
   }

   /**
    * Setter for property passwort.
    *
    * @param passwort The passwort to set.
    */
   public void setPasswort(String passwort)
   {
      ConnectFirebirdDatabase.passwort = passwort;
   }
   
}



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
         ResultSet rs;
         String url = "jdbc:firebirdsql://" + host + ":" + port + "/" + pfad;

         con = DriverManager.getConnection(url, benutzername, passwort);
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
   
   public static void update(String q)
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
         stmt = con.createStatement();
         stmt.executeUpdate(q);
      }      
      catch(SQLException ex)
      {
         ex.printStackTrace();
      }      
   }
   
//   public static void commit()
//   {
//      try
//      {
//         Class.forName("org.firebirdsql.jdbc.FBDriver");
//      }
//      catch(ClassNotFoundException e)
//      {
//         e.printStackTrace();
//      }
//      try
//      {         
//         String url = "jdbc:firebirdsql://" + host + ":" + port + "/" + pfad;
//
//         con = DriverManager.getConnection(url, benutzername, passwort);
////         stmt = con.createStatement();
//         con.commit();
//      }      
//      catch(SQLException ex)
//      {
//         ex.printStackTrace();
//      } 
//   }

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



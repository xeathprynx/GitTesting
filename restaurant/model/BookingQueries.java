/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Prakas
 */
public class BookingQueries {
    private static final String URL = "jdbc:derby://localhost:1527/sample";
    private static final String USERNAME = "app";
    private static final String PASSWORD = "app";
    private static final int CAPACITY = 20;
    
   private Connection connection = null; // manages connection
   private PreparedStatement selectAllBookings = null; 
   private PreparedStatement selectBookingsDay = null;
   private PreparedStatement selectTotalDinersForDay = null; 
   private PreparedStatement insertNewBooking = null; 

    public BookingQueries() {
         try 
      {
         connection = 
            DriverManager.getConnection( URL, USERNAME, PASSWORD );

         // create query that selects all entries in the AddressBook
         selectAllBookings = 
            connection.prepareStatement( "SELECT * FROM APP.BOOKINGS");
         
         // create query that selects entries with a specific last name
         selectBookingsDay = connection.prepareStatement( 
            "SELECT * FROM BOOKINGS WHERE LASTNAME = ?" );
         
         selectTotalDinersForDay = connection.prepareStatement( 
            "SELECT SUM(DINERS) FROM BOOKINGS" );
         
         // create insert that adds a new entry into the database
         insertNewBooking = connection.prepareStatement( 
            "INSERT INTO BOOKINGS " + 
            "( LASTNAME,PHONE, DINERS, DAYOFWEEK ) " + 
            "VALUES ( ?, ?, ?, ? )" );
         
      } // end try
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();
         System.exit( 1 );
      } // end catch
    }
    
    public List< Booking > getAllBooking()
   {
      ArrayList< Booking > results = null;
      ResultSet resultSet = null;
      
      try 
      {
         // executeQuery returns ResultSet containing matching entries
         resultSet = selectAllBookings.executeQuery(); 
         results = new ArrayList< Booking >();
         
         while ( resultSet.next() )
         {
               results.add( new Booking(
               resultSet.getString( "DAYOFWEEK" ),
               resultSet.getInt( "DINERS" ),
               resultSet.getInt( "ID" ),
               resultSet.getString( "LASTNAME" ),
               resultSet.getString( "PHONE" ) ) );
         } // end while
      } // end try
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();         
      } // end catch
      finally
      {
         try 
         {
            resultSet.close();
         } // end try
         catch ( SQLException sqlException )
         {
            sqlException.printStackTrace();         
            close();
         } // end catch
      } // end finally
      
      return results;
   } // end method getAllPeople
    
     public List< Booking > getBookingsForDay( String name )
   {
      List< Booking > results = null;
      ResultSet resultSet = null;

      try 
      {
         selectBookingsDay.setString( 1, name ); // specify last name

         // executeQuery returns ResultSet containing matching entries
         resultSet = selectBookingsDay.executeQuery(); 

         results = new ArrayList< Booking >();

         while ( resultSet.next() )
         {
            results.add( new Booking( 
               resultSet.getString( "DAYOFWEEK" ),
               resultSet.getInt( "DINERS" ),
               resultSet.getInt( "ID" ),
               resultSet.getString( "LASTNAME" ),
               resultSet.getString( "PHONE" ) ) );
         } // end while
      } // end try
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();
      } // end catch
      finally
      {
         try 
         {
            resultSet.close();
         } // end try
         catch ( SQLException sqlException )
         {
            sqlException.printStackTrace();         
            close();
         } // end catch
      } // end finally
      
      return results;
   } // end method getPeopleByName
    
    public int addBooking( 
      String name, String phone, int reservation, String day )
   {
      int result = 0;
      int chk = 0; 
      
      chk = totalDinersForDay();
      // set parameters, then execute insertNewPerson
      try 
      {
       if(chk < CAPACITY){
         insertNewBooking.setString( 1, name );
         insertNewBooking.setString( 2, phone );
         insertNewBooking.setInt( 3, reservation );
         insertNewBooking.setString( 4, day );
  
       }   
       else {
           JOptionPane.showMessageDialog(null,"Sorry The Resturaunt Capacity is Full");
       }
         // insert the new entry; returns # of rows updated
         result = insertNewBooking.executeUpdate();
      
      } // end try
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();
         close();
      } // end catch
      
      return result;
   } // end method addPerson
   
     public void close()
   {
      try 
      {
         connection.close();
      } // end try
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();
      } // end catch
   } // end method close

    public int totalDinersForDay() {
             String results = null;
             ResultSet resultSet = null;
      
      try 
      {
         // executeQuery returns ResultSet containing matching entries
         resultSet = selectTotalDinersForDay.executeQuery(); 
         resultSet.next();
         results = resultSet.getString(1);
      } // end try
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();         
      } // end catch
      finally
      {
         try 
         {
            resultSet.close();
         } // end try
         catch ( SQLException sqlException )
         {
            sqlException.printStackTrace();         
            close();
         } // end catch
      } // end finally
      
      return Integer.parseInt(results);
    }
   
}

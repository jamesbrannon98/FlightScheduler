
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jimmy
 */
public class BookingCustomer {

    private static PreparedStatement getBookedSeats;
    private static ResultSet resultSet;
    private static int seatsBooked;
    private static PreparedStatement getFlightSeats;
    private static int totalSeats;
    GUIFrame GUI = new GUIFrame();
    private static final String host = "jdbc:derby://localhost:1527/FlightSchedulerDBJamesjjb6295";
    private static final String user = "java";
    private static final String password = "java";
    
    public static void BookingCustomer(String name, String flight, String date, 
            Timestamp time) throws SQLException{
        Date day = Date.valueOf(date);
        Connection con = getDBConnection();
        PreparedStatement pstmt = null;
        getFlightSeats = con.prepareStatement("select seats from flight where name = ?");
        getFlightSeats.setString(1, flight);
        resultSet = getFlightSeats.executeQuery();
        resultSet.next();
        totalSeats = resultSet.getInt(1);
        getBookedSeats = con.prepareStatement("select count(flight) from bookings where flight = ? and day = ?");
        getBookedSeats.setString(1, flight); getBookedSeats.setDate(2, day); 
        resultSet = getBookedSeats.executeQuery(); 
        resultSet.next(); 
        seatsBooked = resultSet.getInt(1);
        if (totalSeats > seatsBooked){
            try{
                pstmt = con.prepareStatement("INSERT INTO Bookings(customer, flight, day, timestamp) Values(?,?,?,?)");
                pstmt.setString(1, name);
                pstmt.setString(2, flight);
                pstmt.setDate(3, day);
                pstmt.setTimestamp(4, time);
                pstmt.executeUpdate();
            }
            catch(SQLException err){
                System.out.println(err.getMessage());                
            }
        }
        else if (totalSeats == seatsBooked){
            try{
                pstmt = con.prepareStatement("INSERT INTO Waitlist(customer, flight, day, timestamp) Values(?,?,?,?)");
                pstmt.setString(1, name);
                pstmt.setString(2, flight);
                pstmt.setDate(3, day);
                pstmt.setTimestamp(4, time);
                pstmt.executeUpdate();
            }
            catch(SQLException err){
            System.out.println(err.getMessage());                
            }            
        }
        GUIFrame.getAllBookings();
        GUIFrame.getAllWaitlist();
    }
    
    private static Connection getDBConnection() {
        Connection con = null;
        try{
            con = DriverManager.getConnection(host,user,password);
            return con;
                    }
        catch(SQLException err){
            System.out.println(err.getMessage());
            }
        return con;
    }    

    public static void main (String[] args) throws SQLException{
        GUIFrame.getAllBookings();
        GUIFrame.getAllWaitlist();
    }
}

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jimmy
 */
public class DropFlight {

    private static PreparedStatement getBookings;
    private static PreparedStatement getFlights;
    private static ResultSet resultSet;
    private static ResultSet flights;
    private static PreparedStatement getFlightSeats;
    private static ResultSet flightSeats;
    private static int totalSeats;
    public static String string;
    private static PreparedStatement getBookedSeats;
    private static int seatsBooked;
    private static ResultSet date;
    private static int i;
    private static PreparedStatement getWaitlist;
    private static ResultSet waitlist;
    GUIFrame GUI = new GUIFrame();
    private static final String host = "jdbc:derby://localhost:1527/FlightSchedulerDBJamesjjb6295";
    private static final String user = "java";
    private static final String password = "java";
    
    public static String DropFlight(String flight) throws SQLException{
        string = "";
        Connection con = getDBConnection();
        PreparedStatement pstmt = null;
        getWaitlist = con.prepareStatement("Select Customer From Waitlist where Flight = ?");
        getWaitlist.setString(1, flight);
        waitlist = getWaitlist.executeQuery();
        while (waitlist.next()){
            string = string + waitlist.getString(1) + " was taken off the waitlist for flight " + flight + ".\n";
        }
        pstmt = con.prepareStatement("Delete from Waitlist where Flight = ?");
        pstmt.setString(1, flight);
        pstmt.executeUpdate();
        getBookings = con.prepareStatement("Select Customer From Bookings where Flight = ? order by timestamp");
        getBookings.setString(1, flight);
        resultSet = getBookings.executeQuery();
        try{
            pstmt = con.prepareStatement("Delete From Flight where Name = ?");
            pstmt.setString(1, flight);
            pstmt.executeUpdate();
            }
        catch(SQLException err){
            System.out.println(err.getMessage());                
            }
        while (resultSet.next()){
            getFlights = con.prepareStatement("Select Name from Flight", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            flights = getFlights.executeQuery();
            while (flights.next()){
                System.out.println(resultSet.getString(1));
                System.out.println(flights.getString(1));
                java.sql.Timestamp time = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
                pstmt = con.prepareStatement("Select Day from Bookings where Customer = ? and Flight = ?");
                pstmt.setString(1, resultSet.getString(1));
                pstmt.setString(2, flight);
                date = pstmt.executeQuery();
                date.next();
                getFlightSeats = con.prepareStatement("select seats from flight where name = ?");
                getFlightSeats.setString(1, flights.getString(1));
                flightSeats = getFlightSeats.executeQuery();
                flightSeats.next();
                totalSeats = flightSeats.getInt(1);
                getBookedSeats = con.prepareStatement("select count(flight) from bookings where flight = ? and day = ?");
                getBookedSeats.setString(1, flights.getString(1)); getBookedSeats.setDate(2, date.getDate(1)); 
                flightSeats = getBookedSeats.executeQuery(); 
                flightSeats.next(); 
                seatsBooked = flightSeats.getInt(1);
                if (totalSeats > seatsBooked){
                    try{
                        pstmt = con.prepareStatement("INSERT INTO Bookings(customer, flight, day, timestamp) Values(?,?,?,?)");
                        pstmt.setString(1, resultSet.getString(1));
                        pstmt.setString(2, flights.getString(1));
                        pstmt.setDate(3, date.getDate(1));
                        pstmt.setTimestamp(4, time);
                        pstmt.executeUpdate();
                        string = string + resultSet.getString(1) + " was rebooked on flight " + flights.getString(1) + " on " + date.getDate(1) + ".\n";
                        break;
                        }
                    catch(SQLException err){
                        System.out.println(err.getMessage());
                        break;
                        }

                }
                else if (flights.isLast()){
                    string = string + resultSet.getString(1) + " could not be rebooked.\n";
                    break;
                }
                System.out.println(flights.getString(1));
            }
            pstmt = con.prepareStatement("Delete From Bookings where Customer = ? and Flight = ?");
            pstmt.setString(1, resultSet.getString(1));
            pstmt.setString(2, flight);
            pstmt.executeUpdate();
        }
        GUIFrame.getAllBookings();
        GUIFrame.getAllWaitlist();
        GUIFrame.getAllFlight();
        return string;
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
        GUIFrame.getAllFlight();
    }
}
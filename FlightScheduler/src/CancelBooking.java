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
public class CancelBooking {

    private static PreparedStatement getFlight;
    private static ResultSet resultSet;
    private static String flight;
    private static PreparedStatement getWaitlist;
    GUIFrame GUI = new GUIFrame();
    private static final String host = "jdbc:derby://localhost:1527/FlightSchedulerDBJamesjjb6295";
    private static final String user = "java";
    private static final String password = "java";
    
    public static String CancelBooking(String name, String date) throws SQLException{
        Date day = Date.valueOf(date);
        Connection con = getDBConnection();
        PreparedStatement pstmt = null;
        getFlight = con.prepareStatement("Select Flight from Bookings where Customer = ? and Day = ?");
        getFlight.setString(1, name);
        getFlight.setDate(2, day);
        resultSet = getFlight.executeQuery();
        resultSet.next();
        try{
            flight = resultSet.getString(1);
        }
        catch (SQLException err){
            getFlight = con.prepareStatement("Select Flight from Waitlist where Customer = ? and Day = ?");
            getFlight.setString(1, name);
            getFlight.setDate(2, day);
            resultSet = getFlight.executeQuery();
            resultSet.next();
            try{
                flight = resultSet.getString(1);
            }
            catch (SQLException er){
                return flight;
            }
        }
        try{
            pstmt = con.prepareStatement("Delete From Bookings where Customer = ? and Day = ?");
            pstmt.setString(1, name);
            pstmt.setDate(2, day);
            pstmt.executeUpdate();
            }
        catch(SQLException err){
            System.out.println(err.getMessage());                
            }
        try{
            pstmt = con.prepareStatement("Delete From Waitlist where Customer = ? and Day = ?");
            pstmt.setString(1, name);
            pstmt.setDate(2, day);
            pstmt.executeUpdate();
            }
        catch(SQLException err){
            System.out.println(err.getMessage());                
            }
        getWaitlist = con.prepareStatement("Select Customer from Waitlist where Flight = ? and Day = ?");
        getWaitlist.setString(1, flight);
        getWaitlist.setDate(2, day);
        resultSet = getWaitlist.executeQuery();
        while(resultSet.next()){
            java.sql.Timestamp time = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
            pstmt = con.prepareStatement("Delete from Waitlist where Customer = ? and Day = ?");
            pstmt.setString(1, resultSet.getString(1));
            pstmt.setDate(2, day);
            pstmt.executeUpdate();
            BookingCustomer.BookingCustomer(resultSet.getString(1), flight, date, time);
        }
        GUIFrame.getAllBookings();
        GUIFrame.getAllWaitlist();
        return flight;
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

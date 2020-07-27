
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jimmy
 */
public class CheckFlightStatus {
    GUIFrame GUI = new GUIFrame();
    private static final String host = "jdbc:derby://localhost:1527/FlightSchedulerDBJamesjjb6295";
    private static final String user = "java";
    private static final String password = "java";
    private static PreparedStatement pstmt;

    public static String CheckFlightStatus(String flight, String date) throws SQLException{
        Date day = Date.valueOf(date);
        String string = "Customer\tStatus\n";
        Connection con = getDBConnection();
        pstmt = con.prepareStatement("Select Customer from Bookings where flight = ? and day = ?");
        ResultSet rst;
        pstmt.setString(1, flight);
        pstmt.setDate(2, day);
        rst = pstmt.executeQuery();
        while (rst.next()){
            String name = rst.getString("customer");
            string = string + name + "\tBooked\n";
        }
        pstmt = con.prepareStatement("Select Customer from Waitlist where flight = ? and day = ?");
        pstmt.setString(1, flight);
        pstmt.setDate(2, day);
        rst = pstmt.executeQuery();
        while (rst.next()){
            String name = rst.getString("customer");
            string = string + name + "\tWaitlisted\n";
        }
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
}


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
public class CheckCustomerStatus {
    GUIFrame GUI = new GUIFrame();
    private static final String host = "jdbc:derby://localhost:1527/FlightSchedulerDBJamesjjb6295";
    private static final String user = "java";
    private static final String password = "java";
    private static PreparedStatement pstmt;

    public static String CheckCustomerStatus(String name) throws SQLException{
        String string = "Flight\tStatus\tDate\n";
        Connection con = getDBConnection();
        pstmt = con.prepareStatement("Select Flight, Day from Bookings where Customer = ?");
        ResultSet rst;
        pstmt.setString(1, name);
        rst = pstmt.executeQuery();
        while (rst.next()){
            String flight = rst.getString("flight");
            String day = rst.getString("day");
            string = string + flight + "\tBooked\t" + day + "\n";
        }
        pstmt = con.prepareStatement("Select Flight, Day from Waitlist where Customer = ?");
        pstmt.setString(1, name);
        rst = pstmt.executeQuery();
        while (rst.next()){
            String flight = rst.getString("flight");
            String day = rst.getString("day");
            string = string + flight + "\tWaitlisted\t" + day + "\n";
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

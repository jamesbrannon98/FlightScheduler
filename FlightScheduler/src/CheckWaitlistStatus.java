
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
public class CheckWaitlistStatus {
    GUIFrame GUI = new GUIFrame();
    private static final String host = "jdbc:derby://localhost:1527/FlightSchedulerDBJamesjjb6295";
    private static final String user = "java";
    private static final String password = "java";
    private static PreparedStatement pstmt;

    public static String CheckWaitlistStatus(String date) throws SQLException{
        Date day = Date.valueOf(date);
        String string = "Customer\tFlight\n";
        Connection con = getDBConnection();
        pstmt = con.prepareStatement("Select Customer, Flight from Waitlist where day = ?");
        ResultSet rst;
        pstmt.setDate(1, day);
        rst = pstmt.executeQuery();
        while (rst.next()){
            String name = rst.getString("customer");
            String flight = rst.getString("flight");
            string = string + name + "\t" + flight + "\n";
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

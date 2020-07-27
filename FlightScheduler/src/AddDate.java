
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
public class AddDate {
    GUIFrame GUI = new GUIFrame();
    private static final String host = "jdbc:derby://localhost:1527/FlightSchedulerDBJamesjjb6295";
    private static final String user = "java";
    private static final String password = "java";
    
    public static void AddDate(String date) throws SQLException{
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO Day(date) Values(?)";
        Date day = Date.valueOf(date);
        try{
            con = getDBConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, date);
            pstmt.executeUpdate();
        }
        catch(SQLException err){
            System.out.println(err.getMessage());
        }
        GUIFrame.getAllDate();
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
        GUIFrame.getAllDate();
    }
}

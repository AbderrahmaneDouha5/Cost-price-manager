
package prixdrevien.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class maindb {
    
    public static Connection conn = null;
   
     
    public static void openConnection(String db){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/"+db, "root", "f56bsh15vuy");
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    public static void closeConnection(Connection conn, PreparedStatement ps, ResultSet rs ){
        if (rs != null) {
        try {
            rs.close();
        } catch (SQLException e) { 
            e.printStackTrace();
        }
    }
    if (ps != null) {
        try {
            ps.close();
        } catch (SQLException e) { 
            e.printStackTrace();
        }
    }
    if (conn != null) {
        try {
            conn.close();
        } catch (SQLException e) { 
            e.printStackTrace();
        }
    }
    
    }
     public static void closeConnection( ResultSet rs ){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) { 
                e.printStackTrace();
            }
        }
    
    }
    
    
      public static void closeConnection(PreparedStatement ps){
        
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) { 
                e.printStackTrace();
            }
        }
    }
    
    
      public static void closeConnection(Connection conn){
        
    if (conn != null) {
        try {
            conn.close();
        } catch (SQLException e) { 
            e.printStackTrace();
        }
    }
    
    }
}


package prixdrevien.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import prixdrevien.db.maindb;


public class issentials {
    
    private static String db;
    public  static Connection conn = null;
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;
    
    public issentials(String db){
        this.db = db;
        
        
    }
   
    
    public static void create(String query){
        maindb.openConnection(db);
        try{
            
            ps = maindb.conn.prepareStatement(query);
            ps.execute();
            
    
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
           maindb.closeConnection(maindb.conn, ps, rs);
       }
    }
    
    public static ResultSet get(String query){
        maindb.openConnection(db);
        try{
            
            ps = maindb.conn.prepareStatement(query);
            rs = ps.executeQuery();
    
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }
    
    public static  void update(String query){
        maindb.openConnection(db);
        try{
            ps = maindb.conn.prepareStatement(query);
            ps.execute();
            
    
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
           maindb.closeConnection(maindb.conn, ps, rs);
       }
    }
//==============================================================================
//==============================================================================
    
    
    
    public static int getLastId(String table){
        int result = 0;
        try{

            rs = get("SELECT id FROM "+table+" "
                    + "ORDER BY id DESC "
                    + "LIMIT 1;");
            while(rs.next()){
                result = rs.getInt("id");
            }
            
    
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}

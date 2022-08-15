
package prixdrevien.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import prixdrevien.db.maindb;


public class issentials {
    
    private  String db;
    public   Connection conn = null;
    public  PreparedStatement ps = null;
    public  ResultSet rs = null;
    
    public issentials(String db){
        this.db = db;
        
        
    }
   
    
    public  void create(String query){
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
    
    public  ResultSet get(String query){
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
    
    public   void update(String query){
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
    
    
    
    public  int getLastId(String table){
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
        }finally{
            maindb.closeConnection(conn, ps, rs);
        }
        return result;
    }
}

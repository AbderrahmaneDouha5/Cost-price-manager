
package prixdrevien.db.components;

import java.sql.ResultSet;
import java.sql.SQLException;
import prixdrevien.db.maindb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import prixdrevien.db.issentials;

public class Fournisseur {
    
    private Folder folder ;
    private String db;
    public  Connection conn = null;
    public PreparedStatement ps = null;
    public ResultSet rs = null;
    private issentials iss;
    
    public  Fournisseur(String db){
        this.db = db;
        iss = new issentials(db);
        folder = new Folder(db);
    }
    
    
    public int getFourId(String nom){
        int result = 0;
        try{
            rs = iss.get("SELECT id FROM fournisseurs "
                    + "WHERE nom = '"+nom+"'");
            maindb.closeConnection(conn);
            maindb.closeConnection(ps);
            while(rs.next()) result = rs.getInt("id");
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
    public void createFour(String nom, String pay, String comp){
        iss.create("INSERT INTO fournisseurs(nom, compagnie, pay)"
                + "VALUES('"+nom+"', '"+pay+"', '"+comp+"');");
        
    }
    
    public ResultSet getAll(){
        return iss.get("SELECT * FROM fournisseurs;");
        
    }
    
    public ResultSet Search(String nom){
        return iss.get("SELECT * FROM fournisseurs "
                + "WHERE nom = '"+nom+"';");
    }
}

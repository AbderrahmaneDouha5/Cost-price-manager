
package prixdrevien.db.components;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import prixdrevien.db.issentials;
import prixdrevien.db.maindb;


public class IR {
    private Folder folder ;
    private String db;
    public  Connection conn = null;
    public PreparedStatement ps = null;
    public ResultSet rs = null;
    private issentials iss;
    
    public  IR(String db){
        this.db = db;
        iss = new issentials(db);
        folder = new Folder(db);
    }
    
 
    
    
    
    public void createIR(int  id, String ref, String des, String date, String unit,
            double s_amount, double amount_on_s, double unit_price,
            double taux){
        double total = unit_price*amount_on_s*s_amount*taux;
        try{
            ResultSet rs = folder.Search("Id",id);
            while(rs.next()) total = total + rs.getDouble("f.total") ;
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        iss.create("INSERT INTO i_r(referencee, descriptionne, datetim, unit, s_amount, "
                + "amount_on_s, amount, unit_priceda, unit_price, taux, total)"
                + "VALUES('"+ref+"', '"+des+"', '"+date+"', '"+unit+"',"
                +s_amount+", "+amount_on_s+", "
                +amount_on_s*s_amount+", "+unit_price*taux+", "+unit_price+", "
                +taux+", "+total+");");
        createIRFolderRelation(id, iss.getLastId("i_r"));
        
    }
    
    
    private void createIRFolderRelation(int folder, int ir){
        iss.create("INSERT INTO folders_irs_relation(folder_id, i_r_id)"
                + "VALUES("+folder+", "+ir+");");
    }
    
    
    public ResultSet getAll(){
        return iss.get("SELECT * FROM  i_r AS ir "
                + "JOIN folders_irs_relation AS fir "
                + "ON ir.id = fir.i_r_id "
                + "JOIN folders AS f "
                + "ON fir.folder_id = f.id ;");

    }
    
    public ResultSet Search(String key,int id){
        switch(key){
            case "Id":
                key = "ir.id";
                break;
        }
        return iss.get("SELECT * FROM  i_r AS ir "
                + "JOIN folders_irs_relation AS fir "
                + "ON ir.id = fir.i_r_id "
                + "JOIN folders AS f "
                + "ON fir.folder_id = f.id "
                + "WHERE "+key+" = "+id+";");

    }
    
    public ResultSet Search(String key ,String ref){
        switch(key){
            case "Réference" :
                key = "ir.referencee";
                break;
            case "Unité" :
                key = "ir.unit";
                break;
        }
        return iss.get("SELECT * FROM  i_r AS ir "
                + "JOIN folders_irs_relation AS fir "
                + "ON ir.id = fir.i_r_id "
                + "JOIN folders AS f "
                + "ON fir.folder_id = f.id "
                + "WHERE "+key+" LIKE '%"+ref+"%';");

    }
    
    public ResultSet SearchSp(String key, String ref){
        switch(key){
            case "Réference" :
                key = "ir.referencee";
                break;
            case "Unité" :
                key = "ir.unit";
                break;
        }
        return iss.get("SELECT * FROM  i_r AS ir "
                + "JOIN folders_irs_relation AS fir "
                + "ON ir.id = fir.i_r_id "
                + "JOIN folders AS f "
                + "ON fir.folder_id = f.id "
                + "WHERE "+key+" = '"+ref+"';");

    }
}
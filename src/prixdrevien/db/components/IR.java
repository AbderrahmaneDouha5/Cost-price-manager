
package prixdrevien.db.components;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
    
 
    
    
    
    public void createIR(int  id, String ref, String des,  
            String date,  String unit,double s_amount, 
            double amount_on_s, double unit_price,
            double taux, int four_id){
        
        
        iss.create("INSERT INTO i_r(referencee, descriptionne, unit) "
                + "VALUES( '"+ref+"', '"+des+"', '"+unit+"' );");
        createIRAchat(id, iss.getLastId("i_r"), s_amount,  amount_on_s,
                unit_price, taux,  date, four_id);
        
    }
    
    
    public void createIRAchat(int id, int ir,
            double s_amount, double amount_on_s, double unit_price,
            double taux, String date, int four_id){
            double total = unit_price*amount_on_s*s_amount*taux;
        
        try{
            rs = folder.Search("Id",id);
            
            while(rs.next()) total = total + rs.getDouble("f.total") ;
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            maindb.closeConnection(rs);
        }
        
        iss.create("INSERT INTO ir_register_dachats(folder_id, i_r_id, "
                + "datetim,  s_amount, "
                + "amount_on_s, amount, unit_priceda, unit_price, taux, total, "
                + "fournisseur_id) "
                + "VALUES("+id+", "+ir+", '"+date+"', "
                +s_amount+", "+amount_on_s+", "
                +amount_on_s*s_amount+", "+unit_price*taux+", "+unit_price+", "
                +taux+", "+total+", "+four_id+");");
    }
    
    
    
    
    
    public ResultSet getAll(){
        return iss.get("SELECT * FROM  ir_register_dachats AS fir "
                + "JOIN i_r AS ir "
                + "ON ir.id = fir.i_r_id "
                + "JOIN folders AS f "
                + "ON fir.folder_id = f.id "
                + "JOIN fournisseurs AS four "
                + "ON four.id = fir.fournisseur_id "
                + ";");

    }
    
    public ResultSet getAllDesc(){
        return iss.get("SELECT * FROM  ir_register_dachats AS fir "
                + "JOIN i_r AS ir "
                + "ON ir.id = fir.i_r_id "
                + "JOIN folders AS f "
                + "ON fir.folder_id = f.id "
                + "JOIN fournisseurs AS four "
                + "ON four.id = fir.fournisseur_id "
                + "ORDER BY fir.datetim DESC;");

    }
    
    public ResultSet Search(String key,int id){
        switch(key){
            case "Id":
                key = "ir.id";
                break;
        }
        return iss.get("SELECT * FROM  i_r AS ir "
                + "JOIN ir_register_dachats AS fir "
                + "ON ir.id = fir.i_r_id "
                + "JOIN folders AS f "
                + "ON fir.folder_id = f.id "
                + "JOIN fournisseurs AS four "
                + "ON four.id = fir.fournisseur_id "
                + "WHERE "+key+" = "+id+" "
                + ";");

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
                + "JOIN ir_register_dachats AS fir "
                + "ON ir.id = fir.i_r_id "
                + "JOIN folders AS f "
                + "ON fir.folder_id = f.id "
                + "JOIN fournisseurs AS four "
                + "ON four.id = fir.fournisseur_id "
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
                + "JOIN ir_register_dachats AS fir "
                + "ON ir.id = fir.i_r_id "
                + "JOIN folders AS f "
                + "ON fir.folder_id = f.id "
                + "JOIN fournisseurs AS four "
                + "ON four.id = fir.fournisseur_id"
                + "WHERE "+key+" = '"+ref+"';");

    }
    
    
    public void updateTotal(int folder_id){
        rs = Search("f.id", folder_id);
        double folder_total = 0.0;
        List<Integer> ids = new ArrayList<>();
        try{
            while(rs.next()){
                ids.add(rs.getInt("fir.id"));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            maindb.closeConnection(rs);
        }
        
        try{
            rs = folder.Search("Id", folder_id);
            while(rs.next()){
                folder_total = rs.getDouble("total");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            maindb.closeConnection(rs);
        }
        
        for(int i =0 ;i<ids.size();i++){
            double amount = getTotal(ids.get(i)).get(0);
            double unit_priceda = getTotal(ids.get(i)).get(1);
            
            iss.update("UPDATE ir_register_dachats "
                    + "SET total = "+(amount*unit_priceda+folder_total)+" "
                    + "WHERE id = "+ids.get(i)+" AND folder_id = "+folder_id+";");
        }
    }
    
    
    
    public List<Double> getTotal(int id){
        rs = Search("fir.id", id);
        List<Double> result = new ArrayList<>();
        try{
            while(rs.next()){
                result.add(rs.getDouble("fir.amount"));
                result.add(rs.getDouble("fir.unit_priceda"));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            maindb.closeConnection(rs);
        }
        return result;
    }
    
    
}
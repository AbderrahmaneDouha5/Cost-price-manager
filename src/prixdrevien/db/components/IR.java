
package prixdrevien.db.components;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import prixdrevien.db.issentials;
import prixdrevien.db.maindb;


public class IR {
    
    private String db;
    public  Connection conn = null;
    public PreparedStatement ps = null;
    public ResultSet rs = null;
    
    
    public  IR(String db){
        this.db = db;
    }
    
 
    
    
    
    public void createIR(int folder, String ref, String des,
            double s_amount, double amount_on_s, double amount, double unit_price){
        issentials.create("INSERT INTO i_r(referncee, descriptionn, s_amount, "
                + "amount_on_s, amount, unit_price)"
                + "VALUES(referencee = '"+ref+"', descriptionn = '"+des+"', "
                + "s_amount = "+s_amount+", amount_on_s = "+amount_on_s+", "
                + "amount = "+amount+", unit_price = "+unit_price+");");
        createIRFolderRelation(folder, issentials.getLastId("i_r"));
        
    }
    
    
    private void createIRFolderRelation(int folder, int ir){
        issentials.create("INSERT INTO folders_irs_relation( "
                + "folder_id = "+folder+", i_r_id = "+ir+");");
    }
    
    
}
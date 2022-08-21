
package prixdrevien.db.components;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import prixdrevien.db.issentials;
import prixdrevien.db.maindb;


public class Employee {
    
    private Folder folder ;
    private String db;
    public  Connection conn = null;
    public PreparedStatement ps = null;
    public ResultSet rs = null;
    private issentials iss;
    
    public  Employee(String db){
        this.db = db;
        iss = new issentials(db);
        folder = new Folder(db);
    }
    
    public void createEmployee( String nom, String post,  
            String number,  String date,double salary ){
        
        
        iss.create("INSERT INTO employee(nom, phone_number, post, begining_date, salary) "
                + "VALUES( '"+nom+"', '"+post+"', '"+number+"', '"+date+"', "+salary+" );");
        
        
    }
    
    public ResultSet getAll(){
        return iss.get("SELECT * FROM  employee ");

    }
    
    
    
    public ResultSet Search(String key,int id){
        switch(key){
            case "Id":
                key = "id";
                break;
        }
        return iss.get("SELECT * FROM  employee "
                + "WHERE "+key+" = "+id+";");

    }
    
    
    public ResultSet Search(String key ,String nom){
        switch(key){
            case "Nom" :
                key = "nom";
                break;
            
        }
        return iss.get("SELECT * FROM  employee "
                + "WHERE "+key+" LIKE '%"+nom+"%' ;");

    }
    
    public ResultSet SearchSp(String key, String nom){
        switch(key){
            case "Nom" :
                key = "nom";
                break;
            
        }
        return iss.get("SELECT * FROM  employee "
                + "WHERE "+key+" = '"+nom+"' ");

    }
    
    public  double getEmployeeTotal(){
        rs = iss.get("SELECT SUM(salary) AS total FROM employee;");
        double total = 0.0;
        try{
            while(rs.next()){
                total = rs.getDouble("total");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
           maindb.closeConnection(rs);
       }
        return total;
    }
    public  int getEmployeeCount(){
        rs = iss.get("SELECT COUNT(id) AS count FROM employee;");
        int count = 0;
        try{
            while(rs.next()){
                count = rs.getInt("count");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
           maindb.closeConnection(rs);
       }
        return count;
    }
}


package prixdrevien.db.components;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import prixdrevien.db.issentials;
import prixdrevien.db.maindb;


public class Folder {
    
    
    private String db;
    public  Connection conn = null;
    public PreparedStatement ps = null;
    public ResultSet rs = null;
    private issentials iss;
    public Folder(String db){
        this.db = db;
        iss = new issentials(db);
        
    }
   
    
    
    
    
    private void createExternalTransportCosts(){
        iss.create("INSERT INTO transport_external() "
                    + "VALUES();");
    }
    
    private void createInternalTransportCosts(){
        iss.create("INSERT INTO transport_internal() "
                    + "VALUES();");
    }
    
    
   
    
    private void createTransportCosts(){
        int internalId = iss.getLastId("transport_internal");
        int externalId = iss.getLastId("transport_external");
            
        iss.create("INSERT INTO transport_costs(external_id, internal_id) "
                    + "VALUES("+externalId+", "+internalId+");")  ; 
    }
    
    private void createTransitionCosts(){
        iss.create("INSERT INTO transition_costs() "
                    + "VALUES();");
    }
    
    private void createBancCosts(){
        iss.create("INSERT INTO banc_costs() "
                    + "VALUES();");
    }
    
    private void createCustomsCosts(){
        iss.create("INSERT INTO customs_costs() "
                    + "VALUES();");
    }
    
    private void createVariableCosts(){
        iss.create("INSERT INTO variable_costs() "
                    + "VALUES();");
    }
  
    private void createCosts(){
        createExternalTransportCosts();
        createInternalTransportCosts();
        createTransportCosts();
        createBancCosts();
        createCustomsCosts();
        createTransitionCosts();
        createVariableCosts();
    }
    
    public void createFolder(String billNumber, String banc, String port, String date){
        createCosts();
        maindb.closeConnection(maindb.conn, ps, rs);
        iss.create("INSERT INTO folders(n_facture, banc, porte, datetim,  "
                    + "transport_id, transit_id, banc_id, customs_id, variable_id) "+"VALUES('"+billNumber+"', '"+banc+"', "
                    + "'"+port+"','"+date+"', '"+iss.getLastId("transport_costs")+"', '"+iss.getLastId("transition_costs")+"', '"
                    +  iss.getLastId("banc_costs")+"', '"+iss.getLastId("customs_costs")+"', '"
                    +  iss.getLastId("variable_costs")+"');");
    }
    
 //=============================================================================
 //=============================================================================
    
    
    
    
    
    public ResultSet Search( String key, String researched){
        
        switch(key){
            case "Port" :
                key = "f.porte";
                break;
            case "N.Facture":
                key = "f.n_facture";
                break;
            case "Banc" : 
                key = "f.banc";
                break;
        }
        return iss.get("SELECT * FROM folders AS f " +
                    "JOIN transport_costs AS ttc " +
                    "ON ttc.id = f.transport_id " +
                    "JOIN transport_external AS tx " +
                    "ON tx.id = ttc.external_id " +
                    "JOIN transport_internal AS ti " +
                    "ON ti.id = ttc.internal_id " +
                    "JOIN transition_costs AS tc " +
                    "ON tc.id = f.transit_id " +
                    "JOIN banc_costs AS bc " +
                    "ON bc.id = f.banc_id " +
                    "JOIN customs_costs AS cc " +
                    "ON cc.id = f.customs_id " +
                    "JOIN variable_costs AS vc " +
                    "ON vc.id = f.variable_id "
                    + "WHERE "+key+" LIKE '%"+researched+"%';");
    }
    
    public ResultSet SearchSp( String key, String researched){
        switch(key){
            case "Port" :
                key = "f.porte";
                break;
            case "N.Facture" :
                key = "f.n_facture";
                break;
            case "Banc" :
                key = "f.banc";
                break;
        }
        return iss.get("SELECT * FROM folders AS f " +
                    "JOIN transport_costs AS ttc " +
                    "ON ttc.id = f.transport_id " +
                    "JOIN transport_external AS tx " +
                    "ON tx.id = ttc.external_id " +
                    "JOIN transport_internal AS ti " +
                    "ON ti.id = ttc.internal_id " +
                    "JOIN transition_costs AS tc " +
                    "ON tc.id = f.transit_id " +
                    "JOIN banc_costs AS bc " +
                    "ON bc.id = f.banc_id " +
                    "JOIN customs_costs AS cc " +
                    "ON cc.id = f.customs_id " +
                    "JOIN variable_costs AS vc " +
                    "ON vc.id = f.variable_id "
                    + "WHERE "+key+" = '"+researched+"';");
    } 
    
    public ResultSet Search( String key, int researched){
        switch(key){
            case "Id" :
                key = "f.id";
                break;
        }
        
        return iss.get("SELECT * FROM folders AS f " +
                    "JOIN transport_costs AS ttc " +
                    "ON ttc.id = f.transport_id " +
                    "JOIN transport_external AS tx " +
                    "ON tx.id = ttc.external_id " +
                    "JOIN transport_internal AS ti " +
                    "ON ti.id = ttc.internal_id " +
                    "JOIN transition_costs AS tc " +
                    "ON tc.id = f.transit_id " +
                    "JOIN banc_costs AS bc " +
                    "ON bc.id = f.banc_id " +
                    "JOIN customs_costs AS cc " +
                    "ON cc.id = f.customs_id " +
                    "JOIN variable_costs AS vc " +
                    "ON vc.id = f.variable_id "
                   + "WHERE "+key+" = "+researched+";");
    }
    
    
    
    public ResultSet getAll(){
        return iss.get("SELECT * FROM folders AS f " +
                    "JOIN transport_costs AS ttc " +
                    "ON ttc.id = f.transport_id " +
                    "JOIN transport_external AS tx " +
                    "ON tx.id = ttc.external_id " +
                    "JOIN transport_internal AS ti " +
                    "ON ti.id = ttc.internal_id " +
                    "JOIN transition_costs AS tc " +
                    "ON tc.id = f.transit_id " +
                    "JOIN banc_costs AS bc " +
                    "ON bc.id = f.banc_id " +
                    "JOIN customs_costs AS cc " +
                    "ON cc.id = f.customs_id " +
                    "JOIN variable_costs AS vc " +
                    "ON vc.id = f.variable_id ;");
    }
    
 //=============================================================================
 //=============================================================================
    
    public void updateVariableCost(int id, double m, double c, 
            double e, double f, double a, double v){
        double t = m+c+e+f+a+v;
        iss.update("UPDATE variable_costs "
                + "SET frais_mission = "+m+", "
                + "clarque = "+c+", "
                + "servier = "+e+", "
                + "repat = "+f+", "
                + "acts = "+a+", "
                + "variable = "+v+", "
                + "total = "+t+" "
                + "WHERE id = "+id+";");
    }
    
    public void updateTotal(int id){
        double total  = 0;
        double variableTotal = 0;
       double bancTotal = 0;
       double customsTotal = 0;
       double transportTotal = 0;
       double transitionTotal = 0;
        try{
            ResultSet rs = getAll();
            while(rs.next()){
                total = rs.getDouble("f.total");
                variableTotal = rs.getDouble("vc.total");
                bancTotal = rs.getDouble("bc.total");
                customsTotal = rs.getDouble("cc.ctotal");
                transportTotal = rs.getDouble("ttc.total");
                transitionTotal = rs.getDouble("tc.total");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        maindb.closeConnection(conn, ps, rs);
        total = variableTotal+bancTotal+customsTotal+transportTotal+transitionTotal;
        iss.update("UPDATE folders "
                + "SET total = "+total+" "
                + "WHERE id = "+id+";");
        
    }

 
    public void updateBancCost(int id, double rd, double sc,
            double dc, double dd1, double dd2, double dd3,
            double cc){
        double total = rd+sc+dc+dd1+dd2+dd3+cc;
        
        iss.update("UPDATE banc_costs "
                + "SET reglement_def = "+rd+", "
                + "frais_swift = "+sc+", "
                + "frais_disblocage = "+dc+", "
                + "domic_document1 = "+dd1+", "
                + "domic_document2 = "+dd2+", "
                + "domic_document3 = "+dd3+", "
                + "frais_corresponant = "+cc+", "
                + "total = "+total+" "
                + "WHERE id = "+id+";");
        
    }
  
    public void updateTransitionCost(int id, double tc,double ec,
            double epa, double sil, double pc, double vc){
        double total = tc+epa+sil+pc+vc;
        
        iss.update("UPDATE transition_costs " +
                "SET transition_costs =" +
                tc+", servier = "+
                ec+", epa = "+epa+", sil = "+
                sil+", parc = "+pc+", variable = "+
                vc+", total = "+total+" "+
                "WHERE id = "+id+";");
        
    }
    
    public void updateCustomCost(int id, double tcs,double tva,
            double dc, double qc, double cxc){
        double total = tcs+tva+dc+qc;
        double CCtotal = total+cxc;
        
        iss.update("UPDATE Customs_costs " +
                "SET tcs =" +
                tcs+", tva = "+
                tva+", d_douane = "+dc+", q_douane = "+
                qc+", cx = "+cxc+", total = "+
                total+", ctotal = "+CCtotal+" "+
                "WHERE id = "+id+";");
        
    }
    
    public void updateETransport(int id, double eet,double set,
            double tet, double taux){
        double total = eet+set+(tet*taux);
       
        
        iss.update("UPDATE transport_external " +
                "SET echange =" +
                eet+", surstarie = "+
                set+", transport = "+tet+", taux_change = "+
                taux+", total = "+
                total+" "+
                "WHERE id = "+id+";");
        
    }
    
    public void updateITransport(int id, double it,double ic,
            double iv){
        double total = it+ic+iv;
       
        
        iss.update("UPDATE transport_internal " +
                "SET transport =" +
                it+", couler = "+
                ic+", vehicle_service = "+iv+", total = "+
                total+" "+
                "WHERE id = "+id+";");
        
    }
    
    public void updateTransport(int id){
        Double t1 = 0.0;
        Double t2 = 0.0;
        
        int e_id = getCostId(id).get(5);
        try{
            ResultSet rs = Search("external_id",e_id);
            while(rs.next()){
                t1 = rs.getDouble("tx.total");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        int i_id = getCostId(id).get(6);
        try{
            ResultSet rs = Search("internal_id",i_id);
            while(rs.next()){
                t2 = rs.getDouble("ti.total");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        Double total = t1+t2;
        
        iss.update("UPDATE transport_costs " +
                "SET external_total =" +
                t1+", internal_total = "+
                t2+", total = "+
                total+" "+
                "WHERE id = "+id+";");
        
    }
    
    public List<Integer> getCostId(int id){
        // 0 -> variableCost
        // 1 -> bancCost
        // 2 -> customsCost
        // 3 -> transportCost
        // 4 -> transitionCost
        // 5 -> externalTransport   
        // 6 -> internalTransport
        
       // @param id -> folder id
       // @return result -> list of all their costs foreign keys
        List<Integer> result = new ArrayList<>();
        try{
            ResultSet rs = Search("Id",id);
            while(rs.next()){
                result.add(rs.getInt("f.variable_id"));
                result.add(rs.getInt("f.banc_id"));
                result.add(rs.getInt("f.customs_id"));
                result.add(rs.getInt("f.transport_id"));
                result.add(rs.getInt("f.transit_id"));
                result.add(rs.getInt("ttc.external_id"));
                result.add(rs.getInt("ttc.internal_id"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
           maindb.closeConnection(maindb.conn, ps, rs);
       }
        return result;
    }
    
    
    public int getFolderId(String n_facture){
        ResultSet rs = SearchSp("N.Facture",n_facture);
        int result = -1;
        try{
            while(rs.next()){
                result = rs.getInt("f.id");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
           maindb.closeConnection(maindb.conn, ps, rs);
       }
        return result;
    }
} 


package prixdrevien.main.pages;

import javax.swing.table.TableColumn;
import prixdrevien.db.components.Folder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import prixdrevien.db.components.Employee;
import prixdrevien.db.components.Fournisseur;
import prixdrevien.db.components.IR;
import prixdrevien.db.maindb;

public class initialResources extends javax.swing.JPanel {
    Folder folder ;
    IR ir;
    ResultSet rs;
    Fournisseur fournisseur;
    int folderSelectedRow;
    int irSelectedRow;
    Employee employee;
    
    public initialResources(String db) {
        fournisseur = new Fournisseur(db);
        folder = new Folder(db);
        ir = new IR(db);
        employee = new Employee(db);
        
        initComponents();
        myInit();
        
    }

    private void myInit(){
        resizeTable(foldersTable,11,1490,new int[][] {
            new int[]{0,50}
        });
        resizeTable(fourTable,4,1490,new int[][] {
            new int[]{0,50}
        });
        resizeTable(irTable,12,1490,new int[][]{
            new int[]{0,50},
            new int[]{8,50},
            new int[]{9,100},
            new int[]{10,100}
        });
        resizeTable(irFoldersTable,10,1240,new int[][]{
            new int[]{0,50},
            new int[]{7,100},
            new int[]{8,100}
 
        });
        resizeTable(employeeTable,6,1490,new int[][] {
            new int[]{0,50}
        });
        renderFoldersTable(folder.getAll());
        renderIRTable(ir.getAllDesc());
        renderFourTable(fournisseur.getAll());
        renderEmployeeTable(employee.getAll());
    }
    
    private void resizeTable(javax.swing.JTable table, int columnNumber, int width, int[][] min){
        TableColumn column = null;
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < min.length; i++) {
            column = table.getColumnModel().getColumn(min[i][0]);
            column.setMaxWidth(min[i][1]); 
            set.add(min[i][0]); 
        }
        for(int i = 0; i<columnNumber;i++){
            column = table.getColumnModel().getColumn(i);
            if(!set.contains(i)){
                column.setMaxWidth(( width - (min.length + 1) * 50) / (columnNumber - (min.length + 1) )); 
            }
        }
    }
    
    private void renderIRTable(ResultSet rs){
       String ref;
       int id;
       double total;
       String date;
       String des;
       double sq;
       double qos;
       double q;
       double pu;
       double taux;
       String unit;
       double puda;
       String four;
       
       DefaultTableModel model =   (DefaultTableModel) irTable.getModel();
       model.setNumRows(0);
       HashSet<Integer> set = new HashSet<>();
       
       try{
           while(rs.next()){
               
               id = rs.getInt("ir.id");
               ref = rs.getString("ir.referencee");
               date = rs.getString("fir.datetim");
               total = rs.getDouble("fir.total");
               des = rs.getString("ir.descriptionne");
               sq = rs.getDouble("fir.s_amount");
               qos = rs.getDouble("fir.amount_on_s");
               q = rs.getDouble("fir.amount");
               pu = rs.getDouble("fir.unit_price");
               taux = rs.getDouble("fir.taux");
               unit = rs.getString("ir.unit");
               puda = rs.getDouble("fir.unit_priceda");
               four = rs.getString("four.nom");
               if(!set.contains(id)){
                    String[] row = { String.valueOf(id), ref, des, date,  four, String.valueOf(sq)
                        , String.valueOf(qos), String.valueOf(q), unit, String.valueOf(pu) 
                        ,String.valueOf(puda) ,String.valueOf(total)};
                    model.addRow(row);
                    set.add(id);
               }
              
               
               
              
           }
       }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
           maindb.closeConnection(rs);
       }
       irTable.setModel(model);
    }
    
    private void renderEmployeeTable(ResultSet rs){
       String name;
       int id;
       String post;
       String number;
       double salary;
       String date;
       
       DefaultTableModel model =   (DefaultTableModel) employeeTable.getModel();
       model.setNumRows(0);
       
       
       try{
           while(rs.next()){
               
               id = rs.getInt("id");
               name = rs.getString("nom");
               post = rs.getString("post");
               salary = rs.getDouble("salary");
               number = rs.getString("phone_number");
               date = rs.getString("begining_date");
               String[] row = {String.valueOf(id), 
               name,
               number, 
               date,
               post, 
               String.valueOf(salary) };
               
               model.addRow(row);
              
               
               
              
           }
       }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
           maindb.closeConnection(rs);
       }
       employeeTable.setModel(model);
       totalEmployeeField.setText(String.valueOf(employee.getEmployeeTotal()));
       countEmployeeField.setText(String.valueOf(employee.getEmployeeCount()));
    }
    
    
    private void renderFoldersTable(ResultSet rs){
        String billNumber;
       int id;
       double total;
       String date;
       String port;
       String banc;
       double variableTotal;
       double bancTotal;
       double customsTotal;
       double transportTotal;
       double transitionTotal;

       DefaultTableModel model =   (DefaultTableModel) foldersTable.getModel();
       model.setNumRows(0);
       
       try{
           while(rs.next()){
               id = rs.getInt("f.id");
               billNumber = rs.getString("f.n_facture");
               date = rs.getString("f.datetim");
               total = rs.getDouble("f.total");
               banc = rs.getString("f.banc");
               port = rs.getString("f.porte");
               variableTotal = rs.getDouble("vc.total");
               bancTotal = rs.getDouble("bc.total");
               customsTotal = rs.getDouble("cc.total");
               transportTotal = rs.getDouble("ttc.total");
               transitionTotal = rs.getDouble("tc.total");

               String[] row = { String.valueOf(id), billNumber,banc, port, date, String.valueOf(bancTotal), String.valueOf(customsTotal)
                       , String.valueOf(transportTotal), String.valueOf(transitionTotal), String.valueOf(variableTotal),  String.valueOf(total) };
               model.addRow(row);
              
           }
       }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
           maindb.closeConnection(rs);
       }
       foldersTable.setModel(model);
    }
    
    private void renderIRFoldersTable(ResultSet rs){
       
       int id;
       double total;
       String date;
       String n_facture;
       double sq;
       double qos;
       double q;
       double pu;
       double taux;
       String unit;
       double puda;
       String four;
       
       DefaultTableModel model =   (DefaultTableModel) irFoldersTable.getModel();
       model.setNumRows(0);
       
       try{
           while(rs.next()){
               
               id = rs.getInt("ir.id");
               
               date = rs.getString("fir.datetim");
               total = rs.getDouble("fir.total");
               n_facture = rs.getString("f.n_facture");
               sq = rs.getDouble("fir.s_amount");
               qos = rs.getDouble("fir.amount_on_s");
               q = rs.getDouble("fir.amount");
               pu = rs.getDouble("fir.unit_price");
               taux = rs.getDouble("fir.taux");
               unit = rs.getString("ir.unit");
               puda = rs.getDouble("fir.unit_priceda");
               four = rs.getString("four.nom");
              

               String[] row = { String.valueOf(id), n_facture, date, four, String.valueOf(sq)
                       , String.valueOf(qos), String.valueOf(q),  String.valueOf(pu) ,String.valueOf(puda) ,
                       String.valueOf(total)};
               model.addRow(row);
              
           }
       }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
           maindb.closeConnection(rs);
       }
       irFoldersTable.setModel(model);
    }
    
    
    private void renderFourTable(ResultSet rs){
       String nom;
       String pay;
       String comp;
       int id;
       DefaultTableModel model =   (DefaultTableModel) fourTable.getModel();
       model.setNumRows(0);
       
       try{
           while(rs.next()){
               id = rs.getInt("id");
               nom = rs.getString("nom");
               pay = rs.getString("pay");
               comp = rs.getString("compagnie");
               
               
              

               String[] row = { String.valueOf(id) , nom, comp, pay};
               model.addRow(row);
              
           }
       }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
           maindb.closeConnection(rs);
       }
       fourTable.setModel(model);
    }
    
    
    private void renderVariableCosts(int id){
        // Variable Costs
        Double MissionCostVariable = 0.0;
        Double clarqueCostVariable = 0.0;
        Double employeeCostVariable = 0.0;
        Double foodCostVariable = 0.0;
        Double actsCostVariable = 0.0;
        Double variableCostVariable = 0.0;
        Double totalVariable = 0.0;
        try{
            ResultSet rs = folder.Search("Id",id);
            while(rs.next()){
                // Variable Costs
                MissionCostVariable = rs.getDouble("vc.frais_mission");
                clarqueCostVariable = rs.getDouble("vc.clarque");
                employeeCostVariable = rs.getDouble("vc.servier") ;
                foodCostVariable = rs.getDouble("vc.repat");
                actsCostVariable = rs.getDouble("vc.acts");
                variableCostVariable = rs.getDouble("vc.variable");
                totalVariable = rs.getDouble("vc.total");
                
            }
            maindb.closeConnection(rs);
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        MissionCostVariableField.setText(MissionCostVariable.toString());
        clarqueCostVariableField.setText(clarqueCostVariable.toString());
        employeeCostVariableField.setText(employeeCostVariable.toString());
        foodCostVariableField.setText(foodCostVariable.toString());
        actsCostVariableField.setText(actsCostVariable.toString());
        variableCostVariableField.setText(variableCostVariable.toString());
        totalVariableField.setText(totalVariable.toString());
    }
   
    
    private void renderBancCosts(int id){
        // Variable Costs
        Double rd = 0.0;
        Double sc = 0.0;
        Double dc = 0.0;
        Double dd1 = 0.0;
        Double dd2 = 0.0;
        Double dd3 = 0.0;
        Double cc = 0.0;
        Double total = 0.0;
        try{
            rs = folder.Search("Id",id);
            while(rs.next()){
                // Variable Costs
                rd = rs.getDouble("bc.reglement_def");
                sc = rs.getDouble("bc.frais_swift");
                dc = rs.getDouble("bc.frais_disblocage") ;
                dd1 = rs.getDouble("bc.domic_document1");
                dd2 = rs.getDouble("bc.domic_document2");
                dd3 = rs.getDouble("bc.domic_document3");
                cc = rs.getDouble("bc.frais_corresponant");
                total = rs.getDouble("bc.total");
                
            }
            maindb.closeConnection(rs);
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        RDBancField.setText(rd.toString());
        SCBancField.setText(sc.toString());
        DCBancField.setText(dc.toString());
        DD1BancField.setText(dd1.toString());
        DD2BancField.setText(dd2.toString());
        DD3BancField.setText(dd3.toString());
        CCBancField.setText(cc.toString());
        totalBancField.setText(total.toString());
    }
    
    private void renderTransitionCosts(int id){
        // Variable Costs
        Double tc = 0.0;
        Double ec = 0.0;
        Double epa = 0.0;
        Double sil = 0.0;
        Double pc = 0.0;
        Double vc = 0.0;
        Double total = 0.0;
        try{
            rs = folder.Search("Id",id);
            while(rs.next()){
                // Variable Costs
                tc = rs.getDouble("tc.transition_costs");
                ec = rs.getDouble("tc.servier");
                epa = rs.getDouble("tc.epa") ;
                sil = rs.getDouble("tc.sil");
                pc = rs.getDouble("tc.parc");
                vc = rs.getDouble("tc.variable");
                total = rs.getDouble("tc.total");
                
            }
            maindb.closeConnection(rs);
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        TCTransitionField.setText(tc.toString());
        ECTransitionField.setText(ec.toString());
        EPACTransitionField.setText(epa.toString());
        SILCTransitionField.setText(sil.toString());
        PCTransitionField.setText(pc.toString());
        VCTransitionField.setText(vc.toString());
        totalTransitionField.setText(total.toString());
    }
    
    private void renderCustomCosts(int id){
        // Variable Costs
        Double tcs = 0.0;
        Double tva = 0.0;
        Double qc = 0.0;
        Double dc = 0.0;
        Double cxc = 0.0;
        Double total = 0.0;
        Double CCtotal = 0.0;
        try{
            rs = folder.Search("Id",id);
            while(rs.next()){
                // Variable Costs
                tcs = rs.getDouble("cc.tcs");
                tva = rs.getDouble("cc.tva");
                qc = rs.getDouble("cc.q_douane") ;
                dc = rs.getDouble("cc.d_douane");
                cxc = rs.getDouble("cc.cx");
                total = rs.getDouble("cc.total");
                CCtotal = rs.getDouble("cc.ctotal");
                
            }
            maindb.closeConnection(rs);
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        TCSCustomField.setText(tcs.toString());
        TVACustomField.setText(tva.toString());
        DCustomField.setText(dc.toString());
        QCustomField.setText(qc.toString());
        CXCustomfield.setText(cxc.toString());
        totalCustomField.setText(total.toString());
        totalCXCustomField.setText(CCtotal.toString());
    }
    
    private void renderExternalTransport(int id){
        // Variable Costs
        Double eet = 0.0;
        Double set = 0.0;
        Double tet = 0.0;
        Double taux = 0.0;
        Double total = 0.0;
        
        try{
            rs = folder.Search("external_id",id);
            while(rs.next()){
                // Variable Costs
                eet = rs.getDouble("tx.echange");
                set = rs.getDouble("tx.surstarie");
                tet = rs.getDouble("tx.transport") ;
                taux = rs.getDouble("tx.taux_change");
                total = rs.getDouble("tx.total");
                
                
            }
            maindb.closeConnection(rs);
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        EETrasportField.setText(eet.toString());
        SETransportField.setText(set.toString());
        TETransportField.setText(tet.toString());
        TauxETransportField.setText(taux.toString());
        totalETransportField.setText(total.toString());
    }
    
    private void renderInternalTransport(int id){
        // Variable Costs
        Double it = 0.0;
        Double ic = 0.0;
        Double iv = 0.0;
        Double total = 0.0;
        
        try{
            rs = folder.Search("external_id",id);
            while(rs.next()){
                // Variable Costs
                it = rs.getDouble("ti.transport");
                ic = rs.getDouble("ti.couler");
                iv = rs.getDouble("ti.vehicle_service") ;
                total = rs.getDouble("ti.total");
                
                
            }
            maindb.closeConnection(rs);
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        ITTransportField.setText(it.toString());
        ICTransportField.setText(ic.toString());
        IVTransportField.setText(iv.toString());
        totalITransportField.setText(total.toString());
        
    }
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addFolderDialog = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel215 = new javax.swing.JLabel();
        billNumberField = new javax.swing.JTextField();
        bancField = new javax.swing.JTextField();
        jLabel216 = new javax.swing.JLabel();
        portField = new javax.swing.JTextField();
        jLabel217 = new javax.swing.JLabel();
        kButton5 = new com.k33ptoo.components.KButton();
        kButton6 = new com.k33ptoo.components.KButton();
        jLabel218 = new javax.swing.JLabel();
        addFolderErrorLabel = new javax.swing.JLabel();
        addIRDialog = new javax.swing.JDialog();
        jPanel29 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        addIRPriceField = new javax.swing.JTextField();
        kButton10 = new com.k33ptoo.components.KButton();
        addIRRefField = new javax.swing.JTextField();
        addIRSQField = new javax.swing.JTextField();
        addIRFolderField = new javax.swing.JTextField();
        addIRQOSField = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        addIRQField = new javax.swing.JTextField();
        kButton7 = new com.k33ptoo.components.KButton();
        jSeparator4 = new javax.swing.JSeparator();
        addIRTauxField = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        addIRDesField = new javax.swing.JTextArea();
        jLabel34 = new javax.swing.JLabel();
        addIRErrorLabel = new javax.swing.JLabel();
        addIRuntCombo = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        billNumberList = new javax.swing.JList<>();
        jScrollPane10 = new javax.swing.JScrollPane();
        fourList = new javax.swing.JList<>();
        addIRFourField = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        irFoldersDialog = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        irFoldersSearchField = new javax.swing.JTextField();
        irFoldersSearchCombo = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        kButton28 = new com.k33ptoo.components.KButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        irFoldersTable = new javax.swing.JTable();
        buyIRDialog = new javax.swing.JDialog();
        jPanel11 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        buyIRPriceField = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        buyIRSQField = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        buyIRQOSField = new javax.swing.JTextField();
        buyIRQField = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        buyIRTauxField = new javax.swing.JTextField();
        buyIRFolderField = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        kButton8 = new com.k33ptoo.components.KButton();
        kButton11 = new com.k33ptoo.components.KButton();
        buyIRFourField = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        buyBillNumberList = new javax.swing.JList<>();
        jSeparator2 = new javax.swing.JSeparator();
        buyIRErrorLabel = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        buyFourList = new javax.swing.JList<>();
        addFournisseurDialog = new javax.swing.JDialog();
        jPanel22 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        addFournisseurNomField = new javax.swing.JTextField();
        addFournisseurComagnieField = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        addFournisseurPayField = new javax.swing.JTextField();
        jLabel220 = new javax.swing.JLabel();
        kButton12 = new com.k33ptoo.components.KButton();
        kButton22 = new com.k33ptoo.components.KButton();
        jSeparator8 = new javax.swing.JSeparator();
        addEmployeeDialog = new javax.swing.JDialog();
        jPanel23 = new javax.swing.JPanel();
        jLabel221 = new javax.swing.JLabel();
        jLabel222 = new javax.swing.JLabel();
        nameEmployeeField = new javax.swing.JTextField();
        postEmployeeField = new javax.swing.JTextField();
        jLabel223 = new javax.swing.JLabel();
        jLabel224 = new javax.swing.JLabel();
        numberEmployeeField = new javax.swing.JTextField();
        kButton23 = new com.k33ptoo.components.KButton();
        kButton27 = new com.k33ptoo.components.KButton();
        salaryEmployeeField = new javax.swing.JTextField();
        jLabel225 = new javax.swing.JLabel();
        addEmployeeErrorLabel = new javax.swing.JLabel();
        monthEmployeeCombo = new javax.swing.JComboBox<>();
        yearEmployeeCombo = new javax.swing.JComboBox<>();
        dayEmployeeCombo = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        contentTabbedPane = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        irSearchField = new javax.swing.JTextField();
        irSearchCombo = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        kButton4 = new com.k33ptoo.components.KButton();
        jPanel17 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        irTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        fourTable = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        fourSearchField = new javax.swing.JTextField();
        fourSearchCombo = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        kButton9 = new com.k33ptoo.components.KButton();
        jPanel19 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel36 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel37 = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        foldersTable = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        foldersSearchField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel191 = new javax.swing.JLabel();
        foldersSearchCombo = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        kButton1 = new com.k33ptoo.components.KButton();
        jPanel41 = new javax.swing.JPanel();
        jPanel65 = new javax.swing.JPanel();
        jLabel192 = new javax.swing.JLabel();
        CCBancField = new javax.swing.JTextField();
        DD3BancField = new javax.swing.JTextField();
        DD2BancField = new javax.swing.JTextField();
        jLabel193 = new javax.swing.JLabel();
        jLabel194 = new javax.swing.JLabel();
        jLabel195 = new javax.swing.JLabel();
        DCBancField = new javax.swing.JTextField();
        jLabel196 = new javax.swing.JLabel();
        jLabel197 = new javax.swing.JLabel();
        DD1BancField = new javax.swing.JTextField();
        SCBancField = new javax.swing.JTextField();
        jLabel198 = new javax.swing.JLabel();
        jLabel199 = new javax.swing.JLabel();
        RDBancField = new javax.swing.JTextField();
        jLabel200 = new javax.swing.JLabel();
        jSeparator24 = new javax.swing.JSeparator();
        totalBancField = new javax.swing.JTextField();
        kButton24 = new com.k33ptoo.components.KButton();
        kButton25 = new com.k33ptoo.components.KButton();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        jLabel136 = new javax.swing.JLabel();
        jLabel137 = new javax.swing.JLabel();
        jLabel138 = new javax.swing.JLabel();
        jLabel139 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jPanel43 = new javax.swing.JPanel();
        jLabel87 = new javax.swing.JLabel();
        variableCostVariableField = new javax.swing.JTextField();
        actsCostVariableField = new javax.swing.JTextField();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        employeeCostVariableField = new javax.swing.JTextField();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        foodCostVariableField = new javax.swing.JTextField();
        clarqueCostVariableField = new javax.swing.JTextField();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        MissionCostVariableField = new javax.swing.JTextField();
        jLabel94 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        totalVariableField = new javax.swing.JTextField();
        kButton13 = new com.k33ptoo.components.KButton();
        kButton14 = new com.k33ptoo.components.KButton();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jLabel102 = new javax.swing.JLabel();
        VCTransitionField = new javax.swing.JTextField();
        PCTransitionField = new javax.swing.JTextField();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        EPACTransitionField = new javax.swing.JTextField();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        SILCTransitionField = new javax.swing.JTextField();
        ECTransitionField = new javax.swing.JTextField();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        TCTransitionField = new javax.swing.JTextField();
        jLabel109 = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        totalTransitionField = new javax.swing.JTextField();
        kButton15 = new com.k33ptoo.components.KButton();
        kButton16 = new com.k33ptoo.components.KButton();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        DCustomField = new javax.swing.JTextField();
        TVACustomField = new javax.swing.JTextField();
        TCSCustomField = new javax.swing.JTextField();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        QCustomField = new javax.swing.JTextField();
        jLabel119 = new javax.swing.JLabel();
        jLabel201 = new javax.swing.JLabel();
        jLabel202 = new javax.swing.JLabel();
        jLabel203 = new javax.swing.JLabel();
        totalCustomField = new javax.swing.JTextField();
        jLabel204 = new javax.swing.JLabel();
        jLabel205 = new javax.swing.JLabel();
        jLabel206 = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        CXCustomfield = new javax.swing.JTextField();
        totalCXCustomField = new javax.swing.JTextField();
        jLabel207 = new javax.swing.JLabel();
        jLabel208 = new javax.swing.JLabel();
        jLabel209 = new javax.swing.JLabel();
        jLabel210 = new javax.swing.JLabel();
        jLabel211 = new javax.swing.JLabel();
        jLabel212 = new javax.swing.JLabel();
        jLabel213 = new javax.swing.JLabel();
        jLabel214 = new javax.swing.JLabel();
        kButton20 = new com.k33ptoo.components.KButton();
        kButton21 = new com.k33ptoo.components.KButton();
        jPanel48 = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        jLabel120 = new javax.swing.JLabel();
        EETrasportField = new javax.swing.JTextField();
        jLabel121 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        SETransportField = new javax.swing.JTextField();
        totalETransportField = new javax.swing.JTextField();
        jSeparator16 = new javax.swing.JSeparator();
        TETransportField = new javax.swing.JTextField();
        jLabel123 = new javax.swing.JLabel();
        kButton17 = new com.k33ptoo.components.KButton();
        kButton18 = new com.k33ptoo.components.KButton();
        TauxETransportField = new javax.swing.JTextField();
        jLabel124 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        jLabel140 = new javax.swing.JLabel();
        jPanel50 = new javax.swing.JPanel();
        jLabel128 = new javax.swing.JLabel();
        ITTransportField = new javax.swing.JTextField();
        jLabel129 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        ICTransportField = new javax.swing.JTextField();
        totalITransportField = new javax.swing.JTextField();
        jSeparator17 = new javax.swing.JSeparator();
        IVTransportField = new javax.swing.JTextField();
        jLabel131 = new javax.swing.JLabel();
        kButton19 = new com.k33ptoo.components.KButton();
        kButton26 = new com.k33ptoo.components.KButton();
        jLabel141 = new javax.swing.JLabel();
        jLabel142 = new javax.swing.JLabel();
        jLabel143 = new javax.swing.JLabel();
        jLabel144 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        employeeSearchField = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel219 = new javax.swing.JLabel();
        employeeSearchCombo = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        kButton3 = new com.k33ptoo.components.KButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        employeeTable = new javax.swing.JTable();
        countEmployeeField = new javax.swing.JTextField();
        totalEmployeeField = new javax.swing.JTextField();

        addFolderDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addFolderDialog.setTitle("Ajouter un Dossier");
        addFolderDialog.setAlwaysOnTop(true);
        addFolderDialog.setLocation(new java.awt.Point(500, 500));
        addFolderDialog.setModal(true);
        addFolderDialog.setResizable(false);
        addFolderDialog.setSize(new java.awt.Dimension(600, 500));

        jPanel5.setBackground(new java.awt.Color(27, 32, 44));

        jLabel215.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel215.setForeground(new java.awt.Color(255, 255, 255));
        jLabel215.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel215.setText("N.Facture:");

        billNumberField.setPreferredSize(new java.awt.Dimension(150, 35));
        billNumberField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billNumberFieldjTextField1ActionPerformed(evt);
            }
        });

        bancField.setPreferredSize(new java.awt.Dimension(150, 35));
        bancField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bancFieldjTextField1ActionPerformed(evt);
            }
        });

        jLabel216.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel216.setForeground(new java.awt.Color(255, 255, 255));
        jLabel216.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel216.setText("Banque:");

        portField.setPreferredSize(new java.awt.Dimension(150, 35));
        portField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portFieldjTextField1ActionPerformed(evt);
            }
        });

        jLabel217.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel217.setForeground(new java.awt.Color(255, 255, 255));
        jLabel217.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel217.setText("Port:");

        kButton5.setForeground(new java.awt.Color(27, 32, 44));
        kButton5.setText("Enregestrier");
        kButton5.setkForeGround(new java.awt.Color(27, 32, 44));
        kButton5.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton5.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton5.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton5MouseClicked(evt);
            }
        });

        kButton6.setForeground(new java.awt.Color(27, 32, 44));
        kButton6.setText("Cancel");
        kButton6.setkEndColor(new java.awt.Color(255, 51, 102));
        kButton6.setkHoverEndColor(new java.awt.Color(255, 51, 153));
        kButton6.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton6.setkHoverStartColor(new java.awt.Color(255, 0, 0));
        kButton6.setkStartColor(new java.awt.Color(255, 0, 0));
        kButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton6MouseClicked(evt);
            }
        });
        kButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton6ActionPerformed(evt);
            }
        });

        jLabel218.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel218.setForeground(new java.awt.Color(255, 255, 255));
        jLabel218.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel218.setText("Ajouter un Dossier");

        addFolderErrorLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        addFolderErrorLabel.setForeground(new java.awt.Color(255, 0, 0));
        addFolderErrorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel215, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                                .addComponent(billNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel216, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bancField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel217, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(kButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(kButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel218, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(addFolderErrorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel218, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel215, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(billNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel216, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bancField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel217, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addFolderErrorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout addFolderDialogLayout = new javax.swing.GroupLayout(addFolderDialog.getContentPane());
        addFolderDialog.getContentPane().setLayout(addFolderDialogLayout);
        addFolderDialogLayout.setHorizontalGroup(
            addFolderDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        addFolderDialogLayout.setVerticalGroup(
            addFolderDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        addIRDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addIRDialog.setAlwaysOnTop(true);
        addIRDialog.setResizable(false);
        addIRDialog.setSize(new java.awt.Dimension(1000, 670));
        addIRDialog.setType(java.awt.Window.Type.POPUP);

        jPanel29.setBackground(new java.awt.Color(27, 32, 44));
        jPanel29.setPreferredSize(new java.awt.Dimension(600, 600));

        jLabel28.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Sac Quantité:");

        jLabel29.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Prix Unitaire:");

        jLabel30.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Quantité dans Sac:");

        jLabel31.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Designation:");

        jLabel53.setBackground(new java.awt.Color(27, 32, 44));
        jLabel53.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("Ajouter Une Mettière Première");

        jLabel54.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("Reference:");

        addIRPriceField.setPreferredSize(new java.awt.Dimension(150, 35));
        addIRPriceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addIRPriceFieldActionPerformed(evt);
            }
        });

        kButton10.setText("Enregestrier");
        kButton10.setEnabled(false);
        kButton10.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton10.setkHoverForeGround(new java.awt.Color(27, 32, 44));
        kButton10.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton10MouseClicked(evt);
            }
        });

        addIRRefField.setPreferredSize(new java.awt.Dimension(150, 35));
        addIRRefField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addIRRefFieldActionPerformed(evt);
            }
        });

        addIRSQField.setPreferredSize(new java.awt.Dimension(150, 35));
        addIRSQField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addIRSQFieldActionPerformed(evt);
            }
        });
        addIRSQField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                addIRSQFieldKeyReleased(evt);
            }
        });

        addIRFolderField.setPreferredSize(new java.awt.Dimension(150, 35));
        addIRFolderField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addIRFolderFieldActionPerformed(evt);
            }
        });
        addIRFolderField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                addIRFolderFieldKeyReleased(evt);
            }
        });

        addIRQOSField.setPreferredSize(new java.awt.Dimension(150, 35));
        addIRQOSField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addIRQOSFieldActionPerformed(evt);
            }
        });
        addIRQOSField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                addIRQOSFieldKeyReleased(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Quantité Total:");

        addIRQField.setEnabled(false);
        addIRQField.setPreferredSize(new java.awt.Dimension(150, 35));
        addIRQField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addIRQFieldActionPerformed(evt);
            }
        });

        kButton7.setForeground(new java.awt.Color(27, 32, 44));
        kButton7.setText("Cancel");
        kButton7.setkEndColor(new java.awt.Color(255, 51, 102));
        kButton7.setkHoverEndColor(new java.awt.Color(255, 51, 153));
        kButton7.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton7.setkHoverStartColor(new java.awt.Color(255, 0, 0));
        kButton7.setkStartColor(new java.awt.Color(255, 0, 0));
        kButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton7MouseClicked(evt);
            }
        });
        kButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton7ActionPerformed(evt);
            }
        });

        addIRTauxField.setPreferredSize(new java.awt.Dimension(150, 35));
        addIRTauxField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addIRTauxFieldActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Taux De Change:");

        addIRDesField.setColumns(20);
        addIRDesField.setRows(5);
        jScrollPane2.setViewportView(addIRDesField);

        jLabel34.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Dossier N.Facture:");

        addIRErrorLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        addIRErrorLabel.setForeground(new java.awt.Color(255, 0, 0));
        addIRErrorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        addIRuntCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "piece", "g", "kg", "litre" }));

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Unit:");

        jScrollPane6.setBorder(null);

        billNumberList.setBackground(new java.awt.Color(27, 32, 44));
        billNumberList.setForeground(new java.awt.Color(255, 255, 255));
        billNumberList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        billNumberList.setAlignmentX(1.0F);
        billNumberList.setAlignmentY(1.0F);
        billNumberList.setSelectionBackground(new java.awt.Color(255, 255, 255));
        billNumberList.setSelectionForeground(new java.awt.Color(27, 32, 44));
        billNumberList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                billNumberListMouseClicked(evt);
            }
        });
        billNumberList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                billNumberListKeyReleased(evt);
            }
        });
        jScrollPane6.setViewportView(billNumberList);

        jScrollPane10.setBorder(null);

        fourList.setBackground(new java.awt.Color(27, 32, 44));
        fourList.setForeground(new java.awt.Color(255, 255, 255));
        fourList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        fourList.setAlignmentX(1.0F);
        fourList.setAlignmentY(1.0F);
        fourList.setSelectionBackground(new java.awt.Color(255, 255, 255));
        fourList.setSelectionForeground(new java.awt.Color(27, 32, 44));
        fourList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fourListMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(fourList);

        addIRFourField.setPreferredSize(new java.awt.Dimension(150, 35));
        addIRFourField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addIRFourFieldActionPerformed(evt);
            }
        });
        addIRFourField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                addIRFourFieldKeyReleased(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("Fournisseur:");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addIRRefField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addIRPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addIRFourField, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(addIRFolderField, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(addIRSQField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(298, 298, 298)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(addIRQOSField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(addIRuntCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addGap(804, 804, 804)
                                .addComponent(kButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addGap(684, 684, 684)
                                .addComponent(kButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(addIRErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 988, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addIRQField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addIRTauxField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(108, 108, 108)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel53)
                .addGap(6, 6, 6)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addComponent(addIRRefField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addComponent(addIRPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(79, 79, 79)
                                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(addIRFolderField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel29Layout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(11, 11, 11)
                                .addComponent(addIRFourField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(addIRSQField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(addIRQOSField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9)
                    .addComponent(addIRuntCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addComponent(addIRQField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addComponent(addIRTauxField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(20, 20, 20)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(addIRErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        javax.swing.GroupLayout addIRDialogLayout = new javax.swing.GroupLayout(addIRDialog.getContentPane());
        addIRDialog.getContentPane().setLayout(addIRDialogLayout);
        addIRDialogLayout.setHorizontalGroup(
            addIRDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        );
        addIRDialogLayout.setVerticalGroup(
            addIRDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
        );

        irFoldersDialog.setAlwaysOnTop(true);
        irFoldersDialog.setResizable(false);
        irFoldersDialog.setSize(new java.awt.Dimension(1250, 620));

        jPanel9.setBackground(new java.awt.Color(27, 32, 44));

        jPanel10.setBackground(new java.awt.Color(27, 32, 44));

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Historique Des Achats :");

        jPanel24.setBackground(new java.awt.Color(27, 32, 44));

        irFoldersSearchField.setForeground(new java.awt.Color(153, 153, 153));
        irFoldersSearchField.setText("recherché par Réference...");
        irFoldersSearchField.setPreferredSize(new java.awt.Dimension(250, 22));
        irFoldersSearchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                irFoldersSearchFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                irFoldersSearchFieldFocusLost(evt);
            }
        });
        irFoldersSearchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                irFoldersSearchFieldActionPerformed(evt);
            }
        });
        irFoldersSearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                irFoldersSearchFieldKeyReleased(evt);
            }
        });

        irFoldersSearchCombo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        irFoldersSearchCombo.setForeground(new java.awt.Color(27, 32, 44));
        irFoldersSearchCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Réference", "Id", "Unité" }));
        irFoldersSearchCombo.setPreferredSize(new java.awt.Dimension(100, 22));
        irFoldersSearchCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                irFoldersSearchComboActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("recherché par:");

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prixdrevien/images/211818_search_icon(6).png"))); // NOI18N

        kButton28.setForeground(new java.awt.Color(27, 32, 44));
        kButton28.setText("Ajouter ");
        kButton28.setkForeGround(new java.awt.Color(27, 32, 44));
        kButton28.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton28.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton28.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton28MouseClicked(evt);
            }
        });
        kButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton28ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel22)
                .addGap(0, 0, 0)
                .addComponent(irFoldersSearchCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(irFoldersSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 615, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(kButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(irFoldersSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(kButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(irFoldersSearchCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel22))))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        irFoldersTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        irFoldersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "N.Facture", "Date", "Fournisseur", "Couler Quantité", " Quantité d.Couler", "Quantité", "P.U Dev", "P.U DA", "Monton DA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        irFoldersTable.setAutoscrolls(false);
        irFoldersTable.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        irFoldersTable.setSelectionBackground(new java.awt.Color(27, 32, 44));
        irFoldersTable.setShowGrid(true);
        irFoldersTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(irFoldersTable);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout irFoldersDialogLayout = new javax.swing.GroupLayout(irFoldersDialog.getContentPane());
        irFoldersDialog.getContentPane().setLayout(irFoldersDialogLayout);
        irFoldersDialogLayout.setHorizontalGroup(
            irFoldersDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        irFoldersDialogLayout.setVerticalGroup(
            irFoldersDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        buyIRDialog.setAlwaysOnTop(true);
        buyIRDialog.setResizable(false);
        buyIRDialog.setSize(new java.awt.Dimension(510, 820));

        jPanel11.setBackground(new java.awt.Color(27, 32, 44));

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Acheter :");

        buyIRPriceField.setPreferredSize(new java.awt.Dimension(150, 35));
        buyIRPriceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyIRPriceFieldActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("Prix Unitaire:");

        buyIRSQField.setPreferredSize(new java.awt.Dimension(150, 35));
        buyIRSQField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyIRSQFieldActionPerformed(evt);
            }
        });
        buyIRSQField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buyIRSQFieldKeyReleased(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Sac Quantité:");

        jLabel37.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("Quantité dans Sac:");

        buyIRQOSField.setPreferredSize(new java.awt.Dimension(150, 35));
        buyIRQOSField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyIRQOSFieldActionPerformed(evt);
            }
        });
        buyIRQOSField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buyIRQOSFieldKeyReleased(evt);
            }
        });

        buyIRQField.setEnabled(false);
        buyIRQField.setPreferredSize(new java.awt.Dimension(150, 35));
        buyIRQField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyIRQFieldActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Quantité Total:");

        jLabel39.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("Taux De Change:");

        buyIRTauxField.setPreferredSize(new java.awt.Dimension(150, 35));
        buyIRTauxField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyIRTauxFieldActionPerformed(evt);
            }
        });

        buyIRFolderField.setPreferredSize(new java.awt.Dimension(150, 35));
        buyIRFolderField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyIRFolderFieldActionPerformed(evt);
            }
        });
        buyIRFolderField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buyIRFolderFieldKeyReleased(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("Dossier N.Facture:");

        kButton8.setForeground(new java.awt.Color(27, 32, 44));
        kButton8.setText("Cancel");
        kButton8.setkEndColor(new java.awt.Color(255, 51, 102));
        kButton8.setkHoverEndColor(new java.awt.Color(255, 51, 153));
        kButton8.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton8.setkHoverStartColor(new java.awt.Color(255, 0, 0));
        kButton8.setkStartColor(new java.awt.Color(255, 0, 0));
        kButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton8MouseClicked(evt);
            }
        });
        kButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton8ActionPerformed(evt);
            }
        });

        kButton11.setText("Enregestrier");
        kButton11.setEnabled(false);
        kButton11.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton11.setkHoverForeGround(new java.awt.Color(27, 32, 44));
        kButton11.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton11MouseClicked(evt);
            }
        });

        buyIRFourField.setPreferredSize(new java.awt.Dimension(150, 35));
        buyIRFourField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyIRFourFieldActionPerformed(evt);
            }
        });
        buyIRFourField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buyIRFourFieldKeyReleased(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Fourniseur:");

        jScrollPane7.setBorder(null);

        buyBillNumberList.setBackground(new java.awt.Color(27, 32, 44));
        buyBillNumberList.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        buyBillNumberList.setForeground(new java.awt.Color(255, 255, 255));
        buyBillNumberList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        buyBillNumberList.setAutoscrolls(false);
        buyBillNumberList.setSelectionBackground(new java.awt.Color(255, 255, 255));
        buyBillNumberList.setSelectionForeground(new java.awt.Color(27, 32, 44));
        buyBillNumberList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buyBillNumberListMouseClicked(evt);
            }
        });
        buyBillNumberList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buyBillNumberListKeyReleased(evt);
            }
        });
        buyBillNumberList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                buyBillNumberListValueChanged(evt);
            }
        });
        jScrollPane7.setViewportView(buyBillNumberList);

        buyIRErrorLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        buyIRErrorLabel.setForeground(new java.awt.Color(255, 0, 0));
        buyIRErrorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jScrollPane11.setBorder(null);

        buyFourList.setBackground(new java.awt.Color(27, 32, 44));
        buyFourList.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        buyFourList.setForeground(new java.awt.Color(255, 255, 255));
        buyFourList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        buyFourList.setAutoscrolls(false);
        buyFourList.setSelectionBackground(new java.awt.Color(255, 255, 255));
        buyFourList.setSelectionForeground(new java.awt.Color(27, 32, 44));
        buyFourList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buyFourListMouseClicked(evt);
            }
        });
        buyFourList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buyFourListKeyReleased(evt);
            }
        });
        buyFourList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                buyFourListValueChanged(evt);
            }
        });
        jScrollPane11.setViewportView(buyFourList);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(80, 80, 80)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buyIRFourField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(62, 62, 62)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buyIRFolderField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buyIRPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buyIRSQField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buyIRQOSField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buyIRQField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buyIRTauxField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(kButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(kButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(42, Short.MAX_VALUE))))
            .addComponent(buyIRErrorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buyIRFolderField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buyIRPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buyIRSQField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buyIRQOSField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buyIRQField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buyIRTauxField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(buyIRFourField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buyIRErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout buyIRDialogLayout = new javax.swing.GroupLayout(buyIRDialog.getContentPane());
        buyIRDialog.getContentPane().setLayout(buyIRDialogLayout);
        buyIRDialogLayout.setHorizontalGroup(
            buyIRDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        buyIRDialogLayout.setVerticalGroup(
            buyIRDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        addFournisseurDialog.setResizable(false);
        addFournisseurDialog.setSize(new java.awt.Dimension(600, 460));

        jPanel22.setBackground(new java.awt.Color(27, 32, 44));

        jLabel55.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("Nom:");

        addFournisseurNomField.setPreferredSize(new java.awt.Dimension(150, 35));
        addFournisseurNomField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFournisseurNomFieldActionPerformed(evt);
            }
        });

        addFournisseurComagnieField.setPreferredSize(new java.awt.Dimension(150, 35));
        addFournisseurComagnieField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFournisseurComagnieFieldActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("Compagnie:");

        jLabel44.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("Pay:");

        addFournisseurPayField.setPreferredSize(new java.awt.Dimension(150, 35));
        addFournisseurPayField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFournisseurPayFieldActionPerformed(evt);
            }
        });
        addFournisseurPayField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                addFournisseurPayFieldKeyReleased(evt);
            }
        });

        jLabel220.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel220.setForeground(new java.awt.Color(255, 255, 255));
        jLabel220.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel220.setText("Ajouter un Fournisseur :");

        kButton12.setForeground(new java.awt.Color(27, 32, 44));
        kButton12.setText("Cancel");
        kButton12.setkEndColor(new java.awt.Color(255, 51, 102));
        kButton12.setkHoverEndColor(new java.awt.Color(255, 51, 153));
        kButton12.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton12.setkHoverStartColor(new java.awt.Color(255, 0, 0));
        kButton12.setkStartColor(new java.awt.Color(255, 0, 0));
        kButton12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton12MouseClicked(evt);
            }
        });
        kButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton12ActionPerformed(evt);
            }
        });

        kButton22.setForeground(new java.awt.Color(27, 32, 44));
        kButton22.setText("Enregestrier");
        kButton22.setkForeGround(new java.awt.Color(27, 32, 44));
        kButton22.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton22.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton22.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton22MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel220, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(kButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(kButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addFournisseurNomField, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(addFournisseurComagnieField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addFournisseurPayField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)))
                .addGap(50, 50, 50))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jLabel220, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addFournisseurNomField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addFournisseurComagnieField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addFournisseurPayField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kButton22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout addFournisseurDialogLayout = new javax.swing.GroupLayout(addFournisseurDialog.getContentPane());
        addFournisseurDialog.getContentPane().setLayout(addFournisseurDialogLayout);
        addFournisseurDialogLayout.setHorizontalGroup(
            addFournisseurDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        addFournisseurDialogLayout.setVerticalGroup(
            addFournisseurDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        addEmployeeDialog.setLocation(new java.awt.Point(600, 300));
        addEmployeeDialog.setResizable(false);
        addEmployeeDialog.setSize(new java.awt.Dimension(600, 660));

        jPanel23.setBackground(new java.awt.Color(27, 32, 44));

        jLabel221.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel221.setForeground(new java.awt.Color(255, 255, 255));
        jLabel221.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel221.setText("Ajouter un Employee");

        jLabel222.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel222.setForeground(new java.awt.Color(255, 255, 255));
        jLabel222.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel222.setText("Nom:");

        nameEmployeeField.setPreferredSize(new java.awt.Dimension(150, 35));
        nameEmployeeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameEmployeeFieldjTextField1ActionPerformed(evt);
            }
        });

        postEmployeeField.setPreferredSize(new java.awt.Dimension(150, 35));
        postEmployeeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postEmployeeFieldjTextField1ActionPerformed(evt);
            }
        });

        jLabel223.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel223.setForeground(new java.awt.Color(255, 255, 255));
        jLabel223.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel223.setText("Post:");

        jLabel224.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel224.setForeground(new java.awt.Color(255, 255, 255));
        jLabel224.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel224.setText("Némuro:");

        numberEmployeeField.setPreferredSize(new java.awt.Dimension(150, 35));
        numberEmployeeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numberEmployeeFieldjTextField1ActionPerformed(evt);
            }
        });
        numberEmployeeField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                numberEmployeeFieldKeyReleased(evt);
            }
        });

        kButton23.setForeground(new java.awt.Color(27, 32, 44));
        kButton23.setText("Cancel");
        kButton23.setkEndColor(new java.awt.Color(255, 51, 102));
        kButton23.setkHoverEndColor(new java.awt.Color(255, 51, 153));
        kButton23.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton23.setkHoverStartColor(new java.awt.Color(255, 0, 0));
        kButton23.setkStartColor(new java.awt.Color(255, 0, 0));
        kButton23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton23MouseClicked(evt);
            }
        });
        kButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton23ActionPerformed(evt);
            }
        });

        kButton27.setForeground(new java.awt.Color(27, 32, 44));
        kButton27.setText("Enregestrier");
        kButton27.setkForeGround(new java.awt.Color(27, 32, 44));
        kButton27.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton27.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton27.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton27MouseClicked(evt);
            }
        });

        salaryEmployeeField.setPreferredSize(new java.awt.Dimension(150, 35));
        salaryEmployeeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salaryEmployeeFieldjTextField1ActionPerformed(evt);
            }
        });

        jLabel225.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel225.setForeground(new java.awt.Color(255, 255, 255));
        jLabel225.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel225.setText("Salare:");

        addEmployeeErrorLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        addEmployeeErrorLabel.setForeground(new java.awt.Color(255, 0, 0));
        addEmployeeErrorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        monthEmployeeCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthEmployeeComboActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Le mois :");

        jLabel20.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Le jour :");

        jLabel21.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("L'anné :");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addComponent(kButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(kButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel23Layout.createSequentialGroup()
                                        .addComponent(jLabel222, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(nameEmployeeField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel23Layout.createSequentialGroup()
                                        .addComponent(jLabel223, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(postEmployeeField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel23Layout.createSequentialGroup()
                                        .addComponent(jLabel224, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(numberEmployeeField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel23Layout.createSequentialGroup()
                                        .addComponent(jLabel225, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(salaryEmployeeField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(yearEmployeeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel21))
                                        .addGap(30, 30, 30)
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(monthEmployeeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel19))
                                        .addGap(30, 30, 30)
                                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(dayEmployeeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(44, 44, 44))
                    .addComponent(jLabel221, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(addEmployeeErrorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(jLabel221, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel222, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameEmployeeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel223, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(postEmployeeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel224, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numberEmployeeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel225, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salaryEmployeeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monthEmployeeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dayEmployeeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearEmployeeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kButton27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(addEmployeeErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout addEmployeeDialogLayout = new javax.swing.GroupLayout(addEmployeeDialog.getContentPane());
        addEmployeeDialog.getContentPane().setLayout(addEmployeeDialogLayout);
        addEmployeeDialogLayout.setHorizontalGroup(
            addEmployeeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        addEmployeeDialogLayout.setVerticalGroup(
            addEmployeeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(248, 248, 249));
        setMinimumSize(new java.awt.Dimension(1500, 900));
        setPreferredSize(new java.awt.Dimension(1500, 900));

        jPanel1.setBackground(new java.awt.Color(248, 248, 249));
        jPanel1.setPreferredSize(new java.awt.Dimension(950, 50));

        jLabel1.setBackground(new java.awt.Color(27, 32, 44));
        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Les Matières Premières");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(226, 226, 226)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 18, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );

        jSeparator1.setBackground(new java.awt.Color(27, 32, 44));
        jSeparator1.setPreferredSize(new java.awt.Dimension(1100, 10));

        contentTabbedPane.setBackground(new java.awt.Color(204, 204, 204));
        contentTabbedPane.setForeground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(248, 248, 249));

        jPanel2.setBackground(new java.awt.Color(248, 248, 249));
        jPanel2.setPreferredSize(new java.awt.Dimension(1500, 50));

        jPanel16.setBackground(new java.awt.Color(248, 248, 249));

        irSearchField.setForeground(new java.awt.Color(153, 153, 153));
        irSearchField.setText("recherché par Réference...");
        irSearchField.setPreferredSize(new java.awt.Dimension(250, 22));
        irSearchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                irSearchFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                irSearchFieldFocusLost(evt);
            }
        });
        irSearchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                irSearchFieldActionPerformed(evt);
            }
        });
        irSearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                irSearchFieldKeyReleased(evt);
            }
        });

        irSearchCombo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        irSearchCombo.setForeground(new java.awt.Color(27, 32, 44));
        irSearchCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Réference", "Id", "Unité" }));
        irSearchCombo.setPreferredSize(new java.awt.Dimension(100, 22));
        irSearchCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                irSearchComboActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(27, 32, 44));
        jLabel2.setText("recherché par:");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prixdrevien/images/211818_search_icon(6).png"))); // NOI18N

        kButton4.setForeground(new java.awt.Color(27, 32, 44));
        kButton4.setText("Ajouter ");
        kButton4.setkForeGround(new java.awt.Color(27, 32, 44));
        kButton4.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton4.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton4.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton4MouseClicked(evt);
            }
        });
        kButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(irSearchCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(irSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80)
                .addComponent(kButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(irSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(kButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(irSearchCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addGap(0, 0, 0))
        );

        jPanel17.setBackground(new java.awt.Color(248, 248, 249));

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(27, 32, 44));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Les Matières Premières :");
        jLabel12.setPreferredSize(new java.awt.Dimension(200, 25));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 1400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGap(0, 32, Short.MAX_VALUE)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel17Layout.createSequentialGroup()
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 17, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        irTable.setFont(new java.awt.Font("Yu Gothic", 0, 14)); // NOI18N
        irTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Reference", "Designation", "Date", "Fournisseur", "Couler Quantité", "Quantité dans Couler", "Quantité", "Unité", "P.U Dev", "P.U DA", "Monton DA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        irTable.setMaximumSize(new java.awt.Dimension(1500, 900));
        irTable.setMinimumSize(new java.awt.Dimension(1500, 900));
        irTable.setPreferredSize(new java.awt.Dimension(1500, 900));
        irTable.setSelectionBackground(new java.awt.Color(27, 32, 44));
        irTable.setShowGrid(true);
        irTable.getTableHeader().setReorderingAllowed(false);
        irTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                irTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(irTable);

        jPanel7.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 3, 1500, 730));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        contentTabbedPane.addTab("Tableau", jPanel3);

        jPanel14.setBackground(new java.awt.Color(248, 248, 249));
        jPanel14.setLayout(new java.awt.BorderLayout());

        fourTable.setFont(new java.awt.Font("Yu Gothic", 0, 14)); // NOI18N
        fourTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Nom", "Compagnie", "Pay"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        fourTable.setMaximumSize(new java.awt.Dimension(1500, 900));
        fourTable.setMinimumSize(new java.awt.Dimension(1500, 900));
        fourTable.setPreferredSize(new java.awt.Dimension(1500, 900));
        fourTable.setSelectionBackground(new java.awt.Color(27, 32, 44));
        fourTable.setShowGrid(true);
        fourTable.getTableHeader().setReorderingAllowed(false);
        fourTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fourTableMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(fourTable);

        jPanel14.add(jScrollPane8, java.awt.BorderLayout.CENTER);

        jPanel18.setBackground(new java.awt.Color(248, 248, 249));

        fourSearchField.setForeground(new java.awt.Color(153, 153, 153));
        fourSearchField.setText("recherché par Réference...");
        fourSearchField.setPreferredSize(new java.awt.Dimension(250, 22));
        fourSearchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fourSearchFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                fourSearchFieldFocusLost(evt);
            }
        });
        fourSearchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fourSearchFieldActionPerformed(evt);
            }
        });
        fourSearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fourSearchFieldKeyReleased(evt);
            }
        });

        fourSearchCombo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        fourSearchCombo.setForeground(new java.awt.Color(27, 32, 44));
        fourSearchCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Réference", "Id", "Unité" }));
        fourSearchCombo.setPreferredSize(new java.awt.Dimension(100, 22));
        fourSearchCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fourSearchComboActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(27, 32, 44));
        jLabel13.setText("recherché par:");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prixdrevien/images/211818_search_icon(6).png"))); // NOI18N

        kButton9.setForeground(new java.awt.Color(27, 32, 44));
        kButton9.setText("Ajouter ");
        kButton9.setkForeGround(new java.awt.Color(27, 32, 44));
        kButton9.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton9.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton9.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton9MouseClicked(evt);
            }
        });
        kButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fourSearchCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(fourSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80)
                .addComponent(kButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(fourSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(kButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(fourSearchCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)))
                .addGap(18, 18, 18))
        );

        jPanel19.setBackground(new java.awt.Color(248, 248, 249));

        jLabel16.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(27, 32, 44));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Les Fournisseurs :");
        jLabel16.setPreferredSize(new java.awt.Dimension(200, 25));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 1400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel19Layout.createSequentialGroup()
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 17, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel14.add(jPanel15, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 1500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        contentTabbedPane.addTab("Les Fournisseurs", jPanel4);

        jPanel6.setBackground(new java.awt.Color(248, 248, 249));

        jPanel8.setBackground(new java.awt.Color(248, 248, 249));
        jPanel8.setPreferredSize(new java.awt.Dimension(950, 42));

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(27, 32, 44));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Les charges");
        jLabel7.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel14.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(27, 32, 44));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Les charges");
        jLabel14.setPreferredSize(new java.awt.Dimension(200, 25));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 1400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 1500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.setBackground(new java.awt.Color(248, 248, 249));

        jPanel38.setBackground(new java.awt.Color(248, 248, 249));

        jPanel40.setBackground(new java.awt.Color(248, 248, 249));

        foldersTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        foldersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "N.Facture", "Banc", "Port", "Date", "Frais Banquère", "Frais Douane", "Frais Transport", "Frais Transit", "Frais Variable", "Monton"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        foldersTable.setDoubleBuffered(true);
        foldersTable.setMaximumSize(new java.awt.Dimension(1500, 900));
        foldersTable.setSelectionBackground(new java.awt.Color(27, 32, 44));
        foldersTable.setShowGrid(true);
        foldersTable.getTableHeader().setReorderingAllowed(false);
        foldersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                foldersTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                foldersTableMouseEntered(evt);
            }
        });
        jScrollPane4.setViewportView(foldersTable);

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
        );

        jPanel20.setBackground(new java.awt.Color(248, 248, 249));

        foldersSearchField.setForeground(new java.awt.Color(153, 153, 153));
        foldersSearchField.setText("recherché par n.facture...");
        foldersSearchField.setPreferredSize(new java.awt.Dimension(250, 22));
        foldersSearchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                foldersSearchFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                foldersSearchFieldFocusLost(evt);
            }
        });
        foldersSearchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foldersSearchFieldActionPerformed(evt);
            }
        });
        foldersSearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                foldersSearchFieldKeyReleased(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prixdrevien/images/211818_search_icon(6).png"))); // NOI18N

        jLabel191.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel191.setForeground(new java.awt.Color(27, 32, 44));
        jLabel191.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel191.setText("Les Dossiers");
        jLabel191.setPreferredSize(new java.awt.Dimension(200, 25));

        foldersSearchCombo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        foldersSearchCombo.setForeground(new java.awt.Color(27, 32, 44));
        foldersSearchCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N.Facture", "Id", "Banc", "Port" }));
        foldersSearchCombo.setPreferredSize(new java.awt.Dimension(100, 22));
        foldersSearchCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foldersSearchComboActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(27, 32, 44));
        jLabel6.setText("recherché par:");

        kButton1.setForeground(new java.awt.Color(27, 32, 44));
        kButton1.setText("Ajouter ");
        kButton1.setkForeGround(new java.awt.Color(27, 32, 44));
        kButton1.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton1.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton1.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jLabel191, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(foldersSearchCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(foldersSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80)
                .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(foldersSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel191, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(foldersSearchCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(1, 1, 1)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 25, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel38, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("Dossiers", jPanel37);

        jPanel41.setBackground(new java.awt.Color(248, 248, 249));

        jPanel65.setBackground(new java.awt.Color(27, 32, 44));
        jPanel65.setPreferredSize(new java.awt.Dimension(800, 656));

        jLabel192.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel192.setForeground(new java.awt.Color(255, 255, 255));
        jLabel192.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel192.setText("Description:");
        jLabel192.setPreferredSize(new java.awt.Dimension(200, 25));

        CCBancField.setEnabled(false);
        CCBancField.setPreferredSize(new java.awt.Dimension(150, 35));
        CCBancField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CCBancFieldjTextField15ActionPerformed(evt);
            }
        });

        DD3BancField.setEnabled(false);
        DD3BancField.setPreferredSize(new java.awt.Dimension(150, 35));
        DD3BancField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DD3BancFieldjTextField14ActionPerformed(evt);
            }
        });

        DD2BancField.setEnabled(false);
        DD2BancField.setPreferredSize(new java.awt.Dimension(150, 35));
        DD2BancField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DD2BancFieldjTextField13ActionPerformed(evt);
            }
        });

        jLabel193.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel193.setForeground(new java.awt.Color(255, 255, 255));
        jLabel193.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel193.setText("DoMic DocuMeNT 2:");

        jLabel194.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel194.setForeground(new java.awt.Color(255, 255, 255));
        jLabel194.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel194.setText("DoMic DocuMeNT 3:");

        jLabel195.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel195.setForeground(new java.awt.Color(255, 255, 255));
        jLabel195.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel195.setText("Frais corresponant:");

        DCBancField.setEnabled(false);
        DCBancField.setPreferredSize(new java.awt.Dimension(150, 35));
        DCBancField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DCBancFieldjTextField10ActionPerformed(evt);
            }
        });

        jLabel196.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel196.setForeground(new java.awt.Color(255, 255, 255));
        jLabel196.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel196.setText("Frais DisBlocage:");

        jLabel197.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel197.setForeground(new java.awt.Color(255, 255, 255));
        jLabel197.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel197.setText("DoMic DocuMeNT 1:");

        DD1BancField.setEnabled(false);
        DD1BancField.setPreferredSize(new java.awt.Dimension(150, 35));
        DD1BancField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DD1BancFieldjTextField8ActionPerformed(evt);
            }
        });

        SCBancField.setEnabled(false);
        SCBancField.setPreferredSize(new java.awt.Dimension(150, 35));
        SCBancField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SCBancFieldjTextField4ActionPerformed(evt);
            }
        });

        jLabel198.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel198.setForeground(new java.awt.Color(255, 255, 255));
        jLabel198.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel198.setText("Frais Swift:");

        jLabel199.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel199.setForeground(new java.awt.Color(255, 255, 255));
        jLabel199.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel199.setText("Monton:");
        jLabel199.setPreferredSize(new java.awt.Dimension(200, 25));

        RDBancField.setEnabled(false);
        RDBancField.setPreferredSize(new java.awt.Dimension(150, 35));
        RDBancField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RDBancFieldjTextField1ActionPerformed(evt);
            }
        });

        jLabel200.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel200.setForeground(new java.awt.Color(255, 255, 255));
        jLabel200.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel200.setText("Reglement Definitife:");

        totalBancField.setEnabled(false);
        totalBancField.setPreferredSize(new java.awt.Dimension(150, 35));
        totalBancField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalBancFieldjTextField16ActionPerformed(evt);
            }
        });

        kButton24.setText("Enregestrier");
        kButton24.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton24.setkHoverForeGround(new java.awt.Color(27, 32, 44));
        kButton24.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton24MouseClicked(evt);
            }
        });

        kButton25.setText("Modifier");
        kButton25.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton25.setkHoverForeGround(new java.awt.Color(27, 32, 44));
        kButton25.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton25MouseClicked(evt);
            }
        });

        jLabel132.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel132.setForeground(new java.awt.Color(255, 255, 255));
        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel132.setText("DA");

        jLabel133.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel133.setForeground(new java.awt.Color(255, 255, 255));
        jLabel133.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel133.setText("DA");

        jLabel134.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel134.setForeground(new java.awt.Color(255, 255, 255));
        jLabel134.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel134.setText("DA");

        jLabel135.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel135.setForeground(new java.awt.Color(255, 255, 255));
        jLabel135.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel135.setText("DA");

        jLabel136.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel136.setForeground(new java.awt.Color(255, 255, 255));
        jLabel136.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel136.setText("DA");

        jLabel137.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel137.setForeground(new java.awt.Color(255, 255, 255));
        jLabel137.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel137.setText("DA");

        jLabel138.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel138.setForeground(new java.awt.Color(255, 255, 255));
        jLabel138.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel138.setText("DA");

        jLabel139.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel139.setForeground(new java.awt.Color(255, 255, 255));
        jLabel139.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel139.setText("DA");

        javax.swing.GroupLayout jPanel65Layout = new javax.swing.GroupLayout(jPanel65);
        jPanel65.setLayout(jPanel65Layout);
        jPanel65Layout.setHorizontalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel65Layout.createSequentialGroup()
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel65Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jSeparator24, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel65Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel194, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                            .addComponent(jLabel195, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                            .addComponent(jLabel193, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                            .addComponent(jLabel196, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                            .addComponent(jLabel197, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                            .addComponent(jLabel200, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel199, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(SCBancField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(DD1BancField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(DCBancField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(DD2BancField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(DD3BancField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(CCBancField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(RDBancField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel65Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(kButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 356, Short.MAX_VALUE)
                        .addComponent(totalBancField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel132)
                    .addComponent(jLabel133)
                    .addComponent(jLabel134)
                    .addComponent(jLabel135)
                    .addComponent(jLabel136)
                    .addComponent(jLabel137)
                    .addComponent(jLabel138)
                    .addComponent(jLabel139))
                .addGap(44, 44, 44))
            .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel65Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel198, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel192, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(594, Short.MAX_VALUE)))
        );
        jPanel65Layout.setVerticalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel65Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel199, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RDBancField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel200, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel132))
                .addGap(35, 35, 35)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SCBancField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel133))
                .addGap(34, 34, 34)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DCBancField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel196, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel134))
                .addGap(34, 34, 34)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DD1BancField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel197, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel135))
                .addGap(34, 34, 34)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DD2BancField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel193, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel136))
                .addGap(35, 35, 35)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DD3BancField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel194, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel137))
                .addGap(34, 34, 34)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CCBancField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel195, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel138))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator24, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalBancField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel139))
                .addGap(151, 151, 151))
            .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel65Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel192, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(115, 115, 115)
                    .addComponent(jLabel198, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(573, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(350, 350, 350)
                .addComponent(jPanel65, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(350, 350, 350))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel65, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Charge Banquère", jPanel41);

        jPanel43.setBackground(new java.awt.Color(27, 32, 44));
        jPanel43.setPreferredSize(new java.awt.Dimension(800, 610));

        jLabel87.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(255, 255, 255));
        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel87.setText("Description:");
        jLabel87.setPreferredSize(new java.awt.Dimension(200, 25));

        variableCostVariableField.setEnabled(false);
        variableCostVariableField.setPreferredSize(new java.awt.Dimension(150, 35));
        variableCostVariableField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                variableCostVariableFieldFocusLost(evt);
            }
        });
        variableCostVariableField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                variableCostVariableFieldjTextField25ActionPerformed(evt);
            }
        });

        actsCostVariableField.setEnabled(false);
        actsCostVariableField.setPreferredSize(new java.awt.Dimension(150, 35));
        actsCostVariableField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                actsCostVariableFieldFocusLost(evt);
            }
        });
        actsCostVariableField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actsCostVariableFieldjTextField26ActionPerformed(evt);
            }
        });

        jLabel88.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(255, 255, 255));
        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel88.setText("Les Acts:");

        jLabel89.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(255, 255, 255));
        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel89.setText("Variables:");

        employeeCostVariableField.setEnabled(false);
        employeeCostVariableField.setPreferredSize(new java.awt.Dimension(150, 35));
        employeeCostVariableField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                employeeCostVariableFieldFocusLost(evt);
            }
        });
        employeeCostVariableField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeCostVariableFieldjTextField27ActionPerformed(evt);
            }
        });

        jLabel90.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(255, 255, 255));
        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel90.setText("Les Servier:");

        jLabel91.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(255, 255, 255));
        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel91.setText("Les Repat:");

        foodCostVariableField.setEnabled(false);
        foodCostVariableField.setPreferredSize(new java.awt.Dimension(150, 35));
        foodCostVariableField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                foodCostVariableFieldFocusLost(evt);
            }
        });
        foodCostVariableField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foodCostVariableFieldActionPerformed(evt);
            }
        });

        clarqueCostVariableField.setEnabled(false);
        clarqueCostVariableField.setPreferredSize(new java.awt.Dimension(150, 35));
        clarqueCostVariableField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                clarqueCostVariableFieldFocusLost(evt);
            }
        });
        clarqueCostVariableField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clarqueCostVariableFieldActionPerformed(evt);
            }
        });

        jLabel92.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(255, 255, 255));
        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel92.setText("Clarque:");

        jLabel93.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(255, 255, 255));
        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel93.setText("Monton:");
        jLabel93.setPreferredSize(new java.awt.Dimension(200, 25));

        MissionCostVariableField.setEnabled(false);
        MissionCostVariableField.setPreferredSize(new java.awt.Dimension(150, 35));
        MissionCostVariableField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                MissionCostVariableFieldFocusLost(evt);
            }
        });
        MissionCostVariableField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MissionCostVariableFieldActionPerformed(evt);
            }
        });

        jLabel94.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(255, 255, 255));
        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel94.setText("Frais De Mission:");

        totalVariableField.setEnabled(false);
        totalVariableField.setPreferredSize(new java.awt.Dimension(150, 35));
        totalVariableField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalVariableFieldActionPerformed(evt);
            }
        });

        kButton13.setText("Modifier");
        kButton13.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton13.setkHoverForeGround(new java.awt.Color(27, 32, 44));
        kButton13.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton13MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                kButton13MouseEntered(evt);
            }
        });
        kButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton13kButton4ActionPerformed(evt);
            }
        });

        kButton14.setText("Enregestrier");
        kButton14.setEnabled(false);
        kButton14.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton14.setkHoverForeGround(new java.awt.Color(27, 32, 44));
        kButton14.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton14MouseClicked(evt);
            }
        });

        jLabel95.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(255, 255, 255));
        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel95.setText("DA");

        jLabel96.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(255, 255, 255));
        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel96.setText("DA");

        jLabel97.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(255, 255, 255));
        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel97.setText("DA");

        jLabel98.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(255, 255, 255));
        jLabel98.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel98.setText("DA");

        jLabel99.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(255, 255, 255));
        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel99.setText("DA");

        jLabel100.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(255, 255, 255));
        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel100.setText("DA");

        jLabel101.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(255, 255, 255));
        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel101.setText("DA");

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 339, Short.MAX_VALUE)
                        .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel43Layout.createSequentialGroup()
                                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(clarqueCostVariableField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(foodCostVariableField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(employeeCostVariableField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(actsCostVariableField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(variableCostVariableField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel96)
                                    .addComponent(jLabel97)
                                    .addComponent(jLabel98)
                                    .addComponent(jLabel99)
                                    .addComponent(jLabel100)))
                            .addGroup(jPanel43Layout.createSequentialGroup()
                                .addComponent(MissionCostVariableField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel95))))
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(kButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator13)
                            .addComponent(totalVariableField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel101)))
                .addGap(44, 44, 44))
            .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel43Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel43Layout.createSequentialGroup()
                            .addGap(50, 50, 50)
                            .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel92, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel89, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel88, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel94, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(566, Short.MAX_VALUE)))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MissionCostVariableField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel95))
                .addGap(40, 40, 40)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clarqueCostVariableField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel96))
                .addGap(40, 40, 40)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(employeeCostVariableField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel97))
                .addGap(39, 39, 39)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(foodCostVariableField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel98))
                    .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(actsCostVariableField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel99))
                .addGap(40, 40, 40)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(variableCostVariableField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel100))
                .addGap(40, 40, 40)
                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalVariableField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel101))
                .addGap(229, 229, 229))
            .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel43Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(39, 39, 39)
                    .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(46, 46, 46)
                    .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(46, 46, 46)
                    .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(122, 122, 122)
                    .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(46, 46, 46)
                    .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(328, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(350, 350, 350)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(350, 350, 350))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel42Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        jTabbedPane4.addTab("Charge Variables", jPanel42);

        jPanel44.setBackground(new java.awt.Color(248, 248, 249));

        jPanel45.setBackground(new java.awt.Color(27, 32, 44));
        jPanel45.setPreferredSize(new java.awt.Dimension(800, 610));

        jLabel102.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel102.setForeground(new java.awt.Color(255, 255, 255));
        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel102.setText("Description:");
        jLabel102.setPreferredSize(new java.awt.Dimension(200, 25));

        VCTransitionField.setEnabled(false);
        VCTransitionField.setPreferredSize(new java.awt.Dimension(150, 35));
        VCTransitionField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VCTransitionFieldActionPerformed(evt);
            }
        });

        PCTransitionField.setEnabled(false);
        PCTransitionField.setPreferredSize(new java.awt.Dimension(150, 35));
        PCTransitionField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PCTransitionFieldActionPerformed(evt);
            }
        });

        jLabel103.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(255, 255, 255));
        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel103.setText("Les Parc:");

        jLabel104.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel104.setForeground(new java.awt.Color(255, 255, 255));
        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel104.setText("Variables:");

        EPACTransitionField.setEnabled(false);
        EPACTransitionField.setPreferredSize(new java.awt.Dimension(150, 35));
        EPACTransitionField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EPACTransitionFieldActionPerformed(evt);
            }
        });

        jLabel105.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel105.setForeground(new java.awt.Color(255, 255, 255));
        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel105.setText("EPA:");

        jLabel106.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(255, 255, 255));
        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel106.setText("SiL:");

        SILCTransitionField.setEnabled(false);
        SILCTransitionField.setPreferredSize(new java.awt.Dimension(150, 35));
        SILCTransitionField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SILCTransitionFieldActionPerformed(evt);
            }
        });

        ECTransitionField.setEnabled(false);
        ECTransitionField.setPreferredSize(new java.awt.Dimension(150, 35));
        ECTransitionField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ECTransitionFieldActionPerformed(evt);
            }
        });

        jLabel107.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel107.setForeground(new java.awt.Color(255, 255, 255));
        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel107.setText("Les Servier:");

        jLabel108.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel108.setForeground(new java.awt.Color(255, 255, 255));
        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel108.setText("Monton:");
        jLabel108.setPreferredSize(new java.awt.Dimension(200, 25));

        TCTransitionField.setEnabled(false);
        TCTransitionField.setPreferredSize(new java.awt.Dimension(150, 35));
        TCTransitionField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TCTransitionFieldActionPerformed(evt);
            }
        });

        jLabel109.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(255, 255, 255));
        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel109.setText("Frais Transit:");

        totalTransitionField.setEnabled(false);
        totalTransitionField.setPreferredSize(new java.awt.Dimension(150, 35));
        totalTransitionField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalTransitionFieldActionPerformed(evt);
            }
        });

        kButton15.setText("Modifier");
        kButton15.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton15.setkHoverForeGround(new java.awt.Color(27, 32, 44));
        kButton15.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton15MouseClicked(evt);
            }
        });
        kButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton15ActionPerformed(evt);
            }
        });

        kButton16.setText("Enregestrier");
        kButton16.setEnabled(false);
        kButton16.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton16.setkHoverForeGround(new java.awt.Color(27, 32, 44));
        kButton16.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton16MouseClicked(evt);
            }
        });

        jLabel110.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(255, 255, 255));
        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel110.setText("DA");

        jLabel111.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(255, 255, 255));
        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel111.setText("DA");

        jLabel112.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel112.setForeground(new java.awt.Color(255, 255, 255));
        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel112.setText("DA");

        jLabel113.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel113.setForeground(new java.awt.Color(255, 255, 255));
        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel113.setText("DA");

        jLabel114.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel114.setForeground(new java.awt.Color(255, 255, 255));
        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel114.setText("DA");

        jLabel115.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel115.setForeground(new java.awt.Color(255, 255, 255));
        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel115.setText("DA");

        jLabel116.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel116.setForeground(new java.awt.Color(255, 255, 255));
        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel116.setText("DA");

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel45Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 364, Short.MAX_VALUE)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel45Layout.createSequentialGroup()
                                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(ECTransitionField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(SILCTransitionField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(EPACTransitionField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(PCTransitionField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(VCTransitionField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel111)
                                    .addComponent(jLabel112)
                                    .addComponent(jLabel113)
                                    .addComponent(jLabel114)
                                    .addComponent(jLabel115)))
                            .addGroup(jPanel45Layout.createSequentialGroup()
                                .addComponent(TCTransitionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel110))))
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addComponent(kButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator14)
                            .addComponent(totalTransitionField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel116)
                        .addGap(6, 6, 6)))
                .addGap(14, 14, 14))
            .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel45Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel45Layout.createSequentialGroup()
                            .addGap(50, 50, 50)
                            .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel107, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel104, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel103, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel109, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel105, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(566, Short.MAX_VALUE)))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel45Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TCTransitionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel110))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ECTransitionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel111))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(EPACTransitionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel112))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(SILCTransitionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel113))
                            .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addComponent(PCTransitionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel114))
                .addGap(40, 40, 40)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(VCTransitionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel115))
                .addGap(41, 41, 41)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalTransitionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel116))
                .addGap(191, 191, 191))
            .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel45Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(39, 39, 39)
                    .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(46, 46, 46)
                    .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(46, 46, 46)
                    .addComponent(jLabel105, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(122, 122, 122)
                    .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(46, 46, 46)
                    .addComponent(jLabel104, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(297, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(317, 317, 317)
                .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(350, 350, 350))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Charge Transit", jPanel44);

        jPanel46.setBackground(new java.awt.Color(248, 248, 249));

        jPanel47.setBackground(new java.awt.Color(27, 32, 44));
        jPanel47.setMaximumSize(new java.awt.Dimension(800, 610));
        jPanel47.setPreferredSize(new java.awt.Dimension(800, 610));

        DCustomField.setEnabled(false);
        DCustomField.setPreferredSize(new java.awt.Dimension(150, 35));
        DCustomField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DCustomFieldjTextField11ActionPerformed(evt);
            }
        });

        TVACustomField.setEnabled(false);
        TVACustomField.setPreferredSize(new java.awt.Dimension(150, 35));
        TVACustomField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TVACustomFieldjTextField9ActionPerformed(evt);
            }
        });

        TCSCustomField.setEnabled(false);
        TCSCustomField.setPreferredSize(new java.awt.Dimension(150, 35));
        TCSCustomField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TCSCustomFieldjTextField5ActionPerformed(evt);
            }
        });

        jLabel117.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel117.setForeground(new java.awt.Color(255, 255, 255));
        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel117.setText("Description:");
        jLabel117.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel118.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel118.setForeground(new java.awt.Color(27, 32, 44));
        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel118.setText("Monton:");
        jLabel118.setPreferredSize(new java.awt.Dimension(200, 25));

        QCustomField.setEnabled(false);
        QCustomField.setPreferredSize(new java.awt.Dimension(150, 35));
        QCustomField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QCustomFieldjTextField12ActionPerformed(evt);
            }
        });

        jLabel119.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel119.setForeground(new java.awt.Color(255, 255, 255));
        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel119.setText("TCS:");

        jLabel201.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel201.setForeground(new java.awt.Color(255, 255, 255));
        jLabel201.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel201.setText("D.Douane:");

        jLabel202.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel202.setForeground(new java.awt.Color(255, 255, 255));
        jLabel202.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel202.setText("total:");

        jLabel203.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel203.setForeground(new java.awt.Color(255, 255, 255));
        jLabel203.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel203.setText("Q/Douane:");

        totalCustomField.setEnabled(false);
        totalCustomField.setPreferredSize(new java.awt.Dimension(150, 35));
        totalCustomField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalCustomFieldjTextField17ActionPerformed(evt);
            }
        });

        jLabel204.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel204.setForeground(new java.awt.Color(27, 32, 44));
        jLabel204.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel204.setText("Description:");
        jLabel204.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel205.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel205.setForeground(new java.awt.Color(27, 32, 44));
        jLabel205.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel205.setText("Monton:");
        jLabel205.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel206.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel206.setForeground(new java.awt.Color(255, 255, 255));
        jLabel206.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel206.setText("CX:");

        CXCustomfield.setEnabled(false);
        CXCustomfield.setPreferredSize(new java.awt.Dimension(150, 35));
        CXCustomfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CXCustomfieldjTextField18ActionPerformed(evt);
            }
        });

        totalCXCustomField.setEnabled(false);
        totalCXCustomField.setPreferredSize(new java.awt.Dimension(150, 35));
        totalCXCustomField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalCXCustomFieldjTextField19ActionPerformed(evt);
            }
        });

        jLabel207.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel207.setForeground(new java.awt.Color(255, 255, 255));
        jLabel207.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel207.setText("TVA:");

        jLabel208.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel208.setForeground(new java.awt.Color(255, 255, 255));
        jLabel208.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel208.setText("DA");

        jLabel209.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel209.setForeground(new java.awt.Color(255, 255, 255));
        jLabel209.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel209.setText("DA");

        jLabel210.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel210.setForeground(new java.awt.Color(255, 255, 255));
        jLabel210.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel210.setText("DA");

        jLabel211.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel211.setForeground(new java.awt.Color(255, 255, 255));
        jLabel211.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel211.setText("DA");

        jLabel212.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel212.setForeground(new java.awt.Color(255, 255, 255));
        jLabel212.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel212.setText("DA");

        jLabel213.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel213.setForeground(new java.awt.Color(255, 255, 255));
        jLabel213.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel213.setText("DA");

        jLabel214.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel214.setForeground(new java.awt.Color(255, 255, 255));
        jLabel214.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel214.setText("DA");

        kButton20.setText("Modifier");
        kButton20.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton20.setkHoverForeGround(new java.awt.Color(27, 32, 44));
        kButton20.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton20MouseClicked(evt);
            }
        });
        kButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton20ActionPerformed(evt);
            }
        });

        kButton21.setText("Enregestrier");
        kButton21.setEnabled(false);
        kButton21.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton21.setkHoverForeGround(new java.awt.Color(27, 32, 44));
        kButton21.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton21MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel47Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel47Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel117, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel47Layout.createSequentialGroup()
                                .addGap(265, 265, 265)
                                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel47Layout.createSequentialGroup()
                                        .addComponent(totalCustomField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel212)
                                        .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel47Layout.createSequentialGroup()
                                                .addGap(48, 48, 48)
                                                .addComponent(jLabel202, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(totalCXCustomField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel214))
                                            .addGroup(jPanel47Layout.createSequentialGroup()
                                                .addGap(41, 41, 41)
                                                .addComponent(jLabel206, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(CXCustomfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel213))))))
                            .addGroup(jPanel47Layout.createSequentialGroup()
                                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel201, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel207, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel203, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel47Layout.createSequentialGroup()
                                        .addComponent(DCustomField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel209))
                                    .addGroup(jPanel47Layout.createSequentialGroup()
                                        .addComponent(TVACustomField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel210))
                                    .addGroup(jPanel47Layout.createSequentialGroup()
                                        .addComponent(QCustomField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel211))
                                    .addGroup(jPanel47Layout.createSequentialGroup()
                                        .addComponent(TCSCustomField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel208)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel204, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel205, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel47Layout.createSequentialGroup()
                        .addGap(285, 285, 285)
                        .addComponent(jLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel47Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(kButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel204, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel205, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel117, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(CXCustomfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel206, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel213))
                    .addGroup(jPanel47Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel47Layout.createSequentialGroup()
                                .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66)
                                .addComponent(jLabel201, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel47Layout.createSequentialGroup()
                                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(TCSCustomField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel208))
                                .addGap(60, 60, 60)
                                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(DCustomField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel209))))))
                .addGap(61, 61, 61)
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(TVACustomField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel210))
                    .addComponent(jLabel207, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel203, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(QCustomField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel211))
                .addGap(18, 18, 18)
                .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(totalCustomField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel212))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel202, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(totalCXCustomField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel214)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kButton20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60))
        );

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                .addGap(350, 350, 350)
                .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, 793, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(350, 350, 350))
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jTabbedPane4.addTab("Charge De Douane", jPanel46);

        jPanel48.setBackground(new java.awt.Color(248, 248, 249));

        jPanel49.setBackground(new java.awt.Color(27, 32, 44));
        jPanel49.setPreferredSize(new java.awt.Dimension(500, 600));

        jLabel120.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel120.setForeground(new java.awt.Color(255, 255, 255));
        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel120.setText("Exterieur");

        EETrasportField.setEnabled(false);
        EETrasportField.setPreferredSize(new java.awt.Dimension(150, 35));
        EETrasportField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EETrasportFieldjTextField20ActionPerformed(evt);
            }
        });

        jLabel121.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel121.setForeground(new java.awt.Color(255, 255, 255));
        jLabel121.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel121.setText("Echange:");

        jLabel122.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel122.setForeground(new java.awt.Color(255, 255, 255));
        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel122.setText("Surstarie:");

        SETransportField.setEnabled(false);
        SETransportField.setPreferredSize(new java.awt.Dimension(150, 35));
        SETransportField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SETransportFieldjTextField21ActionPerformed(evt);
            }
        });

        totalETransportField.setEnabled(false);
        totalETransportField.setPreferredSize(new java.awt.Dimension(150, 35));
        totalETransportField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalETransportFieldjTextField22ActionPerformed(evt);
            }
        });

        jSeparator16.setForeground(new java.awt.Color(255, 255, 255));

        TETransportField.setEnabled(false);
        TETransportField.setPreferredSize(new java.awt.Dimension(150, 35));
        TETransportField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TETransportFieldjTextField23ActionPerformed(evt);
            }
        });

        jLabel123.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel123.setForeground(new java.awt.Color(255, 255, 255));
        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel123.setText("Transport:");

        kButton17.setText("Modifier");
        kButton17.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton17.setkHoverForeGround(new java.awt.Color(27, 32, 44));
        kButton17.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton17MouseClicked(evt);
            }
        });

        kButton18.setText("Enregestrier");
        kButton18.setEnabled(false);
        kButton18.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton18.setkHoverForeGround(new java.awt.Color(27, 32, 44));
        kButton18.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton18MouseClicked(evt);
            }
        });

        TauxETransportField.setEnabled(false);
        TauxETransportField.setPreferredSize(new java.awt.Dimension(150, 35));
        TauxETransportField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TauxETransportFieldjTextField24ActionPerformed(evt);
            }
        });

        jLabel124.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel124.setForeground(new java.awt.Color(255, 255, 255));
        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel124.setText("Taux De Change:");

        jLabel125.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel125.setForeground(new java.awt.Color(255, 255, 255));
        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel125.setText("DA");

        jLabel126.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel126.setForeground(new java.awt.Color(255, 255, 255));
        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel126.setText("DA");

        jLabel127.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel127.setForeground(new java.awt.Color(255, 255, 255));
        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel127.setText("Dev");

        jLabel140.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel140.setForeground(new java.awt.Color(255, 255, 255));
        jLabel140.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel140.setText("DA");

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel49Layout.createSequentialGroup()
                                .addGap(225, 225, 225)
                                .addComponent(jLabel120, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel49Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(kButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(kButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel49Layout.createSequentialGroup()
                                .addGap(276, 276, 276)
                                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(totalETransportField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                                    .addComponent(jSeparator16))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel140))
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel123, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel122, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel121, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel124, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel49Layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(EETrasportField, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel49Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TETransportField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SETransportField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel49Layout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addComponent(TauxETransportField, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel125)
                            .addComponent(jLabel126)
                            .addComponent(jLabel127))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel120)
                .addGap(63, 63, 63)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel121, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EETrasportField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel125))
                .addGap(60, 60, 60)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel122, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SETransportField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel126))
                .addGap(55, 55, 55)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel123, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(TETransportField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel127)))
                .addGap(55, 55, 55)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel124, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TauxETransportField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalETransportField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel140))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kButton17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        jPanel50.setBackground(new java.awt.Color(27, 32, 44));
        jPanel50.setPreferredSize(new java.awt.Dimension(500, 600));

        jLabel128.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel128.setForeground(new java.awt.Color(255, 255, 255));
        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel128.setText("Interieur");

        ITTransportField.setEnabled(false);
        ITTransportField.setPreferredSize(new java.awt.Dimension(150, 35));
        ITTransportField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ITTransportFieldjTextField28ActionPerformed(evt);
            }
        });

        jLabel129.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel129.setForeground(new java.awt.Color(255, 255, 255));
        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel129.setText("Transport:");

        jLabel130.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel130.setForeground(new java.awt.Color(255, 255, 255));
        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel130.setText("Couler:");

        ICTransportField.setEnabled(false);
        ICTransportField.setPreferredSize(new java.awt.Dimension(150, 35));
        ICTransportField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ICTransportFieldjTextField29ActionPerformed(evt);
            }
        });

        totalITransportField.setEnabled(false);
        totalITransportField.setPreferredSize(new java.awt.Dimension(150, 35));
        totalITransportField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalITransportFieldjTextField30ActionPerformed(evt);
            }
        });

        jSeparator17.setForeground(new java.awt.Color(255, 255, 255));

        IVTransportField.setEnabled(false);
        IVTransportField.setPreferredSize(new java.awt.Dimension(150, 35));
        IVTransportField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IVTransportFieldjTextField31ActionPerformed(evt);
            }
        });

        jLabel131.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel131.setForeground(new java.awt.Color(255, 255, 255));
        jLabel131.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel131.setText("Vehicle De Service:");

        kButton19.setText("Modifier");
        kButton19.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton19.setkHoverForeGround(new java.awt.Color(27, 32, 44));
        kButton19.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton19MouseClicked(evt);
            }
        });

        kButton26.setText("Enregestrier");
        kButton26.setEnabled(false);
        kButton26.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton26.setkHoverForeGround(new java.awt.Color(27, 32, 44));
        kButton26.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton26MouseClicked(evt);
            }
        });

        jLabel141.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel141.setForeground(new java.awt.Color(255, 255, 255));
        jLabel141.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel141.setText("DA");

        jLabel142.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel142.setForeground(new java.awt.Color(255, 255, 255));
        jLabel142.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel142.setText("DA");

        jLabel143.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel143.setForeground(new java.awt.Color(255, 255, 255));
        jLabel143.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel143.setText("DA");

        jLabel144.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel144.setForeground(new java.awt.Color(255, 255, 255));
        jLabel144.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel144.setText("DA");

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(225, 225, 225)
                .addComponent(jLabel128, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addGap(291, 291, 291)
                        .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel50Layout.createSequentialGroup()
                                .addComponent(totalITransportField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel144))))
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel50Layout.createSequentialGroup()
                                .addComponent(kButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(kButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel50Layout.createSequentialGroup()
                                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel131, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(jLabel130, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel129, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(38, 38, 38)
                                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ICTransportField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ITTransportField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(IVTransportField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel141)
                                    .addComponent(jLabel142)
                                    .addComponent(jLabel143))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(0, 34, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel128)
                .addGap(70, 70, 70)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ITTransportField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel141))
                .addGap(60, 60, 60)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel130, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ICTransportField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel142))
                .addGap(60, 60, 60)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel131, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IVTransportField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel143))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalITransportField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel144))
                .addGap(18, 18, 18)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kButton26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Charge De Transport", jPanel48);

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane4)
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane4)
        );

        jTabbedPane1.addTab("Charge De Dossier", jPanel36);

        jPanel12.setLayout(new java.awt.BorderLayout());

        jPanel21.setBackground(new java.awt.Color(248, 248, 249));

        employeeSearchField.setForeground(new java.awt.Color(153, 153, 153));
        employeeSearchField.setText("recherché par n.facture...");
        employeeSearchField.setPreferredSize(new java.awt.Dimension(250, 22));
        employeeSearchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                employeeSearchFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                employeeSearchFieldFocusLost(evt);
            }
        });
        employeeSearchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeSearchFieldActionPerformed(evt);
            }
        });
        employeeSearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                employeeSearchFieldKeyReleased(evt);
            }
        });

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prixdrevien/images/211818_search_icon(6).png"))); // NOI18N

        jLabel219.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel219.setForeground(new java.awt.Color(27, 32, 44));
        jLabel219.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel219.setText("Les Employée");
        jLabel219.setPreferredSize(new java.awt.Dimension(200, 25));

        employeeSearchCombo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        employeeSearchCombo.setForeground(new java.awt.Color(27, 32, 44));
        employeeSearchCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N.Facture", "Id", "Banc", "Port" }));
        employeeSearchCombo.setPreferredSize(new java.awt.Dimension(100, 22));
        employeeSearchCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeSearchComboActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(27, 32, 44));
        jLabel18.setText("recherché par:");

        kButton3.setForeground(new java.awt.Color(27, 32, 44));
        kButton3.setText("Ajouter ");
        kButton3.setkForeGround(new java.awt.Color(27, 32, 44));
        kButton3.setkHoverEndColor(new java.awt.Color(192, 192, 192));
        kButton3.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton3.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton3MouseClicked(evt);
            }
        });

        employeeTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        employeeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Nom", "Némuro", "Post", "Date de Début", "Salare"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        employeeTable.setDoubleBuffered(true);
        employeeTable.setMaximumSize(new java.awt.Dimension(1500, 900));
        employeeTable.setSelectionBackground(new java.awt.Color(51, 0, 51));
        employeeTable.setShowGrid(true);
        employeeTable.getTableHeader().setReorderingAllowed(false);
        employeeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employeeTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                employeeTableMouseEntered(evt);
            }
        });
        jScrollPane9.setViewportView(employeeTable);

        countEmployeeField.setFont(new java.awt.Font("Yu Gothic", 1, 14)); // NOI18N
        countEmployeeField.setForeground(new java.awt.Color(27, 32, 44));
        countEmployeeField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        countEmployeeField.setEnabled(false);
        countEmployeeField.setPreferredSize(new java.awt.Dimension(300, 55));

        totalEmployeeField.setFont(new java.awt.Font("Yu Gothic", 1, 14)); // NOI18N
        totalEmployeeField.setForeground(new java.awt.Color(27, 32, 44));
        totalEmployeeField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalEmployeeField.setEnabled(false);
        totalEmployeeField.setMinimumSize(new java.awt.Dimension(300, 55));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jLabel219, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98)
                .addComponent(jLabel18)
                .addGap(12, 12, 12)
                .addComponent(employeeSearchCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(kButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(126, Short.MAX_VALUE))
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(countEmployeeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalEmployeeField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane9)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(jLabel219, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(employeeSearchCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(employeeSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel17)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(countEmployeeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalEmployeeField, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel12.add(jPanel21, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Charge De Employée", jPanel12);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1534, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 743, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        contentTabbedPane.addTab("Les charges", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1500, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(contentTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1500, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contentTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 830, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void irSearchFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_irSearchFieldFocusGained
        irSearchField.setText("");
        irSearchField.setForeground(new java.awt.Color(27,32,44));
    }//GEN-LAST:event_irSearchFieldFocusGained

    private void irSearchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_irSearchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_irSearchFieldActionPerformed

    private void irSearchComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_irSearchComboActionPerformed
        irSearchField.setText("recherché par "+ irSearchCombo.getSelectedItem().toString()+"...");
    }//GEN-LAST:event_irSearchComboActionPerformed

    private void irSearchFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_irSearchFieldFocusLost
        if(irSearchField.getText().equals("")){
            irSearchField.setText("recherché par "+irSearchCombo.getSelectedItem().toString()+"...");
            irSearchField.setForeground(new java.awt.Color(153,153,153));
        }
    }//GEN-LAST:event_irSearchFieldFocusLost

    private void addIRPriceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addIRPriceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addIRPriceFieldActionPerformed

    private void addIRRefFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addIRRefFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addIRRefFieldActionPerformed

    private void addIRSQFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addIRSQFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addIRSQFieldActionPerformed

    private void billNumberFieldjTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billNumberFieldjTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_billNumberFieldjTextField1ActionPerformed

    private void bancFieldjTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bancFieldjTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bancFieldjTextField1ActionPerformed

    private void portFieldjTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portFieldjTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_portFieldjTextField1ActionPerformed

    private void kButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton6ActionPerformed

    private void kButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton6MouseClicked
        addFolderDialog.dispose();
    }//GEN-LAST:event_kButton6MouseClicked

    private void kButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton5MouseClicked
        if(billNumberField.getText().equals("") || bancField.getText().equals("") || portField.getText().equals("")){
            addFolderErrorLabel.setText("Vous Devez Entrer tout Les Informations");
            
        }else{
            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedDate = date.format(dateFormater);
            String billNumber = billNumberField.getText();
            String banc = bancField.getText();
            String port = portField.getText();
            folder.createFolder(billNumber, banc, port, formattedDate);
            maindb.closeConnection(maindb.conn, folder.ps, folder.rs);
            addFolderDialog.dispose();
            renderFoldersTable(folder.getAll());
            billNumberField.setText("");
            bancField.setText("");
            portField.setText("");
        }
    }//GEN-LAST:event_kButton5MouseClicked
    
    
    private void addIRFolderFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addIRFolderFieldActionPerformed
       // TODO add your handling code here:
    }//GEN-LAST:event_addIRFolderFieldActionPerformed

    private void addIRQOSFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addIRQOSFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addIRQOSFieldActionPerformed

    private void addIRQFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addIRQFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addIRQFieldActionPerformed

    private void kButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton7MouseClicked
        addIRPriceField.setText("");
        addIRQField.setText("");
        addIRQOSField.setText("");
        addIRSQField.setText("");
        addIRTauxField.setText("");
        addIRFolderField.setText("");
        addIRRefField.setText("");
        addIRErrorLabel.setText("");
        addIRDialog.dispose();
    }//GEN-LAST:event_kButton7MouseClicked

    private void kButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton7ActionPerformed

    private void addIRTauxFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addIRTauxFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addIRTauxFieldActionPerformed

    private void kButton10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton10MouseClicked
        String airf = addIRFolderField.getText();
        String airref = addIRRefField.getText();
        String airdes = addIRDesField.getText();
        String four = addIRFourField.getText();
        String unit = addIRuntCombo.getSelectedItem().toString();
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDate = date.format(dateFormater);
        
        if(!addIRPriceField.getText().equals("") && !addIRQField.getText().equals("") &&
        !addIRQOSField.getText().equals("") && !addIRSQField.getText().equals("") &&
        !addIRTauxField.getText().equals("") && !airf.equals("") && !airref.equals("")){
            try{
                double airprice = Double.parseDouble(addIRPriceField.getText());
                
                double airqos = Double.parseDouble(addIRQOSField.getText());
                double airsq = Double.parseDouble(addIRSQField.getText());
                double airtaux = Double.parseDouble(addIRTauxField.getText());
                
                int id = folder.getFolderId(airf);
                System.out.println(id);
                if(id > -1){
                    
                    addIRPriceField.setText("");
                    addIRQField.setText("");
                    addIRQOSField.setText("");
                    addIRSQField.setText("");
                    addIRTauxField.setText("");
                    addIRFolderField.setText("");
                    addIRRefField.setText("");
                    addIRErrorLabel.setText("");
                    
                    ir.createIR(id, airref, airdes, formattedDate, unit, airsq, airqos,  airprice, airtaux, fournisseur.getFourId(four));
                    renderIRTable(ir.getAll());
                    addIRDialog.dispose();
                }else{
                    addIRErrorLabel.setText("Il n'y a pas de Dossier avec ce Numéro de Facture");
                }
            }catch(java.lang.NumberFormatException nfe){
                addIRErrorLabel.setText("Entrez une Valeur Valide S'il Vous Plait");
            }
        }
        else{
            addIRErrorLabel.setText("Entrez Tout Les Informations S'il Vous Plait");
        }
    }//GEN-LAST:event_kButton10MouseClicked

    private void addIRQOSFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addIRQOSFieldKeyReleased
        if(!addIRSQField.getText().equals("")){
            double q = Double.parseDouble(addIRQOSField.getText()) * Double.parseDouble(addIRSQField.getText());
            addIRQField.setText(String.valueOf(q));
        }
    }//GEN-LAST:event_addIRQOSFieldKeyReleased

    private void addIRSQFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addIRSQFieldKeyReleased
        if(!addIRQOSField.getText().equals("")){
            double q = Double.parseDouble(addIRQOSField.getText()) * Double.parseDouble(addIRSQField.getText());
            addIRQField.setText(String.valueOf(q));
        }
    }//GEN-LAST:event_addIRSQFieldKeyReleased

    private void kButton26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton26MouseClicked
        if(ITTransportField.isEnabled()){
            int n = JOptionPane.showConfirmDialog(
                this,
                "Avez-vous sur d'avoir terminé cette opération",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
            if(n == 0){
                Double it = Double.valueOf(ITTransportField.getText());
                Double ic = Double.valueOf(ICTransportField.getText());
                Double iv = Double.valueOf(IVTransportField.getText());

                int cost_id = folder.getCostId(folderSelectedRow).get(6);
                folder.updateITransport(cost_id, it, ic, iv);
                folder.updateTransport(folderSelectedRow);
                folder.updateTotal(folderSelectedRow);
                ir.updateTotal(folderSelectedRow);
                renderInternalTransport(folderSelectedRow);
                renderFoldersTable(folder.getAll());
                renderIRTable(ir.getAll());
            }

            ITTransportField.setEnabled(false);
            ICTransportField.setEnabled(false);
            IVTransportField.setEnabled(false);

        }
    }//GEN-LAST:event_kButton26MouseClicked

    private void kButton19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton19MouseClicked
        ITTransportField.setEnabled(true);
        ICTransportField.setEnabled(true);
        IVTransportField.setEnabled(true);

    }//GEN-LAST:event_kButton19MouseClicked

    private void IVTransportFieldjTextField31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IVTransportFieldjTextField31ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IVTransportFieldjTextField31ActionPerformed

    private void totalITransportFieldjTextField30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalITransportFieldjTextField30ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalITransportFieldjTextField30ActionPerformed

    private void ICTransportFieldjTextField29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ICTransportFieldjTextField29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ICTransportFieldjTextField29ActionPerformed

    private void ITTransportFieldjTextField28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ITTransportFieldjTextField28ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ITTransportFieldjTextField28ActionPerformed

    private void TauxETransportFieldjTextField24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TauxETransportFieldjTextField24ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TauxETransportFieldjTextField24ActionPerformed

    private void kButton18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton18MouseClicked
        if(EETrasportField.isEnabled()){
            int n = JOptionPane.showConfirmDialog(
                this,
                "Avez-vous sur d'avoir terminé cette opération",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
            if(n == 0){
                Double eet = Double.valueOf(EETrasportField.getText());
                Double set = Double.valueOf(SETransportField.getText());
                Double tet = Double.valueOf(TETransportField.getText());
                Double taux = Double.valueOf(TauxETransportField.getText());

                int cost_id = folder.getCostId(folderSelectedRow).get(5);
                folder.updateETransport(cost_id, eet, set, tet, taux);
                folder.updateTransport(folderSelectedRow);
                folder.updateTotal(folderSelectedRow);
                ir.updateTotal(folderSelectedRow);
                renderExternalTransport(folderSelectedRow);
                renderFoldersTable(folder.getAll());
                renderIRTable(ir.getAll());
            }

            EETrasportField.setEnabled(false);
            SETransportField.setEnabled(false);
            TETransportField.setEnabled(false);
            TauxETransportField.setEnabled(false);
        }
    }//GEN-LAST:event_kButton18MouseClicked

    private void kButton17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton17MouseClicked
        EETrasportField.setEnabled(true);
        SETransportField.setEnabled(true);
        TETransportField.setEnabled(true);
        TauxETransportField.setEnabled(true);
    }//GEN-LAST:event_kButton17MouseClicked

    private void TETransportFieldjTextField23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TETransportFieldjTextField23ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TETransportFieldjTextField23ActionPerformed

    private void totalETransportFieldjTextField22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalETransportFieldjTextField22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalETransportFieldjTextField22ActionPerformed

    private void SETransportFieldjTextField21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SETransportFieldjTextField21ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SETransportFieldjTextField21ActionPerformed

    private void EETrasportFieldjTextField20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EETrasportFieldjTextField20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EETrasportFieldjTextField20ActionPerformed

    private void kButton21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton21MouseClicked
        if(TCSCustomField.isEnabled()){
            int n = JOptionPane.showConfirmDialog(
                this,
                "Avez-vous sur d'avoir terminé cette opération",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
            if(n == 0){
                Double tcs = Double.valueOf(TCSCustomField.getText());
                Double tva = Double.valueOf(TVACustomField.getText());
                Double qc = Double.valueOf(QCustomField.getText());
                Double dc = Double.valueOf(DCustomField.getText());
                Double cxc = Double.valueOf(CXCustomfield.getText());

                int cost_id = folder.getCostId(folderSelectedRow).get(2);
                folder.updateCustomCost(cost_id, tcs, tva, qc, dc, cxc);
                folder.updateTotal(folderSelectedRow);
                ir.updateTotal(folderSelectedRow);
                renderCustomCosts(folderSelectedRow);
                renderFoldersTable(folder.getAll());
                renderIRTable(ir.getAllDesc());
            }

            CXCustomfield.setEnabled(false);
            QCustomField.setEnabled(false);
            TVACustomField.setEnabled(false);
            TCSCustomField.setEnabled(false);
            DCustomField.setEnabled(false);
        }
    }//GEN-LAST:event_kButton21MouseClicked

    private void kButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton20ActionPerformed

    private void kButton20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton20MouseClicked

        CXCustomfield.setEnabled(true);
        QCustomField.setEnabled(true);
        TVACustomField.setEnabled(true);
        TCSCustomField.setEnabled(true);
        DCustomField.setEnabled(true);
    }//GEN-LAST:event_kButton20MouseClicked

    private void totalCXCustomFieldjTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalCXCustomFieldjTextField19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalCXCustomFieldjTextField19ActionPerformed

    private void CXCustomfieldjTextField18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CXCustomfieldjTextField18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CXCustomfieldjTextField18ActionPerformed

    private void totalCustomFieldjTextField17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalCustomFieldjTextField17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalCustomFieldjTextField17ActionPerformed

    private void QCustomFieldjTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QCustomFieldjTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_QCustomFieldjTextField12ActionPerformed

    private void TCSCustomFieldjTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TCSCustomFieldjTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCSCustomFieldjTextField5ActionPerformed

    private void TVACustomFieldjTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TVACustomFieldjTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TVACustomFieldjTextField9ActionPerformed

    private void DCustomFieldjTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DCustomFieldjTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DCustomFieldjTextField11ActionPerformed

    private void kButton16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton16MouseClicked
        if(TCTransitionField.isEnabled()){
            int n = JOptionPane.showConfirmDialog(
                this,
                "Avez-vous sur d'avoir terminé cette opération",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
            if(n == 0){
                Double tc = Double.valueOf(TCTransitionField.getText());
                Double ec = Double.valueOf(ECTransitionField.getText());
                Double epa = Double.valueOf(EPACTransitionField.getText());
                Double pc = Double.valueOf(PCTransitionField.getText());
                Double vc = Double.valueOf(VCTransitionField.getText());
                Double sil = Double.valueOf(SILCTransitionField.getText());

                int cost_id = folder.getCostId(folderSelectedRow).get(4);
                folder.updateTransitionCost(cost_id, tc, ec, epa, sil, pc, vc);
                folder.updateTotal(folderSelectedRow);
                ir.updateTotal(folderSelectedRow);
                renderTransitionCosts(folderSelectedRow);
                renderFoldersTable(folder.getAll());
                renderIRTable(ir.getAllDesc());
            }
            TCTransitionField.setEnabled(false);
            ECTransitionField.setEnabled(false);
            EPACTransitionField.setEnabled(false);
            PCTransitionField.setEnabled(false);
            VCTransitionField.setEnabled(false);
            SILCTransitionField.setEnabled(false);
        }
    }//GEN-LAST:event_kButton16MouseClicked

    private void kButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton15ActionPerformed

    private void kButton15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton15MouseClicked
        TCTransitionField.setEnabled(true);
        ECTransitionField.setEnabled(true);
        EPACTransitionField.setEnabled(true);
        SILCTransitionField.setEnabled(true);
        PCTransitionField.setEnabled(true);
        VCTransitionField.setEnabled(true);
        
    }//GEN-LAST:event_kButton15MouseClicked

    private void totalTransitionFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalTransitionFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalTransitionFieldActionPerformed

    private void TCTransitionFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TCTransitionFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCTransitionFieldActionPerformed

    private void ECTransitionFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ECTransitionFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ECTransitionFieldActionPerformed

    private void SILCTransitionFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SILCTransitionFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SILCTransitionFieldActionPerformed

    private void EPACTransitionFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EPACTransitionFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EPACTransitionFieldActionPerformed

    private void PCTransitionFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PCTransitionFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PCTransitionFieldActionPerformed

    private void VCTransitionFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VCTransitionFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VCTransitionFieldActionPerformed

    private void kButton14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton14MouseClicked

        if(MissionCostVariableField.isEnabled()){
            int n = JOptionPane.showConfirmDialog(
                this,
                "Avez-vous sur d'avoir terminé cette opération",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
            if(n == 0){
                Double m = Double.valueOf(MissionCostVariableField.getText());
                Double c = Double.valueOf(clarqueCostVariableField.getText());
                Double e = Double.valueOf(employeeCostVariableField.getText());
                Double f = Double.valueOf(foodCostVariableField.getText());
                Double a = Double.valueOf(actsCostVariableField.getText());
                Double v = Double.valueOf(variableCostVariableField.getText());

                int cost_id = folder.getCostId(folderSelectedRow).get(0);
                folder.updateVariableCost(cost_id, m, c, e, f, a, v);
                folder.updateTotal(folderSelectedRow);
                ir.updateTotal(folderSelectedRow);
                renderVariableCosts(folderSelectedRow);
                renderFoldersTable(folder.getAll());
                renderIRTable(ir.getAllDesc());
            }
            MissionCostVariableField.setEnabled(false);
            clarqueCostVariableField.setEnabled(false);
            employeeCostVariableField.setEnabled(false);
            foodCostVariableField.setEnabled(false);
            actsCostVariableField.setEnabled(false);
            variableCostVariableField.setEnabled(false);
        }

    }//GEN-LAST:event_kButton14MouseClicked

    private void kButton13kButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton13kButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton13kButton4ActionPerformed

    private void kButton13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton13MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton13MouseEntered

    private void kButton13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton13MouseClicked
        MissionCostVariableField.setEnabled(true);
        clarqueCostVariableField.setEnabled(true);
        employeeCostVariableField.setEnabled(true);
        foodCostVariableField.setEnabled(true);
        actsCostVariableField.setEnabled(true);
        variableCostVariableField.setEnabled(true);
    }//GEN-LAST:event_kButton13MouseClicked

    private void totalVariableFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalVariableFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalVariableFieldActionPerformed

    private void MissionCostVariableFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MissionCostVariableFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MissionCostVariableFieldActionPerformed

    private void clarqueCostVariableFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clarqueCostVariableFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clarqueCostVariableFieldActionPerformed

    private void foodCostVariableFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foodCostVariableFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_foodCostVariableFieldActionPerformed

    private void employeeCostVariableFieldjTextField27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeCostVariableFieldjTextField27ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employeeCostVariableFieldjTextField27ActionPerformed

    private void actsCostVariableFieldjTextField26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actsCostVariableFieldjTextField26ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_actsCostVariableFieldjTextField26ActionPerformed

    private void variableCostVariableFieldjTextField25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_variableCostVariableFieldjTextField25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_variableCostVariableFieldjTextField25ActionPerformed

    private void kButton25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton25MouseClicked
        RDBancField.setEnabled(true);
        SCBancField.setEnabled(true);
        DCBancField.setEnabled(true);
        DD1BancField.setEnabled(true);
        DD2BancField.setEnabled(true);
        DD3BancField.setEnabled(true);
        CCBancField.setEnabled(true);
    }//GEN-LAST:event_kButton25MouseClicked

    private void kButton24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton24MouseClicked
        if(RDBancField.isEnabled()){
            int n = JOptionPane.showConfirmDialog(
                this,
                "Avez-vous sur d'avoir terminé cette opération",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
            if(n == 0){
                Double rd = Double.valueOf(RDBancField.getText());
                Double sc = Double.valueOf(SCBancField.getText());
                Double dc = Double.valueOf(DCBancField.getText());
                Double dd1 = Double.valueOf(DD1BancField.getText());
                Double dd2 = Double.valueOf(DD2BancField.getText());
                Double dd3 = Double.valueOf(DD3BancField.getText());
                Double cc = Double.valueOf(CCBancField.getText());

                int cost_id = folder.getCostId(folderSelectedRow).get(1);
                folder.updateBancCost(cost_id, rd, sc, dc, dd1, dd2, dd3, cc);
                folder.updateTotal(folderSelectedRow);
                ir.updateTotal(folderSelectedRow);
                renderBancCosts(folderSelectedRow);
                renderFoldersTable(folder.getAll());
                renderIRTable(ir.getAllDesc());
            }
            RDBancField.setEnabled(false);
            SCBancField.setEnabled(false);
            DCBancField.setEnabled(false);
            DD1BancField.setEnabled(false);
            DD2BancField.setEnabled(false);
            DD3BancField.setEnabled(false);
            CCBancField.setEnabled(false);
        }
    }//GEN-LAST:event_kButton24MouseClicked

    private void totalBancFieldjTextField16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalBancFieldjTextField16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalBancFieldjTextField16ActionPerformed

    private void RDBancFieldjTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RDBancFieldjTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RDBancFieldjTextField1ActionPerformed

    private void SCBancFieldjTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SCBancFieldjTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SCBancFieldjTextField4ActionPerformed

    private void DD1BancFieldjTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DD1BancFieldjTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DD1BancFieldjTextField8ActionPerformed

    private void DCBancFieldjTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DCBancFieldjTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DCBancFieldjTextField10ActionPerformed

    private void DD2BancFieldjTextField13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DD2BancFieldjTextField13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DD2BancFieldjTextField13ActionPerformed

    private void DD3BancFieldjTextField14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DD3BancFieldjTextField14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DD3BancFieldjTextField14ActionPerformed

    private void CCBancFieldjTextField15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CCBancFieldjTextField15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CCBancFieldjTextField15ActionPerformed

    private void foldersTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_foldersTableMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_foldersTableMouseEntered

    private void foldersTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_foldersTableMouseClicked
        folderSelectedRow = Integer.parseInt(foldersTable.getValueAt(foldersTable.getSelectedRow(),0).toString());
        
        renderVariableCosts(folderSelectedRow);
        renderBancCosts(folderSelectedRow);
        renderTransitionCosts(folderSelectedRow);
        renderCustomCosts(folderSelectedRow);
        renderExternalTransport(folderSelectedRow);
        renderInternalTransport(folderSelectedRow);

    }//GEN-LAST:event_foldersTableMouseClicked

    private void foldersSearchComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foldersSearchComboActionPerformed
        foldersSearchField.setText("recherché par "+ foldersSearchCombo.getSelectedItem().toString()+"...");
    }//GEN-LAST:event_foldersSearchComboActionPerformed

    private void foldersSearchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_foldersSearchFieldKeyReleased
        if(foldersSearchField.getText().equals("")){
            renderFoldersTable(folder.getAll());
        }else if(foldersSearchCombo.getSelectedItem().equals("Id") && !foldersSearchField.getText().equals("")){
            int researched = Integer.parseInt(foldersSearchField.getText());
            renderFoldersTable(folder.Search("Id", researched));
        }else{
            String researched = foldersSearchField.getText();
            String key = foldersSearchCombo.getSelectedItem().toString();
            renderFoldersTable(folder.Search(key, researched));
        }
    }//GEN-LAST:event_foldersSearchFieldKeyReleased

    private void foldersSearchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foldersSearchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_foldersSearchFieldActionPerformed

    private void foldersSearchFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_foldersSearchFieldFocusLost
        if(foldersSearchField.getText().equals("")){
            foldersSearchField.setText("recherché par "+foldersSearchCombo.getSelectedItem().toString()+"...");
            foldersSearchField.setForeground(new java.awt.Color(153,153,153));
        }
    }//GEN-LAST:event_foldersSearchFieldFocusLost

    private void foldersSearchFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_foldersSearchFieldFocusGained
        foldersSearchField.setText("");
        foldersSearchField.setForeground(new java.awt.Color(27,32,44));
    }//GEN-LAST:event_foldersSearchFieldFocusGained

    private void kButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton1MouseClicked
        addFolderDialog.setVisible(true);
    }//GEN-LAST:event_kButton1MouseClicked

    private void irSearchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_irSearchFieldKeyReleased
        if(irSearchField.getText().equals("")){
            renderIRTable(ir.getAllDesc());
        }else if(irSearchCombo.getSelectedItem().equals("Id") && !irSearchField.getText().equals("")){
            int researched = Integer.parseInt(irSearchField.getText());
            renderIRTable(ir.Search("Id", researched));
        }else{
            String researched = irSearchField.getText();
            String key = irSearchCombo.getSelectedItem().toString();
            renderIRTable(ir.Search(key, researched));
        }
    }//GEN-LAST:event_irSearchFieldKeyReleased

    private void irTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_irTableMouseClicked
        irSelectedRow = Integer.parseInt(irTable.getValueAt(irTable.getSelectedRow(),0).toString());
        renderIRFoldersTable(ir.Search("Id", irSelectedRow));
        //irFoldersLabel.setText(foldersTable.getValueAt(irTable.getSelectedRow(),1).toString());
        irFoldersDialog.setVisible(true);
    }//GEN-LAST:event_irTableMouseClicked

    private void kButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton4MouseClicked
        addIRDialog.setVisible(true);
    }//GEN-LAST:event_kButton4MouseClicked

    private void kButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton4ActionPerformed

    private void addIRFolderFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addIRFolderFieldKeyReleased
        String researched = addIRFolderField.getText();
        DefaultListModel model = new DefaultListModel();
         
        try{
            rs = folder.Search("N.Facture", researched);
            while(rs.next()){
                String billNumber = rs.getString("f.n_facture");
                
                model.addElement(billNumber);
                model.addElement("----------------------------------------------");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        billNumberList.setModel(model);
    }//GEN-LAST:event_addIRFolderFieldKeyReleased

    private void billNumberListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_billNumberListMouseClicked
        if(billNumberList.getSelectedIndex()%2==0){
            addIRFolderField.setText(billNumberList.getSelectedValue());
            billNumberList.setModel(new DefaultListModel());
        }
    }//GEN-LAST:event_billNumberListMouseClicked

    private void buyIRPriceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyIRPriceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buyIRPriceFieldActionPerformed

    private void buyIRSQFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyIRSQFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buyIRSQFieldActionPerformed

    private void buyIRSQFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buyIRSQFieldKeyReleased
        if(!buyIRQOSField.getText().equals("")){
            double q = Double.parseDouble(buyIRQOSField.getText()) * Double.parseDouble(buyIRSQField.getText());
            buyIRQField.setText(String.valueOf(q));
        }
    }//GEN-LAST:event_buyIRSQFieldKeyReleased

    private void buyIRQOSFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyIRQOSFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buyIRQOSFieldActionPerformed

    private void buyIRQOSFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buyIRQOSFieldKeyReleased
        if(!buyIRSQField.getText().equals("")){
            double q = Double.parseDouble(buyIRQOSField.getText()) * Double.parseDouble(buyIRSQField.getText());
            buyIRQField.setText(String.valueOf(q));
        }
    }//GEN-LAST:event_buyIRQOSFieldKeyReleased

    private void buyIRQFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyIRQFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buyIRQFieldActionPerformed

    private void buyIRTauxFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyIRTauxFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buyIRTauxFieldActionPerformed

    private void buyIRFolderFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyIRFolderFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buyIRFolderFieldActionPerformed

    private void buyIRFolderFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buyIRFolderFieldKeyReleased
        String researched = buyIRFolderField.getText();
        DefaultListModel model = new DefaultListModel();
         
        try{
            rs = folder.Search("N.Facture", researched);
            while(rs.next()){
                String billNumber = rs.getString("f.n_facture");
                
                model.addElement(billNumber);
                model.addElement("----------------------------------------------");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        buyBillNumberList.setModel(model);
    }//GEN-LAST:event_buyIRFolderFieldKeyReleased

    private void kButton8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton8MouseClicked
        buyIRDialog.dispose();
    }//GEN-LAST:event_kButton8MouseClicked

    private void kButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton8ActionPerformed

    private void kButton11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton11MouseClicked
        String airf = buyIRFolderField.getText();
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDate = date.format(dateFormater);
        String four = buyIRFourField.getText();
        if(!buyIRPriceField.getText().equals("") && !buyIRQField.getText().equals("") &&
        !buyIRQOSField.getText().equals("") && !buyIRSQField.getText().equals("") &&
        !buyIRTauxField.getText().equals("") && !airf.equals("")){
            try{
                double airprice = Double.parseDouble(buyIRPriceField.getText());
                
                double airqos = Double.parseDouble(buyIRQOSField.getText());
                double airsq = Double.parseDouble(buyIRSQField.getText());
                double airtaux = Double.parseDouble(buyIRTauxField.getText());
                
                int id = folder.getFolderId(airf);
                
                if(id > -1){
                    
                    buyIRPriceField.setText("");
                    buyIRQField.setText("");
                    buyIRQOSField.setText("");
                    buyIRSQField.setText("");
                    buyIRTauxField.setText("");
                    buyIRFolderField.setText("");
                    buyIRFourField.setText("");
                    buyIRErrorLabel.setText("");
                    
                    ir.createIRAchat(id, irSelectedRow,  airsq, airqos,  airprice, airtaux, formattedDate, fournisseur.getFourId(four));
                    renderIRFoldersTable(ir.Search("Id",id));
                    renderIRTable(ir.getAllDesc());
                    buyIRDialog.dispose();
                }else{
                    buyIRErrorLabel.setText("Il n'y a pas de Dossier avec ce Numéro de Facture");
                }
            }catch(java.lang.NumberFormatException nfe){
                buyIRErrorLabel.setText("Entrez une Valeur Valide S'il Vous Plait");
            }
        }
        else{
            buyIRErrorLabel.setText("Entrez Tout Les Informations S'il Vous Plait");
        }
    }//GEN-LAST:event_kButton11MouseClicked

    private void buyIRFourFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyIRFourFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buyIRFourFieldActionPerformed

    private void buyBillNumberListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buyBillNumberListMouseClicked
        if(buyBillNumberList.getSelectedIndex()%2==0){
            buyIRFolderField.setText(buyBillNumberList.getSelectedValue());
            buyBillNumberList.setModel(new DefaultListModel());
        }
    }//GEN-LAST:event_buyBillNumberListMouseClicked

    private void buyBillNumberListKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buyBillNumberListKeyReleased
        
    }//GEN-LAST:event_buyBillNumberListKeyReleased

    private void buyBillNumberListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_buyBillNumberListValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_buyBillNumberListValueChanged

    private void fourTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fourTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_fourTableMouseClicked

    private void fourSearchFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fourSearchFieldFocusGained
        fourSearchField.setText("");
        fourSearchField.setForeground(new java.awt.Color(27,32,44));
    }//GEN-LAST:event_fourSearchFieldFocusGained

    private void fourSearchFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fourSearchFieldFocusLost
        if(fourSearchField.getText().equals("")){
            fourSearchField.setText("recherché par "+fourSearchCombo.getSelectedItem().toString()+"...");
            fourSearchField.setForeground(new java.awt.Color(153,153,153));
        }
    }//GEN-LAST:event_fourSearchFieldFocusLost

    private void fourSearchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fourSearchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fourSearchFieldActionPerformed

    private void fourSearchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fourSearchFieldKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_fourSearchFieldKeyReleased

    private void fourSearchComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fourSearchComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fourSearchComboActionPerformed

    private void kButton9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton9MouseClicked
        addFournisseurDialog.setVisible(true);
    }//GEN-LAST:event_kButton9MouseClicked

    private void kButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton9ActionPerformed

    private void employeeSearchFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_employeeSearchFieldFocusGained
        employeeSearchField.setText("");
        employeeSearchField.setForeground(new java.awt.Color(27,32,44));
    }//GEN-LAST:event_employeeSearchFieldFocusGained

    private void employeeSearchFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_employeeSearchFieldFocusLost
        if(employeeSearchField.getText().equals("")){
            employeeSearchField.setText("recherché par "+employeeSearchCombo.getSelectedItem().toString()+"...");
            employeeSearchField.setForeground(new java.awt.Color(153,153,153));
        }
    }//GEN-LAST:event_employeeSearchFieldFocusLost

    private void employeeSearchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeSearchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employeeSearchFieldActionPerformed

    private void employeeSearchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_employeeSearchFieldKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_employeeSearchFieldKeyReleased

    private void employeeSearchComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeSearchComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employeeSearchComboActionPerformed

    private void kButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton3MouseClicked
        Month[] months =  java.time.Month.values();
        LocalDateTime current = LocalDateTime.now();
        
        // set Months Combo Values
        for(int i= 0;i<months.length;i++){
            monthEmployeeCombo.addItem(months[i].toString());
        }
        // set year Combo Values
        for(int i = 2000;i<Integer.parseInt(current.toString().subSequence(0, 4).toString())+1;i++){
            yearEmployeeCombo.addItem(String.valueOf(i));
        }
        String currentMonth = monthEmployeeCombo.getSelectedItem().toString();
        // set day Combo Values
        for(int i = 1;i<Month.valueOf(currentMonth).maxLength()+1;i++){
            dayEmployeeCombo.addItem(String.valueOf(i));
        }
        monthEmployeeCombo.setSelectedIndex(Integer.parseInt(current.toString().subSequence(5, 7).toString())-1);
        yearEmployeeCombo.setSelectedIndex(Integer.parseInt(current.toString().subSequence(0, 4).toString())-2000);
        dayEmployeeCombo.setSelectedIndex(Integer.parseInt(current.toString().subSequence(8, 10).toString())-1);
        addEmployeeDialog.setVisible(true);
    }//GEN-LAST:event_kButton3MouseClicked

    private void employeeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeeTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_employeeTableMouseClicked

    private void employeeTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeeTableMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_employeeTableMouseEntered

    private void fourListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fourListMouseClicked
        if(fourList.getSelectedIndex()%2==0){
            addIRFourField.setText(fourList.getSelectedValue());
            fourList.setModel(new DefaultListModel());
        }
    }//GEN-LAST:event_fourListMouseClicked

    private void addIRFourFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addIRFourFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addIRFourFieldActionPerformed

    private void addIRFourFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addIRFourFieldKeyReleased
        String researched = addIRFourField.getText();
        DefaultListModel model = new DefaultListModel();
         
        try{
            rs = fournisseur.Search( researched);
            while(rs.next()){
                String billNumber = rs.getString("nom");
                
                model.addElement(billNumber);
                model.addElement("----------------------------------------------");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            maindb.closeConnection(rs);
        }
        fourList.setModel(model);
    }//GEN-LAST:event_addIRFourFieldKeyReleased

    private void billNumberListKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billNumberListKeyReleased
        
    }//GEN-LAST:event_billNumberListKeyReleased

    private void addFournisseurNomFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFournisseurNomFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addFournisseurNomFieldActionPerformed

    private void addFournisseurComagnieFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFournisseurComagnieFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addFournisseurComagnieFieldActionPerformed

    private void addFournisseurPayFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFournisseurPayFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addFournisseurPayFieldActionPerformed

    private void addFournisseurPayFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addFournisseurPayFieldKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_addFournisseurPayFieldKeyReleased

    private void kButton12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton12MouseClicked
        addFournisseurNomField.setText("");
        addFournisseurComagnieField.setText("");
        addFournisseurPayField.setText("");
        addFournisseurDialog.dispose();
    }//GEN-LAST:event_kButton12MouseClicked

    private void kButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton12ActionPerformed

    private void kButton22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton22MouseClicked
        String nom = addFournisseurNomField.getText();
        String pay = addFournisseurPayField.getText();
        String comp = addFournisseurComagnieField.getText();
        
        fournisseur.createFour(nom, pay, comp);
        renderFourTable(fournisseur.getAll());
        addFournisseurNomField.setText("");
        addFournisseurComagnieField.setText("");
        addFournisseurPayField.setText("");
        addFournisseurDialog.dispose();
        
    }//GEN-LAST:event_kButton22MouseClicked

    private void buyFourListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buyFourListMouseClicked
        if(buyFourList.getSelectedIndex()%2==0){
            buyIRFourField.setText(buyFourList.getSelectedValue());
            buyFourList.setModel(new DefaultListModel());
        }
    }//GEN-LAST:event_buyFourListMouseClicked

    private void buyFourListKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buyFourListKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_buyFourListKeyReleased

    private void buyFourListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_buyFourListValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_buyFourListValueChanged

    private void buyIRFourFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buyIRFourFieldKeyReleased
        String researched = buyIRFourField.getText();
        DefaultListModel model = new DefaultListModel();
         
        try{
            rs = fournisseur.Search(researched);
            while(rs.next()){
                String billNumber = rs.getString("nom");
                
                model.addElement(billNumber);
                model.addElement("----------------------------------------------");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        buyFourList.setModel(model);
    }//GEN-LAST:event_buyIRFourFieldKeyReleased

    private void nameEmployeeFieldjTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameEmployeeFieldjTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameEmployeeFieldjTextField1ActionPerformed

    private void postEmployeeFieldjTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postEmployeeFieldjTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_postEmployeeFieldjTextField1ActionPerformed

    private void numberEmployeeFieldjTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numberEmployeeFieldjTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numberEmployeeFieldjTextField1ActionPerformed

    private void kButton23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton23MouseClicked
        addEmployeeDialog.dispose();
        nameEmployeeField.setText("");
        postEmployeeField.setText("");
        numberEmployeeField.setText("");
        salaryEmployeeField.setText("");
        
    }//GEN-LAST:event_kButton23MouseClicked

    private void kButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton23ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton23ActionPerformed

    private void kButton27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton27MouseClicked
        String name = nameEmployeeField.getText();
        String post = postEmployeeField.getText();
        String number = numberEmployeeField.getText();
        int year = Integer.parseInt(yearEmployeeCombo.getSelectedItem().toString());
        int month = monthEmployeeCombo.getSelectedIndex()+1;
        int day = Integer.parseInt(dayEmployeeCombo.getSelectedItem().toString());
        
        String date = LocalDateTime.of(year, month, day, 0, 0).toString().substring(0, 10);
        
        
        double salary;
        if(!name.equals("") && !post.equals("") && !number.equals("") && !salaryEmployeeField.getText().equals("")){
            try{
                salary = Double.parseDouble(salaryEmployeeField.getText());
                Integer.parseInt(number);
                employee.createEmployee(name, post, number, date, salary);
                renderEmployeeTable(employee.getAll());
                addEmployeeDialog.dispose();
            }catch(java.lang.NumberFormatException nfe){
                addEmployeeErrorLabel.setText("Entrez une Valeur Valide S'il Vous Plait");
            }
        }else{
            addEmployeeErrorLabel.setText("Entrez Tout Les Informations S'il Vous Plait");
        }
    }//GEN-LAST:event_kButton27MouseClicked

    private void salaryEmployeeFieldjTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salaryEmployeeFieldjTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_salaryEmployeeFieldjTextField1ActionPerformed

    private void monthEmployeeComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthEmployeeComboActionPerformed
        Month[] months =  java.time.Month.values();
        LocalDateTime current = LocalDateTime.now();
        String currentMonth = monthEmployeeCombo.getSelectedItem().toString();
        dayEmployeeCombo.removeAllItems();
        for(int i = 1;i<Month.valueOf(currentMonth).maxLength()+1;i++){
            dayEmployeeCombo.addItem(String.valueOf(i));
        }
        dayEmployeeCombo.setSelectedIndex(Integer.parseInt(current.toString().subSequence(8, 10).toString())-1);
    }//GEN-LAST:event_monthEmployeeComboActionPerformed

    private void numberEmployeeFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numberEmployeeFieldKeyReleased
 
    }//GEN-LAST:event_numberEmployeeFieldKeyReleased

    private void MissionCostVariableFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_MissionCostVariableFieldFocusLost
     
    }//GEN-LAST:event_MissionCostVariableFieldFocusLost

    private void clarqueCostVariableFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_clarqueCostVariableFieldFocusLost
   
    }//GEN-LAST:event_clarqueCostVariableFieldFocusLost

    private void employeeCostVariableFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_employeeCostVariableFieldFocusLost
    
    }//GEN-LAST:event_employeeCostVariableFieldFocusLost

    private void foodCostVariableFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_foodCostVariableFieldFocusLost
     
    }//GEN-LAST:event_foodCostVariableFieldFocusLost

    private void actsCostVariableFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_actsCostVariableFieldFocusLost
      
    }//GEN-LAST:event_actsCostVariableFieldFocusLost

    private void variableCostVariableFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_variableCostVariableFieldFocusLost
        
    }//GEN-LAST:event_variableCostVariableFieldFocusLost

    private void irFoldersSearchFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_irFoldersSearchFieldFocusGained
        irFoldersSearchField.setText("");
        irFoldersSearchField.setForeground(new java.awt.Color(27,32,44));
    }//GEN-LAST:event_irFoldersSearchFieldFocusGained

    private void irFoldersSearchFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_irFoldersSearchFieldFocusLost
        if(irFoldersSearchField.getText().equals("")){
            irFoldersSearchField.setText("recherché par "+irFoldersSearchCombo.getSelectedItem().toString()+"...");
            irFoldersSearchField.setForeground(new java.awt.Color(153,153,153));
        }
    }//GEN-LAST:event_irFoldersSearchFieldFocusLost

    private void irFoldersSearchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_irFoldersSearchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_irFoldersSearchFieldActionPerformed

    private void irFoldersSearchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_irFoldersSearchFieldKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_irFoldersSearchFieldKeyReleased

    private void irFoldersSearchComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_irFoldersSearchComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_irFoldersSearchComboActionPerformed

    private void kButton28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton28MouseClicked
        buyIRDialog.setVisible(true);
    }//GEN-LAST:event_kButton28MouseClicked

    private void kButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton28ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton28ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CCBancField;
    private javax.swing.JTextField CXCustomfield;
    private javax.swing.JTextField DCBancField;
    private javax.swing.JTextField DCustomField;
    private javax.swing.JTextField DD1BancField;
    private javax.swing.JTextField DD2BancField;
    private javax.swing.JTextField DD3BancField;
    private javax.swing.JTextField ECTransitionField;
    private javax.swing.JTextField EETrasportField;
    private javax.swing.JTextField EPACTransitionField;
    private javax.swing.JTextField ICTransportField;
    private javax.swing.JTextField ITTransportField;
    private javax.swing.JTextField IVTransportField;
    private javax.swing.JTextField MissionCostVariableField;
    private javax.swing.JTextField PCTransitionField;
    private javax.swing.JTextField QCustomField;
    private javax.swing.JTextField RDBancField;
    private javax.swing.JTextField SCBancField;
    private javax.swing.JTextField SETransportField;
    private javax.swing.JTextField SILCTransitionField;
    private javax.swing.JTextField TCSCustomField;
    private javax.swing.JTextField TCTransitionField;
    private javax.swing.JTextField TETransportField;
    private javax.swing.JTextField TVACustomField;
    private javax.swing.JTextField TauxETransportField;
    private javax.swing.JTextField VCTransitionField;
    private javax.swing.JTextField actsCostVariableField;
    private javax.swing.JDialog addEmployeeDialog;
    private javax.swing.JLabel addEmployeeErrorLabel;
    private javax.swing.JDialog addFolderDialog;
    private javax.swing.JLabel addFolderErrorLabel;
    private javax.swing.JTextField addFournisseurComagnieField;
    private javax.swing.JDialog addFournisseurDialog;
    private javax.swing.JTextField addFournisseurNomField;
    private javax.swing.JTextField addFournisseurPayField;
    private javax.swing.JTextArea addIRDesField;
    private javax.swing.JDialog addIRDialog;
    private javax.swing.JLabel addIRErrorLabel;
    private javax.swing.JTextField addIRFolderField;
    private javax.swing.JTextField addIRFourField;
    private javax.swing.JTextField addIRPriceField;
    private javax.swing.JTextField addIRQField;
    private javax.swing.JTextField addIRQOSField;
    private javax.swing.JTextField addIRRefField;
    private javax.swing.JTextField addIRSQField;
    private javax.swing.JTextField addIRTauxField;
    private javax.swing.JComboBox<String> addIRuntCombo;
    private javax.swing.JTextField bancField;
    private javax.swing.JTextField billNumberField;
    private javax.swing.JList<String> billNumberList;
    private javax.swing.JList<String> buyBillNumberList;
    private javax.swing.JList<String> buyFourList;
    private javax.swing.JDialog buyIRDialog;
    private javax.swing.JLabel buyIRErrorLabel;
    private javax.swing.JTextField buyIRFolderField;
    private javax.swing.JTextField buyIRFourField;
    private javax.swing.JTextField buyIRPriceField;
    private javax.swing.JTextField buyIRQField;
    private javax.swing.JTextField buyIRQOSField;
    private javax.swing.JTextField buyIRSQField;
    private javax.swing.JTextField buyIRTauxField;
    private javax.swing.JTextField clarqueCostVariableField;
    private javax.swing.JTabbedPane contentTabbedPane;
    private javax.swing.JTextField countEmployeeField;
    private javax.swing.JComboBox<String> dayEmployeeCombo;
    private javax.swing.JTextField employeeCostVariableField;
    private javax.swing.JComboBox<String> employeeSearchCombo;
    private javax.swing.JTextField employeeSearchField;
    private javax.swing.JTable employeeTable;
    private javax.swing.JComboBox<String> foldersSearchCombo;
    private javax.swing.JTextField foldersSearchField;
    private javax.swing.JTable foldersTable;
    private javax.swing.JTextField foodCostVariableField;
    private javax.swing.JList<String> fourList;
    private javax.swing.JComboBox<String> fourSearchCombo;
    private javax.swing.JTextField fourSearchField;
    private javax.swing.JTable fourTable;
    private javax.swing.JDialog irFoldersDialog;
    private javax.swing.JComboBox<String> irFoldersSearchCombo;
    private javax.swing.JTextField irFoldersSearchField;
    private javax.swing.JTable irFoldersTable;
    private javax.swing.JComboBox<String> irSearchCombo;
    private javax.swing.JTextField irSearchField;
    private javax.swing.JTable irTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel191;
    private javax.swing.JLabel jLabel192;
    private javax.swing.JLabel jLabel193;
    private javax.swing.JLabel jLabel194;
    private javax.swing.JLabel jLabel195;
    private javax.swing.JLabel jLabel196;
    private javax.swing.JLabel jLabel197;
    private javax.swing.JLabel jLabel198;
    private javax.swing.JLabel jLabel199;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel200;
    private javax.swing.JLabel jLabel201;
    private javax.swing.JLabel jLabel202;
    private javax.swing.JLabel jLabel203;
    private javax.swing.JLabel jLabel204;
    private javax.swing.JLabel jLabel205;
    private javax.swing.JLabel jLabel206;
    private javax.swing.JLabel jLabel207;
    private javax.swing.JLabel jLabel208;
    private javax.swing.JLabel jLabel209;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel210;
    private javax.swing.JLabel jLabel211;
    private javax.swing.JLabel jLabel212;
    private javax.swing.JLabel jLabel213;
    private javax.swing.JLabel jLabel214;
    private javax.swing.JLabel jLabel215;
    private javax.swing.JLabel jLabel216;
    private javax.swing.JLabel jLabel217;
    private javax.swing.JLabel jLabel218;
    private javax.swing.JLabel jLabel219;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel220;
    private javax.swing.JLabel jLabel221;
    private javax.swing.JLabel jLabel222;
    private javax.swing.JLabel jLabel223;
    private javax.swing.JLabel jLabel224;
    private javax.swing.JLabel jLabel225;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane4;
    private com.k33ptoo.components.KButton kButton1;
    private com.k33ptoo.components.KButton kButton10;
    private com.k33ptoo.components.KButton kButton11;
    private com.k33ptoo.components.KButton kButton12;
    private com.k33ptoo.components.KButton kButton13;
    private com.k33ptoo.components.KButton kButton14;
    private com.k33ptoo.components.KButton kButton15;
    private com.k33ptoo.components.KButton kButton16;
    private com.k33ptoo.components.KButton kButton17;
    private com.k33ptoo.components.KButton kButton18;
    private com.k33ptoo.components.KButton kButton19;
    private com.k33ptoo.components.KButton kButton20;
    private com.k33ptoo.components.KButton kButton21;
    private com.k33ptoo.components.KButton kButton22;
    private com.k33ptoo.components.KButton kButton23;
    private com.k33ptoo.components.KButton kButton24;
    private com.k33ptoo.components.KButton kButton25;
    private com.k33ptoo.components.KButton kButton26;
    private com.k33ptoo.components.KButton kButton27;
    private com.k33ptoo.components.KButton kButton28;
    private com.k33ptoo.components.KButton kButton3;
    private com.k33ptoo.components.KButton kButton4;
    private com.k33ptoo.components.KButton kButton5;
    private com.k33ptoo.components.KButton kButton6;
    private com.k33ptoo.components.KButton kButton7;
    private com.k33ptoo.components.KButton kButton8;
    private com.k33ptoo.components.KButton kButton9;
    private javax.swing.JComboBox<String> monthEmployeeCombo;
    private javax.swing.JTextField nameEmployeeField;
    private javax.swing.JTextField numberEmployeeField;
    private javax.swing.JTextField portField;
    private javax.swing.JTextField postEmployeeField;
    private javax.swing.JTextField salaryEmployeeField;
    private javax.swing.JTextField totalBancField;
    private javax.swing.JTextField totalCXCustomField;
    private javax.swing.JTextField totalCustomField;
    private javax.swing.JTextField totalETransportField;
    private javax.swing.JTextField totalEmployeeField;
    private javax.swing.JTextField totalITransportField;
    private javax.swing.JTextField totalTransitionField;
    private javax.swing.JTextField totalVariableField;
    private javax.swing.JTextField variableCostVariableField;
    private javax.swing.JComboBox<String> yearEmployeeCombo;
    // End of variables declaration//GEN-END:variables
}

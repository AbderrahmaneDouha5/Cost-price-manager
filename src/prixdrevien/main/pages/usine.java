
package prixdrevien.main.pages;

import java.util.HashSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import prixdrevien.db.issentials;
import prixdrevien.db.maindb;
import java.sql.ResultSet;

public class usine extends javax.swing.JPanel {
    issentials iss;
    String db;
    public usine(String db) {
        this.db = db;
        iss = new issentials(db);
        initComponents();
        renderFactoryCosts();
        
    }

    private void updateFactory(int dp, int yp, int en,
            double ec, double rc, double fc, double repc,
            double tc){
        
        iss.update("UPATE factory "
                + "SET daily_dp = "+dp
                +", yearly_dp = "+yp+", employee_n = "+en
                +", e_c = "+ec+", r_c = "+rc+", f_c = "+fc
                +", tc = "+tc+", rep_c = "+repc+";");
    }
    private void renderFactoryCosts(){
        
       int dp;
       int yp;
       int en;
       double ec;
       double rc;
       double fc;
       double repc;
       double tc;
       
       
       
       try{
           ResultSet rs = iss.get("SELECT * FROM factory;");
           while(rs.next()){
               
               dp = rs.getInt("daily_p");
               yp = rs.getInt("yearly_p");
               en = rs.getInt("employee_n");
               ec = rs.getDouble("e_c");
               fc = rs.getDouble("f_C");
               repc = rs.getDouble("rep_c");
               rc = rs.getDouble("r_c");
               tc = rs.getDouble("t_c");
               
               updateFactory(dp, yp, en, ec, rc, fc, repc, tc);
  
           }
           maindb.closeConnection(rs);
       }catch(Exception e){
            e.printStackTrace();
        }

    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel200 = new javax.swing.JLabel();
        jLabel198 = new javax.swing.JLabel();
        enFactoryField = new javax.swing.JTextField();
        ypFactoryField = new javax.swing.JTextField();
        dpFactoryField = new javax.swing.JTextField();
        jLabel196 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel201 = new javax.swing.JLabel();
        ecFactoryField = new javax.swing.JTextField();
        tcFactoryField = new javax.swing.JTextField();
        jLabel202 = new javax.swing.JLabel();
        rcFactoryField = new javax.swing.JTextField();
        jLabel203 = new javax.swing.JLabel();
        jLabel204 = new javax.swing.JLabel();
        fcFactoryField = new javax.swing.JTextField();
        repcFactoryField = new javax.swing.JTextField();
        jLabel205 = new javax.swing.JLabel();
        kButton24 = new com.k33ptoo.components.KButton();
        kButton25 = new com.k33ptoo.components.KButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(248, 248, 249));
        setPreferredSize(new java.awt.Dimension(1500, 900));

        jPanel1.setBackground(new java.awt.Color(248, 248, 249));
        jPanel1.setPreferredSize(new java.awt.Dimension(950, 50));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(248, 248, 249));

        jPanel4.setBackground(new java.awt.Color(27, 32, 44));
        jPanel4.setPreferredSize(new java.awt.Dimension(1300, 600));

        jLabel200.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel200.setForeground(new java.awt.Color(255, 255, 255));
        jLabel200.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel200.setText("Numéro d'employé:");

        jLabel198.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel198.setForeground(new java.awt.Color(255, 255, 255));
        jLabel198.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel198.setText("Production Annuelle:");

        enFactoryField.setEnabled(false);
        enFactoryField.setPreferredSize(new java.awt.Dimension(150, 35));
        enFactoryField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enFactoryFieldjTextField4ActionPerformed(evt);
            }
        });

        ypFactoryField.setEnabled(false);
        ypFactoryField.setPreferredSize(new java.awt.Dimension(150, 35));
        ypFactoryField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ypFactoryFieldjTextField1ActionPerformed(evt);
            }
        });

        dpFactoryField.setEnabled(false);
        dpFactoryField.setPreferredSize(new java.awt.Dimension(150, 35));
        dpFactoryField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dpFactoryFieldjTextField10ActionPerformed(evt);
            }
        });

        jLabel196.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel196.setForeground(new java.awt.Color(255, 255, 255));
        jLabel196.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel196.setText("Production Quotidienne:");

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Informations General");

        jLabel201.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel201.setForeground(new java.awt.Color(255, 255, 255));
        jLabel201.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel201.setText("Frais de L'électricité:");

        ecFactoryField.setEnabled(false);
        ecFactoryField.setPreferredSize(new java.awt.Dimension(150, 35));
        ecFactoryField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ecFactoryFieldjTextField4ActionPerformed(evt);
            }
        });

        tcFactoryField.setEnabled(false);
        tcFactoryField.setPreferredSize(new java.awt.Dimension(150, 35));
        tcFactoryField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tcFactoryFieldjTextField4ActionPerformed(evt);
            }
        });

        jLabel202.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel202.setForeground(new java.awt.Color(255, 255, 255));
        jLabel202.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel202.setText("Frais de Transport:");

        rcFactoryField.setEnabled(false);
        rcFactoryField.setPreferredSize(new java.awt.Dimension(150, 35));
        rcFactoryField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rcFactoryFieldjTextField4ActionPerformed(evt);
            }
        });

        jLabel203.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel203.setForeground(new java.awt.Color(255, 255, 255));
        jLabel203.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel203.setText("Frais du Loyer:");

        jLabel204.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel204.setForeground(new java.awt.Color(255, 255, 255));
        jLabel204.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel204.setText("Frais des Repas:");

        fcFactoryField.setEnabled(false);
        fcFactoryField.setPreferredSize(new java.awt.Dimension(150, 35));
        fcFactoryField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fcFactoryFieldjTextField4ActionPerformed(evt);
            }
        });

        repcFactoryField.setEnabled(false);
        repcFactoryField.setPreferredSize(new java.awt.Dimension(150, 35));
        repcFactoryField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repcFactoryFieldjTextField4ActionPerformed(evt);
            }
        });

        jLabel205.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel205.setForeground(new java.awt.Color(255, 255, 255));
        jLabel205.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel205.setText("Frais de Réparation:");

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(kButton25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(kButton24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel203, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel198, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel196, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(jLabel200, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(ypFactoryField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(enFactoryField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dpFactoryField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(rcFactoryField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(130, 130, 130)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel204, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(fcFactoryField, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel201, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(ecFactoryField, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel202, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel205, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(repcFactoryField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tcFactoryField, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE))))))
                .addGap(28, 28, 28))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel196, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dpFactoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ecFactoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel201, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tcFactoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel202, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel198, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ypFactoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(enFactoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel200, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rcFactoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel203, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(repcFactoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel205, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fcFactoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel204, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kButton24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(132, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(248, 248, 249));

        jLabel1.setBackground(new java.awt.Color(27, 32, 44));
        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Usine");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1277, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void enFactoryFieldjTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enFactoryFieldjTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_enFactoryFieldjTextField4ActionPerformed

    private void ypFactoryFieldjTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ypFactoryFieldjTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ypFactoryFieldjTextField1ActionPerformed

    private void dpFactoryFieldjTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dpFactoryFieldjTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dpFactoryFieldjTextField10ActionPerformed

    private void ecFactoryFieldjTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ecFactoryFieldjTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ecFactoryFieldjTextField4ActionPerformed

    private void tcFactoryFieldjTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tcFactoryFieldjTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tcFactoryFieldjTextField4ActionPerformed

    private void rcFactoryFieldjTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rcFactoryFieldjTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rcFactoryFieldjTextField4ActionPerformed

    private void fcFactoryFieldjTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fcFactoryFieldjTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fcFactoryFieldjTextField4ActionPerformed

    private void repcFactoryFieldjTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repcFactoryFieldjTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_repcFactoryFieldjTextField4ActionPerformed

    private void kButton24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton24MouseClicked
        if(dpFactoryField.isEnabled()){
            int n = JOptionPane.showConfirmDialog(
                this,
                "Avez-vous sur d'avoir terminé cette opération",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
            if(n == 0){
                int dp = Integer.valueOf(dpFactoryField.getText());
                int yp = Integer.valueOf(ypFactoryField.getText());
                int en = Integer.valueOf(enFactoryField.getText());
                Double ec = Double.valueOf(rcFactoryField.getText());
                Double rc = Double.valueOf(ecFactoryField.getText());
                Double repc = Double.valueOf(tcFactoryField.getText());
                Double tc = Double.valueOf(repcFactoryField.getText());
                Double fc = Double.valueOf(fcFactoryField.getText());


                renderFactoryCosts();
                
            }
            dpFactoryField.setEnabled(false);
            ypFactoryField.setEnabled(false);
            enFactoryField.setEnabled(false);
            rcFactoryField.setEnabled(false);
            ecFactoryField.setEnabled(false);
            tcFactoryField.setEnabled(false);
            repcFactoryField.setEnabled(false);
            fcFactoryField.setEnabled(false);
        }
    }//GEN-LAST:event_kButton24MouseClicked

    private void kButton25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton25MouseClicked
        dpFactoryField.setEnabled(true);
        ypFactoryField.setEnabled(true);
        enFactoryField.setEnabled(true);
        rcFactoryField.setEnabled(true);
        ecFactoryField.setEnabled(true);
        tcFactoryField.setEnabled(true);
        repcFactoryField.setEnabled(true);
        fcFactoryField.setEnabled(true);
    }//GEN-LAST:event_kButton25MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField dpFactoryField;
    private javax.swing.JTextField ecFactoryField;
    private javax.swing.JTextField enFactoryField;
    private javax.swing.JTextField fcFactoryField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel196;
    private javax.swing.JLabel jLabel198;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel200;
    private javax.swing.JLabel jLabel201;
    private javax.swing.JLabel jLabel202;
    private javax.swing.JLabel jLabel203;
    private javax.swing.JLabel jLabel204;
    private javax.swing.JLabel jLabel205;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private com.k33ptoo.components.KButton kButton24;
    private com.k33ptoo.components.KButton kButton25;
    private javax.swing.JTextField rcFactoryField;
    private javax.swing.JTextField repcFactoryField;
    private javax.swing.JTextField tcFactoryField;
    private javax.swing.JTextField ypFactoryField;
    // End of variables declaration//GEN-END:variables
}

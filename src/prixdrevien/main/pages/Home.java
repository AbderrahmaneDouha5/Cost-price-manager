
package prixdrevien.main.pages;


import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import prixdrevien.main.JpanelLoader;

public class Home extends javax.swing.JFrame {
    
    products p;
    initialResources ir;
    productionLine pl = new productionLine();
    main m = new main();
    
    public Home(String db) {
        initComponents();
        JpanelLoader.jPanelLoader(contentPanel, m);
        ir = new initialResources(db);
        p = new products(db);
    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rootPanel = new javax.swing.JPanel();
        sideBarPanel = new javax.swing.JPanel();
        homeButton = new javax.swing.JButton();
        productsButton = new javax.swing.JButton();
        initialResourcesButton = new javax.swing.JButton();
        productionLineButton = new javax.swing.JButton();
        contentPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Prix De Revien");
        setBounds(new java.awt.Rectangle(200, 100, 0, 0));
        setMinimumSize(new java.awt.Dimension(1500, 900));
        setPreferredSize(new java.awt.Dimension(1550, 900));
        setResizable(false);
        setSize(new java.awt.Dimension(1550, 900));

        rootPanel.setLayout(new java.awt.BorderLayout());

        sideBarPanel.setBackground(new java.awt.Color(27, 32, 44));
        sideBarPanel.setPreferredSize(new java.awt.Dimension(50, 800));

        homeButton.setBackground(new java.awt.Color(27, 32, 44));
        homeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prixdrevien/images/216242_home_icon.png"))); // NOI18N
        homeButton.setBorder(null);
        homeButton.setPreferredSize(new java.awt.Dimension(50, 50));
        homeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeButtonMouseClicked(evt);
            }
        });
        homeButton.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                homeButtonComponentHidden(evt);
            }
        });
        sideBarPanel.add(homeButton);

        productsButton.setBackground(new java.awt.Color(27, 32, 44));
        productsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prixdrevien/images/1291777_price_price tag_pricing_product_icon(2).png"))); // NOI18N
        productsButton.setBorder(null);
        productsButton.setPreferredSize(new java.awt.Dimension(50, 50));
        productsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productsButtonMouseClicked(evt);
            }
        });
        sideBarPanel.add(productsButton);

        initialResourcesButton.setBackground(new java.awt.Color(27, 32, 44));
        initialResourcesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prixdrevien/images/7977449_environment_management_resources_natural_sustainability_icon(1).png"))); // NOI18N
        initialResourcesButton.setBorder(null);
        initialResourcesButton.setPreferredSize(new java.awt.Dimension(50, 50));
        initialResourcesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                initialResourcesButtonMouseClicked(evt);
            }
        });
        initialResourcesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initialResourcesButtonActionPerformed(evt);
            }
        });
        sideBarPanel.add(initialResourcesButton);

        productionLineButton.setBackground(new java.awt.Color(27, 32, 44));
        productionLineButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prixdrevien/images/6051092_factory_industry_manufacturing_production_icon(6)_1.png"))); // NOI18N
        productionLineButton.setBorder(null);
        productionLineButton.setPreferredSize(new java.awt.Dimension(50, 50));
        productionLineButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productionLineButtonMouseClicked(evt);
            }
        });
        productionLineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productionLineButtonActionPerformed(evt);
            }
        });
        sideBarPanel.add(productionLineButton);

        rootPanel.add(sideBarPanel, java.awt.BorderLayout.LINE_START);

        contentPanel.setBackground(new java.awt.Color(248, 248, 249));

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1450, Short.MAX_VALUE)
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 900, Short.MAX_VALUE)
        );

        rootPanel.add(contentPanel, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void productsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productsButtonMouseClicked
        JpanelLoader.jPanelLoader(contentPanel, p);
    }//GEN-LAST:event_productsButtonMouseClicked

    private void initialResourcesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_initialResourcesButtonMouseClicked
        JpanelLoader.jPanelLoader(contentPanel, ir);
    }//GEN-LAST:event_initialResourcesButtonMouseClicked

    private void productionLineButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productionLineButtonMouseClicked
        JpanelLoader.jPanelLoader(contentPanel, pl);
    }//GEN-LAST:event_productionLineButtonMouseClicked

    private void homeButtonComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_homeButtonComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_homeButtonComponentHidden

    private void homeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeButtonMouseClicked
        JpanelLoader.jPanelLoader(contentPanel, m);
    }//GEN-LAST:event_homeButtonMouseClicked

    private void productionLineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productionLineButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productionLineButtonActionPerformed

    private void initialResourcesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_initialResourcesButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_initialResourcesButtonActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contentPanel;
    private javax.swing.JButton homeButton;
    private javax.swing.JButton initialResourcesButton;
    private javax.swing.JButton productionLineButton;
    private javax.swing.JButton productsButton;
    private javax.swing.JPanel rootPanel;
    private javax.swing.JPanel sideBarPanel;
    // End of variables declaration//GEN-END:variables
}

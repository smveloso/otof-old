/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smveloso.otof.util.thumb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

/**
 *
 * @author sergio
 */
public class ThumbGui extends javax.swing.JFrame {

    /**
     * Creates new form ThumbGui
     */
    public ThumbGui() {
        initComponents();
        sliderLargura.setLabelTable(sliderLargura.createStandardLabels(100, 10));
        sliderLargura.setPaintLabels(true);
        sliderAltura.setLabelTable(sliderAltura.createStandardLabels(100, 10));
        sliderAltura.setPaintLabels(true);

        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGerar = new javax.swing.JButton();
        panelDimensoes = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        sliderLargura = new javax.swing.JSlider();
        jLabel4 = new javax.swing.JLabel();
        sliderAltura = new javax.swing.JSlider();
        lblThumbnail = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtInputFileName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtOutputFileName = new javax.swing.JTextField();
        btnInputSearch = new javax.swing.JButton();
        btnOutputSearch = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Thumb Test");

        btnGerar.setText("Gerar!");
        btnGerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarActionPerformed(evt);
            }
        });

        panelDimensoes.setBorder(javax.swing.BorderFactory.createTitledBorder("Dimensões"));

        jLabel3.setText("Largura:");

        sliderLargura.setMajorTickSpacing(40);
        sliderLargura.setMaximum(1000);
        sliderLargura.setMinimum(10);
        sliderLargura.setMinorTickSpacing(40);
        sliderLargura.setPaintTicks(true);

        jLabel4.setText("Altura:");

        sliderAltura.setMajorTickSpacing(40);
        sliderAltura.setMaximum(1000);
        sliderAltura.setMinimum(10);
        sliderAltura.setMinorTickSpacing(40);
        sliderAltura.setPaintTicks(true);

        javax.swing.GroupLayout panelDimensoesLayout = new javax.swing.GroupLayout(panelDimensoes);
        panelDimensoes.setLayout(panelDimensoesLayout);
        panelDimensoesLayout.setHorizontalGroup(
            panelDimensoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDimensoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDimensoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDimensoesLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sliderLargura, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDimensoesLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sliderAltura, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        panelDimensoesLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {sliderAltura, sliderLargura});

        panelDimensoesLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel3, jLabel4});

        panelDimensoesLayout.setVerticalGroup(
            panelDimensoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDimensoesLayout.createSequentialGroup()
                .addGroup(panelDimensoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sliderLargura, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDimensoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sliderAltura, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        panelDimensoesLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {sliderAltura, sliderLargura});

        lblThumbnail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblThumbnail.setBorder(javax.swing.BorderFactory.createTitledBorder("Visualização (tamanho fixo)"));
        lblThumbnail.setMaximumSize(new java.awt.Dimension(260, 561));
        lblThumbnail.setMinimumSize(new java.awt.Dimension(260, 561));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Arquivos de entrada e saída"));

        jLabel1.setText("Arquivo entrada:");

        txtInputFileName.setText("/home/sergio/Env/code/otof-old/");

        jLabel2.setText("Arquivo saida:");

        txtOutputFileName.setText("/home/sergio/Env/code/otof-old/");

        btnInputSearch.setText("...");
        btnInputSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInputSearchActionPerformed(evt);
            }
        });

        btnOutputSearch.setText("...");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtInputFileName)
                    .addComponent(txtOutputFileName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnInputSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOutputSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtInputFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInputSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtOutputFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOutputSearch))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDimensoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGerar))
                    .addComponent(lblThumbnail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDimensoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblThumbnail, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(btnGerar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarActionPerformed
        actionGerar();
    }//GEN-LAST:event_btnGerarActionPerformed

    private void btnInputSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInputSearchActionPerformed
        actionSearchInput();
    }//GEN-LAST:event_btnInputSearchActionPerformed

    private void actionSearchInput() {
        File file = actionChooseFile();
        if (null != file) {
            txtInputFileName.setText(file.getAbsolutePath());
        }
    }

    private void actionSearchOutput() {
        File file = actionChooseFile();
        if (null != file) {
            txtOutputFileName.setText(file.getAbsolutePath());
        }
    }
    
    private File actionChooseFile() {
        File file = null;
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (chooser.showOpenDialog(this) ==  JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }
        return file;
    }
    
    private void actionGerar() {
        
        System.out.println(">>> actionGerar()");
        
        File infile = new File(txtInputFileName.getText());
        File outfile = new File(txtOutputFileName.getText());
        int width = sliderLargura.getValue();
        int height = sliderAltura.getValue();
        
        // get raw data for jpeg
        System.err.println("Extraindo raw para dimensões: " + width + ":" + height);
        byte[] raw = DefaultThumbUtil.getInstance().makeRawThumb(infile, width, height);
        byte[] rawToDisplay = DefaultThumbUtil.getInstance().makeRawThumb(infile, 200, 300);
        
        
        
        // output to file
        System.err.println("Salvando em: " + outfile.getAbsolutePath());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outfile);
            fos.write(raw);
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Throwable whatever) {
                    System.err.println("Erro fechando fos: " + whatever.getMessage());
                }
            }
        }
        
        // output to jlabel
        System.err.println("JLabel ...");
        try {
            ImageIcon imgIcon = new ImageIcon(rawToDisplay);
            lblThumbnail.setIcon(imgIcon);
            
        } finally {
            
        }
            
        
        
        
        
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ThumbGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThumbGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThumbGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThumbGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThumbGui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGerar;
    private javax.swing.JButton btnInputSearch;
    private javax.swing.JButton btnOutputSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblThumbnail;
    private javax.swing.JPanel panelDimensoes;
    private javax.swing.JSlider sliderAltura;
    private javax.swing.JSlider sliderLargura;
    private javax.swing.JTextField txtInputFileName;
    private javax.swing.JTextField txtOutputFileName;
    // End of variables declaration//GEN-END:variables
}

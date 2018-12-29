package org.smveloso.otof.gui.dialog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author sergio
 */
public class NewAlbumDialog extends javax.swing.JDialog {

    private static final Logger logger = LoggerFactory.getLogger(NewAlbumDialog.class);
    
    public NewAlbumDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private String newAlbumName;
    private String newAlbumPath;
    private boolean completed = false;

    /**
     * 
     * @return True if user did not cancel operation. 
     */
    public boolean isCompleted() {
        return completed;
    }
    
    private void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    public String getNewAlbumName() {
        return newAlbumName;
    }

    public void setNewAlbumName(String newAlbumName) {
        this.newAlbumName = newAlbumName;
    }

    public String getNewAlbumPath() {
        return newAlbumPath;
    }

    public void setNewAlbumPath(String newAlbumPath) {
        this.newAlbumPath = newAlbumPath;
    }
    
    private void actionCreateLFSAAlbum() {
        logger.trace(">>> actionCreateLFSAAlbum()");
        //TODO VALIDATIONS !!!
        setNewAlbumName(txtLFSAName.getText());
        setNewAlbumPath(txtLFSAMountPoint.getText());
        setCompleted(true);
        actionFechar();
        logger.trace("<<< actionCreateLFSAAlbum()");
    }
    
    private void actionFechar() {
        logger.trace(">>> actionFechar()");
        this.setVisible(false);
        logger.trace("<<< actionFechar()");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabPaneMain = new javax.swing.JTabbedPane();
        pnlLocalFileSystemAlbum = new javax.swing.JPanel();
        lblLFSAAlbumName = new javax.swing.JLabel();
        txtLFSAName = new javax.swing.JTextField();
        lblLFSAMountPoint = new javax.swing.JLabel();
        txtLFSAMountPoint = new javax.swing.JTextField();
        btnLFSASearch = new javax.swing.JButton();
        btnLFSACreate = new javax.swing.JButton();
        btnLFSACancel = new javax.swing.JButton();
        pnlOtherTypes = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Create new album");

        lblLFSAAlbumName.setText("Album's name");

        txtLFSAName.setText("new album");

        lblLFSAMountPoint.setText("Mount point in local filesystem");

        btnLFSASearch.setText("jButton1");

        btnLFSACreate.setText("Create");
        btnLFSACreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLFSACreateActionPerformed(evt);
            }
        });

        btnLFSACancel.setText("Cancel");
        btnLFSACancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLFSACancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLocalFileSystemAlbumLayout = new javax.swing.GroupLayout(pnlLocalFileSystemAlbum);
        pnlLocalFileSystemAlbum.setLayout(pnlLocalFileSystemAlbumLayout);
        pnlLocalFileSystemAlbumLayout.setHorizontalGroup(
            pnlLocalFileSystemAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLocalFileSystemAlbumLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLocalFileSystemAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLFSAName)
                    .addGroup(pnlLocalFileSystemAlbumLayout.createSequentialGroup()
                        .addGroup(pnlLocalFileSystemAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLFSAAlbumName)
                            .addComponent(lblLFSAMountPoint))
                        .addGap(0, 250, Short.MAX_VALUE))
                    .addGroup(pnlLocalFileSystemAlbumLayout.createSequentialGroup()
                        .addComponent(txtLFSAMountPoint)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLFSASearch, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLocalFileSystemAlbumLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnLFSACancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLFSACreate)))
                .addContainerGap())
        );
        pnlLocalFileSystemAlbumLayout.setVerticalGroup(
            pnlLocalFileSystemAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLocalFileSystemAlbumLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLFSAAlbumName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLFSAName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLFSAMountPoint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlLocalFileSystemAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLFSAMountPoint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLFSASearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(pnlLocalFileSystemAlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLFSACreate)
                    .addComponent(btnLFSACancel))
                .addContainerGap())
        );

        tabPaneMain.addTab("Local FS Albums", pnlLocalFileSystemAlbum);

        javax.swing.GroupLayout pnlOtherTypesLayout = new javax.swing.GroupLayout(pnlOtherTypes);
        pnlOtherTypes.setLayout(pnlOtherTypesLayout);
        pnlOtherTypesLayout.setHorizontalGroup(
            pnlOtherTypesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 491, Short.MAX_VALUE)
        );
        pnlOtherTypesLayout.setVerticalGroup(
            pnlOtherTypesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 165, Short.MAX_VALUE)
        );

        tabPaneMain.addTab("Other Album Types", pnlOtherTypes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabPaneMain)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabPaneMain)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLFSACreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLFSACreateActionPerformed
        actionCreateLFSAAlbum();
    }//GEN-LAST:event_btnLFSACreateActionPerformed

    private void btnLFSACancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLFSACancelActionPerformed
        setCompleted(false);
        actionFechar();
    }//GEN-LAST:event_btnLFSACancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLFSACancel;
    private javax.swing.JButton btnLFSACreate;
    private javax.swing.JButton btnLFSASearch;
    private javax.swing.JLabel lblLFSAAlbumName;
    private javax.swing.JLabel lblLFSAMountPoint;
    private javax.swing.JPanel pnlLocalFileSystemAlbum;
    private javax.swing.JPanel pnlOtherTypes;
    private javax.swing.JTabbedPane tabPaneMain;
    private javax.swing.JTextField txtLFSAMountPoint;
    private javax.swing.JTextField txtLFSAName;
    // End of variables declaration//GEN-END:variables
}

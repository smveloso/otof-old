package org.smveloso.otof.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smveloso.otof.em.JpaManager;
import org.smveloso.otof.em.LocationDAO;
import org.smveloso.otof.facade.FacadeException;
import org.smveloso.otof.facade.ServiceFacade;
import org.smveloso.otof.gui.dialog.NewAlbumDialog;
import org.smveloso.otof.model.Album;
import org.smveloso.otof.model.LocalFileSystemAlbum;
import org.smveloso.otof.gui.tablemodel.AlbumListTableModel;
import org.smveloso.otof.gui.tablemodel.PhotoListTableModel;
import org.smveloso.otof.model.Location;
import org.smveloso.otof.model.Photo;
import org.smveloso.otof.util.thumb.DefaultThumbUtil;

/**
 *
 * @author sergiomv
 */
public class MainFrame extends javax.swing.JFrame {

    private static final Logger logger = LoggerFactory.getLogger(MainFrame.class);

    private MainFrameState state;
    
    private ServiceFacade serviceFacade;
    
    private AlbumListTableModel albumListTableModel;
    
    private PhotoListTableModel albumPhotosTableModel;
    
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        logger.debug(">>> MainFrame()");
        initLookAndFeel();
        beforeInitComponents();
        initComponents();
        afterInitComponents();
        logger.debug("<<< MainFrame()");
    }

    private void beforeInitComponents() {
        logger.debug(">>> beforeInitComponents()");
        serviceFacade = ServiceFacade.getInstance();
        this.state = new MainFrameState();
        this.albumListTableModel = new AlbumListTableModel();
        this.albumListTableModel.associateToState(state);
        this.albumPhotosTableModel = new PhotoListTableModel();
        this.albumPhotosTableModel.associateToState(state);
        logger.debug("<<< beforeInitComponents()");
    }
    
    private void afterInitComponents() {
        logger.debug(">>> afterInitComponents()");
        this.tableAlbums.getSelectionModel().addListSelectionListener(albumListSelectionListener);
        this.tableAlbumFotos.getSelectionModel().addListSelectionListener(photoListSelectionListener);
        actionLoadAllAlbums();
        logger.debug("<<< afterInitComponents()");
    }
    
    public AlbumListTableModel getAlbumListTableModel() {
        return albumListTableModel;
    }

    public PhotoListTableModel getAlbumPhotosTableModel() {
        return albumPhotosTableModel;
    }
    
    private MainFrameState getMainFrameState() {
        return this.state;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabpnlMain = new javax.swing.JTabbedPane();
        pnlSearch = new javax.swing.JPanel();
        pnlAlbumTab = new javax.swing.JPanel();
        pnlAlbumFotos = new javax.swing.JPanel();
        scrollTableAlbumFotos = new javax.swing.JScrollPane();
        tableAlbumFotos = new javax.swing.JTable();
        pnlFotoPreview = new javax.swing.JPanel();
        lblThumbnail = new javax.swing.JLabel();
        pnlAlbums = new javax.swing.JPanel();
        scrollTableAlbums = new javax.swing.JScrollPane();
        tableAlbums = new javax.swing.JTable();
        btnOpNovoAlbum = new javax.swing.JButton();
        btnOpRemoverAlbum = new javax.swing.JButton();
        btnOpUpdateAlbum = new javax.swing.JButton();
        btnOpRefreshAlbumList = new javax.swing.JButton();
        pnlHouseKeeping = new javax.swing.JPanel();
        pnlBottom = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("OTOF");

        javax.swing.GroupLayout pnlSearchLayout = new javax.swing.GroupLayout(pnlSearch);
        pnlSearch.setLayout(pnlSearchLayout);
        pnlSearchLayout.setHorizontalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1137, Short.MAX_VALUE)
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 604, Short.MAX_VALUE)
        );

        tabpnlMain.addTab("Search", pnlSearch);

        pnlAlbumFotos.setBorder(javax.swing.BorderFactory.createTitledBorder("Fotos"));

        tableAlbumFotos.setModel(getAlbumPhotosTableModel());
        tableAlbumFotos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollTableAlbumFotos.setViewportView(tableAlbumFotos);

        pnlFotoPreview.setBorder(javax.swing.BorderFactory.createTitledBorder("Preview"));

        lblThumbnail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnlFotoPreviewLayout = new javax.swing.GroupLayout(pnlFotoPreview);
        pnlFotoPreview.setLayout(pnlFotoPreviewLayout);
        pnlFotoPreviewLayout.setHorizontalGroup(
            pnlFotoPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFotoPreviewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblThumbnail, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlFotoPreviewLayout.setVerticalGroup(
            pnlFotoPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFotoPreviewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblThumbnail, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlAlbumFotosLayout = new javax.swing.GroupLayout(pnlAlbumFotos);
        pnlAlbumFotos.setLayout(pnlAlbumFotosLayout);
        pnlAlbumFotosLayout.setHorizontalGroup(
            pnlAlbumFotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAlbumFotosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollTableAlbumFotos, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFotoPreview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlAlbumFotosLayout.setVerticalGroup(
            pnlAlbumFotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAlbumFotosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAlbumFotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlFotoPreview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scrollTableAlbumFotos, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlAlbums.setBorder(javax.swing.BorderFactory.createTitledBorder("Albums"));

        tableAlbums.setModel(getAlbumListTableModel());
        tableAlbums.setName("tableAlbums"); // NOI18N
        tableAlbums.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollTableAlbums.setViewportView(tableAlbums);

        btnOpNovoAlbum.setText("Novo");
        btnOpNovoAlbum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpNovoAlbumActionPerformed(evt);
            }
        });

        btnOpRemoverAlbum.setText("Remover");

        btnOpUpdateAlbum.setText("Sincronizar");
        btnOpUpdateAlbum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpUpdateAlbumActionPerformed(evt);
            }
        });

        btnOpRefreshAlbumList.setText("R");
        btnOpRefreshAlbumList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpRefreshAlbumListActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAlbumsLayout = new javax.swing.GroupLayout(pnlAlbums);
        pnlAlbums.setLayout(pnlAlbumsLayout);
        pnlAlbumsLayout.setHorizontalGroup(
            pnlAlbumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAlbumsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAlbumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollTableAlbums, javax.swing.GroupLayout.DEFAULT_SIZE, 1079, Short.MAX_VALUE)
                    .addGroup(pnlAlbumsLayout.createSequentialGroup()
                        .addComponent(btnOpNovoAlbum)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOpRemoverAlbum)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOpUpdateAlbum, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnOpRefreshAlbumList)))
                .addContainerGap())
        );

        pnlAlbumsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnOpNovoAlbum, btnOpRemoverAlbum, btnOpUpdateAlbum});

        pnlAlbumsLayout.setVerticalGroup(
            pnlAlbumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAlbumsLayout.createSequentialGroup()
                .addComponent(scrollTableAlbums, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAlbumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOpNovoAlbum)
                    .addComponent(btnOpRemoverAlbum)
                    .addComponent(btnOpUpdateAlbum)
                    .addComponent(btnOpRefreshAlbumList)))
        );

        javax.swing.GroupLayout pnlAlbumTabLayout = new javax.swing.GroupLayout(pnlAlbumTab);
        pnlAlbumTab.setLayout(pnlAlbumTabLayout);
        pnlAlbumTabLayout.setHorizontalGroup(
            pnlAlbumTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAlbumTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAlbumTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlAlbumFotos, javax.swing.GroupLayout.PREFERRED_SIZE, 1113, Short.MAX_VALUE)
                    .addComponent(pnlAlbums, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlAlbumTabLayout.setVerticalGroup(
            pnlAlbumTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAlbumTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlAlbums, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlAlbumFotos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabpnlMain.addTab("Album", pnlAlbumTab);

        javax.swing.GroupLayout pnlHouseKeepingLayout = new javax.swing.GroupLayout(pnlHouseKeeping);
        pnlHouseKeeping.setLayout(pnlHouseKeepingLayout);
        pnlHouseKeepingLayout.setHorizontalGroup(
            pnlHouseKeepingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1137, Short.MAX_VALUE)
        );
        pnlHouseKeepingLayout.setVerticalGroup(
            pnlHouseKeepingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 604, Short.MAX_VALUE)
        );

        tabpnlMain.addTab("Limpeza", pnlHouseKeeping);

        pnlBottom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnClose.setText("Close Window");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBottomLayout = new javax.swing.GroupLayout(pnlBottom);
        pnlBottom.setLayout(pnlBottomLayout);
        pnlBottomLayout.setHorizontalGroup(
            pnlBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBottomLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlBottomLayout.setVerticalGroup(
            pnlBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBottomLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClose)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabpnlMain)
            .addComponent(pnlBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabpnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, 631, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        actionFechar();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnOpNovoAlbumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpNovoAlbumActionPerformed
        actionCriarNovoAlbum();
    }//GEN-LAST:event_btnOpNovoAlbumActionPerformed

    private void btnOpRefreshAlbumListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpRefreshAlbumListActionPerformed
        actionLoadAllAlbums();
    }//GEN-LAST:event_btnOpRefreshAlbumListActionPerformed

    private void btnOpUpdateAlbumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpUpdateAlbumActionPerformed
        actionAtualizarAlbum();
    }//GEN-LAST:event_btnOpUpdateAlbumActionPerformed

    //
    // ACTIONS
    //
    
    private void actionFechar() {
        JpaManager.getInstance().finish();
        this.dispose();
    }
    
    /** loads ALL albums into state */
    private void actionLoadAllAlbums() {
        logger.debug(">> loadAllAlbums()");
        try {
            this.state.setAlbumList(serviceFacade.getAllAlbums());
        } catch (FacadeException e) {
            guiMostraAviso("ERRO:" + e.getMessage());
        }
        logger.debug("<< loadAllAlbums()");
    }    

    private void actionCriarNovoAlbum() {
        logger.debug(">>> actionCriarNovoAlbum()");
        NewAlbumDialog dialog = null;
        
        try {
            dialog = new NewAlbumDialog(this, true);
            dialog.setVisible(true);        
            if (dialog.isCompleted()) {
                // only LFS albums are supported
                ServiceFacade.getInstance().newLocalFileSystemAlbum(dialog.getNewAlbumName(), dialog.getNewAlbumPath());
                actionLoadAllAlbums();
            } else {
                logger.trace("Canceled by user.");
            }
        } catch (FacadeException e) {
            String msg = e.getMessage();
            JOptionPane.showMessageDialog(this,msg,"Houve um erro",JOptionPane.ERROR_MESSAGE);
        } finally {
            logger.trace("Disposing of newalbumdialog");
            if (null != dialog) dialog.dispose();
            logger.debug("<<< actionCriarNovoAlbum()");
        }

    }
    
    private void actionSelecionarAlbum(Album album) {
        logger.debug(">>> actionSelecionarAlbum(Album)");
        getMainFrameState().setAlbum(album);
        actionAtualizarListaFotos(album);
        logger.debug("<<< actionSelecionarAlbum(Album)");        
    }

    private void actionSelecionarPhoto(Photo photo) {
        logger.debug(">>> actionSelecionarPhoto(Photo)");
        getMainFrameState().setPhoto(photo);
        actionAtualizarThumbnail(photo);
        logger.debug("<<< actionSelecionarPhoto(Photo)");
    }
    
    private void actionAtualizarThumbnail(Photo photo) {
        logger.debug(">>> actionAtualizarThumbnail(Photo photo)");
        // A Photo does not point to a file or set of files.
        // There may be several files in several albums.
        // I'll use the knowledge of the current selected album
        //  and trust that I can find at least a location in it.
        if (photo != null) {
            Album selectedAlbum = getMainFrameState().getAlbum();
            
            // sanity
            if (null == selectedAlbum) throw new IllegalStateException("BOOM: album could not be null at this point.");

            List<Location> locations = LocationDAO.getInstance().findLocationInAlbumByPhoto(selectedAlbum, photo);

            // sanity
            if (null == locations) throw new IllegalStateException("BOOM: it seems no location was found by DAO");
            if (locations.isEmpty()) throw new IllegalStateException("BOOM: empty list of locations not acceptable at this point");

            // Take the first and trust our luck ...
            Location location = locations.get(0);
            File file = new File(location.getPath());
    
            logger.trace("FILE: " + file.getAbsolutePath());
            
            // sanity
            if (!(file.isFile()) || !(file.canRead())) throw new RuntimeException("WARN: CAN'T READ FILE");
            
            int width = pnlFotoPreview.getWidth();
            int height = pnlFotoPreview.getHeight();
            logger.trace("W: " + width);
            logger.trace("H: " + height);
            byte[] raw = DefaultThumbUtil.getInstance().makeRawThumb(file, width, height);
            ImageIcon imgIcon = new ImageIcon(raw);
            lblThumbnail.setIcon(imgIcon);

        } else {
            actionClearThumbnail();
        }
        logger.debug("<<< actionAtualizarThumbnail(Photo photo)");
    }
    
    private void actionClearThumbnail() {
        logger.debug(">>> actionClearThumbnail()");
        lblThumbnail.setIcon(null);
        logger.debug("<<< actionClearThumbnail()");
    }
    
    private void actionAtualizarListaFotos(Album album) {
        logger.debug(">>> actionAtualizarListaFotos(Album)");
        try {
            List<Photo> albumPhotos;
            if (null == album) {
                logger.debug("null album, will set empty list of photos");
                albumPhotos = new ArrayList<>();
            } else {
                albumPhotos = serviceFacade.getAlbumPhotos(album);
            }            
            getMainFrameState().setPhotosList(albumPhotos);
        } catch (FacadeException e) {
            String msg = e.getMessage();
            JOptionPane.showMessageDialog(this,msg,"Houve um erro",JOptionPane.ERROR_MESSAGE);
        }
        logger.debug("<<< actionAtualizarListaFotos(Album)");        
    }
    
    private void actionAtualizarAlbum() {
               
        logger.debug(">>> actionAtualizarAlbum()");
        
        if (!getMainFrameState().isAlbumSelected()) {
            guiMostraAviso("Por favor selecione um álbum para atualizar");
            return;
        }
        
        try {
            Album album = getMainFrameState().getAlbum();
            serviceFacade.performAlbumUpdate(album);
            actionSelecionarAlbum(album);
            
        } catch (FacadeException e) {
            String msg = e.getMessage();
            JOptionPane.showMessageDialog(this,msg,"Houve um erro",JOptionPane.ERROR_MESSAGE);
        /*
        } catch (InterruptedException | ExecutionException e) {
            //throw new FacadeException("Job interrupted or failed: " + e.getMessage());
            JOptionPane.showMessageDialog(this,e.getMessage(),"Houve um erro",JOptionPane.ERROR_MESSAGE);
        */ 
        } finally {
            
        }

        logger.debug("<<< actionAtualizarAlbum()");

    }

    //
    // GUI STUFF
    //
  
    private void guiMostraAviso(String texto) {
        JOptionPane.showMessageDialog(this, texto, "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private String initLookAndFeel() {
        //log.trace(">> initLookAndFeel()");
        String lafName = UIManager.getLookAndFeel().getName();

        try {
            String systemLafName = UIManager.getSystemLookAndFeelClassName();
            //log.debug("Trying system laf: " + systemLafName);
            UIManager.setLookAndFeel(systemLafName);
        } catch (Exception e) {
            //log.warn("Could not set the LAF to native: " + e.getMessage());
        }

        //log.debug("Using LAF: " + UIManager.getLookAndFeel().getName());
        //log.trace("<< initLookAndFeel()");
        return lafName;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnOpNovoAlbum;
    private javax.swing.JButton btnOpRefreshAlbumList;
    private javax.swing.JButton btnOpRemoverAlbum;
    private javax.swing.JButton btnOpUpdateAlbum;
    private javax.swing.JLabel lblThumbnail;
    private javax.swing.JPanel pnlAlbumFotos;
    private javax.swing.JPanel pnlAlbumTab;
    private javax.swing.JPanel pnlAlbums;
    private javax.swing.JPanel pnlBottom;
    private javax.swing.JPanel pnlFotoPreview;
    private javax.swing.JPanel pnlHouseKeeping;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JScrollPane scrollTableAlbumFotos;
    private javax.swing.JScrollPane scrollTableAlbums;
    private javax.swing.JTable tableAlbumFotos;
    private javax.swing.JTable tableAlbums;
    private javax.swing.JTabbedPane tabpnlMain;
    // End of variables declaration//GEN-END:variables

    ListSelectionListener albumListSelectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            logger.trace(">>> albumListSelectionListener.valueChanged(...)");                
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();
            // expect single-selection mode
            if (lsm.getSelectionMode() == ListSelectionModel.SINGLE_SELECTION) {
                if (!e.getValueIsAdjusting()) {
                    int index = lsm.getMinSelectionIndex();
                    logger.debug("INDEX IS: " + index);
                    if (index == -1) {
                        actionSelecionarAlbum(null);
                    } else if (lsm.isSelectedIndex(index)) {
                        Album album = getMainFrameState().getAlbumList().get(index); 
                        actionSelecionarAlbum(album);
                    } else {
                        logger.warn("dont know what to do ...");
                    }
                }
            }
        }
    };

    ListSelectionListener photoListSelectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            logger.trace(">>> photoListSelectionListener.valueChanged(...)");                
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();
            // expect single-selection mode
            if (lsm.getSelectionMode() == ListSelectionModel.SINGLE_SELECTION) {
                if (!e.getValueIsAdjusting()) {
                    int index = lsm.getMinSelectionIndex();
                    logger.debug("INDEX IS: " + index);
                    if (index == -1) {
                        actionSelecionarPhoto(null);
                    } else if (lsm.isSelectedIndex(index)) {
                        Photo photo = getMainFrameState().getPhotosList().get(index); 
                        actionSelecionarPhoto(photo);
                    } else {
                        logger.warn("dont know what to do ...");
                    }
                }
            }
        }
    };   
}
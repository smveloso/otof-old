package org.smveloso.otof.gui;

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
import org.smveloso.otof.facade.FacadeException;
import org.smveloso.otof.facade.ServiceFacade;
import org.smveloso.otof.gui.dialog.NewAlbumDialog;
import org.smveloso.otof.model.Album;
import org.smveloso.otof.gui.tablemodel.AlbumListTableModel;
import org.smveloso.otof.gui.tablemodel.LocationListTableModel;
import org.smveloso.otof.gui.tablemodel.PhotoListTableModel;
import org.smveloso.otof.model.Photo;

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

    private LocationListTableModel locationsTableModel;
    
    int thumbWidth;
    int thumbHeight;

    
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
        
        this.locationsTableModel = new LocationListTableModel();
        this.locationsTableModel.associateToState(state);
        
        logger.debug("<<< beforeInitComponents()");
    }
    
    private void afterInitComponents() {
        logger.debug(">>> afterInitComponents()");
        this.tableAlbums.getSelectionModel().addListSelectionListener(albumListSelectionListener);
        this.tableFotos.getSelectionModel().addListSelectionListener(photoListSelectionListener);
        this.tableLocations.getSelectionModel().addListSelectionListener(locationListSelectionListener);
        
        thumbWidth = pnlFotoPreview.getWidth();
        thumbHeight = pnlFotoPreview.getHeight();
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
        pnlSearchTab = new javax.swing.JPanel();
        pnlFotos = new javax.swing.JPanel();
        scrollTableFotos = new javax.swing.JScrollPane();
        tableFotos = new javax.swing.JTable();
        pnlFotoPreview = new javax.swing.JPanel();
        lblThumbnail = new javax.swing.JLabel();
        pnlFotosPagination = new javax.swing.JPanel();
        lblFotosNumberOfPages = new javax.swing.JLabel();
        txtFotosNumberOfPages = new javax.swing.JTextField();
        lblFotosCurrentPage = new javax.swing.JLabel();
        txtFotosCurrentPage = new javax.swing.JTextField();
        lblFotosGoToPage = new javax.swing.JLabel();
        btnFotosGoToPage = new javax.swing.JButton();
        txtFotosGotToPage = new javax.swing.JTextField();
        bntFotosNextPage = new javax.swing.JButton();
        btnFotosPreviousPage = new javax.swing.JButton();
        scrollTableLocations = new javax.swing.JScrollPane();
        tableLocations = new javax.swing.JTable();
        tabpnlCriterios = new javax.swing.JTabbedPane();
        pnlAlbums = new javax.swing.JPanel();
        scrollTableAlbums = new javax.swing.JScrollPane();
        tableAlbums = new javax.swing.JTable();
        btnOpNovoAlbum = new javax.swing.JButton();
        btnOpRemoverAlbum = new javax.swing.JButton();
        btnOpUpdateAlbum = new javax.swing.JButton();
        btnOpRefreshAlbumList = new javax.swing.JButton();
        pnlSearch = new javax.swing.JPanel();
        pnlHouseKeeping = new javax.swing.JPanel();
        pnlBottom = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("OTOF");

        pnlFotos.setBorder(javax.swing.BorderFactory.createTitledBorder("Fotos"));

        tableFotos.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        tableFotos.setModel(getAlbumPhotosTableModel());
        tableFotos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollTableFotos.setViewportView(tableFotos);

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

        pnlFotosPagination.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblFotosNumberOfPages.setText("Pages:");

        lblFotosCurrentPage.setText("Current Page:");

        lblFotosGoToPage.setText("Go to page:");

        btnFotosGoToPage.setText("GO");

        bntFotosNextPage.setText("next");

        btnFotosPreviousPage.setText("prev");

        javax.swing.GroupLayout pnlFotosPaginationLayout = new javax.swing.GroupLayout(pnlFotosPagination);
        pnlFotosPagination.setLayout(pnlFotosPaginationLayout);
        pnlFotosPaginationLayout.setHorizontalGroup(
            pnlFotosPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFotosPaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFotosNumberOfPages)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFotosNumberOfPages, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFotosCurrentPage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFotosCurrentPage, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnFotosPreviousPage, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(bntFotosNextPage, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFotosGoToPage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFotosGotToPage, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFotosGoToPage)
                .addContainerGap())
        );

        pnlFotosPaginationLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {bntFotosNextPage, btnFotosPreviousPage});

        pnlFotosPaginationLayout.setVerticalGroup(
            pnlFotosPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFotosPaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFotosPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFotosNumberOfPages)
                    .addComponent(txtFotosNumberOfPages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFotosCurrentPage)
                    .addComponent(txtFotosCurrentPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFotosGoToPage)
                    .addComponent(btnFotosGoToPage)
                    .addComponent(txtFotosGotToPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntFotosNextPage)
                    .addComponent(btnFotosPreviousPage))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tableLocations.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollTableLocations.setViewportView(tableLocations);

        javax.swing.GroupLayout pnlFotosLayout = new javax.swing.GroupLayout(pnlFotos);
        pnlFotos.setLayout(pnlFotosLayout);
        pnlFotosLayout.setHorizontalGroup(
            pnlFotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFotosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlFotosPagination, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollTableFotos)
                    .addComponent(scrollTableLocations))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFotoPreview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlFotosLayout.setVerticalGroup(
            pnlFotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFotosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFotosLayout.createSequentialGroup()
                        .addComponent(scrollTableFotos, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addComponent(scrollTableLocations, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlFotosPagination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlFotoPreview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tableAlbums.setModel(getAlbumListTableModel());
        tableAlbums.setName("tableAlbums"); // NOI18N
        tableAlbums.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollTableAlbums.setViewportView(tableAlbums);

        btnOpNovoAlbum.setText("+");
        btnOpNovoAlbum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpNovoAlbumActionPerformed(evt);
            }
        });

        btnOpRemoverAlbum.setText("-");

        btnOpUpdateAlbum.setText("S");
        btnOpUpdateAlbum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpUpdateAlbumActionPerformed(evt);
            }
        });

        btnOpRefreshAlbumList.setText("Rf");
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
                .addComponent(scrollTableAlbums, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAlbumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOpNovoAlbum, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOpUpdateAlbum, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAlbumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnOpRefreshAlbumList, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOpRemoverAlbum, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        pnlAlbumsLayout.setVerticalGroup(
            pnlAlbumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAlbumsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAlbumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAlbumsLayout.createSequentialGroup()
                        .addGroup(pnlAlbumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnOpRemoverAlbum)
                            .addComponent(btnOpNovoAlbum))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAlbumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnOpRefreshAlbumList)
                            .addComponent(btnOpUpdateAlbum)))
                    .addComponent(scrollTableAlbums, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47))
        );

        tabpnlCriterios.addTab("Álbums", pnlAlbums);

        javax.swing.GroupLayout pnlSearchLayout = new javax.swing.GroupLayout(pnlSearch);
        pnlSearch.setLayout(pnlSearchLayout);
        pnlSearchLayout.setHorizontalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1108, Short.MAX_VALUE)
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 163, Short.MAX_VALUE)
        );

        tabpnlCriterios.addTab("Search", pnlSearch);

        javax.swing.GroupLayout pnlSearchTabLayout = new javax.swing.GroupLayout(pnlSearchTab);
        pnlSearchTab.setLayout(pnlSearchTabLayout);
        pnlSearchTabLayout.setHorizontalGroup(
            pnlSearchTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSearchTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlFotos, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(tabpnlCriterios))
                .addContainerGap())
        );
        pnlSearchTabLayout.setVerticalGroup(
            pnlSearchTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabpnlCriterios, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFotos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        tabpnlMain.addTab("Fotos", pnlSearchTab);

        javax.swing.GroupLayout pnlHouseKeepingLayout = new javax.swing.GroupLayout(pnlHouseKeeping);
        pnlHouseKeeping.setLayout(pnlHouseKeepingLayout);
        pnlHouseKeepingLayout.setHorizontalGroup(
            pnlHouseKeepingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1137, Short.MAX_VALUE)
        );
        pnlHouseKeepingLayout.setVerticalGroup(
            pnlHouseKeepingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 648, Short.MAX_VALUE)
        );

        tabpnlMain.addTab("Limpeza", pnlHouseKeeping);

        pnlBottom.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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
            .addComponent(btnClose)
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
                .addComponent(tabpnlMain)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        actionSyncAlbum();
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
        List<Photo> albumPhotos = null;
        try {
            if (null == album) {
                logger.trace("null album, will set empty list of photos");
                albumPhotos = new ArrayList<>();
            } else {
                albumPhotos = serviceFacade.getAlbumPhotos(album);
            }     
        } catch (FacadeException e) {
            String msg = e.getMessage();
            JOptionPane.showMessageDialog(this,msg,"Houve um erro",JOptionPane.ERROR_MESSAGE);
        }
        actionAtualizarListaFotos(albumPhotos);
        logger.debug("<<< actionSelecionarAlbum(Album)");        
    }

    private void actionSelecionarPhoto(Photo photo) {
        logger.debug(">>> actionSelecionarPhoto(Photo)");
        getMainFrameState().setPhoto(photo);
        actionAtualizarThumbnail(photo);
        logger.debug("<<< actionSelecionarPhoto(Photo)");
    }

    private void actionAtualizarThumbnail(byte[] raw) {
        logger.debug(">>> actionAtualizarThumbnail(byte[])");
        ImageIcon imgIcon = new ImageIcon(raw);
        lblThumbnail.setIcon(imgIcon);
        logger.debug("<<< actionAtualizarThumbnail(byte[])");
    }
    
    private void actionAtualizarThumbnail(Photo photo) {
        logger.debug(">>> actionAtualizarThumbnail(Photo photo)");
        if (photo != null) {
            byte[] raw = serviceFacade.getThumbnail(photo.getId());                
            if (null == raw) {
                //TODO: carregar uma imagem padrão de 'sem thumbnail'
                logger.warn("NO THUMBNAIL.");
                actionClearThumbnail();
            } else {
                actionAtualizarThumbnail(raw);
            }
        } else {
            logger.debug("Photo is null: clearing thumbnail.");
            actionClearThumbnail();
        }
        logger.debug("<<< actionAtualizarThumbnail(Photo photo)");
    }
    
    private void actionClearThumbnail() {
        logger.debug(">>> actionClearThumbnail()");
        lblThumbnail.setIcon(null);
        logger.debug("<<< actionClearThumbnail()");
    }
    
    /** <p/> Carrega a lista de fotos que
     *       deve ser exibida na tabela.
     * 
     * @param List<Photo>
     */
    private void actionAtualizarListaFotos(List<Photo> photos) {
        logger.debug(">>> actionAtualizarListaFotos(List<Photo>)");
        getMainFrameState().setPhotosList(photos);
        logger.debug("<<< actionAtualizarListaFotos(List<Photo>)");
    }
    
    private void actionSyncAlbum() {
               
        logger.debug(">>> actionSyncAlbum()");
        
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
        } finally {
            
        }

        logger.debug("<<< actionSyncAlbum()");

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
    private javax.swing.JButton bntFotosNextPage;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnFotosGoToPage;
    private javax.swing.JButton btnFotosPreviousPage;
    private javax.swing.JButton btnOpNovoAlbum;
    private javax.swing.JButton btnOpRefreshAlbumList;
    private javax.swing.JButton btnOpRemoverAlbum;
    private javax.swing.JButton btnOpUpdateAlbum;
    private javax.swing.JLabel lblFotosCurrentPage;
    private javax.swing.JLabel lblFotosGoToPage;
    private javax.swing.JLabel lblFotosNumberOfPages;
    private javax.swing.JLabel lblThumbnail;
    private javax.swing.JPanel pnlAlbums;
    private javax.swing.JPanel pnlBottom;
    private javax.swing.JPanel pnlFotoPreview;
    private javax.swing.JPanel pnlFotos;
    private javax.swing.JPanel pnlFotosPagination;
    private javax.swing.JPanel pnlHouseKeeping;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlSearchTab;
    private javax.swing.JScrollPane scrollTableAlbums;
    private javax.swing.JScrollPane scrollTableFotos;
    private javax.swing.JScrollPane scrollTableLocations;
    private javax.swing.JTable tableAlbums;
    private javax.swing.JTable tableFotos;
    private javax.swing.JTable tableLocations;
    private javax.swing.JTabbedPane tabpnlCriterios;
    private javax.swing.JTabbedPane tabpnlMain;
    private javax.swing.JTextField txtFotosCurrentPage;
    private javax.swing.JTextField txtFotosGotToPage;
    private javax.swing.JTextField txtFotosNumberOfPages;
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

    ListSelectionListener locationListSelectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            logger.trace(">>> locationListSelectionListener.valueChanged(...)");                
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
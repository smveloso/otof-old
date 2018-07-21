package org.smveloso.otof.gui.job;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.SwingWorker;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;


/**
 *
 * @author sergiomv
 *
 * Based on WaitOperationDialog.java from componentes-trt
 *
 */
public class WaitWindowWorkerDialog extends javax.swing.JDialog implements PropertyChangeListener {

    //private static final Log log = LogFactory.getLog("applet");    
    
    private SwingWorker worker;

    private boolean displayProgress;
    private boolean displayMensagem;


    public WaitWindowWorkerDialog(java.awt.Frame parent, SwingWorker worker) {
        this(parent,worker,true,true);
    }

    public WaitWindowWorkerDialog(java.awt.Frame parent, SwingWorker worker, boolean displayProgress, boolean displayMensagem) {
        super(parent, true);
        //log.trace("[AppletWaitWindowWorkerDialog] >>");

        this.displayProgress = displayProgress;
        this.displayMensagem = displayMensagem;

        initComponents();

        // center dialog in the screen
        this.setLocationRelativeTo(null);

        if (!displayProgress) {
            this.progress.setVisible(false);
        }

        if (!displayMensagem) {
            this.lblMensagem.setVisible(false);
        }

        pack();

        //log.trace("[AppletWaitWindowWorkerDialog] will fire worker ...");
        this.worker = worker;
        this.worker.addPropertyChangeListener(this);
        this.worker.execute();
        //log.trace("[AppletWaitWindowWorkerDialog] worker fired !");

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        progress = new javax.swing.JProgressBar();
        lblMensagem = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Aguarde ...");
        setUndecorated(true);
        setResizable(false);

        panelPrincipal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelPrincipal.setMaximumSize(new java.awt.Dimension(307, 97));
        panelPrincipal.setMinimumSize(new java.awt.Dimension(307, 97));

        lblTitulo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTitulo.setText("Por favor aguarde ...");

        lblMensagem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMensagem.setText("_");

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(progress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMensagem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblMensagem;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JProgressBar progress;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //log.debug("[AppletWaitWindowWorkerDialog] >> propertyChange " + evt.getPropertyName() + " " + evt.getOldValue() + " => " + evt.getNewValue());

        if ("state".equals(evt.getPropertyName())) {
            if (SwingWorker.StateValue.DONE.equals(evt.getNewValue())) {
                //log.debug("[AppletWaitWindowWorkerDialog] worker is done, will close dialog");
                this.setVisible(false);
                this.dispose();
            }
        } else if (displayMensagem && "mensagem".equals(evt.getPropertyName())) {
            changeMessageTo((String) evt.getNewValue());
        } else if (displayProgress && "progress".equals(evt.getPropertyName())) {
            updateProgress((Integer) evt.getNewValue());
        }

    }

    private void changeMessageTo(String newMsg) {
        this.lblMensagem.setText(newMsg);
    }

    private void updateProgress(int value) {
        this.progress.setValue(value);
    }

    public boolean isDisplayMensagem() {
        return displayMensagem;
    }

    public void setDisplayMensagem(boolean displayMensagem) {
        this.displayMensagem = displayMensagem;
    }

    public boolean isDisplayProgress() {
        return displayProgress;
    }

    public void setDisplayProgress(boolean displayProgress) {
        this.displayProgress = displayProgress;
    }

    public SwingWorker getWorker() {
        return worker;
    }

}
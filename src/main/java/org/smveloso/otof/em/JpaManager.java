package org.smveloso.otof.em;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author sergiomv
 */
public class JpaManager {

    private static JpaManager instance = null;
    
    private JpaManager() {
        init();
    }
    
    public static synchronized JpaManager getInstance() {
        if (null == instance) {
            instance = new JpaManager();
        }
        return instance;
    }

    private EntityManagerFactory emFactory = null;
    
    private void init() {
        if (null == emFactory) {
            emFactory = Persistence.createEntityManagerFactory("org.smveloso_otof_jar_1-SNAPSHOTPU");
        }
    }
    
    public synchronized void finish() {
        // sanidade
        if (null == emFactory) {
            throw new IllegalStateException("EM Factory is null during finish.");
        }
        emFactory.close();
        instance = null;
    }
    
    public synchronized EntityManagerFactory getFactory() {
        return emFactory;
    }
    
}

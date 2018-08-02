package org.smveloso.otof.em;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/** Singleton.
 *  <p>Inicializa e mantém uma "EntityManagerFactory".</p>
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
    
    public synchronized boolean isInitialized() {
        return (null != emFactory) && emFactory.isOpen();
    }
    
    private synchronized void init() {
        if (!isInitialized()) {
            emFactory = Persistence.createEntityManagerFactory("org.smveloso_otof_jar_1-SNAPSHOTPU");
        }
    }
    
    public synchronized void finish() {
        if (isInitialized()) {
            emFactory.close();
        }
    }
    
    protected synchronized EntityManagerFactory getFactory() {
        return emFactory;
    }

    // EntityManager is NOT thread-safe
    public EntityManager getEntityManager() {
        return getFactory().createEntityManager();
    }
    
}

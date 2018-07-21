package org.smveloso.otof.em;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public abstract class DAO {
    
    // JpaManager eh um singleton
    JpaManager jpaManager;

    protected DAO() {
         jpaManager = JpaManager.getInstance();
    }
        
    protected EntityManager getEntityManager() {
        // sempre cria um novo entitymanager, pois este nao eh thread-safe
        return jpaManager.getEntityManager();
    }
    
}

package org.smveloso.otof.em;

import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DAO {
    
    private static final Logger logger = LoggerFactory.getLogger(DAO.class);
    
    // JpaManager eh um singleton
    JpaManager jpaManager;

    protected DAO() {
        jpaManager = JpaManager.getInstance();
    }
        
    protected EntityManager getEntityManager() {
        // sempre cria um novo entitymanager, pois este nao eh thread-safe
        return jpaManager.getEntityManager();
    }
    
    protected void closeEM(EntityManager em) {
        logger.trace("... will close em ...");
        em.close();
        logger.debug("=> EM CLOSED <=");
    }
    
}

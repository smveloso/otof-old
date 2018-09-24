package org.smveloso.otof.em;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smveloso.otof.em.exception.EmException;
import org.smveloso.otof.em.exception.NonexistentEntityException;
import org.smveloso.otof.model.Album;
import org.smveloso.otof.model.Photo;

/**
 *
 * @author sergio
 */
public class AlbumDAO extends DAO implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(AlbumDAO.class);
    
    private static AlbumDAO instance;
    
    private AlbumDAO() {
    }
    
    public static synchronized AlbumDAO getInstance() {
        if (null == instance) {
            instance = new AlbumDAO();
        }
        return instance;
    }
    
    public void create(Album album) {
        System.out.println(">> create");
        try {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(album);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                closeEM(em);
            }
        }
    } catch (Throwable any) {
        System.out.println("BOOM:" + any.getMessage());
        throw any;
    }
        System.out.println("<< create");
    }

    public void update(Album album) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            album = em.merge(album);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = album.getId();
                if (findAlbum(id) == null) {
                    throw new NonexistentEntityException("The album with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                closeEM(em);
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Album album;
            try {
                album = em.getReference(Album.class, id);
                album.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The album with id " + id + " no longer exists.", enfe);
            }
            em.remove(album);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                closeEM(em);
            }
        }
    }

    public List<Album> findAlbumEntities() {
        return findAlbumEntities(true, -1, -1);
    }

    public List<Album> findAlbumEntities(int maxResults, int firstResult) {
        return findAlbumEntities(false, maxResults, firstResult);
    }

    private List<Album> findAlbumEntities(boolean all, int maxResults, int firstResult) {
        logger.debug(">>> findAlbumEntities(...)");
        logger.trace("(all,max,first) :: " + all + " " + maxResults + " " + firstResult);
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Album.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            logger.debug("<<< findAlbumEntities(...)");
            return q.getResultList();
        } finally {
            closeEM(em);
        }
    }

    public Album findAlbum(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Album.class, id);
        } finally {
            closeEM(em);
        }
    }

    /* como o EM eh sempre re-criado, nunca será 'managed' ...
    public boolean isManaged(Album album) {
        return getEntityManager().contains(album);
    }
    */
    
    public Album findAlbumByName(String name) throws EmException {
        Album album = null;
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Album> tq = em.createNamedQuery("Album.byName", Album.class);
            tq.setParameter("name", name);
            album = tq.getSingleResult();
        } catch (NoResultException e) {
            //throw new EmException("Erro ao buscar foto por digest: " + e.getMessage(),e);
        } catch (NonUniqueResultException e) {
            throw new EmException("Erro ao buscar album por nome: " + e.getMessage(), e);
        } catch (PersistenceException e) {
            throw new EmException("Erro ao buscar album por nome: " + e.getMessage(), e);
        } finally {
            closeEM(em);
        }
        return album;
    }
        
    public int getAlbumCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Album> rt = cq.from(Album.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            closeEM(em);
        }
    }
    
}

package org.smveloso.otof.em;

import org.smveloso.otof.em.exception.EmException;
import org.smveloso.otof.em.exception.NonexistentEntityException;
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
import org.smveloso.otof.model.Photo;
import org.smveloso.otof.model.Thumbnail;

/**
 *
 * @author sergiomv
 */
public class PhotoDAO extends DAO implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(PhotoDAO.class);
    
    private static PhotoDAO instance;
    
    private PhotoDAO() {
    }
    
    public static synchronized PhotoDAO getInstance() {
        if (null == instance) {
            instance = new PhotoDAO();
        }
        return instance;
    }
    
    public void create(Photo foto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(foto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                closeEM(em);
            }
        }
    }

    public void update(Photo foto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            foto = em.merge(foto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = foto.getId();
                if (findFoto(id) == null) {
                    throw new NonexistentEntityException("The foto with id " + id + " no longer exists.");
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
            Photo foto;
            try {
                foto = em.getReference(Photo.class, id);
                foto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The foto with id " + id + " no longer exists.", enfe);
            }
            em.remove(foto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                closeEM(em);
            }
        }
    }

    public List<Photo> findFotoEntities() {
        return findFotoEntities(true, -1, -1);
    }

    public List<Photo> findFotoEntities(int maxResults, int firstResult) {
        return findFotoEntities(false, maxResults, firstResult);
    }

    private List<Photo> findFotoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Photo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            if (em != null) {
                closeEM(em);
            }    
        }
    }
    
    public Photo findFoto(Long id) {
        return findFoto(id,false);
    }
    
    public Photo findFoto(Long id, boolean loadThumbnails) {
        logger.trace(">>> findPhoto(Long,boolean)");
        logger.trace("LOAD THUMBS? " + loadThumbnails);
        EntityManager em = getEntityManager();
        Photo photo;
        try {
            photo = em.find(Photo.class, id);
            if (loadThumbnails && (null != photo)) {
                int size = photo.thumbnails.size();
                logger.trace("THUMBS: " + size);
            }
            return photo;
        } finally {
            if (em != null) {
                closeEM(em);
            }
            logger.trace("<<< findPhoto(Long,boolean)");
        }
    }

    public Thumbnail findThumbnail(Long id) {
        logger.trace(">>> fundThumbnail(Long)");
        EntityManager em = getEntityManager();
        Thumbnail thumbnail;
        try {
            thumbnail = em.find(Thumbnail.class,id);
            return thumbnail;
        } finally {
            if (em != null) {
                closeEM(em);
            }
        logger.trace("<<< fundThumbnail(Long)");
        }
    }
    
    public int getTotalPhotoCount() {
            System.out.println(">> getTotalPhotoCont");

        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Photo> rt = cq.from(Photo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            if (em != null) {
                closeEM(em);
            }
            System.out.println("<< getTotalPhotoCont");
        }
    }    

    public Photo findFotoByDigest(String digest) throws EmException {
        System.out.println(">> findFotoByDigest");
        Photo foto = null;
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Photo> tq = em.createNamedQuery("Photo.byDigest", Photo.class);
            tq.setParameter("digest", digest);
            foto = tq.getSingleResult();
        } catch (NoResultException e) {
            //throw new EmException("Erro ao buscar foto por digest: " + e.getMessage(),e);
        } catch (NonUniqueResultException e) {
            throw new EmException("Erro ao buscar foto por digest: " + e.getMessage(), e);
        } catch (PersistenceException e) {
            throw new EmException("Erro ao buscar foto por digest: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                closeEM(em);
            }
            System.out.println("<< findFotoByDigest");
        }
        return foto;
    }    
}
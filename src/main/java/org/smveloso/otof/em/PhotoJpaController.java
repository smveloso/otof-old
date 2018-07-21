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
import org.smveloso.otof.model.Photo;

/**
 *
 * @author sergiomv
 */
public class PhotoJpaController extends DAO implements Serializable {

    private static PhotoJpaController instance;
    
    private PhotoJpaController() {
    }
    
    public static synchronized PhotoJpaController getInstance() {
        if (null == instance) {
            instance = new PhotoJpaController();
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
                em.close();
            }
        }
    }

    public void edit(Photo foto) throws NonexistentEntityException, Exception {
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
                em.close();
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
                em.close();
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
            em.close();
        }
    }
        
    public Photo findFoto(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Photo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTotalPhotoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Photo> rt = cq.from(Photo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }    

    public Photo findFotoByDigest(String digest) throws EmException {
        Photo foto = null;
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Photo> tq = em.createNamedQuery("Foto.porDigest", Photo.class);
            tq.setParameter("digest", digest);
            foto = tq.getSingleResult();
        } catch (NoResultException e) {
            //throw new EmException("Erro ao buscar foto por digest: " + e.getMessage(),e);
        } catch (NonUniqueResultException e) {
            throw new EmException("Erro ao buscar foto por digest: " + e.getMessage(), e);
        } catch (PersistenceException e) {
            throw new EmException("Erro ao buscar foto por digest: " + e.getMessage(), e);
        } finally {
            em.close();
        }
        return foto;
    }    
}
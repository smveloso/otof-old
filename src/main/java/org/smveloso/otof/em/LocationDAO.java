package org.smveloso.otof.em;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.smveloso.otof.em.exception.NonexistentEntityException;
import org.smveloso.otof.model.Album;
import org.smveloso.otof.model.Location;
import org.smveloso.otof.model.Photo;

/**
 *
 * @author sergio
 */
public class LocationDAO extends DAO implements Serializable {

    private static LocationDAO instance = null;
    
    private LocationDAO() {
    }

    public static synchronized LocationDAO getInstance() {
        if (null == instance) {
            instance = new LocationDAO();
        }
        return instance;
    }
    
    public void create(Location location) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(location);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void update(Location location) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            location = em.merge(location);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = location.getId();
                if (findLocation(id) == null) {
                    throw new NonexistentEntityException("The location with id " + id + " no longer exists.");
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
            Location location;
            try {
                location = em.getReference(Location.class, id);
                location.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The location with id " + id + " no longer exists.", enfe);
            }
            em.remove(location);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Location> findLocationEntities() {
        return findLocationEntities(true, -1, -1);
    }

    public List<Location> findLocationEntities(int maxResults, int firstResult) {
        return findLocationEntities(false, maxResults, firstResult);
    }

    private List<Location> findLocationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Location.class));
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

    public Location findLocation(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Location.class, id);
        } finally {
            em.close();
        }
    }

    public int getLocationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Location> rt = cq.from(Location.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    //TODO what to do with multiple locations in the same album (should I even allow it ?)
    public Location findLocationInAlbumByPath(Album album, String path) {
        EntityManager em = getEntityManager();
        try {
            Location location = 
                    em.createQuery("select l from Location l where l.album.id = :aid and l.path = :path", 
                                   Location.class)
                    .setParameter("path", path).setParameter("aid", album.getId())
                    .getSingleResult();
            return location;
        } catch (NoResultException notFoundIsOK) {
            return null;
        } finally {
            em.close();
        }
    }

    //TODO what to do with multiple locations in the same album (should I even allow it ?)    
    //     must allow it: multiple occurrences of a photo in an album in different locations
    public Location findLocationInAlbumByPhoto(Album album, Photo photo) {
        EntityManager em = getEntityManager();
        try {
            Location location = 
                    em.createQuery("select l from Location l where l.album.id = :aid and l.photo.id = :pid", 
                                   Location.class)
                    .setParameter("pid", photo.getId()).setParameter("aid", album.getId())
                    .getSingleResult();
            return location;
        } catch (NoResultException notFoundIsOK) {
            return null;
        } finally {
            em.close();
        }        
    }
    
}

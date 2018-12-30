package org.smveloso.otof.em;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smveloso.otof.em.exception.NonexistentEntityException;
import org.smveloso.otof.model.Album;
import org.smveloso.otof.model.Location;
import org.smveloso.otof.model.Photo;
import org.smveloso.otof.util.Misc;

/**
 *
 * @author sergio
 */
public class LocationDAO extends DAO implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(LocationDAO.class);;
    
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
                closeEM(em);
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
                closeEM(em);
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
                closeEM(em);
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
            if (em != null) {
                closeEM(em);
            }
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
            if (em != null) {
                closeEM(em);
            }
        }
    }
    
    public Location findLocationInAlbumByPath(Album album, String path) {
        logger.debug(">>> findLocationInAlbumByPath(Album,String)");
        EntityManager em = getEntityManager();
        try {
            Location location = 
                    em.createQuery("select l from Location l where l.album.id = :aid and l.path = :path", 
                                   Location.class)
                    .setParameter("path", path).setParameter("aid", album.getId())
                    .getSingleResult();
            logger.debug("<<< findLocationInAlbumByPath(Album,String)");
            return location;
        } catch (NoResultException notFoundIsOK) {
            logger.debug("<<< findLocationInAlbumByPath(Album,String) => null");
            return null;
        } finally {
            if (em != null) {
                closeEM(em);
            }
        }
    }

    public List<Location> findLocationInAlbumByPhoto(Album album, Photo photo) {
        logger.trace(">>> findLocationInAlbumByPhoto(Album album, Photo photo)");
        EntityManager em = getEntityManager();
        try {
            List<Location> locations = 
                    em.createQuery("select l from Location l where l.album.id = :aid and l.photo.id = :pid", 
                                   Location.class)
                    .setParameter("pid", photo.getId()).setParameter("aid", album.getId())
                    .getResultList();
            return locations;
        } catch (NoResultException notFoundIsOK) {
            logger.trace("not found");
            return null;
        } finally {
            if (em != null) {
                closeEM(em);
            }
            logger.trace("<<< findLocationInAlbumByPhoto(Album album, Photo photo)");
        }        
    }

    public List<Location> findPhotoLocations(Photo photo) {
        EntityManager em = getEntityManager();
        try {
            List<Location> locations = 
                    em.createQuery("select l from Location l where l.photo.id = :pid", 
                                   Location.class)
                    .setParameter("pid", photo.getId())
                    .getResultList();
            return locations;
        } catch (NoResultException notFoundIsOK) {
            logger.trace("not found");
            return null;
        } finally {
            if (em != null) {
                closeEM(em);
            }
            logger.trace("<<< findLocationInAlbumByPhoto(Album album, Photo photo)");
        }
    }
    
    public int getNumberOfPhotosInAlbum(Album album) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select distinct(loc.photo.id) from Location loc where loc.album.id = :albumid");
            return q.setParameter("albumid", album.getId()).getResultList().size();
        } finally {
            if (em != null) {
                closeEM(em);
            }
        }
    }
    
    public List<Photo> getAlbumPhotos(Album album, int page, int pagesize) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select distinct p from Photo p inner join Location loc " +
                                     "on p.id = loc.photo.id where loc.album.id = :albumid order by p.id",Photo.class);
            q.setParameter("albumid",album.getId());
                       
            if (page > 0 && pagesize > 0) {
                q.setFirstResult(Misc.getStartIndex(pagesize, page));
                q.setMaxResults(pagesize);                
            }
            
            List<Photo> photos = q.getResultList();
             
            return photos;
            
        } catch (NoResultException notFoundIsOK) {
            return new ArrayList<>();
        } finally {
            if (em != null) {
                closeEM(em);
            }
        }                    
    }
    
}

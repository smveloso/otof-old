package org.smveloso.otof.em;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.smveloso.otof.model.Foto;

/**
 *
 * @author sergiomv
 */
public class FotoJpaController implements Serializable {

    public FotoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Foto foto) {
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

    public void edit(Foto foto) throws NonexistentEntityException, Exception {
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
            Foto foto;
            try {
                foto = em.getReference(Foto.class, id);
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

    public List<Foto> findFotoEntities() {
        return findFotoEntities(true, -1, -1);
    }

    public List<Foto> findFotoEntities(int maxResults, int firstResult) {
        return findFotoEntities(false, maxResults, firstResult);
    }

    private List<Foto> findFotoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Foto.class));
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
        
    public Foto findFoto(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Foto.class, id);
        } finally {
            em.close();
        }
    }

    public int getFotoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Foto> rt = cq.from(Foto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }    

    public Foto findFotoByDigest(String digest) throws EmException {
        Foto foto = null;
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Foto> tq = em.createNamedQuery("Foto.porDigest", Foto.class);
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
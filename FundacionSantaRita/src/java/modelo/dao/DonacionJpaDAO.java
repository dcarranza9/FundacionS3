/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import modelo.dao.exceptions.NonexistentEntityException;
import modelo.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Donacion;
import modelo.Fundacion;

/**
 *
 * @author David
 */
public class DonacionJpaDAO implements Serializable {

    public DonacionJpaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Donacion donacion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fundacion nit = donacion.getNit();
            if (nit != null) {
                nit = em.getReference(nit.getClass(), nit.getNit());
                donacion.setNit(nit);
            }
            em.persist(donacion);
            if (nit != null) {
                nit.getDonacionList().add(donacion);
                nit = em.merge(nit);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDonacion(donacion.getIdDonacion()) != null) {
                throw new PreexistingEntityException("Donacion " + donacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Donacion donacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Donacion persistentDonacion = em.find(Donacion.class, donacion.getIdDonacion());
            Fundacion nitOld = persistentDonacion.getNit();
            Fundacion nitNew = donacion.getNit();
            if (nitNew != null) {
                nitNew = em.getReference(nitNew.getClass(), nitNew.getNit());
                donacion.setNit(nitNew);
            }
            donacion = em.merge(donacion);
            if (nitOld != null && !nitOld.equals(nitNew)) {
                nitOld.getDonacionList().remove(donacion);
                nitOld = em.merge(nitOld);
            }
            if (nitNew != null && !nitNew.equals(nitOld)) {
                nitNew.getDonacionList().add(donacion);
                nitNew = em.merge(nitNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = donacion.getIdDonacion();
                if (findDonacion(id) == null) {
                    throw new NonexistentEntityException("The donacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Donacion donacion;
            try {
                donacion = em.getReference(Donacion.class, id);
                donacion.getIdDonacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The donacion with id " + id + " no longer exists.", enfe);
            }
            Fundacion nit = donacion.getNit();
            if (nit != null) {
                nit.getDonacionList().remove(donacion);
                nit = em.merge(nit);
            }
            em.remove(donacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Donacion> findDonacionEntities() {
        return findDonacionEntities(true, -1, -1);
    }

    public List<Donacion> findDonacionEntities(int maxResults, int firstResult) {
        return findDonacionEntities(false, maxResults, firstResult);
    }

    private List<Donacion> findDonacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Donacion.class));
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

    public Donacion findDonacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Donacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDonacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Donacion> rt = cq.from(Donacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

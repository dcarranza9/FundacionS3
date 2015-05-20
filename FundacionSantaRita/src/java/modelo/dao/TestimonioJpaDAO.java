/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import modelo.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Paciente;
import modelo.Terapia;
import modelo.Testimonio;

/**
 *
 * @author David
 */
public class TestimonioJpaDAO implements Serializable {

    public TestimonioJpaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Testimonio testimonio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paciente idPaciente = testimonio.getIdPaciente();
            if (idPaciente != null) {
                idPaciente = em.getReference(idPaciente.getClass(), idPaciente.getIdPaciente());
                testimonio.setIdPaciente(idPaciente);
            }
            Terapia codigoTerapia = testimonio.getCodigoTerapia();
            if (codigoTerapia != null) {
                codigoTerapia = em.getReference(codigoTerapia.getClass(), codigoTerapia.getCodigoTerapia());
                testimonio.setCodigoTerapia(codigoTerapia);
            }
            em.persist(testimonio);
            if (idPaciente != null) {
                idPaciente.getTestimonioList().add(testimonio);
                idPaciente = em.merge(idPaciente);
            }
            if (codigoTerapia != null) {
                codigoTerapia.getTestimonioList().add(testimonio);
                codigoTerapia = em.merge(codigoTerapia);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Testimonio testimonio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Testimonio persistentTestimonio = em.find(Testimonio.class, testimonio.getIdTestimonio());
            Paciente idPacienteOld = persistentTestimonio.getIdPaciente();
            Paciente idPacienteNew = testimonio.getIdPaciente();
            Terapia codigoTerapiaOld = persistentTestimonio.getCodigoTerapia();
            Terapia codigoTerapiaNew = testimonio.getCodigoTerapia();
            if (idPacienteNew != null) {
                idPacienteNew = em.getReference(idPacienteNew.getClass(), idPacienteNew.getIdPaciente());
                testimonio.setIdPaciente(idPacienteNew);
            }
            if (codigoTerapiaNew != null) {
                codigoTerapiaNew = em.getReference(codigoTerapiaNew.getClass(), codigoTerapiaNew.getCodigoTerapia());
                testimonio.setCodigoTerapia(codigoTerapiaNew);
            }
            testimonio = em.merge(testimonio);
            if (idPacienteOld != null && !idPacienteOld.equals(idPacienteNew)) {
                idPacienteOld.getTestimonioList().remove(testimonio);
                idPacienteOld = em.merge(idPacienteOld);
            }
            if (idPacienteNew != null && !idPacienteNew.equals(idPacienteOld)) {
                idPacienteNew.getTestimonioList().add(testimonio);
                idPacienteNew = em.merge(idPacienteNew);
            }
            if (codigoTerapiaOld != null && !codigoTerapiaOld.equals(codigoTerapiaNew)) {
                codigoTerapiaOld.getTestimonioList().remove(testimonio);
                codigoTerapiaOld = em.merge(codigoTerapiaOld);
            }
            if (codigoTerapiaNew != null && !codigoTerapiaNew.equals(codigoTerapiaOld)) {
                codigoTerapiaNew.getTestimonioList().add(testimonio);
                codigoTerapiaNew = em.merge(codigoTerapiaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = testimonio.getIdTestimonio();
                if (findTestimonio(id) == null) {
                    throw new NonexistentEntityException("The testimonio with id " + id + " no longer exists.");
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
            Testimonio testimonio;
            try {
                testimonio = em.getReference(Testimonio.class, id);
                testimonio.getIdTestimonio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The testimonio with id " + id + " no longer exists.", enfe);
            }
            Paciente idPaciente = testimonio.getIdPaciente();
            if (idPaciente != null) {
                idPaciente.getTestimonioList().remove(testimonio);
                idPaciente = em.merge(idPaciente);
            }
            Terapia codigoTerapia = testimonio.getCodigoTerapia();
            if (codigoTerapia != null) {
                codigoTerapia.getTestimonioList().remove(testimonio);
                codigoTerapia = em.merge(codigoTerapia);
            }
            em.remove(testimonio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Testimonio> findTestimonioEntities() {
        return findTestimonioEntities(true, -1, -1);
    }

    public List<Testimonio> findTestimonioEntities(int maxResults, int firstResult) {
        return findTestimonioEntities(false, maxResults, firstResult);
    }

    private List<Testimonio> findTestimonioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Testimonio.class));
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

    public Testimonio findTestimonio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Testimonio.class, id);
        } finally {
            em.close();
        }
    }

    public int getTestimonioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Testimonio> rt = cq.from(Testimonio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

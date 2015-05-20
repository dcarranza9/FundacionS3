/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import modelo.dao.exceptions.IllegalOrphanException;
import modelo.dao.exceptions.NonexistentEntityException;
import modelo.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Testimonio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.HistoriaClinica;
import modelo.Terapia;

/**
 *
 * @author David
 */
public class TerapiaJpaDAO implements Serializable {

    public TerapiaJpaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Terapia terapia) throws PreexistingEntityException, Exception {
        if (terapia.getTestimonioList() == null) {
            terapia.setTestimonioList(new ArrayList<Testimonio>());
        }
        if (terapia.getHistoriaClinicaList() == null) {
            terapia.setHistoriaClinicaList(new ArrayList<HistoriaClinica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Testimonio> attachedTestimonioList = new ArrayList<Testimonio>();
            for (Testimonio testimonioListTestimonioToAttach : terapia.getTestimonioList()) {
                testimonioListTestimonioToAttach = em.getReference(testimonioListTestimonioToAttach.getClass(), testimonioListTestimonioToAttach.getIdTestimonio());
                attachedTestimonioList.add(testimonioListTestimonioToAttach);
            }
            terapia.setTestimonioList(attachedTestimonioList);
            List<HistoriaClinica> attachedHistoriaClinicaList = new ArrayList<HistoriaClinica>();
            for (HistoriaClinica historiaClinicaListHistoriaClinicaToAttach : terapia.getHistoriaClinicaList()) {
                historiaClinicaListHistoriaClinicaToAttach = em.getReference(historiaClinicaListHistoriaClinicaToAttach.getClass(), historiaClinicaListHistoriaClinicaToAttach.getPacienteId());
                attachedHistoriaClinicaList.add(historiaClinicaListHistoriaClinicaToAttach);
            }
            terapia.setHistoriaClinicaList(attachedHistoriaClinicaList);
            em.persist(terapia);
            for (Testimonio testimonioListTestimonio : terapia.getTestimonioList()) {
                Terapia oldCodigoTerapiaOfTestimonioListTestimonio = testimonioListTestimonio.getCodigoTerapia();
                testimonioListTestimonio.setCodigoTerapia(terapia);
                testimonioListTestimonio = em.merge(testimonioListTestimonio);
                if (oldCodigoTerapiaOfTestimonioListTestimonio != null) {
                    oldCodigoTerapiaOfTestimonioListTestimonio.getTestimonioList().remove(testimonioListTestimonio);
                    oldCodigoTerapiaOfTestimonioListTestimonio = em.merge(oldCodigoTerapiaOfTestimonioListTestimonio);
                }
            }
            for (HistoriaClinica historiaClinicaListHistoriaClinica : terapia.getHistoriaClinicaList()) {
                Terapia oldCodigoTerapiaOfHistoriaClinicaListHistoriaClinica = historiaClinicaListHistoriaClinica.getCodigoTerapia();
                historiaClinicaListHistoriaClinica.setCodigoTerapia(terapia);
                historiaClinicaListHistoriaClinica = em.merge(historiaClinicaListHistoriaClinica);
                if (oldCodigoTerapiaOfHistoriaClinicaListHistoriaClinica != null) {
                    oldCodigoTerapiaOfHistoriaClinicaListHistoriaClinica.getHistoriaClinicaList().remove(historiaClinicaListHistoriaClinica);
                    oldCodigoTerapiaOfHistoriaClinicaListHistoriaClinica = em.merge(oldCodigoTerapiaOfHistoriaClinicaListHistoriaClinica);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTerapia(terapia.getCodigoTerapia()) != null) {
                throw new PreexistingEntityException("Terapia " + terapia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Terapia terapia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Terapia persistentTerapia = em.find(Terapia.class, terapia.getCodigoTerapia());
            List<Testimonio> testimonioListOld = persistentTerapia.getTestimonioList();
            List<Testimonio> testimonioListNew = terapia.getTestimonioList();
            List<HistoriaClinica> historiaClinicaListOld = persistentTerapia.getHistoriaClinicaList();
            List<HistoriaClinica> historiaClinicaListNew = terapia.getHistoriaClinicaList();
            List<String> illegalOrphanMessages = null;
            for (Testimonio testimonioListOldTestimonio : testimonioListOld) {
                if (!testimonioListNew.contains(testimonioListOldTestimonio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Testimonio " + testimonioListOldTestimonio + " since its codigoTerapia field is not nullable.");
                }
            }
            for (HistoriaClinica historiaClinicaListOldHistoriaClinica : historiaClinicaListOld) {
                if (!historiaClinicaListNew.contains(historiaClinicaListOldHistoriaClinica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HistoriaClinica " + historiaClinicaListOldHistoriaClinica + " since its codigoTerapia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Testimonio> attachedTestimonioListNew = new ArrayList<Testimonio>();
            for (Testimonio testimonioListNewTestimonioToAttach : testimonioListNew) {
                testimonioListNewTestimonioToAttach = em.getReference(testimonioListNewTestimonioToAttach.getClass(), testimonioListNewTestimonioToAttach.getIdTestimonio());
                attachedTestimonioListNew.add(testimonioListNewTestimonioToAttach);
            }
            testimonioListNew = attachedTestimonioListNew;
            terapia.setTestimonioList(testimonioListNew);
            List<HistoriaClinica> attachedHistoriaClinicaListNew = new ArrayList<HistoriaClinica>();
            for (HistoriaClinica historiaClinicaListNewHistoriaClinicaToAttach : historiaClinicaListNew) {
                historiaClinicaListNewHistoriaClinicaToAttach = em.getReference(historiaClinicaListNewHistoriaClinicaToAttach.getClass(), historiaClinicaListNewHistoriaClinicaToAttach.getPacienteId());
                attachedHistoriaClinicaListNew.add(historiaClinicaListNewHistoriaClinicaToAttach);
            }
            historiaClinicaListNew = attachedHistoriaClinicaListNew;
            terapia.setHistoriaClinicaList(historiaClinicaListNew);
            terapia = em.merge(terapia);
            for (Testimonio testimonioListNewTestimonio : testimonioListNew) {
                if (!testimonioListOld.contains(testimonioListNewTestimonio)) {
                    Terapia oldCodigoTerapiaOfTestimonioListNewTestimonio = testimonioListNewTestimonio.getCodigoTerapia();
                    testimonioListNewTestimonio.setCodigoTerapia(terapia);
                    testimonioListNewTestimonio = em.merge(testimonioListNewTestimonio);
                    if (oldCodigoTerapiaOfTestimonioListNewTestimonio != null && !oldCodigoTerapiaOfTestimonioListNewTestimonio.equals(terapia)) {
                        oldCodigoTerapiaOfTestimonioListNewTestimonio.getTestimonioList().remove(testimonioListNewTestimonio);
                        oldCodigoTerapiaOfTestimonioListNewTestimonio = em.merge(oldCodigoTerapiaOfTestimonioListNewTestimonio);
                    }
                }
            }
            for (HistoriaClinica historiaClinicaListNewHistoriaClinica : historiaClinicaListNew) {
                if (!historiaClinicaListOld.contains(historiaClinicaListNewHistoriaClinica)) {
                    Terapia oldCodigoTerapiaOfHistoriaClinicaListNewHistoriaClinica = historiaClinicaListNewHistoriaClinica.getCodigoTerapia();
                    historiaClinicaListNewHistoriaClinica.setCodigoTerapia(terapia);
                    historiaClinicaListNewHistoriaClinica = em.merge(historiaClinicaListNewHistoriaClinica);
                    if (oldCodigoTerapiaOfHistoriaClinicaListNewHistoriaClinica != null && !oldCodigoTerapiaOfHistoriaClinicaListNewHistoriaClinica.equals(terapia)) {
                        oldCodigoTerapiaOfHistoriaClinicaListNewHistoriaClinica.getHistoriaClinicaList().remove(historiaClinicaListNewHistoriaClinica);
                        oldCodigoTerapiaOfHistoriaClinicaListNewHistoriaClinica = em.merge(oldCodigoTerapiaOfHistoriaClinicaListNewHistoriaClinica);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = terapia.getCodigoTerapia();
                if (findTerapia(id) == null) {
                    throw new NonexistentEntityException("The terapia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Terapia terapia;
            try {
                terapia = em.getReference(Terapia.class, id);
                terapia.getCodigoTerapia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The terapia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Testimonio> testimonioListOrphanCheck = terapia.getTestimonioList();
            for (Testimonio testimonioListOrphanCheckTestimonio : testimonioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Terapia (" + terapia + ") cannot be destroyed since the Testimonio " + testimonioListOrphanCheckTestimonio + " in its testimonioList field has a non-nullable codigoTerapia field.");
            }
            List<HistoriaClinica> historiaClinicaListOrphanCheck = terapia.getHistoriaClinicaList();
            for (HistoriaClinica historiaClinicaListOrphanCheckHistoriaClinica : historiaClinicaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Terapia (" + terapia + ") cannot be destroyed since the HistoriaClinica " + historiaClinicaListOrphanCheckHistoriaClinica + " in its historiaClinicaList field has a non-nullable codigoTerapia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(terapia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Terapia> findTerapiaEntities() {
        return findTerapiaEntities(true, -1, -1);
    }

    public List<Terapia> findTerapiaEntities(int maxResults, int firstResult) {
        return findTerapiaEntities(false, maxResults, firstResult);
    }

    private List<Terapia> findTerapiaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Terapia.class));
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

    public Terapia findTerapia(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Terapia.class, id);
        } finally {
            em.close();
        }
    }

    public int getTerapiaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Terapia> rt = cq.from(Terapia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

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
import modelo.HistoriaClinica;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Medicamento;

/**
 *
 * @author David
 */
public class MedicamentoJpaDAO implements Serializable {

    public MedicamentoJpaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Medicamento medicamento) throws PreexistingEntityException, Exception {
        if (medicamento.getHistoriaClinicaList() == null) {
            medicamento.setHistoriaClinicaList(new ArrayList<HistoriaClinica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<HistoriaClinica> attachedHistoriaClinicaList = new ArrayList<HistoriaClinica>();
            for (HistoriaClinica historiaClinicaListHistoriaClinicaToAttach : medicamento.getHistoriaClinicaList()) {
                historiaClinicaListHistoriaClinicaToAttach = em.getReference(historiaClinicaListHistoriaClinicaToAttach.getClass(), historiaClinicaListHistoriaClinicaToAttach.getPacienteId());
                attachedHistoriaClinicaList.add(historiaClinicaListHistoriaClinicaToAttach);
            }
            medicamento.setHistoriaClinicaList(attachedHistoriaClinicaList);
            em.persist(medicamento);
            for (HistoriaClinica historiaClinicaListHistoriaClinica : medicamento.getHistoriaClinicaList()) {
                Medicamento oldCodigoMedicamentoOfHistoriaClinicaListHistoriaClinica = historiaClinicaListHistoriaClinica.getCodigoMedicamento();
                historiaClinicaListHistoriaClinica.setCodigoMedicamento(medicamento);
                historiaClinicaListHistoriaClinica = em.merge(historiaClinicaListHistoriaClinica);
                if (oldCodigoMedicamentoOfHistoriaClinicaListHistoriaClinica != null) {
                    oldCodigoMedicamentoOfHistoriaClinicaListHistoriaClinica.getHistoriaClinicaList().remove(historiaClinicaListHistoriaClinica);
                    oldCodigoMedicamentoOfHistoriaClinicaListHistoriaClinica = em.merge(oldCodigoMedicamentoOfHistoriaClinicaListHistoriaClinica);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMedicamento(medicamento.getCodigoMedicamento()) != null) {
                throw new PreexistingEntityException("Medicamento " + medicamento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Medicamento medicamento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medicamento persistentMedicamento = em.find(Medicamento.class, medicamento.getCodigoMedicamento());
            List<HistoriaClinica> historiaClinicaListOld = persistentMedicamento.getHistoriaClinicaList();
            List<HistoriaClinica> historiaClinicaListNew = medicamento.getHistoriaClinicaList();
            List<String> illegalOrphanMessages = null;
            for (HistoriaClinica historiaClinicaListOldHistoriaClinica : historiaClinicaListOld) {
                if (!historiaClinicaListNew.contains(historiaClinicaListOldHistoriaClinica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HistoriaClinica " + historiaClinicaListOldHistoriaClinica + " since its codigoMedicamento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<HistoriaClinica> attachedHistoriaClinicaListNew = new ArrayList<HistoriaClinica>();
            for (HistoriaClinica historiaClinicaListNewHistoriaClinicaToAttach : historiaClinicaListNew) {
                historiaClinicaListNewHistoriaClinicaToAttach = em.getReference(historiaClinicaListNewHistoriaClinicaToAttach.getClass(), historiaClinicaListNewHistoriaClinicaToAttach.getPacienteId());
                attachedHistoriaClinicaListNew.add(historiaClinicaListNewHistoriaClinicaToAttach);
            }
            historiaClinicaListNew = attachedHistoriaClinicaListNew;
            medicamento.setHistoriaClinicaList(historiaClinicaListNew);
            medicamento = em.merge(medicamento);
            for (HistoriaClinica historiaClinicaListNewHistoriaClinica : historiaClinicaListNew) {
                if (!historiaClinicaListOld.contains(historiaClinicaListNewHistoriaClinica)) {
                    Medicamento oldCodigoMedicamentoOfHistoriaClinicaListNewHistoriaClinica = historiaClinicaListNewHistoriaClinica.getCodigoMedicamento();
                    historiaClinicaListNewHistoriaClinica.setCodigoMedicamento(medicamento);
                    historiaClinicaListNewHistoriaClinica = em.merge(historiaClinicaListNewHistoriaClinica);
                    if (oldCodigoMedicamentoOfHistoriaClinicaListNewHistoriaClinica != null && !oldCodigoMedicamentoOfHistoriaClinicaListNewHistoriaClinica.equals(medicamento)) {
                        oldCodigoMedicamentoOfHistoriaClinicaListNewHistoriaClinica.getHistoriaClinicaList().remove(historiaClinicaListNewHistoriaClinica);
                        oldCodigoMedicamentoOfHistoriaClinicaListNewHistoriaClinica = em.merge(oldCodigoMedicamentoOfHistoriaClinicaListNewHistoriaClinica);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = medicamento.getCodigoMedicamento();
                if (findMedicamento(id) == null) {
                    throw new NonexistentEntityException("The medicamento with id " + id + " no longer exists.");
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
            Medicamento medicamento;
            try {
                medicamento = em.getReference(Medicamento.class, id);
                medicamento.getCodigoMedicamento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medicamento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<HistoriaClinica> historiaClinicaListOrphanCheck = medicamento.getHistoriaClinicaList();
            for (HistoriaClinica historiaClinicaListOrphanCheckHistoriaClinica : historiaClinicaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Medicamento (" + medicamento + ") cannot be destroyed since the HistoriaClinica " + historiaClinicaListOrphanCheckHistoriaClinica + " in its historiaClinicaList field has a non-nullable codigoMedicamento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(medicamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Medicamento> findMedicamentoEntities() {
        return findMedicamentoEntities(true, -1, -1);
    }

    public List<Medicamento> findMedicamentoEntities(int maxResults, int firstResult) {
        return findMedicamentoEntities(false, maxResults, firstResult);
    }

    private List<Medicamento> findMedicamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Medicamento.class));
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

    public Medicamento findMedicamento(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Medicamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedicamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Medicamento> rt = cq.from(Medicamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

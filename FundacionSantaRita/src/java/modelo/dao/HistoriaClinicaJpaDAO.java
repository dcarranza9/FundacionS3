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
import modelo.Medicamento;
import modelo.Paciente;
import modelo.Terapia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.HistoriaClinica;

/**
 *
 * @author David
 */
public class HistoriaClinicaJpaDAO implements Serializable {

    public HistoriaClinicaJpaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HistoriaClinica historiaClinica) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Paciente pacienteOrphanCheck = historiaClinica.getPaciente();
        if (pacienteOrphanCheck != null) {
            HistoriaClinica oldHistoriaClinicaOfPaciente = pacienteOrphanCheck.getHistoriaClinica();
            if (oldHistoriaClinicaOfPaciente != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Paciente " + pacienteOrphanCheck + " already has an item of type HistoriaClinica whose paciente column cannot be null. Please make another selection for the paciente field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medicamento codigoMedicamento = historiaClinica.getCodigoMedicamento();
            if (codigoMedicamento != null) {
                codigoMedicamento = em.getReference(codigoMedicamento.getClass(), codigoMedicamento.getCodigoMedicamento());
                historiaClinica.setCodigoMedicamento(codigoMedicamento);
            }
            Paciente paciente = historiaClinica.getPaciente();
            if (paciente != null) {
                paciente = em.getReference(paciente.getClass(), paciente.getIdPaciente());
                historiaClinica.setPaciente(paciente);
            }
            Terapia codigoTerapia = historiaClinica.getCodigoTerapia();
            if (codigoTerapia != null) {
                codigoTerapia = em.getReference(codigoTerapia.getClass(), codigoTerapia.getCodigoTerapia());
                historiaClinica.setCodigoTerapia(codigoTerapia);
            }
            em.persist(historiaClinica);
            if (codigoMedicamento != null) {
                codigoMedicamento.getHistoriaClinicaList().add(historiaClinica);
                codigoMedicamento = em.merge(codigoMedicamento);
            }
            if (paciente != null) {
                paciente.setHistoriaClinica(historiaClinica);
                paciente = em.merge(paciente);
            }
            if (codigoTerapia != null) {
                codigoTerapia.getHistoriaClinicaList().add(historiaClinica);
                codigoTerapia = em.merge(codigoTerapia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHistoriaClinica(historiaClinica.getPacienteId()) != null) {
                throw new PreexistingEntityException("HistoriaClinica " + historiaClinica + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HistoriaClinica historiaClinica) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HistoriaClinica persistentHistoriaClinica = em.find(HistoriaClinica.class, historiaClinica.getPacienteId());
            Medicamento codigoMedicamentoOld = persistentHistoriaClinica.getCodigoMedicamento();
            Medicamento codigoMedicamentoNew = historiaClinica.getCodigoMedicamento();
            Paciente pacienteOld = persistentHistoriaClinica.getPaciente();
            Paciente pacienteNew = historiaClinica.getPaciente();
            Terapia codigoTerapiaOld = persistentHistoriaClinica.getCodigoTerapia();
            Terapia codigoTerapiaNew = historiaClinica.getCodigoTerapia();
            List<String> illegalOrphanMessages = null;
            if (pacienteNew != null && !pacienteNew.equals(pacienteOld)) {
                HistoriaClinica oldHistoriaClinicaOfPaciente = pacienteNew.getHistoriaClinica();
                if (oldHistoriaClinicaOfPaciente != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Paciente " + pacienteNew + " already has an item of type HistoriaClinica whose paciente column cannot be null. Please make another selection for the paciente field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codigoMedicamentoNew != null) {
                codigoMedicamentoNew = em.getReference(codigoMedicamentoNew.getClass(), codigoMedicamentoNew.getCodigoMedicamento());
                historiaClinica.setCodigoMedicamento(codigoMedicamentoNew);
            }
            if (pacienteNew != null) {
                pacienteNew = em.getReference(pacienteNew.getClass(), pacienteNew.getIdPaciente());
                historiaClinica.setPaciente(pacienteNew);
            }
            if (codigoTerapiaNew != null) {
                codigoTerapiaNew = em.getReference(codigoTerapiaNew.getClass(), codigoTerapiaNew.getCodigoTerapia());
                historiaClinica.setCodigoTerapia(codigoTerapiaNew);
            }
            historiaClinica = em.merge(historiaClinica);
            if (codigoMedicamentoOld != null && !codigoMedicamentoOld.equals(codigoMedicamentoNew)) {
                codigoMedicamentoOld.getHistoriaClinicaList().remove(historiaClinica);
                codigoMedicamentoOld = em.merge(codigoMedicamentoOld);
            }
            if (codigoMedicamentoNew != null && !codigoMedicamentoNew.equals(codigoMedicamentoOld)) {
                codigoMedicamentoNew.getHistoriaClinicaList().add(historiaClinica);
                codigoMedicamentoNew = em.merge(codigoMedicamentoNew);
            }
            if (pacienteOld != null && !pacienteOld.equals(pacienteNew)) {
                pacienteOld.setHistoriaClinica(null);
                pacienteOld = em.merge(pacienteOld);
            }
            if (pacienteNew != null && !pacienteNew.equals(pacienteOld)) {
                pacienteNew.setHistoriaClinica(historiaClinica);
                pacienteNew = em.merge(pacienteNew);
            }
            if (codigoTerapiaOld != null && !codigoTerapiaOld.equals(codigoTerapiaNew)) {
                codigoTerapiaOld.getHistoriaClinicaList().remove(historiaClinica);
                codigoTerapiaOld = em.merge(codigoTerapiaOld);
            }
            if (codigoTerapiaNew != null && !codigoTerapiaNew.equals(codigoTerapiaOld)) {
                codigoTerapiaNew.getHistoriaClinicaList().add(historiaClinica);
                codigoTerapiaNew = em.merge(codigoTerapiaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = historiaClinica.getPacienteId();
                if (findHistoriaClinica(id) == null) {
                    throw new NonexistentEntityException("The historiaClinica with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HistoriaClinica historiaClinica;
            try {
                historiaClinica = em.getReference(HistoriaClinica.class, id);
                historiaClinica.getPacienteId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historiaClinica with id " + id + " no longer exists.", enfe);
            }
            Medicamento codigoMedicamento = historiaClinica.getCodigoMedicamento();
            if (codigoMedicamento != null) {
                codigoMedicamento.getHistoriaClinicaList().remove(historiaClinica);
                codigoMedicamento = em.merge(codigoMedicamento);
            }
            Paciente paciente = historiaClinica.getPaciente();
            if (paciente != null) {
                paciente.setHistoriaClinica(null);
                paciente = em.merge(paciente);
            }
            Terapia codigoTerapia = historiaClinica.getCodigoTerapia();
            if (codigoTerapia != null) {
                codigoTerapia.getHistoriaClinicaList().remove(historiaClinica);
                codigoTerapia = em.merge(codigoTerapia);
            }
            em.remove(historiaClinica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HistoriaClinica> findHistoriaClinicaEntities() {
        return findHistoriaClinicaEntities(true, -1, -1);
    }

    public List<HistoriaClinica> findHistoriaClinicaEntities(int maxResults, int firstResult) {
        return findHistoriaClinicaEntities(false, maxResults, firstResult);
    }

    private List<HistoriaClinica> findHistoriaClinicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistoriaClinica.class));
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

    public HistoriaClinica findHistoriaClinica(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistoriaClinica.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoriaClinicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistoriaClinica> rt = cq.from(HistoriaClinica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

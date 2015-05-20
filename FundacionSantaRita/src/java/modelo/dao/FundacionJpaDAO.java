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
import modelo.Evento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Donacion;
import modelo.Funcionario;
import modelo.Fundacion;

/**
 *
 * @author David
 */
public class FundacionJpaDAO implements Serializable {

    public FundacionJpaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fundacion fundacion) throws PreexistingEntityException, Exception {
        if (fundacion.getEventoList() == null) {
            fundacion.setEventoList(new ArrayList<Evento>());
        }
        if (fundacion.getDonacionList() == null) {
            fundacion.setDonacionList(new ArrayList<Donacion>());
        }
        if (fundacion.getFuncionarioList() == null) {
            fundacion.setFuncionarioList(new ArrayList<Funcionario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Evento> attachedEventoList = new ArrayList<Evento>();
            for (Evento eventoListEventoToAttach : fundacion.getEventoList()) {
                eventoListEventoToAttach = em.getReference(eventoListEventoToAttach.getClass(), eventoListEventoToAttach.getIdEvento());
                attachedEventoList.add(eventoListEventoToAttach);
            }
            fundacion.setEventoList(attachedEventoList);
            List<Donacion> attachedDonacionList = new ArrayList<Donacion>();
            for (Donacion donacionListDonacionToAttach : fundacion.getDonacionList()) {
                donacionListDonacionToAttach = em.getReference(donacionListDonacionToAttach.getClass(), donacionListDonacionToAttach.getIdDonacion());
                attachedDonacionList.add(donacionListDonacionToAttach);
            }
            fundacion.setDonacionList(attachedDonacionList);
            List<Funcionario> attachedFuncionarioList = new ArrayList<Funcionario>();
            for (Funcionario funcionarioListFuncionarioToAttach : fundacion.getFuncionarioList()) {
                funcionarioListFuncionarioToAttach = em.getReference(funcionarioListFuncionarioToAttach.getClass(), funcionarioListFuncionarioToAttach.getIdFuncionario());
                attachedFuncionarioList.add(funcionarioListFuncionarioToAttach);
            }
            fundacion.setFuncionarioList(attachedFuncionarioList);
            em.persist(fundacion);
            for (Evento eventoListEvento : fundacion.getEventoList()) {
                Fundacion oldNitOfEventoListEvento = eventoListEvento.getNit();
                eventoListEvento.setNit(fundacion);
                eventoListEvento = em.merge(eventoListEvento);
                if (oldNitOfEventoListEvento != null) {
                    oldNitOfEventoListEvento.getEventoList().remove(eventoListEvento);
                    oldNitOfEventoListEvento = em.merge(oldNitOfEventoListEvento);
                }
            }
            for (Donacion donacionListDonacion : fundacion.getDonacionList()) {
                Fundacion oldNitOfDonacionListDonacion = donacionListDonacion.getNit();
                donacionListDonacion.setNit(fundacion);
                donacionListDonacion = em.merge(donacionListDonacion);
                if (oldNitOfDonacionListDonacion != null) {
                    oldNitOfDonacionListDonacion.getDonacionList().remove(donacionListDonacion);
                    oldNitOfDonacionListDonacion = em.merge(oldNitOfDonacionListDonacion);
                }
            }
            for (Funcionario funcionarioListFuncionario : fundacion.getFuncionarioList()) {
                Fundacion oldNitOfFuncionarioListFuncionario = funcionarioListFuncionario.getNit();
                funcionarioListFuncionario.setNit(fundacion);
                funcionarioListFuncionario = em.merge(funcionarioListFuncionario);
                if (oldNitOfFuncionarioListFuncionario != null) {
                    oldNitOfFuncionarioListFuncionario.getFuncionarioList().remove(funcionarioListFuncionario);
                    oldNitOfFuncionarioListFuncionario = em.merge(oldNitOfFuncionarioListFuncionario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFundacion(fundacion.getNit()) != null) {
                throw new PreexistingEntityException("Fundacion " + fundacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fundacion fundacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fundacion persistentFundacion = em.find(Fundacion.class, fundacion.getNit());
            List<Evento> eventoListOld = persistentFundacion.getEventoList();
            List<Evento> eventoListNew = fundacion.getEventoList();
            List<Donacion> donacionListOld = persistentFundacion.getDonacionList();
            List<Donacion> donacionListNew = fundacion.getDonacionList();
            List<Funcionario> funcionarioListOld = persistentFundacion.getFuncionarioList();
            List<Funcionario> funcionarioListNew = fundacion.getFuncionarioList();
            List<String> illegalOrphanMessages = null;
            for (Evento eventoListOldEvento : eventoListOld) {
                if (!eventoListNew.contains(eventoListOldEvento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evento " + eventoListOldEvento + " since its nit field is not nullable.");
                }
            }
            for (Donacion donacionListOldDonacion : donacionListOld) {
                if (!donacionListNew.contains(donacionListOldDonacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Donacion " + donacionListOldDonacion + " since its nit field is not nullable.");
                }
            }
            for (Funcionario funcionarioListOldFuncionario : funcionarioListOld) {
                if (!funcionarioListNew.contains(funcionarioListOldFuncionario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Funcionario " + funcionarioListOldFuncionario + " since its nit field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Evento> attachedEventoListNew = new ArrayList<Evento>();
            for (Evento eventoListNewEventoToAttach : eventoListNew) {
                eventoListNewEventoToAttach = em.getReference(eventoListNewEventoToAttach.getClass(), eventoListNewEventoToAttach.getIdEvento());
                attachedEventoListNew.add(eventoListNewEventoToAttach);
            }
            eventoListNew = attachedEventoListNew;
            fundacion.setEventoList(eventoListNew);
            List<Donacion> attachedDonacionListNew = new ArrayList<Donacion>();
            for (Donacion donacionListNewDonacionToAttach : donacionListNew) {
                donacionListNewDonacionToAttach = em.getReference(donacionListNewDonacionToAttach.getClass(), donacionListNewDonacionToAttach.getIdDonacion());
                attachedDonacionListNew.add(donacionListNewDonacionToAttach);
            }
            donacionListNew = attachedDonacionListNew;
            fundacion.setDonacionList(donacionListNew);
            List<Funcionario> attachedFuncionarioListNew = new ArrayList<Funcionario>();
            for (Funcionario funcionarioListNewFuncionarioToAttach : funcionarioListNew) {
                funcionarioListNewFuncionarioToAttach = em.getReference(funcionarioListNewFuncionarioToAttach.getClass(), funcionarioListNewFuncionarioToAttach.getIdFuncionario());
                attachedFuncionarioListNew.add(funcionarioListNewFuncionarioToAttach);
            }
            funcionarioListNew = attachedFuncionarioListNew;
            fundacion.setFuncionarioList(funcionarioListNew);
            fundacion = em.merge(fundacion);
            for (Evento eventoListNewEvento : eventoListNew) {
                if (!eventoListOld.contains(eventoListNewEvento)) {
                    Fundacion oldNitOfEventoListNewEvento = eventoListNewEvento.getNit();
                    eventoListNewEvento.setNit(fundacion);
                    eventoListNewEvento = em.merge(eventoListNewEvento);
                    if (oldNitOfEventoListNewEvento != null && !oldNitOfEventoListNewEvento.equals(fundacion)) {
                        oldNitOfEventoListNewEvento.getEventoList().remove(eventoListNewEvento);
                        oldNitOfEventoListNewEvento = em.merge(oldNitOfEventoListNewEvento);
                    }
                }
            }
            for (Donacion donacionListNewDonacion : donacionListNew) {
                if (!donacionListOld.contains(donacionListNewDonacion)) {
                    Fundacion oldNitOfDonacionListNewDonacion = donacionListNewDonacion.getNit();
                    donacionListNewDonacion.setNit(fundacion);
                    donacionListNewDonacion = em.merge(donacionListNewDonacion);
                    if (oldNitOfDonacionListNewDonacion != null && !oldNitOfDonacionListNewDonacion.equals(fundacion)) {
                        oldNitOfDonacionListNewDonacion.getDonacionList().remove(donacionListNewDonacion);
                        oldNitOfDonacionListNewDonacion = em.merge(oldNitOfDonacionListNewDonacion);
                    }
                }
            }
            for (Funcionario funcionarioListNewFuncionario : funcionarioListNew) {
                if (!funcionarioListOld.contains(funcionarioListNewFuncionario)) {
                    Fundacion oldNitOfFuncionarioListNewFuncionario = funcionarioListNewFuncionario.getNit();
                    funcionarioListNewFuncionario.setNit(fundacion);
                    funcionarioListNewFuncionario = em.merge(funcionarioListNewFuncionario);
                    if (oldNitOfFuncionarioListNewFuncionario != null && !oldNitOfFuncionarioListNewFuncionario.equals(fundacion)) {
                        oldNitOfFuncionarioListNewFuncionario.getFuncionarioList().remove(funcionarioListNewFuncionario);
                        oldNitOfFuncionarioListNewFuncionario = em.merge(oldNitOfFuncionarioListNewFuncionario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = fundacion.getNit();
                if (findFundacion(id) == null) {
                    throw new NonexistentEntityException("The fundacion with id " + id + " no longer exists.");
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
            Fundacion fundacion;
            try {
                fundacion = em.getReference(Fundacion.class, id);
                fundacion.getNit();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fundacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Evento> eventoListOrphanCheck = fundacion.getEventoList();
            for (Evento eventoListOrphanCheckEvento : eventoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Fundacion (" + fundacion + ") cannot be destroyed since the Evento " + eventoListOrphanCheckEvento + " in its eventoList field has a non-nullable nit field.");
            }
            List<Donacion> donacionListOrphanCheck = fundacion.getDonacionList();
            for (Donacion donacionListOrphanCheckDonacion : donacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Fundacion (" + fundacion + ") cannot be destroyed since the Donacion " + donacionListOrphanCheckDonacion + " in its donacionList field has a non-nullable nit field.");
            }
            List<Funcionario> funcionarioListOrphanCheck = fundacion.getFuncionarioList();
            for (Funcionario funcionarioListOrphanCheckFuncionario : funcionarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Fundacion (" + fundacion + ") cannot be destroyed since the Funcionario " + funcionarioListOrphanCheckFuncionario + " in its funcionarioList field has a non-nullable nit field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(fundacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fundacion> findFundacionEntities() {
        return findFundacionEntities(true, -1, -1);
    }

    public List<Fundacion> findFundacionEntities(int maxResults, int firstResult) {
        return findFundacionEntities(false, maxResults, firstResult);
    }

    private List<Fundacion> findFundacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fundacion.class));
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

    public Fundacion findFundacion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fundacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getFundacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fundacion> rt = cq.from(Fundacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

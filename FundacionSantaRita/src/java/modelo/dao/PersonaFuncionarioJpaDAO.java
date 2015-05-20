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
import modelo.Funcionario;
import modelo.Persona;
import modelo.PersonaFuncionario;

/**
 *
 * @author David
 */
public class PersonaFuncionarioJpaDAO implements Serializable {

    public PersonaFuncionarioJpaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PersonaFuncionario personaFuncionario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario idFuncionario = personaFuncionario.getIdFuncionario();
            if (idFuncionario != null) {
                idFuncionario = em.getReference(idFuncionario.getClass(), idFuncionario.getIdFuncionario());
                personaFuncionario.setIdFuncionario(idFuncionario);
            }
            Persona cedula = personaFuncionario.getCedula();
            if (cedula != null) {
                cedula = em.getReference(cedula.getClass(), cedula.getCedula());
                personaFuncionario.setCedula(cedula);
            }
            em.persist(personaFuncionario);
            if (idFuncionario != null) {
                idFuncionario.getPersonaFuncionarioList().add(personaFuncionario);
                idFuncionario = em.merge(idFuncionario);
            }
            if (cedula != null) {
                cedula.getPersonaFuncionarioList().add(personaFuncionario);
                cedula = em.merge(cedula);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PersonaFuncionario personaFuncionario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PersonaFuncionario persistentPersonaFuncionario = em.find(PersonaFuncionario.class, personaFuncionario.getIdPesonaFuncionario());
            Funcionario idFuncionarioOld = persistentPersonaFuncionario.getIdFuncionario();
            Funcionario idFuncionarioNew = personaFuncionario.getIdFuncionario();
            Persona cedulaOld = persistentPersonaFuncionario.getCedula();
            Persona cedulaNew = personaFuncionario.getCedula();
            if (idFuncionarioNew != null) {
                idFuncionarioNew = em.getReference(idFuncionarioNew.getClass(), idFuncionarioNew.getIdFuncionario());
                personaFuncionario.setIdFuncionario(idFuncionarioNew);
            }
            if (cedulaNew != null) {
                cedulaNew = em.getReference(cedulaNew.getClass(), cedulaNew.getCedula());
                personaFuncionario.setCedula(cedulaNew);
            }
            personaFuncionario = em.merge(personaFuncionario);
            if (idFuncionarioOld != null && !idFuncionarioOld.equals(idFuncionarioNew)) {
                idFuncionarioOld.getPersonaFuncionarioList().remove(personaFuncionario);
                idFuncionarioOld = em.merge(idFuncionarioOld);
            }
            if (idFuncionarioNew != null && !idFuncionarioNew.equals(idFuncionarioOld)) {
                idFuncionarioNew.getPersonaFuncionarioList().add(personaFuncionario);
                idFuncionarioNew = em.merge(idFuncionarioNew);
            }
            if (cedulaOld != null && !cedulaOld.equals(cedulaNew)) {
                cedulaOld.getPersonaFuncionarioList().remove(personaFuncionario);
                cedulaOld = em.merge(cedulaOld);
            }
            if (cedulaNew != null && !cedulaNew.equals(cedulaOld)) {
                cedulaNew.getPersonaFuncionarioList().add(personaFuncionario);
                cedulaNew = em.merge(cedulaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = personaFuncionario.getIdPesonaFuncionario();
                if (findPersonaFuncionario(id) == null) {
                    throw new NonexistentEntityException("The personaFuncionario with id " + id + " no longer exists.");
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
            PersonaFuncionario personaFuncionario;
            try {
                personaFuncionario = em.getReference(PersonaFuncionario.class, id);
                personaFuncionario.getIdPesonaFuncionario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personaFuncionario with id " + id + " no longer exists.", enfe);
            }
            Funcionario idFuncionario = personaFuncionario.getIdFuncionario();
            if (idFuncionario != null) {
                idFuncionario.getPersonaFuncionarioList().remove(personaFuncionario);
                idFuncionario = em.merge(idFuncionario);
            }
            Persona cedula = personaFuncionario.getCedula();
            if (cedula != null) {
                cedula.getPersonaFuncionarioList().remove(personaFuncionario);
                cedula = em.merge(cedula);
            }
            em.remove(personaFuncionario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PersonaFuncionario> findPersonaFuncionarioEntities() {
        return findPersonaFuncionarioEntities(true, -1, -1);
    }

    public List<PersonaFuncionario> findPersonaFuncionarioEntities(int maxResults, int firstResult) {
        return findPersonaFuncionarioEntities(false, maxResults, firstResult);
    }

    private List<PersonaFuncionario> findPersonaFuncionarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PersonaFuncionario.class));
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

    public PersonaFuncionario findPersonaFuncionario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PersonaFuncionario.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaFuncionarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PersonaFuncionario> rt = cq.from(PersonaFuncionario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

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
import modelo.Persona;
import modelo.PersonaPaciente;

/**
 *
 * @author David
 */
public class PersonaPacienteJpaDAO implements Serializable {

    public PersonaPacienteJpaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PersonaPaciente personaPaciente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paciente idPaciente = personaPaciente.getIdPaciente();
            if (idPaciente != null) {
                idPaciente = em.getReference(idPaciente.getClass(), idPaciente.getIdPaciente());
                personaPaciente.setIdPaciente(idPaciente);
            }
            Persona cedula = personaPaciente.getCedula();
            if (cedula != null) {
                cedula = em.getReference(cedula.getClass(), cedula.getCedula());
                personaPaciente.setCedula(cedula);
            }
            em.persist(personaPaciente);
            if (idPaciente != null) {
                idPaciente.getPersonaPacienteList().add(personaPaciente);
                idPaciente = em.merge(idPaciente);
            }
            if (cedula != null) {
                cedula.getPersonaPacienteList().add(personaPaciente);
                cedula = em.merge(cedula);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PersonaPaciente personaPaciente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PersonaPaciente persistentPersonaPaciente = em.find(PersonaPaciente.class, personaPaciente.getIdPersonaPaciente());
            Paciente idPacienteOld = persistentPersonaPaciente.getIdPaciente();
            Paciente idPacienteNew = personaPaciente.getIdPaciente();
            Persona cedulaOld = persistentPersonaPaciente.getCedula();
            Persona cedulaNew = personaPaciente.getCedula();
            if (idPacienteNew != null) {
                idPacienteNew = em.getReference(idPacienteNew.getClass(), idPacienteNew.getIdPaciente());
                personaPaciente.setIdPaciente(idPacienteNew);
            }
            if (cedulaNew != null) {
                cedulaNew = em.getReference(cedulaNew.getClass(), cedulaNew.getCedula());
                personaPaciente.setCedula(cedulaNew);
            }
            personaPaciente = em.merge(personaPaciente);
            if (idPacienteOld != null && !idPacienteOld.equals(idPacienteNew)) {
                idPacienteOld.getPersonaPacienteList().remove(personaPaciente);
                idPacienteOld = em.merge(idPacienteOld);
            }
            if (idPacienteNew != null && !idPacienteNew.equals(idPacienteOld)) {
                idPacienteNew.getPersonaPacienteList().add(personaPaciente);
                idPacienteNew = em.merge(idPacienteNew);
            }
            if (cedulaOld != null && !cedulaOld.equals(cedulaNew)) {
                cedulaOld.getPersonaPacienteList().remove(personaPaciente);
                cedulaOld = em.merge(cedulaOld);
            }
            if (cedulaNew != null && !cedulaNew.equals(cedulaOld)) {
                cedulaNew.getPersonaPacienteList().add(personaPaciente);
                cedulaNew = em.merge(cedulaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = personaPaciente.getIdPersonaPaciente();
                if (findPersonaPaciente(id) == null) {
                    throw new NonexistentEntityException("The personaPaciente with id " + id + " no longer exists.");
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
            PersonaPaciente personaPaciente;
            try {
                personaPaciente = em.getReference(PersonaPaciente.class, id);
                personaPaciente.getIdPersonaPaciente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personaPaciente with id " + id + " no longer exists.", enfe);
            }
            Paciente idPaciente = personaPaciente.getIdPaciente();
            if (idPaciente != null) {
                idPaciente.getPersonaPacienteList().remove(personaPaciente);
                idPaciente = em.merge(idPaciente);
            }
            Persona cedula = personaPaciente.getCedula();
            if (cedula != null) {
                cedula.getPersonaPacienteList().remove(personaPaciente);
                cedula = em.merge(cedula);
            }
            em.remove(personaPaciente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PersonaPaciente> findPersonaPacienteEntities() {
        return findPersonaPacienteEntities(true, -1, -1);
    }

    public List<PersonaPaciente> findPersonaPacienteEntities(int maxResults, int firstResult) {
        return findPersonaPacienteEntities(false, maxResults, firstResult);
    }

    private List<PersonaPaciente> findPersonaPacienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PersonaPaciente.class));
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

    public PersonaPaciente findPersonaPaciente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PersonaPaciente.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaPacienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PersonaPaciente> rt = cq.from(PersonaPaciente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

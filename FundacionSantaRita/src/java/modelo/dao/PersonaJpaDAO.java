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
import modelo.PersonaPaciente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Persona;
import modelo.PersonaFuncionario;

/**
 *
 * @author David
 */
public class PersonaJpaDAO implements Serializable {

    public PersonaJpaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) throws PreexistingEntityException, Exception {
        if (persona.getPersonaPacienteList() == null) {
            persona.setPersonaPacienteList(new ArrayList<PersonaPaciente>());
        }
        if (persona.getPersonaFuncionarioList() == null) {
            persona.setPersonaFuncionarioList(new ArrayList<PersonaFuncionario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PersonaPaciente> attachedPersonaPacienteList = new ArrayList<PersonaPaciente>();
            for (PersonaPaciente personaPacienteListPersonaPacienteToAttach : persona.getPersonaPacienteList()) {
                personaPacienteListPersonaPacienteToAttach = em.getReference(personaPacienteListPersonaPacienteToAttach.getClass(), personaPacienteListPersonaPacienteToAttach.getIdPersonaPaciente());
                attachedPersonaPacienteList.add(personaPacienteListPersonaPacienteToAttach);
            }
            persona.setPersonaPacienteList(attachedPersonaPacienteList);
            List<PersonaFuncionario> attachedPersonaFuncionarioList = new ArrayList<PersonaFuncionario>();
            for (PersonaFuncionario personaFuncionarioListPersonaFuncionarioToAttach : persona.getPersonaFuncionarioList()) {
                personaFuncionarioListPersonaFuncionarioToAttach = em.getReference(personaFuncionarioListPersonaFuncionarioToAttach.getClass(), personaFuncionarioListPersonaFuncionarioToAttach.getIdPesonaFuncionario());
                attachedPersonaFuncionarioList.add(personaFuncionarioListPersonaFuncionarioToAttach);
            }
            persona.setPersonaFuncionarioList(attachedPersonaFuncionarioList);
            em.persist(persona);
            for (PersonaPaciente personaPacienteListPersonaPaciente : persona.getPersonaPacienteList()) {
                Persona oldCedulaOfPersonaPacienteListPersonaPaciente = personaPacienteListPersonaPaciente.getCedula();
                personaPacienteListPersonaPaciente.setCedula(persona);
                personaPacienteListPersonaPaciente = em.merge(personaPacienteListPersonaPaciente);
                if (oldCedulaOfPersonaPacienteListPersonaPaciente != null) {
                    oldCedulaOfPersonaPacienteListPersonaPaciente.getPersonaPacienteList().remove(personaPacienteListPersonaPaciente);
                    oldCedulaOfPersonaPacienteListPersonaPaciente = em.merge(oldCedulaOfPersonaPacienteListPersonaPaciente);
                }
            }
            for (PersonaFuncionario personaFuncionarioListPersonaFuncionario : persona.getPersonaFuncionarioList()) {
                Persona oldCedulaOfPersonaFuncionarioListPersonaFuncionario = personaFuncionarioListPersonaFuncionario.getCedula();
                personaFuncionarioListPersonaFuncionario.setCedula(persona);
                personaFuncionarioListPersonaFuncionario = em.merge(personaFuncionarioListPersonaFuncionario);
                if (oldCedulaOfPersonaFuncionarioListPersonaFuncionario != null) {
                    oldCedulaOfPersonaFuncionarioListPersonaFuncionario.getPersonaFuncionarioList().remove(personaFuncionarioListPersonaFuncionario);
                    oldCedulaOfPersonaFuncionarioListPersonaFuncionario = em.merge(oldCedulaOfPersonaFuncionarioListPersonaFuncionario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPersona(persona.getCedula()) != null) {
                throw new PreexistingEntityException("Persona " + persona + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getCedula());
            List<PersonaPaciente> personaPacienteListOld = persistentPersona.getPersonaPacienteList();
            List<PersonaPaciente> personaPacienteListNew = persona.getPersonaPacienteList();
            List<PersonaFuncionario> personaFuncionarioListOld = persistentPersona.getPersonaFuncionarioList();
            List<PersonaFuncionario> personaFuncionarioListNew = persona.getPersonaFuncionarioList();
            List<String> illegalOrphanMessages = null;
            for (PersonaPaciente personaPacienteListOldPersonaPaciente : personaPacienteListOld) {
                if (!personaPacienteListNew.contains(personaPacienteListOldPersonaPaciente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PersonaPaciente " + personaPacienteListOldPersonaPaciente + " since its cedula field is not nullable.");
                }
            }
            for (PersonaFuncionario personaFuncionarioListOldPersonaFuncionario : personaFuncionarioListOld) {
                if (!personaFuncionarioListNew.contains(personaFuncionarioListOldPersonaFuncionario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PersonaFuncionario " + personaFuncionarioListOldPersonaFuncionario + " since its cedula field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PersonaPaciente> attachedPersonaPacienteListNew = new ArrayList<PersonaPaciente>();
            for (PersonaPaciente personaPacienteListNewPersonaPacienteToAttach : personaPacienteListNew) {
                personaPacienteListNewPersonaPacienteToAttach = em.getReference(personaPacienteListNewPersonaPacienteToAttach.getClass(), personaPacienteListNewPersonaPacienteToAttach.getIdPersonaPaciente());
                attachedPersonaPacienteListNew.add(personaPacienteListNewPersonaPacienteToAttach);
            }
            personaPacienteListNew = attachedPersonaPacienteListNew;
            persona.setPersonaPacienteList(personaPacienteListNew);
            List<PersonaFuncionario> attachedPersonaFuncionarioListNew = new ArrayList<PersonaFuncionario>();
            for (PersonaFuncionario personaFuncionarioListNewPersonaFuncionarioToAttach : personaFuncionarioListNew) {
                personaFuncionarioListNewPersonaFuncionarioToAttach = em.getReference(personaFuncionarioListNewPersonaFuncionarioToAttach.getClass(), personaFuncionarioListNewPersonaFuncionarioToAttach.getIdPesonaFuncionario());
                attachedPersonaFuncionarioListNew.add(personaFuncionarioListNewPersonaFuncionarioToAttach);
            }
            personaFuncionarioListNew = attachedPersonaFuncionarioListNew;
            persona.setPersonaFuncionarioList(personaFuncionarioListNew);
            persona = em.merge(persona);
            for (PersonaPaciente personaPacienteListNewPersonaPaciente : personaPacienteListNew) {
                if (!personaPacienteListOld.contains(personaPacienteListNewPersonaPaciente)) {
                    Persona oldCedulaOfPersonaPacienteListNewPersonaPaciente = personaPacienteListNewPersonaPaciente.getCedula();
                    personaPacienteListNewPersonaPaciente.setCedula(persona);
                    personaPacienteListNewPersonaPaciente = em.merge(personaPacienteListNewPersonaPaciente);
                    if (oldCedulaOfPersonaPacienteListNewPersonaPaciente != null && !oldCedulaOfPersonaPacienteListNewPersonaPaciente.equals(persona)) {
                        oldCedulaOfPersonaPacienteListNewPersonaPaciente.getPersonaPacienteList().remove(personaPacienteListNewPersonaPaciente);
                        oldCedulaOfPersonaPacienteListNewPersonaPaciente = em.merge(oldCedulaOfPersonaPacienteListNewPersonaPaciente);
                    }
                }
            }
            for (PersonaFuncionario personaFuncionarioListNewPersonaFuncionario : personaFuncionarioListNew) {
                if (!personaFuncionarioListOld.contains(personaFuncionarioListNewPersonaFuncionario)) {
                    Persona oldCedulaOfPersonaFuncionarioListNewPersonaFuncionario = personaFuncionarioListNewPersonaFuncionario.getCedula();
                    personaFuncionarioListNewPersonaFuncionario.setCedula(persona);
                    personaFuncionarioListNewPersonaFuncionario = em.merge(personaFuncionarioListNewPersonaFuncionario);
                    if (oldCedulaOfPersonaFuncionarioListNewPersonaFuncionario != null && !oldCedulaOfPersonaFuncionarioListNewPersonaFuncionario.equals(persona)) {
                        oldCedulaOfPersonaFuncionarioListNewPersonaFuncionario.getPersonaFuncionarioList().remove(personaFuncionarioListNewPersonaFuncionario);
                        oldCedulaOfPersonaFuncionarioListNewPersonaFuncionario = em.merge(oldCedulaOfPersonaFuncionarioListNewPersonaFuncionario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = persona.getCedula();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
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
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PersonaPaciente> personaPacienteListOrphanCheck = persona.getPersonaPacienteList();
            for (PersonaPaciente personaPacienteListOrphanCheckPersonaPaciente : personaPacienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the PersonaPaciente " + personaPacienteListOrphanCheckPersonaPaciente + " in its personaPacienteList field has a non-nullable cedula field.");
            }
            List<PersonaFuncionario> personaFuncionarioListOrphanCheck = persona.getPersonaFuncionarioList();
            for (PersonaFuncionario personaFuncionarioListOrphanCheckPersonaFuncionario : personaFuncionarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the PersonaFuncionario " + personaFuncionarioListOrphanCheckPersonaFuncionario + " in its personaFuncionarioList field has a non-nullable cedula field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

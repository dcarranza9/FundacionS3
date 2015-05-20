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
import modelo.Usuario;
import modelo.Fundacion;
import modelo.Cita;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Funcionario;
import modelo.PersonaFuncionario;

/**
 *
 * @author David
 */
public class FuncionarioJpaDAO implements Serializable {

    public FuncionarioJpaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Funcionario funcionario) throws PreexistingEntityException, Exception {
        if (funcionario.getCitaList() == null) {
            funcionario.setCitaList(new ArrayList<Cita>());
        }
        if (funcionario.getPersonaFuncionarioList() == null) {
            funcionario.setPersonaFuncionarioList(new ArrayList<PersonaFuncionario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = funcionario.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getIdUsuario());
                funcionario.setUsuario(usuario);
            }
            Fundacion nit = funcionario.getNit();
            if (nit != null) {
                nit = em.getReference(nit.getClass(), nit.getNit());
                funcionario.setNit(nit);
            }
            List<Cita> attachedCitaList = new ArrayList<Cita>();
            for (Cita citaListCitaToAttach : funcionario.getCitaList()) {
                citaListCitaToAttach = em.getReference(citaListCitaToAttach.getClass(), citaListCitaToAttach.getIdCita());
                attachedCitaList.add(citaListCitaToAttach);
            }
            funcionario.setCitaList(attachedCitaList);
            List<PersonaFuncionario> attachedPersonaFuncionarioList = new ArrayList<PersonaFuncionario>();
            for (PersonaFuncionario personaFuncionarioListPersonaFuncionarioToAttach : funcionario.getPersonaFuncionarioList()) {
                personaFuncionarioListPersonaFuncionarioToAttach = em.getReference(personaFuncionarioListPersonaFuncionarioToAttach.getClass(), personaFuncionarioListPersonaFuncionarioToAttach.getIdPesonaFuncionario());
                attachedPersonaFuncionarioList.add(personaFuncionarioListPersonaFuncionarioToAttach);
            }
            funcionario.setPersonaFuncionarioList(attachedPersonaFuncionarioList);
            em.persist(funcionario);
            if (usuario != null) {
                Funcionario oldFuncionarioOfUsuario = usuario.getFuncionario();
                if (oldFuncionarioOfUsuario != null) {
                    oldFuncionarioOfUsuario.setUsuario(null);
                    oldFuncionarioOfUsuario = em.merge(oldFuncionarioOfUsuario);
                }
                usuario.setFuncionario(funcionario);
                usuario = em.merge(usuario);
            }
            if (nit != null) {
                nit.getFuncionarioList().add(funcionario);
                nit = em.merge(nit);
            }
            for (Cita citaListCita : funcionario.getCitaList()) {
                Funcionario oldIdFuncionarioOfCitaListCita = citaListCita.getIdFuncionario();
                citaListCita.setIdFuncionario(funcionario);
                citaListCita = em.merge(citaListCita);
                if (oldIdFuncionarioOfCitaListCita != null) {
                    oldIdFuncionarioOfCitaListCita.getCitaList().remove(citaListCita);
                    oldIdFuncionarioOfCitaListCita = em.merge(oldIdFuncionarioOfCitaListCita);
                }
            }
            for (PersonaFuncionario personaFuncionarioListPersonaFuncionario : funcionario.getPersonaFuncionarioList()) {
                Funcionario oldIdFuncionarioOfPersonaFuncionarioListPersonaFuncionario = personaFuncionarioListPersonaFuncionario.getIdFuncionario();
                personaFuncionarioListPersonaFuncionario.setIdFuncionario(funcionario);
                personaFuncionarioListPersonaFuncionario = em.merge(personaFuncionarioListPersonaFuncionario);
                if (oldIdFuncionarioOfPersonaFuncionarioListPersonaFuncionario != null) {
                    oldIdFuncionarioOfPersonaFuncionarioListPersonaFuncionario.getPersonaFuncionarioList().remove(personaFuncionarioListPersonaFuncionario);
                    oldIdFuncionarioOfPersonaFuncionarioListPersonaFuncionario = em.merge(oldIdFuncionarioOfPersonaFuncionarioListPersonaFuncionario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFuncionario(funcionario.getIdFuncionario()) != null) {
                throw new PreexistingEntityException("Funcionario " + funcionario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Funcionario funcionario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario persistentFuncionario = em.find(Funcionario.class, funcionario.getIdFuncionario());
            Usuario usuarioOld = persistentFuncionario.getUsuario();
            Usuario usuarioNew = funcionario.getUsuario();
            Fundacion nitOld = persistentFuncionario.getNit();
            Fundacion nitNew = funcionario.getNit();
            List<Cita> citaListOld = persistentFuncionario.getCitaList();
            List<Cita> citaListNew = funcionario.getCitaList();
            List<PersonaFuncionario> personaFuncionarioListOld = persistentFuncionario.getPersonaFuncionarioList();
            List<PersonaFuncionario> personaFuncionarioListNew = funcionario.getPersonaFuncionarioList();
            List<String> illegalOrphanMessages = null;
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Usuario " + usuarioOld + " since its funcionario field is not nullable.");
            }
            for (Cita citaListOldCita : citaListOld) {
                if (!citaListNew.contains(citaListOldCita)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cita " + citaListOldCita + " since its idFuncionario field is not nullable.");
                }
            }
            for (PersonaFuncionario personaFuncionarioListOldPersonaFuncionario : personaFuncionarioListOld) {
                if (!personaFuncionarioListNew.contains(personaFuncionarioListOldPersonaFuncionario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PersonaFuncionario " + personaFuncionarioListOldPersonaFuncionario + " since its idFuncionario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getIdUsuario());
                funcionario.setUsuario(usuarioNew);
            }
            if (nitNew != null) {
                nitNew = em.getReference(nitNew.getClass(), nitNew.getNit());
                funcionario.setNit(nitNew);
            }
            List<Cita> attachedCitaListNew = new ArrayList<Cita>();
            for (Cita citaListNewCitaToAttach : citaListNew) {
                citaListNewCitaToAttach = em.getReference(citaListNewCitaToAttach.getClass(), citaListNewCitaToAttach.getIdCita());
                attachedCitaListNew.add(citaListNewCitaToAttach);
            }
            citaListNew = attachedCitaListNew;
            funcionario.setCitaList(citaListNew);
            List<PersonaFuncionario> attachedPersonaFuncionarioListNew = new ArrayList<PersonaFuncionario>();
            for (PersonaFuncionario personaFuncionarioListNewPersonaFuncionarioToAttach : personaFuncionarioListNew) {
                personaFuncionarioListNewPersonaFuncionarioToAttach = em.getReference(personaFuncionarioListNewPersonaFuncionarioToAttach.getClass(), personaFuncionarioListNewPersonaFuncionarioToAttach.getIdPesonaFuncionario());
                attachedPersonaFuncionarioListNew.add(personaFuncionarioListNewPersonaFuncionarioToAttach);
            }
            personaFuncionarioListNew = attachedPersonaFuncionarioListNew;
            funcionario.setPersonaFuncionarioList(personaFuncionarioListNew);
            funcionario = em.merge(funcionario);
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                Funcionario oldFuncionarioOfUsuario = usuarioNew.getFuncionario();
                if (oldFuncionarioOfUsuario != null) {
                    oldFuncionarioOfUsuario.setUsuario(null);
                    oldFuncionarioOfUsuario = em.merge(oldFuncionarioOfUsuario);
                }
                usuarioNew.setFuncionario(funcionario);
                usuarioNew = em.merge(usuarioNew);
            }
            if (nitOld != null && !nitOld.equals(nitNew)) {
                nitOld.getFuncionarioList().remove(funcionario);
                nitOld = em.merge(nitOld);
            }
            if (nitNew != null && !nitNew.equals(nitOld)) {
                nitNew.getFuncionarioList().add(funcionario);
                nitNew = em.merge(nitNew);
            }
            for (Cita citaListNewCita : citaListNew) {
                if (!citaListOld.contains(citaListNewCita)) {
                    Funcionario oldIdFuncionarioOfCitaListNewCita = citaListNewCita.getIdFuncionario();
                    citaListNewCita.setIdFuncionario(funcionario);
                    citaListNewCita = em.merge(citaListNewCita);
                    if (oldIdFuncionarioOfCitaListNewCita != null && !oldIdFuncionarioOfCitaListNewCita.equals(funcionario)) {
                        oldIdFuncionarioOfCitaListNewCita.getCitaList().remove(citaListNewCita);
                        oldIdFuncionarioOfCitaListNewCita = em.merge(oldIdFuncionarioOfCitaListNewCita);
                    }
                }
            }
            for (PersonaFuncionario personaFuncionarioListNewPersonaFuncionario : personaFuncionarioListNew) {
                if (!personaFuncionarioListOld.contains(personaFuncionarioListNewPersonaFuncionario)) {
                    Funcionario oldIdFuncionarioOfPersonaFuncionarioListNewPersonaFuncionario = personaFuncionarioListNewPersonaFuncionario.getIdFuncionario();
                    personaFuncionarioListNewPersonaFuncionario.setIdFuncionario(funcionario);
                    personaFuncionarioListNewPersonaFuncionario = em.merge(personaFuncionarioListNewPersonaFuncionario);
                    if (oldIdFuncionarioOfPersonaFuncionarioListNewPersonaFuncionario != null && !oldIdFuncionarioOfPersonaFuncionarioListNewPersonaFuncionario.equals(funcionario)) {
                        oldIdFuncionarioOfPersonaFuncionarioListNewPersonaFuncionario.getPersonaFuncionarioList().remove(personaFuncionarioListNewPersonaFuncionario);
                        oldIdFuncionarioOfPersonaFuncionarioListNewPersonaFuncionario = em.merge(oldIdFuncionarioOfPersonaFuncionarioListNewPersonaFuncionario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = funcionario.getIdFuncionario();
                if (findFuncionario(id) == null) {
                    throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.");
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
            Funcionario funcionario;
            try {
                funcionario = em.getReference(Funcionario.class, id);
                funcionario.getIdFuncionario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Usuario usuarioOrphanCheck = funcionario.getUsuario();
            if (usuarioOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Funcionario (" + funcionario + ") cannot be destroyed since the Usuario " + usuarioOrphanCheck + " in its usuario field has a non-nullable funcionario field.");
            }
            List<Cita> citaListOrphanCheck = funcionario.getCitaList();
            for (Cita citaListOrphanCheckCita : citaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Funcionario (" + funcionario + ") cannot be destroyed since the Cita " + citaListOrphanCheckCita + " in its citaList field has a non-nullable idFuncionario field.");
            }
            List<PersonaFuncionario> personaFuncionarioListOrphanCheck = funcionario.getPersonaFuncionarioList();
            for (PersonaFuncionario personaFuncionarioListOrphanCheckPersonaFuncionario : personaFuncionarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Funcionario (" + funcionario + ") cannot be destroyed since the PersonaFuncionario " + personaFuncionarioListOrphanCheckPersonaFuncionario + " in its personaFuncionarioList field has a non-nullable idFuncionario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Fundacion nit = funcionario.getNit();
            if (nit != null) {
                nit.getFuncionarioList().remove(funcionario);
                nit = em.merge(nit);
            }
            em.remove(funcionario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Funcionario> findFuncionarioEntities() {
        return findFuncionarioEntities(true, -1, -1);
    }

    public List<Funcionario> findFuncionarioEntities(int maxResults, int firstResult) {
        return findFuncionarioEntities(false, maxResults, firstResult);
    }

    private List<Funcionario> findFuncionarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Funcionario.class));
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

    public Funcionario findFuncionario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Funcionario.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncionarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Funcionario> rt = cq.from(Funcionario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

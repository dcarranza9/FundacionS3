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
import modelo.Cita;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Paciente;
import modelo.Testimonio;
import modelo.PersonaPaciente;

/**
 *
 * @author David
 */
public class PacienteJpaDAO implements Serializable {

    public PacienteJpaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Paciente paciente) throws PreexistingEntityException, Exception {
        if (paciente.getCitaList() == null) {
            paciente.setCitaList(new ArrayList<Cita>());
        }
        if (paciente.getTestimonioList() == null) {
            paciente.setTestimonioList(new ArrayList<Testimonio>());
        }
        if (paciente.getPersonaPacienteList() == null) {
            paciente.setPersonaPacienteList(new ArrayList<PersonaPaciente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HistoriaClinica historiaClinica = paciente.getHistoriaClinica();
            if (historiaClinica != null) {
                historiaClinica = em.getReference(historiaClinica.getClass(), historiaClinica.getPacienteId());
                paciente.setHistoriaClinica(historiaClinica);
            }
            List<Cita> attachedCitaList = new ArrayList<Cita>();
            for (Cita citaListCitaToAttach : paciente.getCitaList()) {
                citaListCitaToAttach = em.getReference(citaListCitaToAttach.getClass(), citaListCitaToAttach.getIdCita());
                attachedCitaList.add(citaListCitaToAttach);
            }
            paciente.setCitaList(attachedCitaList);
            List<Testimonio> attachedTestimonioList = new ArrayList<Testimonio>();
            for (Testimonio testimonioListTestimonioToAttach : paciente.getTestimonioList()) {
                testimonioListTestimonioToAttach = em.getReference(testimonioListTestimonioToAttach.getClass(), testimonioListTestimonioToAttach.getIdTestimonio());
                attachedTestimonioList.add(testimonioListTestimonioToAttach);
            }
            paciente.setTestimonioList(attachedTestimonioList);
            List<PersonaPaciente> attachedPersonaPacienteList = new ArrayList<PersonaPaciente>();
            for (PersonaPaciente personaPacienteListPersonaPacienteToAttach : paciente.getPersonaPacienteList()) {
                personaPacienteListPersonaPacienteToAttach = em.getReference(personaPacienteListPersonaPacienteToAttach.getClass(), personaPacienteListPersonaPacienteToAttach.getIdPersonaPaciente());
                attachedPersonaPacienteList.add(personaPacienteListPersonaPacienteToAttach);
            }
            paciente.setPersonaPacienteList(attachedPersonaPacienteList);
            em.persist(paciente);
            if (historiaClinica != null) {
                Paciente oldPacienteOfHistoriaClinica = historiaClinica.getPaciente();
                if (oldPacienteOfHistoriaClinica != null) {
                    oldPacienteOfHistoriaClinica.setHistoriaClinica(null);
                    oldPacienteOfHistoriaClinica = em.merge(oldPacienteOfHistoriaClinica);
                }
                historiaClinica.setPaciente(paciente);
                historiaClinica = em.merge(historiaClinica);
            }
            for (Cita citaListCita : paciente.getCitaList()) {
                Paciente oldIdPacienteOfCitaListCita = citaListCita.getIdPaciente();
                citaListCita.setIdPaciente(paciente);
                citaListCita = em.merge(citaListCita);
                if (oldIdPacienteOfCitaListCita != null) {
                    oldIdPacienteOfCitaListCita.getCitaList().remove(citaListCita);
                    oldIdPacienteOfCitaListCita = em.merge(oldIdPacienteOfCitaListCita);
                }
            }
            for (Testimonio testimonioListTestimonio : paciente.getTestimonioList()) {
                Paciente oldIdPacienteOfTestimonioListTestimonio = testimonioListTestimonio.getIdPaciente();
                testimonioListTestimonio.setIdPaciente(paciente);
                testimonioListTestimonio = em.merge(testimonioListTestimonio);
                if (oldIdPacienteOfTestimonioListTestimonio != null) {
                    oldIdPacienteOfTestimonioListTestimonio.getTestimonioList().remove(testimonioListTestimonio);
                    oldIdPacienteOfTestimonioListTestimonio = em.merge(oldIdPacienteOfTestimonioListTestimonio);
                }
            }
            for (PersonaPaciente personaPacienteListPersonaPaciente : paciente.getPersonaPacienteList()) {
                Paciente oldIdPacienteOfPersonaPacienteListPersonaPaciente = personaPacienteListPersonaPaciente.getIdPaciente();
                personaPacienteListPersonaPaciente.setIdPaciente(paciente);
                personaPacienteListPersonaPaciente = em.merge(personaPacienteListPersonaPaciente);
                if (oldIdPacienteOfPersonaPacienteListPersonaPaciente != null) {
                    oldIdPacienteOfPersonaPacienteListPersonaPaciente.getPersonaPacienteList().remove(personaPacienteListPersonaPaciente);
                    oldIdPacienteOfPersonaPacienteListPersonaPaciente = em.merge(oldIdPacienteOfPersonaPacienteListPersonaPaciente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPaciente(paciente.getIdPaciente()) != null) {
                throw new PreexistingEntityException("Paciente " + paciente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Paciente paciente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paciente persistentPaciente = em.find(Paciente.class, paciente.getIdPaciente());
            HistoriaClinica historiaClinicaOld = persistentPaciente.getHistoriaClinica();
            HistoriaClinica historiaClinicaNew = paciente.getHistoriaClinica();
            List<Cita> citaListOld = persistentPaciente.getCitaList();
            List<Cita> citaListNew = paciente.getCitaList();
            List<Testimonio> testimonioListOld = persistentPaciente.getTestimonioList();
            List<Testimonio> testimonioListNew = paciente.getTestimonioList();
            List<PersonaPaciente> personaPacienteListOld = persistentPaciente.getPersonaPacienteList();
            List<PersonaPaciente> personaPacienteListNew = paciente.getPersonaPacienteList();
            List<String> illegalOrphanMessages = null;
            if (historiaClinicaOld != null && !historiaClinicaOld.equals(historiaClinicaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain HistoriaClinica " + historiaClinicaOld + " since its paciente field is not nullable.");
            }
            for (Cita citaListOldCita : citaListOld) {
                if (!citaListNew.contains(citaListOldCita)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cita " + citaListOldCita + " since its idPaciente field is not nullable.");
                }
            }
            for (Testimonio testimonioListOldTestimonio : testimonioListOld) {
                if (!testimonioListNew.contains(testimonioListOldTestimonio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Testimonio " + testimonioListOldTestimonio + " since its idPaciente field is not nullable.");
                }
            }
            for (PersonaPaciente personaPacienteListOldPersonaPaciente : personaPacienteListOld) {
                if (!personaPacienteListNew.contains(personaPacienteListOldPersonaPaciente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PersonaPaciente " + personaPacienteListOldPersonaPaciente + " since its idPaciente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (historiaClinicaNew != null) {
                historiaClinicaNew = em.getReference(historiaClinicaNew.getClass(), historiaClinicaNew.getPacienteId());
                paciente.setHistoriaClinica(historiaClinicaNew);
            }
            List<Cita> attachedCitaListNew = new ArrayList<Cita>();
            for (Cita citaListNewCitaToAttach : citaListNew) {
                citaListNewCitaToAttach = em.getReference(citaListNewCitaToAttach.getClass(), citaListNewCitaToAttach.getIdCita());
                attachedCitaListNew.add(citaListNewCitaToAttach);
            }
            citaListNew = attachedCitaListNew;
            paciente.setCitaList(citaListNew);
            List<Testimonio> attachedTestimonioListNew = new ArrayList<Testimonio>();
            for (Testimonio testimonioListNewTestimonioToAttach : testimonioListNew) {
                testimonioListNewTestimonioToAttach = em.getReference(testimonioListNewTestimonioToAttach.getClass(), testimonioListNewTestimonioToAttach.getIdTestimonio());
                attachedTestimonioListNew.add(testimonioListNewTestimonioToAttach);
            }
            testimonioListNew = attachedTestimonioListNew;
            paciente.setTestimonioList(testimonioListNew);
            List<PersonaPaciente> attachedPersonaPacienteListNew = new ArrayList<PersonaPaciente>();
            for (PersonaPaciente personaPacienteListNewPersonaPacienteToAttach : personaPacienteListNew) {
                personaPacienteListNewPersonaPacienteToAttach = em.getReference(personaPacienteListNewPersonaPacienteToAttach.getClass(), personaPacienteListNewPersonaPacienteToAttach.getIdPersonaPaciente());
                attachedPersonaPacienteListNew.add(personaPacienteListNewPersonaPacienteToAttach);
            }
            personaPacienteListNew = attachedPersonaPacienteListNew;
            paciente.setPersonaPacienteList(personaPacienteListNew);
            paciente = em.merge(paciente);
            if (historiaClinicaNew != null && !historiaClinicaNew.equals(historiaClinicaOld)) {
                Paciente oldPacienteOfHistoriaClinica = historiaClinicaNew.getPaciente();
                if (oldPacienteOfHistoriaClinica != null) {
                    oldPacienteOfHistoriaClinica.setHistoriaClinica(null);
                    oldPacienteOfHistoriaClinica = em.merge(oldPacienteOfHistoriaClinica);
                }
                historiaClinicaNew.setPaciente(paciente);
                historiaClinicaNew = em.merge(historiaClinicaNew);
            }
            for (Cita citaListNewCita : citaListNew) {
                if (!citaListOld.contains(citaListNewCita)) {
                    Paciente oldIdPacienteOfCitaListNewCita = citaListNewCita.getIdPaciente();
                    citaListNewCita.setIdPaciente(paciente);
                    citaListNewCita = em.merge(citaListNewCita);
                    if (oldIdPacienteOfCitaListNewCita != null && !oldIdPacienteOfCitaListNewCita.equals(paciente)) {
                        oldIdPacienteOfCitaListNewCita.getCitaList().remove(citaListNewCita);
                        oldIdPacienteOfCitaListNewCita = em.merge(oldIdPacienteOfCitaListNewCita);
                    }
                }
            }
            for (Testimonio testimonioListNewTestimonio : testimonioListNew) {
                if (!testimonioListOld.contains(testimonioListNewTestimonio)) {
                    Paciente oldIdPacienteOfTestimonioListNewTestimonio = testimonioListNewTestimonio.getIdPaciente();
                    testimonioListNewTestimonio.setIdPaciente(paciente);
                    testimonioListNewTestimonio = em.merge(testimonioListNewTestimonio);
                    if (oldIdPacienteOfTestimonioListNewTestimonio != null && !oldIdPacienteOfTestimonioListNewTestimonio.equals(paciente)) {
                        oldIdPacienteOfTestimonioListNewTestimonio.getTestimonioList().remove(testimonioListNewTestimonio);
                        oldIdPacienteOfTestimonioListNewTestimonio = em.merge(oldIdPacienteOfTestimonioListNewTestimonio);
                    }
                }
            }
            for (PersonaPaciente personaPacienteListNewPersonaPaciente : personaPacienteListNew) {
                if (!personaPacienteListOld.contains(personaPacienteListNewPersonaPaciente)) {
                    Paciente oldIdPacienteOfPersonaPacienteListNewPersonaPaciente = personaPacienteListNewPersonaPaciente.getIdPaciente();
                    personaPacienteListNewPersonaPaciente.setIdPaciente(paciente);
                    personaPacienteListNewPersonaPaciente = em.merge(personaPacienteListNewPersonaPaciente);
                    if (oldIdPacienteOfPersonaPacienteListNewPersonaPaciente != null && !oldIdPacienteOfPersonaPacienteListNewPersonaPaciente.equals(paciente)) {
                        oldIdPacienteOfPersonaPacienteListNewPersonaPaciente.getPersonaPacienteList().remove(personaPacienteListNewPersonaPaciente);
                        oldIdPacienteOfPersonaPacienteListNewPersonaPaciente = em.merge(oldIdPacienteOfPersonaPacienteListNewPersonaPaciente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = paciente.getIdPaciente();
                if (findPaciente(id) == null) {
                    throw new NonexistentEntityException("The paciente with id " + id + " no longer exists.");
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
            Paciente paciente;
            try {
                paciente = em.getReference(Paciente.class, id);
                paciente.getIdPaciente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The paciente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            HistoriaClinica historiaClinicaOrphanCheck = paciente.getHistoriaClinica();
            if (historiaClinicaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Paciente (" + paciente + ") cannot be destroyed since the HistoriaClinica " + historiaClinicaOrphanCheck + " in its historiaClinica field has a non-nullable paciente field.");
            }
            List<Cita> citaListOrphanCheck = paciente.getCitaList();
            for (Cita citaListOrphanCheckCita : citaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Paciente (" + paciente + ") cannot be destroyed since the Cita " + citaListOrphanCheckCita + " in its citaList field has a non-nullable idPaciente field.");
            }
            List<Testimonio> testimonioListOrphanCheck = paciente.getTestimonioList();
            for (Testimonio testimonioListOrphanCheckTestimonio : testimonioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Paciente (" + paciente + ") cannot be destroyed since the Testimonio " + testimonioListOrphanCheckTestimonio + " in its testimonioList field has a non-nullable idPaciente field.");
            }
            List<PersonaPaciente> personaPacienteListOrphanCheck = paciente.getPersonaPacienteList();
            for (PersonaPaciente personaPacienteListOrphanCheckPersonaPaciente : personaPacienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Paciente (" + paciente + ") cannot be destroyed since the PersonaPaciente " + personaPacienteListOrphanCheckPersonaPaciente + " in its personaPacienteList field has a non-nullable idPaciente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(paciente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Paciente> findPacienteEntities() {
        return findPacienteEntities(true, -1, -1);
    }

    public List<Paciente> findPacienteEntities(int maxResults, int firstResult) {
        return findPacienteEntities(false, maxResults, firstResult);
    }

    private List<Paciente> findPacienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Paciente.class));
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

    public Paciente findPaciente(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Paciente.class, id);
        } finally {
            em.close();
        }
    }

    public int getPacienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Paciente> rt = cq.from(Paciente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

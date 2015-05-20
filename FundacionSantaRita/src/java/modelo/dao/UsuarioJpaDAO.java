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
import modelo.Funcionario;
import modelo.Rol;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Usuario;

/**
 *
 * @author David
 */
public class UsuarioJpaDAO implements Serializable {

    public UsuarioJpaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (usuario.getRoleList() == null) {
            usuario.setRoleList(new ArrayList<Rol>());
        }
        List<String> illegalOrphanMessages = null;
        Funcionario funcionarioOrphanCheck = usuario.getFuncionario();
        if (funcionarioOrphanCheck != null) {
            Usuario oldUsuarioOfFuncionario = funcionarioOrphanCheck.getUsuario();
            if (oldUsuarioOfFuncionario != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Funcionario " + funcionarioOrphanCheck + " already has an item of type Usuario whose funcionario column cannot be null. Please make another selection for the funcionario field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario funcionario = usuario.getFuncionario();
            if (funcionario != null) {
                funcionario = em.getReference(funcionario.getClass(), funcionario.getIdFuncionario());
                usuario.setFuncionario(funcionario);
            }
            List<Rol> attachedRoleList = new ArrayList<Rol>();
            for (Rol roleListRoleToAttach : usuario.getRoleList()) {
                roleListRoleToAttach = em.getReference(roleListRoleToAttach.getClass(), roleListRoleToAttach.getCodigoRol());
                attachedRoleList.add(roleListRoleToAttach);
            }
            usuario.setRoleList(attachedRoleList);
            em.persist(usuario);
            if (funcionario != null) {
                funcionario.setUsuario(usuario);
                funcionario = em.merge(funcionario);
            }
            for (Rol roleListRole : usuario.getRoleList()) {
                Usuario oldIdUsuarioOfRoleListRole = roleListRole.getIdUsuario();
                roleListRole.setIdUsuario(usuario);
                roleListRole = em.merge(roleListRole);
                if (oldIdUsuarioOfRoleListRole != null) {
                    oldIdUsuarioOfRoleListRole.getRoleList().remove(roleListRole);
                    oldIdUsuarioOfRoleListRole = em.merge(oldIdUsuarioOfRoleListRole);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getIdUsuario()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuario());
            Funcionario funcionarioOld = persistentUsuario.getFuncionario();
            Funcionario funcionarioNew = usuario.getFuncionario();
            List<Rol> roleListOld = persistentUsuario.getRoleList();
            List<Rol> roleListNew = usuario.getRoleList();
            List<String> illegalOrphanMessages = null;
            if (funcionarioNew != null && !funcionarioNew.equals(funcionarioOld)) {
                Usuario oldUsuarioOfFuncionario = funcionarioNew.getUsuario();
                if (oldUsuarioOfFuncionario != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Funcionario " + funcionarioNew + " already has an item of type Usuario whose funcionario column cannot be null. Please make another selection for the funcionario field.");
                }
            }
            for (Rol roleListOldRole : roleListOld) {
                if (!roleListNew.contains(roleListOldRole)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Role " + roleListOldRole + " since its idUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (funcionarioNew != null) {
                funcionarioNew = em.getReference(funcionarioNew.getClass(), funcionarioNew.getIdFuncionario());
                usuario.setFuncionario(funcionarioNew);
            }
            List<Rol> attachedRoleListNew = new ArrayList<Rol>();
            for (Rol roleListNewRoleToAttach : roleListNew) {
                roleListNewRoleToAttach = em.getReference(roleListNewRoleToAttach.getClass(), roleListNewRoleToAttach.getCodigoRol());
                attachedRoleListNew.add(roleListNewRoleToAttach);
            }
            roleListNew = attachedRoleListNew;
            usuario.setRoleList(roleListNew);
            usuario = em.merge(usuario);
            if (funcionarioOld != null && !funcionarioOld.equals(funcionarioNew)) {
                funcionarioOld.setUsuario(null);
                funcionarioOld = em.merge(funcionarioOld);
            }
            if (funcionarioNew != null && !funcionarioNew.equals(funcionarioOld)) {
                funcionarioNew.setUsuario(usuario);
                funcionarioNew = em.merge(funcionarioNew);
            }
            for (Rol roleListNewRole : roleListNew) {
                if (!roleListOld.contains(roleListNewRole)) {
                    Usuario oldIdUsuarioOfRoleListNewRole = roleListNewRole.getIdUsuario();
                    roleListNewRole.setIdUsuario(usuario);
                    roleListNewRole = em.merge(roleListNewRole);
                    if (oldIdUsuarioOfRoleListNewRole != null && !oldIdUsuarioOfRoleListNewRole.equals(usuario)) {
                        oldIdUsuarioOfRoleListNewRole.getRoleList().remove(roleListNewRole);
                        oldIdUsuarioOfRoleListNewRole = em.merge(oldIdUsuarioOfRoleListNewRole);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getIdUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Rol> roleListOrphanCheck = usuario.getRoleList();
            for (Rol roleListOrphanCheckRole : roleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Role " + roleListOrphanCheckRole + " in its roleList field has a non-nullable idUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Funcionario funcionario = usuario.getFuncionario();
            if (funcionario != null) {
                funcionario.setUsuario(null);
                funcionario = em.merge(funcionario);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

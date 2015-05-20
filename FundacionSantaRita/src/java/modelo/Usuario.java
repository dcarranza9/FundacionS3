
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Representa a un usuario del sistema de la funcacion con todos
 * los atributos relevantes para que un funcionario pueda acceder a este y 
 * realizar sus labores.
 * 
 * @author David
 */
@Entity
@Table(name = "usuarios")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario"),
    @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password")})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_usuario")
    private String idUsuario;
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario", fetch = FetchType.EAGER)
    private List<Rol> roleList;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_funcionario", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private Funcionario funcionario;

    /**
     *Construye un usuario sin atributos inicialmente.
     */
    public Usuario() {
    }

    /**
     * Construye un usuario con su identificador inicialmente. El identificador
     * es en relacion con el identificador del funcionario.
     * 
     * @param idUsuario el identificador de usuario. 
     */
    public Usuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     *Construye un usuario con todos los parametros necesarios. El identificador
     * es en relacion con el identificador del funcionario.
     * 
     * @param idUsuario el identificador de usuario.
     * @param usuario nombre de usuario.
     * @param password la contraseña del usuario.
     */
    public Usuario(String idUsuario, String usuario, String password) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.password = password;
    }

    /**
     * Obtiene el identificador establecido al usuario.
     * 
     * @return el identificador de usuario.
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece un identificador al usuario.
     * 
     * @param idUsuario el identificador que se quiere establecer.
     */
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene el nombre de usuario establecido al usuario.
     * 
     * @return el nombre de usuario.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Establece un nombre de usuario al usuario para poder ingresar al sistema.
     * 
     * @param usuario el nombre de usuario que se quiere establcer.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la contraseña establecida al usuario. Este campo tiene el tamaño
     * de una cadena hash MD5.
     * 
     * @return 
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece una contraseña al usuario para poder ingresar al sistema.
     * 
     * @param password la contraseña a establecer.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene la lista de actividades establecidas al usuario que puede
     * realizar en el sistema.
     * 
     * @return la lista de actividades.
     */
    public List<Rol> getRoleList() {
        return roleList;
    }

    /**
     * Establece una lista de actividades que puede realizar el usuario en 
     * el sistema.
     *
     * @param roleList la lista de actividades a establecer.
     */
    public void setRoleList(List<Rol> roleList) {
        this.roleList = roleList;
    }

    /**
     * Obtiene el funcionario propietario del usuario.
     *
     * @return el funcionario propietario del usuario
     */
    public Funcionario getFuncionario() {
        return funcionario;
    }

    /**
     * Establece un funcionario propietario al usuario.
     * 
     * @param funcionario el funcionario propietario a establecer.
     */
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Usuario[ idUsuario=" + idUsuario + " ]";
    }
    
}

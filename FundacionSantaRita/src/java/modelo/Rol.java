
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Representa las actividades que puede desempeñar los usuarios dentro del sistema.
 * 
 * @author David
 */
@Entity
@Table(name = "roles")
@NamedQueries({
    @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r"),
    @NamedQuery(name = "Rol.findByCodigoRol", query = "SELECT r FROM Rol r WHERE r.codigoRol = :codigoRol"),
    @NamedQuery(name = "Rol.findByDescripcion", query = "SELECT r FROM Rol r WHERE r.descripcion = :descripcion")})
public class Rol implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_rol")
    private Integer codigoRol;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Usuario idUsuario;

    /**
     * Construye un rol sin atributos inicialmente.
     */
    public Rol() {
    }

    /**
     * Construye un rol inicialmente con su identificador.
     * 
     * @param codigoRol el identificador del rol.
     */
    public Rol(Integer codigoRol) {
        this.codigoRol = codigoRol;
    }
    
    /**
     * Construye un rol inicialmente con el codigo y la descripcion.
     * 
     * @param codigoRol el identificador del rol.
     * @param descripcion la descripcion del rol.
     */
    public Rol(Integer codigoRol, String descripcion) {
        this.codigoRol = codigoRol;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el codigo establecido al rol.
     * 
     * @return el identificador del rol
     */
    public Integer getCodigoRol() {
        return codigoRol;
    }

    /**
     * Establece un identificador al rol.
     * 
     * @param codigoRol el identificador a establecer en el rol.
     */
    public void setCodigoRol(Integer codigoRol) {
        this.codigoRol = codigoRol;
    }

    /**
     * Obtiene la descripcion establecida al rol.
     * 
     * @return la descripcion del rol.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece una descripcion al rol. 
     * 
     * @param descripcion la descripcion a establecer.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
     * Obtiene el usuario con al menod su identificador, establecido al rol.
     * 
     * @return el usuario que tiene el rol.
     */
    public Usuario getIdUsuario() {
        return idUsuario;
    }
    
    /**
     * Establece al rol un usuario con al menos su identificador.
     * 
     * @param idUsuario el usuario a establecer.
     */
    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoRol != null ? codigoRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rol)) {
            return false;
        }
        Rol other = (Rol) object;
        if ((this.codigoRol == null && other.codigoRol != null) || (this.codigoRol != null && !this.codigoRol.equals(other.codigoRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Role[ codigoRol=" + codigoRol + " ]";
    }
    
}

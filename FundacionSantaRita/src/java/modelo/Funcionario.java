
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Esta clase representan a un funcionario de la fundacion con todos los 
 * atributos requeridos ademas de los atributos de la clase persona por ser
 * una extension de esta.
 *
 * @author David
 */
@Entity
@Table(name = "funcionarios")
@NamedQueries({
    @NamedQuery(name = "Funcionario.findAll", query = "SELECT f FROM Funcionario f"),
    @NamedQuery(name = "Funcionario.findByIdFuncionario", query = "SELECT f FROM Funcionario f WHERE f.idFuncionario = :idFuncionario"),
    @NamedQuery(name = "Funcionario.findByPerfil", query = "SELECT f FROM Funcionario f WHERE f.perfil = :perfil")})
public class Funcionario implements Serializable {
    @Basic(optional = false)
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_funcionario")
    private String idFuncionario;
    @Basic(optional = false)
    @Column(name = "perfil")
    private String perfil;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFuncionario", fetch = FetchType.EAGER)
    private List<Cita> citaList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "funcionario", fetch = FetchType.EAGER)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFuncionario", fetch = FetchType.EAGER)
    private List<PersonaFuncionario> personaFuncionarioList;
    @JoinColumn(name = "nit", referencedColumnName = "nit")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Fundacion nit;

    /**
     * Construye un funcionario sin atributos inicialmente.
     */
    public Funcionario() {
    }

    /**
     * Construye un funcionario incialmente con su identificador.
     * 
     * @param idFuncionario el identificador del funcionario.
     */
    public Funcionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    /**
     * Construye un funcionario incialmente con su identificador y el perfil
     * que asumira.
     * 
     * @param idFuncionario el identificador del funcionario.
     * @param perfil el perfil que asumira el funcionario.
     */
    public Funcionario(String idFuncionario, String perfil) {
        this.idFuncionario = idFuncionario;
        this.perfil = perfil;
    }

    /**
     *Obtiene el idetificador establecido al funcionario.
     * 
     * @return el identificador del funcionario.
     */
    public String getIdFuncionario() {
        return idFuncionario;
    }

    /**
     *Obtiene el identificador del funcionario el cual lo diferencia de otros
     * funcinarios. 
     * 
     * @param idFuncionario el identiicador del funcionario que se quiere
     * establecer al identificador.
     */
    public void setIdFuncionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    /**
     *Obtiene el perfil establecido al funcionario el cual asume dentro de la 
     * fundacion.
     * 
     * @return el perfil del funcionario.
     */
    public String getPerfil() {
        return perfil;
    }

    /**
     *Establece el perfil que asumira el funcionario dentro de la fundacion.
     * 
     * @param perfil el perfil del funcionario.
     */
    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
    
    /**
     *Obtiene la fecha en que ingreso el funcionario a trabajar a la fundacion.
     * 
     * @return la fecha en que ingreso el funcionario.
     */
    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    /**Establece la fecha de ingreso del funcionario a la fundacion.
     *
     * @param fechaIngreso la fecha de ingreso del funcionario.
     */
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    /**
     * Obtiene la lista de citas establecidas y que debe atender el funcionario.
     * 
     * @return la lista de citas establecidas al usuario.
     */
    public List<Cita> getCitaList() {
        return citaList;
    }

    /**
     *Establece la lista de citas que debe atender el funcionario.
     * 
     * @param citaList 
     */
    public void setCitaList(List<Cita> citaList) {
        this.citaList = citaList;
    }

    /**
     * Obtiene el usuario con sus atributos que tiene asignado el funcionario.
     * 
     * @return el usuario del funcionario.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece un usuario al funcionario. El usuario a establecer debe tener 
     * todos los atributos. 
     * 
     * @param usuario el usuario que se quiere establecer.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la lista de personas asociadas al funcionario. Normalmente
     * la lista de personas deberia tener un elemento. 
     * 
     * @return la lista de persona asociadas al funcionario.
     */
    public List<PersonaFuncionario> getPersonaFuncionarioList() {
        return personaFuncionarioList;
    }

    /**
     * Establece una lista de personas para asociar al funcionario. Comunmente
     * esta lista debera tener solo un elemento la cual sera la persona a 
     * asociada al funcionario con solo el identificador de la persona.
     * 
     * @param personaFuncionarioList
     */
    public void setPersonaFuncionarioList(List<PersonaFuncionario> personaFuncionarioList) {
        this.personaFuncionarioList = personaFuncionarioList;
    }

    /**
     *Obtiene el identificador de la fundacion a la que pertence el funcionario.
     * 
     * @return el nit que identifica la fundacion.
     */
    public Fundacion getNit() {
        return nit;
    }

    /**
     * Establece el identificador de la fundacion a cual pertenece el funcionario..
     *
     * @param nit el identificador de la fundacion que se quiere establecer.
     */
    public void setNit(Fundacion nit) {
        this.nit = nit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFuncionario != null ? idFuncionario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Funcionario)) {
            return false;
        }
        Funcionario other = (Funcionario) object;
        if ((this.idFuncionario == null && other.idFuncionario != null) || (this.idFuncionario != null && !this.idFuncionario.equals(other.idFuncionario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Funcionario[ idFuncionario=" + idFuncionario + " ]";
    }
    
}

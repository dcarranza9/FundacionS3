
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
 *
 * @author David
 */
@Entity
@Table(name = "persona_paciente")
@NamedQueries({
    @NamedQuery(name = "PersonaPaciente.findAll", query = "SELECT p FROM PersonaPaciente p"),
    @NamedQuery(name = "PersonaPaciente.findByIdPersonaPaciente", query = "SELECT p FROM PersonaPaciente p WHERE p.idPersonaPaciente = :idPersonaPaciente")})
public class PersonaPaciente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_persona_paciente")
    private Integer idPersonaPaciente;
    @JoinColumn(name = "id_paciente", referencedColumnName = "id_paciente")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Paciente idPaciente;
    @JoinColumn(name = "cedula", referencedColumnName = "cedula")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Persona cedula;

    /**
     *
     */
    public PersonaPaciente() {
    }

    /**
     *
     * @param idPersonaPaciente
     */
    public PersonaPaciente(Integer idPersonaPaciente) {
        this.idPersonaPaciente = idPersonaPaciente;
    }

    /**
     *
     * @return
     */
    public Integer getIdPersonaPaciente() {
        return idPersonaPaciente;
    }

    /**
     *
     * @param idPersonaPaciente
     */
    public void setIdPersonaPaciente(Integer idPersonaPaciente) {
        this.idPersonaPaciente = idPersonaPaciente;
    }

    /**
     *
     * @return
     */
    public Paciente getIdPaciente() {
        return idPaciente;
    }

    /**
     *
     * @param idPaciente
     */
    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
    }

    /**
     *
     * @return
     */
    public Persona getCedula() {
        return cedula;
    }

    /**
     *
     * @param cedula
     */
    public void setCedula(Persona cedula) {
        this.cedula = cedula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersonaPaciente != null ? idPersonaPaciente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonaPaciente)) {
            return false;
        }
        PersonaPaciente other = (PersonaPaciente) object;
        if ((this.idPersonaPaciente == null && other.idPersonaPaciente != null) || (this.idPersonaPaciente != null && !this.idPersonaPaciente.equals(other.idPersonaPaciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.PersonaPaciente[ idPersonaPaciente=" + idPersonaPaciente + " ]";
    }
    
}

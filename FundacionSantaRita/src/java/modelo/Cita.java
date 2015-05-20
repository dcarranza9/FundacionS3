
package modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Resprepresenta una cita medica como abstraccion del mundo real. Contiene 
 * parametros basicos necesarios para que pueda existir una cita medica.
 * 
 * Esta clase se encuentra ubicada en el paquete de modelo.
 * 
 * @author David
 */
@Entity
@Table(name = "citas")
@NamedQueries({
    @NamedQuery(name = "Cita.findAll", query = "SELECT c FROM Cita c"),
    @NamedQuery(name = "Cita.findByIdCita", query = "SELECT c FROM Cita c WHERE c.idCita = :idCita"),
    @NamedQuery(name = "Cita.findByFechaCita", query = "SELECT c FROM Cita c WHERE c.fechaCita = :fechaCita"),
    @NamedQuery(name = "Cita.findByLugarCita", query = "SELECT c FROM Cita c WHERE c.lugarCita = :lugarCita")})
public class Cita implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cita")
    private Integer idCita;
    @Basic(optional = false)
    @Column(name = "fecha_cita")
    @Temporal(TemporalType.DATE)
    private Date fechaCita;
    @Basic(optional = false)
    @Column(name = "lugar_cita")
    private String lugarCita;
    @JoinColumn(name = "id_funcionario", referencedColumnName = "id_funcionario")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Funcionario idFuncionario;
    @JoinColumn(name = "id_paciente", referencedColumnName = "id_paciente")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Paciente idPaciente;

    /**
     *Construye una Cita  sin atributos inicialmente.
     */
    public Cita() {
    }

    /**
     *Contruye una cita con todos los parametros que necesita.
     * 
     * @param fechaCita la fecha en que se asistira a la cita.
     * @param lugarCita el lugar donde se dara la cita.
     * @param idFuncionario el funcionario con minimo su identificador.
     * @param idPaciente el paciente con minimo su identificador.
     */
    public Cita(Date fechaCita, String lugarCita, Funcionario idFuncionario, Paciente idPaciente) {
        this.fechaCita = fechaCita;
        this.lugarCita = lugarCita;
        this.idFuncionario = idFuncionario;
        this.idPaciente = idPaciente;
    }

    /**
     * Obtiene el identificador de una cita
     * 
     * @return el identificador de la cita
     */
    public Integer getIdCita() {
        return idCita;
    }

    /**
     * Obtiene la fecha que se ha establecido para una cita.
     * 
     * @return la fecha de la cita.
     */
    public Date getFechaCita() {
        return fechaCita;
    }

    
    /**
     *Establece la fecha para una cita.
     * 
     * @param fechaCita la fecha que se quiere establecer.
     */
    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    /**
     * Obtiene el lugar en el que se realizara la cita.
     * 
     * @return el lugar que se ha establecido para la cita.
     */
    public String getLugarCita() {
        return lugarCita;
    }

    /**
     *Establece el lugar en el que se realizara la cita, este puede ser una
     * referencia a un sitio como un consultorio o la descripcion de una 
     * ubicacion.
     * 
     * @param lugarCita el lugar en el que se realizara la cita.
     */
    public void setLugarCita(String lugarCita) {
        this.lugarCita = lugarCita;
    }

    /**
     * Obtiene el funcionario establecido a la cita con al menos su respectivo 
     * identificador.
     * 
     * @return el funcionario establecido en la cita.
     */
    public Funcionario getIdFuncionario() {
        return idFuncionario;
    }

    /**
     * Establece un funcionario con al menos su identificador.
     *
     * @param idFuncionario el funcionario que atendera la cita.
     */
    public void setIdFuncionario(Funcionario idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    /**
     * Obtiene el paciente con al menos su identificador que se ha establecido 
     * a la cita.
     * 
     * @return el paciente asociado a la cita.
     */
    public Paciente getIdPaciente() {
        return idPaciente;
    }

    /**
     *Establece un paciente con al menos su identificador a la cita.
     * 
     * @param idPaciente el paciente con al menos su identificador.
     */
    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCita != null ? idCita.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cita)) {
            return false;
        }
        Cita other = (Cita) object;
        if ((this.idCita == null && other.idCita != null) || (this.idCita != null && !this.idCita.equals(other.idCita))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Cita[ idCita=" + idCita + " ]";
    }
    
}

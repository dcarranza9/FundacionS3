
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
@Table(name = "testimonios")
@NamedQueries({
    @NamedQuery(name = "Testimonio.findAll", query = "SELECT t FROM Testimonio t"),
    @NamedQuery(name = "Testimonio.findByIdTestimonio", query = "SELECT t FROM Testimonio t WHERE t.idTestimonio = :idTestimonio"),
    @NamedQuery(name = "Testimonio.findByDescripcion", query = "SELECT t FROM Testimonio t WHERE t.descripcion = :descripcion")})
public class Testimonio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_testimonio")
    private Integer idTestimonio;
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "id_paciente", referencedColumnName = "id_paciente")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Paciente idPaciente;
    @JoinColumn(name = "codigo_terapia", referencedColumnName = "codigo_terapia")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Terapia codigoTerapia;

    /**
     *
     */
    public Testimonio() {
    }

    /**
     *
     * @param idTestimonio
     */
    public Testimonio(Integer idTestimonio) {
        this.idTestimonio = idTestimonio;
    }

    /**
     *
     * @return
     */
    public Integer getIdTestimonio() {
        return idTestimonio;
    }

    /**
     *
     * @param idTestimonio
     */
    public void setIdTestimonio(Integer idTestimonio) {
        this.idTestimonio = idTestimonio;
    }

    /**
     *
     * @return
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     *
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    public Terapia getCodigoTerapia() {
        return codigoTerapia;
    }

    /**
     *
     * @param codigoTerapia
     */
    public void setCodigoTerapia(Terapia codigoTerapia) {
        this.codigoTerapia = codigoTerapia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTestimonio != null ? idTestimonio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Testimonio)) {
            return false;
        }
        Testimonio other = (Testimonio) object;
        if ((this.idTestimonio == null && other.idTestimonio != null) || (this.idTestimonio != null && !this.idTestimonio.equals(other.idTestimonio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Testimonio[ idTestimonio=" + idTestimonio + " ]";
    }
    
}

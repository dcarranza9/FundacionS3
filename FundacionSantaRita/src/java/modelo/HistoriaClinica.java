
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *Representa el histrial clinico de los pacientes como una abstraccion del
 * mundo real. Contiene los parametros que se toman en cuenta en la logica
 * del neocio de la fundacion.
 * 
 * @author David
 */
@Entity
@Table(name = "historia_clinica")
@NamedQueries({
    @NamedQuery(name = "HistoriaClinica.findAll", query = "SELECT h FROM HistoriaClinica h"),
    @NamedQuery(name = "HistoriaClinica.findByPacienteId", query = "SELECT h FROM HistoriaClinica h WHERE h.pacienteId = :pacienteId"),
    @NamedQuery(name = "HistoriaClinica.findByDiagnostico", query = "SELECT h FROM HistoriaClinica h WHERE h.diagnostico = :diagnostico"),
    @NamedQuery(name = "HistoriaClinica.findByFechaDiagnostico", query = "SELECT h FROM HistoriaClinica h WHERE h.fechaDiagnostico = :fechaDiagnostico")})
public class HistoriaClinica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "paciente_id")
    private String pacienteId;
    @Basic(optional = false)
    @Column(name = "diagnostico")
    private String diagnostico;
    @Basic(optional = false)
    @Column(name = "fecha_diagnostico")
    @Temporal(TemporalType.DATE)
    private Date fechaDiagnostico;
    @JoinColumn(name = "codigo_medicamento", referencedColumnName = "codigo_medicamento")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Medicamento codigoMedicamento;
    @JoinColumn(name = "paciente_id", referencedColumnName = "id_paciente", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private Paciente paciente;
    @JoinColumn(name = "codigo_terapia", referencedColumnName = "codigo_terapia")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Terapia codigoTerapia;

    /**
     *Cosntruye un histrorial clinico sin atributos inicialmente.
     */
    public HistoriaClinica() {
    }

    /**
     * Construye un historial clinico inicialmente con el identificador del paciente.
     * 
     * @param pacienteId el identificador del paciente.
     */
    public HistoriaClinica(String pacienteId) {
        this.pacienteId = pacienteId;
    }

    /**
     * Construye un historial clinico inicialmente con el identificador del paciente,
     * el diagnostico y la fecha del diagnostico.
     * 
     * @param pacienteId el identificador del paciente.
     * @param diagnostico el diagnostico.
     * @param fechaDiagnostico facha del diagnostico.
     */
    public HistoriaClinica(String pacienteId, String diagnostico, Date fechaDiagnostico) {
        this.pacienteId = pacienteId;
        this.diagnostico = diagnostico;
        this.fechaDiagnostico = fechaDiagnostico;
    }

    /**
     * Obtiene el identificador del paciente establecido en el histrorial.
     *
     * @return el identificador del paciente en el historial.
     */
    public String getPacienteId() {
        return pacienteId;
    }

    /**
     * Establece el identificador del paciente al historial clinico.
     * 
     * @param pacienteId el identificador del paciente.
     */
    public void setPacienteId(String pacienteId) {
        this.pacienteId = pacienteId;
    }

    /**
     * Obtiene el diagnotico establecido en el historial clinico.
     * 
     * @return el diagnostico establecido en el historial clinico.
     */
    public String getDiagnostico() {
        return diagnostico;
    }

    /**
     * Establece un diagnostico al historial clinico.
     * 
     * @param diagnostico el diagnostico que se quiere establecer.
     */
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    /**
     * Obtiene la fecha establecida establecida en el historial clinico
     * en la que se realizo el diagnostico.
     * 
     * @return la fecha del diagnostico.
     */
    public Date getFechaDiagnostico() {
        return fechaDiagnostico;
    }

    /**
     * Establece la fecha del diagnostico en el historial clinico.
     * 
     * @param fechaDiagnostico la fecha del diagnostico a establecer.
     */
    public void setFechaDiagnostico(Date fechaDiagnostico) {
        this.fechaDiagnostico = fechaDiagnostico;
    }

    /**
     * Obtiene el medicamento con al menos su codigo establecido en el 
     * historial clinico.
     * 
     * @return el medicamento establecido en el historial.
     */
    public Medicamento getCodigoMedicamento() {
        return codigoMedicamento;
    }

    /**
     * Establece un medicamento con al menos su codigo al historial clinico.
     * 
     * @param codigoMedicamento el medicamento que se quiere establecer.
     */
    public void setCodigoMedicamento(Medicamento codigoMedicamento) {
        this.codigoMedicamento = codigoMedicamento;
    }

    /**
     * Obtiene el paciente establecido al historial clinico.
     * 
     * @return el paciente en el historial clinico.
     */
    public Paciente getPaciente() {
        return paciente;
    }

    /**
     * Establece un paciente al historial clinico.
     * 
     * @param paciente el paciente que se quiere establecer.
     */
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    /**
     * Obtiene la terapia con al menos su identificador establecida al historial clinico.
     * 
     * @return la terapia con al menos su identificador.
     */
    public Terapia getCodigoTerapia() {
        return codigoTerapia;
    }

    /**
     *Establece una terapia con al menos su identificador al historial clinico. 
     * 
     * @param codigoTerapia la terapia que se quiere establecer.
     */
    public void setCodigoTerapia(Terapia codigoTerapia) {
        this.codigoTerapia = codigoTerapia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pacienteId != null ? pacienteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoriaClinica)) {
            return false;
        }
        HistoriaClinica other = (HistoriaClinica) object;
        if ((this.pacienteId == null && other.pacienteId != null) || (this.pacienteId != null && !this.pacienteId.equals(other.pacienteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.HistoriaClinica[ pacienteId=" + pacienteId + " ]";
    }
    
}

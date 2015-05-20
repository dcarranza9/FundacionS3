package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author David
 */
@Entity
@Table(name = "paciente")
@NamedQueries({
    @NamedQuery(name = "Paciente.findAll", query = "SELECT p FROM Paciente p"),
    @NamedQuery(name = "Paciente.findByIdPaciente", query = "SELECT p FROM Paciente p WHERE p.idPaciente = :idPaciente"),
    @NamedQuery(name = "Paciente.findByRaza", query = "SELECT p FROM Paciente p WHERE p.raza = :raza"),
    @NamedQuery(name = "Paciente.findByOcupacion", query = "SELECT p FROM Paciente p WHERE p.ocupacion = :ocupacion"),
    @NamedQuery(name = "Paciente.findByNivelEscolar", query = "SELECT p FROM Paciente p WHERE p.nivelEscolar = :nivelEscolar"),
    @NamedQuery(name = "Paciente.findByAntecedentesHereditarios", query = "SELECT p FROM Paciente p WHERE p.antecedentesHereditarios = :antecedentesHereditarios")})
public class Paciente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_paciente")
    private String idPaciente;
    @Basic(optional = false)
    @Column(name = "raza")
    private String raza;
    @Basic(optional = false)
    @Column(name = "ocupacion")
    private String ocupacion;
    @Basic(optional = false)
    @Column(name = "nivel_escolar")
    private String nivelEscolar;
    @Basic(optional = false)
    @Column(name = "antecedentes_hereditarios")
    private String antecedentesHereditarios;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaciente", fetch = FetchType.EAGER)
    private List<Cita> citaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaciente", fetch = FetchType.EAGER)
    private List<Testimonio> testimonioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaciente", fetch = FetchType.EAGER)
    private List<PersonaPaciente> personaPacienteList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "paciente", fetch = FetchType.EAGER)
    private HistoriaClinica historiaClinica;

    /**
     *
     */
    public Paciente() {
    }

    /**
     *
     * @param idPaciente
     */
    public Paciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    /**
     *
     * @param idPaciente
     * @param raza
     * @param ocupacion
     * @param nivelEscolar
     * @param antecedentesHereditarios
     */
    public Paciente(String idPaciente, String raza, String ocupacion, String nivelEscolar, String antecedentesHereditarios) {
        this.idPaciente = idPaciente;
        this.raza = raza;
        this.ocupacion = ocupacion;
        this.nivelEscolar = nivelEscolar;
        this.antecedentesHereditarios = antecedentesHereditarios;
    }

    /**
     *
     * @return
     */
    public String getIdPaciente() {
        return idPaciente;
    }

    /**
     *
     * @param idPaciente
     */
    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    /**
     *
     * @return
     */
    public String getRaza() {
        return raza;
    }

    /**
     *
     * @param raza
     */
    public void setRaza(String raza) {
        this.raza = raza;
    }

    /**
     *
     * @return
     */
    public String getOcupacion() {
        return ocupacion;
    }

    /**
     *
     * @param ocupacion
     */
    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    /**
     *
     * @return
     */
    public String getNivelEscolar() {
        return nivelEscolar;
    }

    /**
     *
     * @param nivelEscolar
     */
    public void setNivelEscolar(String nivelEscolar) {
        this.nivelEscolar = nivelEscolar;
    }

    /**
     *
     * @return
     */
    public String getAntecedentesHereditarios() {
        return antecedentesHereditarios;
    }

    /**
     *
     * @param antecedentesHereditarios
     */
    public void setAntecedentesHereditarios(String antecedentesHereditarios) {
        this.antecedentesHereditarios = antecedentesHereditarios;
    }

    /**
     *
     * @return
     */
    public List<Cita> getCitaList() {
        return citaList;
    }

    /**
     *
     * @param citaList
     */
    public void setCitaList(List<Cita> citaList) {
        this.citaList = citaList;
    }

    /**
     *
     * @return
     */
    public List<Testimonio> getTestimonioList() {
        return testimonioList;
    }

    /**
     *
     * @param testimonioList
     */
    public void setTestimonioList(List<Testimonio> testimonioList) {
        this.testimonioList = testimonioList;
    }

    /**
     *
     * @return
     */
    public List<PersonaPaciente> getPersonaPacienteList() {
        return personaPacienteList;
    }

    /**
     *
     * @param personaPacienteList
     */
    public void setPersonaPacienteList(List<PersonaPaciente> personaPacienteList) {
        this.personaPacienteList = personaPacienteList;
    }

    /**
     *
     * @return
     */
    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }

    /**
     *
     * @param historiaClinica
     */
    public void setHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinica = historiaClinica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPaciente != null ? idPaciente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paciente)) {
            return false;
        }
        Paciente other = (Paciente) object;
        if ((this.idPaciente == null && other.idPaciente != null) || (this.idPaciente != null && !this.idPaciente.equals(other.idPaciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Paciente[ idPaciente=" + idPaciente + " ]";
    }
    
}

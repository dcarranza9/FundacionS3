
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
import javax.persistence.Table;

/**
 * Representa una terapia del mundo real con todos los atributos  ofrecida por 
 * la fundacion.
 * 
 * @author David
 */

@Entity
@Table(name = "terapias")
@NamedQueries({
    @NamedQuery(name = "Terapia.findAll", query = "SELECT t FROM Terapia t"),
    @NamedQuery(name = "Terapia.findByCodigoTerapia", query = "SELECT t FROM Terapia t WHERE t.codigoTerapia = :codigoTerapia"),
    @NamedQuery(name = "Terapia.findByNombreTerapia", query = "SELECT t FROM Terapia t WHERE t.nombreTerapia = :nombreTerapia"),
    @NamedQuery(name = "Terapia.findByDescripcionTerapia", query = "SELECT t FROM Terapia t WHERE t.descripcionTerapia = :descripcionTerapia")})
public class Terapia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo_terapia")
    private String codigoTerapia;
    @Column(name = "nombre_terapia")
    private String nombreTerapia;
    @Column(name = "descripcion_terapia")
    private String descripcionTerapia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoTerapia", fetch = FetchType.EAGER)
    private List<Testimonio> testimonioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoTerapia", fetch = FetchType.EAGER)
    private List<HistoriaClinica> historiaClinicaList;

    /**
     *Construye una terapia sin atributos inicialmente.
     */
    public Terapia() {
    }

    /**
     * Construye una terapia inicialmente con el codigo.
     * 
     * @param codigoTerapia el codigo de la terapia.
     */
    public Terapia(String codigoTerapia) {
        this.codigoTerapia = codigoTerapia;
    }

    /**
     * Obtiene el codigo establecido a la terapia.
     * 
     * @return el identificador de la terapia.
     */
    public String getCodigoTerapia() {
        return codigoTerapia;
    }

    /**
     * Establece un codigo a la terapia.
     * 
     * @param codigoTerapia el codigo que se quiere establecer.
     */
    public void setCodigoTerapia(String codigoTerapia) {
        this.codigoTerapia = codigoTerapia;
    }

    /**
     * Obtiene el nombre establecido a la terapia.
     * 
     * @return el nombre de la terapia.
     */
    public String getNombreTerapia() {
        return nombreTerapia;
    }

    /**
     * Establece un nombre a la terapia.
     * 
     * @param nombreTerapia en nombre que se quiere establecer.
     */
    public void setNombreTerapia(String nombreTerapia) {
        this.nombreTerapia = nombreTerapia;
    }

    /**
     *Obtiene la descipcion establecida a la terapia.
     *
     * @return la descripcion de la terapia.
     */
    public String getDescripcionTerapia() {
        return descripcionTerapia;
    }

    /**
     *Establece una descripcion a la terapia.
     * 
     * @param descripcionTerapia la descricion a establecer.
     */
    public void setDescripcionTerapia(String descripcionTerapia) {
        this.descripcionTerapia = descripcionTerapia;
    }

    /**
     * Obtiene la lista de testimonios establecidos que se han realizado sobre 
     * la terapia.
     * 
     * @return la lista de testimonios de la terapia.
     */
    public List<Testimonio> getTestimonioList() {
        return testimonioList;
    }

    /**
     *Establece una lista de testimonios realizados sobre la terapia.
     * 
     * @param testimonioList la lista de testimonios a establcer.
     */
    public void setTestimonioList(List<Testimonio> testimonioList) {
        this.testimonioList = testimonioList;
    }

    /**
     * Obtiene la lista de historias clinicas a las que esta asignada la terapia.
     * 
     * @return la lista de historias clinicas en las que esta asignada la terapia
     */
    public List<HistoriaClinica> getHistoriaClinicaList() {
        return historiaClinicaList;
    }

    /**
     * Establece una lista de historias clinicas en las que se debe asignar la 
     * terapia.
     * 
     * @param historiaClinicaList lista de historias clinicas a establecer-
     */
    public void setHistoriaClinicaList(List<HistoriaClinica> historiaClinicaList) {
        this.historiaClinicaList = historiaClinicaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoTerapia != null ? codigoTerapia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Terapia)) {
            return false;
        }
        Terapia other = (Terapia) object;
        if ((this.codigoTerapia == null && other.codigoTerapia != null) || (this.codigoTerapia != null && !this.codigoTerapia.equals(other.codigoTerapia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Terapia[ codigoTerapia=" + codigoTerapia + " ]";
    }
    
}

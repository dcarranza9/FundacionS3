
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Esta clase representa el concepto de una donacion en el mundo real y cuenta
 * con atributos representan a otros conceptos que lo componen extraidos
 * de un formulario fisico  que debe completarse en especifico para la 
 * fundacion. Esta clase se encuentra ubicada en el paquete de modelo.
 * 
 * @author David
 */
@Entity
@Table(name = "donaciones")
@NamedQueries({
    @NamedQuery(name = "Donacion.findAll", query = "SELECT d FROM Donacion d"),
    @NamedQuery(name = "Donacion.findByIdDonacion", query = "SELECT d FROM Donacion d WHERE d.idDonacion = :idDonacion"),
    @NamedQuery(name = "Donacion.findByDonador", query = "SELECT d FROM Donacion d WHERE d.donador = :donador"),
    @NamedQuery(name = "Donacion.findByMotivo", query = "SELECT d FROM Donacion d WHERE d.motivo = :motivo"),
    @NamedQuery(name = "Donacion.findByFecha", query = "SELECT d FROM Donacion d WHERE d.fecha = :fecha")})
public class Donacion implements Serializable {
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_donacion")
    private Integer idDonacion;
    @Column(name = "donador")
    private String donador;
    @Basic(optional = false)
    @Column(name = "motivo")
    private String motivo;
    @JoinColumn(name = "nit", referencedColumnName = "nit")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Fundacion nit;

    /**
     *Construye una donacion sin atributos inicialmente.
     */
    public Donacion() {
    }

    /**
     *Construye una donacion inicialmente con su identificador. 
     * 
     * @param idDonacion el identificador de la donacion
     */
    public Donacion(Integer idDonacion) {
        this.idDonacion = idDonacion;
    }

    /**
     *Construye una donacion inicialmente con su identificador y el motivo 
     * por el que se realizó la donacion.
     * 
     * @param idDonacion el identificador de la donacion.
     * @param motivo el motivo por el cual se realizo la donacion.
     */
    public Donacion(Integer idDonacion, String motivo) {
        this.idDonacion = idDonacion;
        this.motivo = motivo;
    }

    /**
     * Obtiene el identificador establecido a la donacion.
     *
     * @return el identificador de la donacion.
     */
    public Integer getIdDonacion() {
        return idDonacion;
    }

    /**
     *Establece un identificador para la donacion.
     * @param idDonacion el identificador que se quiere establecer a la donacion.
     */
    public void setIdDonacion(Integer idDonacion) {
        this.idDonacion = idDonacion;
    }

    /**
     *Obtiene el nombre que se establecio a la donacion.
     * 
     * @return el nombre del donador.
     */
    public String getDonador() {
        return donador;
    }

    /**
     *Establece el nombre del donador a la donacion.
     * 
     * @param donador el nombre del 
     */
    public void setDonador(String donador) {
        this.donador = donador;
    }

    /**
     *Obtiene el motivo establecido a la donacion.
     * 
     * @return el motivo de la donacion
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     *Establece el motivo de la donacion.
     * 
     * @param motivo el motivo que se quiere establecer
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    /**
     *Obtiene el nit de la fundacion.
     * 
     * @return el nit de la fundacion.
     */
    public Fundacion getNit() {
        return nit;
    }

    /**
     *Establece el nit de la fundacion ya que es a la que se le realiza.
     * 
     * @param nit el nit de la fundacion.
     */
    public void setNit(Fundacion nit) {
        this.nit = nit;
    }
    
    /**
     * Obtiene la fecha en que se realizo la donacion, este es un campo que 
     * se pone automaticamente en la base de datos por lo que no es posible
     * establecerlo antes.
     * 
     * @return la fecha en que se realizo la donacion. 
     */
    public Date getFecha() {
        return fecha;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDonacion != null ? idDonacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Donacion)) {
            return false;
        }
        Donacion other = (Donacion) object;
        if ((this.idDonacion == null && other.idDonacion != null) || (this.idDonacion != null && !this.idDonacion.equals(other.idDonacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Donacion[ idDonacion=" + idDonacion + " ]";
    }
    
}

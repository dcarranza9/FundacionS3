
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
 * Esta clase representa un evento de la fundacion en el mundo real y contiene 
 * los atributos relevantas de como se registran fisicamente. Esta clase se
 * encuentra ubicada en el paquete de modelo.
 * 
 * @author David
 */
@Entity
@Table(name = "eventos")
@NamedQueries({
    @NamedQuery(name = "Evento.findAll", query = "SELECT e FROM Evento e"),
    @NamedQuery(name = "Evento.findByIdEvento", query = "SELECT e FROM Evento e WHERE e.idEvento = :idEvento"),
    @NamedQuery(name = "Evento.findByDescripcion", query = "SELECT e FROM Evento e WHERE e.descripcion = :descripcion"),
    @NamedQuery(name = "Evento.findByNombreEvento", query = "SELECT e FROM Evento e WHERE e.nombreEvento = :nombreEvento"),
    @NamedQuery(name = "Evento.findByFecha", query = "SELECT e FROM Evento e WHERE e.fecha = :fecha"),
    @NamedQuery(name = "Evento.findByLugar", query = "SELECT e FROM Evento e WHERE e.lugar = :lugar")})
public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_evento")
    private Integer idEvento;
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "nombre_evento")
    private String nombreEvento;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "lugar")
    private String lugar;
    @JoinColumn(name = "nit", referencedColumnName = "nit")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Fundacion nit;

    /**
     * Construye un evento sin atributos inicialmente.
     */
    public Evento() {
    }

    /**
     * Construye un evento con todos los atributos necesarios inicialmente.
     * 
     * @param nombreEvento El nombre del evento.
     * @param fecha la fecha de realizacion.
     * @param lugar el lugar en que se realizo el evento.
     */
    public Evento(String nombreEvento, Date fecha, String lugar) {
        
        this.nombreEvento = nombreEvento;
        this.fecha = fecha;
        this.lugar = lugar;
    }

    /**
     *Obtiene el identificador del evento. Este no es un campo que se deba 
     * establecer manualmente ya que se lleva una secuencia en la base de datos.
     * 
     * @return el identificador.
     */
    public Integer getIdEvento() {
        return idEvento;
    }

    /**
     * Obtiene la descripcion establecida al evento.
     *
     * @return la descripcion del evento
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece una descripcion del evento. 
     * 
     * @param descripcion la descripcion que se quiera establecer.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *Obtiene el nombre establecido al evento.
     * 
     * @return el nombre del evento.
     */
    public String getNombreEvento() {
        return nombreEvento;
    }

    /**
     * Establece un nombre al evento.
     * 
     * @param nombreEvento
     */
    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    /**
     * Obtiene la fecha de realizacion establecida al evento.
     * 
     * @return la fecha de realizacion del evento.
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de realizacion del evento.
     * 
     * @param fecha la fecha de realizacion del evento.
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el lugar establecido en donde se relizo el evento.
     * 
     * @return el lugar de realizacion del evento.
     */
    public String getLugar() {
        return lugar;
    }

    /**
     * Establece el lugar donde se realizara el evento.
     * 
     * @param lugar el lugar del evento a establecer.
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    /**
     * Obtiene el nit de la fundacion la cual realizo el evento.
     * 
     * @return el nit de la fundacion.
     */
    public Fundacion getNit() {
        return nit;
    }

    /**
     * Establece el nit de la fundacion que realizo el evento
     * 
     * @param nit el nit de la fundacion a establecer.
     */
    public void setNit(Fundacion nit) {
        this.nit = nit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvento != null ? idEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.idEvento == null && other.idEvento != null) || (this.idEvento != null && !this.idEvento.equals(other.idEvento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Evento[ idEvento=" + idEvento + " ]";
    }
    
}

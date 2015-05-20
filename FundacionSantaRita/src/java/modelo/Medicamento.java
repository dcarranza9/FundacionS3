
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Esta clase representa un medicameto del mundo real y contiene los atributos
 * caracteristicos relevantes para la fundación.Esta clase se encuentra ubicada
 * en el paquete de modelo.
 * 
 * @author David
 */
@Entity
@Table(name = "medicamentos")
@NamedQueries({
    @NamedQuery(name = "Medicamento.findAll", query = "SELECT m FROM Medicamento m"),
    @NamedQuery(name = "Medicamento.findByCodigoMedicamento", query = "SELECT m FROM Medicamento m WHERE m.codigoMedicamento = :codigoMedicamento"),
    @NamedQuery(name = "Medicamento.findByNombre", query = "SELECT m FROM Medicamento m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Medicamento.findByLaboratorio", query = "SELECT m FROM Medicamento m WHERE m.laboratorio = :laboratorio"),
    @NamedQuery(name = "Medicamento.findByRecomendaciones", query = "SELECT m FROM Medicamento m WHERE m.recomendaciones = :recomendaciones"),
    @NamedQuery(name = "Medicamento.findByDescripcion", query = "SELECT m FROM Medicamento m WHERE m.descripcion = :descripcion"),
    @NamedQuery(name = "Medicamento.findByFechaCaducidad", query = "SELECT m FROM Medicamento m WHERE m.fechaCaducidad = :fechaCaducidad"),
    @NamedQuery(name = "Medicamento.findByComposicion", query = "SELECT m FROM Medicamento m WHERE m.composicion = :composicion"),
    @NamedQuery(name = "Medicamento.findByContraindicaciones", query = "SELECT m FROM Medicamento m WHERE m.contraindicaciones = :contraindicaciones"),
    @NamedQuery(name = "Medicamento.findByContenido", query = "SELECT m FROM Medicamento m WHERE m.contenido = :contenido")})
public class Medicamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo_medicamento")
    private String codigoMedicamento;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "laboratorio")
    private String laboratorio;
    @Basic(optional = false)
    @Column(name = "recomendaciones")
    private String recomendaciones;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "fecha_caducidad")
    @Temporal(TemporalType.DATE)
    private Date fechaCaducidad;
    @Basic(optional = false)
    @Column(name = "composicion")
    private String composicion;
    @Basic(optional = false)
    @Column(name = "contraindicaciones")
    private String contraindicaciones;
    @Basic(optional = false)
    @Column(name = "contenido")
    private String contenido;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoMedicamento", fetch = FetchType.EAGER)
    private List<HistoriaClinica> historiaClinicaList;

    /**
     * Construye un medicamento inicialmente sin atributos.
     */
    public Medicamento() {
    }

    /**
     * Construye un medicamento con su codido inicialmente.
     * 
     * @param codigoMedicamento el codigo del medicamento. 
     */
    public Medicamento(String codigoMedicamento) {
        this.codigoMedicamento = codigoMedicamento;
    }

    /**
     * Construye un medicamento con todos sus atributos relevantes inicialmente.
     * 
     * @param codigoMedicamento el codigo del medicamento.
     * @param nombre el nombre del medicamento
     * @param recomendaciones la recomendaciones de uso y conservacion del medicamento.
     * @param descripcion descripcion del medicamento.
     * @param fechaCaducidad la fecha de vencimiento del medicamento.
     * @param composicion la composicion del medicamento.
     * @param contraindicaciones las contraindicaciones del medicamento.
     * @param contenido el contenido neto del medicamento.
     */
    public Medicamento(String codigoMedicamento, String nombre, String recomendaciones, String descripcion, Date fechaCaducidad, String composicion, String contraindicaciones, String contenido) {
        this.codigoMedicamento = codigoMedicamento;
        this.nombre = nombre;
        this.recomendaciones = recomendaciones;
        this.descripcion = descripcion;
        this.fechaCaducidad = fechaCaducidad;
        this.composicion = composicion;
        this.contraindicaciones = contraindicaciones;
        this.contenido = contenido;
    }

    /**
     * Obtiene el codigo establecido al mediamento.
     * 
     * @return el codigo del medicamento. 
     */
    public String getCodigoMedicamento() {
        return codigoMedicamento;
    }

    /**
     * Establece un codigo al medicamento.
     * 
     * @param codigoMedicamento el codigo que se quiere establecer
     */
    public void setCodigoMedicamento(String codigoMedicamento) {
        this.codigoMedicamento = codigoMedicamento;
    }
    
    /**
     * Obtiene el nombre del medicamento.
     * 
     * @return el nombre del medicamento.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del medicamento.
     * 
     * @param nombre el nombre del medicamento.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el nombre del laboratorio que elaboro el medicamento.
     * 
     * @return el nombre del laboratorio que produjo el medicamento. 
     */
    public String getLaboratorio() {
        return laboratorio;
    }

    /**
     * Establece el nombre del laboratorio que produjo el medicamento.
     * 
     * @param laboratorio el nombre del laboratorio a establecer.
     */
    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }
    
    /**
     * Obtiene las recomendaciones de uso y/o conservacion establecidas al medicamento.
     * 
     * @return las recomendaciones del medicamento.
     */
    public String getRecomendaciones() {
        return recomendaciones;
    }
    
    /**
     * Establece las recomendaciones de uso y/o conservacion del medicamento.
     * 
     * @param recomendaciones las recomendaciones de uso y/o conservacion a establecer.
     */
    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }
    
    /**
     * Obtiene la descripcion establecida al medicamento.
     * 
     * @return  la descripcion del medicamento.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripcion del medicamento.
     * 
     * @param descripcion la descripcion que se quiere establecer.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la fecha de vencimiento del medicamento.
     * 
     * @return la fecha de vencimiento del medicamento.
     */
    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    /**
     * Establece la fecha de vencimiento del medicamento.
     * 
     * @param fechaCaducidad la fecha de vencimiento que se quiere estalecer.
     */
    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    /**
     * Obtiene la composicion del medicamento.
     * 
     * @return la composicion del medicamento.
     */
    public String getComposicion() {
        return composicion;
    }

    /**
     * Establece la composicion del medicamento.
     * 
     * @param composicion la composicion del medicamento a establcer.
     */
    public void setComposicion(String composicion) {
        this.composicion = composicion;
    }

    /**
     * Obtiene las contraindicaciones establecidas al medicameto.
     * 
     * @return las contraindicaciones del medicamento.
     */
    public String getContraindicaciones() {
        return contraindicaciones;
    }

    /**
     * Establece las contraindicaciones del medicamento
     * 
     * @param contraindicaciones las contraindicaciones a establecer.
     */
    public void setContraindicaciones(String contraindicaciones) {
        this.contraindicaciones = contraindicaciones;
    }

    /**
     * Obtiene el contenido neto establecido al medicamento.
     * 
     * @return el contenido neto del medicamento.
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * Establece el contenido neto al medicamento.
     * 
     * @param contenido el contenido neto a establecer.
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    /**
     * Obtiene la lista de historias clinicas en las que se ha asignado el 
     * medicamento.
     * 
     * @return la lista de historias clinicas en las que se asigno el medicamento.
     */
    public List<HistoriaClinica> getHistoriaClinicaList() {
        return historiaClinicaList;
    }

    /**
     * Establce la lista de historias clinicas en las que se ha asignado el medicamento.
     * 
     * @param historiaClinicaList la lista de historias clinicas a establecer.
     */
    public void setHistoriaClinicaList(List<HistoriaClinica> historiaClinicaList) {
        this.historiaClinicaList = historiaClinicaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoMedicamento != null ? codigoMedicamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medicamento)) {
            return false;
        }
        Medicamento other = (Medicamento) object;
        if ((this.codigoMedicamento == null && other.codigoMedicamento != null) || (this.codigoMedicamento != null && !this.codigoMedicamento.equals(other.codigoMedicamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Medicamento[ codigoMedicamento=" + codigoMedicamento + " ]";
    }
    
}

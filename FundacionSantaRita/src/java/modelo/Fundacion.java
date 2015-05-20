
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
 * Representa a la fundacion conceptualmente con sus atributos propios y en 
 * relacion.
 * 
 * @author David
 */
@Entity
@Table(name = "fundacion")
@NamedQueries({
    @NamedQuery(name = "Fundacion.findAll", query = "SELECT f FROM Fundacion f"),
    @NamedQuery(name = "Fundacion.findByNit", query = "SELECT f FROM Fundacion f WHERE f.nit = :nit"),
    @NamedQuery(name = "Fundacion.findByNombre", query = "SELECT f FROM Fundacion f WHERE f.nombre = :nombre")})
public class Fundacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nit")
    private String nit;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nit", fetch = FetchType.EAGER)
    private List<Evento> eventoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nit", fetch = FetchType.EAGER)
    private List<Donacion> donacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nit", fetch = FetchType.EAGER)
    private List<Funcionario> funcionarioList;
    
    /**
     * Construye una fundacion sin atributos incialmente.
     */

    public Fundacion() {
    }
    /**
     * Construye una fundacion inicialmente con su identificador.
     * @param nit el identificador de la fundacion.
     */
    public Fundacion(String nit) {
        this.nit = nit;
    }
    
    /**
     *Cosntruye una fundacion con el identificador y el nombre.
     * 
     * @param nit el identificador de la fundacion.
     * @param nombre el nombre de la fundacion.
     */
    public Fundacion(String nit, String nombre) {
        this.nit = nit;
        this.nombre = nombre;
    }
    
    /**
     * Obtiene el identificador de la fundacion establecido a la fundacion.
     * 
     * @return el identificador de la fundacion.
     */
    public String getNit() {
        return nit;
    }

    /**
     * Establece un identificador a la fundacion.
     * 
     * @param nit el identificador de la fundacion. 
     */
    public void setNit(String nit) {
        this.nit = nit;
    }
    
    /**
     * Obtiene el nombre establecido a la fundacion.
     *  
     * @return el nombre de la fundacion. 
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Establece un nombre a la fundacion.
     * 
     * @param nombre el nombre que se quiere establecer a la fundacion. 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Obtiene una lista de eventos realizados por la fundacion.
     * 
     * @return lista de eventos de la fundacion.
     */
    public List<Evento> getEventoList() {
        return eventoList;
    }

    /**
     * Establece una lista de eventos realizados a la fundacion.
     * 
     * @param eventoList lista de eventos realizados.
     */
    public void setEventoList(List<Evento> eventoList) {
        this.eventoList = eventoList;
    }

    /**
     * Obtiene una lista de donaciones realizadas a la fundacion.
     * 
     * @return la lista de donaciones realizadas establecidas.
     */
    public List<Donacion> getDonacionList() {
        return donacionList;
    }

    /**
     * Establece una lista de donaciones realizadas a la fundacion.
     * 
     * @param donacionList la lista de donaciones que se quiere establecer.
     */
    public void setDonacionList(List<Donacion> donacionList) {
        this.donacionList = donacionList;
    }

    /**
     * Obtiene una lista establecida de funcionarios asociados a la fundacion.
     * 
     * @return lista de funcionarios asociados de la fundacion.
     */
    public List<Funcionario> getFuncionarioList() {
        return funcionarioList;
    }

    /**
     * Establece una lista de funcionarios asociados a la fundacion.
     * 
     * @param funcionarioList la lista de funcionarios que se quieren asociar.
     */
    public void setFuncionarioList(List<Funcionario> funcionarioList) {
        this.funcionarioList = funcionarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nit != null ? nit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fundacion)) {
            return false;
        }
        Fundacion other = (Fundacion) object;
        if ((this.nit == null && other.nit != null) || (this.nit != null && !this.nit.equals(other.nit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Fundacion[ nit=" + nit + " ]";
    }
    
}

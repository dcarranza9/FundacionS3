
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
 * Representa las personas que son funcionarios en la fundacion. Establece 
 * la relacion entre las entidades personas y funcionarios.
 * 
 * @author David
 */
@Entity
@Table(name = "persona_funcionario")
@NamedQueries({
    @NamedQuery(name = "PersonaFuncionario.findAll", query = "SELECT p FROM PersonaFuncionario p"),
    @NamedQuery(name = "PersonaFuncionario.findByIdPesonaFuncionario", query = "SELECT p FROM PersonaFuncionario p WHERE p.idPesonaFuncionario = :idPesonaFuncionario")})
public class PersonaFuncionario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pesona_funcionario")
    private Integer idPesonaFuncionario;
    @JoinColumn(name = "id_funcionario", referencedColumnName = "id_funcionario")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Funcionario idFuncionario;
    @JoinColumn(name = "cedula", referencedColumnName = "cedula")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Persona cedula;

    /**
     *Construye una persona-funcionario inicialmete sin atributos.
     */
    public PersonaFuncionario() {
    }

    /**
     * Obtiene el identificador de la persona-funcionario. Esta dato es piuesto
     * por una secuencia de la base de datos por lo que solo es posible optenerlo.
     * 
     * @return el identificador de la persona-funcionario.
     */
    public Integer getIdPesonaFuncionario() {
        return idPesonaFuncionario;
    }

    /**
     *Obtiene el funcionario con al menos su identificador asociado a una persona.
     *
     * @return el funcionario asociado a la persona.
     */
    public Funcionario getIdFuncionario() {
        return idFuncionario;
    }

    /**
     * Establece un funcionario a una persona que se quiere asignar como funcionario.
     * 
     * @param idFuncionario el funcionario que se quiere establecer.
     */
    public void setIdFuncionario(Funcionario idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    /**
     * Obtiene la persona con al menos su identificador asociada como 
     * funcionario de la fundacion. 
     * 
     * @return la persona asignada como funcionario.
     */
    public Persona getCedula() {
        return cedula;
    }

    /**
     * Establece una persona con al menos su identificador que se quiere 
     * asignar como funcionario.
     * 
     * @param cedula la persona que se quiere asignar como funcionario.
     */
    public void setCedula(Persona cedula) {
        this.cedula = cedula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPesonaFuncionario != null ? idPesonaFuncionario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonaFuncionario)) {
            return false;
        }
        PersonaFuncionario other = (PersonaFuncionario) object;
        if ((this.idPesonaFuncionario == null && other.idPesonaFuncionario != null) || (this.idPesonaFuncionario != null && !this.idPesonaFuncionario.equals(other.idPesonaFuncionario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.PersonaFuncionario[ idPesonaFuncionario=" + idPesonaFuncionario + " ]";
    }
    
}

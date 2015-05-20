
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
 * Esta clase reprensenta una persona del mundo real con todos los
 * atributos caracteristicos relevantes  para la fundacion.
 * 
 * @author David
 */
@Entity
@Table(name = "persona")
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p"),
    @NamedQuery(name = "Persona.findByCedula", query = "SELECT p FROM Persona p WHERE p.cedula = :cedula"),
    @NamedQuery(name = "Persona.findByNombres", query = "SELECT p FROM Persona p WHERE p.nombres = :nombres"),
    @NamedQuery(name = "Persona.findByApellidos", query = "SELECT p FROM Persona p WHERE p.apellidos = :apellidos"),
    @NamedQuery(name = "Persona.findByGenero", query = "SELECT p FROM Persona p WHERE p.genero = :genero"),
    @NamedQuery(name = "Persona.findByFechaNacimiento", query = "SELECT p FROM Persona p WHERE p.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Persona.findByTelefono", query = "SELECT p FROM Persona p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "Persona.findByEmail", query = "SELECT p FROM Persona p WHERE p.email = :email"),
    @NamedQuery(name = "Persona.findByDireccion", query = "SELECT p FROM Persona p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "Persona.findByEstadoCivil", query = "SELECT p FROM Persona p WHERE p.estadoCivil = :estadoCivil")})
public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cedula")
    private String cedula;
    @Basic(optional = false)
    @Column(name = "nombres")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "genero")
    private Character genero;
    @Basic(optional = false)
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "estado_civil")
    private String estadoCivil;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cedula", fetch = FetchType.EAGER)
    private List<PersonaPaciente> personaPacienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cedula", fetch = FetchType.EAGER)
    private List<PersonaFuncionario> personaFuncionarioList;

    /**
     *Construye una persona si atributos inicialmente.
     */
    public Persona() {
    }

    /**
     * Construye una persona inicialmente con su identificador.
     * 
     * @param cedula el identificador de la persona.
     */
    public Persona(String cedula) {
        this.cedula = cedula;
    }

    /**
     *Crea una persona con todos los atributos inicialmente.
     * 
     * @param cedula cedula de la persona que hace de identificador
     * @param nombres los nombres de una persona. Uno o mas nombres.
     * @param apellidos los apellidos de la persona. Uno o mas.
     * @param direccion direccion domiciliaria de la persona.
     * @param genero genero de la  persona.
     * @param telefono numero de contacto telefonico de la persona.
     * @param email correo electonico de la persona.
     * @param fechaNacimiento fecha de nacimiento de la persona.
     * @param estadoCivil
     */
    public Persona(String cedula, String nombres, String apellidos, Character genero, Date fechaNacimiento, String telefono, String email, String direccion, String estadoCivil) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.estadoCivil = estadoCivil;
    }

    /**
     * Obtiene el numero de la cedula establecido a la person.
     *
     * @return la cedula de la persona.
     */
    public String getCedula() {
        return cedula;
    }

   /**
     *Establece un numero de cedula a una persona.
     * 
     * @param cedula numero de cedula de la persona
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     *Obtiene los numbres establecidos a la persona.
     * 
     * @return los nombres de la persona.
     */
    public String getNombres() {
        return nombres;
    }

    /**
     *Establece los nombres a una persona.
     * 
     * @param nombres los nombres de la persona.
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * Obtiene los apellidos establecidos a la persona.
     *
     * @return los apellidos de la persona.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     *Establece los apellidos a una persona.
     * 
     * @param apellidos los apellidos que se quieren establecer.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     *Obtiene el genero sexual establecido a una persona.
     *
     * @return el genero sexual.
     */
    public Character getGenero() {
        return genero;
    }

    /**
     *Establece un genero sexual a una persona.
     * 
     * @param genero el genero sexual que se quiere establecer a la persona.
     */
    public void setGenero(Character genero) {
        this.genero = genero;
    }

    /**
     *Obtiene la fecha de nacimiento establecida a una persona.
     * 
     * @return la fecha de nacimiento de la persona.
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento a una persona.
     *
     * @param fechaNacimiento la fecha de nacimiento que se quiere establecer 
     * a la persona
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * 
     * Obtiene el numero de telefono que se ha establecido a la persona.
     *
     * @return el numero telefonico de la persona.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     *Establece el numero de telefono a una persona.
     * 
     * @param telefono el numero de telefono que se quiere establecer.
     * 
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     *Obtiene el correo electronico establecido a una persona.
     * 
     * @return el correo electronico de la persona.
     */
    public String getEmail() {
        return email;
    }

    /**
     *Establece el correo electronico a una persona.
     * 
     * @param email el correo electronico que se quiere establecer a la persona.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *Obtiene la direccion residencial establecida a una persona.
     * 
     * @return la direccion residencial de la persona.
     * 
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     *Establece la direccion residencial a una persona.
     * 
     * @param direccion la direccion residencial que se quiere establecer.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el estado civil establecido a la persona.
     * 
     * @return el estado civil.
     */
    public String getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * Establece al estado civil a la persona. 
     * 
     * @param estadoCivil el estado civil a establecer.
     */
    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /**
     *Obtiene una lista de personas que son pacientes.
     * 
     * @return la lista de pacientes.
     */
    public List<PersonaPaciente> getPersonaPacienteList() {
        return personaPacienteList;
    }

    /**
     * Establece una lista de pacientes
     * 
     * @param personaPacienteList lista de pacientes.
     */
    public void setPersonaPacienteList(List<PersonaPaciente> personaPacienteList) {
        this.personaPacienteList = personaPacienteList;
    }

    /**
     * Obtiene la lista de funcionarios establecida.
     * 
     * @return lista de funcionarios.
     */
    public List<PersonaFuncionario> getPersonaFuncionarioList() {
        return personaFuncionarioList;
    }

    /**
     * Establece una lista de funcionarios.
     * 
     * @param personaFuncionarioList lista de funcionarios a establecer.
     */
    public void setPersonaFuncionarioList(List<PersonaFuncionario> personaFuncionarioList) {
        this.personaFuncionarioList = personaFuncionarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cedula != null ? cedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.cedula == null && other.cedula != null) || (this.cedula != null && !this.cedula.equals(other.cedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Persona[ cedula=" + cedula + " ]";
    }
    
}

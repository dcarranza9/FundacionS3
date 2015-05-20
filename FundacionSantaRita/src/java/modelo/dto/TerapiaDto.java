package modelo.dto;

/**
 *
 * @author MAICK
 */
public class TerapiaDto {
    String descripcion;
    String codigo;
    String nombre;

    public TerapiaDto(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public TerapiaDto(String Codigo, String nombre, String descripcion) {
        this.descripcion = descripcion;
        this.codigo = Codigo;
        this.nombre = nombre;
    }
    
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String Codigo) {
        this.codigo = Codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}


package modelo.conexion;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author David
 */
public class Conexion {
    private static final EntityManagerFactory emf = buildEntityManagerFactory();
    private static EntityManagerFactory buildEntityManagerFactory() {
    try {
        return Persistence.createEntityManagerFactory("FundacionSantaRitaPU");
    }catch (Throwable ex) {
    throw new RuntimeException("Error al crear la factoria de JPA");
    }
    }
    public static EntityManagerFactory getJPAFactory() {
    return emf;
    }
}

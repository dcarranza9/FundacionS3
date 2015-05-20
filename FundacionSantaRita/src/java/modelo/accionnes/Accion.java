
package modelo.accionnes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author David
 */
public abstract class Accion {

    private static String getPackage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public abstract String ejecutar(HttpServletRequest request,HttpServletResponse response);

    public static Accion getAccion(String tipo) {
        Accion accion = null;
        try {
            accion = (Accion) Class.forName(getPackage()+"."+tipo+"Accion").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
        e.printStackTrace();
        }

        return accion;
    }

}
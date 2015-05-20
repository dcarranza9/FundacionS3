package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Oscar
 */
public class Conexion {
    
    public static Conexion instancia; 
    private Connection cnn;
    
   private Conexion(){
        try {//org.apache.derby.jdbc.EmbeddedDriver
                Class.forName("com.mysql.jdbc.Driver");
                cnn=DriverManager.getConnection("jdbc:mysql://localhost:3306/fundacion?zeroDateTimeBehavior=convertToNull","root","");
        } catch (ClassNotFoundException ex) {
            System.out.println("error 1"+ex.getMessage());
        } catch (SQLException ex) {
          System.out.println("error 2"+ex.getMessage());
        }
   }
   
   public synchronized static Conexion entregarConexion() {
   if(instancia==null)
   { System.out.println("esta creando la conexion");
   instancia=new Conexion();
   }
   return instancia;   
   } 
   
   
   public void cerraConexion(){
   instancia=null;
   }

    /**
     * @return the cnn
     */
    public Connection getCnn() {
        return cnn;
    }
   
   
}

package modelo.dao;
import interfaces.CRUD;
import java.util.List;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dto.TerapiaDto;

/**
 *
 * @author MAICK
 */
public class TerapiaDao implements CRUD<TerapiaDto> {
    
    private static final String SQL_INSERT="insert into medicamentos (codigo_terapia,nombre_terapia,descripcion_terapia) values (?,?,?)";
    private static final String SQL_DELETE="delete from terapias where codigo_terapia=?";
    private static final String SQL_UPDATE="update terapias set codigo_terapia=?,nombre_terapia=?,descripcion_terapia=?";
    private static final String SQL_READ="select * from terapias where codigo_terapia=?";
    private static final String SQL_READALL="select * from terapias";
    
    private static final Conexion con= Conexion.entregarConexion();
    PreparedStatement ps; 

    @Override
    public boolean crear(TerapiaDto c) {
        try {
            ps=con.getCnn().prepareStatement(SQL_INSERT);
            ps.setString(1,c.getCodigo());
            ps.setString(2,c.getNombre());
            ps.setString(3,c.getDescripcion());
           
            if(ps.executeUpdate()>0){return true;}
            
        } catch (SQLException ex) {
            Logger.getLogger(TerapiaDto.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{con.cerraConexion();}
        return false;
    }

    @Override
    public boolean delete(TerapiaDto c) {
        try {
            ps=con.getCnn().prepareStatement(SQL_DELETE);
            ps.setString(1,c.getCodigo());
            if(ps.executeUpdate()>0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TerapiaDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally{con.cerraConexion();}
        return false;
    }

    @Override
    public boolean update(TerapiaDto c) {
     try {
            ps=con.getCnn().prepareStatement(SQL_UPDATE);
            System.out.println(c.getCodigo()+" ---- "+c.getNombre()+" ---- "+c.getDescripcion() );
            ps.setString(1,c.getCodigo());
            ps.setString(2,c.getNombre());
            ps.setString(3,c.getDescripcion());
            if(ps.executeUpdate()>0){return true;}
        } catch (SQLException ex) {
            Logger.getLogger(TerapiaDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally{con.cerraConexion();}
        return false;  
        
    }

    @Override
    public TerapiaDto read(TerapiaDto c) {
         TerapiaDto l=null;
        try {
            ps=con.getCnn().prepareStatement(SQL_READ);
            ResultSet rs;
            ps.setString(1,c.getCodigo());
            
            rs=ps.executeQuery();
            while(rs.next())
                 l=new TerapiaDto(rs.getString(0),rs.getString(1),rs.getString(2));
            
        } catch (SQLException ex) {
            Logger.getLogger(TerapiaDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{con.cerraConexion();}
        return l;
    }

    @Override
    public List<TerapiaDto> readAll() {
       ArrayList <TerapiaDto> terapias=new ArrayList();
        try {
            ps=con.getCnn().prepareStatement(SQL_READALL);
            ResultSet rs;
                  
            rs=ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString(1)+","+rs.getString(2)+","+rs.getString(3));
                 terapias.add(new TerapiaDto(rs.getString(1),rs.getString(2),rs.getString(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TerapiaDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            con.cerraConexion();
            System.out.println("se cerro la conexion");
        }
        System.out.println("se retornan las terapias");
        return terapias; 
        
        
    }
}

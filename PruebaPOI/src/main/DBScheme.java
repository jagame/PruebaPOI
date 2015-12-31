
package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que representa un esquema de una base de datos
 */
public class DBScheme implements AutoCloseable{
    
    private Connection con;
    
    
    public DBScheme(DBClass db, String ip, String dbName, String user, String pass)
            throws ClassNotFoundException,IllegalAccessException,InstantiationException,SQLException{
        
        Class.forName(db.getClassName()).newInstance();
        
        con = DriverManager.getConnection("jdbc:"+db+":"+"//"+ip+"/"+dbName, user, pass);
        
    }
    
    public DBScheme(DBClass db, String ip, String dbName)
            throws ClassNotFoundException,IllegalAccessException,InstantiationException,SQLException{
        this(db,ip,dbName,null,null);
    }
    
    public DBScheme(String ip, String dbName, String user, String pass)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException,SQLException{
        this(DBClass.MYSQL,ip,dbName,user,pass);
    }
    
    public DBScheme(String ip, String dbName)
            throws ClassNotFoundException,IllegalAccessException,InstantiationException,SQLException{
        this(DBClass.MYSQL,ip,dbName);
    }
    
    /**
     * Devuelve una lista con todos los nombres de las tablas del esquema actual
     * @return
     * @throws SQLException 
     */
    public List<String> getTableNames() throws SQLException{
        ResultSet rs = con.getMetaData().getTables(null, null, null, null);
        List<String> listaNombres = new ArrayList<>();
        String name;
        while(rs.next()){
            name = rs.getString(3);
            listaNombres.add( name );
        }
        
        return listaNombres;
    }
    /**
     * Devuelve un map con el nombre y tipo de todas las columnas de una tabla dada existente que el actual esquema
     * @param table
     * @return
     * @throws SQLException 
     */
    public Map<String,String> getColumnsInfo(String table)throws SQLException{
        ResultSet rs = con.getMetaData().getColumns(null, null, table, null);
        Map mapNombreTipo = new HashMap();
        String name;
        String type;
        while(rs.next()){
            name = rs.getString(4);
            type = rs.getString(5);
            mapNombreTipo.put(name, type);
        }
        
        return mapNombreTipo;
    }
    
    /**
     * Devuelve un resultset con todos los registros de la tabla pasada por parámetro
     * @param table
     * @return
     * @throws SQLException 
     */
    public ResultSet selectAllFrom(String table)throws SQLException{
        Statement sta = con.createStatement();
        
        return sta.executeQuery("SELECT * FROM "+table);
    }

    @Override
    public void close() throws Exception {
        con.close();
    }
    
    
    
}

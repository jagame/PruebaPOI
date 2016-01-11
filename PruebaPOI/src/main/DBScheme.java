
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
    
    public static class DBColumn{
        
        private String name;
        private String type;

        public DBColumn(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    
    private Connection con;
    
    
    public DBScheme(DBClass db, String ip, String dbName, String user, String pass)
            throws SQLException{
        
        try{
            Class.forName(db.getClassName()).newInstance();
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException e){
            throw new RuntimeException("No se ha encontrado el Driver del motor de Base de Datos");
        }
        String uri = "jdbc:"+db+":"+"//"+ip+"/"+dbName;
        con = DriverManager.getConnection(uri, user, pass);
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
     * Devuelve un map con el nombre de la tabla y Metadatos de la columna de todas las columnas que cumplan el patrón en las tablas que cumplan el patrón dado
     * @param table
     * @return
     * @throws SQLException 
     */
    public Map<String,List<DBColumn>> getColumnsInfo(String table) throws SQLException{
        return getColumnsInfo(table,null);
    }
    /**
     * Devuelve un map con el nombre de la tabla y Metadatos de la columna de todas las columnas de unas tablas que cumplan el patrón dado
     * @param table
     * @param columnExp
     * @return Map<NombreTabla,ListaMetadatosColumna>
     * @throws SQLException 
     */
    public Map<String,List<DBColumn>> getColumnsInfo(String table, String columnExp)throws SQLException{
        ResultSet rs = con.getMetaData().getColumns(null, null, table, columnExp);
        Map<String,List<DBColumn>> mapNombreTipo = new HashMap();
        String name;
        String type;
        String tableName;
        List<DBColumn> columnList;
        DBColumn column;
        while(rs.next()){
            tableName = rs.getString(3);
            name = rs.getString(4);
            type = rs.getString(5);
            column = new DBColumn(name, type);
            mapNombreTipo.putIfAbsent(tableName, new ArrayList());
            columnList = mapNombreTipo.get(tableName);
            columnList.add(column);
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

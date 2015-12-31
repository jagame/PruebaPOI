/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 * Enumerador con clases de drivers de BBDD
 * @author jagam
 */
public enum DBClass {
    MYSQL("com.mysql.jdbc.Driver");
    
    private final String driver;
    
    DBClass(String driver){
        this.driver = driver;
    }
    
    /**
     * Devuelve el nombre de la clase del Driver de este gestor de Base de Datos
     * @return 
     */
    public String getClassName(){
        return driver;
    }
    
    @Override
    public String toString(){
        return super.toString().toLowerCase();
    }
}

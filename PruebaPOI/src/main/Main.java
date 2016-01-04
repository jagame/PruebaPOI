/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jagam
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        Usa Ctrl+space, todo esta documentado con javadoc
        */
        new LoginWindow().setVisible(true);
        /*
        try( DBScheme db = new DBScheme("localhost", "pruebasExcel","root","root")){
            
            ExcelUtils.saveDBtoExcel(db, "C:\\Users\\jagam\\Documents\\Pruebas\\pruebaExcel.xls");
            
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }
    
}

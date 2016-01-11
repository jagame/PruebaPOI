/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import main.view.LoginWindow;
import main.view.GetDataWindow;
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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        new GetDataWindow().setVisible(true);
        
        
        /*
        try( DBScheme db = new DBScheme("localhost", "pruebasExcel","root","root")){
            
            ExcelUtils.saveDBtoExcel(db, "C:\\Users\\jagam\\Documents\\Pruebas\\pruebaExcel.xls");
            
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *  Clase orientada a la gestión de ficheros excel
 */
public class ExcelUtils {
    
    Workbook wb;
    String path;
    
    /**
     * Importa a un excel todas las tablas existentes en un objeto DBScheme
     * @param db
     * @param path
     * @throws IOException
     * @throws SQLException 
     */
    public static void saveDBtoExcel( DBScheme db, String path )throws IOException,SQLException{
        ExcelUtils eu = new ExcelUtils(path);
            ResultSet rs;
            
            for( String s : db.getTableNames() ){
                rs = db.selectAllFrom(s);
                eu.saveResultSetToExcelSheet(rs);
            }
            
            eu.saveWorkbook();
    }
    
    
    public ExcelUtils(String path){
        wb = new HSSFWorkbook();
        this.path = path;
    }
    
    /**
     * Almacena el fichero excel en el path indicado al instanciar esta clase
     * @throws IOException 
     */
    public void saveWorkbook()throws IOException{
        wb.write( new FileOutputStream(path) );
    }
    
    /**
     * Devuelve el CellStyle indicado para las celdas de cabecera de la tabla
     * @return 
     */
    public CellStyle GetTitleStyle(){
        CellStyle title = wb.createCellStyle();
        Font fuenteTitle = wb.createFont();
        fuenteTitle.setBold(true);
        title.setFont(fuenteTitle);
        title.setAlignment(CellStyle.ALIGN_CENTER);
        title.setBorderBottom( CellStyle.BORDER_MEDIUM );
        
        return title;
    }
    /**
     * Devuelve el CellStyle adecuado para mostrar una fecha en un formato legible
     * @return 
     */
    public CellStyle getDateStyle(){
        CellStyle style = wb.createCellStyle();
        CreationHelper ch = wb.getCreationHelper();
        style.setDataFormat( ch.createDataFormat().getFormat("dd/mm/yyyy") );
        
        return style;
    }
    /**
     * Almacena todo el contenido del resulset pasado por parámetro en una nueva Sheet dentro de este Workbook
     * @param rs
     * @throws SQLException
     * @throws IOException 
     */
    public void saveResultSetToExcelSheet(ResultSet rs)throws SQLException,IOException{
        CellStyle headerStyle = GetTitleStyle();
        Sheet sheet = wb.createSheet(rs.getMetaData().getTableName(1));
        int contaRow = 0;
        Row row = sheet.createRow(contaRow++);
        Cell cell;
        String value;
        int numColumnas = rs.getMetaData().getColumnCount();
        
        for( int i = 0 ; i<numColumnas ; i++ ){
            cell = row.createCell(i);
            value = rs.getMetaData().getColumnName(i+1);
            cell.setCellValue(value);
            cell.setCellStyle(headerStyle);
        }
        while( rs.next() ){
            row = sheet.createRow(contaRow++);
            setResultSetRowToExcelRow(rs, row);
        }
    }
    
    private void setResultSetRowToExcelRow( ResultSet rs, Row row )throws SQLException{
        int numColumnas = rs.getMetaData().getColumnCount();
        int type;
        int rsCol;
        CellStyle dateStyle = getDateStyle();
        Cell cell;
        for( int i = 0 ; i < numColumnas ; i++ ){
            rsCol = i+1;
            cell = row.createCell(i);
            type = rs.getMetaData().getColumnType(rsCol);
            switch( type ){
                case Types.BIGINT:
                case Types.BINARY:
                case Types.DECIMAL:
                case Types.DOUBLE:
                case Types.FLOAT:
                case Types.INTEGER:
                case Types.NUMERIC:
                case Types.REAL:
                case Types.SMALLINT:
                case Types.TINYINT:
                    double number = rs.getDouble(rsCol);
                    cell.setCellValue( number );
                    break;
                case Types.DATE:
                case Types.TIME:
                case Types.TIMESTAMP:
                case Types.TIMESTAMP_WITH_TIMEZONE:
                    Date date = rs.getDate(rsCol);
                    cell.setCellValue(date);
                    cell.setCellStyle( dateStyle );
                    break;
                case Types.BIT:
                case Types.BOOLEAN:
                    boolean bool = rs.getBoolean(rsCol);
                    cell.setCellValue(bool);
                    break;
                default:
                    String str = rs.getString(rsCol);
                    cell.setCellValue(str);
            }
        }
    }
    
}

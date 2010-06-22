/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author Richard Finkers
 */
public class ValidateDataSheet {

    /** Validation results */
    private static boolean sheetValidated;
    /** The import stream to load the data sheet */
    private static InputStream dataSheet;


    /**
     * Checks if a file is a valid excel (2003/2007?) workbook.
     * @param datasheet
     * @return valid or not valid
     */
    public static boolean validate(File datasheet) {
        sheetValidated = false;
        /** The excel workbook */
        Workbook wb;

        try {
            dataSheet = new FileInputStream(datasheet);
            wb = WorkbookFactory.create(dataSheet); //Is also check for valid workbook
            int rows=wb.getSheetAt(0).getLastRowNum();
            int cols=wb.getSheetAt(0).getRow(0).getLastCellNum();
            if(rows<5 || cols<5){
                return sheetValidated;//return false. TODO: better handeling of this error and report message to the user
            }

            sheetValidated = true;
        } catch (IOException ex) {
            Logger.getLogger(ValidateDataSheet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ValidateDataSheet.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sheetValidated;
    }

}

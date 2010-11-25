/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Richard Finkers
 */
public class ReadExcelSheet extends ManipulateExcelSheet {

    public ReadExcelSheet() {
    }

    public static void readPredictorAndResponseValue(String predictor, String sessionID) throws FileNotFoundException, InvalidFormatException, IOException {

        File responseSheet = new File(sessionID + "/response.xls");
        File predictorSheet = new File(sessionID + "/predictor.xls");

        readPredictorAndResponseValue(responseSheet, predictorSheet, predictor);
    }

    public static void readPredictorAndResponseValue(File responseSheet, File predictorSheet, String predictor) throws FileNotFoundException, InvalidFormatException, IOException {
        /** wb for the response variables */
        Workbook responseWorkbook;
        /** wb for the predictor variables */
        Workbook predictorWorkbook;

        responseWorkbook = loadExcelSheet(responseSheet);
        predictorWorkbook = loadExcelSheet(predictorSheet);
        Row row0 = responseWorkbook.getSheetAt(0).getRow(0);
        Row row1 = responseWorkbook.getSheetAt(0).getRow(1);
        Row row2 = predictorWorkbook.getSheetAt(0).getRow(1);

        row0.getLastCellNum();

        double[][] data = new double[2][row0.getLastCellNum()];
        String[] genotypeLabels = new String[row0.getLastCellNum()];
        //skip header row!
        for (int i = 1; i < row0.getLastCellNum(); i++) {
            data[0][i] = row1.getCell(i).getNumericCellValue();//predictor
            data[1][i] = row2.getCell(i).getNumericCellValue();//response
            genotypeLabels[i] = row0.getCell(i).getStringCellValue();
        }

        //return dataset

    }
}

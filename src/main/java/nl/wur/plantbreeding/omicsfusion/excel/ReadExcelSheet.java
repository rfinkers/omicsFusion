/*
 * Copyright 2011 omicstools.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.wur.plantbreeding.omicsfusion.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import nl.wur.plantbreeding.logic.jfreechart.GenotypeXYDataset;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.jfree.data.xy.DefaultXYDataset;

/**
 * Read excel sheets.
 * @author Richard Finkers
 * @version 1.0
 */
public class ReadExcelSheet extends ManipulateExcelSheet {

    public ReadExcelSheet() {
    }

    public static void readPredictorAndResponseValue(String predictor, String sessionID) throws FileNotFoundException, InvalidFormatException, IOException {

        File responseSheet = new File(sessionID + "/response.xls");
        File predictorSheet = new File(sessionID + "/predictor.xls");

        //readPredictorAndResponseValue(responseSheet, predictorSheet, predictor);
    }

    public static DefaultXYDataset readPredictorAndResponseValue(File responseSheet, File predictorSheet, String predictor) throws FileNotFoundException, InvalidFormatException, IOException {


        /** wb for the response variables */
        Sheet respWbSheet;
        /** wb for the predictor variables */
        Sheet predWbSheet;

        respWbSheet = loadExcelSheet(responseSheet).getSheetAt(0);//trait
        predWbSheet = loadExcelSheet(predictorSheet).getSheetAt(0);//matrix


        //TODO: refractor for sheet instead of row!
        Row predictorRow = predWbSheet.getRow(0);//matrix

        int i = 0;
        try {
            //Find the right column.
            for (i = 1; i < predictorRow.getLastCellNum(); i++) {//ommit the header column for the genotypes
                //System.out.println("row header:" + predictorRow.getCell(i).getStringCellValue());
                if (predictorRow.getCell(i).getStringCellValue().trim().equals(predictor)) {
                    break;
                }
            }
        } catch (Exception e) {
            //handle null pointer (empty column & int errors
            System.out.println("ERROR: " + e.getCause());
        }
        System.out.println("Predictor: " + predictor);

        System.out.println("Need column: " + i);

        System.out.println("predictor: " + predWbSheet.getLastRowNum() + " Cell: " + predictorRow.getLastCellNum());

        double[][] data = new double[2][predWbSheet.getLastRowNum()];
        String[] genotypeLabels = new String[predWbSheet.getLastRowNum()];
        //skip header row!

        try {
            for (int j = 0; j < predWbSheet.getLastRowNum(); j++) {
                //data matrix is 0 based, however, we have to start reading the excel sheet from row 1 (row0 = header).
                int z=j+1;
                data[0][j] = respWbSheet.getRow(z).getCell(1).getNumericCellValue();//predictor                
                data[1][j] = predWbSheet.getRow(z).getCell(i).getNumericCellValue();//response                
                genotypeLabels[j] = respWbSheet.getRow(z).getCell(0).getStringCellValue();
                //System.out.println("counter: " + z + " label:" + genotypeLabels[j] + " pred: " + data[0][j] + " resp: " + data[1][j]);
            }
        } catch (Exception e) {
            //TODO: throw exception
            System.out.println("Error in data");
        }

        /** The dataset */
        DefaultXYDataset dataSet = new GenotypeXYDataset("Genotype", data, genotypeLabels, genotypeLabels);

        //return dataset
        return dataSet;
    }
}

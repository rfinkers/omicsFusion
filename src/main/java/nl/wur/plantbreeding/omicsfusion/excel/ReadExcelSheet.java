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
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.wur.plantbreeding.logic.jfreechart.GenotypeXYDataset;
import org.apache.commons.math.stat.regression.SimpleRegression;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.jfree.data.xy.DefaultXYDataset;

/**
 * Read excel sheets.
 * @author Richard Finkers
 * @version 1.0
 */
public class ReadExcelSheet extends ManipulateExcelSheet {

    /** The logger. */
    private static final Logger LOG = Logger.getLogger(
            ReadExcelSheet.class.getName());

    public ReadExcelSheet() {
    }

    /**
     * Read the values of the predictor and response sheets and store these in
     * an DefaultXYDataset object.
     * @param responseSheet Name and location of the response sheet.
     * @param predictorSheet Name and location of the predictor sheet.
     * @param predictor Name of the selected predictor.
     * @return A XY chart object.
     * @throws DataSheetValidationException Response variable not found in the 
     * excel sheet.
     * @throws FileNotFoundException File not found.
     * @throws InvalidFormatException Not a compatible Excel format.
     * @throws IOException Error reading the file.
     * @throws Exception Not handled exception.
     */
    public static DefaultXYDataset readPredictorAndResponseValue(
            File responseSheet, File predictorSheet, String predictor)
            throws DataSheetValidationException, FileNotFoundException,
            InvalidFormatException, IOException,
            Exception {


        /** wb for the response variables. */
        Sheet respWbSheet;
        /** wb for the predictor variables. */
        Sheet predWbSheet;

        //trait
        respWbSheet = loadExcelSheet(responseSheet).getSheetAt(0);
        //predictor matrix
        predWbSheet = loadExcelSheet(predictorSheet).getSheetAt(0);

        //TODO: refractor for sheet instead of row!
        Row predictorRow = predWbSheet.getRow(0);//matrix

        int i = 0;
        boolean found = false;

        //Find the right column.
        for (i = 1; i < predictorRow.getLastCellNum(); i++) {
            //ommit the header column for the genotypes
            //System.out.println("row header:"
            //+ predictorRow.getCell(i).getStringCellValue());
            if (predictorRow.getCell(i).getStringCellValue().trim().
                    equals(predictor)) {
                found = true;
                break;
            }
        }

        if (found == false) {
            LOG.warning("Response variable not found in the excel sheet.");
            throw new DataSheetValidationException("Response variable not "
                    + "found in the excel sheet");
        }

        LOG.log(Level.INFO, "Predictor: {0}", predictor);

        LOG.log(Level.INFO, "Need column: {0}", i);

        LOG.log(Level.INFO, "Last row: {0} Last Column: {1}",
                new Object[]{predWbSheet.getLastRowNum(),
                    predictorRow.getLastCellNum()});

        double[][] data = new double[2][predWbSheet.getLastRowNum()];
        String[] genotypeLabels = new String[predWbSheet.getLastRowNum()];
        //skip header row!

        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        //add the object for regression
        SimpleRegression slr = new SimpleRegression();

        //initialize z which holds the correct row number.
        int z = 0;

        try {
            for (int j = 0; j < predWbSheet.getLastRowNum(); j++) {
                //data matrix is 0 based, however, we have to start reading the
                //excel sheet from row 1 (row0 = header).
                z = j + 1;
                data[0][j] = respWbSheet.getRow(z).getCell(1).
                        getNumericCellValue();//response
                data[1][j] = predWbSheet.getRow(z).getCell(i).
                        getNumericCellValue();//predictor
                //Genotype labels can contain numeric or string values.
                //TODO: generic switch?
                Cell cell = respWbSheet.getRow(z).getCell(0);

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        genotypeLabels[j] = respWbSheet.getRow(z).getCell(0).
                                getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            genotypeLabels[j] =
                                    cell.getDateCellValue().toString();
                        } else {
                            genotypeLabels[j] =
                                    Double.toString(cell.getNumericCellValue());
                        }
                        break;
                }

                //System.out.println("counter: " + z + " label:" +
                //genotypeLabels[j] + " pred: " + data[0][j] + " resp: " +
                //data[1][j]);

                //Get the min and max response variable for regression.
                if (data[0][j] > maxX) {
                    maxX = data[0][j];
                }
                if (data[0][j] < minX) {
                    minX = data[0][j];
                }
                //Add data to regression dataset
                slr.addData(data[0][j], data[1][j]);
            }
        }
        catch (Exception e) {
            //TODO: throw exception
            LOG.warning("Error in data at row: " + z);
            e.printStackTrace();
        }

        //Predict a new y values for the extreme X?
        double[][] regLine = new double[2][2];
        LOG.log(Level.INFO, "preY: {0} predY: {1}",
                new Object[]{slr.predict(minX), slr.predict(maxX)});
        regLine[0][0] = minX;
        regLine[1][0] = slr.predict(minX);
        regLine[0][1] = maxX;
        regLine[1][1] = slr.predict(maxX);

        /** The dataset */
        DefaultXYDataset dataSet = new GenotypeXYDataset("Genotype",
                data, genotypeLabels, genotypeLabels);
        //add the regression series to the dataSet.
        dataSet.addSeries("regression", regLine);

        //return dataset
        return dataSet;
    }
}

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
import nl.wur.plantbreeding.omicsfusion.utils.Constants;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Richard Finkers
 */
public class ValidateDataSheets extends ManipulateExcelSheet {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(ValidateDataSheets.class.getName());

    private ValidateDataSheets() {
    }

    /**
     * Checks if a file is a valid excel (2003/2007?) workbook.
     * @param responseSheet
     * @param predictorSheet
     * @throws DataSheetValidationException
     * @throws InvalidFormatException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void validateExcelSheets(File responseSheet, File predictorSheet) throws DataSheetValidationException, InvalidFormatException, FileNotFoundException, IOException {

        /** nr of rows in response sheet */
        int responseRows;
        /** nr of rows in predictor sheet */
        int predictorRows;

        /** wb for the response variables */
        Workbook responseWorkbook;
        /** wb for the predictor variables */
        Workbook predictorWorkbook;

        responseWorkbook = loadExcelSheet(responseSheet);
        responseRows = checkWorkbookDimensions(responseWorkbook, responseSheet.getName(), Constants.MIN_RESPONSE_COLUMNS + 1);// +1 because of column containing the labels.
        predictorWorkbook = loadExcelSheet(predictorSheet);
        predictorRows = checkWorkbookDimensions(predictorWorkbook, predictorSheet.getName(), Constants.MIN_PREDICTOR_COLUMNS + 1);// +1 because of columns containing the labels.

        if (predictorRows == responseRows) {
            //FIXME: temporarily disabled due to numeric / text column validation problem
            //compareIndividualNames(responseWorkbook, predictorWorkbook);
        } else {
            throw new DataSheetValidationException("Number of individuals on the predictor and response variable sheets differ");
        }
    }

    public static void validatePredictResponseSheet() {
        //TODO: implement validatePredictResponseSheet
    }

    /**
     * Check the dimension of an excel sheet
     * @param wb Name of the workbook
     * @param minColumns Minimal number of variables (columns) required for this type of workbook.
     * @return The number of rows (= number of individuals)
     * @throws DataSheetValidationException Dimensions of the workbook are not correct.
     */
    private static int checkWorkbookDimensions(Workbook wb, String fileName, int minColumns) throws DataSheetValidationException {
        int rows = wb.getSheetAt(0).getLastRowNum();
        int cols = wb.getSheetAt(0).getRow(0).getLastCellNum();
        LOG.log(Level.INFO, "wb:{0} MinCol {1}", new Object[]{wb.getSheetName(0), minColumns});
        LOG.log(Level.INFO, "Rows: {0} Columns: {1}", new Object[]{rows, cols});
        if (rows < Constants.MIN_INDIVIDUALS) {
            throw new DataSheetValidationException("At least " + Constants.MIN_INDIVIDUALS + " individuals required, while " + rows + " are present in the excel sheet.");
        } else if (cols < minColumns) {
            //return false. TODO: better handeling of this error and report message to the user
            throw new DataSheetValidationException("Expected dimensions of the sheet: " + fileName + " are not correct");
        }
        return rows;
    }

    private static void compareIndividualNames(Workbook responseWorkbook, Workbook predictorWorkbook) throws DataSheetValidationException {
        for (int i = 0; i < responseWorkbook.getSheetAt(0).getLastRowNum(); i++) {
            //Do not check the header row.
            if (i != 0) {
                //field content can be text of numeric
                if (!responseWorkbook.getSheetAt(0).getRow(i).getCell(0).equals(predictorWorkbook.getSheetAt(0).getRow(i).getCell(0))) {
                    int rowNumber = i + 1;
                    throw new DataSheetValidationException("The individual name in row:"
                            + rowNumber + " does not match for both sheets (" + responseWorkbook.getSheetAt(0).getRow(i).getCell(0).getStringCellValue() + "/" + predictorWorkbook.getSheetAt(0).getRow(i).getCell(0).getStringCellValue() + ")");
                }
            }
        }
    }
}

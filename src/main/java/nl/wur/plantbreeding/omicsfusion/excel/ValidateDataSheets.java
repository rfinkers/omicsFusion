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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Validation if the excel sheets are valid, have the correct dimensions and
 * that the names of the individuals are identical in the different excel
 * sheets.
 *
 * @author Richard Finkers
 * @version 1.0
 * @since 1.0
 */
public class ValidateDataSheets extends ManipulateExcelSheet {

    /**
     * The logger.
     */
    private static final Logger LOG = Logger.getLogger(
            ValidateDataSheets.class.getName());

    /**
     * Default constructor. private ValidateDataSheets() { }
     *
     * /**
     * Checks if a file is a valid excel (2003/2007/2010) workbook.
     *
     * @param responseSheet The response sheet object.
     * @param predictorSheet The predictor sheet object.
     * @throws DataSheetValidationException Excel sheet validation failed.
     * @throws InvalidFormatException Not a valid excel workbook.
     * @throws FileNotFoundException Signals that an attempt to open the file
     * denoted by a specified pathname has failed.
     * @throws IOException Signals that an I/O exception of some sort has
     * occurred. This class is the general class of exceptions produced by
     * failed or interrupted I/O operations.
     */
    public static void validateExcelSheets(File responseSheet,
            File predictorSheet) throws DataSheetValidationException,
            InvalidFormatException, FileNotFoundException, IOException {

        /**
         * nr of rows in response sheet.
         */
        int responseRows;
        /**
         * nr of rows in predictor sheet.
         */
        int predictorRows;

        /**
         * wb for the response variables.
         */
        Workbook responseWorkbook;
        /**
         * wb for the predictor variables.
         */
        Workbook predictorWorkbook;

        responseWorkbook = loadExcelSheet(responseSheet);
        // +1 because of column containing the labels.
        responseRows = checkWorkbookDimensions(responseWorkbook,
                responseSheet.getName(), Constants.MIN_RESPONSE_COLUMNS + 1);
        predictorWorkbook = loadExcelSheet(predictorSheet);
        // +1 because of columns containing the labels.
        predictorRows = checkWorkbookDimensions(predictorWorkbook,
                predictorSheet.getName(), Constants.MIN_PREDICTOR_COLUMNS + 1);

        if (predictorRows != responseRows) {
            throw new DataSheetValidationException("Number of individuals on "
                    + "the predictor and response variable sheets differ");
        }

        compareIndividualNames(responseWorkbook, predictorWorkbook);
    }

    /**
     * Check the validate predict response excel sheet.
     */
    public static void validatePredictResponseSheet() {
        throw new UnsupportedOperationException("Not yet implemented");
        //TODO: implement validatePredictResponseSheet
    }

    /**
     * Check the dimension of an excel sheet.
     *
     * @param wb Name of the workbook.
     * @param fileName The name of the excel sheet.
     * @param minColumns Minimal number of variables (columns) required for this
     * type of workbook.
     * @return The number of rows (= number of individuals).
     * @throws DataSheetValidationException Dimensions of the workbook are not
     * correct.
     */
    private static int checkWorkbookDimensions(Workbook wb, String fileName,
            int minColumns) throws DataSheetValidationException {
        int rows = wb.getSheetAt(0).getLastRowNum();
        int cols = wb.getSheetAt(0).getRow(0).getLastCellNum();
        LOG.log(Level.INFO, "wb:{0} MinCol {1}",
                new Object[]{wb.getSheetName(0), minColumns});
        LOG.log(Level.INFO, "Rows: {0} Columns: {1}", new Object[]{rows, cols});
        if (rows < Constants.MIN_INDIVIDUALS) {
            throw new DataSheetValidationException("At least "
                    + Constants.MIN_INDIVIDUALS
                    + " individuals required, while " + rows
                    + " are present in the excel sheet.");
        } else if (cols < minColumns) {
            //return false.
            //TODO: better handeling of this error and report to the user
            throw new DataSheetValidationException("Expected dimensions of the"
                    + " sheet: " + fileName + " are not correct");
        }
        return rows;
    }

    /**
     * Check if the individual name column has an header and if the names within
     * the excel sheets has the same order.
     *
     * @param responseWorkbook The response workbook.
     * @param predictorWorkbook The predictor workbook.
     * @throws DataSheetValidationException Error validating the excel sheet.
     */
    private static void compareIndividualNames(Workbook responseWorkbook,
            Workbook predictorWorkbook) throws DataSheetValidationException {
        for (int i = 0; i < responseWorkbook.getSheetAt(0).getLastRowNum();
                i++) {
            //Check the header row.
            Cell resp = responseWorkbook.getSheetAt(0).getRow(i).getCell(0);
            Cell pred = predictorWorkbook.getSheetAt(0).getRow(i).getCell(0);
            if (resp == null || pred == null) {
                LOG.log(Level.WARNING, "Header row cannot be empty");
                if (resp == null && pred == null) {
                    throw new DataSheetValidationException("Cell A1 in the "
                            + "predictor and response sheet is empty."
                            + "expect \"sample\" as a valid header.");
                } else if (resp == null) {
                    throw new DataSheetValidationException("Cell A1 in the "
                            + "response sheet is empty. We "
                            + "expect \"sample\" as a valid header.");
                } else {
                    throw new DataSheetValidationException("Cell A1 in the "
                            + "predictor sheet is empty. We "
                            + "expect \"sample\" as a valid header.");
                }
            }
            //Check the individual names.
            if (i != 0) {
                //field content can be text of numeric
                String cellType = null;
                switch (resp.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        cellType = "string";
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        cellType = "double";
                        break;
                }
                if (cellType.equals("string")) {
                    if (!resp.getStringCellValue().trim().equals(
                            pred.getStringCellValue().trim())) {
                        int rowNumber = i + 1;
                        throw new DataSheetValidationException("The individual "
                                + "names in row: " + rowNumber + " does not "
                                + "match for both sheets ("
                                + resp.getStringCellValue() + "/"
                                + pred.getStringCellValue() + ")");
                    }
                } else {
                    if (resp.getNumericCellValue()
                            != pred.getNumericCellValue()) {
                        int rowNumber = i + 1;
                        throw new DataSheetValidationException("The individual "
                                + "names in row: " + rowNumber + " does not "
                                + "match for both sheets ("
                                + resp.getNumericCellValue() + "/"
                                + pred.getNumericCellValue() + ")");
                    }
                }
            }
        }
    }
}

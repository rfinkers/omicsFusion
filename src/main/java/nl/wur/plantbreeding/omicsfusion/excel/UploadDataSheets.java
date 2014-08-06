/*
 * Copyright 2011 omicstools.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package nl.wur.plantbreeding.omicsfusion.excel;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import nl.wur.plantbreeding.logic.sqlite.SqLiteQueries;
import nl.wur.plantbreeding.omicsfusion.datatypes.DataPointDataType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Parses the information in the excel sheets and uploads this information to
 * the SQLite database.
 *
 * @author Richard Finkers
 * @version 1.0
 *
 */
public class UploadDataSheets extends ManipulateExcelSheet {

    /**
     *
     * @param responseExcelFile
     * @param responseType
     * @param predictorExcelFile
     * @param predictorType
     * @param directory
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public static void uploadExcelSheets(File responseExcelFile,
            String responseType, File predictorExcelFile, String predictorType,
            String directory) throws SQLException, ClassNotFoundException {
        //TODO: refractor to methods!

        /**
         * wb for the response variables.
         */
        Workbook responseWorkbook = null;
        /**
         * wb for the predictor variables.
         */
        Workbook predictorWorkbook = null;

        try {
            responseWorkbook = loadExcelSheet(responseExcelFile);
            predictorWorkbook = loadExcelSheet(predictorExcelFile);
        } catch (IOException iOException) {
            System.out.println("IO");
        } catch (InvalidFormatException invalidFormatException) {
            System.out.println("Format");
        }

        Sheet predictorSheet = null;
        Sheet responseSheet = null;

        //First row contains the variable
        //TODO: NPE check
        try {
            responseSheet = responseWorkbook.getSheetAt(0);
            predictorSheet = predictorWorkbook.getSheetAt(0);
        } catch (Exception e) {
            //TODO
        }

        //Second row can contain the ontology ID (idetified with column name)
        final String ontologyID = "ontology";
        int responseRowCounter = 1;
        int predictorRowCounter = 1;
        try {
            if (responseSheet.getRow(responseRowCounter).getCell(0).getRichStringCellValue().getString().equals(ontologyID)) {
                responseRowCounter++;
                //parse row to db
                //FIXME: will introduce a bug?
                //variable, ontologyID
            }
            if (predictorSheet.getRow(predictorRowCounter).getCell(0).getRichStringCellValue().getString().equals(ontologyID)) {
                predictorRowCounter++;
            //parse row to DB

                //variable, ontologyID
            }
        } catch (Exception e) {
            System.out.println("Exception!");
        }
        //Data (first column genotype, other columns data).
        //parse the rest of the data.
        /**
         * List with response variables.
         */
        List<DataPointDataType> rdp = new ArrayList<>();
        /**
         * List with predictor variables.
         */
        List<DataPointDataType> pdp = new ArrayList<>();
        /**
         * List with traits.
         */
        HashMap<String, String> resp = new HashMap<>();

        //Fill the corresponding data objects.
        rdp = parseDataCells(responseRowCounter, responseSheet, "t");
        pdp = parseDataCells(predictorRowCounter, predictorSheet, "p");
        resp = parseTraitList(responseSheet);

        SqLiteQueries sql = new SqLiteQueries();
        sql.loadExcelData(rdp, pdp, resp, directory);
    }

    /**
     * Parses the numeric values, from the EXCEL sheets, and load them in a
     * list. *
     */
    private static List<DataPointDataType> parseDataCells(int rowCounter, Sheet excelSheet, String pre) {
        List<DataPointDataType> dataPoints = new ArrayList<>();
        for (int i = rowCounter;
                i < excelSheet.getLastRowNum() + 1; i++) {
            String header;
            Double value;
            String genotype;
            //Read the name of the genotype
            if (excelSheet.getRow(i).getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                genotype = Double.toString(excelSheet.getRow(i).getCell(0).getNumericCellValue());
            } else {
                genotype = excelSheet.getRow(i).getCell(0).getStringCellValue().trim();
            }

            for (int j = 1; j < excelSheet.getRow(0).getLastCellNum(); j++) {
                if (!excelSheet.getRow(0).getCell(j).getStringCellValue().isEmpty()) {
                    header = excelSheet.getRow(0).getCell(j).getStringCellValue();
                    //TODO: Test the following
                    //Valus could also be as text? This already leads to an now uncatched error in the next line.
                    //this is redundant with the same code in the method above -> Refractor out
                    //Use SWITCH to go through the different options and act accordingly.
                    //FIXME: miss one row!. +1 leads to NPE!??? Is this still teh case after refractoring?
                    switch (excelSheet.getRow(i).getCell(j).getCellType()) {
                        case (Cell.CELL_TYPE_NUMERIC):
                            value = excelSheet.getRow(i).getCell(j).
                                    getNumericCellValue();
                            break;
                        case (Cell.CELL_TYPE_STRING):
                            value = Double.parseDouble(excelSheet.getRow(i).
                                    getCell(j).getStringCellValue());
                            break;
                        default:
                            value = Double.NaN;
                    }
                    dataPoints.add(new DataPointDataType(genotype, i, header, pre + j, value));
                }
            }
        }
        return dataPoints;
    }

    /**
     * Parse the list of traits.
     */
    private static HashMap<String, String> parseTraitList(Sheet excelSheet) {
        HashMap<String, String> traitNames = new HashMap<>();
        for (int j = 1; j < excelSheet.getRow(0).getLastCellNum(); j++) {
            if (!excelSheet.getRow(0).getCell(j).getStringCellValue().isEmpty()) {
                String header = excelSheet.getRow(0).getCell(j).getStringCellValue();
                System.out.println("Trait: " + header);
                traitNames.put("t" + j, header);
            }
        }
        return traitNames;
    }
}

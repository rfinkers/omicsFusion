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

import com.almworks.sqlite4java.SQLiteException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nl.wur.plantbreeding.logic.sqlite4java.SqLiteQueries;
import nl.wur.plantbreeding.omicsfusion.datatypes.DataPointDataType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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

    public static void uploadExcelSheets(File responseExcelFile,
            String responseType, File predictorExcelFile, String predictorType,
            String directory) throws SQLiteException {
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
        }
        catch (IOException iOException) {
            System.out.println("IO");
        }
        catch (InvalidFormatException invalidFormatException) {
            System.out.println("Format");
        }

        List<DataPointDataType> rdp = null;
        int predictorRowCounter = 0;
        Sheet predictorSheet = null;

        //First row contains the variable
        Sheet responseSheet = responseWorkbook.getSheetAt(0);
        predictorSheet = predictorWorkbook.getSheetAt(0);
        Row responseHeaderRow = responseSheet.getRow(0);
        int responseRowLenght = responseHeaderRow.getLastCellNum();
        Row predictorHeaderRow = predictorSheet.getRow(0);
        int predictorRowLength = predictorHeaderRow.getLastCellNum();
        //Second row can contain the ontology ID (idetified with column name)
        final String ontologyID = "ontology";
        int responseRowCounter = 1;
        predictorRowCounter = 1;
        if (responseSheet.getRow(responseRowCounter).getCell(0).
                getRichStringCellValue().getString().equals(ontologyID)) {
            responseRowCounter++;
            //parse row to db

            //variable, ontologyID
        }
        if (predictorSheet.getRow(predictorRowCounter).getCell(0).
                getRichStringCellValue().getString().equals(ontologyID)) {
            predictorRowCounter++;
            //parse row to DB

            //variable, ontologyID
        }
        //Data (first column genotype, other columns data).
        //parse the rest of the data.
        rdp = new ArrayList<DataPointDataType>();
        for (int i = responseRowCounter;
                i < responseSheet.getLastRowNum(); i++) {

            String trait;
            Double observation;
            String genotype =
                    responseSheet.getRow(i).getCell(0).getStringCellValue();
            for (int j = 1; j < responseRowLenght; j++) {
                if (!responseHeaderRow.getCell(j).getStringCellValue().equals("")) {
                    trait = responseHeaderRow.getCell(j).getStringCellValue();
                    if (responseSheet.getRow(i).getCell(j).getCellType()
                            == Cell.CELL_TYPE_NUMERIC) {
                        try {
                            observation = responseSheet.getRow(i).getCell(j).getNumericCellValue();
                        }
                        catch (Exception e) {
                            observation = Double.NaN;
                        }
                    } else {
                        observation = Double.NaN;
                    }
                    rdp.add(new DataPointDataType(genotype, trait, observation));
                }
            }
        }

        List<DataPointDataType> pdp = new ArrayList<DataPointDataType>();
        for (int i = predictorRowCounter;
                i < predictorSheet.getLastRowNum(); i++) {

            String header;
            Double value;
            String genotype = predictorSheet.getRow(i).getCell(0).getStringCellValue();
            for (int j = 1; j < predictorRowLength; j++) {
                if (!predictorHeaderRow.getCell(j).getStringCellValue().equals("")) {
                    header = predictorHeaderRow.getCell(j).getStringCellValue();
                    if (predictorSheet.getRow(i).getCell(j).getCellType()
                            == Cell.CELL_TYPE_NUMERIC) {
                        try {
                            value = predictorSheet.getRow(i).getCell(j).getNumericCellValue();
                        }
                        catch (Exception e) {
                            value = Double.NaN;
                        }
                    } else {
                        value = Double.NaN;
                    }
                    pdp.add(new DataPointDataType(genotype, header, value));
                }
            }
        }

        SqLiteQueries sql = new SqLiteQueries();
        sql.loadExcelData(rdp, pdp, directory);
    }
}

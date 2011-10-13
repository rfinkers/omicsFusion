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
import java.sql.Time;
import java.util.Timer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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
            String responseType, File predictorExcelFile, String predictorType) {

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
        }
        catch (InvalidFormatException invalidFormatException) {
        }

        //First row contains the variable
        Sheet responseSheet = responseWorkbook.getSheetAt(0);
        Sheet predictorSheet = predictorWorkbook.getSheetAt(0);
        Row responseHeaderRow = responseSheet.getRow(0);
        Row predictorHeaderRow = predictorSheet.getRow(0);
        //Second row can contain the ontology ID (idetified with column name)
        String ontologyID = "ontology";
        int responseRowCounter = 1;
        int predictorRowCounter = 1;
        if (responseSheet.getRow(responseRowCounter).getCell(0).
                getRichStringCellValue().getString().equals(ontologyID)) {
            responseRowCounter++;
            //parse row to db
        }
        if (predictorSheet.getRow(predictorRowCounter).getCell(0).
                getRichStringCellValue().getString().equals(ontologyID)) {
            predictorRowCounter++;
            //parse row to DB
        }
        //Data (first column genotype, other columns data).
        //parse the rest of the data.
        for (int i = responseRowCounter;
                i < responseSheet.getLastRowNum(); i++) {
        }

        for (int i = predictorRowCounter;
                i < predictorSheet.getLastRowNum(); i++) {
        }

        throw new UnsupportedOperationException("Not yet implemented");
    }
}

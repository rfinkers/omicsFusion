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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;

/**
 * Functions to work with excel sheets.
 * @author Richard Finkers
 */
public class ManipulateExcelSheet {

    /** the logger. */
    private static final Logger LOG = Logger.getLogger(
            ManipulateExcelSheet.class.getName());

    /**
     * Extracts the contents of the matrix formatted excel sheets and writes the
     * contents to an list type of excel sheet? Alternatively: leave this to R
     * and use this method to validate the sheet?
     * @param wb An excel workbook
     */
    public void extractCellContents(Workbook wb) {
        //Should not be void?? Write to new style of sheet?
        Sheet sheet1 = wb.getSheetAt(0);
        for (Row row : sheet1) {
            for (Cell cell : row) {
                CellReference cellRef = new CellReference(row.getRowNum(),
                        cell.getColumnIndex());
                LOG.info(cellRef.formatAsString());
                LOG.info(" - ");

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        LOG.info(cell.getRichStringCellValue().
                                getString());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            LOG.info(cell.getDateCellValue().toString());
                        } else {
                            LOG.info(Double.toString(
                                    cell.getNumericCellValue()));
                        }
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        LOG.info(Boolean.toString(cell.getBooleanCellValue()));
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        LOG.info(cell.getCellFormula());
                        break;
                    default:
                        System.out.println();
                }
            }
        }
    }

    /**
     * Load an excel sheet from the file system.
     * @param excelSheet
     * @return The excel sheet which was loaded from the file system.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws InvalidFormatException
     */
    protected static Workbook loadExcelSheet(File excelSheet)
            throws FileNotFoundException, IOException, InvalidFormatException {
        return WorkbookFactory.create(new FileInputStream(excelSheet));
        //Is also check for valid workbook
    }
}

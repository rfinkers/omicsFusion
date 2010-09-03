/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.utils;

import com.csvreader.CsvReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Logger;
import nl.wur.plantbreeding.omicsfusion.datatypes.CsvSummaryDataType;

/**
 * Utility class to read/write CVS file's.
 * @author Richard Finkers
 * @version 1.0
 */
public class CSV {

    private static final Logger LOG = Logger.getLogger(CSV.class.getName());

    /**
     * Read the summary CSV files for a method.
     * @param fileName Name of the result file
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static ArrayList<CsvSummaryDataType> readSummaryCsv(String fileName) throws FileNotFoundException, IOException {
        //FIXME: add check for file?
        ArrayList<CsvSummaryDataType> csvSum = new ArrayList<CsvSummaryDataType>();

        CsvReader reader = new CsvReader(fileName);

        reader.readHeaders();

        CsvSummaryDataType dataPoint;
        while (reader.readRecord()) {
            {
                dataPoint = new CsvSummaryDataType();
                //TODO: add null / other error checking (NotANumberexception etc).
                //TODO: add formating of the numbers
                dataPoint.setResponsVariable(reader.get(""));
                dataPoint.setMean(Double.valueOf(reader.get("means")));
                dataPoint.setSd(Double.valueOf(reader.get("sd")));
                dataPoint.setRank(Double.valueOf(reader.get("ra")));
                csvSum.add(dataPoint);
            }
        }
        reader.close();
        return csvSum;
    }
}

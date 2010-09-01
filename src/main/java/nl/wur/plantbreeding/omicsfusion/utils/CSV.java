/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.utils;

import com.csvreader.CsvReader;
import java.io.FileNotFoundException;
import java.io.IOException;
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

        int i=1;
        CsvSummaryDataType dataPoint;
        while (reader.readRecord()) {
            {
                dataPoint = new CsvSummaryDataType();
                //TODO: add null / other error checking (NotANumberexception etc).
                //TODO: add formating of the numbers
                String variable = reader.get("");
                //System.out.println("Variable: " + variable);
                Double mean = Double.valueOf(reader.get("means"));
                //System.out.println("Mean: " + mean);
                Double sd = Double.valueOf(reader.get("sd"));
                //System.out.println("SD: " + sd);
                Double rank = Double.valueOf(reader.get("ra"));
               // System.out.println("Rank: " + rank);
                System.out.println(i + " Variable: " + variable + " Mean: " + mean + " SD " + sd + " Rank " + rank);
                i++;
                csvSum.add(dataPoint);
            }
        }
        reader.close();
        System.out.println("list: " + csvSum.size() );
        return csvSum;
    }
}

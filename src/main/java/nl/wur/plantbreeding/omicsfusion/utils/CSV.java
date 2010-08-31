/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.utils;

import com.csvreader.CsvReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

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
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void readSummaryCsv(String fileName) throws FileNotFoundException, IOException {
        CsvReader reader = new CsvReader(fileName);

        reader.readHeaders();

        while (reader.readRecord()) {
            {
                String variable = reader.get("variable");
                Double mean = Double.valueOf(reader.get("mean"));
                Double sd = Double.valueOf(reader.get("sd"));
                Integer rank = Integer.valueOf(reader.get("rank"));
            }

            reader.close();
        }

    }
}

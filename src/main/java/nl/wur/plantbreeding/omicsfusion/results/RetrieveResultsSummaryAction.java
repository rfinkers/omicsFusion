/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.results;

import com.csvreader.CsvReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import nl.wur.plantbreeding.logic.util.FileOrDirectoryExists;
import nl.wur.plantbreeding.omicsfusion.datatypes.CsvSummaryDataType;
import nl.wur.plantbreeding.omicsfusion.utils.CSV;

/**
 * Action class that summarizes the results of the pipeline.
 * @author Richard Finkers
 * @version 1.0
 */
public class RetrieveResultsSummaryAction extends RetrieveResultsSummaryValidationForm {

    /** The Logger */
    private static final Logger LOG = Logger.getLogger(RetrieveResultsSummaryAction.class.getName());
    /** Serial Version UID */
    private static final long serialVersionUID = 1L;

    @Override
    public String execute() throws Exception {
        //Check the status of the submitted jobs?
        //Check the availability of the respults file. Just scan for all possible results files?
        HashMap<String, ArrayList<CsvSummaryDataType>> methResults = getMethodsWithResultsSummaryFiles(getSessionId());
        if (methResults.isEmpty()) {
            addActionError("No results found");
            return ERROR;
        }
        //Take the summaries from the CSV file's
        //Calculate the rank and sort acoordingly
        //Create a table (with background colors).
        return SUCCESS;
    }

    private HashMap<String, ArrayList<CsvSummaryDataType>> getMethodsWithResultsSummaryFiles(String sessionID) throws FileNotFoundException, IOException {
        HashMap<String, ArrayList<CsvSummaryDataType>> results = new HashMap<String, ArrayList<CsvSummaryDataType>>();
        String tempDir = "/home/finke002/e125586fcf9ba1b02a33093a2c17/";
        if (FileOrDirectoryExists.FileOrDirectoryExists(tempDir + "LASSO_coef_Sum.csv")) {
            results.put("lasso", CSV.readSummaryCsv(tempDir + "LASSO_coef_Sum.csv"));
        } //Calculate the rank and sort acoordingly
        //Create a table (with background colors).
        return results;
    }

    /**
     * Get the background color for the table according to a gradient of 20
     * colors from white to blue.
     * @param result
     * @param min
     * @param max 
     * @return String containing the color code.
     */
    protected String getBackgroundColor(float result, float min, float max) {
        String background = "#ffffff"; //White is default color.

        float range = max - min;
        float value = result - min;
        range *= 100;
        value *= 100;
        int group = (int) Math.abs(value / (range / 20));
        switch (group) {
            case 1:
                background = "#f3f4f8";
                break;
            case 2:
                background = "#e7eaf0";
                break;
            case 3:
                background = "#dbdfe9";
                break;
            case 4:
                background = "#ced4e1";
                break;
            case 5:
                background = "#c2cada";
                break;
            case 6:
                background = "#b6bfd2";
                break;
            case 7:
                background = "#aab4cb";
                break;
            case 8:
                background = "#9ea9c3";
                break;
            case 9:
                background = "#929fbc";
                break;
            case 10:
                background = "#8694b4";
                break;
            case 11:
                background = "#7989ad";
                break;
            case 12:
                background = "#6d7fa5";
                break;
            case 13:
                background = "#61749e";
                break;
            case 14:
                background = "#556996";
                break;
            case 15:
                background = "#495f8f";
                break;
            case 16:
                background = "#3d5487";
                break;
            case 17:
                background = "#304980";
                break;
            case 18:
                background = "#243e78";
                break;
            case 19:
                background = "#183471";
                break;
            case 20:
                background = "#183471";
                break;
            default:
                background = "#ffffff";
                break;
        }
        return background;
    }
}

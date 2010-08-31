/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.results;

import java.util.logging.Logger;

/**
 * Action class that summarizes the results of the pipeline.
 * @author finke002
 */
public class RetrieveResultsSummaryAction extends RetrieveResultsSummaryValidationForm {

    /** The Logger */
    private static final Logger LOG = Logger.getLogger(RetrieveResultsSummaryAction.class.getName());
    /** Serial Version UID */
    private static final long serialVersionUID = 1L;
    //take the sessionID token
    //Check the status of the submitted jobs?
    //Check the availability of the respults file.
    //Take the summaries from the CSV file's
    //Calculate the rank and sort acoordingly
    //Create a table (with background colors).

    /**
     * Get the background color for the table according to a gradient of 20
     * colors from white to blue.
     * @return String containing the color code.
     */
    private String getBackgroundColor(float result, float min, float max) {
        String background = "#ffffff"; //White is default color.

        float range = max - min;
        float value = result - min;
        range *= 100;
        value *= 100;
        int group = (int) Math.abs(value / (range / 20));
        switch (group) {
            case 1:
                background = "#f3f4f8";
            case 2:
                background = "#e7eaf0";
            case 3:
                background = "#dbdfe9";
            case 4:
                background = "#ced4e1";
            case 5:
                background = "#c2cada";
            case 6:
                background = "#b6bfd2";
            case 7:
                background = "#aab4cb";
            case 8:
                background = "#9ea9c3";
            case 9:
                background = "#929fbc";
            case 10:
                background = "#8694b4";
            case 11:
                background = "#7989ad";
            case 12:
                background = "#6d7fa5";
            case 13:
                background = "#61749e";
            case 14:
                background = "#556996";
            case 15:
                background = "#495f8f";
            case 16:
                background = "#3d5487";
            case 17:
                background = "#304980";
            case 18:
                background = "#243e78";
            case 19:
                background = "#183471";
            default:
                background = "#ffffff";
        }
        return background;
    }
}

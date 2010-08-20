/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.utils;

/**
 * Constants used within the omicsFusion project
 * @author finke002
 */
public class Constants {

    /** user details object name. */
    public static final String USER = "omicsFusionUser";
    /** Minimum number of columns for predictor sheet. */
    public final static int PREDICTOR_COLUMNS = 5;
    /** Minimum number of columns for response sheet. */
    public final static int RESPONSE_COLUMNS = 1;
    /** The total number of iterations on the dataset. */
    public static final int ITERATIONS = 2;//FIXME: 100 instead of 2
    /** outer cross validation (to optimize parameters. */
    public static final int NUMBERFOLDS = 10;

    private Constants() {
    }
}

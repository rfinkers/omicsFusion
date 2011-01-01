package nl.wur.plantbreeding.omicsfusion.utils;

/**
 * Constants used within the omicsFusion project.
 * @author Richard Finkers
 * @version 0.1
 */
public class Constants {

    /** user details object name. */
    public static final String USER = "omicsFusionUser";
    /** Minimum number of columns for predictor sheet. */
    public final static int MIN_PREDICTOR_COLUMNS = 5;
    /** Minimum number of columns for response sheet. */
    public final static int MIN_RESPONSE_COLUMNS = 1;
    /** The total number of iterations on the dataset. */
    public static final int ITERATIONS = 10;//FIXME: set to 100 for final program.
    /** outer cross validation (to optimize parameters). */
    public static final int NUMBER_FOLDS_OUTER = 10;
    /** inner cross validation (to optimize parameters). */
    public static final int NUMBER_FOLDS_INNER = 10;
    /** How many CPU's can be used for one method. */
    public static final int MAX_NUMBER_CPU = 4;
    
    private Constants() {
    }
}

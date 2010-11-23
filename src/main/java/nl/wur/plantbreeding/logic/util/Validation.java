package nl.wur.plantbreeding.logic.util;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Heleen de Weerd
 */
public class Validation {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(Validation.class.getName());

    private Validation() {
    }

    /**
     * Validate if String contains special characters.
     * @param validation
     * @return TRUE if string contains special characters.
     */
    public static boolean containsSpecialCharactersCheck(String validation) {
        if (validation == null) {
            return false;
        }

        Pattern pattern = Pattern.compile("[!@#$%&|\\+?\\*;]");
        Matcher letterMatch = pattern.matcher(validation);
        if (letterMatch.find()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validates int.
     * TODO: int cannot store these characters anyway?
     * @param validation
     * @return TRUE if string contains special characters.
     */
    public static boolean containsSpecialCharactersCheck(int validation) {
        Pattern pattern = Pattern.compile("[!@#$%&|\\+?\\*;\"\']");
        Matcher letterMatch = pattern.matcher(Integer.toString(validation));
        if (letterMatch.find()) {
            LOG.info(Integer.toString(validation));
            return true;
        } else {
            return false;
        }
    }

    /**
     * See if string contains information
     * @param nullString
     * @return string (null or given string)
     */
    public static String nullString(String nullString) {
        Pattern characters = Pattern.compile("[\\S\\W\\D]+");
        Matcher letterMatch = characters.matcher(nullString);
        if (!(letterMatch.find())) {
            return null;
        } else {
            return nullString;
        }
    }

    /**
     * See if float contains letters
     * @param floatValidation
     * @return TRUE if string contains special characters.
     */
    public static boolean floatContainsLetters(String floatValidation) {
        Pattern digits = Pattern.compile("[A-Za-z]+");
        Matcher digitMatch = digits.matcher(floatValidation);
        if (digitMatch.find()) {
            return true;
        } else {
            return false;
        }
    }
}

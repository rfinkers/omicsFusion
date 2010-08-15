/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.excel;

/**
 *
 * @author finke002
 */
public class DataSheetValidationException extends Exception {

    private static final long serialVersionUID = 20100815L;

    /**
     *
     */
    public DataSheetValidationException() {
        super();
    }

    /**
     *
     * @param cause
     */
    public DataSheetValidationException(Throwable cause) {
        super(cause);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public DataSheetValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 
     * @param message
     */
    public DataSheetValidationException(String message) {
        super(message);
    }
}

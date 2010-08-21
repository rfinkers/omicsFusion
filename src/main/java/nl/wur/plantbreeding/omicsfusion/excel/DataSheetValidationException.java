/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.excel;

/**
 * Exception which is thrown when there is an error during the
 * Excel sheet validation.
 * @author Richard Finkers
 * @version 1.0
 */
public class DataSheetValidationException extends Exception {

    private static final long serialVersionUID = 20100815L;

    /**
     * Excel sheet validation exception.
     */
    public DataSheetValidationException() {
        super();
    }

    /**
     * Excel sheet validation exception.
     * @param cause
     */
    public DataSheetValidationException(Throwable cause) {
        super(cause);
    }

    /**
     * Excel sheet validation exception.
     * @param message
     * @param cause
     */
    public DataSheetValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Excel sheet validation exception.
     * @param message
     */
    public DataSheetValidationException(String message) {
        super(message);
    }
}

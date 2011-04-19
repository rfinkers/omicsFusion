/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.run.status;

import com.opensymphony.xwork2.ActionSupport;
import java.util.logging.Logger;
import nl.wur.plantbreeding.logic.util.Validation;

/**
 * Validates the SessionID within the run status validation scripts.
 * @author Richard Finkers
 */
public class RunStatusValidationForm extends ActionSupport {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(
            RunStatusValidationForm.class.getName());
    /** Serial Version UID */
    private static final long serialVersionUID = 1L;
    /** session token */
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId.trim();
    }

    @Override
    public void validate() {
        System.out.println("Validate");
        if (Validation.containsSpecialCharactersCheck(sessionId) == true) {
            //do something like throwing an error
            System.out.println("TRUE");
        }
        //TODO: we expect a fixed length for the sessionID Include a length check
    }
}

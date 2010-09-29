/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.results;

import com.opensymphony.xwork2.ActionSupport;
import java.util.logging.Logger;
import nl.wur.plantbreeding.logic.util.Validation;

/**
 *
 * @author finke002
 */
public class RetrieveResultsSummaryValidationForm extends ActionSupport {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(RetrieveResultsSummaryValidationForm.class.getName());
    /** Serial Version UID */
    private static final long serialVersionUID = 1L;
    /** session token */
    private String sessionId;
    /** The response variable */
    private String variable;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId.trim();
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    @Override
    public void validate() {
        LOG.info("Validate");
        if (Validation.containsSpecialCharactersCheck(sessionId) == true) {
            //do something like throwing an error
           LOG.info("TRUE");
        }
        //TODO: we expect a fixed length for the sessionID Include a length check
        //TODO: null check
    }
}

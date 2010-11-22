/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.results;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Show a XY scatterplot for a predictor / response variable. This form contains the variables.
 * @author Richard Finkers
 * @version 1.0
 */
public class PredictorResponseXYScatterForm extends ActionSupport {

    /** The logger */
    //private static final Logger LOG = Logger.getLogger(PredictorResponseXYScatterForm.class.getName());
    /** Serial Version UID */
    private static final long serialVersionUID = 100906L;

    /** session token */
    private String sessionId;
    /** predictor */
    private String predictor;
    /** response variable */
    private String response;

    public String getPredictor() {
        return predictor;
    }

    public void setPredictor(String predictor) {
        this.predictor = predictor.trim();
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response.trim();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId.trim();
    }

    @Override
    public void validate() {
        LOG.info("sessionID: {0}", getSessionId());
        LOG.info("Predictor: {0}", getPredictor());
        LOG.info("Response : {0}", getResponse());
//        if (getSessionId() == null) {
//            addActionError("Add a valid sessionID");
//        }
    }
}

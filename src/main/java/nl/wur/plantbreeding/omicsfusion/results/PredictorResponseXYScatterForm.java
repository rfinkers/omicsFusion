/*
 * Copyright 2011 omicstools.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.wur.plantbreeding.omicsfusion.results;

import com.opensymphony.xwork2.ActionSupport;
import java.util.logging.Logger;

/**
 * Show a XY scatterplot for a predictor / response variable. This form contains
 * the variables.
 *
 * @author Richard Finkers
 * @version 1.0
 */
public class PredictorResponseXYScatterForm extends ActionSupport {
    //TODO: can we use this form while using request parameters? See BreeDB code.

    /**
     * The logger
     */
    protected static final Logger LOG = Logger.getLogger(
            PredictorResponseXYScatterForm.class.getName());
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 100906L;
    /**
     * session token
     */
    private String session;
//    /** predictor */
//    private String predictor;
//    /** response variable */
//    private String response;
//
//    public String getPredictor() {
//        return predictor;
//    }
//
//    public void setPredictor(String predictor) {
//        this.predictor = predictor.trim();
//    }
//
//    public String getResponse() {
//        return response;
//    }
////
//    public void setResponse(String response) {
//        this.response = response.trim();
//    }

    public String getSession() {
        return session;
    }
//

    public void setSession(String sessionId) {
        this.session = sessionId.trim();
    }

    @Override
    public void validate() {
//        LOG.log(Level.INFO, "sessionID: {0}", getSession());
//        LOG.log(Level.INFO, "Predictor: {0}", getPredictor());
//        LOG.log(Level.INFO, "Response : {0}", getResponse());
//        if (getSessionId() == null) {
//            addActionError("Add a valid sessionID");
//        }
    }
}

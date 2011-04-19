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
import nl.wur.plantbreeding.logic.util.Validation;

/**
 *
 * @author Richard Finkers
 */
public class RetrieveResultsSummaryValidationForm extends ActionSupport {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(
            RetrieveResultsSummaryValidationForm.class.getName());
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

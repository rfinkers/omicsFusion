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
package nl.wur.plantbreeding.omicsfusion.wizard;

import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Action which handles upload of the data sheets and validates the if they are
 * conform the requried standards. Data will be stored in a database?
 *
 * @author Richard Finkers
 * @version 1.0
 */
public class ProcedureSelectionAction extends DataUploadValidationForm
        implements ServletRequestAware {

    private static final long serialVersionUID = 170610L;
    /**
     * The logger.
     */
    private static final Logger LOG
            = Logger.getLogger(ProcedureSelectionAction.class.getName());
    /**
     * the request.
     */
    private HttpServletRequest request;

    @Override
    public String execute() throws Exception {

        return SUCCESS;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}

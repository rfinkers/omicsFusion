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

import com.opensymphony.xwork2.ActionSupport;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Reset the session before starting the upload wizard.
 *
 * @author Richard Finkers
 */
public class StartSubmitWizardAction extends ActionSupport
        implements ServletRequestAware {

    /**
     * SUID
     */
    private static final long serialVersionUID = 050111L;
    /**
     * The logger
     */
    private static final Logger LOG
            = Logger.getLogger(StartSubmitWizardAction.class.getName());
    /**
     * the request
     */
    private HttpServletRequest request;

    @Override
    public String execute() throws Exception {
        request.getSession().invalidate();
        return SUCCESS;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}

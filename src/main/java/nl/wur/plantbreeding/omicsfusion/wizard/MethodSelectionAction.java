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

import java.util.ArrayList;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import nl.wur.plantbreeding.logic.sqlite.SqLiteQueries;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;
import nl.wur.plantbreeding.omicsfusion.utils.ServletUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Process the selected methods and make them available on the session scope
 * under the variable methods.
 *
 * @author Richard Finkers
 * @version 1.0
 */
public class MethodSelectionAction extends MethodSelectionValidationForm
        implements ServletRequestAware {

    private static final long serialVersionUID = 180610L;
    /**
     * The logger.
     */
    private static final Logger LOG
            = Logger.getLogger(MethodSelectionAction.class.getName());
    /**
     * the request.
     */
    private HttpServletRequest request;

    @Override
    public String execute() throws Exception {

        ArrayList<String> methods = new ArrayList<>();

        if (isRidge() == true) {
            methods.add(Constants.RIDGE);
        }
        if (isElasticNet() == true) {
            methods.add(Constants.EN);
        }
        if (isRf() == true) {
            methods.add(Constants.RF);
        }
        if (isSvm() == true) {
            methods.add(Constants.SVM);
        }
        if (isPcr() == true) {
            methods.add(Constants.PCR);
        }
        if (isPls() == true) {
            methods.add(Constants.PLS);
        }
        if (isSpls() == true) {
            methods.add(Constants.SPLS);
        }
        if (isLasso() == true) {
            methods.add(Constants.LASSO);
        }
        if (isUnivariate() == true) {
            methods.add(Constants.UNIVARIATE);
        }

        SqLiteQueries sql = new SqLiteQueries();
        sql.addMethods(ServletUtils.getResultsDir(request), methods);

        request.getSession().setAttribute(Constants.SelectedMethods, methods);

        return SUCCESS;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}

/*
 * Copyright 2011 omicstools.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package nl.wur.plantbreeding.omicsfusion.wizard;

import com.opensymphony.xwork2.validator.annotations.ExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import nl.wur.plantbreeding.logic.sqlite.SqLiteQueries;
import nl.wur.plantbreeding.omicsfusion.email.ExceptionEmail;
import nl.wur.plantbreeding.omicsfusion.entities.UserList;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;
import nl.wur.plantbreeding.omicsfusion.utils.ServletUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * process the user information and makes it available on the session scope.
 *
 * @author Richard Finkers
 * @version 2.0
 */
@ParentPackage(value = "userDetails")
@InterceptorRef("jsonValidationWorkflowStack")
@Results({
    @Result(location = "/submit/userDetails.jsp", name = "input"),
    @Result(location = "/submit/uploadExcel.jsp", name = "success"),
    @Result(location = "/submit/userDetails.jsp", name = "error")
})
@Validations(requiredStrings = {
    @RequiredStringValidator(fieldName = "name",
            type = ValidatorType.FIELD, message = "Please enter your name"),
    @RequiredStringValidator(fieldName = "email",
            type = ValidatorType.FIELD, message = "Please enter your email")
}, expressions = {
    @ExpressionValidator(expression = "name.trim().length() > 5",
            message = "Password must have as minimum 6 Characters.")
}
        //        ,fieldExpressions = {
        //    @FieldExpressionValidator(fieldName = "name",
        //            expression = "name.trim().length() > 6",
        //            message = "Password must have as minimum 6 Characters."),
        //    @FieldExpressionValidator(fieldName = "email",
        //            expression = "email.trim().length() > 6",
        //            message = "Accept the Agreement.")
        //}
        )
public class UserDetailsAction extends UserDetailsValidationForm
        implements ServletRequestAware {

    private static final long serialVersionUID = 170610L;
    /**
     * the request
     */
    private HttpServletRequest request;
    /**
     * The logger
     */
    private static final Logger LOG
            = Logger.getLogger(UserDetailsAction.class.getName());

    @Override
    public String execute() throws Exception {
        LOG.log(Level.INFO, "Action: user details completed: {0}", getEmail());

        UserList user = new UserList();
        try {
            //TODO: get the user ID? or just before persisting the object?
            //TODO: finish UserDetailsAction-validation.xml add validation to all.

            user.setUserName(getName());
            user.setEmail(getEmail());
            user.setAffiliation(getAffiliation());
            user.setCountry(getCountry());

            LOG.info(user.toString());

            request.getSession().setAttribute(Constants.USER, user);

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Exception: {0}", ex.getMessage());
            addActionError("An exception occured");
            ex.printStackTrace();
            ExceptionEmail.SendExceptionEmail(ex);
            return ERROR;
        }

        try {
            SqLiteQueries sq = new SqLiteQueries();
            sq.initializeDatabase(ServletUtils.getResultsDir(
                    request.getSession()));
            SqLiteQueries sq2 = new SqLiteQueries();
            sq2.addUser(ServletUtils.getResultsDir(
                    request.getSession()), user);
        } catch (SQLException sQLiteException) {
            LOG.log(Level.SEVERE, "Exception: {0}",
                    sQLiteException.getMessage());
            addActionError("SQLite exception: " + sQLiteException.getMessage());
            /**
             * FIXME: this error gets thrown when the "omicsFusion" data dir
             * does not exists. We have tho handle that differently.
             */
            sQLiteException.printStackTrace();
            return ERROR;
        }

        return SUCCESS;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}

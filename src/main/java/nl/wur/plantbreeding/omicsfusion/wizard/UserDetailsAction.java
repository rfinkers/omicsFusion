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

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.wur.plantbreeding.omicsfusion.email.ExceptionEmail;
import nl.wur.plantbreeding.omicsfusion.entities.UserList;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;
import org.apache.struts2.interceptor.SessionAware;

/**
 * process the user information and makes it available on the session scope.
 * @author Richard Finkers
 * @version 1.0
 */
public class UserDetailsAction extends UserDetailsValidationForm implements SessionAware {

    private static final long serialVersionUID = 170610L;
    /** the session */
    private Map<String, Object> session;
    /** The logger */
    private static final Logger LOG = Logger.getLogger(UserDetailsAction.class.getName());

    @Override
    public String execute() throws Exception {
        LOG.log(Level.INFO, "Action: user details completed: {0}", getEmail());
        try {
            //TODO: get the user ID? or just before persisting the object?
            //TODO: finish UserDetailsAction-validation.xml add validation to all.
            UserList user = new UserList();
            user.setUserName(getName());
            user.setEmail(getEmail());
            user.setAffiliation(getAffiliation());
            user.setCountry(getCounty());

            LOG.info(user.toString());//TODO: remove debug code

            session.put(Constants.USER, user);

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Exception: {0}", ex.getMessage());
            addActionError("An exceoption occured");
            ex.printStackTrace();
            ExceptionEmail.SendExceptionEmail(ex);
            return ERROR;
        }
        return SUCCESS;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}

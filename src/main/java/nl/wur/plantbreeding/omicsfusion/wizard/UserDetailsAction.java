/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.wizard;

import java.util.Map;
import nl.wur.plantbreeding.omicsfusion.entities.UserList;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author Richard Finkers
 */
public class UserDetailsAction extends UserDetailsValidationForm implements SessionAware {

    private static final long serialVersionUID = 170610L;
    /** the session */
    private Map<String, Object> session;

    @Override
    public String execute() throws Exception {
        getLOG().info("Action: user details completed: " + getEmail());//TODO: remove debug code
        try {
            //TODO: get the user ID? or just before persisting the object?
            //TODO: finish UserDetailsAction-validation.xml add validation to all.
            UserList user = new UserList();
            user.setUserName(getName());
            user.setEmail(getEmail());
            user.setAffiliation(getAffiliation());
            user.setCountry(getCounty());

            System.out.println(user.toString());//TODO: remove debug code

            session.put(Constants.USER, user);

        } catch (Exception ex) {
            System.out.println("Exception ");
            ex.printStackTrace();
        }
        return SUCCESS;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}

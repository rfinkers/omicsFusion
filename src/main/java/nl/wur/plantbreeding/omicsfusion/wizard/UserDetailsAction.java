package nl.wur.plantbreeding.omicsfusion.wizard;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.wur.plantbreeding.omicsfusion.entities.UserList;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;
import org.apache.struts2.interceptor.SessionAware;

/**
 * Proces the user information and makes it available on the session scope.
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
            ex.printStackTrace();
        }
        return SUCCESS;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}

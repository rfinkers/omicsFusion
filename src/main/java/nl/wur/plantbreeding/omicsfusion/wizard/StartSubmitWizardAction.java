/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.wizard;

import com.opensymphony.xwork2.ActionSupport;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Reset the session before starting the upload wizard.
 * @author Richard Finkers
 */
public class StartSubmitWizardAction extends ActionSupport implements ServletRequestAware {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(StartSubmitWizardAction.class.getName());
    /** the request */
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

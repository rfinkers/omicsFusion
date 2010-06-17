/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.wizard;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Richard Finkers
 */
public class UserDetailsAction extends UserDetailsValidationForm {

    private static final long serialVersionUID = 170610L;

    /** get the httpservletrequest.
     * @return the HttpServletRequest.
     */
    public HttpServletRequest getServletRequest() {
        return ServletActionContext.getRequest();
    }

    /** Get the servlet context.
     * @return the ServletContext.
     */
    public ServletContext getServletContext() {
        return ServletActionContext.getServletContext();
    }

    @Override
    public String execute() throws Exception {
        System.out.println("Test completed");
        return SUCCESS;
    }
}

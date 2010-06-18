/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author finke002
 */
public class ServletUtils {

    private ServletUtils() {
    }

    /** get the httpservletrequest.
     * @return the HttpServletRequest.
     */
    public static HttpServletRequest getServletRequest() {
        return ServletActionContext.getRequest();
    }

    /** Get the servlet context.
     * @return the ServletContext.
     */
    public static ServletContext getServletContext() {
        return ServletActionContext.getServletContext();
    }

}

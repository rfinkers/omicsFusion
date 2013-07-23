/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package nl.wur.plantbreeding.omicsfusion.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 * Generic methods to use the request / context.
 *
 * @author Richard Finkers.
 * @version 1.0
 */
public class ServletUtils {

    private ServletUtils() {
    }

    /**
     * get the httpservletrequest.
     *
     * @return the HttpServletRequest.
     */
    public static HttpServletRequest getServletRequest() {
        return ServletActionContext.getRequest();
    }

    /**
     * Get the servlet context.
     *
     * @return the ServletContext.
     */
    public static ServletContext getServletContext() {
        return ServletActionContext.getServletContext();
    }

    /**
     * Get the results directory, including the current session directory, as
     * configured in the context.
     *
     * @param request The current HTTP request.
     * @return the location of the result directory (including slash or
     * backslash).
     */
    public static String getResultsDir(HttpServletRequest request) {
        return getResultsDir(request.getSession());
    }

    /**
     * Get the results directory, including the requested session directory, as
     * configured in the context.
     *
     * @param request The current HTTP request.
     * @param sessionID The entered session.
     * @return the location of the result directory (including slash or
     * backslash).
     */
    public static String getResultsDir(
            HttpServletRequest request, String sessionID) {
        String resultsDirectory = request.getSession().getServletContext().getInitParameter("resultsDirectory");
        if (!(resultsDirectory.endsWith("/")
                || resultsDirectory.endsWith("\\"))) {
            resultsDirectory += System.getProperty("file.separator");
        }
        resultsDirectory += sessionID;

        return resultsDirectory;
    }

    /**
     * Get the results directory, including the current session directory, as
     * configured in the context.
     *
     * @param session The current HTTP session.
     * @return the location of the result directory (including slash or
     * backslash).
     */
    public static String getResultsDir(HttpSession session) {
        //String resultsDirectory = System.getProperty("java.io.tmpdir");
        String resultsDirectory = session.getServletContext().getInitParameter("resultsDirectory");
        if (!(resultsDirectory.endsWith("/")
                || resultsDirectory.endsWith("\\"))) {
            resultsDirectory += System.getProperty("file.separator");
        }
        resultsDirectory += session.getId();

        return resultsDirectory;
    }
}

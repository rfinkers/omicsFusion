/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.results;

import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Check for which methods results are available. This action results in an page
 * that allows further investigation of more detailed methods results.
 * @author Richard Finkers
 * @version 1.0
 */
public class MethodsWithResultsAction extends MethodsWithResultsForm implements ServletRequestAware {

    /** the request */
    private HttpServletRequest request;
    /** The logger */
    private static final Logger LOG = Logger.getLogger(MethodsWithResultsAction.class.getName());

    //TODO: Shares method with retrieveSummaryAction. Refractor to have one results utility class?
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
}

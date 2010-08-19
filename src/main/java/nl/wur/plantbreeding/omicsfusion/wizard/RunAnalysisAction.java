/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.wizard;

import com.opensymphony.xwork2.ActionSupport;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import nl.wur.plantbreeding.omicsfusion.methods.ElasticNet;
import nl.wur.plantbreeding.omicsfusion.methods.Univariate;
import nl.wur.plantbreeding.omicsfusion.utils.CmdExec;
import nl.wur.plantbreeding.omicsfusion.utils.WriteFile;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Runs the selected analysis methods on the
 * @author Richard Finkers
 */
public class RunAnalysisAction extends ActionSupport implements ServletRequestAware {

    /** Serial Version UID */
    private static final long serialVersionUID = 20100815L;
    /** The logger */
    private static final Logger LOG = Logger.getLogger(RunAnalysisAction.class.getName());
    /** the request */
    private HttpServletRequest request;

    @Override
    public String execute() throws Exception {
        //Read the selected methods

        System.out.println("load univariate");
        //Create the relevant run scripts
        @SuppressWarnings("unchecked")
        HashMap<String, String> sheets = (HashMap<String, String>) getRequest().getSession().getAttribute("sheets");

        System.out.println("Response: " + sheets.get("response"));
        System.out.println("Predictor: " + sheets.get("predictor"));
        //split up in test cases first? <-reusable for all others (as testCase.RData)?

        //TODO: slowest methods first
        if ("test".equals("test")) {
            //do something
        }

        ElasticNet en = new ElasticNet();
        String elasticNet = en.getAnalysisScript(sheets);

        Univariate uv = new Univariate();//FIXME: only Univariate during initial testing phase.
        String script = uv.analysisisRScript("/tmp/" + getRequest().getSession().getId());

        System.out.println("call writefile");
        WriteFile wf = new WriteFile();
        System.out.println("use writefile");
        wf.WriteFile("/tmp/" + getRequest().getSession().getId() + "/elasticNet.R", elasticNet);
        wf.WriteFile("/tmp/" + getRequest().getSession().getId() + "/univariate.R", script);
        LOG.info("Univariate script written to disk");
        wf.WriteFile("/tmp/" + getRequest().getSession().getId() + "/batch.sh",
                "#!/bin/sh\ncd /tmp/" + getRequest().getSession().getId() + "\nR --no-save < univariate.R");
        LOG.info("Writing invocation script");

        //Submit jobs to the SGE QUEUE
        int jobId = CmdExec.CmdExec("/tmp/" + getRequest().getSession().getId() + "/");
        //TODO: get the logId's. Handle logId=0 error in case of problem? Store the logId's in a database or textfile?
        if (jobId == 0) {
            LOG.severe("error during submission");
        } else {
            LOG.info("Subitted to que with jobId: " + jobId);
        }
        //Submit successfull Message?

        //Info on identifyer for job status
        System.out.println("Running Analysis");


        //Send out the email with details
        //Session invalidate and forward last details via request
        return SUCCESS;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.setRequest(request);
    }

    /**
     * @return the request
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}

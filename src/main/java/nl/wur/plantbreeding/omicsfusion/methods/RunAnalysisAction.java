/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.methods;

import com.opensymphony.xwork2.ActionSupport;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
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
    HttpServletRequest request;

    @Override
    public String execute() throws Exception {
        //Read the selected methods

        System.out.println("load univariate");
        //Create the relevant run scripts
        Univariate uv = new Univariate();//FIXME: only Univariate during initial testing phase.
        String script = uv.analysisisRScript("/tmp/" + request.getSession().getId());

        System.out.println("call writefile");
        WriteFile wf = new WriteFile();
        System.out.println("use writefile");
        wf.WriteFile("/tmp/" + request.getSession().getId() + "/univariate.R", script);
        LOG.info("Univariate script written to disk");
        wf.WriteFile("/tmp/" + request.getSession().getId() + "/batch.sh", 
                "#!/bin/sh\ncd /tmp/" + request.getSession().getId() + "\nR --no-save < univariate.R");

        //Submit jobs to the SGE QUEUE
        CmdExec.CmdExec("/tmp/" + request.getSession().getId()+ "/");

        //Submit successfull Message?

        //Info on identifyer for job status
        System.out.println("Running Analysis");


        return SUCCESS;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}

package nl.wur.plantbreeding.omicsfusion.wizard;

import com.opensymphony.xwork2.ActionSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import nl.wur.plantbreeding.omicsfusion.methods.ElasticNet;
import nl.wur.plantbreeding.omicsfusion.methods.Lasso;
import nl.wur.plantbreeding.omicsfusion.methods.PCR;
import nl.wur.plantbreeding.omicsfusion.methods.PartialLeasedSquares;
import nl.wur.plantbreeding.omicsfusion.methods.RandomForest;
import nl.wur.plantbreeding.omicsfusion.methods.Ridge;
import nl.wur.plantbreeding.omicsfusion.methods.SVM;
import nl.wur.plantbreeding.omicsfusion.methods.SparsePLS;
import nl.wur.plantbreeding.omicsfusion.methods.Univariate;
import nl.wur.plantbreeding.omicsfusion.utils.CmdExec;
import nl.wur.plantbreeding.omicsfusion.utils.WriteFile;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Creates the SGE compatible R scripts for the selected methods. The scripts
 * will be submitted to the DEFAULT SGE queue.
 * @author Richard Finkers
 * @version 1.0
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
        //Read the relevant data from the session
        @SuppressWarnings("unchecked")
        HashMap<String, String> sheets = (HashMap<String, String>) getRequest().getSession().getAttribute("sheets");
        @SuppressWarnings("unchecked")
        ArrayList<String> methods = (ArrayList<String>) getRequest().getSession().getAttribute("methods");

        //we want to write text files.

        //Get the list with jobId's from SGE
         ArrayList<Integer> jobIds = new ArrayList<Integer>();

        //which methods to run?
        for (String method : methods) {
            if (method.equals("ridge")) {
                Ridge mth = new Ridge();
                String mthString = mth.getAnalysisScript(sheets);
                writeScriptFile("ridge.R", mthString);
                jobIds.add(submitToSGE("ridge"));
            } else if (method.equals("en")) {
                ElasticNet mth = new ElasticNet();
                String mthString = mth.getAnalysisScript(sheets);
                writeScriptFile("en.R", mthString);
                jobIds.add(submitToSGE("en"));
            } else if (method.equals("rf")) {
                RandomForest mth = new RandomForest();
                String mthString = mth.getAnalysisScript(sheets);
                writeScriptFile("rf.R", mthString);
                jobIds.add(submitToSGE("rf"));
            } else if (method.equals("svm")) {
                SVM mth = new SVM();
                String mthString = mth.getAnalysisScript(sheets);
                writeScriptFile("svm.R", mthString);
                jobIds.add(submitToSGE("svm"));
            } else if (method.equals("pcr")) {
                PCR mth = new PCR();
                String mthString = mth.getAnalysisScript(sheets);
                writeScriptFile("pcr.R", mthString);
                jobIds.add(submitToSGE("pcr"));
            } else if (method.equals("pls")) {
                PartialLeasedSquares mth = new PartialLeasedSquares();
                String mthString = mth.getAnalysisScript(sheets);
                writeScriptFile("pls.R", mthString);
                jobIds.add(submitToSGE("pls"));
            } else if (method.equals("spls")) {
                SparsePLS mth = new SparsePLS();
                String mthString = mth.getAnalysisScript(sheets);
                writeScriptFile("spls.R", mthString);
                jobIds.add(submitToSGE("spls"));
            } else if (method.equals("lasso")) {
                Lasso mth = new Lasso();
                String mthString = mth.getAnalysisScript(sheets);
                writeScriptFile("lasso.R", mthString);
                jobIds.add(submitToSGE("lasso"));
            } else if (method.equals("univariate")) {
                Univariate mth = new Univariate();
                String mthString = mth.getAnalysisScript(sheets);
                writeScriptFile("univariate.R", mthString);
                jobIds.add(submitToSGE("univariate"));
            }
        }

        LOG.log(Level.INFO, "Submitted {0} jobs to SGE.", jobIds.size());

        //Send out the email with details
        //Session invalidate and forward last details via request
        return SUCCESS;
    }

    /**
     * Write the R analysis script to the filesystem.
     * @param scriptName Name of the script file.
     * @param script Contents of the script.
     * @throws IOException When writing to disk fails.
     */
    public void writeScriptFile(String scriptName, String script) throws IOException {
        WriteFile wf = new WriteFile();
        wf.WriteFile("/tmp/" + getRequest().getSession().getId() + "/" + scriptName, script);
    }

    /**
     * Create the SGE submission script and submit the job to SGE.
     * @param scriptName Name of the scripts.
     * @return The jobID.
     * @throws IOException When writing to disk fails.
     */
    public int submitToSGE(String scriptName) throws IOException {
        WriteFile wf = new WriteFile();
        wf.WriteFile("/tmp/" + getRequest().getSession().getId() + "/" + scriptName + ".sh",
                "#!/bin/sh\ncd /tmp/" + getRequest().getSession().getId() + "\nR --no-save < " + scriptName + ".R");
        //Submit jobs to the SGE QUEUE
        int jobId = CmdExec.CmdExec("/tmp/" + getRequest().getSession().getId() + "/");
        if (jobId == 0) {
            LOG.severe("error during submission");//TODO: implement exception?
        } else {
            LOG.log(Level.INFO, "Subitted to que with jobId: {0}", jobId);
        }
        return jobId;
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

/*
 * Copyright 2011 omicstools.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.wur.plantbreeding.omicsfusion.wizard;

import static nl.wur.plantbreeding.omicsfusion.email.SubmissionCompleteEmail.SubmissionCompleteEmail;
import com.opensymphony.xwork2.ActionSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import nl.wur.plantbreeding.omicsfusion.email.ExceptionEmail;
import nl.wur.plantbreeding.omicsfusion.methods.ElasticNet;
import nl.wur.plantbreeding.omicsfusion.methods.Lasso;
import nl.wur.plantbreeding.omicsfusion.methods.PCR;
import nl.wur.plantbreeding.omicsfusion.methods.PartialLeasedSquares;
import nl.wur.plantbreeding.omicsfusion.methods.RSessionInfo;
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
public class RunAnalysisAction extends ActionSupport
        implements ServletRequestAware {

    /** Serial Version UID. */
    private static final long serialVersionUID = 20100815L;
    /** The logger. */
    private static final Logger LOG = Logger.getLogger(
            RunAnalysisAction.class.getName());
    /** the request. */
    private HttpServletRequest request;

    @Override
    public String execute() throws Exception {
        //Read the relevant data from the session
        @SuppressWarnings("unchecked")
        HashMap<String, String> sheets =
                (HashMap<String, String>) getRequest().getSession().
                getAttribute("sheets");
        @SuppressWarnings("unchecked")
        ArrayList<String> methods =
                (ArrayList<String>) getRequest().getSession().
                getAttribute("methods");

        //we want to write text files.

        //Get the list with jobId's from SGE
        ArrayList<Integer> jobIds = new ArrayList<Integer>();

        //which methods to run?
        //Order here is equal to the order on the SGE submission queue? 
        //Schedule slow jobs first! Or can should this be controlled via order 
        //in submission screen / orther ordening options / queue weight?
        //Relative timings on the CxE Flesh color /Metabolite dataset.

        try {
            //RF - 50 /SPLS - 34 /Ridge - 32 /EN - 8 /SVM - 5 /PCR - 2 /PLS - 1 /LASSO - 1
            for (String method : methods) {
                if (method.equals("rf")) {
                    RandomForest mth = new RandomForest();
                    String mthString = mth.getAnalysisScript(sheets);
                    writeScriptFile("rf.R", mthString);
                    jobIds.add(submitToSGE("rf"));
                } else if (method.equals("spls")) {
                    SparsePLS mth = new SparsePLS();
                    String mthString = mth.getAnalysisScript(sheets);
                    writeScriptFile("spls.R", mthString);
                    jobIds.add(submitToSGE("spls"));
                } else if (method.equals("ridge")) {
                    Ridge mth = new Ridge();
                    String mthString = mth.getAnalysisScript(sheets);
                    writeScriptFile(method + ".R", mthString);
                    int job = submitToSGE(method);
                    if (job != 0) {
                        jobIds.add(job);
                    }
                } else if (method.equals("en")) {
                    ElasticNet mth = new ElasticNet();
                    String mthString = mth.getAnalysisScript(sheets);
                    writeScriptFile("en.R", mthString);
                    jobIds.add(submitToSGE("en"));
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
            //Always run the RSessionInfo job
            RSessionInfo rsi = new RSessionInfo();
            String mthString = rsi.getAnalysisScript(sheets);
            writeScriptFile("sessionInfo.R", mthString);
            jobIds.add(submitToSGE("sessionInfo"));

        }
        catch (IOException e) {
            addActionError("qsub not found, please check your SGE configuration");//TODO: check
            return ERROR;//TODO: configure
        }
        // TODO: log userId or sessionID as primary key? / method / jobId / startTime / sessionId / (finishTime <- status update daemon).

        LOG.log(Level.INFO, "Submitted {0} jobs to SGE.", jobIds.size());

        //Send out the email with details
        try {
            // TODO: add user details and sessionID to constructor
            SubmissionCompleteEmail();
        }
        catch (Exception e) {
            LOG.log(Level.WARNING, "exception on sending confirmation email");
            e.printStackTrace();
            ExceptionEmail.SendExceptionEmail(e);
            return ERROR;
        }

        //Session invalidate and forward last details via request. Moved to the submit
        return SUCCESS;
    }

    /**
     * Write the R analysis script to the file system.
     * @param scriptName Name of the script file.
     * @param script Contents of the script.
     * @throws IOException When writing to disk fails.
     */
    public void writeScriptFile(String scriptName, String script)
            throws IOException {
        WriteFile wf = new WriteFile();
        wf.WriteFile(getResultsDir() + getRequest().getSession().getId()
                + "/" + scriptName, script);
    }

    /**
     * Create the SGE submission script and submit the job to SGE.
     * @param scriptName Name of the scripts.
     * @return The jobID.
     * @throws IOException When writing to disk fails.
     */
    public int submitToSGE(String scriptName) throws IOException {
        WriteFile wf = new WriteFile();
        wf.WriteFile(getResultsDir() + getRequest().getSession().getId()
                + "/" + scriptName + ".pbs",
                "#!/bin/sh\ncd " + getResultsDir()
                + getRequest().getSession().getId() + "\nR --no-save < "
                + scriptName + ".R\n");
        //Submit jobs to the SGE QUEUE
        int jobId = 0;
        String queue =
                request.getSession().getServletContext().getInitParameter("SGEQueue");
        jobId = CmdExec.ExecuteQSubCmd(getResultsDir()
                + getRequest().getSession().getId() + "/", scriptName, queue);

        if (jobId == 0) {
            LOG.severe("error during submission");//TODO: implement exception? Also is thrown when a wrong queue name is selected
        } else {
            LOG.log(Level.INFO, "Subitted to que with jobId: {0}", jobId);
        }
        return jobId;
    }

    /**
     * Get the temp directory from the system.
     * @return the location of the temp directory (including slash or backslash).
     */
    private String getResultsDir() {
        //String resultsDirectory = System.getProperty("java.io.tmpdir");
        String resultsDirectory =
                request.getSession().getServletContext().getInitParameter("resultsDirectory");
        if (!( resultsDirectory.endsWith("/") || resultsDirectory.endsWith("\\") )) {
            resultsDirectory += System.getProperty("file.separator");
        }
        return resultsDirectory;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.setRequest(request);
    }

    /**
     * @return the request
     */
    private HttpServletRequest getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}

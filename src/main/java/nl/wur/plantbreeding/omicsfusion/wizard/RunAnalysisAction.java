/*
 * Copyright 2011 omicstools.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package nl.wur.plantbreeding.omicsfusion.wizard;

import com.opensymphony.xwork2.ActionSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import nl.wur.plantbreeding.logic.sqlite4java.SqLiteQueries;
import nl.wur.plantbreeding.omicsfusion.email.ExceptionEmail;
import static nl.wur.plantbreeding.omicsfusion.email.SubmissionCompleteEmail.SubmissionCompleteEmail;
import nl.wur.plantbreeding.omicsfusion.methods.*;
import nl.wur.plantbreeding.omicsfusion.utils.CmdExec;
import nl.wur.plantbreeding.omicsfusion.utils.ServletUtils;
import nl.wur.plantbreeding.omicsfusion.utils.WriteFile;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Creates the SGE compatible R scripts for the selected methods. The scripts
 * will be submitted to the DEFAULT SGE queue.
 *
 * @author Richard Finkers
 * @version 1.0
 */
public class RunAnalysisAction extends ActionSupport
        implements ServletRequestAware {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 20100815L;
    /**
     * The logger.
     */
    private static final Logger LOG = Logger.getLogger(
            RunAnalysisAction.class.getName());
    /**
     * the request.
     */
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
        HashMap<String, Integer> jobIds = new HashMap<String, Integer>();

        //which methods to run?
        //Order here is equal to the order on the SGE submission queue?
        //Schedule slow jobs first! Or can should this be controlled via order
        //in submission screen / orther ordening options / queue weight?
        //Relative timings on the CxE Flesh color /Metabolite dataset.

        try {
            //Relative time: RF - 50 /SPLS - 34 /Ridge - 32 /EN - 8
            //SVM - 5 /PCR - 2 /PLS - 1 /LASSO - 1
            for (String method : methods) {
                if (method.equals("rf")) {
                    RandomForest mth = new RandomForest();
                    String mthString = mth.getAnalysisScript(sheets);
                    writeScriptFile("rf.R", mthString);
                    jobIds.put("rf", submitToSGE("rf"));
                } else if (method.equals("spls")) {
                    SparsePLS mth = new SparsePLS();
                    String mthString = mth.getAnalysisScript(sheets);
                    writeScriptFile("spls.R", mthString);
                    jobIds.put("spls", submitToSGE("spls"));
                } else if (method.equals("ridge")) {
                    Ridge mth = new Ridge();
                    String mthString = mth.getAnalysisScript(sheets);
                    writeScriptFile(method + ".R", mthString);
                    int job = submitToSGE(method);
                    if (job != 0) {
                        jobIds.put("ridge", job);
                    }
                } else if (method.equals("en")) {
                    ElasticNet mth = new ElasticNet();
                    String mthString = mth.getAnalysisScript(sheets);
                    writeScriptFile("en.R", mthString);
                    jobIds.put("en", submitToSGE("en"));
                } else if (method.equals("svm")) {
                    SVM mth = new SVM();
                    String mthString = mth.getAnalysisScript(sheets);
                    writeScriptFile("svm.R", mthString);
                    jobIds.put("svm", submitToSGE("svm"));
                } else if (method.equals("pcr")) {
                    PCR mth = new PCR();
                    String mthString = mth.getAnalysisScript(sheets);
                    writeScriptFile("pcr.R", mthString);
                    jobIds.put("pcr", submitToSGE("pcr"));
                } else if (method.equals("pls")) {
                    PartialLeasedSquares mth = new PartialLeasedSquares();
                    String mthString = mth.getAnalysisScript(sheets);
                    writeScriptFile("pls.R", mthString);
                    jobIds.put("pls", submitToSGE("pls"));
                } else if (method.equals("lasso")) {
                    Lasso mth = new Lasso();
                    String mthString = mth.getAnalysisScript(sheets);
                    writeScriptFile("lasso.R", mthString);
                    jobIds.put("lasso", submitToSGE("lasso"));
                } else if (method.equals("univariate")) {
                    Univariate mth = new Univariate();
                    String mthString = mth.getAnalysisScript(sheets);
                    writeScriptFile("univariate.R", mthString);
                    jobIds.put("univariate", submitToSGE("univariate"));
                }
            }
            //Always run the RSessionInfo job
            RSessionInfo rsi = new RSessionInfo();
            String mthString = rsi.getAnalysisScript(sheets);
            writeScriptFile("sessionInfo.R", mthString);
            jobIds.put("sessionInfo", submitToSGE("sessionInfo"));

            //Add the info to the database
            SqLiteQueries sql = new SqLiteQueries();
            sql.addSgeId(ServletUtils.getResultsDir(request), jobIds);

        }
        catch (IOException e) {
            addActionError("qsub not found, please check your SGE configuration");//TODO: check
            return ERROR;//TODO: configure
        }
        catch (Exception e) {
            addActionError("Error during SGE submission");
            LOG.log(Level.SEVERE, "Exception: {0}", e.getMessage());
            return ERROR;
        }
        // TODO: log userId or sessionID as primary key? / method / jobId / startTime / sessionId / (finishTime <- status update daemon).

        LOG.log(Level.INFO, "Submitted {0} jobs to SGE.", jobIds.size());

        //Send out the email with details;
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
        wf.WriteFile(ServletUtils.getResultsDir(request)
                + "/" + scriptName, script);
    }

    /**
     * Create the SGE submission script and submit the job to SGE.
     * @param scriptName Name of the scripts.
     * @return The jobID.
     * @throws IOException When writing to disk fails.
     * @throws NullPointerException SGE qsub is not available.
     */
    public int submitToSGE(String scriptName) throws IOException,
            NullPointerException {

        //SGE submission queue.
        String queue =
                request.getSession().getServletContext().getInitParameter("SGEQueue");

        WriteFile wf = new WriteFile();
        wf.WriteFile(ServletUtils.getResultsDir(request)
                + "/" + scriptName + ".pbs",
                "#!/bin/sh\n\n"
                + "#$ -cwd\n"
                + "#$ -N " + scriptName + "\n"
                + "#$ -m e\n"
                + "#$ -M richard.finkers@wur.nl\n"
                + "#$ -S /bin/bash\n"
                + "#$ -q " + queue + "\n"
                + "cd " + ServletUtils.getResultsDir(request)
                + "\nR --no-save < "
                + scriptName + ".R\n");
        //Submit jobs to the SGE QUEUE
        int jobId = 0;

        jobId = CmdExec.ExecuteQSubCmd(ServletUtils.getResultsDir(request)
                + "/", scriptName);

        if (jobId == 0) {
            LOG.severe("error during submission");
            //TODO: implement exception?
            //Also is thrown when a wrong queue name is selected
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

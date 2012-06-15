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
import nl.wur.plantbreeding.omicsfusion.utils.Constants;
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
        ArrayList<String> methods =
                (ArrayList<String>) getRequest().getSession().getAttribute("methods");

        //Get the name of the response from the SQLite databse.
        SqLiteQueries slq = new SqLiteQueries();
        ArrayList<String> responseNames =
                slq.getResponseNames(ServletUtils.getResultsDir(request));

        String responseName = "";
        if (responseNames.isEmpty()) {
            addActionError(getText("error.no.responsevariable"));
        } else if (responseNames.size() == 1) {
            responseName = responseNames.get(0);
        } else {
            //TODO: implment for > 1 response variable.
            responseName = responseNames.get(0);
        }

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
                if (method.equals(Constants.RF)) {
                    RandomForest mth = new RandomForest();
                    String mthString = mth.getAnalysisScript(responseName);
                    writeScriptFile("rf.R", mthString);
                    jobIds.put(Constants.RF, submitToSGE(Constants.RF));
                } else if (method.equals(Constants.SPLS)) {
                    SparsePLS mth = new SparsePLS();
                    String mthString = mth.getAnalysisScript(responseName);
                    writeScriptFile("spls.R", mthString);
                    jobIds.put(Constants.SPLS, submitToSGE(Constants.SPLS));
                } else if (method.equals(Constants.RIDGE)) {
                    Ridge mth = new Ridge();
                    String mthString = mth.getAnalysisScript(responseName);
                    writeScriptFile(method + ".R", mthString);
                    int job = submitToSGE(method);
                    if (job != 0) {
                        jobIds.put(Constants.RIDGE, job);
                    }
                } else if (method.equals(Constants.EN)) {
                    ElasticNet mth = new ElasticNet();
                    String mthString = mth.getAnalysisScript(responseName);
                    writeScriptFile("en.R", mthString);
                    jobIds.put(Constants.EN, submitToSGE(Constants.EN));
                } else if (method.equals(Constants.SVM)) {
                    SVM mth = new SVM();
                    String mthString = mth.getAnalysisScript(responseName);
                    writeScriptFile("svm.R", mthString);
                    jobIds.put(Constants.SVM, submitToSGE(Constants.SVM));
                } else if (method.equals(Constants.PCR)) {
                    PCR mth = new PCR();
                    String mthString = mth.getAnalysisScript(responseName);
                    writeScriptFile("pcr.R", mthString);
                    jobIds.put(Constants.PCR, submitToSGE(Constants.PCR));
                } else if (method.equals(Constants.PLS)) {
                    PartialLeasedSquares mth = new PartialLeasedSquares();
                    String mthString = mth.getAnalysisScript(responseName);
                    writeScriptFile("pls.R", mthString);
                    jobIds.put(Constants.PLS, submitToSGE(Constants.PLS));
                } else if (method.equals(Constants.LASSO)) {
                    Lasso mth = new Lasso();
                    String mthString = mth.getAnalysisScript(responseName);
                    writeScriptFile("lasso.R", mthString);
                    jobIds.put(Constants.LASSO, submitToSGE(Constants.LASSO));
                } else if (method.equals(Constants.UNIVARIATE)) {
                    Univariate mth = new Univariate();
                    String mthString = mth.getAnalysisScript(responseName);
                    writeScriptFile("univariate.R", mthString);
                    jobIds.put(Constants.UNIVARIATE, submitToSGE(Constants.UNIVARIATE));
                }
            }
            //Always run the RSessionInfo job
            RSessionInfo rsi = new RSessionInfo();
            String mthString = rsi.getAnalysisScript(responseName);
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
     *
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
     *
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

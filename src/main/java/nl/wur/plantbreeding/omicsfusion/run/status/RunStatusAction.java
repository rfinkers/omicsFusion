/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.run.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import nl.wur.plantbreeding.omicsfusion.utils.CmdExec;
import nl.wur.plantbreeding.omicsfusion.utils.ServletUtils;

/**
 * Action class that shows the progress of the R jobs.
 * @author Richard Finkers
 * @version 1.0
 */
public class RunStatusAction extends RunStatusValidationForm {

    /** The Logger */
    private static final Logger LOG = Logger.getLogger(
            RunStatusAction.class.getName());
    /** Serial Version UID */
    private static final long serialVersionUID = 26082010L;

    @Override
    public String execute() throws Exception {

        ArrayList<Integer> jobIdsFromDatabase =
                getJobIdsFromDatabase(getSessionId());

        System.out.println("Jobs: " + jobIdsFromDatabase.size());

        HashMap<String, Boolean> status = new HashMap<String, Boolean>();
        //Check the status of the submitted jobs
        int i = 0;
        for (Integer jobId : jobIdsFromDatabase) {
            boolean CheckJobStatus = CmdExec.CheckJobStatus(jobId);
            System.out.println("Status:" + CheckJobStatus);
            status.put("job" + i, CheckJobStatus);
            i++;
        }
        HttpServletRequest servletRequest = ServletUtils.getServletRequest();
        servletRequest.setAttribute("status", status);

        //Method <-> Finished / In progress / Scheduled

        return SUCCESS;
    }

    private ArrayList<Integer> getJobIdsFromDatabase(String sessionId)
            throws NumberFormatException {
        System.out.println("SessionID" + sessionId);
        //take the sessionID token from the form and get the jobs for this session.
        //TODO: read from database? Now dummy placeholder to get the data
        ArrayList<Integer> jobsId = new ArrayList<Integer>(10);
        for (int i = 0; i < 10; i++) {
            jobsId.add(4549 + i);
        }
        return jobsId;
    }
}

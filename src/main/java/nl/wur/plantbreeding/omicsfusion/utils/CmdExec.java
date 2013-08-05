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
package nl.wur.plantbreeding.omicsfusion.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;

/**
 * Methods for interaction with the Sun Grid Engine.
 *
 * @author Richard Finkers
 * @version 1.0
 */
public class CmdExec {

    /**
     * The logger
     */
    private static final Logger LOG = Logger.getLogger(CmdExec.class.getName());

    private CmdExec() {
    }

    /**
     * Executes a SGE batch script and returns the job id of the submission.
     *
     * @param executionDir Name of the directory where the job scripts / data
     * resides.
     * @param method Method to be executed.
     * @param jobIDs
     * @return JobID on the SGE grid.
     * @throws IOException Batch job script not found.
     */
    public static int ExecuteQSubCmd(String executionDir, String method,
            HashMap<String, Integer> jobIDs)
            throws IOException {
        Process p;
        switch (method) {
            case Constants.RF:
            case Constants.SPLS:
            case Constants.RIDGE:
                //TODO: replace email with email user?
                //TODO: add this to the if test above?
                if (Constants.MAX_NUMBER_CPU > 2) {
                p = Runtime.getRuntime().exec(
                        "qsub -p -1023 -pe Rmpi "
                        + Constants.MAX_NUMBER_CPU
                        + " " + executionDir
                        + method + ".pbs");
            } else {
                p = Runtime.getRuntime().exec("qsub "
                        + executionDir + method + ".pbs");
            }
                break;
            case Constants.EMAIL:
                int counter = 0;
                String execution = "qsub  -hold_jid ";
                //TODO: null check on jobIDs
                Iterator it = jobIDs.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pairs = (Map.Entry) it.next();
                    if (counter == 0) {
                        execution += pairs.getValue();
                    } else {
                        execution += "," + pairs.getValue();
                    }
                    counter++;

                    it.remove(); // avoids a ConcurrentModificationException
                }
                execution += " ";
                execution += executionDir;
                execution += method + ".pbs";
                p = Runtime.getRuntime().exec(execution);
                break;
            default:
                p = Runtime.getRuntime().exec("qsub "
                        + executionDir + method + ".pbs");
                break;
        }
        String line;
        try (BufferedReader input
                = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            line = input.readLine();
        }

        int jobId = 0;
        if (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens()) {
                try {
                    jobId = Integer.parseInt(st.nextToken());
                    if (jobId != 0) {
                        break;
                    }
                } catch (NumberFormatException numberFormatException) {
                    //do nothing
                }
            }
        }//FIXME: else throw exception?? or handle jobId=0 at a different level?
        return jobId;
    }

    /**
     * Check the status of the jobs with the SGE cluster.
     *
     * @param jobId Job to check.
     * @return true if jobID does not exist on the SGE queue.
     * @throws IOException
     * @throws InterruptedException
     */
    public static boolean CheckJobStatus(int jobId)
            throws IOException, InterruptedException {
        //TODO: boolean or String? Probably String as this can contain more information about currently runnign jobs (e.g. start time etc.).
        boolean finished = false;
        //String line;
        Process p = Runtime.getRuntime().exec("qstat -j " + jobId);
        BufferedReader error;
        try (BufferedReader input
                = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            p.waitFor();
            String line = input.readLine();
            String errors = error.readLine();
            if (errors != null && errors.contains("do not exist")) {
                LOG.info("Errors check");
                System.out.println("errors:" + errors);
                finished = true;
            } else if (line != null) {
                System.out.println("input check");
                //submission_time:

                while ((line = input.readLine()) != null) {
                    System.out.println("result: " + line);
                }
            }
        }
        error.close();
        return finished;
    }
}

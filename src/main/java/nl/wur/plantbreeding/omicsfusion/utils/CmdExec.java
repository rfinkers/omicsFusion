/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.logging.Logger;

/**
 * Methods for interaction with the Sun Grid Engine.
 * @author Richard Finkers
 * @version 1.0
 */
public class CmdExec {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(CmdExec.class.getName());

    private CmdExec() {
    }

    /**
     * Executes a SGE batch script and returns the job id of the submission.
     * @param executionDir Name of the directory where the job scripts / data resides.
     * @param method Method to be executed.
     * @param queue
     * @return JobID on the SGE grid.
     * @throws IOException Batch job script not found.
     */
    public static int ExecuteQSubCmd(String executionDir, String method, String queue) throws IOException {
        Process p;
        if (method.equals("rf") || method.equals("spls") || method.equals("ridge")) {
            //TODO: -q queue my_job.pbs
            //TODO: -P omicsFusion -> project name
            //TODO: queue from initParameters
            //TODO: replace email with email user?
            //FIXME: use mpi only if MAX_NUMBER_CPU > 3. otherwise, default to normal submission

            if (Constants.MAX_NUMBER_CPU > 2) {//TODO: add this to the test above?
                p = Runtime.getRuntime().exec("qsub -S /bin/bash -p -100 -pe Rmpi " + Constants.MAX_NUMBER_CPU + " -q " + queue + " -m bea -M richard.finkers@wur.nl " + executionDir + method + ".pbs");
            } else {
                p = Runtime.getRuntime().exec("qsub -S /bin/bash -q " + queue + " " + executionDir + method + ".pbs");
            }
        } else {
            p = Runtime.getRuntime().exec("qsub -S /bin/bash -q " + queue + " " + executionDir + method + ".pbs");
        }
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = input.readLine();
        input.close();


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
     * @param jobId Job to check.
     * @return true if jobID does not exist on the SGE queue.
     * @throws IOException
     * @throws InterruptedException
     */
    public static boolean CheckJobStatus(int jobId) throws IOException, InterruptedException {
        //TODO: boolean or String? Probably String as this can contain more information about currently runnign jobs (e.g. start time etc.).
        boolean finished = false;
        //String line;
        Process p = Runtime.getRuntime().exec("qstat -j " + jobId);
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        p.waitFor();

        String line = input.readLine();
        String errors = error.readLine();

        if (errors != null && errors.contains("do not exist")) {
            System.out.println("Errors check");
            System.out.println("errors:" + errors);
            finished = true;
        } else if (line != null) {
            System.out.println("input check");
            //submission_time:

            while ((line = input.readLine()) != null) {
                System.out.println("result: " + line);
            }
        }
        input.close();
        error.close();
        return finished;
    }
}

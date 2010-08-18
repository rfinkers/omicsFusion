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
 * Executes a SGE batch script and returns the nr of the submission
 * @author finke002
 */
public class CmdExec {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(CmdExec.class.getName());

    private CmdExec() {
    }

    public static int CmdExec(String executionDir) throws IOException {

        Process p = Runtime.getRuntime().exec("qsub -S /bin/bash " + executionDir + "batch.sh");
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = input.readLine();
        input.close();

        int jobId = 0;
        StringTokenizer st = new StringTokenizer(line);
        while (st.hasMoreTokens()) {
            try {
                jobId = Integer.parseInt(st.nextToken());
                if (jobId != 0) {
                    break;
                }
            }
            catch (NumberFormatException numberFormatException) {
                //do nothing
            }
        }
        return jobId;
    }
}

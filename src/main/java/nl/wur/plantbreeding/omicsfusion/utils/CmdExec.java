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
 * Executes
 * @author finke002
 */
public class CmdExec {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(CmdExec.class.getName());

    public static void CmdExec(String executionDir) throws IOException {

        String line;
        Process p = Runtime.getRuntime().exec("qsub -S /qsub -S /bin/bash " + executionDir + "batch.sh");
        BufferedReader input =
                new BufferedReader(new InputStreamReader(p.getInputStream()));
        while (( line = input.readLine() ) != null) {//We actually only need the first line.
            System.out.println(line);
        }
//        try {
//
//            Pattern intsOnly = Pattern.compile("\\d+");
//            Matcher makeMatch = intsOnly.matcher(line);
//            makeMatch.find();
//            String inputInt = makeMatch.group();
//            System.out.println(inputInt);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }

        int jobId=0;
        StringTokenizer st = new StringTokenizer(line);
        while (st.hasMoreTokens()) {
            try {
                jobId = Integer.parseInt(st.nextToken());
                if(jobId!=0){
                    break;
                }
            }
            catch (NumberFormatException numberFormatException) {
                //do nothing
            }
        }

        System.out.println("JobID: " + jobId);


        input.close();

    }
}

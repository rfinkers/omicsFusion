/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.logic.Rserve;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RConnection;

/**
 *
 * @author Roeland Voorrips
 */
public class Rutil {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(Rutil.class.getName());

    /**
     * RConnection.assign cannot handle a 2-dimensional array, this is a workaround
     * for a rectangular (non-null) double[][] array
     * @param connection
     * @param ident the identifier of the matrix in R
     * @param data the rectangular array, both dimensions >0
     * @throws org.rosuda.REngine.REngineException
     */
    public static void assignMatrix(RConnection connection, String ident, double[][] data)
            throws REngineException {
        //data should be a rectangular natrix with both dimensions >0
        double[] temp = new double[data.length * data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                temp[i * data[0].length + j] = data[i][j];
            }
        }
        connection.assign(ident, temp);
        connection.voidEval(ident + " <- matrix(" + ident + ", nrow=" + data.length + ", byrow=TRUE)");
    }

    /**
     * RConnection.assign cannot handle a 2-dimensional array, this is a workaround
     * for a rectangular (non-null) int[][] array
     * @param connection
     * @param ident the identifier of the matrix in R
     * @param data the rectangular array, both dimensions >0
     * @throws org.rosuda.REngine.REngineException
     */
    public static void assignMatrix(RConnection connection, String ident, int[][] data)
            throws REngineException {
        //data should be a rectangular natrix with both dimensions >0
        int[] temp = new int[data.length * data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                temp[i * data[0].length + j] = data[i][j];
            }
        }
        connection.assign(ident, temp);
        connection.voidEval(ident + " <- matrix(" + ident + ", nrow=" + data.length + ", byrow=TRUE)");
    }

    /**
     * Tool for debugging Rserve problems.
     * rout passes a command to Rserve, captures the text output and prints it to System.out
     * @param connection
     * @param cmd the command to be passed to R
     */
    public static void rout(RConnection connection, String cmd) {
        REXP result;
        System.out.println("rout: cmd='" + cmd + "'");
        try {
            result = connection.eval("capture.output(" + cmd + ")");
            if (result.isNull()) {
                System.out.println("output is null");
                return;
            }
            if (!result.isString()) {
                System.out.println("result not string but " + result.getClass().getName());
                return;
            }
            String[] output = result.asStrings();
            if (output == null || output.length == 0) {
                System.out.println("no output");
                return;
            }
            System.out.println("output (" + output.length + " lines):");
            for (int i = 0; i < output.length; i++) {
                System.out.println(output[i]);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "an exception occurred: {0}", e.getMessage());
        }
        return;
    }

    private Rutil() {
    }
}

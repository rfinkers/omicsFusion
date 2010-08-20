/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.methods;

import java.util.logging.Logger;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;

/**
 * Application of PLS
 * @author Richard Finkers
 * @version 1.0
 */
public class PartialLeasedSquares extends Analysis {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(PartialLeasedSquares.class.getName());

    @Override
    protected String initializeResultObjects() {
        return super.initializeResultObjects("pls");
    }

    @Override
    protected String getRequiredLibraries() {
        String rCode = "# Load requried libraries for PLS\n";
        //rCode += "library(glmnet)\n";
        return rCode;
    }

    @Override
    public String getAnalysis() {
        return super.getAnalysis("pls");
    }

    @Override
    public String writeResults() {//TODO: check
        String rCode = "# Write results to disk\n";
        rCode += "write.csv(Train_Coeff, paste(\"PLS_coef\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_R2, paste(\"PLS_R2\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(methodResults, paste(\"PLS_Frac\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        //rCode += "write.xls(methodResults, \"PLSnew.xls\")";
        return rCode;
    }
}

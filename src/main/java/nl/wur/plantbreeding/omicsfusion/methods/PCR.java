/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.methods;

import java.util.logging.Logger;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;

/**
 * Application of Principal Component Regression
 * @author Richard Finkers
 * @version 1.0
 */
public class PCR extends Analysis {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(PCR.class.getName());

    @Override
    protected String initializeResultObjects() {
        return super.initializeResultObjects("pcr");
    }

    @Override
    protected String getRequiredLibraries() {
        String rCode = "# Load requried libraries for PCR\n";
        rCode += "library(pls)\n";
        return rCode;
    }

    @Override
    public String getAnalysis() {
        return super.getAnalysis("pcr");
    }

    @Override
    public String writeResults() {//TODO: check
        String rCode = "# Write results to disk\n";
        rCode += "write.csv(Train_Coeff, paste(\"PCR_coef\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_R2, paste(\"PCR_R2\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(methodResults, paste(\"PCR_Frac\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        //rCode += "write.xls(methodResults, \"PCRnew.xls\")"; FIXME: implement
        return rCode;
    }
}

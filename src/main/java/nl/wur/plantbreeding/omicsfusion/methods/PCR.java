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
    protected String combineResults() {
        String rCode = super.combineResults();
        rCode += "# Combine Method specific results\n";
        String trainNcomp = "Train_Ncomp <- cbind(";
        for (int i = 0; i < Constants.NUMBER_FOLDS_OUTER; i++) {
            trainNcomp += "opt_comp_" + i;
            if (i < 9) {
                trainNcomp += ", ";
            }
        }
        return rCode + trainNcomp + ")\n\n";
    }

    @Override
    public String writeResults() {
        String rCode = "# Write results to disk\n";
        rCode += "write.csv(Train_Coeff, paste(\"PCR_coef\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_Coeff_Summary, paste(\"PCR_coef_Sum\", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_R2, paste(\"PCR_R2\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_Ncomp, paste(\"PCR_Ncomp\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(methodResults, paste(\"PCR\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        //rCode += "write.xls(methodResults, \"PCRnew.xls\")";

        return rCode;
    }
}

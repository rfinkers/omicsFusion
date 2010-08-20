/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.methods;

import java.util.logging.Logger;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;

/**
 * SVM regression using e1071
 * @author Richard Finkers
 */
public class SVM extends Analysis {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(SVM.class.getName());

    @Override
    protected String initializeResultObjects() {
        return super.initializeResultObjects("svm");
    }

    @Override
    protected String getRequiredLibraries() {
        String rCode = "# Load requried libraries for svm\n";
        //rCode += "library(glmnet)\n";
        return rCode;
    }

    @Override
    public String getAnalysis() {
        return super.getAnalysis("svm");
    }

    @Override
    protected String combineResults() {
        String rCode = "# Combine results\n";
        String trainR2 = "Train_R2 <- cbind(";
        String test = "methodResults <- cbind(";

        for (int i = 0; i < Constants.NUMBERFOLDS; i++) {
            trainR2 += "R2_" + i;
            test += "test_" + i;
            if (i < 9) {
                trainR2 += ", ";
                test += ", ";
            }
        }
        return rCode + trainR2 + ")\n" + test + ")\n\n";
    }

    @Override
    public String writeResults() {//TODO: check
        String rCode = "# Write results to disk\n";
        rCode += "write.csv(Train_R2, paste(\"SVM_R2\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(methodResults, paste(\"SVM\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        //rCode += "write.xls(methodResults, \"PLSnew.xls\")";
        return rCode;
    }
}

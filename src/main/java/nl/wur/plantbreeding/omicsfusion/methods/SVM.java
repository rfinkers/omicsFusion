/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.methods;

import java.util.logging.Logger;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;

/**
 * SVM regression.
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
        rCode += "library(kernlab)\n";
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
        String sigma = "Train_sigma <- cbind(";
        String cost = "Train_cost <- cbind(";
        String test = "methodResults <- cbind(";

        for (int i = 0; i < Constants.NUMBER_FOLDS_OUTER; i++) {
            trainR2 += "R2_" + i;
            test += "test_" + i;
            sigma += "tune_sigma_" + i;
            cost += "tune_cost_" + i;
            if (i < 9) {
                trainR2 += ", ";
                test += ", ";
                sigma += ", ";
                cost += ", ";
            }
        }
        return rCode + trainR2 + ")\n" + sigma + ")\n" + cost + ")\n" + test + ")\n\n";
    }

    @Override
    public String writeResults() {
        String rCode = "# Write results to disk\n";
        rCode += "write.csv(Train_R2, paste(\"SVM_R2\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_sigma, paste(\"SVM_Sigma\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_cost, paste(\"SVM_Cost\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(methodResults, paste(\"SVM\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        //rCode += "write.xls(methodResults, \"PLSnew.xls\")";
        return rCode;
    }
}

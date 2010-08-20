/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.methods;

import java.util.logging.Logger;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;

/**
 * Ridge analysis
 * @author Richard Finkers
 */
public class Ridge extends Analysis {

    /** The Logger */
    private static final Logger LOG = Logger.getLogger(Ridge.class.getName());

    @Override
    protected String initializeResultObjects() {
        return super.initializeResultObjects("ridge");
    }

    @Override
    protected String getRequiredLibraries() {
        String rCode = "# Load requried libraries for RIDGE\n";
        rCode += "library(glmnet)\n";
        rCode += "library(Matrix)\n";
        return rCode;
    }

    @Override
    public String getAnalysis() {
        return super.getAnalysis("ridge");
    }

    @Override
        protected String combineResults() {
        String rCode = "# Combine results\n";
        String trainCoeff = "Train_Coeff <- cbind(";
        String trainLambda = "Train_Lambda <- cbind(";
        String trainR2 = "Train_R2 <- cbind(";
        String test = "methodResults <- cbind(";

        for (int i = 0; i < Constants.NUMBERFOLDS; i++) {
            trainCoeff += "coefs_" + i;
            trainLambda += "lambda_" + i;
            trainR2 += "R2_" + i;
            test += "test_" + i;
            if (i < 9) {
                trainCoeff += ", ";
                trainLambda += ", ";
                trainR2 += ", ";
                test += ", ";
            }
        }
        return rCode + trainCoeff + ")\n" + trainLambda + ")\n" + trainR2 + ")\n" + test + ")\n\n";
    }

    @Override
    public String writeResults() {//TODO: check
        String rCode = "# Write results to disk\n";
        rCode += "write.csv(Train_Coeff, paste(\"RIDGE_coef\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_R2, paste(\"RIDGE_R2\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_Lambda, paste(\"RIDGE_Lambda\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(methodResults, paste(\"RIDGE\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        //rCode += "write.xls(methodResults, \"RIDGEnew.xls\")";
        return rCode;
    }
}

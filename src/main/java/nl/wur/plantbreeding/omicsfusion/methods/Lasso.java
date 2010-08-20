package nl.wur.plantbreeding.omicsfusion.methods;

import java.util.logging.Logger;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;

/**
 * LASSO Analysis
 * @author Richard Finkers
 * @version 1.0
 */
public class Lasso extends Analysis {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(Lasso.class.getName());

    @Override
    protected String initializeResultObjects() {
        return super.initializeResultObjects("lasso");
    }

    @Override
    protected String getRequiredLibraries() {
        String rCode = "# Load requried libraries for Lasso\n";
        rCode += "library(glmnet)\n";
        rCode += "library(Matrix)\n";
        return rCode;
    }

    @Override
    public String getAnalysis() {
        return super.getAnalysis("lasso");
    }

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
    public String writeResults() {
        String rCode = "# Write results to disk\n";
        rCode += "write.csv(Train_Coeff, paste(\"LASSO_coef\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_R2, paste(\"LASSO_R2\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_Lambda, paste(\"LASSO_Lambda\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(methodResults, paste(\"LASSO\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        //rCode += "write.xls(lasso, \"LASSOnew.xls\")";
        return rCode;
    }
}

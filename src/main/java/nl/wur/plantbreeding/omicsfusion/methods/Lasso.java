/*
 * Copyright 2011 omicstools.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package nl.wur.plantbreeding.omicsfusion.methods;

import java.util.logging.Logger;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;

/**
 * LASSO Analysis.
 *
 * @author Richard Finkers
 * @version 1.0
 */
public class Lasso extends Analysis {

    /**
     * The logger.
     */
    private static final Logger LOG =
            Logger.getLogger(Lasso.class.getName());

    /**
     * {@inheritDoc}
     */
    @Override
    protected String initializeResultObjects() {
        return super.initializeResultObjects("lasso");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getRequiredLibraries() {
        String rCode = super.getRequiredLibraries();
        rCode += "# Load requried libraries for Lasso\n";
        rCode += "library(glmnet)\n";
        rCode += "library(Matrix)\n";
        return rCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAnalysis(String responseVariable) {
        return super.getAnalysis(Constants.LASSO, responseVariable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String combineResults() {
        String rCode = "# Combine results\n";
        String trainCoeff = "Train_Coeff <- cbind(";
        String trainLambda = "Train_Lambda <- cbind(";
        String trainR2 = "Train_R2 <- cbind(";
        String test = "methodResults <- cbind(";

        for (int i = 0; i < Constants.NUMBER_FOLDS_OUTER; i++) {
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
        return rCode + trainCoeff + ")\n" + trainLambda + ")\n"
                + trainR2 + ")\n" + test + ")\n\n";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String writeResultsToDisk() {
        String rCode = "# Write results to disk\n";
        rCode += "save.image(file=\"lasso.RData\")\n";
        rCode += "write.csv(Train_Coeff, paste(\"LASSO_coef\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_Coeff_Summary, paste(\"LASSO_coef_Sum\", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_R2, paste(\"LASSO_R2\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_Lambda, paste(\"LASSO_Lambda\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(methodResults, paste(\"LASSO\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        //rCode += "write.xls(lasso, \"LASSOnew.xls\")";
        return rCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String writeResultsToDB() {
        String rCode = "# Write results to the SQLite database\n";
        rCode += "Train_Coeff_Summary_<-cbind(responseVariable,\"" + Constants.LASSO + "\","
                + "as.data.frame(Train_Coeff_Summary))\n";
        rCode += "colnames(Train_Coeff_Summary_)[1]<-\"method\"\n";

        rCode += "con <- dbConnect(\"SQLite\", dbname = \"omicsFusion.db\")\n";
        rCode += "dbWriteTable(con, \"results\",Train_Coeff_Summary_,append=TRUE)\n";
        return rCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String writeRImage() {
        return "save.image(file=\"lasso.RData\", safe = TRUE)\n\n";
    }
}

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
 * Ridge analysis.
 *
 * @author Richard Finkers
 */
public class Ridge extends Analysis {

    /**
     * The Logger.
     */
    private static final Logger LOG = Logger.getLogger(Ridge.class.getName());

    @Override
    protected String initializeResultObjects() {
        return super.initializeResultObjects("ridge");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getRequiredLibraries() {
        String rCode = super.getRequiredLibraries();
        rCode += "# Load requried libraries for RIDGE\n";
        rCode += "library(glmnet)\n";
        rCode += "library(Matrix)\n";
        return rCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAnalysis(String responseVariable) {
        return super.getAnalysis(Constants.RIDGE, responseVariable);
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
        rCode += "save.image(file=\"ridge.RData\")\n";
        rCode += "write.csv(Train_Coeff, paste(\"RIDGE_coef\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_Coeff_Summary, paste(\"RIDGE_coef_Sum\", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_R2, paste(\"RIDGE_R2\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_Lambda, paste(\"RIDGE_Lambda\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(methodResults, paste(\"RIDGE\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        //rCode += "write.xls(methodResults, \"RIDGEnew.xls\")";
        return rCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String writeResultsToDB() {
        String rCode = "# Write results to the SQLite database\n";
        rCode += "Train_Coeff_Summary_<-cbind(responseVariable,\"" + Constants.RIDGE + "\","
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
        return "save.image(file=\"ridge.RData\", safe = TRUE)\n\n";
    }
}

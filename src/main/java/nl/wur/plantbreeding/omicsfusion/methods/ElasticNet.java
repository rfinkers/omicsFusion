/*
 * Copyright 2011 omicstools.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.wur.plantbreeding.omicsfusion.methods;

import java.util.logging.Logger;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;

/**
 * Elastic net (glm).
 * @author Richard Finkers
 * @version 1.0
 */
public class ElasticNet extends Analysis {

    /**The Logger */
    private static final Logger LOG =
            Logger.getLogger(ElasticNet.class.getName());

    /** {@inheritDoc} */
    @Override
    protected String initializeResultObjects() {
        return super.initializeResultObjects("en");
    }

    /** {@inheritDoc} */
    @Override
    public String getRequiredLibraries() {
        String rCode = super.getRequiredLibraries();
        rCode += "# Load requried libraries for ElasticNet\n";
        rCode += "library(glmnet)\n";
        return rCode;
    }

    /** {@inheritDoc} */
    @Override
    public String getAnalysis() {
        return super.getAnalysis("en");
    }

    /** {@inheritDoc} */
    @Override
    protected String combineResults() {
        String rCode = "# Combine results\n";
        String trainCoeff = "Train_Coeff <- cbind(";
        String trainFraction = "Train_Fraction <- cbind(";
        String trainLambda = "Train_Lambda <- cbind(";
        String trainR2 = "Train_R2 <- cbind(";
        String test = "methodResults <- cbind(";

        for (int i = 0; i < Constants.NUMBER_FOLDS_OUTER; i++) {
            trainCoeff += "coefs_" + i;
            trainFraction += "frac_" + i;
            trainLambda += "lambda_" + i;
            trainR2 += "R2_" + i;
            test += "test_" + i;
            if (i < 9) {
                trainCoeff += ", ";
                trainFraction += ", ";
                trainLambda += ", ";
                trainR2 += ", ";
                test += ", ";
            }
        }
        return rCode + trainCoeff + ")\n" + trainFraction + ")\n"
                + trainLambda + ")\n" + trainR2 + ")\n" + test + ")\n\n";
    }

    /** {@inheritDoc} */
    @Override
    public String writeResults() {
        String rCode = "# Write results to disk\n";
        rCode += "write.csv(Train_Coeff, paste(\"EN_coef\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_Coeff_Summary, paste(\"EN_coef_Sum\", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_R2, paste(\"EN_R2\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_Lambda, paste(\"EN_Lambda\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_Fraction, paste(\"EN_Frac\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(methodResults, paste(\"EN\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        //rCode += "write.xls(methodResults, \"ennew.xls\")";
        return rCode;
    }
}

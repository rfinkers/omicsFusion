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
 * SVM regression.
 *
 * @author Richard Finkers
 */
public class SVM extends Analysis {

    /**
     * The logger.
     */
    private static final Logger LOG = Logger.getLogger(SVM.class.getName());

    /** {@inheritDoc} */
    @Override
    protected String initializeResultObjects() {
        return super.initializeResultObjects("svm");
    }

    /** {@inheritDoc} */
    @Override
    protected String getRequiredLibraries() {
        String rCode = super.getRequiredLibraries();
        rCode += "# Load requried libraries for svm\n";
        rCode += "library(kernlab)\n";
        return rCode;
    }

    /** {@inheritDoc} */
    @Override
    public String getAnalysis() {
        return super.getAnalysis("svm");
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    protected String getRowMeansAndSD() {
        String rCode = "# Get row means, SD and (absolute) rank\n";
        //TODO: Implement for SVM
        return rCode;
    }

    /** {@inheritDoc} */
    @Override
    public String writeResultsToDisk() {
        String rCode = "# Write results to disk\n";
        //TODO: implement summary method
        rCode += "save.image(file=\"svm.RData\")\n";
        rCode += "write.csv(Train_R2, paste(\"SVM_R2\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_sigma, paste(\"SVM_Sigma\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_cost, paste(\"SVM_Cost\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(methodResults, paste(\"SVM\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        //rCode += "write.xls(methodResults, \"PLSnew.xls\")";
        return rCode;
    }
}

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

import nl.wur.plantbreeding.omicsfusion.utils.Constants;

/**
 * Sparse partial least squares analysis
 *
 * @author Richard Finkers
 */
public class SparsePLS extends Analysis {

    /**
     * {@inheritDoc}
     */
    @Override
    protected String initializeResultObjects() {
        return super.initializeResultObjects("spls");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getRequiredLibraries() {
        String rCode = super.getRequiredLibraries();
        rCode += "# Load requried libraries for PLS\n";
        rCode += "library(pls)\n";
        rCode += "library(MASS)\n";
        rCode += "library(nnet)\n";
        rCode += "library(spls)\n";
        return rCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAnalysis(String responseVariable) {
        return super.getAnalysis(Constants.SPLS, responseVariable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String combineResults() {
        String rCode = super.combineResults();
        rCode += "# Combine Method specific results\n";
        String Train_eta = "Train_eta <- cbind(";
        String Train_K = "Train_K <- cbind(";
        for (int i = 0; i < Constants.NUMBER_FOLDS_OUTER; i++) {
            Train_eta += "eta_" + i;
            Train_K += "K_" + i;
            if (i < 9) {
                Train_eta += ", ";
                Train_K += ", ";
            }
        }
        return rCode + Train_eta + ")\n" + Train_K + ")\n\n";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String writeResultsToDisk() {
        String rCode = "# Write results to disk\n";
        rCode += "save.image(file=\"spls.RData\")\n";
        rCode += "write.csv(Train_Coeff, paste(\"SPLS_coef\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_Coeff_Summary, paste(\"SPLS_coef_Sum\", "
                + "\".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_R2, paste(\"SPLS_R2\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_eta, paste(\"SPLS_Eta\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_K, paste(\"SPLS_K\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(methodResults, paste(\"SPLS\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        //rCode += "write.xls(methodResults, \"SPLSnew.xls\")";
        return rCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String writeResultsToDB() {
        String rCode = "# Write results to the SQLite database\n";
        rCode += "Train_Coeff_Summary_<-cbind(responseVariable,\"" + Constants.SPLS + "\","
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
        return "save.image(file=\"spls.RData\", safe = TRUE)\n\n";
    }
}

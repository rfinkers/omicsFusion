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
 * Application of Principal Component Regression.
 *
 * @author Richard Finkers
 * @version 1.0
 */
public class PCR extends Analysis {

    /**
     * The logger.
     */
    private static final Logger LOG = Logger.getLogger(PCR.class.getName());

    /**
     * {@inheritDoc}
     */
    @Override
    protected String initializeResultObjects() {
        return super.initializeResultObjects("pcr");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getRequiredLibraries() {
        String rCode = super.getRequiredLibraries();
        rCode += "# Load requried libraries for PCR\n";
        rCode += "library(pls)\n";
        return rCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAnalysis() {
        return super.getAnalysis("pcr");
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String writeResultsToDisk() {
        String rCode = "# Write results to disk\n";
        rCode += "save.image(file=\"pcr.RData\")\n";
        rCode += "write.csv(Train_Coeff, paste(\"PCR_coef\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_Coeff_Summary, paste(\"PCR_coef_Sum\", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_R2, paste(\"PCR_R2\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_Ncomp, paste(\"PCR_Ncomp\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(methodResults, paste(\"PCR\", \"_\", "
                + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        //rCode += "write.xls(methodResults, \"PCRnew.xls\")";

        return rCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String writeResultsToDB() {
        String rCode = "# Write results to the SQLite database\n";
        rCode +="Train_Coeff_Summary_<-cbind(\"PCR\",as.data.frame(Train_Coeff_Summary))\n";
        rCode +="colnames(Train_Coeff_Summary_)[1]<-\"method\"\n";

        rCode += "con <- dbConnect(\"SQLite\", dbname = \"omicsFusion.db\")\n";
        rCode += "dbWriteTable(con, \"results\",Train_Coeff_Summary_,append=TRUE)\n";
        return rCode;
    }
}

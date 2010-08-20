/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.methods;

import nl.wur.plantbreeding.omicsfusion.utils.Constants;

/**
 * Sparse PLS analysis
 * @author Richard Finkers
 */
public class SparsePLS extends Analysis {

    @Override
    protected String initializeResultObjects() {
        return super.initializeResultObjects("pls");
    }

    @Override
    protected String getRequiredLibraries() {
        String rCode = "# Load requried libraries for PLS\n";
        //rCode += "library(glmnet)\n";
        return rCode;
    }

    @Override
    public String getAnalysis() {
        return super.getAnalysis("pls");
    }

    @Override
    public String writeResults() {//TODO: check
        String rCode = "# Write results to disk\n";
        rCode += "write.csv(Train_Coeff, paste(\"SPLS_coef\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_R2, paste(\"SPLS_R2\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(methodResults, paste(\"SPLS\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        //rCode += "write.xls(methodResults, \"SPLSnew.xls\")";
        return rCode;
    }
}

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
        return super.initializeResultObjects("spls");
    }

    @Override
    protected String getRequiredLibraries() {
        String rCode = "# Load requried libraries for PLS\n";
        rCode += "library(pls)\n";
        rCode += "library(MASS)\n";
        rCode += "library(nnet)\n";
        rCode += "library(spls)\n";
        return rCode;
    }

    @Override
    public String getAnalysis() {
        return super.getAnalysis("spls");
    }

    @Override
    protected String combineResults() {
        String rCode = super.combineResults();
        rCode += "# Combine Method specific results\n";
        String Train_eta = "Train_eta <- cbind(";
        String Train_K = "Train_K <- cbind(";
        for (int i = 0; i < Constants.NUMBERFOLDS; i++) {
            Train_eta += "eta_" + i;
            Train_K += "K_" + i;
            if (i < 9) {
                Train_eta += ", ";
                Train_K += ", ";
            }
        }
        return rCode + Train_eta + ")\n" + Train_K + ")\n\n";
    }

    @Override
    public String writeResults() {
        String rCode = "# Write results to disk\n";
        rCode += "write.csv(Train_Coeff, paste(\"SPLS_coef\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_R2, paste(\"SPLS_R2\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_eta, paste(\"SPLS_Eta\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_K, paste(\"SPLS_K\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(methodResults, paste(\"SPLS\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        //rCode += "write.xls(methodResults, \"SPLSnew.xls\")";
        return rCode;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.methods;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Shows the status of all installed packages.
 * @author Richard Finkers
 */
public class RSessionInfo extends Analysis {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(RSessionInfo.class.getName());

    @Override
    protected String getRequiredLibraries() {
        String rCode = super.getRequiredLibraries();
        rCode += "# Loading method specific libraries\n";
        //Add all method specific libraries here
        rCode += "library(randomForest)\n";
        rCode += "library(glmnet)\n";
        rCode += "library(Matrix)\n";
        rCode += "library(pls)\n";
        rCode += "library(kernlab)\n";
        rCode += "library(MASS)\n";
        rCode += "library(nnet)\n";
        rCode += "library(spls)\n";
        rCode += "library(multtest)\n";
        //Parralelization specific libraries.
        rCode += "library(doMPI)\n";
        rCode += "library(snow)\n";
        return rCode;
    }

    @Override
    protected String getAnalysis() {
        String rCode = "#Analysis methods\n";
        rCode += "session <- sessionInfo()\n";
        rCode += "latex <- toLatex(sessionInfo(), locale=FALSE)";
        return rCode;
    }

    @Override
    protected String writeResults() {
        String rCode = "#Write R sesion info details\n";
        //TODO: how to write the relevant info to a file
        rCode += "write(latex, file=\"sessionInfo.tex\")";
        return rCode;
    }

    //Next section contains overrided methods that are not relevant to this
    //script and should therfere return nothing.
    @Override
    protected String preProcessMatrix() {
        return "";
    }

    @Override
    protected String combineResults() {
        return "";
    }

    @Override
    protected String getTrainingSets() {
        return "";
    }

    @Override
    protected String getRowMeansAndSD() {
        return "";
    }

    @Override
    protected String initializeResultObjects() {
        return "";
    }

    @Override
    protected String loadPredictorAndResponseDataSheets(HashMap<String, String> excelSheets) {
        return "";
    }
}
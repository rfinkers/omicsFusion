/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.methods;

import java.util.HashMap;
import java.util.logging.Logger;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;

/**
 * Analysis scripts for the omicsFusion pipeline.
 * @author Richard Finkers
 */
public class Analysis {

    /** A logger */
    private static final Logger LOG = Logger.getLogger(Analysis.class.getName());

    Analysis() {
    }

    protected String loadExcelSheets(HashMap<String,String> excelSheets) {
        String rCode = "# Load the excel sheets\n";
        rCode += "library(gdata)\n";//Used to load excel sheets
        rCode += "library(caret)\n";//Used for createfolds in training set
        rCode += "predictorSheet <- read.xls(\"" + excelSheets.get("predictor")+"\")\n";
        rCode += "responseSheet <- read.xls(\"" + excelSheets.get("response")+"\")\n";
        rCode += "response <- colnames(responseSheet[2])\n";//TODO: Tric does not work
        rCode += "dataSet=cbind(responseSheet[2],predictorSheet[-1])\n";
        rCode += "dataSet=na.omit(dataSet)";//TODO: handle differently? Added in addition to the script of Animesh.
        return rCode;
    }

    protected String preProcessMatrix() {
        String rCode = "# Pre process data matrix\n";
        rCode += "DesignMatrix <- model.matrix(BRIX_P ~ . - 1, dataSet)\n";//FIXME: HARDCODED
        rCode += "DesignMatrix <- scale(DesignMatrix)\n";
        return rCode;
    }

    protected String getTrainingSets() {
        String rCode =" # Create training sets\n";
        //FIXME: HARDCODED
        rCode += "  inTrainingSet <- createFolds(dataSet$BRIX_P, k = " + Constants.NUMBERFOLDS + ", list = TRUE, returnTrain = T)\n";
        for (int i = 0; i < Constants.NUMBERFOLDS; i++) {
            int j=i+1;//R object contains 1-10 instead of 0-9!
            rCode += "  trainingSet" + i + " <- inTrainingSet$\"" + j + "\"\n";
        }
        return rCode;
    }

    protected String getRequiredLibraries() {
        String rCode = "# Load requried libraries\n";
        return rCode;
    }

    /**
     * Initialization of empty variables to store the results.
     * @return
     */
    protected String initializeResultObjects() {
        String rCode = "# Initialize results\n";
        return rCode;
    }

    protected String getAnalysis() {
        String rCode = "# Perform analysis\n";
        return rCode;
    }

    protected String combineResults() {
        String rCode = "# Combine results\n";
        return rCode;
    }

    protected String writeResults() {
        String rCode = "# Write results to disk\n";
        return rCode;
    }

    public String getAnalysisScript(HashMap<String,String> excelSheets) {
        String rScript="# Concatenating analisis script\n";
        rScript += loadExcelSheets(excelSheets);
        rScript += preProcessMatrix();
        rScript += initializeResultObjects();
        rScript += getRequiredLibraries();
        rScript += getAnalysis();
        rScript += combineResults();
        rScript += writeResults();
        return rScript;
    }
}

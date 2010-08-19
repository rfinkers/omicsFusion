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

    /**
     * Loads The Excel sheets from the filesystem.
     * @param excelSheets The names of the predictor and response excel sheets.
     * @return R program code.
     */
    protected String loadExcelSheets(HashMap<String, String> excelSheets) {
        String rCode = "# Load the generic R libraries \n";
        rCode += "library(gdata)\n";//Used to load excel sheets
        rCode += "library(caret)\n\n";//Used for createfolds in training set
        rCode += "# Load the excel sheets\n";
        rCode += "predictorSheet <- read.xls(\"" + excelSheets.get("predictor") + "\")\n";
        rCode += "responseSheet  <- read.xls(\"" + excelSheets.get("response") + "\")\n";
        rCode += "response <- colnames(responseSheet[2])\n";//TODO: Tric does not work
        rCode += "dataSet=cbind(responseSheet[2],predictorSheet[-1])\n";
        rCode += "dataSet=na.omit(dataSet)\n\n";//TODO: handle differently? Added in addition to the script of Animesh.
        return rCode;
    }

    /**
     * Preprocess the data matrix.
     * @return R program code.
     */
    protected String preProcessMatrix() {
        String rCode = "# Pre process data matrix\n";
        rCode += "DesignMatrix <- model.matrix(dataSet$BRIX_P ~ . - 1, dataSet)\n";//FIXME: dataSet[1] coding does not work / HARDCODED dataSet%BRIX_M
        rCode += "DesignMatrix <- scale(DesignMatrix)\n\n";
        return rCode;
    }

    /**
     * Get the training sets.
     * @return R program code.
     */
    protected String getTrainingSets() {
        String rCode = " # Create training sets\n";
        //FIXME: dataSet[1] coding does not work. HARDCODED dataSet%BRIX_M
        rCode += "  inTrainingSet <- createFolds(dataSet$BRIX_P, k = " + Constants.NUMBERFOLDS + ", list = TRUE, returnTrain = T)\n";
        for (int i = 0; i < Constants.NUMBERFOLDS; i++) {
            int j = i + 1;//R object contains 1-10 instead of 0-9!
            rCode += "  trainingSet" + i + " <- inTrainingSet$\"" + j + "\"\n";
        }
        rCode += "\n";
        return rCode;
    }

    /**
     * Load the required libraries for this method.
     * @return R compatible code.
     */
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

    /**
     * Analysis method specific run code.
     * @return R compatible code.
     */
    protected String getAnalysis() {
        String rCode = "# Perform analysis\n";
        return rCode;
    }

    /**
     * Method that combines the result sets.
     * @return R compatible code.
     */
    protected String combineResults() {
        String rCode = "# Combine results\n";
        return rCode;
    }

    /**
     * Write the results to a csv file.
     * @return R compatible code.
     */
    protected String writeResults() {
        String rCode = "# Write results to disk\n";
        return rCode;
    }

    /**
     * Generic run script.
     * @param excelSheets The names of the predictor and response excel sheets.
     * @return the complete R compatible script for the selected method.
     */
    public String getAnalysisScript(HashMap<String, String> excelSheets) {
        String rScript = "# Concatenating analisis script\n";
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

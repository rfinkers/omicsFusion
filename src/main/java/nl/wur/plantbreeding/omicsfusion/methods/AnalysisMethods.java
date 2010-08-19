/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.methods;

/**
 * Interface defining how each analysis method should be defined.
 * @author Richard Finkers
 */
public interface AnalysisMethods {

    String rScript = "";

    /**
     * Which R libraries are used for this Method.
     * @return A string containing the required libraries.
     */
    String getRequiredLibraries();

    /**
     * Initialization of empty variables to store the results.
     * @return
     */
    String initializeResultObjects();

    String getAnalysis();

    String combineResults();

    String writeResults();

    String getAnalysisScript();
}

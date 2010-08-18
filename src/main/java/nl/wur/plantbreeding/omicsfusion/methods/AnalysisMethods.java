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
     * Initialization of empty variables to store the results.
     * @param loop Outer cross validation loop (To estimate MSER).
     * @param itterations Inner cross validation (to optimize parameters).
     * @return
     */
    String getParamterInitialization(int loop, int itterations);

    String getAnalysis();

    String getPostProcessing();
}

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

/**
 * Shows the status of all installed packages.
 *
 * @author Richard Finkers
 */
public class RSessionInfo extends Analysis {

    /**
     * The logger.
     */
    private static final Logger LOG
            = Logger.getLogger(RSessionInfo.class.getName());

    /**
     * {
     *
     * @inheritDoc}
     */
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
        //Load excel sheets
//        rCode += "library(gdata)\n";
//        rCode += "library(XLConnect)\n";
        //SQLite library
        rCode += "library(RSQLite)\n";
        return rCode;
    }

    /**
     * {
     *
     * @inheritDoc}
     */
    @Override
    protected String getAnalysis(String responseVariable) {
        String rCode = "#Analysis methods\n";
        rCode += "session <- sessionInfo()\n";
        rCode += "latex <- toLatex(sessionInfo(), locale=FALSE)";
        return rCode;
    }

    /**
     * {
     *
     * @inheritDoc}
     */
    @Override
    protected String writeResultsToDisk() {
        String rCode = "#Write R sesion info details\n";
        //TODO: how to write the relevant info to a file
        rCode += "write(latex, file=\"sessionInfo.tex\")";
        return rCode;
    }

    //Next section contains overrided methods that are not relevant to this
    //script and should therfere return nothing.
    /**
     * {
     *
     * @inheritDoc}
     */
    @Override
    protected String preProcessMatrix(String responseVariable) {
        return "";
    }

    /**
     * {
     *
     * @inheritDoc}
     */
    @Override
    protected String combineResults() {
        return "";
    }

    /**
     * {
     *
     * @inheritDoc}
     */
    @Override
    protected String getTrainingSets(String responseVariable) {
        return "";
    }

    /**
     * {
     *
     * @inheritDoc}
     */
    @Override
    protected String getRowMeansAndSD() {
        return "";
    }

    /**
     * {
     *
     * @inheritDoc}
     */
    @Override
    protected String initializeResultObjects() {
        return "";
    }
}

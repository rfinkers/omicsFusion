/*
 * Copyright 2011 omicstools.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.wur.plantbreeding.omicsfusion.utils;

/**
 * Constants used within the omicsFusion project.
 *
 * @author Richard Finkers
 * @version 0.1
 */
public class Constants {

    /**
     * Name of the omicsFusion SQLite database.
     */
    public static final String OMICSFUSION_DB = "omicsFusion.db";
    /**
     * user details object name.
     */
    public static final String USER = "omicsFusionUser";
    /**
     * user data upload object name.
     */
    public static final String DataUpload = "sheets";
    /**
     * user selected.methods object name.
     */
    public static final String SelectedMethods = "methods";
    /**
     * Minimum number of columns for predictor sheet.
     */
    public final static int MIN_PREDICTOR_COLUMNS = 5;
    /**
     * Minimum number of columns for response sheet.
     */
    public final static int MIN_RESPONSE_COLUMNS = 1;
    /**
     * Minimum number of individuals
     */
    public final static int MIN_INDIVIDUALS = 20;
    /**
     * The total number of iterations on the dataset. FIXME: set to 100 for
     * final program.
     */
    public static final int ITERATIONS = 10;
    /**
     * outer cross validation (to optimize parameters).
     */
    public static final int NUMBER_FOLDS_OUTER = 10;
    /**
     * inner cross validation (to optimize parameters).
     */
    public static final int NUMBER_FOLDS_INNER = 10;//Test voor Roeland
    /**
     * How many CPU's can be used for one method.
     */
    public static final int MAX_NUMBER_CPU = 8;
    /**
     * What is the maximum number of results to show in the summary table.
     */
    public static final int MAX_SUMMARY_RESULTS = 2500;
    /**
     * RandomForest identifier.
     */
    public static final String RF = "rf";
    /**
     * Support Vector Machine identifier.
     */
    public static final String SVM = "svm";
    /**
     * Ridge regression identifier.
     */
    public static final String RIDGE = "ridge";
    /**
     * PLS identifier.
     */
    public static final String PLS = "pls";
    /**
     * SPLS identifier.
     */
    public static final String SPLS = "spls";
    /**
     * Univariate identifier.
     */
    public static final String UNIVARIATE = "univariate";
    /**
     * Univariate Benjamini-Hochberg FDR identifier.
     */
    public static final String BH = "bh";
    /**
     * Lasso identifier.
     */
    public static final String LASSO = "lasso";
    /**
     * PCR identifier.
     */
    public static final String PCR = "pcr";
    /**
     * Elastic Net identifier.
     */
    public static final String EN = "en";
    /**
     * Package Versions.
     */
    public static final String SESSIONINFO = "sessionInfo";

    private Constants() {
    }
}

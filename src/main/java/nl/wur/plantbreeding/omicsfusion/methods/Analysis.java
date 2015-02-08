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

import java.util.HashMap;
import java.util.logging.Logger;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;

//TODO: if the datasets contains not recognized NA values, we get an error that
//      RSME is not valid for train method.
/**
 * Generic Analysis class for the omicsFusion pipeline. Each method needs to
 * implement its own specification, but might inherit code from this class.
 *
 * @author Richard Finkers
 * @version 1.5
 *
 * 1.5: Updated for sqlite usage.
 */
public class Analysis {

    /**
     * A logger.
     */
    private static final Logger LOG
            = Logger.getLogger(Analysis.class.getName());

    Analysis() {
    }

    /**
     * Load the required libraries for this method.
     *
     * @return R compatible code.
     */
    protected String getRequiredLibraries() {
        String rCode = "# Load requried generic libraries\n";
        rCode += "library(lattice)\n";//dependencies of caret.
        rCode += "library(reshape)\n";//dependencies of caret.
        rCode += "library(plyr)\n";//dependencies of caret.
        rCode += "library(caret)\n";//Used for createfolds in training set.
        rCode += "library(RSQLite)\n";//Used to connect to the SQLite database.
        rCode += "\n";
        return rCode;
    }

    /**
     * Generic implementation to get database connection
     *
     * @return R code to connect to SQLite database.
     */
    protected String getDatabaseConnection() {
        return "con <- dbConnect(SQLite(), dbname = \"omicsFusion.db\", "
                + "cache_size = 5000, synchronous = \"full\")\n";
    }

    /**
     * Read data from the SQLite database.
     *
     * @param responseVariable responseVariable to analyze.
     * @return R program code.
     */
    protected String loadPredictorAndResponseDataSheets(
            String responseVariable) {
        String rCode = "# Set the response variable\n";
        rCode += "responseVariable <- '" + responseVariable + "'\n\n";
        rCode += "# Load the PredictResponse data sheet from the "
                + "SQLite database.\n";
        rCode += getDatabaseConnection();
        rCode += "predQuery <- dbSendQuery(con, \"SELECT genotypeID, "
                + "predictorID, observation FROM predictor\")\n";
        rCode += "predData <- fetch(predQuery, n = -1)\n";
        rCode += "dbClearResult(predQuery)\n";
        rCode += "respQuery <- dbSendQuery(con, \"SELECT genotypeID, "
                + "traitID, observation "
                + "FROM response "
                + "WHERE traitID ='" + responseVariable.trim() + "'\")\n";
        rCode += "respData <- fetch(respQuery, n = -1)\n";
        rCode += "dbClearResult(respQuery)\n";
        //Convert to matrix
        //We need a data frame instead of an matrix. Why?
        rCode += "respMatrix <- "
                + "as.data.frame(tapply(respData[,3],respData[,c(1,2)],c))\n";
        rCode += "predMatrix <- "
                + "as.data.frame(tapply(predData[,3],predData[,c(1,2)],c))\n";
        return rCode;
    }

    /**
     * Permute the dataset.
     *
     * @param permuted True if permuted dataset should be obtained.
     * @return dataset.
     */
    protected String getDataset(boolean permuted) {
        //Prepare the dataset object.
        String rCode = "";
        if (permuted) {
            rCode += "dataSet=cbind(sample(respMatrix),predMatrix)\n\n";
        } else {
            rCode += "dataSet=cbind(respMatrix,predMatrix)\n\n";
        }
        return rCode;
    }

    /**
     * Preprocess the data matrix.
     *
     * @param responseVariable Name of the response variable.
     * @return R program code.
     */
    protected String preProcessMatrix(String responseVariable) {
        String rCode = "# Pre process data matrix\n";

        rCode += "DesignMatrix <- "
                + "model.matrix(dataSet$" + responseVariable.trim()
                + " ~ . - 1, dataSet)\n";
        //TODO: different centering and autoscaling algorithms? User selectable.
        rCode += "DesignMatrix <- scale(DesignMatrix)\n\n";
        return rCode;
    }

    /**
     * test.
     */
    /**
     * Handle missing data via omit or imputation. This method should correct
     * for each analysis. Missing data in the response matrix might differ for
     * each run.
     *
     * @return R program code.
     */
    protected String handleMissingData() {
        String rCode = "# Missing data\n";
        //TODO: better na.omit strategies. Imputation? Added in addition to the
        //script of Animesh.
        //option: replace NA with column mean.
        rCode += "dataSet=na.omit(dataSet)\n\n";
        return rCode;
    }

    /**
     * Write the current R session to disk.
     *
     * @return R program code.
     */
    protected String writeRImage() {
        return "save.image(file=\"test.RData\", safe = TRUE)\n\n";
    }

    /**
     * Load the predictor and response sheets.
     *
     * @param excelSheets Name of the excel sheets
     * @return R program code.
     * @deprecated Load from SQLite db.
     */
    protected String loadPredictResponseDataSheet(
            HashMap<String, String> excelSheets) {
        String rCode = "# Load the PredictResponse data sheet.\n";
        //TODO: test how generic the CSV import works.
        //Can we test for decimal point, etc?
        if (excelSheets.get("predictResponse") != null) {
            if (excelSheets.get("predictor").contains("csv")) {
                rCode += "predictResponse <- read.csv2(\""
                        + excelSheets.get("predictResponse")
                        + "\", header=TRUE,sep=\",\",dec=\".\")\n";
            } else {
                rCode += "predictResponse <- read.xls(\""
                        + excelSheets.get("predictResponse") + "\")\n";
            }
        }
        return rCode;
    }

    /**
     * ind the values for lambda over which the optimization of lambda should be
     * carried out Could be done outside of the for loop I think.
     *
     * @TODO: t1 is now fixed, make dynamic for analysis of multiple response
     * variables.
     *
     * @return R program code to determine value of lambda.
     */
    protected String getLambda() {
        String rCode = " # Estimate inital Lambda for RIDGE regression.\n";
        rCode += "cvres<-cv.glmnet(x=DesignMatrix,y=dataSet$t1,nfolds=10)\n";
        rCode += "lambda<-cvres$lambda\n\n";
        return rCode;
    }

    /**
     * Get the training sets.
     *
     * @param responseVariable Name of the response variable.
     * @return R program code.
     */
    protected String getTrainingSets(String responseVariable) {
        //TODO: we might get different training and test sets profided via the dataset upload.
        String rCode = " # Create training sets\n";

        //Can do different procedures. E.G. bootstraps, resampling, leaf-one-out, etc. User selectable?
        rCode += "  inTrainingSet <- createFolds(dataSet$"
                + responseVariable.trim()
                + ", k = " + Constants.NUMBER_FOLDS_OUTER
                + ", list = TRUE, returnTrain = T)\n";
        for (int i = 0; i < Constants.NUMBER_FOLDS_OUTER; i++) {
            int j = i + 1;//R object contains 1-10 instead of 0-9!
            if (j < 10 && Constants.NUMBER_FOLDS_OUTER > 9) {
                rCode += "  trainingSet" + i
                        + " <- inTrainingSet$Fold0" + j + "\n";
            } else {
                rCode += "  trainingSet" + i
                        + " <- inTrainingSet$Fold" + j + "\n";
            }
        }
        rCode += "\n";
        return rCode;
    }

    /**
     * Initialization of the most commonly used empty variables to store the
     * results. Most methods do, however, require their own initialization.
     *
     * @return R compatible code.
     */
    protected String initializeResultObjects() {
        //TODO: how is this method used?
        String rCode = "# Initialize results and add column names\n";
        for (int i = 0; i < Constants.NUMBER_FOLDS_OUTER; i++) {
            rCode += "coln <-  paste(" + i + ",seq(1:" + Constants.ITERATIONS
                    + "),sep=\"_\")\n";
            rCode += "coefs_" + i
                    + " <- matrix(data = NA,nrow = dim(DesignMatrix)[2], "
                    + "ncol =" + Constants.ITERATIONS + ")\n";
            rCode += "colnames(coefs_" + i + ") <- coln\n";
            rCode += "y_fit"
                    + i + " <- matrix(data = NA, nrow = dim(DesignMatrix)[2], "
                    + "ncol =" + Constants.ITERATIONS + ")\n";
            rCode += "colnames(y_fit" + i + ") <- coln\n";
            rCode += "R2_" + i + " <- matrix(data=NA,nrow=1,ncol="
                    + Constants.ITERATIONS + ")\n";
            rCode += "colnames(R2_" + i + ") <- coln\n";
            rCode += "frac_" + i + " <- matrix(data=NA,nrow=1,ncol="
                    + Constants.ITERATIONS + ")\n";
            rCode += "colnames(frac_" + i + ") <- coln\n";
            rCode += "lambda_" + i + " <- matrix(data=NA,nrow=1,ncol="
                    + Constants.ITERATIONS + ")\n";
            rCode += "colnames(lambda_" + i + ") <- coln\n";
            rCode += "test_" + i + " <- matrix(data=NA,nrow="
                    + Constants.ITERATIONS + ",ncol=2)\n\n";
            rCode += "colnames(test_" + i + ") <- c(\"RMSE\",\"R2\")\n";
        }
        return rCode;
    }

    /**
     * Initialization of empty variables to store the results.
     *
     * @param analysisMethod Name of the analysis method. Allowed names are: rf,
     * en, ridge, lasso, pls, spls, pcr, univariate, & svm.
     * @return R compatible code.
     */
    protected String initializeResultObjects(String analysisMethod) {
        String rCode = "# Initialize results\n";
        for (int i = 0; i < Constants.NUMBER_FOLDS_OUTER; i++) {
            rCode += "coln <-  paste(" + i + ",seq(1:"
                    + Constants.ITERATIONS + "),sep=\"_\")\n";
            rCode += "test_" + i + " <- matrix(data=NA,nrow="
                    + Constants.ITERATIONS + ",ncol=2)\n";
            rCode += "RMSE <- paste(\"RMSE_" + i + "\",seq(1:"
                    + Constants.ITERATIONS + "),sep=\"_\")\n";
            rCode += "R2 <- paste(\"R2_" + i + "\",seq(1:"
                    + Constants.ITERATIONS + "),sep=\"_\")\n";
            rCode += "colnames(test_" + i + ") <- c(\"RMSE\",\"R2\")\n";
            rCode += "y_fit" + i + " <- matrix(data = NA, "
                    + "nrow = dim(DesignMatrix)[2], "
                    + "ncol =" + Constants.ITERATIONS + ")\n";
            rCode += "colnames(y_fit" + i + ") <- coln\n";
            rCode += "R2_" + i + " <- matrix(data=NA,nrow=1,"
                    + "ncol=" + Constants.ITERATIONS + ")\n";
            rCode += "colnames(R2_" + i + ") <- coln\n";
            switch (analysisMethod) {
                case Constants.EN:
                case Constants.RIDGE:
                case Constants.LASSO:
                    rCode += "lambda_"
                            + i + " <- matrix(data = NA, nrow = 1, ncol = "
                            + Constants.ITERATIONS + ")\n";
                    rCode += "colnames(lambda_" + i + ") <- coln\n";
                    //row 1 is always the intercept. This is always the first row.
                    rCode += "coefs_"
                            + i + " <- matrix(data = NA,"
                            + "nrow = dim(DesignMatrix)[2]+1,"
                            + "ncol=" + Constants.ITERATIONS + ")\n";
                    rCode += "colnames(coefs_" + i + ") <- coln\n";
                    rCode += "rownames(coefs_" + i
                            + ") <- c(\"intercept\",colnames(DesignMatrix))\n";
                    break;
                case Constants.PCR:
                case Constants.SPLS:
                case Constants.PLS:
                    rCode += "coefs_" + i + " <- matrix(data = NA,"
                            + "nrow = dim(DesignMatrix)[2],"
                            + "ncol=" + Constants.ITERATIONS + ")\n";
                    rCode += "colnames(coefs_" + i + ") <- coln\n";
                    rCode += "rownames(coefs_" + i + ") <- colnames(DesignMatrix)\n";
                    break;
            }
            if (analysisMethod.equals(Constants.SPLS)) {
                rCode += "eta_" + i + " <- matrix(data = NA, nrow = 1, ncol ="
                        + Constants.ITERATIONS + ")\n";
                rCode += "colnames(eta_" + i + ") <- coln\n";
                rCode += "K_" + i + " <- matrix(data = NA, nrow = 1, ncol ="
                        + Constants.ITERATIONS + ")\n";
                rCode += "colnames(K_" + i + ") <- coln\n";
            }
            if (analysisMethod.equals(Constants.EN)) {
                rCode += "frac_" + i + " <- matrix(data=NA,nrow=1,ncol="
                        + Constants.ITERATIONS + ")\n";
                rCode += "colnames(frac_" + i + ") <- coln\n";
            }
            if (analysisMethod.equals(Constants.PCR)
                    || analysisMethod.equals(Constants.PLS)) {
                rCode += "opt_comp_" + i + " <- matrix(data = NA, nrow = 1, "
                        + "ncol =" + Constants.ITERATIONS + ")\n";
                rCode += "colnames(opt_comp_" + i + ") <- coln\n";
            }
            if (analysisMethod.equals(Constants.RF)) {
                rCode += "mtry_" + i + " <- matrix(data = NA, nrow = 1, ncol = "
                        + Constants.ITERATIONS + ")\n";
                rCode += "colnames(mtry_" + i + ") <- coln\n";
                rCode += "imp_" + i + " <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = "
                        + Constants.ITERATIONS + ")\n";
                rCode += "rownames(imp_" + i + ") <- colnames(DesignMatrix)\n";
                rCode += "colnames(imp_" + i + ") <- coln\n";
            }
            if (analysisMethod.equals(Constants.SVM)) {
                rCode += "tune_cost_" + i + " <- matrix(data = NA, "
                        + "nrow = dim(DesignMatrix)[2], "
                        + "ncol = " + Constants.ITERATIONS + ")\n";
                rCode += "colnames(tune_cost_" + i + ") <- coln\n";
                rCode += "tune_sigma_" + i + " <- matrix(data = NA, "
                        + "nrow = dim(DesignMatrix)[2], "
                        + "ncol = " + Constants.ITERATIONS + ")\n";
                rCode += "colnames(tune_sigma_" + i + ") <- coln\n";
            }
            rCode += "\n";
        }
        return rCode;
    }

    /**
     * Analysis method specific run code.
     *
     * @param responseVariable Name of the response variable.
     * @return R compatible code.
     */
    //Note: univariate has the analysis implemented here, while most methods
    //only return the name of the analysis.
    protected String getAnalysis(String responseVariable) {
        String rCode = "# Perform analysis\n";
        return rCode;
    }

    /**
     * Generic analysis method run code.
     *
     * @param analysisMethod Which Analysis should we run?
     * @param responseVariable Name of the response variable.
     * @return R compatible code.
     */
    protected String getAnalysis(String analysisMethod, String responseVariable) {
        String rCode = "# Analysis Loop \n";//TODO: generalize
        rCode += "for (index in 1:" + Constants.ITERATIONS + ") {\n";
        rCode += getTrainingSets(responseVariable);
        // innerLoop = how many times to do the inner loop cross validation.
        // The NUMBER_FOLDS_INNER reflects how the test / predictor subsets
        // are made! 10 means automatically 10 % / 90 % while 20 means 5% / 95%.
        rCode += "  innerLoop <- trainControl(method = \"cv\", number = "
                + Constants.NUMBER_FOLDS_INNER + ")\n";
        for (int i = 0; i < Constants.NUMBER_FOLDS_OUTER; i++) {
            int j = i + 1;
            switch (analysisMethod) {
                case Constants.EN:
                    rCode += "      ## Elastic Net Round: " + j + "\n";
                    break;
                case Constants.LASSO:
                    rCode += "      ## Lasso Net Round: " + j + "\n";
                    break;
                case Constants.RIDGE:
                    rCode += "      ## Ridge Round: " + j + "\n";
                    break;
                case Constants.SVM:
                    rCode += "      ## SVN Round: " + j + "\n";
                    break;
                case Constants.SPLS:
                    rCode += "      ## SPLS Round: " + j + "\n";
                    break;
                case Constants.PCR:
                    rCode += "      ## PCR Round: " + j + "\n";
                    break;
                case Constants.PLS:
                    rCode += "      ## PLS Round: " + j + "\n";
                    break;
                case Constants.RF:
                    rCode += "      ## RF Round: " + j + "\n";
                    break;
            }
            rCode += "      ## Create predictor and response test and "
                    + "training sets\n";
            //TODO: test set can be an separate set of sheets!
            rCode += "      predictorTrainSet" + i
                    + " <- DesignMatrix[trainingSet" + i + ",]\n";
            // outer test set
            rCode += "      predictorTestSet" + i
                    + " <- DesignMatrix[-trainingSet" + i + ",]\n";
            //FIXME: predictorSets are scaled, responseSets are not scaled??
            rCode += "      responseTrainSet" + i + " <- dataSet$"
                    + responseVariable.trim() + "[trainingSet" + i + "]\n";
            rCode += "      responseTestSet" + i + " <- dataSet$"
                    + responseVariable.trim() + "[-trainingSet" + i + "]\n";
            //TODO: write trainingset to a file.
            rCode += "      ## Parameter optimalization\n";
            switch (analysisMethod) {
                case Constants.EN:
                    rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"glmnet\", metric = \"RMSE\", tuneLength = 10, trControl = innerLoop)\n";
                    break;
                case Constants.LASSO:
                    //TODO: function of tuneGrid? If there is a default on the other methods, perhaps we should code them anyway?
                    // tunegrid:
                    // .alpha depens on the method
                    //FIXME: seq(0, 1, by = 0.1) -> lambda (see code chris)
                    rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"glmnet\", metric = \"RMSE\", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 1, by = 0.1), .alpha = 1), trControl = innerLoop)\n";
                    break;
                case Constants.SVM:
                    rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"svmRadial\", metric = \"RMSE\", tuneLength = 10, trControl = innerLoop)\n";
                    break;
                case Constants.PLS:
                    rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"pls\", metric = \"RMSE\", tuneLength = 10, trControl = innerLoop)\n";
                    break;
                case Constants.RF:
                case Constants.SPLS:
                case Constants.RIDGE:
                    if (Constants.MAX_NUMBER_CPU > 2) {
                        int workerCount = Constants.MAX_NUMBER_CPU - 1;
                        //TODO: start in the first itteration only? Then, move if statement to R level
                        //Required additional packages: foreach, iterators, codetools, Rmpi
                        rCode += "      library(doMPI)\n";
                        // there is also an: maxcores= parameter on startMPIcluster
                        rCode += "      cl <- startMPIcluster(count = " + workerCount + ", verbose = TRUE)\n";
                        rCode += "      registerDoMPI(cl)\n";
                    }
                    switch (analysisMethod) {
                        case (Constants.RF):
                            rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"rf\", metric = \"RMSE\", tuneLength = 10, trControl = innerLoop)\n";
                            break;
                        case (Constants.SPLS):
                            rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"spls\", metric = \"RMSE\", tuneLength = 10, trControl = innerLoop)\n";
                            break;
                        case (Constants.RIDGE):
                            //TODO: tuneGrid = data.frame(.lambda = seq(0, 100, by = 0.1) <- can this be replaced by lambda
                            rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"glmnet\", metric = \"RMSE\", tuneLength = 10, tuneGrid = data.frame(.lambda = lambda, .alpha = 0), trControl = innerLoop)\n";
                            break;
                    }

                    if (Constants.MAX_NUMBER_CPU > 2) {
                        //TODO: stop in the last itteration only?
                        rCode += "      closeCluster(cl)\n";
                    }
                    break;
                case Constants.PCR:
                    // tuneLenght is an arbitrary value. Can be optomized by method. Now just choosen a value. 10 is a default of the method. PCR somtimes requires more.
                    // if you choose 10, you cannot get more components than 10?
                    rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"pcr\", metric = \"RMSE\", tuneLength = 50, trControl = innerLoop)\n";
                    break;
            }
            //TODO: are the different methods to obtain y_fit necessary?
            //TODO: what is the (different) meaning for coefs? contains .lamda, .k or .ncomp depending on the method.
            //TODO: why somethimes type="response" and other times type="coefficient" in predecit function?
            //TODO: overall. what is the impact of the difference used within the functions on the overall comparability.
            //parameter optimalization & back-rediction of the outer training set and coefficients under that optimized model.
            switch (analysisMethod) {
                case Constants.EN:
                case Constants.LASSO:
                case Constants.RIDGE:
                    switch (analysisMethod) {
                        case Constants.EN:
                            rCode += "      frac_" + i + "[, index] <- fit_" + i + "$finalModel$tuneValue$alpha\n";
                            rCode += "      lambda_" + i + "[, index] <- fit_" + i + "$finalModel$tuneValue$lambda\n";
                            break;
                        case Constants.LASSO:
                        case Constants.RIDGE:
                            rCode += "      lambda_" + i + "[, index] <- fit_" + i + "$bestTune$lambda\n";
                            break;
                    }
                    rCode += "      coefs_" + i + "[, index] <- as.matrix(coef(fit_" + i + "$finalModel, s = fit_" + i + "$finalModel$tuneValue$lambda))\n";
                    //predection on outer training set.
                    rCode += "      preds_" + i + " <- predict(fit_" + i + "$finalModel, newx = predictorTrainSet" + i + ", s = fit_" + i + "$finalModel$tuneValue$lambda, type = \"response\")\n";
                    rCode += "      y_fit_" + i + " <- preds_" + i + "[, 1]\n";
                    break;
                case Constants.PCR:
                case Constants.PLS:
                    rCode += "      coefs_" + i + "[, index] <- coef(fit_" + i + "$finalModel, ncomp = fit_" + i + "$finalModel$tuneValue$ncomp)\n";
                    rCode += "      y_fit_" + i + " <- predict(fit_" + i + "$finalModel, ncomp = fit_" + i + "$finalModel$tuneValue$ncomp)\n";
                    rCode += "      opt_comp_" + i + "[, index] <- fit_" + i + "$finalModel$tuneValue$ncomp\n";
                    break;
                case Constants.RF:
                    rCode += "      y_fit_" + i + " <- predict(fit_" + i + "$finalModel)\n";
                    rCode += "      mtry_" + i + "[, index] <- fit_" + i + "$finalModel$tuneValue$mtry\n";
                    rCode += "      imp_" + i + "[, index] <- fit_" + i + "$finalModel$importance\n";
                    break;
                case Constants.SVM:
                    rCode += "      y_fit_" + i + " <- predict(fit_" + i + "$finalModel)\n";
                    rCode += "      tune_cost_" + i + "[, index] <- fit_" + i + "$bestTune$C\n";
                    rCode += "      tune_sigma_" + i + "[, index] <- fit_" + i + "$bestTune$sigma\n";
                    break;
                case Constants.SPLS:
                    rCode += "      y_fit_" + i + " <- predict(fit_" + i + "$finalModel)\n";
                    rCode += "      coefs_" + i + "[, index] <- predict(fit_" + i + "$finalModel, type = \"coefficient\", fit_" + i + "$finalModel$tuneValue$eta, fit_" + i + "$finalModel$tuneValue$K)\n";
                    rCode += "      K_" + i + "[, index] <- fit_" + i + "$finalModel$tuneValue$K\n";
                    rCode += "      eta_" + i + "[, index] <- fit_" + i + "$finalModel$tuneValue$eta\n";
                    break;
            }
            //R2 calculated on the outer training set.
            rCode += "      R2_" + i + "[, index] <- (cor(responseTrainSet" + i + ", y_fit_" + i + ")^2) * 100\n";//TODO: On how many samples is this R2 calculated?
            rCode += "      ## Outer test set.\n";
            //Outer test set
            switch (analysisMethod) {
                case Constants.EN:
                case Constants.LASSO:
                case Constants.RIDGE:
                    rCode += "      bhModels_" + i + " <- list(glmnet = fit_" + i + ")\n";
                    break;
                case Constants.PCR:
                    rCode += "      bhModels_" + i + " <- list(pcr = fit_" + i + ")\n";
                    break;
                case Constants.PLS:
                    rCode += "      bhModels_" + i + " <- list(pls = fit_" + i + ")\n";
                    break;
                case Constants.RF:
                    rCode += "      bhModels_" + i + " <- list(rf = fit_" + i + ")\n";
                    break;
                case Constants.SVM:
                    rCode += "      bhModels_" + i + " <- list(svmRadial = fit_" + i + ")\n";
                    break;
                case Constants.SPLS:
                    rCode += "      bhModels_" + i + " <- list(spls = fit_" + i + ")\n";
                    break;
            }
            rCode += "      allPred_" + i + " <- extractPrediction(bhModels_" + i + ", testX = predictorTestSet" + i + ", testY = responseTestSet" + i + ")\n";
            rCode += "      testPred_" + i + " <- subset(allPred_" + i + ", dataType == \"Test\")\n";
            //Contins MSEP and corresponding R2 value for outer test data. Only on 9 individuals in CxE flesh test set!!
            rCode += "      sorted_" + i + " <- as.matrix(by(testPred_" + i + ", list(model = testPred_" + i + "$model), function(x) postResample(x$pred, x$obs)))\n";
            //test contains the MSEP and fraction
            //TODO: colnames
            switch (analysisMethod) {
                case Constants.EN:
                case Constants.LASSO:
                case Constants.RIDGE:
                    rCode += "      test_" + i + "[index, ] <- sorted_" + i + "[, 1]$glmnet\n\n";
                    break;
                case Constants.PCR:
                    rCode += "      test_" + i + "[index, ] <- sorted_" + i + "[, 1]$pcr\n\n";
                    break;
                case Constants.PLS:
                    rCode += "      test_" + i + "[index, ] <- sorted_" + i + "[, 1]$pls\n\n";
                    break;
                case Constants.RF:
                    rCode += "      test_" + i + "[index, ] <- sorted_" + i + "[, 1]$rf\n\n";
                    break;
                case Constants.SVM:
                    rCode += "      test_" + i + "[index, ] <- sorted_" + i + "[, 1]$svm\n\n";
                    break;
                case Constants.SPLS:
                    rCode += "      test_" + i + "[index, ] <- sorted_" + i + "[, 1]$spls\n\n";
                    break;
            }
        }
        //TODO: Calculate R2, for the previous sets, after finishing this 10 folds? Combine all 10 testPred_i and
        rCode += "}\n\n";
        return rCode;
    }

    /**
     * Now perform once more a single loop cross-validation to choose optimized
     * parameters for the final model for the whole data set.
     *
     * @return R compatible code.
     */
    protected String getFinalAnalysisWithOptimizedParamterers() {
        String rCode = "# Final Analysis\n";
        //TODO implement
        //TODO: Method specific?
        return rCode;

    }

    /**
     * Method that combines the result sets.
     *
     * @return R compatible code.
     */
    protected String combineResults() {
        String rCode = "# Combine results\n";
        String trainCoeff = "Train_Coeff <- cbind(";
        String trainR2 = "Train_R2 <- cbind(";
        String test = "methodResults <- cbind(";

        for (int i = 0; i < Constants.NUMBER_FOLDS_OUTER; i++) {
            trainCoeff += "coefs_" + i;
            trainR2 += "R2_" + i;
            test += "test_" + i;
            if (i < 9) {
                trainCoeff += ", ";
                trainR2 += ", ";
                test += ", ";
            }
        }
        return rCode + trainCoeff + ")\n" + trainR2 + ")\n" + test + ")\n\n";
    }

    /**
     * Calculate the row average and SD for each row in the data matrix. Some
     * methods calculates an intercept, this function will omit this from the
     * results.
     *
     * @return R compatible code fragment.
     */
    protected String getRowMeansAndSD() {
        String rCode = "# Get row means, SD and (absolute) rank\n";
        //dataSet contains predictor column. So, function == trye when the
        //Train_Coef also contains the intercept.
        rCode += "if (dim(dataSet)[2] == dim(Train_Coeff)[1])\n";
        rCode += "{\n";
        //if true: remove the column with the response value only
        rCode += "  means<-apply(Train_Coeff[-1,],1,mean)\n";
        rCode += "  sd<-apply(Train_Coeff[-1,],1,sd)\n";
        rCode += "  ra<-rank(abs(apply(Train_Coeff[-1,],1,mean)))\n";
        rCode += "} else {\n";
        //if false: do nothing TODO: this is not an really safe approach.
        //The difference should be 1. Test for this?
        rCode += "  means<-apply(Train_Coeff,1,mean)\n";
        rCode += "  sd<-apply(Train_Coeff,1,sd)\n";
        rCode += "  ra<-rank(abs(apply(Train_Coeff,1,mean)))\n";
        rCode += "}\n";
        rCode += "Train_Coeff_Summary <- cbind (means,sd,ra)\n";
        return rCode;
    }

    /**
     * Write the results to a csv file.
     *
     * @return R compatible code.
     * @deprecated Write to SQLite DB.
     */
    protected String writeResultsToDisk() {
        String rCode = "# Write results to disk\n";
        return rCode;
    }

    /**
     * Write the results to a SQLite database.
     *
     * @return R compatible code.
     */
    protected String writeResultsToDB() {
        String rCode = "# Write results to the SQLite database\n";
        return rCode;
    }

    /**
     * Predict the response using a new set of predictors.
     *
     * @param analysisMethod Name of the method for which to predict the
     * response.
     * @return R compatible code.
     */
    protected String predictResponseFromNew(String analysisMethod) {
        String rCode = "# Predict response for a new set of predictors.\n";
        //Load the data sheet.

        //Get the optimized parameters for the model.
        //Calculate the new responses and safe them to a file.
        return rCode;
    }

    /**
     * Generic run script.
     *
     * @param responseVariable Name of the response variable.
     * @param methodName Name of the method for which to create the script.
     * @param permutation Is this an permutation or not?
     * @return the complete R compatible script for the selected method.
     */
    public String getAnalysisScript(String responseVariable, String methodName,
            boolean permutation) {
        String rScript = "# Concatenating analisis script\n";
        //TODO: read resource bundle for last update date?
        rScript += getRequiredLibraries();
        //Do not use checkResponseName for loading: SQLite handles this fine.
        rScript += loadPredictorAndResponseDataSheets(responseVariable);
        rScript += writeRImage();
        if (permutation == true) {
            rScript += getDataset(true);
        } else {
            rScript += getDataset(false);
        }
        if (methodName.equals(Constants.RIDGE)) {
            rScript += getLambda();
        }
        rScript += handleMissingData();
        rScript += preProcessMatrix(checkResponseName(responseVariable));
        rScript += initializeResultObjects();
        rScript += getAnalysis(checkResponseName(responseVariable));
        //TODO: run analysis once more with determined optimum.
        rScript += getFinalAnalysisWithOptimizedParamterers();
        rScript += combineResults(); //should we still use this?
        rScript += getRowMeansAndSD(); //should we still use this?

        //rScript += writeRImage();
        //TODO: fix the following error!
//Error in save.image(file = "test.RData", safe = TRUE) :
//image could not be renamed and is left in test.RDataTmp
//In addition: Warning message:
//In file.rename(outfile, file) :
//cannot rename file 'test.RDataTmp' to 'test.RData', reason 'No such file or directory'
        rScript += writeResultsToDB();
        rScript += writeRImage();
        return rScript;
    }

    /**
     * Generic run script.
     *
     * @param responseVariable Name of the response variable.
     * @return the complete R compatible script for the selected method.
     */
    public String getAnalysisScript(String responseVariable) {
        return getAnalysisScript(responseVariable, "none", false);
    }

    /**
     * Generic run script.
     *
     * @param responseVariable Name of the response variable.
     * @param methodName Name of the method to run.
     * @return the complete R compatible script for the selected method.
     */
    public String getAnalysisScript(String responseVariable, String methodName) {
        return getAnalysisScript(responseVariable, methodName, false);
    }

    /**
     * Check if the response name starts with an integer. If yes, part of the R
     * code will not work, unless '' are introduced.
     *
     * @param responseName Name of the response variable
     * @return checked response variable.
     */
    protected String checkResponseName(String responseName) {
        //If the response starts with a number, R chrashes!
        String tmp = responseName.substring(0, 1);
        boolean startWithInteger = true;
        try {
            Integer.parseInt(tmp);
        } catch (NumberFormatException e) {
            startWithInteger = false;
        }
        if (startWithInteger == true) {
            responseName = "'" + responseName + "'";
        }
        return responseName;
    }
}

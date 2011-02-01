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
package nl.wur.plantbreeding.omicsfusion.methods;

import java.util.HashMap;
import java.util.logging.Logger;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;
//FIXME: implement as abstract? Check manuals.
//TODO: if the datasets contains not recognized NA values, we get an error that RSME is not valid for train method.

/**
 * Generic Analysis class for the omicsFusion pipeline. Each method needs to implement its own specification, but might inherit code from this class.
 * @author Richard Finkers
 * @version 1.0
 */
public class Analysis {

    /** A logger */
    private static final Logger LOG = Logger.getLogger(Analysis.class.getName());

    Analysis() {
    }

    /**
     * Loads The Excel sheets from the file system.
     * @param excelSheets The names of the predictor and response excel sheets.
     * @return R program code.
     */
    protected String loadPredictorAndResponseDataSheets(HashMap<String, String> excelSheets) {
        String rCode = "# Load the generic R libraries \n";
        rCode += "# Load the excel sheets\n";
        //TODO: test how generic the CSV import works. Can we test for decimal point, etc?
        if (excelSheets.get("predictor").contains("csv")) {
            rCode += "predictorSheet <- read.csv2(\"" + excelSheets.get("predictor") + "\", header=TRUE,sep=\";\",dec=\".\")\n";
        } else {
            rCode += "predictorSheet <- read.xls(\"" + excelSheets.get("predictor") + "\")\n";
        }
        if (excelSheets.get("response").contains("csv")) {
            rCode += "responseSheet  <- read.csv2(\"" + excelSheets.get("response") + "\", header=TRUE,sep=\";\",dec=\".\")\n";
        } else {
            rCode += "responseSheet  <- read.xls(\"" + excelSheets.get("response") + "\")\n";
        }
        //Concatenate the final dataSet, containing 1 response and a predictor matrix
        //The rownames are set acoording to the sample names
        rCode += "dataSet=cbind(responseSheet[2],predictorSheet[-1])\n";
        //FIXME: set rownames
        //rCode += "rownames(dataSet) <- responseSheet[1]\n";
        //TODO: better na.omit strategies. Imputation? Added in addition to the script of Animesh.
        //option: replace NA with row mean.
        rCode += "dataSet=na.omit(dataSet)\n\n";
        return rCode;
    }

    protected String loadPredictResponseDataSheet(HashMap<String, String> excelSheets) {
        String rCode = "# Load the PredictResponse data sheet.\n";
        //TODO: test how generic the CSV import works. Can we test for decimal point, etc?
        if (excelSheets.get("predictResponse") != null) {
            if (excelSheets.get("predictor").contains("csv")) {
                rCode += "predictResponse <- read.csv2(\"" + excelSheets.get("predictResponse") + "\", header=TRUE,sep=\";\",dec=\".\")\n";
            } else {
                rCode += "predictResponse <- read.xls(\"" + excelSheets.get("predictResponse") + "\")\n";
            }
        } else {
            //TODO: error handling
        }

        //TODO: file validation. Should also be done during upload

        return rCode;
    }

    /**
     * Preprocess the data matrix.
     * @return R program code.
     */
    protected String preProcessMatrix() {
        String rCode = "# Pre process data matrix\n";
        //Let's Hack. TODO: Isn't there another way than overriding the trait name?
        rCode += "nameVector<-colnames(dataSet)\n";
        rCode += "traitName<-nameVector[1]\n";
        rCode += "write.table(file=\"analysis.txt\", traitName)\n";
        rCode += "nameVector[1]<-\"Response\"\n";
        rCode += "colnames(dataSet)<-nameVector\n";
        //Ok, back to normal
        rCode += "DesignMatrix <- model.matrix(dataSet$Response ~ . - 1, dataSet)\n";
        //TODO: different centering and autoscaling algorithms? User selectable.
        rCode += "DesignMatrix <- scale(DesignMatrix)\n\n";
        return rCode;
    }

    /**
     * Get the training sets.
     * @return R program code.
     */
    protected String getTrainingSets() {
        //TODO: we might get different training and test sets profided via the dataset upload.
        String rCode = " # Create training sets\n";
        //TODO: dataSet[1] coding does not work. currently using unlist(dataset[1]). Does this work, or should we code it now to dataSet$Response?
        //Can do different procedures. E.G. bootstraps, resampling, leaf-one-out, etc. User selectable?
        rCode += "  inTrainingSet <- createFolds(dataSet$Response, k = " + Constants.NUMBER_FOLDS_OUTER + ", list = TRUE, returnTrain = T)\n";
        for (int i = 0; i < Constants.NUMBER_FOLDS_OUTER; i++) {
            int j = i + 1;//R object contains 1-10 instead of 0-9!
            if (j < 10) {
                rCode += "  trainingSet" + i + " <- inTrainingSet$Fold0" + j + "\n";
            } else {
                rCode += "  trainingSet" + i + " <- inTrainingSet$Fold" + j + "\n";
            }
        }
        rCode += "\n";
        return rCode;
    }

    /**
     * Load the required libraries for this method.
     * @return R compatible code.
     */
    protected String getRequiredLibraries() {
        String rCode = "# Load requried generic libraries\n";
        rCode += "library(gdata)\n";//Used to load excel sheets
        rCode += "library(lattice)\n";//dependencies of caret
        rCode += "library(reshape)\n";//dependencies of caret
        rCode += "library(plyr)\n";//dependencies of caret
        rCode += "library(caret)\n";//Used for createfolds in training set
        //rCode += "library(doMPI)\n\n";//train() from caret can use mpi. We use this for RF and SPSL
        return rCode;
    }

    /**
     * Initialization of the most commonly used empty variables to store the results. Most methods do, however, require their own initialization.
     * @return R compatible code.
     */
    protected String initializeResultObjects() {
        //TODO: how is this method used?
        String rCode = "# Initialize results and add column names\n";
        for (int i = 0; i < Constants.NUMBER_FOLDS_OUTER; i++) {
            rCode += "coln <-  paste(" + i + ",seq(1:" + Constants.ITERATIONS + "),sep=\"_\")\n";
            rCode += "coefs_" + i + " <- matrix(data = NA,nrow = dim(DesignMatrix)[2], ncol =" + Constants.ITERATIONS + ")\n";
            rCode += "colnames(coefs_" + i + ") <- coln\n";
            rCode += "y_fit" + i + " <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol =" + Constants.ITERATIONS + ")\n";
            rCode += "colnames(y_fit" + i + ") <- coln\n";
            rCode += "R2_" + i + " <- matrix(data=NA,nrow=1,ncol=" + Constants.ITERATIONS + ")\n";
            rCode += "colnames(R2_" + i + ") <- coln\n";
            rCode += "frac_" + i + " <- matrix(data=NA,nrow=1,ncol=" + Constants.ITERATIONS + ")\n";
            rCode += "colnames(frac_" + i + ") <- coln\n";
            rCode += "lambda_" + i + " <- matrix(data=NA,nrow=1,ncol=" + Constants.ITERATIONS + ")\n";
            rCode += "colnames(lambda_" + i + ") <- coln\n";
            rCode += "test_" + i + " <- matrix(data=NA,nrow=" + Constants.ITERATIONS + ",ncol=2)\n\n";
            rCode += "colnames(test_" + i + ") <- c(\"RMSE\",\"R2\")\n";
        }
        return rCode;
    }

    /**
     * Initialization of empty variables to store the results.
     * @param analysisMethod Name of the analysis method. Allowed names are: rf, en, ridge, lasso, pls, spls, pcr, univariate, & svm.
     * @return R compatible code.
     */
    protected String initializeResultObjects(String analysisMethod) {
        String rCode = "# Initialize results\n";
        for (int i = 0; i < Constants.NUMBER_FOLDS_OUTER; i++) {
            rCode += "coln <-  paste(" + i + ",seq(1:" + Constants.ITERATIONS + "),sep=\"_\")\n";
            rCode += "test_" + i + " <- matrix(data=NA,nrow=" + Constants.ITERATIONS + ",ncol=2)\n";
            rCode += "RMSE <- paste(\"RMSE_" + i + "\",seq(1:" + Constants.ITERATIONS + "),sep=\"_\")\n";
            rCode += "R2 <- paste(\"R2_" + i + "\",seq(1:" + Constants.ITERATIONS + "),sep=\"_\")\n";
            rCode += "colnames(test_" + i + ") <- c(\"RMSE\",\"R2\")\n";
            rCode += "y_fit" + i + " <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol =" + Constants.ITERATIONS + ")\n";
            rCode += "colnames(y_fit" + i + ") <- coln\n";
            rCode += "R2_" + i + " <- matrix(data=NA,nrow=1,ncol=" + Constants.ITERATIONS + ")\n";
            rCode += "colnames(R2_" + i + ") <- coln\n";
            if (analysisMethod.equals("en") || analysisMethod.equals("ridge") || analysisMethod.equals("lasso")) {
                rCode += "lambda_" + i + " <- matrix(data = NA, nrow = 1, ncol = " + Constants.ITERATIONS + ")\n";
                rCode += "colnames(lambda_" + i + ") <- coln\n";
                //row 1 is always the intercept. This is always the first row.
                rCode += "coefs_" + i + " <- matrix(data = NA,nrow = dim(DesignMatrix)[2]+1,ncol=" + Constants.ITERATIONS + ")\n";
                rCode += "colnames(coefs_" + i + ") <- coln\n";
                rCode += "rownames(coefs_" + i + ") <- c(\"intercept\",colnames(DesignMatrix))\n";
            } else if (analysisMethod.equals("pcr") || analysisMethod.equals("spls") || analysisMethod.equals("pls")) {
                rCode += "coefs_" + i + " <- matrix(data = NA,nrow = dim(DesignMatrix)[2],ncol=" + Constants.ITERATIONS + ")\n";
                rCode += "colnames(coefs_" + i + ") <- coln\n";
                rCode += "rownames(coefs_" + i + ") <- colnames(DesignMatrix)\n";
            }
            if (analysisMethod.equals("spls")) {
                rCode += "eta_" + i + " <- matrix(data = NA, nrow = 1, ncol =" + Constants.ITERATIONS + ")\n";
                rCode += "colnames(eta_" + i + ") <- coln\n";
                rCode += "K_" + i + " <- matrix(data = NA, nrow = 1, ncol =" + Constants.ITERATIONS + ")\n";
                rCode += "colnames(K_" + i + ") <- coln\n";
            }
            if (analysisMethod.equals("en")) {
                rCode += "frac_" + i + " <- matrix(data=NA,nrow=1,ncol=" + Constants.ITERATIONS + ")\n";
                rCode += "colnames(frac_" + i + ") <- coln\n";
            }
            if (analysisMethod.equals("pcr") || analysisMethod.equals("pls")) {
                rCode += "opt_comp_" + i + " <- matrix(data = NA, nrow = 1, ncol =" + Constants.ITERATIONS + ")\n";
                rCode += "colnames(opt_comp_" + i + ") <- coln\n";
            }
            if (analysisMethod.equals("rf")) {
                rCode += "mtry_" + i + " <- matrix(data = NA, nrow = 1, ncol = " + Constants.ITERATIONS + ")\n";
                rCode += "colnames(mtry_" + i + ") <- coln\n";
                rCode += "imp_" + i + " <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = " + Constants.ITERATIONS + ")\n";
                rCode += "rownames(imp_" + i + ") <- colnames(DesignMatrix)\n";
                rCode += "colnames(imp_" + i + ") <- coln\n";
            }
            if (analysisMethod.equals("svm")) {
                rCode += "tune_cost_" + i + " <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = " + Constants.ITERATIONS + ")\n";
                rCode += "colnames(tune_cost_" + i + ") <- coln\n";
                rCode += "tune_sigma_" + i + " <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = " + Constants.ITERATIONS + ")\n";
                rCode += "colnames(tune_sigma_" + i + ") <- coln\n";
            }
            rCode += "\n";
        }
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
     * Generic analysis method run code.
     * @param analysisMethod Which Analysis should we run?
     * @return R compatible code.
     */
    protected String getAnalysis(String analysisMethod) {
        String rCode = "# Analysis Loop \n";//TODO: generalize
        rCode += "for (index in 1:" + Constants.ITERATIONS + ") {\n";
        rCode += getTrainingSets();
        // innerLoop = how many times to do the inner loop cross validation.
        // The NUMBER_FOLDS_INNER reflects how the test / predictor subsets are made! 10 means automatically 10 % / 90 % while 20 means 5% / 95%.
        rCode += "  innerLoop <- trainControl(method = \"cv\", number = " + Constants.NUMBER_FOLDS_INNER + ")\n";
        for (int i = 0; i < Constants.NUMBER_FOLDS_OUTER; i++) {
            int j = i + 1;
            if (analysisMethod.equals("en")) {
                rCode += "      ## Elastic Net Round: " + j + "\n";
            } else if (analysisMethod.equals("lasso")) {
                rCode += "      ## Lasso Net Round: " + j + "\n";
            } else if (analysisMethod.equals("ridge")) {
                rCode += "      ## Ridge Round: " + j + "\n";
            } else if (analysisMethod.equals("svm")) {
                rCode += "      ## SVN Round: " + j + "\n";
            } else if (analysisMethod.equals("spls")) {
                rCode += "      ## SPLS Round: " + j + "\n";
            } else if (analysisMethod.equals("pcr")) {
                rCode += "      ## PCR Round: " + j + "\n";
            } else if (analysisMethod.equals("pls")) {
                rCode += "      ## PLS Round: " + j + "\n";
            } else if (analysisMethod.equals("rf")) {
                rCode += "      ## RF Round: " + j + "\n";
            }
            rCode += "      ## Create predictor and response test and training sets\n";
            //TODO: test set can be an separate set of sheets!
            rCode += "      predictorTrainSet" + i + " <- DesignMatrix[trainingSet" + i + ",]\n";
            rCode += "      predictorTestSet" + i + " <- DesignMatrix[-trainingSet" + i + ",]\n";// outer test set
            //FIXME: predictorSets are scaled, responseSets are not scaled??
            rCode += "      responseTrainSet" + i + " <- dataSet$Response[trainingSet" + i + "]\n";//dataSet[1] does not work. Selects potentially wrong columns
            rCode += "      responseTestSet" + i + " <- dataSet$Response[-trainingSet" + i + "]\n";//dataSet[1] does not work
            //TODO: write trainingset to a file.
            rCode += "      ## Parameter optimalization\n";
            if (analysisMethod.equals("en")) {
                rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"glmnet\", metric = \"RMSE\", tuneLength = 10, trControl = innerLoop)\n";
            } else if (analysisMethod.equals("lasso")) {
                //TODO: function of tuneGrid? If there is a default on the other methods, perhaps we should code them anyway?
                // tunegrid:
                // .alpha depens on the method
                rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"glmnet\", metric = \"RMSE\", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 1, by = 0.1), .alpha = 1), trControl = innerLoop)\n";
            } else if (analysisMethod.equals("ridge")) {
                rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"glmnet\", metric = \"RMSE\", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 100, by = 0.1), .alpha = 0), trControl = innerLoop)\n";
            } else if (analysisMethod.equals("svm")) {
                rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"svmRadial\", metric = \"RMSE\", tuneLength = 10, trControl = innerLoop)\n";
            } else if (analysisMethod.equals("pls")) {
                rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"pls\", metric = \"RMSE\", tuneLength = 10, trControl = innerLoop)\n";
            } else if (analysisMethod.equals("rf") || analysisMethod.equals("spls")) {
                if (Constants.MAX_NUMBER_CPU > 2) {
                    int workerCount = Constants.MAX_NUMBER_CPU - 1;
                    //TODO: start in the first itteration only? Then, move if statement to R level
                    //Required additional packages: foreach, iterators, codetools, Rmpi
                    rCode += "      library(doMPI)\n";
                    // there is also an: maxcores= parameter on startMPIcluster
                    rCode += "      cl <- startMPIcluster(count = " + workerCount + ", verbose = TRUE)\n";
                    rCode += "      registerDoMPI(cl)\n";
                }
                if (analysisMethod.equals("rf")) {
                    rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"rf\", metric = \"RMSE\", tuneLength = 10, trControl = innerLoop)\n";
                } else if (analysisMethod.equals("spls")) {
                    rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"spls\", metric = \"RMSE\", tuneLength = 10, trControl = innerLoop)\n";
                }
                if (Constants.MAX_NUMBER_CPU > 2) {
                    //TODO: stop in the last itteration only?
                    rCode += "      closeCluster(cl)\n";
                }
            } else if (analysisMethod.equals("pcr")) {
                // tuneLenght is an arbitrary value. Can be optomized by method. Now just choosen a value. 10 is a default of the method. PCR somtimes requires more.
                // if you choose 10, you cannot get more components than 10?
                rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"pcr\", metric = \"RMSE\", tuneLength = 50, trControl = innerLoop)\n";
            }
            //TODO: are the different methods to obtain y_fit necessary?
            //TODO: what is the (different) meaning for coefs? contains .lamda, .k or .ncomp depending on the method.
            //TODO: why somethimes type="response" and other times type="coefficient" in predecit function?
            //TODO: overall. what is the impact of the difference used within the functions on the overall comparability.
            //parameter optimalization & back-rediction of the outer training set and coefficients under that optimized model.
            if (analysisMethod.equals("en") || analysisMethod.equals("lasso") || analysisMethod.equals("ridge")) {
                if (analysisMethod.equals("en")) {
                    rCode += "      frac_" + i + "[, index] <- fit_" + i + "$finalModel$tuneValue$.alpha\n";
                    rCode += "      lambda_" + i + "[, index] <- fit_" + i + "$finalModel$tuneValue$.lambda\n";
                } else if (analysisMethod.equals("lasso") || analysisMethod.equals("ridge")) {
                    rCode += "      lambda_" + i + "[, index] <- fit_" + i + "$bestTune$.lambda\n";
                }
                rCode += "      coefs_" + i + "[, index] <- as.matrix(coef(fit_" + i + "$finalModel, s = fit_" + i + "$finalModel$tuneValue$.lambda))\n";
                //predection on outer training set.
                rCode += "      preds_" + i + " <- predict(fit_" + i + "$finalModel, newx = predictorTrainSet" + i + ", s = fit_" + i + "$finalModel$tuneValue$.lambda, type = \"response\")\n";
                rCode += "      y_fit_" + i + " <- preds_" + i + "[, 1]\n";
            } else if (analysisMethod.equals("pcr") || analysisMethod.equals("pls")) {
                rCode += "      coefs_" + i + "[, index] <- coef(fit_" + i + "$finalModel, ncomp = fit_" + i + "$finalModel$tuneValue$.ncomp)\n";
                rCode += "      y_fit_" + i + " <- predict(fit_" + i + "$finalModel, ncomp = fit_" + i + "$finalModel$tuneValue$.ncomp)\n";
                rCode += "      opt_comp_" + i + "[, index] <- fit_" + i + "$finalModel$tuneValue$.ncomp\n";
            } else if (analysisMethod.equals("rf")) {
                rCode += "      y_fit_" + i + " <- predict(fit_" + i + "$finalModel)\n";
                rCode += "      mtry_" + i + "[, index] <- fit_" + i + "$finalModel$tuneValue$.mtry\n";
                rCode += "      imp_" + i + "[, index] <- fit_" + i + "$finalModel$importance\n";
            } else if (analysisMethod.equals("svm")) {
                rCode += "      y_fit_" + i + " <- predict(fit_" + i + "$finalModel)\n";
                rCode += "      tune_cost_" + i + "[, index] <- fit_" + i + "$bestTune$.C\n";
                rCode += "      tune_sigma_" + i + "[, index] <- fit_" + i + "$bestTune$.sigma\n";
            } else if (analysisMethod.equals("spls")) {
                rCode += "      y_fit_" + i + " <- predict(fit_" + i + "$finalModel)\n";
                rCode += "      coefs_" + i + "[, index] <- predict(fit_" + i + "$finalModel, type = \"coefficient\", fit_" + i + "$finalModel$tuneValue$.eta, fit_" + i + "$finalModel$tuneValue$.K)\n";
                rCode += "      K_" + i + "[, index] <- fit_" + i + "$finalModel$tuneValue$.K\n";
                rCode += "      eta_" + i + "[, index] <- fit_" + i + "$finalModel$tuneValue$.eta\n";
            }
            //R2 calculated on the outer training set.
            rCode += "      R2_" + i + "[, index] <- (cor(responseTrainSet" + i + ", y_fit_" + i + ")^2) * 100\n";//TODO: On how many samples is this R2 calculated?
            rCode += "      ## Outer test set.\n";
            //Outer test set
            if (analysisMethod.equals("en") || analysisMethod.equals("lasso") || analysisMethod.equals("ridge")) {
                rCode += "      bhModels_" + i + " <- list(glmnet = fit_" + i + ")\n";
            } else if (analysisMethod.equals("pcr")) {
                rCode += "      bhModels_" + i + " <- list(pcr = fit_" + i + ")\n";
            } else if (analysisMethod.equals("pls")) {
                rCode += "      bhModels_" + i + " <- list(pls = fit_" + i + ")\n";
            } else if (analysisMethod.equals("rf")) {
                rCode += "      bhModels_" + i + " <- list(rf = fit_" + i + ")\n";
            } else if (analysisMethod.equals("svm")) {
                rCode += "      bhModels_" + i + " <- list(svmRadial = fit_" + i + ")\n";
            } else if (analysisMethod.equals("spls")) {
                rCode += "      bhModels_" + i + " <- list(spls = fit_" + i + ")\n";
            }
            rCode += "      allPred_" + i + " <- extractPrediction(bhModels_" + i + ", testX = predictorTestSet" + i + ", testY = responseTestSet" + i + ")\n";
            rCode += "      testPred_" + i + " <- subset(allPred_" + i + ", dataType == \"Test\")\n";
            //Contins MSEP and corresponding R2 value for outer test data. Only on 9 individuals in CxE flesh test set!!
            rCode += "      sorted_" + i + " <- as.matrix(by(testPred_" + i + ", list(model = testPred_" + i + "$model), function(x) postResample(x$pred, x$obs)))\n";
            //test contains the MSEP and fraction
            //TODO: colnames
            if (analysisMethod.equals("en") || analysisMethod.equals("lasso") || analysisMethod.equals("ridge")) {
                rCode += "      test_" + i + "[index, ] <- sorted_" + i + "[, 1]$glmnet\n\n";
            } else if (analysisMethod.equals("pcr")) {
                rCode += "      test_" + i + "[index, ] <- sorted_" + i + "[, 1]$pcr\n\n";
            } else if (analysisMethod.equals("pls")) {
                rCode += "      test_" + i + "[index, ] <- sorted_" + i + "[, 1]$pls\n\n";
            } else if (analysisMethod.equals("rf")) {
                rCode += "      test_" + i + "[index, ] <- sorted_" + i + "[, 1]$rf\n\n";
            } else if (analysisMethod.equals("svm")) {
                rCode += "      test_" + i + "[index, ] <- sorted_" + i + "[, 1]$svm\n\n";
            } else if (analysisMethod.equals("spls")) {
                rCode += "      test_" + i + "[index, ] <- sorted_" + i + "[, 1]$spls\n\n";
            }
            //TODO: cleanup of unused objects?
        }
        //TODO: Calculate R2, for the previous sets, after finishing this 10 folds? Combine all 10 testPred_i and
        rCode += "}\n\n";
        return rCode;
    }

    /**
     * Method that combines the result sets.
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
     * @return R compatible code fragment.
     */
    protected String getRowMeansAndSD() {
        String rCode = "# Get row means, SD and (absolute) rank\n";
        //dataSet contains predictor column. So, function == trye when the Train_Coef also contains the intercept.
        rCode += "if (dim(dataSet)[2] == dim(Train_Coeff)[1])\n";
        rCode += "{\n";
        //if true: remove the column with the response value only
        rCode += "  means<-apply(Train_Coeff[-1,],1,mean)\n";
        rCode += "  sd<-apply(Train_Coeff[-1,],1,sd)\n";
        rCode += "  ra<-rank(abs(apply(Train_Coeff[-1,],1,mean)))\n";
        rCode += "} else {\n";
        //if false: do nothing TODO: this is not an really safe approach. The difference should be 1. Test for this?
        rCode += "  means<-apply(Train_Coeff,1,mean)\n";
        rCode += "  sd<-apply(Train_Coeff,1,sd)\n";
        rCode += "  ra<-rank(abs(apply(Train_Coeff,1,mean)))\n";
        rCode += "}\n";
        rCode += "Train_Coeff_Summary <- cbind (means,sd,ra)\n";
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
     * Predict the response using a new set of predictors.
     * @param analysisMethod Name of the method for which to predict the response.
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
     * @param excelSheets The names of the predictor and response excel sheets.
     * @return the complete R compatible script for the selected method.
     */
    public String getAnalysisScript(HashMap<String, String> excelSheets) {
        String rScript = "# Concatenating analisis script\n";
        rScript += getRequiredLibraries();
        rScript += loadPredictorAndResponseDataSheets(excelSheets);
        rScript += preProcessMatrix();
        rScript += initializeResultObjects();
        rScript += getAnalysis();
        rScript += combineResults();
        rScript += getRowMeansAndSD();
        rScript += writeResults();
        return rScript;
    }
}

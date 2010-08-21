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

    protected String initializeResultObjects() {
        String rCode = "# Initialize results\n";
        for (int i = 0; i < Constants.NUMBERFOLDS; i++) {
            rCode += "coefs_" + i + " <- matrix(data = NA,nrow = dim(DesignMatrix)[2],ncol=" + Constants.ITERATIONS + ")\n";
            rCode += "y_fit" + i + " <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol =" + Constants.ITERATIONS + ")\n";
            rCode += "R2_" + i + " <- matrix(data=NA,nrow=1,ncol=" + Constants.ITERATIONS + ")\n";
            rCode += "frac_" + i + " <- matrix(data=NA,nrow=1,ncol=" + Constants.ITERATIONS + ")\n";
            rCode += "lambda_" + i + " <- matrix(data=NA,nrow=1,ncol=" + Constants.ITERATIONS + ")\n";
            rCode += "test_" + i + " <- matrix(data=NA,nrow=" + Constants.ITERATIONS + ",ncol=2)\n\n";
        }
        return rCode;
    }

    /**
     * Initialization of empty variables to store the results.
     * @param analysisMethod
     * @return
     */
    protected String initializeResultObjects(String analysisMethod) {
        String rCode = "# Initialize results\n";
        for (int i = 0; i < Constants.NUMBERFOLDS; i++) {
            rCode += "test_" + i + " <- matrix(data=NA,nrow=" + Constants.ITERATIONS + ",ncol=2)\n\n";
            rCode += "y_fit" + i + " <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol =" + Constants.ITERATIONS + ")\n";
            rCode += "R2_" + i + " <- matrix(data=NA,nrow=1,ncol=" + Constants.ITERATIONS + ")\n";
            if (analysisMethod.equals("en") || analysisMethod.equals("ridge") || analysisMethod.equals("lasso")) {
                rCode += "lambda_" + i + " <- matrix(data = NA, nrow = 1, ncol = " + Constants.ITERATIONS + ")\n";
                rCode += "coefs_" + i + " <- matrix(data = NA,nrow = dim(DesignMatrix)[2]+1,ncol=" + Constants.ITERATIONS + ")\n";                
            } else if (analysisMethod.equals("pcr") || analysisMethod.equals("spls") || analysisMethod.equals("pls")) {
                rCode += "coefs_" + i + " <- matrix(data = NA,nrow = dim(DesignMatrix)[2],ncol=" + Constants.ITERATIONS + ")\n";
            }
            if (analysisMethod.equals("en")) {
                rCode += "frac_" + i + " <- matrix(data=NA,nrow=1,ncol=" + Constants.ITERATIONS + ")\n";
            }
            if (analysisMethod.equals("pcr") || analysisMethod.equals("pls")) {
                rCode += "opt_comp_" + i + " <- matrix(data = NA, nrow = 1, ncol =" + Constants.ITERATIONS + ")\n";
            }
            if (analysisMethod.equals("rf")) {
                rCode += "mtry_" + i + " <- matrix(data = NA, nrow = 1, ncol = " + Constants.ITERATIONS + ")\n";
                rCode += "imp_" + i + " <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = " + Constants.ITERATIONS + ")\n";
            }
            if (analysisMethod.equals("svm")) {
                rCode += "tune_cost_" + i + " <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = " + Constants.ITERATIONS + ")\n";
                rCode += "tune_sigma_" + i + " <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = " + Constants.ITERATIONS + ")\n";
            }
            if (analysisMethod.equals("spls")) {
                rCode += "eta_" + i + " <- matrix(data = NA, nrow = 1, ncol =" + Constants.ITERATIONS + ")\n";
                rCode += "K_" + i + " <- matrix(data = NA, nrow = 1, ncol =" + Constants.ITERATIONS + ")\n";
            }
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
        String rCode = "#Elastic Net Analysis\n";
        rCode += "for (index in 1:" + Constants.ITERATIONS + ") {\n";
        rCode += getTrainingSets();
        rCode += "  con <- trainControl(method = \"cv\", number = " + Constants.NUMBERFOLDS + ")\n"; //TODO: Ask animesh if number=10 has something to do with Constants.NUMBERFOLDS
        for (int i = 0; i < Constants.NUMBERFOLDS; i++) {
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
            rCode += "      predictorTrainSet" + i + " <- DesignMatrix[trainingSet" + i + ",]\n";
            rCode += "      predictorTestSet" + i + " <- DesignMatrix[-trainingSet" + i + ",]\n";// outer test set
            rCode += "      responseTrainSet" + i + " <- dataSet$BRIX_P[trainingSet" + i + "]\n";//FIXME hardcoded. dataSet[1] does not work. Selects potentially wrong columns
            rCode += "      responseTestSet" + i + " <- dataSet$BRIX_P[-trainingSet" + i + "]\n";//FIXME hardcoded. dataSet[1] does not work
            rCode += "      ## Parameter optimalization\n";
            if (analysisMethod.equals("en")) {
                rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"glmnet\", metric = \"RMSE\", tuneLength = 10, trControl = con)\n";
            } else if (analysisMethod.equals("lasso")) {
                rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"glmnet\", metric = \"RMSE\", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 1, by = 0.1), .alpha = 1), trControl = con)\n";
            } else if (analysisMethod.equals("ridge")) {
                rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"glmnet\", metric = \"RMSE\", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 100, by = 0.1), .alpha = 0), trControl = con)\n";
            } else if (analysisMethod.equals("svm")) {
                rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"svmRadial\", metric = \"RMSE\", tuneLength = 10, trControl = con)\n";
            } else if (analysisMethod.equals("spls")) {
                rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"spls\", metric = \"RMSE\", tuneLength = 10, trControl = con)\n";
            } else if (analysisMethod.equals("pcr")) {
                rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"pcr\", metric = \"RMSE\", tuneLength = 50, trControl = con)\n";
            } else if (analysisMethod.equals("pls")) {
                rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"pls\", metric = \"RMSE\", tuneLength = 50, trControl = con)\n";
            } else if (analysisMethod.equals("rf")) {
                rCode += "      fit_" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"rf\", metric = \"RMSE\", tuneLength = 50, trControl = con)\n";
            }
            if (analysisMethod.equals("en")) {
                rCode += "      frac_" + i + "[, index] <- fit_" + i + "$finalModel$tuneValue$.alpha\n";
            }
            if (analysisMethod.equals("en") || analysisMethod.equals("ridge") || analysisMethod.equals("lasso")) {
                rCode += "      lambda_" + i + "[, index] <- fit_" + i + "$finalModel$tuneValue$.lambda\n";
            }
            if (analysisMethod.equals("en") || analysisMethod.equals("lasso") || analysisMethod.equals("ridge")) {
                rCode += "      coefs_" + i + "[, index] <- as.matrix(coef(fit_" + i + "$finalModel, s = fit_" + i + "$finalModel$tuneValue$.lambda))\n";
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
            rCode += "      R2_" + i + "[, index] <- (cor(responseTrainSet" + i + ", y_fit_" + i + ")^2) * 100\n";//TODO: On how many samples is this R2 calculated?
            rCode += "      ## Running model??????\n";
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
            rCode += "      sorted_" + i + " <- as.matrix(by(testPred_" + i + ", list(model = testPred_" + i + "$model), function(x) postResample(x$pred, x$obs)))\n";
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
        //TODO: Calculate R2, for the previous sets, after finishing this 10 folds?
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

        for (int i = 0; i < Constants.NUMBERFOLDS; i++) {
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

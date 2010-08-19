package nl.wur.plantbreeding.omicsfusion.methods;

import java.util.logging.Logger;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;

/**
 * LASSO Analysis
 * @author Richard Finkers
 */
public class Lasso extends Analysis {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(Lasso.class.getName());

    @Override
    protected String getRequiredLibraries() {
        String rCode = "# Load requried libraries for Lasso\n";
        rCode += "library(glmnet)\n";
        return rCode;
    }

    @Override
    public String initializeResultObjects() {
        String rCode = "# Initialize results\n";
        for (int i = 0; i < Constants.NUMBERFOLDS; i++) {
            rCode += "coefsLASS_" + i + " <- matrix(data=NA, nrow=dim(DesignMatrix)[2]+1, ncol=" + Constants.ITERATIONS + ")\n";
            rCode += "R2_lasso_" + i + " <- matrix(data=NA, nrow=1, ncol=" + Constants.ITERATIONS + ")\n";
            rCode += "lasso_lambda_" + i + " <- matrix(data=NA,nrow=1,ncol=" + Constants.ITERATIONS + ")\n";
            rCode += "test_" + i + "_lasso <-matrix(data=NA,nrow=" + Constants.ITERATIONS + ",ncol=2)\n\n";
        }
        return rCode;
    }

    @Override
    public String getAnalysis() {
        String rCode = "# Lasso Analysis\n";
        rCode += "for (index in 1:" + Constants.ITERATIONS + ") {\n";
        rCode += getTrainingSets();
        rCode += "  con <- trainControl(method = \"cv\", number = " + Constants.NUMBERFOLDS + ")\n"; //TODO: Ask animesh if number=10 has something to do with Constants.NUMBERFOLDS
        for (int i = 0; i < Constants.NUMBERFOLDS; i++) {
            int j = i + 1;
            rCode += "      ## Lasso Round: " + j + "\n";
            rCode += "      ## Create predictor and response test and training sets\n";
            rCode += "      predictorTrainSet" + i + " <- DesignMatrix[trainingSet" + i + ",]\n";
            rCode += "      predictorTestSet" + i + " <- DesignMatrix[-trainingSet" + i + ",]\n";// outer test set
            rCode += "      responseTrainSet" + i + " <- dataSet$BRIX_P[trainingSet" + i + "]\n";//FIXME hardcoded. dataSet[1] does not work. Selects potentially wrong columns
            rCode += "      responseTestSet" + i + " <- dataSet$BRIX_P[-trainingSet" + i + "]\n";//FIXME hardcoded. dataSet[1] does not work
            rCode += "      ## Parameter optimalization\n";
            rCode += "      lassoFit" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"glmnet\", metric = \"RMSE\", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 1, by = 0.1), .alpha = 1), trControl = con)\n";
            rCode += "      lasso_lambda_" + i + "[, index] <- enFit" + i + "$bestTune$.lambda\n";
            rCode += "      coefs_lasso_" + i + "[, index] <- as.matrix(coef(lassoFit" + i + "$finalModel, s = lassoFit" + i + "$finalModel$tuneValue$.lambda))\n";
            rCode += "      preds_lasso_" + i + " <- predict(lassoFit" + i + "$finalModel, newx = predictorTrainSet" + i + ", s = lassoFit" + i + "$finalModel$tuneValue$.lambda, type = \"response\")\n";
            rCode += "      y_fit_en_" + i + " <- preds_lasso_" + i + "[, 1]\n";
            rCode += "      R2_lasso_" + i + "[, index] <- (cor(responseTrainSet" + i + ", y_fit_en_" + i + ")^2) * 100\n";//TODO: On how many samples is this R2 calculated?
            rCode += "      ## Running model??????\n";
            rCode += "      bhModels" + i + "_lasso <- list(glmnet = lassoFit" + i + ")\n";
            rCode += "      allPred_" + i + "_lasso <- extractPrediction(bhModels" + i + "_lasso, testX = predictorTestSet" + i + ", testY = responseTestSet" + i + ")\n";
            rCode += "      testPred" + i + "_lasso <- subset(allPred" + i + "_lasso, dataType == \"Test\")\n";
            rCode += "      sorted" + i + "_lasso <- as.matrix(by(testPred" + i + "_lasso, list(model = testPred" + i + "_lasso$model), function(x) postResample(x$pred, x$obs)))\n";
            rCode += "      test_" + i + "_lasso[index, ] <- sorted" + i + "_lasso[, 1]$glmnet\n\n";
            //TODO: cleanup of unused objects?
            //FIXME: Lasso code fragment is almost equal to elastic net. Generalize in Method!
        }
        //TODO: Calculate R2, for the previous sets, after finishing this 10 folds?
        rCode += "}\n\n";
        return rCode;
    }

    @Override
    public String combineResults() {
        String rCode = "# Combine results\n";
        String trainCoeff = "lasso_Train_Coeff <- cbind(";
        String trainFraction = "lasso_Train_Fraction <- cbind(";
        String trainLambda = "lasso_Train_Lambda <- cbind(";
        String trainR2 = "lasso_Train_R2 <- cbind(";
        String test = "lasso <- cbind(";

        for (int i = 0; i < Constants.NUMBERFOLDS; i++) {
            trainCoeff += "coefs_lasso_" + i;
            trainFraction += "lasso_frac_" + i;
            trainLambda += "lasso_lambda_" + i;
            trainR2 += "R2_lasso_" + i;
            test += "test_" + i + "_lasso";
            if (i < 9) {
                trainCoeff += ", ";
                trainFraction += ", ";
                trainLambda += ", ";
                trainR2 += ", ";
                test += ", ";
            }
        }
        return rCode + trainCoeff + ")\n" + trainFraction + ")\n" + trainLambda + ")\n" + trainR2 + ")\n" + test + ")\n\n";
    }

    @Override
    public String writeResults() {
        String rCode = "# Write results to disk\n";
        rCode += "write.csv(lasso_Train_Coeff, paste(\"EN_coef\", \"_\", totiter, sep = \"\"))\n";
        rCode += "write.csv(lasso_Train_R2, paste(\"EN_R2\", \"_\", totiter, sep = \"\"))\n";
        rCode += "write.csv(lasso_Train_Lambda, paste(\"EN_Lambda\", \"_\", totiter, sep = \"\"))\n";
        rCode += "write.csv(lasso_Train_Fraction, paste(\"EN_Frac\", \"_\", totiter, sep = \"\"))\n";
        rCode += "write.csv(lasso, paste(\"EN_Frac\", \"_\", totiter, sep = \"\"))\n";
        rCode += "write.xls(lasso, \"ennew.xls\")";
        return rCode;
    }
}

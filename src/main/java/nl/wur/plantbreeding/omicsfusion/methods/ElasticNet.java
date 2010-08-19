package nl.wur.plantbreeding.omicsfusion.methods;

import java.util.logging.Logger;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;

/**
 * Elastic net application using GLMNET and caret
 * @author Richard Finkers
 * @version 1.0
 */
public class ElasticNet extends Analysis {

    /**The Logger */
    private static final Logger LOG = Logger.getLogger(ElasticNet.class.getName());

    @Override
    public String getRequiredLibraries() {
        String rCode = "# Load requried libraries for ElasticNet\n";
        rCode += "library(glmnet)\n";
        return rCode;
    }

    @Override
    public String initializeResultObjects() {
        String rCode = "# Initialize results\n";
        for (int i = 0; i < Constants.NUMBERFOLDS; i++) {
            rCode += "coefsEN_" + i + " <- matrix(data=NA,nrow=dim(DesignMatrix)[2]+1,ncol=" + Constants.ITERATIONS + ")\n";
            rCode += "R2_en_" + i + " <- matrix(data=NA,nrow=1,ncol=" + Constants.ITERATIONS + ")\n";
            rCode += "en_frac_" + i + " <- matrix(data=NA,nrow=1,ncol=" + Constants.ITERATIONS + ")\n";
            rCode += "en_lambda_" + i + " <- matrix(data=NA,nrow=1,ncol=" + Constants.ITERATIONS + ")\n";
            rCode += "test_" + i + "_en <- matrix(data=NA,nrow=" + Constants.ITERATIONS + ",ncol=2)\n\n";
        }
        return rCode;
    }

    @Override
    public String getAnalysis() {
        String rCode = "#Elastic Net Analysis\n";
        rCode += "for (index in 1:" + Constants.ITERATIONS + ") {\n";
        rCode += getTrainingSets();
        rCode += "  con <- trainControl(method = \"cv\", number = " + Constants.NUMBERFOLDS + ")\n"; //TODO: Ask animesh if number=10 has something to do with Constants.NUMBERFOLDS
        for (int i = 0; i < Constants.NUMBERFOLDS; i++) {
            int j = i + 1;
            rCode += "      ## Elastic Net Round: " + j + "\n";
            rCode += "      ## Create predictor and response test and training sets\n";
            rCode += "      predictorTrainSet" + i + " <- DesignMatrix[trainingSet" + i + ",]\n";
            rCode += "      predictorTestSet" + i + " <- DesignMatrix[-trainingSet" + i + ",]\n";// outer test set
            rCode += "      responseTrainSet" + i + " <- dataSet$BRIX_P[trainingSet" + i + "]\n";//FIXME hardcoded. dataSet[1] does not work. Selects potentially wrong columns
            rCode += "      responseTestSet" + i + " <- dataSet$BRIX_P[-trainingSet" + i + "]\n";//FIXME hardcoded. dataSet[1] does not work
            rCode += "      ## Parameter optimalization\n";
            rCode += "      enFit" + i + " <- train(predictorTrainSet" + i + ", responseTrainSet" + i + ", \"glmnet\", metric = \"RMSE\", tuneLength = 10, trControl = con)\n";
            rCode += "      en_frac_" + i + "[, index] <- enFit" + i + "$finalModel$tuneValue$.alpha\n";
            rCode += "      en_lambda_" + i + "[, index] <- enFit" + i + "$finalModel$tuneValue$.lambda\n";
            rCode += "      coefsEN_" + i + "[, index] <- as.matrix(coef(enFit" + i + "$finalModel, s = enFit" + i + "$finalModel$tuneValue$.lambda))\n";
            rCode += "      predsEN_" + i + " <- predict(enFit" + i + "$finalModel, newx = predictorTrainSet" + i + ", s = enFit" + i + "$finalModel$tuneValue$.lambda, type = \"response\")\n";
            rCode += "      y_fit_en_" + i + " <- predsEN_" + i + "[, 1]\n";
            rCode += "      R2_en_" + i + "[, index] <- (cor(responseTrainSet" + i + ", y_fit_en_" + i + ")^2) * 100\n";//TODO: On how many samples is this R2 calculated?
            rCode += "      ## Running model??????\n";
            rCode += "      bhModels" + i + "_en <- list(glmnet = enFit" + i + ")\n";
            rCode += "      allPred" + i + "_en <- extractPrediction(bhModels" + i + "_en, testX = predictorTestSet" + i + ", testY = responseTestSet" + i + ")\n";
            rCode += "      testPred" + i + "_en <- subset(allPred" + i + "_en, dataType == \"Test\")\n";
            rCode += "      sorted" + i + "_en <- as.matrix(by(testPred" + i + "_en, list(model = testPred" + i + "_en$model), function(x) postResample(x$pred, x$obs)))\n";
            rCode += "      test_" + i + "_en[index, ] <- sorted" + i + "_en[, 1]$glmnet\n\n";
            //TODO: cleanup of unused objects?
        }
        //TODO: Calculate R2, for the previous sets, after finishing this 10 folds?
        rCode += "}\n\n";
        return rCode;
    }

    @Override
    public String combineResults() {
        String rCode = "# Combine results\n";
        String trainCoeff = "EN_Train_Coeff <- cbind(";
        String trainFraction = "EN_Train_Fraction <- cbind(";
        String trainLambda = "EN_Train_Lambda <- cbind(";
        String trainR2 = "EN_Train_R2 <- cbind(";
        String test = "en <- cbind(";

        for (int i = 0; i < Constants.NUMBERFOLDS; i++) {
            trainCoeff += "coefsEN_" + i;
            trainFraction += "en_frac_" + i;
            trainLambda += "en_lambda_" + i;
            trainR2 += "R2_en_" + i;
            test += "test_" + i + "_en";
            if (i < 9) {
                trainCoeff += ", ";
                trainFraction += ", ";
                trainLambda += ", ";
                trainR2 += ", ";
                test += ", ";
            }
        }
        return rCode + trainCoeff + ")\n" + trainFraction + ")\n" + trainLambda + ")\n" + trainR2 + ")\n"+ test + ")\n\n";
    }

    @Override
    public String writeResults() {
        String rCode = "# Write results to disk\n";
        rCode += "write.csv(EN_Train_Coeff, paste(\"EN_coef\", \"_\", totiter, sep = \"\"))\n";
        rCode += "write.csv(EN_Train_R2, paste(\"EN_R2\", \"_\", totiter, sep = \"\"))\n";
        rCode += "write.csv(EN_Train_Lambda, paste(\"EN_Lambda\", \"_\", totiter, sep = \"\"))\n";
        rCode += "write.csv(EN_Train_Fraction, paste(\"EN_Frac\", \"_\", totiter, sep = \"\"))\n";
        rCode += "write.csv(en, paste(\"EN_Frac\", \"_\", totiter, sep = \"\"))\n";
        rCode += "write.xls(en, \"ennew.xls\")";
        return rCode;
    }
}

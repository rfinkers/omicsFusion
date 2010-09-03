/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.methods;

import java.util.logging.Logger;
import nl.wur.plantbreeding.omicsfusion.utils.Constants;

/**
 * Random forests analysis
 * @author Richard Finkers
 * @version 1.0
 */
public class RandomForest extends Analysis {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(RandomForest.class.getName());

    @Override
    protected String initializeResultObjects() {
        return super.initializeResultObjects("rf");
    }

    @Override
    protected String getRequiredLibraries() {
        String rCode = "# Load requried libraries for Random Forest\n";
        rCode += "library(randomForest)\n";
        return rCode;
    }

    @Override
    public String getAnalysis() {
        return super.getAnalysis("rf");
    }

    @Override
    protected String combineResults() {
        String rCode = "# Combine results\n";
        String trainMtry = "Train_mtry <- cbind(";
        String trainVarImp = "Train_varImp <- cbind(";
        String trainR2 = "Train_R2 <- cbind(";
        String test = "methodResults <- cbind(";

        for (int i = 0; i < Constants.NUMBER_FOLDS_OUTER; i++) {
            trainMtry += "mtry_" + i;
            trainVarImp += "imp_" + i;
            trainR2 += "R2_" + i;
            test += "test_" + i;
            if (i < 9) {
                trainMtry += ", ";
                trainVarImp += ", ";
                trainR2 += ", ";
                test += ", ";
            }
        }
        return rCode + trainMtry + ")\n" + trainVarImp + ")\n" + trainR2 + ")\n" + test + ")\n\n";
    }

    @Override
    protected String getRowMeansAndSD() {
        String rCode = "# Get row means, SD and (absolute) rank\n";
        //dataSet contains predictor column. So, function == trye when the Train_Coef also contains the intercept.
        rCode += "means<-apply(Train_varImp,1,mean)\n";
        rCode += "sd<-apply(Train_varImp,1,sd)\n";
        rCode += "ra<-rank(abs(apply(Train_varImp,1,mean)))\n";
        rCode += "Train_varImp_Summary <- cbind (means,sd,ra)\n";
        return rCode;
    }

    @Override
    public String writeResults() {
        String rCode = "# Write results to disk\n";
        rCode += "write.csv(Train_R2, paste(\"RF_R2\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_varImp, paste(\"RF_varImp\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_varImp_Summary, paste(\"RF_varImp_Summary\", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(Train_mtry, paste(\"RF_mtry\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        rCode += "write.csv(methodResults, paste(\"RF\", \"_\", " + Constants.ITERATIONS + ", \".csv\" , sep = \"\"))\n";
        //rCode += "write.xls(methodResults, \"RFnew.xls\")";
        return rCode;
    }
}

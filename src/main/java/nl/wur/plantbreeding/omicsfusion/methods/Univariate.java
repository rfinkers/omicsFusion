/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.methods;

import java.util.logging.Logger;

/**
 * Contains the script required to perform Univariate analysis.
 * @author Richard Finkers
 */
public class Univariate extends Analysis {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(Univariate.class.getName());

    @Override
    protected String getRequiredLibraries() {
        String rCode = super.getRequiredLibraries();
        rCode += "# Loading method specific libraries\n";
        //Add all method specific libraries here
        rCode += "library(multtest)\n";
        return rCode;
    }

    @Override
    protected String getAnalysis() {
        String rCode = "#Univariate Analysis\n";
        //run the analysis
        rCode += "for(i in 1:dim(DesignMatrix)[2])\n";
        rCode += "{\n";
        rCode += "    lmres<-lm(dataSet$BRIX_P ~ DesignMatrix[,i])\n";//FIXME: hardcoded
        rCode += "    anovares<-anova(lmres)\n";
        rCode += "    sumres<-summary(lmres)\n";
        rCode += "    r2[i]<-sumres$r.squared\n";
        rCode += "    beta0[i]<-sumres$coefficients[1]\n";
        rCode += "    beta1[i]<-sumres$coefficients[2]\n";
        rCode += "    pval[i]<-anovares[1,\"Pr(>F)\"]\n";
        rCode += "}\n";
        //Find the best predictor among the univariate predictors
        rCode += "j<-which(pval==min(pval))\n";
        return rCode;
    }

    @Override
    protected String writeResults() {
        String rCode = "#Write Univariate and FDR correction results details\n";
        rCode += "write.csv(outp,\"univariate_all_coef.csv\")\n";
        rCode += "write.csv(bh, \"univariate_bh_coef.csv\")\n";
        rCode += "write.csv(pvalue, \"univariate_p_coef.csv\")\n";
        return rCode;
    }

    @Override
    protected String combineResults() {
        //Write results to XLS files
        String rCode = "index<-1:dim(DesignMatrix)[2]\n";
        rCode += "trtname <- colnames(DesignMatrix)\n";
        rCode += "adjp1<-mt.rawp2adjp(rawp=pval[order(index)])\n";
        rCode += "adjp<-adjp1$adjp[order(adjp1$index),]\n";
        //Raw p values
        rCode += "means <- pval\n";//Because other methods provide means.
        rCode += "pval<-1/pval\n";//Because other methods rank on coefficent.
        rCode += "ra<-rank(abs(apply(as.matrix(pval),1,mean)))\n";
        rCode += "sd <-pval\n";//we need an sd column for the csv reader
        rCode += "pvalue <- cbind (means,sd,ra)\n";
        //BH corrected p values
        //Raw p values
        rCode += "bh <- 1/adjp[,7]\n";//Because other methods rank on coefficient
        rCode += "means <- adjp[,7]\n";//Because other methods provide means.
        rCode += "ra<-rank(abs(apply(as.matrix(bh),1,mean)))\n";
        rCode += "sd <-pval\n";//we need an sd column for the csv reader
        rCode += "bh <- cbind (means,sd,ra)\n";
        //All methods combined
        rCode += "outp<-cbind(beta1,r2,pval,adjp)\n";
        //Add the response traits to the file
        rCode += "rownames(outp)<-trtname\n";
        rCode += "rownames(pvalue)<-trtname\n";
        rCode += "rownames(bh)<-trtname\n";
        return rCode;
    }

    @Override
    protected String getTrainingSets() {
        return "";
    }

    @Override
    protected String getRowMeansAndSD() {
        return "";
    }

    @Override
    protected String initializeResultObjects() {
        //initialize the variables
        String rCode = "r2<-NULL\n";
        rCode += "r2adj<-NULL\n";
        rCode += "beta0<-NULL\n";
        rCode += "beta1<-NULL\n";
        rCode += "pval<-NULL\n";
        return rCode;
    }
}

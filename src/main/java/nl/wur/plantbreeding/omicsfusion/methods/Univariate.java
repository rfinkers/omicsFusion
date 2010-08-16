/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.methods;

import java.util.logging.Logger;
import nl.wur.plantbreeding.omicsfusion.wizard.DataUploadAction;
import nl.wur.plantbreeding.omicsfusion.wizard.DataUploadValidationForm;

/**
 * Contains the script required to perform Univariate analysis.
 * @author Richard Finkers
 */
public class Univariate extends Analysis {

    /** The logger */
    private static final Logger LOG = Logger.getLogger(Univariate.class.getName());
    /** The script file */
    //private String rScript = "";

    /**
     * Basic analysis script for R
     * @return The script
     */
    public String analysisisRScript() {
        //Write the script
        rScript = "#Univariate Analysis\n";
        //required libraries
        rScript += "library(gdata)\n";
        //load the input data
        rScript += "Y<-read.xls(\"brix.xls\")\n";//FIXME: hardcode sheet names
        rScript += "rownames(Y)<-Y[1]\n";
        rScript += "Y<-Y[2]\n";
        rScript += "X<-read.xls(\"traits.xls\")\n";//FIXME: hardcode sheet names
        //run the analysis
        rScript += "for(i in 2:dim(X)[2])\n";
        rScript += "{\n";
        rScript += "    lmres<-lm(Y~X[,i])\n";
        rScript += "    anovares<-anova(lmres)\n";
        rScript += "    sumres<-summary(lmres)\n";
        rScript += "    r2[i]<-sumres$r.squared\n";
        rScript += "    beta0[i]<-sumres$coefficients[1]\n";
        rScript += "    beta1[i]<-sumres$coefficients[2]\n";
        rScript += "    pval[i]<-anovares[1,\"Pr(>F)\"]\n";
        rScript += "}\n";
        //Find the best predictor among the univariate predictors
        rScript += "j<-which(pval==min(pval))\n";
        //Write results to XLS files
        rScript += "index<-1:dim(X)[2]\n";
        rScript += "adjp1<-mt.rawp2adjp(rawp=pval[order(index)])\n";
        rScript += "outp<-cbind(beta1,r2,pval,adjp)\n";
        rScript += "filename5<-paste(trtname,\"Univ_coef.xls\",sep=\"_\")\n";
        rScript += "write.xls(outp,filename5)\n";

        return rScript;
    }
    //Export to tmp directory
    //Submit to SGE

//r2<-NULL
//r2adj<-NULL
//beta0<-NULL
//beta1<-NULL
//pval<-NULL
//

//for(i in 1:dim(X)[2])
//{
//lmres<-lm(y ~ X[,i])
//anovares<-anova(lmres)
//sumres<-summary(lmres)
//r2[i]<-sumres$r.squared
//r2adj[i]<-sumres$adj.r.squared
//beta0[i]<-sumres$coefficients[1]
//beta1[i]<-sumres$coefficients[2]
//pval[i]<-anovares[1,"Pr(>F)"]
//}
//
//# Find the best predictor among the univariate predictors
//j<-which(pval==min(pval))
//
//# Produce results in Xl files
//index<-1:dim(X)[2]
//adjp1<-mt.rawp2adjp(rawp=pval[order(index)])
//adjp<-adjp1$adjp[order(adjp1$index),]
//
//
//outp<-cbind(beta1,r2,pval,adjp)
//filename5<-paste(trtname,"Univ_coef.xls",sep="_")
//write.xls(outp,filename5)
//
//co<-cbind(co,outp)
}

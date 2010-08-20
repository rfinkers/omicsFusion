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

    /** The script file */
    //private String rScript = "";
    /**
     * Basic analysis script for R
     * @param output Output directory
     * @return The script
     */
    public String analysisisRScript(String output) {
        //Write the script
        String rScript = "#Univariate Analysis\n";
        //Set the working directory
        rScript += "setwd(\"" + output + "\")\n";
        //required libraries
        rScript += "library(gdata)\n";
        rScript += "library(multtest)\n";
        //initialize the variables
        rScript += "r2<-NULL\n";
        rScript += "r2adj<-NULL\n";
        rScript += "beta0<-NULL\n";
        rScript += "beta1<-NULL\n";
        rScript += "pval<-NULL\n";
        //load the input data
        rScript += "Y<-read.xls(\"brix.xls\")\n";//FIXME: hardcode sheet names
        //rScript += "rownames(Y)<-Y[1]\n";
        //rScript += "Y<-Y[2]\n";
        rScript += "X<-read.xls(\"traits.xls\")\n";//FIXME: hardcode sheet names
        rScript += "trtname<-colnames(X)\n";
        //run the analysis
        rScript += "for(i in 2:dim(X)[2])\n";
        rScript += "{\n";
        rScript += "    lmres<-lm(Y[,2]~X[,i])\n";
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
        rScript += "adjp<-adjp1$adjp[order(adjp1$index),]\n";
        rScript += "outp<-cbind(trtname,beta1,r2,pval,adjp)\n";
        rScript += "filename5<-paste(trtname,\"Univ_coef.csv\")\n";
        rScript += "write.csv(outp,filename5)\n";

        return rScript;
    }

}

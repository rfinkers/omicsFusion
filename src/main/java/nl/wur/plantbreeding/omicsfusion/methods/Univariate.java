/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.wur.plantbreeding.omicsfusion.methods;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Univariate analysis
 * @author Richard Finkers
 */
public class Univariate extends Analysis {

    //Write the script
    //Export to tmp directory
    //Submit to SGE

    FileOutputStream fos = getFos();// FIXME: add tmp directory whith the excel files!







//r2<-NULL
//r2adj<-NULL
//beta0<-NULL
//beta1<-NULL
//pval<-NULL
//
//
//
//for(i in 1:dim(X)[2])
//{
//
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

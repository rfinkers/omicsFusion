/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.wur.solgenomics.omicsfusion2.methods;

/**
 * Application of PCR
 * @author Richard Finkers
 */
public class PCR {
//data1<-data.frame(y,X=I(X))
//
//
//pcrres<-pcr(y ~ X, data=data1, validation="CV")
//
//# To find out optimum component for PCR
//
//plot(RMSEP(pcrres), legendpos="topright", xlim=c(0,100))
//
//# By seeing the first plot it seems tht components lies betweeen
//#18 and 25 so, I want to magify this
//# this is not automated!
//
//plot(RMSEP(pcrres), legendpos="topright", xlim=c(40,50))
//rmsep<-RMSEP(pcrres)
//opt_pcr<- which(rmsep$val["adjCV",1,]==min(rmsep$val["adjCV",1,])) - 1
//opt_pcr
//
//explvar(pcrres)
//
//
//coefpcr<-coef(pcrres, ncomp=opt_pcr)
//filename12<-paste(trtname,"PCR_coef.xls")
//write.xls(coefpcr,filename12)
//
//
//
//# NOTE: these are fitted values, not independent predictions for new data !!
//# Prediction of the flesh colour data
//
//predpcr<-predict(pcrres, ncomp=42)
//filename13<-paste(trtname,"PCR_pred.xls")
//write.xls(predpcr,filename13)
//
//pr<-cbind(pr,predpcr)
//co<-cbind(co,coefpcr)
}

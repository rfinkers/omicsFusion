/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.wur.plantbreeding.omicsfusion.methods;

/**
 * Application of PLS
 * @author Richard Finkers
 */
public class PartialLeasedSquares {
//data1<-data.frame(y,X=I(X))
//
//
//plsres<-plsr(y ~ X, data=data1, validation="CV")
//summary(plsres)
//
//# To see the pattern where optimum components lies from CV
//
//plot(RMSEP(plsres), legendpos="topright")
//
//# Better pic just for good annotation and xlim()
//# uses to check the proper components for PLS.
//# here I have used 10 , i.e within 10 components where
//# RMSEP is minimum
//plot(RMSEP(plsres), legendpos="topright", xlim=c(0,10),ylim=c(0,20))
//rmsep<-RMSEP(plsres)
//
//# To see how much variance explained by each of the components
//explvar(plsres)
//opt_pls<- which(rmsep$val["adjCV",1,]==min(rmsep$val["adjCV",1,])) - 1
//
//coefpls<-coef(plsres, ncomp=opt_pls)
//filename10<-paste(trtname,"PLS_coef.xls",sep="_")
//write.xls(coefpls,filename10)
//
//
//# NOTE: these are fitted values, not independent predictions for new data !!
//predpls<-predict(plsres, ncomp=2)
//filename11<-paste(trtname,"PLS_pred.xls",sep="_")
//write.xls(predpls,filename11)
//
//pr<-cbind(pr,predpls)
//co<-cbind(co,coefpls)
}

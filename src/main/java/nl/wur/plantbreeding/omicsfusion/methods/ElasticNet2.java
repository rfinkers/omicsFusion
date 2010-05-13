/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.wur.plantbreeding.omicsfusion.methods;

/**
 * Elastic net application using ELASTIC NET
 * @author Richard Finkers
 */
public class ElasticNet2 {
//sds.cven<-cv.enet(x=X,y=y,s=seq(0,1,length=100),mode="fraction",lambda=0, plot.it=T,trace=FALSE)
//en.opt<-sds.cven$s[which(sds.cven$cv==min(sds.cven$cv))]
//# lambda=0.15 after cross validation
//en1<-enet(x=X,y=y,lambda=en.opt,normalize=TRUE,intercept=TRUE)
//#plot(en1)
//# storing the values coefficients
//ypred<-predict.enet(en1,s=en.opt,type="coef",mode="fraction")
//
//# this command will store coefficients in the working directory in a XL file
//filename17<-paste(trtname,"Enet_coef_fraction.xls",sep="_")
//write.xls(ypred$coefficients,filename17)
//#
//
//
//# prediction
//enpred<-predict.enet(en1,newx=X,s=en.opt,type="fit",mode="fraction")
//filename18<-paste(trtname,"Enet_pred_fraction.xls",sep="_")
//write.xls(enpred$fit,filename18)
//
//pr<-cbind(pr,enpred$fit)
//coef_enet_enet<-ypred$coefficients
//co<-cbind(co,coef_enet_enet)
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.wur.solgenomics.omicsfusion2.methods;

/**
 * LASSO Analysis
 * @author Richard Finkers
 */
public class Lasso {
//data1<-data.frame(y,X=I(X))
//
//
//# this code is added for reg. coeff. path plot and
//# to visualize the proper lambda value in plot
//#
//lassocoef <-
//function(formula,data,sopt,plot.opt=TRUE, ...)
//{
//# Plot coefficients of Lasso regression
//#
//
//require(pls)
//require(lars)
//
//    mf <<- match.call(expand.dots = FALSE)
//    m <- match(c("formula", "data"), names(mf), 0)
//    mf <- mf[c(1, m)]
//    mf[[1]] <- as.name("model.frame")
//    mf <- eval(mf, parent.frame())
//    mt <- attr(mf, "terms")
//    y <- model.response(mf, "numeric")
//    X <- delete.intercept(model.matrix(mt, mf))
//
//
//mod_lasso <- lars(X,y)
//
//aa <- apply(abs(mod_lasso$beta),1,sum)
//ind <- which.min(abs(aa/max(aa)-sopt))
//coef <- mod_lasso$beta[ind[1],]
//numb.zero <- sum(coef==0)
//numb.nonzero <- sum(coef!=0)
//
//if (plot.opt){
//plot(mod_lasso,breaks=FALSE,cex=0.4,col=gray(0.6),...)
//abline(v=sopt,lty=2)
//title(paste(numb.zero,"coefficients are zero,",numb.nonzero,"are not zero"))
//}
//
//list(coefficients=coef,sopt=sopt,numb.zero=numb.zero,numb.nonzero=numb.nonzero,ind=ind)
//}
//
//
//lasso_res=lassoCV(y~X,data=data1,K=10,fraction=seq(0.1,1,by=0.1))
//
//lasso_coef=lassocoef(y~X,data=data1,sopt=lasso_res$sopt)
//filename6<-paste(trtname,"Lasso_coef.xls",sep="_")
//write.xls(lasso_coef$coefficients,filename6)
//
//lasso_coef$numb.nonzero
//lasso_res$sopt
//
//object <- lars(X,y,type="lasso")
//#s=0.2=lasso_res$sopt
//
//fits <- predict.lars(object, X,s=lasso_res$sopt,type="fit",mode="fraction")
//filename7<-paste(trtname,"Lasso_pred.xls",sep="_")
//write.xls(fits$fit,filename7)
//
//pr<-cbind(pr,fits$fit)
//lasso_coefficients<-lasso_coef$coefficients
//co<-cbind(co,lasso_coefficients)
}

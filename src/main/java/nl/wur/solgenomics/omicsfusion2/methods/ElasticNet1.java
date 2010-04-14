/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.solgenomics.omicsfusion2.methods;

/**
 * Elastic net application using GLMNET and caret
 * @author Richard Finkers
 */
public class ElasticNet1 {
//con<-trainControl(method="cv",number=10)
//
//fit_glm <- NULL
//fit_glm233<-train(X, y,method="glmnet",metric="RMSE",trControl=con,tuneLength = 10)
//fit_glm233$finalModel$tuneValue
//fit_glm233$finalModel$tuneValue$.alpha
//glmnetcalc<-glmnet(X,y,alpha=fit_glm233$finalModel$tuneValue$.alpha)
//
//fitcoef<-predict(glmnetcalc,s=fit_glm233$finalModel$tuneValue$.alpha,type="coefficients")
//CoefEN<-as.matrix(fitcoef)
//filename15<-paste(trtname,"Enet_coef_GLM.xls",sep="_")
//write.xls(CoefEN[2:(dim(X)[2]+1)], filename15)
//
//# for predicting with optimal alpha
//
//expred <- extractPrediction(list(fit_glm233))
//plotObsVsPred(expred)
//filename16<-paste(trtname,"Enet_pred_GLM.xls",sep="_")
//write.xls(expred, filename16)
//
//# The following code can also be used which has a similar function as avove
//fit_predict<-predict(fit_glm233,newdata=X)
//
//
//pr<-cbind(pr,expred)
//coef_enetglm<-CoefEN[2:(dim(X)[2]+1)]
//co<-cbind(co,coef_enetglm)
//
//# extracting coeffcients ????
}

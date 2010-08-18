/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wur.plantbreeding.omicsfusion.methods;

import java.util.logging.Logger;

/**
 * Elastic net application using GLMNET and caret
 * @author Richard Finkers
 */
public class ElasticNet extends Analysis implements AnalysisMethods {

    /**The Logger */
    private static final Logger LOG = Logger.getLogger(ElasticNet.class.getName());

    @Override
    public String getParamterInitialization(int loop, int itterations) {
        String rCode = "";
        rCode += "coefsEN" + loop + "<-matrix(data=NA,nrow=dim(DesignMatrix)[2]+1,ncol=" + itterations + ")";
        rCode += "R2_en" + loop + "<-matrix(data=NA,nrow=1,ncol=" + itterations + ")";
        rCode += "en_frac_" + loop + "<-matrix(data=NA,nrow=1,ncol=" + itterations + ")";
        rCode += "en_lambda_" + loop + "<-matrix(data=NA,nrow=1,ncol=" + itterations + ")";
        rCode += "test" + loop + "test1_en<-matrix(data=NA,nrow=" + itterations + ",ncol=2)";
        return rCode;
    }

    @Override
    public String getAnalysis() {
        throw new UnsupportedOperationException("Not supported yet.");

//enFit1 <- train(tr1, trainFlesh1, "glmnet", metric="RMSE",tuneLength = 10, trControl = con)
//
//en_frac_1[,index]<-enFit1$finalModel$tuneValue$.alpha
//en_lambda_1[,index]<-enFit1$finalModel$tuneValue$.lambda
//
//coefsEN1[,index]<-as.matrix(coef(enFit1$finalModel,s=enFit1$finalModel$tuneValue$.lambda))
//
//predsEN1<-predict(enFit1$finalModel,newx=tr1,s=enFit1$finalModel$tuneValue$.lambda, type ="response")
//
//y_fit_en1<-predsEN1[,1]
//
//R2_en1[,index]<-(cor(trainFlesh1,y_fit_en1)^2)*100
//
//
//bhModels1_en <- list(glmnet=enFit1)
//#bhPred1_en<- predict(bhModels1_en, newdata = testBH1)
//allPred1_en <- extractPrediction(bhModels1_en, testX = testBH1, testY = testFlesh1)
//testPred1_en <- subset(allPred1_en, dataType == "Test")
//sorted1_en<-as.matrix(by(testPred1_en, list(model = testPred1_en$model), function(x) postResample(x$pred, x$obs)))
//test1_en[index,]<-sorted1_en[,1]$glmnet
    }

    @Override
    public String getPostProcessing() {
        throw new UnsupportedOperationException("Not supported yet.");
//        EN_Train_Coeff<-cbind(coefsEN1,coefsEN2,coefsEN3,coefsEN4,coefsEN5,coefsEN6,coefsEN7,coefsEN8, coefsEN9,coefsEN10)
//#EN_Train_Coeff<-cbind(coefsEN1,coefsEN2,coefsEN3,coefsEN4,coefsEN5,coefsEN6,coefsEN7,coefsEN8, coefsEN9,coefsEN10)
//
//EN_Train_Fraction<-cbind(en_frac_1, en_frac_2, en_frac_3, en_frac_4, en_frac_5, en_frac_6, en_frac_7, en_frac_8, en_frac_9, en_frac_10)
//EN_Train_Lambda<-cbind(en_lambda_1, en_lambda_2, en_lambda_3, en_lambda_4, en_lambda_5, en_lambda_6, en_lambda_7, en_lambda_8, en_lambda_9, en_lambda_10)
//EN_Train_R2<-cbind(R2_en1,R2_en2,R2_en3,R2_en4,R2_en5,R2_en6,R2_en7,R2_en8,R2_en9,R2_en10)
//#EN_Train_y_Fit<-cbind(y_fit_en1,y_fit_en2, y_fit_en3,y_fit_en4,y_fit_en5,y_fit_en6,y_fit_en7,y_fit_en8,y_fit_en9,y_fit_en10)
//
//write.csv(EN_Train_Coeff, paste("EN_coef", "_", totiter, sep=""))
//write.csv(EN_Train_R2, paste("EN_R2", "_", totiter, sep=""))
//write.csv(EN_Train_Fraction , paste("EN_Frac", "_", totiter, sep=""))
//write.csv(EN_Train_Lambda , paste("EN_Lambda", "_", totiter, sep=""))
    }
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

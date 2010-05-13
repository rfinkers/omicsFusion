/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.wur.plantbreeding.omicsfusion.methods;

/**
 * SVM regression using e1071
 * @author Richard Finkers
 */
public class SVM {
//data1<-data.frame(y,X=I(X))
//
//# For optimization of the parameter
//# gamma = tuning parameter for RBF
//# cost=tuning parameter for SVR
//
//obj1 <- tune.svm(y~., data = data1, sampling = "fix", gamma = 2^c(-8,-4,0,4), cost = 2^c(-8,-4,-2,0,2))
//obj1
//
//plot(obj1,transform.x = log2, transform.y = log2)
//
//
//
//# gamma and cost is taken from optimized value.
//# careful!!!Not automated
//
//m1 <- svm(X, y,gamma=obj1$best.parameters$gamma,cost=obj1$best.parameters$cost)
//new1 <- predict(m1, X)
//filename14<-paste(trtname,"SVM_pred",sep="_")
//write.xls(new1,filename14)
//
//pr<-cbind(pr,new1)
}

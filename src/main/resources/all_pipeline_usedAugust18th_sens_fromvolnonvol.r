# Input files are not transposed , i.e variables (p) are already in columns and samples (n) are in rows.
# The results are stored in working directory as a XL format

rm(list = ls())

# Load appropriate package for analysis

library(xlsReadWrite)
library(randomForest)
library(spls)
library(chemometrics)
library(lars)
library(caret)
library(glmnet)
library(pls)
library(elasticnet)
library(e1071)
library(lars)
library(multtest)

# Read the files which are required to be analyzed

# Data of Martijn Eggink
rawdata <- read.csv(file = "vol_3harvests_sweet.csv", header = TRUE)

oogst <- rawdata[, 1]
ras <- rawdata[, 2]
herh <- rawdata[, 3]
rasherh <- rawdata[, 4]
raslabel <- rawdata[, 5]
vruchttype <- rawdata[, 6]

vola <- rawdata[, 7:397]
nonvol <- rawdata[, 398:412]
sens <- rawdata[, 413:426]
volnonvol <- rawdata[, 7:409]

#attach(nonvol)
attach(sens)
attach(volnonvol)

#met<-read.xls('cen233.xls',rowNames=F,colNames=T,sheet=1)
#dim(met)

# Preprocessing of the data: log10 transformed
# and auto scaled

X <- scale(log10(volnonvol))

# Reading response (trait)
# new1<- read.xls(file='EnzDe5.xls',colNames=FALSE)
# write.table(new1,file='EnzDe5.txt',sep=',',quote=FALSE,row.names=FALSE,col.names=FALSE)
# x<-read.table('EnzDe5.txt', header=TRUE,sep=',')
# data<-x[,2]
# centering the response
# for final prediction I add mean(response)and its snot
# shown in code

for (j in 1:14) {
    print(j)
    print(colnames(sens)[j])

    y <- sens[, j]
    #trtname<-colnames(sens)[j]
    trtname <- paste(colnames(sens)[j], "volnonvol_sweet", sep = "_")

    y <- scale(y, center = TRUE, scale = F)

    # mean(data)
    # has to be added in for predicted data set
    # to compare with the raw values

    # ###############################
    # 1)  Random Forest analysis
    #
    ####################################
    mydata <- cbind(y, X)
    con <- trainControl(method = "cv", number = 10)

    # Optimize mtry using the RMSE as a criterion in a 10-fold cross validataion
    rf_res <- train(X, y, method = "rf", metric = "RMSE", trControl = con, tuneLength = 10)
    rf_res$finalModel$tuneValue
    rf_res$finalModel$tuneValue$.mtry

    # After optimization of mtry the optimal value for mtry is used for Random Forest
    rfres <- randomForest(y = y, x = X, mtry = rf_res$finalModel$tuneValue$.mtry, importance = TRUE, na.action = na.omit)
    rfres

    # calculate the predicted values for the trait, prediction by RandomForest
    # write the predicted values to an Excel file
    rf_pred <- predict(rfres)
    filename1 <- paste(trtname, "pred_rf.xls", sep = "_")
    write.xls(rf_pred, filename1)

    # variables used
    rf_varused <- varUsed(rfres, by.tree = FALSE, count = TRUE)
    # variable importance
    import <- importance(rfres)
    # combine varused with variable importance in a single data frame
    pr <- rf_pred

    import <- cbind(rf_varused, import)
    # write varused and importance to an Excel file
    filename2 <- paste(trtname, "rf_importance.xls", sep = "_")
    write.xls(import, filename2)
    # Plot variable importance
    #varImpPlot(rfres)

    co <- import
    #rownames<-rownames(co)

    # ###############################
    #
    # 2) Sparse PLS analysis
    #
    ####################################
    data1 <- data.frame(y, X = I(X))

    cvspls <- cv.spls(X, y, K = c(1:20), eta = seq(0.1, 0.9, 0.1), plot.it = FALSE)

    sPLS <- spls(X, y, eta = cvspls$eta.opt, K = cvspls$K.opt)
    coefsPLS <- coef(sPLS)
    colnames(coefsPLS) <- "PLS_coef"
    predsPLS <- predict(sPLS, type = "fit")

    # save the results in a working folder

    filename3 <- paste(trtname, "sPLS_coef.xls", sep = "_")
    filename4 <- paste(trtname, "sPLS_pred.xls", sep = "_")
    write.xls(predsPLS, filename4)
    write.xls(coefsPLS, filename3)

    pr <- cbind(pr, predsPLS)
    co <- cbind(co, coefsPLS)

    # ###############################
    #
    # 3) Univariate Analyses
    #
    ####################################
    r2 <- NULL
    r2adj <- NULL
    beta0 <- NULL
    beta1 <- NULL
    pval <- NULL

    for (i in 1:dim(X)[2]) {
        lmres <- lm(y ~ X[, i])
        anovares <- anova(lmres)
        sumres <- summary(lmres)
        r2[i] <- sumres$r.squared
        r2adj[i] <- sumres$adj.r.squared
        beta0[i] <- sumres$coefficients[1]
        beta1[i] <- sumres$coefficients[2]
        pval[i] <- anovares[1, "Pr(>F)"]
    }

    # Find the best predictor among the univariate predictors
    j <- which(pval == min(pval))

    # Produce results in Xl files
    index <- 1:dim(X)[2]
    adjp1 <- mt.rawp2adjp(rawp = pval[order(index)])
    adjp <- adjp1$adjp[order(adjp1$index), ]

    outp <- cbind(beta1, r2, pval, adjp)
    filename5 <- paste(trtname, "Univ_coef.xls", sep = "_")
    write.xls(outp, filename5)

    co <- cbind(co, outp)

    # ###############################
    #
    # 4) LASSO Analysis
    #
    ####################################
    data1 <- data.frame(y, X = I(X))

    # this code is added for reg. coeff. path plot and
    # to visualize the proper lambda value in plot
    #
    lassocoef <- function(formula, data, sopt, plot.opt = TRUE, ...) {
        # Plot coefficients of Lasso regression
        #

        require(pls)
        require(lars)

        mf <<- match.call(expand.dots = FALSE)
        m <- match(c("formula", "data"), names(mf), 0)
        mf <- mf[c(1, m)]
        mf[[1]] <- as.name("model.frame")
        mf <- eval(mf, parent.frame())
        mt <- attr(mf, "terms")
        y <- model.response(mf, "numeric")
        X <- delete.intercept(model.matrix(mt, mf))

        mod_lasso <- lars(X, y)

        aa <- apply(abs(mod_lasso$beta), 1, sum)
        ind <- which.min(abs(aa/max(aa) - sopt))
        coef <- mod_lasso$beta[ind[1], ]
        numb.zero <- sum(coef == 0)
        numb.nonzero <- sum(coef != 0)

        if (plot.opt) {
            plot(mod_lasso, breaks = FALSE, cex = 0.4, col = gray(0.6), ...)
            abline(v = sopt, lty = 2)
            title(paste(numb.zero, "coefficients are zero,", numb.nonzero, "are not zero"))
        }

        list(coefficients = coef, sopt = sopt, numb.zero = numb.zero, numb.nonzero = numb.nonzero, ind = ind)
    }

    lasso_res = lassoCV(y ~ X, data = data1, K = 10, fraction = seq(0.1, 1, by = 0.1))

    lasso_coef = lassocoef(y ~ X, data = data1, sopt = lasso_res$sopt)
    filename6 <- paste(trtname, "Lasso_coef.xls", sep = "_")
    write.xls(lasso_coef$coefficients, filename6)

    lasso_coef$numb.nonzero
    lasso_res$sopt

    object <- lars(X, y, type = "lasso")
    #s=0.2=lasso_res$sopt

    fits <- predict.lars(object, X, s = lasso_res$sopt, type = "fit", mode = "fraction")
    filename7 <- paste(trtname, "Lasso_pred.xls", sep = "_")
    write.xls(fits$fit, filename7)

    pr <- cbind(pr, fits$fit)
    lasso_coefficients <- lasso_coef$coefficients
    co <- cbind(co, lasso_coefficients)

    # ###############################
    #
    # 5) Ridge analysis
    #
    ####################################
    data1 <- data.frame(y, X = I(X))

    res_ridge <- plotRidge(y ~ X, data = data1, lambda = seq(10, 300, by = 1))

    res_ridge$lambdaopt
    #116
    predval <- res_ridge$predicted
    filename8 <- paste(trtname, "Ridge_pred.xls", sep = "_")
    write.xls(predval, filename8)
    rescoef <- lm.ridge(y ~ X, data = data1, lambda = res_ridge$lambdaopt)
    filename9 <- paste(trtname, "Ridge_coef.xls", sep = "_")
    write.xls(rescoef$coef, filename9)

    ridge_coefficients <- rescoef$coef
    pr <- cbind(pr, predval)
    co <- cbind(co, ridge_coefficients)

    ## ##########################
    #  6) Application of PLS
    #############################

    data1 <- data.frame(y, X = I(X))

    plsres <- plsr(y ~ X, data = data1, validation = "CV")
    summary(plsres)

    # To see the pattern where optimum components lies from CV

    plot(RMSEP(plsres), legendpos = "topright")

    # Better pic just for good annotation and xlim()
    # uses to check the proper components for PLS.
    # here I have used 10 , i.e within 10 components where
    # RMSEP is minimum
    plot(RMSEP(plsres), legendpos = "topright", xlim = c(0, 10), ylim = c(0, 20))
    rmsep <- RMSEP(plsres)

    # To see how much variance explained by each of the components
    explvar(plsres)
    opt_pls <- which(rmsep$val["adjCV", 1, ] == min(rmsep$val["adjCV", 1, ])) - 1

    coefpls <- coef(plsres, ncomp = opt_pls)
    filename10 <- paste(trtname, "PLS_coef.xls", sep = "_")
    write.xls(coefpls, filename10)

    # NOTE: these are fitted values, not independent predictions for new data !!
    predpls <- predict(plsres, ncomp = 2)
    filename11 <- paste(trtname, "PLS_pred.xls", sep = "_")
    write.xls(predpls, filename11)

    pr <- cbind(pr, predpls)
    co <- cbind(co, coefpls)

    #############################################################
    #
    # 7) Application of PCR
    #
    ###############################################################
    data1 <- data.frame(y, X = I(X))

    pcrres <- pcr(y ~ X, data = data1, validation = "CV")

    # To find out optimum component for PCR

    plot(RMSEP(pcrres), legendpos = "topright", xlim = c(0, 100))

    # By seeing the first plot it seems tht components lies betweeen
    #18 and 25 so, I want to magify this
    # this is not automated!

    plot(RMSEP(pcrres), legendpos = "topright", xlim = c(40, 50))
    rmsep <- RMSEP(pcrres)
    opt_pcr <- which(rmsep$val["adjCV", 1, ] == min(rmsep$val["adjCV", 1, ])) - 1
    opt_pcr

    explvar(pcrres)

    coefpcr <- coef(pcrres, ncomp = opt_pcr)
    filename12 <- paste(trtname, "PCR_coef.xls")
    write.xls(coefpcr, filename12)

    # NOTE: these are fitted values, not independent predictions for new data !!
    # Prediction of the flesh colour data

    predpcr <- predict(pcrres, ncomp = 42)
    filename13 <- paste(trtname, "PCR_pred.xls")
    write.xls(predpcr, filename13)

    pr <- cbind(pr, predpcr)
    co <- cbind(co, coefpcr)

    ######################################
    #
    # 8) SVM regression using e1071
    #
    ####################################
    data1 <- data.frame(y, X = I(X))

    # For optimization of the parameter
    # gamma = tuning parameter for RBF
    # cost=tuning parameter for SVR

    obj1 <- tune.svm(y ~ ., data = data1, sampling = "fix", gamma = 2^c(-8, -4, 0, 4), cost = 2^c(-8, -4, -2, 0, 2))
    obj1

    plot(obj1, transform.x = log2, transform.y = log2)

    # gamma and cost is taken from optimized value.
    # careful!!!Not automated

    m1 <- svm(X, y, gamma = obj1$best.parameters$gamma, cost = obj1$best.parameters$cost)
    new1 <- predict(m1, X)
    filename14 <- paste(trtname, "SVM_pred", sep = "_")
    write.xls(new1, filename14)

    pr <- cbind(pr, new1)

    ######################################
    # 9) Elastic net application using GLMNET
    # and caret
    ####################################
    con <- trainControl(method = "cv", number = 10)

    fit_glm <- NULL
    fit_glm233 <- train(X, y, method = "glmnet", metric = "RMSE", trControl = con, tuneLength = 10)
    fit_glm233$finalModel$tuneValue
    fit_glm233$finalModel$tuneValue$.alpha
    glmnetcalc <- glmnet(X, y, alpha = fit_glm233$finalModel$tuneValue$.alpha)

    fitcoef <- predict(glmnetcalc, s = fit_glm233$finalModel$tuneValue$.alpha, type = "coefficients")
    CoefEN <- as.matrix(fitcoef)
    filename15 <- paste(trtname, "Enet_coef_GLM.xls", sep = "_")
    write.xls(CoefEN[2:(dim(X)[2] + 1)], filename15)

    # for predicting with optimal alpha

    expred <- extractPrediction(list(fit_glm233))
    plotObsVsPred(expred)
    filename16 <- paste(trtname, "Enet_pred_GLM.xls", sep = "_")
    write.xls(expred, filename16)

    # The following code can also be used which has a similar function as avove
    fit_predict <- predict(fit_glm233, newdata = X)

    pr <- cbind(pr, expred)
    coef_enetglm <- CoefEN[2:(dim(X)[2] + 1)]
    co <- cbind(co, coef_enetglm)

    # extracting coeffcients ????

    ######################################
    # Elastic net application using ELASTIC NET
    #
    ####################################
    sds.cven <- cv.enet(x = X, y = y, s = seq(0, 1, length = 100), mode = "fraction", lambda = 0, plot.it = T, trace = FALSE)
    en.opt <- sds.cven$s[which(sds.cven$cv == min(sds.cven$cv))]
    # lambda=0.15 after cross validation
    en1 <- enet(x = X, y = y, lambda = en.opt, normalize = TRUE, intercept = TRUE)
    #plot(en1)
    # storing the values coefficients
    ypred <- predict.enet(en1, s = en.opt, type = "coef", mode = "fraction")

    # this command will store coefficients in the working directory in a XL file
    filename17 <- paste(trtname, "Enet_coef_fraction.xls", sep = "_")
    write.xls(ypred$coefficients, filename17)
    #

    # prediction
    enpred <- predict.enet(en1, newx = X, s = en.opt, type = "fit", mode = "fraction")
    filename18 <- paste(trtname, "Enet_pred_fraction.xls", sep = "_")
    write.xls(enpred$fit, filename18)

    pr <- cbind(pr, enpred$fit)
    coef_enet_enet <- ypred$coefficients
    co <- cbind(co, coef_enet_enet)

    ########################################
    # 10)  Stepwise Regression is done in
    # MATLAB using stepwise()
    #########################################
    filename20 <- paste(trtname, "pred.xls", sep = "_")
    filename21 <- paste(trtname, "coef.xls", sep = "_")
    filename22 <- paste(trtname, "coef2.xls", sep = "_")

    #rownames(co)<-rownames

    write.xls(pr, filename20)
    write.xls(x = co, file = filename21)
    write.xls(x = co, file = filename22, rowNames = rownames(co))
}
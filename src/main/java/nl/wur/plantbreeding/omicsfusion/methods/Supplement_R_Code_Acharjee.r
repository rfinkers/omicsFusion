####################  For calculating MSEP ############################################

rm(list = ls())

# Alternative
# install.packages('caret', dependencies = c('Depends', 'Suggests'))
library(xlsReadWrite)
library(caret)
library(randomForest)
library(spls)
library(chemometrics)
library(lars)
library(glmnet)
library(pls)
library(elasticnet)
library(e1071)
library(lars)
library(multtest)
library(penalized)

# read the data
data1 <- read.xls("Met163.xls")
dim(data1)

# Design matrix
DesignMatrix <- model.matrix(Flesh ~ . - 1, data1)
DesignMatrix <- scale(DesignMatrix)

############## Run the programme 100 times takes around 4 days and 16 hours
totiter <- 100
###########################################  PCR matrix initialization  #################################
coefsPCR1 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
y_fit_PCR1 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_pcr1 <- matrix(data = NA, nrow = 1, ncol = totiter)
opt_comp_pcr1 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsPCR2 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
y_fit_PCR2 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_pcr2 <- matrix(data = NA, nrow = 1, ncol = totiter)
opt_comp_pcr2 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsPCR3 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
y_fit_PCR3 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_pcr3 <- matrix(data = NA, nrow = 1, ncol = totiter)
opt_comp_pcr3 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsPCR4 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
y_fit_PCR4 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_pcr4 <- matrix(data = NA, nrow = 1, ncol = totiter)
opt_comp_pcr4 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsPCR5 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
y_fit_PCR5 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_pcr5 <- matrix(data = NA, nrow = 1, ncol = totiter)
opt_comp_pcr5 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsPCR6 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
y_fit_PCR6 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_pcr6 <- matrix(data = NA, nrow = 1, ncol = totiter)
opt_comp_pcr6 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsPCR7 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
y_fit_PCR7 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_pcr7 <- matrix(data = NA, nrow = 1, ncol = totiter)
opt_comp_pcr7 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsPCR8 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
y_fit_PCR8 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_pcr8 <- matrix(data = NA, nrow = 1, ncol = totiter)
opt_comp_pcr8 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsPCR9 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
y_fit_PCR9 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_pcr9 <- matrix(data = NA, nrow = 1, ncol = totiter)
opt_comp_pcr9 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsPCR10 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
y_fit_PCR10 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_pcr10 <- matrix(data = NA, nrow = 1, ncol = totiter)
opt_comp_pcr10 <- matrix(data = NA, nrow = 1, ncol = totiter)

test1_pcr <- matrix(data = NA, nrow = totiter, ncol = 2)
test2_pcr <- matrix(data = NA, nrow = totiter, ncol = 2)
test3_pcr <- matrix(data = NA, nrow = totiter, ncol = 2)
test4_pcr <- matrix(data = NA, nrow = totiter, ncol = 2)
test5_pcr <- matrix(data = NA, nrow = totiter, ncol = 2)
test6_pcr <- matrix(data = NA, nrow = totiter, ncol = 2)
test7_pcr <- matrix(data = NA, nrow = totiter, ncol = 2)
test8_pcr <- matrix(data = NA, nrow = totiter, ncol = 2)
test9_pcr <- matrix(data = NA, nrow = totiter, ncol = 2)
test10_pcr <- matrix(data = NA, nrow = totiter, ncol = 2)

##########################################  PLS matrix initialization#################################
coefsPLS1 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
y_fit_PLS1 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_pls1 <- matrix(data = NA, nrow = 1, ncol = totiter)
opt_comp_pls1 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsPLS2 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
y_fit_PLS2 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_pls2 <- matrix(data = NA, nrow = 1, ncol = totiter)
opt_comp_pls2 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsPLS3 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
y_fit_PLS3 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_pls3 <- matrix(data = NA, nrow = 1, ncol = totiter)
opt_comp_pls3 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsPLS4 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
y_fit_PLS4 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_pls4 <- matrix(data = NA, nrow = 1, ncol = totiter)
opt_comp_pls4 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsPLS5 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
y_fit_PLS5 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_pls5 <- matrix(data = NA, nrow = 1, ncol = totiter)
opt_comp_pls5 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsPLS6 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
y_fit_PLS6 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_pls6 <- matrix(data = NA, nrow = 1, ncol = totiter)
opt_comp_pls6 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsPLS7 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
y_fit_PLS7 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_pls7 <- matrix(data = NA, nrow = 1, ncol = totiter)
opt_comp_pls7 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsPLS8 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
y_fit_PLS8 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_pls8 <- matrix(data = NA, nrow = 1, ncol = totiter)
opt_comp_pls8 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsPLS9 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
y_fit_PLS9 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_pls9 <- matrix(data = NA, nrow = 1, ncol = totiter)
opt_comp_pls9 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsPLS10 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
y_fit_PLS10 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_pls10 <- matrix(data = NA, nrow = 1, ncol = totiter)
opt_comp_pls10 <- matrix(data = NA, nrow = 1, ncol = totiter)

test1_pls <- matrix(data = NA, nrow = totiter, ncol = 2)
test2_pls <- matrix(data = NA, nrow = totiter, ncol = 2)
test3_pls <- matrix(data = NA, nrow = totiter, ncol = 2)
test4_pls <- matrix(data = NA, nrow = totiter, ncol = 2)
test5_pls <- matrix(data = NA, nrow = totiter, ncol = 2)
test6_pls <- matrix(data = NA, nrow = totiter, ncol = 2)
test7_pls <- matrix(data = NA, nrow = totiter, ncol = 2)
test8_pls <- matrix(data = NA, nrow = totiter, ncol = 2)
test9_pls <- matrix(data = NA, nrow = totiter, ncol = 2)
test10_pls <- matrix(data = NA, nrow = totiter, ncol = 2)

#################### Elastic Net matrix Initiation  ##############################
coefsEN1 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
R2_en1 <- matrix(data = NA, nrow = 1, ncol = totiter)
en_frac_1 <- matrix(data = NA, nrow = 1, ncol = totiter)
en_lambda_1 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsEN2 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
R2_en2 <- matrix(data = NA, nrow = 1, ncol = totiter)
en_frac_2 <- matrix(data = NA, nrow = 1, ncol = totiter)
en_lambda_2 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsEN3 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
R2_en3 <- matrix(data = NA, nrow = 1, ncol = totiter)
en_frac_3 <- matrix(data = NA, nrow = 1, ncol = totiter)
en_lambda_3 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsEN4 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
R2_en4 <- matrix(data = NA, nrow = 1, ncol = totiter)
en_frac_4 <- matrix(data = NA, nrow = 1, ncol = totiter)
en_lambda_4 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsEN5 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
R2_en5 <- matrix(data = NA, nrow = 1, ncol = totiter)
en_frac_5 <- matrix(data = NA, nrow = 1, ncol = totiter)
en_lambda_5 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsEN6 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
R2_en6 <- matrix(data = NA, nrow = 1, ncol = totiter)
en_frac_6 <- matrix(data = NA, nrow = 1, ncol = totiter)
en_lambda_6 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsEN7 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
R2_en7 <- matrix(data = NA, nrow = 1, ncol = totiter)
en_frac_7 <- matrix(data = NA, nrow = 1, ncol = totiter)
en_lambda_7 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsEN8 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
R2_en8 <- matrix(data = NA, nrow = 1, ncol = totiter)
en_frac_8 <- matrix(data = NA, nrow = 1, ncol = totiter)
en_lambda_8 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsEN9 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
R2_en9 <- matrix(data = NA, nrow = 1, ncol = totiter)
en_frac_9 <- matrix(data = NA, nrow = 1, ncol = totiter)
en_lambda_9 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsEN10 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
R2_en10 <- matrix(data = NA, nrow = 1, ncol = totiter)
en_frac_10 <- matrix(data = NA, nrow = 1, ncol = totiter)
en_lambda_10 <- matrix(data = NA, nrow = 1, ncol = totiter)

test1_en <- matrix(data = NA, nrow = totiter, ncol = 2)
test2_en <- matrix(data = NA, nrow = totiter, ncol = 2)
test3_en <- matrix(data = NA, nrow = totiter, ncol = 2)
test4_en <- matrix(data = NA, nrow = totiter, ncol = 2)
test5_en <- matrix(data = NA, nrow = totiter, ncol = 2)
test6_en <- matrix(data = NA, nrow = totiter, ncol = 2)
test7_en <- matrix(data = NA, nrow = totiter, ncol = 2)
test8_en <- matrix(data = NA, nrow = totiter, ncol = 2)
test9_en <- matrix(data = NA, nrow = totiter, ncol = 2)
test10_en <- matrix(data = NA, nrow = totiter, ncol = 2)

#################### Matrix initialization for LASSO  #######################
coefsLASSO1 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
R2_lasso1 <- matrix(data = NA, nrow = 1, ncol = totiter)
lasso_lambda_1 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsLASSO2 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
R2_lasso2 <- matrix(data = NA, nrow = 1, ncol = totiter)
lasso_lambda_2 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsLASSO3 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
R2_lasso3 <- matrix(data = NA, nrow = 1, ncol = totiter)
lasso_lambda_3 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsLASSO4 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
R2_lasso4 <- matrix(data = NA, nrow = 1, ncol = totiter)
lasso_lambda_4 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsLASSO5 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
R2_lasso5 <- matrix(data = NA, nrow = 1, ncol = totiter)
lasso_lambda_5 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsLASSO6 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
R2_lasso6 <- matrix(data = NA, nrow = 1, ncol = totiter)
lasso_lambda_6 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsLASSO7 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
R2_lasso7 <- matrix(data = NA, nrow = 1, ncol = totiter)
lasso_lambda_7 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsLASSO8 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
R2_lasso8 <- matrix(data = NA, nrow = 1, ncol = totiter)
lasso_lambda_8 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsLASSO9 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
R2_lasso9 <- matrix(data = NA, nrow = 1, ncol = totiter)
lasso_lambda_9 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsLASSO10 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
R2_lasso10 <- matrix(data = NA, nrow = 1, ncol = totiter)
lasso_lambda_10 <- matrix(data = NA, nrow = 1, ncol = totiter)

test1_lasso <- matrix(data = NA, nrow = totiter, ncol = 2)
test2_lasso <- matrix(data = NA, nrow = totiter, ncol = 2)
test3_lasso <- matrix(data = NA, nrow = totiter, ncol = 2)
test4_lasso <- matrix(data = NA, nrow = totiter, ncol = 2)
test5_lasso <- matrix(data = NA, nrow = totiter, ncol = 2)
test6_lasso <- matrix(data = NA, nrow = totiter, ncol = 2)
test7_lasso <- matrix(data = NA, nrow = totiter, ncol = 2)
test8_lasso <- matrix(data = NA, nrow = totiter, ncol = 2)
test9_lasso <- matrix(data = NA, nrow = totiter, ncol = 2)
test10_lasso <- matrix(data = NA, nrow = totiter, ncol = 2)

##### SVM Regression ############################################
svm_tune_Cost1 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
svm_tune_Sigma1 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_svm1 <- matrix(data = NA, nrow = 1, ncol = totiter)

svm_tune_Cost2 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
svm_tune_Sigma2 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_svm2 <- matrix(data = NA, nrow = 1, ncol = totiter)

svm_tune_Cost3 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
svm_tune_Sigma3 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_svm3 <- matrix(data = NA, nrow = 1, ncol = totiter)

svm_tune_Cost4 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
svm_tune_Sigma4 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_svm4 <- matrix(data = NA, nrow = 1, ncol = totiter)

svm_tune_Cost5 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
svm_tune_Sigma5 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_svm5 <- matrix(data = NA, nrow = 1, ncol = totiter)

svm_tune_Cost6 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
svm_tune_Sigma6 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_svm6 <- matrix(data = NA, nrow = 1, ncol = totiter)

svm_tune_Cost7 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
svm_tune_Sigma7 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_svm7 <- matrix(data = NA, nrow = 1, ncol = totiter)

svm_tune_Cost8 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
svm_tune_Sigma8 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_svm8 <- matrix(data = NA, nrow = 1, ncol = totiter)

svm_tune_Cost9 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
svm_tune_Sigma9 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_svm9 <- matrix(data = NA, nrow = 1, ncol = totiter)

svm_tune_Cost10 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
svm_tune_Sigma10 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_svm10 <- matrix(data = NA, nrow = 1, ncol = totiter)

test1_svm <- matrix(data = NA, nrow = totiter, ncol = 2)
test2_svm <- matrix(data = NA, nrow = totiter, ncol = 2)
test3_svm <- matrix(data = NA, nrow = totiter, ncol = 2)
test4_svm <- matrix(data = NA, nrow = totiter, ncol = 2)
test5_svm <- matrix(data = NA, nrow = totiter, ncol = 2)
test6_svm <- matrix(data = NA, nrow = totiter, ncol = 2)
test7_svm <- matrix(data = NA, nrow = totiter, ncol = 2)
test8_svm <- matrix(data = NA, nrow = totiter, ncol = 2)
test9_svm <- matrix(data = NA, nrow = totiter, ncol = 2)
test10_svm <- matrix(data = NA, nrow = totiter, ncol = 2)

################## SPLS Matrix Initialization###########################
Coefspls1 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_spls1 <- matrix(data = NA, nrow = 1, ncol = totiter)
spls_K_1 <- matrix(data = NA, nrow = 1, ncol = totiter)
spls_eta_1 <- matrix(data = NA, nrow = 1, ncol = totiter)

Coefspls2 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_spls2 <- matrix(data = NA, nrow = 1, ncol = totiter)
spls_K_2 <- matrix(data = NA, nrow = 1, ncol = totiter)
spls_eta_2 <- matrix(data = NA, nrow = 1, ncol = totiter)

Coefspls3 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_spls3 <- matrix(data = NA, nrow = 1, ncol = totiter)
spls_K_3 <- matrix(data = NA, nrow = 1, ncol = totiter)
spls_eta_3 <- matrix(data = NA, nrow = 1, ncol = totiter)

Coefspls4 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_spls4 <- matrix(data = NA, nrow = 1, ncol = totiter)
spls_K_4 <- matrix(data = NA, nrow = 1, ncol = totiter)
spls_eta_4 <- matrix(data = NA, nrow = 1, ncol = totiter)

Coefspls5 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_spls5 <- matrix(data = NA, nrow = 1, ncol = totiter)
spls_K_5 <- matrix(data = NA, nrow = 1, ncol = totiter)
spls_eta_5 <- matrix(data = NA, nrow = 1, ncol = totiter)

Coefspls6 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_spls6 <- matrix(data = NA, nrow = 1, ncol = totiter)
spls_K_6 <- matrix(data = NA, nrow = 1, ncol = totiter)
spls_eta_6 <- matrix(data = NA, nrow = 1, ncol = totiter)

Coefspls7 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_spls7 <- matrix(data = NA, nrow = 1, ncol = totiter)
spls_K_7 <- matrix(data = NA, nrow = 1, ncol = totiter)
spls_eta_7 <- matrix(data = NA, nrow = 1, ncol = totiter)

Coefspls8 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_spls8 <- matrix(data = NA, nrow = 1, ncol = totiter)
spls_K_8 <- matrix(data = NA, nrow = 1, ncol = totiter)
spls_eta_8 <- matrix(data = NA, nrow = 1, ncol = totiter)

Coefspls9 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_spls9 <- matrix(data = NA, nrow = 1, ncol = totiter)
spls_K_9 <- matrix(data = NA, nrow = 1, ncol = totiter)
spls_eta_9 <- matrix(data = NA, nrow = 1, ncol = totiter)

Coefspls10 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_spls10 <- matrix(data = NA, nrow = 1, ncol = totiter)
spls_K_10 <- matrix(data = NA, nrow = 1, ncol = totiter)
spls_eta_10 <- matrix(data = NA, nrow = 1, ncol = totiter)

test1_spls <- matrix(data = NA, nrow = totiter, ncol = 2)
test2_spls <- matrix(data = NA, nrow = totiter, ncol = 2)
test3_spls <- matrix(data = NA, nrow = totiter, ncol = 2)
test4_spls <- matrix(data = NA, nrow = totiter, ncol = 2)
test5_spls <- matrix(data = NA, nrow = totiter, ncol = 2)
test6_spls <- matrix(data = NA, nrow = totiter, ncol = 2)
test7_spls <- matrix(data = NA, nrow = totiter, ncol = 2)
test8_spls <- matrix(data = NA, nrow = totiter, ncol = 2)
test9_spls <- matrix(data = NA, nrow = totiter, ncol = 2)
test10_spls <- matrix(data = NA, nrow = totiter, ncol = 2)

############## Random Forest matrix initialization ###################
R2_rf1 <- matrix(data = NA, nrow = 1, ncol = totiter)
rf_mtry1 <- matrix(data = NA, nrow = 1, ncol = totiter)

R2_rf2 <- matrix(data = NA, nrow = 1, ncol = totiter)
rf_mtry2 <- matrix(data = NA, nrow = 1, ncol = totiter)

R2_rf3 <- matrix(data = NA, nrow = 1, ncol = totiter)
rf_mtry3 <- matrix(data = NA, nrow = 1, ncol = totiter)

R2_rf4 <- matrix(data = NA, nrow = 1, ncol = totiter)
rf_mtry4 <- matrix(data = NA, nrow = 1, ncol = totiter)

R2_rf5 <- matrix(data = NA, nrow = 1, ncol = totiter)
rf_mtry5 <- matrix(data = NA, nrow = 1, ncol = totiter)

R2_rf6 <- matrix(data = NA, nrow = 1, ncol = totiter)
rf_mtry6 <- matrix(data = NA, nrow = 1, ncol = totiter)

R2_rf7 <- matrix(data = NA, nrow = 1, ncol = totiter)
rf_mtry7 <- matrix(data = NA, nrow = 1, ncol = totiter)

R2_rf8 <- matrix(data = NA, nrow = 1, ncol = totiter)
rf_mtry8 <- matrix(data = NA, nrow = 1, ncol = totiter)

R2_rf9 <- matrix(data = NA, nrow = 1, ncol = totiter)
rf_mtry9 <- matrix(data = NA, nrow = 1, ncol = totiter)

R2_rf10 <- matrix(data = NA, nrow = 1, ncol = totiter)
rf_mtry10 <- matrix(data = NA, nrow = 1, ncol = totiter)

rf_imp_1 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
rf_imp_2 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
rf_imp_3 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
rf_imp_4 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
rf_imp_5 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
rf_imp_6 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
rf_imp_7 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
rf_imp_8 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
rf_imp_9 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
rf_imp_10 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)

test1_rf <- matrix(data = NA, nrow = totiter, ncol = 2)
test2_rf <- matrix(data = NA, nrow = totiter, ncol = 2)
test3_rf <- matrix(data = NA, nrow = totiter, ncol = 2)
test4_rf <- matrix(data = NA, nrow = totiter, ncol = 2)
test5_rf <- matrix(data = NA, nrow = totiter, ncol = 2)
test6_rf <- matrix(data = NA, nrow = totiter, ncol = 2)
test7_rf <- matrix(data = NA, nrow = totiter, ncol = 2)
test8_rf <- matrix(data = NA, nrow = totiter, ncol = 2)
test9_rf <- matrix(data = NA, nrow = totiter, ncol = 2)
test10_rf <- matrix(data = NA, nrow = totiter, ncol = 2)

###################  Ridge Regression initialization ###################
coefsRidge1 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
ridge_lambda_1 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsRidge2 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
ridge_lambda_2 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsRidge3 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
ridge_lambda_3 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsRidge4 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
ridge_lambda_4 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsRidge5 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
ridge_lambda_5 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsRidge6 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
ridge_lambda_6 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsRidge7 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
ridge_lambda_7 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsRidge8 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
ridge_lambda_8 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsRidge9 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
ridge_lambda_9 <- matrix(data = NA, nrow = 1, ncol = totiter)

coefsRidge10 <- matrix(data = NA, nrow = dim(DesignMatrix)[2] + 1, ncol = totiter)
ridge_lambda_10 <- matrix(data = NA, nrow = 1, ncol = totiter)

y_fit_ridge1 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_ridge1 <- matrix(data = NA, nrow = 1, ncol = totiter)

y_fit_ridge2 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_ridge2 <- matrix(data = NA, nrow = 1, ncol = totiter)

y_fit_ridge3 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_ridge3 <- matrix(data = NA, nrow = 1, ncol = totiter)

y_fit_ridge4 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_ridge4 <- matrix(data = NA, nrow = 1, ncol = totiter)

y_fit_ridge5 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_ridge5 <- matrix(data = NA, nrow = 1, ncol = totiter)

y_fit_ridge6 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_ridge6 <- matrix(data = NA, nrow = 1, ncol = totiter)

y_fit_ridge7 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_ridge7 <- matrix(data = NA, nrow = 1, ncol = totiter)

y_fit_ridge8 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_ridge8 <- matrix(data = NA, nrow = 1, ncol = totiter)

y_fit_ridge9 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_ridge9 <- matrix(data = NA, nrow = 1, ncol = totiter)

y_fit_ridge10 <- matrix(data = NA, nrow = dim(DesignMatrix)[2], ncol = totiter)
R2_ridge10 <- matrix(data = NA, nrow = 1, ncol = totiter)

test1_ridge <- matrix(data = NA, nrow = totiter, ncol = 2)
test2_ridge <- matrix(data = NA, nrow = totiter, ncol = 2)
test3_ridge <- matrix(data = NA, nrow = totiter, ncol = 2)
test4_ridge <- matrix(data = NA, nrow = totiter, ncol = 2)
test5_ridge <- matrix(data = NA, nrow = totiter, ncol = 2)
test6_ridge <- matrix(data = NA, nrow = totiter, ncol = 2)
test7_ridge <- matrix(data = NA, nrow = totiter, ncol = 2)
test8_ridge <- matrix(data = NA, nrow = totiter, ncol = 2)
test9_ridge <- matrix(data = NA, nrow = totiter, ncol = 2)
test10_ridge <- matrix(data = NA, nrow = totiter, ncol = 2)

################  Start the iteration #############################
for (index in 1:totiter) {

    print(index)

    # 10 fold Cross validation with input data (outer train)

    inTrain1 <- createFolds(data1$Flesh, k = 10, list = TRUE, returnTrain = T)
    # outer train data set

    Train1 <- inTrain1$"1"

    tr1 <- DesignMatrix[Train1, ]

    # outer test
    ts1 <- DesignMatrix[-Train1, ]
    #preProc1 <- preProcess(tr1,method='scale')
    trainBH1 <- tr1
    testBH1 <- ts1
    trainFlesh1 <- data1$Flesh[Train1]
    testFlesh1 <- data1$Flesh[-Train1]

    con <- trainControl(method = "cv", number = 10)

    # PCR
    # 10 fold CV for outer train
    # storing the values like r2,coeff., ncomp

    pcrFit1 <- train(tr1, trainFlesh1, "pcr", metric = "RMSE", tuneLength = 50, trControl = con)
    coefsPCR1[, index] <- coef(pcrFit1$finalModel, ncomp = pcrFit1$finalModel$tuneValue$.ncomp)
    y_fit_PCR1 <- predict(pcrFit1$finalModel, ncomp = pcrFit1$finalModel$tuneValue$.ncomp)
    R2_pcr1 <- (cor(y_fit_PCR1, trainFlesh1)^2) * 100
    opt_comp_pcr1[, index] <- pcrFit1$finalModel$tuneValue$.ncomp

    #calculation for test data
    bhModels1_pcr <- list(pcr = pcrFit1)
    #bhPred1_pcr <- predict(bhModels1_pcr, newdata = testBH1)
    allPred1_pcr <- extractPrediction(bhModels1_pcr, testX = testBH1, testY = testFlesh1)
    testPred1_pcr <- subset(allPred1_pcr, dataType == "Test")
    sorted1_pcr <- as.matrix(by(testPred1_pcr, list(model = testPred1_pcr$model), function(x) postResample(x$pred, x$obs)))

    # MSEP and r2
    test1_pcr[index, ] <- sorted1_pcr[, 1]$pcr

    #PLS
    plsFit1 <- train(tr1, trainFlesh1, "pls", metric = "RMSE", tuneLength = 10, trControl = con)
    coefsPLS1[, index] <- coef(plsFit1$finalModel, ncomp = plsFit1$finalModel$tuneValue$.ncomp)
    y_fit_PLS1 <- predict(plsFit1$finalModel, ncomp = plsFit1$finalModel$tuneValue$.ncomp)
    R2_pls1[, index] <- (cor(y_fit_PLS1, trainFlesh1)^2) * 100
    opt_comp_pls1[, index] <- plsFit1$finalModel$tuneValue$.ncomp

    bhModels1_pls <- list(pls = plsFit1)
    #bhPred1_pls <- predict(bhModels1_pls, newdata = testBH1)
    allPred1_pls <- extractPrediction(bhModels1_pls, testX = testBH1, testY = testFlesh1)
    testPred1_pls <- subset(allPred1_pls, dataType == "Test")
    sorted1_pls <- as.matrix(by(testPred1_pls, list(model = testPred1_pls$model), function(x) postResample(x$pred, x$obs)))
    test1_pls[index, ] <- sorted1_pls[, 1]$pls

    #EN
    enFit1 <- train(tr1, trainFlesh1, "glmnet", metric = "RMSE", tuneLength = 10, trControl = con)
    en_frac_1[, index] <- enFit1$finalModel$tuneValue$.alpha
    en_lambda_1[, index] <- enFit1$finalModel$tuneValue$.lambda
    coefsEN1[, index] <- as.matrix(coef(enFit1$finalModel, s = enFit1$finalModel$tuneValue$.lambda))
    predsEN1 <- predict(enFit1$finalModel, newx = tr1, s = enFit1$finalModel$tuneValue$.lambda, type = "response")
    y_fit_en1 <- predsEN1[, 1]
    R2_en1[, index] <- (cor(trainFlesh1, y_fit_en1)^2) * 100

    bhModels1_en <- list(glmnet = enFit1)
    #bhPred1_en<- predict(bhModels1_en, newdata = testBH1)
    allPred1_en <- extractPrediction(bhModels1_en, testX = testBH1, testY = testFlesh1)
    testPred1_en <- subset(allPred1_en, dataType == "Test")
    sorted1_en <- as.matrix(by(testPred1_en, list(model = testPred1_en$model), function(x) postResample(x$pred, x$obs)))
    test1_en[index, ] <- sorted1_en[, 1]$glmnet

    #LASSO
    lassoFit1 <- train(tr1, trainFlesh1, "glmnet", metric = "RMSE", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 1, by = 0.1), .alpha = 1), trControl = con)
    lasso_lambda_1[, index] <- lassoFit1$bestTune$.lambda
    coefsLASSO1[, index] <- as.matrix(coef(lassoFit1$finalModel, s = lassoFit1$finalModel$tuneValue$.lambda))
    predslasso1 <- predict(lassoFit1$finalModel, newx = tr1, s = lassoFit1$finalModel$tuneValue$.lambda, type = "response")
    y_fit_lasso1 <- predslasso1[, 1]
    R2_lasso1[, index] <- (cor(trainFlesh1, y_fit_lasso1)^2) * 100

    bhModels1_lasso <- list(glmnet = lassoFit1)
    #bhPred1_lasso<- predict(bhModels1_lasso, newdata = testBH1)
    allPred1_lasso <- extractPrediction(bhModels1_lasso, testX = testBH1, testY = testFlesh1)
    testPred1_lasso <- subset(allPred1_lasso, dataType == "Test")
    sorted1_lasso <- as.matrix(by(testPred1_lasso, list(model = testPred1_lasso$model), function(x) postResample(x$pred, x$obs)))
    test1_lasso[index, ] <- sorted1_lasso[, 1]$glmnet

    #SVM
    svmFit1 <- train(tr1, trainFlesh1, "svmRadial", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_svm1 <- predict(svmFit1$finalModel)
    svm_tune_Cost1[, index] <- svmFit1$bestTune$.C
    svm_tune_Sigma1[, index] <- svmFit1$bestTune$.sigma
    R2_svm1[, index] <- (cor(y_fit_svm1, trainFlesh1)^2) * 100

    bhModels1_svm <- list(svmRadial = svmFit1)
    #bhPred1_svm<- predict(bhModels1_svm, newdata = testBH1)
    allPred1_svm <- extractPrediction(bhModels1_svm, testX = testBH1, testY = testFlesh1)
    testPred1_svm <- subset(allPred1_svm, dataType == "Test")
    sorted1_svm <- as.matrix(by(testPred1_svm, list(model = testPred1_svm$model), function(x) postResample(x$pred, x$obs)))
    test1_svm[index, ] <- sorted1_svm[, 1]$svm

    #SPLS
    splsFit1 <- train(tr1, trainFlesh1, "spls", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_spls1 <- predict(splsFit1$finalModel)
    Coefspls1[, index] <- predict(splsFit1$finalModel, type = "coefficient", splsFit1$finalModel$tuneValue$.eta, splsFit1$finalModel$tuneValue$.K)
    R2_spls1 <- (cor(y_fit_spls1, trainFlesh1)^2) * 100
    spls_K_1[, index] <- splsFit1$finalModel$tuneValue$.K
    spls_eta_1[, index] <- splsFit1$finalModel$tuneValue$.eta

    bhModels1_spls <- list(spls = splsFit1)
    #bhPred1_spls<- predict(bhModels1_spls, newdata = testBH1)
    allPred1_spls <- extractPrediction(bhModels1_spls, testX = testBH1, testY = testFlesh1)
    testPred1_spls <- subset(allPred1_spls, dataType == "Test")
    sorted1_spls <- as.matrix(by(testPred1_spls, list(model = testPred1_spls$model), function(x) postResample(x$pred, x$obs)))
    test1_spls[index, ] <- sorted1_spls[, 1]$spls

    #RF
    rfFit1 <- train(tr1, trainFlesh1, "rf", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_rf1 <- predict(rfFit1$finalModel)
    R2_rf1[, index] <- (cor(y_fit_rf1, trainFlesh1)^2) * 100
    rf_mtry1[, index] <- rfFit1$finalModel$tuneValue$.mtry
    rf_imp_1[, index] <- rfFit1$finalModel$importance

    bhModels1_rf <- list(rf = rfFit1)
    #bhPred1_rf<- predict(bhModels1_rf, newdata = testBH1)
    allPred1_rf <- extractPrediction(bhModels1_rf, testX = testBH1, testY = testFlesh1)
    testPred1_rf <- subset(allPred1_rf, dataType == "Test")
    sorted1_rf <- as.matrix(by(testPred1_rf, list(model = testPred1_rf$model), function(x) postResample(x$pred, x$obs)))
    test1_rf[index, ] <- sorted1_rf[, 1]$rf

    #RIDGE
    ridgeFit1 <- train(tr1, trainFlesh1, "glmnet", metric = "RMSE", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 100, by = 0.1), .alpha = 0), trControl = con)
    ridge_lambda_1[, index] <- ridgeFit1$bestTune$.lambda
    coefsRidge1[, index] <- as.matrix(coef(ridgeFit1$finalModel, s = ridgeFit1$finalModel$tuneValue$.lambda))
    predsridge1 <- predict(ridgeFit1$finalModel, newx = tr1, s = ridgeFit1$finalModel$tuneValue$.lambda, type = "response")
    y_fit_ridge1 <- predsridge1[, 1]
    R2_ridge1[, index] <- (cor(trainFlesh1, y_fit_ridge1)^2) * 100

    bhModels1_ridge <- list(glmnet = ridgeFit1)
    allPred1_ridge <- extractPrediction(bhModels1_ridge, testX = testBH1, testY = testFlesh1)
    testPred1_ridge <- subset(allPred1_ridge, dataType == "Test")
    sorted1_ridge <- as.matrix(by(testPred1_ridge, list(model = testPred1_ridge$model), function(x) postResample(x$pred, x$obs)))
    test1_ridge[index, ] <- sorted1_ridge[, 1]$glmnet

    ##################################################### 2nd part of the data set###################################
    Train2 <- inTrain1$"2"
    tr2 <- DesignMatrix[Train2, ]
    ts2 <- DesignMatrix[-Train2, ]
    #preProc2 <- preProcess(tr2,method='scale')
    trainBH2 <- tr2
    testBH2 <- ts2
    trainFlesh2 <- data1$Flesh[Train2]
    testFlesh2 <- data1$Flesh[-Train2]

    # PCR
    pcrFit2 <- train(tr2, trainFlesh2, "pcr", metric = "RMSE", tuneLength = 50, trControl = con)
    coefsPCR2[, index] <- coef(pcrFit2$finalModel, ncomp = pcrFit2$finalModel$tuneValue$.ncomp)
    y_fit_PCR2 <- predict(pcrFit2$finalModel, ncomp = pcrFit2$finalModel$tuneValue$.ncomp)
    R2_pcr2[, index] <- (cor(y_fit_PCR2, trainFlesh2)^2) * 100
    opt_comp_pcr2[, index] <- pcrFit2$finalModel$tuneValue$.ncomp

    bhModels2_pcr <- list(pcr = pcrFit2)
    #bhPred2_pcr <- predict(bhModels2_pcr, newdata = testBH2)
    allPred2_pcr <- extractPrediction(bhModels2_pcr, testX = testBH2, testY = testFlesh2)
    testPred2_pcr <- subset(allPred2_pcr, dataType == "Test")
    sorted2_pcr <- as.matrix(by(testPred2_pcr, list(model = testPred2_pcr$model), function(x) postResample(x$pred, x$obs)))
    test2_pcr[index, ] <- sorted2_pcr[, 1]$pcr

    #PLS
    plsFit2 <- train(tr2, trainFlesh2, "pls", metric = "RMSE", tuneLength = 10, trControl = con)
    coefsPLS2[, index] <- coef(plsFit2$finalModel, ncomp = plsFit2$finalModel$tuneValue$.ncomp)
    y_fit_PLS2 <- predict(plsFit2$finalModel, ncomp = plsFit2$finalModel$tuneValue$.ncomp)
    R2_pls2[, index] <- (cor(y_fit_PLS2, trainFlesh2)^2) * 100
    opt_comp_pls2[, index] <- plsFit2$finalModel$tuneValue$.ncomp

    bhModels2_pls <- list(pls = plsFit2)
    #bhPred2_pls <- predict(bhModels2_pls, newdata = testBH2)
    allPred2_pls <- extractPrediction(bhModels2_pls, testX = testBH2, testY = testFlesh2)
    testPred2_pls <- subset(allPred2_pls, dataType == "Test")
    sorted2_pls <- as.matrix(by(testPred2_pls, list(model = testPred2_pls$model), function(x) postResample(x$pred, x$obs)))
    test2_pls[index, ] <- sorted2_pls[, 1]$pls

    #EN
    enFit2 <- train(tr2, trainFlesh2, "glmnet", metric = "RMSE", tuneLength = 10, trControl = con)
    en_frac_2[, index] <- enFit2$finalModel$tuneValue$.alpha
    en_lambda_2[, index] <- enFit2$finalModel$tuneValue$.lambda
    coefsEN2[, index] <- as.matrix(coef(enFit2$finalModel, s = enFit1$finalModel$tuneValue$.lambda))
    predsEN2 <- predict(enFit2$finalModel, newx = tr2, s = enFit2$finalModel$tuneValue$.lambda, type = "response")
    y_fit_en2 <- predsEN2[, 1]
    R2_en2[, index] <- (cor(trainFlesh2, y_fit_en2)^2) * 100

    bhModels2_en <- list(glmnet = enFit2)
    #bhPred2_en<- predict(bhModels2_en, newdata = testBH2)
    allPred2_en <- extractPrediction(bhModels2_en, testX = testBH2, testY = testFlesh2)
    testPred2_en <- subset(allPred2_en, dataType == "Test")
    sorted2_en <- as.matrix(by(testPred2_en, list(model = testPred2_en$model), function(x) postResample(x$pred, x$obs)))
    test2_en[index, ] <- sorted2_en[, 1]$glmnet

    #LASSO
    lassoFit2 <- train(tr2, trainFlesh2, "glmnet", metric = "RMSE", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 1, by = 0.1), .alpha = 1), trControl = con)
    lasso_lambda_2[, index] <- lassoFit2$bestTune$.lambda
    coefsLASSO2[, index] <- as.matrix(coef(lassoFit2$finalModel, s = lassoFit2$finalModel$tuneValue$.lambda))
    predslasso2 <- predict(lassoFit2$finalModel, newx = tr2, s = lassoFit2$finalModel$tuneValue$.lambda, type = "response")
    y_fit_lasso2 <- predslasso2[, 1]
    R2_lasso2[, index] <- (cor(trainFlesh2, y_fit_lasso2)^2) * 100

    bhModels2_lasso <- list(glmnet = lassoFit2)
    #bhPred2_lasso<- predict(bhModels2_lasso, newdata = testBH2)
    allPred2_lasso <- extractPrediction(bhModels2_lasso, testX = testBH2, testY = testFlesh2)
    testPred2_lasso <- subset(allPred2_lasso, dataType == "Test")
    sorted2_lasso <- as.matrix(by(testPred2_lasso, list(model = testPred2_lasso$model), function(x) postResample(x$pred, x$obs)))
    test2_lasso[index, ] <- sorted2_lasso[, 1]$glmnet

    # SVM
    svmFit2 <- train(tr2, trainFlesh2, "svmRadial", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_svm2 <- predict(svmFit2$finalModel)
    svm_tune_Cost2[, index] <- svmFit2$bestTune$.C
    svm_tune_Sigma2[, index] <- svmFit2$bestTune$.sigma
    R2_svm2[, index] <- (cor(y_fit_svm2, trainFlesh2)^2) * 100

    bhModels2_svm <- list(svmRadial = svmFit2)
    #bhPred2_svm<- predict(bhModels2_svm, newdata = testBH2)
    allPred2_svm <- extractPrediction(bhModels2_svm, testX = testBH2, testY = testFlesh2)
    testPred2_svm <- subset(allPred2_svm, dataType == "Test")
    sorted2_svm <- as.matrix(by(testPred2_svm, list(model = testPred2_svm$model), function(x) postResample(x$pred, x$obs)))
    test2_svm[index, ] <- sorted2_svm[, 1]$svm

    # SPLS
    splsFit2 <- train(tr2, trainFlesh2, "spls", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_spls2 <- predict(splsFit2$finalModel)
    Coefspls2[, index] <- predict(splsFit2$finalModel, type = "coefficient", splsFit2$finalModel$tuneValue$.eta, splsFit2$finalModel$tuneValue$.K)
    R2_spls2[, index] <- (cor(y_fit_spls2, trainFlesh2)^2) * 100
    spls_K_2[, index] <- splsFit2$finalModel$tuneValue$.K
    spls_eta_2[, index] <- splsFit2$finalModel$tuneValue$.eta

    bhModels2_spls <- list(spls = splsFit2)
    #bhPred2_spls<- predict(bhModels2_svm, newdata = testBH2)
    allPred2_spls <- extractPrediction(bhModels2_spls, testX = testBH2, testY = testFlesh2)
    testPred2_spls <- subset(allPred2_spls, dataType == "Test")
    sorted2_spls <- as.matrix(by(testPred2_spls, list(model = testPred2_spls$model), function(x) postResample(x$pred, x$obs)))
    test2_spls[index, ] <- sorted2_spls[, 1]$spls

    # RF
    rfFit2 <- train(tr2, trainFlesh2, "rf", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_rf2 <- predict(rfFit2$finalModel)
    R2_rf2[, index] <- (cor(y_fit_rf2, trainFlesh2)^2) * 100
    rf_mtry2[, index] <- rfFit2$finalModel$tuneValue$.mtry
    rf_imp_2[, index] <- rfFit2$finalModel$importance

    bhModels2_rf <- list(rf = rfFit2)
    #bhPred2_rf<- predict(bhModels2_rf, newdata = testBH2)
    allPred2_rf <- extractPrediction(bhModels2_rf, testX = testBH2, testY = testFlesh2)
    testPred2_rf <- subset(allPred2_rf, dataType == "Test")
    sorted2_rf <- as.matrix(by(testPred2_rf, list(model = testPred2_rf$model), function(x) postResample(x$pred, x$obs)))
    test2_rf[index, ] <- sorted2_rf[, 1]$rf

    # RIDGE
    ridgeFit2 <- train(tr2, trainFlesh2, "glmnet", metric = "RMSE", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 100, by = 0.1), .alpha = 0), trControl = con)
    ridge_lambda_2[, index] <- ridgeFit2$bestTune$.lambda
    coefsRidge2[, index] <- as.matrix(coef(ridgeFit2$finalModel, s = ridgeFit2$finalModel$tuneValue$.lambda))
    predsridge2 <- predict(ridgeFit2$finalModel, newx = tr2, s = ridgeFit2$finalModel$tuneValue$.lambda, type = "response")
    y_fit_ridge2 <- predsridge2[, 1]
    R2_ridge2[, index] <- (cor(trainFlesh2, y_fit_ridge2)^2) * 100

    bhModels2_ridge <- list(glmnet = ridgeFit2)
    allPred2_ridge <- extractPrediction(bhModels2_ridge, testX = testBH2, testY = testFlesh2)
    testPred2_ridge <- subset(allPred2_ridge, dataType == "Test")
    sorted2_ridge <- as.matrix(by(testPred2_ridge, list(model = testPred2_ridge$model), function(x) postResample(x$pred, x$obs)))
    test2_ridge[index, ] <- sorted2_ridge[, 1]$glmnet

    ####-------------------------------Split 3-------------------------
    Train3 <- inTrain1$"3"
    tr3 <- DesignMatrix[Train3, ]
    ts3 <- DesignMatrix[-Train3, ]
    #preProc3 <- preProcess(tr3,method='scale')
    trainBH3 <- tr3
    testBH3 <- ts3
    trainFlesh3 <- data1$Flesh[Train3]
    testFlesh3 <- data1$Flesh[-Train3]

    # PCR
    pcrFit3 <- train(tr3, trainFlesh3, "pcr", metric = "RMSE", tuneLength = 50, trControl = con)
    coefsPCR3[, index] <- coef(pcrFit3$finalModel, ncomp = pcrFit3$finalModel$tuneValue$.ncomp)
    y_fit_PCR3 <- predict(pcrFit3$finalModel, ncomp = pcrFit3$finalModel$tuneValue$.ncomp)
    R2_pcr3[, index] <- (cor(y_fit_PCR3, trainFlesh3)^2) * 100
    opt_comp_pcr3[, index] <- pcrFit3$finalModel$tuneValue$.ncomp

    bhModels3_pcr <- list(pcr = pcrFit3)
    #bhPred3_pcr <- predict(bhModels3_pcr, newdata = testBH3)
    allPred3_pcr <- extractPrediction(bhModels3_pcr, testX = testBH3, testY = testFlesh3)
    testPred3_pcr <- subset(allPred3_pcr, dataType == "Test")
    sorted3_pcr <- as.matrix(by(testPred3_pcr, list(model = testPred3_pcr$model), function(x) postResample(x$pred, x$obs)))
    test3_pcr[index, ] <- sorted3_pcr[, 1]$pcr

    #PLS
    plsFit3 <- train(tr3, trainFlesh3, "pls", metric = "RMSE", tuneLength = 10, trControl = con)
    coefsPLS3[, index] <- coef(plsFit3$finalModel, ncomp = plsFit3$finalModel$tuneValue$.ncomp)
    y_fit_PLS3 <- predict(plsFit3$finalModel, ncomp = plsFit3$finalModel$tuneValue$.ncomp)
    R2_pls3[, index] <- (cor(y_fit_PLS3, trainFlesh3)^2) * 100
    opt_comp_pls3[, index] <- plsFit3$finalModel$tuneValue$.ncomp

    bhModels3_pls <- list(pls = plsFit3)
    #bhPred3_pls <- predict(bhModels3_pls, newdata = testBH3)
    allPred3_pls <- extractPrediction(bhModels3_pls, testX = testBH3, testY = testFlesh3)
    testPred3_pls <- subset(allPred3_pls, dataType == "Test")
    sorted3_pls <- as.matrix(by(testPred3_pls, list(model = testPred3_pls$model), function(x) postResample(x$pred, x$obs)))
    test3_pls[index, ] <- sorted3_pls[, 1]$pls

    # EN
    enFit3 <- train(tr3, trainFlesh3, "glmnet", metric = "RMSE", tuneLength = 10, trControl = con)
    en_frac_3[, index] <- enFit3$finalModel$tuneValue$.alpha
    en_lambda_3[, index] <- enFit3$finalModel$tuneValue$.lambda
    coefsEN3[, index] <- as.matrix(coef(enFit3$finalModel, s = enFit3$finalModel$tuneValue$.lambda))
    predsEN3 <- predict(enFit3$finalModel, newx = tr3, s = enFit3$finalModel$tuneValue$.lambda, type = "response")
    y_fit_en3 <- predsEN3[, 1]
    R2_en3[, index] <- (cor(trainFlesh3, y_fit_en3)^2) * 100

    bhModels3_en <- list(glmnet = enFit3)
    #bhPred3_en<- predict(bhModels3_en, newdata = testBH3)
    allPred3_en <- extractPrediction(bhModels3_en, testX = testBH3, testY = testFlesh3)
    testPred3_en <- subset(allPred3_en, dataType == "Test")
    sorted3_en <- as.matrix(by(testPred3_en, list(model = testPred3_en$model), function(x) postResample(x$pred, x$obs)))
    test3_en[index, ] <- sorted3_en[, 1]$glmnet

    # LASSO
    lassoFit3 <- train(tr3, trainFlesh3, "glmnet", metric = "RMSE", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 1, by = 0.1), .alpha = 1), trControl = con)
    lasso_lambda_3[, index] <- lassoFit3$bestTune$.lambda
    coefsLASSO3[, index] <- as.matrix(coef(lassoFit3$finalModel, s = lassoFit3$finalModel$tuneValue$.lambda))
    predslasso3 <- predict(lassoFit3$finalModel, newx = tr3, s = lassoFit3$finalModel$tuneValue$.lambda, type = "response")
    y_fit_lasso3 <- predslasso3[, 1]
    R2_lasso3[, index] <- (cor(trainFlesh3, y_fit_lasso3)^2) * 100

    bhModels3_lasso <- list(glmnet = lassoFit3)
    #bhPred3_lasso<- predict(bhModels3_lasso, newdata = testBH3)
    allPred3_lasso <- extractPrediction(bhModels3_lasso, testX = testBH3, testY = testFlesh3)
    testPred3_lasso <- subset(allPred3_lasso, dataType == "Test")
    sorted3_lasso <- as.matrix(by(testPred3_lasso, list(model = testPred3_lasso$model), function(x) postResample(x$pred, x$obs)))
    test3_lasso[index, ] <- sorted3_lasso[, 1]$glmnet

    # SVM
    svmFit3 <- train(tr3, trainFlesh3, "svmRadial", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_svm3 <- predict(svmFit3$finalModel)
    svm_tune_Cost3[, index] <- svmFit3$bestTune$.C
    svm_tune_Sigma3[, index] <- svmFit3$bestTune$.sigma
    R2_svm3[, index] <- (cor(y_fit_svm3, trainFlesh3)^2) * 100

    bhModels3_svm <- list(svmRadial = svmFit3)
    #bhPred3_svm<- predict(bhModels3_svm, newdata = testBH3)
    allPred3_svm <- extractPrediction(bhModels3_svm, testX = testBH3, testY = testFlesh3)
    testPred3_svm <- subset(allPred3_svm, dataType == "Test")
    sorted3_svm <- as.matrix(by(testPred3_svm, list(model = testPred3_svm$model), function(x) postResample(x$pred, x$obs)))
    test3_svm[index, ] <- sorted3_svm[, 1]$svm

    # SPLS
    splsFit3 <- train(tr3, trainFlesh3, "spls", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_spls3 <- predict(splsFit3$finalModel)
    Coefspls3[, index] <- predict(splsFit3$finalModel, type = "coefficient", splsFit3$finalModel$tuneValue$.eta, splsFit3$finalModel$tuneValue$.K)
    R2_spls3[, index] <- (cor(y_fit_spls3, trainFlesh3)^2) * 100
    spls_K_3[, index] <- splsFit3$finalModel$tuneValue$.K
    spls_eta_3[, index] <- splsFit3$finalModel$tuneValue$.eta

    bhModels3_spls <- list(spls = splsFit3)
    #bhPred3_spls<- predict(bhModels3_svm, newdata = testBH3)
    allPred3_spls <- extractPrediction(bhModels3_spls, testX = testBH3, testY = testFlesh3)
    testPred3_spls <- subset(allPred3_spls, dataType == "Test")
    sorted3_spls <- as.matrix(by(testPred3_spls, list(model = testPred3_spls$model), function(x) postResample(x$pred, x$obs)))
    test3_spls[index, ] <- sorted3_spls[, 1]$spls

    # RF
    rfFit3 <- train(tr3, trainFlesh3, "rf", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_rf3 <- predict(rfFit3$finalModel)
    R2_rf3[, index] <- (cor(y_fit_rf3, trainFlesh3)^2) * 100
    rf_mtry3[, index] <- rfFit3$finalModel$tuneValue$.mtry
    rf_imp_3[, index] <- rfFit3$finalModel$importance

    bhModels3_rf <- list(rf = rfFit3)
    #bhPred3_rf<- predict(bhModels3_rf, newdata = testBH3)
    allPred3_rf <- extractPrediction(bhModels3_rf, testX = testBH3, testY = testFlesh3)
    testPred3_rf <- subset(allPred3_rf, dataType == "Test")
    sorted3_rf <- as.matrix(by(testPred3_rf, list(model = testPred3_rf$model), function(x) postResample(x$pred, x$obs)))
    test3_rf[index, ] <- sorted3_rf[, 1]$rf

    # RIDGE
    ridgeFit3 <- train(tr3, trainFlesh3, "glmnet", metric = "RMSE", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 100, by = 0.1), .alpha = 0), trControl = con)
    ridge_lambda_3[, index] <- ridgeFit3$bestTune$.lambda
    coefsRidge3[, index] <- as.matrix(coef(ridgeFit3$finalModel, s = ridgeFit3$finalModel$tuneValue$.lambda))
    predsridge3 <- predict(ridgeFit3$finalModel, newx = tr3, s = ridgeFit3$finalModel$tuneValue$.lambda, type = "response")
    y_fit_ridge3 <- predsridge3[, 1]
    R2_ridge3[, index] <- (cor(trainFlesh3, y_fit_ridge3)^2) * 100

    bhModels3_ridge <- list(glmnet = ridgeFit3)
    allPred3_ridge <- extractPrediction(bhModels3_ridge, testX = testBH3, testY = testFlesh3)
    testPred3_ridge <- subset(allPred3_ridge, dataType == "Test")
    sorted3_ridge <- as.matrix(by(testPred3_ridge, list(model = testPred3_ridge$model), function(x) postResample(x$pred, x$obs)))
    test3_ridge[index, ] <- sorted3_ridge[, 1]$glmnet

    ####-------------------------------Split 4-------------------------
    Train4 <- inTrain1$"4"
    tr4 <- DesignMatrix[Train4, ]
    ts4 <- DesignMatrix[-Train4, ]
    #preProc4 <- preProcess(tr4,method='scale')
    trainBH4 <- tr4
    testBH4 <- ts4
    trainFlesh4 <- data1$Flesh[Train4]
    testFlesh4 <- data1$Flesh[-Train4]

    #PCR
    pcrFit4 <- train(tr4, trainFlesh4, "pcr", metric = "RMSE", tuneLength = 50, trControl = con)
    coefsPCR4[, index] <- coef(pcrFit4$finalModel, ncomp = pcrFit4$finalModel$tuneValue$.ncomp)
    y_fit_PCR4 <- predict(pcrFit4$finalModel, ncomp = pcrFit4$finalModel$tuneValue$.ncomp)
    R2_pcr4[, index] <- (cor(y_fit_PCR4, trainFlesh4)^2) * 100
    opt_comp_pcr4[, index] <- pcrFit4$finalModel$tuneValue$.ncomp

    bhModels4_pcr <- list(pcr = pcrFit4)
    #bhPred4_pcr <- predict(bhModels4_pcr, newdata = testBH4)
    allPred4_pcr <- extractPrediction(bhModels4_pcr, testX = testBH4, testY = testFlesh4)
    testPred4_pcr <- subset(allPred4_pcr, dataType == "Test")
    sorted4_pcr <- as.matrix(by(testPred4_pcr, list(model = testPred4_pcr$model), function(x) postResample(x$pred, x$obs)))
    test4_pcr[index, ] <- sorted4_pcr[, 1]$pcr

    #PLS
    plsFit4 <- train(tr4, trainFlesh4, "pls", metric = "RMSE", tuneLength = 10, trControl = con)
    coefsPLS4[, index] <- coef(plsFit4$finalModel, ncomp = plsFit4$finalModel$tuneValue$.ncomp)
    y_fit_PLS4 <- predict(plsFit4$finalModel, ncomp = plsFit4$finalModel$tuneValue$.ncomp)
    R2_pls4[, index] <- (cor(y_fit_PLS4, trainFlesh4)^2) * 100
    opt_comp_pls4[, index] <- plsFit4$finalModel$tuneValue$.ncomp

    bhModels4_pls <- list(pls = plsFit4)
    #bhPred4_pls <- predict(bhModels4_pls, newdata = testBH4)
    allPred4_pls <- extractPrediction(bhModels4_pls, testX = testBH4, testY = testFlesh4)
    testPred4_pls <- subset(allPred4_pls, dataType == "Test")
    sorted4_pls <- as.matrix(by(testPred4_pls, list(model = testPred4_pls$model), function(x) postResample(x$pred, x$obs)))
    test4_pls[index, ] <- sorted4_pls[, 1]$pls

    # EN
    enFit4 <- train(tr4, trainFlesh4, "glmnet", metric = "RMSE", tuneLength = 10, trControl = con)
    en_frac_4[, index] <- enFit4$finalModel$tuneValue$.alpha
    en_lambda_4[, index] <- enFit4$finalModel$tuneValue$.lambda
    coefsEN4[, index] <- as.matrix(coef(enFit4$finalModel, s = enFit4$finalModel$tuneValue$.lambda))
    predsEN4 <- predict(enFit4$finalModel, newx = tr4, s = enFit4$finalModel$tuneValue$.lambda, type = "response")
    y_fit_en4 <- predsEN4[, 1]
    R2_en4[, index] <- (cor(trainFlesh4, y_fit_en4)^2) * 100

    bhModels4_en <- list(glmnet = enFit4)
    #bhPred4_en<- predict(bhModels4_en, newdata = testBH4)
    allPred4_en <- extractPrediction(bhModels4_en, testX = testBH4, testY = testFlesh4)
    testPred4_en <- subset(allPred4_en, dataType == "Test")
    sorted4_en <- as.matrix(by(testPred4_en, list(model = testPred4_en$model), function(x) postResample(x$pred, x$obs)))
    test4_en[index, ] <- sorted4_en[, 1]$glmnet

    # LASSO
    lassoFit4 <- train(tr4, trainFlesh4, "glmnet", metric = "RMSE", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 1, by = 0.1), .alpha = 1), trControl = con)
    lasso_lambda_4[, index] <- lassoFit4$bestTune$.lambda
    coefsLASSO4[, index] <- as.matrix(coef(lassoFit4$finalModel, s = lassoFit4$finalModel$tuneValue$.lambda))
    predslasso4 <- predict(lassoFit4$finalModel, newx = tr4, s = lassoFit4$finalModel$tuneValue$.lambda, type = "response")
    y_fit_lasso4 <- predslasso4[, 1]
    R2_lasso4[, index] <- (cor(trainFlesh4, y_fit_lasso4)^2) * 100

    bhModels4_lasso <- list(glmnet = lassoFit4)
    #bhPred4_lasso<- predict(bhModels4_lasso, newdata = testBH4)
    allPred4_lasso <- extractPrediction(bhModels4_lasso, testX = testBH4, testY = testFlesh4)
    testPred4_lasso <- subset(allPred4_lasso, dataType == "Test")
    sorted4_lasso <- as.matrix(by(testPred4_lasso, list(model = testPred4_lasso$model), function(x) postResample(x$pred, x$obs)))
    test4_lasso[index, ] <- sorted4_lasso[, 1]$glmnet

    # SVM
    svmFit4 <- train(tr4, trainFlesh4, "svmRadial", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_svm4 <- predict(svmFit4$finalModel)
    svm_tune_Cost4[, index] <- svmFit4$bestTune$.C
    svm_tune_Sigma4[, index] <- svmFit4$bestTune$.sigma
    R2_svm4[, index] <- (cor(y_fit_svm4, trainFlesh4)^2) * 100

    bhModels4_svm <- list(svmRadial = svmFit4)
    #bhPred4_svm<- predict(bhModels4_svm, newdata = testBH4)
    allPred4_svm <- extractPrediction(bhModels4_svm, testX = testBH4, testY = testFlesh4)
    testPred4_svm <- subset(allPred4_svm, dataType == "Test")
    sorted4_svm <- as.matrix(by(testPred4_svm, list(model = testPred4_svm$model), function(x) postResample(x$pred, x$obs)))
    test4_svm[index, ] <- sorted4_svm[, 1]$svm

    # SPLS
    splsFit4 <- train(tr4, trainFlesh4, "spls", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_spls4 <- predict(splsFit4$finalModel)
    Coefspls4[, index] <- predict(splsFit4$finalModel, type = "coefficient", splsFit4$finalModel$tuneValue$.eta, splsFit4$finalModel$tuneValue$.K)
    R2_spls4[, index] <- (cor(y_fit_spls4, trainFlesh4)^2) * 100
    spls_K_4[, index] <- splsFit4$finalModel$tuneValue$.K
    spls_eta_4[, index] <- splsFit4$finalModel$tuneValue$.eta

    bhModels4_spls <- list(spls = splsFit4)
    #bhPred4_spls<- predict(bhModels4_svm, newdata = testBH4)
    allPred4_spls <- extractPrediction(bhModels4_spls, testX = testBH4, testY = testFlesh4)
    testPred4_spls <- subset(allPred4_spls, dataType == "Test")
    sorted4_spls <- as.matrix(by(testPred4_spls, list(model = testPred4_spls$model), function(x) postResample(x$pred, x$obs)))
    test4_spls[index, ] <- sorted4_spls[, 1]$spls

    # RF
    rfFit4 <- train(tr4, trainFlesh4, "rf", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_rf4 <- predict(rfFit4$finalModel)
    R2_rf4[, index] <- (cor(y_fit_rf4, trainFlesh4)^2) * 100
    rf_mtry4[, index] <- rfFit4$finalModel$tuneValue$.mtry
    rf_imp_4[, index] <- rfFit4$finalModel$importance

    bhModels4_rf <- list(rf = rfFit4)
    #bhPred4_rf<- predict(bhModels4_rf, newdata = testBH4)
    allPred4_rf <- extractPrediction(bhModels4_rf, testX = testBH4, testY = testFlesh4)
    testPred4_rf <- subset(allPred4_rf, dataType == "Test")
    sorted4_rf <- as.matrix(by(testPred4_rf, list(model = testPred4_rf$model), function(x) postResample(x$pred, x$obs)))
    test4_rf[index, ] <- sorted4_rf[, 1]$rf

    # RIDGE
    ridgeFit4 <- train(tr4, trainFlesh4, "glmnet", metric = "RMSE", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 100, by = 0.1), .alpha = 0), trControl = con)
    ridge_lambda_4[, index] <- ridgeFit4$bestTune$.lambda
    coefsRidge4[, index] <- as.matrix(coef(ridgeFit4$finalModel, s = ridgeFit4$finalModel$tuneValue$.lambda))
    predsridge4 <- predict(ridgeFit4$finalModel, newx = tr4, s = ridgeFit4$finalModel$tuneValue$.lambda, type = "response")
    y_fit_ridge4 <- predsridge4[, 1]
    R2_ridge4[, index] <- (cor(trainFlesh4, y_fit_ridge4)^2) * 100

    bhModels4_ridge <- list(glmnet = ridgeFit4)
    allPred4_ridge <- extractPrediction(bhModels4_ridge, testX = testBH4, testY = testFlesh4)
    testPred4_ridge <- subset(allPred4_ridge, dataType == "Test")
    sorted4_ridge <- as.matrix(by(testPred4_ridge, list(model = testPred4_ridge$model), function(x) postResample(x$pred, x$obs)))
    test4_ridge[index, ] <- sorted4_ridge[, 1]$glmnet

    ####-------------------------------Split 5-------------------------
    Train5 <- inTrain1$"5"
    tr5 <- DesignMatrix[Train5, ]
    ts5 <- DesignMatrix[-Train5, ]
    #preProc5 <- preProcess(tr5,method='scale')
    trainBH5 <- tr5
    testBH5 <- ts5
    trainFlesh5 <- data1$Flesh[Train5]
    testFlesh5 <- data1$Flesh[-Train5]


    # PCR
    pcrFit5 <- train(tr5, trainFlesh5, "pcr", metric = "RMSE", tuneLength = 50, trControl = con)
    coefsPCR5[, index] <- coef(pcrFit5$finalModel, ncomp = pcrFit5$finalModel$tuneValue$.ncomp)
    y_fit_PCR5 <- predict(pcrFit5$finalModel, ncomp = pcrFit5$finalModel$tuneValue$.ncomp)
    R2_pcr5[, index] <- (cor(y_fit_PCR5, trainFlesh5)^2) * 100
    opt_comp_pcr5[, index] <- pcrFit5$finalModel$tuneValue$.ncomp

    bhModels5_pcr <- list(pcr = pcrFit5)
    #bhPred5_pcr <- predict(bhModels5_pcr, newdata = testBH5)
    allPred5_pcr <- extractPrediction(bhModels5_pcr, testX = testBH5, testY = testFlesh5)
    testPred5_pcr <- subset(allPred5_pcr, dataType == "Test")
    sorted5_pcr <- as.matrix(by(testPred5_pcr, list(model = testPred5_pcr$model), function(x) postResample(x$pred, x$obs)))
    test5_pcr[index, ] <- sorted5_pcr[, 1]$pcr

    #PLS
    plsFit5 <- train(tr5, trainFlesh5, "pls", metric = "RMSE", tuneLength = 10, trControl = con)
    coefsPLS5[, index] <- coef(plsFit5$finalModel, ncomp = plsFit5$finalModel$tuneValue$.ncomp)
    y_fit_PLS5 <- predict(plsFit5$finalModel, ncomp = plsFit5$finalModel$tuneValue$.ncomp)
    R2_pls5[, index] <- (cor(y_fit_PLS5, trainFlesh5)^2) * 100
    opt_comp_pls5[, index] <- plsFit5$finalModel$tuneValue$.ncomp

    bhModels5_pls <- list(pls = plsFit5)
    #bhPred5_pls <- predict(bhModels5_pls, newdata = testBH5)
    allPred5_pls <- extractPrediction(bhModels5_pls, testX = testBH5, testY = testFlesh5)
    testPred5_pls <- subset(allPred5_pls, dataType == "Test")
    sorted5_pls <- as.matrix(by(testPred5_pls, list(model = testPred5_pls$model), function(x) postResample(x$pred, x$obs)))
    test5_pls[index, ] <- sorted5_pls[, 1]$pls


    # EN
    enFit5 <- train(tr5, trainFlesh5, "glmnet", metric = "RMSE", tuneLength = 10, trControl = con)
    en_frac_5[, index] <- enFit5$finalModel$tuneValue$.alpha
    en_lambda_5[, index] <- enFit5$finalModel$tuneValue$.lambda
    coefsEN5[, index] <- as.matrix(coef(enFit5$finalModel, s = enFit5$finalModel$tuneValue$.lambda))
    predsEN5 <- predict(enFit5$finalModel, newx = tr5, s = enFit5$finalModel$tuneValue$.lambda, type = "response")
    y_fit_en5 <- predsEN5[, 1]
    R2_en5[, index] <- (cor(trainFlesh5, y_fit_en5)^2) * 100

    bhModels5_en <- list(glmnet = enFit5)
    #bhPred5_en<- predict(bhModels5_en, newdata = testBH5)
    allPred5_en <- extractPrediction(bhModels5_en, testX = testBH5, testY = testFlesh5)
    testPred5_en <- subset(allPred5_en, dataType == "Test")
    sorted5_en <- as.matrix(by(testPred5_en, list(model = testPred5_en$model), function(x) postResample(x$pred, x$obs)))
    test5_en[index, ] <- sorted5_en[, 1]$glmnet

    # LASSO
    lassoFit5 <- train(tr5, trainFlesh5, "glmnet", metric = "RMSE", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 1, by = 0.1), .alpha = 1), trControl = con)
    lasso_lambda_5[, index] <- lassoFit5$bestTune$.lambda
    coefsLASSO5[, index] <- as.matrix(coef(lassoFit5$finalModel, s = lassoFit5$finalModel$tuneValue$.lambda))
    predslasso5 <- predict(lassoFit5$finalModel, newx = tr5, s = lassoFit5$finalModel$tuneValue$.lambda, type = "response")
    y_fit_lasso5 <- predslasso5[, 1]
    R2_lasso5[, index] <- (cor(trainFlesh5, y_fit_lasso5)^2) * 100

    bhModels5_lasso <- list(glmnet = lassoFit5)
    #bhPred5_lasso<- predict(bhModels5_lasso, newdata = testBH5)
    allPred5_lasso <- extractPrediction(bhModels5_lasso, testX = testBH5, testY = testFlesh5)
    testPred5_lasso <- subset(allPred5_lasso, dataType == "Test")
    sorted5_lasso <- as.matrix(by(testPred5_lasso, list(model = testPred5_lasso$model), function(x) postResample(x$pred, x$obs)))
    test5_lasso[index, ] <- sorted5_lasso[, 1]$glmnet

    # SVM
    svmFit5 <- train(tr5, trainFlesh5, "svmRadial", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_svm5 <- predict(svmFit5$finalModel)
    svm_tune_Cost5[, index] <- svmFit5$bestTune$.C
    svm_tune_Sigma5[, index] <- svmFit5$bestTune$.sigma
    R2_svm5[, index] <- (cor(y_fit_svm5, trainFlesh5)^2) * 100

    bhModels5_svm <- list(svmRadial = svmFit5)
    #bhPred5_svm<- predict(bhModels5_svm, newdata = testBH5)
    allPred5_svm <- extractPrediction(bhModels5_svm, testX = testBH5, testY = testFlesh5)
    testPred5_svm <- subset(allPred5_svm, dataType == "Test")
    sorted5_svm <- as.matrix(by(testPred5_svm, list(model = testPred5_svm$model), function(x) postResample(x$pred, x$obs)))
    test5_svm[index, ] <- sorted5_svm[, 1]$svm

    # SPLS
    splsFit5 <- train(tr5, trainFlesh5, "spls", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_spls5 <- predict(splsFit5$finalModel)
    Coefspls5[, index] <- predict(splsFit5$finalModel, type = "coefficient", splsFit5$finalModel$tuneValue$.eta, splsFit5$finalModel$tuneValue$.K)
    R2_spls5[, index] <- (cor(y_fit_spls5, trainFlesh5)^2) * 100
    spls_K_5[, index] <- splsFit5$finalModel$tuneValue$.K
    spls_eta_5[, index] <- splsFit5$finalModel$tuneValue$.eta

    bhModels5_spls <- list(spls = splsFit5)
    #bhPred5_spls<- predict(bhModels5_svm, newdata = testBH5)
    allPred5_spls <- extractPrediction(bhModels5_spls, testX = testBH5, testY = testFlesh5)
    testPred5_spls <- subset(allPred5_spls, dataType == "Test")
    sorted5_spls <- as.matrix(by(testPred5_spls, list(model = testPred5_spls$model), function(x) postResample(x$pred, x$obs)))
    test5_spls[index, ] <- sorted5_spls[, 1]$spls

    #RF
    rfFit5 <- train(tr5, trainFlesh5, "rf", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_rf5 <- predict(rfFit5$finalModel)
    R2_rf5[, index] <- (cor(y_fit_rf5, trainFlesh5)^2) * 100
    rf_mtry5[, index] <- rfFit5$finalModel$tuneValue$.mtry
    rf_imp_5[, index] <- rfFit5$finalModel$importance

    bhModels5_rf <- list(rf = rfFit5)
    #bhPred5_rf<- predict(bhModels5_rf, newdata = testBH5)
    allPred5_rf <- extractPrediction(bhModels5_rf, testX = testBH5, testY = testFlesh5)
    testPred5_rf <- subset(allPred5_rf, dataType == "Test")
    sorted5_rf <- as.matrix(by(testPred5_rf, list(model = testPred5_rf$model), function(x) postResample(x$pred, x$obs)))
    test5_rf[index, ] <- sorted5_rf[, 1]$rf

    #RIDGE
    ridgeFit5 <- train(tr5, trainFlesh5, "glmnet", metric = "RMSE", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 100, by = 0.1), .alpha = 0), trControl = con)
    ridge_lambda_5[, index] <- ridgeFit5$bestTune$.lambda
    coefsRidge5[, index] <- as.matrix(coef(ridgeFit5$finalModel, s = ridgeFit5$finalModel$tuneValue$.lambda))
    predsridge5 <- predict(ridgeFit5$finalModel, newx = tr5, s = ridgeFit5$finalModel$tuneValue$.lambda, type = "response")
    y_fit_ridge5 <- predsridge5[, 1]
    R2_ridge5[, index] <- (cor(trainFlesh5, y_fit_ridge5)^2) * 100

    bhModels5_ridge <- list(glmnet = ridgeFit5)
    allPred5_ridge <- extractPrediction(bhModels5_ridge, testX = testBH5, testY = testFlesh5)
    testPred5_ridge <- subset(allPred5_ridge, dataType == "Test")
    sorted5_ridge <- as.matrix(by(testPred5_ridge, list(model = testPred5_ridge$model), function(x) postResample(x$pred, x$obs)))
    test5_ridge[index, ] <- sorted5_ridge[, 1]$glmnet


    ####-------------------------------Split 6-------------------------
    Train6 <- inTrain1$"6"
    tr6 <- DesignMatrix[Train6, ]
    ts6 <- DesignMatrix[-Train6, ]
    #preProc6 <- preProcess(tr6,method='scale')
    trainBH6 <- tr6
    testBH6 <- ts6
    trainFlesh6 <- data1$Flesh[Train6]
    testFlesh6 <- data1$Flesh[-Train6]

    # PCR
    pcrFit6 <- train(tr6, trainFlesh6, "pcr", metric = "RMSE", tuneLength = 50, trControl = con)
    coefsPCR6[, index] <- coef(pcrFit6$finalModel, ncomp = pcrFit6$finalModel$tuneValue$.ncomp)
    y_fit_PCR6 <- predict(pcrFit6$finalModel, ncomp = pcrFit6$finalModel$tuneValue$.ncomp)
    R2_pcr6[, index] <- (cor(y_fit_PCR6, trainFlesh6)^2) * 100
    opt_comp_pcr6[, index] <- pcrFit6$finalModel$tuneValue$.ncomp

    bhModels6_pcr <- list(pcr = pcrFit6)
    #bhPred6_pcr <- predict(bhModels6_pcr, newdata = testBH6)
    allPred6_pcr <- extractPrediction(bhModels6_pcr, testX = testBH6, testY = testFlesh6)
    testPred6_pcr <- subset(allPred6_pcr, dataType == "Test")
    sorted6_pcr <- as.matrix(by(testPred6_pcr, list(model = testPred6_pcr$model), function(x) postResample(x$pred, x$obs)))
    test6_pcr[index, ] <- sorted6_pcr[, 1]$pcr

    #PLS
    plsFit6 <- train(tr6, trainFlesh6, "pls", metric = "RMSE", tuneLength = 10, trControl = con)
    coefsPLS6[, index] <- coef(plsFit6$finalModel, ncomp = plsFit6$finalModel$tuneValue$.ncomp)
    y_fit_PLS6 <- predict(plsFit6$finalModel, ncomp = plsFit6$finalModel$tuneValue$.ncomp)
    R2_pls6[, index] <- (cor(y_fit_PLS6, trainFlesh6)^2) * 100
    opt_comp_pls6[, index] <- plsFit6$finalModel$tuneValue$.ncomp

    bhModels6_pls <- list(pls = plsFit6)
    #bhPred6_pls <- predict(bhModels6_pls, newdata = testBH6)
    allPred6_pls <- extractPrediction(bhModels6_pls, testX = testBH6, testY = testFlesh6)
    testPred6_pls <- subset(allPred6_pls, dataType == "Test")
    sorted6_pls <- as.matrix(by(testPred6_pls, list(model = testPred6_pls$model), function(x) postResample(x$pred, x$obs)))
    test6_pls[index, ] <- sorted6_pls[, 1]$pls

    # EN
    enFit6 <- train(tr6, trainFlesh6, "glmnet", metric = "RMSE", tuneLength = 10, trControl = con)
    en_frac_6[, index] <- enFit6$finalModel$tuneValue$.alpha
    en_lambda_6[index] <- enFit6$finalModel$tuneValue$.lambda
    coefsEN6[, index] <- as.matrix(coef(enFit6$finalModel, s = enFit6$finalModel$tuneValue$.lambda))
    predsEN6 <- predict(enFit6$finalModel, newx = tr6, s = enFit6$finalModel$tuneValue$.lambda, type = "response")
    y_fit_en6 <- predsEN6[, 1]
    R2_en6[, index] <- (cor(trainFlesh6, y_fit_en6)^2) * 100

    bhModels6_en <- list(glmnet = enFit6)
    #bhPred6_en<- predict(bhModels6_en, newdata = testBH6)
    allPred6_en <- extractPrediction(bhModels6_en, testX = testBH6, testY = testFlesh6)
    testPred6_en <- subset(allPred6_en, dataType == "Test")
    sorted6_en <- as.matrix(by(testPred6_en, list(model = testPred6_en$model), function(x) postResample(x$pred, x$obs)))
    test6_en[index, ] <- sorted6_en[, 1]$glmnet

    # LASSO
    lassoFit6 <- train(tr6, trainFlesh6, "glmnet", metric = "RMSE", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 1, by = 0.1), .alpha = 1), trControl = con)
    lasso_lambda_6[, index] <- lassoFit6$bestTune$.lambda
    coefsLASSO6[, index] <- as.matrix(coef(lassoFit6$finalModel, s = lassoFit6$finalModel$tuneValue$.lambda))
    predslasso6 <- predict(lassoFit6$finalModel, newx = tr6, s = lassoFit6$finalModel$tuneValue$.lambda, type = "response")
    y_fit_lasso6 <- predslasso6[, 1]
    R2_lasso6[, index] <- (cor(trainFlesh6, y_fit_lasso6)^2) * 100

    bhModels6_lasso <- list(glmnet = lassoFit6)
    #bhPred6_lasso<- predict(bhModels6_lasso, newdata = testBH6)
    allPred6_lasso <- extractPrediction(bhModels6_lasso, testX = testBH6, testY = testFlesh6)
    testPred6_lasso <- subset(allPred6_lasso, dataType == "Test")
    sorted6_lasso <- as.matrix(by(testPred6_lasso, list(model = testPred6_lasso$model), function(x) postResample(x$pred, x$obs)))
    test6_lasso[index, ] <- sorted6_lasso[, 1]$glmnet

    # SVM
    svmFit6 <- train(tr6, trainFlesh6, "svmRadial", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_svm6 <- predict(svmFit6$finalModel)
    svm_tune_Cost6[, index] <- svmFit6$bestTune$.C
    svm_tune_Sigma6[, index] <- svmFit6$bestTune$.sigma
    R2_svm6[, index] <- (cor(y_fit_svm6, trainFlesh6)^2) * 100

    bhModels6_svm <- list(svmRadial = svmFit6)
    #bhPred6_svm<- predict(bhModels6_svm, newdata = testBH6)
    allPred6_svm <- extractPrediction(bhModels6_svm, testX = testBH6, testY = testFlesh6)
    testPred6_svm <- subset(allPred6_svm, dataType == "Test")
    sorted6_svm <- as.matrix(by(testPred6_svm, list(model = testPred6_svm$model), function(x) postResample(x$pred, x$obs)))
    test6_svm[index, ] <- sorted6_svm[, 1]$svm

    # SPLS
    splsFit6 <- train(tr6, trainFlesh6, "spls", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_spls6 <- predict(splsFit6$finalModel)
    Coefspls6[, index] <- predict(splsFit6$finalModel, type = "coefficient", splsFit6$finalModel$tuneValue$.eta, splsFit6$finalModel$tuneValue$.K)
    R2_spls6[, index] <- (cor(y_fit_spls6, trainFlesh6)^2) * 100
    spls_K_6[, index] <- splsFit6$finalModel$tuneValue$.K
    spls_eta_6[, index] <- splsFit6$finalModel$tuneValue$.eta

    bhModels6_spls <- list(spls = splsFit6)
    #bhPred6_spls<- predict(bhModels6_svm, newdata = testBH6)
    allPred6_spls <- extractPrediction(bhModels6_spls, testX = testBH6, testY = testFlesh6)
    testPred6_spls <- subset(allPred6_spls, dataType == "Test")
    sorted6_spls <- as.matrix(by(testPred6_spls, list(model = testPred6_spls$model), function(x) postResample(x$pred, x$obs)))
    test6_spls[index, ] <- sorted6_spls[, 1]$spls

    # RF
    rfFit6 <- train(tr6, trainFlesh6, "rf", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_rf6 <- predict(rfFit6$finalModel)
    R2_rf6[, index] <- (cor(y_fit_rf6, trainFlesh6)^2) * 100
    rf_mtry6[, index] <- rfFit6$finalModel$tuneValue$.mtry
    rf_imp_6[, index] <- rfFit6$finalModel$importance

    bhModels6_rf <- list(rf = rfFit6)
    #bhPred6_rf<- predict(bhModels6_rf, newdata = testBH6)
    allPred6_rf <- extractPrediction(bhModels6_rf, testX = testBH6, testY = testFlesh6)
    testPred6_rf <- subset(allPred6_rf, dataType == "Test")
    sorted6_rf <- as.matrix(by(testPred6_rf, list(model = testPred6_rf$model), function(x) postResample(x$pred, x$obs)))
    test6_rf[index, ] <- sorted6_rf[, 1]$rf

    # RIDGE
    ridgeFit6 <- train(tr6, trainFlesh6, "glmnet", metric = "RMSE", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 100, by = 0.1), .alpha = 0), trControl = con)
    ridge_lambda_6[, index] <- ridgeFit6$bestTune$.lambda
    coefsRidge6[, index] <- as.matrix(coef(ridgeFit6$finalModel, s = ridgeFit6$finalModel$tuneValue$.lambda))
    predsridge6 <- predict(ridgeFit6$finalModel, newx = tr6, s = ridgeFit6$finalModel$tuneValue$.lambda, type = "response")
    y_fit_ridge6 <- predsridge6[, 1]
    R2_ridge6[, index] <- (cor(trainFlesh6, y_fit_ridge6)^2) * 100

    bhModels6_ridge <- list(glmnet = ridgeFit6)
    allPred6_ridge <- extractPrediction(bhModels6_ridge, testX = testBH6, testY = testFlesh6)
    testPred6_ridge <- subset(allPred6_ridge, dataType == "Test")
    sorted6_ridge <- as.matrix(by(testPred6_ridge, list(model = testPred6_ridge$model), function(x) postResample(x$pred, x$obs)))
    test6_ridge[index, ] <- sorted6_ridge[, 1]$glmnet

    ####-------------------------------Split 7-------------------------
    Train7 <- inTrain1$"7"
    tr7 <- DesignMatrix[Train7, ]
    ts7 <- DesignMatrix[-Train7, ]
    #preProc7 <- preProcess(tr7,method='scale')
    trainBH7 <- tr7
    testBH7 <- ts7
    trainFlesh7 <- data1$Flesh[Train7]
    testFlesh7 <- data1$Flesh[-Train7]

    # PCR
    pcrFit7 <- train(tr7, trainFlesh7, "pcr", metric = "RMSE", tuneLength = 50, trControl = con)
    coefsPCR7[, index] <- coef(pcrFit7$finalModel, ncomp = pcrFit7$finalModel$tuneValue$.ncomp)
    y_fit_PCR7 <- predict(pcrFit7$finalModel, ncomp = pcrFit7$finalModel$tuneValue$.ncomp)
    R2_pcr7[, index] <- (cor(y_fit_PCR7, trainFlesh7)^2) * 100
    opt_comp_pcr7[, index] <- pcrFit7$finalModel$tuneValue$.ncomp

    bhModels7_pcr <- list(pcr = pcrFit7)
    #bhPred7_pcr <- predict(bhModels7_pcr, newdata = testBH7)
    allPred7_pcr <- extractPrediction(bhModels7_pcr, testX = testBH7, testY = testFlesh7)
    testPred7_pcr <- subset(allPred7_pcr, dataType == "Test")
    sorted7_pcr <- as.matrix(by(testPred7_pcr, list(model = testPred7_pcr$model), function(x) postResample(x$pred, x$obs)))
    test7_pcr[index, ] <- sorted7_pcr[, 1]$pcr

    #PLS
    plsFit7 <- train(tr7, trainFlesh7, "pls", metric = "RMSE", tuneLength = 10, trControl = con)
    coefsPLS7[, index] <- coef(plsFit7$finalModel, ncomp = plsFit7$finalModel$tuneValue$.ncomp)
    y_fit_PLS7 <- predict(plsFit7$finalModel, ncomp = plsFit7$finalModel$tuneValue$.ncomp)
    R2_pls7[, index] <- (cor(y_fit_PLS7, trainFlesh7)^2) * 100
    opt_comp_pls7[, index] <- plsFit7$finalModel$tuneValue$.ncomp

    bhModels7_pls <- list(pls = plsFit7)
    #bhPred7_pls <- predict(bhModels7_pls, newdata = testBH7)
    allPred7_pls <- extractPrediction(bhModels7_pls, testX = testBH7, testY = testFlesh7)
    testPred7_pls <- subset(allPred7_pls, dataType == "Test")
    sorted7_pls <- as.matrix(by(testPred7_pls, list(model = testPred7_pls$model), function(x) postResample(x$pred, x$obs)))
    test7_pls[index, ] <- sorted7_pls[, 1]$pls

    # EN
    enFit7 <- train(tr7, trainFlesh7, "glmnet", metric = "RMSE", tuneLength = 10, trControl = con)
    en_frac_7[, index] <- enFit7$finalModel$tuneValue$.alpha
    en_lambda_7[, index] <- enFit7$finalModel$tuneValue$.lambda
    coefsEN7[, index] <- as.matrix(coef(enFit7$finalModel, s = enFit7$finalModel$tuneValue$.lambda))
    predsEN7 <- predict(enFit7$finalModel, newx = tr7, s = enFit7$finalModel$tuneValue$.lambda, type = "response")
    y_fit_en7 <- predsEN7[, 1]
    R2_en7[, index] <- (cor(trainFlesh7, y_fit_en7)^2) * 100

    bhModels7_en <- list(glmnet = enFit7)
    #bhPred7_en<- predict(bhModels7_en, newdata = testBH7)
    allPred7_en <- extractPrediction(bhModels7_en, testX = testBH7, testY = testFlesh7)
    testPred7_en <- subset(allPred7_en, dataType == "Test")
    sorted7_en <- as.matrix(by(testPred7_en, list(model = testPred7_en$model), function(x) postResample(x$pred, x$obs)))
    test7_en[index, ] <- sorted7_en[, 1]$glmnet

    # LASSO
    lassoFit7 <- train(tr7, trainFlesh7, "glmnet", metric = "RMSE", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 1, by = 0.1), .alpha = 1), trControl = con)
    lasso_lambda_7[, index] <- lassoFit7$bestTune$.lambda
    coefsLASSO7[, index] <- as.matrix(coef(lassoFit7$finalModel, s = lassoFit7$finalModel$tuneValue$.lambda))
    predslasso7 <- predict(lassoFit7$finalModel, newx = tr7, s = lassoFit7$finalModel$tuneValue$.lambda, type = "response")
    y_fit_lasso7 <- predslasso7[, 1]
    R2_lasso7[, index] <- (cor(trainFlesh7, y_fit_lasso7)^2) * 100

    bhModels7_lasso <- list(glmnet = lassoFit7)
    #bhPred7_lasso<- predict(bhModels7_lasso, newdata = testBH7)
    allPred7_lasso <- extractPrediction(bhModels7_lasso, testX = testBH7, testY = testFlesh7)
    testPred7_lasso <- subset(allPred7_lasso, dataType == "Test")
    sorted7_lasso <- as.matrix(by(testPred7_lasso, list(model = testPred7_lasso$model), function(x) postResample(x$pred, x$obs)))
    test7_lasso[index, ] <- sorted7_lasso[, 1]$glmnet

    # SVM
    svmFit7 <- train(tr7, trainFlesh7, "svmRadial", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_svm7 <- predict(svmFit7$finalModel)
    svm_tune_Cost7[, index] <- svmFit7$bestTune$.C
    svm_tune_Sigma7[, index] <- svmFit7$bestTune$.sigma
    R2_svm7[, index] <- (cor(y_fit_svm7, trainFlesh7)^2) * 100

    bhModels7_svm <- list(svmRadial = svmFit7)
    #bhPred7_svm<- predict(bhModels7_svm, newdata = testBH7)
    allPred7_svm <- extractPrediction(bhModels7_svm, testX = testBH7, testY = testFlesh7)
    testPred7_svm <- subset(allPred7_svm, dataType == "Test")
    sorted7_svm <- as.matrix(by(testPred7_svm, list(model = testPred7_svm$model), function(x) postResample(x$pred, x$obs)))
    test7_svm[index, ] <- sorted7_svm[, 1]$svm

    # SPLS
    splsFit7 <- train(tr7, trainFlesh7, "spls", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_spls7 <- predict(splsFit7$finalModel)
    Coefspls7[, index] <- predict(splsFit7$finalModel, type = "coefficient", splsFit7$finalModel$tuneValue$.eta, splsFit7$finalModel$tuneValue$.K)
    R2_spls7[, index] <- (cor(y_fit_spls7, trainFlesh7)^2) * 100
    spls_K_7[, index] <- splsFit7$finalModel$tuneValue$.K
    spls_eta_7[, index] <- splsFit7$finalModel$tuneValue$.eta

    bhModels7_spls <- list(spls = splsFit7)
    #bhPred7_spls<- predict(bhModels7_svm, newdata = testBH7)
    allPred7_spls <- extractPrediction(bhModels7_spls, testX = testBH7, testY = testFlesh7)
    testPred7_spls <- subset(allPred7_spls, dataType == "Test")
    sorted7_spls <- as.matrix(by(testPred7_spls, list(model = testPred7_spls$model), function(x) postResample(x$pred, x$obs)))
    test7_spls[index, ] <- sorted7_spls[, 1]$spls

    # RF
    #set.seed(5)
    rfFit7 <- train(tr7, trainFlesh7, "rf", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_rf7 <- predict(rfFit7$finalModel)
    R2_rf7[, index] <- (cor(y_fit_rf7, trainFlesh7)^2) * 100
    rf_mtry7[, index] <- rfFit7$finalModel$tuneValue$.mtry
    rf_imp_7[, index] <- rfFit7$finalModel$importance

    bhModels7_rf <- list(rf = rfFit7)
    #bhPred7_rf<- predict(bhModels7_rf, newdata = testBH7)
    allPred7_rf <- extractPrediction(bhModels7_rf, testX = testBH7, testY = testFlesh7)
    testPred7_rf <- subset(allPred7_rf, dataType == "Test")
    sorted7_rf <- as.matrix(by(testPred7_rf, list(model = testPred7_rf$model), function(x) postResample(x$pred, x$obs)))
    test7_rf[index, ] <- sorted7_rf[, 1]$rf

    # RIDGE
    #set.seed(5)
    ridgeFit7 <- train(tr7, trainFlesh7, "glmnet", metric = "RMSE", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 100, by = 0.1), .alpha = 0), trControl = con)
    ridge_lambda_7[, index] <- ridgeFit7$bestTune$.lambda
    coefsRidge7[, index] <- as.matrix(coef(ridgeFit7$finalModel, s = ridgeFit7$finalModel$tuneValue$.lambda))
    predsridge7 <- predict(ridgeFit7$finalModel, newx = tr7, s = ridgeFit7$finalModel$tuneValue$.lambda, type = "response")
    y_fit_ridge7 <- predsridge7[, 1]
    R2_ridge7[, index] <- (cor(trainFlesh7, y_fit_ridge7)^2) * 100

    bhModels7_ridge <- list(glmnet = ridgeFit7)
    allPred7_ridge <- extractPrediction(bhModels7_ridge, testX = testBH7, testY = testFlesh7)
    testPred7_ridge <- subset(allPred7_ridge, dataType == "Test")
    sorted7_ridge <- as.matrix(by(testPred7_ridge, list(model = testPred7_ridge$model), function(x) postResample(x$pred, x$obs)))
    test7_ridge[index, ] <- sorted7_ridge[, 1]$glmnet

    ####-------------------------------Split 8-------------------------
    Train8 <- inTrain1$"8"
    tr8 <- DesignMatrix[Train8, ]
    ts8 <- DesignMatrix[-Train8, ]
    #preProc8 <- preProcess(tr8,method='scale')
    trainBH8 <- tr8
    testBH8 <- ts8
    trainFlesh8 <- data1$Flesh[Train8]
    testFlesh8 <- data1$Flesh[-Train8]

    # PCR
    #set.seed(5)
    pcrFit8 <- train(tr8, trainFlesh8, "pcr", metric = "RMSE", tuneLength = 50, trControl = con)
    coefsPCR8[, index] <- coef(pcrFit8$finalModel, ncomp = pcrFit8$finalModel$tuneValue$.ncomp)
    y_fit_PCR8 <- predict(pcrFit8$finalModel, ncomp = pcrFit8$finalModel$tuneValue$.ncomp)
    R2_pcr8[, index] <- (cor(y_fit_PCR8, trainFlesh8)^2) * 100
    opt_comp_pcr8[, index] <- pcrFit8$finalModel$tuneValue$.ncomp

    bhModels8_pcr <- list(pcr = pcrFit8)
    #bhPred8_pcr <- predict(bhModels8_pcr, newdata = testBH8)
    allPred8_pcr <- extractPrediction(bhModels8_pcr, testX = testBH8, testY = testFlesh8)
    testPred8_pcr <- subset(allPred8_pcr, dataType == "Test")
    sorted8_pcr <- as.matrix(by(testPred8_pcr, list(model = testPred8_pcr$model), function(x) postResample(x$pred, x$obs)))
    test8_pcr[index, ] <- sorted8_pcr[, 1]$pcr

    #PLS
    #set.seed(5)
    plsFit8 <- train(tr8, trainFlesh8, "pls", metric = "RMSE", tuneLength = 10, trControl = con)
    coefsPLS8[, index] <- coef(plsFit8$finalModel, ncomp = plsFit8$finalModel$tuneValue$.ncomp)
    y_fit_PLS8 <- predict(plsFit8$finalModel, ncomp = plsFit8$finalModel$tuneValue$.ncomp)
    R2_pls8[, index] <- (cor(y_fit_PLS8, trainFlesh8)^2) * 100
    opt_comp_pls8[, index] <- plsFit8$finalModel$tuneValue$.ncomp

    bhModels8_pls <- list(pls = plsFit8)
    #bhPred8_pls <- predict(bhModels8_pls, newdata = testBH8)
    allPred8_pls <- extractPrediction(bhModels8_pls, testX = testBH8, testY = testFlesh8)
    testPred8_pls <- subset(allPred8_pls, dataType == "Test")
    sorted8_pls <- as.matrix(by(testPred8_pls, list(model = testPred8_pls$model), function(x) postResample(x$pred, x$obs)))
    test8_pls[index, ] <- sorted8_pls[, 1]$pls

    # EN
    #set.seed(5)
    enFit8 <- train(tr8, trainFlesh8, "glmnet", metric = "RMSE", tuneLength = 10, trControl = con)
    en_frac_8[, index] <- enFit8$finalModel$tuneValue$.alpha
    en_lambda_8[, index] <- enFit8$finalModel$tuneValue$.lambda
    coefsEN8[, index] <- as.matrix(coef(enFit8$finalModel, s = enFit8$finalModel$tuneValue$.lambda))
    predsEN8 <- predict(enFit8$finalModel, newx = tr8, s = enFit8$finalModel$tuneValue$.lambda, type = "response")
    y_fit_en8 <- predsEN8[, 1]
    R2_en8[, index] <- (cor(trainFlesh8, y_fit_en8)^2) * 100

    bhModels8_en <- list(glmnet = enFit8)
    #bhPred8_en<- predict(bhModels8_en, newdata = testBH8)
    allPred8_en <- extractPrediction(bhModels8_en, testX = testBH8, testY = testFlesh8)
    testPred8_en <- subset(allPred8_en, dataType == "Test")
    sorted8_en <- as.matrix(by(testPred8_en, list(model = testPred8_en$model), function(x) postResample(x$pred, x$obs)))
    test8_en[index, ] <- sorted8_en[, 1]$glmnet

    # LASSO
    lassoFit8 <- train(tr8, trainFlesh8, "glmnet", metric = "RMSE", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 1, by = 0.1), .alpha = 1), trControl = con)
    lasso_lambda_8[, index] <- lassoFit8$bestTune$.lambda
    coefsLASSO8[, index] <- as.matrix(coef(lassoFit8$finalModel, s = lassoFit8$finalModel$tuneValue$.lambda))
    predslasso8 <- predict(lassoFit8$finalModel, newx = tr8, s = lassoFit8$finalModel$tuneValue$.lambda, type = "response")
    y_fit_lasso8 <- predslasso8[, 1]
    R2_lasso8[, index] <- (cor(trainFlesh8, y_fit_lasso8)^2) * 100

    bhModels8_lasso <- list(glmnet = lassoFit8)
    #bhPred8_lasso<- predict(bhModels8_lasso, newdata = testBH8)
    allPred8_lasso <- extractPrediction(bhModels8_lasso, testX = testBH8, testY = testFlesh8)
    testPred8_lasso <- subset(allPred8_lasso, dataType == "Test")
    sorted8_lasso <- as.matrix(by(testPred8_lasso, list(model = testPred8_lasso$model), function(x) postResample(x$pred, x$obs)))
    test8_lasso[index, ] <- sorted8_lasso[, 1]$glmnet

    # SVM
    #set.seed(5)

    svmFit8 <- train(tr8, trainFlesh8, "svmRadial", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_svm8 <- predict(svmFit8$finalModel)
    svm_tune_Cost8[, index] <- svmFit8$bestTune$.C
    svm_tune_Sigma8[, index] <- svmFit8$bestTune$.sigma
    R2_svm8[, index] <- (cor(y_fit_svm8, trainFlesh8)^2) * 100

    bhModels8_svm <- list(svmRadial = svmFit8)
    #bhPred8_svm<- predict(bhModels8_svm, newdata = testBH8)
    allPred8_svm <- extractPrediction(bhModels8_svm, testX = testBH8, testY = testFlesh8)
    testPred8_svm <- subset(allPred8_svm, dataType == "Test")
    sorted8_svm <- as.matrix(by(testPred8_svm, list(model = testPred8_svm$model), function(x) postResample(x$pred, x$obs)))
    test8_svm[index, ] <- sorted8_svm[, 1]$svm

    # SPLS
    #set.seed(5)
    splsFit8 <- train(tr8, trainFlesh8, "spls", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_spls8 <- predict(splsFit8$finalModel)
    Coefspls8[, index] <- predict(splsFit8$finalModel, type = "coefficient", splsFit8$finalModel$tuneValue$.eta, splsFit8$finalModel$tuneValue$.K)
    R2_spls8[, index] <- (cor(y_fit_spls8, trainFlesh8)^2) * 100
    spls_K_8[, index] <- splsFit8$finalModel$tuneValue$.K
    spls_eta_8[, index] <- splsFit8$finalModel$tuneValue$.eta

    bhModels8_spls <- list(spls = splsFit8)
    #bhPred8_spls<- predict(bhModels8_svm, newdata = testBH8)
    allPred8_spls <- extractPrediction(bhModels8_spls, testX = testBH8, testY = testFlesh8)
    testPred8_spls <- subset(allPred8_spls, dataType == "Test")
    sorted8_spls <- as.matrix(by(testPred8_spls, list(model = testPred8_spls$model), function(x) postResample(x$pred, x$obs)))
    test8_spls[index, ] <- sorted8_spls[, 1]$spls

    # RF
    #set.seed(5)
    rfFit8 <- train(tr8, trainFlesh8, "rf", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_rf8 <- predict(rfFit8$finalModel)
    R2_rf8[, index] <- (cor(y_fit_rf8, trainFlesh8)^2) * 100
    rf_mtry8[, index] <- rfFit8$finalModel$tuneValue$.mtry
    rf_imp_8[, index] <- rfFit8$finalModel$importance

    bhModels8_rf <- list(rf = rfFit8)
    #bhPred8_rf<- predict(bhModels8_rf, newdata = testBH8)
    allPred8_rf <- extractPrediction(bhModels8_rf, testX = testBH8, testY = testFlesh8)
    testPred8_rf <- subset(allPred8_rf, dataType == "Test")
    sorted8_rf <- as.matrix(by(testPred8_rf, list(model = testPred8_rf$model), function(x) postResample(x$pred, x$obs)))
    test8_rf[index, ] <- sorted8_rf[, 1]$rf

    # RIDGE
    #set.seed(5)
    ridgeFit8 <- train(tr8, trainFlesh8, "glmnet", metric = "RMSE", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 100, by = 0.1), .alpha = 0), trControl = con)
    ridge_lambda_8[, index] <- ridgeFit8$bestTune$.lambda
    coefsRidge8[, index] <- as.matrix(coef(ridgeFit8$finalModel, s = ridgeFit8$finalModel$tuneValue$.lambda))
    predsridge8 <- predict(ridgeFit8$finalModel, newx = tr8, s = ridgeFit8$finalModel$tuneValue$.lambda, type = "response")
    y_fit_ridge8 <- predsridge8[, 1]
    R2_ridge8[, index] <- (cor(trainFlesh8, y_fit_ridge8)^2) * 100

    bhModels8_ridge <- list(glmnet = ridgeFit8)
    allPred8_ridge <- extractPrediction(bhModels8_ridge, testX = testBH8, testY = testFlesh8)
    testPred8_ridge <- subset(allPred8_ridge, dataType == "Test")
    sorted8_ridge <- as.matrix(by(testPred8_ridge, list(model = testPred8_ridge$model), function(x) postResample(x$pred, x$obs)))
    test8_ridge[index, ] <- sorted8_ridge[, 1]$glmnet

    ####-------------------------------Split 9-------------------------
    Train9 <- inTrain1$"9"
    tr9 <- DesignMatrix[Train9, ]
    ts9 <- DesignMatrix[-Train9, ]
    #preProc9 <- preProcess(tr9,method='scale')
    trainBH9 <- tr9
    testBH9 <- ts9
    trainFlesh9 <- data1$Flesh[Train9]
    testFlesh9 <- data1$Flesh[-Train9]

    # PCR
    #set.seed(5)
    pcrFit9 <- train(tr9, trainFlesh9, "pcr", metric = "RMSE", tuneLength = 50, trControl = con)
    coefsPCR9[, index] <- coef(pcrFit9$finalModel, ncomp = pcrFit9$finalModel$tuneValue$.ncomp)
    y_fit_PCR9 <- predict(pcrFit9$finalModel, ncomp = pcrFit9$finalModel$tuneValue$.ncomp)
    R2_pcr9[, index] <- (cor(y_fit_PCR9, trainFlesh9)^2) * 100
    opt_comp_pcr9[, index] <- pcrFit9$finalModel$tuneValue$.ncomp

    bhModels9_pcr <- list(pcr = pcrFit9)
    #bhPred9_pcr <- predict(bhModels9_pcr, newdata = testBH9)
    allPred9_pcr <- extractPrediction(bhModels9_pcr, testX = testBH9, testY = testFlesh9)
    testPred9_pcr <- subset(allPred9_pcr, dataType == "Test")
    sorted9_pcr <- as.matrix(by(testPred9_pcr, list(model = testPred9_pcr$model), function(x) postResample(x$pred, x$obs)))
    test9_pcr[index, ] <- sorted9_pcr[, 1]$pcr

    #PLS
    #set.seed(5)
    plsFit9 <- train(tr9, trainFlesh9, "pls", metric = "RMSE", tuneLength = 10, trControl = con)
    coefsPLS9[, index] <- coef(plsFit9$finalModel, ncomp = plsFit9$finalModel$tuneValue$.ncomp)
    y_fit_PLS9 <- predict(plsFit9$finalModel, ncomp = plsFit9$finalModel$tuneValue$.ncomp)
    R2_pls9[, index] <- (cor(y_fit_PLS9, trainFlesh9)^2) * 100
    opt_comp_pls9[, index] <- plsFit9$finalModel$tuneValue$.ncomp

    bhModels9_pls <- list(pls = plsFit9)
    #bhPred9_pls <- predict(bhModels9_pls, newdata = testBH9)
    allPred9_pls <- extractPrediction(bhModels9_pls, testX = testBH9, testY = testFlesh9)
    testPred9_pls <- subset(allPred9_pls, dataType == "Test")
    sorted9_pls <- as.matrix(by(testPred9_pls, list(model = testPred9_pls$model), function(x) postResample(x$pred, x$obs)))
    test9_pls[index, ] <- sorted9_pls[, 1]$pls

    # EN
    #set.seed(5)
    enFit9 <- train(tr9, trainFlesh9, "glmnet", metric = "RMSE", tuneLength = 10, trControl = con)
    en_frac_9[, index] <- enFit9$finalModel$tuneValue$.alpha
    en_lambda_9[, index] <- enFit9$finalModel$tuneValue$.lambda
    coefsEN9[, index] <- as.matrix(coef(enFit9$finalModel, s = enFit9$finalModel$tuneValue$.lambda))
    predsEN9 <- predict(enFit9$finalModel, newx = tr9, s = enFit9$finalModel$tuneValue$.lambda, type = "response")
    y_fit_en9 <- predsEN9[, 1]
    R2_en9[, index] <- (cor(trainFlesh9, y_fit_en9)^2) * 100

    bhModels9_en <- list(glmnet = enFit9)
    #bhPred9_en<- predict(bhModels9_en, newdata = testBH9)
    allPred9_en <- extractPrediction(bhModels9_en, testX = testBH9, testY = testFlesh9)
    testPred9_en <- subset(allPred9_en, dataType == "Test")
    sorted9_en <- as.matrix(by(testPred9_en, list(model = testPred9_en$model), function(x) postResample(x$pred, x$obs)))
    test9_en[index, ] <- sorted9_en[, 1]$glmnet

    # LASSO
    lassoFit9 <- train(tr9, trainFlesh9, "glmnet", metric = "RMSE", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 1, by = 0.1), .alpha = 1), trControl = con)
    lasso_lambda_9[, index] <- lassoFit9$bestTune$.lambda
    coefsLASSO9[, index] <- as.matrix(coef(lassoFit9$finalModel, s = lassoFit9$finalModel$tuneValue$.lambda))
    predslasso9 <- predict(lassoFit9$finalModel, newx = tr9, s = lassoFit9$finalModel$tuneValue$.lambda, type = "response")
    y_fit_lasso9 <- predslasso9[, 1]
    R2_lasso9[, index] <- (cor(trainFlesh9, y_fit_lasso9)^2) * 100

    bhModels9_lasso <- list(glmnet = lassoFit9)
    #bhPred9_lasso<- predict(bhModels9_lasso, newdata = testBH9)
    allPred9_lasso <- extractPrediction(bhModels9_lasso, testX = testBH9, testY = testFlesh9)
    testPred9_lasso <- subset(allPred9_lasso, dataType == "Test")
    sorted9_lasso <- as.matrix(by(testPred9_lasso, list(model = testPred9_lasso$model), function(x) postResample(x$pred, x$obs)))
    test9_lasso[index, ] <- sorted9_lasso[, 1]$glmnet

    # SVM
    #set.seed(5)
    svmFit9 <- train(tr9, trainFlesh9, "svmRadial", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_svm9 <- predict(svmFit9$finalModel)
    svm_tune_Cost9[, index] <- svmFit9$bestTune$.C
    svm_tune_Sigma9[, index] <- svmFit9$bestTune$.sigma
    R2_svm9[, index] <- (cor(y_fit_svm9, trainFlesh9)^2) * 100

    bhModels9_svm <- list(svmRadial = svmFit9)
    #bhPred9_svm<- predict(bhModels9_svm, newdata = testBH9)
    allPred9_svm <- extractPrediction(bhModels9_svm, testX = testBH9, testY = testFlesh9)
    testPred9_svm <- subset(allPred9_svm, dataType == "Test")
    sorted9_svm <- as.matrix(by(testPred9_svm, list(model = testPred9_svm$model), function(x) postResample(x$pred, x$obs)))
    test9_svm[index, ] <- sorted9_svm[, 1]$svm

    # SPLS
    #set.seed(5)
    splsFit9 <- train(tr9, trainFlesh9, "spls", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_spls9 <- predict(splsFit9$finalModel)
    Coefspls9[, index] <- predict(splsFit9$finalModel, type = "coefficient", splsFit9$finalModel$tuneValue$.eta, splsFit9$finalModel$tuneValue$.K)
    R2_spls9[, index] <- (cor(y_fit_spls9, trainFlesh9)^2) * 100
    spls_K_9[, index] <- splsFit9$finalModel$tuneValue$.K
    spls_eta_9[, index] <- splsFit9$finalModel$tuneValue$.eta

    bhModels9_spls <- list(spls = splsFit9)
    #bhPred9_spls<- predict(bhModels9_svm, newdata = testBH9)
    allPred9_spls <- extractPrediction(bhModels9_spls, testX = testBH9, testY = testFlesh9)
    testPred9_spls <- subset(allPred9_spls, dataType == "Test")
    sorted9_spls <- as.matrix(by(testPred9_spls, list(model = testPred9_spls$model), function(x) postResample(x$pred, x$obs)))
    test9_spls[index, ] <- sorted9_spls[, 1]$spls

    # RF
    #set.seed(5)
    rfFit9 <- train(tr9, trainFlesh9, "rf", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_rf9 <- predict(rfFit9$finalModel)
    R2_rf9[, index] <- (cor(y_fit_rf9, trainFlesh9)^2) * 100
    rf_mtry9[, index] <- rfFit9$finalModel$tuneValue$.mtry
    rf_imp_9[, index] <- rfFit9$finalModel$importance

    bhModels9_rf <- list(rf = rfFit9)
    #bhPred9_rf<- predict(bhModels9_rf, newdata = testBH9)
    allPred9_rf <- extractPrediction(bhModels9_rf, testX = testBH9, testY = testFlesh9)
    testPred9_rf <- subset(allPred9_rf, dataType == "Test")
    sorted9_rf <- as.matrix(by(testPred9_rf, list(model = testPred9_rf$model), function(x) postResample(x$pred, x$obs)))
    test9_rf[index, ] <- sorted9_rf[, 1]$rf

    # RIDGE
    #set.seed(5)
    ridgeFit9 <- train(tr9, trainFlesh9, "glmnet", metric = "RMSE", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 100, by = 0.1), .alpha = 0), trControl = con)
    ridge_lambda_9[, index] <- ridgeFit9$bestTune$.lambda
    coefsRidge9[, index] <- as.matrix(coef(ridgeFit9$finalModel, s = ridgeFit9$finalModel$tuneValue$.lambda))
    predsridge9 <- predict(ridgeFit9$finalModel, newx = tr9, s = ridgeFit9$finalModel$tuneValue$.lambda, type = "response")
    y_fit_ridge9 <- predsridge9[, 1]
    R2_ridge9[, index] <- (cor(trainFlesh9, y_fit_ridge9)^2) * 100

    bhModels9_ridge <- list(glmnet = ridgeFit9)
    allPred9_ridge <- extractPrediction(bhModels9_ridge, testX = testBH9, testY = testFlesh9)
    testPred9_ridge <- subset(allPred9_ridge, dataType == "Test")
    sorted9_ridge <- as.matrix(by(testPred9_ridge, list(model = testPred9_ridge$model), function(x) postResample(x$pred, x$obs)))
    test9_ridge[index, ] <- sorted9_ridge[, 1]$glmnet

    ####-------------------------------Split 10-------------------------
    Train10 <- inTrain1$"10"
    tr10 <- DesignMatrix[Train10, ]
    ts10 <- DesignMatrix[-Train10, ]
    #preProc10 <- preProcess(tr10,method='scale')
    trainBH10 <- tr10
    testBH10 <- ts10
    trainFlesh10 <- data1$Flesh[Train10]
    testFlesh10 <- data1$Flesh[-Train10]

    # PCR
    #set.seed(5)
    pcrFit10 <- train(tr10, trainFlesh10, "pcr", metric = "RMSE", tuneLength = 50, trControl = con)
    coefsPCR10[, index] <- coef(pcrFit10$finalModel, ncomp = pcrFit10$finalModel$tuneValue$.ncomp)
    y_fit_PCR10 <- predict(pcrFit10$finalModel, ncomp = pcrFit10$finalModel$tuneValue$.ncomp)
    R2_pcr10[, index] <- (cor(y_fit_PCR10, trainFlesh10)^2) * 100
    opt_comp_pcr10[, index] <- pcrFit10$finalModel$tuneValue$.ncomp

    bhModels10_pcr <- list(pcr = pcrFit10)
    #bhPred10_pcr <- predict(bhModels10_pcr, newdata = testBH10)
    allPred10_pcr <- extractPrediction(bhModels10_pcr, testX = testBH10, testY = testFlesh10)
    testPred10_pcr <- subset(allPred10_pcr, dataType == "Test")
    sorted10_pcr <- as.matrix(by(testPred10_pcr, list(model = testPred10_pcr$model), function(x) postResample(x$pred, x$obs)))
    test4_pcr[index, ] <- sorted4_pcr[, 1]$pcr

    # PLS
    #set.seed(5)
    plsFit10 <- train(tr10, trainFlesh10, "pls", metric = "RMSE", tuneLength = 10, trControl = con)
    coefsPLS10[, index] <- coef(plsFit10$finalModel, ncomp = plsFit10$finalModel$tuneValue$.ncomp)
    y_fit_PLS10 <- predict(plsFit10$finalModel, ncomp = plsFit10$finalModel$tuneValue$.ncomp)
    R2_pls10[, index] <- (cor(y_fit_PLS10, trainFlesh10)^2) * 100
    opt_comp_pls10[, index] <- plsFit10$finalModel$tuneValue$.ncomp

    bhModels10_pls <- list(pls = plsFit10)
    #bhPred10_pls <- predict(bhModels10_pls, newdata = testBH10)
    allPred10_pls <- extractPrediction(bhModels10_pls, testX = testBH10, testY = testFlesh10)
    testPred10_pls <- subset(allPred10_pls, dataType == "Test")
    sorted10_pls <- as.matrix(by(testPred10_pls, list(model = testPred10_pls$model), function(x) postResample(x$pred, x$obs)))
    test10_pls[index, ] <- sorted10_pls[, 1]$pls

    # EN
    #set.seed(5)
    enFit10 <- train(tr10, trainFlesh10, "glmnet", metric = "RMSE", tuneLength = 10, trControl = con)
    en_frac_10[, index] <- enFit10$finalModel$tuneValue$.alpha
    en_lambda_10[, index] <- enFit10$finalModel$tuneValue$.lambda
    coefsEN10[, index] <- as.matrix(coef(enFit10$finalModel, s = enFit10$finalModel$tuneValue$.lambda))
    predsEN10 <- predict(enFit10$finalModel, newx = tr10, s = enFit10$finalModel$tuneValue$.lambda, type = "response")
    y_fit_en10 <- predsEN10[, 1]
    R2_en10[, index] <- (cor(trainFlesh10, y_fit_en10)^2) * 100

    bhModels10_en <- list(glmnet = enFit10)
    #bhPred10_en<- predict(bhModels10_en, newdata = testBH10)
    allPred10_en <- extractPrediction(bhModels10_en, testX = testBH10, testY = testFlesh10)
    testPred10_en <- subset(allPred10_en, dataType == "Test")
    sorted10_en <- as.matrix(by(testPred10_en, list(model = testPred10_en$model), function(x) postResample(x$pred, x$obs)))
    test10_en[index, ] <- sorted10_en[, 1]$glmnet

    # LASSO
    lassoFit10 <- train(tr10, trainFlesh10, "glmnet", metric = "RMSE", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 1, by = 0.1), .alpha = 1), trControl = con)
    lasso_lambda_10[, index] <- lassoFit10$bestTune$.lambda
    coefsLASSO10[, index] <- as.matrix(coef(lassoFit10$finalModel, s = lassoFit10$finalModel$tuneValue$.lambda))
    predslasso10 <- predict(lassoFit10$finalModel, newx = tr10, s = lassoFit10$finalModel$tuneValue$.lambda, type = "response")
    y_fit_lasso10 <- predslasso10[, 1]
    R2_lasso10[, index] <- (cor(trainFlesh10, y_fit_lasso10)^2) * 100

    bhModels10_lasso <- list(glmnet = lassoFit10)
    #bhPred10_lasso<- predict(bhModels10_lasso, newdata = testBH10)
    allPred10_lasso <- extractPrediction(bhModels10_lasso, testX = testBH10, testY = testFlesh10)
    testPred10_lasso <- subset(allPred10_lasso, dataType == "Test")
    sorted10_lasso <- as.matrix(by(testPred10_lasso, list(model = testPred10_lasso$model), function(x) postResample(x$pred, x$obs)))
    test10_lasso[index, ] <- sorted10_lasso[, 1]$glmnet

    # SVM
    #set.seed(5)
    svmFit10 <- train(tr10, trainFlesh10, "svmRadial", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_svm10 <- predict(svmFit10$finalModel)
    svm_tune_Cost10[, index] <- svmFit10$bestTune$.C
    svm_tune_Sigma10[, index] <- svmFit10$bestTune$.sigma
    R2_svm10[, index] <- (cor(y_fit_svm10, trainFlesh10)^2) * 100

    bhModels10_svm <- list(svmRadial = svmFit10)
    #bhPred10_svm<- predict(bhModels10_svm, newdata = testBH10)
    allPred10_svm <- extractPrediction(bhModels10_svm, testX = testBH10, testY = testFlesh10)
    testPred10_svm <- subset(allPred10_svm, dataType == "Test")
    sorted10_svm <- as.matrix(by(testPred10_svm, list(model = testPred10_svm$model), function(x) postResample(x$pred, x$obs)))
    test10_svm[index, ] <- sorted10_svm[, 1]$svm

    # SPLS
    #set.seed(5)
    splsFit10 <- train(tr10, trainFlesh10, "spls", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_spls10 <- predict(splsFit10$finalModel)
    Coefspls10[, index] <- predict(splsFit10$finalModel, type = "coefficient", splsFit10$finalModel$tuneValue$.eta, splsFit10$finalModel$tuneValue$.K)
    R2_spls10[, index] <- (cor(y_fit_spls10, trainFlesh10)^2) * 100
    spls_K_10[, index] <- splsFit10$finalModel$tuneValue$.K
    spls_eta_10[, index] <- splsFit10$finalModel$tuneValue$.eta

    bhModels10_spls <- list(spls = splsFit10)
    #bhPred10_spls<- predict(bhModels10_svm, newdata = testBH10)
    allPred10_spls <- extractPrediction(bhModels10_spls, testX = testBH10, testY = testFlesh10)
    testPred10_spls <- subset(allPred10_spls, dataType == "Test")
    sorted10_spls <- as.matrix(by(testPred10_spls, list(model = testPred10_spls$model), function(x) postResample(x$pred, x$obs)))
    test10_spls[index, ] <- sorted10_spls[, 1]$spls

    # RF
    rfFit10 <- train(tr10, trainFlesh10, "rf", metric = "RMSE", tuneLength = 10, trControl = con)
    y_fit_rf10 <- predict(rfFit10$finalModel)
    R2_rf10[, index] <- (cor(y_fit_rf10, trainFlesh10)^2) * 100
    rf_mtry10[, index] <- rfFit10$finalModel$tuneValue$.mtry
    rf_imp_10[, index] <- rfFit10$finalModel$importance

    bhModels10_rf <- list(rf = rfFit10)
    #bhPred10_rf<- predict(bhModels10_rf, newdata = testBH10)
    allPred10_rf <- extractPrediction(bhModels10_rf, testX = testBH10, testY = testFlesh10)
    testPred10_rf <- subset(allPred10_rf, dataType == "Test")
    sorted10_rf <- as.matrix(by(testPred10_rf, list(model = testPred10_rf$model), function(x) postResample(x$pred, x$obs)))
    test10_rf[index, ] <- sorted10_rf[, 1]$rf

    # RIDGE
    ridgeFit10 <- train(tr10, trainFlesh10, "glmnet", metric = "RMSE", tuneLength = 10, tuneGrid = data.frame(.lambda = seq(0, 100, by = 0.1), .alpha = 0), trControl = con)
    ridge_lambda_10[, index] <- ridgeFit10$bestTune$.lambda
    coefsRidge10[, index] <- as.matrix(coef(ridgeFit10$finalModel, s = ridgeFit10$finalModel$tuneValue$.lambda))
    predsridge10 <- predict(ridgeFit10$finalModel, newx = tr10, s = ridgeFit10$finalModel$tuneValue$.lambda, type = "response")
    y_fit_ridge10 <- predsridge10[, 1]
    R2_ridge10[, index] <- (cor(trainFlesh10, y_fit_ridge10)^2) * 100

    bhModels10_ridge <- list(glmnet = ridgeFit10)
    allPred10_ridge <- extractPrediction(bhModels10_ridge, testX = testBH10, testY = testFlesh10)
    testPred10_ridge <- subset(allPred10_ridge, dataType == "Test")
    sorted10_ridge <- as.matrix(by(testPred10_ridge, list(model = testPred10_ridge$model), function(x) postResample(x$pred, x$obs)))
    test10_ridge[index, ] <- sorted10_ridge[, 1]$glmnet
}



########################### Storing the training data  set  #######################################
######### PLS ##########################
PLS_Train_Coeff <- cbind(coefsPLS1, coefsPLS2, coefsPLS3, coefsPLS4, coefsPLS5, coefsPLS6, coefsPLS7, coefsPLS8, coefsPLS9, coefsPLS10)
#PLS_Train_y_Fit<-cbind(y_fit_PLS1,y_fit_PLS2, y_fit_PLS3,y_fit_PLS4,y_fit_PLS5,y_fit_PLS6,y_fit_PLS7,y_fit_PLS8,y_fit_PLS9,y_fit_PLS10)
PLS_Train_R2 <- cbind(R2_pls1, R2_pls2, R2_pls3, R2_pls4, R2_pls5, R2_pls6, R2_pls7, R2_pls8, R2_pls9, R2_pls10)
PLS_Train_ncomp <- cbind(opt_comp_pls1, opt_comp_pls2, opt_comp_pls3, opt_comp_pls4, opt_comp_pls5, opt_comp_pls6, opt_comp_pls7, opt_comp_pls8, opt_comp_pls9, opt_comp_pls10)

write.csv(PLS_Train_Coeff, paste("PLS_coef", "_", totiter, sep = ""))
write.csv(PLS_Train_R2, paste("PLS_R2", "_", totiter, sep = ""))
write.csv(PLS_Train_ncomp, paste("PLS_comp", "_", totiter, sep = ""))

######### PCR ##########################
PCR_Train_Coeff <- cbind(coefsPCR1, coefsPCR2, coefsPCR3, coefsPCR4, coefsPCR5, coefsPCR6, coefsPCR7, coefsPCR8, coefsPCR9, coefsPCR10)
#PCR_Train_y_Fit<-cbind(y_fit_PCR1,y_fit_PCR2, y_fit_PCR3,y_fit_PCR4,y_fit_PCR5,y_fit_PCR6,y_fit_PCR7,y_fit_PCR8,y_fit_PCR9,y_fit_PCR10)
PCR_Train_R2 <- cbind(R2_pcr1, R2_pcr2, R2_pcr3, R2_pcr4, R2_pcr5, R2_pcr6, R2_pcr7, R2_pcr8, R2_pcr9, R2_pcr10)
PCR_Train_ncomp <- cbind(opt_comp_pcr1, opt_comp_pcr2, opt_comp_pcr3, opt_comp_pcr4, opt_comp_pcr5, opt_comp_pcr6, opt_comp_pcr7, opt_comp_pcr8, opt_comp_pcr9, opt_comp_pcr10)

write.csv(PCR_Train_Coeff, paste("PCR_coef", "_", totiter, sep = ""))
write.csv(PCR_Train_R2, paste("PCR_R2", "_", totiter, sep = ""))
write.csv(PCR_Train_ncomp, paste("PCR_comp", "_", totiter, sep = ""))
########### EN #################
EN_Train_Coeff <- cbind(coefsEN1, coefsEN2, coefsEN3, coefsEN4, coefsEN5, coefsEN6, coefsEN7, coefsEN8, coefsEN9, coefsEN10)
#EN_Train_Coeff<-cbind(coefsEN1,coefsEN2,coefsEN3,coefsEN4,coefsEN5,coefsEN6,coefsEN7,coefsEN8, coefsEN9,coefsEN10)
EN_Train_Fraction <- cbind(en_frac_1, en_frac_2, en_frac_3, en_frac_4, en_frac_5, en_frac_6, en_frac_7, en_frac_8, en_frac_9, en_frac_10)
EN_Train_Lambda <- cbind(en_lambda_1, en_lambda_2, en_lambda_3, en_lambda_4, en_lambda_5, en_lambda_6, en_lambda_7, en_lambda_8, en_lambda_9, en_lambda_10)
EN_Train_R2 <- cbind(R2_en1, R2_en2, R2_en3, R2_en4, R2_en5, R2_en6, R2_en7, R2_en8, R2_en9, R2_en10)
#EN_Train_y_Fit<-cbind(y_fit_en1,y_fit_en2, y_fit_en3,y_fit_en4,y_fit_en5,y_fit_en6,y_fit_en7,y_fit_en8,y_fit_en9,y_fit_en10)

write.csv(EN_Train_Coeff, paste("EN_coef", "_", totiter, sep = ""))
write.csv(EN_Train_R2, paste("EN_R2", "_", totiter, sep = ""))
write.csv(EN_Train_Fraction, paste("EN_Frac", "_", totiter, sep = ""))
write.csv(EN_Train_Lambda, paste("EN_Lambda", "_", totiter, sep = ""))

########### LASSO #################
LASSO_Train_lambda <- cbind(lasso_lambda_1, lasso_lambda_2, lasso_lambda_3, lasso_lambda_4, lasso_lambda_5, lasso_lambda_6, lasso_lambda_7, lasso_lambda_8, lasso_lambda_9, lasso_lambda_10)
LASSO_Train_R2 <- cbind(R2_lasso1, R2_lasso2, R2_lasso3, R2_lasso4, R2_lasso5, R2_lasso6, R2_lasso7, R2_lasso8, R2_lasso9, R2_lasso10)
LASSO_Train_Coeff <- cbind(coefsLASSO1, coefsLASSO2, coefsLASSO3, coefsLASSO4, coefsLASSO5, coefsLASSO6, coefsLASSO7, coefsLASSO8, coefsLASSO9, coefsLASSO10)

write.csv(LASSO_Train_Coeff, paste("lasso_coef", "_", totiter, sep = ""))
write.csv(LASSO_Train_R2, paste("Lasso_R2", "_", totiter, sep = ""))
write.csv(LASSO_Train_lambda, paste("Lasso_Lambda", "_", totiter, sep = ""))

###################  SVM   ##########################
SVM_Train_R2 <- cbind(R2_svm1, R2_svm2, R2_svm3, R2_svm4, R2_svm5, R2_svm6, R2_svm7, R2_svm8, R2_svm9, R2_svm10)
SVM_Train_Cost <- cbind(svm_tune_Cost1, svm_tune_Cost2, svm_tune_Cost3, svm_tune_Cost4, svm_tune_Cost5, svm_tune_Cost6, svm_tune_Cost7, svm_tune_Cost8, svm_tune_Cost9, svm_tune_Cost10)
SVM_Train_Sigma <- cbind(svm_tune_Sigma1, svm_tune_Sigma2, svm_tune_Sigma3, svm_tune_Sigma4, svm_tune_Sigma5, svm_tune_Sigma6, svm_tune_Sigma7, svm_tune_Sigma8, svm_tune_Sigma9, svm_tune_Sigma10)

write.csv(SVM_Train_Sigma, paste("SVM_Sig", "_", totiter, sep = ""))
write.csv(SVM_Train_R2, paste("SVM_R2", "_", totiter, sep = ""))
write.csv(SVM_Train_Cost, paste("SVM_cost", "_", totiter, sep = ""))
#write.csv( , paste('EN_Lambda', '_', totiter, sep=''))

####################   SPLS   ###########################
SPLS_Train_K <- cbind(spls_K_1, spls_K_2, spls_K_3, spls_K_4, spls_K_5, spls_K_6, spls_K_7, spls_K_8, spls_K_9, spls_K_10)
SPLS_Train_Eta <- cbind(spls_eta_1, spls_eta_2, spls_eta_3, spls_eta_4, spls_eta_5, spls_eta_6, spls_eta_7, spls_eta_8, spls_eta_9, spls_eta_10)
SPLS_Train_Coeff <- cbind(Coefspls1, Coefspls2, Coefspls3, Coefspls4, Coefspls5, Coefspls6, Coefspls7, Coefspls8, Coefspls9, Coefspls10)
SPLS_Train_R2 <- cbind(R2_spls1, R2_spls2, R2_spls3, R2_spls4, R2_spls5, R2_spls6, R2_spls7, R2_spls8, R2_spls9, R2_spls10)

write.csv(SPLS_Train_Coeff, paste("SPLS_coef", "_", totiter, sep = ""))
write.csv(SPLS_Train_R2, paste("SPLS_R2", "_", totiter, sep = ""))
write.csv(SPLS_Train_Eta, paste("SPLS_Eta", "_", totiter, sep = ""))
write.csv(SPLS_Train_K, paste("SPLS_K", "_", totiter, sep = ""))

########################## RIDGE #################################
RIDGE_Train_Coef <- cbind(coefsRidge1, coefsRidge2, coefsRidge3, coefsRidge4, coefsRidge5, coefsRidge6, coefsRidge7, coefsRidge8, coefsRidge9, coefsRidge10)
RIDGE_Train_lambda <- cbind(ridge_lambda_1, ridge_lambda_2, ridge_lambda_3, ridge_lambda_4, ridge_lambda_5, ridge_lambda_6, ridge_lambda_7, ridge_lambda_8, ridge_lambda_9, ridge_lambda_10)
RIDGE_TRAIN_R2 <- cbind(R2_ridge1, R2_ridge2, R2_ridge3, R2_ridge4, R2_ridge5, R2_ridge6, R2_ridge7, R2_ridge8, R2_ridge9, R2_ridge10)

write.csv(RIDGE_Train_Coef, paste("Ridge_coef", "_", totiter, sep = ""))
write.csv(RIDGE_Train_lambda, paste("Ridge_Lambda", "_", totiter, sep = ""))
write.csv(RIDGE_TRAIN_R2, paste("Ridge_R2", "_", totiter, sep = ""))

################################ RF   #############################################################################
RF_Train_R2 <- cbind(R2_rf1, R2_rf2, R2_rf3, R2_rf4, R2_rf5, R2_rf6, R2_rf7, R2_rf8, R2_rf9, R2_rf10)
RF_Train_mtry <- cbind(rf_mtry1, rf_mtry2, rf_mtry3, rf_mtry4, rf_mtry5, rf_mtry6, rf_mtry7, rf_mtry8, rf_mtry9, rf_mtry10)
RF_Train_varImp <- cbind(rf_imp_1, rf_imp_2, rf_imp_3, rf_imp_4, rf_imp_5, rf_imp_6, rf_imp_7, rf_imp_8, rf_imp_9, rf_imp_10)

write.csv(RF_Train_R2, paste("RF_R2", "_", totiter, sep = ""))
write.csv(RF_Train_mtry, paste("RF_mtry", "_", totiter, sep = ""))
write.csv(RF_Train_varImp, paste("RF_varImp", "_", totiter, sep = ""))

########################### Storing the test data set#######################################
write.csv(test1_pcr, paste("pcr_1_test", "_", totiter, sep = ""))
write.csv(test2_pcr, paste("pcr_2_test", "_", totiter, sep = ""))
write.csv(test3_pcr, paste("pcr_3_test", "_", totiter, sep = ""))
write.csv(test4_pcr, paste("pcr_4_test", "_", totiter, sep = ""))
write.csv(test5_pcr, paste("pcr_5_test", "_", totiter, sep = ""))
write.csv(test6_pcr, paste("pcr_6_test", "_", totiter, sep = ""))
write.csv(test7_pcr, paste("pcr_7_test", "_", totiter, sep = ""))
write.csv(test8_pcr, paste("pcr_8_test", "_", totiter, sep = ""))
write.csv(test9_pcr, paste("pcr_9_test", "_", totiter, sep = ""))
write.csv(test10_pcr, paste("pcr_10_test", "_", totiter, sep = ""))

write.csv(test1_pls, paste("pls_1_test", "_", totiter, sep = ""))
write.csv(test2_pls, paste("pls_2_test", "_", totiter, sep = ""))
write.csv(test3_pls, paste("pls_3_test", "_", totiter, sep = ""))
write.csv(test4_pls, paste("pls_4_test", "_", totiter, sep = ""))
write.csv(test5_pls, paste("pls_5_test", "_", totiter, sep = ""))
write.csv(test6_pls, paste("pls_6_test", "_", totiter, sep = ""))
write.csv(test7_pls, paste("pls_7_test", "_", totiter, sep = ""))
write.csv(test8_pls, paste("pls_8_test", "_", totiter, sep = ""))
write.csv(test9_pls, paste("pls_9_test", "_", totiter, sep = ""))
write.csv(test10_pls, paste("pls_10_test", "_", totiter, sep = ""))

write.csv(test1_en, paste("en_1_test", "_", totiter, sep = ""))
write.csv(test2_en, paste("en_2_test", "_", totiter, sep = ""))
write.csv(test3_en, paste("en_3_test", "_", totiter, sep = ""))
write.csv(test4_en, paste("en_4_test", "_", totiter, sep = ""))
write.csv(test5_en, paste("en_5_test", "_", totiter, sep = ""))
write.csv(test6_en, paste("en_6_test", "_", totiter, sep = ""))
write.csv(test7_en, paste("en_7_test", "_", totiter, sep = ""))
write.csv(test8_en, paste("en_8_test", "_", totiter, sep = ""))
write.csv(test9_en, paste("en_9_test", "_", totiter, sep = ""))
write.csv(test10_en, paste("en_10_test", "_", totiter, sep = ""))

write.csv(test1_lasso, paste("lasso_1_test", "_", totiter, sep = ""))
write.csv(test2_lasso, paste("lasso_2_test", "_", totiter, sep = ""))
write.csv(test3_lasso, paste("lasso_3_test", "_", totiter, sep = ""))
write.csv(test4_lasso, paste("lasso_4_test", "_", totiter, sep = ""))
write.csv(test5_lasso, paste("lasso_5_test", "_", totiter, sep = ""))
write.csv(test6_lasso, paste("lasso_6_test", "_", totiter, sep = ""))
write.csv(test7_lasso, paste("lasso_7_test", "_", totiter, sep = ""))
write.csv(test8_lasso, paste("lasso_8_test", "_", totiter, sep = ""))
write.csv(test9_lasso, paste("lasso_9_test", "_", totiter, sep = ""))
write.csv(test10_lasso, paste("lasso_10_test", "_", totiter, sep = ""))

write.csv(test1_svm, paste("svm_1_test", "_", totiter, sep = ""))
write.csv(test2_svm, paste("svm_2_test", "_", totiter, sep = ""))
write.csv(test3_svm, paste("svm_3_test", "_", totiter, sep = ""))
write.csv(test4_svm, paste("svm_4_test", "_", totiter, sep = ""))
write.csv(test5_svm, paste("svm_5_test", "_", totiter, sep = ""))
write.csv(test6_svm, paste("svm_6_test", "_", totiter, sep = ""))
write.csv(test7_svm, paste("svm_7_test", "_", totiter, sep = ""))
write.csv(test8_svm, paste("svm_8_test", "_", totiter, sep = ""))
write.csv(test9_svm, paste("svm_9_test", "_", totiter, sep = ""))
write.csv(test10_svm, paste("svm_10_test", "_", totiter, sep = ""))

write.csv(test1_ridge, paste("ridge_1_test", "_", totiter, sep = ""))
write.csv(test2_ridge, paste("ridge_2_test", "_", totiter, sep = ""))
write.csv(test3_ridge, paste("ridge_3_test", "_", totiter, sep = ""))
write.csv(test4_ridge, paste("ridge_4_test", "_", totiter, sep = ""))
write.csv(test5_ridge, paste("ridge_5_test", "_", totiter, sep = ""))
write.csv(test6_ridge, paste("ridge_6_test", "_", totiter, sep = ""))
write.csv(test7_ridge, paste("ridge_7_test", "_", totiter, sep = ""))
write.csv(test8_ridge, paste("ridge_8_test", "_", totiter, sep = ""))
write.csv(test9_ridge, paste("ridge_9_test", "_", totiter, sep = ""))
write.csv(test10_ridge, paste("ridge_10_test", "_", totiter, sep = ""))

write.csv(test1_spls, paste("spls_1_test", "_", totiter, sep = ""))
write.csv(test2_spls, paste("spls_2_test", "_", totiter, sep = ""))
write.csv(test3_spls, paste("spls_3_test", "_", totiter, sep = ""))
write.csv(test4_spls, paste("spls_4_test", "_", totiter, sep = ""))
write.csv(test5_spls, paste("spls_5_test", "_", totiter, sep = ""))
write.csv(test6_spls, paste("spls_6_test", "_", totiter, sep = ""))
write.csv(test7_spls, paste("spls_7_test", "_", totiter, sep = ""))
write.csv(test8_spls, paste("spls_8_test", "_", totiter, sep = ""))
write.csv(test9_spls, paste("spls_9_test", "_", totiter, sep = ""))
write.csv(test10_spls, paste("spls_10_test", "_", totiter, sep = ""))

write.csv(test1_rf, paste("rf_1_test", "_", totiter, sep = ""))
write.csv(test2_rf, paste("rf_2_test", "_", totiter, sep = ""))
write.csv(test3_rf, paste("rf_3_test", "_", totiter, sep = ""))
write.csv(test4_rf, paste("rf_4_test", "_", totiter, sep = ""))
write.csv(test5_rf, paste("rf_5_test", "_", totiter, sep = ""))
write.csv(test6_rf, paste("rf_6_test", "_", totiter, sep = ""))
write.csv(test7_rf, paste("rf_7_test", "_", totiter, sep = ""))
write.csv(test8_rf, paste("rf_8_test", "_", totiter, sep = ""))
write.csv(test9_rf, paste("rf_9_test", "_", totiter, sep = ""))
write.csv(test10_rf, paste("rf_10_test", "_", totiter, sep = ""))

test_op_svm <- cbind(test1_svm, test2_svm, test3_svm, test4_svm, test5_svm, test6_svm, test7_svm, test8_svm, test9_svm, test10_svm)
test_op_spls <- cbind(test1_spls, test2_spls, test3_spls, test4_spls, test5_spls, test6_spls, test7_spls, test8_spls, test9_spls, test10_spls)
test_op_pls <- cbind(test1_pls, test2_pls, test3_pls, test4_pls, test5_pls, test6_pls, test7_pls, test8_pls, test9_pls, test10_pls)
test_op_pcr <- cbind(test1_pcr, test2_pcr, test3_pcr, test4_pcr, test5_pcr, test6_pcr, test7_pcr, test8_pcr, test9_pcr, test10_pcr)
test_op_rf <- cbind(test1_rf, test2_rf, test3_rf, test4_rf, test5_rf, test6_rf, test7_rf, test8_rf, test9_rf, test10_rf)
test_op_ridge <- cbind(test1_ridge, test2_ridge, test3_ridge, test4_ridge, test5_ridge, test6_ridge, test7_ridge, test8_ridge, test9_ridge, test10_ridge)
test_op_en <- cbind(test1_en, test2_en, test3_en, test4_en, test5_en, test6_en, test7_en, test8_en, test9_en, test10_en)
test_op_lasso <- cbind(test1_lasso, test2_lasso, test3_lasso, test4_lasso, test5_lasso, test6_lasso, test7_lasso, test8_lasso, test9_lasso, test10_lasso)

#write.xls(test_op_svm,'test_results_svm.xls')
#write.xls(test_op_spls,'test_results_spls.xls')
#write.xls(test_op_pls,'test_results_pls.xls')
#write.xls(test_op_pcr,'test_results_pcr.xls')
#write.xls(test_op_rf,'test_results_rf.xls')
#write.xls(test_op_ridge,'test_results_ridge.xls')
#write.xls(test_op_en,'test_results_en.xls')
#write.xls(test_op_lasso,'test_results_lasso.xls')

ridge <- cbind(test1_ridge, test2_ridge, test3_ridge, test4_ridge, test5_ridge, test6_ridge, test7_ridge, test8_ridge, test9_ridge, test10_ridge)
write.xls(ridge, "ridgenew.xls")

pcr <- cbind(test1_pcr, test2_pcr, test3_pcr, test4_pcr, test5_pcr, test6_pcr, test7_pcr, test8_pcr, test9_pcr, test10_pcr)
write.xls(pcr, "pcrnew.xls")

pls <- cbind(test1_pls, test2_pls, test3_pls, test4_pls, test5_pls, test6_pls, test7_pls, test8_pls, test9_pls, test10_pls)
write.xls(pls, "plsnew.xls")

rf <- cbind(test1_rf, test2_rf, test3_rf, test4_rf, test5_rf, test6_rf, test7_rf, test8_rf, test9_rf, test10_rf)
write.xls(rf, "rfnew.xls")

svm <- cbind(test1_svm, test2_svm, test3_svm, test4_svm, test5_svm, test6_svm, test7_svm, test8_svm, test9_svm, test10_svm)
write.xls(svm, "svmnew.xls")

lasso_all <- cbind(test1_lasso, test2_lasso, test3_lasso, test4_lasso, test5_lasso, test6_lasso, test7_lasso, test8_lasso, test9_lasso, test10_lasso)
write.xls(lasso_all, "lassonew.xls")

spls <- cbind(test1_spls, test2_spls, test3_spls, test4_spls, test5_spls, test6_spls, test7_spls, test8_spls, test9_spls, test10_spls)
write.xls(spls, "splsnew.xls")

en <- cbind(test1_en, test1_en, test2_en, test3_en, test4_en, test5_en, test6_en, test7_en, test8_en, test9_en, test10_en)
write.xls(en, "ennew.xls")
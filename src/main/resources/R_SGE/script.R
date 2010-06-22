r2<-NULL
r2adj<-NULL
beta0<-NULL
beta1<-NULL
pval<-NULL

library(gdata)
library(multtest)
X<-read.xls("traits.xls")

rownames(X)<-X[,1]
y<-X[,2]

for(i in 2:dim(X)[2])
{
	lmres<-lm(y ~ X[,i])
	anovares<-anova(lmres)
	sumres<-summary(lmres)
	r2[i]<-sumres$r.squared
	r2adj[i]<-sumres$adj.r.squared
	beta0[i]<-sumres$coefficients[1]
	beta1[i]<-sumres$coefficients[2]
	pval[i]<-anovares[1,"Pr(>F)"]
}

# Find the best predictor among the univariate predictors
j<-which(pval==min(pval))

# Produce results in Xl files
index<-1:dim(X)[2]
adjp1<-mt.rawp2adjp(rawp=pval[order(index)])
adjp<-adjp1$adjp[order(adjp1$index),]

trtname<-colnames(X)[2]#fix??

outp<-cbind(beta1,r2,pval,adjp)
filename5<-paste(trtname,"Univ_coef.xls",sep="_")
write.csv(outp,filename5)

#co<-cbind(co,outp)

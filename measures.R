setwd("./")
library(ade4)

#Defaults:
defaults <- read.table("./measures.tab", header = TRUE)
s.value(defaults[, c(1:2)], defaults[,3], method = "greylevel", origin = c( min(defaults[,1]+1), min(defaults[4]+1))) #mapper
s.value(defaults[, c(1:2)], defaults[,4], method = "greylevel", origin = c( min(defaults[,1]+1), min(defaults[4]+1))) #mapper
acp = dudi.pca(defaults) # Analyse de composantes principales
2 # 2 valeurs propres: 2D
s.corcircle(acp$co) # Cercle des correlations


#codes 5:5

#crossover:
crossovers <- read.table("./5:5_measures/crossoverAv.tab", header = TRUE)
shapiro.test(crossovers[,1]) # >0.05: normalité
shapiro.test(crossovers[,2]) # >0.05: normalité
var.test(crossovers[,1], crossovers[,2]) # <0.05: homogénéité
# distributions normales && homogénéité: t-test:
t.test(crossovers[,1],crossovers[,2],var.equal = T) # <0.05: indépendance

#eligibility / posWeight:
e_pw <- read.table("./5:5_measures/eligibility_posweight.tab", header = TRUE)
shapiro.test(e_pw[,1]) # >0.05: normalité
shapiro.test(e_pw[,2]) # >0.05: normalité
shapiro.test(e_pw[,3]) # >0.05: normalité
shapiro.test(e_pw[,4]) # >0.05: normalité
var.test(e_pw[,1], e_pw[,3]) # <0.05: homogénéité
var.test(e_pw[,1], e_pw[,4]) # <0.05: homogénéité
var.test(e_pw[,2], e_pw[,3]) # <0.05: homogénéité
var.test(e_pw[,2], e_pw[,4]) # <0.05: homogénéité
# distributions !normales && homogénéité: wilcox-test.
wilcox.test(e_pw[,1],e_pw[,3] ,alternative="greater") # <0.05: indépendance -> dep
wilcox.test(e_pw[,1],e_pw[,4] ,alternative="greater") # <0.05: indépendance -> non-dep
wilcox.test(e_pw[,2], e_pw[,3] ,alternative="greater") # <0.05: homogénéité -> dep
wilcox.test(e_pw[,2], e_pw[,4] ,alternative="greater") # <0.05: homogénéité -> non-dep
acp2 = dudi.pca(e_pw)  # Analyse de composantes principales
2 # 2 valeurs propres: 2D
s.corcircle(acp2$co) # Cercle des correlations


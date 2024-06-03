'''
Fixed Income Asset Allocator:
For each ratings based fund, calculate:
    OAS relative to JUCO
    MA of OAS relative to JUCO over t months
    Standard deviation of MA of OAS relative to JUCO over t months
    Z-score of each current relative OAS to MA of relative OAS:
    (thisMonthRelativeSpread - MA RelativeSpread) / SDev of relative spread over MA

'''
import pandas as pd
import numpy as np
import math
bondData = pd.read_csv('/Users/jonahschwartzman/Downloads/computerScience/Personal/Python/BondData.csv')

xcccSpreads = np.array(bondData['JCCCOAS'])
xbSpreads = np.array(bondData['JUC2OAS'])
xbbSpreads = np.array(bondData['JUC1OAS'])
aggSpreads = np.array(bondData['JUC0OAS'])

xcccRelativeSpreads = xcccSpreads/aggSpreads
xbbRelativeSpreads = xbbSpreads/aggSpreads
xbRelativeSpreads = xbSpreads/aggSpreads
totalMonths = len(aggSpreads)
t = 10

xcccAvgRelativeSpreads = np.zeros(totalMonths-t)
xcccStds = np.zeros(totalMonths-t)
xcccZScores = np.zeros(totalMonths-t)
for i in range(0, totalMonths-t):
    xcccAvgRelativeSpreads[i] = np.average(xcccRelativeSpreads[i: i+t])
    xcccStds[i] = np.std(xcccRelativeSpreads[i: i+t])
    xcccZScores[i] = (xcccRelativeSpreads[i+t] - xcccAvgRelativeSpreads[i]) / xcccStds[i]

xbbAvgRelativeSpreads = np.zeros(totalMonths-t)
xbbStds = np.zeros(totalMonths-t)
xbbZScores = np.zeros(totalMonths-t)
for i in range(0, totalMonths-t):
    xbbAvgRelativeSpreads[i] = np.average(xbbRelativeSpreads[i: i+t])
    xbbStds[i] = np.std(xbbRelativeSpreads[i: i+t])
    xbbZScores[i] = (xbbRelativeSpreads[i+t] - xbbAvgRelativeSpreads[i]) / xbbStds[i]

xbAvgRelativeSpreads = np.zeros(totalMonths-t)
xbStds = np.zeros(totalMonths-t)
xbZScores = np.zeros(totalMonths-t)
for i in range(0, totalMonths-t):
    xbAvgRelativeSpreads[i] = np.average(xbRelativeSpreads[i: i+t])
    xbStds[i] = np.std(xbRelativeSpreads[i: i+t])
    xbZScores[i] = (xbRelativeSpreads[i+t] - xbAvgRelativeSpreads[i]) / xbStds[i]

print("XCCC z-scores: ", xcccZScores)
print("XB z-scores: ", xbZScores)
print("XBB z-scores: ", xbZScores)
for v in xbZScores:
    print(v)

'''f = open("bondDataTestFile.txt", "w")
f.write(str(xcccZScores))
'''
f = open("/Users/jonahschwartzman/Downloads/computerScience/Personal/Python/bondDataTestFile.txt", "w")
for v in xbZScores:
    f.write(str(v))
    f.write("\n")
f.close()

'''print("XCCC avg rel spread: ", xcccAvgSpreads)
print("XCCC avg rel sds: ", xcccsds)
print("XCCC z scores: ", xccczs)
print()
print("XBB avg rel spread: ", xbbAvgSpreads)
print("XBB avg rel sds: ", xbbsds)
print("XBB z scores: ", xbbzs)
print()
print("XB avg rel spread: ", xbAvgSpreads)
print("XB avg rel sds: ", xbsds)
print("XB z scores: ", xbzs)
print()

print(xcccSpreads)
print(xcccRelSpreads)'''






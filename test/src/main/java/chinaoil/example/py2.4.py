# -*- coding: utf-8 -*-
__author__ = 'hk'

###导入需要的库
import pandas as pd
import matplotlib.pyplot as plt
###加载数据，使用标题行
df=pd.read_csv("D:\SparkWorkSpace\dataset\catering_sale.csv",header=0)
##查看df的统计信息
df.count() ##统计非空值记录数

df.describe() ###获取df的统计信息


#建立图像
plt.figure()
#画箱线图
bp = df.boxplot()
# flies为异常值的标签
x = bp['fliers'][0].get_xdata()
y = bp['fliers'][0].get_ydata()
y.sort()

#用annotate添加注释
for i in range(len(x)):
    plt.annotate(y[i], xy = (x[i],y[i]), xytext=(x[i]+0.1-0.8/(y[i]-y[i-1]),y[i]))

plt.show()

# -*- coding: utf-8 -*-
__author__ = 'hk'
###导入需要的库
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt

###把日期列作为索引，并转换为日期格式
df=pd.read_csv("/test\dataset\catering_sale.csv",header=0,index_col='sale_date',parse_dates=True)
###把空值置为0
df1=df.fillna(0)
###根据年月求和
df_ym=df1.resample('M',how='sum')
##取年月
df2=df_ym.to_period('M')
##数据可视化
df2.plot(kind='bar',rot=30)
plt.show()
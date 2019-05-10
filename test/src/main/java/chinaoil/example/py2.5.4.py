# -*- coding: utf-8 -*-
__author__ = 'hk'
import pandas as pd
##读取HDFS上的数据
df=pd.read_csv("D:\SparkWorkSpace\dataset\catering_sale.csv",header=0)
##定位数据集中的空值
df[df.isnull().values==True]
###以0填补空值
df.fillna(0)
##或该列的平均值填补空值
df['sale_amt'].fillna(df['sale_amt'].count())
##或用该列前一行值填补空值
df.fillna(method='pad')


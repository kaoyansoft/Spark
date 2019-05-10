# -*- coding: utf-8 -*-
from pyspark.sql import SparkSession
from pyspark.sql import Row
import matplotlib.pyplot as plt
import numpy as np
from pyspark.sql.functions import *

spark = SparkSession \
    .builder \
    .appName("Python Spark SQL basic example") \
    .config("spark.some.config.option", "some-value") \
    .getOrCreate()
#读取CSV文件，保留文件标题，并创建spark 的一张derby数据库的表
df=spark.read.csv("D:\SparkWorkSpace\dataset\catering_sale.csv",header=True)
##转换数据类型
df1=df.select(df['sale_date'],df['sale_amt'].cast("Double"))
###假设把22.0奇异值替换为200.0
df1 = df1.fillna(0)
df1.replace(22.0,200.0,'sale_amt')
###去空格
df1.select(trim(df1.sale_date).alias('sale_date')).show()
###去年份
df1.select(substring(df1.sale_date,1,4).alias('year'),df1.sale_amt).show()


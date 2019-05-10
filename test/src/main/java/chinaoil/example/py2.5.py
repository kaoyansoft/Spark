# -*- coding: utf-8 -*-
__author__ = 'hk'
from pyspark.sql import SparkSession
from pyspark.sql import Row
import matplotlib.pyplot as plt
import numpy as np

spark = SparkSession \
    .builder \
    .appName("Python Spark SQL basic example") \
    .config("spark.some.config.option", "some-value") \
    .getOrCreate()

sc = spark.sparkContext
# 加载textfile文件并转换为行式
userrdd = sc.textFile("file:///test/dataset/ml-100k/u.user").map(lambda line: line.split("|"))
#利用反射机制把RDD转换为DataFrame
df = userrdd.map(lambda fields: Row(name=fields[0], age=int(fields[1]),gender=fields[2],occupation=fields[3],zip=fields[4]))

# 把dataframe注册为一个table.
schemauser = spark.createDataFrame(df)
schemauser.createOrReplaceTempView("user")

# 在table上运行SQL.
age = spark.sql("SELECT age FROM user")
#把运行结果转换为RDD
ages = age.rdd.map(lambda p: p.age).collect()
plt.hist(ages, bins=20, color='lightblue', normed=True)
#plt.show()

# 选取用户职业数据.
count_occp = spark.sql("SELECT occupation,count(occupation) as cnt FROM user Group by occupation order by cnt")
#查看前5行数据
count_occp.show(5)
#获取职业名称及职业数，以便画出各职业对应总数图形
#把运行结果转换为RDD
x_axis = count_occp.rdd.map(lambda p: p.occupation).collect()#collect转python类型
y_axis = count_occp.rdd.map(lambda p: p.cnt).collect()

pos = np.arange(len(x_axis))
width = 1.0
###隐式新增一个figure，或为当前figure新增一个axes
ax = plt.axes()
ax.set_xticks(pos + (width / 2)) ###设置x轴刻度
ax.set_xticklabels(x_axis) ####在对应刻度打上标签

plt.bar(pos, y_axis, width, color='orange')
plt.xticks(rotation=30) ####x轴上的标签旋转30度
fig = plt.gcf() ###获取当前figure的应用
fig.set_size_inches(16,10) ###设置当前figure大小
plt.show()


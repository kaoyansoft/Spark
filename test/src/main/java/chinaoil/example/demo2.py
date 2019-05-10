__author__ = 'hk'
from pyspark.sql import SparkSession
import os, time
if __name__ == "__main__":
    #os.environ['SPARK_HOME'] = "D:/soft/spark-2.4.2-bin-hadoop2.6"
    spark = SparkSession.builder \
        .appName("test") \
        .master("local[2]") \
        .getOrCreate()
    datas = ["hi I love you", "hello", "ni hao"]
    sc = spark.sparkContext
    rdd = sc.parallelize(datas)
    print(type(datas))
    print(type(rdd))
    print(rdd.count())
    print(rdd.first())
    time.sleep(100)
    spark.stop()

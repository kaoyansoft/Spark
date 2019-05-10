__author__ = 'hk'

from pyspark.sql import SparkSession
from pyspark.sql import Row

spark = SparkSession.builder \
        .appName("sparkDemo") \
        .master("local[4]") \
        .getOrCreate()
sc = spark.sparkContext
userrdd = sc.textFile("/test\dataset\ml-100k\u.user").map(lambda line:line.split("|"))
df = userrdd.map(lambda fields: Row(userid=fields[0], age=int(fields[1]),gender=fields[2],occupation=fields[3],zip=fields[4]))

schemauser = spark.createDataFrame(df)
schemauser.createOrReplaceTempView("user")
schemauser.describe("userid","age", "gender","occupation","zip").show()
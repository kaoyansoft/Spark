package chinaoil.example

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrame
/**
 * Created by hk on 2019/5/8.
 */

  object Sca3_1 {
    def main(args: Array[String]): Unit = {
      val spark = SparkSession.builder()
        .appName("Spark SQL basic example")
        .master("local[2]")
        .getOrCreate()

      //创建DataFrame
      val df1 = spark.read.option("header", true).format("csv").load("file:///test/dataset/customer.csv")
      // 转换字符类型
      val df2 = df1.select(
        df1("name").cast("String"),
        df1("age").cast("Double"),
        df1("gender").cast("String"))
      //显示df2的Schema
      df2.printSchema()

      df2.createOrReplaceTempView("customer")
      //查询
      val cust1 = spark.sql("SELECT * FROM customer WHERE age BETWEEN 30 AND 35")
      cust1.limit(5).show
      val cust2 = spark.sql("SELECT * FROM customer WHERE gender like 'M'")
      cust2.limit(5).show
      spark.stop()
    }

  }


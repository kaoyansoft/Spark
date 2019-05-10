package chinaoil.example

import org.apache.spark.ml.feature.Word2Vec
import org.apache.spark.sql.SparkSession

object Sca4_2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Spark SQL basic example")
      .master("local[2]")
      .getOrCreate()
    // 输入数据，每行为一个词袋，可来自语句或文档。
    val documentDF = spark.createDataFrame(Seq(
      "Hi I heard about Spark".split(" "),
      "I wish Java could use case classes".split(" "),
      "Logistic regression models are neat".split(" ")
    ).map(Tuple1.apply)).toDF("text")
    documentDF.show(5)
    //训练从词到向量的映射
    val word2Vec = new Word2Vec()
      .setInputCol("text")
      .setOutputCol("result")
      .setVectorSize(3)
      .setMinCount(0)
    val model = word2Vec.fit(documentDF)
    val result = model.transform(documentDF)
    result.select("result").take(3).foreach(println)
    spark.stop()
  }
}

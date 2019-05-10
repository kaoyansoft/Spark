package chinaoil.example

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.StopWordsRemover
object Sca4_5 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Spark SQL basic example")
      .master("local[2]")
      .getOrCreate()
    val remover = new StopWordsRemover()
      .setInputCol("raw")
      .setOutputCol("filtered")
    //其中，“I”, “the”, “had”以及“a”被移除。
    val dataSet = spark.createDataFrame(Seq(
      (0, Seq("I", "saw", "the", "red", "balloon")),
      (1, Seq("Mary", "had", "a", "little", "lamb"))
    )).toDF("id", "raw")
    remover.transform(dataSet).show(false)
    spark.stop()
  }
}

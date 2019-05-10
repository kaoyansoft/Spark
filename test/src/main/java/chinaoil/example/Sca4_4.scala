package chinaoil.example

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.{RegexTokenizer,Tokenizer}
import org.apache.spark.sql.functions._
object Sca4_4 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Spark SQL basic example")
      .master("local[2]")
      .getOrCreate()
    val sentenceDataFrame = spark.createDataFrame(Seq(
      (0,"Hi I heard about Spark"),
      (1,"I wish Java could use case classes"),
      (2,"Logistic,regression,models,are,neat")
    )).toDF("id","sentence")
    //token默认是按空格分词
    val tokenizer =new Tokenizer().setInputCol("sentence").setOutputCol("words")
    //Regextoken是按正则表达式分词，适用更广泛
    val regexTokenizer =new RegexTokenizer()
      .setInputCol("sentence")
      .setOutputCol("words")
      .setPattern("\\W")//或者使用 .setPattern("\\w+").setGaps(false)

    val countTokens = udf {(words:Seq[String])=> words.length }

    val tokenized = tokenizer.transform(sentenceDataFrame)
    tokenized.select("sentence","words")
      .withColumn("tokens", countTokens(col("words"))).show(false)
    val regexTokenized = regexTokenizer.transform(sentenceDataFrame)
    regexTokenized.select("sentence","words")
      .withColumn("tokens", countTokens(col("words"))).show(false)
    spark.stop()
  }
}

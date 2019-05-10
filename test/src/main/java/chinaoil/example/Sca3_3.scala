package chinaoil.example
import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.{HashingTF, Tokenizer}
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.sql.{Row, SparkSession}

object Sca3_3 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Spark SQL basic example")
      .master("local[2]")
      .getOrCreate()

    //准备训练文档，（id，内容，标签）
    val training = spark.createDataFrame(Seq(
      (0L, "a b c d e spark", 1.0),
      (1L, "b d", 0.0),
      (2L, "spark f g h", 1.0),
      (3L, "hadoop mapreduce", 0.0)
    )).toDF("id", "text", "label")

    //配置ML Pipeline，由三个stage组成，tokenizer, hashingTF, and lr.
    val tokenizer = new Tokenizer()
      .setInputCol("text")
      .setOutputCol("words")
    val hashingTF = new HashingTF()
      .setNumFeatures(1000)
      .setInputCol(tokenizer.getOutputCol)
      .setOutputCol("features")
    val lr = new LogisticRegression()
      .setMaxIter(10)
      .setRegParam(0.001)
    val pipeline = new Pipeline()
      .setStages(Array(tokenizer, hashingTF, lr))

    //在训练数据集上使用Pipeline
    val model = pipeline.fit(training)

    // Now we can optionally save the fitted pipeline to disk
    //现在可以保存安装好的流水线到磁盘上
    model.write.overwrite().save("/tmp/spark-logistic-regression-model")

    //现在可以保存未安装好的Pipeline保存到磁盘上
    pipeline.write.overwrite().save("/tmp/unfit-lr-model")

    // 装载模型
    val sameModel = PipelineModel.load("/tmp/spark-logistic-regression-model")

    //准备测试文档，不包含标签(id, text) .
    val test = spark.createDataFrame(Seq(
      (4L, "spark i j k"),
      (5L, "l m n"),
      (6L, "spark hadoop spark"),
      (7L, "apache hadoop")
    )).toDF("id", "text")
    //在测试文档上做出预测.
    sameModel.transform(test)
      .select("id", "text", "probability", "prediction")
      .collect()
      .foreach { case Row(id: Long, text: String, prob: Vector, prediction: Double) =>
        println(s"($id, $text) --> prob=$prob, prediction=$prediction")
      }
    spark.stop()
  }
}

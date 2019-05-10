package chinaoil.example

import org.apache.spark.ml.feature.DCT
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
object Sca4_10 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf();
    sparkConf.setMaster("local[*]").setAppName(this.getClass.getSimpleName)
    val spark = SparkSession
      .builder
      .config(sparkConf)
      .appName("DCT")
      .getOrCreate()
    val data = Seq(
      Vectors.dense(0.0, 1.0, -2.0, 3.0),
      Vectors.dense(-1.0, 2.0, 4.0, -7.0),
      Vectors.dense(14.0, -2.0, -5.0, 1.0))

    val df = spark.createDataFrame(data.map(Tuple1.apply)).toDF("features")

    val dct = new DCT()
      .setInputCol("features")
      .setOutputCol("featuresDCT")
      .setInverse(false)

    val dctDf = dct.transform(df)
    dctDf.select("featuresDCT").show(false)
    spark.stop()
  }
}

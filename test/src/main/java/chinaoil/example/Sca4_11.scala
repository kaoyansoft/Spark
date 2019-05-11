package chinaoil.example

import org.apache.spark.ml.feature.DCT
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
object Sca4_11 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf();
    sparkConf.setMaster("local[*]").setAppName(this.getClass.getSimpleName)
    val spark = SparkSession
      .builder
      .config(sparkConf)
      .appName("DCT")
      .getOrCreate()

  }
}

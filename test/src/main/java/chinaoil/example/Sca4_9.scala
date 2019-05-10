package chinaoil.example

import org.apache.spark.ml.feature.PCA
import org.apache.spark.ml.feature.StandardScaler
import org.apache.spark.sql.SparkSession

object Sca4_8 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Spark SQL basic example")
      .master("local[2]")
      .getOrCreate()

  }
}

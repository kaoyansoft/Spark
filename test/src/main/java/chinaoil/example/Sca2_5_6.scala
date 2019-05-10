package chinaoil.example

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.linalg.distributed.RowMatrix
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.linalg.Matrix


object  Sca2_5_6 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SparkPi").setMaster("local[2]")
    val sc = new SparkContext(conf)
//    val data = Array(
//      Vectors.dense(1,2,3,4,5,6,7,8,9),
//      Vectors.dense(5,6,7,8,9,0,8,6,7),
//      Vectors.dense(9,0,8,7,1,4,3,2,1),
//      Vectors.dense(6,4,2,1,3,4,2,1,5),
//      Vectors.dense(4,5,7,1,4,0,2,1,8))
//    val dataRDD = sc.parallelize(data, 2)
//    val mat: RowMatrix = new RowMatrix(dataRDD)
//    val svd = mat.computeSVD(3,true)
//    val U: RowMatrix = svd.U
//    val s: Vector = svd.s
//    val V: Matrix = svd.V
//    print(V)
    val data = Array( Vectors.dense(1,2,3,4,5,6,7,8,9),
      Vectors.dense(5,6,7,8,9,0,8,6,7),
      Vectors.dense(9,0,8,7,1,4,3,2,1),
      Vectors.dense(6,4,2,1,3,4,2,1,5),
      Vectors.dense(4,5,7,1,4,0,2,1,8))
    val dataRDD = sc.parallelize(data,2)
    val mat: RowMatrix = new RowMatrix(dataRDD)
    val pc: Matrix = mat.computePrincipalComponents(3)
    print(pc)
}

}

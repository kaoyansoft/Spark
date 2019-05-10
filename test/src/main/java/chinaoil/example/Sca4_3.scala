package chinaoil.example

import org.apache.spark.ml.feature.{CountVectorizer,CountVectorizerModel}
import org.apache.spark.sql.SparkSession

object Sca4_3 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Spark SQL basic example")
      .master("local[2]")
      .getOrCreate()

    val df = spark.createDataFrame(Seq(
      (0,Array("a","b","c")),
      (1,Array("a","b","b","c","a"))
    )).toDF("id","words")
    //每行text都是Array [String]类型的文档
    df.show(5)
    //从语料库中拟合CountVectorizerModel
    val cvModel:CountVectorizerModel=new CountVectorizer()
      .setInputCol("words")
      .setOutputCol("features")
      .setVocabSize(3)
      .setMinDF(2)
      .fit(df)
    //调用fit，CountVectorizer产生CountVectorizerModel含词汇（a，b，c）
    //或者，用先验词汇表定义CountVectorizerModel,已有词典才可用
    val cvm =new CountVectorizerModel(Array("a","b","c"))
      .setInputCol("words")
      .setOutputCol("features")
    //每个向量代表文档的词汇表中每个词语出现的次数。
    cvModel.transform(df).show(false)
    cvm.transform(df).show(false)
    spark.stop()
  }
}

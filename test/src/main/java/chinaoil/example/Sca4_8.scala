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
    val rawDataFrame=spark.read.format("libsvm").load("file:///test/dataset/sample_libsvm_data.txt")
    val scaledDataFrame=new StandardScaler()
      .setInputCol("features")
      .setOutputCol("scaledFeatures")
      .setWithMean(false)//对于稀疏数据（如本次使用的数据），不要使用平均值
      .setWithStd(true)
      .fit(rawDataFrame)
      .transform(rawDataFrame)
    //PCA Model
    val pcaModel=new PCA().setInputCol("scaledFeatures")
      .setOutputCol("pcaFeatures")
      .setK(3)//
      .fit(scaledDataFrame)
    //进行PCA降维
    pcaModel.transform(scaledDataFrame).select("label","pcaFeatures").show(10,false)
    //没有标准化特征向量，直接进行PCA主成分：各主成分之间值变化太大，有数量级的差别。
    //标准化特征向量后PCA主成分，各主成分之间值基本上在同一水平上，结果更合理
    //如何选择k值？
    val  pcaModel1=new PCA().setInputCol("scaledFeatures")
      .setOutputCol("pcaFeatures")
      .setK(50)//
      .fit(scaledDataFrame)
    var i=1
    for( x<-pcaModel1.explainedVariance.toArray){
      println(i+"\t"+x+"  ")
      i +=1
    }
    //运行结果（前10行），随着k的增加，精度趋于平稳。
    spark.stop()
  }
}

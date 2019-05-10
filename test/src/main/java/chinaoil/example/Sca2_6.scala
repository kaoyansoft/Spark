package chinaoil.example

import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.classification.LogisticRegressionModel
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.mllib.evaluation.RegressionMetrics


object Sca2_6 {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .appName("MySparkApp")
      .master("local[2]")
      .getOrCreate()
//    import sp
    val path="D:\\soft\\spark-2.4.2-bin-hadoop2.6\\data\\mllib\\sample_libsvm_data.txt"
    val data=spark.read.format("libsvm").load(path)
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3),1234L)
    val lr = new LogisticRegression().setThreshold(0.6).setMaxIter(10).setRegParam(0.3).setElasticNetParam(0.8)
    val lrModel = lr.fit(trainingData)
    val predictions = lrModel.transform(testData)
    predictions.show()
    val evaluator = new BinaryClassificationEvaluator().setLabelCol("label")
    val accuracy = evaluator.evaluate(predictions)
    val rm2 = new RegressionMetrics(predictions.select("prediction", "label")
      .rdd.map(x =>(x(0).asInstanceOf[Double], x(1).asInstanceOf[Double])))
    println("MSE: " + rm2.meanSquaredError)
    println("MAE: " + rm2.meanAbsoluteError)
    println("RMSE Squared: " + rm2.rootMeanSquaredError)
    //将其作为多分类结果进行评估，可计算F1、准确率、召回率、正确率
    val multiclassClassificationEvaluator = new MulticlassClassificationEvaluator()
    def printlnMetric(metricName: String): Unit = {
      println(metricName + " = " + multiclassClassificationEvaluator.setMetricName(metricName).evaluate(predictions))
    }
    printlnMetric("f1")//f1 = 0.9646258503401359
    printlnMetric("weightedPrecision")//weightedPrecision = 0.9675324675324675
    printlnMetric("weightedRecall")//weightedRecall = 0.9642857142857142
    printlnMetric("accuracy")//accuracy = 0.9642857142857143

    //将其作为二分类结果进行评估，可计算areaUnderROC、areaUnderPR
    val binaryClassificationEvaluator = new BinaryClassificationEvaluator()
    def printlnMetric1(metricName: String): Unit = {
      println(metricName + " = " + binaryClassificationEvaluator.setMetricName(metricName).evaluate(predictions))
    }

    printlnMetric1("areaUnderROC") //结果为areaUnderROC = 0.9944444444444444
    printlnMetric1("areaUnderPR")//结果为areaUnderPR = 0.9969948018193632
    //分类正确且分类为1的样本数量 TP 是17
  import spark.implicits._
    predictions.filter($"label" === $"prediction").filter($"label"===1).count
    //分类正确且分类为0的样本数量 TN 是10
    predictions.filter($"label" === $"prediction").filter($"label"===0).count
    //分类错误且分类为0的样本数量 FN是1
    predictions.filter($"label" !== $"prediction").filter($"prediction"===0).count
    //分类错误且分类为1的样本数量 FP是0
    predictions.filter($"label" !== $"prediction").filter($"prediction"===1).count
    spark.stop()
  }

}
package chinaoil.example
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.linalg.{Vector, Vectors}
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.sql.{Row, SparkSession}

object Sca3_2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Spark SQL basic example")
      .master("local[2]")
      .getOrCreate()

    //从（标识、特征）元组开始训练数据.
    val training = spark.createDataFrame(Seq(
      (1.0, Vectors.dense(0.0, 1.1, 0.1)),
      (0.0, Vectors.dense(2.0, 1.0, -1.0)),
      (0.0, Vectors.dense(2.0, 1.3, 1.0)),
      (1.0, Vectors.dense(0.0, 1.2, -0.5))
    )).toDF("label", "features")

    //创建一个LogisticRegression实例。 这个实例是一个估计器.
    val lr = new LogisticRegression()
    //打印参数，文档和任何默认值.
    println("LogisticRegression parameters:\n" + lr.explainParams() + "\n")

    //我们可以使用setter方法来设置参数.
    lr.setMaxIter(10)
      .setRegParam(0.01)

    //训练LogisticRegression模型，这里使用了存储在lr中的参数。.
    val model1 = lr.fit(training)
    //由于模型1是模型(即由估计器生成的转换器),
    //我们可以查看它在fit（）中使用的参数。
    //打印参数（名称：值）对，其中名称是唯一的ID,
    // LogisticRegression实例。
    println("Model 1 was fit using parameters: " + model1.parent.extractParamMap)

    //我们可以用ParamMap来指定参数,
    //它支持几种指定参数的方法。
    val paramMap = ParamMap(lr.maxIter -> 20)
      .put(lr.maxIter, 30)  //指定1个参数。 这会覆盖原来的maxIter。
      .put(lr.regParam -> 0.1, lr.threshold -> 0.55)  // 指定多个参数。

    //也可以组合ParamMaps.
    val paramMap2 = ParamMap(lr.probabilityCol -> "myProbability")  // 修改输出列名
    val paramMapCombined = paramMap ++ paramMap2

    //现在使用paramMapCombined参数学习一个新的模型。
    // paramMapCombined覆盖之前通过lr.set *方法设置的所有参数。
    val model2 = lr.fit(training, paramMapCombined)
    println("Model 2 was fit using parameters: " + model2.parent.extractParamMap)

    // 准备测试数据
    val test = spark.createDataFrame(Seq(
      (1.0, Vectors.dense(-1.0, 1.5, 1.3)),
      (0.0, Vectors.dense(3.0, 2.0, -0.1)),
      (1.0, Vectors.dense(0.0, 2.2, -1.5))
    )).toDF("label", "features")

    //使用Transformer.transform（）方法对测试数据进行预测
    // LogisticRegression.transform将仅使用“特征”列
    //请注意，model2.transform（）输出一个“myProbability”列，而不是通常的。
    // 我们先前通过lr.probabilityCol参数重新命名了'probability'列
    model2.transform(test)
      .select("features", "label", "myProbability", "prediction")
      .collect()
      .foreach { case Row(features: Vector, label: Double, prob: Vector, prediction: Double) =>
        println(s"($features, $label) -> prob=$prob, prediction=$prediction")
      }
    spark.stop()
  }
}
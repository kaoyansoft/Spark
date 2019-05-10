# -*- coding: utf-8 -*-
from pyspark.sql import SparkSession
from pyspark.sql import Row
import matplotlib.pyplot as plt
import numpy as np
from pyspark.sql.functions import *
from pyspark.mllib.linalg import Matrix
from pyspark.mllib.linalg.distributed import SingularValueDecomposition
from pyspark.mllib.linalg import Vector
from pyspark.mllib.linalg import Vectors
from pyspark.mllib.linalg.distributed import RowMatrix

#SVD分解后：
spark = SparkSession.builder \
    .appName("sparkDemo") \
    .master("local[4]") \
    .getOrCreate()
sc = spark.sparkContext
# val data = Array( Vectors.dense(1,2,3,4,5,6,7,8,9),
#                   Vectors.dense(5,6,7,8,9,0,8,6,7),
#                   Vectors.dense(9,0,8,7,1,4,3,2,1),
#                   Vectors.dense(6,4,2,1,3,4,2,1,5),
#                   Vectors.dense(4,5,7,1,4,0,2,1,8))
# val dataRDD = sc.parallelize(data, 2)
# val mat: RowMatrix = new RowMatrix(dataRDD)
# val svd = mat.computeSVD(3, computeU = true)
# val U: RowMatrix = svd.U #左奇异矩阵
# val s: Vector = svd.s
# val V: Matrix = svd.V #右奇异矩阵

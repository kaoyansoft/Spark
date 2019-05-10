__author__ = 'hk'
from pandas import DataFrame
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
x = np.linspace(0, 10, 100)
df=DataFrame({'sin(x)':np.sin(x),'cos(x)':np.cos(x)},index=x)
df.plot()
plt.show()

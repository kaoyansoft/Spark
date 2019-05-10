# -*- coding: utf-8 -*-
__author__ = 'hk'

import numpy as np
import matplotlib.pyplot as plt

plt.rcParams['font.sans-serif']=['SimHei'] ###显示中文
plt.rcParams['axes.unicode_minus']=False ##防止坐标轴上的-号变为方块
x = np.linspace(0, 10, 100)
y = np.sin(x)
y1 = np.cos(x)
##绘制一个图，长为10，宽为6（默认值是每个单位80像素）
plt.figure(figsize=(10,6))
###在图列中自动显示$间内容
plt.plot(x,y,label="$sin(x)$",color="red",linewidth=2)
plt.plot(x,y1,"b--",label="$cos(x^2)$") ###b（blue），--线形
plt.xlabel(u"X值") ##X坐标名称，u表示unicode编码
plt.ylabel(u"Y值")
plt.title(u"三角函数图像") ##t图名称
plt.ylim(-1.2,1.2) ##y上的max、min值
plt.legend() ##显示图例
plt.savefig('fig01.png') ##保持到当前目录
plt.show()
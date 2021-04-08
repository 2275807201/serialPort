### 读取树莓派的信号
# serialPort
Java串口编程例子

#项目介绍
项目详细介绍见博文：https://blog.csdn.net/wu_boy/article/details/112062071

微信订阅号：八卦程序

------------------------------
改造别人的代码，读取树莓派的信号。
第一注意点：1.RXTXCommDriver源代码只会读取ttyS,ttySA,ttyUSB开头的设备。所以要对/dev/ttyAMA0建立软连接，如ln -s /dev/ttyAMA0 /dev/ttyS33
但是软连接每次重启后会失效
参考：https://blog.csdn.net/qq_42865331/article/details/90264722

第二点：用pi用户启动java程序没有权限读取ttyAMA0串口信息。需要以root用户运行java程序。
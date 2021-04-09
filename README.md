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

-------------------------------

傻逼了，其实完全没有必要用RXTX读取串口。linux已经屏蔽了不同接口（串口，并口，usb口）的细节，一切皆文件。所以java只需要把/dev/ttyAMA0当做普通的文件进行读取就可以了。
package cn.wu.demo.serialport;

import cn.hutool.core.util.HexUtil;
import cn.wu.demo.serialport.util.SerialPortUtils;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 串口测试程序
 * @author wusq
 * @date 2021/1/1
 */
public class SerialPortTest {

    private static Logger log = LoggerFactory.getLogger(SerialPortTest.class);

    static volatile boolean shutdownFlag = false;

    public static void main(String[] args) throws Exception{

        log.info("程序开启运行");

        // 显示端口标识符列表
        showPortNameList();

        // 电源最低电量，低于最低电量，树莓派关机
        int needPower = Integer.valueOf(args[0]);

        // 树莓派软连接设备端口
        String portName = "/dev/ttyS33";

        // 树莓派电量检测，低于需要的电量，执行关机操作
        powerCheck(portName, needPower);

        log.info("程序开启运行结束");
    }

    private static void powerCheck(String portName, int needPower ) {

        SerialPort serialPort = null;
        try {
            // 打开串口
            serialPort= SerialPortUtils.open(portName, 9600, SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            final SerialPort sp = serialPort;

            // 监听串口读取数据
            SerialPortUtils.addListener(serialPort, () -> {

                // 读取串口消息
                byte[] data = SerialPortUtils.read(sp);
                String message = new String(data);
                log.info("接收的到的字符串：{}", message);

                // 根据消息来判断是否需要关机
                messageHandle(needPower, message);
            });

            // 休息5s
            Thread.sleep(10 * 1000);
        }catch (Exception e){
            log.error("捕获到异常", e);
        }finally {
            serialPort.close();
        }
    }

    /**
     * 消息处理
     * @param needPower
     * @param message
     */
    private static void messageHandle(int needPower, String message) {

        // 电量信号关键字
        int batcap = message.indexOf("BATCAP");
        if(batcap != -1){

            // 截取字符串
            String substring = message.substring(batcap + 6);

            int i = substring.indexOf(",");
            if(i != -1){

                // 截取出电量
                String power = substring.substring(0, i);

                // 转换为电量字符串
                int powerInt = Integer.parseInt(power.trim());

                // 检查是否需要关机
                checkShutdown(needPower, powerInt);

            }
        }
    }

    /**
     * 检查是否需要关机
     * @param needPower
     * @param powerInt
     */
    private static void checkShutdown(int needPower, int powerInt) {

        if( !shutdownFlag && needPower > powerInt){
            log.info("当前电量：{},最低电量：{}", powerInt, needPower);

            // 执行关机操作
            doShutdown();
        }
    }

    private static void doShutdown() {
        try {
            Runtime.getRuntime().exec("sudo sync");
            Thread.sleep(1000);
            log.info("电源开始关机");
            Runtime.getRuntime().exec("sudo shutdown now -h");
        } catch (Exception e) {
            log.error("关机失败", e);
        }
    }

    /**
     * 显示所有端口名称
     */
    private static void showPortNameList() {

        Enumeration portIdentifiers = CommPortIdentifier.getPortIdentifiers();

        while (portIdentifiers.hasMoreElements()){
            CommPortIdentifier o = (CommPortIdentifier) portIdentifiers.nextElement();
            log.info("端口标识符名称：{}, 拥有者：{}, 类型：{}", o.getName(), o.getCurrentOwner(), o.getPortType());
        }

    }
}

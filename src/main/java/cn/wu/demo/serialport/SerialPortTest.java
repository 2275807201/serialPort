package cn.wu.demo.serialport;

import cn.hutool.core.util.HexUtil;
import cn.wu.demo.serialport.util.SerialPortUtils;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
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

    public static void main(String[] args) throws Exception{

//        log.info("开始调试");
//        System.in.read();

        // 显示端口标识符列表
        showPortNameList();

        System.out.println("program start1111");

        String portName = "/dev/ttyS33";
        int baudRate = 9600;

        for (int i = 0; i < 3; i++) {

            readPi(portName, baudRate);

//            // 休息5s
//            Thread.sleep(5000);

            log.info("休息结束");
        }


        log.info("main 程序结束");


//        log.info("开始发送数据11");
//        // 往串口发送数据
//        byte[] data = {1, 2, 3};
//        SerialPortUtils.write(serialPort, data);
//        log.info("发送数据结束22");

        /*// 关闭串口
        Thread.sleep(2000);
        SerialPortUtils.close(serialPort);*/

//        // 测试可用端口
//        List<String> strings = SerialPortUtils.listPortName();
//        log.info("列出所有可以打印的端口名：{}", strings);
//        strings.forEach(o -> System.out.println(o));
//
//        log.info("列出打印名结束333");
    }

    private static void readPi(String portName, int baudRate) {
        SerialPort serialPort = null;
        try {
            // 打开串口
            serialPort= SerialPortUtils.open(portName, baudRate, SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            final SerialPort sp = serialPort;

            // 监听串口读取数据
            SerialPortUtils.addListener(serialPort, () -> {
                byte[] data = SerialPortUtils.read(sp);
                log.info("接收的到的字符串：{}", new String(data));
//            System.out.println(HexUtil.encodeHexStr(data));
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

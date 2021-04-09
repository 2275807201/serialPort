package cn.wu.demo.serialport;

import com.pi4j.io.gpio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 硬件测试
 */
public class HardwareTest {

	private static Logger log = LoggerFactory.getLogger(HardwareTest.class);

	public static void main(String[] args) {

		HardwareTest test = new HardwareTest();

		// 执行测试
		try {
			test.controlFan();
		} catch (Exception e) {
			log.error("测试出错", e);
		}
	}

	/**
	 * 控制风扇
	 */
	private void controlFan()  {

		// 创建一个GPIO控制器
		final GpioController gpio = GpioFactory.getInstance();

		// 获取1号GPIO针脚并设置高电平状态，对应的是树莓派上的12号针脚，可以参考pi4j提供的图片。
		final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "fan", PinState.LOW);

		while(true) {

			//设置高电平
			pin.high();
			log.info("打开风扇");
			sleep(1000);

			//设置低电平
			pin.low();
			System.out.println("关闭风扇");
			sleep(1000);
		}
	}

	private void sleep(long t)  {

		try {
			Thread.sleep(t);
		}catch (Exception e){
			log.error("睡眠出错", e);
		}

	}

}

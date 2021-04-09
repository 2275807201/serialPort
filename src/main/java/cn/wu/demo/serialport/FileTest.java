package cn.wu.demo.serialport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class FileTest {

	private static Logger log = LoggerFactory.getLogger(FileTest.class);

	public static void main(String[] args) {

		log.info("程序执行开始,文件流读取电量");

		// 从电池读取电量
		readFromBattery();

//		readWriteTest();

		log.info("程序执行结束");
	}

	private static void readWriteTest() {

		String filePath = "/dev/ttyAMA0";

		File file = new File(filePath);

		log.info("开始写文件222");

		// 读字符串
//		readString(file);

		// 写字符串
		writeString(file);

		// 读字符串
//		readString(file);
	}

	private static void readString(File file) {
		try(FileInputStream fis = new FileInputStream(file)){

				byte [] bytes = new byte[128];

//				while (true){

					int length = fis.read(bytes);

					if(length != -1){
//						String msg = new String(bytes,0,length,StandardCharsets.UTF_8);

						log.info("读取到文件内容：{}", Arrays.toString(bytes));
					}else {
						log.info("文件读取结束");
//						break;
					}

//				}
		}catch (Exception e){
			log.error("文件流出错", e);
		}
	}

	private static void writeString(File file) {
		try(FileOutputStream fos = new FileOutputStream(file)){

			log.info("开始写字符串");
			byte [] bytes = {1,1,1,0,0,0,1,1,0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0};

			// 写10次
//			for (int i = 0; i < 10; i++) {
				fos.write( bytes);
//				log.info("写完第：{}个字符串了", i);
//			}

			log.info("写字符串结束");
		}catch (Exception e){
			log.error("写文件出错", e);
		}
	}

	/**
	 * 从电池读取电量
	 */
	private static void readFromBattery() {
		for (int i = 0; i < 10; i++) {
			log.info("第{}次读取文件", i);

			// 读取dev文件
			readDevFile();
		}
	}

	/**
	 * 读取dev文件
	 */
	private static void readDevFile() {
		String filePath = "/dev/ttyAMA0";

		File file = new File(filePath);

		try(FileInputStream fis = new FileInputStream(file)){

			// 从文件流里面读取10次
			for (int i = 0; i < 1000; i++) {

				byte [] bytes = new byte[1024];

				int length = fis.read(bytes);

				if(length != -1){
					String msg = new String(bytes,0,length);

					log.info("索引：{},读取到文件内容：{}", i, msg);
				}else {

					log.info("文件读取结束");
					break;
				}
			}
		}catch (Exception e){
			log.error("文件流出错", e);
		}
	}


}

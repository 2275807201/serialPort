package cn.wu.demo.serialport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;

public class FileTest {

	private static Logger log = LoggerFactory.getLogger(FileTest.class);

	public static void main(String[] args) {

		log.info("程序执行开始,文件流读取电量");

		for (int i = 0; i < 10; i++) {
			log.info("第{}次读取文件", i);

			// 读取dev文件
			readDevFile();
		}

		log.info("程序执行结束");
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

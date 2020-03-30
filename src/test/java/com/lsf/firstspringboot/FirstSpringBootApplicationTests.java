package com.lsf.firstspringboot;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@SpringBootTest
class FirstSpringBootApplicationTests {



	@Test
	void contextLoads() {
		for (int i = 0; i < 100; i++) {
			new Thread(() ->{
				try {
					InputStream inputStream = new FileInputStream(new File("\u202AC:\\Users\\shaofan.li\\Desktop\\新建文本文档 (2).txt"));
					String strings = "";
					byte[] bytes = new byte[1024];
					ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
					int b = 0;
					while (b != -1){
						b = inputStream.read(bytes);
						byteArrayInputStream.write(bytes, 0 ,b);
					}
					strings = byteArrayInputStream.toString() + Thread.currentThread().getId();
					strings.replaceAll("a","b");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}

	}

}

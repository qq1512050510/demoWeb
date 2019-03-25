package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.winter.config.bean.Config;

/*@RunWith(SpringJUnit4ClassRunner.class)
// @SpringBootTest(classes=WebAppConfig.class)// 指定spring-boot的启动类
@SpringBootTest(classes = DemoWebApplication.class) // 1.4.0 前版本 注意启动类不要搞错了
*/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoWebApplication.class)
@WebAppConfiguration
@ComponentScan(basePackages={"com.winter.config.bean"})  
/*@ComponentScan(basePackages={"cn.kfit","org.kfit"})  */
public class ConfigPropertiesTest {
	@Autowired
	private Config config;

	@Test
	public void propsTest(){
		System.out.println("driverclassName: " + config.getDriverclassname());
		System.out.println("url: " + config.getUrl());
	}
}
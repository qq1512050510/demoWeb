package com.example.demo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.winter.model.User;
import com.winter.service.UserService;

/*@RunWith(SpringRunner.class)*/
@RunWith(SpringJUnit4ClassRunner.class) 
@SpringBootTest(classes = DemoWebApplication.class) 
/*
@MapperScan("com.winter.mapper")//将项目中对应的mapper类的路径加进来就可以了
@ContextConfiguration(locations = "classpath:application.yml")*/

//@SpringBootTest(classes=WebAppConfig.class)// 指定spring-boot的启动类     
public class ServiceTests {

	
	@Autowired
	private UserService userService;

	@Test
	public void contextLoads() {
		System.out.println("test");
		List<User> listUser = userService.findAllUser(1, 10);
		System.out.println(listUser.size());
	}

}

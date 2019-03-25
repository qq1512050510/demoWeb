package com.winter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.winter.mapper") // 将项目中对应的mapper类的路径加进来就可以了
public class DemoWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoWebApplication.class, args);
	}
}

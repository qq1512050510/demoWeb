package com.winter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.winter.mapper") // 将项目中对应的mapper类的路径加进来就可以了
public class DemoWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoWebApplication.class, args);
	}
	 
    /**
     * 设置匹配*.action后缀请求
     * @param dispatcherServlet
     * @return
     */
    /*@Bean
    public ServletRegistrationBean<DispatcherServlet> servletRegistrationBean(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean<DispatcherServlet> servletServletRegistrationBean = new ServletRegistrationBean<>(dispatcherServlet);
        servletServletRegistrationBean.addUrlMappings("*.action");
        return servletServletRegistrationBean;
    }*/
}

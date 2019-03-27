package com.winter.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
 
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //开启路径后缀匹配
       // configurer.setUseRegisteredSuffixPatternMatch(true);
    }
    /**
     * 注册 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestIntercepter());
    }
}

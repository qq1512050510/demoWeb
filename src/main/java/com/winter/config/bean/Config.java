package com.winter.config.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/*import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring") // 接收application.yml中的datasource下面的属性
*/
@Component
@ConfigurationProperties(prefix = "spring.datasource")
@PropertySource("classpath:/application.yml")
public class Config {
	@Value("${url}")
	public String url;
	@Value("${username}")
	public String username;
	@Value("${password}")
	public String password;
	@Value("${driver-class-name}")
	public String driverclassname;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDriverclassname() {
		return driverclassname;
	}
	public void setDriverclassname(String driverclassname) {
		this.driverclassname = driverclassname;
	}
}
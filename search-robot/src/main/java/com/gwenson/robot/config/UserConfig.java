package com.gwenson.robot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * search-robot的Controller校验参数
 * @author Gwensong  email : gwenson@163.com
 *
 */
@Configuration
@ConfigurationProperties(prefix="gwenson.robot.user")
public class UserConfig {

	private String username;
	
	private String password;

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
	
	
}



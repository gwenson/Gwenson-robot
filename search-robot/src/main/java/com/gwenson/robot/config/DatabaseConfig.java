package com.gwenson.robot.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
/**
 * 分表数配置的参数
 * @author Gwensong  email : gwenson@163.com
 *
 */
@Configuration
@ConfigurationProperties(prefix="database.table")
public class DatabaseConfig {
	/**
	 * 表总张数
	 */
	@NotNull
	private int num;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	
}

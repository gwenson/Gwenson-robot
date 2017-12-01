package com.gwenson.robot.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
/**
 * 代理ip端口的配置参数
 * @author Gwensong  email : gwenson@163.com
 *
 */
@Configuration
@ConfigurationProperties(prefix="gwensong.robot.property")
public class PropertyConfig {

	
	@NotNull
	private Boolean start;
	
	@NotNull
	private Boolean autoScheduled;
	
	private String userDefinedPath;

	/**
	 * 获取代理是否开启
	 */
	public Boolean getStart() {
		return start;
	}

	/**
	 * 设置代理是否开启
	 */
	public void setStart(Boolean start) {
		this.start = start;
	}

	/**
	 * 获取自动代理ip是否开启
	 */
	public Boolean getAutoScheduled() {
		return autoScheduled;
	}
	/**
	 * 设置自动代理ip是否开启
	 */
	public void setAutoScheduled(Boolean autoScheduled) {
		this.autoScheduled = autoScheduled;
	}

	/**
	 * 获取自定义代理ip路径
	 */
	public String getUserDefinedPath() {
		return userDefinedPath;
	}
	/**
	 * 设置自定义代理ip路径
	 */
	public void setUserDefinedPath(String userDefinedPath) {
		this.userDefinedPath = userDefinedPath;
	}
	
}

package com.gwenson.robot.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 搜索优先范围参数
 * @author Gwensong  email : gwenson@163.com
 *
 */
@Configuration
@ConfigurationProperties(prefix="gwensong.robot.search")
public class SearchConfig {

	/**
	 * 范围 wide|depth
	 */
	@NotNull
	private String scope;
	/**
	 * 白名单地址
	 */
	private String whitelistPath;
	
	/**
	 * 黑名单地址
	 */
	private String blacklistPath;
	
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getWhitelistPath() {
		return whitelistPath;
	}

	public void setWhitelistPath(String whitelistPath) {
		this.whitelistPath = whitelistPath;
	}

	public String getBlacklistPath() {
		return blacklistPath;
	}

	public void setBlacklistPath(String blacklistPath) {
		this.blacklistPath = blacklistPath;
	}
	
	
	
}

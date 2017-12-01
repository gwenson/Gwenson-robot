package com.gwenson.robot.proterty.model;
/**
 * 代理端口和ip
 *  @author gwenson email : gwenson@163.com
 *
 */
public class Property {

	private String ip;
	
	private Integer port;

	public Property() {
	}
	
	public Property(String ip, Integer port) {
		this.ip = ip;
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "Property [ip=" + ip + ", port=" + port + "]";
	}

	
	
}

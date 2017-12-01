package com.gwenson.robot.utils;

public enum ScopeEnum {
	/**
	 * 宽度
	 */
	WIDE("wide"),
	/**
	 * 深度
	 */
	DEPTH("depth");
	
	private String scope;
	
	ScopeEnum(String scope){
		this.scope=scope;
	}
	
	public String getScope() {
		return scope;
	}
	
}

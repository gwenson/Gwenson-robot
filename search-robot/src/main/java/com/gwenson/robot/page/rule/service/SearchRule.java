package com.gwenson.robot.page.rule.service;

public interface SearchRule {

	/**
	 * 判断url是否在黑白名单内
	 * @param url
	 * @return
	 */
	public boolean isContainsUrl( final String url);
	
	/**
	 * 判断是否在黑名单内，是则清空深度队列
	 * @param url
	 * @return
	 */
	public boolean isClearDepthQueues(final String url);
	
}

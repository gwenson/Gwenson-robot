package com.gwenson.robot.page.service;

/**
 * 调度任务服务
 * @author gwenson email : gwenson@163.com
 *
 */
public interface DispatchTaskService {

	/**
	 * 开始搜索文章
	 */
	public void dispatchStart();
	/**
	 * 宽度搜索
	 */
	public void wideSearch();
	/**
	 * 深度优先搜索
	 */
	public void depthSearch();
}

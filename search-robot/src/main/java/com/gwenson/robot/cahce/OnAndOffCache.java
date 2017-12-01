package com.gwenson.robot.cahce;

/**
 * 循序里开关条件
 * @author Gwenson
 *
 */
public class OnAndOffCache {

	/**
	 * 搜索循环里开关条件   
	 * START:TRUE
	 * SHUTDOWN:FALSE
	 */
	private volatile static boolean  searchStartedCache= false;

	/**
	 * 搜索循环里开关条件   
	 * START:TRUE
	 * SHUTDOWN:FALSE
	 * @return
	 */
	public static boolean isSearchStartedCache() {
		return searchStartedCache;
	}

	public synchronized static void setSearchStartedCache(boolean searchStartedCache) {
		OnAndOffCache.searchStartedCache = searchStartedCache;
	}

	
}

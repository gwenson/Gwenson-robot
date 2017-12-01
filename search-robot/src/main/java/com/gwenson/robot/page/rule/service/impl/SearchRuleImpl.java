package com.gwenson.robot.page.rule.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwenson.common.dao.redis.IdenticalUrlDao;
import com.gwenson.robot.page.redis.dao.DepthLockDao;
import com.gwenson.robot.page.redis.dao.DepthQueueUrlDao;
import com.gwenson.robot.page.rule.dao.BlacklistDao;
import com.gwenson.robot.page.rule.dao.WhitelistDao;
import com.gwenson.robot.page.rule.service.SearchRule;

@Service("searchRule")
public class SearchRuleImpl implements SearchRule{

	private static Logger log=LoggerFactory.getLogger(SearchRuleImpl.class);
	
	@Autowired
	private IdenticalUrlDao identicalUrlDao;
	@Autowired
	private BlacklistDao blacklistDao;
	@Autowired
	private WhitelistDao whitelistDao;
	@Autowired
	private DepthQueueUrlDao depthQueueUrlDao;
	@Autowired
	private DepthLockDao depthLockDao;
	@Override
	public boolean isContainsUrl(final String url) {
		log.info("########判断url是否在黑白名单内#########");
		if(whitelistDao.contains(url)){
			return false;
		}
		
		if(blacklistDao.contains(url)){
			return true;
		}
		
		return identicalUrlDao.contains(url);
		
	}

	@Override
	public boolean isClearDepthQueues(String url) {
		log.info("############开是判断是否在黑名单内，是则清空深度队列#################");
		if(blacklistDao.contains(url)){
			depthQueueUrlDao.clear();
			depthLockDao.deleteLock();
			return true;
		}
		return false;
	}

	
	
}

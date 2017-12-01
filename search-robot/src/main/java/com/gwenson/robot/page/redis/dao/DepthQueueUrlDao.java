package com.gwenson.robot.page.redis.dao;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.gwenson.common.dao.redis.IdenticalUrlDao;
import com.gwenson.robot.page.service.impl.DispatchTaskServiceImpl;

/**
 * 深度优先搜索队列url
 * @author gwenson email : gwenson@163.com
 *
 */
@Component
public class DepthQueueUrlDao {
	private static Logger log=LoggerFactory.getLogger(DispatchTaskServiceImpl.class); 
	private final static String DEPTH_QUEUE_URL_REDIS_KEY="DEPTH_QUEUE_URL_REDIS_KEY";
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private IdenticalUrlDao identicalUrlDao;
	
	/**
	 * 推送url到待执行的队列中
	 * @param url
	 */
	public void pushQueue(String url){
		log.debug("########开始推送url到待执行的队列中#########");
		BoundListOperations<String,String> listOps = redisTemplate.boundListOps(DEPTH_QUEUE_URL_REDIS_KEY);
		boolean contains = identicalUrlDao.contains(url);
		if(!contains){
			boolean contains2 = false;
			if(listOps.size()>0){
				List<String> range = listOps.range(0, listOps.size());
				contains2 = range.contains(url);
			}
			if(!contains2)
				listOps.leftPush(url);
		}
	}
	
	/**
	 * 推送url到待执行的队列中
	 * @param url
	 */
	public void pushQueue(Set<String> urls){
		BoundListOperations<String,String> listOps = redisTemplate.boundListOps(DEPTH_QUEUE_URL_REDIS_KEY);
		for(String url:urls){
			boolean contains = identicalUrlDao.contains(url);
			if(!contains){
				boolean contains2 = false;
				if(listOps.size()>0){
					List<String> range = listOps.range(0, listOps.size());
					contains2 = range.contains(url);
				}
				if(!contains2)
					listOps.leftPush(url);
			}
		}
			
	}
	
	
	/**
	 * 把队列中的最后一个url弹出(阻塞)
	 * @return
	 */
	public String brPopQueue(){
		log.debug("###把队列中的最后一个url弹出(阻塞)######");
		BoundListOperations<String,String> listOps = redisTemplate.boundListOps(DEPTH_QUEUE_URL_REDIS_KEY);
		String rightPop = listOps.rightPop(0, TimeUnit.SECONDS);
		return rightPop;
	}
	/**
	 * 把队列中的最后一个url弹出(非阻塞)
	 * @return
	 */
	public String popQueue(){
		BoundListOperations<String,String> listOps = redisTemplate.boundListOps(DEPTH_QUEUE_URL_REDIS_KEY);
		String rightPop = listOps.rightPop();
		return rightPop;
	}
	
	public Long size(){
		BoundListOperations<String,String> listOps = redisTemplate.boundListOps(DEPTH_QUEUE_URL_REDIS_KEY);
		return listOps.size();
	}
	
	/**
	 * 清空队列
	 * @return
	 */
	public boolean clear(){
		Boolean hasKey = redisTemplate.hasKey(DEPTH_QUEUE_URL_REDIS_KEY);
		if(hasKey){
			redisTemplate.delete(DEPTH_QUEUE_URL_REDIS_KEY);
		}
		return true;
	}
	
}

package com.gwenson.robot.page.redis.dao;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.gwenson.common.dao.redis.IdenticalUrlDao;
import com.gwenson.common.utils.StaticUrl;

/**
 * 广度搜索队列url
 * @author gwenson email : gwenson@163.com
 *
 */
@Component
public class WideQueueUrlDao {

	private final static String QUEUE_URL_REDIS_KEY="QUEUE_URL_REDIS_KEY";
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private IdenticalUrlDao identicalUrlDao;
	
	/**
	 * 推送url到待执行的队列中
	 * @param url
	 */
	public void pushQueue(String url){
		BoundListOperations<String,String> listOps = redisTemplate.boundListOps(QUEUE_URL_REDIS_KEY);
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
		BoundListOperations<String,String> listOps = redisTemplate.boundListOps(QUEUE_URL_REDIS_KEY);
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
	 * 把队列中的最后一个url弹出(阻塞),广度不会有空值
	 * @return
	 */
	public String brPopQueue(){
		BoundListOperations<String,String> listOps = redisTemplate.boundListOps(QUEUE_URL_REDIS_KEY);
		if(listOps.size()==0){
			this.pushQueue(StaticUrl.START_URL);
			this.pushQueue(StaticUrl.START_URL2);
			this.pushQueue(StaticUrl.START_URL3);
			this.pushQueue(StaticUrl.START_URL4);
		}
		String rightPop = listOps.rightPop(0, TimeUnit.SECONDS);
		return rightPop;
	}
	/**
	 * 把队列中的最后一个url弹出(非阻塞),广度不会有空值
	 * @return
	 */
	public String popQueue(){
		BoundListOperations<String,String> listOps = redisTemplate.boundListOps(QUEUE_URL_REDIS_KEY);
		if(listOps.size()==0){
			this.pushQueue(StaticUrl.START_URL);
			this.pushQueue(StaticUrl.START_URL2);
			this.pushQueue(StaticUrl.START_URL3);
			this.pushQueue(StaticUrl.START_URL4);
		}
		String rightPop = listOps.rightPop();
		return rightPop;
	}
	
	public Long size(){
		BoundListOperations<String,String> listOps = redisTemplate.boundListOps(QUEUE_URL_REDIS_KEY);
		return listOps.size();
	}
	
	public void delete(){
		redisTemplate.delete(QUEUE_URL_REDIS_KEY);
	}
}

package com.gwenson.common.dao.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.JSON;
import com.gwenson.common.model.SearchBlog;

/**
 * SearchBlog 对象队列
 * @author gwenson
 *
 */
public class QueueSearchBlogDao {
	private static final String QUEUE_SEARCHBLOG_REDIS_KEY="QUEUE_SEARCHBLOG_REDIS_KEY";
	
	private StringRedisTemplate redisTemplate;
	
	public QueueSearchBlogDao(StringRedisTemplate redisTemplate){
		this.redisTemplate=redisTemplate;
	}
	
	
	/**
	 * 推送SearchBlog到待执行的队列中
	 * @param SearchBlog
	 */
	public void pushQueue(SearchBlog searchBlog){
		BoundListOperations<String,String> listOps = redisTemplate.boundListOps(QUEUE_SEARCHBLOG_REDIS_KEY);
		listOps.leftPush(JSON.toJSONString(searchBlog));
	}
	
	/**
	 * 把队列中的最后一个SearchBlog弹出(阻塞)
	 * @return
	 */
	public SearchBlog brPopQueue(){
		BoundListOperations<String,String> listOps = redisTemplate.boundListOps(QUEUE_SEARCHBLOG_REDIS_KEY);
		String rightPop = listOps.rightPop(0, TimeUnit.SECONDS);
		SearchBlog searchBlog = JSON.parseObject(rightPop, SearchBlog.class);
		return searchBlog;
	}
	
}

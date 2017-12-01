package com.gwenson.robot.page.redis.dao;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

/**
 * 深度优先搜索redis的分布式锁
 * @author Gwensong  email : gwenson@163.com
 *
 */
@Component
public class DepthLockDao {

	/**
	 * 深度优先搜索锁的key
	 */
	private final static String DEPTH_REDIS_LOCK="DEPTH_REDIS_LOCK";
	/**
	 * 过期时间
	 */
	private final static long TIME_OUT=10;
	/**
	 * 过期时间key
	 */
	private final static String DEPTH_REDIS_LOCK_TIMEOUT_KEY="DEPTH_REDIS_LOCK_TIMEOUT";
	/**
	 * 过期时间value
	 */
	private final static String DEPTH_REDIS_LOCK_TIMEOUT_VALUE="DEPTH_REDIS_LOCK_TIMEOUT_VALUE";
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	/**
	 * 判断当前锁;
	 * true存在，刷新失效时间;
	 * false不存在
	 * @return
	 */
	public boolean isLock(){
		BoundValueOperations<String, String> valueOps = redisTemplate.boundValueOps(DEPTH_REDIS_LOCK);
		String string = valueOps.get();
		BoundValueOperations<String,String> valueOps2 = redisTemplate.boundValueOps(DEPTH_REDIS_LOCK_TIMEOUT_KEY);
		Long expire = valueOps2.getExpire();
		if(string!=null&&!"".equals(string)&&expire>0){
			valueOps2.expire(TIME_OUT, TimeUnit.SECONDS);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 设置当前锁
	 * @return
	 */
	public boolean setLock(final String domain){
		
		redisTemplate.execute(new RedisCallback<Boolean>() {

			@SuppressWarnings("unchecked")
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				
				RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
				RedisSerializer<String> valueSerializer = (RedisSerializer<String>) redisTemplate.getValueSerializer();
				
				byte[] rawKey = stringSerializer.serialize(DEPTH_REDIS_LOCK);
				byte[] rawValue = valueSerializer.serialize(domain);
				connection.set(rawKey, rawValue);
				
				byte[] timeOutRawKey = stringSerializer.serialize(DEPTH_REDIS_LOCK_TIMEOUT_KEY);
				byte[] timeOutRawValue = valueSerializer.serialize(DEPTH_REDIS_LOCK_TIMEOUT_VALUE);
				connection.set(timeOutRawKey, timeOutRawValue);
				
				final long rawTimeout = TimeoutUtils.toMillis(TIME_OUT, TimeUnit.SECONDS);
				try {
					connection.pExpire(timeOutRawKey, rawTimeout);
				} catch (Exception e) {
					// Driver may not support pExpire or we may be running on Redis 2.4
					connection.expire(timeOutRawKey, TimeoutUtils.toSeconds(TIME_OUT, TimeUnit.SECONDS));
				}
				return true;
			}
		});
		
		return true;
	}
	
	/**
	 * 清空当前锁
	 * @return
	 */
	public boolean deleteLock(){
		redisTemplate.delete(DEPTH_REDIS_LOCK_TIMEOUT_KEY);
		return true;
	}
	
	/**
	 * 获取锁的值
	 * @return
	 */
	public String getLockDomainUrl(){
		return redisTemplate.opsForValue().get(DEPTH_REDIS_LOCK);
		
	}
}

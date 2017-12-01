package com.gwenson.common.dao.redis;

import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationUtils;

import com.gwenson.common.utils.MD5;
import com.gwenson.common.utils.NumberUtil;

/**
 * 去重url
 * @author gwenson email : gwenson@163.com
 *
 */
public class IdenticalUrlDao {
	private static final String ITERATE_URL_REDIS_KEY="ITERATE_URL_REDIS_KEY";
	
	
	private StringRedisTemplate redisTemplate;
	
	public IdenticalUrlDao(StringRedisTemplate redisTemplate){
		this.redisTemplate=redisTemplate;
	}
	
	/**
	 * 保存去重url 线程安全
	 * @param url
	 * @return
	 */
	public long set(final String url){
		return redisTemplate.execute(new RedisCallback<Long>() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				String str = MD5.toStr(url);
				RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
				int key2 = NumberUtil.getStringModBitCount(str, NumberUtil.BIT_COUT);
				byte[] key = redisTemplate.getStringSerializer().serialize(ITERATE_URL_REDIS_KEY+key2);
				byte[] v = valueSerializer.serialize(str);
				return connection.sAdd(key, v);
			}
		}, true);
		
	}
	
	/**
	 * 判断url是否存在线程安全
	 * @param url
	 * @return
	 */
	public boolean contains(final String url){
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				String str = MD5.toStr(url);
				RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
				int key2 = NumberUtil.getStringModBitCount(str, NumberUtil.BIT_COUT);
				byte[] key = redisTemplate.getStringSerializer().serialize(ITERATE_URL_REDIS_KEY+key2);
				if(connection.exists(key)){
					 Set<String> set = SerializationUtils.deserialize(connection.sMembers(key), valueSerializer);
					 if(set!=null){
						 return set.contains(str);
					 }
				}
				return false;
			}
		},true);
	}

	
	
}

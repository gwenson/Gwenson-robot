package com.gwenson.robot.proterty.redis.dao;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.gwenson.robot.proterty.model.Property;
/**
 * 端口代理ip
 * @author gwenson email : gwenson@163.com
 *
 */
@Component
public class PropertyRedisDao {

	private static final String PROPERTY_REDIS_KEY="PROPERTY_REDIS_KEY";
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	public void set(List<Property> propertys) {
		redisTemplate.opsForValue().set(PROPERTY_REDIS_KEY, JSON.toJSONString(propertys));
	}
	
	public boolean expire(Date outTime) {
		return redisTemplate.expire(PROPERTY_REDIS_KEY, outTime.getTime(),TimeUnit.SECONDS);
	}
	
	public void setAndExpire(List<Property> propertys, Date outTime) {
		redisTemplate.opsForValue().set(PROPERTY_REDIS_KEY, JSON.toJSONString(propertys));
		this.expire(outTime); 
	}
	
	public long ttl() {
		Long expire = redisTemplate.getExpire(PROPERTY_REDIS_KEY);
		return expire;
	}
	
	public String getValue() {
		return (String) redisTemplate.opsForValue().get(PROPERTY_REDIS_KEY);
	}
	
	public List<Property> getPropertys() {
		String value = this.getValue();
		if(value!=null&&!"".equals(value)){
			List<Property> propertys = (List<Property>) JSON.parseArray(value, Property.class);
			return propertys;
		}
		return null;
	}
	
	

	
}

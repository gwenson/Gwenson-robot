package com.gwenson.common.dao.redis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.alibaba.fastjson.JSON;
import com.gwenson.common.utils.NumberUtil;
import com.gwenson.common.utils.SimHash;
/**
 * 全文去重
 * @author gwenson
 *
 */
public class IdenticTextDao {
	
	private static final String ITERATE_TEXT_1_REDIS_KEY="ITERATE_TEXT_1_REDIS_KEY";
	private static final String ITERATE_TEXT_2_REDIS_KEY="ITERATE_TEXT_2_REDIS_KEY";
	private static final String ITERATE_TEXT_3_REDIS_KEY="ITERATE_TEXT_3_REDIS_KEY";
	private static final String ITERATE_TEXT_4_REDIS_KEY="ITERATE_TEXT_4_REDIS_KEY";
	
	
	private StringRedisTemplate redisTemplate;
	
	public IdenticTextDao(StringRedisTemplate redisTemplate){
		this.redisTemplate=redisTemplate;
	}
	
	
	/**
	 * 增加一条不重复的SimHashCode（相同会被覆盖）
	 * @param url
	 * @return
	 */
	public long set(final String SimHashCode){
		
		
		//线程安全
		return redisTemplate.execute(new RedisCallback<Long>() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
				RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
				String[] subSimHashCode = SimHash.subSimHashCode(SimHashCode, 4);
				
//				byte[] v = valueSerializer.serialize(SimHashCode);
				int mod = NumberUtil.getStringModBitCount(SimHashCode, NumberUtil.BIT_COUT);
				byte[] modKey = stringSerializer.serialize(String.valueOf(mod));
				
				Long vl =0l;
				
				//储存分片simhashcode
				byte[] key = null;
				Map<String,String> map = null;
				byte[] value = null;
				
				for(int i = 0;i<subSimHashCode.length;i++){
					map = null;
					switch (i) {
					case 0:
						key = stringSerializer.serialize(ITERATE_TEXT_1_REDIS_KEY);
						break;
					case 1:
						key = stringSerializer.serialize(ITERATE_TEXT_2_REDIS_KEY);
						break;
					case 2:
						key = stringSerializer.serialize(ITERATE_TEXT_3_REDIS_KEY);
						break;
					case 3:
						key = stringSerializer.serialize(ITERATE_TEXT_4_REDIS_KEY);
						break;
					default:
						key = null;
						break;
					}
					if(null!=key){
						
						if(true){

							Boolean hExists = connection.hExists(key, modKey);
							byte[] hGet = null;
							if(hExists){
								hGet = connection.hGet(key,modKey);
							}
							if(null!=hGet){
								String jsonMap = (String) valueSerializer.deserialize(hGet);
//								log.info("set old :"+jsonMap);
								if(jsonMap!=null&&!"".equals(jsonMap)){
									map = JSON.parseObject(jsonMap, Map.class);
								}
							}
							if(map==null||map.isEmpty()){
								map = new HashMap<>();
							}
							Set list = new HashSet<>();
							if(map.containsKey(subSimHashCode[i])){
								String sub = map.get(subSimHashCode[i]);
								list = JSON.parseObject(sub, Set.class);
							}
							list.add(SimHashCode);
							map.put(subSimHashCode[i], JSON.toJSONString(list));
							value = valueSerializer.serialize(JSON.toJSONString(map));
//							log.info("set now "+JSON.toJSONString(map));
//							value = hashValueSerializer.serialize(map);
							Boolean boolean1 = connection.hSet(key, modKey, value);
							if(boolean1){
								vl = 1l;
							}
						
							
						}
					}
				}
				
				return vl;
			}
		}, true);
		
	}
	/**
	 * 判断SimHashCode是否存在
	 * @param url
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean contains(final String simHashCode){
		String[] subSimHashCode = SimHash.subSimHashCode(simHashCode, 4);
		int mod = NumberUtil.getStringModBitCount(simHashCode, NumberUtil.BIT_COUT);
		
		String key = null;
		Set<String> set =null;
		Map<String,String> map = null;
		
		for(int i = 0; i < subSimHashCode.length;i++){
			set = null;
			switch (i) {
			case 0:
				key = ITERATE_TEXT_1_REDIS_KEY;
				break;
			case 1:
				key = ITERATE_TEXT_2_REDIS_KEY;
				break;
			case 2:
				key = ITERATE_TEXT_3_REDIS_KEY;
				break;
			case 3:
				key = ITERATE_TEXT_4_REDIS_KEY;
				break;
			default:
				key = null;
				break;
			}
			
			BoundHashOperations<String,String,String> boundHashOps = redisTemplate.boundHashOps(key);
			Boolean hasKey = boundHashOps.hasKey(String.valueOf(mod));
			if(hasKey){
				String jsonMap = boundHashOps.get(String.valueOf(mod));
				if(null != jsonMap &&!"".equals(jsonMap)){
					map = JSON.parseObject(jsonMap, Map.class);
					boolean containsKey = map.containsKey(subSimHashCode[i]);
					if(containsKey){
						String jsonSet = map.get(subSimHashCode[i]);
						if(null != jsonSet &&!"".equals(jsonSet)){
							set = JSON.parseObject(jsonSet, Set.class);
							for(String simHashCode2:set){
								int distance = SimHash.getDistance(simHashCode, simHashCode2);
								if(distance<=3){
									return true;
								}
							}
						}
					}
				}
			}
		}
		
		return false;
	}
	
}

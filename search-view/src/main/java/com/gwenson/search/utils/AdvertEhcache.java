package com.gwenson.search.utils;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
/**
 * 广告代码缓存
 * @author Gwensong  email : gwenson@163.com
 *
 */
public class AdvertEhcache {
	private final static String AdvertFilter="AdvertFilter";
	public static Cache getCache(){
		CacheManager cacheManager=CacheManager.getInstance();
		Cache cache = cacheManager.getCache(AdvertFilter);
		
		return cache;
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getList(){
		Cache cache = getCache();
		Element element = cache.get(AdvertFilter);
		if(element!=null){
			 Object objectValue = element.getObjectValue();
			 if(objectValue!=null){
				 if(objectValue instanceof List){
					 List<String> list=(List<String>) objectValue;
					 return list;
				 }
			 }
		}
		return null;
	}
	
	public static void putList(List<String> list){
		Cache cache = getCache();
		cache.put(new Element(AdvertFilter, list));
	}
}

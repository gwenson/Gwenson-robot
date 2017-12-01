package com.gwenson.robot.page.rule.dao;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwenson.common.utils.PropertiesUtil;
import com.gwenson.robot.config.SearchConfig;
import com.gwenson.robot.utils.DocumentUtil;
import com.gwenson.robot.utils.WhiteAndBlackListUtil;
/**
 * 白名单
 * @author Gwensong  email : gwenson@163.com
 *
 */
@Component
public class WhitelistDao {
	private static Logger log=LoggerFactory.getLogger(WhitelistDao.class);
	
	private static volatile List<String> WHITELIST;
	
	private static volatile long oldSize=0;
	
	
	@Autowired
	private SearchConfig searchConfig;
	
	
	static{
		
		String whitelistPath = PropertiesUtil.GetValueByKey("/application.properties", "gwensong.robot.search.whitelistPath",BlacklistDao.class);
		log.info(whitelistPath);
		
		if(null != whitelistPath && !"".equals(whitelistPath)){
			
			File file = WhiteAndBlackListUtil.getFile(whitelistPath);
			WHITELIST = WhiteAndBlackListUtil.getListFile(file);
			oldSize=file.length();
			log.info("size:"+WHITELIST.size()+"---fileLength:"+oldSize);
		}
		
		
	}
	
	
	/**
	 * 获取白名单
	 * @return
	 */
	public List<String> getWhitelist(){
		String whitelistPath = searchConfig.getWhitelistPath();
		
		if(null != whitelistPath && !"".equals(whitelistPath)){
			File file = WhiteAndBlackListUtil.getFile(whitelistPath);
			if(null != file && file.exists() && file.isFile()){
				long length = file.length();
				if(length != oldSize){
					WHITELIST = WhiteAndBlackListUtil.getListFile(file);
				}
			}
		}
		return WHITELIST;
	}
	
	
	/**
	 * 判断是否
	 * @param url
	 * @return
	 */
	public boolean contains(String url){
		getWhitelist();
		if(null != WHITELIST && WHITELIST.size()>0){
			String urlWideDomain = null;
			String whiteDomain = null;
			for(String site:WHITELIST){
				urlWideDomain = DocumentUtil.getDomainWide(url);
				whiteDomain = DocumentUtil.getDomainWide(site);
				if(urlWideDomain.endsWith(whiteDomain))
					return true;
			}
		}
		return false;
	}
	
	
}

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
 * 黑名单
 * @author Gwensong  email : gwenson@163.com
 *
 */
@Component
public class BlacklistDao {

	private static Logger log=LoggerFactory.getLogger(BlacklistDao.class);
	
	private static volatile List<String> BLACKLIST;
	
	private static volatile long oldSize=0;
	

	@Autowired
	private SearchConfig searchConfig;

	
	static{
		
		String blacklistPath = PropertiesUtil.GetValueByKey("/application.properties", "gwensong.robot.search.blacklistPath",BlacklistDao.class);
		log.info(blacklistPath);
		
		if(null != blacklistPath && !"".equals(blacklistPath)){
			
			File file = WhiteAndBlackListUtil.getFile(blacklistPath);
			BLACKLIST = WhiteAndBlackListUtil.getListFile(file);
			oldSize=file.length();
			log.info("size:"+BLACKLIST.size()+"---fileLength:"+oldSize);
		}
		
	}
	
	
	
	/**
	 * 获取黑名单
	 * @return
	 */
	public List<String> getBlacklist(){
		String blacklistPath = searchConfig.getBlacklistPath();
		if(null != blacklistPath && !"".equals(blacklistPath)){
			File file = WhiteAndBlackListUtil.getFile(blacklistPath);
			if(null != file && file.exists() && file.isFile()){
				long length = file.length();
				if(length != oldSize){
					BLACKLIST = WhiteAndBlackListUtil.getListFile(file);
				}
			}
		}
		return BLACKLIST;
	}
	
	/**
	 * 判断是否
	 * @param url
	 * @return
	 */
	public boolean contains(String url){
		getBlacklist();
		if(null != BLACKLIST && BLACKLIST.size()>0){
			String urlWideDomain = null;
			String blackDomain = null;
			for(String site:BLACKLIST){
				urlWideDomain = DocumentUtil.getDomainWide(url);
				blackDomain = DocumentUtil.getDomainWide(site);
				if(urlWideDomain.endsWith(blackDomain))
					return true;
			}
		}
		return false;
	}
	
	
}

package com.gwenson.controller;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.gwenson.robot.cahce.OnAndOffCache;
import com.gwenson.robot.config.UserConfig;
/**
 * 停止任务
 * @author Gwenson
 *
 */
@RestController
@RequestMapping(value="/shutdown")
public class ShutdownController {

	private static Logger log=LoggerFactory.getLogger(ShutdownController.class); 
	
	@Autowired  
    private UserConfig userConfig;
	
	/**
	 * 停止搜索
	 * @param username
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/search")
	public byte[] search(@RequestParam String username,@RequestParam String password) throws UnsupportedEncodingException{
		
		JSONObject result = new JSONObject();
		String localUsername = userConfig.getUsername();
		String localPassword = userConfig.getPassword();
		
		if(localUsername.equals(username)&&localPassword.equals(password)){
			if(OnAndOffCache.isSearchStartedCache()){
				OnAndOffCache.setSearchStartedCache(false);
				result.put("result", true);
				result.put("msg", "SEARCH_SHUTDOWN shutdown successful ! ");
				log.info("SEARCH_SHUTDOWN shutdown successful ! ");
			}else{
				log.info("SEARCH_SHUTDOWN shutdown failed ! ");
				result.put("result", false);
				result.put("msg", "SEARCH_SHUTDOWN shutdown failed ! ");
			}
		}else{
			result.put("result", false);
			result.put("msg", "YOUR USERNAME OR PASSWORD ERROR");
		}
		return result.toString().getBytes("UTF-8");
	}
	
}

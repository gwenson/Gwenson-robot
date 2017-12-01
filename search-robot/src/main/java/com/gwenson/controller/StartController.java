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
import com.gwenson.robot.proterty.task.PropertyTask;
@RestController
@RequestMapping(value="/start")
public class StartController {

	private static Logger log=LoggerFactory.getLogger(StartController.class); 
	
	
	@Autowired  
    private UserConfig userConfig;
	@Autowired
	private PropertyTask propertyTask;
	
	/**
	 * 启动搜索
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

			if(!OnAndOffCache.isSearchStartedCache()){
				OnAndOffCache.setSearchStartedCache(true);
				result.put("result", true);
				result.put("msg", "SEARCH_STARTED start successful ! ");
				log.info("SEARCH_STARTED start successful ! ");
			}else{
				log.info("SEARCH_STARTED start failed ! ");
				result.put("result", false);
				result.put("msg", "SEARCH_STARTED start failed ! ");
			}
		
		}else{
			result.put("result", false);
			result.put("msg", "YOUR USERNAME OR PASSWORD ERROR");
		}
		return result.toString().getBytes("UTF-8");
	}
	
	
	/**
	 * 启动爬取代理端口ip
	 * @param username
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/crawlerProperty")
	public byte[] crawlerProperty(@RequestParam String username,@RequestParam String password) throws UnsupportedEncodingException{
		JSONObject result = new JSONObject();
		String localUsername = userConfig.getUsername();
		String localPassword = userConfig.getPassword();
		if(localUsername.equals(username)&&localPassword.equals(password)){
			propertyTask.crawlerProperty();
			result.put("result", true);
			result.put("msg", "crawlerProperty started success");
		}else{
			result.put("result", false);
			result.put("msg", "YOUR USERNAME OR PASSWORD ERROR");
		}
		return result.toString().getBytes("UTF-8");
	}
	
}

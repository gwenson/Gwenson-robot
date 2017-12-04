package com.gwenson.search.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gwenson.common.model.SearchBlog;
import com.gwenson.search.model.Page;
import com.gwenson.search.service.imp.AdvertService;
import com.gwenson.search.service.imp.SearchPageService;

@RestController
public class SearchController {

	@Autowired
	private SearchPageService searchPageService;
	@Autowired
	private AdvertService advertService;
	
	
	@RequestMapping("/search")
	public ModelAndView  search(@RequestParam(required=false) String words
			,@RequestParam(required=false)Integer currPage
			) throws UnsupportedEncodingException{
		ModelAndView mv = new ModelAndView("search");
		if(words!=null&&!"".equals(words)){
			words =java.net.URLDecoder.decode(words, "UTF-8");
			Page<SearchBlog> page = new Page<>();
			page.setPageNo(currPage);
			Map<String,Object> map = new HashMap<>();
			map.put("condition", words);
			page.setParamsMap(map);
			try {
				Page<SearchBlog> findByPage  = searchPageService.search(page);
				List<String> adverts = advertService.findByCode();
				mv.addObject("page", findByPage);
				mv.addObject("advert", adverts);
				mv.addObject("words", words);
			} catch (Exception e) {
				
			}
		}else{
			return new ModelAndView("redirect:/");
		}
		
		return mv;
	}
	
	@RequestMapping("/searchPage")
	public byte[] searchPage(@RequestParam String condition
			,@RequestParam(required=false)Integer currPage
			) throws UnsupportedEncodingException{
		JSONObject result=new JSONObject();
		if(condition!=null&&!"".equals(condition)){
			Page<SearchBlog> page = new Page<>();
			page.setPageNo(currPage);
			Map<String,Object> map = new HashMap<>();
			map.put("condition", condition);
			page.setParamsMap(map);
			try {
				Page<SearchBlog> findByPage  = searchPageService.search(page);
				List<String> findByCode = advertService.findByCode();
				result.put("result", true);
				result.put("msg", "");
				result.put("page", findByPage);
				result.put("advert", findByCode);
			} catch (Exception e) {
				result.put("result", false);
				result.put("msg", e.getMessage());
			}
		}else{
			result.put("result", false);
			result.put("msg", "空条件");
		}
		
		return JSON.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect).getBytes("UTF-8");
	}
}

package com.gwenson.listener;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.gwenson.robot.config.PropertyConfig;
import com.gwenson.robot.page.service.DispatchTaskService;
import com.gwenson.robot.proterty.model.Property;
import com.gwenson.robot.proterty.redis.dao.PropertyRedisDao;
import com.gwenson.robot.proterty.task.PropertyTask;
import com.gwenson.robot.utils.PropertyUtil;

/**
 * 容器启动完成后自动启动监听器
 * @author gwenson
 *
 */
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent>{
	private static Logger log=LoggerFactory.getLogger(ApplicationStartup.class); 
	@Override
	public void onApplicationEvent(ContextRefreshedEvent  event) {
		
		log.info("########### ApplicationStartup listener runing ###############");
		/**
		 * 根据配置信息是否开启自动代理ip采集
		 */
		final PropertyConfig propertyConfig = event.getApplicationContext().getBean(PropertyConfig.class);
		final Boolean start = propertyConfig.getStart();
		final Boolean autoScheduled = propertyConfig.getAutoScheduled();
		final String userDefinedPath = propertyConfig.getUserDefinedPath();
		if(start==true&&autoScheduled==true){
			
			PropertyTask propertyTask = event.getApplicationContext().getBean(PropertyTask.class);
			
			ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
			scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
				
				@Override
				public void run() {
					propertyTask.crawlerProperty();
					
				}
			}, 0, 20, TimeUnit.MINUTES);
		}else if(start==true&&userDefinedPath!=null&&!"".equals(userDefinedPath)){
			//加载自定义代理ip端口到redis
			PropertyRedisDao propertyRedisDao = event.getApplicationContext().getBean(PropertyRedisDao.class);
			List<Property> propertys = PropertyUtil.getFileProperty(userDefinedPath);
			propertyRedisDao.set(propertys);
		}
		
		final DispatchTaskService dispatchTaskService =  event.getApplicationContext().getBean(DispatchTaskService.class);
		
		//爬取内容
		Thread searchBlog=new Thread(new Runnable() {
			
			@Override
			public void run() {
				dispatchTaskService.dispatchStart();
				
			}
		});
		log.info("###############ApplicationStartup listener runing is searchBlog Thread #############");
		searchBlog.start();
		
	}

}

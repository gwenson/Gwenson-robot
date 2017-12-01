package com.gwenson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.gwenson.common.dao.redis.IdenticTextDao;
import com.gwenson.common.dao.redis.IdenticalUrlDao;
import com.gwenson.common.dao.redis.QueueSearchBlogDao;

@SpringBootApplication
public class SearchRobotApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchRobotApplication.class, args);
	}
	
    @Autowired
	private StringRedisTemplate redisTemplate;
    
    @Bean
    public IdenticalUrlDao identicalUrlDao(){
    	return new IdenticalUrlDao(redisTemplate);
    }
    
    @Bean
    public IdenticTextDao identicTextDao(){
    	return new IdenticTextDao(redisTemplate);
    }
    
    @Bean
    public QueueSearchBlogDao queueSearchBlogDao(){
    	return new QueueSearchBlogDao(redisTemplate);
    }
}

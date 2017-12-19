package com.gwenson.robot.page.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.Word;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwenson.common.dao.redis.IdenticTextDao;
import com.gwenson.common.dao.redis.QueueSearchBlogDao;
import com.gwenson.common.model.SearchBlog;
import com.gwenson.common.utils.DateUtil;
import com.gwenson.common.utils.EncoderUtil;
import com.gwenson.common.utils.NumberUtil;
import com.gwenson.common.utils.SimHash;
import com.gwenson.common.utils.StaticUrl;
import com.gwenson.robot.cahce.OnAndOffCache;
import com.gwenson.robot.config.DatabaseConfig;
import com.gwenson.robot.config.PropertyConfig;
import com.gwenson.robot.config.SearchConfig;
import com.gwenson.robot.page.dto.DepthUrlsDTO;
import com.gwenson.robot.page.redis.dao.DepthLockDao;
import com.gwenson.robot.page.redis.dao.DepthQueueUrlDao;
import com.gwenson.robot.page.redis.dao.WideQueueUrlDao;
import com.gwenson.robot.page.rule.service.SearchRule;
import com.gwenson.robot.page.service.DispatchTaskService;
import com.gwenson.robot.proterty.model.Property;
import com.gwenson.robot.proterty.redis.dao.PropertyRedisDao;
import com.gwenson.robot.utils.ConnectUtil;
import com.gwenson.robot.utils.DocumentUtil;
import com.gwenson.robot.utils.ScopeEnum;

/**
 * 调度任务服务实现
 * @author gwenson email : gwenson@163.com
 *
 */
@Component("dispatchTaskService")
public class DispatchTaskServiceImpl implements DispatchTaskService{

	
	private static Logger log=LoggerFactory.getLogger(DispatchTaskServiceImpl.class); 
	
	@Autowired
	private WideQueueUrlDao wideQueueUrlDao;
	@Autowired
	private DepthQueueUrlDao depthQueueUrlDao;
	@Autowired
	private PropertyRedisDao propertyRedisDao;
	@Autowired
	private IdenticTextDao identicTextDao;
	@Autowired
	private QueueSearchBlogDao queueSearchBlogDao;
	@Autowired
	private DepthLockDao depthLockDao;
	@Autowired  
    private PropertyConfig propertyConfig; 
	@Autowired
	private SearchConfig searchConfig;
	@Autowired
	private SearchRule searchRule;
	@Autowired
	private DatabaseConfig databaseConfig;
	
	
	static{
		try {
			Document doc = ConnectUtil.connectUrl("https://www.oschina.net/blog");
			if(doc!=null){
				String text = doc.body().text();
				List<Word> seg = WordSegmenter.seg(text, SegmentationAlgorithm.BidirectionalMinimumMatching);
				log.info("热部署分词>>>{}",seg.toString());
			}
		} catch (IOException e) {
			log.error("热部署分词错误", e);
		}
		
	}
	
	
	@Override
	public void dispatchStart() {
		
			log.info("################### dispatchStart runing #####################");
			log.info("################### "+searchConfig.getScope()+" #####################");
			//广度优先
			if(searchConfig.getScope().equalsIgnoreCase(ScopeEnum.WIDE.getScope())){
				wideSearch();
			}
			//深度优先
			if(searchConfig.getScope().equalsIgnoreCase(ScopeEnum.DEPTH.getScope())){
				depthSearch();
			}	
		
		
		
	}
	
	/**
	 * 深度优先
	 */
	@Override
	public void depthSearch(){
		int corePoolSize=10;
		ThreadPoolExecutor fixedThreadPool=new ThreadPoolExecutor(corePoolSize, corePoolSize, 0l, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		while(true){
			if(OnAndOffCache.isSearchStartedCache()){
				log.debug("#################进入循环#####################");
				//判断深度锁
				if(!depthLockDao.isLock()&&(depthQueueUrlDao.size()==null||depthQueueUrlDao.size()==0)){
					log.debug("#################进入广度#####################");
					final String wideUrl = wideQueueUrlDao.brPopQueue();
					log.debug("#################"+wideUrl+"#####################");
					
					depthQueueUrlDao.pushQueue(wideUrl);
					//设置深度锁
					depthLockDao.setLock(DocumentUtil.getDomainWide(wideUrl));
				}
				
				
				final String depthUrl = depthQueueUrlDao.popQueue();
				if(null !=depthUrl && !"".equals(depthUrl) ){
					boolean contains = searchRule.isContainsUrl(depthUrl);
					if(!contains){
						fixedThreadPool.execute(new Runnable() {
							
							@Override
							public void run() {
								log.debug("#################执行采集#####################");
								Document accessUrl = accessUrl(depthUrl);
								depthOperationDoc(depthUrl, accessUrl);
								
							}

							
						});
						
						isCleanDepthLock();
						
					}else{
						searchRule.isClearDepthQueues(depthUrl);
					}
				}else{
					isCleanDepthLock();
				}
				
				//防止应用停掉浪费线程池的queue资源
				while (true) {
					
					if(fixedThreadPool.getQueue().size()==0){
						log.debug("########退出队列清空等待###########");
						break;
					}
					
				}
				
				
			}
		}
		
		
	
		
	}

	/**
	 * 判断是否清空深度锁
	 */
	private void isCleanDepthLock() {
		Long size = depthQueueUrlDao.size();
		if(null==size||0==size){
			log.debug("#################深度队列空值休眠#####################");
			try {
				Thread.sleep(25000);
				Long size2 = depthQueueUrlDao.size();
				if(null==size2||0==size2){
					log.debug("#################清空锁#####################");
					//清空深度锁
					depthLockDao.deleteLock();
				}
					
			} catch (InterruptedException e) {
				
			}
		}
	}

	/**
	 * 深度优先操作doc
	 * @param url
	 * @param accessUrl
	 */
	private void depthOperationDoc(final String url, final Document accessUrl){

		if(accessUrl!=null&&!"".equals(accessUrl)){
			
			String text = DocumentUtil.getText(accessUrl);
			text = text.replaceAll(" ", "");
			int max=65535/3;
			
			if(text!=null&&!"".equals(text)){
				//判断内容是否重复
				String simHashCode = analyzeSimHashCode(text);
				
				boolean containsText = identicTextDao.contains(simHashCode);
				
				boolean containsUrl = searchRule.isContainsUrl(url);
				
				if(!containsUrl){
					if(!containsText){
						
						SearchBlog searchBlog = createModel(url, accessUrl, text, max);
						//这里用作简单的消息队列推送
						queueSearchBlogDao.pushQueue(searchBlog);
						/*
						还没持久化，在持久化时，调用：
						identicalUrlDao.set(searchBlog.getUrl());
						identicTextDao.set(simHashCode);
						实现去重
						详情例子看第400行
						*/
					}
					
					DepthUrlsDTO depthUrls = DocumentUtil.getDepthUrls(depthLockDao.getLockDomainUrl(),accessUrl);
					if(depthUrls!=null){
						if(depthUrls.getDepth()!=null&&depthUrls.getDepth().size()>0){
							log.debug("############ depth urls size : "+depthUrls.getDepth().size()+"#################");
							depthQueueUrlDao.pushQueue(depthUrls.getDepth());
						}
						if(depthUrls.getWide()!=null&&depthUrls.getWide().size()>0){
							log.debug("############ wide urls size : "+depthUrls.getWide().size()+"#################");
							wideQueueUrlDao.pushQueue(depthUrls.getWide());
						}
						
					}
					
				}
				
				
				
			}
		}
		
	
		
	}
	
	/**
	 * 广度优先
	 */
	@Override
	public void wideSearch() {
		int corePoolSize=10;
		ThreadPoolExecutor fixedThreadPool=new ThreadPoolExecutor(corePoolSize, corePoolSize, 0l, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		while (true) {
			if(OnAndOffCache.isSearchStartedCache()){
				try {
					final String url = wideQueueUrlDao.brPopQueue();
					boolean containsUrl = searchRule.isContainsUrl(url);

					if(!containsUrl){
						fixedThreadPool.execute(new Runnable() {
							
							@Override
							public void run() {
								Document accessUrl = accessUrl(url);
								wideOperationDoc(url, accessUrl);
							}

							
						});
					}
				} catch (Exception e) {
					log.error("线程池业务运行出现错误 : ", e.getMessage());
				}
				//休眠,模拟真实访问，防止代理ip封锁
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// 
				}
				
				//防止应用停掉浪费线程池的queue资源
				while (true) {
					
					if(fixedThreadPool.getQueue().size()==0){
						break;
					}
					
				}
				
			}
		}
	}
	
	
	
	/**
	 * 访问url
	 * @param startUrl
	 * @return
	 */
	private Document accessUrl(final String startUrl) {
		//代理是否开启
		final Boolean start = propertyConfig.getStart();
		Document document = null;
		//判断代理是否开启
		if(start==true){
			List<Property> propertys =null;
			while (true) {
				propertys = propertyRedisDao.getPropertys();
				if(propertys!=null&&propertys.size()>0){
					break;
				}else{
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						
					}
				}
			}
			try {
				document = ConnectUtil.connectUrl(propertys, null==startUrl||"".equals(startUrl)?StaticUrl.START_URL:startUrl);
			} catch (IOException e) {
				log.error(e.getMessage()+":"+startUrl,e);
			}
		}else{
			try {
				document = ConnectUtil.connectUrl(null==startUrl||"".equals(startUrl)?StaticUrl.START_URL:startUrl);
			} catch (IOException e) {
				log.error(e.getMessage()+":"+startUrl,e);
			}
		}
	
		return document;
	}

	/**
	 * 广度优先的操作doc
	 * @param url
	 * @param accessUrl
	 */
	private void wideOperationDoc(final String url, final Document accessUrl) {

		if(accessUrl!=null&&!"".equals(accessUrl)){
			
			String text = DocumentUtil.getText(accessUrl);
			text = text.replaceAll(" ", "");
			int max=65535/3;
			
			if(text!=null&&!"".equals(text)){
				//判断内容是否重复
				String simHashCode = analyzeSimHashCode(text);
				
				boolean containsText = identicTextDao.contains(simHashCode);
				
				boolean containsUrl = searchRule.isContainsUrl(url);
				
				if(!containsUrl){
					if(!containsText){
						
						SearchBlog searchBlog = createModel(url, accessUrl, text, max);
						//这里用作简单的消息队列推送
						queueSearchBlogDao.pushQueue(searchBlog);
						/*
						还没持久化，在持久化时，调用：
						identicalUrlDao.set(searchBlog.getUrl());
						identicTextDao.set(simHashCode);
						实现去重
						详情例子看第400行
						*/
						
						
					}
					Set<String> url2 = DocumentUtil.getUrls(accessUrl);
					wideQueueUrlDao.pushQueue(url2);
					
				}
			}
		}
		
	
		
	}

	/**
	 * 分析文本得到simHashCode
	 * @param text
	 * @return
	 */
	private String analyzeSimHashCode(String text) {
		List<String> analyzeSearchTerms = new ArrayList<>();
		WordSegmenter.seg(text, SegmentationAlgorithm.BidirectionalMinimumMatching)
			.forEach(e -> analyzeSearchTerms.add(e.getText()));
		
		String simHashCode = SimHash.getSimHashCode(analyzeSearchTerms);
		return simHashCode;
	}
	
	/**
	 * 构建model
	 * @param url 当前url
	 * @param doc Document
	 * @param text 处理过的文本内容
	 * @param max
	 * @return
	 */
	private SearchBlog createModel(final String url, final Document doc, String text, int max) {
		String description = DocumentUtil.getDescription(doc);
		String keywords = DocumentUtil.getKeywords(doc);
		String postTime = DocumentUtil.getPostTime(doc);
		String title = DocumentUtil.getTitle(doc);
		SearchBlog searchBlog = new SearchBlog();
		searchBlog.setDescription(description);
		searchBlog.setKeywords(keywords);
		Date postTime2 = null;
		if(postTime!=null&&!"".equals(postTime)){
			postTime2 = DateUtil.string2Date(postTime,DateUtil.DATE_FORMATHOURS);
		}
		searchBlog.setPostTime(postTime2);
		if(text.length()>max){
			text=text.substring(0, max-1);
		}
		searchBlog.setText(EncoderUtil.filterEmoji2(text));
		searchBlog.setUrl(url);
		searchBlog.setTitle(title);
		int tableId = NumberUtil.getStringModBitCount(url, databaseConfig.getNum());
		searchBlog.setTableId(tableId);
		return searchBlog;
	}
	
	
	/*
	用的是spring JDBC
	public void saveSearchBlogStart() {
		log.info("saveSearchBlogStart runing ....");
		while(true){
			if(OnAndOffCache.isSaveQueueStartedCache()){
				SearchBlog searchBlog = queueSearchBlogDao.brPopQueue();
				if(searchBlog!=null){
					boolean contains = identicalUrlDao.contains(searchBlog.getUrl());
					EsIndex index = null;
					if(!contains){
						List<String> analyzeSearchTerms = new ArrayList<>();
						WordSegmenter.seg(searchBlog.getText(), SegmentationAlgorithm.BidirectionalMinimumMatching)
							.forEach(e -> analyzeSearchTerms.add(e.getText()));;
						String simHashCode = SimHash.getSimHashCode(analyzeSearchTerms);
						boolean contains2 = identicTextDao.contains(simHashCode);
						if(!contains2){
							try {
								SearchBlog create = searchBlogRepository.create(searchBlog);
								if(create!=null&&create.getId()!=null){
									identicalUrlDao.set(searchBlog.getUrl());
									identicTextDao.set(simHashCode);
									if(index.getId()!=null&&!"".equals(index.getId()))
										log.info("save ："+create.getId()+","+searchBlog.getUrl());
								}
							} catch (Exception e) {
								// 
								log.error("searchBlog创建失败 ： ", e);
								log.info(searchBlog.getUrl());
							}
						}else{
							log.info("##### text saved"+searchBlog.getUrl()+" #####");
						}
						
					}else{
						if(contains){
							log.info("##### url saved"+searchBlog.getUrl() +" #####");
						}
					}
				
				}else{
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
//						
					}
				}
			}else{
				//未启动时，休眠3秒
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// 
				}
			}
				
		}
	}*/

}

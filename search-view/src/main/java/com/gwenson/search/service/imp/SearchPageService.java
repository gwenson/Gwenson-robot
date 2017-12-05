package com.gwenson.search.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gwenson.common.dao.redis.IdenticTextDao;
import com.gwenson.common.dao.redis.IdenticalUrlDao;
import com.gwenson.common.dao.redis.QueueSearchBlogDao;
import com.gwenson.common.model.SearchBlog;
import com.gwenson.common.utils.SimHash;
import com.gwenson.search.dao.SearchBlogMapper;
import com.gwenson.search.model.EsIndex;
import com.gwenson.search.model.Page;
import com.gwenson.search.service.EsIndexService;
import com.gwenson.search.utils.SearchDisposeUtil;

@Service
public class SearchPageService{
	private static Logger log=(Logger) LoggerFactory.getLogger(SearchPageService.class);
	@Autowired
	private SearchBlogMapper searchBlogMapper;
	@Autowired
	private EsIndexService esIndexService;
	@Autowired
	private IdenticTextDao identicTextDao;
	@Autowired
	private QueueSearchBlogDao queueSearchBlogDao;
	@Autowired
	private IdenticalUrlDao identicalUrlDao;

	/**
	 * 分页搜索
	 * @param page
	 * @return
	 */
	public Page<SearchBlog> search(Page<SearchBlog> page){
		
		org.springframework.data.domain.Page<EsIndex> page2 = esIndexService.search(page);
		String searchContent = (String) page.getParamsMap().get("condition");
		List<String> wordAnalyzeSearchTerms = esIndexService.getWordAnalyzeSearchTerms(searchContent);
		log.debug("list<String>={}",wordAnalyzeSearchTerms);
		page.setTotalCount(page2.getTotalElements());
		page.setTotalPage(page2.getTotalPages());
		List<EsIndex> content = page2.getContent();
		if(null!=content&&0<content.size()){
			List<SearchBlog> list = new ArrayList<>();
			SearchBlog searchBlog = null;
			for(EsIndex e:content){
				try {
					searchBlog = searchBlogMapper.findByIdAndTableId(e.getTableId(), e.getSbId());
					if(null!=searchBlog){
						searchBlog = SearchDisposeUtil.disposeEm(searchBlog, wordAnalyzeSearchTerms);
						list.add(searchBlog);
					}
				} catch (Exception e1) {
					log.debug("查询出错", e1);
				}
			}
			page.setResult(list);
		}
		return page;
	}

	/**
	 * 插入一条数据
	 * @param searchBlog
	 * @return
	 */
	public void insert(SearchBlog searchBlog){
		searchBlogMapper.insert(searchBlog);
	}
	
	
	
	
	/**
	 * 从队列拉取下来保存
	 */
	@Async
	public void saveSearchBlogStart() {
		log.info("saveSearchBlogStart runing ....");
		while(true){
			SearchBlog searchBlog = queueSearchBlogDao.brPopQueue();
			if(searchBlog!=null){
				boolean contains = identicalUrlDao.contains(searchBlog.getUrl());
				EsIndex esIndex = null;
				if(!contains){
					List<String> analyzeSearchTerms = esIndexService.getWordAnalyzeSearchTerms(searchBlog.getText());
					/*List<String> analyzeSearchTerms = new ArrayList<>();
					WordSegmenter.seg(searchBlog.getText(), SegmentationAlgorithm.BidirectionalMinimumMatching)
						.forEach(e -> analyzeSearchTerms.add(e.getText()));;*/
					String simHashCode = SimHash.getSimHashCode(analyzeSearchTerms);
					boolean contains2 = identicTextDao.contains(simHashCode);
					if(!contains2){
						try {
							searchBlogMapper.insert(searchBlog);
							if(searchBlog!=null&&searchBlog.getId()!=null){
								esIndex = new EsIndex();
								esIndex.setSbId(searchBlog.getId());
								esIndex.setTableId(searchBlog.getTableId());
								esIndex.setTitle(searchBlog.getTitle());
								esIndex.setDescription(searchBlog.getDescription());
								esIndex.setKeywords(searchBlog.getKeywords());
								esIndex.setPostTime(searchBlog.getPostTime()!=null?searchBlog.getPostTime().getTime():new Date().getTime());
								esIndexService.saveEsIndex(esIndex);
								identicalUrlDao.set(searchBlog.getUrl());
								identicTextDao.set(simHashCode);
								if(esIndex.getId()!=null&&!"".equals(esIndex.getId()))
									log.debug("save ："+esIndex.getId()+","+searchBlog.getUrl());
							}
						} catch (Exception e) {
							// 
							log.error("searchBlog创建失败 ： ", e);
							log.debug(searchBlog.getUrl());
						}
					}else{
						log.debug("##### text saved"+searchBlog.getUrl()+" #####");
					}
					
				}else{
					if(contains){
						log.debug("##### url saved"+searchBlog.getUrl() +" #####");
					}
				}
			
			}else{
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
//					
				}
			}
		
				
		}
	}
}

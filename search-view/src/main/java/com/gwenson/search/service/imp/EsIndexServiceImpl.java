package com.gwenson.search.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.gwenson.common.model.SearchBlog;
import com.gwenson.search.model.EsIndex;
import com.gwenson.search.service.EsIndexService;

@Service("esIndexServiceImpl")
public class EsIndexServiceImpl implements EsIndexService {
	private static Logger log = (Logger) LoggerFactory.getLogger(EsIndexServiceImpl.class);
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Override
	public String saveEsIndex(EsIndex ei) {
		String id = ei.getId();
		ei.setId(ei.getSbId() + "." + ei.getTableId());
		try {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(id).withObject(ei).build();
			String index = elasticsearchTemplate.index(indexQuery);
			return index;
		} catch (Exception e) {
			log.error("insert or update user info error.", e);
			return null;
		}
	}

	@Override
	public Page<EsIndex> search(com.gwenson.search.model.Page<SearchBlog> page) {
		String condition = (String) page.getParamsMap().get("condition");
		if (null != condition && !"".equals(condition)) {
			SearchQuery citySearchQuery = getSearchQuery(page.getPageNo(), page.getPageSize(), condition);
			Page<EsIndex> page2 = elasticsearchTemplate.queryForPage(citySearchQuery, EsIndex.class);
			return page2;
		}

		return null;

	}

	/* 搜索模式 */
	String SCORE_MODE_SUM = "sum"; // 权重分求和模式
	Float MIN_SCORE = 10.0F; // 由于无相关性的分值默认为 1 ，设置权重分最小值为 10

	/**
	 * 根据搜索词构造搜索查询语句
	 *
	 * 代码流程： - 权重分查询 - 短语匹配 - 设置权重分最小值 - 设置分页参数
	 *
	 * @param pageNumber
	 *            当前页码
	 * @param pageSize
	 *            每页大小
	 * @param searchContent
	 *            搜索内容
	 * @return
	 */
	private SearchQuery getSearchQuery(Integer pageNumber, Integer pageSize, String... searchContent) {
		FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
				.add(QueryBuilders.matchPhraseQuery("title", searchContent),
						ScoreFunctionBuilders.weightFactorFunction(1000))
				.add(QueryBuilders.matchPhraseQuery("description", searchContent),
						ScoreFunctionBuilders.weightFactorFunction(100))
				.add(QueryBuilders.matchPhraseQuery("keywords", searchContent),
						ScoreFunctionBuilders.weightFactorFunction(100))
				.scoreMode(SCORE_MODE_SUM).setMinScore(MIN_SCORE);
		// 分页参数
		Pageable pageable = new PageRequest(pageNumber - 1, pageSize);
		return new NativeSearchQueryBuilder().withQuery(functionScoreQueryBuilder).withPageable(pageable).build();
	}

	/**
         * 调用 ES 获取 word 分词后结果
         *
         * @param searchContent
         * @return
         */
	@Override
	 public List<String> getWordAnalyzeSearchTerms(String searchContent) {
		 // 调用 IK 分词分词
		 AnalyzeRequestBuilder ikRequest = new AnalyzeRequestBuilder(elasticsearchTemplate.getClient(),
		 AnalyzeAction.INSTANCE,"searchindex",searchContent);
	     ikRequest.setTokenizer("word");
		 List<AnalyzeResponse.AnalyzeToken> ikTokenList = ikRequest.execute().actionGet().getTokens();
	   
	    // 循环赋值
	    List<String> searchTermList = new ArrayList<>();
	    ikTokenList.forEach(ikToken -> { searchTermList.add(ikToken.getTerm()); });
	    return searchTermList;
	 }

}

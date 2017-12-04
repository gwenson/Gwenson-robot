package com.gwenson.search.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.gwenson.common.model.SearchBlog;
import com.gwenson.search.model.EsIndex;

public interface EsIndexService {
	
	public String saveEsIndex(EsIndex ei);
	
	public Page<EsIndex> search(com.gwenson.search.model.Page<SearchBlog> page);
	
	 public List<String> getWordAnalyzeSearchTerms(String searchContent);
}

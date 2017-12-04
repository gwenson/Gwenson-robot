package com.gwenson.search.dao.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gwenson.search.model.EsIndex;

@Repository
public interface EsIndexRepository extends ElasticsearchRepository<EsIndex, String>, PagingAndSortingRepository<EsIndex, String> {
	
}

package com.gwenson.search.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gwenson.common.model.SearchBlog;


@Repository
public interface SearchBlogMapper {
	
//	public List<SearchBlogWithBLOBs> findByIds(@Param("tableId") int tableId ,@Param("ids") List<Long> ids);
	
	public SearchBlog findByIdAndTableId(@Param("tableId") int tableId ,@Param("id") Long id);
	
	public void insert(SearchBlog searchBlog);
	
}
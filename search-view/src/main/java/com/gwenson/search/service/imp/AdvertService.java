package com.gwenson.search.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwenson.search.dao.AdvertMapper;
import com.gwenson.search.utils.AdvertEhcache;

@Service
public class AdvertService {

	@Autowired
	private AdvertMapper advertMapper;
	
	public List<String> findByCode(){
		List<String> list = AdvertEhcache.getList();
		if(list!=null&&list.size()>0){
			return list;
		}else{
			list=advertMapper.findByCode();
			if(list!=null&&list.size()>0){
				AdvertEhcache.putList(list);
				return list;
			}
		}
		return null;
	}
}

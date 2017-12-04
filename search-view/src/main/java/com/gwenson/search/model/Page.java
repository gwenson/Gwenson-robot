package com.gwenson.search.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;

/**
 * 分页
 * 
 * @author 翟洪坤
 * @date 2015-06-29
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = -1688241932064784307L;
    
    public final static Integer PAGE_SIZE_NUMBER=15;
    
    protected Integer pageNo = 1; // 当前页
    protected Integer pageSize = PAGE_SIZE_NUMBER; // 每一页记录数
    protected String sorting = ""; // 排序字段和排序方向,多个排序用逗号分隔。例如：ID ASC, NAME DESC
    protected boolean findCount = false; // 是否只查询总记录数
    protected List<T> result = new ArrayList<T>(); // 返回实体结果集
    protected List<Map<String, Object>> objectResult = new ArrayList<Map<String, Object>>(); // 返回结果集
    protected long totalCount = -1; // 总记录数
    protected long totalPage = -1; // 总页数
    protected T object; // 单个对象，查询使用
    protected Map<String, Object> paramsMap = new HashMap<String, Object>();// 特殊参数设置(对于对象不能设置的参数，进行补充，如时间段查询)
    protected Direction direction;

    public Integer getPageNo(){
        return pageNo;
    }

    public void setPageNo(Integer pageNo){
    	if(pageNo==null){
    		this.pageNo =1;
    	}else{
    		 this.pageNo = pageNo < 1?1:pageNo;
    	}
    }

    public Integer getPageSize(){
        return pageSize;
    }

    public void setPageSize(Integer pageSize){
    	if(pageSize==null){
    		this.pageSize =PAGE_SIZE_NUMBER;
    	}else{
    		 this.pageSize = pageSize < 1?PAGE_SIZE_NUMBER:pageSize;
    	}
    }

    public boolean findCount(){
        return findCount;
    }

    public void setFindCount(boolean findCount){
        this.findCount = findCount;
    }

    public List<T> getResult(){
        return result;
    }

    public void setResult(List<T> result){
        this.result = result;
    }

    public List<Map<String, Object>> getObjectResult(){
        return objectResult;
    }

    public void setObjectResult(List<Map<String, Object>> objectResult){
        this.objectResult = objectResult;
    }

    public long getTotalCount(){
        return totalCount;
    }

    public void setTotalCount(long totalCount){
        this.totalCount = totalCount;
    }

    public T getObject(){
        return object;
    }

    public void setObject(T object){
        this.object = object;
    }

    public Direction getDirection(){
        return direction;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    /**
     * 根据pageSize与totalCount计算总页数,默认值为-1
     */
    public long getTotalPage(){
        long count = -1;
        if (this.totalCount >= 0){
            count = this.totalCount / this.pageSize;
            if (this.totalCount % this.pageSize > 0){
                count++;
            }
        }
        return count;
    }

    public void setTotalPage(long totalPage){
        this.totalPage = totalPage;
    }

    public String getSorting(){
        return sorting;
    }

    /**
     * 组装排序
     */
    public void setSorting(String sorting){
        this.sorting = sorting;
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始
     */
    public long getFirst(){
        return ((pageNo - 1) * pageSize) + 1;
    }

    /**
     * 是否还有下一页
     */
    public boolean isHasNext(){
        return (pageNo + 1 <= getTotalPage());
    }

    /**
     * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号
     */
    public long getNextPage(){
        return isHasNext()?(pageNo + 1):pageNo;
    }

    /**
     * 是否还有上一页
     */
    public boolean isHasPre(){
        return (pageNo - 1 >= 1);
    }

    /**
     * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号
     */
    public long getPrePage(){
        return isHasPre()?(pageNo - 1):pageNo;
    }

    public Map<String, Object> getParamsMap(){
        return paramsMap;
    }

    public void setParamsMap(Map<String, Object> paramsMap){
        this.paramsMap = paramsMap;
    }
}

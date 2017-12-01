package com.gwenson.robot.utils;

import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwenson.common.utils.EncoderUtil;
import com.gwenson.common.utils.RegExUtil;
import com.gwenson.robot.page.dto.DepthUrlsDTO;

/**
 * Document处理工具类
 * @author gwenson email : gwenson@163.com
 *
 */
public class DocumentUtil {

	private static Logger log=LoggerFactory.getLogger(DocumentUtil.class); 
	
	 public static final String regexURL = "http://.*?";
//			 "^http://([w-]+.)+[w-]+(/[w-./?%&=]*)?$";
	
	
	/**
	 * 获取全文
	 * @param doc
	 * @return
	 */
	public static String getText(final Document doc){
		Elements title = doc.select("body");
		if(title!=null){
			String text = title.text();
			return EncoderUtil.filterEmoji2(text);
		}
		return null;
	}
	
	
	/**
	 * 获取标题
	 * @param doc
	 * @return
	 */
	public static String getTitle(final Document doc){
		Elements title = doc.select("title");
		if(title!=null){
			String titleText = title.text();
			return EncoderUtil.filterEmoji2(titleText);
		}
		return null;
		
	}
	/**
	 * 获取备注
	 * @param doc
	 * @return
	 */
	public static String getDescription(final Document doc){
		Elements keywords = doc.select("meta[name=description]");
		if(null!=keywords){
			String description = keywords.attr("content");
			return EncoderUtil.filterEmoji2(description);
		}
		return null;
		
	}
	
	/**
	 * 获取索引文本
	 * @param doc
	 * @return
	 */
	public static String getKeywords(final Document doc){
		Elements keywords = doc.select("meta[name=keywords]");
		if(null!=keywords){
			String description = keywords.attr("content");
			return EncoderUtil.filterEmoji2(description);
		}
		return null;
		
	}
	
	
	/**
	 * 获取当前页面的URL
	 * @param doc
	 * @return
	 */
	public static Set<String> getUrls(final Document doc){
		Elements select = doc.select("a");
		Set<String> urls =new HashSet<>();
		String uri = doc.baseUri();
		String localhost = getDomainSite(uri);
		if(localhost!=null&&!"".equals(localhost)){
			urls.add(localhost);
		}
		for(int i=0;i<select.size();i++){
			Element element = select.get(i);
			String attr = element.attr("abs:href");
			String attr1 = element.attr("href");
			
			if(attr!=null&&!"".equals(attr)){
				if(!attr1.startsWith("#")){
					int indexOf = attr1.indexOf("#");
					if(indexOf>-1){
						String substring = attr.substring(0, indexOf);
						if(null!=substring&&!"".equals(substring)){
							if(!EncoderUtil.isContainChinese(substring)){
								urls.add(substring);
							}
						}
					}else{
						if(!EncoderUtil.isContainChinese(attr)){
							urls.add(attr);
						}
					}
					
				}
			}
		}
		return urls;
		
	}
	
	/**
	 * 获取当前页面的URL(深度优先)
	 * @param doc
	 * @return
	 */
	public static DepthUrlsDTO getDepthUrls(final String domainUrl,final Document doc){
		
		Elements select = doc.select("a");
		Set<String> depthUrls =new HashSet<>();
		Set<String> wideUrls =new HashSet<>();
		
		
		String uri = doc.baseUri();
		String domain=domainUrl;
		if(domain==null||"".equals(domain)){
			domain=getDomainWide(uri);
		}
		//加上当前url的域名
		String localhost = getDomainSite(uri);
		if(localhost!=null&&!"".equals(localhost)){
			depthUrls.add(localhost);
		}
		for(int i=0;i<select.size();i++){
			Element element = select.get(i);
			String attr = element.attr("abs:href");
			String attr1 = element.attr("href");
			
			if(attr!=null&&!"".equals(attr)){
				if(!attr1.startsWith("#")){
					int indexOf = attr1.indexOf("#");
					if(indexOf>-1){
						String substring = attr.substring(0, indexOf);
						if(null!=substring&&!"".equals(substring)){
							if(!EncoderUtil.isContainChinese(substring)){
								
								if(domain.equals(getDomainWide(substring))){
									depthUrls.add(substring);
								}else{
									wideUrls.add(substring);
								}
								
							}
						}
					}else{
						if(!EncoderUtil.isContainChinese(attr)){
							if(domain.equals(getDomainWide(attr))){
								depthUrls.add(attr);
							}else{
								wideUrls.add(attr);
							}
							
						}
					}
					
				}
			}
		}
		DepthUrlsDTO depthUrls2 = new DepthUrlsDTO(depthUrls,wideUrls);
		log.debug("#######################"+depthUrls2.getDepth().size()+"#############################");
		log.debug("#######################"+depthUrls2.getWide().size()+"#############################");
		
		return new DepthUrlsDTO(depthUrls,wideUrls);
		
	}
	
	
	/**
	 * 获取全文的录入时间
	 * @param doc
	 * @return
	 */
	public static String getPostTime(final Document doc){
		String text = getText(doc);
		if(text!=null&&!"".equals(text)){
			String time = RegExUtil.regExTime(text);
			return time;
		}
		return null;
	}
	
	/**
	 * 获取url的域名 site(带请求协议)
	 * @param url
	 * @return
	 */
	public static String getDomainSite(String url){
		int fromIndex = fromIndex(url);
		int indexOf = url.indexOf("/", fromIndex);
		if(indexOf>-1){
			return url.substring(0, indexOf);
			
		}
		return url;
	}


	private static int fromIndex(String url) {
		String h1="http://";
		String h2="https://";
		boolean startsWith = url.startsWith(h1);
		boolean startsWith2 = url.startsWith(h2);
		int fromIndex=0;
		if(startsWith){
			fromIndex=h1.length();
		}
		if(startsWith2){
			fromIndex=h2.length();
		}
		return fromIndex;
	}
	
	/**
	 * 获取url的域名 site(不带请求协议)
	 * @param url
	 * @return
	 */
	public static String getDomain(String url){
		int fromIndex = fromIndex(url);
		
		int indexOf = url.indexOf("/", fromIndex);
		String substring = url;
		if(indexOf>-1){
			substring=url.substring(0, indexOf);
		}
		
		substring=substring.substring(fromIndex);
		return substring;
	}
	
	/**
	 * 获取url的域名 site(不带请求协议、www)
	 * @param url
	 * @return
	 */
	public static String getDomainWide(String url){
		String domain = getDomain(url);
		String www="www";
		if(domain.startsWith(www))
			domain = domain.substring(www.length()+1);
		return domain;
	}
	
}

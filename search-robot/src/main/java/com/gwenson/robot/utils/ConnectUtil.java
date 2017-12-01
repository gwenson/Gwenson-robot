package com.gwenson.robot.utils;

import java.io.IOException;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwenson.common.utils.EncoderUtil;
import com.gwenson.robot.proterty.model.Property;

/**
 * 连接工具类
 * 
 * @author gwenson email : gwenson@163.com
 *
 */
public class ConnectUtil {

	private static Logger log = LoggerFactory.getLogger(ConnectUtil.class);
	/**
	 * USER_ANGENTS的当前下标
	 */
	private static volatile int user_angents_curr_index=0;
	private static final String[] USER_ANGENTS={
			"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36",
			"Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)",
			"Mozilla/5.0 (compatible; Baiduspider/2.0; +http://www.baidu.com/search/spider.html)",
			"Sogou web spider/4.0(+http://www.sogou.com/docs/help/webmasters.htm#07)",
			"Mozilla/5.0 (compatible; Yahoo! Slurp/3.0; http://help.yahoo.com/help/us/ysearch/slurp)",
			"Sosospider+(+http://help.soso.com/webspider.htm)",
			"Mozilla/5.0 (compatible; YoudaoBot/1.0; http://www.youdao.com/help/webmaster/spider/; )",
			"msnbot/1.0 (+http://search.msn.com/msnbot.htm)",
			"Mozilla/5.0 (compatible; bingbot/2.0; +http://www.bing.com/bingbot.htm)",
			"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)"

	};

	/**
	 * 启用代理端口（propertys 非空的情况下） 连接 url
	 * 
	 * @param propertys
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static Document connectUrl(List<Property> propertys, final String url) throws IOException {
		if (!EncoderUtil.isContainChinese(url)) {
			if (propertys != null && propertys.size() > 0) {
				int index = (int) (Math.random() * (propertys.size() - 1));
				if (index >= propertys.size()) {
					index = propertys.size() - 1;
					if (propertys.size() == 1) {
						index = 0;
					}
				}
				Property property = propertys.get(index);
				if (property != null && property.getIp() != null && !"".equals(property.getIp())
						&& property.getPort() != null && !"".equals(property.getPort())) {
					Connection connect = Jsoup.connect(url);
					Document doc = connect
							.userAgent(getUserAgent())
							.cookie("auth", "token")
							.header("accept", "*/*")
							.header("connection", "Keep-Alive")
							.proxy(property.getIp(), property.getPort())
							.timeout(3000)
							.get();
					if (doc != null) {
						log.info("connectUrl：" + url + "使用的代理ip与端口：" + property.getIp() + ":" + property.getPort());
					} else {
						log.info("connectUrl is no ：" + url + "使用的代理ip与端口：" + property.getIp() + ":"
								+ property.getPort());
					}

					return doc;
				}
			}
		}
		return null;
	}
	
	/**
	 * 不启用启用代理端口 连接 url
	 * 
	 * @param propertys
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static Document connectUrl(final String url) throws IOException {
		if (!EncoderUtil.isContainChinese(url)) {
			Connection connect = Jsoup.connect(url);
			Document doc = connect
					.userAgent(getUserAgent())
					.cookie("auth", "token")
					.header("accept", "*/*")
					.header("connection", "Keep-Alive")
					.timeout(3000)
					.get();
			if (doc != null) {
				log.info("connectUrl：" + url + "不使用的代理ip与端口。");
			} else {
				log.info("connectUrl is no ：" + url + "不使用的代理ip与端口。");
			}
			return doc;
		}
		return null;
	}
	
	/**
	 * 获取UserAgent，实现轮换UserAgent
	 * @return
	 */
	public static String getUserAgent(){
		String userAngent="";
		int length = USER_ANGENTS.length;
		if(length>1){
			synchronized (ConnectUtil.class) {
				userAngent=USER_ANGENTS[user_angents_curr_index];
				if(length-1==user_angents_curr_index){
					user_angents_curr_index=0;
				}else{
					user_angents_curr_index=user_angents_curr_index+1;
				}
				
			}
		}
		return userAngent;
	}
	
}

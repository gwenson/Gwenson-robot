package com.gwenson.robot.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwenson.robot.proterty.model.Property;



/**
 * 爬代理ip工具类
 * @author gwenson email : gwenson@163.com
 *
 */
public class PropertyUtil {
	
	private static Logger log=LoggerFactory.getLogger(PropertyUtil.class);
	
	/**
	 * 测试ip地址是否能访问
	 * @param ip
	 * @param port
	 * @return
	 */
	public static Property testIP(final Property property){
		try {
			log.info("###### Property testIP  runing as "+property.getIp()+":"+property.getPort()+" ######");
	    	Connection connect = Jsoup.connect("http://www.baidu.com");
	    	Document doc = null;
				doc = connect
						  .userAgent("Mozilla")
						  .cookie("auth", "token")
						  .timeout(3000)
						  .proxy(property.getIp(), property.getPort())
						  .get();
				if(doc!=null){
					return property;
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("线程 [ "+Thread.currentThread().getName()+" ] 测试代理端口失败",e);
		}
		return null;
		
	}
	
	

	
	
	/**
	 * 搜索代理ip
	 * @return
	 * @throws IOException
	 */
	public static Document ipSearch(){
		Connection connect = Jsoup.connect("http://api.xicidaili.com/free2016.txt");
		Document doc = null;
		try {
			doc = connect
//  			  .data("query", "Java")
				  .userAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
				  .cookie("auth", "token")
				  .timeout(30000)
				  .get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return doc;
	}
	
	
	/**
	 * 搜索代理ip
	 * @return
	 * @throws IOException
	 */
	public static List<Document> ipSearch2(){
		List<Document> list =new ArrayList<>();
		for(int i= 1;i<11;i++){
			Connection connect = Jsoup.connect("http://www.xicidaili.com/nn/"+(i==1?"":i));
			Document doc = null;
			try {
				doc = connect
//  			  .data("query", "Java")
						.userAgent(ConnectUtil.getUserAgent())
						.cookie("Hm_lvt_0cf76c77469e965d2957f0553e6ecf59", "1493908355")
						.cookie("_free_proxy_session", "BAh7B0kiD3Nlc3Npb25faWQGOgZFVEkiJTk4MjI0YzEwMDNhOWI1ZGJhYmE5ZDJjYzBjZjcyMDBjBjsAVEkiEF9jc3JmX3Rva2VuBjsARkkiMWx5UDVqbE1OYmxLWTJVL0RpUW9JOVpXaTA2NFpnRncwWGVPeUY5MEVXcUk9BjsARg%3D%3D--247b745dc7bbe65d2219dcde6d88f0791a88c854")
						.cookie("__guid", "264997385.1028486519774784000.1506535772881.0818")
						.cookie("monitor_count", "8")
						.timeout(30000)
						.get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			if(doc!=null){
				log.info("##### crawler 2 not null "+i+"页 #####");
				list.add(doc);
			}else{
				log.info("##### crawler 2 null "+i+"页 #####");
			}
		}
		return list;
	}
	
	
	
	
	
	/**
	 * 获取临时代理ip
	 * @return
	 * @throws IOException
	 */
	public static List<Property> getTempPropertys(){
		Document ipSearch = ipSearch();
		List<Property> list = new ArrayList<>();
		if(ipSearch!=null){
			list.addAll(disopeProperty1(ipSearch)) ;
		}
		List<Document> ipSearch2 = ipSearch2();
		if(ipSearch2!=null&&ipSearch2.size()>0){
			List<Property> disopeProperty2 = disopeProperty2(ipSearch2);
			if(disopeProperty2!=null&&disopeProperty2.size()>0){
				list.addAll(disopeProperty2);
			}
		}
		return list;
		
	}
	
	/**
	 * 处理代理ip
	 * @param doc
	 * @return
	 * @throws IOException
	 */
	public static List<Property> disopeProperty1(Document doc){
		if(doc!=null&&!"".equals(doc)){
			String text=doc.select("body").text();
			String[] split = text.split(" ");
			List<Property> propertys=new LinkedList<>();
			for(String p:split){
				if(p!=null&&!"".equals(" ")){
					String[] propertyArray = p.split(":");
					if(propertyArray!=null&&propertyArray.length==2){
						if(!"".equals(propertyArray[0])&&!"".equals(propertyArray[1])){
							propertys.add(new Property(propertyArray[0], Integer.parseInt(propertyArray[1])));
						}
					}
				}
			}
			return propertys;
		}
		return null;
	}
	/**
	 * 处理代理ip
	 * @param doc
	 * @return
	 * @throws IOException
	 */
	public static List<Property> disopeProperty2(List<Document> docs){
		List<Property> propertys=new ArrayList<>();
		int n=0;
		for(Document doc:docs){
			int i =0;
			n++;
			if(doc!=null&&!"".equals(doc)){
				Elements select = doc.select("#ip_list");
				if(select!=null){
					Elements ips = select.select(".odd");
					if(ips!=null){
						for(Element e:ips){
							Elements td = e.select("td");
							if(td!=null&&td.size()>3){
								String ip = td.get(1).text();
								String port = td.get(2).text();
								if(ip!=null&&!"".equals(ip)&&port!=null&&!"".equals(port)){
									int parseInt = Integer.parseInt(port);
									propertys.add(new Property(ip, parseInt));
									i++;
								}
							}
						}
					}
				}
				log.info("#####  crawlerProperty 2 "+n+"页"+i+"个  #####");
			}
		}
		return propertys;
	}
	
	/**
	 * 从自定义的代理ip文件里获取代理列表
	 * @param path
	 * @return
	 */
	public static List<Property> getFileProperty(final String path){
		File file = new File(path);
		BufferedReader reader = null;
		List<Property> list = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString =null;
			String[] splits = null;
			String[] splits2 = null;
			Property property = null;
			while((tempString = reader.readLine()) != null){
				splits = null;
				splits = tempString.split(",");
				for(int i=0;i<splits.length;i++){ 
					if(null!=splits[i]&&!"".equals(splits[i])){
						splits2 = null;
						splits2 = splits[i].split(":");
						if(splits2.length==2){
							property = new Property(splits2[0], Integer.parseInt(splits2[1]));
							list.add(property);
						}
					}
				}
			}
		} catch (IOException e) {
			log.error("读取file出错", e.getMessage());
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					
				}
			}
		}
		return list;
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(ScopeEnum.DEPTH.getScope());
	}
}

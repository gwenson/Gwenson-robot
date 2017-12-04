package com.gwenson.search.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gwenson.common.model.SearchBlog;

public class SearchDisposeUtil {

	/**
	 * SearchBlogWithBLOBs的title和text处理
	 * @param sbwb
	 * @param indexTexts
	 * @return
	 */
	public static SearchBlog disposeEm(SearchBlog sbwb, List<String> indexTexts){
		Pattern p = null;
		Matcher m = null;
		String title = sbwb.getTitle();
		String text = sbwb.getText();
		int minStart=text.length();
		for(String indexText:indexTexts){
			p = Pattern.compile(indexText,Pattern.CASE_INSENSITIVE);
			
			m = p.matcher(text);
			StringBuilder tempText = new StringBuilder();
			int lastStart=0;
			int lastEnd=0;
			
			while(m.find()){
				int start = m.start();
				int end = m.end();
				if(lastStart==0&&minStart>start){
					minStart=start;
					System.out.println(indexText+":"+minStart);
				}
				if(start!=lastEnd){
					tempText.append(text.substring(lastEnd, start));
				}
				tempText.append("<em>"+text.substring(start, end)+"</em>");
				lastStart=start;
				lastEnd=end;
			}
			if(lastEnd<text.length()){
				tempText.append(text.substring(lastEnd));
			}
			text = tempText.toString();
			
			m = p.matcher(title);
			StringBuilder tempTitle = new StringBuilder();
			int lastEnd2=0;
			while(m.find()){
				int start = m.start();
				int end = m.end();
				if(start!=lastEnd2){
					tempTitle.append(title.substring(lastEnd2, start));
				}
				tempTitle.append("<em>"+title.substring(start, end)+"</em>");
				lastEnd2=end;
			}
			if(lastEnd2<title.length()){
				tempTitle.append(title.substring(lastEnd2));
			}
			title=tempTitle.toString();
		}
		
		sbwb.setTitle(title);
		if(minStart<text.length()){
			String t=text.substring(minStart); 
			int n=t.length();
			if(n<388){
				int i=388-n;
				if(i<text.length()-n){
					text=text.substring(minStart-i); 
				}
			}else{
				text=t;
			}
		}
		String temp=text;
		text=text.substring(0, text.length()<388?text.length():388);
		int lastIndexOf = text.lastIndexOf("<em>");
		int lastIndexOf2 = text.lastIndexOf("</em>");
		if(lastIndexOf>lastIndexOf2){
			int indexOf = temp.indexOf("</em>", lastIndexOf);
			String substring = temp.substring(lastIndexOf, indexOf+5);
			text=text.substring(0, lastIndexOf)+substring;
		}
		sbwb.setText(text);
		return sbwb;
		
	}
	
	
/*	public static void main(String[] args) {
		SearchBlog searchBlog = new SearchBlog();
		String s="掘金客户端一个帮助开发者成长的社区下载掘金客户端一个帮助开发者成长的社区下载掘金浏览器插件前往掘金翻译计划商务合作关于掘金基本法建议反馈加入我们商务合作专栏介绍©2017掘金"
				+"sssAndroidssJava核心技术（卷一）这本书是国外的大牛写的，与《Java编程思想》齐名的Java图书泰山北斗，非常适合Java入门和巩固基础Java编程思想这本应该是Java界相当出名的一本书了，Java界的金坷垃2、公众号现在微信公众号已经越来越火了，如果能够关注几个比较好的技术公众号，只要利用我们平时的碎片时间，便能够提升我们的技术水平，何乐而不为呢！①stormzhang这个公众号是由「stormzhang」维护的，江湖人称「段子张」或「良心张」分享了很多Android方面的干货，除了Android之外也有很多其他方面的干货，真的相当的良心，我开始写博客也是受了张哥的<em>良心推荐</em>影响，。stormzhang②郭霖郭神出品，必属精品，郭神在CSDN上面"
				+"这本书是国外的，与《Java编程思想》齐名的Java图书泰山北斗，非常适合Java入门<em>大牛写的</em>和巩固基础Java编程思想这本应该是Java界相当出名的一本书了，Java界的金坷垃2、公众号现在微信公众号已经越来越火了，如果能够关注几个比较好的技术公众号，只要利用我们平时的碎片时间，便能够提升我们的技术水平，何乐而不为呢！①stormzhang这个公众号是由「stormzhang」维护的，江湖人称「段子张」或「良心张」分享了很多Android方面的干货，除了";
		searchBlog.setText(s);
		String s2="sssssJava核心技术（卷一）这本书是国外的大牛写的，与《Java编程思想》齐名的Java图书泰山北斗，非常适合Java入门和巩固基础Java编程思想这本应该是Java界相当出名的一本书了，Java界的金坷垃2、公众号现在微信公众号已经越来越火了，如果能够关注几个比较好的技术公众号，只要利用我们平时的碎片时间，便能够提升我们的技术水平，何乐而不为呢！①stormzhang这个公众号是由「stormzhang」维护的，江湖人称「段子张」或「良心张」分享了很多Android方面的干货，除了Android之外也有很多其他方面的干货，真的相当的良心，我开始写博客也是受了张哥的影响，良心推荐。stormzhang②郭霖郭神出品，必属精品，郭神在CSDN上面";
		searchBlog.setTitle(s2);
		List l=new ArrayList<>();
		l.add("大牛写的");
		l.add("sdfsdfsdf");
		SearchBlog disposeEm = disposeEm(searchBlog, l);
		System.out.println("Text-- "+disposeEm.getText());
		System.out.println("Title-- "+disposeEm.getTitle());
	}*/
}

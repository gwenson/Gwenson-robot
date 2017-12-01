package com.gwenson.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExUtil {

	public static  String regExNum(String t){
		String regEx="[^0-9]";   
		Pattern p = Pattern.compile(regEx);   
		Matcher m = p.matcher(t);   
		return m.replaceAll("").trim();
	}
	
	/**
	 * 截取文本中的时间
	 * @param text
	 * @return
	 */
	public static String regExTime(String text){
//		"关于召开知识管理典型设计评审工作会议的通知 2011-4-29 15:30:18公司各部门：兹定于2011年5月4日下午14：00在";
		Pattern pattern = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{1,2}[ ][0-9]{1,2}[:][0-9]{1,2}");  
        Matcher matcher = pattern.matcher(text);  
        String dateStr = null;  
        if(matcher.find()){  
          dateStr = matcher.group(0);  
        }
        if(dateStr==null||"".equals(dateStr)){
        	return null;
        }
        String str =dateStr.toString();  
		return str;
	}
	
	
	public static void main(String[] args) {
		String t="关闭 SeaTomorrow的博客 目录视图 摘要视图 订阅 异步赠书：9月重磅新书升级，本本经典           程序员9月书讯      每周荐书：ES6、虚拟现实、物联网（评论送书） 一种提取HTML网页正文的方法 标签： JavaHTML正文提取新闻数据 2015-09-12 13:39 4859人阅读 评论(1) 收藏 举报 分类： 爬虫 Java 正文提取 版权声明：本文为博主原创文章，未经博主允许不得转载。 这里所说的正文提取主要是针对新闻页面等网页的主体是文字的HTML页面。在做一些与文本处理相关的实验时往往需要大量的文本，虽然网络上已经存在了一些开放数据集如搜狗语料库，但是有的时候也需要根据具体的需求来爬取特定的网站。在我们通过算法获得了需要的HTML页面以后，如何获取页面的正文是一个需要考虑的问题。如果是针对某一个网站的爬取工作，同一网站编码风格往往是一致的，这时只需要简单的浏览一下包含正文的标签，就能发现规律（一个网站的网页正文一般都包含在具有指定id或class的标签下，或者可以根据多个属性的组合来定位正文，因为所有的包含正文的标签结构是一致的）。但是如果在像百度新闻这样的搜索平台中爬新闻，不同的网页来自于不同的网站，没有固定的结构，就需要一种通用的方法。这里我介绍一种自己想到的方法，针对绝大多数的新闻网站都可以有效的提取正文。 这个方法其实思路很简单，分为以下三个步骤： 1. 提取所有的div标签。 2. 提取每一个div标签里包含的p标签内容并保存在变量content中，对于div标签中嵌套的div标签直接过滤掉。 3. 保存具有最长size的content作为该网页的正文。 在研究了很多HTML页面之后，我发现所有的正文都包含在div标签中，而且他们之间的层次关系很近，所以以div标签为出发点可以最大程度减小搜索范围。由于正文多被一个或多个p标签包含，所以单纯的搜索p标签是不切实际的，但是p标签可以作为一种最细化的条件来进行div标签内正文的提取，因为有的时候div标签里还会有其他的标签包括文本内容，比如a标签等，这些是不算正文内容的。当然如果一个div中嵌套了包含正文的div，假如这个div中又恰好包含了含p标签的无用文本，那么这个算法会把正文以及无用文本都提取出来，为了避免这种情况的发生，我们在遍历div标签时如果遇到嵌套的div会直接跳过（反正之后这个嵌套div还是会被我们搜索的）。最后，由于该页面是以文字内容为主体的，所以包含最多字符的div标签，也就是最长size的content为该网页的正文。 下面贴一下demo代码： package getContent;";
	
		String s=regExTime(t);
		System.out.println(s);
	}
	
}

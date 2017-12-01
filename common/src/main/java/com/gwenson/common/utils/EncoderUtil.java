package com.gwenson.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncoderUtil {

	public static String encoder(String str){
		try {
			if(str!=null&&!"".equals(str)){
				str = java.net.URLEncoder.encode(str,"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
		
	}
	
	public static String decoder(String str){
		try {
			if(str!=null&&!"".equals(str)){
				str = java.net.URLDecoder.decode(str,"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 过滤emoji
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {  
        if(source != null)
        {
            Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",Pattern.UNICODE_CASE | Pattern . CASE_INSENSITIVE ) ;
            Matcher emojiMatcher = emoji.matcher(source);
            if ( emojiMatcher.find()) 
            {
                source = emojiMatcher.replaceAll("*");
                return source ; 
            }
        return source;
       }
       return source;  
    }
	
	
	private static boolean isNotEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 * 
	 * @param source
	 * @return
	 */
	public static String filterEmoji2(String source) {
		int len = source.length();
		StringBuilder buf = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (isNotEmojiCharacter(codePoint)) {
				buf.append(codePoint);
			} else {

				buf.append("*");

			}
		}
		return buf.toString();
	}
	
	
	/**
	 * base64编码
	 * 
	 * @param bstr
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("restriction")
	public static String encodeBase64(String str) {
		byte[] bytes = null;
		try {
			bytes = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new sun.misc.BASE64Encoder().encode(bytes);
	}

	/**
	 * base64解码
	 * 
	 * @param str
	 * @return string
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("restriction")
	public static String decodeBase64(String str) {
		byte[] bt = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String string = null;
		try {
			string = new String(bt, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return string;
	}
	
	public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
	
//	public static void main(String[] args) throws IOException {
//		String s="sadasd";//%E6%88%91
//		String encoder = encoder(s);
//		System.out.println(isContainChinese(s));
//		System.getProperties().setProperty("proxySet", "true");
		/*System.getProperties().setProperty("http.proxyHost", "127.0.1.1");
		System.getProperties().setProperty("http.proxyPort", "8123");
		HttpURLConnection connection = (HttpURLConnection) new URL("http://www.baidu.com/").openConnection();
		connection.setConnectTimeout(6000); // 6s
		connection.setReadTimeout(6000);
		connection.setUseCaches(false);
		if(connection.getResponseCode() == 200){
			System.out.println("使用代理IP连接网络成功");
		}else{
			System.out.println("使用代理IP连接网络失败");
		}*/
		/*System.getProperties().setProperty("http.proxyHost", "127.0.1.1");
        System.getProperties().setProperty("http.proxyPort", "8123");*/
		/*Connection connect = Jsoup.connect("https://cloud.tencent.com/product/cmq.html");
		Document doc = connect.userAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
			  .cookie("auth", "token")
			  .timeout(3000)
			  .proxy("119.183.154.208", 8118)
			  .get();
		System.out.println(doc.baseUri());*/
		
//	}
}

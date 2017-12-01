package com.gwenson.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5 {
	private static final Logger log = LoggerFactory.getLogger(MD5.class);
	
	/**
	 * MD5编码UTF-8字符串
	 * @param str 文本
	 * @return 结果
	 */
	public static String toStr(String str) {  
        MessageDigest messageDigest = null;  
  
        try {  
            messageDigest = MessageDigest.getInstance("MD5");  
  
            messageDigest.reset();  
  
            messageDigest.update(str.getBytes("UTF-8"));  

        } catch (NoSuchAlgorithmException e) {  
        	log.info("NoSuchAlgorithmException caught!");  
            log.error("NoSuchAlgorithmException caught!", e);
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();
            log.error("UnsupportedEncoding!", e);
        }
  
        byte[] byteArray = messageDigest.digest();  
  
        StringBuffer md5StrBuff = new StringBuffer();  
  
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            else  
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
        }  
  
        return md5StrBuff.toString();  
    }
	
	/**
	 * MD5编码由文本转UTF-8字节数组
	 */
	public static String toStr(byte[] bytes) {  
        MessageDigest messageDigest = null;  
  
        try {  
            messageDigest = MessageDigest.getInstance("MD5");  
  
            messageDigest.reset();  
  
            messageDigest.update(bytes);  

        } catch (NoSuchAlgorithmException e) {  
        	log.info("NoSuchAlgorithmException caught!");  
            log.error("NoSuchAlgorithmException caught!", e);
        }
  
        byte[] byteArray = messageDigest.digest();  
  
        StringBuffer md5StrBuff = new StringBuffer();  
  
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            else  
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
        }  
  
        return md5StrBuff.toString();  
    }
}

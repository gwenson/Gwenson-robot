package com.gwenson.common.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * properties工具类
 * @author Gwensong  email : gwenson@163.com
 *
 */
public class PropertiesUtil {
	
	private static Logger log=LoggerFactory.getLogger(PropertiesUtil.class);
	/**
	 * 根据Key读取Value
	 * @param filePath
	 * @param key
	 * @return
	 */
    public static String GetValueByKey(String filePath, String key,Class<?> c ) {
        Properties pps = new Properties();
        try {
//            InputStream in = new BufferedInputStream (new FileInputStream(filePath));  
        	InputStream in = c.getClass().getResourceAsStream(filePath);
            pps.load(in);
            String value = pps.getProperty(key);
            log.info(key + " = " + value);
            return value;
            
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 读取Properties的全部信息
     * @param filePath
     * @return
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
	public static Map<String, String> GetAllProperties(String filePath,Class<?> c ) throws IOException {
        Properties pps = new Properties();
        InputStream in = c.getClass().getResourceAsStream(filePath);
        pps.load(in);
        Enumeration en = pps.propertyNames(); //得到配置文件的名字
        Map<String, String> map=new HashMap<String, String>();
        while(en.hasMoreElements()) {
            String strKey = (String) en.nextElement();
            String strValue = pps.getProperty(strKey);
            log.info(strKey + "=" + strValue);
            map.put(strKey, strValue);
        }
		return map;
        
    }
    
    /**
     * 写入Properties信息
     * @param filePath
     * @param pKey
     * @param pValue
     * @throws IOException
     */
    public static void WriteProperties (String filePath, String pKey, String pValue,Class<?> c ) throws IOException {
        Properties pps = new Properties();
        
        InputStream in = c.getClass().getResourceAsStream(filePath);
        //从输入流中读取属性列表（键和元素对） 
        pps.load(in);
        //调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。  
        //强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
        OutputStream out = new FileOutputStream(filePath);
        pps.setProperty(pKey, pValue);
        //以适合使用 load 方法加载到 Properties 表中的格式，  
        //将此 Properties 表中的属性列表（键和元素对）写入输出流  
        pps.store(out, "Update " + pKey + " name");
    }
}

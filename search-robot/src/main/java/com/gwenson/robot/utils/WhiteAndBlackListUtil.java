package com.gwenson.robot.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwenson.robot.page.rule.dao.BlacklistDao;

public class WhiteAndBlackListUtil {
	private static Logger log=LoggerFactory.getLogger(WhiteAndBlackListUtil.class);
	/**
	 * 从自定义的黑白名单文件获取
	 * @param file
	 * @return
	 */
	public static List<String> getListFile(File file){
		BufferedReader reader = null;
		List<String> list = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString =null;
			String[] splits = null;
			String  split = null;
			while((tempString = reader.readLine()) != null){
				splits = null;
				splits = tempString.split(",");
				for(int i=0;i<splits.length;i++){ 
					split =splits[i];
					if(null!=split&&!"".equals(split)){
						list.add(split);
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
	
	/**
	 * InputStream转File
	 * @param ins
	 * @param file
	 */
	public static void inputstreamtofile(InputStream ins,File file){
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			
		}finally{
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					
				}
			}
			if(ins!=null){
				try {
					ins.close();
				} catch (IOException e) {
				}
			}
		}
		
		
	}
	
	/**
	 * 获取File
	 * @param path
	 * @return
	 */
	public static File getFile(String path){
		InputStream ins = BlacklistDao.class.getClass().getResourceAsStream(path);
		File file = new File(path);
		WhiteAndBlackListUtil.inputstreamtofile(ins, file);
		return file;
	}
}

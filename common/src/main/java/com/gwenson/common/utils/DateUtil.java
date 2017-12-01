package com.gwenson.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期处理
 * @author 翟洪坤
 * @date 2015-06-29
 */
public final class DateUtil

{

	public static Logger logger = LoggerFactory.getLogger(DateUtil.class);
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMATHOURS = "yyyy-MM-dd HH:mm";
	public static final String DATE_FORMATDAY = "yyyy-MM-dd";
	public static final String DATE_FORMATTION = "yyyyMMddHHmmss";
	public static final String DATE_SHORT_FORMAT = "yyyy-MM-dd";
	public static final String DATE_SHORT_FORMATTION = "yyyyMMdd";
	public static final String DATE_SHORTYEAR_FORMATTION = "yyMMdd";
	public static final String DATE_SHORT_FORMAT_YEAR = "yyyy";
	public static final String DATE_SHORT_FORMAT_MONTH = "yyyy-MM";
	public static final String DATE_MONTH_DAY_FORMAT = "MM-dd";
	
    /**
     * 标准时间格式转化为字符串
     * @param Date 日期
     * @return String 日期字符串
     */
    public static String date2String(Date date){
    	String dateStr = "";
    	try {
			if(date != null){
				dateStr = new SimpleDateFormat(DATE_FORMAT).format(date.getTime());
			}
		} catch (Exception e) {
			logger.error("日期转化为字符串出错！");
			e.printStackTrace();
		}
        return dateStr;
    }
    /**
     * 月日时间格式转化为字符串
     * @param date 日期
     * @return String 日期字符串  yyyy-MM-dd 00:00
     */
    public static Date cover2ZeroTime(Date date, Boolean flag){
    	Date d = null;
    	try {
    		if(date != null){
    			String dateStr = new SimpleDateFormat(DATE_SHORT_FORMAT).format(date.getTime());
    			if (flag) {
    				dateStr+=" 00:00:00";
				}else{
					dateStr+=" 23:59:59";
				}
    			d = new SimpleDateFormat(DATE_FORMAT).parse(dateStr);
    		}
    	} catch (Exception e) {
    		logger.error("日期转化为字符串出错！");
    		e.printStackTrace();
    	}
    	return d;
    }
    
    /**
     * 月日时间格式转化为字符串
     * @param date 日期
     * @return String 日期字符串  yyyyMMddHHmmss
     */
    public static String dates2String(Date date){
    	String dateStr = "";
    	try {
    		if(date != null){
    			dateStr = new SimpleDateFormat(DATE_FORMATTION).format(date.getTime());
    		}
    	} catch (Exception e) {
    		logger.error("日期转化为字符串出错！");
    		e.printStackTrace();
    	}
    	return dateStr;
    }
    
    /**
     * 字符串转化为标准时间格式
     * @param dateStr 日期字符串
     * @return Date 日期
     */
    public static Date string2Date(String dateStr){
    	Date date = null;
    	if(dateStr != null && !("").equals(dateStr)){
    		try {
    			date = new SimpleDateFormat(DATE_FORMAT).parse(dateStr);
    		} catch (ParseException e) {
    			logger.error("字符串转化为日期出错！");
    			e.printStackTrace();
    		}
    	}
    	return date;
    }
    
    /**
     * 短时间格式转化为字符串
     * @param date 日期
     * @return String 日期字符串 
     */
    public static String datesShort2String(Date date){
    	String dateStr = "";
    	try {
    		if(date != null){
    			dateStr = new SimpleDateFormat(DATE_SHORT_FORMATTION).format(date.getTime());
    		}
    	} catch (Exception e) {
    		logger.error("日期转化为字符串出错！");
    		e.printStackTrace();
    	}
    	return dateStr;
    }
    
    /**
     * 字符串转化为标准时间格式
     * @param dateStr 日期字符串
     * @param format 日期格式
     * @return Date 日期
     */
    public static Date string2Date(String dateStr,String format){
        Date date = null;
        if(dateStr != null && !("").equals(dateStr)){
                try {
                        date = new SimpleDateFormat(format).parse(dateStr);
                } catch (ParseException e) {
                        logger.error("字符串转化为日期出错！");
                        e.printStackTrace();
                }
        }
        return date;
    }
    
    /**
     * 短时间格式转化为字符串
     * @param date 日期
     * @return String 日期字符串 
     */
    public static String datesShortYear2String(Date date){
    	String dateStr = "";
    	try {
    		if(date != null){
    			dateStr = new SimpleDateFormat(DATE_SHORT_FORMATTION).format(date.getTime());
    			dateStr = dateStr.substring(2, 8);
    		}
    	} catch (Exception e) {
    		logger.error("日期转化为字符串出错！");
    		e.printStackTrace();
    	}
    	return dateStr;
    }
    
    /**
     * 短时间格式转化为字符串
     * @param date 日期
     * @return String 日期字符串 
     */
    public static String dateShort2String(Date date){
    	String dateStr = "";
    	try {
			if(date != null){
				dateStr = new SimpleDateFormat(DATE_SHORT_FORMAT).format(date.getTime());
			}
		} catch (Exception e) {
			logger.error("日期转化为字符串出错！");
			e.printStackTrace();
		}
        return dateStr;
    }
    
    /**
     * 短时间格式转化为字符串
     * @param date 日期
     * @return String 日期字符串 
     */
    public static String dateShort2String(Long time){
    	String dateStr = "";
    	try {
			if(time != null){
				dateStr = new SimpleDateFormat(DATE_SHORT_FORMAT).format(time);
			}
		} catch (Exception e) {
			logger.error("日期转化为字符串出错！");
			e.printStackTrace();
		}
        return dateStr;
    }
    
    /**
     * 字符串转化为短时间格式
     * @param dateStr 日期字符串
     * @return Date 日期
     */
    public static Date string2DateShort(String dateStr){
    	Date date = null;
    	if(dateStr != null && !("").equals(dateStr)){
    		try {
    			date = new SimpleDateFormat(DATE_SHORT_FORMAT).parse(dateStr);
    		} catch (ParseException e) {
    			logger.error("字符串转化为日期出错！");
    			e.printStackTrace();
    		}
    	}
    	return date;
    }
    
    /**
     * 月日时间格式转化为字符串
     * @param date 日期
     * @return String 日期字符串 
     */
    public static String dateMonthAndDay2String(Date date){
    	String dateStr = "";
    	try {
			if(date != null){
				dateStr = new SimpleDateFormat(DATE_MONTH_DAY_FORMAT).format(date.getTime());
			}
		} catch (Exception e) {
			logger.error("日期转化为字符串出错！");
			e.printStackTrace();
		}
        return dateStr;
    }
    
    /**
     * 字符串转化为月日时间格式
     * @param dateStr 日期字符串
     * @return Date 日期
     */
    public static Date string2DateMonthAndDay(String dateStr){
    	Date date = null;
    	if(dateStr != null && !("").equals(dateStr)){
    		try {
    			date = new SimpleDateFormat(DATE_MONTH_DAY_FORMAT).parse(dateStr);
    		} catch (ParseException e) {
    			logger.error("字符串转化为日期出错！");
    			e.printStackTrace();
    		}
    	}
    	return date;
    }
    
    
    /**
     * 年时间格式转化为字符串 yyyy
     * @param date 日期
     * @return String 日期字符串 yyyy
     */
    public static String date2YearString(Date date){
    	String dateStr = "";
    	try {
			if(date != null){
				dateStr = new SimpleDateFormat(DATE_SHORT_FORMAT_YEAR).format(date.getTime());
			}
		} catch (Exception e) {
			logger.error("日期转化为字符串出错！");
			e.printStackTrace();
		}
        return dateStr;
    }
    /**
     * 年月时间格式转化为字符串 yyyy-MM
     * @param date 日期
     * @return String 日期字符串 yyyy-MM
     */
    public static String date2YearMonthString(Date date){
    	String dateStr = "";
    	try {
    		if(date != null){
    			dateStr = new SimpleDateFormat(DATE_SHORT_FORMAT_MONTH).format(date.getTime());
    		}
    	} catch (Exception e) {
    		logger.error("日期转化为字符串出错！");
    		e.printStackTrace();
    	}
    	return dateStr;
    }
    /**
     * 年份的加减运算
     * @param Date
     * @param i
     * @return
     */
    public static Date addOrMinusYear(Date nowDate, int i) { 
        Date rtn = null;  
        GregorianCalendar cal = new GregorianCalendar();  
        cal.setTime(nowDate);  
        cal.add(1, i);  
        rtn = cal.getTime();  
        return rtn;  
    }  
    /**
     * 月份的加减运算
     * @param Date
     * @param i
     * @return
     */
    public static Date addOrMinusMonth(Date nowDate, int i) { 
    	Date rtn = null;  
    	GregorianCalendar cal = new GregorianCalendar();  
    	cal.setTime(nowDate);  
    	cal.add(2, i);  
    	rtn = cal.getTime();  
    	return rtn;  
    }  
    
    public static Timestamp getCurTime(){
		return new Timestamp(System.currentTimeMillis());
	}
    
	/**
	 * @author wugs
	 * 获取下几天
	 * @param currentDate
	 * @param time
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static Date nextDate(Date currentDate,int time) {
		Date date = null ;
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(currentDate);
		calendar.add(calendar.DATE, time);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime();
		return date;
	}
    
}

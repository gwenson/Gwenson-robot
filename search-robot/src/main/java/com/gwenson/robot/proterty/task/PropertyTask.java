package com.gwenson.robot.proterty.task;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.gwenson.robot.proterty.model.Property;
import com.gwenson.robot.proterty.redis.dao.PropertyRedisDao;
import com.gwenson.robot.utils.PropertyUtil;

/**
 * 爬代理ip数据任务类
 * 
 * @author gwenson email : gwenson@163.com
 *
 */
@Component
public class PropertyTask {
	private Logger log = LoggerFactory.getLogger(PropertyTask.class);
	@Autowired
	private PropertyRedisDao propertyRedisDao;
	private List<Property> propertys = null;
	private List<Property> tempPropertys = null;

	/**
	 * 定时爬代理ip
	 */
//	@Scheduled(fixedRate = 6000 * 10 * 5)
	public void crawlerProperty() {
		log.info("crawlerProperty runing ....");
		propertys = new LinkedList<>();
		tempPropertys = null;
		try {
			// propertys = propertyRedisDao.getPropertys();
			if (propertys == null || propertys.size() <= 0) {
				tempPropertys = PropertyUtil.getTempPropertys();
				if (tempPropertys != null && tempPropertys.size() > 0) {
					int size = tempPropertys.size();
					Thread[] threadArray = new Thread[size];
					for (int n1 = 0; n1 < threadArray.length; n1++) {
						threadArray[n1] = new Thread(new Runnable() {
							public void run() {
								String name = Thread.currentThread().getName();
								int index = Integer.valueOf(name);
								addProperty(PropertyUtil.testIP(tempPropertys.get(index)));
							}
						});
						threadArray[n1].setName("" + n1);
						threadArray[n1].start();
					}
					for (int n2 = 0; n2 < threadArray.length; n2++) {
						threadArray[n2].join();
					}
					log.info(JSONArray.toJSONString(propertys));
					propertyRedisDao.set(propertys);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("线程 [ " + Thread.currentThread().getName() + " ] 捉取代理端口失败 ", e);
		}
	}

	/**
	 * 添加到 propertys 的 list
	 * 
	 * @param testIP
	 */
	private synchronized void addProperty(final Property testIP) {
		if (testIP != null && testIP.getIp() != null && !"".equals(testIP.getIp()) && testIP.getPort() != null
				&& !"".equals(testIP.getPort())) {
			propertys.add(testIP);
			log.info("Temp list add property:[ " + "ip:" + testIP.getIp() + " , port:" + testIP.getPort() + " ] size:"
					+ propertys.size());
		}
	}
	
}

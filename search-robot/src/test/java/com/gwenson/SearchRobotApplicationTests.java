package com.gwenson;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.gwenson.robot.page.redis.dao.DepthLockDao;
import com.gwenson.robot.page.redis.dao.WideQueueUrlDao;
import com.gwenson.robot.page.service.DispatchTaskService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchRobotApplicationTests {

//	@Autowired
//	private SearchBlogRepository searchBlogRepository;
	@Autowired
	private DispatchTaskService dispatchTaskService;
//	@Autowired
//	private EsIndexService esIndexService;
//	@Autowired
//	private IdenticalUrlDao identicalUrlDao;
	@Autowired
	private StringRedisTemplate redisTemplate;
//	@Autowired
//	private IdenticTextDao identicTextDao;
	@Autowired
	private DepthLockDao depthLockDao;
	@Autowired
	private WideQueueUrlDao wideQueueUrlDao;
	
	@Test
	public void contextLoads() throws IOException {
		
//		wideQueueUrlDao.delete();
		
//		boolean lock4 = depthLockDao.isLock();
//		System.out.println(lock4);
//		boolean lock3 = depthLockDao.setLock("1");
//		System.out.println(lock3);
//		boolean lock2 = depthLockDao.isLock();
//		System.out.println(lock2);
//		boolean lock = depthLockDao.deleteLock();
//		System.out.println(lock);
		/*SearchBlog searchBlog = new SearchBlog();
		searchBlog.setUrl("1");
		searchBlogRepository.create(searchBlog);*/
		/*SearchResoucefile searchResoucefile=new SearchResoucefile();
		searchResoucefile.setText("test");
		searchResoucefile.setUrl("urlTest");
		searchResoucefile.setAuthor("author");
		searchResoucefile.setAuthorTime("2017-05-30 13:19:18");
		searchResoucefile.setAuthorUrl("authorUrl");
		searchResoucefile.setHot(111l);
		searchResoucefile.setInputTime(new Date());
		searchResoucefile.setIsOfficial(1);
		searchResoucefile.setReviewNumber(11);
		searchResoucefile.setScanNumber(0);
		searchResoucefile.setTarget("target");
		searchResoucefile.setTitle("title");
		searchResoucefile.setType(1);
		SearchResoucefile create = searchResouceFileRepository.create(searchResoucefile);*/
		
		/*long set = identicalUrlDao.set("http://blog.csdn.net/blogdevteam/article/details/77651442111");
		boolean contains = identicalUrlDao.contains("http://blog.csdn.net/blogdevteam/article/details/77651442111");
		System.out.println(set);
		System.out.println(contains);*/
		
		/*String code= "0010111101001111"+"0000011001100111"+"0001010101110110"+"0100100000100100";
		String code2="0010101100001111"+"0000011001110111"+"0001010101100110"+"0100000000100100";
		String code3="0010111101001101"+"0000000001110111"+"0001010101000010"+"0100000000000100";
		boolean contains = identicTextDao.contains(code);
		System.out.println(contains);
		long set = identicTextDao.set(code);
		boolean contains2 = identicTextDao.contains(code);
		System.out.println(set);
		System.out.println(contains2);*/
		
		/*SearchBlog searchBlog =new SearchBlog();
		String url = "http://ssuupv.blog.163.com/loftarchive/唔唔";
		searchBlog.setUrl(url);
		searchBlog.setTitle(url);
		searchBlog.setDescription(url);
		searchBlog.setKeywords("url");
		searchBlog.setPostTime(new Date());
		searchBlog.setText(url);
		searchBlogRepository.create(searchBlog);*/
		/*String url="https://github.com/TanNingMeng";
		Connection connect = Jsoup.connect(url);
		//http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=11000002000019
		//http://weibo.com/u/1734298257?refer_flag=1005050003_
		Document accessUrl = connect
//			  .data("query", "Java")
				  .userAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
				  .cookie("auth", "token")
				  .timeout(3000)
				  .get();

		if(accessUrl!=null&&!"".equals(accessUrl)){
			String text = DocumentUtil.getText(accessUrl);
			text = text.replaceAll(" ", "");
			int max=65535/10/3;
			if(text!=null&&!"".equals(text)){
				String description = DocumentUtil.getDescription(accessUrl);
				String keywords = DocumentUtil.getKeywords(accessUrl);
				String postTime = DocumentUtil.getPostTime(accessUrl);
				String title = DocumentUtil.getTitle(accessUrl);
				SearchBlog searchBlog = new SearchBlog();
				searchBlog.setDescription(description);
				searchBlog.setKeywords(keywords);
				Date postTime2 = null;
				if(postTime!=null&&!"".equals(postTime)){
					postTime2 = DateUtil.string2Date(postTime,DateUtil.DATE_FORMATHOURS);
				}
				searchBlog.setPostTime(postTime2);
				if(text.length()>max){
					text=text.substring(0, max-1);
				}
				searchBlog.setText(EncoderUtil.filterEmoji(text));
				searchBlog.setUrl(url);
				searchBlog.setTitle(title);
				try {
					String text2 = searchBlog.getText();
					System.out.println(text2);
					searchBlogRepository.create(searchBlog);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}*/
		
		/*EsIndex esIndex = new EsIndex();
		esIndex.setSbId(3l);
		esIndex.setTableId(0);
		esIndex.setId(esIndex.getSbId()+"."+esIndex.getTableId());
		esIndex.setTitle("爱是一阵风sdf");
		esIndex.setDescription("爱是一阵风，吹到哪里就爱到哪里！");
		esIndex.setKeywords("爱是一阵风，吹到哪里就爱到哪里！爱是你我");
		esIndex.setPostTime(new Date());
		EsIndex saveEsIndex = esIndexService.saveEsIndex(esIndex);
		System.out.println(saveEsIndex);*/
		
		/*String ITERATE_URL_REDIS_KEY="ITERATE_URL_REDIS_KEY";
		String url="http://blog.csdn.net/blogdevteam/article/details/77651442111";
		String url2="http://blog.csdn.net/blogdevteam/article/details/776514421112";*/
		
		/*SetOperations<String, String> boundSetOps = redisTemplate.opsForSet();
		boundSetOps.add(ITERATE_URL_REDIS_KEY, values);

		final byte[] rawKey = rawKey(key);
		final byte[][] rawValues = rawValues(values);
		return execute(new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection) {
				return connection.sAdd(rawKey, rawValues);
			}
		}, true);*/
	
		
		/*Long execute = redisTemplate.execute(new RedisCallback<Long>() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				String str = MD5.toStr(url);
				RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
				int key2 = NumberUtil.getStringModBitCount(str, NumberUtil.BIT_COUT);
				System.out.println(str+":"+key2);
				byte[] key = redisTemplate.getStringSerializer().serialize(ITERATE_URL_REDIS_KEY+key2);
				byte[] v = valueSerializer.serialize(str);
				return connection.sAdd(key, v);
			}
		}, true);
		System.out.println("redis1: "+execute);*/
		/*Long execute2 = redisTemplate.execute(new RedisCallback<Long>() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				String str = MD5.toStr(url2);
				RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
				int key2 = NumberUtil.getStringModBitCount(str, NumberUtil.BIT_COUT);
				System.out.println(str+":"+key2);
				byte[] key = redisTemplate.getStringSerializer().serialize(ITERATE_URL_REDIS_KEY+key2);
				byte[] v = valueSerializer.serialize(str);
				return connection.sAdd(key, v);
			}
		}, true);
		System.out.println("redis2: "+execute2);
		Boolean b=redisTemplate.execute(new RedisCallback<Boolean>() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				String str = MD5.toStr(url2);
				RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
				int key2 = NumberUtil.getStringModBitCount(str, NumberUtil.BIT_COUT);
				byte[] key = redisTemplate.getStringSerializer().serialize(ITERATE_URL_REDIS_KEY+key2);
				if(connection.exists(key)){
					 Set<String> set = SerializationUtils.deserialize(connection.sMembers(key), valueSerializer);
					 if(set!=null){
						 return set.contains(str);
					 }
				}
				return false;
			}
		}, true);
		System.out.println("redis1:"+b);
		
		SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
		String str = MD5.toStr(url2);
		int key2 = NumberUtil.getStringModBitCount(str, NumberUtil.BIT_COUT);
		Set<String> set = opsForSet.members(ITERATE_URL_REDIS_KEY+key2);
		boolean contains = set.contains(str);
		System.out.println(contains);*/
		
		/*String simHashCode="1001101011101010001111000101011011111000110000000000000000000100";
		long set = identicTextDao.set(simHashCode);
		System.out.println(set);
		boolean contains = identicTextDao.contains(simHashCode);
		System.out.println(contains);*/
	}

}

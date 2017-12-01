# Gwenson-robot爬虫机器人
**这是一个使用JAVA语言开发的爬虫机器人，利用redis的list实现匀速爬取目标，并实现了url去重和目标内容去重。其中目标内容去重是先利用word分词然后再利用simhash算法得到一个SimHashCode值，再利用抽屉原理判断海明距离从而得到相似度。注意：使用JDK1.8**

[TOC]

### 我的项目应用部署地址

​	[Gwenson 个人爬虫搜索引擎](http:\\www.gwenson.com)





### 工程项目的介绍

1. common模块项目是一个公共工具类依赖包模块。

   结构介绍：

   ```
   common
   └─src
       └─main
         └─java
             └─com
                 └─gwenson
                    └─common
                         ├─dao
                         │  └─redis
                         ├─model
                         └─utils
       
   ```

   ​

2. search-robot模块项目是一个基于spring boot框架的爬虫机器人模块。

   结构介绍：

   ```wiki
   search-robot
   └─src
       └─main
         ├─java
         │  └─com
         │      └─gwenson
         │          ├─controller
         │          ├─listener
         │          └─robot
         │              ├─cahce
         │              ├─config
         │              ├─page
         │              │  ├─dto
         │              │  ├─redis
         │              │  │  └─dao
         │              │  ├─rule
         │              │  │  ├─dao
         │              │  │  └─service
         │              │  │      └─impl
         │              │  └─service
         │              │      └─impl
         │              ├─proterty
         │              │  ├─model
         │              │  ├─redis
         │              │  │  └─dao
         │              │  └─task
         │              └─utils
         └─resources
              ├─application.properties   //启动配置
              ├─application-prod.properties  //生成环境
              ├─application-dev.properties   //测试环境
              ├─blacklist.txt			//黑名单
              ├─whitelist.txt			//白名单
              └─logback.xml			//logback日志配置
   ```

   ​

   search-robot的src/main/resouces下的文件介绍

   ​	application.properties是项目启动的必要配置文件：

   ```properties
   #项目监听端口
   server.port=8081
   #项目路径
   #server.context-path=/
   #引用生配置，prod是生产，dev是测试
   spring.profiles.active=prod
   #容器启动完成后的监听类
   context.listener.classes=com.gwenson.listener.ApplicationStartup

   #是否开启代理ip,true开启,false不开启
   gwensong.robot.property.start=false
   #是否开启自动定时爬取代理ip功能，true开启，false不开启
   gwensong.robot.property.autoScheduled=false
   #自定义代理ip列表
   #gwensong.robot.property.userDefinedPath=D:/gwenson/property.txt
   gwensong.robot.property.userDefinedPath=

   #分表数，用来生成tableId
   database.table.num=64


   #设置爬虫的优先级别，广度优先为:wide;深度度优先为:depth
   gwensong.robot.search.scope=depth
   #配置白名单列表
   gwensong.robot.search.whitelistPath=/whitelist.txt
   #配置黑名单列表
   gwensong.robot.search.blacklistPath=/blacklist.txt

   #web controller启动爬虫用户名
   gwenson.robot.user.username=root
   #web controller启动爬虫密码
   gwenson.robot.user.password=123456

   ```

   ​	application-prod.properties或application-dev.properties是区分启动的环境配置，和application.properties里的spring.profiles.active=prod对应：

   ```properties

   profile = prod_envrimont

   #tomcat 编码设置，默认是UTF-8
   server.tomcat.uri-encoding=UTF-8
   #tomcat的启动日志logs
   server.tomcat.accesslog.directory=/gwenson/logs/search-robot/tomcat.log

   # REDIS (RedisProperties) 
   # database name   
   spring.redis.database=0
   # localhost # server host redis的地址 
   spring.redis.host=127.0.0.1
   # server password  redis的密码
   spring.redis.password=

   # connection port  redis的监听端口
   spring.redis.port=6379 
   # pool settings ...  
   spring.redis.pool.max-idle=8 
   spring.redis.pool.min-idle=0  
   spring.redis.pool.max-active=-1  
   spring.redis.pool.max-wait=-1
   spring.redis.timeout=100000

   ```



### 怎么使用?

```markdown
1、安装redis
(因为该项目是居于redis作为内容储存，值得注意的是：爬取到的内容是以消息方式推送到redis的list的，如果想把内储存到Mysql数据库请看。)

2、在项目src/main/resources下的application-prod.properties里的spring.redis.host= 
spring.redis.password= 
配置你redis地址和密码

3、在项目的根目录下运行 mvn clean install package -Dmaven.test.skip=true
  用maven编译好jar包
  
4、执行jar运行命令启动
java -jar /gwenson/app/search-robot/search-robot-0.0.1-SNAPSHOT.ja

5、在浏览器地址执行下面url启动爬虫
http://localhost:8081/start/search?username=root&password=123456
或
curl "http://localhost:8081/start/search?username=root&password=123456"
```



### 项目开源地址

​	[在开源中国Gitee上](https://gitee.com/wgs123/Gwenson-robot)

​	[在Github上](https://github.com/gwenson/Gwenson-robot)


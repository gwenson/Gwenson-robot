# Gwenson-robot爬虫机器人
**这是一个使用JAVA语言开发的爬虫机器人，利用redis的list实现匀速爬取目标，并实现了url去重和目标内容去重。其中目标内容去重是先利用word分词然后再利用simhash算法得到一个SimHashCode值，再利用抽屉原理判断海明距离从而得到相似度。注意：使用JDK1.8**

[TOC]

### 我的项目应用部署地址

​	[Gwenson 个人爬虫搜索引擎](http://www.gwenson.com)



### 该项目都用到了哪些技术？

​	spring boot、spring data redis、jsoup、word、mybatis、spring data elasticsearch、spring mvc、bootstrap、JSP等等

​	

### 工程项目结构的介绍

####  common 模块项目是一个公共工具类依赖包模块。

   项目结构介绍：

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

   

####  search-robot模块项目是一个基于spring boot框架的爬虫机器人模块。


   项目结构介绍：

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

   	application-prod.properties或application-dev.properties是区分启动的环境配置，和application.properties里的spring.profiles.active=prod对应：

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



#### search-view模块是一个基于spring mvc的搜索引擎web门户应用

项目结构介绍:

```wiki
src
├─main
│  ├─java
│  │  └─com
│  │      └─gwenson
│  │          └─search
│  │              ├─controller
│  │              ├─dao
│  │              │  ├─elasticsearch
│  │              │  └─xml
│  │              ├─filter
│  │              ├─interceptor
│  │              ├─listener
│  │              ├─model
│  │              ├─service
│  │              │  └─imp
│  │              ├─servlet
│  │              └─utils
│  ├─resources
│  │  └─spring //spring datasource位置
│  └─webapp
│      └─WEB-INF
│          ├─static
│          │  ├─conf
│          │  └─html  //首页 index.html
└──────────└─views   //结果页 search.jsp
```

​

 search-view的src/main/resouces下的文件介绍

​	jdbc.properties是search-view的mysql数据库和redis连接用户和密码存放文件：

```properties
#mysql的数据库连接配置
jdbc.driverClassName=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://127.0.0.1:3306/search?useUnicode=true&amp;characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=true
jdbc.username=root
jdbc.password=123456
jdbc.maxActive=20  
jdbc.maxIdle=10
jdbc.maxWait=4000

#Redis settings
#Redis的配置
redis.host=127.0.0.1
redis.port=6379
redis.password=
redis.maxIdle=300
redis.minIdle=10
redis.maxActive=600
redis.maxWait=1000
redis.testOnBorrow=true
redis.testOnReturn=true
redis.timeout=1000000

```




  ### 怎么使用?

####  (一) 部署启动search-robot

 ```
1、安装redis

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



#### (二) 部署启动search-view


1、安装MYSQL
​	然后在src/main/resouces下的jdbc.properties的mysql的数据库连接配置配置上你的地址属性

2、安装elasticsearch-2.3.4版本
​	安装word分词插件[点击这里查看安装方法](https://my.oschina.net/u/2532423/blog/1552640)

​	在src/main/resouces下的spring.xml配置elasticsearch的服务地址:

```
<elasticsearch:transport-client id="client" cluster-nodes="127.0.0.1:9300" cluster-name="my-application" />
```

127.0.0.1:9300改为你的服务地址和端口

3、编译项目

​	在Gwenson-robot根目录下执行

```
$ mvn clean install package -Dmaven.test.skip=true
```

3、安装tomcat

​	修改tomcat下的conf下的server.xml的

```
 <Context docBase="D:\eclipse workspace\Gwenson\search-view\target\search-view" path="/" reloadable="true" /></Host>
```

​	docBase改为你的项目地址

​	启动tomcat下的bin下的startup.sh

​	




### 项目开源地址

​	[在开源中国Gitee上](https://gitee.com/wgs123/Gwenson-robot)

​	[在Github上](https://github.com/gwenson/Gwenson-robot)


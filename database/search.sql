-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.6.35-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      linux-glibc2.5
-- HeidiSQL 版本:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 search 的数据库结构
CREATE DATABASE IF NOT EXISTS `search` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `search`;

-- 导出  表 search.constant_advert 结构
CREATE TABLE IF NOT EXISTS `constant_advert` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='广告常量代码';

-- 数据导出被取消选择。
-- 导出  过程 search.createTable 结构
DELIMITER //
CREATE DEFINER=`root`@`%` PROCEDURE `createTable`()
BEGIN
DECLARE `@i` int(11);     
DECLARE `@createSql` VARCHAR(2560); 
DECLARE `@createIndexSql1` VARCHAR(2560);     
DECLARE `@createIndexSql2` VARCHAR(2560);
DECLARE `@createIndexSql3` VARCHAR(2560);

set `@i`=10; 

WHILE  `@i`< 64 DO                 
    
 -- `M_ID` bigint AUTO_INCREMENT PRIMARY KEY NOT NULL,
 -- 创建表        
 SET @createSql = CONCAT('CREATE TABLE `search_blog_',`@i`,'` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`tableId` INT(11) NOT NULL DEFAULT ',`@i`,',
	`title` VARCHAR(100) NULL DEFAULT NULL,
	`description` VARCHAR(300) NULL DEFAULT NULL,
	`url` VARCHAR(500) NULL DEFAULT NULL,
	`keywords` VARCHAR(300) NULL DEFAULT NULL,
	`text` TEXT NULL,
	`postTime` DATETIME NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `POST TIME` (`postTime`)
)
COMMENT=''博客''
COLLATE=''utf8_general_ci''
ENGINE=MyISAM
'
 ); 
 prepare stmt from @createSql; 
 execute stmt;                             

 -- 创建索引    
 --  set @createIndexSql1  = CONCAT('create index `POST TIME` on search_blog_',`@i`,'(`postTime`);');
 --  prepare stmt from @createIndexSql1; 
-- execute stmt; 
	SET `@i`= `@i`+1; 
	END WHILE;
END//
DELIMITER ;

-- 导出  表 search.search_blog_0 结构
CREATE TABLE IF NOT EXISTS `search_blog_0` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '0',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_1 结构
CREATE TABLE IF NOT EXISTS `search_blog_1` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '1',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_10 结构
CREATE TABLE IF NOT EXISTS `search_blog_10` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '10',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_11 结构
CREATE TABLE IF NOT EXISTS `search_blog_11` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '11',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_12 结构
CREATE TABLE IF NOT EXISTS `search_blog_12` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '12',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_13 结构
CREATE TABLE IF NOT EXISTS `search_blog_13` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '13',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_14 结构
CREATE TABLE IF NOT EXISTS `search_blog_14` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '14',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_15 结构
CREATE TABLE IF NOT EXISTS `search_blog_15` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '15',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_16 结构
CREATE TABLE IF NOT EXISTS `search_blog_16` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '16',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_17 结构
CREATE TABLE IF NOT EXISTS `search_blog_17` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '17',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_18 结构
CREATE TABLE IF NOT EXISTS `search_blog_18` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '18',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_19 结构
CREATE TABLE IF NOT EXISTS `search_blog_19` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '19',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_2 结构
CREATE TABLE IF NOT EXISTS `search_blog_2` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '2',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_20 结构
CREATE TABLE IF NOT EXISTS `search_blog_20` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '20',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_21 结构
CREATE TABLE IF NOT EXISTS `search_blog_21` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '21',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_22 结构
CREATE TABLE IF NOT EXISTS `search_blog_22` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '22',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_23 结构
CREATE TABLE IF NOT EXISTS `search_blog_23` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '23',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_24 结构
CREATE TABLE IF NOT EXISTS `search_blog_24` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '24',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_25 结构
CREATE TABLE IF NOT EXISTS `search_blog_25` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '25',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_26 结构
CREATE TABLE IF NOT EXISTS `search_blog_26` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '26',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_27 结构
CREATE TABLE IF NOT EXISTS `search_blog_27` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '27',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_28 结构
CREATE TABLE IF NOT EXISTS `search_blog_28` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '28',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_29 结构
CREATE TABLE IF NOT EXISTS `search_blog_29` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '29',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_3 结构
CREATE TABLE IF NOT EXISTS `search_blog_3` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '3',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_30 结构
CREATE TABLE IF NOT EXISTS `search_blog_30` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '30',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_31 结构
CREATE TABLE IF NOT EXISTS `search_blog_31` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '31',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_32 结构
CREATE TABLE IF NOT EXISTS `search_blog_32` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '32',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_33 结构
CREATE TABLE IF NOT EXISTS `search_blog_33` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '33',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_34 结构
CREATE TABLE IF NOT EXISTS `search_blog_34` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '34',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_35 结构
CREATE TABLE IF NOT EXISTS `search_blog_35` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '35',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_36 结构
CREATE TABLE IF NOT EXISTS `search_blog_36` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '36',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_37 结构
CREATE TABLE IF NOT EXISTS `search_blog_37` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '37',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_38 结构
CREATE TABLE IF NOT EXISTS `search_blog_38` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '38',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_39 结构
CREATE TABLE IF NOT EXISTS `search_blog_39` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '39',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_4 结构
CREATE TABLE IF NOT EXISTS `search_blog_4` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '4',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_40 结构
CREATE TABLE IF NOT EXISTS `search_blog_40` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '40',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_41 结构
CREATE TABLE IF NOT EXISTS `search_blog_41` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '41',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_42 结构
CREATE TABLE IF NOT EXISTS `search_blog_42` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '42',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_43 结构
CREATE TABLE IF NOT EXISTS `search_blog_43` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '43',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_44 结构
CREATE TABLE IF NOT EXISTS `search_blog_44` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '44',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_45 结构
CREATE TABLE IF NOT EXISTS `search_blog_45` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '45',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_46 结构
CREATE TABLE IF NOT EXISTS `search_blog_46` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '46',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_47 结构
CREATE TABLE IF NOT EXISTS `search_blog_47` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '47',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_48 结构
CREATE TABLE IF NOT EXISTS `search_blog_48` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '48',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_49 结构
CREATE TABLE IF NOT EXISTS `search_blog_49` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '49',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_5 结构
CREATE TABLE IF NOT EXISTS `search_blog_5` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '5',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_50 结构
CREATE TABLE IF NOT EXISTS `search_blog_50` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '50',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_51 结构
CREATE TABLE IF NOT EXISTS `search_blog_51` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '51',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_52 结构
CREATE TABLE IF NOT EXISTS `search_blog_52` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '52',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_53 结构
CREATE TABLE IF NOT EXISTS `search_blog_53` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '53',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_54 结构
CREATE TABLE IF NOT EXISTS `search_blog_54` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '54',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_55 结构
CREATE TABLE IF NOT EXISTS `search_blog_55` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '55',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_56 结构
CREATE TABLE IF NOT EXISTS `search_blog_56` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '56',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_57 结构
CREATE TABLE IF NOT EXISTS `search_blog_57` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '57',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_58 结构
CREATE TABLE IF NOT EXISTS `search_blog_58` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '58',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_59 结构
CREATE TABLE IF NOT EXISTS `search_blog_59` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '59',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_6 结构
CREATE TABLE IF NOT EXISTS `search_blog_6` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '6',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_60 结构
CREATE TABLE IF NOT EXISTS `search_blog_60` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '60',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_61 结构
CREATE TABLE IF NOT EXISTS `search_blog_61` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '61',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_62 结构
CREATE TABLE IF NOT EXISTS `search_blog_62` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '62',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_63 结构
CREATE TABLE IF NOT EXISTS `search_blog_63` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '63',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_7 结构
CREATE TABLE IF NOT EXISTS `search_blog_7` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '7',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_8 结构
CREATE TABLE IF NOT EXISTS `search_blog_8` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '8',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
-- 导出  表 search.search_blog_9 结构
CREATE TABLE IF NOT EXISTS `search_blog_9` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tableId` int(11) NOT NULL DEFAULT '9',
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `keywords` varchar(300) DEFAULT NULL,
  `text` text,
  `postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POST TIME` (`postTime`)
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='博客';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

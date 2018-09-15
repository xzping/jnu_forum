CREATE DATABASE jnu_forum;

-- User表
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT NULL,
  `introduction` varchar(16) DEFAULT NULL,
  `password` varchar(60) NOT NULL,
  `username` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8

-- topic表
CREATE TABLE `topic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category` varchar(16) NOT NULL,
  `code` varchar(1024) DEFAULT NULL,
  `content` varchar(1024) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `title` varchar(32) NOT NULL,
  `id_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6br5a7f1rn1bi94rkmi38mcln` (`id_user`),
  CONSTRAINT `FK6br5a7f1rn1bi94rkmi38mcln` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8

-- answer表
CREATE TABLE `answer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(1024) DEFAULT NULL,
  `content` varchar(1024) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `useful` bit(1) NOT NULL,
  `id_topic` bigint(20) DEFAULT NULL,
  `id_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjyya1ipmyfw7iea1kr80c43h1` (`id_topic`),
  KEY `FK8ddy56hrr3tsqi29o3i5q48al` (`id_user`),
  CONSTRAINT `FK8ddy56hrr3tsqi29o3i5q48al` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  CONSTRAINT `FKjyya1ipmyfw7iea1kr80c43h1` FOREIGN KEY (`id_topic`) REFERENCES `topic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8

-- message表
CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `from_id` bigint(20) DEFAULT NULL,
  `to_id` bigint(20) DEFAULT NULL,
  `content` text,
  `created_date` datetime DEFAULT NULL,
  `id_topic` bigint(20) DEFAULT NULL,
  `has_read` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8

-- image表
CREATE TABLE `image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `imgUrl` varchar(100) NOT NULL,
  `id_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8
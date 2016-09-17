CREATE DATABASE `stats` DEFAULT CHARACTER SET utf8;

USE `stats`;

DROP TABLE IF EXISTS `poller_group`;

CREATE TABLE `poller_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(100) NOT NULL,
  `group_value` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_GRP` (`group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `poller_item`;

CREATE TABLE `poller_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `poller_group_id` int(11) NOT NULL,
  `poller_name` varchar(100) NOT NULL,
  `poller_type` varchar(20) NOT NULL,
  `poller_cmd` varchar(200) NOT NULL,
  `poller_interval` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `poller_output`;

CREATE TABLE `poller_output` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `poller_item_id` int(11) NOT NULL,
  `created_time` datetime NOT NULL,
  `output` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `poller_output_detail`;

CREATE TABLE `poller_output_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `poller_item_id` int(11) NOT NULL,
  `stats_key` varchar(50) NOT NULL,
  `stats_value` varchar(50) NOT NULL,
  `created_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `report`;

CREATE TABLE `report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `poller_item_id` int(11) NOT NULL,
  `poller_type` varchar(20) NOT NULL,
  `stats_key` varchar(50) NOT NULL,
  `stats_value` varchar(50) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
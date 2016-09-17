/*
Navicat MySQL Data Transfer

Source Server         : self
Source Server Version : 50511
Source Host           : localhost:3306
Source Database       : notepad

Target Server Type    : MYSQL
Target Server Version : 50511
File Encoding         : 65001

Date: 2011-06-10 17:23:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `account`
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `account_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `passWord` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `user_id` (`user_id`),
  KEY `FKB9D38A2DF0952CD0` (`user_id`),
  CONSTRAINT `FKB9D38A2DF0952CD0` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('1', 'hj@163.com', 'B59C67BF196A4758191E42F76670CEBA', '', '1');

-- ----------------------------
-- Table structure for `account_role`
-- ----------------------------
DROP TABLE IF EXISTS `account_role`;
CREATE TABLE `account_role` (
  `account_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`account_id`,`role_id`),
  KEY `FK410D03484B6A68F0` (`role_id`),
  KEY `FK410D0348FFA4E7E4` (`account_id`),
  CONSTRAINT `FK410D03484B6A68F0` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `FK410D0348FFA4E7E4` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account_role
-- ----------------------------
INSERT INTO `account_role` VALUES ('1', '1');
INSERT INTO `account_role` VALUES ('1', '3');

-- ----------------------------
-- Table structure for `education`
-- ----------------------------
DROP TABLE IF EXISTS `education`;
CREATE TABLE `education` (
  `education_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `degree` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `profession` varchar(255) DEFAULT NULL,
  `school` varchar(255) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`education_id`),
  KEY `FKEEAB67A8C78570E7` (`userId`),
  CONSTRAINT `FKEEAB67A8C78570E7` FOREIGN KEY (`userId`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of education
-- ----------------------------
INSERT INTO `education` VALUES ('1', '本科', '毕业时，大学从我身上下来，边穿裤子边说，你走，青春留下！这时，我才知道不是我上了大学，而是大学上了我。', '2003-06-30 13:47:34', '轮机工程', '武汉理工大学', '1999-09-01 13:47:08', '1');
INSERT INTO `education` VALUES ('2', '高中', '那段岁月，知愁又不知愁！', '1999-06-30 13:49:05', '理科', '四川攀枝花矿务局十三中学', '1995-09-01 13:49:22', '1');

-- ----------------------------
-- Table structure for `event`
-- ----------------------------
DROP TABLE IF EXISTS `event`;
CREATE TABLE `event` (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `eventName` varchar(255) DEFAULT NULL,
  `result` varchar(255) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `FK5C6729AC78570E7` (`userId`),
  CONSTRAINT `FK5C6729AC78570E7` FOREIGN KEY (`userId`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of event
-- ----------------------------

-- ----------------------------
-- Table structure for `job`
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `industry` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `property` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`job_id`),
  KEY `FK19BBDC78570E7` (`userId`),
  CONSTRAINT `FK19BBDC78570E7` FOREIGN KEY (`userId`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of job
-- ----------------------------
INSERT INTO `job` VALUES ('1', '成都欧塞克信息技术有限公司', '外包', '举步维艰的时候', '2011-02-28 13:52:25', 'IT', 'j2ee', '私营', '50', '2010-01-19 13:52:02', '1');
INSERT INTO `job` VALUES ('2', '成都信必优', 'web', '继续艰难的历程', '2011-06-09 13:53:55', 'IT', 'j2ee', '外企', '100', '2011-03-01 13:53:43', '1');

-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `groupName` varchar(255) DEFAULT NULL,
  `menuName` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', 'massage.menu.group.account', 'massage.menu.userList', '/account/showUserList.do');
INSERT INTO `menu` VALUES ('2', 'massage.menu.group.account', 'massage.menu.userInfo', '/account/modifyOwnerInfo.do');
INSERT INTO `menu` VALUES ('3', 'massage.menu.group.management', 'massage.menu.accountManagement', '/manage/accountManagement.do');

-- ----------------------------
-- Table structure for `other`
-- ----------------------------
DROP TABLE IF EXISTS `other`;
CREATE TABLE `other` (
  `other_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`other_id`),
  KEY `FK6527F10C78570E7` (`userId`),
  CONSTRAINT `FK6527F10C78570E7` FOREIGN KEY (`userId`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of other
-- ----------------------------

-- ----------------------------
-- Table structure for `project`
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `project_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `environment` varchar(255) DEFAULT NULL,
  `liability` varchar(255) DEFAULT NULL,
  `projectName` varchar(255) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `tool` varchar(255) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  KEY `FKED904B19C78570E7` (`userId`),
  CONSTRAINT `FKED904B19C78570E7` FOREIGN KEY (`userId`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('1', 'nokia项目', '2011-03-31 13:55:28', 'winxp', '解决bug', 'wellness', '2011-03-01 13:55:43', 'eclipse', '1');
INSERT INTO `project` VALUES ('2', '移动搜索', '2010-12-09 13:57:22', 'winxp', '音乐搜索', '圈内', '2010-03-09 13:57:14', 'eclipse', '1');

-- ----------------------------
-- Table structure for `resource`
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `resource_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resourceName` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('1', 'account management', '/manage/**');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `roleName` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'ROLE_ADMIN', 'ROLE_ADMIN', '');
INSERT INTO `role` VALUES ('3', 'ROLE_GUEST', 'ROLE_GUEST', '');

-- ----------------------------
-- Table structure for `role_resource`
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource` (
  `role_id` bigint(20) NOT NULL,
  `resource_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`resource_id`),
  KEY `FKAEE599B74B6A68F0` (`role_id`),
  KEY `FKAEE599B775F636F0` (`resource_id`),
  CONSTRAINT `FKAEE599B74B6A68F0` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `FKAEE599B775F636F0` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_resource
-- ----------------------------
INSERT INTO `role_resource` VALUES ('1', '1');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `msn` varchar(255) DEFAULT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `cardType` int(11) DEFAULT NULL,
  `career` varchar(255) DEFAULT NULL,
  `children` tinyint(1) NOT NULL DEFAULT '0',
  `dream` varchar(255) DEFAULT NULL,
  `evaluation` varchar(255) DEFAULT NULL,
  `gender` tinyint(1) NOT NULL DEFAULT '0',
  `health` varchar(255) DEFAULT NULL,
  `height` double DEFAULT NULL,
  `hobby` varchar(255) DEFAULT NULL,
  `homePage` varchar(255) DEFAULT NULL,
  `householdRegister` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `marriage` tinyint(1) NOT NULL DEFAULT '0',
  `militaryId` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `nation` varchar(255) DEFAULT NULL,
  `nationalId` varchar(255) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `nickName` varchar(255) DEFAULT NULL,
  `passportId` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `registTime` datetime DEFAULT NULL,
  `residence` varchar(255) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `zipCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'hawkist@hotmail.com', '898899721', '成都金牛区蜀明东路4-1.19', '哈哈', '1800-09-15 10:19:24', '1', 'IT', '0', '睡觉睡到自然醒，数钱数到手抽筋', '星斗市民', '1', '良好', '167', '读书、睡觉', 'http://www.hj.com', '四川、泸州', null, '1', null, '13608058461', '汉族', '546635198309141249', '中国', 'hyman', null, '08302701856', '哈哈', '2011-06-09 13:43:49', '四川、成都', '3000', 'hawkist', '80', '61000');

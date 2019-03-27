/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : demoweb

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-06-11 17:55:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for animals
-- ----------------------------
DROP TABLE IF EXISTS `animals`;
CREATE TABLE `animals` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `memo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of animals
-- ----------------------------
INSERT INTO `animals` VALUES ('4', '大马猴', '10', '散养');
INSERT INTO `animals` VALUES ('5', '小绵阳', '8', '圈养');
INSERT INTO `animals` VALUES ('6', '火星猿', '1', '圈养');
INSERT INTO `animals` VALUES ('7', '狗', '10', '测试');
INSERT INTO `animals` VALUES ('8', '狗', '10', '测试');
INSERT INTO `animals` VALUES ('9', '狗', '10', '测试');
INSERT INTO `animals` VALUES ('10', '狗', '10', '测试');
INSERT INTO `animals` VALUES ('11', '狗', '10', '测试');
INSERT INTO `animals` VALUES ('12', '测试', '123', '测试');
INSERT INTO `animals` VALUES ('13', '测试', '123', '测试');
INSERT INTO `animals` VALUES ('14', '测试', '123', '测试');
INSERT INTO `animals` VALUES ('15', '测试', '123', '测试');
INSERT INTO `animals` VALUES ('16', '测试', '123', '测试');
INSERT INTO `animals` VALUES ('17', '测试', '123', '测试');
INSERT INTO `animals` VALUES ('18', '测试', '123', '测试');
INSERT INTO `animals` VALUES ('19', '1', '1', '1');
INSERT INTO `animals` VALUES ('20', '1', '1', '1');
INSERT INTO `animals` VALUES ('21', '1', '1', '1');
INSERT INTO `animals` VALUES ('22', '1', '1', '1');
INSERT INTO `animals` VALUES ('23', '阿斯蒂芬', '1', '阿斯蒂芬');

-- ----------------------------
-- Table structure for sql_connector
-- ----------------------------
DROP TABLE IF EXISTS `sql_connector`;
CREATE TABLE `sql_connector` (
  `sqlId` varchar(255) NOT NULL,
  `conId` varchar(255) NOT NULL,
  `sqlState` varchar(50) DEFAULT NULL COMMENT '转换状态（query/insert）',
  `sqlDialet` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`sqlId`,`conId`),
  KEY `fk_t_sql_has_t_connector_t_connector1_idx` (`conId`),
  KEY `fk_t_sql_has_t_connector_t_sql_idx` (`sqlId`),
  CONSTRAINT `fk_t_sql_has_t_connector_t_connector1` FOREIGN KEY (`conId`) REFERENCES `t_connector` (`conId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_sql_has_t_connector_t_sql` FOREIGN KEY (`sqlId`) REFERENCES `t_sql` (`sqlId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sql_connector
-- ----------------------------

-- ----------------------------
-- Table structure for t_connector
-- ----------------------------
DROP TABLE IF EXISTS `t_connector`;
CREATE TABLE `t_connector` (
  `conId` varchar(255) NOT NULL COMMENT '主键',
  `conUrl` varchar(1024) DEFAULT NULL COMMENT '连接地址',
  `conUserName` varchar(512) DEFAULT NULL COMMENT '用户名',
  `conPassword` varchar(512) DEFAULT NULL COMMENT '密码',
  `conSqlType` varchar(512) DEFAULT NULL COMMENT '数据库类型',
  `conDriverName` varchar(1024) DEFAULT NULL COMMENT '驱动类',
  PRIMARY KEY (`conId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_connector
-- ----------------------------
INSERT INTO `t_connector` VALUES ('1', 'jdbc:mysql://127.0.0.1:3306/demoweb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=UTF-8', 'root', 'success', 'mysql', 'com.mysql.cj.jdbc.Driver');

-- ----------------------------
-- Table structure for t_sort
-- ----------------------------
DROP TABLE IF EXISTS `t_sort`;
CREATE TABLE `t_sort` (
  `sortId` int(11) NOT NULL,
  PRIMARY KEY (`sortId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sort
-- ----------------------------

-- ----------------------------
-- Table structure for t_sql
-- ----------------------------
DROP TABLE IF EXISTS `t_sql`;
CREATE TABLE `t_sql` (
  `sqlId` varchar(255) NOT NULL COMMENT '主键',
  `sqlContent` varchar(1024) DEFAULT NULL COMMENT 'sql内容',
  `sqlDetails` varchar(1024) DEFAULT NULL COMMENT 'sql解释',
  `t_sort_sortId` int(11) DEFAULT NULL,
  `sqlSort` varchar(45) DEFAULT NULL COMMENT 'sql类型（）',
  PRIMARY KEY (`sqlId`),
  KEY `fk_t_sql_t_sort1_idx` (`t_sort_sortId`),
  CONSTRAINT `fk_t_sql_t_sort1` FOREIGN KEY (`t_sort_sortId`) REFERENCES `t_sort` (`sortId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sql
-- ----------------------------
INSERT INTO `t_sql` VALUES ('', ' 11', null, null, null);
INSERT INTO `t_sql` VALUES ('1', '2', '2', '0', null);
INSERT INTO `t_sql` VALUES ('10', '6', '6', '0', null);
INSERT INTO `t_sql` VALUES ('11', '1', null, null, null);
INSERT INTO `t_sql` VALUES ('14', '1', null, null, null);
INSERT INTO `t_sql` VALUES ('2', '3', '7', '0', null);
INSERT INTO `t_sql` VALUES ('3', '3', '7', '0', null);
INSERT INTO `t_sql` VALUES ('4', '3', '7', '0', null);
INSERT INTO `t_sql` VALUES ('5', '3', '7', '0', null);
INSERT INTO `t_sql` VALUES ('6', '3', '7', '0', null);
INSERT INTO `t_sql` VALUES ('7', '3', '7', '0', null);
INSERT INTO `t_sql` VALUES ('8', '3', '7', '0', null);
INSERT INTO `t_sql` VALUES ('9', '3', '7', '0', null);
INSERT INTO `t_sql` VALUES ('ADSa', ' 11', null, null, null);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('111', '123', '123', '123');
INSERT INTO `t_user` VALUES ('123', '123', '123', '123');

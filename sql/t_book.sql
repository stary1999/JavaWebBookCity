/*
 Navicat MySQL Data Transfer

 Source Server         : 1
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : localhost:3306
 Source Schema         : book

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 15/07/2021 16:45:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_book
-- ----------------------------
DROP TABLE IF EXISTS `t_book`;
CREATE TABLE `t_book`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `price` decimal(11, 2) NULL DEFAULT NULL,
  `author` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sales` int NULL DEFAULT NULL,
  `stock` int NULL DEFAULT NULL,
  `img_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_book
-- ----------------------------
INSERT INTO `t_book` VALUES (1, 'java从入门到放弃', 80.00, '国哥', 10009, 9, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (2, '数据结构与算法', 78.50, '严敏君', 6, 15, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (3, '怎样拐跑别人的媳妇', 68.00, '龙伍', 99999, 52, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (4, '木虚肉盖饭', 16.00, '小胖', 1000, 50, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (5, 'C++编程思想', 45.50, '刚哥', 14, 95, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (6, '蛋炒饭', 9.90, '周星星', 12, 53, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (7, '赌神', 66.50, '龙伍', 125, 535, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (8, 'Java编程思想', 99.50, '阳哥', 47, 36, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (9, 'JavaScript从入门到精通', 9.90, '婷姐', 85, 95, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (10, 'cocos2d-x游戏编程入门', 49.00, '国哥', 52, 62, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (11, 'C语言程序设计', 28.00, '谭浩强', 52, 74, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (12, 'Lua语言程序设计', 51.50, '雷丰阳', 48, 82, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (13, '西游记', 12.00, '罗贯中', 19, 9999, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (14, '水浒传', 33.05, '华仔', 22, 88, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (15, '操作系统原理', 133.05, '刘优', 122, 188, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (16, '数据结构 java版', 173.15, '封大神', 21, 81, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (17, 'UNIX高级环境编程', 99.15, '乐天', 210, 810, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (18, 'javaScript高级编程', 69.15, '国哥', 210, 810, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (19, '大话设计模式', 89.15, '国哥', 20, 10, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (20, '人月神话', 88.15, '刚哥', 20, 80, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (24, '时间简史2', 999.00, 'st', 1998, 11, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (25, 'admin', 77.00, 'st', 1998, 11, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (26, 'admin', 85.00, 'st', 1998, 11, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (27, '时间简史2', 999.00, 'st', 1998, 11, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (28, '名词', 999.00, 'st', 1998, 11, 'static/img/default.jpg');
INSERT INTO `t_book` VALUES (29, '时间简史2', 999.00, 'st', 1998, 11, 'static/img/default.jpg');

SET FOREIGN_KEY_CHECKS = 1;

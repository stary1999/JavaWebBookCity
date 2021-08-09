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

 Date: 16/07/2021 16:14:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_order_item
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `count` int NULL DEFAULT NULL,
  `price` decimal(11, 2) NULL DEFAULT NULL,
  `total_price` decimal(11, 2) NULL DEFAULT NULL,
  `order_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE,
  CONSTRAINT `t_order_item_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `t_order` (`order_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order_item
-- ----------------------------
INSERT INTO `t_order_item` VALUES (4, 'java从入门到头秃', 2, 1000.00, 2000.00, '16215083798471');
INSERT INTO `t_order_item` VALUES (5, '数据结构与算法', 1, 100.00, 100.00, '16215083798471');
INSERT INTO `t_order_item` VALUES (6, 'java从入门到头秃', 2, 1000.00, 2000.00, '16215090641871');
INSERT INTO `t_order_item` VALUES (7, '数据结构与算法', 1, 100.00, 100.00, '16215090641871');
INSERT INTO `t_order_item` VALUES (8, '数据结构与算法', 1, 78.50, 78.50, '16215093651601');
INSERT INTO `t_order_item` VALUES (9, '怎样拐跑别人的媳妇', 1, 68.00, 68.00, '16215093651601');
INSERT INTO `t_order_item` VALUES (10, '木虚肉盖饭', 1, 16.00, 16.00, '16215093651601');
INSERT INTO `t_order_item` VALUES (11, 'java从入门到放弃', 5, 80.00, 400.00, '16215102939861');
INSERT INTO `t_order_item` VALUES (12, 'java从入门到放弃', 5, 80.00, 400.00, '16215103122831');
INSERT INTO `t_order_item` VALUES (13, '数据结构与算法', 1, 78.50, 78.50, '16263525696174');
INSERT INTO `t_order_item` VALUES (14, '木虚肉盖饭', 1, 16.00, 16.00, '16263525696174');
INSERT INTO `t_order_item` VALUES (15, 'C++编程思想', 1, 45.50, 45.50, '16263525696174');
INSERT INTO `t_order_item` VALUES (16, '数据结构与算法', 1, 78.50, 78.50, '16264077688454');
INSERT INTO `t_order_item` VALUES (17, '木虚肉盖饭', 1, 16.00, 16.00, '16264077688454');
INSERT INTO `t_order_item` VALUES (18, 'C++编程思想', 1, 45.50, 45.50, '16264077688454');
INSERT INTO `t_order_item` VALUES (19, 'java从入门到放弃', 1, 80.00, 80.00, '16264077688454');
INSERT INTO `t_order_item` VALUES (20, '木虚肉盖饭', 2, 16.00, 32.00, '16264077801494');
INSERT INTO `t_order_item` VALUES (21, 'C++编程思想', 1, 45.50, 45.50, '16264077801494');
INSERT INTO `t_order_item` VALUES (22, '数据结构与算法', 1, 78.50, 78.50, '16264077801494');

SET FOREIGN_KEY_CHECKS = 1;

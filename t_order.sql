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

 Date: 16/07/2021 16:13:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `order_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `price` decimal(11, 2) NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `t_order_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('16215083798471', '2021-05-20 18:59:40', 2100.00, 1, 1);
INSERT INTO `t_order` VALUES ('16215090641871', '2021-05-20 19:11:04', 2100.00, 2, 4);
INSERT INTO `t_order` VALUES ('16215093651601', '2021-05-20 19:16:05', 162.50, 1, 1);
INSERT INTO `t_order` VALUES ('16215102939861', '2021-05-20 19:31:34', 400.00, 1, 3);
INSERT INTO `t_order` VALUES ('16215103122831', '2021-05-20 19:31:52', 400.00, 1, 2);
INSERT INTO `t_order` VALUES ('16263525696174', '2021-07-15 20:36:10', 140.00, 0, 4);
INSERT INTO `t_order` VALUES ('16264077688454', '2021-07-16 11:56:09', 220.00, 2, 4);
INSERT INTO `t_order` VALUES ('16264077801494', '2021-07-16 11:56:20', 156.00, 2, 4);

SET FOREIGN_KEY_CHECKS = 1;

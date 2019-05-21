/*
Navicat MySQL Data Transfer

Source Server         : 192.168.88.88
Source Server Version : 50643
Source Host           : 192.168.88.88:3306
Source Database       : graduation_system

Target Server Type    : MYSQL
Target Server Version : 50643
File Encoding         : 65001

Date: 2019-05-13 14:58:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college` (
  `collegeID` int(11) NOT NULL,
  `collegeName` varchar(200) NOT NULL COMMENT '课程名',
  PRIMARY KEY (`collegeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of college
-- ----------------------------
INSERT INTO `college` VALUES ('1', '计算机');
INSERT INTO `college` VALUES ('2', '软件');
INSERT INTO `college` VALUES ('3', '信科');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `courseID` int(255) NOT NULL,
  `courseName` varchar(200) DEFAULT NULL COMMENT '课题名称',
  `teacherID` int(11) NOT NULL COMMENT '指导教师id',
  `studentID` int(11) DEFAULT NULL,
  `collegeID` int(11) NOT NULL COMMENT '所属院系',
  `score` int(11) DEFAULT NULL COMMENT '学分',
  `pass` int(1) unsigned NOT NULL COMMENT '是否通过',
  PRIMARY KEY (`courseID`),
  KEY `collegeID` (`collegeID`),
  KEY `teacherID` (`teacherID`),
  KEY `course_ibfk_3` (`studentID`),
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`collegeID`) REFERENCES `college` (`collegeID`),
  CONSTRAINT `course_ibfk_2` FOREIGN KEY (`teacherID`) REFERENCES `teacher` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('900101', '', '9001', '10001', '1', '0', '1');
INSERT INTO `course` VALUES ('900102', '', '9001', '10002', '1', '0', '1');
INSERT INTO `course` VALUES ('900103', '', '9001', '10004', '1', '0', '1');
INSERT INTO `course` VALUES ('900201', '', '9002', '0', '1', '0', '1');
INSERT INTO `course` VALUES ('900202', '', '9002', '0', '1', '0', '1');
INSERT INTO `course` VALUES ('900203', '', '9002', '0', '1', '0', '1');

-- ----------------------------
-- Table structure for event
-- ----------------------------
DROP TABLE IF EXISTS `event`;
CREATE TABLE `event` (
  `eventid` varchar(255) CHARACTER SET utf8 NOT NULL,
  `starttime` date DEFAULT NULL,
  `endtime` date DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `executed` int(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of event
-- ----------------------------
INSERT INTO `event` VALUES ('1', '2019-04-01', '2019-05-13', '信息录入', '2');
INSERT INTO `event` VALUES ('2', '2019-04-01', '2019-05-13', '修改题目', '2');
INSERT INTO `event` VALUES ('3', '2019-04-01', '2019-05-13', '一轮选题', '2');
INSERT INTO `event` VALUES ('4', '2019-04-01', '2019-05-13', '教师选择', '2');
INSERT INTO `event` VALUES ('5', '2019-04-01', '2019-05-13', '二轮选题', '2');
INSERT INTO `event` VALUES ('6', '2019-04-01', '2019-05-13', '教师选择', '1');
INSERT INTO `event` VALUES ('7', '2019-04-01', '2019-05-13', '管理员分配', '0');
INSERT INTO `event` VALUES ('8', '2019-04-01', '2019-05-13', '论文上传', '0');
INSERT INTO `event` VALUES ('9', '2019-04-01', '2019-05-13', '登陆成绩', '0');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleID` int(11) NOT NULL,
  `roleName` varchar(20) NOT NULL,
  `permissions` varchar(255) DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`roleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('0', 'admin', null);
INSERT INTO `role` VALUES ('1', 'teacher', null);
INSERT INTO `role` VALUES ('2', 'student', null);

-- ----------------------------
-- Table structure for selectedcourse
-- ----------------------------
DROP TABLE IF EXISTS `selectedcourse`;
CREATE TABLE `selectedcourse` (
  `courseID` int(11) NOT NULL,
  `studentID` int(11) NOT NULL,
  `ads` varchar(255) DEFAULT NULL COMMENT '地址',
  KEY `courseID` (`courseID`),
  KEY `studentID` (`studentID`),
  CONSTRAINT `selectedcourse_ibfk_1` FOREIGN KEY (`courseID`) REFERENCES `course` (`courseID`),
  CONSTRAINT `selectedcourse_ibfk_2` FOREIGN KEY (`studentID`) REFERENCES `student` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of selectedcourse
-- ----------------------------
INSERT INTO `selectedcourse` VALUES ('900101', '10001', '');
INSERT INTO `selectedcourse` VALUES ('900102', '10002', '');
INSERT INTO `selectedcourse` VALUES ('900103', '10004', '');

-- ----------------------------
-- Table structure for selecttable
-- ----------------------------
DROP TABLE IF EXISTS `selecttable`;
CREATE TABLE `selecttable` (
  `courseID` int(11) NOT NULL,
  `studentID` int(11) NOT NULL,
  `pass` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of selecttable
-- ----------------------------
INSERT INTO `selecttable` VALUES ('900201', '10003', '1');
INSERT INTO `selecttable` VALUES ('900202', '10003', '2');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(200) NOT NULL,
  `sex` varchar(20) DEFAULT NULL,
  `phone` int(12) DEFAULT NULL COMMENT '出生日期',
  `grade` date DEFAULT NULL COMMENT '入学时间',
  `collegeID` int(11) NOT NULL COMMENT '院系id',
  PRIMARY KEY (`userID`),
  KEY `collegeID` (`collegeID`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`collegeID`) REFERENCES `college` (`collegeID`)
) ENGINE=InnoDB AUTO_INCREMENT=100016 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('10001', '学生1', '男', '1890001001', '2015-09-01', '1');
INSERT INTO `student` VALUES ('10002', '学生2', '男', '1890001002', '2015-09-01', '1');
INSERT INTO `student` VALUES ('10003', '学生3', '男', '1890001003', '2015-09-01', '1');
INSERT INTO `student` VALUES ('10004', '学生4', '男', '1890001004', '2015-09-01', '1');
INSERT INTO `student` VALUES ('10005', '学生5', '男', '1890001005', '2015-09-01', '1');
INSERT INTO `student` VALUES ('10006', '学生6', '男', '1890001006', '2015-09-01', '1');
INSERT INTO `student` VALUES ('10007', '学生7', '男', '1890001007', '2015-09-01', '2');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(200) NOT NULL,
  `sex` varchar(20) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `phone` varchar(12) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL COMMENT '职称',
  `titleCount` int(3) DEFAULT NULL,
  `collegeID` int(11) NOT NULL COMMENT '院系',
  PRIMARY KEY (`userID`),
  KEY `collegeID` (`collegeID`),
  CONSTRAINT `teacher_ibfk_1` FOREIGN KEY (`collegeID`) REFERENCES `college` (`collegeID`)
) ENGINE=InnoDB AUTO_INCREMENT=2015004 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('9001', '教师1', '女', '741849808@qq.com', '18960060933', '普通教师', '3', '1');
INSERT INTO `teacher` VALUES ('9002', '教师2', '男', '741849808@qq.com', '18960060933', '助教', '3', '1');

-- ----------------------------
-- Table structure for userlogin
-- ----------------------------
DROP TABLE IF EXISTS `userlogin`;
CREATE TABLE `userlogin` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `role` int(11) NOT NULL DEFAULT '2' COMMENT '角色权限',
  PRIMARY KEY (`userID`),
  KEY `role` (`role`),
  CONSTRAINT `userlogin_ibfk_1` FOREIGN KEY (`role`) REFERENCES `role` (`roleID`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userlogin
-- ----------------------------
INSERT INTO `userlogin` VALUES ('1', 'admin', '123', '0');
INSERT INTO `userlogin` VALUES ('122', '9001', '123', '1');
INSERT INTO `userlogin` VALUES ('123', '9002', '123', '1');
INSERT INTO `userlogin` VALUES ('124', '10001', '123', '2');
INSERT INTO `userlogin` VALUES ('125', '10002', '123', '2');
INSERT INTO `userlogin` VALUES ('126', '10003', '123', '2');
INSERT INTO `userlogin` VALUES ('127', '10004', '123', '2');
INSERT INTO `userlogin` VALUES ('128', '10005', '123', '2');
INSERT INTO `userlogin` VALUES ('129', '10006', '123', '2');
INSERT INTO `userlogin` VALUES ('130', '10007', '123', '2');

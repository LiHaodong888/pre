/*
 Navicat Premium Data Transfer

 Date: 19/08/2019 18:12:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for social_UserConnection
-- ----------------------------
DROP TABLE IF EXISTS `social_UserConnection`;
CREATE TABLE `social_UserConnection` (
                                         `userId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                         `providerId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                         `providerUserId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                         `rank` int(11) NOT NULL,
                                         `displayName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                         `profileUrl` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                         `imageUrl` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                         `accessToken` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                         `secret` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                         `refreshToken` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                         `expireTime` bigint(20) DEFAULT NULL,
                                         `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `tenant_id` int(11) DEFAULT NULL COMMENT '租户id',
                                         PRIMARY KEY (`userId`,`providerId`,`providerUserId`) USING BTREE,
                                         UNIQUE KEY `UserConnectionRank` (`userId`,`providerId`,`rank`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='社交登录表';


-- ----------------------------
-- Table structure for sys_db
-- ----------------------------
DROP TABLE IF EXISTS `sys_db`;
CREATE TABLE `sys_db` (
                          `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                          `db_type` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '数据库类型',
                          `host` int(15) DEFAULT NULL COMMENT '数据库地址',
                          `port` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '数据库端口',
                          `db_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '数据库名称',
                          `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名',
                          `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',
                          `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='数据库管理';

-- ----------------------------
-- Records of sys_db
-- ----------------------------
BEGIN;
INSERT INTO `sys_db` VALUES (1, 'MySQL', 127, '3306', 'pre', 'root', 'root', '2019-04-27 08:42:41', NULL);
INSERT INTO `sys_db` VALUES (2, 'MySQL', 127, '3306', 'pre', 'root', 'root', '2019-05-13 17:10:54', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
                            `dept_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '部门主键ID',
                            `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '部门名称',
                            `sort` int(11) DEFAULT NULL COMMENT '排序',
                            `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                            `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
                            `parent_id` int(11) DEFAULT NULL COMMENT '上级部门',
                            `tenant_id` int(11) DEFAULT NULL COMMENT '租户id',
                            PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='部门管理';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES (4, '某某集团', 0, '2019-04-21 22:53:33', '2019-08-14 03:25:41', '0', 0, 1);
INSERT INTO `sys_dept` VALUES (5, '上海公司', 1, '2019-04-21 22:53:57', '2019-08-14 03:25:42', '0', 4, 1);
INSERT INTO `sys_dept` VALUES (6, '研发部', 2, '2019-04-21 22:54:10', '2019-08-14 03:25:42', '0', 5, 1);
INSERT INTO `sys_dept` VALUES (7, '财务部', 3, '2019-04-21 22:54:46', '2019-08-14 03:25:43', '0', 5, 1);
INSERT INTO `sys_dept` VALUES (12, '市场部', 4, '2019-04-30 14:31:46', '2019-08-14 03:25:43', '0', 5, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
                            `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
                            `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '数据值',
                            `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标签名',
                            `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
                            `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
                            `sort` int(4) DEFAULT NULL COMMENT '排序（升序）',
                            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
                            `del_flag` int(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
                            PRIMARY KEY (`id`) USING BTREE,
                            KEY `sys_dict_value` (`value`) USING BTREE,
                            KEY `sys_dict_label` (`label`) USING BTREE,
                            KEY `sys_dict_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` VALUES (8, '1', '锁定', 'user_status', NULL, 1, '2019-06-07 20:43:29', '2019-06-27 16:32:16', NULL, 0);
INSERT INTO `sys_dict` VALUES (9, '2', '活动', 'user_status', NULL, 0, '2019-06-25 11:31:42', '2019-06-25 11:31:42', NULL, 0);
INSERT INTO `sys_dict` VALUES (14, '1', '1', 'a', NULL, 0, '2019-06-27 03:10:27', '2019-06-27 03:10:27', NULL, 0);
INSERT INTO `sys_dict` VALUES (24, '2', 'tesd', 'test', NULL, 0, '2019-07-12 03:25:06', '2019-07-12 03:25:06', NULL, 0);
INSERT INTO `sys_dict` VALUES (26, 'sdfsdf', 'sdfsd', 'ssdsfd', NULL, 2, '2019-07-14 03:11:11', '2019-07-14 03:11:11', NULL, 0);
INSERT INTO `sys_dict` VALUES (44, 'gh', 'gh', 'd阿瑟东', NULL, 0, '2019-07-20 18:46:13', '2019-07-20 18:46:13', NULL, 0);
INSERT INTO `sys_dict` VALUES (47, 'sa', 'ws', '12312', NULL, 1, '2019-07-31 07:07:41', '2019-07-31 07:07:41', NULL, 0);
INSERT INTO `sys_dict` VALUES (48, NULL, NULL, 'trfdz', '描述', NULL, '2019-08-01 07:53:27', '2019-08-01 07:53:27', '备注', 0);
INSERT INTO `sys_dict` VALUES (49, '11', '11', '12321', NULL, 0, '2019-08-04 04:42:13', '2019-08-04 04:42:13', NULL, 0);
INSERT INTO `sys_dict` VALUES (50, NULL, NULL, 'ss', 'ss', NULL, '2019-08-05 08:56:30', '2019-08-05 08:56:30', 'ss', 0);
INSERT INTO `sys_dict` VALUES (51, NULL, NULL, '12', '21', NULL, '2019-08-05 08:57:48', '2019-08-05 08:57:48', '2', 0);
INSERT INTO `sys_dict` VALUES (52, NULL, NULL, '23', '231', NULL, '2019-08-05 08:58:06', '2019-08-05 08:58:06', '13', 0);
INSERT INTO `sys_dict` VALUES (53, 'sfda2', 'fdas1', 'trfdz', NULL, 0, '2019-08-06 14:08:01', '2019-08-15 02:29:04', NULL, 0);
INSERT INTO `sys_dict` VALUES (54, NULL, NULL, '111', '222', NULL, '2019-08-09 02:01:45', '2019-08-09 02:01:45', '233', 0);
INSERT INTO `sys_dict` VALUES (55, NULL, NULL, '555', '555', NULL, '2019-08-09 02:01:58', '2019-08-09 02:01:58', '66', 0);
INSERT INTO `sys_dict` VALUES (56, NULL, NULL, 'SEX', '性别', NULL, '2019-08-12 01:18:50', '2019-08-12 01:18:50', '', 0);
INSERT INTO `sys_dict` VALUES (57, 'aa', 'aaa', '12', NULL, 0, '2019-08-12 06:53:47', '2019-08-12 06:53:47', NULL, 0);
INSERT INTO `sys_dict` VALUES (58, 'aa', 'aaa', '12', NULL, 0, '2019-08-12 06:53:50', '2019-08-12 06:53:50', NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
                           `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                           `job_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '岗位名称',
                           `dept_id` int(11) DEFAULT NULL COMMENT '部门id',
                           `sort` int(10) DEFAULT NULL COMMENT '排序',
                           `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                           `tenant_id` int(11) DEFAULT NULL COMMENT '租户id',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='岗位管理';

-- ----------------------------
-- Records of sys_job
-- ----------------------------
BEGIN;
INSERT INTO `sys_job` VALUES (1, '董事长', 4, 0, '2019-05-03 09:43:56', NULL, 1);
INSERT INTO `sys_job` VALUES (3, '全栈开发', 6, 1, '2019-05-03 10:31:03', NULL, 1);
INSERT INTO `sys_job` VALUES (4, '软件测试', 6, 2, '2019-05-03 10:31:41', NULL, 1);
INSERT INTO `sys_job` VALUES (5, '财务总监', 7, 3, '2019-06-16 00:44:59', NULL, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
                           `id` int(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
                           `request_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作IP',
                           `type` int(3) DEFAULT NULL COMMENT '操作类型 1 操作记录2异常记录',
                           `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作人',
                           `description` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作描述',
                           `action_method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求方法',
                           `action_url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                           `params` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求参数',
                           `ua` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '浏览器',
                           `class_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '类路径',
                           `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求方法',
                           `start_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
                           `finish_time` timestamp NULL DEFAULT NULL COMMENT '完成时间',
                           `consuming_time` bigint(11) DEFAULT NULL COMMENT '消耗时间',
                           `ex_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '异常详情信息',
                           `ex_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '异常描述',
                           `tenant_id` int(11) DEFAULT NULL COMMENT '租户id',
                           PRIMARY KEY (`id`) USING BTREE,
                           KEY `index_type` (`type`) USING BTREE COMMENT '日志类型'
) ENGINE=InnoDB AUTO_INCREMENT=963 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='系统日志';


-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
                            `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
                            `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单名称',
                            `perms` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单权限标识',
                            `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '前端跳转URL',
                            `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单组件',
                            `parent_id` int(11) DEFAULT NULL COMMENT '父菜单ID',
                            `icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图标',
                            `sort` int(11) DEFAULT '1' COMMENT '排序',
                            `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单类型 （类型   0：目录   1：菜单   2：按钮）',
                            `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '逻辑删除标记(0--正常 1--删除)',
                            `is_frame` tinyint(1) DEFAULT NULL COMMENT '是否为外链',
                            PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, '权限管理', '', 'admin', '', 0, 'authority', 1, '0', '2019-04-21 22:45:08', '2019-06-21 23:52:00', '0', 1);
INSERT INTO `sys_menu` VALUES (2, '用户管理', '', 'user', 'admin/user', 1, 'user', 1, '1', '2019-04-21 22:49:22', '2019-05-12 19:02:34', '0', 1);
INSERT INTO `sys_menu` VALUES (3, '部门管理', '', 'dept', 'admin/dept', 1, 'dept', 2, '1', '2019-04-21 22:52:11', '2019-05-12 21:25:14', '0', 1);
INSERT INTO `sys_menu` VALUES (5, '用户新增', 'sys:user:add', '', NULL, 2, '', 0, '2', '2019-04-22 13:09:11', '2019-06-08 11:21:07', '0', 1);
INSERT INTO `sys_menu` VALUES (6, '系统管理', '', 'sys', '', 0, 'sys', 2, '0', '2019-04-22 23:48:02', '2019-06-21 23:52:07', '0', 1);
INSERT INTO `sys_menu` VALUES (7, '日志管理', '', 'log', 'sys/log', 6, 'log', 0, '1', '2019-04-22 23:59:40', '2019-05-05 20:20:52', '0', 1);
INSERT INTO `sys_menu` VALUES (11, '部门新增', 'sys:dept:add', '', NULL, 3, '', 0, '2', '2019-04-25 11:09:50', '2019-06-08 13:13:45', '0', 1);
INSERT INTO `sys_menu` VALUES (13, '角色管理', '', 'role', 'admin/role', 1, 'peoples', 1, '1', '2019-04-29 21:08:28', '2019-05-05 20:20:53', '0', 1);
INSERT INTO `sys_menu` VALUES (14, '用户修改', 'sys:user:update', '', NULL, 2, '', 0, '2', '2019-04-30 23:43:31', '2019-06-08 11:22:23', '0', 1);
INSERT INTO `sys_menu` VALUES (15, '角色新增', 'sys:role:add', '', NULL, 13, '', 0, '2', '2019-05-01 08:49:21', '2019-06-09 16:39:48', '0', 1);
INSERT INTO `sys_menu` VALUES (16, '菜单管理', '', 'menu', 'admin/menu', 1, 'menu', 3, '1', '2019-05-03 15:26:58', '2019-05-05 20:20:56', '0', 1);
INSERT INTO `sys_menu` VALUES (17, '岗位管理', '', 'job', 'admin/job', 1, 'job', 4, '1', '2019-05-03 15:27:25', '2019-05-05 20:20:56', '0', 1);
INSERT INTO `sys_menu` VALUES (18, '异常日志', '', 'erlog', 'sys/errorlog', 6, 'erlog', 1, '1', '2019-05-03 23:00:54', '2019-05-05 20:20:57', '0', 1);
INSERT INTO `sys_menu` VALUES (25, '日志查看', 'sys:log:view', '', '', 18, '', 0, '2', '2019-05-06 22:46:13', '2019-06-10 13:52:19', '0', 1);
INSERT INTO `sys_menu` VALUES (26, '日志删除', 'sys:log:delete', '', '', 18, '', 0, '2', '2019-05-06 22:46:47', '2019-06-10 13:52:24', '0', 1);
INSERT INTO `sys_menu` VALUES (27, '日志删除', 'sys:log:delete', '', '', 7, '', 0, '2', '2019-05-06 22:47:47', '2019-06-08 13:15:05', '0', 1);
INSERT INTO `sys_menu` VALUES (28, '菜单增加', 'sys:menu:add', '', '', 16, '', 0, '2', '2019-05-08 16:09:43', '2019-06-08 13:14:02', '0', 1);
INSERT INTO `sys_menu` VALUES (29, '菜单修改', 'sys:menu:update', '', '', 16, '', 0, '2', '2019-05-08 16:10:06', '2019-06-08 13:14:05', '0', 1);
INSERT INTO `sys_menu` VALUES (30, '部门修改', 'sys:dept:update', '', '', 3, '', 0, '2', '2019-05-08 23:49:54', '2019-06-08 13:13:49', '0', 1);
INSERT INTO `sys_menu` VALUES (31, '部门删除', 'sys:dept:delete', '', '', 3, '', 0, '2', '2019-05-08 23:53:41', '2019-06-08 13:13:52', '0', 1);
INSERT INTO `sys_menu` VALUES (32, '删除', 'sys:log:delete', '', '', 18, '', 0, '2', '2019-05-10 13:58:52', NULL, '0', 1);
INSERT INTO `sys_menu` VALUES (33, '用户查看', 'sys:user:view', '', '', 2, '', 0, '2', '2019-05-12 18:59:46', '2019-06-08 11:23:01', '0', 1);
INSERT INTO `sys_menu` VALUES (34, '角色修改', 'sys:role:update', '', '', 13, '', 0, '2', '2019-05-12 19:05:03', '2019-06-08 13:13:29', '0', 1);
INSERT INTO `sys_menu` VALUES (35, '用户删除', 'sys:user:delete', '', '', 2, '', 0, '2', '2019-05-12 19:08:13', '2019-06-08 11:23:07', '0', 1);
INSERT INTO `sys_menu` VALUES (36, '菜单删除', 'sys:menu:delete', '', '', 16, '', 0, '2', '2019-05-12 19:10:02', '2019-06-08 13:14:09', '0', 1);
INSERT INTO `sys_menu` VALUES (37, '角色删除', 'sys:role:delete', '', '', 13, '', 0, '2', '2019-05-12 19:11:14', '2019-06-08 13:13:34', '0', 1);
INSERT INTO `sys_menu` VALUES (38, '角色查看', 'sys:role:view', '', '', 13, '', 0, '2', '2019-05-12 19:11:37', '2019-06-08 13:13:37', '0', 1);
INSERT INTO `sys_menu` VALUES (39, '岗位新增', 'sys:job:add', '', '', 17, '', 0, '2', '2019-05-12 19:15:57', '2019-06-08 13:14:40', '0', 1);
INSERT INTO `sys_menu` VALUES (40, '岗位修改', 'sys:job:update', '', '', 17, '', 0, '2', '2019-05-12 19:16:30', '2019-06-08 13:14:43', '0', 1);
INSERT INTO `sys_menu` VALUES (41, '岗位查看', 'sys:job:view', '', '', 17, '', 0, '2', '2019-05-12 19:16:44', '2019-06-08 13:14:47', '0', 1);
INSERT INTO `sys_menu` VALUES (42, '岗位删除', 'sys:job:delete', '', '', 17, '', 0, '2', '2019-05-12 19:17:16', '2019-06-08 13:14:50', '0', 1);
INSERT INTO `sys_menu` VALUES (43, '字典管理', '', 'dict', 'sys/dict', 6, 'tag', 0, '1', '2019-05-16 18:17:32', '2019-08-14 03:37:22', '0', 1);
INSERT INTO `sys_menu` VALUES (44, '部门查看', 'sys:dept:view', '', '', 3, '', 0, '2', '2019-06-07 20:50:31', '2019-06-08 13:13:55', '0', 1);
INSERT INTO `sys_menu` VALUES (45, '字典查看', 'sys:dipt:view', '', '', 43, '', 0, '2', '2019-06-07 20:55:42', '2019-06-08 13:14:56', '0', 1);
INSERT INTO `sys_menu` VALUES (46, '菜单查看', 'sys:menu:view', '', '', 16, '', 0, '2', '2019-06-08 13:14:32', NULL, '0', 1);
INSERT INTO `sys_menu` VALUES (47, '修改密码', 'sys:user:updatePass', '', '', 2, '', 0, '2', '2019-06-15 09:43:20', '2019-06-15 09:43:20', '0', 1);
INSERT INTO `sys_menu` VALUES (48, '修改邮箱', 'sys:user:updateEmail', '', '', 2, '', 0, '2', '2019-06-15 09:43:58', '2019-06-15 09:43:58', '0', 1);
INSERT INTO `sys_menu` VALUES (50, '代码生成', '', 'codegen', 'sys/codegen', 6, 'exit-fullscreen', 0, '1', '2019-08-05 07:28:08', '2019-08-05 07:28:49', '0', 1);
INSERT INTO `sys_menu` VALUES (51, '社交账号管理', '', 'social', 'admin/social', 1, 'guide', 6, '1', '2019-08-05 07:29:12', '2019-08-05 07:30:52', '0', 1);
INSERT INTO `sys_menu` VALUES (52, '社交查看', 'sys:social:view', '', '', 51, '', 0, '2', '2019-08-05 08:00:33', '2019-08-05 08:00:33', '0', 1);
INSERT INTO `sys_menu` VALUES (53, '代码生成', 'sys:codegen:codegen', '', '', 50, '', 0, '2', '2019-08-05 08:34:52', '2019-08-05 08:34:52', '0', 1);
INSERT INTO `sys_menu` VALUES (54, '解绑账号', 'sys:social:untied', '', '', 51, '', 0, '2', '2019-08-05 08:35:25', '2019-08-05 08:35:25', '0', 1);
INSERT INTO `sys_menu` VALUES (55, '项目地址', '', 'https://gitee.com/li_haodong/pre', '', 0, 'blogLink', 0, '0', '2019-08-07 10:30:27', '2019-08-07 10:30:27', '0', 0);
INSERT INTO `sys_menu` VALUES (56, '租户管理', '', 'tenant', 'admin/tenant', 1, 'list', 5, '1', '2019-08-14 03:38:32', '2019-08-14 03:40:27', '0', 1);
INSERT INTO `sys_menu` VALUES (57, '新增租户', 'sys:tenant:add', '', '', 56, '', 0, '2', '2019-08-14 03:39:09', '2019-08-14 03:39:09', '0', 1);
INSERT INTO `sys_menu` VALUES (58, '修改租户', 'sys:tenant:update', '', '', 56, '', 0, '2', '2019-08-14 03:39:23', '2019-08-14 03:39:23', '0', 1);
INSERT INTO `sys_menu` VALUES (59, '删除租户', 'sys:tenant:del', '', '', 56, '', 0, '2', '2019-08-14 03:39:39', '2019-08-14 03:39:39', '0', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
                            `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色主键',
                            `role_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
                            `role_code` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色标识',
                            `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色描述',
                            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
                            `ds_type` int(1) DEFAULT NULL COMMENT '数据权限类型',
                            `ds_scope` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '数据权限范围 1 全部 2 本级 3 本级以及子级 4 自定义',
                            `tenant_id` int(11) DEFAULT NULL COMMENT '租户id',
                            PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='系统角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (5, '管理员', 'ADMIN_ROLE', '管理员', '2019-04-22 21:53:38', '2019-08-14 03:26:15', '0', 1, '4,5,6,7,12', 1);
INSERT INTO `sys_role` VALUES (7, '开发人员', 'DEV_ROLE', '开发人员', '2019-04-24 21:11:28', '2019-08-14 03:26:16', '0', 3, '6', 1);
INSERT INTO `sys_role` VALUES (14, '体验人员', 'ROLE_LEARN', '专供体验系统人员', '2019-06-21 08:06:10', '2019-08-14 03:26:17', '0', 1, '4,5,6,7,12', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
                                 `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '部门主键ID',
                                 `role_id` int(20) DEFAULT NULL COMMENT '角色ID',
                                 `dept_id` int(20) DEFAULT NULL COMMENT '部门ID',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=212 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色与部门对应关系';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_dept` VALUES (74, 0, 4);
INSERT INTO `sys_role_dept` VALUES (75, 0, 5);
INSERT INTO `sys_role_dept` VALUES (76, 0, 6);
INSERT INTO `sys_role_dept` VALUES (77, 0, 7);
INSERT INTO `sys_role_dept` VALUES (78, 0, 12);
INSERT INTO `sys_role_dept` VALUES (99, 0, 4);
INSERT INTO `sys_role_dept` VALUES (100, 0, 5);
INSERT INTO `sys_role_dept` VALUES (101, 0, 6);
INSERT INTO `sys_role_dept` VALUES (102, 0, 7);
INSERT INTO `sys_role_dept` VALUES (103, 0, 12);
INSERT INTO `sys_role_dept` VALUES (104, 0, 4);
INSERT INTO `sys_role_dept` VALUES (105, 0, 5);
INSERT INTO `sys_role_dept` VALUES (106, 0, 6);
INSERT INTO `sys_role_dept` VALUES (107, 0, 7);
INSERT INTO `sys_role_dept` VALUES (108, 0, 12);
INSERT INTO `sys_role_dept` VALUES (109, 0, 4);
INSERT INTO `sys_role_dept` VALUES (110, 0, 5);
INSERT INTO `sys_role_dept` VALUES (111, 0, 6);
INSERT INTO `sys_role_dept` VALUES (112, 0, 7);
INSERT INTO `sys_role_dept` VALUES (113, 0, 12);
INSERT INTO `sys_role_dept` VALUES (196, 7, 6);
INSERT INTO `sys_role_dept` VALUES (202, 5, 4);
INSERT INTO `sys_role_dept` VALUES (203, 5, 5);
INSERT INTO `sys_role_dept` VALUES (204, 5, 6);
INSERT INTO `sys_role_dept` VALUES (205, 5, 7);
INSERT INTO `sys_role_dept` VALUES (206, 5, 12);
INSERT INTO `sys_role_dept` VALUES (207, 14, 4);
INSERT INTO `sys_role_dept` VALUES (208, 14, 5);
INSERT INTO `sys_role_dept` VALUES (209, 14, 6);
INSERT INTO `sys_role_dept` VALUES (210, 14, 7);
INSERT INTO `sys_role_dept` VALUES (211, 14, 12);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                 `role_id` int(11) NOT NULL COMMENT '角色ID',
                                 `menu_id` int(11) NOT NULL COMMENT '菜单ID',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 KEY `index_role_id` (`role_id`) USING BTREE COMMENT '角色Id',
                                 KEY `index_menu_id` (`menu_id`) USING BTREE COMMENT '菜单Id'
) ENGINE=InnoDB AUTO_INCREMENT=2403 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色菜单表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (2280, 7, 55);
INSERT INTO `sys_role_menu` VALUES (2281, 7, 1);
INSERT INTO `sys_role_menu` VALUES (2282, 7, 2);
INSERT INTO `sys_role_menu` VALUES (2283, 7, 33);
INSERT INTO `sys_role_menu` VALUES (2284, 7, 13);
INSERT INTO `sys_role_menu` VALUES (2285, 7, 38);
INSERT INTO `sys_role_menu` VALUES (2286, 7, 3);
INSERT INTO `sys_role_menu` VALUES (2287, 7, 44);
INSERT INTO `sys_role_menu` VALUES (2288, 7, 16);
INSERT INTO `sys_role_menu` VALUES (2289, 7, 17);
INSERT INTO `sys_role_menu` VALUES (2290, 7, 41);
INSERT INTO `sys_role_menu` VALUES (2291, 7, 6);
INSERT INTO `sys_role_menu` VALUES (2292, 7, 7);
INSERT INTO `sys_role_menu` VALUES (2293, 7, 18);
INSERT INTO `sys_role_menu` VALUES (2336, 5, 55);
INSERT INTO `sys_role_menu` VALUES (2337, 5, 1);
INSERT INTO `sys_role_menu` VALUES (2338, 5, 2);
INSERT INTO `sys_role_menu` VALUES (2339, 5, 5);
INSERT INTO `sys_role_menu` VALUES (2340, 5, 14);
INSERT INTO `sys_role_menu` VALUES (2341, 5, 33);
INSERT INTO `sys_role_menu` VALUES (2342, 5, 35);
INSERT INTO `sys_role_menu` VALUES (2343, 5, 47);
INSERT INTO `sys_role_menu` VALUES (2344, 5, 48);
INSERT INTO `sys_role_menu` VALUES (2345, 5, 13);
INSERT INTO `sys_role_menu` VALUES (2346, 5, 15);
INSERT INTO `sys_role_menu` VALUES (2347, 5, 34);
INSERT INTO `sys_role_menu` VALUES (2348, 5, 37);
INSERT INTO `sys_role_menu` VALUES (2349, 5, 38);
INSERT INTO `sys_role_menu` VALUES (2350, 5, 3);
INSERT INTO `sys_role_menu` VALUES (2351, 5, 11);
INSERT INTO `sys_role_menu` VALUES (2352, 5, 30);
INSERT INTO `sys_role_menu` VALUES (2353, 5, 31);
INSERT INTO `sys_role_menu` VALUES (2354, 5, 44);
INSERT INTO `sys_role_menu` VALUES (2355, 5, 16);
INSERT INTO `sys_role_menu` VALUES (2356, 5, 28);
INSERT INTO `sys_role_menu` VALUES (2357, 5, 29);
INSERT INTO `sys_role_menu` VALUES (2358, 5, 36);
INSERT INTO `sys_role_menu` VALUES (2359, 5, 46);
INSERT INTO `sys_role_menu` VALUES (2360, 5, 17);
INSERT INTO `sys_role_menu` VALUES (2361, 5, 39);
INSERT INTO `sys_role_menu` VALUES (2362, 5, 40);
INSERT INTO `sys_role_menu` VALUES (2363, 5, 41);
INSERT INTO `sys_role_menu` VALUES (2364, 5, 42);
INSERT INTO `sys_role_menu` VALUES (2365, 5, 56);
INSERT INTO `sys_role_menu` VALUES (2366, 5, 57);
INSERT INTO `sys_role_menu` VALUES (2367, 5, 58);
INSERT INTO `sys_role_menu` VALUES (2368, 5, 59);
INSERT INTO `sys_role_menu` VALUES (2369, 5, 51);
INSERT INTO `sys_role_menu` VALUES (2370, 5, 52);
INSERT INTO `sys_role_menu` VALUES (2371, 5, 54);
INSERT INTO `sys_role_menu` VALUES (2372, 5, 6);
INSERT INTO `sys_role_menu` VALUES (2373, 5, 7);
INSERT INTO `sys_role_menu` VALUES (2374, 5, 27);
INSERT INTO `sys_role_menu` VALUES (2375, 5, 43);
INSERT INTO `sys_role_menu` VALUES (2376, 5, 45);
INSERT INTO `sys_role_menu` VALUES (2377, 5, 50);
INSERT INTO `sys_role_menu` VALUES (2378, 5, 18);
INSERT INTO `sys_role_menu` VALUES (2379, 5, 25);
INSERT INTO `sys_role_menu` VALUES (2380, 5, 26);
INSERT INTO `sys_role_menu` VALUES (2381, 14, 55);
INSERT INTO `sys_role_menu` VALUES (2382, 14, 1);
INSERT INTO `sys_role_menu` VALUES (2383, 14, 2);
INSERT INTO `sys_role_menu` VALUES (2384, 14, 33);
INSERT INTO `sys_role_menu` VALUES (2385, 14, 13);
INSERT INTO `sys_role_menu` VALUES (2386, 14, 38);
INSERT INTO `sys_role_menu` VALUES (2387, 14, 3);
INSERT INTO `sys_role_menu` VALUES (2388, 14, 44);
INSERT INTO `sys_role_menu` VALUES (2389, 14, 16);
INSERT INTO `sys_role_menu` VALUES (2390, 14, 46);
INSERT INTO `sys_role_menu` VALUES (2391, 14, 17);
INSERT INTO `sys_role_menu` VALUES (2392, 14, 41);
INSERT INTO `sys_role_menu` VALUES (2393, 14, 56);
INSERT INTO `sys_role_menu` VALUES (2394, 14, 51);
INSERT INTO `sys_role_menu` VALUES (2395, 14, 52);
INSERT INTO `sys_role_menu` VALUES (2396, 14, 6);
INSERT INTO `sys_role_menu` VALUES (2397, 14, 7);
INSERT INTO `sys_role_menu` VALUES (2398, 14, 43);
INSERT INTO `sys_role_menu` VALUES (2399, 14, 45);
INSERT INTO `sys_role_menu` VALUES (2400, 14, 50);
INSERT INTO `sys_role_menu` VALUES (2401, 14, 18);
INSERT INTO `sys_role_menu` VALUES (2402, 14, 25);
COMMIT;

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant` (
                              `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '租户id',
                              `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '租户名称',
                              `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '租户编号',
                              `start_time` timestamp NULL DEFAULT NULL COMMENT '授权开始时间',
                              `end_time` timestamp NULL DEFAULT NULL COMMENT '授权结束时间',
                              `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '0正常 9-冻结',
                              `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标记',
                              `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='租户表';

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
BEGIN;
INSERT INTO `sys_tenant` VALUES (1, '上海公司', '1', '2019-08-10 00:00:00', '2020-08-10 00:00:00', '0', '0', '2019-08-10 10:13:12', '2019-08-10 12:44:52');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                            `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                            `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
                            `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
                            `dept_id` int(10) DEFAULT NULL COMMENT '部门ID',
                            `job_id` int(10) DEFAULT NULL COMMENT '岗位ID',
                            `email` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱',
                            `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号',
                            `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像',
                            `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                            `lock_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '0-正常，1-锁定',
                            `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '0-正常，1-删除',
                            `tenant_id` int(11) DEFAULT NULL COMMENT '租户id',
                            PRIMARY KEY (`user_id`) USING BTREE,
                            UNIQUE KEY `user_idx_username` (`username`) USING BTREE,
                            KEY `user_idx_dept_id` (`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (4, 'lihaodong', '$2a$10$.WPow3X6iSC24vR9KDwXHew66tDgokxp73Oydh.KDqPsEI4zny6Ti', 6, 3, 'lihaodongmail@163.com', '17521296869', 'http://file.52lhd.com/pre_red.png', '2019-04-23 23:29:51', '2019-08-14 03:25:08', '0', '0', 1);
INSERT INTO `sys_user` VALUES (6, 'dev', '$2a$10$.WPow3X6iSC24vR9KDwXHew66tDgokxp73Oydh.KDqPsEI4zny6Ti', 6, 4, 'lihaodongmail@163.com', '17521296868', 'http://file.52lhd.com/pre_red.png', '2019-04-25 11:11:56', '2019-08-14 03:25:09', '0', '0', 1);
INSERT INTO `sys_user` VALUES (8, 'admin', '$2a$10$IqetfiQLlcj7fmx2KvSpmeYyrNKMrqVqrjcDa3hRLEcLJovm.6CDO', 6, 3, 'lihaodongmail@163.com', '17521296867', 'http://file.52lhd.com/pre_red.png', '2019-06-21 08:06:57', '2019-08-14 03:25:16', '0', '0', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                 `user_id` int(10) NOT NULL COMMENT '用户ID',
                                 `role_id` int(10) NOT NULL COMMENT '角色ID',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户角色表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (46, 6, 7);
INSERT INTO `sys_user_role` VALUES (53, 4, 5);
INSERT INTO `sys_user_role` VALUES (55, 8, 14);
INSERT INTO `sys_user_role` VALUES (60, 13, 14);
INSERT INTO `sys_user_role` VALUES (61, 14, 14);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

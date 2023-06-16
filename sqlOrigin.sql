# Host: localhost  (Version 5.7.19-log)
# Date: 2022-06-13 10:04:40
# Generator: MySQL-Front 6.1  (Build 1.26)



CREATE DATABASE `guigu-auth` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
use `guigu-auth`;

#
# Structure for table "sys_dept"
#

CREATE TABLE `sys_dept` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `name` varchar(50) NOT NULL DEFAULT '' COMMENT '部门名称',
                            `parent_id` bigint(20) DEFAULT '0' COMMENT '上级部门id',
                            `tree_path` varchar(255) DEFAULT ',' COMMENT '树结构',
                            `sort_value` int(11) DEFAULT '1' COMMENT '排序',
                            `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
                            `phone` varchar(11) DEFAULT NULL COMMENT '电话',
                            `status` tinyint(1) DEFAULT '1' COMMENT '状态（1正常 0停用）',
                            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                            `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:可用 1:已删除）',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2018 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='组织机构';

#
# Data for table "sys_dept"
#

INSERT INTO `sys_dept` VALUES (1,'硅谷集团有限公司',0,',1,',1,'张老师','15659090912',1,'2022-05-24 16:13:13','2022-05-24 16:13:13',0),(10,'北京校区',1,',1,10,',1,'李老师','18790007789',1,'2022-05-24 16:13:15','2022-05-24 16:13:15',0),(20,'上海校区',1,',1,20,',1,'王老师','15090987678',1,'2022-05-25 14:02:25','2022-05-25 14:02:25',0),(30,'深圳校区',1,',1,30,',1,'李老师','15090987678',1,'2022-05-25 14:02:24','2022-05-25 14:02:24',0),(1010,'教学部分',10,',1,10,1010,',1,'李老师','15090987678',1,'2022-05-25 14:02:24','2022-05-25 14:02:24',0),(1020,'运营部门',10,',1,10,1020,',1,'王老师','15090987678',1,'2022-05-25 14:02:29','2022-05-25 14:02:29',0),(1021,'Java学科',1010,',1,10,1010,1021,',1,'王老师','15090987678',1,'2022-05-24 16:13:31','2022-05-24 16:13:31',0),(1022,'大数据学科',1010,',1,10,1010,1022,',1,'王老师','15090987678',1,'2022-05-25 14:02:22','2022-05-25 14:02:22',0),(1024,'前端学科',1010,',1,10,1010,1024,',1,'李老师','15090987678',1,'2022-05-25 14:02:22','2022-05-25 14:02:22',0),(1025,'客服',1020,',1,10,1020,1025,',1,'李老师','15090987678',1,'2022-05-25 14:02:23','2022-05-25 14:02:23',0),(1026,'网站推广',1020,',1,10,1020,1026,',1,'30.607366','15090987678',1,'2022-05-25 14:02:26','2022-05-25 14:02:26',0),(1027,'线下运营',1020,',1,10,1020,1027,',1,'李老师','15090987678',1,'2022-05-25 14:02:26','2022-05-25 14:02:26',0),(1028,'设计',1020,',1,10,1020,1028,',1,'李老师','15090987678',1,'2022-05-25 14:02:27','2022-05-25 14:02:27',0),(2012,'教学部门',20,',1,20,2012,',1,'王老师','18909890765',1,'2022-05-24 16:13:51','2022-05-24 16:13:51',0),(2013,'教学部门',30,',1,30,2013,',1,'李老师','18567867895',1,'2022-05-24 16:13:50','2022-05-24 16:13:50',0),(2016,'Java学科',2012,',1,20,2012,2012,',1,'张老师','15090909909',1,'2022-05-25 10:51:12','2022-05-25 10:51:12',0),(2017,'大数据学科',2012,',1,20,2012,2012,',1,'李老师','15090980989',1,'2022-05-27 09:11:54',NULL,0);

#
# Structure for table "sys_menu"
#

CREATE TABLE `sys_menu` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
                            `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属上级',
                            `name` varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
                            `type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '类型(0:目录,1:菜单,2:按钮)',
                            `path` varchar(100) DEFAULT NULL COMMENT '路由地址',
                            `component` varchar(100) DEFAULT NULL COMMENT '组件路径',
                            `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
                            `icon` varchar(100) DEFAULT NULL COMMENT '图标',
                            `sort_value` int(11) DEFAULT NULL COMMENT '排序',
                            `status` tinyint(4) DEFAULT NULL COMMENT '状态(0:禁止,1:正常)',
                            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:可用 1:已删除）',
                            PRIMARY KEY (`id`),
                            KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';


#
# Data for table "sys_menu"
#

INSERT INTO `sys_menu` (`id`,`parent_id`,`name`,`type`,`path`,`component`,`perms`,`icon`,`sort_value`,`status`,`create_time`,`update_time`,`is_deleted`) VALUES (2,0,'系统管理',0,'system','Layout',NULL,'el-icon-s-tools',1,1,'2021-05-31 18:05:37','2022-06-09 09:23:24',0),(3,2,'用户管理',1,'sysUser','system/sysUser/list','','el-icon-s-custom',1,1,'2021-05-31 18:05:37','2022-06-09 09:22:47',0),(4,2,'角色管理',1,'sysRole','system/sysRole/list','','el-icon-user-solid',2,1,'2021-05-31 18:05:37','2022-06-09 09:37:18',0),(5,2,'菜单管理',1,'sysMenu','system/sysMenu/list','','el-icon-s-unfold',3,1,'2021-05-31 18:05:37','2022-06-09 09:37:21',0),(6,3,'查看',2,NULL,NULL,'bnt.sysUser.list',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(7,3,'添加',2,NULL,NULL,'bnt.sysUser.add',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(8,3,'修改',2,NULL,NULL,'bnt.sysUser.update',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(9,3,'删除',2,NULL,NULL,'bnt.sysUser.remove',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(10,4,'查看',2,NULL,NULL,'bnt.sysRole.list',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(11,4,'添加',2,NULL,NULL,'bnt.sysRole.add',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(12,4,'修改',2,NULL,NULL,'bnt.sysRole.update',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(13,4,'删除',2,NULL,NULL,'bnt.sysRole.remove',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(14,5,'查看',2,NULL,NULL,'bnt.sysMenu.list',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(15,5,'添加',2,NULL,NULL,'bnt.sysMenu.add',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(16,5,'修改',2,NULL,NULL,'bnt.sysMenu.update',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(17,5,'删除',2,NULL,NULL,'bnt.sysMenu.remove',NULL,1,1,'2021-05-31 18:05:37','2022-06-09 09:22:38',0),(18,3,'分配角色',2,NULL,NULL,'bnt.sysUser.assignRole',NULL,1,1,'2022-05-23 17:14:32','2022-06-09 09:22:38',0),(19,4,'分配权限',2,'assignAuth','system/sysRole/assignAuth','bnt.sysRole.assignAuth',NULL,1,1,'2022-05-23 17:18:14','2022-06-09 09:22:38',0),(20,2,'部门管理',1,'sysDept','system/sysDept/list','','el-icon-s-operation',4,1,'2022-05-24 10:07:05','2022-06-09 09:38:12',0),(21,20,'查看',2,NULL,NULL,'bnt.sysDept.list',NULL,1,1,'2022-05-24 10:07:44','2022-06-09 09:22:38',0),(22,2,'岗位管理',1,'sysPost','system/sysPost/list','','el-icon-more-outline',5,1,'2022-05-24 10:25:30','2022-06-09 09:38:13',0),(23,22,'查看',2,NULL,NULL,'bnt.sysPost.list',NULL,1,1,'2022-05-24 10:25:45','2022-06-09 09:22:38',0),(24,20,'添加',2,NULL,NULL,'bnt.sysDept.add',NULL,1,1,'2022-05-25 15:31:27','2022-06-09 09:22:38',0),(25,20,'修改',2,NULL,NULL,'bnt.sysDept.update',NULL,1,1,'2022-05-25 15:31:41','2022-06-09 09:22:38',0),(26,20,'删除',2,NULL,NULL,'bnt.sysDept.remove',NULL,1,1,'2022-05-25 15:31:59','2022-06-09 09:22:38',0),(27,22,'添加',2,NULL,NULL,'bnt.sysPost.add',NULL,1,1,'2022-05-25 15:32:44','2022-06-09 09:22:38',0),(28,22,'修改',2,NULL,NULL,'bnt.sysPost.update',NULL,1,1,'2022-05-25 15:32:58','2022-06-09 09:22:38',0),(29,22,'删除',2,NULL,NULL,'bnt.sysPost.remove',NULL,1,1,'2022-05-25 15:33:11','2022-06-09 09:22:38',0),(31,30,'查看',2,NULL,NULL,'bnt.sysOperLog.list',NULL,1,1,'2022-05-26 16:10:17','2022-06-09 09:22:38',0),(33,32,'查看',2,NULL,NULL,'bnt.sysLoginLog.list',NULL,1,1,'2022-05-26 16:36:31','2022-06-09 09:36:36',0);

#
# Structure for table "sys_post"
#

CREATE TABLE `sys_post` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
                            `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
                            `name` varchar(50) NOT NULL DEFAULT '' COMMENT '岗位名称',
                            `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
                            `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（1正常 0停用）',
                            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                            `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:可用 1:已删除）',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='岗位信息表';

#
# Data for table "sys_post"
#

INSERT INTO `sys_post` VALUES (5,'dsz','董事长','1',1,'2022-05-24 10:33:53',NULL,0),(6,'zjl','总经理','2',1,'2022-05-24 10:34:08',NULL,0),(7,'wz','网咨','',1,'2022-05-27 08:56:41','2022-05-27 08:56:41',1),(8,'yyzj','运营总监','',1,'2022-06-08 17:14:21',NULL,0);

#
# Structure for table "sys_role"
#

CREATE TABLE `sys_role` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
                            `role_name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
                            `role_code` varchar(20) DEFAULT NULL COMMENT '角色编码',
                            `description` varchar(255) DEFAULT NULL COMMENT '描述',
                            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:可用 1:已删除）',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='角色';

#
# Data for table "sys_role"
#

INSERT INTO `sys_role` VALUES (1,'系统管理员','SYSTEM','系统管理员','2021-05-31 18:09:18','2022-06-08 09:21:10',0),(2,'普通管理员','COMMON','普通管理员','2021-06-01 08:38:40','2022-02-24 10:42:46',0),(8,'用户管理员','yhgly',NULL,'2022-06-08 17:39:04','2022-06-08 17:39:04',0);

#
# Structure for table "sys_role_menu"
#

CREATE TABLE `sys_role_menu` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                 `role_id` bigint(20) NOT NULL DEFAULT '0',
                                 `menu_id` bigint(11) NOT NULL DEFAULT '0',
                                 `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:可用 1:已删除）',
                                 PRIMARY KEY (`id`),
                                 KEY `idx_role_id` (`role_id`),
                                 KEY `idx_menu_id` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='角色菜单';

#
# Data for table "sys_role_menu"
#

INSERT INTO `sys_role_menu` VALUES (1,2,2,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(2,2,3,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(3,2,6,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(4,2,7,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(5,2,8,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(6,2,9,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(7,2,18,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(8,2,4,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(9,2,10,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(10,2,11,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(11,2,12,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(12,2,13,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(13,2,19,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(14,2,5,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(15,2,14,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(16,2,15,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(17,2,16,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(18,2,17,'2022-06-02 16:11:27','2022-06-02 16:16:10',1),(19,2,2,'2022-06-02 16:16:10','2022-06-09 09:26:34',1),(20,2,3,'2022-06-02 16:16:10','2022-06-09 09:26:34',1),(21,2,6,'2022-06-02 16:16:10','2022-06-09 09:26:34',1),(22,2,7,'2022-06-02 16:16:10','2022-06-09 09:26:34',1),(23,2,8,'2022-06-02 16:16:10','2022-06-09 09:26:34',1),(24,2,2,'2022-06-09 09:26:34','2022-06-09 09:26:34',0),(25,2,3,'2022-06-09 09:26:34','2022-06-09 09:26:34',0),(26,2,6,'2022-06-09 09:26:34','2022-06-09 09:26:34',0),(27,2,7,'2022-06-09 09:26:34','2022-06-09 09:26:34',0),(28,2,8,'2022-06-09 09:26:34','2022-06-09 09:26:34',0),(29,2,5,'2022-06-09 09:26:34','2022-06-09 09:26:34',0),(30,2,14,'2022-06-09 09:26:34','2022-06-09 09:26:34',0),(31,2,20,'2022-06-09 09:26:34','2022-06-09 09:26:34',0),(32,2,21,'2022-06-09 09:26:34','2022-06-09 09:26:34',0);

#
# Structure for table "sys_user"
#

CREATE TABLE `sys_user` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员id',
                            `username` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
                            `password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
                            `name` varchar(50) DEFAULT NULL COMMENT '姓名',
                            `phone` varchar(11) DEFAULT NULL COMMENT '手机',
                            `head_url` varchar(200) DEFAULT NULL COMMENT '头像地址',
                            `dept_id` bigint(20) DEFAULT NULL COMMENT '部门id',
                            `post_id` bigint(20) DEFAULT NULL COMMENT '岗位id',
                            `description` varchar(255) DEFAULT NULL COMMENT '描述',
                            `status` tinyint(3) DEFAULT NULL COMMENT '状态（1：正常 0：停用）',
                            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:可用 1:已删除）',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

#
# Data for table "sys_user"
#

INSERT INTO `sys_user` VALUES (1,'admin','96e79218965eb72c92a549dd5a330112','admin','15099909888','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',1021,5,NULL,1,'2021-05-31 18:08:43','2022-05-25 11:34:25',0),(2,'wangqq','96e79218965eb72c92a549dd5a330112','王倩倩','15010546381','http://r61cnlsfq.hn-bkt.clouddn.com/b09b3467-3d99-437a-bd2e-dd8c9be92bb8',1022,6,NULL,1,'2022-02-08 10:35:38','2022-05-25 15:58:31',0),(3,'wanggang','96e79218965eb72c92a549dd5a330112','王刚','18909098909',NULL,1024,5,NULL,0,'2022-05-24 11:05:40','2022-06-02 10:19:25',0);

#
# Structure for table "sys_user_role"
#

CREATE TABLE `sys_user_role` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                 `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
                                 `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
                                 `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:可用 1:已删除）',
                                 PRIMARY KEY (`id`),
                                 KEY `idx_role_id` (`role_id`),
                                 KEY `idx_admin_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='用户角色';

#
# Data for table "sys_user_role"
#

INSERT INTO `sys_user_role` VALUES (2,2,2,'2022-01-20 20:49:37','2022-02-24 10:43:07',0),(3,1,1,'2022-05-19 10:37:27','2022-05-24 16:55:53',1),(4,2,1,'2022-05-19 10:37:27','2022-05-24 16:55:53',1),(5,1,1,'2022-05-24 16:55:53','2022-05-24 16:55:53',0),(6,2,3,'2022-05-25 16:09:31','2022-05-25 16:09:31',0),(7,2,4,'2022-06-02 11:08:14','2022-06-02 11:15:36',1),(8,2,4,'2022-06-02 11:15:36','2022-06-02 16:10:53',1),(9,1,4,'2022-06-02 11:15:36','2022-06-02 16:10:53',1),(10,1,4,'2022-06-02 16:10:53','2022-06-02 16:10:53',0);

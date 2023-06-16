use `guigu-auth`;
show create table sys_role;
show create table sys_user;

CREATE TABLE `sys_role` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色id',
                            `role_name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
                            `role_code` varchar(20) DEFAULT NULL COMMENT '角色编码',
                            `description` varchar(255) DEFAULT NULL COMMENT '描述',
                            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:可用 1:已删除）',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COMMENT='角色'


CREATE TABLE `sys_user` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '会员id',
                            `username` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
                            `password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
                            `name` varchar(50) DEFAULT NULL COMMENT '姓名',
                            `phone` varchar(11) DEFAULT NULL COMMENT '手机',
                            `head_url` varchar(200) DEFAULT NULL COMMENT '头像地址',
                            `dept_id` bigint DEFAULT NULL COMMENT '部门id',
                            `post_id` bigint DEFAULT NULL COMMENT '岗位id',
                            `description` varchar(255) DEFAULT NULL COMMENT '描述',
                            `status` tinyint DEFAULT NULL COMMENT '状态（1：正常 0：停用）',
                            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:可用 1:已删除）',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表'



# 多表联合查询根据用户的id获取对应的菜单
select distinct sm.*
from sys_menu sm
inner join sys_role_menu srm on sm.id = srm.menu_id
inner join sys_user_role sur on sur.role_id = srm.role_id
where sur.user_id = 33
and sm.is_deleted = 0
and srm.is_deleted = 0
and sur.is_deleted = 0
and sm.status = 1;
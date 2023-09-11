CREATE TABLE `classmates` (
                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '同学的id',
                        `name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '用户的姓名',
                        `grade` VARCHAR(50) DEFAULT NULL COMMENT '班级',
                        `group` tinyint(1) DEFAULT NULL COMMENT '小组',
                        `nickName` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
                        `phone` VARCHAR(11) DEFAULT NULL COMMENT '手机号',
                        `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
                        `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                        `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记(0:不可用 1:可用)',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1686562083136630797 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
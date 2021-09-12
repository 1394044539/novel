-- 2021/09/10
ALTER TABLE `sys_user`
ADD COLUMN `user_status` char(1) NULL COMMENT '用户状态(0：正常；1：禁用)' AFTER `wechat_number`;

-- 2021/09/11
ALTER TABLE `sys_user`
ADD COLUMN `full_memory` decimal(8, 3) NULL COMMENT '可用内存空间' AFTER `user_status`,
ADD COLUMN `use_memory` decimal(8, 3) NULL COMMENT '已用空间' AFTER `full_memory`;

CREATE TABLE `novel_history` (
  `history_id` varchar(32) NOT NULL COMMENT '历史记录id',
  `ip` varchar(16) DEFAULT NULL COMMENT 'ip',
  `last_novel_id` varchar(32) DEFAULT NULL COMMENT '最后访问小说id',
  `last_volume_id` varchar(32) DEFAULT NULL COMMENT '最后访问的分卷id',
  `last_chapter_id` varchar(32) DEFAULT NULL COMMENT '最后访问的章节id',
  `mark_name` varchar(50) DEFAULT NULL COMMENT '书签名',
  `record_type` char(1) DEFAULT NULL COMMENT '记录类型（0：历史记录：1：书签记录）',
  `record_percentage` decimal(5,2) DEFAULT NULL COMMENT '阅读进度',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`history_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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

-- 2021/09/22 用户注册申请表
CREATE TABLE `sys_register` (
  `register_id` char(32) NOT NULL COMMENT '主键id',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `register_message` varchar(512) DEFAULT NULL COMMENT '申请消息',
  `register_status` char(1) DEFAULT NULL COMMENT '注册状态(0：待审批，1：已发送，2：已注册；3：已作废)',
  `register_user_id` char(32) DEFAULT NULL COMMENT '注册用户id',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` char(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` char(32) DEFAULT NULL COMMENT '修改人',
  `is_delete` char(1) DEFAULT NULL COMMENT '是否删除（0：否；1：是）',
  PRIMARY KEY (`register_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 2021/10/29 字典值
ALTER TABLE `sys_dict`
ADD COLUMN `dict_value` varchar(32) NULL COMMENT '参数值' AFTER `dict_name`;
ALTER TABLE `sys_dict_param`
ADD COLUMN `param_value` varchar(32) NULL COMMENT '参数值' AFTER `param_name`;

-- 2021/11/1 章节表增加小说id
ALTER TABLE `novel_chapter`
ADD COLUMN `novel_id` char(32) NULL COMMENT '小说id' AFTER `chapter_id`;

-- 2022/1/7 新增公告表
CREATE TABLE `feedback` (
  `feedback_id` char(32) NOT NULL COMMENT '主键id',
  `feedback_title` varchar(60) DEFAULT NULL COMMENT '反馈标题',
  `feedback_content` varchar(1024) DEFAULT NULL COMMENT '反馈内容',
  `feedback_type` char(1) DEFAULT NULL COMMENT '反馈类型：（0:bug,1:意见）',
  `handle_status` char(1) DEFAULT NULL COMMENT '处理状态：（0:待处理,1:已完成）',
  `is_delete` char(1) DEFAULT NULL COMMENT '是否删除',
  `create_by` char(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` char(32) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`feedback_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

 ru
-- 2022/01/11 公告表删除字段
ALTER TABLE `sys_notice` DROP COLUMN `is_open`;

-- 2022/01/30 字段修改
ALTER TABLE novel_history`
CHANGE COLUMN `last_volume_id` `last_series_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后访问的系列id' AFTER `last_novel_id`;

ALTER TABLE `novel_history`
MODIFY COLUMN `record_percentage` decimal(10, 6) NULL DEFAULT NULL COMMENT '阅读进度' AFTER `record_type`;
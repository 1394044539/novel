-- 2021/09/10
ALTER TABLE `sys_user`
ADD COLUMN `user_status` char(1) NULL COMMENT '用户状态(0：正常；1：禁用)' AFTER `wechat_number`;

-- 2021/09/11
ALTER TABLE `sys_user`
ADD COLUMN `full_memory` decimal(8, 3) NULL COMMENT '可用内存空间' AFTER `user_status`,
ADD COLUMN `use_memory` decimal(8, 3) NULL COMMENT '已用空间' AFTER `full_memory`;
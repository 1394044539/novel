-- 2021/09/10
ALTER TABLE `sys_user`
ADD COLUMN `user_status` char(1) NULL COMMENT '用户状态(0：正常；1：禁用)' AFTER `wechat_number`;
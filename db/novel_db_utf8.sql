/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2021/9/6 23:01:28                            */
/*==============================================================*/


drop table if exists novel;

drop table if exists novel_chapter;

drop table if exists novel_comment;

drop table if exists novel_data;

drop table if exists novel_file;

drop table if exists novel_type_rel;

drop table if exists novel_volume;

drop table if exists sys_dict;

drop table if exists sys_dict_param;

drop table if exists sys_log;

drop table if exists sys_notice;

drop table if exists sys_permission;

drop table if exists sys_role;

drop table if exists sys_role_permission;

drop table if exists sys_user;

drop table if exists sys_user_role;

drop table if exists user_collection;

/*==============================================================*/
/* Table: novel                                                 */
/*==============================================================*/
create table novel
(
   novel_id             varchar(32) not null comment '主键id',
   novel_author         varchar(32) comment '作者',
   public_time          datetime comment '发布日期',
   novel_name           varchar(128) comment '小说名',
   total_line           int comment '总行数',
   total_word           bigint comment '总字数',
   novel_img            varchar(512) comment '封面',
   novel_desc           varchar(512) comment '小说描述',
   novel_introduce      varchar(1024) comment '小说介绍',
   novel_hot            bigint comment '小说热度',
   novel_up             bigint comment '小说点赞次数',
   novel_click          bigint comment '小说点击次数',
   novel_comment        bigint comment '小说评论次数',
   novel_share          bigint comment '小说分享次数',
   novel_collect        bigint comment '小说收藏次数',
   create_time          datetime comment '创建时间',
   create_by            varchar(32) comment '创建人',
   update_time          datetime comment '更新时间',
   update_by            varchar(32) comment '更新人',
   is_delete            tinyint(4) comment '0：否；1：是',
   primary key (novel_id)
)
ENGINE = InnoDB;

alter table novel comment '小说表';

/*==============================================================*/
/* Table: novel_chapter                                         */
/*==============================================================*/
create table novel_chapter
(
   chapter_id           varchar(32) not null comment '主键id',
   volume_id            varchar(32) comment '分卷id',
   chapter_name         varchar(128) comment '章节名',
   chapter_order        int comment '章节排序',
   start_line           int comment '开始行数',
   end_line             int comment '结束行数',
   total_line           int comment '总行数',
   epub_path            varchar(64) comment 'epub路径',
   parent_id            varchar(32) comment '父章节id',
   create_time          datetime comment '创建时间',
   create_by            varchar(32) comment '创建人',
   update_time          datetime comment '更新时间',
   update_by            varchar(32) comment '更新人',
   is_delete            tinyint(4) comment '0：否；1：是',
   primary key (chapter_id)
)
ENGINE = InnoDB;

alter table novel_chapter comment '小说章节表';

/*==============================================================*/
/* Table: novel_comment                                         */
/*==============================================================*/
create table novel_comment
(
   comment_id           varchar(32) comment '主键id',
   comment_type         tinyint comment '评论类型(0:小说,1:分卷,2:章节)',
   novel_id             varchar(32) comment '小说id',
   volume_id            varchar(32) comment '分卷id',
   chapter_id           varchar(32) comment '章节id',
   comment_content      varchar(512) comment '评论内容',
   comment_level        tinyint comment '评论层级(0:第一个评论,1:回复第一层评论的人,2:回复第二层人的，剩下的都算第三层)',
   reply_id             varchar(32) comment '回复的主键id',
   comment_up           bigint comment '评论点赞数',
   comment_num          bigint comment '评论数量',
   create_time          datetime comment '创建时间',
   create_by            varchar(32) comment '创建人',
   update_time          datetime comment '更新时间',
   update_by            varchar(32) comment '更新人',
   is_delete            tinyint(4) comment '0：否；1：是'
)
ENGINE = InnoDB;

alter table novel_comment comment '小说评论表';

/*==============================================================*/
/* Table: novel_data                                            */
/*==============================================================*/
create table novel_data
(
   novel_data_id        varchar(32) not null comment '小说数据主键',
   novel_id             varchar(32) comment '小说id',
   total_hot            bigint comment '总热度',
   total_up             bigint comment '总点赞',
   total_click          bigint comment '总点击',
   total_comment        bigint comment '总评论',
   total_collect        bigint comment '总收藏',
   total_share          bigint comment '总分享',
   month_hot            bigint comment '月热度',
   month_up             bigint comment '月点赞',
   month_click          bigint comment '月点击',
   month_comment        bigint comment '月评论',
   month_collect        bigint comment '月收藏',
   month_share          bigint comment '月分享',
   week_hot             bigint comment '周热度',
   week_up              bigint comment '周点赞',
   week_clike           bigint comment '周点击',
   week_comment         bigint comment '周评论',
   week_collect         bigint comment '周收藏',
   week_share           bigint comment '周分享',
   create_time          datetime comment '创建时间',
   create_by            varchar(32) comment '创建人',
   update_time          datetime comment '更新时间',
   update_by            varchar(32) comment '更新人',
   is_delete            tinyint(4) comment '0：否；1：是',
   primary key (novel_data_id)
)
ENGINE = InnoDB;

alter table novel_data comment '小说数据表';

/*==============================================================*/
/* Table: novel_file                                            */
/*==============================================================*/
create table novel_file
(
   file_id              varchar(32) not null comment '文件id',
   file_name            varchar(128) comment '文件名',
   file_path            varchar(512) comment '文件路径',
   file_type            varchar(16) comment '文件类型',
   file_md5             varchar(32) comment '文件md5',
   file_size            bigint(20) comment '文件大小',
   create_time          datetime comment '创建时间',
   create_by            varchar(32) comment '创建人',
   update_time          datetime comment '更新时间',
   update_by            varchar(32) comment '更新人',
   is_delete            tinyint(4) comment '0：否；1：是',
   primary key (file_id)
)
ENGINE = InnoDB;

alter table novel_file comment '小说文件表';

/*==============================================================*/
/* Table: novel_type_rel                                        */
/*==============================================================*/
create table novel_type_rel
(
   novel_type_rel_id    varchar(32) not null comment '主键id',
   novel_id             varchar(32) comment '小说id',
   type_code            varchar(32) comment '类型编码',
   create_time          datetime comment '创建时间',
   create_by            varchar(32) comment '创建人',
   update_time          datetime comment '更新时间',
   update_by            varchar(32) comment '更新人',
   is_delete            tinyint(4) comment '0：否；1：是',
   primary key (novel_type_rel_id)
)
ENGINE = InnoDB;

alter table novel_type_rel comment '小说类型关联表';

/*==============================================================*/
/* Table: novel_volume                                          */
/*==============================================================*/
create table novel_volume
(
   volume_id            varchar(32) not null comment '主键id',
   novel_id             varchar(32) comment '小说id',
   file_id              varchar(32) comment '文件id',
   volume_name          varchar(128) comment '分卷名',
   volume_img           varchar(512) comment '封面',
   public_time          datetime comment '发布时间',
   volume_desc          varchar(1024) comment '分卷描述',
   volume_order         int comment '分卷排序',
   total_line           int comment '总行数',
   total_word           bigint comment '总字数',
   create_time          datetime comment '创建时间',
   create_by            varchar(32) comment '创建人',
   update_time          datetime comment '更新时间',
   update_by            varchar(32) comment '更新人',
   is_delete            tinyint(4) comment '0：否；1：是',
   primary key (volume_id)
)
ENGINE = InnoDB;

alter table novel_volume comment '小说分卷表';

/*==============================================================*/
/* Table: sys_dict                                              */
/*==============================================================*/
create table sys_dict
(
   dict_id              varchar(32) not null comment '主键id',
   dict_code            varchar(32) comment '字典编码',
   dict_name            varchar(32) comment '字典名称',
   dict_desc            varchar(512) comment '字段描述',
   dict_type            tinyint(4) comment '字典类型',
   create_time          datetime comment '创建时间',
   create_by            varchar(32) comment '创建人',
   update_time          datetime comment '更新时间',
   update_by            varchar(32) comment '更新人',
   is_delete            tinyint(4) comment '0：否；1：是',
   primary key (dict_id)
)
ENGINE = InnoDB;

alter table sys_dict comment '字典表';

/*==============================================================*/
/* Table: sys_dict_param                                        */
/*==============================================================*/
create table sys_dict_param
(
   dict_param_id        varchar(32) not null comment '主键id',
   dict_id              varchar(32) comment '父字典id',
   param_code           varchar(32) comment '参数编码',
   param_name           varchar(32) comment '参数名称',
   param_desc           varchar(512) comment '参数描述',
   param_order          int(10) comment '参数排序',
   create_time          datetime comment '创建时间',
   create_by            varchar(32) comment '创建人',
   update_time          datetime comment '更新时间',
   update_by            varchar(32) comment '更新人',
   is_delete            tinyint(4) comment '0：否；1：是',
   primary key (dict_param_id)
)
ENGINE = InnoDB;

alter table sys_dict_param comment '字段子类表';

/*==============================================================*/
/* Table: sys_log                                               */
/*==============================================================*/
create table sys_log
(
   log_id               varchar(32) not null comment '主键id',
   operator_user_name   varchar(16) comment '操作人用户名',
   operator_account_name varchar(16) comment '操作人账户',
   operator_user_id     varchar(32) comment '操作人id',
   method               varchar(8) comment '请求方式',
   param                varchar(1024) comment '请求参数',
   ip                   varchar(16) comment '请求地址',
   path                 varchar(512) comment '请求路径',
   log_desc             varchar(512) comment '日志描述',
   log_type             tinyint(4) comment '日志类型（通用类型0;其他为1）',
   create_time          datetime comment '创建时间',
   create_by            varchar(32) comment '创建人',
   update_time          datetime comment '更新时间',
   update_by            varchar(32) comment '更新人',
   is_delete            tinyint(4) comment '0：否；1：是',
   primary key (log_id)
)
ENGINE = InnoDB;

alter table sys_log comment '日志表';

/*==============================================================*/
/* Table: sys_notice                                            */
/*==============================================================*/
create table sys_notice
(
   notice_id            varchar(32) comment '主键id',
   notice_title         varchar(64) comment '公告标题',
   notice_content       text comment '公告内容',
   is_open              tinyint comment '是否首页打开(0:否,1:是)',
   main_show            tinyint comment '首页展示(0:不展示,1:展示)',
   notice_order         int comment '首页排序',
   is_public            tinyint comment '是否发布(0:否,1:是)',
   create_time          datetime comment '创建时间',
   create_by            varchar(32) comment '创建人',
   update_time          datetime comment '更新时间',
   update_by            varchar(32) comment '更新人',
   is_delete            tinyint(4) comment '0：否；1：是'
)
ENGINE = InnoDB;

alter table sys_notice comment '系统公告表';

/*==============================================================*/
/* Table: sys_permission                                        */
/*==============================================================*/
create table sys_permission
(
   permission_id        varchar(32) not null comment '主键id',
   permission_code      varchar(16) comment '权限编码',
   permission_name      varchar(16) comment '权限名称',
   permission_desc      varchar(512) comment '权限描述',
   create_time          datetime comment '创建时间',
   create_by            varchar(32) comment '创建人',
   update_time          datetime comment '更新时间',
   update_by            varchar(32) comment '更新人',
   is_delete            tinyint(4) comment '0：否；1：是',
   primary key (permission_id)
)
ENGINE = InnoDB;

alter table sys_permission comment '权限表';

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
   role_id              varchar(32) not null comment '角色id',
   role_code            varchar(16) comment '角色编号',
   role_name            varchar(16) comment '角色名称',
   role_desc            varchar(512) comment '角色描述',
   create_time          datetime comment '创建时间',
   create_by            varchar(32) comment '创建人',
   update_time          datetime comment '更新时间',
   update_by            varchar(32) comment '更新人',
   is_delete            tinyint(4) comment '0：否；1：是',
   primary key (role_id)
)
ENGINE = InnoDB;

alter table sys_role comment '角色表';

/*==============================================================*/
/* Table: sys_role_permission                                   */
/*==============================================================*/
create table sys_role_permission
(
   role_permission_id   varchar(32) not null comment '主键id',
   role_id              varchar(32) comment '角色id',
   permission_id        varchar(32) comment '权限id',
   create_time          datetime comment '创建时间',
   create_by            varchar(32) comment '创建人',
   update_time          datetime comment '更新时间',
   update_by            varchar(32) comment '更新人',
   is_delete            tinyint(4) comment '0：否；1：是',
   primary key (role_permission_id)
)
ENGINE = InnoDB;

alter table sys_role_permission comment '角色权限关联表';

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   user_id              varchar(32) not null comment '主键id',
   user_name            varchar(16) comment '用户名称',
   account_name         varchar(16) comment '用户账号',
   phone                varchar(15) comment '手机号',
   email                varchar(254) comment '邮箱',
   password             varchar(64) comment '密码',
   identity_card        varchar(20) comment '身份证',
   true_name            varchar(8) comment '真实姓名',
   photo                varchar(128) comment '头像',
   qq_number            varchar(16) comment 'QQ号',
   wechat_number        varchar(32) comment '微信号',
   create_time          datetime comment '创建时间',
   create_by            varchar(32) comment '创建人',
   update_time          datetime comment '更新时间',
   update_by            varchar(32) comment '更新人',
   is_delete            tinyint(4) comment '0：否；1：是',
   primary key (user_id)
)
ENGINE = InnoDB;

alter table sys_user comment '用户表';

/*==============================================================*/
/* Table: sys_user_role                                         */
/*==============================================================*/
create table sys_user_role
(
   user_role_id         varchar(32) not null comment '主键id',
   user_id              varchar(32) comment '用户id',
   role_id              varchar(32) comment '角色id',
   create_time          datetime comment '创建时间',
   create_by            varchar(32) comment '创建人',
   update_time          datetime comment '更新时间',
   update_by            varchar(32) comment '更新人',
   is_delete            tinyint(4) comment '0：否；1：是',
   primary key (user_role_id)
)
ENGINE = InnoDB;

alter table sys_user_role comment '用户角色关联表';

/*==============================================================*/
/* Table: user_collection                                       */
/*==============================================================*/
create table user_collection
(
   collection_id        varchar(32) comment '主键id',
   collection_type      tinyint comment '收藏类型(0:分卷,1:小说,2:文件夹)',
   catalog_name         varchar(32) comment '文件夹名称',
   parent_id            varchar(32) comment '文件夹id',
   volume_id            varchar(32) comment '分卷id',
   novel_id             varchar(32) comment '小说id',
   create_time          datetime comment '创建时间',
   create_by            varchar(32) comment '创建人',
   update_time          datetime comment '更新时间',
   update_by            varchar(32) comment '更新人',
   is_delete            tinyint(4) comment '0：否；1：是'
)
ENGINE = InnoDB;

alter table user_collection comment '用户收藏表';


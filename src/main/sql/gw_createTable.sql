/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014/9/15 19:46:11                           */
/*==============================================================*/


/*==============================================================*/
/* Table: C_ATTACHMENT                                          */
/*==============================================================*/
create table C_ATTACHMENT
(
   ID                   bigint not null auto_increment comment '主键',
   NAME                 varchar(64) comment '名称',
   MEMO                 varchar(256) comment '备注',
   TABLE_NAME           varchar(16) comment '表名',
   TABLE_ID             bigint comment '表ID',
   PATH                 varchar(128) comment '路径',
   FILE_TYPE            varchar(16) comment '文件类型',
   FILE_SIZE            bigint comment '文件大小(KB)',
   CREATE_TIME          date not null comment '创建时间',
   UPDATE_TIME          date not null comment '修改时间',
   TYPE                 int not null comment '类型(1.标题图片,2 附件)',
   IS_USER              int not null default 0 comment '是否使用(0 未使用 ,1 已使用)',
   primary key (ID)
)
engine = InnoDB;

alter table C_ATTACHMENT comment '公共附件表';

/*==============================================================*/
/* Table: C_BASE_DICT                                           */
/*==============================================================*/
create table C_BASE_DICT
(
   ID                   bigint not null auto_increment comment '字典主键',
   PID                  bigint comment '字典父主键',
   CODE                 varchar(32) comment '字典代码',
   NAME                 varchar(64) comment '字典名称',
   TYPE                 varchar(16) comment '字典类别',
   MEMO                 varchar(128) comment '字典备注',
   primary key (ID)
)
engine = InnoDB;

alter table C_BASE_DICT comment '数据字典表';

/*==============================================================*/
/* Table: C_MENU                                                */
/*==============================================================*/
create table C_MENU
(
   ID                   bigint not null auto_increment comment '主键',
   PID                  bigint comment '菜单父级ID',
   NAME                 varchar(64) comment '菜单名称',
   STATES               int comment '菜单状态( -1 删除 0 保存 1 发布)',
   SORT                 int comment '菜单顺序',
   LINK                 varchar(256) comment '菜单链接',
   CODE                 varchar(64) comment '菜单编码',
   PUBCLISH_TIME        date comment '发布时间',
   CREATE_TIME          date not null comment '创建时间',
   UPDATE_TIME          date not null comment '修改时间',
   primary key (ID)
)
engine = InnoDB;

alter table C_MENU comment '菜单表';

/*==============================================================*/
/* Table: C_MESSAGE                                             */
/*==============================================================*/
create table C_MESSAGE
(
   ID                   bigint not null auto_increment comment '主键',
   NAME                 varchar(64) comment '留言者名称',
   CONTACT_INFO         varchar(128) comment '留言者联系方式',
   CONTENT              varchar(256) not null comment '留言内容',
   IS_BROWSE            int not null comment '是否浏览(0 未浏览 1 已浏览)',
   CREATE_TIME          date not null comment '留言时间',
   primary key (ID)
)
engine = InnoDB;

alter table C_MESSAGE comment '系统用户留言表';

/*==============================================================*/
/* Table: C_MODUAL                                              */
/*==============================================================*/
create table C_MODUAL
(
   ID                   bigint not null auto_increment comment '模块ID',
   NAME                 varchar(32) comment '模块名称',
   CODE                 varchar(32) comment '模块常量',
   URL                  varchar(128) comment '模块地址',
   SORT                 int comment '模块顺序',
   PID                  bigint comment '父模块ID',
   PREMIER_ID           bigint comment '一级菜单ID',
   ICON                 varchar(36) comment '样式图标',
   primary key (ID)
)
engine = InnoDB;

alter table C_MODUAL comment '系统资源表';

/*==============================================================*/
/* Table: C_TOPIC_DETAIL                                        */
/*==============================================================*/
create table C_TOPIC_DETAIL
(
   ID                   bigint not null auto_increment comment '主键',
   PARENT_ID            bigint comment '一级目录外键',
   NAME                 varchar(36) comment '名称',
   TITLE                varchar(36) comment '标题',
   SYMBOL               varchar(36) comment '来源',
   IS_TITLE_PICTURE     int comment '是否有标题图片(0 无 1 有)',
   CONTENT              longtext comment '内容',
   JSP_URL              varchar(128) comment 'Jsp连接地址',
   WORD_CONTENT         longtext comment '文本内容',
   CREATE_TIME          date not null comment '创建时间',
   UPDATE_TIME          date not null comment '更新时间',
   primary key (ID)
)
engine = InnoDB;

alter table C_TOPIC_DETAIL comment '栏目主题明细表';

/*==============================================================*/
/* Table: C_TOPIC_PARENT                                        */
/*==============================================================*/
create table C_TOPIC_PARENT
(
   ID                   bigint not null auto_increment comment '主键',
   PID                  bigint not null comment '上级目录ID',
   NAME                 varchar(32) not null comment '目录名称',
   TITLE                varchar(64) comment '目录标题',
   CODE                 varchar(16) not null comment '代码',
   CONTENT              varchar(256) comment '目录介绍',
   SORT                 int comment '目录顺序',
   CREATE_TIME          date not null comment '发布时间',
   UPDATE_TIME          date not null comment '修改时间',
   primary key (ID)
)
engine = InnoDB;

alter table C_TOPIC_PARENT comment '专题表';

/*==============================================================*/
/* Table: C_TOPIC_TYPE_RELATION                                 */
/*==============================================================*/
create table C_TOPIC_TYPE_RELATION
(
   ID                   bigint not null auto_increment,
   TOPIC_ID             bigint comment '专题ID',
   TYPE_ID              bigint comment '类型ID',
   primary key (ID)
)
engine = InnoDB;

alter table C_TOPIC_TYPE_RELATION comment '专题分类关联表';

/*==============================================================*/
/* Table: C_TYPE                                                */
/*==============================================================*/
create table C_TYPE
(
   ID                   bigint not null auto_increment comment '类型ID',
   PID                  bigint not null comment '一级ID',
   NAME                 varchar(64) comment '类型名称',
   CREATE_TIME          date not null comment '创建时间',
   UPDATE_TIME          date not null comment '修改时间',
   SORT                 int not null comment '排序',
   primary key (ID)
)
engine = InnoDB;

alter table C_TYPE comment '类型表';

/*==============================================================*/
/* Table: C_USER                                                */
/*==============================================================*/
create table C_USER
(
   ID                   bigint not null auto_increment comment '用户主键',
   NAME                 varchar(32) comment '用户名称',
   LOGIN_NAME           varchar(32) comment '登录名称',
   PASSWORD             varchar(32) comment '密码',
   PHONE                varchar(16) comment '手机',
   CREATE_TIME          date not null comment '创建时间',
   UPDATE_TIME          date not null comment '更新时间',
   primary key (ID)
)
engine = InnoDB;

alter table C_USER comment '系统用户表';


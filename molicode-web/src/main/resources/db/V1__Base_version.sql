CREATE TABLE `ac_common_ext_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `owner_type` int(11) NOT NULL COMMENT '归属类型：1.系统，2.项目',
  `owner_code` varchar(50)  NOT NULL COMMENT '归属码',
  `ext_key` varchar(50)  NOT NULL COMMENT '扩展key',
  `ext_value` varchar(4000)  DEFAULT NULL COMMENT '扩展值',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '类型：1.文本，2.json,3.xml, 4.properties, 5.开关',
  `creator` varchar(20)   DEFAULT NULL COMMENT '创建人',
  `operator` varchar(20)   DEFAULT NULL COMMENT '修改人',
  `concurrent_version` bigint(20) DEFAULT '0' COMMENT '并发版本号',
  `data_version` bigint(20) DEFAULT '0' COMMENT '数据版本',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态：1.有效，-1.无效',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_extkeyvalue` (`owner_type`,`owner_code`,`ext_key`)
) COMMENT='通用扩展属性';

CREATE TABLE `ac_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` int(11) NOT NULL COMMENT '类型：1.文本，2.json,3.xml, 4.properties, 5.开关',
  `project_key` varchar(50)  DEFAULT NULL COMMENT '项目key',
  `scope` int(11) DEFAULT NULL COMMENT '范围：1.本地，2.仓库',
  `config_key` varchar(50)  NOT NULL COMMENT '配置key',
  `config_value` varchar(2000)  DEFAULT NULL COMMENT '配置值',
  `ext1` varchar(50)  DEFAULT NULL COMMENT '扩展1',
  `ext2` varchar(50)  DEFAULT NULL COMMENT '扩展2',
  `ext3` varchar(50)  DEFAULT NULL COMMENT '扩展3',
  `creator` varchar(20)  DEFAULT NULL COMMENT '创建人',
  `operator` varchar(20)  DEFAULT NULL COMMENT '修改人',
  `concurrent_version` bigint(20) DEFAULT '0' COMMENT '并发版本号',
  `data_version` bigint(20) DEFAULT '0' COMMENT '数据版本',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态：1.有效，-1.无效',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
)  COMMENT='配置信息';

CREATE TABLE `ac_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50)  DEFAULT NULL COMMENT '名称',
  `remark` varchar(500)  DEFAULT NULL COMMENT '备注',
  `project_key` varchar(50)  DEFAULT NULL COMMENT '项目key',
  `type` int(11) DEFAULT NULL COMMENT '类型:1.本地，2.仓库',
  `ext1` varchar(50)   DEFAULT NULL COMMENT '扩展1',
  `ext2` varchar(50)   DEFAULT NULL COMMENT '扩展2',
  `ext3` varchar(50)   DEFAULT NULL COMMENT '扩展3',
  `creator` varchar(20)   DEFAULT NULL COMMENT '创建人',
  `operator` varchar(20)   DEFAULT NULL COMMENT '修改人',
  `concurrent_version` bigint(20) DEFAULT '0' COMMENT '并发版本号',
  `data_version` bigint(20) DEFAULT '0' COMMENT '数据版本',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态：1.有效，-1.无效',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
)  COMMENT='项目';

CREATE TABLE `ac_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `user_name` varchar(50)  NOT NULL COMMENT '用户名称',
  `nick_name` varchar(50)  DEFAULT NULL COMMENT '用户昵称',
  `gender` tinyint(4) DEFAULT '1' COMMENT '性别：1.男，2.女',
  `password_md5` varchar(50)  DEFAULT NULL COMMENT '密码MD5',
  `birth_day` date DEFAULT NULL COMMENT '出生日期',
  `user_mark` varchar(20)  DEFAULT NULL COMMENT '用户标签',
  `role_code` varchar(50)  DEFAULT NULL COMMENT '角色码',
  `remark` varchar(200)  DEFAULT NULL COMMENT '备注',
  `ext1` varchar(50)  DEFAULT NULL COMMENT '扩展1',
  `ext2` varchar(50)  DEFAULT NULL COMMENT '扩展2',
  `ext3` varchar(50)  DEFAULT NULL COMMENT '扩展3',
  `creator` varchar(20)  DEFAULT NULL COMMENT '创建人',
  `operator` varchar(20)  DEFAULT NULL COMMENT '操作人',
  `concurrent_version` bigint(20) DEFAULT '0' COMMENT '并发版本号',
  `data_version` bigint(20) DEFAULT '0' COMMENT '数据版本号',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态：1.正常，-1.删除',
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
)COMMENT='用户信息';


INSERT INTO ac_user (`user_name`, `nick_name`, `gender`, `password_md5`, `birth_day`, `role_code`, `remark`, `concurrent_version`, `data_version`, `status`, `created`, `modified`) VALUES ('admin', '超级管理员', '1', 'b19ab76ee452116f55a844249d8bc6fa', '1990-01-01', 'sys_admin', '默认账号', '1', '1', '1', '2019-07-01 00:00:00', '2019-07-01 00:00:00');

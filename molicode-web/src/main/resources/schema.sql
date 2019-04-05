CREATE TABLE `ac_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `user_name` varchar(50)  NOT NULL COMMENT '用户名称',
  `nick_name` varchar(50)  DEFAULT NULL COMMENT '用户昵称',
  `gender` tinyint(4) DEFAULT '1' COMMENT '性别：1.男，2.女',
  `password_md5` varchar(50)  DEFAULT NULL COMMENT '密码MD5',
  `birth_day` date DEFAULT NULL COMMENT '出生日期',
  `user_mark` varchar(20)  DEFAULT NULL COMMENT '用户标签',
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

CREATE TABLE `fuck_you` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `user_name` varchar(50)  NOT NULL COMMENT '用户名称',
  `nick_name` varchar(50)  DEFAULT NULL COMMENT '用户昵称'
  PRIMARY KEY (`id`)
)COMMENT='test';

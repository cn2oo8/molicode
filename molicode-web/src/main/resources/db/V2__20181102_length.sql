ALTER TABLE ac_common_ext_info
CHANGE COLUMN `ext_value`  `ext_value` varchar(4000)  DEFAULT NULL COMMENT '扩展值';

ALTER TABLE ac_config
CHANGE COLUMN `config_value`  `config_value` varchar(4000)  DEFAULT NULL COMMENT '配置值';
